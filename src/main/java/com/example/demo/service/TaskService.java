package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.TaskDto;
import com.example.demo.exceptions.TaskNotFoundException;
import com.example.demo.persistence.domain.Task;
import com.example.demo.persistence.repo.TaskRepo;
import com.example.demo.util.SpringBeanUtil;

@Service
public class TaskService {

	// this is where our business logic will happen

//	this is also where CRUD logic will take place.

	// implements are crud functionality
	private TaskRepo repo;

	// makes object mapping easy by automatically determining how one object model
	// maps to another.
	private ModelMapper mapper;

	// we create our mapToDto

	private TaskDto mapToDTO(Task task) {
		return this.mapper.map(task, TaskDto.class);
	}

	@Autowired
	public TaskService(TaskRepo repo, ModelMapper mapper) {
		super();
		this.repo = repo;
		this.mapper = mapper;
	}

	// Create
	public TaskDto create(Task task) {
		return this.mapToDTO(this.repo.save(task));
	}

	// read all method
	public List<TaskDto> readAll() {
		return this.repo.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
		// stream - returns a sequential stream considering collection as its source
		// map - used to map each element to its corresponding result
		// :: - for each e.g. Random random = new Random();
		// random.ints().limit(10).forEach(System.out::println);
		// Collectors - used to return a list or string
	}

	// read one method
	public TaskDto readOne(Long id) {
		return this.mapToDTO(this.repo.findById(id).orElseThrow(TaskNotFoundException::new));
	}

	// update
	public TaskDto update(TaskDto taskDto, Long id) {
		// check if record exists
		Task toUpdate = this.repo.findById(id).orElseThrow(TaskNotFoundException::new);
		// set the record to update
		toUpdate.setName(taskDto.getName());
		// check update for any nulls
		SpringBeanUtil.mergeNotNull(taskDto, toUpdate);
		// retun the method from repo
		return this.mapToDTO(this.repo.save(toUpdate));

	}

	// what happenes when you try to merge null stuff?

	// Delete
	public boolean delete(Long id) {
		this.repo.deleteById(id);// true
		return !this.repo.existsById(id);// true
	}

	// Find by name
	public List<TaskDto> findByList(int id) {
		return this.repo.findByList(id).stream().map(this::mapToDTO).collect(Collectors.toList());
		
	}

}
