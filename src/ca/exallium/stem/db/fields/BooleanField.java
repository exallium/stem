package ca.exallium.stem.db.fields;

/**
 * Created with IntelliJ IDEA.
 * User: alex
 * Date: 13-04-06
 * Time: 1:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class BooleanField extends DatabaseField<Boolean> {

    public static String TAG = "db.fields.BooleanField";

    @Override
    public String toDB() {
        return getData() ? "1" : "0";
    }

    @Override
    public String columnType() {
        return "INTEGER";
    }
}
