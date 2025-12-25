package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

public class BaseTest {

	protected WebDriver driver;
	private static final String BASE_URL = "https://practicetestautomation.com/practice-test-login/";

	private static final String ZAP_HOST = "localhost";
	private static final int ZAP_PORT = 8080;

	@BeforeMethod
	public void setUp() {
		WebDriverManager.chromedriver().setup();
		Proxy proxy = new Proxy();
		proxy.setHttpProxy(ZAP_HOST + ":" + ZAP_PORT);
		proxy.setSslProxy(ZAP_HOST + ":" + ZAP_PORT);

		ChromeOptions options = new ChromeOptions();
		options.setProxy(proxy);
		options.addArguments("--ignore-certificate-errors");

		// optional but useful with ZAP:
		options.addArguments("--disable-web-security");
		options.addArguments("--allow-insecure-localhost");

		driver = new ChromeDriver(options);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		driver.get(BASE_URL);
	}

	@AfterMethod
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}
	}
}
