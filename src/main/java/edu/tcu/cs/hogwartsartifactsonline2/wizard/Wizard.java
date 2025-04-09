package edu.tcu.cs.hogwartsartifactsonline2.wizard;

import edu.tcu.cs.hogwartsartifactsonline2.artifact.Artifact;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Wizard implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "owner")
    private List<Artifact> Artifacts = new ArrayList<>();

    public Wizard() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Artifact> getArtifacts() {
        return Artifacts;
    }

    public void setArtifacts(List<Artifact> Artifacts) {
        this.Artifacts = Artifacts;
    }

    public void addArtifact(Artifact artifact) {
        artifact.setOwner(this);
        this.Artifacts.add(artifact);
    }

    public Integer getNumberOfArtifacts() {
        return this.Artifacts.size();
    }

    public void removeAllArtifacts() {
        this.Artifacts.stream().forEach(artifact -> artifact.setOwner(null));
        this.Artifacts = null;
    }

    public void removeArtifact(Artifact artifactToBeAssigned) {
        artifactToBeAssigned.setOwner(null);
        this.Artifacts.remove(artifactToBeAssigned);
    }
}
