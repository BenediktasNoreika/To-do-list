package com.example.demo.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TaskListDto {

	public Long id;
	public String name;

	private List<TaskDto> task = new ArrayList<>();
}
