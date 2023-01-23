package pompages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import genericLibraries.WebDriverUtility;

/**
 * This class contains all the elements, locators and respective business libraries of Create Organizations page
 * @author QPS-Basavanagudi
 *
 */
public class CreateOrganizationPage {

	//Declaration
	@FindBy(xpath = "//span[@class='lvtHeaderText']") private WebElement pageHeader;
	@FindBy(name = "accountname") private WebElement organizationNameTF;
	@FindBy(name = "industry") private WebElement industryDropdown;
	@FindBy(name = "accounttype") private WebElement typeDropdown;
	@FindBy(xpath = "//input[contains(@value,'Save')]") private WebElement saveButton;
	
	//Initialization
	public CreateOrganizationPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}
	
	//Utilization
	
	/**
	 * This method is used to fetch the create organizations page header
	 * @return
	 */
	public String getPageHeader() {
		return pageHeader.getText();
	}
	
	/**
	 * This method is used to set the organization name in text field
	 * @param name
	 */
	public void setOrganizationName(String name) {
		organizationNameTF.sendKeys(name);
	}
	
	/**
	 * This method is used to choose the industry from industry drop down
	 * @param web
	 * @param value
	 */
	public void selectIndustry(WebDriverUtility web, String value) {
		web.dropdown(value, industryDropdown);
	}
	
	/**
	 * This method is used to choose the investor type from type drop down
	 * @param web
	 * @param value
	 */
	public void selectType(WebDriverUtility web, String value) {
		web.dropdown(value, typeDropdown);
	}
	
	/**
	 * This method is used to click on save button
	 */
	public void clickSaveButton() {
		saveButton.click();
	}
}
