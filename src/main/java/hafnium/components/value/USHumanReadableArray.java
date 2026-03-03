package hafnium.components.value;

import java.util.List;

import org.apache.cayenne.PersistentObject;

import is.rebbi.core.humanreadable.HumanReadableUtils;
import ng.appserver.NGContext;
import ng.appserver.templating.NGComponent;
import ng.kvc.NGKeyValueCodingAdditions;

public class USHumanReadableArray extends NGComponent {

	public int currentIndex;
	public Object currentObject;

	public USHumanReadableArray( NGContext context ) {
		super( context );
	}

	@Override
	public boolean synchronizesVariablesWithBindings() {
		return false;
	}

	public String valueWhenEmpty() {
		return (String)valueForBinding( "valueWhenEmpty" );
	}

	private String keyPath() {
		return (String)valueForBinding( "keyPath" );
	}

	private boolean forceLowercase() {
		final Boolean value = (Boolean)valueForBinding( "forceLowercase" );
		return value != null && value;
	}

	public List<?> objects() {
		return (List<?>)valueForBinding( "objects" );
	}

	public String lastSeparator() {
		final String lastSeparator = (String)valueForBinding( "lastSeparator" );

		if( lastSeparator == null ) {
			return "og";
		}

		return lastSeparator;
	}

	public boolean hasObjects() {
		return objects() != null && !objects().isEmpty();
	}

	public Object currentString() {

		Object value = null;

		if( keyPath() != null ) {
			if( currentObject != null ) {
				value = NGKeyValueCodingAdditions.Utility.valueForKeyPath( currentObject, keyPath() );
			}
		}
		else {
			value = HumanReadableUtils.toStringHuman( currentObject );
		}

		if( forceLowercase() && (value instanceof String) ) {
			if( value != null && currentIndex > 0 ) {
				value = ((String)value).toLowerCase();
			}
		}

		return value;
	}

	/**
	 * The separator shown between records
	 */
	public String separator() {
		if( currentIndex == objects().size() - 1 ) {
			return "";
		}

		if( currentIndex == objects().size() - 2 ) {
			return " " + lastSeparator() + " ";
		}

		return ", ";
	}

	public boolean isInspectable() {
		return currentObject instanceof PersistentObject;
	}
}
