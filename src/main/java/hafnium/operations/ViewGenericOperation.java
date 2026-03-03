package hafnium.operations;

import java.util.function.BiFunction;

import org.apache.cayenne.PersistentObject;

import hafnium.urls.Inspection;
import ng.appserver.NGActionResults;
import ng.appserver.NGContext;

public class ViewGenericOperation implements DataObjectOperation {

	@Override
	public String name() {
		return "Skoða (almenn útgáfa)";
	}

	@Override
	public String iconName() {
		return "eye-open";
	}

	@Override
	public BiFunction<PersistentObject, NGContext, NGActionResults> execute() {
		return ( object, context ) -> {
			return Inspection.inspectObjectInContextUsingGenericComponent( object, context );
		};
	}

	@Override
	public BiFunction<PersistentObject, NGContext, Boolean> show() {
		return ( object, context ) -> {
			return object != null;
		};
	}
}
