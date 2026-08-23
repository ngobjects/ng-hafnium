package hafnium;

import java.time.Duration;

import ng.appserver.http.NGCookie;
import ng.appserver.http.NGRequest;
import ng.appserver.http.NGResponse;

public class USUserCookies {

	// FIXME: Old logic used to use the application name. We currently don't have an app name // Hugi 2026-03-20
	private static final String COOKIE_NAME = "hafnium-" + "temp" + "-userid";

	public static String cookieValue( NGRequest request ) {
		return request.cookieValueForKey( COOKIE_NAME );
	}

	public static void addCookie( NGRequest request, NGResponse response, String value ) {
		NGCookie cookie = new NGCookie( COOKIE_NAME, value, Duration.ofDays( 30 ) );
		response.addCookie( cookie );
	}

	public static void deleteCookie( NGRequest request, NGResponse response ) {
		NGCookie cookie = new NGCookie( COOKIE_NAME, "bye-bye", Duration.ZERO );
		response.addCookie( cookie );
	}
}