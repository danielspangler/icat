package edu.odu.icat.model;

import java.util.List;
import static com.google.common.base.Preconditions.*;
import static com.google.common.base.Strings.*;

/**
 * Represents a Force in a problem domain.
 */
public class Force extends Base {

    private Entity origin;
    private Entity destination;
    private int weight;
    private String notes;

    /**
     * Do not use, this is for serialization purposes only
     */
    Force() {}

    public Force(Entity origin, Entity destination, int weight) {
        setOrigin(origin);
        setDestination(destination);
        setWeight(weight);
    }

    public Entity getOrigin() {
        return origin;
    }

    public void setOrigin(Entity origin) {
        checkArgument(origin!=null, "The origin may not be null");
        if (destination!=null) {
            checkArgument(origin!=destination, "The origin may not be the same as the destination");
        }
        this.origin = origin;
    }

    public Entity getDestination() {
        return destination;
    }

    public void setDestination(Entity destination) {
        checkArgument(destination!=null, "The destination may not be null");
        if (origin!=null) {
            checkArgument(origin!=destination, "The destination may not be the same as the origin");
        }
        this.destination = destination;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
