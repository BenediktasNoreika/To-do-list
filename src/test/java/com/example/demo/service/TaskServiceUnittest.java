package com.example.demo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import com.example.demo.dto.TaskDto;
import com.example.demo.persistence.domain.Task;
import com.example.demo.persistence.repo.TaskRepo;

@SpringBootTest
@ActiveProfiles("dev")
class TaskServiceUnittest {
	
	@Autowired
	private TaskService service;

	@MockBean
	private TaskRepo repo;

	@Autowired
	private ModelMapper mapper;
	
	
	private final Task TEST_TASK_1 = new Task(1L,"dishes", "Tomorrow", false );
	private final Task TEST_TASK_2 = new Task(1L,"hoover", "Today", false );
	private final Task TEST_TASK_3 = new Task(1L,"laundry", "Friday", false );
	private final Task TEST_TASK_4 = new Task(1L,"sweep", "monday", false );
	
	private final List<Task> LIST_OF_TASKS = List.of(TEST_TASK_1, TEST_TASK_2, TEST_TASK_3, TEST_TASK_4);
	
	private TaskDto mapToDTO(Task task) {
		return this.mapper.map(task, TaskDto.class);		
	}
	
	
	@Test
	void createTest() throws Exception {
		Task newTask = new Task("dust","today",false);
		Task newTaskFull = new Task(5L,"dust","today",false);
		when(this.repo.save(newTask)).thenReturn(newTaskFull);
		assertThat(this.service.create(newTask))
				.isEqualTo(this.mapToDTO(newTaskFull));
		verify(this.repo, atLeastOnce()).save(newTask);
	}
	

	@Test
	void readAllTest() throws Exception {
		when(this.repo.findAll()).thenReturn(LIST_OF_TASKS);
		
		List<TaskDto> listOfTaskDTOs = LIST_OF_TASKS.stream().map(this::mapToDTO)
				.collect(Collectors.toList());
		
		assertThat(this.service.readAll()).isEqualTo(listOfTaskDTOs);
		verify(this.repo, atLeastOnce()).findAll();
	}

	@Test
	void readOneTest() throws Exception {
		Long id = 1L;
		when(this.repo.findById(id)).thenReturn(Optional.of(TEST_TASK_1));
		assertThat(this.service.readOne(id))
				.isEqualTo(this.mapToDTO(TEST_TASK_1));
		verify(this.repo, atLeastOnce()).findById(id);
	}
	
	
	
	@Test
	void deleteSuccessTest() throws Exception {
		Long id = 1L;
		when(this.repo.existsById(id)).thenReturn(false);
		assertThat(this.service.delete(id)).isEqualTo(true);
		verify(this.repo, atLeastOnce()).existsById(id);
	}
	
	
	@Test
	void deleteFailureTest() throws Exception {
		Long id = 1L;
		when(this.repo.existsById(id)).thenReturn(true);
		assertThat(this.service.delete(id)).isEqualTo(false);
		verify(this.repo, atLeastOnce()).existsById(id);
	}

}