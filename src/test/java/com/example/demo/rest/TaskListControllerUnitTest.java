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

import com.example.demo.dto.TaskListDto;
import com.example.demo.persistence.domain.TaskList;

import com.example.demo.service.TaskListService;


@SpringBootTest


public class TaskListControllerUnitTest {
	
	@Autowired
	private TaskListController controller;
	
	@MockBean
	private TaskListService service;
	
	@Autowired
	private ModelMapper mapper;
	
	private TaskListDto maptoDto(TaskList taskList) {
		return this.mapper.map(taskList, TaskListDto.class);
	}
	
	private final TaskList Test_taskList_1 = new TaskList(1L,"dishes");
	private final TaskList Test_taskList_2 = new TaskList(1L,"hoover");
	private final TaskList Test_taskList_3 = new TaskList(1L,"laundry");
	private final TaskList Test_taskList_4 = new TaskList(1L,"sweep");
	
	
	private final List<TaskList> ListOfTaskList = List.of(Test_taskList_1,Test_taskList_2,Test_taskList_3,Test_taskList_4);
	
	//for create
	@Test
	void createTest() throws Exception {
		when(this.service.create(Test_taskList_1)).thenReturn(this.maptoDto(Test_taskList_1));
		
		assertThat(new ResponseEntity<TaskListDto>(this.maptoDto(Test_taskList_1), HttpStatus.CREATED)).isEqualTo(this.controller.create(Test_taskList_1));
		
		verify(this.service, atLeastOnce()).create(Test_taskList_1);
		
	}
	
	@Test 
	void readOneTest() throws Exception{
		when(this.service.readOne(Test_taskList_1.getId())).thenReturn(this.maptoDto(Test_taskList_1));
		
		assertThat(new ResponseEntity<TaskListDto>(this.maptoDto(Test_taskList_1), HttpStatus.OK)).isEqualTo(this.controller.readOne(Test_taskList_1.getId()));

		verify(this.service, atLeastOnce()).readOne(Test_taskList_1.getId());
	}
	
	@Test
	void readAllTest() throws Exception {
		
		List<TaskListDto> dtos = ListOfTaskList.stream().map(this::maptoDto).collect(Collectors.toList());
		
		when(this.service.readAll()).thenReturn(dtos);
		
		assertThat(this.controller.read())
		.isEqualTo(new ResponseEntity<>(dtos, HttpStatus.OK ));
		
		verify(this.service, atLeastOnce()).readAll();
		
	}
	@Test
	void updateTest() throws Exception {
		when(this.service.update(this.maptoDto(Test_taskList_1), Test_taskList_1.getId())).thenReturn(this.maptoDto(Test_taskList_1));
	    
		assertThat(new ResponseEntity<TaskListDto>(this.maptoDto(Test_taskList_1), HttpStatus.ACCEPTED))
		.isEqualTo(this.controller.update(Test_taskList_1.getId(), this.maptoDto(Test_taskList_1)));
		
		verify(this.service, atLeastOnce()).update(this.maptoDto(Test_taskList_1), Test_taskList_1.getId());
		

	}
	
	@Test
	void deleteTest() throws Exception {
		when(this.service.delete(Test_taskList_1.getId())).thenReturn(false);
		assertThat(this.controller.delete(Test_taskList_1.getId()))
		.isEqualTo(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
		verify(this.service, atLeastOnce()).delete(Test_taskList_1.getId());
		

	}
	
	
	@Test
	void deleteTest2() throws Exception {
		when(this.service.delete(Test_taskList_1.getId())).thenReturn(true);
		assertThat(this.controller.delete(Test_taskList_1.getId())).isEqualTo(new ResponseEntity<>(HttpStatus.NO_CONTENT));
		verify(this.service, atLeastOnce()).delete(Test_taskList_1.getId());
		

	}
}
	