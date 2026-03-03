package hafnium.components;

import ng.appserver.NGActionResults;
import ng.appserver.NGApplication;
import ng.appserver.NGContext;
import ng.appserver.templating.NGComponent;

public class USLoginPage extends NGComponent {

	public String username;
	public String password;

	public USLoginPage( NGContext context ) {
		super( context );
	}

	public String siteName() {
		return application().properties().get( "hafnium.siteName" );
	}

	public NGActionResults login() {
		final String adminUsername = application().properties().get( "hafnium.adminUsername" );
		final String adminPassword = application().properties().get( "hafnium.adminPassword" );

		if( adminUsername != null && adminUsername.equals( username ) && adminPassword != null && adminPassword.equals( password ) ) {
			USStartPage nextPage = NGApplication.application().pageWithName( USStartPage.class, context() );
			return nextPage;
		}

		return null;
	}
}
