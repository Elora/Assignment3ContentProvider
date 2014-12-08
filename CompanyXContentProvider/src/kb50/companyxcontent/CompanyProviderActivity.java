package kb50.companyxcontent;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class CompanyProviderActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_company_provider);
	}
	
	public void onClickAddTitle(View view){
		String title = ((EditText) findViewById(R.id.txtTitle)).getText().toString();
		String isbn = ((EditText) findViewById(R.id.txtISBN)).getText().toString();
		
		//---add a book---
		ContentValues values = new ContentValues();
		values.put(CompanyProvider.TITLE, title);
		values.put(CompanyProvider.ISBN, isbn);

		Uri uri = getContentResolver().insert(CompanyProvider.CONTENT_URI, values);
		Toast.makeText(getBaseContext(), uri.toString(), Toast.LENGTH_LONG).show();
	}
	
	public void onClickRetrieveTitles(View view){
		//---retrieve the titles---
		Uri allTitles = Uri.parse("content://net.learn2develop.provider.Books/books");
		Cursor c;
		if(android.os.Build.VERSION.SDK_INT < 11){
			//---before honeycomb---
			c = managedQuery(allTitles, null, null, null, "title desc");
		}else{
			//---honeycomb and later---
			CursorLoader cursorLoader = new CursorLoader(this, allTitles, null, null, null, "title desc");
			c = cursorLoader.loadInBackground();
		}
		
		if(c.moveToFirst()){
			do{
				Toast.makeText(this, c.getString(c.getColumnIndex(CompanyProvider._ID)) + ", " + c.getString(c.getColumnIndex(CompanyProvider.TITLE)) + ", " + c.getString(c.getColumnIndex(CompanyProvider.ISBN)), Toast.LENGTH_SHORT).show();
			}while(c.moveToNext());
		}
	}
}