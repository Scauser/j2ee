package com.fdmgroup.java_week1_assessment;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class GroupControllerTest {

	private GroupController traineeGroupController;
	private Trainee mockTrainee;
	private DatabaseWriter mockWriter;
	private DatabaseReader mockReader;
	private Map<String, Trainee> mockMap;

	@Before
	public void setup() {
		// before start testing, create mock instances of the mock variables
		mockWriter = mock(DatabaseWriter.class);
		mockReader = mock(DatabaseReader.class);
		mockMap = mock(Map.class);
		mockTrainee = mock(Trainee.class);
		// set for method readGroup() as return value mockMap variable
		when(mockReader.readGroup()).thenReturn(mockMap);
		// create new instance of the class GroupController
		traineeGroupController = new GroupController(mockWriter, mockReader);
	}

	@Test
	public void test_GetAllTraineesMethod_CallsReadGroupMethodOfInjectedDatabaseReader_WhenCalled(){
		// act
		// call method getAllTrainees, which in turn calls method readGroup DatabaseReader interface
		// look at GroupController 16 line
		traineeGroupController.getAllTrainees();
		// assert
		// verify that method readGroup DatabaseReader interface was called only 1 time
		verify(mockReader, times(1)).readGroup();
	}

	@Test
	public void test_GetAllTraineesMethod_ReturnsMapGivenToItByReadGroupMethodOfInjectedReader_WhenCalled(){
		// act
		Map<String, Trainee> allTrainees = traineeGroupController.getAllTrainees();
		// assert
		// check that mockMap is equal to allTrainees variable, 
		// look at 32 line, mockMap and allTrainees are equal, because 
		// allTrainees comes from getAllTrainees() GroupController, which in turn 
		// calls readGroup() DatabaseReader, that returns mockMap, 
		// because as method return value we defined mockMap, look at 32 line. 
		assertEquals(mockMap, allTrainees);
	}

	@Test
	public void test_GetAllTraineesMethod_ReturnsAnEmptyMap_IfNoTraineesAreInGroup() {
		// simulate size method of the variable mockMap, that will return 0
		when(mockMap.size()).thenReturn(0);
		// act
		// because mockMap and allTrainees is one map, 
		// plz look at previous test method for more detail 
		Map<String, Trainee> allTrainees = traineeGroupController.getAllTrainees();
		// assert
		// allTrains.size() will return 0, because (64 line) mockMap.size() is 0;
		assertEquals(0, allTrainees.size());
	}

	@Test
	public void test_GetAllTrainees_ReturnsMapOfSizeOne_IfOneTraineeIsInTheGroup(){
		// for more details plz look at previous method comments
		// mock
		when(mockMap.size()).thenReturn(1);
		// act
		Map<String, Trainee> allTrainees = traineeGroupController.getAllTrainees();
		// assert
		assertEquals(1, allTrainees.size());
	}	

	@Test
	public void test_AddTraineeMethod_CallsAddTraineeMethodOfDatabaseWriter_WhenCalled(){
		// act
		traineeGroupController.addTrainee(mockTrainee);
		// assert
		// verify that the method addTrainee of the DatabaseWriter interface was 
		// called only once with any Trainee class instance
		verify(mockWriter, times(1)).addTrainee(any(Trainee.class));
	}

	@Test
	public void test_AddTraineeMethod_CallsAddTraineeMethodOfDatabaseWriterPassingTraineeObjectDefinedAsInput_WhenCalled(){
		// act
		traineeGroupController.addTrainee(mockTrainee);
		// assert
		// verify that the method addTrainee of the DatabaseWriter interface was 
		// called only once with mockTrainee variable (it is work only for mockTrainee variable)
		verify(mockWriter, times(1)).addTrainee(mockTrainee);
	}

	@Test
	public void test_RemoveTraineeMethodByUsername_CallsDeleteTraineeByUsernameMethodOfInjectedWriter_WhenCalled(){
		// act
		traineeGroupController.removeTraineeByUsername("");
		// assert
		// verify that the method deleteTraineeByUsername of the DatabaseWriter interface was 
		// called only once with any string as parameter
		verify(mockWriter, times(1)).deleteTraineeByUsername(anyString());
	}

	@Test
	public void test_RemoveTraineeMethodByUsername_CallsDeleteTraineeByUsernameMethodOfInjectedWriterPassingUsernameDefinedAsInput_WhenCalled(){
		// act
		String username = "john.smith";
		traineeGroupController.removeTraineeByUsername(username);
		// assert
		// verify that the method deleteTraineeByUsername of the DatabaseWriter interface was 
		// called only once with username variable (it is work only for username variable)
		verify(mockWriter, times(1)).deleteTraineeByUsername(username);
	}
}
