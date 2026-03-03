package hafnium.components.relationships;

import java.util.List;

import org.apache.cayenne.Persistent;
import org.apache.cayenne.PersistentObject;
import org.apache.cayenne.access.DataContext;
import org.apache.cayenne.map.ObjRelationship;

import hafnium.components.USBaseComponent;
import hafnium.urls.Inspection;
import is.rebbi.core.humanreadable.HumanReadableUtils;
import ng.appserver.NGActionResults;
import ng.appserver.NGContext;
import ng.kvc.NGKeyValueCoding;
import ng.kvc.NGKeyValueCodingAdditions;

/**
 * Inspects a to-many relationship, allowing editing, addition and removal of objects.
 */

public class USToManyRelationship extends USBaseComponent {

	public PersistentObject currentObject;

	public USToManyRelationship( NGContext context ) {
		super( context );
	}

	@Override
	public boolean synchronizesVariablesWithBindings() {
		return false;
	}

	private PersistentObject object() {
		return (PersistentObject)valueForBinding( "object" );
	}

	private String key() {
		return (String)valueForBinding( "key" );
	}

	private String displayKey() {
		return (String)valueForBinding( "displayKey" );
	}

	private ObjRelationship relationship() {
		return object().getObjectContext().getEntityResolver().getObjEntity( object().getClass() ).getRelationship( key() );
	}

	@SuppressWarnings("unchecked")
	public List<PersistentObject> destinationObjects() {
		return (List<PersistentObject>)NGKeyValueCoding.Utility.valueForKey( object(), key() );
	}

	public NGActionResults createObject() {
		DataContext dc = (DataContext)object().getObjectContext();
		String destinationEntityName = relationship().getTargetEntityName();
		Persistent newObject = dc.newObject( destinationEntityName );
		object().addToManyTarget( relationship().getName(), newObject, true );
		return Inspection.editObjectInContext( newObject, context() );
	}

	public NGActionResults removeObject() {
		object().removeToManyTarget( relationship().getName(), currentObject, true );
		return context().page();
	}

	public Object displayString() {
		Object displayString = null;

		if( displayKey() == null ) {
			displayString = HumanReadableUtils.toStringHuman( currentObject );
		}
		else {
			displayString = NGKeyValueCodingAdditions.Utility.valueForKeyPath( currentObject, displayKey() );
		}

		return displayString;
	}

	public NGActionResults selectObject() {
		USRelationshipTargetSelection nextPage = pageWithName( USRelationshipTargetSelection.class );
		nextPage.object = object();
		nextPage.key = key();
		nextPage.callingComponent = context().page();
		return nextPage;
	}
}
