package edu.tcu.cs.hogwartsartifactsonline2.system;

import edu.tcu.cs.hogwartsartifactsonline2.artifact.ArtifactRepository;
import edu.tcu.cs.hogwartsartifactsonline2.artifact.artifact;
import edu.tcu.cs.hogwartsartifactsonline2.hogwartsuser.HogwartsUser;
import edu.tcu.cs.hogwartsartifactsonline2.hogwartsuser.UserService;
import edu.tcu.cs.hogwartsartifactsonline2.wizard.WizardRepository;
import edu.tcu.cs.hogwartsartifactsonline2.wizard.wizard;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DBDataInitializer implements CommandLineRunner {

    private final ArtifactRepository artifactRepository;

    private final WizardRepository wizardRepository;

    private final UserService userService;

    public DBDataInitializer(ArtifactRepository artifactRepository, WizardRepository wizardRepository, UserService userService) {
        this.artifactRepository = artifactRepository;
        this.wizardRepository = wizardRepository;
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {

        artifact a1 = new artifact();
//        a1.setId("1250808601744904191");
        a1.setName("Deluminator");
        a1.setDescription("It's a Deluminator");
        a1.setImageUrl("ImageUrl");


        artifact a2 = new artifact();
//        a2.setId("1250808601744904192");
        a2.setName("Cloak");
        a2.setDescription("It's a Cloak");
        a2.setImageUrl("ImageUrl");


        artifact a3 = new artifact();
//        a3.setId("1250808601744904193");
        a3.setName("Wand");
        a3.setDescription("It's a wand");
        a3.setImageUrl("ImageUrl");


        artifact a4 = new artifact();
//        a4.setId("1250808601744904194");
        a4.setName("Map");
        a4.setDescription("It's a Map");
        a4.setImageUrl("ImageUrl");


        artifact a5 = new artifact();
//        a5.setId("1250808601744904195");
        a5.setName("Sword");
        a5.setDescription("It's a Sword");
        a5.setImageUrl("ImageUrl");


        artifact a6 = new artifact();
//        a6.setId("1250808601744904196");
        a6.setName("Stone");
        a6.setDescription("It's a Stone");
        a6.setImageUrl("ImageUrl");


        wizard w1 = new wizard();
//        w1.setId(1);
        w1.setName("Albus");
        w1.addArtifact(a1);
        w1.addArtifact(a3);

        wizard w2 = new wizard();
//        w2.setId(2);
        w2.setName("Harry");
        w2.addArtifact(a2);
        w2.addArtifact(a4);

        wizard w3 = new wizard();
//        w3.setId(2);
        w3.setName("Neville");
        w3.addArtifact(a5);

        wizardRepository.save(w1);
        wizardRepository.save(w2);
        wizardRepository.save(w3);

        artifactRepository.save(a6);

        HogwartsUser u1 = new HogwartsUser();
//        u1.setId(1);
        u1.setUsername("john");
        u1.setPassword("123456");
        u1.setEnabled(true);
        u1.setRoles("admin user");

        HogwartsUser u2 = new HogwartsUser();
//        u2.setId(2);
        u2.setUsername("eric");
        u2.setPassword("654321");
        u2.setEnabled(true);
        u2.setRoles("user");

        HogwartsUser u3 = new HogwartsUser();
//        u3.setId(3);
        u3.setUsername("tom");
        u3.setPassword("qwerty");
        u3.setEnabled(false);
        u3.setRoles("user");

        this.userService.save(u1);
        this.userService.save(u2);
        this.userService.save(u3);




    }
}
