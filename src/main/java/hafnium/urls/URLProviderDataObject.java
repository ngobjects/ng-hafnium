package hafnium.urls;

import java.util.UUID;

import org.apache.cayenne.DataObject;
import org.apache.cayenne.ObjectId;

import hafnium.urls.Inspection.InspectionRoute;
import jambalaya.ObjectIdSerializer;
import jambalaya.interfaces.UUIDStamped;
import jambalaya.interfaces.UniqueIDStamped;

public class URLProviderDataObject implements URLProvider<DataObject> {

	public static final String PK_IDENTIFIER_PREFIX = "id-";

	public static final String UNIQUE_ID_IDENTIFIER_PREFIX = "uid-";

	@Override
	public String urlForObject( final DataObject dataObject ) {

		if( dataObject instanceof UniqueIDStamped ) {
			final String uniqueID = ((UniqueIDStamped)dataObject).uniqueID();

			if( uniqueID != null ) {
				return urlForUniqueID( dataObject.getObjectId().getEntityName(), uniqueID );
			}
		}

		if( dataObject instanceof UUIDStamped ) {
			final UUID uniqueID = ((UUIDStamped)dataObject).uniqueID();

			if( uniqueID != null ) {
				return urlForUniqueID( dataObject.getObjectId().getEntityName(), uniqueID.toString() );
			}
		}

		return urlForObjectId( dataObject.getObjectId() );
	}

	public static String urlForObjectId( final ObjectId objectId ) {
		final InspectionRoute inspectionRoute = InspectionRoute.forEntityName( objectId.getEntityName() );

		if( inspectionRoute == null ) {
			return "no-route";
		}

		final String typeIdentifierString = inspectionRoute.urlPrefix();
		final String objectIdentifierString = PK_IDENTIFIER_PREFIX + ObjectIdSerializer.serialize( objectId );
		return fullURL( typeIdentifierString, objectIdentifierString );
	}

	public static String urlForUniqueID( final String entityName, final String uniqueID ) {
		final InspectionRoute inspectionRoute = InspectionRoute.forEntityName( entityName );

		if( inspectionRoute == null ) {
			return "no-route";
		}

		final String typeIdentifierString = inspectionRoute.urlPrefix();
		final String objectIdentifierString = UNIQUE_ID_IDENTIFIER_PREFIX + uniqueID;
		return fullURL( typeIdentifierString, objectIdentifierString );
	}

	private static String fullURL( final String typeIdentifierString, final String objectIdentifierString ) {
		final StringBuilder b = new StringBuilder();
		b.append( "/i/" );
		b.append( typeIdentifierString );
		b.append( "/" );
		b.append( objectIdentifierString );
		return b.toString();
	}
}