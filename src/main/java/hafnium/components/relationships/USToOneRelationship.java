package hafnium.components.relationships;

import org.apache.cayenne.Persistent;
import org.apache.cayenne.PersistentObject;
import org.apache.cayenne.access.DataContext;
import org.apache.cayenne.map.ObjEntity;
import org.apache.cayenne.map.ObjRelationship;

import hafnium.components.USBaseComponent;
import hafnium.urls.Inspection;
import is.rebbi.core.humanreadable.HumanReadableUtils;
import ng.appserver.NGActionResults;
import ng.appserver.NGContext;
import ng.kvc.NGKeyValueCoding;
import ng.kvc.NGKeyValueCodingAdditions;

/**
 * Inspects a to-one relationship, allowing editing, addition and removal of objects.
 */

public class USToOneRelationship extends USBaseComponent {

	public USToOneRelationship( NGContext context ) {
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
		ObjEntity objEntity = object().getObjectContext().getEntityResolver().getObjEntity( object().getClass() );
		return objEntity.getRelationship( key() );
	}

	public PersistentObject destinationObject() {
		return (PersistentObject)NGKeyValueCoding.Utility.valueForKey( object(), key() );
	}

	public NGActionResults createObject() {
		DataContext dc = (DataContext)object().getObjectContext();
		String destinationEntityName = relationship().getTargetEntityName();
		Persistent newObject = dc.newObject( destinationEntityName );
		NGKeyValueCoding.Utility.takeValueForKey( object(), newObject, relationship().getName() );
		return Inspection.editObjectInContext( newObject, context() );
	}

	public NGActionResults removeObject() {
		NGKeyValueCoding.Utility.takeValueForKey( object(), null, relationship().getName() );
		return null;
	}

	public Object displayString() {
		Object displayString = null;

		if( displayKey() == null ) {
			displayString = HumanReadableUtils.toStringHuman( destinationObject() );
		}
		else {
			displayString = NGKeyValueCodingAdditions.Utility.valueForKeyPath( destinationObject(), displayKey() );
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
