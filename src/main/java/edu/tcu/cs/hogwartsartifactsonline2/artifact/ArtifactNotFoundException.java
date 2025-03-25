package edu.tcu.cs.hogwartsartifactsonline2.artifact;

public class ArtifactNotFoundException extends RuntimeException {
    public ArtifactNotFoundException(String id) {
        super("Could not find artifact with id " + id + " :(");
    }

}
