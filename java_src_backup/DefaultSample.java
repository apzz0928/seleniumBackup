import org.openqa.selenium.By;		
import org.openqa.selenium.WebDriver;		
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;	
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.Assert;		
import org.testng.annotations.Test;	
import org.testng.annotations.BeforeTest;	
import org.testng.annotations.AfterTest;	

public class DefaultSample {		
	    private WebDriver driver;	
		private static String CHROMEDRIVER_FILE_PATH;
		private static String FIREFOXDRIVER_FILE_PATH;
		private static String INTERNETEXPLORER_FILE_PATH;
		private static String TestBrowser;
		
		@BeforeTest
		public void beforeTest() {	
	  		TestBrowser = "chrome";
	  		CHROMEDRIVER_FILE_PATH = "D:/Selenium/driver/chromedriver.exe"; //ũ�� ����̹� ���� ���
	  		FIREFOXDRIVER_FILE_PATH = "D:/Selenium/driver/geckodriver.exe"; //���̾����� ����̹� ���� ���
	  		INTERNETEXPLORER_FILE_PATH = "D:/Selenium/driver/IEDriverServer32.exe"; //IE ����̹� ���� ���  		
	  		
	  		if(TestBrowser.equals("chrome")){
	  		//�׽�Ʈ �������� ũ������ ����
	  	  		System.setProperty("webdriver.chrome.driver", CHROMEDRIVER_FILE_PATH);
	  	  		driver = new ChromeDriver();
	  		} else if (TestBrowser.equals("firefox")) {
	  	  		System.setProperty("webdriver.gecko.driver", FIREFOXDRIVER_FILE_PATH);
	  	  		driver = new FirefoxDriver();
	  		} else if (TestBrowser.equals("ie")) {
	  			System.setProperty("webdriver.ie.driver", INTERNETEXPLORER_FILE_PATH);
	  	  		driver = new InternetExplorerDriver();
	  		}
		}		
		
		@Test				
		public void testEasy1() {	
			driver.get("http://demo.guru99.com/selenium/guru99home/");
			String title = driver.getTitle();				 
			Assert.assertTrue(title.contains("Demo Guru99 Page")); 		
		}	

		@AfterTest
		public void afterTest() {
			driver.quit();			
		}		
}	