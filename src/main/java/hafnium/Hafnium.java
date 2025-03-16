package hafnium;

import hafnium.components.USBaseComponent;
import hafnium.components.USBaseViewTools;
import hafnium.components.USEditPageGeneric;
import hafnium.components.USEditWrapper;
import hafnium.components.USListPageEdit;
import hafnium.components.USViewPage;
import hafnium.components.USViewPageGeneric;
import hafnium.components.USViewWrapper;
import hafnium.components.ViewLink;
import hafnium.urls.ObjectRouteHandler;
import ng.appserver.NGApplication;
import ng.plugins.NGPlugin;

public class Hafnium extends NGPlugin {

	@Override
	public void load( NGApplication application ) {
		registerComponents( application );
		application.routeTable().map( "/i/*", new ObjectRouteHandler() );
	}

	public static void registerComponents( NGApplication application ) {
		application.elementManager().registerElementClass( USBaseComponent.class );
		application.elementManager().registerElementClass( USBaseViewTools.class );
		application.elementManager().registerElementClass( USEditPageGeneric.class );
		application.elementManager().registerElementClass( USEditWrapper.class );
		application.elementManager().registerElementClass( USListPageEdit.class );
		application.elementManager().registerElementClass( USViewPage.class );
		application.elementManager().registerElementClass( USViewPageGeneric.class );
		application.elementManager().registerElementClass( USViewWrapper.class );
		application.elementManager().registerElementClass( ViewLink.class );
	}
}