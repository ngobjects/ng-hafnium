package hafnium.menu;

import ng.appserver.NGActionResults;
import ng.appserver.NGContext;

public interface USInvokesActionInContext {

	public NGActionResults invokeActionInContext( NGContext context );
}