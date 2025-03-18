package hafnium.components;

import is.rebbi.core.util.StringUtilities;
import ng.appserver.NGContext;
import ng.appserver.templating.NGComponent;

public abstract class USBaseComponent extends NGComponent {

	/**
	 * A unique ID suitable for use in client side operations.
	 */
	private String _uniqueID;

	public USBaseComponent( NGContext context ) {
		super( context );
	}

	/**
	 * @return value of the optional "id" binding for components that generate a uniqueID.
	 */
	private String id() {
		return (String)valueForBinding( "id" );
	}

	/**
	 * @return A unique ID that can be used for creating unique IDs for elements on a page.
	 */
	public String uniqueID() {

		if( _uniqueID == null ) {
			if( StringUtilities.hasValue( id() ) ) {
				_uniqueID = id();
			}
			else {
				_uniqueID = context().elementID().toString();
			}

			_uniqueID = "u_" + _uniqueID.replace( '.', '_' );
		}

		return _uniqueID;
	}

	/**
	 * @return Name of component to wrap around content when viewing objects.
	 */
	public String lookName() {
		return application().properties().get( "hafnium.defaultLookName" );
	}

	/**
	 * @return Name of component to wrap around content when editing objects.
	 */
	public String editLookName() {
		return application().properties().get( "hafnium.editLookName" );
	}
}