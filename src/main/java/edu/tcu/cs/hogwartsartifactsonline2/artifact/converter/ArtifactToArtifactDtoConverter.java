package edu.tcu.cs.hogwartsartifactsonline2.artifact.converter;

import edu.tcu.cs.hogwartsartifactsonline2.artifact.artifact;
import edu.tcu.cs.hogwartsartifactsonline2.artifact.dto.ArtifactDto;
import edu.tcu.cs.hogwartsartifactsonline2.wizard.converter.WizardToWizardDtoConverter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ArtifactToArtifactDtoConverter implements Converter<artifact, ArtifactDto> {

    private final WizardToWizardDtoConverter wizardToWizardDtoConverter;

    public ArtifactToArtifactDtoConverter(WizardToWizardDtoConverter wizardToWizardDtoConverter) {
        this.wizardToWizardDtoConverter = wizardToWizardDtoConverter;
    }

    @Override
    public ArtifactDto convert(artifact source) {
        ArtifactDto artifactDto = new ArtifactDto(source.getId(),
                source.getName(),
                source.getDescription(),
                source.getImageUrl(),
                source.getOwner() != null
                        ? this.wizardToWizardDtoConverter.convert(source.getOwner())
                        : null );
        return artifactDto;
    }
}
