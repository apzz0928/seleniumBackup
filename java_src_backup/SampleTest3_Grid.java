import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertTrue;
import static org.testng.AssertJUnit.fail;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;		
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;	
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;	
import org.testng.annotations.BeforeTest;	
import org.testng.annotations.AfterTest;


public class SampleTest3_Grid {		
	    private static WebDriver driver;	
		//private static String CHROMEDRIVER_FILE_PATH;
		private static String FIREFOXDRIVER_FILE_PATH;
		private static String INTERNETEXPLORER_FILE_PATH;
		private static String TestBrowser;
		private static JavascriptExecutor js;
		private static String baseUrl, nodeURL;
		private static StringBuffer verificationErrors = new StringBuffer();
		
		@BeforeTest
		public void beforeTest() throws MalformedURLException {	
	  		TestBrowser = "chrome";
	  		//CHROMEDRIVER_FILE_PATH = "D:/Selenium/driver/chromedriver.exe"; //크롬 드라이버 파일 경로
	  		FIREFOXDRIVER_FILE_PATH = "D:/Selenium/driver/geckodriver.exe"; //파이어폭스 드라이버 파일 경로
	  		INTERNETEXPLORER_FILE_PATH = "D:/Selenium/driver/IEDriverServer32.exe"; //IE 드라이버 파일 경로  		
	  		
	  		if(TestBrowser.equals("chrome")){
	  			baseUrl = "https://rct-d-p.astorm.com";
		        nodeURL = "http://10.10.105.228:5555/wd/hub";
		        
				DesiredCapabilities cap = DesiredCapabilities.chrome();
				cap.setBrowserName("chrome");
				cap.setPlatform(Platform.WINDOWS);
				driver = new RemoteWebDriver(new URL(nodeURL), cap);
	  			/*
	  			//테스트 브라우저를 크롬으로 설정
	  	  		System.setProperty("webdriver.chrome.driver", CHROMEDRIVER_FILE_PATH);
	  	  		driver = new ChromeDriver();
	  	  		*/
	  		} else if (TestBrowser.equals("firefox")) {
	  	  		System.setProperty("webdriver.gecko.driver", FIREFOXDRIVER_FILE_PATH);
	  	  		driver = new FirefoxDriver();
	  		} else if (TestBrowser.equals("ie")) {
	  			System.setProperty("webdriver.ie.driver", INTERNETEXPLORER_FILE_PATH);
	  	  		driver = new InternetExplorerDriver();
	  		}	  		
	        js = (JavascriptExecutor) driver;
		}		
		
	  	public static void elementWait(String selector, String word){
			WebDriverWait wait = new WebDriverWait(driver, 10);
	  		switch(selector){
	  		case "id":
		  		wait.until(ExpectedConditions.elementToBeClickable(By.id(word)));
		  		break;
	  		case "class":
		  		wait.until(ExpectedConditions.elementToBeClickable(By.className(word)));	  
	  			break;
	  		case "name":
	  			wait.until(ExpectedConditions.elementToBeClickable(By.name(word)));
	  			break;
	  		case "css":
		  		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(word)));	
	  			break;
	  		case "linkText":
		  		wait.until(ExpectedConditions.elementToBeClickable(By.linkText(word)));	  	
	  			break;
	  		case "text":
		  		wait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(word)));
	  			break;
	  		case "tagName":
	  			wait.until(ExpectedConditions.elementToBeClickable(By.tagName(word)));
	  			break;
	  		}
		};
		
		@Test(priority = 0)
		public void Login() throws Exception {
	        driver.get(baseUrl + "/login/form.ct");
	  		elementWait("class", "uid_login_type");
	        assertTrue(driver.getPageSource().contains("User ID"));
	        assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));        
	        System.out.println("로그인 페이지 : Pass");
	        js.executeScript("$('input[name=j_username]').prop('value', 'apzz0928')");
	        js.executeScript("$('input[name=j_password]').prop('value', 'qordlf!@34')");
	        js.executeScript("$('.ac_btn_text')[0].click();");
	        System.out.println("로그인 : Pass");    
	    }
	  	@Test(priority = 1)
	    public void langChange() throws Exception {
	    	driver.get(baseUrl + "/common/locale/ko");
	        assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
	        assertTrue(driver.getPageSource().contains("true"));
	        System.out.println("언어변경 : Pass");
	        driver.get(baseUrl);
		}

	  	@Test(priority = 3)
	    public void Logout(){
	  		js.executeScript("$('.member-info > a')[0].click();");
	  		elementWait("class", "uid_login_type");
	  	};
	  	
		@AfterTest
		public void afterTest() throws Exception {
			Thread.sleep(3000);
			driver.close();
	        String verificationErrorString = verificationErrors.toString();
	        if (!"".equals(verificationErrorString)) {
	            fail(verificationErrorString);
	        }
		}		
}	