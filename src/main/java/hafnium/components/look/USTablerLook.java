package hafnium.components.look;

import hafnium.menu.USInvokesActionInContext;
import hafnium.menu.USMenu;
import hafnium.menu.USMenuItem;
import hafnium.menu.USMenuItemEntity;
import hafnium.menu.USMenuItemPage;
import hafnium.urls.Inspection;
import jambalaya.definitions.EntityDefinition;
import ng.appserver.NGActionResults;
import ng.appserver.NGApplication;
import ng.appserver.NGContext;
import ng.appserver.templating.NGComponent;

public class USTablerLook extends NGComponent {

	public USMenuItem menuItem;
	public USMenuItem subItem;
	public USMenuItem subSubItem;

	public USTablerLook( NGContext context ) {
		super( context );
	}

	public USMenu menu() {
		return USMenu.defaultMenu();
	}

	public String siteName() {
		return application().properties().get( "hafnium.siteName" );
	}

	/**
	 * FIXME: This should be configurable. Should be null for a non-fluid layout
	 */
	public String bodyClass() {
		return "layout-fluid";
	}

	// --------------- Menu action bridge methods --------------- //

	/**
	 * Bridge method for root-level menu item clicks.
	 * Works around USMenuItem.action() not having access to a context.
	 */
	public NGActionResults menuItemClick() {
		return openMenuItem( menuItem );
	}

	/**
	 * Bridge method for sub-level menu item clicks.
	 */
	public NGActionResults subItemClick() {
		return openMenuItem( subItem );
	}

	/**
	 * Bridge method for sub-sub-level menu item clicks.
	 */
	public NGActionResults subSubItemClick() {
		return openMenuItem( subSubItem );
	}

	/**
	 * Opens the appropriate page for the given menu item.
	 */
	private NGActionResults openMenuItem( USMenuItem item ) {
		if( item instanceof USMenuItemEntity entityItem ) {
			return Inspection.openListPage( EntityDefinition.get( entityItem.entityName() ).entityClass(), context() );
		}

		if( item instanceof USMenuItemPage pageItem && pageItem.pageClass() != null ) {
			return NGApplication.application().pageWithName( pageItem.pageClass(), context() );
		}

		if( item instanceof USInvokesActionInContext contextItem ) {
			return contextItem.invokeActionInContext( context() );
		}

		return null;
	}

	// --------------- CSS helpers for menu rendering --------------- //

	public String liClass() {
		if( menuItem.hasChildren() ) {
			return "nav-item dropdown";
		}

		return "nav-item";
	}

	public String rootLinkClass() {
		if( menuItem.hasChildren() ) {
			return "nav-link dropdown-toggle";
		}

		return "nav-link";
	}

	public String dataBsToggle() {
		if( menuItem.hasChildren() ) {
			return "dropdown";
		}

		return null;
	}

	public String dataBsAutoClose() {
		if( menuItem.hasChildren() ) {
			return "outside";
		}

		return null;
	}
}
