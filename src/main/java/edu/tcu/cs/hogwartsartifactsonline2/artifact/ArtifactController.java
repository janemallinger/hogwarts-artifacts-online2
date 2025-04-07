package edu.tcu.cs.hogwartsartifactsonline2.artifact;

import com.fasterxml.jackson.core.JsonProcessingException;
import edu.tcu.cs.hogwartsartifactsonline2.artifact.converter.ArtifactDtoToArtifactConverter;
import edu.tcu.cs.hogwartsartifactsonline2.artifact.converter.ArtifactToArtifactDtoConverter;
import edu.tcu.cs.hogwartsartifactsonline2.artifact.dto.ArtifactDto;
//import edu.tcu.cs.hogwartsartifactsonline2.client.imagestorage.ImageStorageClient;
import edu.tcu.cs.hogwartsartifactsonline2.system.Result;
import edu.tcu.cs.hogwartsartifactsonline2.system.StatusCode;
//import io.micrometer.core.instrument.MeterRegistry;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${api.endpoint.base-url}/artifacts")
public class ArtifactController {

    private final ArtifactService artifactService;

    private final ArtifactToArtifactDtoConverter artifactToArtifactDtoConverter;

    private final ArtifactDtoToArtifactConverter artifactDtoToArtifactConverter;

    public ArtifactController(ArtifactService artifactService, ArtifactToArtifactDtoConverter artifactToArtifactDtoConverter, ArtifactDtoToArtifactConverter artifactDtoToArtifactConverter) {
        this.artifactService = artifactService;
        this.artifactToArtifactDtoConverter = artifactToArtifactDtoConverter;
        this.artifactDtoToArtifactConverter = artifactDtoToArtifactConverter;
    }

    @GetMapping("/{artifactId}")
    public Result findArtifactById(@PathVariable String artifactId) {
        artifact foundArtifact = this.artifactService.findById(artifactId);
        ArtifactDto artifactDto = this.artifactToArtifactDtoConverter.convert(foundArtifact);
        return new Result(true, StatusCode.SUCCESS, "Find One Success", artifactDto);
    }

    @GetMapping()
    public Result findArtifacts() {
        List<artifact> foundArtifacts = this.artifactService.findAll();
        List<ArtifactDto> artifactDtos = foundArtifacts.stream()
                .map(this.artifactToArtifactDtoConverter::convert)
                .collect(Collectors.toList());
        return new Result(true, StatusCode.SUCCESS, "Find all success", artifactDtos);
    }

    @PostMapping()
    public Result addArtifact(@Validated @RequestBody ArtifactDto artifactDto) {
        artifact newArtifact = this.artifactDtoToArtifactConverter.convert(artifactDto);
        artifact savedArtifact = this.artifactService.save(newArtifact);
        ArtifactDto savedArtifactDto = this.artifactToArtifactDtoConverter.convert(savedArtifact);

        return new Result(true, StatusCode.SUCCESS, "Add success", savedArtifactDto);
    }

    @PutMapping("/{artifactId")
    public Result updateArtifact(@PathVariable String artifactId, @Valid @RequestBody ArtifactDto artifactDto) {
        artifact update = this.artifactDtoToArtifactConverter.convert(artifactDto);
        artifact updatedArtifact = this.artifactService.update(artifactId, update);
        ArtifactDto updatedArtifactDto = this.artifactToArtifactDtoConverter.convert(updatedArtifact);

        return new Result(true, StatusCode.SUCCESS, "Update Success", updatedArtifactDto);

    }

    @DeleteMapping("/{artifactsId}")
    public Result deleteArtifact(@PathVariable String artifactId) {
        this.artifactService.delete(artifactId);
        return new Result(true, StatusCode.SUCCESS, "Delete Success");
    }


}
