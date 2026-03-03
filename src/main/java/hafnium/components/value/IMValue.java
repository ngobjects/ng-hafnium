package hafnium.components.value;

import java.util.List;

import org.apache.cayenne.PersistentObject;

import ng.appserver.NGContext;
import ng.appserver.templating.NGComponent;
import ng.kvc.NGKeyValueCodingAdditions;

/**
 * Display any object as a human readable string in a component.
 */

public class IMValue extends NGComponent {

	public IMValue( NGContext context ) {
		super( context );
	}

	@Override
	public boolean synchronizesVariablesWithBindings() {
		return false;
	}

	public Object object() {
		if( hasBinding( "object" ) ) {
			return valueForBinding( "object" );
		}

		if( hasBinding( "value" ) ) {
			return valueForBinding( "value" );
		}

		return null;
	}

	public String keyPath() {
		return (String)valueForBinding( "keyPath" );
	}

	public boolean isArray() {
		return object() instanceof List;
	}

	public boolean disabled() {
		final Boolean value = (Boolean)valueForBinding( "disabled" );

		if( value != null && value ) {
			return true;
		}

		if( !(object() instanceof PersistentObject) ) {
			return true;
		}

		return false;
	}

	public Object valueForDisplay() {
		if( object() != null && keyPath() != null ) {
			return NGKeyValueCodingAdditions.Utility.valueForKeyPath( object(), keyPath() );
		}

		return object();
	}
}
