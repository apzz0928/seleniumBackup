import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertTrue;
import static org.testng.AssertJUnit.fail;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;	
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;	

public class SampleTest4_InternetExplorer {		
	    private static WebDriver driver;	
		private static String TestBrowser;
		private static JavascriptExecutor js;
		private static String baseUrl, nodeURL;
		private static StringBuffer verificationErrors = new StringBuffer();
		
		@BeforeClass
		public void beforeTest() throws MalformedURLException {	
	  		TestBrowser = "ie";
	  		
	  		if(TestBrowser.equals("chrome")){
	  			baseUrl = "https://rct-d-p.astorm.com";
		        nodeURL = "http://10.10.105.228:5555/wd/hub";
		        
				DesiredCapabilities cap = DesiredCapabilities.chrome();
				cap.setBrowserName("chrome");
				cap.setPlatform(Platform.WINDOWS);
				driver = new RemoteWebDriver(new URL(nodeURL), cap);
	  		} else if (TestBrowser.equals("firefox")) {
	  			baseUrl = "https://rct-d-p.astorm.com";
		        nodeURL = "http://10.10.105.228:5556/wd/hub";
		        
				DesiredCapabilities cap = DesiredCapabilities.firefox();
				cap.setBrowserName("firefox");
				cap.setPlatform(Platform.WINDOWS);
				driver = new RemoteWebDriver(new URL(nodeURL), cap);
	  		} else if (TestBrowser.equals("ie")) {
	  			baseUrl = "https://rct-d-p.astorm.com";
		        nodeURL = "http://10.10.105.228:5557/wd/hub";
		        
				DesiredCapabilities cap = DesiredCapabilities.internetExplorer();
				cap.setBrowserName("internet explorer");
				cap.setPlatform(Platform.WINDOWS);
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
		public void Login() {
	        driver.get(baseUrl + "/login/form.ct");
	  		elementWait("class", "uid_login_type");
	        assertTrue(driver.getPageSource().contains("User ID"));
	        assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));        
	        System.out.println(TestBrowser +  " �α��� ������ : Pass");
	        js.executeScript("$('input[name=j_username]').prop('value', 'apzz0928')");
	        js.executeScript("$('input[name=j_password]').prop('value', 'qordlf!@34')");
	        js.executeScript("$('.ac_btn_text')[0].click();");
	        System.out.println(TestBrowser + " �α��� : Pass");    
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");   
	    }
	  	//@Test(priority = 1)
	    public void langChange() {
	    	driver.get(baseUrl + "/common/locale/ko");
	        assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
	        assertTrue(driver.getPageSource().contains("true"));
	        System.out.println(TestBrowser + " ���� : Pass");
	        driver.get(baseUrl);
		}
	    
	  	//@Test(priority = 2)
	    public void sendMail() {
	        driver.get(baseUrl + "/gmcmd/sendMailForm.ct");
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	        assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
	        assertTrue(driver.getPageSource().contains("Ư�� �������� ������ �߼��ϴ� ����Դϴ�."));
	        System.out.println(TestBrowser + " ���� ���� > ���� ���� > ������ ���� : Pass");
	        js.executeScript("$('input[name=recvAccountKey]').val('350238');");
	        js.executeScript("$('input[name=sendNickName]').val('HerowarzTest');");
	        js.executeScript("$('input[name=mailTitle]').val('���� �����Դϴ�.');");
	        js.executeScript("$('textarea[name=mailContents]').val('���� �����Դϴ�.');");
	        js.executeScript("$('input[name=itemCode]').val('500001');");
	        js.executeScript("$('input[name=itemCount]').val('1');");
	        js.executeScript("$('input[name=gold]').val('100');");
	        js.executeScript("$('input[name=reason]').val('���� �׽�Ʈ �Դϴ�.');");
	        js.executeScript("$('.ac_btn_text')[1].click()");
	        elementWait("class", "ac_container_message_body");
	        assertTrue(driver.getPageSource().contains("GM ����� ȣ�� �Ǿ����ϴ�."));
	        js.executeScript("$('.uid_ok_btn').click();");
	        System.out.println(TestBrowser + " ���� ���� > ���� ���� > ���� : Pass");
	        elementWait("name", "keyword");
	        js.executeScript("$('input[name=keyward]').val('�˻��� �Դϴ�.');");
	        js.executeScript("$('.ac_btn_text')[2].click()");
	        elementWait("name", "keyword");
	        assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
	        System.out.println(TestBrowser + " ���� ���� > ���� ���� > �˻� : Pass");
	    }

		@AfterClass
		public void afterTest() throws Exception {
			Thread.sleep(3000);
			driver.close();	
	        String verificationErrorString = verificationErrors.toString();
	        if (!"".equals(verificationErrorString)) {
	            fail(verificationErrorString);
	        }
		}			
}	