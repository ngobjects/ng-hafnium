package hafnium.menu;

import java.util.function.Function;

import ng.appserver.NGActionResults;
import ng.appserver.NGContext;

public class USMenuItemFunction extends USMenuItem {

	private Function<NGContext, NGActionResults> _function;
	private NGContext _context;

	private void setFunction( Function<NGContext, NGActionResults> function ) {
		_function = function;
	}

	public static USMenuItemFunction create( String name, String iconClasses, Function<NGContext, NGActionResults> function, NGContext context ) {
		USMenuItemFunction item = new USMenuItemFunction();
		item.setName( name );
		item.setIconClasses( iconClasses );
		item.setFunction( function );
		item._context = context;
		return item;
	}

	@Override
	public NGActionResults action() {
		return _function.apply( _context );
	}
}