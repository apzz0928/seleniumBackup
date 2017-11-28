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
import org.openqa.selenium.opera.OperaDriver;
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
public class rctTestAuthMng {
	private static WebDriver driver;
	private static String TestBrowser;
	private static StringBuffer verificationErrors = new StringBuffer();
	private static String CHROMEDRIVER_FILE_PATH;
	private static String FIREFOXDRIVER_FILE_PATH;
	private static String INTERNETEXPLORER_FILE_PATH;
	private static String OPERA_FILE_PATH;
  	private static JavascriptExecutor js; 
 	private static HttpURLConnection huc;
  	private static int respCode;
    private static String baseUrl;

    @BeforeClass
    public static void setUp() throws Exception {
  		TestBrowser = "opera";
  		CHROMEDRIVER_FILE_PATH = "D:/Selenium/driver/chromedriver.exe"; //ũ�� ����̹� ���� ���
  		FIREFOXDRIVER_FILE_PATH = "D:/Selenium/driver/geckodriver.exe"; //���̾����� ����̹� ���� ���
  		INTERNETEXPLORER_FILE_PATH = "D:/Selenium/driver/IEDriverServer32.exe"; //IE ����̹� ���� ���  		
  		OPERA_FILE_PATH = "D:/Selenium/driver/operadriver.exe";
  		
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
  		} else if (TestBrowser.equals("opera")) {
  			System.setProperty("webdriver.opera.driver", OPERA_FILE_PATH);
  	  		driver = new OperaDriver();
  		}
  		
  		driver.manage().window().setSize(new Dimension(1200, 1400));
        //javaScript�� �� �� �ֵ��� ����
        js = (JavascriptExecutor) driver;        
        baseUrl = "https://rct-d-p.astorm.com";
    }
 
  	//������Ʈâ �Է� �� Ȯ��
  	public static void sendKeyAlert(String statusText, String number) throws Exception {
  		try {
  			driver.wait();
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
    public void Login() {
        driver.get(baseUrl + "/login/form.ct");
  		elementWait("class", "uid_login_type");
        assertTrue(driver.getPageSource().contains("User ID"));
        assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));        
        System.out.println("�α��� ������ : Pass");
        js.executeScript("$('input[name=j_username]').prop('value', 'apzz0928')");
        js.executeScript("$('input[name=j_password]').prop('value', 'qordlf!@34')");
        js.executeScript("$('.ac_btn_text')[0].click();");
        System.out.println("�α��� : Pass");
    }
    
  	@Test(priority = 1)
    public void langChange(){
    	driver.get(baseUrl + "/common/locale/ko");
        assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
        assertTrue(driver.getPageSource().contains("true"));
        System.out.println("���� : Pass");
        driver.get(baseUrl);
	}
    
  	//@Test(priority = 2)
    public void authorityMenu() {
        driver.get(baseUrl + "/authority/menugroup.ct");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");
        assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
        assertTrue(driver.getPageSource().contains("�޴� ����"));
        System.out.println("���� ���� > �޴� ���� > ����Ʈ ������ ���� : Pass");
        js.executeScript("$('.uid_group_order_btn:eq(0) > button > div > div > div > span:eq(0)').click();");
        elementWait("class", "ac_container_message_footer");
        js.executeScript("$('.uid_ok_btn').click();");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");
        js.executeScript("$('.uid_group_order_btn:eq(1) > button > div > div > div > span:eq(0)').click();");
        elementWait("class", "ac_container_message_footer");
        js.executeScript("$('.uid_ok_btn').click();");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");
        System.out.println("���� ���� > �޴� ���� > ���� ���� : Pass");
        js.executeScript("$('.ac_input').prop('value', 'common')");
        js.executeScript("$('.uid_menu_search').click();");
        assertTrue(driver.getPageSource().contains("������"));
        System.out.println("���� ���� > �޴� ���� > �˻� : Pass");
        js.executeScript("$('.ac_input').prop('value', '')");
        js.executeScript("$('.uid_menu_search').click();");
        js.executeScript("$('#uid_menu_list_tbody > tr > td:eq(1) > a')[0].click();");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");
        assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
        System.out.println("���� ���� > �޴� ���� > �󼼺��� ������ ���� : Pass");
        js.executeScript("$('.uid_show_layer_menu_add_btn').click();");
        js.executeScript("$('div > input').prop('value', 'Test Menu1');");
        js.executeScript("$('input[name=menuURI]').val('/test.ct');");
        js.executeScript("$('.uid_layer_menu_add_btn').click();");
        elementWait("class", "ac_container_message_footer");
        assertTrue(driver.getPageSource().contains("���ڸ� �Է� ���� �մϴ�."));
        js.executeScript("$('.uid_ok_btn').click();");
        js.executeScript("$('input[name=menuHide]').val('0');");
        js.executeScript("$('.uid_layer_menu_add_btn').click();");
        elementWait("class", "ac_container_message_footer");
        assertTrue(driver.getPageSource().contains("���ڸ� �Է� ���� �մϴ�."));
        js.executeScript("$('.uid_ok_btn').click();");
        js.executeScript("$('input[name=menuOrder]').val('1');");
        js.executeScript("$('.uid_layer_menu_add_btn').click();");
        elementWait("class", "ac_container_message_footer");
        assertTrue(driver.getPageSource().contains("�߰� �Ǿ����ϴ�."));
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println("���� ���� > �޴� ���� > �󼼺��� ������ > �޴� �߰� : Pass");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");
        js.executeScript("$('.uid_menu_order_btn:eq(2)').click();");
        elementWait("class", "ac_container_message_footer");
        assertTrue(driver.getPageSource().contains("���� �Ǿ����ϴ�."));
        js.executeScript("$('.uid_ok_btn').click();");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");
        js.executeScript("$('.uid_menu_order_btn:eq(3)').click();");
        elementWait("class", "ac_container_message_footer");
        assertTrue(driver.getPageSource().contains("���� �Ǿ����ϴ�."));
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println("���� ���� > �޴� ���� > �󼼺��� ������ > �޴� ���� ���� : Pass");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");
        js.executeScript("$('.uid_menu_del_btn:eq(1)').click();");
        elementWait("class", "ac_container_message_footer");
        assertTrue(driver.getPageSource().contains("���� ���� �� �ּ���."));
        js.executeScript("$('.uid_ok_btn').click();");
        elementWait("class", "ac_container_message_footer");
        assertTrue(driver.getPageSource().contains("���� �Ǿ����ϴ�."));
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println("���� ���� > �޴� ���� > �󼼺��� ������ > �޴� ���� : Pass");        
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");
    }
    
  	//@Test(priority = 3)
    public void authorityGroupMng() {
        driver.get(baseUrl + "/authority/authGroupList.ct");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");
        assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
        assertTrue(driver.getPageSource().contains("���� �׷� ����"));
        System.out.println("���� ���� > ���� �׷� ���� > ����Ʈ ������ > ������ ���� : Pass");
        js.executeScript("$('tbody > tr:last-child > td:eq(1) > a')[0].click();");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");
        System.out.println("���� ���� > ���� �׷� ���� > �󼼺��� ������ > ������ ���� : Pass");
        js.executeScript("$('.ac_toggle_btn:eq(0) > i')[0].click();");
        js.executeScript("$('.uid_authgroup_menu_add_btn').click();");
        elementWait("class", "ac_container_message_footer");
        assertTrue(driver.getPageSource().contains("�߰� �Ǿ����ϴ�."));
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println("���� ���� > ���� �׷� ���� > ����Ʈ ������ > �޴� ���� �߰� : Pass");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");
        js.executeScript("$('.ac_toggle_btn:eq(1) > i')[0].click();");
        js.executeScript("$('.uid_authgroup_menu_delete_btn').click();");
        elementWait("class", "ac_container_message_footer");
        assertTrue(driver.getPageSource().contains("���� �Ǿ����ϴ�."));
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println("���� ���� > ���� �׷� ���� > ����Ʈ ������ > �޴� ���� ���� : Pass");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");
        js.executeScript("$('.ac_toggle_btn:eq(2) > i')[0].click();");
        js.executeScript("$('.uid_authgroup_member_add_btn').click();");
        elementWait("class", "ac_container_message_footer");
        assertTrue(driver.getPageSource().contains("�߰� �Ǿ����ϴ�."));
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println("���� ���� > ���� �׷� ���� > ����Ʈ ������ > ����� ���� �߰� : Pass");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");
        js.executeScript("$('.ac_toggle_btn:eq(3) > i')[0].click();");
        js.executeScript("$('.uid_authgroup_member_delete_btn').click();");
        elementWait("class", "ac_container_message_footer");
        assertTrue(driver.getPageSource().contains("�ش� �������� ������ �����Ͻðڽ��ϱ�?"));
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println("���� ���� > ���� �׷� ���� > ����Ʈ ������ > ����� ���� ���� : Pass");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");
    }
    
  	//@Test(priority = 4)
    public void groupListByAdmin()  {
    	driver.get(baseUrl + "/authority/groupListByAdmin.ct");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");
        assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
        assertTrue(driver.getPageSource().contains("LDAP ����ڸ� �����ϼ���."));
        System.out.println("���� ���� > ������ ���� ���� > ������ ���� : Pass");
        js.executeScript("$('.fa-chevron-down').click();");
        js.executeScript("$('.normal > li:eq(3)').click();");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");
        assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
        assertTrue(driver.getPageSource().contains("�׽�Ʈ ���� ���� ��� ���� �׷�"));
        System.out.println("���� ���� > ������ ���� ���� : INTERNAL ���� ���Ѻ��� Pass");
        js.executeScript("$('.ac_container_tab_header > ul > li:eq(1) > a')[0].click();");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");
        assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
        assertTrue(driver.getPageSource().contains("LDAP ����ڸ� �����ϼ���."));
        System.out.println("���� ���� > ������ ���� ���� > LDAP�� ���� : Pass");
        js.executeScript("$('.fa-chevron-down').click();");
        js.executeScript("$('.normal > li:eq(7)').click();");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");
        assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
        assertTrue(driver.getPageSource().contains("�ߺ����� ������ ���ٺҰ� Ȯ�ο�"));
        System.out.println("���� ���� > ������ ���� ���� > LDAP ���� ���Ѻ��� : Pass");
    }
    
  	//@Test(priority = 5)
    public void ctActionLog() {
        driver.get(baseUrl + "/authority/ctActionLogList.ct");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");
        assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
        assertTrue(driver.getPageSource().contains("�˻������ �����ϴ�."));
        System.out.println("���� ���� > �׼� �α� ��ȸ > ������ ���� : Pass");
        if (TestBrowser.equals("ie")) {
            System.out.println("���� ���� > �׼� �α� ��ȸ > �˻� : *** IE�� �˻����� ���� ***");
        } else {
        	js.executeScript("$('.ac_btn_text').eq(6).click();");
            elementWait("class", "uid_ctactionlog_pagination");
            assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
            System.out.println("���� ���� > �׼� �α� ��ȸ > �˻� : Pass");
        }
        if (TestBrowser.equals("chrome") || TestBrowser.equals("opera")) {
        	elementWait("class", "uid_ctactionlog_parameter");
        	js.executeScript("$('.uid_ctactionlog_parameter:eq(0)').click();");
            handleMultipleWindows("Control Tower @ Cockpit");
            js.executeScript("$('.uid_confirm').click();");
            System.out.println("���� ���� > �׼� �α� ��ȸ > Parameter ���� : Pass");
            handleMultipleWindows("Control Tower @ reboot");
            js.executeScript("$('.uid_ctactionlog_result:eq(0)').click();");
            handleMultipleWindows("Control Tower @ Cockpit");
            js.executeScript("$('.uid_confirm').click();");
            System.out.println("���� ���� > �׼� �α� ��ȸ > Result ���� : Pass");
            handleMultipleWindows("Control Tower @ reboot");
        } else if (TestBrowser.equals("ie") || TestBrowser.equals("firefox")) {
            System.out.println("���� ���� > �׼� �α� ��ȸ > *** IE�� Firefox�� �˾� ��� ����� �����ʾƼ� Parameter & Result Ȯ���������� ***");        	
        }        
    }
    
  	//@Test(priority = 6)
    public void ctMember() {
        driver.get(baseUrl + "/authority/ctMember/list.ct");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");
        assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
        assertTrue(driver.getPageSource().contains("CT ����� ����Ʈ"));
        System.out.println("���� ���� > CT ����� ���� > ������ ���� : Pass");
        js.executeScript("$('.uid_ctmember_add_btn').click();");
        elementWait("class", "au_space_top_0");
        js.executeScript("$('input[name=id]').val('test����');");
        js.executeScript("$('input[name=name]').val('�̸��Դϴ�.');");
        js.executeScript("$('input[name=department]').val('�μ��Դϴ�.');");
        js.executeScript("$('input[name=tel]').val('010-0000-0000');");
        js.executeScript("$('input[name=email]').val('mail@astorm.co.kr');");
        js.executeScript("$('input[name=password]').val('password');");
        js.executeScript("$('input[name=passwordRe]').val('password');");
        js.executeScript("$('.uid_ctmember_save_btn').click();");
        elementWait("class", "ac_container_message_footer");
        assertTrue(driver.getPageSource().contains("���� �Ͻðڽ��ϱ�?"));
        js.executeScript("$('.uid_ok_btn').click();");
        elementWait("class", "ac_container_message_footer");
        assertTrue(driver.getPageSource().contains("����Ǿ����ϴ�."));
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println("���� ���� > CT ����� ���� > ����� �߰� : Pass");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");
        js.executeScript("$('.uid_ctmember_edit_btn:eq(0)').click();");
        elementWait("class", "au_space_top_0");
        js.executeScript("$('input[name=name]').val('***** �̸� - ���� *****');");
        js.executeScript("$('input[name=department]').val('***** �μ� - ���� *****');");
        js.executeScript("$('input[name=tel]').val('***** 010-9999-9999 *****');");
        js.executeScript("$('input[name=email]').val('***** ���ϼ���@astorm.co.kr *****');");
        js.executeScript("$('.uid_ctmember_save_btn').click();");
        elementWait("class", "ac_container_message_footer");
        assertTrue(driver.getPageSource().contains("���� �Ͻðڽ��ϱ�?"));
        js.executeScript("$('.uid_ok_btn').click();");
        elementWait("class", "ac_container_message_footer");
        assertTrue(driver.getPageSource().contains("����Ǿ����ϴ�."));
        js.executeScript("$('.uid_ok_btn').click();");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");
        System.out.println("���� ���� > CT ����� ���� > ����� ���� : Pass");
        js.executeScript("$('.uid_ctmember_initpassword_btn:eq(0)').click();");
        elementWait("class", "ac_container_message_footer");
        assertTrue(driver.getPageSource().contains("���� ��й�ȣ�� �ʱ�ȭ"));
        js.executeScript("$('.uid_ok_btn').click();");
        elementWait("class", "ac_container_message_footer");
        assertTrue(driver.getPageSource().contains("��й�ȣ�� �ʱ�ȭ�Ǿ����ϴ�."));
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println("���� ���� > CT ����� ���� > ��й�ȣ �ʱ�ȭ : Pass");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");
        js.executeScript("$('.uid_ctmember_delete_btn:eq(0)').click();");
        elementWait("class", "ac_container_message_footer");
        assertTrue(driver.getPageSource().contains("���� ���� �Ͻðڽ��ϱ�?"));
        js.executeScript("$('.uid_ok_btn').click();");
        elementWait("class", "ac_container_message_footer");
        assertTrue(driver.getPageSource().contains("�����Ǿ����ϴ�."));
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println("���� ���� > CT ����� ���� > ����� ���� : Pass");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");
    }
    
  	@Test(priority = 7)
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
