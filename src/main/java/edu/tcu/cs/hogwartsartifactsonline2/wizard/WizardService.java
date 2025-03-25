package edu.tcu.cs.hogwartsartifactsonline2.wizard;

import jakarta.transaction.Transactional;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class WizardService {
    private final WizardRepository wizardRepository;

    public WizardService(WizardRepository wizardRepository) {
        this.wizardRepository = wizardRepository;
    }

    public List<wizard> findAll() {
        return this.wizardRepository.findAll();
    }

    public wizard findById(Integer wizardId) {
        return this.wizardRepository.findById(wizardId)
                .orElseThrow(() -> new ObjectNotFoundException(wizardId));
    }

    public wizard save(wizard newWizard) {
        return this.wizardRepository.save(newWizard);
    }

    public wizard update(Integer wizardId, wizard update) {
        return this.wizardRepository.findById(wizardId).map(oldWizard -> {
            oldWizard.setName(update.getName());
            return this.wizardRepository.save(oldWizard);
        });
    }

    public void delete(Integer wizardId) {
        wizard wizardToBeDelete = this.wizardRepository.findById(wizardId)
                .orElsethrow(() -> new ObjectNotFoundException(wizardId));

        wizardToBeDelete.removeAllArtifacts();
        this.wizardRepository.deleteById(wizardId);
    }

}
