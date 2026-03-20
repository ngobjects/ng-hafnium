package hafnium;

import java.time.Duration;

import ng.appserver.NGCookie;
import ng.appserver.NGRequest;
import ng.appserver.NGResponse;

public class USUserCookies {

	// FIXME: Old logic used to use the application name. We currently don't have an app name // Hugi 2026-03-20
	private static final String COOKIE_NAME = "hafnium-" + "temp" + "-userid";

	public static String cookieValue( NGRequest request ) {
		return request.cookieValueForKey( COOKIE_NAME );
	}

	public static void addCookie( NGRequest request, NGResponse response, String value ) {
		NGCookie cookie = new NGCookie( COOKIE_NAME, value );
		cookie.setPath( "/" );
		cookie.setMaxAge( (int)Duration.ofDays( 30 ).toSeconds() );
		response.addCookie( cookie );
	}

	public static void deleteCookie( NGRequest request, NGResponse response ) {
		NGCookie cookie = new NGCookie( COOKIE_NAME, "bye-bye" );
		cookie.setPath( "/" );
		cookie.setMaxAge( 0 );
		response.addCookie( cookie );
	}
}