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

public class CreateAndDuplicateLeadTest {

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
		
		
		String homePageText = driver.findElement(By.xpath("//a[@class='hdrLink']")).getText();
		if(homePageText.contains("Home"))
			System.out.println("Home Page is displayed");
		else
			System.out.println("Home Page is not displayed");
		
		driver.findElement(By.xpath("//a[.='Leads']")).click();
		
		String leads = driver.findElement(By.xpath("//a[@class='hdrLink']")).getText();
		
		if (leads.contains("Leads"))
			System.out.println("Pass");
		else
			System.out.println("Fail");
		
		driver.findElement(By.xpath("//img[@alt='Create Lead...']")).click();
		String createLead = driver.findElement(By.xpath("//span[@class='lvtHeaderText']")).getText();
		if(createLead.contains("Creating New Lead"))
			System.out.println("Pass");
		else
			System.out.println("Fail");
		
		Map<String, String> map = excel.readDataFromExcel("Create and Duplicate Lead", "LeadsTestData");
		WebElement salutationDropdown = driver.findElement(By.name("salutationtype"));
		web.dropdown(map.get("First Name Salutation"), salutationDropdown);
		String leadName = map.get("Last Name")+javaUtil.generateRandomNumber(100);
		driver.findElement(By.name("lastname")).sendKeys(leadName);
		driver.findElement(By.name("company")).sendKeys(map.get("Company"));
		driver.findElement(By.xpath("//input[contains(@value,'Save')]")).click();
		
		String newLeadInfo = driver.findElement(By.xpath("//span[@class='dvHeaderText']")).getText();
		if(newLeadInfo.contains(leadName))
			System.out.println("Pass");
		else
			System.out.println("Fail");
		
		driver.findElement(By.name("Duplicate")).click();
		String duplicatingPage = driver.findElement(By.xpath("//span[@class='lvtHeaderText']")).getText();
		if(duplicatingPage.contains(leadName))
			System.out.println("Pass");
		else
			System.out.println("Fail");
		String duplicateLeadName = map.get("New Last Name")+javaUtil.generateRandomNumber(100);
		WebElement lastName = driver.findElement(By.name("lastname"));
		lastName.clear();
		lastName.sendKeys(duplicateLeadName);
		driver.findElement(By.xpath("//input[contains(@value,'Save')]")).click();
		driver.findElement(By.xpath("//a[@class='hdrLink']")).click();
		
		String newLead = driver.findElement(By.xpath("//table[@class='lvt small']/descendant::tr[last()]/td[3]/a")).getText();
		if(newLead.equals(duplicateLeadName)) {
			System.out.println("Pass");
			excel.setDataToExcel("Create and Duplicate Lead", "Pass", IConstantPath.EXCEL_FILE_PATH, "LeadsTestData");
		}

		else {
			System.out.println("Fail");
			excel.setDataToExcel("Create and Duplicate Lead", "Fail", IConstantPath.EXCEL_FILE_PATH, "LeadsTestData");
		}

		

		WebElement administratorIcon = driver.findElement(By.xpath("//img[@src='themes/softed/images/user.PNG']"));
		web.mouseHover(administratorIcon);

		driver.findElement(By.xpath("//a[text()='Sign Out']")).click();
		web.closeWindows();
		excel.closeWorkbook();
	}
}
