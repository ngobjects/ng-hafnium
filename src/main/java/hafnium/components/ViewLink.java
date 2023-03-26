package hafnium.components;

import org.apache.cayenne.Persistent;

import hafnium.urls.URLProviders;
import ng.appserver.NGComponent;
import ng.appserver.NGContext;

/**
 * Link to view objects.
 *
 * FIXME: Make stateless
 */

public class ViewLink extends NGComponent {

	public ViewLink( NGContext context ) {
		super( context );
	}

	@Override
	public boolean synchronizesVariablesWithBindings() {
		return false;
	}

	/**
	 * Disable the link if the object is null.
	 */
	public Boolean disabled() {

		if( object() == null ) {
			return true;
		}

		// We can't generate URLs for objects that haven't been committed to the DB, so we disable the link
		if( isNewPersistentObject() ) {
			return true;
		}

		final Boolean value = (Boolean)valueForBinding( "disabled" );
		return value != null && value;
	}

	private boolean isNewPersistentObject() {
		return object() instanceof Persistent && ((Persistent)object()).getObjectId().isTemporary();
	}

	/**
	 * @return The value of the "object"-binding.
	 */
	public Object object() {
		return valueForBinding( "object" );
	}

	/**
	 * @return The URL for the link.
	 */
	public String href() {
		System.out.println( 1 );
		if( object() == null ) {
			return null;
		}

		System.out.println( 2 );
		if( isNewPersistentObject() ) {
			return null;
		}

		System.out.println( 3 );
		return URLProviders.urlForObject( object() );
	}

	/**
	 * FIXME: wat?
	 */
	public boolean showPlaceholder() {
		if( disabled() && valueForBinding( "class" ) != null ) {
			return true;
		}

		return false;
	}

	public String disabledClass() {
		return valueForBinding( "class" ) + " disabled";
	}
}