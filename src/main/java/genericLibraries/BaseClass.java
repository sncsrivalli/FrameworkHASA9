package genericLibraries;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import pompages.ContactsPage;
import pompages.CreateContactPage;
import pompages.HomePage;
import pompages.LoginPage;
import pompages.NewContactInfoPage;

public class BaseClass {
	protected ExcelUtility excel;
	protected PropertiesFileUtility property;
	protected JavaUtility javaUtil;
	protected WebDriverUtility web;
	protected WebDriver driver;
	protected LoginPage login;
	protected HomePage home;
	protected ContactsPage contacts;
	protected CreateContactPage createContact;
	protected NewContactInfoPage newContact;
	
	//@BeforeSuite
	//@BeforeTest
	
	@BeforeClass
	public void classSetup() {
		excel = new ExcelUtility();
		property = new PropertiesFileUtility();
		javaUtil = new JavaUtility();
		web = new WebDriverUtility();

		property.propertyFileInitialization(IConstantPath.PROPERTY_FILE_PATH);
		excel.excelInitialization(IConstantPath.EXCEL_FILE_PATH);
	}
	
	@BeforeMethod
	public void methodSetup() {
		long time = Long.parseLong(property.fetchProperty("timeouts"));
		driver = web.openApplication(property.fetchProperty("browser"), property.fetchProperty("url"), time);
		Assert.assertTrue(driver.getTitle().contains("vtiger"));

		login = new LoginPage(driver);
		login.loginToApp(property.fetchProperty("username"), property.fetchProperty("password"));
		Assert.assertTrue(driver.getTitle().contains("Home"));
		
		home = new HomePage(driver);
		contacts = new ContactsPage(driver);
		createContact = new CreateContactPage(driver);
		newContact = new NewContactInfoPage(driver);	
	}
	
	@AfterMethod
	public void methodTeardown() {
		home.signOutOfApp(web);
		web.closeWindows();
	}
	
	@AfterClass
	public void classTeardown() {
		excel.closeWorkbook();
	}
	
	//@AfterTest
	//@AfterSuite

}
