package pomimplementation;

import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import genericLibraries.ExcelUtility;
import genericLibraries.IConstantPath;
import genericLibraries.JavaUtility;
import genericLibraries.PropertiesFileUtility;
import genericLibraries.WebDriverUtility;

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

		if(driver.getTitle().contains("vtiger"))
			System.out.println("Login page displayed");
		else
			System.out.println("Login page not found");
		
		driver.findElement(By.name("user_name")).sendKeys(property.fetchProperty("username"));
		driver.findElement(By.name("user_password")).sendKeys(property.fetchProperty("password"));
		driver.findElement(By.id("submitButton")).submit();
		
		if (driver.getTitle().contains("Home"))
			System.out.println("Home page is displayed");
		else
			System.out.println("Home page not found");

		Map<String, String> map = excel.readDataFromExcel("Create New Event", "EventsTestData");
		WebElement quickCreate = driver.findElement(By.id("qccombo"));
		web.dropdown(map.get("Quick Create"), quickCreate);
		String header = driver.findElement(By.xpath("//td[@class='mailSubHeader']/b")).getText();
		if (header.contains("Create To Do"))
			System.out.println("Create To Do is displayed");
		else
			System.out.println("Create To Do not found");

		String subject = map.get("Subject") + javaUtil.generateRandomNumber(100);
		driver.findElement(By.name("subject")).sendKeys(subject);
		driver.findElement(By.id("jscal_trigger_date_start")).click();

		String currentMonthYear = driver
				.findElement(By
						.xpath("//div[@class='calendar' and contains(@style,'block')]/descendant::td[@class='title']"))
				.getText();

		String[] str = currentMonthYear.split(",");

		int currentMonthInNum = javaUtil.convertStringMonthToInteger(str[0]);
		int currentYearInNum = Integer.parseInt(str[1].trim());
		String requiredStartDate = map.get("Start Date");
		String[] date = requiredStartDate.split("-");
		int requiredYear = Integer.parseInt(date[0]);
		int requiredMonth = Integer.parseInt(date[1]);
		int requiredDate = Integer.parseInt(date[2]);

		while (currentYearInNum < requiredYear) {
			driver.findElement(By.xpath("//div[@class='calendar' and contains(@style,'block')]/descendant::td[.='»']"))
					.click();
			currentMonthYear = driver
					.findElement(By.xpath(
							"//div[@class='calendar' and contains(@style,'block')]/descendant::td[@class='title']"))
					.getText();
			str = currentMonthYear.split(",");
			currentMonthInNum = javaUtil.convertStringMonthToInteger(str[0]);
			currentYearInNum = Integer.parseInt(str[1].trim());

			if (currentYearInNum == requiredYear) {
				while (currentMonthInNum < requiredMonth) {
					driver.findElement(
							By.xpath("//div[@class='calendar' and contains(@style,'block')]/descendant::td[.='›']"))
							.click();
					currentMonthYear = driver.findElement(By.xpath(
							"//div[@class='calendar' and contains(@style,'block')]/descendant::td[@class='title']"))
							.getText();
					str = currentMonthYear.split(",");
					currentMonthInNum = javaUtil.convertStringMonthToInteger(str[0]);
				}
				while (currentMonthInNum > requiredMonth) {
					driver.findElement(
							By.xpath("//div[@class='calendar' and contains(@style,'block')]/descendant::td[.='‹']"))
							.click();
					currentMonthYear = driver.findElement(By.xpath(
							"//div[@class='calendar' and contains(@style,'block')]/descendant::td[@class='title']"))
							.getText();
					str = currentMonthYear.split(",");
					currentMonthInNum = javaUtil.convertStringMonthToInteger(str[0]);
				}
			}
		}

		driver.findElement(By.xpath(
				"//div[@class='calendar' and contains(@style,'block')]/descendant::td[.='" + requiredDate + "']"))
				.click();
		
		WebElement dueDate = driver.findElement(By.id("jscal_field_due_date"));
		dueDate.clear();
		dueDate.sendKeys(map.get("Due Date"));
		driver.findElement(By.xpath("//input[@value='  Save']")).click();
		String eventInfoPageHeader = driver.findElement(By.xpath("//span[@class='lvtHeaderText']")).getText();
		if(eventInfoPageHeader.contains(subject)) {
			System.out.println("Pass");
			excel.setDataToExcel("Create New Event", "Pass", IConstantPath.EXCEL_FILE_PATH, "EventsTestData");
		}
		else {
			System.out.println("Fail");
			excel.setDataToExcel("Create New Event", "Fail", IConstantPath.EXCEL_FILE_PATH, "EventsTestData");
		}

		WebElement administratorIcon = driver.findElement(By.xpath("//img[@src='themes/softed/images/user.PNG']"));
		web.mouseHover(administratorIcon);

		driver.findElement(By.xpath("//a[text()='Sign Out']")).click();
		web.closeWindows();
		excel.closeWorkbook();

	}

	

}
