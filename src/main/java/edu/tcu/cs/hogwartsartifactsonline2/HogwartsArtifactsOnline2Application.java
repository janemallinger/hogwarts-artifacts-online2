package edu.tcu.cs.hogwartsartifactsonline2;

import edu.tcu.cs.hogwartsartifactsonline2.artifact.utils.IdWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class HogwartsArtifactsOnline2Application {

    public static void main(String[] args) {
        SpringApplication.run(HogwartsArtifactsOnline2Application.class, args);
    }

    @Bean
    public IdWorker idWorker(){
        return new IdWorker(1, 1);
    }

}
