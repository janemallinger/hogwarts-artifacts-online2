package edu.tcu.cs.hogwartsartifactsonline2;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.tcu.cs.hogwartsartifactsonline2.artifact.artifact;
import edu.tcu.cs.hogwartsartifactsonline2.system.StatusCode;
import edu.tcu.cs.hogwartsartifactsonline2.wizard.WizardDto.WizardDto;
import edu.tcu.cs.hogwartsartifactsonline2.wizard.WizardService;
import edu.tcu.cs.hogwartsartifactsonline2.wizard.wizard;
import org.hamcrest.Matchers;
import org.hibernate.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.lang.reflect.Array.get;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class WizardControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    WizardService wizardService;

    List<wizard> wizards;

    @Value("${api.endpoint.base-url}")
    String baseUrl;

    @BeforeEach
    void setUp() throws Exception {
        artifact a1 = new artifact();
        a1.setId("1250808601744904191");
        a1.setName("Deluminator");
        a1.setDescription("It's a Deluminator");
        a1.setImageUrl("ImageUrl");


        artifact a2 = new artifact();
        a2.setId("1250808601744904192");
        a2.setName("Cloak");
        a2.setDescription("It's a Cloak");
        a2.setImageUrl("ImageUrl");


        artifact a3 = new artifact();
        a3.setId("1250808601744904193");
        a3.setName("Wand");
        a3.setDescription("It's a wand");
        a3.setImageUrl("ImageUrl");


        artifact a4 = new artifact();
        a4.setId("1250808601744904194");
        a4.setName("Map");
        a4.setDescription("It's a Map");
        a4.setImageUrl("ImageUrl");


        artifact a5 = new artifact();
        a5.setId("1250808601744904195");
        a5.setName("Sword");
        a5.setDescription("It's a Sword");
        a5.setImageUrl("ImageUrl");


        artifact a6 = new artifact();
        a6.setId("1250808601744904196");
        a6.setName("Stone");
        a6.setDescription("It's a Stone");
        a6.setImageUrl("ImageUrl");

        this.wizards = new ArrayList<>();

        wizard w1 = new wizard();
        w1.setId(1);
        w1.setName("Albus");
        w1.addArtifact(a1);
        w1.addArtifact(a3);
        this.wizards.add(w1);

        wizard w2 = new wizard();
        w2.setId(2);
        w2.setName("Harry");
        w2.addArtifact(a2);
        w2.addArtifact(a4);
        this.wizards.add(w2);

        wizard w3 = new wizard();
        w3.setId(3);
        w3.setName("Neville");
        w3.addArtifact(a5);
        this.wizards.add(w3);

    }

    @Test
    void testFindAllWizardsSuccess() throws Exception {
        //Given
        given(this.wizardService.findAll()).willReturn(this.wizards);

        //When and Then
        this.mockMvc.perform(get(this.baseUrl + "/wizards").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("find all success"))
                .andExpect(jsonPath("$.data", Matchers.hasSize(this.wizards.size())))
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[0].name").value("Albus"))
                .andExpect(jsonPath("$.data[1].id").value(2))
                .andExpect(jsonPath("$.data[1].name").value("Harry"));

    }

    @Test
    void testFindWizardByIdSuccess()  throws Exception {
        //Given
        given(this.wizardService.findById(1)).willReturn(this.wizards.get(0));

        //When and Then
        this.mockMvc.perform(get(this.baseUrl + "/wizards/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("find one success"))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.name").value("Albus"));
    }

    @Test
    void testFindWizardByIdNotFound()  throws Exception {
        //Given
        given(this.wizardService.findById(5)).willThrow(new ObjectNotFoundException(1));

        //When and Then
        this.mockMvc.perform(get(this.baseUrl + "/wizards/5").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("couldn't find"))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    void testAddWizardSuccess() throws Exception {
        WizardDto wizardDto = new WizardDto(null, "Hermione", 0);

        String json = this.objectMapper.writeValueAsString(wizardDto);

        wizard savedWizard = new wizard();
        savedWizard.setId(4);
        savedWizard.setName("Hermione");

        //Given
        given(this.wizardService.save(Mockito.any(wizard.class))).willReturn(savedWizard);

        //When and Then
        this.mockMvc.perform(get(this.baseUrl + "/wizards/5").accept(MediaType.APPLICATION_JSON).content(json).accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Added wizard"))
                .andExpect(jsonPath("$.data.id").isNotEmpty())
                .andExpect(jsonPath("$.data.name").value(savedWizard.getName()));
    }
    @Test
    void testUpdateWizardSuccess() throws Exception {
        WizardDto wizardDto = new WizardDto(null, "updated wizard name", 0);

        wizard updatedWizard = new wizard();
        updatedWizard.setId(1);
        updatedWizard.setName("updated wizard name");

        String json = this.objectMapper.writeValueAsString(updatedWizard);

        //Given
        given(this.wizardService.update(eq(1), Mockito.any(wizard.class))).willReturn(updatedWizard);

        //When and Then
        this.mockMvc.perform(put(this.baseUrl + "/wizards/1").contentType(MediaType.APPLICATION_JSON).content(json).accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("updated wizard"))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.name").value(updatedWizard.getName()));
    }

    @Test
    void testUpdatedWizardErrorWithNonExistentId() throws Exception {
        //Given
        given(this.wizardService.update(eq(5), Mockito.any(wizard.class))).willThrow(new ObjectNotFoundException("wizard", 5));

        WizardDto wizardDto = new WizardDto(5, "updated wizard name", 0);

        String json = this.objectMapper.writeValueAsString(wizardDto);

        //When and Then
        this.mockMvc.perform(put(this.baseUrl + "/wizards/5").contentType(MediaType.APPLICATION_JSON).content(json).accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("couldn't find wizard"))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    void testDeleteWizardSuccess() throws Exception {
        //Given
        doNothing().when(this.wizardService).delete(3);

        //When and Then
        this.mockMvc.perform(delete(this.baseUrl + "/wizards/3").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("delete success"))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    void testDeleteWizardErrorWithNonExistentId() throws Exception {
        //Given
        doThrow(new ObjectNotFoundException("wizard", 5)).when(this.wizardService).delete(5);

        //When and Then
        this.mockMvc.perform(delete(this.baseUrl + "/wizards/5").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("could not find wizard with Id 5"))
                .andExpect(jsonPath("$.data").isEmpty());
    }
    @Test
    void testAssignArtifactSuccess() {
        //Given
        doNothing().when(this.wizardService).assignArtifact(2, "1250808601744904192");


        //When and then
        this.mockMvc.perform(put(this.baseUrl + "/wizards/2/artifacts/1250808601744904192").accept(PageAttributes.MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Assign Success"))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    void testAssignArtifactErrorWithNonExistenWizardId() throws Exception {
        //Given
        doThrow(new ObjectNotFoundException("wizard", 5)).when(this.wizardService).assignArtifact(5, "1250808601744904192");


        //When and then
        this.mockMvc.perform(put(this.baseUrl + "/wizards/5/artifacts/1250808601744904192").accept(PageAttributes.MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("could not find"))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    void testAssignArtifactErrorWithNonExistenArtifactId() throws Exception {
        //Given
        doThrow(new ObjectNotFoundException(Optional.of("artifact"), "1250808601744904199")).when(this.wizardService).assignArtifact(5, "1250808601744904199");


        //When and then
        this.mockMvc.perform(put(this.baseUrl + "/wizards/2/artifacts/1250808601744904199").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("could not find"))
                .andExpect(jsonPath("$.data").isEmpty());
    }

}
