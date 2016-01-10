package com.fdmgroup.java_week1_assessment;

import java.util.Map;

public class GroupController {

	private DatabaseWriter writer;
	private DatabaseReader reader;
	
	public GroupController(DatabaseWriter writer, DatabaseReader reader) {
		this.writer = writer;
		this.reader = reader;
	}
	
	public Map<String, Trainee> getAllTrainees() {
		return reader.readGroup();
	}
	
	public void addTrainee(Trainee trainee) {
		writer.addTrainee(trainee);
	}
	
	public void removeTraineeByUsername(String username) {
		writer.deleteTraineeByUsername(username);
	}
}
