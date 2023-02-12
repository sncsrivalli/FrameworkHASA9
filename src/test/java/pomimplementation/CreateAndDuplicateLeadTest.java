package pomimplementation;

import java.util.Map;

import org.openqa.selenium.WebDriver;

import genericLibraries.ExcelUtility;
import genericLibraries.IConstantPath;
import genericLibraries.JavaUtility;
import genericLibraries.PropertiesFileUtility;
import genericLibraries.WebDriverUtility;
import pompages.CreateLeadPage;
import pompages.DuplicatingLeadPage;
import pompages.HomePage;
import pompages.LeadsPage;
import pompages.LoginPage;
import pompages.NewLeadInfoPage;

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

		LoginPage login = new LoginPage(driver);
		HomePage home = new HomePage(driver);
		LeadsPage leads = new LeadsPage(driver);
		CreateLeadPage createLead = new CreateLeadPage(driver);
		NewLeadInfoPage newLeadInfo = new NewLeadInfoPage(driver);
		DuplicatingLeadPage duplicatingPage = new DuplicatingLeadPage(driver);
		
		if(driver.getTitle().contains("vtiger"))
			System.out.println("Login page displayed");
		else
			System.out.println("Login page not found");
		
		login.loginToApp(property.fetchProperty("username"), property.fetchProperty("password"));
				
		if(driver.getTitle().contains("Home"))
			System.out.println("Home Page is displayed");
		else
			System.out.println("Home Page is not displayed");
		
		home.clickLeadsTab();
			
		if (driver.getTitle().contains("Leads"))
			System.out.println("Pass");
		else
			System.out.println("Fail");
		leads.clickPlusButton();
		
		if(createLead.getPageHeader().contains("Creating New Lead"))
			System.out.println("Pass");
		else
			System.out.println("Fail");
		
		Map<String, String> map = excel.readDataFromExcel("Create and Duplicate Lead", "LeadsTestData");
		
		createLead.selectSalutation(web, map.get("First Name Salutation"));
		String leadName = map.get("Last Name")+javaUtil.generateRandomNumber(100);
		createLead.setLastName(leadName);
		createLead.setCompanyName(map.get("Company"));
		createLead.clickSaveButton();
		
		if(newLeadInfo.getPageHeader().contains(leadName))
			System.out.println("Pass");
		else
			System.out.println("Fail");
		
		newLeadInfo.clickDuplicateButton();
		
		if(duplicatingPage.getPageHeader().contains(leadName))
			System.out.println("Pass");
		else
			System.out.println("Fail");
		
		String duplicateLeadName = map.get("New Last Name")+javaUtil.generateRandomNumber(100);
		duplicatingPage.setNewLeadName(duplicateLeadName);
		duplicatingPage.clickSaveButton();
		
		newLeadInfo.clickLeadsLink();
		
		if(leads.getNewLead().equals(duplicateLeadName)) {
			System.out.println("Pass");
			excel.setDataToExcel("Create and Duplicate Lead", "Pass", IConstantPath.EXCEL_FILE_PATH, "LeadsTestData");
		}

		else {
			System.out.println("Fail");
			excel.setDataToExcel("Create and Duplicate Lead", "Fail", IConstantPath.EXCEL_FILE_PATH, "LeadsTestData");
		}

		home.signOutOfApp(web);
		web.closeWindows();
		excel.closeWorkbook();
	}
}
