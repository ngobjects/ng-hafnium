package hafnium;

import ng.appserver.NGRequest;
import ng.appserver.NGResponse;

public class USHTTPUtilities {

	private static final String HEADER_CONTENT_TYPE = "content-type";
	private static final String HEADER_CONTENT_LENGTH = "content-length";
	private static final String HEADER_REDIRECT_LOCATION = "location";

	/**
	 * This method creates a WOResponse with a temporary (302) redirect to the specified URL
	 *
	 * @param targetURL The URL to redirect to
	 */
	public static NGResponse redirectTemporary( final String targetURL ) {
		final NGResponse response = new NGResponse();

		response.setStatus( 302 );
		response.setHeader( HEADER_REDIRECT_LOCATION, targetURL );
		response.setHeader( HEADER_CONTENT_TYPE, "text/html" );
		response.setHeader( HEADER_CONTENT_LENGTH, "0" );

		return response;
	}

	// FIXME: // Hugi 2026-03-20
	public static String ipAddressFromRequest( NGRequest request ) {
		return "";
	}

	// FIXME: // Hugi 2026-03-20
	public static String userAgent( NGRequest request ) {
		return "";
	}
}