package hafnium.operations;

import java.util.function.BiFunction;

import org.apache.cayenne.PersistentObject;

import hafnium.urls.Inspection;
import hafnium.urls.URLProviders;
import ng.appserver.NGActionResults;
import ng.appserver.NGContext;

public class ViewOperation implements DataObjectOperation {

	@Override
	public String name() {
		return "Skoða";
	}

	@Override
	public String iconName() {
		return "eye-open";
	}

	@Override
	public BiFunction<PersistentObject, NGContext, Boolean> show() {
		return ( object, context ) -> {
			return object != null;
		};
	}

	@Override
	public BiFunction<PersistentObject, NGContext, NGActionResults> execute() {
		return ( object, context ) -> {
			return Inspection.inspectObjectInContext( object, context );
		};
	}

	@Override
	public BiFunction<PersistentObject, NGContext, String> urlFunction() {
		return ( object, context ) -> {
			return URLProviders.urlForObject( object );
		};
	}
}
