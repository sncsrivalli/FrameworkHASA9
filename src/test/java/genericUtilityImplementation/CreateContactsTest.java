package genericUtilityImplementation;

import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import genericLibraries.ExcelUtility;
import genericLibraries.IConstantPath;
import genericLibraries.JavaUtility;
import genericLibraries.PropertiesFileUtility;
import genericLibraries.WebDriverUtility;

public class CreateContactsTest {

	public static void main(String[] args) {
		ExcelUtility excel = new ExcelUtility();
		PropertiesFileUtility property = new PropertiesFileUtility();
		JavaUtility javaUtil = new JavaUtility();
		WebDriverUtility web = new WebDriverUtility();

		property.propertyFileInitialization(IConstantPath.PROPERTY_FILE_PATH);
		excel.excelInitialization(IConstantPath.EXCEL_FILE_PATH);

		long time = Long.parseLong(property.fetchProperty("timeouts"));
		WebDriver driver = web.openApplication(property.fetchProperty("browser"), property.fetchProperty("url"), time);

		if (driver.getTitle().contains("vtiger"))
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

		driver.findElement(By.xpath("//a[text()='Contacts']")).click();
		if (driver.getTitle().contains("Contacts"))
			System.out.println("Contacts page displayed");
		else
			System.out.println("Contacts page not found");

		driver.findElement(By.xpath("//img[@title='Create Contact...']")).click();
		String createOrgPageHeader = driver.findElement(By.xpath("//span[@class='lvtHeaderText']")).getText();
		if (createOrgPageHeader.contains("Creating"))
			System.out.println("Creating new organization page displayed");
		else
			System.out.println("Creating new organization page not displayed");

		Map<String, String> map = excel.readDataFromExcel("Create Contact", "ContactsTestData");
		String contactName = map.get("Last Name") + javaUtil.generateRandomNumber(100);
		driver.findElement(By.name("lastname")).sendKeys(contactName);
		driver.findElement(By.xpath("//input[contains(@value,'Save')]")).click();

		String newContactInfoHeader = driver.findElement(By.xpath("//span[@class='dvHeaderText']")).getText();
		if (newContactInfoHeader.contains(contactName))
			System.out.println("New contact created");
		else
			System.out.println("New contact not created");

		driver.findElement(By.xpath("//a[@class='hdrLink']")).click();
		String newContact = driver.findElement(By.xpath("//table[@class='lvt small']/descendant::tr[last()]/td[4]/a"))
				.getText();
		if (newContact.equals(contactName)) {
			System.out.println("Test Pass");
			excel.setDataToExcel("Create Contact", "Pass", IConstantPath.EXCEL_FILE_PATH, "ContactsTestData");
		} else {
			System.out.println("Test Fail");
			excel.setDataToExcel("Create Contact", "Fail", IConstantPath.EXCEL_FILE_PATH, "ContactsTestData");
		}

		WebElement administratorIcon = driver.findElement(By.xpath("//img[@src='themes/softed/images/user.PNG']"));
		web.mouseHover(administratorIcon);

		driver.findElement(By.xpath("//a[text()='Sign Out']")).click();
		web.closeWindows();
		excel.closeWorkbook();
	}

}
