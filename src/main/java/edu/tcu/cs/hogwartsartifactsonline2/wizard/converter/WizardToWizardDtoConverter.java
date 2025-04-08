package edu.tcu.cs.hogwartsartifactsonline2.wizard.converter;

import edu.tcu.cs.hogwartsartifactsonline2.wizard.WizardDto.WizardDto;
import edu.tcu.cs.hogwartsartifactsonline2.wizard.wizard;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class WizardToWizardDtoConverter implements Converter<wizard, WizardDto> {

    @Override
    public WizardDto convert(wizard source) {
        WizardDto wizardDto = new WizardDto(source.getId(),
                source.getName(),
                source.getNumberOfArtifacts());
        return wizardDto;
    }

}