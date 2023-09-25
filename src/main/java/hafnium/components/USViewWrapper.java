package hafnium.components;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ng.appserver.NGContext;
import ng.appserver.NGResponse;

public class USViewWrapper extends USViewPage {

	private static final Logger logger = LoggerFactory.getLogger( USViewWrapper.class );

	/**
	 * Name of the component currently being displayed.
	 */
	private String _displayComponentName;

	public USViewWrapper( NGContext context ) {
		super( context );
	}

	public String displayComponentName() {
		return _displayComponentName;
	}

	public void setDisplayComponentName( String displayComponentName ) {
		_displayComponentName = displayComponentName;
	}

	public String viewToolsComponentName() {
		final String name = application().properties().get( "concept.viewToolsComponentName" );

		if( name == null ) {
			return USBaseViewTools.class.getSimpleName();
		}

		return name;
	}

	@Override
	public void appendToResponse( NGResponse response, NGContext context ) {
		logger.info( "displayComponentName: " + _displayComponentName );
		super.appendToResponse( response, context );
	}
}