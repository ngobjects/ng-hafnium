package hafnium.components;

import hafnium.urls.Inspection;
import ng.appserver.NGActionResults;
import ng.appserver.NGContext;
import ng.appserver.templating.NGComponent;

/**
 * Link to edit objects.
 */

public class EditLink extends NGComponent {

	public EditLink( NGContext context ) {
		super( context );
	}

	@Override
	public boolean synchronizesVariablesWithBindings() {
		return false;
	}

	/**
	 * Disable the link if [object] is null.
	 */
	public boolean disabled() {
		if( object() == null ) {
			return true;
		}

		final Boolean value = (Boolean)valueForBinding( "disabled" );
		return value != null && value;
	}

	/**
	 * @return The value of the "object"-binding.
	 */
	public Object object() {
		return valueForBinding( "object" );
	}

	/**
	 * @return An edit page for [object]
	 */
	public NGActionResults selectObject() {
		return Inspection.editObjectInContext( object(), context() );
	}
}
