package testngImplementation;

import java.util.Map;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import genericLibraries.BaseClass;
import genericLibraries.IConstantPath;
//*******************
public class CreateEventTest extends BaseClass{

	@Test
	public void createEventTest() {
		SoftAssert soft = new SoftAssert();
		
		Map<String, String> map = excel.readDataFromExcel("Create New Event", "EventsTestData");
		home.selectFromQuickCreateDropdown(web, map.get("Quick Create"));
		soft.assertTrue(createToDo.getPageHeader().contains("Create To Do"));
		
		String subject = map.get("Subject") + javaUtil.generateRandomNumber(100);
		createToDo.setSubject(subject);
		createToDo.selectStartDate(web, javaUtil, map.get("Start Date"));
		createToDo.setDueDate(map.get("Due Date"));
		createToDo.clickSaveButton();
		
		soft.assertTrue(eventInfo.getPageHeader().contains(subject));
		if (eventInfo.getPageHeader().contains(subject)) 
			excel.setDataToExcel("Create New Event", "Pass", IConstantPath.EXCEL_FILE_PATH, "EventsTestData");
		else 
			excel.setDataToExcel("Create New Event", "Fail", IConstantPath.EXCEL_FILE_PATH, "EventsTestData");
		
		soft.assertAll();
	}

}
