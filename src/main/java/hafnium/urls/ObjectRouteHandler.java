package hafnium.urls;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.PersistentObject;
import org.apache.cayenne.exp.Expression;
import org.apache.cayenne.exp.ExpressionFactory;
import org.apache.cayenne.map.DbAttribute;
import org.apache.cayenne.map.ObjEntity;
import org.apache.cayenne.query.ObjectSelect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hafnium.urls.Inspection.InspectionRoute;
import jambalaya.Jambalaya;
import ng.appserver.NGRequest;
import ng.appserver.NGRequestHandler;
import ng.appserver.NGResponse;
import ng.appserver.privates.NGParsedURI;

public class ObjectRouteHandler extends NGRequestHandler {

	private static final Logger logger = LoggerFactory.getLogger( ObjectRouteHandler.class );

	@Override
	public NGResponse handleRequest( final NGRequest request ) {
		final Object object = selectedObject( request.parsedURI() );

		// FIXME: 404 handling could really use some improvement here.
		if( object == null ) {
			logger.warn( "Nothing found at {}", request.uri() );
			return new NGResponse( "Nothing found at: " + request.uri(), 404 );
		}

		return Inspection.inspectObjectInContext( object, request.context() ).generateResponse();
	}

	/**
	 * @return The object the user wanted from the URL.
	 */
	private static PersistentObject selectedObject( final NGParsedURI path ) {
		final String typeIdentifier = path.getString( 1 );
		final String objectIdentifier = path.getString( 2 );

		final String objEntityName = entityNameFromTypeIdentifier( typeIdentifier );

		if( objEntityName == null ) {
			return null;
		}

		if( objectIdentifier.startsWith( URLProviderDataObject.PK_IDENTIFIER_PREFIX ) ) {
			final String identifier = objectIdentifier.substring( URLProviderDataObject.PK_IDENTIFIER_PREFIX.length(), objectIdentifier.length() );
			return objectFromPKString( Jambalaya.newContext(), objEntityName, identifier );
		}

		if( objectIdentifier.startsWith( URLProviderDataObject.UNIQUE_ID_IDENTIFIER_PREFIX ) ) {
			final String identifier = objectIdentifier.substring( URLProviderDataObject.UNIQUE_ID_IDENTIFIER_PREFIX.length(), objectIdentifier.length() );
			return objectFromUniqueID( Jambalaya.newContext(), objEntityName, identifier );
		}

		return null;
		//		throw new RuntimeException( "Unsupported URL format" );
	}

	private static PersistentObject objectFromUniqueID( final ObjectContext oc, final String objEntityName, final String uniqueID ) {
		return ObjectSelect
				.query( PersistentObject.class, objEntityName )
				.where( ExpressionFactory.matchExp( "uniqueID", uniqueID ) )
				.selectOne( oc );
	}

	private static PersistentObject objectFromPKString( final ObjectContext oc, final String objEntityName, final String identifier ) {
		final ObjEntity objEntity = oc.getEntityResolver().getObjEntity( objEntityName );
		final Collection<DbAttribute> primaryKeyAttributes = objEntity.getDbEntity().getPrimaryKeys();
		final String[] components = identifier.split( "\\|" );

		final Map<String, Object> keyMap = new HashMap<>();

		int i = 0;

		for( final DbAttribute attribute : primaryKeyAttributes ) {
			keyMap.put( attribute.getName(), components[i++] );
		}

		final Expression exp = ExpressionFactory.matchAllDbExp( keyMap, Expression.EQUAL_TO );

		return ObjectSelect
				.query( PersistentObject.class, objEntityName )
				.where( exp )
				.selectOne( oc );
	}

	/**
	 * FIXME: Evaluate this method
	 */
	private static String entityNameFromTypeIdentifier( final String typeIdentifier ) {
		InspectionRoute inspectionRoute = InspectionRoute.forURLPrefix( typeIdentifier );

		if( inspectionRoute == null ) {
			return null;
			//			throw new RuntimeException( "No view definition found for URL prefix: " + typeIdentifier );
		}

		return inspectionRoute.entityClass().getSimpleName();
	}
}