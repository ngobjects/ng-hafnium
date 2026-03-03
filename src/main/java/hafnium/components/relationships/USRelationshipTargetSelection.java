package hafnium.components.relationships;

import java.util.List;

import org.apache.cayenne.CayenneRuntimeException;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.PersistentObject;
import org.apache.cayenne.map.ObjRelationship;
import org.apache.cayenne.util.Util;

import hafnium.components.USListPageEdit;
import jambalaya.definitions.EntityDefinition;
import ng.appserver.NGActionResults;
import ng.appserver.NGContext;
import ng.appserver.templating.NGComponent;
import ng.kvc.NGKeyValueCoding;

public class USRelationshipTargetSelection extends USListPageEdit {

	public NGComponent callingComponent;
	public PersistentObject object;
	public String key;

	public USRelationshipTargetSelection( NGContext context ) {
		super( context );
	}

	public String sourceEntityName() {
		return EntityDefinition.icelandicName( object.getObjectId().getEntityName() );
	}

	public String destinationEntityName() {
		return EntityDefinition.icelandicName( relationship().getTargetEntityName() );
	}

	public String destinationEntityNamePlural() {
		return EntityDefinition.icelandicNamePlural( relationship().getTargetEntityName() );
	}

	public ObjRelationship relationship() {
		return oc().getEntityResolver().getObjEntity( object.getObjectId().getEntityName() ).getRelationship( key );
	}

	@Override
	public Class entityClass() {
		String name = relationship().getTargetEntity().getJavaClassName();

		try {
			return Util.getJavaClass( name );
		}
		catch( ClassNotFoundException e ) {
			throw new CayenneRuntimeException( "Failed to load class " + name + ": " + e.getMessage(), e );
		}
	}

	public NGActionResults cancel() {
		return callingComponent;
	}

	public NGActionResults selectObject() {

		if( relationship().isToMany() ) {
			object.addToManyTarget( key, currentObject, true );
		}
		else {
			NGKeyValueCoding.Utility.takeValueForKey( object, currentObject, key );
		}

		return callingComponent;
	}

	@Override
	protected ObjectContext oc() {
		return object.getObjectContext();
	}

	private Object currentRelationshipValue() {
		return NGKeyValueCoding.Utility.valueForKey( object, key );
	}

	public boolean currentChecked() {
		if( relationship().isToMany() ) {
			return ((List)currentRelationshipValue()).contains( currentObject );
		}

		return currentRelationshipValue().equals( currentObject );
	}

	public void setCurrentChecked( boolean isChecked ) {
		if( relationship().isToMany() ) {
			if( isChecked ) {
				if( !currentChecked() ) {
					object.addToManyTarget( key, currentObject, true );
				}
			}
			else {
				if( currentChecked() ) {
					object.removeToManyTarget( key, currentObject, true );
				}
			}
		}
	}

	public NGActionResults linkSelected() {
		return callingComponent;
	}
}
