package ca.exallium.stem.db.models;

import ca.exallium.stem.db.fields.StringField;

/**
 * @brief Swiss tournament User.  Think of this as a player.
 */
public class User extends BaseAndroidModel {

    // The user's name
    private StringField name = new StringField();

    // Any extra information we should store
    private StringField extra = new StringField();
}
