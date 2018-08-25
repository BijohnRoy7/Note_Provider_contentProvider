package invenz.roy.noteprovider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class NoteOperation {

    MyDatabaseHelper databaseHelper;
    SQLiteDatabase db;
    List<Note> notes;

    public NoteOperation(Context context) {
        this.databaseHelper = new MyDatabaseHelper(context);
    }

    public  void openDb(){
        db = databaseHelper.getWritableDatabase();
    }

    public void closeDb(){
        db.close();
    }


    public boolean addNote(String note){

        ContentValues contentValues = new ContentValues();
        contentValues.put(MyDatabaseHelper.COL_NOTE, note);

        long id = db.insert(MyDatabaseHelper.TABLE_NAME, null, contentValues);

        return id != -1;

    }


    public Cursor readAllNotes(){

        Cursor cursor = db.query(MyDatabaseHelper.TABLE_NAME, null, null, null, null, null, null);
        return cursor;
    }



}
