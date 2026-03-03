package hafnium.components;

import java.util.List;

import org.apache.cayenne.PersistentObject;

import hafnium.operations.DataObjectOperation;
import hafnium.operations.DataObjectOperations;
import ng.appserver.NGActionResults;
import ng.appserver.NGContext;
import ng.appserver.templating.NGComponent;

public class USOperationMenu extends NGComponent {

	public DataObjectOperation currentOperation;

	public USOperationMenu( NGContext context ) {
		super( context );
	}

	@Override
	public boolean synchronizesVariablesWithBindings() {
		return false;
	}

	public PersistentObject selectedObject() {
		return (PersistentObject)valueForBinding( "selectedObject" );
	}

	public List<DataObjectOperation> operations() {
		return DataObjectOperations.operations();
	}

	public NGActionResults selectOperation() {
		return currentOperation.execute().apply( selectedObject(), context() );
	}

	public String currentOperationURL() {
		return currentOperation.urlFunction().apply( selectedObject(), context() );
	}

	public boolean showCurrentOperation() {
		return currentOperation.show().apply( selectedObject(), context() );
	}

	public String currentOperationClass() {
		return "glyphicon glyphicon-" + currentOperation.iconName();
	}
}
