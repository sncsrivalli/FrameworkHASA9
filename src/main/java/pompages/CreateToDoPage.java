package pompages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import genericLibraries.JavaUtility;
import genericLibraries.WebDriverUtility;

public class CreateToDoPage {

	//Declaration
	
	private String commonCalendarPath = "//div[@class='calendar' and contains(@style,'block')]/descendant::td[%s]";
	private String currentMonthYear = "@class='title'";
	private String nextYearButton = ".='»'";
	private String nextMonthButton = ".='›'";
	private String previousMonthButton = ".='‹'";
	
	@FindBy(xpath = "//td[@class='mailSubHeader']/b")
	private WebElement pageHeader;
	@FindBy(name = "subject")
	private WebElement subjectTF;
	@FindBy(id = "jscal_trigger_date_start")
	private WebElement startDateCalendarIcon;
	@FindBy(id = "jscal_field_due_date")
	private WebElement dueDateTF;
	@FindBy(xpath = "//input[@value='  Save']")
	private WebElement saveButton;
	
	//Initialization
	public CreateToDoPage(WebDriver driver) {
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
	
	/**
	 * This method is used to set the event's subject
	 * @param subject
	 */
	public void setSubject(String subject) {
		subjectTF.sendKeys(subject);
	}
	
	/**
	 * This method is used to set the start date of the event
	 * @param web
	 * @param javaUtil
	 * @param requiredStartDate
	 */
	public void selectStartDate(WebDriverUtility web, JavaUtility javaUtil, String requiredStartDate) {
		startDateCalendarIcon.click();
		
		WebElement currentMonthAndYear = web.convertDynamicXpathToWebElement(commonCalendarPath, currentMonthYear);
		String[] str = currentMonthAndYear.getText().split(",");

		int currentMonthInNum = javaUtil.convertStringMonthToInteger(str[0]);
		int currentYearInNum = Integer.parseInt(str[1].trim());
		 
		String[] date = requiredStartDate.split("-");
		int requiredYear = Integer.parseInt(date[0]);
		int requiredMonth = Integer.parseInt(date[1]);
		int requiredDate = Integer.parseInt(date[2]);

		while (currentYearInNum < requiredYear) {
			web.convertDynamicXpathToWebElement(commonCalendarPath, nextYearButton).click();
			
			str = currentMonthAndYear.getText().split(",");
			currentMonthInNum = javaUtil.convertStringMonthToInteger(str[0]);
			currentYearInNum = Integer.parseInt(str[1].trim());
		}

		while (currentMonthInNum < requiredMonth) {
			web.convertDynamicXpathToWebElement(commonCalendarPath, nextMonthButton).click();
			
			str = currentMonthAndYear.getText().split(",");
			currentMonthInNum = javaUtil.convertStringMonthToInteger(str[0]);
		}
		while (currentMonthInNum > requiredMonth) {
			web.convertDynamicXpathToWebElement(commonCalendarPath, previousMonthButton).click();
			
			str = currentMonthAndYear.getText().split(",");
			currentMonthInNum = javaUtil.convertStringMonthToInteger(str[0]);
		}

		web.convertDynamicXpathToWebElement(commonCalendarPath, ".='" + requiredDate + "'").click();	
	}
	
	/**
	 * This method is used to set the due date for the event
	 * @param dueDate
	 */
	public void setDueDate(String dueDate) {
		dueDateTF.clear();
		dueDateTF.sendKeys(dueDate);
	}
	
	/**
	 * This method is used to click on save button
	 */
	public void clickSaveButton() {
		saveButton.click();
	}
}
