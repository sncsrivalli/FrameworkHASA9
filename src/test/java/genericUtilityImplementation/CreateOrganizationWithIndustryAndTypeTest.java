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

public class CreateOrganizationWithIndustryAndTypeTest {

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
		
		if(driver.getTitle().contains("Home"))
			System.out.println("Home page is displayed");
		else
			System.out.println("Home page not found");
		
		driver.findElement(By.xpath("//a[text()='Organizations']")).click();
		if(driver.getTitle().contains("Organizations"))
			System.out.println("Organizations page displayed");
		else
			System.out.println("Organizations page not found");
		
		driver.findElement(By.xpath("//img[@title='Create Organization...']")).click();
		String createOrgPageHeader = driver.findElement(By.xpath("//span[@class='lvtHeaderText']")).getText();
		if(createOrgPageHeader.contains("Creating"))
			System.out.println("Creating new organization page displayed");
		else
			System.out.println("Creating new organization page not displayed");
		
		Map<String,String> map = excel.readDataFromExcel("Create Organization With Industry And Type", "OrganizationsTestData");
		
		String orgName = map.get("Organization Name")+javaUtil.generateRandomNumber(100);
		driver.findElement(By.name("accountname")).sendKeys(orgName);
		
		WebElement industryDropdown = driver.findElement(By.name("industry"));
		web.dropdown(map.get("Industry"), industryDropdown);
		
		WebElement typeDropdown = driver.findElement(By.name("accounttype"));
		web.dropdown(map.get("Type"), typeDropdown);
		
		driver.findElement(By.xpath("//input[contains(@value,'Save')]")).click();
		
		String newOrgInfoHeader = driver.findElement(By.xpath("//span[@class='dvHeaderText']")).getText();
		if(newOrgInfoHeader.contains(orgName))
			System.out.println("New organization created");
		else
			System.out.println("New organization not created");
		
		driver.findElement(By.xpath("//a[@class='hdrLink']")).click();
		String newOrg = driver.findElement(By.xpath("//table[@class='lvt small']/descendant::tr[last()]/td[3]/a")).getText();
		if(newOrg.equals(orgName)) {
			System.out.println("Test Pass");
			excel.setDataToExcel("Create Organization With Industry And Type", "Pass", IConstantPath.EXCEL_FILE_PATH, "OrganizationsTestData");
		}
		else {
			System.out.println("Test Fail");
			excel.setDataToExcel("Create Organization With Industry And Type", "Fail", IConstantPath.EXCEL_FILE_PATH, "OrganizationsTestData");
		}
		
		WebElement administratorIcon = driver.findElement(By.xpath("//img[@src='themes/softed/images/user.PNG']"));
		web.mouseHover(administratorIcon);
		
		driver.findElement(By.xpath("//a[text()='Sign Out']")).click();
		web.closeWindows();
		excel.closeWorkbook();
	}

}
