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


}
