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
import ng.appserver.templating.NGElementUtils;
import ng.plugins.NGPlugin;

public class Hafnium extends NGPlugin {

	@Override
	public void load( NGApplication application ) {
		registerComponents();
		application.routeTable().map( "/i/*", new ObjectRouteHandler() );
	}

	public static void registerComponents() {
		NGElementUtils.addClass( USBaseComponent.class );
		NGElementUtils.addClass( USBaseViewTools.class );
		NGElementUtils.addClass( USEditPageGeneric.class );
		NGElementUtils.addClass( USEditWrapper.class );
		NGElementUtils.addClass( USListPageEdit.class );
		NGElementUtils.addClass( USViewPage.class );
		NGElementUtils.addClass( USViewPageGeneric.class );
		NGElementUtils.addClass( USViewWrapper.class );
		NGElementUtils.addClass( ViewLink.class );
	}
}