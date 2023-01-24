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

public class CreateOrganizationTest {

	public static void main(String[] args) {
		
		PropertiesFileUtility property = new PropertiesFileUtility();
		ExcelUtility excel = new ExcelUtility();
		JavaUtility javaUtil = new JavaUtility();
		WebDriverUtility web = new WebDriverUtility();
		
		property.propertyFileInitialization(IConstantPath.PROPERTY_FILE_PATH);
		excel.excelInitialization(IConstantPath.EXCEL_FILE_PATH);
		long time = Long.parseLong(property.fetchProperty("timeouts"));
		WebDriver driver = web.openApplication(property.fetchProperty("browser"), property.fetchProperty("url"), time);
		
		String title = driver.findElement(By.xpath("//a[@href='http://www.vtiger.com']")).getText();
		
		if(title.contains("vtiger"))
			System.out.println("Login Page Displayed");
		else
			System.out.println("Login Page is not Displayed");
		
		driver.findElement(By.name("user_name")).sendKeys(property.fetchProperty("username"));
		driver.findElement(By.name("user_password")).sendKeys(property.fetchProperty("password"));
		driver.findElement(By.id("submitButton")).click();
		
		String homePageText = driver.findElement(By.xpath("//a[@class='hdrLink']")).getText();
		if(homePageText.contains("Home"))
			System.out.println("Home Page is displayed");
		else
			System.out.println("Home Page is not displayed");
		
		driver.findElement(By.xpath("//a[.='Organizations']")).click();
		String organizations = driver.findElement(By.xpath("//a[@class='hdrLink']")).getText();
		
		if (organizations.contains("Organization"))
			System.out.println("Pass");
		else
			System.out.println("Fail");
		
		driver.findElement(By.xpath("//img[@alt='Create Organization...']")).click();
		
		String createOrg = driver.findElement(By.xpath("//span[@class='lvtHeaderText']")).getText();
		if(createOrg.contains("Creating New Organization"))
			System.out.println("Pass");
		else
			System.out.println("Fail");
		
		Map<String,String> map = excel.readDataFromExcel("Create Organization", "TestData");
		
		String organizationName = map.get("Organization Name") + javaUtil.generateRandomNumber(100);
		driver.findElement(By.name("accountname")).sendKeys(organizationName);
		
		WebElement industryDropdown = driver.findElement(By.name("industry"));
		web.dropdown(map.get("Industry"), industryDropdown);
		
		driver.findElement(By.xpath("//input[contains(@value,'Save')]")).click();
		
		String newOrgInfo = driver.findElement(By.xpath("//span[@class='dvHeaderText']")).getText();
		if(newOrgInfo.contains(organizationName))
			System.out.println("Pass");
		else
			System.out.println("Fail");
		
		driver.findElement(By.xpath("//a[@class='hdrLink']")).click();
		String newOrg = driver.findElement(By.xpath("//table[@class='lvt small']/descendant::tr[last()]/td[3]")).getText();
		if(newOrg.equals(organizationName))
			System.out.println("Pass");
		else
			System.out.println("Fail");
		
		WebElement administratorIcon = driver.findElement(By.xpath("//img[@src='themes/softed/images/user.PNG']"));
		web.mouseHover(administratorIcon);

		driver.findElement(By.xpath("//a[.='Sign Out']")).click();
		
		driver.quit();
		excel.closeWorkbook();
		
	}

}
