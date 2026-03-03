package hafnium.operations;

import java.util.function.BiFunction;

import org.apache.cayenne.PersistentObject;

import hafnium.urls.Inspection;
import ng.appserver.NGActionResults;
import ng.appserver.NGContext;

public class EditOperation implements DataObjectOperation {

	@Override
	public String name() {
		return "Breyta";
	}

	@Override
	public String iconName() {
		return "pencil";
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
			return Inspection.editObjectInContext( object, context );
		};
	}
}
