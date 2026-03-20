package hafnium.menu;

import java.util.function.Supplier;

import ng.appserver.NGActionResults;
import ng.appserver.NGContext;

public class USMenuItemActionMethod extends USMenuItem {

	private Supplier<NGActionResults> _supplier;

	private void setSupplier( Supplier<NGActionResults> supplier ) {
		_supplier = supplier;
	}

	public static USMenuItemActionMethod create( String name, String iconClasses, Supplier<NGActionResults> supplier ) {
		USMenuItemActionMethod item = new USMenuItemActionMethod();
		item.setName( name );
		item.setIconClasses( iconClasses );
		item.setSupplier( supplier );
		return item;
	}

	@Override
	public NGActionResults invokeActionInContext( NGContext context ) {
		return _supplier.get();
	}
}
