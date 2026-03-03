package hafnium.components;

import org.apache.cayenne.PersistentObject;

import jambalaya.definitions.AttributeDefinition;
import jambalaya.definitions.EntityDefinition;
import ng.appserver.NGContext;
import ng.appserver.templating.NGComponent;

/**
 * Shows a label accompanying fields of information.
 */

public class USLabel extends NGComponent {

	private AttributeDefinition _viewDefinition;

	public USLabel( NGContext context ) {
		super( context );
	}

	@Override
	public boolean synchronizesVariablesWithBindings() {
		return false;
	}

	private PersistentObject object() {
		return (PersistentObject)valueForBinding( "object" );
	}

	private String key() {
		return (String)valueForBinding( "key" );
	}

	private EntityDefinition viewDefinition() {
		return EntityDefinition.get( object().getClass() );
	}

	private AttributeDefinition meta() {
		if( _viewDefinition == null ) {
			_viewDefinition = viewDefinition().attributeNamed( key() );
		}

		return _viewDefinition;
	}

	public String displayName() {
		if( meta() != null ) {
			final String icelandicName = meta().icelandicName();

			if( icelandicName != null ) {
				return icelandicName;
			}
		}

		return key();
	}

	public String text() {
		if( meta() != null ) {
			return meta().text();
		}

		return null;
	}
}
