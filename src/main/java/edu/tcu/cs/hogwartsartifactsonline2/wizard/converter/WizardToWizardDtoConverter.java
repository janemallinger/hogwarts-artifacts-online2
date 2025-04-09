package edu.tcu.cs.hogwartsartifactsonline2.wizard.converter;

import edu.tcu.cs.hogwartsartifactsonline2.wizard.WizardDto.WizardDto;
import edu.tcu.cs.hogwartsartifactsonline2.wizard.Wizard;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class WizardToWizardDtoConverter implements Converter<Wizard, WizardDto> {

    @Override
    public WizardDto convert(Wizard source) {
        WizardDto wizardDto = new WizardDto(source.getId(),
                source.getName(),
                source.getNumberOfArtifacts());

        return wizardDto;
    }

}