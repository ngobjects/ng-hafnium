package hafnium.components;

import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.PersistentObject;

import hafnium.urls.HasSelectedObjectPage;
import jambalaya.Jambalaya;
import jambalaya.definitions.EntityDefinition;
import ng.appserver.NGActionResults;
import ng.appserver.NGComponent;
import ng.appserver.NGContext;

/**
 * Common functionality for client and admin side components.
 */

public abstract class USViewPage<E> extends USBaseComponent implements HasSelectedObjectPage<E> {

	/**
	 * The component to return to in case of action cancellation.
	 */
	private NGComponent _componentToReturnTo;

	/**
	 * Currently selected object.
	 */
	private E _selectedObject;

	private ObjectContext _oc;

	public USViewPage( NGContext context ) {
		super( context );
	}

	public EntityDefinition viewDefinition() {
		return EntityDefinition.get( selectedObject().getClass() );
	}

	public NGComponent callingComponent() {
		return _componentToReturnTo;
	}

	public void setCallingComponent( NGComponent value ) {
		_componentToReturnTo = value;
	}

	public NGActionResults saveChangesAndReturn() {
		saveChanges();
		return returnToCallingComponent();
	}

	public NGActionResults saveChanges() {
		((PersistentObject)selectedObject()).getObjectContext().commitChanges();
		return null;
	}

	public NGActionResults deleteObject() {
		((PersistentObject)selectedObject()).getObjectContext().deleteObject( selectedObject() );
		saveChanges();
		return returnToCallingComponent();
	}

	/**
	 * @return The component instance that invoked this component.
	 */
	public NGActionResults returnToCallingComponent() {
		//		callingComponent().ensureAwakeInContext( context() );
		return callingComponent();
	}

	protected ObjectContext oc() {
		if( _oc == null ) {
			if( selectedObject() != null ) {
				_oc = ((PersistentObject)selectedObject()).getObjectContext();
			}
			else {
				_oc = Jambalaya.newContext();
			}
		}

		return _oc;
	}

	@Override
	public E selectedObject() {
		E newSelectedObject = (E)valueForBinding( "selectedObject" );

		if( newSelectedObject != null && !newSelectedObject.equals( _selectedObject ) ) {
			_selectedObject = newSelectedObject;
		}

		return _selectedObject;
	}

	public void setSelectedObject( E value ) {
		_selectedObject = value;
	}
}