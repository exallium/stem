package ca.exallium.stem.db.models;

import android.database.Cursor;
import ca.exallium.stem.db.Driver;
import ca.exallium.stem.db.fields.DatabaseField;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Alex Hart
 * @date 4/6/2013
 * @brief Generic Model class for SQLite Models
 */
public class Model {
    private static Driver driver;
    public static String TAG = "db.models.Model";

    private boolean is_new = true;

    private String tableName() {
        return this.getClass().getName().toLowerCase();
    }

    /**
     * @brief Get create statement
     * @return The CREATE TABLE statement for a model
     */
    public String createTable() {
        String statement = String.format(
                "CREATE TABLE IF NOT EXISTS %s (", tableName());

        Field [] fields = this.getClass().getFields();
        for (Field f : fields) {
            try {
                Method method = f.getType().getMethod("createColumn");
                String col = method.invoke(f.get(this)).toString();
                statement = String.format("%s %s, ", statement, col);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return statement.replaceAll(", $", "");
    }

    private String toDatabase(Field f) {
        try {
            Method method = f.getType().getMethod("toDB");
            return method.invoke(f.get(this)).toString();
        } catch (NoSuchMethodException e) {
        } catch (InvocationTargetException e) {
        } catch (IllegalAccessException e) {
        }

        return "NULL";
    }

    private static String toDatabase(Object o) {
        try {
            Method method = o.getClass().getMethod("toDB");
            return method.invoke(o).toString();
        } catch (InvocationTargetException e) {
        } catch (NoSuchMethodException e) {
        } catch (IllegalAccessException e) {
        }

        return "NULL";
    }

    private Boolean isPrimaryKey(Field f) {
        try {
            Method method = f.getType().getMethod("isPrimary");
            return (Boolean) method.invoke(f.get(this));
        } catch (NoSuchMethodException e) {
        } catch (InvocationTargetException e) {
        } catch (IllegalAccessException e) {
        }

        return false;
    }

    protected int create() throws SQLException {
        String sql = String.format("INSERT INTO %s", tableName());
        String columns = "(";
        String values = "VALUES(";

        Field[] fields = getClass().getDeclaredFields();
        for (Field f : fields) {
            if (f.getType() != DatabaseField.class)
                continue;

            String fieldName = f.getName().toLowerCase();
            columns += String.format("%s, ", fieldName);
            values += String.format("%s, ", toDatabase(f));
        }

        columns = columns.replaceAll(", $", ")");
        values = values.replaceAll(", $", ")");
        sql = String.format("%s %s %s", sql, columns, values);
        return driver.update(sql);
    }

    /**
     * @brief Saves the model to the database
      */
    public int save() throws SQLException {
        if (is_new) {
            is_new = false;
            return create();
        }

        String sql = String.format("UPDATE %s", tableName());
        String columns = "SET ";
        String where = "WHERE ";

        Field [] fields = getClass().getDeclaredFields();
        for (Field f : fields) {
            if (f.getType() != DatabaseField.class)
                continue;

            String fieldName = f.getName().toLowerCase();
            String fieldPair = String.format(
                    "%s=%s", fieldName, toDatabase(f));
            columns += String.format("%s, ", fieldPair);

            if (isPrimaryKey(f)) {
                where += String.format("%s AND ", fieldPair);
            }
        }

        columns = columns.replaceAll(", $", "");
        where = where.replaceAll(" AND $", "");
        sql = String.format("%s %s %s", sql, columns, where);
        return driver.update(sql);
    }

    /**
     * @brief Loads a model from the database
     */
    public void load(Cursor cursor) {
        Field[] fields = getClass().getDeclaredFields();
        for (Field f : fields) {
            try {
                if (f.getType() != DatabaseField.class)
                    continue;

                String fieldName = f.getName().toLowerCase();
                Method method = f.getType().getMethod("setData", Object.class);

                int columnIndex = cursor.getColumnIndex(fieldName);

                switch (cursor.getType(columnIndex)) {
                    case Cursor.FIELD_TYPE_FLOAT:
                        method.invoke(f.get(this), cursor.getFloat(columnIndex));
                        break;
                    case Cursor.FIELD_TYPE_INTEGER:
                        method.invoke(f.get(this), cursor.getInt(columnIndex));
                        break;
                    default:
                        method.invoke(f.get(this), cursor.getString(columnIndex));
                        break;
                }

            } catch (NoSuchMethodException e) {
            } catch (InvocationTargetException e) {
            } catch (IllegalAccessException e) {
            }
        }

        is_new = false;
    }

    /**
     * @brief Deletes a model from the database
     */
    public void delete() {}

    /**
     * Select gets a list of objects from the database
     * @param hashMap Select params, filtering
     * @param clazz The class we are using for T
     * @param <T>   Type parameter
     * @return A List of objects of type clazz or an empty list.
     * @throws SQLException If something went wrong building the query.
     */
    public static <T> List<T> select(HashMap<String, Object> hashMap,
                                     Class<T> clazz) throws SQLException {

        String sql = null;
        try {
            sql = String.format("SELECT * FROM %s",
                    clazz.getDeclaredField("TABLE_NAME").get(null));
        } catch (NoSuchFieldException e) {
        } catch (IllegalAccessException e) {
        }

        String where = "WHERE ";

        // Process the hashmap
        if (hashMap != null) {
            for (String key : hashMap.keySet()) {
                Object value = hashMap.get(key);

                String dbColumn = key.toLowerCase();
                if (value instanceof List) {
                    // Build an OR statement
                    String or = "(";
                    for(Object obj : (List) value) {
                        String dbValue = toDatabase(obj);
                        String comparator = dbValue.contentEquals("NULL") ? " IS " : "=";
                        or += String.format(
                                "%s%s%s OR ", dbColumn, comparator, dbValue);
                    }

                    or = or.replaceAll(" OR $", ") AND ");
                    where += or;
                } else {
                    String dbValue = toDatabase(value);
                    String comparator = dbValue.contentEquals("NULL") ? " IS " : "=";
                    where += String.format(
                            "%s%s%s AND ", dbColumn, comparator, dbValue);
                }
            }

            where = where.replaceAll(" AND $", "");
            sql = String.format("%s %s", sql, where);
        }

        // Run the query
        Cursor cursor = driver.query(sql);
        List<T> modelList = new ArrayList<T>();
        try {
            while (cursor.moveToNext()) {
                T model = clazz.newInstance();
                Method method = (model
                        .getClass()
                        .getSuperclass()
                        .getDeclaredMethod("loadData", Cursor.class));
                method.invoke(model, cursor);
                modelList.add(model);
            }
        } catch (InstantiationException e) {
        } catch (IllegalAccessException e) {
        } catch (NoSuchMethodException e) {
        } catch (InvocationTargetException e) {
        }

        cursor.close();
        return modelList;

    }

}
