package hafnium.components;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.cayenne.DataObject;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.exp.Expression;
import org.apache.cayenne.exp.ExpressionFactory;
import org.apache.cayenne.map.ObjAttribute;
import org.apache.cayenne.query.ObjectSelect;
import org.apache.cayenne.query.Ordering;
import org.apache.cayenne.query.PrefetchTreeNode;

import hafnium.urls.Inspection;
import is.rebbi.core.util.StringUtilities;
import jambalaya.CayenneUtils;
import jambalaya.Jambalaya;
import jambalaya.definitions.AttributeDefinition;
import jambalaya.definitions.EntityDefinition;
import ng.appserver.NGActionResults;
import ng.appserver.NGContext;
import ng.kvc.NGKeyValueCodingAdditions;

public class USListPageEdit extends USBaseComponent {

	/**
	 * The Object Context to fetch into.
	 */
	private ObjectContext _oc;

	/**
	 * A generic search string, used to generate a qualifier.
	 */
	public String searchString;

	/**
	 * The selected entityViewDefinition
	 */
	private Class _entityClass;

	/**
	 * the object currently being iterated over in lists.
	 */
	public DataObject currentObject;

	/**
	 * The keyPath currently being iterated over in lists.
	 */
	public String currentKeyPath;

	/**
	 * Index of currently selected batch.
	 */
	public int currentBatchIndex = 0;

	/**
	 * Size of each batch.
	 */
	public int batchSize = 200;

	/**
	 * Estimated number of objects returned from the query;
	 */
	private Long _numberOfObjects;

	/**
	 * Orderings to use when sorting the objects.
	 */
	private List<Ordering> _orderings;

	/**
	 * A list of selected objects in the UI
	 */
	public Set<DataObject> selectedObjects = new HashSet<>();

	public USListPageEdit( NGContext context ) {
		super( context );
	}

	public List<Ordering> orderings() {
		if( _orderings == null ) {
			_orderings = new ArrayList<>();
			_orderings.add( initialOrdering() );
		}

		return _orderings;
	}

	public NGActionResults edit() {
		return Inspection.editObjectInContext( currentObject, context() );
	}

	protected ObjectContext oc() {
		if( _oc == null ) {
			_oc = Jambalaya.newContext();
		}

		return _oc;
	}

	public boolean hasMultipleBatches() {
		return numberOfBatches() > 1;
	}

	public Integer currentBatchIndexForDisplay() {
		return currentBatchIndex + 1;
	}

	public void setCurrentBatchIndexForDisplay( Integer value ) {
		currentBatchIndex = value - 1;
	}

	public Integer firstObjectIndexForDisplay() {
		return firstObjectIndex() + 1;
	}

	public List<Integer> batches() {
		return IntStream.rangeClosed( 1, (int)numberOfBatches() ).boxed().collect( Collectors.toList() );
	}

	/**
	 * @return An expression based on the user's input
	 */
	private Expression expression() {

		if( !StringUtilities.hasValue( searchString ) ) {
			return null;
		}

		Expression fromEntity = CayenneUtils.allQualifier( oc(), searchString, entityClass() );
		Expression fromKeyPaths = CayenneUtils.allExpression( oc(), searchString, entityClass(), keyPathsToShow() );
		Expression e = ExpressionFactory.or( fromEntity, fromKeyPaths );

		return e;
	}

	public long numberOfObjects() {
		if( _numberOfObjects == null ) {
			_numberOfObjects = CayenneUtils.count( oc(), entityClass(), expression() );
		}

		return _numberOfObjects;
	}

	public long numberOfBatches() {
		long numberOfBatches = numberOfObjects() / batchSize;

		if( numberOfObjects() % batchSize != 0 ) {
			numberOfBatches++;
		}

		return numberOfBatches;
	}

	public int firstObjectIndex() {
		return currentBatchIndex * batchSize;
	}

	public int lastObjectIndex() {
		return firstObjectIndex() + batchSize;
	}

	public List<?> objects() {
		ObjectSelect<?> query = ObjectSelect.query( entityClass() );

		query.limit( batchSize );
		query.offset( firstObjectIndex() );

		for( String keyPath : CayenneUtils.keyPathsToPrefetch( oc(), entityDefinition().entityClass(), keyPathsToShow() ) ) {
			query.prefetch( PrefetchTreeNode.withPath( keyPath, PrefetchTreeNode.DISJOINT_BY_ID_PREFETCH_SEMANTICS ) );
		}

		query.where( expression() );
		query.orderBy( orderings() ); // FIXME: This is currently changing the query. Need to find out what's happening.

		_numberOfObjects = null;
		return query.select( oc() );
	}

	public EntityDefinition entityDefinition() {
		return EntityDefinition.get( entityClass() );
	}

	public void setEntityClass( Class value ) {
		_entityClass = value;
	}

	public Class entityClass() {
		return _entityClass;
	}

	public Object currentValue() {
		return NGKeyValueCodingAdditions.Utility.valueForKeyPath( currentObject, currentKeyPath );
	}

	public List<String> keyPathsToShow() {
		List<AttributeDefinition> attributesToShow = entityDefinition().attributesToShow();

		if( !attributesToShow.isEmpty() ) {
			return attributesToShow.stream().map( AttributeDefinition::name ).collect( Collectors.toList() );
		}
		else {
			return Jambalaya.serverRuntime().getDataDomain().getEntityResolver().getObjEntity( entityClass() ).getAttributes().stream().map( ObjAttribute::getName ).collect( Collectors.toList() );
		}
	}

	private Ordering initialOrdering() {
		return new Ordering( keyPathsToShow().get( 0 ) );
	}

	public NGActionResults search() {
		currentBatchIndex = 0;
		return null;
	}

	public String currentKeyPathDisplayName() {
		return currentAttributeViewDefinition().icelandicName();
	}

	public AttributeDefinition currentAttributeViewDefinition() {
		return entityDefinition().attributeNamed( currentKeyPath );
	}

	public NGActionResults createObject() {
		ObjectContext childContext = Jambalaya.newContext( oc() );
		Object object = childContext.newObject( entityClass() );
		NGActionResults nextPage = Inspection.editObjectInContext( object, context() );
		return nextPage;
	}

	public boolean currentIsSelected() {
		return selectedObjects.contains( currentObject );
	}

	public void setCurrentIsSelected( boolean value ) {
		if( value ) {
			if( !selectedObjects.contains( currentObject ) ) {
				selectedObjects.add( currentObject );
			}
		}
		else {
			if( selectedObjects.contains( currentObject ) ) {
				selectedObjects.remove( currentObject );
			}
		}
	}

	public NGActionResults deleteSelectedObjects() {
		oc().deleteObjects( selectedObjects );
		oc().commitChanges();

		selectedObjects = new HashSet<>();

		return search();
	}

	public String currentCheckboxID() {
		return uniqueID();
	}

	public NGActionResults toggleSelected() {
		setCurrentIsSelected( !currentIsSelected() );
		return null;
	}

	public String currentTRClass() {
		return currentIsSelected() ? "danger" : null;
	}
}