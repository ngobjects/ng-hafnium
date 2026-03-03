package hafnium.components.fields;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import hafnium.components.USBaseComponent;
import is.rebbi.core.util.StringUtilities;
import ng.appserver.NGContext;

/**
 * A field for entering a date without a time.
 */

public class USLocalDateField extends USBaseComponent {

	public USLocalDateField( NGContext context ) {
		super( context );
	}

	@Override
	public boolean synchronizesVariablesWithBindings() {
		return false;
	}

	public String stringValue() {

		if( hasBinding( "stringValue" ) ) {
			return (String)valueForBinding( "stringValue" );
		}

		final LocalDate value = (LocalDate)valueForBinding( "value" );

		if( value == null ) {
			return null;
		}

		return StringDateParser.format( value );
	}

	public void setStringValue( final String value ) {

		if( hasBinding( "stringValue" ) ) {
			setValueForBinding( value, "stringValue" );
		}
		else {
			try {
				final LocalDate localDate = StringDateParser.parse( value );
				setValueForBinding( localDate, "value" );
			}
			catch( DateTimeParseException e ) {
				throw new RuntimeException( "Snið dagsetningar '%s' er ekki rétt".formatted( value ) );
			}
		}
	}

	private static class StringDateParser {

		private static final DateTimeFormatter DATE_TIME_FORMATTER_WITHOUT_TIME_WITHOUT_PERIODS = DateTimeFormatter.ofPattern( "ddMMyyyy" );
		private static final DateTimeFormatter DATE_TIME_FORMATTER_WITHOUT_TIME = DateTimeFormatter.ofPattern( "d.M.yyyy" );

		/**
		 * Parses the given date in several forms. Short form dates are assigned the current year.
		 */
		public static LocalDate parse( String dateString ) {

			if( dateString == null ) {
				return null;
			}

			LocalDate parsed = null;

			if( dateString.contains( "/" ) ) {
				dateString = dateString.replace( "/", "." );
			}

			if( dateString.contains( "." ) ) {

				final String[] splitString = dateString.split( "\\." );

				// Date is on the form 03.12 or 3.12
				if( splitString.length == 2 ) {
					dateString = dateString + "." + LocalDate.now().getYear();
				}

				// Date is on the form 03.12.20
				if( splitString.length == 3 && splitString[2].length() == 2 ) {
					dateString = splitString[0] + "." + splitString[1] + "." + (Integer.parseInt( splitString[2] ) + 2000);
				}

				// Finally parse date using form 03.12.2020
				parsed = LocalDate.parse( dateString, DATE_TIME_FORMATTER_WITHOUT_TIME );
			}
			else if( StringUtilities.isDigitsOnly( dateString ) ) {
				// Date is on the form 0312
				if( dateString.length() == 4 ) {
					dateString = dateString + LocalDate.now().getYear();
				}

				// Date is on the form 031220
				if( dateString.length() == 6 ) {
					dateString = dateString.substring( 0, 4 ) + (Integer.parseInt( dateString.substring( 4, 6 ) ) + 2000);
				}
				// Date is on the form 03122020
				if( dateString.length() == 8 ) {
					dateString = dateString.substring( 0, 4 ) + (Integer.parseInt( dateString.substring( 4, 8 ) ));
				}

				// Finally parse date using form 03122020
				parsed = LocalDate.parse( dateString, DATE_TIME_FORMATTER_WITHOUT_TIME_WITHOUT_PERIODS );
			}

			return parsed;
		}

		public static String format( LocalDate date ) {

			if( date == null ) {
				return "";
			}

			return DATE_TIME_FORMATTER_WITHOUT_TIME.format( date );
		}
	}
}
