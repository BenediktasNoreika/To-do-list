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

import com.example.demo.dto.TaskListDto;
import com.example.demo.persistence.domain.TaskList;
import com.example.demo.persistence.repo.TaskListRepo;

@SpringBootTest
@ActiveProfiles("dev")
class TaskListServiceUnittest {
	
	@Autowired
	private TaskListService service;

	@MockBean
	private TaskListRepo repo;

	@Autowired
	private ModelMapper mapper;
	
	
	private final TaskList TEST_TASK_1 = new TaskList(1L,"dishes");
	private final TaskList TEST_TASK_2 = new TaskList(1L,"hoover");
	private final TaskList TEST_TASK_3 = new TaskList(1L,"laundry");
	private final TaskList TEST_TASK_4 = new TaskList(1L,"sweep");
	private final List<TaskList> LIST_OF_TASKS = List.of(TEST_TASK_1, TEST_TASK_2, TEST_TASK_3, TEST_TASK_4);
	
	private TaskListDto mapToDTO(TaskList taskList) {
		return this.mapper.map(taskList, TaskListDto.class);		
	}
	
	
	@Test
	void createTest() throws Exception {
		TaskList newTaskList = new TaskList("dust");
		TaskList newTaskListFull = new TaskList(5L,"dust");
		when(this.repo.save(newTaskList)).thenReturn(newTaskListFull);
		assertThat(this.service.create(newTaskList))
				.isEqualTo(this.mapToDTO(newTaskListFull));
		verify(this.repo, atLeastOnce()).save(newTaskList);
	}
	

	@Test
	void readAllTest() throws Exception {
		when(this.repo.findAll()).thenReturn(LIST_OF_TASKS);
		
		List<TaskListDto> listOfTaskListDTOs = LIST_OF_TASKS.stream().map(this::mapToDTO)
				.collect(Collectors.toList());
		
		assertThat(this.service.readAll()).isEqualTo(listOfTaskListDTOs);
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