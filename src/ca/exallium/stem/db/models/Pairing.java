package ca.exallium.stem.db.models;

import ca.exallium.stem.db.fields.BooleanField;
import ca.exallium.stem.db.fields.IntegerField;

/**
 * @brief Database model for Pairings
 */
public class Pairing extends BaseAndroidModel {

    private IntegerField gameId = new IntegerField();
    private IntegerField user1Id = new IntegerField();
    private IntegerField user2Id = new IntegerField();
    private IntegerField user1Wins = new IntegerField();
    private IntegerField user2Wins = new IntegerField();
    private IntegerField draws = new IntegerField();
    private IntegerField round = new IntegerField();
    private BooleanField user1Dropped = new BooleanField();
    private BooleanField user2Dropped = new BooleanField();

}
