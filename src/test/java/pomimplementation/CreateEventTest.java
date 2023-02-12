package pomimplementation;

import java.util.Map;

import org.openqa.selenium.WebDriver;

import genericLibraries.ExcelUtility;
import genericLibraries.IConstantPath;
import genericLibraries.JavaUtility;
import genericLibraries.PropertiesFileUtility;
import genericLibraries.WebDriverUtility;
import pompages.CreateToDoPage;
import pompages.EventInfoPage;
import pompages.HomePage;
import pompages.LoginPage;

public class CreateEventTest {

	public static void main(String[] args) {
		ExcelUtility excel = new ExcelUtility();
		PropertiesFileUtility property = new PropertiesFileUtility();
		JavaUtility javaUtil = new JavaUtility();
		WebDriverUtility web = new WebDriverUtility();

		property.propertyFileInitialization(IConstantPath.PROPERTY_FILE_PATH);
		excel.excelInitialization(IConstantPath.EXCEL_FILE_PATH);

		long time = Long.parseLong(property.fetchProperty("timeouts"));
		WebDriver driver = web.openApplication(property.fetchProperty("browser"), property.fetchProperty("url"), time);

		LoginPage login = new LoginPage(driver);
		HomePage home = new HomePage(driver);
		CreateToDoPage createToDo = new CreateToDoPage(driver);
		EventInfoPage eventInfo = new EventInfoPage(driver);

		if (driver.getTitle().contains("vtiger"))
			System.out.println("Login page displayed");
		else
			System.out.println("Login page not found");

		login.loginToApp(property.fetchProperty("username"), property.fetchProperty("password"));

		if (driver.getTitle().contains("Home"))
			System.out.println("Home page is displayed");
		else
			System.out.println("Home page not found");

		Map<String, String> map = excel.readDataFromExcel("Create New Event", "EventsTestData");
		home.selectFromQuickCreateDropdown(web, map.get("Quick Create"));

		if (createToDo.getPageHeader().contains("Create To Do"))
			System.out.println("Create To Do is displayed");
		else
			System.out.println("Create To Do not found");

		String subject = map.get("Subject") + javaUtil.generateRandomNumber(100);
		createToDo.setSubject(subject);
		createToDo.selectStartDate(web, javaUtil, map.get("Start Date"));
		createToDo.setDueDate(map.get("Due Date"));
		createToDo.clickSaveButton();
		
		if (eventInfo.getPageHeader().contains(subject)) {
			System.out.println("Pass");
			excel.setDataToExcel("Create New Event", "Pass", IConstantPath.EXCEL_FILE_PATH, "EventsTestData");
		} else {
			System.out.println("Fail");
			excel.setDataToExcel("Create New Event", "Fail", IConstantPath.EXCEL_FILE_PATH, "EventsTestData");
		}

		home.signOutOfApp(web);
		web.closeWindows();
		excel.closeWorkbook();

	}

}
