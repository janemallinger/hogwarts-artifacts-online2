package edu.tcu.cs.hogwartsartifactsonline2;

import edu.tcu.cs.hogwartsartifactsonline2.artifact.ArtifactRepository;
import edu.tcu.cs.hogwartsartifactsonline2.artifact.artifact;
import edu.tcu.cs.hogwartsartifactsonline2.wizard.WizardRepository;
import edu.tcu.cs.hogwartsartifactsonline2.wizard.WizardService;
import edu.tcu.cs.hogwartsartifactsonline2.wizard.wizard;
import org.hibernate.ObjectNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WizardServiceTest {

    @Mock
    WizardRepository wizardRepository;

    @Mock
    ArtifactRepository artifactRepository;

    @InjectMocks
    WizardService wizardService;

    List<wizard> wizards;

    @BeforeEach
    void setUp() {
        wizard w1 = new wizard();
        w1.setId(1);
        w1.setName("Albus");

        wizard w2 = new wizard();
        w2.setId(2);
        w2.setName("Harry");

        wizard w3 = new wizard();
        w3.setId(3);
        w3.setName("Neville");

        this.wizards = new ArrayList<>();
        this.wizards.add(w1);
        this.wizards.add(w2);
        this.wizards.add(w3);

    }

    @AfterEach
    void tearDown(){

    }

    @Test
    void testFindAllSuccess() {
        //Given
        given(this.wizardRepository.findAll()).willReturn(this.wizards);

        //When
        List<wizard> actualWizards = this.wizardService.findAll();

        //Then
        assertThat(actualWizards.size()).isEqualTo(this.wizards.size());
        verify(this.wizardRepository, times(1)).findAll();
    }

    @Test
    void testFindByIdSuccess() {
        //Given
        wizard w = new wizard();
        w.setId(1);
        w.setName("Albus");

        given(this.wizardRepository.findById(1)).willReturn(Optional.of(w));

        //When
        wizard returnedWizard = this.wizardService.findById(1);

        //Then
        assertThat(returnedWizard.getId()).isEqualTo(w.getId());
        assertThat(returnedWizard.getName()).isEqualTo(w.getName());
        verify(this.wizardRepository, times(1)).findById(1);
    }

    @Test
    void testFindByIdNotFound() {
        //Given
        given(this.wizardRepository.findById(Mockito.any(Integer.class))).willReturn(Optional.empty());

        //When
        Throwable thrown = catchThrowable(() -> {
            wizard returnedWizard = this.wizardService.findById(1);
        });

        //Then
        assertThat(thrown)
                .isInstanceOf(ObjectNotFoundException.class)
                .hasMessage("Could not find wizard with id 1");
        verify(this.wizardRepository, times(1)).findById(1);
    }

    @Test
    void testSaveSuccess() {
        //Given
        wizard newWizard = new wizard();
        newWizard.setName("Hermione");

        given(this.wizardRepository.save(newWizard)).willReturn(newWizard);

        //When
        wizard returnedWizard = this.wizardService.save(newWizard);

        //Then
        assertThat(returnedWizard.getName()).isEqualTo(newWizard.getName());
        verify(this.wizardRepository, times(1)).save(newWizard);
    }

    @Test
    void testUpdateSuccess() {
        //Given
        wizard oldWizard = new wizard();
        oldWizard.setId(1);
        oldWizard.setName("albus");

        wizard update = new wizard();
        update.setName("albus - updated");

        given(this.wizardRepository.findById(1)).willReturn(Optional.of(oldWizard));
        given(this.wizardRepository.save(oldWizard)).willReturn(oldWizard);

        //When
        wizard updatedWizard = this.wizardService.update(1, update);

        //Then
        assertThat(updatedWizard.getId()).isEqualTo(1);
        assertThat(updatedWizard.getName()).isEqualTo(update.getName());
        verify(this.wizardRepository, times(1)).findById(1);
        verify(this.wizardRepository, times(1)).save(oldWizard);
    }

    @Test
    void testUpdateNotFound() {
        wizard update = new wizard();
        update.setName("albus - update");

        given(this.wizardRepository.findById(1)).willReturn(Optional.empty());

        //When
        assertThrows(ObjectNotFoundException.class, () -> {
            this.wizardService.update(1, update);
        });

        //Then
        verify(this.wizardRepository, times(1)).findById(1);
    }

    @Test
    void testDeleteSuccess() {
        //Given
        wizard wizard = new wizard();
        wizard.setId(1);
        wizard.setName("Albus");

        given(this.wizardRepository.findById(1)).willReturn(Optional.of(wizard));
        doNothing().when(this.wizardRepository).deleteById(1);

        //When
        this.wizardService.delete(1);

        //Then
        verify(this.wizardRepository, times(1)).deleteById(1);
    }

    @Test
    void testDeleteNotFound() {
        //Given
        given(this.wizardRepository.findById(1)).willReturn(Optional.empty());

        //When
        assertThrows(ObjectNotFoundException.class, () -> {
            this.wizardService.delete(1);
        });

        //Then
        verify(this.wizardRepository, times(1)).findById(1);
    }

    @Test
    void testAssignArtifactSuccess() {
        //Given
        artifact a = new artifact();
        a.setId("1250808601744904192");
        a.setName("cloak");
        a.setDescription("It's a cloak");
        a.setImageUrl("ImageUrl");

        wizard w2 = new wizard();
        w2.setId(2);
        w2.setName("Harry");
        w2.addArtifact(a);

        wizard w3 = new wizard();
        w3.setId(3);
        w3.setName("Neville");

        given(this.artifactRepository.findById("1250808601744904192")).willReturn(Optional.of(a));
        given(this.wizardRepository.findById(3)).willReturn(Optional.of(w3));


        //When
        this.wizardService.assignArtifact(3, "1250808601744904192");

        //Then
        assertThat(a.getOwner().getId()).equalTo(3);
        assertThat(w3.getArtifacts()).contains(a);

    }

    @Test
    void testAssignArtifactErrorWithNonExistentWizardId() {
        //Given
        artifact a = new artifact();
        a.setId("1250808601744904192");
        a.setName("cloak");
        a.setDescription("It's a cloak");
        a.setImageUrl("ImageUrl");

        wizard w2 = new wizard();
        w2.setId(2);
        w2.setName("Harry");
        w2.addArtifact(a);

        given(this.artifactRepository.findById("1250808601744904192")).willReturn(Optional.of(a));
        given(this.wizardRepository.findById(3)).willReturn(Optional.empty());


        //When
        Throwable thrown = assertThrows(ObjectNotFoundException.class, () -> {
            this.wizardService.assignArtifact(3, "1250808601744904192");
        });

        //Then
        assertThat(thrown)
                .isInstanceOf(ObjectNotFoundException.class).hasMessage("Could not find");
        assertThat(a.getOwner().getId()).equalTo(2);


    }

    @Test
    void testAssignArtifactWithNonExistentArtifactId() {
        //Given
        given(this.artifactRepository.findById("1250808601744904192")).willReturn(Optional.empty());


        //When
        Throwable thrown = assertThrows(ObjectNotFoundException.class, () -> {
            this.wizardService.assignArtifact(3, "1250808601744904192");
        });

        //Then
        assertThat(thrown)
                .isInstanceOf(ObjectNotFoundException.class).hasMessage("Could not find");

    }
}
