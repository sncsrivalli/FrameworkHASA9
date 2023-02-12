package pompages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import genericLibraries.WebDriverUtility;

/**
 * This class contains all the elements, locators and respective business
 * libraries of Home Page
 * 
 * @author QPS-Basavanagudi
 *
 */
public class HomePage {

	// Declaration
	@FindBy(xpath = "//a[text()='Organizations']")
	private WebElement organizationsTab;
	@FindBy(xpath = "//img[@src='themes/softed/images/user.PNG']")
	private WebElement administratorIcon;
	@FindBy(xpath = "//a[text()='Sign Out']")
	private WebElement signOutButton;
	@FindBy(xpath = "//a[text()='Contacts']")
	private WebElement contactsTab;
	@FindBy(xpath = "//a[.='Leads']")
	private WebElement leadsTab;
	@FindBy(id = "qccombo")
	private WebElement quickCreateDropdown;
	

	// Initialization
	public HomePage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}

	// Utilization
	/**
	 * This method is used to click organizations tab
	 */
	public void clickOrganization() {
		organizationsTab.click();
	}
	
	/**
	 * This method is used to click contacts tab
	 */
	public void clickContact() {
		contactsTab.click();
	}

	/**
	 * This method is used to select required element from quick create drop down
	 * @param web
	 * @param value
	 */
	public void selectFromQuickCreateDropdown(WebDriverUtility web, String value) {
		web.dropdown(value, quickCreateDropdown);
	}
	
	/**
	 * This method is used to click leads tab
	 */
	public void clickLeadsTab() {
		leadsTab.click();
	}
	/**
	 * This method is used to sign out of the application
	 * 
	 * @param web
	 */
	public void signOutOfApp(WebDriverUtility web) {
		web.mouseHover(administratorIcon);
		signOutButton.click();
	}
}
