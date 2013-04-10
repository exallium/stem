package ca.exallium.stem.db.fields;

/**
 * @brief A database field for String values
 */
public class StringField extends DatabaseField<String> {
    @Override
    public String toDB() {
        return String.format("\"%s\"", getData().replace("\"", "'"));
    }
}
