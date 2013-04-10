package ca.exallium.stem.db.models;

import ca.exallium.stem.db.fields.IntegerField;

/**
 * BaseAndroidModel has extra stuff in it to make it play nice on Android
 * All Android models require an _id attribute as a primary key.
 */
public class BaseAndroidModel extends Model {

    protected IntegerField _id = new IntegerField();

}
