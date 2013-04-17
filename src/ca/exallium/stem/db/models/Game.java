package ca.exallium.stem.db.models;

import ca.exallium.stem.db.fields.IntegerField;
import ca.exallium.stem.db.fields.StringField;
import ca.exallium.stem.db.fields.TimestampField;

/**
 * @brief Game information
 */
public class Game extends BaseAndroidModel {

    public static String TAG = "db.models.Game";

    // Game title
    private StringField title = new StringField();

    // When the game was created
    private TimestampField created = new TimestampField();

    // Number of rounds in this game
    private IntegerField rounds = new IntegerField();
}
