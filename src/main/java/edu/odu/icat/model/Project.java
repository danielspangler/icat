package edu.odu.icat.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static com.google.common.base.Preconditions.*;
import static com.google.common.base.Strings.*;

/**
 */
public class Project extends Base {

    private String name;
    private String description;
    private Date creationDate;
    private String author;
    private Date lastModifiedDate;
    private String lastModifiedUser;
    private List<Entity> entities = new ArrayList<Entity>();
    private List<Force> forces = new ArrayList<Force>();
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

    public List<Entity> getEntities() {
        return entities;
    }

    public void setEntities(List<Entity> entities) {
        this.entities = entities;
    }

    public List<Force> getForces() {
        return forces;
    }

    public void setForces(List<Force> forces) {
        this.forces = forces;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
