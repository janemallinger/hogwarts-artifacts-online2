package edu.tcu.cs.hogwartsartifactsonline2.wizard.converter;


import edu.tcu.cs.hogwartsartifactsonline2.wizard.WizardDto.WizardDto;
import edu.tcu.cs.hogwartsartifactsonline2.wizard.Wizard;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class WizardDtoToWizardConverter implements Converter<WizardDto, Wizard> {

    @Override
    public Wizard convert(WizardDto source) {
        Wizard wizard = new Wizard();
        wizard.setId(source.id());
        wizard.setName(source.name());
        return wizard;
    }
}
