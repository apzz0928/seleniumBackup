import java.util.Set;

import org.junit.*;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Alert;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class defaultSetting {
	private static WebDriver driver;
	private static String TestBrowser;
	private static StringBuffer verificationErrors = new StringBuffer();
	private static String CHROMEDRIVER_FILE_PATH;
	private static String FIREFOXDRIVER_FILE_PATH;
	private static String INTERNETEXPLORER_FILE_PATH;
  	private static JavascriptExecutor js; 
  	private static String baseUrl;
  	private static HttpURLConnection huc;
  	private static int respCode;
  	
    @BeforeClass
    public static void setUp() throws Exception {
  		TestBrowser = "firefox";
  		CHROMEDRIVER_FILE_PATH = "D:/Selenium/chromedriver.exe"; //ũ�� ����̹� ���� ���
  		FIREFOXDRIVER_FILE_PATH = "D:/Selenium/geckodriver.exe"; //���̾����� ����̹� ���� ���
  		INTERNETEXPLORER_FILE_PATH = "D:/Selenium/IEDriverServer32.exe"; //IE ����̹� ���� ���  		
  		
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
  		driver.manage().window().setSize(new Dimension(1200, 1400));
  		//javaScript�� �� �� �ֵ��� ����
  		js = (JavascriptExecutor) driver;
  		huc = null;
  		respCode = 200;
  		baseUrl = "http://www.herowarz.com";

    }
  	
  	//��ũ ���� ���� Ȯ��
  	public static boolean brokenLinkCheck (String urlName, String urlLink){
        try {
            huc = (HttpURLConnection)(new URL(urlLink).openConnection());
            huc.setRequestMethod("HEAD");
            huc.connect();
            respCode = huc.getResponseCode();
            if(respCode >= 400){ 
            	System.out.println("***** " + urlName +" : ��ũ ���� HTTP : " + respCode + " *****");
            	fail();
            } else {
            	System.out.println(urlName +" : ��ũ ���� HTTP : " + respCode);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } return false;
    }
  	
  	//�˾� ������
  	public static void handleMultipleWindows(String windowTitle) { 
  		Set<String> windows = driver.getWindowHandles(); 
  		for (String window : windows) { 
  			driver.switchTo().window(window); 
  			if (driver.getTitle().contains(windowTitle)) { 
  				return; 
			} 
		} 
	}
  	
  //alert Ȯ�� ó��
  	public static void acceptAlert(String statusText) throws Exception {
  		try {
  	  		Thread.sleep(3000);
  	  		Alert alert=driver.switchTo().alert();
  	        String alertMessage=driver.switchTo().alert().getText();
  	        System.out.println (statusText + " : *** : " + alertMessage);
  	        alert.accept();
  	        Thread.sleep(1000);
  		} catch (NoAlertPresentException e) {
  			e.printStackTrace();
  		}
  	}
  	//alert ��� ó��
  	public static void dismissAlert(String statusText) throws Exception {
  		try {
	  		Thread.sleep(1000);
	  		Alert alert=driver.switchTo().alert();
	        String alertMessage=driver.switchTo().alert().getText();
	        System.out.println (statusText + " : " + alertMessage);
	        alert.dismiss();
	        Thread.sleep(1000);
  		} catch (NoAlertPresentException e) {
  			e.printStackTrace();
  		}
  	}
  	//������Ʈâ �Է� �� Ȯ��
  	public static void sendKeyAlert(String statusText, String number) throws Exception {
  		try {
	  		Thread.sleep(1000);
	  		Alert alert=driver.switchTo().alert();
	        String alertMessage=driver.switchTo().alert().getText();
	        System.out.println (statusText + " : " + alertMessage);
	        alert.sendKeys(number);
	        alert.accept();
	        Thread.sleep(1000);
	  	} catch (NoAlertPresentException e) {
			e.printStackTrace();
		}
  	}
 
  	@Test
  	public void A_Login() throws Exception {
  		driver.get(baseUrl + "/index.main");
  		Thread.sleep(1000);
  		assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));  
  		assertTrue(driver.getPageSource().contains("���̵� ����"));
  		System.out.println("���������� > ���� : Pass");
  		js.executeScript("$('.uid_login_id').val('apzz0928');");
  		js.executeScript("$('.uid_login_password').val('qordlf12');");
  		Thread.sleep(1000);    	
  		js.executeScript("$('.uid_login_login').eq(0).click();");
  		Thread.sleep(1000);
  		assertTrue(driver.getPageSource().contains("����������"));
  		System.out.println("���������� > �α��� : Pass");
  		Thread.sleep(1000);
  	}
  	
	@AfterClass
    public static void tearDown() throws Exception {
    	//�׽�Ʈ �Ϸ� 3�� �� ������ ����
        Thread.sleep(3000);
    	driver.close();  //driver.quit() ���� �������� ��� ����
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }
}