package com.example.demo.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.demo.dto.TaskDto;
import com.example.demo.persistence.domain.Task;

import com.example.demo.service.TaskService;


@SpringBootTest


public class TaskControllerUnitTest {
	
	@Autowired
	private TaskController controller;
	
	@MockBean
	private TaskService service;
	
	@Autowired
	private ModelMapper mapper;
	
	private TaskDto maptoDto(Task task) {
		return this.mapper.map(task, TaskDto.class);
	}
	
	private final Task Test_task_1 = new Task(1L,"dishes", "Tomorrow", false );
	private final Task Test_task_2 = new Task(1L,"hoover", "Today", false );
	private final Task Test_task_3 = new Task(1L,"laundry", "Friday", false );
	private final Task Test_task_4 = new Task(1L,"sweep", "monday", false );
	
	
	private final List<Task> ListOfTask = List.of(Test_task_1,Test_task_2,Test_task_3,Test_task_4);
	
	//for create
	@Test
	void createTest() throws Exception {
		when(this.service.create(Test_task_1)).thenReturn(this.maptoDto(Test_task_1));
		
		assertThat(new ResponseEntity<TaskDto>(this.maptoDto(Test_task_1), HttpStatus.CREATED)).isEqualTo(this.controller.create(Test_task_1));
		
		verify(this.service, atLeastOnce()).create(Test_task_1);
		
	}
	
	@Test 
	void readOneTest() throws Exception{
		when(this.service.readOne(Test_task_1.getId())).thenReturn(this.maptoDto(Test_task_1));
		
		assertThat(new ResponseEntity<TaskDto>(this.maptoDto(Test_task_1), HttpStatus.OK)).isEqualTo(this.controller.readOne(Test_task_1.getId()));

		verify(this.service, atLeastOnce()).readOne(Test_task_1.getId());
	}
	
	@Test
	void readAllTest() throws Exception {
		
		List<TaskDto> dtos = ListOfTask.stream().map(this::maptoDto).collect(Collectors.toList());
		
		when(this.service.readAll()).thenReturn(dtos);
		
		assertThat(this.controller.read())
		.isEqualTo(new ResponseEntity<>(dtos, HttpStatus.OK ));
		
		verify(this.service, atLeastOnce()).readAll();
		
	}
	@Test
	void updateTest() throws Exception {
		when(this.service.update(this.maptoDto(Test_task_1), Test_task_1.getId())).thenReturn(this.maptoDto(Test_task_1));
	    
		assertThat(new ResponseEntity<TaskDto>(this.maptoDto(Test_task_1), HttpStatus.ACCEPTED))
		.isEqualTo(this.controller.update(Test_task_1.getId(), this.maptoDto(Test_task_1)));
		
		verify(this.service, atLeastOnce()).update(this.maptoDto(Test_task_1), Test_task_1.getId());
		

	}
	
	@Test
	void deleteTest() throws Exception {
		when(this.service.delete(Test_task_1.getId())).thenReturn(false);
		assertThat(this.controller.delete(Test_task_1.getId()))
		.isEqualTo(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
		verify(this.service, atLeastOnce()).delete(Test_task_1.getId());
		

	}
	
	
	@Test
	void deleteTest2() throws Exception {
		when(this.service.delete(Test_task_1.getId())).thenReturn(true);
		assertThat(this.controller.delete(Test_task_1.getId())).isEqualTo(new ResponseEntity<>(HttpStatus.NO_CONTENT));
		verify(this.service, atLeastOnce()).delete(Test_task_1.getId());
		

	}
	
//@Test
	//void findByList() throws Exception {
	//	List<TaskDto> dtos = ListOfTask.stream().map(this::maptoDto).collect(Collectors.toList());

	//	when(this.service.findByList(Test_task_1.getTaskList().getId())).thenReturn(dtos);
		
	//	assertThat(this.controller.findByList(Test_task_1.getTaskList().getId()))
	//	.isEqualTo(new ResponseEntity<List<TaskDto>>(dtos,HttpStatus.OK));
		
	//	verify(this.service, atLeastOnce()).findByList(Test_task_1.getTaskList().getId());
	//}
	
	
	
	
	
	
}
