package invenz.roy.noteprovider;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText etNote;
    private ListView noteListView;
    private NoteOperation noteOperation;
    private List<Note> noteList;
    private CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }



    /*###               initialising widgets           ###*/
    private void init() {

        etNote = findViewById(R.id.idNote);
        noteListView = findViewById(R.id.idList);
        noteOperation = new NoteOperation(this);
        noteList = new ArrayList<>();

    }



    @Override
    protected void onResume() {
        super.onResume();

        noteOperation.openDb();
        //getList();
        getAllNotesFromProvider();
    }


    @Override
    protected void onPause() {
        super.onPause();

        /*###                      closing database           ###*/
        noteOperation.closeDb();
    }


    /*####                          adding note to my Database               ###*/
    public void addNoteToList(View view) {

        String sNote = etNote.getText().toString().trim();
        /*boolean isSuccess = noteOperation.addNote(sNote);

        if (isSuccess){
            Toast.makeText(this, "Added successfully", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "Failed to add the list", Toast.LENGTH_SHORT).show();
        }*/

        Uri uri = Uri.parse("content://"+NoteContentProvider.AUTHORITY+"/"+MyDatabaseHelper.TABLE_NAME);
        ContentValues contentValues = new ContentValues();
        contentValues.put(MyDatabaseHelper.COL_NOTE, sNote);

        Uri insertedUri = getContentResolver().insert(uri, contentValues);

        //getList();
        getAllNotesFromProvider();

    }


    /*###                        getting data from my Database directly           ###*/
    public void getList(){
        Cursor noteCursor = noteOperation.readAllNotes();

        //notes = new ArrayList<>();
        noteList.clear();

        if (noteCursor!=null && noteCursor.moveToFirst()){
            do {
                String id = noteCursor.getString(noteCursor.getColumnIndex(MyDatabaseHelper.COL_ID));
                String noteName = noteCursor.getString(noteCursor.getColumnIndex(MyDatabaseHelper.COL_NOTE));

                Note note = new Note(id, noteName);
                noteList.add(note);

            }while (noteCursor.moveToNext());
        }

        customAdapter = new CustomAdapter( noteList, this);
        noteListView.setAdapter(customAdapter);
    }



    /*###                       getting data from my NoteContentProvider          ####*/
    public void getAllNotesFromProvider(){

        Uri uri = Uri.parse("content://"+NoteContentProvider.AUTHORITY+"/my_note");
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);

        noteList.clear();

        if (cursor!=null && cursor.moveToFirst()){
            do {
                String id = cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.COL_ID));
                String noteName = cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.COL_NOTE));

                Note note = new Note(id, noteName);
                noteList.add(note);

            }while (cursor.moveToNext());
        }

        customAdapter = new CustomAdapter( noteList, this);
        noteListView.setAdapter(customAdapter);

        Toast.makeText(this, "From Provider", Toast.LENGTH_SHORT).show();
    }

}
