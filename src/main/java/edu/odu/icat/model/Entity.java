package edu.odu.icat.model;

import java.awt.Point;
import java.util.List;
import static com.google.common.base.Preconditions.*;
import static com.google.common.base.Strings.*;

/**
 * Represents an Entity in a problem domain.
 */
public class Entity extends Base {

    private String name;
    private String classification;
    private Point location;
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

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

}
