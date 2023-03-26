package hafnium.urls;

import java.util.Objects;

import ng.appserver.NGActionResults;
import ng.appserver.NGResponse;

public class StaticURLResponse implements NGActionResults {

	final String _urlString;

	private StaticURLResponse( final String urlString ) {
		Objects.requireNonNull( urlString );
		_urlString = urlString;
	}

	public static StaticURLResponse of( final String urlString ) {
		return new StaticURLResponse( urlString );
	}

	@Override
	public NGResponse generateResponse() {
		return redirectTemporary( _urlString );
	}

	private NGResponse redirectTemporary( final String targetURL ) {
		final NGResponse response = new NGResponse();

		response.setHeader( targetURL, "location" );
		response.setStatus( 302 );
		response.setHeader( "text/html", "content-type" );
		response.setHeader( "0", "content-length" );

		return response;
	}

}