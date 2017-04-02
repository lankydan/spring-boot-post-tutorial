package lankydan.tutorial.springboot;

import com.fasterxml.jackson.databind.ObjectMapper;
import lankydan.tutorial.springboot.dto.PersonDTO;
import lankydan.tutorial.springboot.repository.PersonRepository;
import lankydan.tutorial.springboot.service.PersonService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(PersonRestController.class)
public class PersonRestControllerTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @MockBean private PersonService personService;

  @MockBean private PersonRepository personRepository;

  private JacksonTester<PersonDTO> jsonTester;

  private PersonDTO personDTO;

  @Before
  public void setup() {
    JacksonTester.initFields(this, objectMapper);
    personDTO = new PersonDTO();
  }

  @Test
  public void persistPerson_IsValid_PersonPersisted() throws Exception {
    final String personDTOJson = jsonTester.write(personDTO).getJson();
    given(personService.isValid(any(PersonDTO.class))).willReturn(true);
    mockMvc
        .perform(post("/persistPerson").content(personDTOJson).contentType(APPLICATION_JSON_UTF8))
        .andExpect(status().isCreated());
    verify(personRepository).persist(any(PersonDTO.class));
  }

  @Test
  public void persistPerson_IsNotValid_PersonNotPersisted() throws Exception {
    final String personDTOJson = jsonTester.write(personDTO).getJson();
    given(personService.isValid(any(PersonDTO.class))).willReturn(false);
    mockMvc
        .perform(post("/persistPerson").content(personDTOJson).contentType(APPLICATION_JSON_UTF8))
        .andExpect(status().isIAmATeapot());
    verify(personRepository, times(0)).persist(any(PersonDTO.class));
  }
}
