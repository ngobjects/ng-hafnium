package hafnium.components.relationships;

import org.apache.cayenne.PersistentObject;
import org.apache.cayenne.map.ObjRelationship;

import ng.appserver.NGContext;
import ng.appserver.templating.NGComponent;

public class USRelationship extends NGComponent {

	public USRelationship( NGContext context ) {
		super( context );
	}

	@Override
	public boolean synchronizesVariablesWithBindings() {
		return false;
	}

	public PersistentObject object() {
		return (PersistentObject)valueForBinding( "object" );
	}

	public String key() {
		return (String)valueForBinding( "key" );
	}

	public Boolean disableObjectSelection() {
		return (Boolean)valueForBinding( "disableObjectSelection" );
	}

	public ObjRelationship relationship() {
		return object().getObjectContext().getEntityResolver().getObjEntity( object().getClass() ).getRelationship( key() );
	}
}
