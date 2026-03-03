package hafnium.components.fields;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

import hafnium.components.USBaseComponent;
import ng.appserver.NGContext;

/**
 * A field for entering a date with a time.
 */

public class USLocalDateTimeField extends USBaseComponent {

	private static final DateTimeFormatter DATE_TIME_FORMATTER_WITH_TIME = DateTimeFormatter.ofPattern( "dd.MM.yyyy HH:mm" );

	public USLocalDateTimeField( NGContext context ) {
		super( context );
	}

	@Override
	public boolean synchronizesVariablesWithBindings() {
		return false;
	}

	public String stringValue() {
		LocalDateTime value = (LocalDateTime)valueForBinding( "value" );

		if( value == null ) {
			return null;
		}

		return DATE_TIME_FORMATTER_WITH_TIME.format( value );
	}

	public void setStringValue( String value ) {
		TemporalAccessor localDate;

		if( value != null ) {
			localDate = LocalDateTime.parse( value, DATE_TIME_FORMATTER_WITH_TIME );
		}
		else {
			localDate = null;
		}

		setValueForBinding( localDate, "value" );
	}
}
