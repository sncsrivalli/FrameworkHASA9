package pomimplementation;

import java.util.Map;

import org.openqa.selenium.WebDriver;

import genericLibraries.ExcelUtility;
import genericLibraries.IConstantPath;
import genericLibraries.JavaUtility;
import genericLibraries.PropertiesFileUtility;
import genericLibraries.TabNames;
import genericLibraries.WebDriverUtility;
import pompages.ContactsPage;
import pompages.CreateContactPage;
import pompages.HomePage;
import pompages.LoginPage;
import pompages.NewContactInfoPage;

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


		LoginPage login = new LoginPage(driver);
		HomePage home = new HomePage(driver);
		ContactsPage contacts = new ContactsPage(driver);
		CreateContactPage createContact = new CreateContactPage(driver);
		NewContactInfoPage newContact = new NewContactInfoPage(driver);
		
		if (driver.getTitle().contains("vtiger"))
			System.out.println("Login page displayed");
		else
			System.out.println("Login page not found");

		login.loginToApp(property.fetchProperty("username"), property.fetchProperty("password"));
		
		if (driver.getTitle().contains("Home"))
			System.out.println("Home page is displayed");
		else
			System.out.println("Home page not found");

		//home.clickContact();
		home.clickRequiredTab(web, TabNames.CONTACTS);
		
		if (driver.getTitle().contains("Contacts"))
			System.out.println("Contacts page displayed");
		else
			System.out.println("Contacts page not found");

		contacts.clickPlusButton();
		
		if (createContact.getPageHeader().contains("Creating"))
			System.out.println("Creating new organization page displayed");
		else
			System.out.println("Creating new organization page not displayed");

		Map<String, String> map = excel.readDataFromExcel("Create Contact", "ContactsTestData");
		String contactName = map.get("Last Name") + javaUtil.generateRandomNumber(100);
		createContact.setContactName(contactName);
		createContact.clickSaveButton();
		
		if (newContact.getPageHeader().contains(contactName))
			System.out.println("New contact created");
		else
			System.out.println("New contact not created");

		newContact.clickContactsLink();
		
		if (contacts.getNewContact().equals(contactName)) {
			System.out.println("Test Pass");
			excel.setDataToExcel("Create Contact", "Pass", IConstantPath.EXCEL_FILE_PATH, "ContactsTestData");
		} else {
			System.out.println("Test Fail");
			excel.setDataToExcel("Create Contact", "Fail", IConstantPath.EXCEL_FILE_PATH, "ContactsTestData");
		}

		home.signOutOfApp(web, home);
		web.closeWindows();
		excel.closeWorkbook();
	}

}
