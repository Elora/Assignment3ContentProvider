package kb50.companyxcontent;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

	private static final String DATABASE_TABLE = "CompanyX";
	private static final int DATABASE_VERSION = 1;

	public DatabaseHelper(Context context) {
		super(context, DATABASE_TABLE, null, DATABASE_VERSION);

	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		try {
			db.execSQL("CREATE TABLE company (id INTEGER PRIMARY KEY,name TEXT,info TEXT,website TEXT)");
			db.execSQL("CREATE TABLE location (id INTEGER PRIMARY KEY,country TEXT,city TEXT,address TEXT)");
			db.execSQL("CREATE TABLE product_image (id INTEGER PRIMARY KEY,path TEXT,company_id INTEGER,FOREIGN KEY(company_id) REFERENCES company (id))");
			db.execSQL("CREATE TABLE office (id INTEGER PRIMARY KEY,tel_number INTEGER,location_id INTEGER,FOREIGN KEY(location_id) REFERENCES location (id),company_id INTEGER, FOREIGN KEY(company_id) REFERENCES company(id))");
		} catch (SQLException e) {
			
			e.printStackTrace();
		}

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS");
		onCreate(db);

	}
}