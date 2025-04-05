package edu.tcu.cs.hogwartsartifactsonline2.artifact;

import edu.tcu.cs.hogwartsartifactsonline2.wizard.wizard;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.io.Serializable;

@Entity
public class artifact implements Serializable {
    @Id
    private String id;
    private String name;
    private String description;
    private String imageUrl;

    @ManyToOne
    private wizard owner;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public wizard getOwner() {
        return owner;
    }

    public void setOwner(wizard owner) {
        this.owner = owner;
    }
}
