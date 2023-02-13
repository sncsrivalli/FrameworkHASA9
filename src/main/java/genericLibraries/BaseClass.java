package genericLibraries;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import pompages.ContactsPage;
import pompages.CreateContactPage;
import pompages.CreateLeadPage;
import pompages.CreateOrganizationPage;
import pompages.CreateToDoPage;
import pompages.DuplicatingLeadPage;
import pompages.EventInfoPage;
import pompages.HomePage;
import pompages.LeadsPage;
import pompages.LoginPage;
import pompages.NewContactInfoPage;
import pompages.NewLeadInfoPage;
import pompages.NewOrganizationInfoPage;
import pompages.OrganizationsPage;

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
	public static JavaUtility sjavaUtil;
	public static WebDriver sdriver;
	protected LeadsPage leads;
	protected CreateLeadPage createLead;
	protected NewLeadInfoPage newLeadInfo;
	protected DuplicatingLeadPage duplicatingPage;
	protected CreateToDoPage createToDo;
	protected EventInfoPage eventInfo;
	protected OrganizationsPage organizations;
	protected CreateOrganizationPage createOrganization;
	protected NewOrganizationInfoPage newOrganization;
	
	
	//@BeforeSuite
	//@BeforeTest
	
	@BeforeClass
	public void classSetup() {
		excel = new ExcelUtility();
		property = new PropertiesFileUtility();
		javaUtil = new JavaUtility();
		sjavaUtil = javaUtil;
		web = new WebDriverUtility();

		property.propertyFileInitialization(IConstantPath.PROPERTY_FILE_PATH);
		excel.excelInitialization(IConstantPath.EXCEL_FILE_PATH);
	}
	
	@BeforeMethod
	public void methodSetup() {
		long time = Long.parseLong(property.fetchProperty("timeouts"));
		driver = web.openApplication(property.fetchProperty("browser"), property.fetchProperty("url"), time);
		sdriver = driver;
		Assert.assertTrue(driver.getTitle().contains("vtiger"));

		login = new LoginPage(driver);
		login.loginToApp(property.fetchProperty("username"), property.fetchProperty("password"));
		Assert.assertTrue(driver.getTitle().contains("Home"));
		
		home = new HomePage(driver);
		contacts = new ContactsPage(driver);
		createContact = new CreateContactPage(driver);
		newContact = new NewContactInfoPage(driver);	
		leads = new LeadsPage(driver);
		createLead = new CreateLeadPage(driver);
		newLeadInfo = new NewLeadInfoPage(driver);
		duplicatingPage = new DuplicatingLeadPage(driver);
		createToDo = new CreateToDoPage(driver);
		eventInfo = new EventInfoPage(driver);
		organizations = new OrganizationsPage(driver);
		createOrganization = new CreateOrganizationPage(driver);
		newOrganization = new NewOrganizationInfoPage(driver);
		
	}
	
	@AfterMethod
	public void methodTeardown() {
		home.signOutOfApp(web, home);
		web.closeWindows();
	}
	
	@AfterClass
	public void classTeardown() {
		excel.closeWorkbook();
	}
	
	//@AfterTest
	//@AfterSuite

}
