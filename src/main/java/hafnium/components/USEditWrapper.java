package hafnium.components;

import hafnium.urls.Inspection;
import ng.appserver.NGActionResults;
import ng.appserver.NGContext;

public class USEditWrapper extends USViewWrapper {

	public USEditWrapper( NGContext context ) {
		super( context );
	}

	public NGActionResults openListPage() {
		return Inspection.openListPage( viewDefinition().entityClass(), context() );
	}
}