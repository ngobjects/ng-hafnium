package hafnium.components;

import ng.appserver.NGContext;
import ng.appserver.templating.NGComponent;

public class RouteLink extends NGComponent {

	public RouteLink( NGContext context ) {
		super( context );
	}

	@Override
	public boolean synchronizesVariablesWithBindings() {
		return false;
	}

	public String url() {
		return (String)valueForBinding( "url" );
	}

	public String href() {
		return url();
	}
}
