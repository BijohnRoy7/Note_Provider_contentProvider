package invenz.roy.noteprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class NoteContentProvider extends ContentProvider {

    MyDatabaseHelper databaseHelper;
    SQLiteDatabase db;

    /*####                      Crating URI matcher          ###*/
    private static UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    public static final String AUTHORITY = "invenz.roy.noteprovider.contentprovider";

    static {
        uriMatcher.addURI(AUTHORITY, "my_note", 0);
        uriMatcher.addURI(AUTHORITY, "my_note/#", 1);
    }



    @Override
    public boolean onCreate() {

        databaseHelper = new MyDatabaseHelper(getContext());
        db = databaseHelper.getWritableDatabase();
        return true;
    }


    /*###                    if uri match, give all data to the user               ###*/
    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        int isMatch = uriMatcher.match(uri);

        switch (isMatch){

            case 0:
                /*###               getting all values        ###*/
                Cursor cursor = db.query(MyDatabaseHelper.TABLE_NAME, projection, selection, selectionArgs, null, null, null);
                return cursor;

            case 1:
                String id = uri.getLastPathSegment(); /*##  getting the id from uri to fetch  ##*/
                Cursor cursor1 = db.query(MyDatabaseHelper.TABLE_NAME, projection, MyDatabaseHelper.COL_ID+" = "+id, null, null, null, null );
                return cursor1;

            default:
                throw new UnsupportedOperationException("Invalid operation");

        }

    }



    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        long id = db.insert(MyDatabaseHelper.TABLE_NAME, null, values);

        uri = Uri.parse("content://"+AUTHORITY+"/"+MyDatabaseHelper.TABLE_NAME+"/"+id);
        return uri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

}
