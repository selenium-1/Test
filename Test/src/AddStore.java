import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class AddStore {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub

		System.setProperty("webdriver.chrome.driver", "D:\\selenium Lib\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		driver.get("https://casemgmt-staging.dev.phishlabs.com/");
		driver.findElement(By.id("username")).sendKeys("gitanjali.bhalerao");
		driver.findElement(By.id("password")).sendKeys("Gitanjali@24");
		driver.findElement(By.cssSelector("[type='submit'][value='Sign In']")).click();

		driver.findElement(By.cssSelector("[href='/user']")).click();

		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabs.get(1));

		driver.findElement(By.cssSelector("[type='search']")).sendKeys("ioc");

		driver.quit();
	}
}
