package ca.exallium.stem.db.fields;

/**
 * Generic DatabaseField class.
 * @param <T> The type of database field to create.
 */
public abstract class DatabaseField<T> {

    private boolean primary = false;
    private T data;

    public DatabaseField() {}

    public DatabaseField(T data) {
        this.data = data;
    }

    public DatabaseField(boolean primary) {
        this.primary = primary;
    }

    public DatabaseField(T data, boolean primary) {
        this.data = data;
        this.primary = primary;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isPrimary() {
        return primary;
    }

    public abstract String toDB();
}
