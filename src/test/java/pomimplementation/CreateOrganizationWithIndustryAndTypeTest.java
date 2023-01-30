package pomimplementation;

import java.util.Map;

import org.openqa.selenium.WebDriver;

import genericLibraries.ExcelUtility;
import genericLibraries.IConstantPath;
import genericLibraries.JavaUtility;
import genericLibraries.PropertiesFileUtility;
import genericLibraries.WebDriverUtility;
import pompages.CreateOrganizationPage;
import pompages.HomePage;
import pompages.LoginPage;
import pompages.NewOrganizationInfoPage;
import pompages.OrganizationsPage;

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
		
		LoginPage login = new LoginPage(driver);
		HomePage home = new HomePage(driver);
		OrganizationsPage organizations = new OrganizationsPage(driver);
		CreateOrganizationPage createOrganization = new CreateOrganizationPage(driver);
		NewOrganizationInfoPage newOrganization = new NewOrganizationInfoPage(driver);
		
		if(driver.getTitle().contains("vtiger"))
			System.out.println("Login page displayed");
		else
			System.out.println("Login page not found");
		
		login.loginToApp(property.fetchProperty("username"), property.fetchProperty("password"));
		
		if(driver.getTitle().contains("Home"))
			System.out.println("Home page is displayed");
		else
			System.out.println("Home page not found");
		
		home.clickOrganization();
		
		if(driver.getTitle().contains("Organizations"))
			System.out.println("Organizations page displayed");
		else
			System.out.println("Organizations page not found");
		
		organizations.clickPlusButton();
		
		if(createOrganization.getPageHeader().contains("Creating"))
			System.out.println("Creating new organization page displayed");
		else
			System.out.println("Creating new organization page not displayed");
		
		Map<String,String> map = excel.readDataFromExcel("Create Organization With Industry And Type", "OrganizationsTestData");
		
		String orgName = map.get("Organization Name")+javaUtil.generateRandomNumber(100);
		createOrganization.setOrganizationName(orgName);
		createOrganization.selectIndustry(web, map.get("Industry"));
		createOrganization.selectType(web, map.get("Type"));
		createOrganization.clickSaveButton();
			
		if(newOrganization.getPageHeader().contains(orgName))
			System.out.println("New organization created");
		else
			System.out.println("New organization not created");
		
		newOrganization.clickOrganizationsLink();
		
		if(organizations.getNewOrganization().equals(orgName)) {
			System.out.println("Test Pass");
			excel.setDataToExcel("Create Organization With Industry And Type", "Pass", IConstantPath.EXCEL_FILE_PATH, "OrganizationsTestData");
		}
		else {
			System.out.println("Test Fail");
			excel.setDataToExcel("Create Organization With Industry And Type", "Fail", IConstantPath.EXCEL_FILE_PATH, "OrganizationsTestData");
		}
		
		home.signOutOfApp(web);
		web.closeWindows();
		excel.closeWorkbook();
	}

}
