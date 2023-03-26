package hafnium.urls;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;

import org.apache.cayenne.DataObject;

import hafnium.components.USEditPageGeneric;
import hafnium.components.USEditWrapper;
import hafnium.components.USListPageEdit;
import hafnium.components.USViewPageGeneric;
import hafnium.components.USViewWrapper;
import ng.appserver.NGActionResults;
import ng.appserver.NGApplication;
import ng.appserver.NGContext;

public class Inspection {

	public static class InspectionRoute {

		private static final Map<Class, InspectionRoute> _inspectionRoutes = new HashMap<>();

		public static final Map<Class, InspectionRoute> inspectionRoutes() {
			return _inspectionRoutes;
		}

		public static void add( Class entityClass, String urlPrefix, BiFunction<DataObject, NGContext, NGActionResults> viewFunction ) {
			final InspectionRoute ir = new InspectionRoute();
			ir.setEntityClass( entityClass );
			ir.setUrlPrefix( urlPrefix );
			ir._viewFunction = viewFunction;
			inspectionRoutes().put( entityClass, ir );
		}

		public static void add( Class entityClass, String urlPrefix, Class viewComponentClass, Class editComponentClass ) {
			final InspectionRoute ir = new InspectionRoute();
			ir.setEntityClass( entityClass );
			ir.setUrlPrefix( urlPrefix );
			ir.setViewComponentClass( viewComponentClass );
			ir.setEditComponentClass( editComponentClass );
			inspectionRoutes().put( entityClass, ir );
		}

		private Class _entityClass;

		/**
		 * Prefix used in URLs to access objects of this type.
		 */
		private String _urlPrefix;

		/**
		 * Class of component used to view objects if this type.
		 */
		private Class _viewComponentClass;

		/**
		 * Class of component used to view objects if this type.
		 */
		private BiFunction<DataObject, NGContext, NGActionResults> _viewFunction;

		/**
		 * Class of component used to edit objects if this type.
		 */
		private Class _editComponentClass;

		public Class entityClass() {
			return _entityClass;
		}

		public void setEntityClass( final Class value ) {
			_entityClass = value;
		}

		public String urlPrefix() {
			return _urlPrefix;
		}

		public void setUrlPrefix( String value ) {
			_urlPrefix = value;
		}

		public Class viewComponentClass() {
			return _viewComponentClass;
		}

		public void setViewComponentClass( Class value ) {
			_viewComponentClass = value;
		}

		public Class editComponentClass() {
			return _editComponentClass;
		}

		public void setEditComponentClass( Class value ) {
			_editComponentClass = value;
		}

		/**
		 * @return The definition for the given URL prefix.
		 */
		public static InspectionRoute forURLPrefix( String urlPrefix ) {
			for( InspectionRoute o : inspectionRoutes().values() ) {
				if( urlPrefix.equals( o.urlPrefix() ) ) {
					return o;
				}
			}

			return null;
		}

		/**
		 * @return The definition for the given URL prefix.
		 */
		public static InspectionRoute forEntityName( String entityName ) {
			for( InspectionRoute o : inspectionRoutes().values() ) {
				if( entityName.equals( o.entityClass().getSimpleName() ) ) {
					return o;
				}
			}

			return null;
			//			throw new IllegalArgumentException( "EntityName not found: " +  entityName );
		}
	}

	/**
	 * @return The given object opened in the default view page.
	 */
	public static NGActionResults inspectObjectInContext( Object object, NGContext context ) {
		final InspectionRoute ir = InspectionRoute.inspectionRoutes().get( object.getClass() );

		if( ir._viewFunction != null ) {
			return ir._viewFunction.apply( (DataObject)object, context );
		}

		final Class<? extends HasSelectedObjectPage> componentClass = ir.viewComponentClass();

		if( componentClass == null ) {
			throw new RuntimeException( "This object type has no associated view component" );
		}

		return inspectObjectInContextUsingComponent( object, context, componentClass );
	}

	/**
	 * @return The given object opened in the default edit page.
	 *
	 * FIXME: No fallbacks should be here, this should be purely configured by the programmer.
	 */
	public static NGActionResults editObjectInContext( Object object, NGContext context ) {
		final InspectionRoute ir = InspectionRoute.inspectionRoutes().get( object.getClass() );
		Class<? extends HasSelectedObjectPage> componentClass = null;

		if( ir != null ) {
			componentClass = ir.editComponentClass();
		}

		// FIXME. I want to stop here, like in the viewing route
		if( componentClass == null ) {
			componentClass = USEditPageGeneric.class;
		}

		return editObjectInContextUsingComponent( object, context, componentClass );
	}

	public static NGActionResults openListPage( Class entityClass, NGContext context ) {
		USListPageEdit nextPage = NGApplication.application().pageWithName( USListPageEdit.class, context );
		nextPage.setEntityClass( entityClass );
		return nextPage;
	}

	public static NGActionResults viewObject( final Object object, final NGContext context ) {
		Objects.requireNonNull( object );
		Objects.requireNonNull( context );

		final String url = URLProviders.urlForObject( object );
		return StaticURLResponse.of( url );
	}

	public static StaticURLResponse inspectObject( Object object, NGContext context ) {
		Objects.requireNonNull( object );
		Objects.requireNonNull( context );

		final String url = URLProviders.urlForObject( object );
		return StaticURLResponse.of( url );
	}

	/**
	 * Takes an object of a supported type and returns an inspection page for it.
	 */
	public static NGActionResults inspectObjectInContextUsingComponent( Object object, NGContext context, Class<? extends HasSelectedObjectPage> componentClass ) {
		return openObjectUsingWrapperAndComponent( object, context, componentClass, USViewWrapper.class );
	}

	public static NGActionResults editObjectInContextUsingComponent( Object object, NGContext context, Class<? extends HasSelectedObjectPage> componentClass ) {
		return openObjectUsingWrapperAndComponent( object, context, componentClass, USEditWrapper.class );
	}

	private static NGActionResults openObjectUsingWrapperAndComponent( Object object, NGContext context, Class<? extends HasSelectedObjectPage> componentClass, Class<? extends USViewWrapper> wrapperClass ) {
		USViewWrapper nextPage = NGApplication.application().pageWithName( wrapperClass, context );
		nextPage.setDisplayComponentName( componentClass.getSimpleName() );
		nextPage.setSelectedObject( object );
		nextPage.setCallingComponent( context.page() );
		return nextPage;
	}

	public static NGActionResults editObjectInContextUsingGenericComponent( Object selectedObject, NGContext context ) {
		Class<? extends HasSelectedObjectPage> pageClass = null;

		if( selectedObject instanceof DataObject ) {
			pageClass = USEditPageGeneric.class;
		}

		return editObjectInContextUsingComponent( selectedObject, context, pageClass );
	}

	public static NGActionResults inspectObjectInContextUsingGenericComponent( Object selectedObject, NGContext context ) {
		Class<? extends HasSelectedObjectPage> pageClass = null;

		if( selectedObject instanceof DataObject ) {
			pageClass = USViewPageGeneric.class;
		}

		return inspectObjectInContextUsingComponent( selectedObject, context, pageClass );
	}
}