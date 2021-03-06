package kb50.companyxcontent;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

public class CompanyProvider extends ContentProvider {

	static final String PROVIDER_NAME = "kb50.companyxcontent.companyxcontentprovider";

	public static final Uri CONTENT_COMPANIES = Uri.parse("content://"
			+ PROVIDER_NAME + "/company");

	public static final Uri CONTENT_OFFICES = Uri.parse("content://"
			+ PROVIDER_NAME + "/office");

	public static final Uri CONTENT_LOCATIONS = Uri.parse("content://"
			+ PROVIDER_NAME + "/location");

	public static final Uri CONTENT_IMAGES = Uri.parse("content://"
			+ PROVIDER_NAME + "/image");

	private static final int COMPANY = 1;
	private static final int COMPANY_ID = 2;

	private static final int OFFICE = 3;
	private static final int OFFICE_ID = 4;

	private static final int LOCATION = 5;
	private static final int LOCATION_ID = 6;

	private static final int IMAGE = 7;
	private static final int IMAGE_ID = 8;

	private DatabaseHelper DBHelper;
	private SQLiteDatabase companyDB;

	private static final UriMatcher uriMatcher;
	static {
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI(PROVIDER_NAME, "company", COMPANY);
		uriMatcher.addURI(PROVIDER_NAME, "companies/#", COMPANY_ID);

		uriMatcher.addURI(PROVIDER_NAME, "office", OFFICE);
		uriMatcher.addURI(PROVIDER_NAME, "offices/#", OFFICE_ID);

		uriMatcher.addURI(PROVIDER_NAME, "location", LOCATION);
		uriMatcher.addURI(PROVIDER_NAME, "locations/#", LOCATION_ID);

		uriMatcher.addURI(PROVIDER_NAME, "image", IMAGE);
		uriMatcher.addURI(PROVIDER_NAME, "images/#", IMAGE_ID);

	}

	@Override
	public boolean onCreate() {

		DBHelper = new DatabaseHelper(getContext());
		
		companyDB = DBHelper.getWritableDatabase();
		
		return (companyDB == null) ? false : true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {

		SQLiteQueryBuilder sqlBuilder = new SQLiteQueryBuilder();

		switch (uriMatcher.match(uri)) {

		case COMPANY:
			sqlBuilder.setTables("company");

			break;

		case COMPANY_ID:
			sqlBuilder.setTables("company");
			sqlBuilder.appendWhere("id" + " = " + uri.getPathSegments().get(1));
			break;

		case OFFICE:
			sqlBuilder.setTables("office");

			break;

		case OFFICE_ID:
			sqlBuilder.setTables("office");
			sqlBuilder.appendWhere("id" + " = " + uri.getPathSegments().get(1));
			break;

		case LOCATION:
			sqlBuilder.setTables("location");

			break;

		case LOCATION_ID:
			sqlBuilder.setTables("location");
			sqlBuilder.appendWhere("id" + " = " + uri.getPathSegments().get(1));
			break;

		case IMAGE:
			sqlBuilder.setTables("product_image");

			break;

		case IMAGE_ID:
			sqlBuilder.setTables("product_image");
			sqlBuilder.appendWhere("id" + " = " + uri.getPathSegments().get(1));
			break;
		}

		Cursor c = sqlBuilder.query(companyDB, projection, selection,
				selectionArgs, null, null, sortOrder);

		// ---register to watch a content URI for changes-- -
		c.setNotificationUri(getContext().getContentResolver(), uri);
		return c;

	}

	@Override
	public String getType(Uri uri) {
		switch (uriMatcher.match(uri)) {

		case COMPANY_ID:
			return "vnd.android.cursor.item/vnd.appcontentprovider.CompanyProvider ";

		case COMPANY:
			return "vnd.android.cursor.dir/vnd.appcontentprovider.CompanyProvider ";

		case OFFICE:
			return "vnd.android.cursor.dir/vnd.appcontentprovider.OfficeProvider ";

		case OFFICE_ID:
			return "vnd.android.cursor.item/vnd.appcontentprovider.OfficeProvider ";

		case LOCATION:
			return "vnd.android.cursor.dir/vnd.appcontentprovider.LocationProvider ";

		case LOCATION_ID:
			return "vnd.android.cursor.item/vnd.appcontentprovider.LocationProvider ";

		case IMAGE:
			return "vnd.android.cursor.dir/vnd.appcontentprovider.ImageProvider ";

		case IMAGE_ID:
			return "vnd.android.cursor.item/vnd.appcontentprovider.ImageProvider ";

		default:
			throw new IllegalArgumentException("Unsupported URI: " + uri);
		}

	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {

		switch (uriMatcher.match(uri)) {

		case COMPANY:
			long rowID = companyDB.insert("company", "", values);

			if (rowID > 0) {
				Uri _uri = ContentUris.withAppendedId(CONTENT_COMPANIES, rowID);
				getContext().getContentResolver().notifyChange(_uri, null);
				return _uri;
			}

			break;

		case OFFICE:

			long rowIDOffice = companyDB.insert("office", "", values);

			if (rowIDOffice > 0) {
				Uri _uri = ContentUris.withAppendedId(CONTENT_OFFICES,
						rowIDOffice);
				getContext().getContentResolver().notifyChange(_uri, null);
				return _uri;
			}

			break;

		case LOCATION:

			long rowIDLocation = companyDB.insert("location", "", values);

			if (rowIDLocation > 0) {
				Uri _uri = ContentUris.withAppendedId(CONTENT_LOCATIONS,
						rowIDLocation);
				getContext().getContentResolver().notifyChange(_uri, null);
				return _uri;
			}

			break;

		case IMAGE:

			long rowIDImages = companyDB.insert("product_image", "", values);

			if (rowIDImages > 0) {
				Uri _uri = ContentUris.withAppendedId(CONTENT_IMAGES,
						rowIDImages);
				getContext().getContentResolver().notifyChange(_uri, null);
				return _uri;
			}

			break;

		}
		throw new SQLException("Failed to insert row into " + uri);

	}

	public int delete(Uri uri, String selection, String[] selectionArgs) {
		int count = 0;
		String id = uri.getPathSegments().get(1);
		switch (uriMatcher.match(uri)) {
		case COMPANY:
			count = companyDB.delete("company", selection, selectionArgs);
			break;
		case COMPANY_ID:

			count = companyDB.delete("company",
					"id = "
							+ id
							+ (!TextUtils.isEmpty(selection) ? " AND ("
									+ selectionArgs + ')' : ""), selectionArgs);
			break;

		case OFFICE:
			count = companyDB.delete("office", selection, selectionArgs);
			break;
		case OFFICE_ID:
			count = companyDB.delete("office",
					"id = "
							+ id
							+ (!TextUtils.isEmpty(selection) ? " AND ("
									+ selectionArgs + ')' : ""), selectionArgs);
			break;

		case LOCATION:
			count = companyDB.delete("location", selection, selectionArgs);
			break;
		case LOCATION_ID:
			count = companyDB.delete("location",
					"id = "
							+ id
							+ (!TextUtils.isEmpty(selection) ? " AND ("
									+ selectionArgs + ')' : ""), selectionArgs);
			break;

		case IMAGE:
			count = companyDB.delete("product_image", selection, selectionArgs);
			break;
		case IMAGE_ID:

			count = companyDB.delete("product_image", "id = "
					+ id
					+ (!TextUtils.isEmpty(selection) ? " AND (" + selectionArgs
							+ ')' : ""), selectionArgs);
			break;
		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		int count = 0;
		switch (uriMatcher.match(uri)) {
		case COMPANY:
			count = companyDB.update("company", values, selection,
					selectionArgs);
			break;
		case COMPANY_ID:
			count = companyDB.update(
					"company",
					values,
					"id="
							+ uri.getPathSegments().get(1)
							+ (!TextUtils.isEmpty(selection) ? " AND ("
									+ selection + ')' : ""), selectionArgs);
			break;

		case LOCATION:
			count = companyDB.update("location", values, selection,
					selectionArgs);
			break;
		case LOCATION_ID:
			count = companyDB.update(
					"location",
					values,
					"id="
							+ uri.getPathSegments().get(1)
							+ (!TextUtils.isEmpty(selection) ? " AND ("
									+ selection + ')' : ""), selectionArgs);
			break;

		case OFFICE:
			count = companyDB
					.update("office", values, selection, selectionArgs);
			break;
		case OFFICE_ID:
			count = companyDB.update(
					"office",
					values,
					"id="
							+ uri.getPathSegments().get(1)
							+ (!TextUtils.isEmpty(selection) ? " AND ("
									+ selection + ')' : ""), selectionArgs);
			break;

		case IMAGE:
			count = companyDB.update("product_image", values, selection,
					selectionArgs);
			break;
		case IMAGE_ID:
			count = companyDB.update(
					"product_image",
					values,
					"id="
							+ uri.getPathSegments().get(1)
							+ (!TextUtils.isEmpty(selection) ? " AND ("
									+ selection + ')' : ""), selectionArgs);
			break;

		default:
			throw new IllegalArgumentException("Unknown Uri" + uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return count;

	}

}
