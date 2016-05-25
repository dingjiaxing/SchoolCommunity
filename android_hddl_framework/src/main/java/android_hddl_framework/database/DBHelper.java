package android_hddl_framework.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DBHelper extends SQLiteAssetHelper{

	private static final String DB_NAME = "anitoys.db";
	private static final int VERSION = 1;
	private String sql = "";
	public DBHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, VERSION);
	}

	public DBHelper(Context context) {
		super(context, DB_NAME, null, VERSION);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		
	}

}
