package main.java.com.mirocidij.simpleconsoleapplication.generic.entity;

public abstract class Entity<T extends Number> {
    protected T id;

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Entity<?> entity) {
            return this.id.equals(entity.id);
        }

        return this.equals(obj);
    }
}
