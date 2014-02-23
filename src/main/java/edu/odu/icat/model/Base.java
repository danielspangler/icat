package edu.odu.icat.model;

import java.util.UUID;

/**
 *
 */
public abstract class Base {

    private UUID id = UUID.randomUUID();

    public UUID getId() {
        return id;
    }

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
