/*
 * package com.example.demo.rest;
 * 
 * import static
 * org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
 * import static
 * org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
 * import static
 * org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
 * 
 * import java.util.List;
 * 
 * import org.junit.jupiter.api.Test; import org.modelmapper.ModelMapper; import
 * org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
 * import org.springframework.boot.test.context.SpringBootTest; import
 * org.springframework.http.MediaType; import
 * org.springframework.test.context.ActiveProfiles; import
 * org.springframework.test.context.jdbc.Sql; import
 * org.springframework.test.context.jdbc.Sql.ExecutionPhase; import
 * org.springframework.test.web.servlet.MockMvc; import
 * org.springframework.test.web.servlet.RequestBuilder; import
 * org.springframework.test.web.servlet.ResultMatcher;
 * 
 * import com.example.demo.dto.TaskDto; import
 * com.example.demo.persistence.domain.Task; import
 * com.fasterxml.jackson.databind.ObjectMapper;
 * 
 * @SpringBootTest
 * 
 * @AutoConfigureMockMvc
 * 
 * @Sql(scripts = { "classpath:task-schema.sql", "classpath:task-data.sql" },
 * executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
 * 
 * @ActiveProfiles(profiles = "dev") public class TaskControllerintergrationTest
 * {
 * 
 * @Autowired private MockMvc mvc;
 * 
 * @Autowired private ObjectMapper jsonifier;
 * 
 * @Autowired private ModelMapper mapper;
 * 
 * private TaskDto mapToDTO(Task task) { return this.mapper.map(task,
 * TaskDto.class); }
 * 
 * 
 * private final Task TEST_TASK_1 = new Task(1L,"dishes", "Tomorrow", false );
 * private final Task TEST_TASK_2 = new Task(1L,"hoover", "Today", false );
 * private final Task Test_task_3 = new Task(1L,"laundry", "Friday", false );
 * private final Task Test_task_4 = new Task(1L,"sweep", "monday", false );
 * 
 * 
 * private final List<Task> LISTOFTASK = List.of(TEST_TASK_1, TEST_TASK_2,
 * Test_task_3, Test_task_4);
 * 
 * private final String URI = "/task";
 * 
 * // Create test
 * 
 * @Test void createTest() throws Exception { TaskDto testDTO = mapToDTO(new
 * Task("dust", "today", false)); String testDTOAsJSON =
 * this.jsonifier.writeValueAsString(testDTO);
 * 
 * RequestBuilder request = post(URI +
 * "/create").contentType(MediaType.APPLICATION_JSON).content(testDTOAsJSON);
 * 
 * ResultMatcher checkStatus = status().isCreated();
 * 
 * TaskDto testSavedDTO = mapToDTO(new Task("dust", "today", false));
 * testSavedDTO.setId(5L); String testSavedDTOAsJSON =
 * this.jsonifier.writeValueAsString(testSavedDTO);
 * 
 * ResultMatcher checkBody = content().json(testSavedDTOAsJSON);
 * 
 * this.mvc.perform(request).andExpect(checkStatus).andExpect(checkBody);
 * 
 * 
 * }
 * 
 * }
 */