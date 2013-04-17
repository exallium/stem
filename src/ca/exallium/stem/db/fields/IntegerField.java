package ca.exallium.stem.db.fields;

/**
 * @brief A Database field for Integer values
 */
public class IntegerField extends DatabaseField<Integer> {
    @Override
    public String toDB() {
        return Integer.toString(getData());
    }

    @Override
    public String columnType() {
        return "INTEGER";
    }
}
