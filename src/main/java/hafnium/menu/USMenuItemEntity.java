package hafnium.menu;

import jambalaya.definitions.EntityDefinition;
import ng.appserver.NGActionResults;

public class USMenuItemEntity extends USMenuItem {

	private String _entityName;

	public static USMenuItemEntity create( String entityName, String iconClasses ) {
		USMenuItemEntity item = new USMenuItemEntity();
		item.setEntityName( entityName );
		item.setIconClasses( iconClasses );
		return item;
	}

	@Override
	public String name() {
		return EntityDefinition.get( _entityName ).icelandicNamePlural();
	}

	public String entityName() {
		return _entityName;
	}

	public void setEntityName( String value ) {
		_entityName = value;
	}

	@Override
	public NGActionResults action() {
		//		return Inspection.openListPage( EntityDefinition.get( _entityName ).entityClass() );
		throw new RuntimeException( "Not supported" );
	}
}