package hafnium;

import hafnium.components.EditLink;
import hafnium.components.RouteLink;
import hafnium.components.USBaseComponent;
import hafnium.components.USBaseViewTools;
import hafnium.components.USEditPageGeneric;
import hafnium.components.USEditWrapper;
import hafnium.components.USLabel;
import hafnium.components.USListPageEdit;
import hafnium.components.USLoginPage;
import hafnium.components.USOperationMenu;
import hafnium.components.USSort;
import hafnium.components.USStartPage;
import hafnium.components.USViewLook;
import hafnium.components.USViewPage;
import hafnium.components.USViewPageGeneric;
import hafnium.components.USViewWrapper;
import hafnium.components.ViewLink;
import hafnium.components.fields.USLocalDateField;
import hafnium.components.fields.USLocalDateTimeField;
import hafnium.components.fields.USLocalTimeField;
import hafnium.components.look.USEditLook;
import hafnium.components.look.USTablerLook;
import hafnium.components.relationships.USRelationship;
import hafnium.components.relationships.USRelationshipTargetSelection;
import hafnium.components.relationships.USToManyRelationship;
import hafnium.components.relationships.USToOneRelationship;
import hafnium.components.value.IMString;
import hafnium.components.value.IMValue;
import hafnium.components.value.USHumanReadableArray;
import hafnium.urls.ObjectRouteHandler;
import ng.plugins.Elements;
import ng.plugins.NGPlugin;
import ng.plugins.Routes;

public class Hafnium implements NGPlugin {

	@Override
	public String namespace() {
		return "hafnium";
	}

	@Override
	public Elements elements() {
		return Elements
				.create()
				.elementClass( EditLink.class )
				.elementClass( IMString.class )
				.elementClass( IMValue.class )
				.elementClass( RouteLink.class )
				.elementClass( USBaseComponent.class )
				.elementClass( USBaseViewTools.class )
				.elementClass( USEditLook.class )
				.elementClass( USEditPageGeneric.class )
				.elementClass( USEditWrapper.class )
				.elementClass( USHumanReadableArray.class )
				.elementClass( USLabel.class )
				.elementClass( USListPageEdit.class )
				.elementClass( USLocalDateField.class )
				.elementClass( USLocalDateTimeField.class )
				.elementClass( USLocalTimeField.class )
				.elementClass( USLoginPage.class )
				.elementClass( USOperationMenu.class )
				.elementClass( USRelationship.class )
				.elementClass( USRelationshipTargetSelection.class )
				.elementClass( USSort.class )
				.elementClass( USStartPage.class )
				.elementClass( USTablerLook.class )
				.elementClass( USToManyRelationship.class )
				.elementClass( USToOneRelationship.class )
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
				.map( "/helium", USLoginPage.class )
				.map( "/i/*", new ObjectRouteHandler() );
	}
}