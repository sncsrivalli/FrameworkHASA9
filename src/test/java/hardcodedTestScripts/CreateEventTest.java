package hardcodedTestScripts;

import java.time.Duration;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class CreateEventTest {

	public static void main(String[] args) {
		Random random = new Random();
		int randomNum = random.nextInt(100);
		
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("http://localhost:8888/");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
		if(driver.getTitle().contains("vtiger"))
			System.out.println("Login page displayed");
		else
			System.out.println("Login page not found");
		
		driver.findElement(By.name("user_name")).sendKeys("admin");
		driver.findElement(By.name("user_password")).sendKeys("admin");
		driver.findElement(By.id("submitButton")).submit();
		
		if(driver.getTitle().contains("Home"))
			System.out.println("Home page is displayed");
		else
			System.out.println("Home page not found");
		
		WebElement quickCreate = driver.findElement(By.id("qccombo"));
		Select s = new Select(quickCreate);
		s.selectByValue("Events");
		
		String header = driver.findElement(By.xpath("//td[@class='mailSubHeader']/b")).getText();
		if(header.contains("Create To Do"))
			System.out.println("Create To Do is displayed");
		else
			System.out.println("Create To Do not found");
		
		String subject = "Event"+randomNum;
		driver.findElement(By.name("subject")).sendKeys(subject);
//		driver.findElement(By.id("jscal_trigger_date_start")).click();
//		
//		driver.findElement(By.id("jscal_trigger_due_date")).click();
		

	}

}
