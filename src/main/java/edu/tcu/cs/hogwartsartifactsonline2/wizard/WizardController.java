package edu.tcu.cs.hogwartsartifactsonline2.wizard;

import edu.tcu.cs.hogwartsartifactsonline2.system.Result;
import edu.tcu.cs.hogwartsartifactsonline2.system.StatusCode;
import edu.tcu.cs.hogwartsartifactsonline2.wizard.WizardDto.WizardDto;
import edu.tcu.cs.hogwartsartifactsonline2.wizard.converter.WizardDtoToWizardConverter;
import edu.tcu.cs.hogwartsartifactsonline2.wizard.converter.WizardToWizardDtoConverter;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping ("${api.endpoint.base-url}/wizards")
public class WizardController {

    private final WizardService wizardService;

    private final WizardDtoToWizardConverter wizardDtoToWizardConverter;

    private final WizardToWizardDtoConverter wizardToWizardDtoConverter;

    public WizardController(WizardService wizardService, WizardDtoToWizardConverter wizardDtoToWizardConverter, WizardToWizardDtoConverter wizardToWizardDtoConverter) {
        this.wizardService = wizardService;
        this.wizardDtoToWizardConverter = wizardDtoToWizardConverter;
        this.wizardToWizardDtoConverter = wizardToWizardDtoConverter;
    }

    @GetMapping()
    public Result findAllWizards() {
        List<wizard> foundWizards = this.wizardService.findAll();

        List<WizardDto> wizardDtos = foundWizards.stream()
                .map(this.wizardToWizardDtoConverter::convert)
                .collect(Collectors.toList());
        return new Result(true, StatusCode.SUCCESS, "Find All Wizards", wizardDtos);
    }

    @GetMapping("/{wizardId}")
    public Result findWizardById(@PathVariable Integer wizardId) {
        wizard foundWizard = this.wizardService.findById(wizardId);
        WizardDto wizardDto = this.wizardToWizardDtoConverter.convert(foundWizard);
        return new Result(true, StatusCode.SUCCESS, "Find by ID", wizardDto);
    }

    @PostMapping
    public Result addWizard(@Valid @RequestBody WizardDto wizardDto) {
        wizard newWizard = this.wizardDtoToWizardConverter.convert(wizardDto);
        wizard savedWizard = this.wizardService.save(newWizard);
        WizardDto savedWizardDto = this.wizardDtoToWizardConverter.convert(savedWizard);
        return new Result(true, StatusCode.SUCCESS, "Add Success", savedWizardDto);
    }

    @PutMapping("/{wizardId}")
    public Result updateWizard(@PathVariable Interger wizardId, @Valid @RequestBody WizardDto wizardDto) {
        wizard update = this.wizardDtoToWizardConverter.convert(wizardDto);
        wizard updateWizard = this.wizardService.update(wizardId, update);
        WizardDto updatedWizardDto = this.wizardToWizardDtoConverter.convert(updateWizard);
        return new Result(true, StatusCode.SUCCESS, "updated success", updatedWizardDto);
    }

    @DeleteMapping("/{wizardId}")
    public Result deleteWizard(@PathVariable Interger wizardId) {
        this.wizardService.delete(wizardId);
        return new Result(true, StatusCode.SUCCESS, "Delete Success");
    }
}
