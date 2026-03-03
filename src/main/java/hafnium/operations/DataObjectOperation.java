package hafnium.operations;

import java.util.function.BiFunction;

import org.apache.cayenne.PersistentObject;

import ng.appserver.NGActionResults;
import ng.appserver.NGContext;

public interface DataObjectOperation {

	/**
	 * Name of the operation (shown in the UI)
	 */
	public String name();

	/**
	 * Name of icon shown in the UI (as specified by font-awesome)
	 */
	public String iconName();

	/**
	 * A function that decides if the operation should be shown to the user.
	 */
	public BiFunction<PersistentObject, NGContext, Boolean> show();

	/**
	 * Defines a function that will be run when the button is clicked, passing the selectedObject if any.
	 */
	public default BiFunction<PersistentObject, NGContext, NGActionResults> execute() {
		return null;
	}

	/**
	 * A function that generates the URL for the current operation.
	 */
	public default BiFunction<PersistentObject, NGContext, String> urlFunction() {
		return null;
	}
}
