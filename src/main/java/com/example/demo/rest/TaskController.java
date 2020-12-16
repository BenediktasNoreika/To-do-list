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

import com.example.demo.dto.TaskDto;
import com.example.demo.persistence.domain.Task;
import com.example.demo.service.TaskService;

@RestController
@CrossOrigin
@RequestMapping("/task") // this is to further define the path
public class TaskController {

	private TaskService service;

	@Autowired
	public TaskController(TaskService service) {
		super();
		this.service = service;
	}

//	@GetMapping("/hello") // This is the mapping i want - Get me something
//	public String hello() {
//		return "hello from task";
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
//	

	// Create method
	@PostMapping("/create")
	public ResponseEntity<TaskDto> create(@RequestBody Task task) {
		TaskDto created = this.service.create(task);
		return new ResponseEntity<>(created, HttpStatus.CREATED);
		// http status code - 201 (created)

	}

	// read all method
	@GetMapping("/read")
	public ResponseEntity<List<TaskDto>> read() {
		return ResponseEntity.ok(this.service.readAll());
		// ok - 200
	}

	// read one
	@GetMapping("/read/{id}")
	public ResponseEntity<TaskDto> readOne(@PathVariable Long id) {
		return ResponseEntity.ok(this.service.readOne(id));
	}

	// update
	@PutMapping("/update/{id}")
	public ResponseEntity<TaskDto> update(@PathVariable Long id, @RequestBody TaskDto taskDto) {
		return new ResponseEntity<>(this.service.update(taskDto, id), HttpStatus.ACCEPTED);
	}

	// Delete one
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<TaskDto> delete(@PathVariable Long id) {
		return this.service.delete(id) ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
				// no_content - if deleted successfully then should return nothing
				: new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
		// if the record isnt found!
	}

	@GetMapping("findByList/{id}")
	public ResponseEntity<List<TaskDto>> findByList(@PathVariable int id) {
		return ResponseEntity.ok(this.service.findByList(id));
	}
}
