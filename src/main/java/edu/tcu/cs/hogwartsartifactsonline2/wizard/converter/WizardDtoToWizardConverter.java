package edu.tcu.cs.hogwartsartifactsonline2.wizard.converter;


import edu.tcu.cs.hogwartsartifactsonline2.wizard.WizardDto.WizardDto;
import edu.tcu.cs.hogwartsartifactsonline2.wizard.wizard;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class WizardDtoToWizardConverter implements Converter<WizardDto, wizard> {

    @Override
    public wizard convert(WizardDto source) {
        wizard wizard = new wizard();
        wizard.setId(source.id());
        wizard.setName(source.name());
        return wizard;
    }
}
