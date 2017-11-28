import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertTrue;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;		
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;	
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.annotations.Test;	
import org.testng.annotations.BeforeTest;	
import org.testng.annotations.AfterTest;


public class SampleTest2_rct {		
	    private WebDriver driver;	
		private static String CHROMEDRIVER_FILE_PATH;
		private static String FIREFOXDRIVER_FILE_PATH;
		private static String INTERNETEXPLORER_FILE_PATH;
		private static String TestBrowser;
		private static JavascriptExecutor js;
		private static String baseUrl;
		
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
	        js = (JavascriptExecutor) driver;        
	        baseUrl = "https://rct-d-p.astorm.com";
		}		
		
		@Test				
		public void A_Login() throws Exception {
	    	//�α���
	        driver.get(baseUrl + "/login/form.ct");
	        Thread.sleep(1000);
	        //�ش� �޼����� ������ Pass
	        assertTrue(driver.getPageSource().contains("User ID"));
	        //�ش� �޼����� ������ Fail
	        assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));        
	        System.out.println("�α��� ������ : Pass");
	        js.executeScript("$('input[name=j_username]').prop('value', 'apzz0928')");
	        js.executeScript("$('input[name=j_password]').prop('value', 'qordlf!@34')");
	        js.executeScript("$('.ac_btn_text')[0].click();");
	        System.out.println("�α��� : Pass");
	        Thread.sleep(1000);        
	    }

		@AfterTest
		public void afterTest() {
			driver.quit();			
		}		
}	