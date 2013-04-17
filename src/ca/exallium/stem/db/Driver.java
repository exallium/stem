package ca.exallium.stem.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * SQL Driver
 */
public class Driver extends SQLiteOpenHelper {

    public static String TAG = "db.Driver";

    public Driver(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public int update(String sql) {
        Cursor c = getWritableDatabase().rawQuery(sql, null);
        return c.getCount();
    }

    public Cursor query(String sql) {
        return getReadableDatabase().rawQuery(sql, null);
    }
}
