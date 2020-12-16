package com.example.demo.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TaskDto {

	private Long id;
	private String name;
	private String dueDate;
	private Boolean completed;
	

}
