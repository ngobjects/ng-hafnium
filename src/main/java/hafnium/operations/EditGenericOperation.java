package hafnium.operations;

import java.util.function.BiFunction;

import org.apache.cayenne.PersistentObject;

import hafnium.urls.Inspection;
import ng.appserver.NGActionResults;
import ng.appserver.NGContext;

public class EditGenericOperation implements DataObjectOperation {

	@Override
	public String name() {
		return "Breyta (almenn útgáfa)";
	}

	@Override
	public String iconName() {
		return "pencil";
	}

	@Override
	public BiFunction<PersistentObject, NGContext, NGActionResults> execute() {
		return ( object, context ) -> {
			return Inspection.editObjectInContextUsingGenericComponent( object, context );
		};
	}

	@Override
	public BiFunction<PersistentObject, NGContext, Boolean> show() {
		return ( object, context ) -> {
			return object != null;
		};
	}
}
