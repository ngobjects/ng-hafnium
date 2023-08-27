package hafnium.menu;

import ng.appserver.NGActionResults;
import ng.appserver.NGComponent;

public class USMenuItemPage extends USMenuItem {

	private Class<? extends NGComponent> _pageClass;

	public Class<? extends NGComponent> pageClass() {
		return _pageClass;
	}

	public void setPageClass( Class<? extends NGComponent> value ) {
		_pageClass = value;
	}

	public static USMenuItemPage create( String name, String iconClasses, Class<? extends NGComponent> pageClass ) {

		if( name == null ) {
			name = pageClass.getSimpleName();
		}

		USMenuItemPage item = new USMenuItemPage();
		item.setName( name );
		item.setIconClasses( iconClasses );
		item.setPageClass( pageClass );
		return item;
	}

	@Override
	public NGActionResults action() {

		if( pageClass() == null ) {
			return null;
		}

		throw new RuntimeException( "We're missing a context" );
		//		return NGApplication.application().pageWithName( pageClass() );
	}
}