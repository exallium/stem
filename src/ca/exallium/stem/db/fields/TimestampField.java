package ca.exallium.stem.db.fields;

import java.sql.Timestamp;

/**
 * @brief a database field to store Datetime information
 */
public class TimestampField extends DatabaseField<Timestamp>{

    public static String TAG = "db.fields.TimestampField";

    @Override
    public String toDB() {
        return Long.toString(getData().getTime());
    }

    @Override
    public String columnType() {
        return "VARCHAR";
    }
}
