package ca.exallium.stem.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import ca.exallium.stem.R;
import ca.exallium.stem.db.models.Game;

import java.util.List;

/**
 * List adapter for games list.
 */
public class GameListAdapter extends ArrayAdapter<Game> {

    private List<Game> objects;
    private int resource;

    public GameListAdapter(Context context, int resource, int textViewResourceId, List<Game> objects) {
        super(context, resource, textViewResourceId, objects);

        this.objects = objects;
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Game game = objects.get(position);

        if (game != null && convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(resource, parent, false);
            TextView gameTitle = (TextView) convertView.findViewById(R.id.game_title);
            gameTitle.setText("ASDF");
        }

        return convertView;
    }
}