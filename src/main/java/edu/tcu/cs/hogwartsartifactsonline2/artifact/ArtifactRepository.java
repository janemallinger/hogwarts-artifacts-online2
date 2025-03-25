package edu.tcu.cs.hogwartsartifactsonline2.artifact;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtifactRepository extends JpaRepository<artifact, String> {
}
