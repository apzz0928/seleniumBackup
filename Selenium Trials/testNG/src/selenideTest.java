
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.codeborne.selenide.Selenide;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;


public class selenideTest {	
	private static WebDriver driver;
	private static String TestBrowser;
	private static String CHROMEDRIVER_FILE_PATH;
	private static String FIREFOXDRIVER_FILE_PATH;
	private static String INTERNETEXPLORER_FILE_PATH;
	
  	//@BeforeClass
  	public void testReady() {
		TestBrowser = "chrome";
		CHROMEDRIVER_FILE_PATH = "D:/Selenium/driver/chromedriver.exe"; //ũ�� ����̹� ���� ���
		FIREFOXDRIVER_FILE_PATH = "D:/Selenium/driver/geckodriver.exe"; //���̾����� ����̹� ���� ���
		INTERNETEXPLORER_FILE_PATH = "D:/Selenium/driver/IEDriverServer32.exe"; //IE ����̹� ���� ���  		
		
		/*
		if(TestBrowser.equals("chrome")){
		//�׽�Ʈ �������� ũ������ ����
			System.setProperty("webdriver.chrome.driver", CHROMEDRIVER_FILE_PATH);
			System.setProperty("selenide.browser", "chrome");
			driver = new ChromeDriver();
		} else if (TestBrowser.equals("firefox")) {
			System.setProperty("webdriver.gecko.driver", FIREFOXDRIVER_FILE_PATH);
			driver = new FirefoxDriver();
		} else if (TestBrowser.equals("ie")) {
			System.setProperty("webdriver.ie.driver", INTERNETEXPLORER_FILE_PATH);
			driver = new InternetExplorerDriver();
		}
		*/
		driver.manage().window().setSize(new Dimension(1200, 1400));
		//javaScript�� �� �� �ֵ��� ����
	}
	
	@Test
  public void f() throws InterruptedException {
		CHROMEDRIVER_FILE_PATH = "D:/Selenium/driver/chromedriver.exe"; //ũ�� ����̹� ���� ���
		System.setProperty("webdriver.chrome.driver", CHROMEDRIVER_FILE_PATH);
		System.setProperty("selenide.browser", "chrome");
//		driver.manage().window().setSize(new Dimension(1200, 1400));
		Selenide.open("https://rct-d-p.astorm.com");
		Thread.sleep(3000);
  }
	
	@AfterClass
	public void testEnd(){
		driver.quit();
	}
}
