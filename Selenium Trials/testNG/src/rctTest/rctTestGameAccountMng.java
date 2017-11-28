package rctTest;

import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertTrue;
import static org.testng.AssertJUnit.fail;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;

import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.firefox.FirefoxDriver; 
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.JavascriptExecutor; //js�� ����ϱ� ���� �߰�
import org.openqa.selenium.Alert; //alert ó����
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass; 
import org.testng.annotations.Test;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class rctTestGameAccountMng {
	private static WebDriver driver;
	private static String TestBrowser;
	private static StringBuffer verificationErrors = new StringBuffer();
	private static String CHROMEDRIVER_FILE_PATH;
	private static String FIREFOXDRIVER_FILE_PATH;
	private static String INTERNETEXPLORER_FILE_PATH;
  	private static JavascriptExecutor js; 
 	private static HttpURLConnection huc;
  	private static int respCode;
    private static String baseUrl;

    @BeforeClass
    public static void setUp() throws Exception {
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
  		driver.manage().window().setSize(new Dimension(1200, 1400));
        //javaScript�� �� �� �ֵ��� ����
        js = (JavascriptExecutor) driver;        
        baseUrl = "https://rct-d-p.astorm.com";
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
    
  	//alert Ȯ�� ó��
  	public static void acceptAlert(String statusText) throws Exception {
  		try {
  	  		Alert alert=driver.switchTo().alert();
  	        String alertMessage=driver.switchTo().alert().getText();
  	        System.out.println (statusText + " : " + alertMessage);
  	        alert.accept();
  	        Thread.sleep(1000);
  		} catch (NoAlertPresentException e) {
  			e.printStackTrace();
  		}
  	}
  	
  	//�Էµ� URL ���� ���� Ȯ��
  	public static boolean brokenLinkCheck (String urlName, String urlLink){
        try {
            huc = (HttpURLConnection)(new URL(urlLink).openConnection());
            huc.setRequestMethod("HEAD");
            huc.connect();
            respCode = huc.getResponseCode();
            if(respCode >= 400 && respCode != 503){ //�ڷ�� ���̷�Ʈx �ٿ�ε� ��ũ 503���� ����ó�� �׷��� http�ڵ�� �����ְ���
            	System.out.println("***** " + urlName +" : ��ũ ���� HTTP : " + respCode + " *****");
            	fail();
            } else {
            	System.out.println(urlName +" : ��ũ ���� HTTP : " + respCode);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
		return false;
    }

  	//window ����Ī��
  	public static void handleMultipleWindows(String windowTitle) { 
  		Set<String> windows = driver.getWindowHandles(); 
  		for (String window : windows) { 
  			driver.switchTo().window(window); 
  			if (driver.getTitle().contains(windowTitle)) { 
  				return; 
			} 
		} 
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
	
  	public static void elementValueWait(String quarter, String word, String valueName){
		WebDriverWait wait = new WebDriverWait(driver, 10);
  		switch(quarter){
  		case "id":
	  		wait.until(ExpectedConditions.textToBePresentInElementValue(By.id(word), valueName));
	  		break;
  		case "class":
	  		wait.until(ExpectedConditions.textToBePresentInElementValue(By.className(word), valueName));
  			break;
  		case "name":
	  		wait.until(ExpectedConditions.textToBePresentInElementValue(By.name(word), valueName));
  			break;
  		case "css":
	  		wait.until(ExpectedConditions.textToBePresentInElementValue(By.cssSelector(word), valueName));	
  			break;
  		case "linkText1":
	  		wait.until(ExpectedConditions.textToBePresentInElementValue(By.linkText(word), valueName));	
  			break;
  		case "text":
	  		wait.until(ExpectedConditions.textToBePresentInElementValue(By.partialLinkText(word), valueName));
  			break;
  		case "tagName":
	  		wait.until(ExpectedConditions.textToBePresentInElementValue(By.tagName(word), valueName));
  			break;
  		}
	};  	

  	
  	@Test(priority = 0)
    public void Login() throws Exception {
        driver.get(baseUrl + "/login/form.ct");
        elementWait("name", "j_username"); //�ش� element ã��������  �ִ� 10�� ��� �� ���� ������ ����
        assertTrue(driver.getPageSource().contains("User ID"));
        assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));        
        System.out.println("�α��� ������ : Pass");
        js.executeScript("$('input[name=j_username]').prop('value', 'apzz0928')");
        js.executeScript("$('input[name=j_password]').prop('value', 'qordlf!@34')");
        elementValueWait("name", "j_username", "apzz0928");
        js.executeScript("$('.ac_btn_text')[0].click();");
        elementWait("id", "ac_layout_aside");
        System.out.println("�α��� : Pass"); 
    }
    
  	@Test(priority = 1)
    public void langChange() throws Exception {
    	driver.get(baseUrl + "/common/locale/ko");
        assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
        assertTrue(driver.getPageSource().contains("true"));
        System.out.println("���� : Pass");
        driver.get(baseUrl);
	}
    
  	//@Test(priority = 2)
    public void accountSearch() throws Exception {
    	driver.get(baseUrl + "/account/gamedata/searchform.ct");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");
    	assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
    	assertTrue(driver.getPageSource().contains("���� �˻�"));
    	System.out.println("���� ���� ���� > ���� �˻� > ������ ���� : Pass");
    	js.executeScript("$('.fa-circle-o:eq(0)').click();");
    	js.executeScript("$('textarea[name=searchKeyword]').val('����');");
    	js.executeScript("$('#uid_account_search_form_search_btn').click();");
    	System.out.println("���� ���� ���� > ���� �˻� > �˻� : Pass");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");
        elementWait("class", "ac_container_pagination");
    	if(TestBrowser.equals("chrome")){
        	js.executeScript("$('.ac_notice > tr:eq(2) > td:eq(12) > div').click();");
        	handleMultipleWindows("Control Tower @ Cockpit");
        	elementWait("class", "ac_initalize_area_friends_list_pop");
        	js.executeScript("$('input[name=]').prop('value', '���ܸ�');");
        	js.executeScript("$('.au_text_center:eq(0)').click();");
        	assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
        	assertTrue(driver.getPageSource().contains("�����Ͱ� �����ϴ�."));
        	assertTrue(driver.getPageSource().contains("���ܸ�"));
        	System.out.println("���� ���� ���� > ���� �˻� > ģ����� �˾� Ȯ�� : Pass");
        	driver.close();
        	handleMultipleWindows("Control Tower @ reboot");
    	}
    	js.executeScript("$('.uid_game_account_init:eq(1)').click();");
    	elementWait("class", "ac_container_message_footer");
        assertTrue(driver.getPageSource().contains("���� �ʱ�ȭ �Ͻðڽ��ϱ�?"));
        js.executeScript("$('.uid_ok_btn').click();");
    	elementWait("class", "ac_container_message_footer");
        js.executeScript("$('.uid_ok_btn').click();");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");
    	js.executeScript("$('.fa-circle-o:eq(0)').click();");
    	js.executeScript("$('textarea[name=searchKeyword]').val('����');");
    	js.executeScript("$('#uid_account_search_form_search_btn').click();");
        elementWait("class", "ac_container_pagination");
    	js.executeScript("$('.ac_notice > tr:eq(2) > td:eq(1) > a')[0].click();");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");
    	System.out.println("���� ���� ���� > ���� �˻� : Pass");
  	}
    
  	//@Test(priority = 3)
    public void recovery() throws Exception {
        driver.get(baseUrl + "/account/recovery.ct");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");
        assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
        assertTrue(driver.getPageSource().contains("��ϵ� ���� ���� �����մϴ�."));
        System.out.println("���� ���� ���� > ���� ���� > ������ ���� : Pass");
    }
    
  	//@Test(priority = 4)
    public void uuidTracking() throws Exception {
    	//���� ���� ���� > uuid���� ���� �̵�
        driver.get(baseUrl + "/historylog/uuidTracking.ct");
        //�ش� �޼����� ������ Fail
        assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
    }
  	
  	@Test(priority = 5)
    public void clanList() throws Exception {
        driver.get(baseUrl + "/clan/clanList.ct");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");
        assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
        System.out.println("Ŭ�� ���� > Ŭ�� ����Ʈ > ������ ���� : Pass");
        js.executeScript("$('.ac_container_right > div > a')[0].click();");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");
        System.out.println("Ŭ�� ���� > Ŭ�� �߰� > ������ ���� : Pass");
        js.executeScript("$('.uid_duplicate_check_btn').click();");
        elementWait("class", "type_inner_space");
        js.executeScript("$('input[name=]').val('9000115');");
        js.executeScript("$('button[type=submit]')[1].click();");
        elementWait("class", "uid_clan_name_use_btn");
        js.executeScript("$('.uid_clan_name_use_btn').click();");
        elementWait("class", "ac_container_message_footer");
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println("Ŭ�� ���� > Ŭ�� �߰� > Ŭ���� �ߺ� Ȯ�� : Pass");
        js.executeScript("$('.uid_search_clan_master_btn').click();");
        js.executeScript("$('input[name=]').val('9000115');");
        js.executeScript("$('button[type=submit]')[1].click();");
        elementWait("class", "uid_use_clan_master_btn");
        js.executeScript("$('.uid_use_clan_master_btn').click();");
        elementWait("class", "ac_container_message_footer");
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println("Ŭ�� ���� > Ŭ�� �߰� > Ŭ���� �ߺ� Ȯ�� : Pass");
        js.executeScript("$('button[type=submit]').click();");
        elementWait("class", "ac_container_message_footer");
        js.executeScript("$('.uid_ok_btn').click();");
        elementWait("class", "ac_container_message_footer");
        js.executeScript("$('.uid_ok_btn').click();");        
        System.out.println("Ŭ�� ���� > Ŭ�� �߰� > Ŭ�� �߰� �Ϸ� : Pass");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");
        js.executeScript("$('input[name=name]').val('9000115');");
        js.executeScript("$('input[name=master_account_key]').val('9000115');");
        js.executeScript("$('.ac_combo_header_cell')[3].click();");
        js.executeScript("$('li[data-key=1]').click();");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");
        System.out.println("Ŭ�� ���� > Ŭ�� ����Ʈ > Ŭ�� �˻� : Pass");
        js.executeScript("$('.uid_clan_name_link')[0].click();");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");
        System.out.println("Ŭ�� ���� > Ŭ������ > �󼼺��� ������ ���� : Pass");
        js.executeScript("$('.uid_duplicate_check_btn').click();");
        js.executeScript("$('input[name=]').val('9000116');");
        js.executeScript("$('button[type=submit]')[2].click();");
        elementWait("class", "uid_clan_name_use_btn");
        js.executeScript("$('.uid_clan_name_use_btn').click();");
        elementWait("class", "ac_container_message_footer");
        assertTrue(driver.getPageSource().contains("�ش� Ŭ������ ����Ͻðڽ��ϱ�?"));
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println("Ŭ�� ���� > Ŭ������ > Ŭ���̸� ���� : Pass");
        js.executeScript("$('.fa-chevron-down').click();");
        js.executeScript("$('li[data-key=1]').click();");
        js.executeScript("$('button[type=submit]')[1].click();");
        elementWait("class", "ac_container_message_footer");
        assertTrue(driver.getPageSource().contains("Ŭ�������� �����Ͻðڽ��ϱ�?"));
        js.executeScript("$('.uid_ok_btn').click();");
        elementWait("class", "ac_container_message_footer");
        assertTrue(driver.getPageSource().contains("���� �Ǿ����ϴ�."));
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println("Ŭ�� ���� > Ŭ������ > Ŭ������ ���� : Pass");        
        js.executeScript("$('.uid_add_clan_member_layer_btn').click();");
        js.executeScript("$('input[name=]').val('9000116');");
        js.executeScript("$('button[type=submit]')[2].click();");
        elementWait("class", "uid_add_clan_member_btn");
        js.executeScript("$('.uid_add_clan_member_btn').click();");
        assertTrue(driver.getPageSource().contains("�߰� �Ͻðڽ��ϱ�?"));
        elementWait("class", "ac_container_message_footer");
        js.executeScript("$('.uid_ok_btn').click();");
        elementWait("class", "ac_container_message_footer");
        assertTrue(driver.getPageSource().contains("�߰� �Ǿ����ϴ�."));
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println("Ŭ�� ���� > Ŭ������ > Ŭ���� �߰� : Pass");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");
        js.executeScript("$('.uid_expel_btn')[0].click();");
        elementWait("class", "ac_container_message_footer");
        assertTrue(driver.getPageSource().contains("�߹� �Ͻðڽ��ϱ�?"));
        js.executeScript("$('.uid_ok_btn').click();");
        elementWait("class", "ac_container_message_footer");
        assertTrue(driver.getPageSource().contains("�߹� �Ǿ����ϴ�."));
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println("Ŭ�� ���� > Ŭ������ > Ŭ���� �߹� : Pass");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");
        js.executeScript("$('.uid_clan_dismantling_btn').click();");
        elementWait("class", "ac_container_message_footer");
        assertTrue(driver.getPageSource().contains("Ŭ���� ��ü �Ͻðڽ��ϱ�?"));
        js.executeScript("$('.uid_ok_btn').click();");
        elementWait("class", "ac_container_message_footer");
        assertTrue(driver.getPageSource().contains("��ü �Ǿ����ϴ�."));
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println("Ŭ�� ���� > Ŭ������ > Ŭ�� ��ü : Pass");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");
        js.executeAsyncScript("$('.au_text_center').click();");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");       
        System.out.println("Ŭ�� ���� > Ŭ������ > Ŭ�� ����Ʈ �������� �̵� : Pass");
    }
    
  	//@Test(priority = 6)
    public void Logout(){
  		js.executeScript("$('.member-info > a')[0].click();");
  		elementWait("class", "uid_login_type");
  	};
    
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
