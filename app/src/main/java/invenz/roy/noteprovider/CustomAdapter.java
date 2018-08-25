package invenz.roy.noteprovider;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class CustomAdapter extends BaseAdapter {

    List<Note> noteList;
    Context context;

    public CustomAdapter(List<Note> noteList, Context context) {
        this.noteList = noteList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return noteList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.single_note, parent, false);
        }

        TextView tvId = convertView.findViewById(R.id.idNoteId);
        TextView tvName = convertView.findViewById(R.id.idNoteName);

        Note note = noteList.get(position);
        tvId.setText(note.getId());
        tvName.setText(note.getNote());

        return convertView;
    }
}
