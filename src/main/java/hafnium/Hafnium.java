package hafnium;

import hafnium.components.USBaseComponent;
import hafnium.components.USBaseViewTools;
import hafnium.components.USEditPageGeneric;
import hafnium.components.USEditWrapper;
import hafnium.components.USListPageEdit;
import hafnium.components.USViewLook;
import hafnium.components.USViewPage;
import hafnium.components.USViewPageGeneric;
import hafnium.components.USViewWrapper;
import hafnium.components.ViewLink;
import hafnium.urls.ObjectRouteHandler;
import ng.plugins.Elements;
import ng.plugins.NGPlugin;
import ng.plugins.Routes;

public class Hafnium implements NGPlugin {

	@Override
	public Elements elements() {
		return Elements
				.create()
				.elementClass( USBaseComponent.class )
				.elementClass( USBaseViewTools.class )
				.elementClass( USEditPageGeneric.class )
				.elementClass( USEditWrapper.class )
				.elementClass( USListPageEdit.class )
				.elementClass( USViewPage.class )
				.elementClass( USViewPageGeneric.class )
				.elementClass( USViewLook.class )
				.elementClass( USViewWrapper.class )
				.elementClass( ViewLink.class );
	}

	@Override
	public Routes routes() {
		return Routes
				.create()
				.map( "/i/*", new ObjectRouteHandler() );
	}
}