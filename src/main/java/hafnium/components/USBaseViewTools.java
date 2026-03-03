package hafnium.components;

import java.util.function.Predicate;

import ng.appserver.NGContext;

public class USBaseViewTools extends USViewPage {

	/**
	 * Predicate that determines if the view tools should be shown for the current request context.
	 * If null, view tools are always hidden.
	 */
	private static Predicate<NGContext> _accessCheck;

	public USBaseViewTools( NGContext context ) {
		super( context );
	}

	/**
	 * Set the access check predicate. The predicate receives the current NGContext
	 * and should return true if the view tools should be shown.
	 */
	public static void setAccessCheck( Predicate<NGContext> accessCheck ) {
		_accessCheck = accessCheck;
	}

	public boolean showViewTools() {
		return _accessCheck != null && _accessCheck.test( context() );
	}
}