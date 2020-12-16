package com.example.demo.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.TaskListDto;
import com.example.demo.persistence.domain.TaskList;
import com.example.demo.service.TaskListService;

@RestController
@CrossOrigin
@RequestMapping("/taskList") // this is to further define the path
public class TaskListController {

	private TaskListService service;

	@Autowired
	public TaskListController(TaskListService service) {
		super();
		this.service = service;
	}
//
//	@GetMapping("/hello") // This is the mapping i want - Get me something
//	public String hello() {
//		return "hello from car";
//	}
//
//	// pathVariable - this is the values in the URL "?id=2"
//	// @RequestBody -- put/post - POST MEHTOD body of that request
//
//	// pattern matching here url - /hello/<valuehere>
//	@GetMapping("/hello/{id}")
//	public String helloName(@PathVariable String id) {
//		return "Hello " + id;
//	}
//
//	// @GetMapping("/num1/{num1}/num2/{num2}/num3/{num3}")
//	@GetMapping("/num1/{num1}/num2/{num2}")
//	public int addTwo(@PathVariable("num1") int number1, @PathVariable("num2") int number2) {
//		return number1 + number2;
//	}

	// Create method
	@PostMapping("/create")
	public ResponseEntity<TaskListDto> create(@RequestBody TaskList taskList) {
		TaskListDto created = this.service.create(taskList);
		return new ResponseEntity<>(created, HttpStatus.CREATED);
		// http status code - 201 (created)

	}

	// read all method
	@GetMapping("/read")
	public ResponseEntity<List<TaskListDto>> read() {
		return ResponseEntity.ok(this.service.readAll());
		// ok - 200
	}

	// read one
	@GetMapping("/read/{id}")
	public ResponseEntity<TaskListDto> readOne(@PathVariable Long id) {
		return ResponseEntity.ok(this.service.readOne(id));
	}

	// update
	@PutMapping("/update/{id}")
	public ResponseEntity<TaskListDto> update(@PathVariable Long id, @RequestBody TaskListDto taskListDto) {
		return new ResponseEntity<>(this.service.update(taskListDto, id), HttpStatus.ACCEPTED);
	}

	// Delete one
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<TaskListDto> delete(@PathVariable Long id) {
		return this.service.delete(id) ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
				// no_content - if deleted successfully then should return nothing
				: new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
		// if the record isnt found!
	}
}
