package pompages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * This class contains all element locators and respective business libraries of Login Page
 * @author QPS-Basavanagudi
 *
 */
public class LoginPage {
	
	//Declaration
	@FindBy(name = "user_name") private WebElement usernameTF;
	@FindBy(name = "user_password") private WebElement passwordTF;
	@FindBy(id = "submitButton") private WebElement loginButton;
	
	//Initialization
	public LoginPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}
	
	//Utilization
	
	/**
	 * This method is used to login to the application
	 * @param username
	 * @param password
	 */
	public void loginToApp(String username, String password) {
		usernameTF.sendKeys(username);
		passwordTF.sendKeys(password);
		loginButton.click();
	}

}
