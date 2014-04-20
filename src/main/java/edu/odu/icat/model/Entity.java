package edu.odu.icat.model;

import static com.google.common.base.Preconditions.*;
import static com.google.common.base.Strings.*;

/**
 * Represents an Entity in a problem domain.
 */
public class Entity extends Base {

    private String name;
    private String classification;
    private Location location;
    private String notes;

    /** DO NOT USE: this is only for serialization purposes **/
    Entity(){}

    public Entity(String name, String classification) {
        setName(name);
        setClassification(classification);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        checkArgument(!isNullOrEmpty(name), "The Entity name may not be empty");
        this.name = name;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        checkArgument(!isNullOrEmpty(classification), "The Entity classification may not be empty");
        this.classification = classification;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return getName();
    }
}
