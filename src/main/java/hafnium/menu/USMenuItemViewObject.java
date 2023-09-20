package hafnium.menu;

import org.apache.cayenne.DataObject;

import hafnium.urls.HasSelectedObjectPage;
import hafnium.urls.Inspection;
import hafnium.urls.URLProviders;
import ng.appserver.NGActionResults;
import ng.appserver.NGContext;

public class USMenuItemViewObject extends USMenuItem implements USInvokesActionInContext {

	private DataObject _object;
	private Class<? extends HasSelectedObjectPage> _viewComponentClass;

	public DataObject object() {
		return _object;
	}

	public void setObject( DataObject newObject ) {
		_object = newObject;
	}

	public static USMenuItemViewObject create( String name, String iconClasses, DataObject object ) {
		return create( name, iconClasses, object, null );
	}

	public static USMenuItemViewObject create( String name, String iconClasses, DataObject object, Class<? extends HasSelectedObjectPage> viewComponentClass ) {
		USMenuItemViewObject item = new USMenuItemViewObject();
		item.setName( name );
		item.setIconClasses( iconClasses );
		item.setObject( object );
		item._viewComponentClass = viewComponentClass;
		return item;
	}

	/**
	 * FIXME: This should be removed once we are using theis to invoke operations and operations have routes.
	 */
	@Override
	public NGActionResults action() {
		throw new RuntimeException( "Bla" );
	}

	@Override
	public String url() {
		if( _viewComponentClass != null ) {
			return null; // FIXME: FIX to allow custom view components.
		}

		return URLProviders.urlForObject( object() );
	}

	@Override
	public NGActionResults invokeActionInContext( NGContext context ) {
		if( _viewComponentClass != null ) {
			return Inspection.inspectObjectInContextUsingComponent( object(), context, _viewComponentClass );
		}

		return Inspection.inspectObjectInContext( object(), context );
	}
}