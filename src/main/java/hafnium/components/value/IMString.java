package hafnium.components.value;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Locale;

import is.rebbi.core.humanreadable.HumanReadableUtils;
import is.rebbi.core.util.StringUtilities;
import ng.appserver.NGContext;
import ng.appserver.templating.NGComponent;

/**
 * String component with some added sugar for displaying values.
 */

public class IMString extends NGComponent {

	public IMString( NGContext context ) {
		super( context );
	}

	@Override
	public boolean synchronizesVariablesWithBindings() {
		return false;
	}

	private int maxLength() {
		final Object v = valueForBinding( "maxLength" );

		if( v instanceof Number ) {
			return ((Number)v).intValue();
		}

		return -1;
	}

	private DateTimeFormatter dateTimeFormatter() {
		return (DateTimeFormatter)valueForBinding( "dateTimeFormatter" );
	}

	public String valueWhenEmpty() {
		return (String)valueForBinding( "valueWhenEmpty" );
	}

	public String value() {
		Object value = valueForBinding( "value" );

		if( value == null ) {
			return "";
		}

		if( value instanceof BigDecimal ) {
			value = NumberFormat.getInstance( Locale.of( "is" ) ).format( value );
		}

		if( value instanceof TemporalAccessor ) {
			if( dateTimeFormatter() != null ) {
				value = dateTimeFormatter().format( (TemporalAccessor)value );
			}
		}

		if( Boolean.class.isAssignableFrom( value.getClass() ) ) {
			if( (boolean)value ) {
				value = "Já";
			}
			else {
				value = "";
			}
		}

		if( !(value instanceof String) ) {
			value = HumanReadableUtils.toStringHuman( value );
		}

		if( maxLength() != -1 ) {
			final String abbreviationPostfix = (String)valueForBinding( "abbreviationPostfix" );
			value = StringUtilities.abbreviate( value.toString(), maxLength(), abbreviationPostfix );
		}

		return (String)value;
	}

	public boolean hasValue() {
		final String v = value();
		return v != null && !v.isEmpty();
	}
}
