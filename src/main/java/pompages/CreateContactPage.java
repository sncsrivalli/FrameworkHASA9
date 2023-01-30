package pompages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import genericLibraries.WebDriverUtility;

/**
 * This class contains all the elements, locators and respective business
 * libraries of Create Contact page
 * 
 * @author QPS-Basavanagudi
 *
 */
public class CreateContactPage {

	// Declaration
	@FindBy(xpath = "//span[@class='lvtHeaderText']")
	private WebElement pageHeader;
	@FindBy(name = "lastname")
	private WebElement contactNameTF;
	@FindBy(xpath = "//img[contains(@onclick,'Accounts&action')]")
	private WebElement organizationPlusButton;
	@FindBy(xpath = "//form[@name='selectall']/descendant::tr[contains(@onmouseout,'lvtColData')]/td[1]/a")
	private List<WebElement> organizationsList;
	@FindBy(xpath = "//input[contains(@value,'Save')]")
	private WebElement saveButton;

	// Initialization
	public CreateContactPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}

	// Utilization

	/**
	 * This method is used to fetch the create organizations page header
	 * 
	 * @return
	 */
	public String getPageHeader() {
		return pageHeader.getText();
	}

	/**
	 * This method is used to set the organization name in text field
	 * 
	 * @param name
	 */
	public void setContactName(String name) {
		contactNameTF.sendKeys(name);
	}
	
	/**
	 * This method is used to select required organization from the existing organizations list
	 * @param web
	 * @param orgName
	 */
	public void selectExistingOrganization(WebDriverUtility web, String orgName) {
		organizationPlusButton.click();
		String parentID = web.getParentWindowID();
		web.childBrowserPopup();
				
		for(WebElement org: organizationsList) {
			if(org.getText().equals(orgName)) {
				org.click();
				break;
			}
		}
		web.switchToWindow(parentID);
	}

	/**
	 * This method is used to click on save button
	 */
	public void clickSaveButton() {
		saveButton.click();
	}
}
