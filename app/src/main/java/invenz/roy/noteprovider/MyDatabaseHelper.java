package invenz.roy.noteprovider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "note_db";
    public static final int DB_VERSION = 1;

    public static final String TABLE_NAME = "note_db";
    public static final String COL_ID = "_id";
    public static final String COL_NOTE = "note";

    public static final String CREATE_TABLE = "Create table if not exists "+TABLE_NAME+" ("+COL_ID+" integer primary key autoincrement, "+COL_NOTE+" varchar)";
    public static final String DROP_TABLE = "DROP TABLE IF EXISTS "+TABLE_NAME;

    public MyDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(DROP_TABLE);
        onCreate(db);
    }
}
