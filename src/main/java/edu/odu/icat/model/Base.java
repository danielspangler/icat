package edu.odu.icat.model;

import java.util.UUID;

/**
 * Base class for all of the model objects to ensure that an id is always present
 * and that all models can do a basic id based equality check.
 */
public abstract class Base {

    private UUID id = UUID.randomUUID();

    /**
     * @return a unique id for the object
     */
    public UUID getId() {
        return id;
    }

    /**
     * This does a basic id based equality check.
     *
     * @param o the object to compare
     * @return true if the objects are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Base base = (Base) o;

        if (!id.equals(base.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

}
