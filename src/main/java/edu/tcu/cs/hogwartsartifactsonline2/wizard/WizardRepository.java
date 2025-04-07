package edu.tcu.cs.hogwartsartifactsonline2.wizard;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WizardRepository  extends JpaRepository<wizard, Integer>{
}
