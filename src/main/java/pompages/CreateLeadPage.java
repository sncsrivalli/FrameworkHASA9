package pompages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import genericLibraries.WebDriverUtility;

/**
 * This class contains all the elements, locators and respective business
 * libraries of Create Lead page
 * 
 * @author QPS-Basavanagudi
 *
 */
public class CreateLeadPage {

	// Declaration
	@FindBy(xpath = "//span[@class='lvtHeaderText']")
	private WebElement pageHeader;
	@FindBy(name = "salutationtype")
	private WebElement salutationDropdown;
	@FindBy(name = "lastname")
	private WebElement lastNameTF;
	@FindBy(name = "company")
	private WebElement companyNameTF;
	@FindBy(xpath = "//input[contains(@value,'Save')]")
	private WebElement saveButton;

	// Initialization
	public CreateLeadPage(WebDriver driver) {
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
	 * This method is used to select the salutation from salutation drop down
	 * 
	 * @param web
	 * @param value
	 */
	public void selectSalutation(WebDriverUtility web, String value) {
		web.dropdown(value, salutationDropdown);
	}

	/**
	 * This method is used to set the lead's last name
	 * 
	 * @param name
	 */
	public void setLastName(String name) {
		lastNameTF.sendKeys(name);
	}

	/**
	 * This method is used to set the company name
	 * 
	 * @param companyName
	 */
	public void setCompanyName(String companyName) {
		companyNameTF.sendKeys(companyName);
	}

	/**
	 * This method is used to click on save button
	 */
	public void clickSaveButton() {
		saveButton.click();
	}
}
