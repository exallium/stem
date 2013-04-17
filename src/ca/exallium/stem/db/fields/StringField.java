package ca.exallium.stem.db.fields;

/**
 * @brief A database field for String values
 */
public class StringField extends DatabaseField<String> {

    public static String TAG = "db.fields.StringField";

    @Override
    public String toDB() {
        return String.format("\"%s\"", getData().replace("\"", "'"));
    }

    @Override
    public String columnType() {
        return "VARCHAR";
    }
}
