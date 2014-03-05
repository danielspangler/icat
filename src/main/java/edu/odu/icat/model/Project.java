package edu.odu.icat.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import static com.google.common.base.Preconditions.*;
import static com.google.common.base.Strings.*;

/**
 * Represents a problem domain.  It is basically a directed graph where each edge is an Entity and each
 * vertex is a Force.  The list of forces and entities return unmodifiable lists in an effort to enforce
 * rules in the addition and removal of these elements.  For instance, you can't remove an entity without
 * removing the forces that act on it first and you can't add a force to an entity that does not exist
 * in this project.
 */
public class Project extends Base {

    private String name;
    private String description;
    private Date creationDate;
    private String author;
    private Date lastModifiedDate;
    private String lastModifiedUser;
    private List<Entity> entities = new ArrayList<Entity>();
    private transient List<Entity> entitiesView = null;
    private List<Force> forces = new ArrayList<Force>();
    private transient List<Force> forcesView = null;
    private String notes;

    /** DO NOT USE: This is only for serialization purposes **/
    Project() {}

    public Project(String name, String description, String author) {
        setName(name);
        setDescription(description);
        setAuthor(author);
        setCreationDate(new Date());
        setLastModifiedDate(getCreationDate());
        setLastModifiedUser(author);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        checkArgument(!isNullOrEmpty(name), "The project name may not be empty");
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        checkArgument(creationDate!=null, "The project creation date may not be null");
        this.creationDate = creationDate;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        checkArgument(!isNullOrEmpty(author), "The project author may not be empty");
        this.author = author;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        checkArgument(lastModifiedDate!=null, "The project last modified date may not be null");
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getLastModifiedUser() {
        return lastModifiedUser;
    }

    public void setLastModifiedUser(String lastModifiedUser) {
        this.lastModifiedUser = lastModifiedUser;
    }

    /**
     * @return an immutable list of entities, use addEntity, removeEntity to modify this list
     */
    public List<Entity> getEntities() {
        if (entitiesView==null) {
            entitiesView = Collections.unmodifiableList(entities);
        }
        return entitiesView;
    }

    public void addEntity(Entity entity) {
        checkArgument(entity!=null, "The entity may not be null");
        entities.add(entity);
    }

    public void remoteEntity(Entity entity) {
        checkArgument(findAllForcesInvolvingEntity(entity).size()==0, "An entity may not be removed from the project if forces are currently acting on it");
        entities.remove(entity);
    }

    public List<Force> findAllForcesInvolvingEntity(Entity entity) {
        final ArrayList<Force> results = new ArrayList<Force>();
        for (Force force : forces) {
            if (force.getOrigin()==entity || force.getDestination()==entity) {
                results.add(force);
            }
        }
        return results;
    }

    /**
     * @return an immutable list of entities, use addForce, removeForce to modify ths list
     */
    public List<Force> getForces() {
        if (forcesView==null) {
            forcesView = Collections.unmodifiableList(forces);
        }
        return forcesView;
    }

    public void addForce(Force force) {
        checkArgument(force!=null, "The force may not be null");
        checkArgument(entities.contains(force.getOrigin()), "The origin entity is not part of this project");
        checkArgument(entities.contains(force.getDestination()), "The destination entity is not part of this project");
        forces.add(force);
    }

    public void removeForce(Force force) {
        forces.remove(force);
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
