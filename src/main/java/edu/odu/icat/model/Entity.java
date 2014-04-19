package edu.odu.icat.model;

import java.util.List;
import static com.google.common.base.Preconditions.*;
import static com.google.common.base.Strings.*;

import edu.odu.icat.controller.Control;

/**
 * Represents an Entity in a problem domain.
 */
public class Entity extends Base {

    private String name;
    private String classification;
    private Location location;
    private String notes;
    private boolean controllable;
    private boolean visible;

    /** DO NOT USE: this is only for serialization purposes **/
    Entity(){}

    public Entity(String name, String classification) {
        setName(name);
        setClassification(classification);
        setVisible(true);
        setControllable(true);

        Control.getInstance().getCurrentProject().addEntity(this);  //Add self to project
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

    public boolean isControllable() {
        return controllable;
    }

    public void setControllable(boolean controllable) {
        this.controllable = controllable;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    @Override
    public String toString() {
        return getName();
    }
}
