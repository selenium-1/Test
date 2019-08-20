import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import au.com.bytecode.opencsv.CSVReader;

public class ReadDataFromCSV {
	static WebDriver driver;
	String CSV_file = "D:\\Skype\\UserRoles.csv";

	@BeforeClass
	public static void openBrowser() {
		System.setProperty("webdriver.chrome.driver",
				"D:\\selenium Lib\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get("https://casemgmt-staging.dev.phishlabs.com/");
		driver.manage().window().maximize();
		driver.findElement(By.id("username")).sendKeys("gitanjali.bhalerao");
		driver.findElement(By.id("password")).sendKeys("Gitanjali@24");
		driver.findElement(By.cssSelector("[type='submit'][value='Sign In']")).click();
		driver.findElement(By.cssSelector("[href='/user']")).click();

//Switch to child window
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabs.get(1));
	}

	@Test
	public void read_data() throws IOException {
		CSVReader reader = new CSVReader(new FileReader(CSV_file));
		String[] cell;
		
		while ((cell = reader.readNext()) != null) {
			for (int i = 0; i < 1; i++) {
				String user = cell[i];
				String user_role = cell[i + 1];
				System.out.println(user);
				driver.findElement(By.cssSelector("[type='search']")).sendKeys(user);
				driver.findElement(By.className("icon-edit")).click();
				Select roles = new Select(driver.findElement(By.id("phishlabs_userbundle_usertype_dbRoles")));
				System.out.println(user_role);
				roles.selectByVisibleText(user_role);
				driver.findElement(By.id("btn_save")).click();
			}
		}
	}
	@AfterClass
	public void close() {
		driver.quit();
	}
	
}