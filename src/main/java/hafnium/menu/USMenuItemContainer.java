package hafnium.menu;

import ng.appserver.NGActionResults;

public class USMenuItemContainer extends USMenuItem {

	@Override
	public NGActionResults action() {
		throw new RuntimeException( "Action should never be invoked on a container item" );
	}

	public static USMenuItemContainer create( final String name, final String iconClasses ) {
		USMenuItemContainer item = new USMenuItemContainer();
		item.setName( name );
		item.setIconClasses( iconClasses );
		return item;
	}
}