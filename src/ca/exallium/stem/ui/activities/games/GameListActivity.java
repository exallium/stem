package ca.exallium.stem.ui.activities.games;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;
import ca.exallium.stem.R;
import ca.exallium.stem.db.models.Game;
import ca.exallium.stem.ui.adapters.GameListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Game Activity Container
 */
public class GameListActivity extends Activity {

    private ListView gamesListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        gamesListView = (ListView) findViewById(R.id.games_list);

        List<Game> games = new ArrayList<Game>();
        for (int i = 0; i < 10; i++) {
            games.add(new Game());
        }

        gamesListView.setAdapter(new GameListAdapter(
                getApplicationContext(),
                R.layout.game_item, R.id.game_title, games));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
}
