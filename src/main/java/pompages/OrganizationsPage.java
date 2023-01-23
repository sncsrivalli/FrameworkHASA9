package pompages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * This class contains all the elements, locators and respective business libraries of Organizations page
 * @author QPS-Basavanagudi
 *
 */
public class OrganizationsPage {
	
	//Declaration
	@FindBy(xpath = "//img[@title='Create Organization...']") private WebElement plusButton;
	@FindBy(xpath = "//table[@class='lvt small']/descendant::tr[last()]/td[3]/a") 
	private WebElement newOrganization;
	
	//Initialization
	public OrganizationsPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}

	//Utilization
	/**
	 * This method is used to click on plus button to create new organization
	 */
	public void clickPlusButton() {
		plusButton.click();
	}
	
	/**
	 * This method returns the new organization name
	 * @return
	 */
	public String getNewOrganization() {
		return newOrganization.getText();
	}
}
