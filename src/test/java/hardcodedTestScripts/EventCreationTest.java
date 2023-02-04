package hardcodedTestScripts;

import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.Locale;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class EventCreationTest {

	public static void main(String[] args) {
		Random random = new Random();
		int randomNum = random.nextInt(100);

		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("http://localhost:8888/");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		if (driver.getTitle().contains("vtiger"))
			System.out.println("Login page displayed");
		else
			System.out.println("Login page not found");

		driver.findElement(By.name("user_name")).sendKeys("admin");
		driver.findElement(By.name("user_password")).sendKeys("admin");
		driver.findElement(By.id("submitButton")).submit();

		if (driver.getTitle().contains("Home"))
			System.out.println("Home page is displayed");
		else
			System.out.println("Home page not found");

		WebElement quickCreate = driver.findElement(By.id("qccombo"));
		Select s = new Select(quickCreate);
		s.selectByValue("Events");

		WebElement header = driver.findElement(By.xpath("//td[@class='mailSubHeader']/b"));
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		String headerText = wait.until(ExpectedConditions.visibilityOf(header)).getText();

		if (headerText.contains("Create To Do"))
			System.out.println("Create To Do is displayed");
		else
			System.out.println("Create To Do not found");

		String subject = "Event" + randomNum;
		driver.findElement(By.name("subject")).sendKeys(subject);
		driver.findElement(By.id("jscal_trigger_date_start")).click();

		String startDatecommonPath = "//div[@class='calendar' and contains(@style,'block')]";
		String currentStartMonthYear = driver.findElement(By.xpath(startDatecommonPath + "/descendant::td[@class='title']"))
				.getText();

		String[] str = currentStartMonthYear.split(",");
		int currentStartMonth = DateTimeFormatter.ofPattern("MMMM").withLocale(Locale.ENGLISH).parse(str[0])
				.get(ChronoField.MONTH_OF_YEAR);
		int currentStartYear = Integer.parseInt(str[1].trim());

		String requiredStartDateMonthYear = "24-10-2025";
		String[] starr = requiredStartDateMonthYear.split("-");
		int requiredStartDate = Integer.parseInt(starr[0]);
		int requiredStartMonth = Integer.parseInt(starr[1]);
		int requiredStartYear = Integer.parseInt(starr[2]);

		while (currentStartYear < requiredStartYear) {
			driver.findElement(By.xpath(startDatecommonPath + "/descendant::td[.='»']")).click();
			currentStartMonthYear = driver.findElement(By.xpath(startDatecommonPath + "/descendant::td[@class='title']")).getText();
			str = currentStartMonthYear.split(",");
			currentStartMonth = DateTimeFormatter.ofPattern("MMMM").withLocale(Locale.ENGLISH).parse(str[0])
					.get(ChronoField.MONTH_OF_YEAR);
			currentStartYear = Integer.parseInt(str[1].trim());
		}

		while (currentStartMonth < requiredStartMonth) {
			driver.findElement(By.xpath(startDatecommonPath + "/descendant::td[.='›']")).click();
			currentStartMonthYear = driver.findElement(By.xpath(startDatecommonPath + "/descendant::td[@class='title']")).getText();
			str = currentStartMonthYear.split(",");
			currentStartMonth = DateTimeFormatter.ofPattern("MMMM").withLocale(Locale.ENGLISH).parse(str[0])
					.get(ChronoField.MONTH_OF_YEAR);
		}
		while (currentStartMonth > requiredStartMonth) {
			driver.findElement(By.xpath(startDatecommonPath + "/descendant::td[.='‹']")).click();
			currentStartMonthYear = driver.findElement(By.xpath(startDatecommonPath + "/descendant::td[@class='title']")).getText();
			str = currentStartMonthYear.split(",");
			currentStartMonth = DateTimeFormatter.ofPattern("MMMM").withLocale(Locale.ENGLISH).parse(str[0])
					.get(ChronoField.MONTH_OF_YEAR);
		}
		driver.findElement(By.xpath(startDatecommonPath + "/descendant::td[.='" + requiredStartDate + "']")).click();
		
		driver.findElement(By.id("jscal_trigger_due_date")).click();
		String dueDatecommonPath = "//table[@bgcolor='white' and @class='small']/descendant::tr[3]/ancestor::div[@id='qcform']/following-sibling::div[@class='calendar'][4]";
		WebElement dueDateCalendar = driver.findElement(By.xpath(dueDatecommonPath));
		String currentDueMonthYear = driver.findElement(By.xpath(dueDatecommonPath+"/descendant::td[@class='title']")).getText();
		System.out.println(currentDueMonthYear);
		
	}

}
