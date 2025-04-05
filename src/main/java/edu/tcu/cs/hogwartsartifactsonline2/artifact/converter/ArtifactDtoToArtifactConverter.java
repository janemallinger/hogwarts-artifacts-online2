package edu.tcu.cs.hogwartsartifactsonline2.artifact.converter;

import edu.tcu.cs.hogwartsartifactsonline2.artifact.artifact;
import edu.tcu.cs.hogwartsartifactsonline2.artifact.dto.ArtifactDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.lang.reflect.ParameterizedType;

@Component
public class ArtifactDtoToArtifactConverter implements Converter<ArtifactDto, artifact> {

    @Override
    public artifact convert(ArtifactDto source) {
        artifact artifact = new artifact();
        artifact.setId(source.id());
        artifact.setName(source.name());
        artifact.setDescription(source.description());
        artifact.setImageUrl(source.imageUrl());

        return artifact;
    }

}
