package ca.exallium.stem.db.models;

import ca.exallium.stem.db.fields.IntegerField;

/**
 * Created with IntelliJ IDEA.
 * User: alex
 * Date: 13-04-06
 * Time: 1:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class GameUserRelation {

    // Game ID
    private IntegerField gameId = new IntegerField();

    // User ID
    private IntegerField userId = new IntegerField();

}
