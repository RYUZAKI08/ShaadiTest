import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class CommunityTest extends CommonFunctions{
	WebDriver driver;
	@BeforeTest
	public void openBrowser() {
		System.setProperty("webdriver.chrome.driver",
				System.getProperty("user.dir") + "//src//test//resources//chromedriver.exe");
		driver = new ChromeDriver();
	}
	
	@DataProvider(name = "getTestData")
	public Object getDataFromExcel() {

		return getData(this.getClass().getSimpleName());

	}

	@Test(dataProvider = "getTestData")
	public void testCommunityName(String email, String password, String mothertongue) {
		
		try {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get("https://www.marathishaadi.com/");
		driver.manage().window().maximize();
		
		// Clicking on Lets begin
		driver.findElement(By.xpath("//button[contains(text(),\"Let's Begin\")]")).click();
		
		// Entering the email
		driver.findElement(By.name("email")).sendKeys(email);
		
		// Entering the password
		driver.findElement(By.name("password1")).sendKeys(password);
		
		// Selecting the profile
		driver.findElement(By.xpath("//body/div[@id='__next']/div[13]/form[1]/div[2]/div[3]/div[1]/div[1]")).click();
		driver.findElement(By.xpath("//div[contains(text(),'Sister')]")).click();
		Thread.sleep(2000);
		
		// Clicking on Next button
		driver.findElement(By.xpath("//button[contains(text(),'Next')]")).click();
		
		// Verifying if correct community is set or not
		Assert.assertTrue(mothertongue.contains(driver.findElement(By.xpath("//body/div[@id='__next']/div[13]/form[1]/div[2]/div[4]/div[1]/div[1]/div[1]")).getText()), "Community Name does not match with Mother tongue");
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
}
