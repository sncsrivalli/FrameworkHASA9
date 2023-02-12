package pompages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * This page contains the elements, locators and respective business libraries of Event Info page
 * @author QPS-Basavanagudi
 *
 */
public class EventInfoPage {

	//Declaration
	@FindBy(xpath = "//span[@class='lvtHeaderText']")
	private WebElement pageHeader;
	
	//Initialization
	public EventInfoPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}
	
	//Utilization
	/**
	 * This method returns the event info page header
	 * @return
	 */
	public String getPageHeader() {
		return pageHeader.getText();
	}
}
