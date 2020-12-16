package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.TaskListDto;
import com.example.demo.exceptions.TaskListNotFoundException;
import com.example.demo.persistence.domain.TaskList;
import com.example.demo.persistence.repo.TaskListRepo;
import com.example.demo.util.SpringBeanUtil;

@Service
public class TaskListService {

	// this is where our business logic will happen

//	this is also where CRUD logic will take place.

	// implements are crud functionality
	private TaskListRepo repo;

	// makes object mapping easy by automatically determining how one object model
	// maps to another.
	private ModelMapper mapper;

	// we create our mapToDto

	private TaskListDto mapToDTO(TaskList taskList) {
		return this.mapper.map(taskList, TaskListDto.class);
	}

	@Autowired
	public TaskListService(TaskListRepo repo, ModelMapper mapper) {
		super();
		this.repo = repo;
		this.mapper = mapper;
	}

	// Create
	public TaskListDto create(TaskList taskList) {
		return this.mapToDTO(this.repo.save(taskList));
	}

	// read all method
	public List<TaskListDto> readAll() {
		return this.repo.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
		// stream - returns a sequential stream considering collection as its source
		// map - used to map each element to its corresponding result
		// :: - for each e.g. Random random = new Random();
		// random.ints().limit(10).forEach(System.out::println);
		// Collectors - used to return a list or string
	}

	// read one method
	public TaskListDto readOne(Long id) {
		return this.mapToDTO(this.repo.findById(id).orElseThrow(TaskListNotFoundException::new));
	}

	// update
	public TaskListDto update(TaskListDto taskListDto, Long id) {
		// check if record exists
		TaskList toUpdate = this.repo.findById(id).orElseThrow(TaskListNotFoundException::new);
		// set the record to update
		toUpdate.setName(taskListDto.getName());
		// check update for any nulls
		SpringBeanUtil.mergeNotNull(taskListDto, toUpdate);
		// retun the method from repo
		return this.mapToDTO(this.repo.save(toUpdate));

	}

	// what happenes when you try to merge null stuff?

	// Delete
	public boolean delete(Long id) {
		this.repo.deleteById(id);// true
		return !this.repo.existsById(id);// true
	}

}
