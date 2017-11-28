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
import org.openqa.selenium.By; 
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.firefox.FirefoxDriver; 
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.JavascriptExecutor; //js�� ����ϱ� ���� �߰�
import org.openqa.selenium.Alert; //alert ó����
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass; 
import org.testng.annotations.Test;



@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class rctTestAll {
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
    private static String accountKey;
    private static String startAt; 
    private static String endAt;  
    private static String startAt1; 
    private static String endAt1;  

    @BeforeClass
    public static void setUp() {
  		TestBrowser = "chrome";
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
        accountKey = "350238";
        startAt = "2017.06.05 00:00:00";
        endAt = "2018.07.01 00:00:00";
        startAt1 = "2017-06-05 00:00:00";
        endAt1 = "2018-07-01 00:00:00";
    }
 
  	//������Ʈâ �Է� �� Ȯ��
  	public static void sendKeyAlert(String statusText, String number) {
  		try {
	  		Alert alert=driver.switchTo().alert();
	        String alertMessage=driver.switchTo().alert().getText();
	        System.out.println (statusText + " : " + alertMessage);
	        alert.sendKeys(number);
	        alert.accept();
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
            	System.out.println(TestBrowser + " ***** " + urlName +" : ��ũ ���� HTTP : " + respCode + " *****");
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
        System.out.println(TestBrowser + " �α��� ������ : Pass");
        js.executeScript("$('input[name=j_username]').prop('value', 'apzz0928')");
        js.executeScript("$('input[name=j_password]').prop('value', 'qordlf!@34')");
        js.executeScript("$('.ac_btn_text')[0].click();");
        System.out.println(TestBrowser + " �α��� : Pass");
    }
    
  	@Test(priority = 1)
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
        //driver.findElement(By.xpath("//div[@id='sk']/div/a/i")).click();
        //driver.findElement(By.cssSelector("ul.normal > li.selected")).click();
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
    
  	@Test(priority = 3)
    public void systemMessage() {
    	driver.get(baseUrl + "/gmcmd/systemMessageForm.ct");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");
        assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
        assertTrue(driver.getPageSource().contains("��� ������ ä��â�� ��µ˴ϴ�."));
        System.out.println(TestBrowser + " ���� ���� > systemMessage > ������ ���� : Pass");
        js.executeScript("$('.fa-circle-o')[0].click();");
        js.executeScript("$('input[name=holdingTimeSec]').val('10');");
        js.executeScript("$('input[name=displayMessage]').val('��ġ 1�� �׽�Ʈ �Դϴ�.');");
        js.executeScript("$('.ac_btn_text')[0].click();");
        elementWait("class", "ac_container_message_footer");
        assertTrue(driver.getPageSource().contains("GM ����� ȣ�� �Ǿ����ϴ�."));
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println(TestBrowser + " ���� ���� > systemMessage > 1�� ��ġ ���� : Pass");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");        
        js.executeScript("$('.fa-circle-o')[1].click();");
        js.executeScript("$('input[name=holdingTimeSec]').val('10');");
        js.executeScript("$('input[name=displayMessage]').val('��ġ 2�� �׽�Ʈ �Դϴ�.');");
        js.executeScript("$('.ac_btn_text')[0].click();");
        elementWait("class", "ac_container_message_footer");
        assertTrue(driver.getPageSource().contains("GM ����� ȣ�� �Ǿ����ϴ�."));
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println(TestBrowser + " ���� ���� > systemMessage > 2�� ��ġ ���� : Pass");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");        
        js.executeScript("$('.fa-circle-o')[2].click();");
        js.executeScript("$('input[name=holdingTimeSec]').val('10');");
        js.executeScript("$('input[name=displayMessage]').val('��ġ 3�� �׽�Ʈ �Դϴ�.');");
        js.executeScript("$('.ac_btn_text')[0].click();");
        elementWait("class", "ac_container_message_footer");
        assertTrue(driver.getPageSource().contains("GM ����� ȣ�� �Ǿ����ϴ�."));
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println(TestBrowser + " ���� ���� > systemMessage > 3�� ��ġ ���� : Pass");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");        
        js.executeScript("$('.fa-circle-o')[3].click();");
        js.executeScript("$('input[name=holdingTimeSec]').val('10');");
        js.executeScript("$('input[name=displayMessage]').val('��ġ 4�� �׽�Ʈ �Դϴ�.');");
        js.executeScript("$('.ac_btn_text')[0].click();");
        elementWait("class", "ac_container_message_footer");
        assertTrue(driver.getPageSource().contains("GM ����� ȣ�� �Ǿ����ϴ�."));
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println(TestBrowser + " ���� ���� > systemMessage > 4�� ��ġ ���� : Pass");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");        
        js.executeScript("$('input[name=keyward]').val('�˻��� �Դϴ�.');");
        js.executeScript("$('.ac_btn_text')[1].click()");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");
        assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
        System.out.println(TestBrowser + " ���� ���� > systemMessage > �˻� : Pass");
    }    
  	//@Test(priority = 4)
    public void ingameNotice() {
    	driver.get(baseUrl + "/gmcmd/ingameNotice.ct");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");
        assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
        assertTrue(driver.getPageSource().contains("�ΰ��� �̺�Ʈ ������ �ݺ��� ���� �� �� �ֽ��ϴ�."));
        System.out.println(TestBrowser + " ���� ���� > �ΰ��� ���� > ������ ���� : Pass");
        js.executeScript("$('input[name=startAt]').val('" + startAt + "');");
        js.executeScript("$('input[name=endAt]').val('" + endAt + "');");
        for(int i = 0; i < 7; i++){
        	js.executeScript("$('.fa-square-o')[0].click()");
        }
        js.executeScript("$('input[name=repeatTime]').val('1');");
        js.executeScript("$('input[name=name]').val('�ΰ��� ���� �����Դϴ�.');");
        js.executeScript("$('input[name=desc]').val('�ΰ��� ���� �����Դϴ�.');");
        js.executeScript("$('.fa-square-o')[0].click()");
        js.executeScript("$('.fa-circle-o')[2].click();");
        js.executeScript("$('.ac_input:eq(11)').val('10')");
        js.executeScript("$('.ac_input:eq(12)').val('�׽�Ʈ ���� ����')");
        js.executeScript("$('.ac_btn_text')[2].click();");
        elementWait("class", "ac_container_message_footer");
        assertTrue(driver.getPageSource().contains("�ԷµǾ����ϴ�."));
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println(TestBrowser + " ���� ���� > �ΰ��� ���� > ��� > Pass");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");
        js.executeScript("$('.ac_btn_text')[3].click();");
        elementWait("class", "ac_container_message_footer");
        assertTrue(driver.getPageSource().contains("���� �Ǿ����ϴ�."));
        js.executeScript("$('.uid_ok_btn').click();");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");
        js.executeScript("$('.ac_btn_text')[3].click();");
        elementWait("class", "ac_container_message_footer");
        assertTrue(driver.getPageSource().contains("���� �Ǿ����ϴ�."));
        js.executeScript("$('.uid_ok_btn').click();");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");
        js.executeScript("$('.ac_btn_text')[3].click();");
        elementWait("class", "ac_container_message_footer");
        assertTrue(driver.getPageSource().contains("���� �Ǿ����ϴ�."));
        js.executeScript("$('.uid_ok_btn').click();");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");
        js.executeScript("$('.ac_btn_text')[4].click();");
        elementWait("class", "ac_container_message_footer");
        assertTrue(driver.getPageSource().contains("���� �Ǿ����ϴ�."));
        js.executeScript("$('.uid_ok_btn').click();");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");
        System.out.println(TestBrowser + " ���� ���� > �ΰ��� ���� > ���� ���� > Pass");
        js.executeScript("$('.ac_btn_text')[3].click();");
        elementWait("class", "ac_container_section_header");
        js.executeScript("$('.uid_layer_close').click();");
        System.out.println(TestBrowser + " ���� ���� > �ΰ��� ���� > �����丮 Ȯ�� > Pass");
    }
  	//@Test(priority = 5)
    public void kickUser() {
        driver.get(baseUrl + "/gmcmd/kickUserForm.ct");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");
        assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
        System.out.println(TestBrowser + " ���� ���� > kickUser > ������ ���� > Pass");
        driver.get(baseUrl + "/gmcmd/kickUserForm.ct");
        driver.findElement(By.xpath("//div[@id='sk']/div/a/i")).click();
        driver.findElement(By.cssSelector("ul.normal > li.selected")).click();
        js.executeScript("$('input[name=accountKey]').val('" + accountKey + "')");
        js.executeScript("$('input[name=forbidTime]').val('0')");
        js.executeScript("$('input[name=reason]').val('KickUser �׽�Ʈ')");
        js.executeScript("$('.uid_gmcmd_kickusercall_btn').click();");
        elementWait("class", "ac_container_message_footer");
        assertTrue(driver.getPageSource().contains("GM ����� ȣ�� �Ǿ����ϴ�."));
        js.executeScript("$('.uid_ok_btn').click();");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");
        System.out.println(TestBrowser + " ���� ���� > kickUser : Pass");
        js.executeScript("$('input[name=keyword]').val('kickUser');");
        js.executeScript("$('.uid_log_search_btn').click();");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");
        assertTrue(driver.getPageSource().contains("������ �Ұ��մϴ�."));
        System.out.println(TestBrowser + " ���� ���� > kickUser > �˻� : Pass");
    }
  	//@Test(priority = 6)
    public void mailOut() {
    	driver.get(baseUrl + "/gmcmd/mailoutform.ct");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");
        assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
        System.out.println(TestBrowser + " ���� ���� > ���� �뷮 �߼� > ������ ���� : Pass");
        driver.findElement(By.name("filedata")).sendKeys("C:\\Users\\Administrator\\Downloads\\�۰�����ι�÷��.xlsx");
        js.executeScript("$('input[name=reason]').val('���� �뷮 �߼� �׽�Ʈ �Դϴ�.');");
        js.executeScript("$('.uid_mailout_submit_btn').click();");
        elementWait("class", "ac_container_message_footer");
        assertTrue(driver.getPageSource().contains("�뷮 ���� �߼��� ���۵˴ϴ�."));
        js.executeScript("$('.uid_ok_btn').click();");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");
        assertTrue(driver.getPageSource().contains("(�߼ۿϷ�)"));
        System.out.println(TestBrowser + " ���� ���� > ���� �뷮 �߼� > �߼ۿϷ� : Pass");
        js.executeScript("$('.au_text_center').click();");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");
    }
    
  	//@Test(priority = 7)
    public void burningEvent() {
    	driver.get(baseUrl + "/event/burning/list.ct");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");
        assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
        assertTrue(driver.getPageSource().contains("�ջ�ǿ��� �������ּ���."));
        System.out.println(TestBrowser + " ���� ���� > ���� �̺�Ʈ > ������ ���� : Pass");
        js.executeScript("$('.au_space_right_5 > a > div > div > div > span')[0].click();");
        elementWait("id", "ac_layout_aside");
        js.executeScript("$('.fa-chevron-down:eq(1)').click();");
        js.executeScript("$('li[data-key=0]').click();");
        js.executeScript("$('input[name=]:eq(0)').val('" + startAt1 + "');");
        js.executeScript("$('input[name=]:eq(1)').val('" + endAt1 + "');");
        js.executeScript("$('.ac_btn_text:eq(2)').click();");
        assertTrue(driver.getPageSource().contains("�߰� �Ǿ����ϴ�."));
        elementWait("class", "ac_container_message_footer");
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println(TestBrowser + " ���� ���� > ���� �̺�Ʈ > ��� : Pass");
        System.out.println(TestBrowser + " 1��");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");
        js.executeScript("$('.uid_sync_burning_conf_btn').click();");
        elementWait("class", "ac_container_message_footer");
        assertTrue(driver.getPageSource().contains("���� ������ �����Ͻðڽ��ϱ�?"));
        js.executeScript("$('.uid_ok_btn').click();");
        elementWait("class", "ac_container_message_footer");
        assertTrue(driver.getPageSource().contains("Ȯ���ϼ���."));
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println(TestBrowser + " ���� ���� > ���� �̺�Ʈ > ����ȭ : Pass");
        System.out.println(TestBrowser + " 2��");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");
        js.executeScript("$('.uid_del_burning_conf_btn').click();");
        elementWait("class", "ac_container_message_footer");
        assertTrue(driver.getPageSource().contains("�̺�Ʈ�� �ٽ� ����ؾ� �մϴ�."));
        js.executeScript("$('.uid_ok_btn').click();"); 
        elementWait("class", "ac_container_message_footer");
        assertTrue(driver.getPageSource().contains("�̺�Ʈ�� ��� �Ǿ����ϴ�."));
        js.executeScript("$('.uid_ok_btn').click();"); 
        System.out.println(TestBrowser + " 3��");
        System.out.println(TestBrowser + " ���� ���� > ���� �̺�Ʈ > ���� : Pass");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");
        js.executeScript("$('.uid_sync_burning_conf_btn').click();");
        elementWait("class", "ac_container_message_footer");
        assertTrue(driver.getPageSource().contains("���� ������ �����Ͻðڽ��ϱ�?"));
        js.executeScript("$('.uid_ok_btn').click();");
        elementWait("class", "ac_container_message_footer");
        assertTrue(driver.getPageSource().contains("Ȯ���ϼ���."));
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println(TestBrowser + " 4��");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");
        js.executeScript("$('.ac_container_tab_header > ul > li > a')[1].click();");
        System.out.println(TestBrowser + " 5��");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");
        assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
        System.out.println(TestBrowser + " ���� ���� > ���� �̺�Ʈ > ���� �� : Pass");
    }
    
  	//@Test(priority = 8)
    public void getServerStatus() {
        driver.get(baseUrl + "/cache/getServerStatus.ct");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");
        assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
        assertTrue(driver.getPageSource().contains("������ ���� �Ǿ� �����˴ϴ�."));
        System.out.println(TestBrowser + " ���� ���� > ���� ���� > ������ ���� : Pass");
        js.executeScript("$('.ac_input:eq(1)').val('50');");
        js.executeScript("$('.fa-chevron-down:eq(1)').click();");
        js.executeScript("$('li[data-key=true]').click();");
        js.executeScript("$('.ac_input:eq(3)').val('2016.01.01 00:00:00');");
        js.executeScript("$('.ac_input:eq(4)').val('2016.01.02 00:00:00');");
        js.executeScript("$('.fa-chevron-down:eq(2)').click();");
        js.executeScript("$('li[data-key=enable]').click();");
        js.executeScript("$('.ac_btn_text:eq(0)').click();");
        elementWait("class", "ac_container_message_footer");
        assertTrue(driver.getPageSource().contains("���������� �����Ͽ����ϴ�."));
        System.out.println(TestBrowser + " 1ȸ ���� �Ϸ�");
        js.executeScript("$('.uid_ok_btn').click();");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");
        js.executeScript("$('.ac_input:eq(1)').val('60');");
        js.executeScript("$('.fa-chevron-down:eq(1)').click();");
        js.executeScript("$('li[data-key=false]').click();");
        js.executeScript("$('.ac_input:eq(3)').val('" + startAt + "');");
        js.executeScript("$('.ac_input:eq(4)').val('" + endAt + "');");
        js.executeScript("$('.fa-chevron-down:eq(2)').click();");
        js.executeScript("$('li[data-key=disable]').click();");
        js.executeScript("$('.ac_btn_text:eq(0)').click();");
        elementWait("class", "ac_container_message_footer");
        assertTrue(driver.getPageSource().contains("���������� �����Ͽ����ϴ�."));
        System.out.println(TestBrowser + " 2ȸ ���� �Ϸ�");
        js.executeScript("$('.uid_ok_btn').click();");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");
        System.out.println(TestBrowser + " ���� ���� > ���� ���� > ���� ���� : Pass");
        js.executeScript("$('input[name=keyward]').val('�˻��� �Դϴ�.');");
        js.executeScript("$('.ac_btn_text')[1].click();");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");
        assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
        System.out.println(TestBrowser + " ���� ���� > ���� ���� > �˻� : Pass");
    }
    
  	//@Test(priority = 9)
    public void limitdrop() {
    	driver.get(baseUrl + "/event/limitdrop/confUploadForm.ct");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");
        assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
        assertTrue(driver.getPageSource().contains("������� �ʽ��ϴ�."));
        System.out.println(TestBrowser + " ���� ���� > ������ ��� ���� > ������ ���� : Pass");
        driver.findElement(By.name("filedata")).sendKeys("C:\\Users\\Administrator\\Downloads\\dropConfSample.xlsx");
        js.executeScript("$('.au_text_center:Eq(1)').click();");
        System.out.println(TestBrowser + " ���� ���� > ������ ��� ���� > ���� ���ε� : Pass");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");
        js.executeScript("$('.au_text_center:Eq(3)').click();");
        elementWait("class", "ac_container_message_footer");
        assertTrue(driver.getPageSource().contains("���ӿ� �ݿ��˴ϴ�."));
        js.executeScript("$('.uid_ok_btn').click();");
        elementWait("class", "ac_container_message_footer");
        assertTrue(driver.getPageSource().contains("reload�� ������ ���ӿ� �ݿ��˴ϴ�."));
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println(TestBrowser + " ���� ���� > ������ ��� ���� > ���� off�� ���� : Pass");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");
        js.executeScript("$('.au_text_center:Eq(3)').click();");
        elementWait("class", "ac_container_message_footer");
        assertTrue(driver.getPageSource().contains("���ӿ� �ݿ��˴ϴ�."));
        js.executeScript("$('.uid_ok_btn').click();");
        elementWait("class", "ac_container_message_footer");
        assertTrue(driver.getPageSource().contains("reload�� ������ ���ӿ� �ݿ��˴ϴ�."));
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println(TestBrowser + " ���� ���� > ������ ��� ���� > ���� on���� ���� : Pass");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");
        js.executeScript("$('.au_text_center:Eq(4)').click();");
        elementWait("class", "ac_container_message_footer");
        assertTrue(driver.getPageSource().contains("���� ������ ���� �Ǿ����ϴ�."));
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println(TestBrowser + " ���� ���� > ������ ��� ���� > reload : Pass");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");     
    }
    
  	//@Test(priority = 10)
    public void ingameGuide() {
        driver.get(baseUrl + "/gmcmd/ingameGuide/list.ct");
        elementWait("id", "ac_layout_aside");
        elementWait("class", "uid_ingame_guide_add_btn");
        assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
        System.out.println(TestBrowser + " ���� ���� > �ΰ��� ���̵� > ������ ���� : Pass");
        js.executeScript("$('.ac_btn_text:eq(31)').click();");
        elementWait("class", "ac_container_layer_body");
        //Thread.sleep(500);
        js.executeScript("$('input[name=firstCategory]').prop('value', '�ΰ��� ���̵� �׽�Ʈ ��з� �Դϴ�.');");
        js.executeScript("$('input[name=secondCategory]').prop('value', '�ΰ��� ���̵� �׽�Ʈ �Һз� �Դϴ�.');");
        js.executeScript("setTimeout(function() { return; }, 2000);");
        //���̵� �ۼ� ���̾� ���� �� ������ �κ� �ε��� �����Ƿ� �ε� �Ϸ� �� ���� �Է�â�� �Է��ϵ��� ����
        js.executeScript("(function(){setTimeout(function(){$('.cke_wysiwyg_frame').contents().find('.cke_editable').text('���� �Է��Դϴ�.')}, 800);})();");
        js.executeScript("$('input[name=order]').val('0');");
        //���� �Է�â�� setTimeout���� �Է� �� �ԷµǱ����� Ŭ���ع����Ƿ� Ŭ���� �������� (��¥�� �޼��� ���̾� �ε��Ǳ������� �ִ� 10�� ��� ����) 
        js.executeScript("(function(){setTimeout(function(){$('.uid_ingame_guide_save_btn').click();}, 1500);})();");
        elementWait("class", "ac_container_message_footer");
        js.executeScript("$('.uid_ok_btn').click();");
        elementWait("class", "ac_container_message_footer");
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println(TestBrowser + " ���� ���� > �ΰ��� ���̵� > �ۼ� : Pass");
        elementWait("id", "ac_layout_aside");
        js.executeScript("$('.ac_btn_text.au_text_center').eq(2).click();");
        elementWait("class", "ac_container_layer_body");
        js.executeScript("$('input[name=firstCategory]').prop('value', '--- ��з� ��з� ��з� ---');");
        js.executeScript("$('input[name=secondCategory]').prop('value', '---�Һз� �Һз� �Һз�---');");
        js.executeScript("$('.cke_wysiwyg_frame').contents().find('.cke_editable').text('--- ���� ���� ���� ---')");
        js.executeScript("$('.uid_ingame_guide_save_btn').click();");
        elementWait("class", "ac_container_message_footer");
        js.executeScript("$('.uid_ok_btn').click();");
        elementWait("class", "ac_container_message_footer");
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println(TestBrowser + " ���� ���� > �ΰ��� ���̵� > ���� : Pass");
        elementWait("id", "ac_layout_aside");
        js.executeScript("$('.ac_btn_text')[3].click();");
        elementWait("class", "ac_container_message_footer");
        js.executeScript("$('.uid_ok_btn').click();");
        elementWait("class", "ac_container_message_footer");
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println(TestBrowser + " ���� ���� > �ΰ��� ���̵� > ���� : Pass");
        elementWait("id", "ac_layout_aside");
    }

	//@Test(priority = 11)
    public void onlineUserCountInfo() {
    	//���Ӱ��� > �¶��� ������ ��ȸ�� �̵�
        driver.get(baseUrl + "/gmcmd/onlineUserCountInfo.ct");
        elementWait("class", "ac_container_section_body");
        assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
        assertTrue(driver.getPageSource().contains("�¶��� ������ ��ȸ"));
        System.out.println(TestBrowser + " ���� ���� > �¶��� ������ ��ȸ : Pass");
    }
    
  	//@Test(priority = 12)
    public void slangForm() {
    	driver.get(baseUrl + "/gmcmd/slangForm.ct");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");
        assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
        assertTrue(driver.getPageSource().contains("���� ǥ����"));
        System.out.println(TestBrowser + " ���� ���� > ��Ģ�� ���� > ������ ���� : Pass");
        //��Ģ�� ���� �׽�Ʈ �� �߰�
        js.executeScript("$('textarea[name=]:eq(0)').prop('value', '��Ģ�� �߰� ���� ǥ���� �Է�â �Դϴ�.');");
        js.executeScript("$('input[name=]:eq(0)').prop('value', '��Ģ�� �߰� �ڸ�Ʈ �Է�â �Դϴ�.');");
        js.executeScript("$('textarea[name=]:eq(1)').prop('value', '��Ģ�� ��� �׽�Ʈ �޼��� �Է�â �Դϴ�.');");
        js.executeScript("$('.uid_slang_word_add_test_btn').click();");
        assertTrue(driver.getPageSource().contains("��ġ�ϴ� ������ �������� �ʽ��ϴ�."));
        System.out.println(TestBrowser + " ���� ���� > ��Ģ�� ���� > ��� > �׽�Ʈ ��� : Pass");
        js.executeScript("$('.uid_slang_word_add_btn').click();");
        elementWait("class", "ac_container_message_footer");
        assertTrue(driver.getPageSource().contains("�߰� �Ͻðڽ��ϱ�?"));
        js.executeScript("$('.uid_cancel_btn').click();");
        js.executeScript("$('.uid_slang_word_add_btn').click();");
        elementWait("class", "ac_container_message_footer");
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println(TestBrowser + " ���� ���� > ��Ģ�� ���� > ��� : Pass");
        elementWait("class", "ac_container_message_footer");
        js.executeScript("$('.uid_ok_btn').click();");
        //��Ģ�� ���� �׽�Ʈ �� ����
        js.executeScript("$('.uid_slang_word_list_modify_btn').last().click();");
        js.executeScript("$('textarea[name=]:eq(2)').prop('value', '��Ģ�� ���� ���� ǥ���� �Է�â �Դϴ�.');");
        js.executeScript("$('input[name=]:eq(3)').prop('value', '��Ģ�� ���� �ڸ�Ʈ �Է�â �Դϴ�.');");
        js.executeScript("$('input[name=]:eq(4)').prop('value', '��Ģ�� ���� �׽�Ʈ �޼��� �Է�â �Դϴ�.');");
        js.executeScript("$('.uid_slang_word_modify_test_btn').click();");
        assertTrue(driver.getPageSource().contains("��ġ�ϴ� ������ �������� �ʽ��ϴ�."));
        System.out.println(TestBrowser + " ���� ���� > ��Ģ�� ���� > ���� > �׽�Ʈ ��� : Pass");
        js.executeScript("$('.uid_slang_word_modify_btn').click();");
        elementWait("class", "ac_container_message_footer");
        assertTrue(driver.getPageSource().contains("���� �����Ͻðڽ��ϱ�?"));
        js.executeScript("$('.uid_cancel_btn').click();");
        js.executeScript("$('.uid_slang_word_modify_btn').click();");
        elementWait("class", "ac_container_message_footer");
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println(TestBrowser + " ���� ���� > ��Ģ�� ���� > ���� : Pass");
        elementWait("class", "ac_container_message_footer");
        js.executeScript("$('.uid_ok_btn').click();");
        //��Ģ�� ���� ����
        js.executeScript("$('.uid_slang_word_list_remove_btn').last().click();");
        elementWait("class", "ac_container_message_footer");
        assertTrue(driver.getPageSource().contains("���� ���� �Ͻðڽ��ϱ�?"));
        js.executeScript("$('.uid_cancel_btn').click();");
        js.executeScript("$('.uid_slang_word_list_remove_btn').last().click();");
        elementWait("class", "ac_container_message_footer");
        js.executeScript("$('.uid_ok_btn').click();");
        elementWait("class", "ac_container_message_footer");
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println(TestBrowser + " ���� ���� > ��Ģ�� ���� > ���� : Pass");
        brokenLinkCheck("���� ���� �ޱ� ��ũ Ȯ��", "https://rct-d-p.astorm.com/gmcmd/slangExcelDownload.ct");
        System.out.println(TestBrowser + " ���� ���� > ��Ģ�� ���� > ���� ���� �ޱ� ��ũ Ȯ�� : Pass");        
        //������ ���� �׽�Ʈ �� �߰�       
        js.executeScript("$('.ac_container_tab_header > ul > li:eq(1) > a')[0].click();");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");
        js.executeScript("$('textarea[name=]:eq(0)').prop('value', '������ �߰� ���� ǥ���� �Է�â �Դϴ�.');");
        js.executeScript("$('input[name=]:eq(0)').prop('value', '������ �߰� �ڸ�Ʈ �Է�â �Դϴ�.');");
        js.executeScript("$('textarea[name=]:eq(1)').prop('value', '������ ��� �׽�Ʈ �޼��� �Է�â �Դϴ�.');");
        js.executeScript("$('.uid_slang_word_add_test_btn').click();");
        assertTrue(driver.getPageSource().contains("��ġ�ϴ� ������ �������� �ʽ��ϴ�."));
        System.out.println(TestBrowser + " ���� ���� > ������ ���� > ��� > �׽�Ʈ ��� : Pass");
        js.executeScript("$('.uid_slang_word_add_btn').click();");
        elementWait("class", "ac_container_message_footer");
        assertTrue(driver.getPageSource().contains("�߰� �Ͻðڽ��ϱ�?"));
        js.executeScript("$('.uid_cancel_btn').click();");
        js.executeScript("$('.uid_slang_word_add_btn').click();");
        elementWait("class", "ac_container_message_footer");
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println(TestBrowser + " ���� ���� > ������ ���� > ��� : Pass");
        elementWait("class", "ac_container_message_footer");
        js.executeScript("$('.uid_ok_btn').click();");
        //��Ģ�� ���� �׽�Ʈ �� ����
        js.executeScript("$('.uid_slang_word_list_modify_btn').last().click();");
        js.executeScript("$('textarea[name=]:eq(2)').prop('value', '��Ģ�� ���� ���� ǥ���� �Է�â �Դϴ�.');");
        js.executeScript("$('input[name=]:eq(3)').prop('value', '��Ģ�� ���� �ڸ�Ʈ �Է�â �Դϴ�.');");
        js.executeScript("$('input[name=]:eq(4)').prop('value', '������ ���� �׽�Ʈ �޼��� �Է�â �Դϴ�.');");
        js.executeScript("$('.uid_slang_word_modify_test_btn').click();");
        assertTrue(driver.getPageSource().contains("��ġ�ϴ� ������ �������� �ʽ��ϴ�."));
        System.out.println(TestBrowser + " ���� ���� > ������ ���� > ���� > �׽�Ʈ ��� : Pass");
        js.executeScript("$('.uid_slang_word_modify_btn').click();");
        elementWait("class", "ac_container_message_footer");
        assertTrue(driver.getPageSource().contains("���� �����Ͻðڽ��ϱ�?"));
        js.executeScript("$('.uid_cancel_btn').click();");
        js.executeScript("$('.uid_slang_word_modify_btn').click();");
        elementWait("class", "ac_container_message_footer");
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println(TestBrowser + " ���� ���� > ������ ���� > ���� : Pass");
        elementWait("class", "ac_container_message_footer");
        js.executeScript("$('.uid_ok_btn').click();");
        //��Ģ�� ���� ����
        js.executeScript("$('.uid_slang_word_list_remove_btn').last().click();");
        elementWait("class", "ac_container_message_footer");
        assertTrue(driver.getPageSource().contains("���� ���� �Ͻðڽ��ϱ�?"));
        js.executeScript("$('.uid_cancel_btn').click();");
        js.executeScript("$('.uid_slang_word_list_remove_btn').last().click();");
        elementWait("class", "ac_container_message_footer");
        js.executeScript("$('.uid_ok_btn').click();");
        elementWait("class", "ac_container_message_footer");
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println(TestBrowser + " ���� ���� > ������ ���� > ���� : Pass");
        brokenLinkCheck("���� ���� �ޱ� ��ũ Ȯ��", "https://rct-d-p.astorm.com/gmcmd/slangExcelDownload.ct");
        System.out.println(TestBrowser + " ���� ���� > ������ ���� > ���� ���� �ޱ� ��ũ Ȯ�� : Pass");
    }
    
  	//@Test(priority = 13)
    public void pvpSchedule() {
    	driver.get(baseUrl + "/event/pvpSchedule/settingList.ct");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");
        assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
        assertTrue(driver.getPageSource().contains("�⺻���� Ƚ��"));
        System.out.println(TestBrowser + " ���� ���� > PvP������ ����Ʈ > ������ ���� : Pass");
        js.executeScript("$('.uid_schedule_add_btn:eq(0)').click();");
        js.executeScript("$('input[name=]:eq(5)').prop('value', '07:00:00');");
        js.executeScript("$('input[name=]:eq(6)').prop('value', '08:00:00');");
        js.executeScript("$('input[name=]:eq(7)').prop('value', '1');");
        js.executeScript("$('input[name=]:eq(8)').prop('value', '500001');");
        js.executeScript("$('input[name=]:eq(9)').prop('value', '1');");
        js.executeScript("$('.uid_schedule_save_btn').click();");
        System.out.println(TestBrowser + " ���� ���� > PvP������ ����Ʈ > ��� : Pass");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");
        js.executeScript("$('.uid_schedule_sync_btn').click();");
        js.executeScript("$('.uid_ok_btn').click();");
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println(TestBrowser + " ���� ���� > PvP������ ����Ʈ > ����ȭ : Pass");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");
        js.executeScript("$('.uid_schedule_del_btn:Eq(1)').click();");
        js.executeScript("$('.uid_schedule_save_btn').click();");
        System.out.println(TestBrowser + " ���� ���� > PvP������ ����Ʈ > ���� : Pass");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");
        js.executeScript("$('.uid_schedule_sync_btn').click();");
        js.executeScript("$('.uid_ok_btn').click();");
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println(TestBrowser + " ���� ���� > PvP������ ����Ʈ > ���� �� ����ȭ : Pass");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");
        js.executeScript("$('.ac_container_tab_header > ul > li:eq(1) > a')[0].click();");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");
        System.out.println(TestBrowser + " ���� ���� > PvP������ ����Ʈ > �����丮 ����Ʈ : Pass");
        js.executeScript("$('.au_text_center > tr > td:eq(1) > a')[0].click();");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");
        assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
        System.out.println(TestBrowser + " ���� ���� > PvP������ ����Ʈ > �����丮 �󼼺��� : Pass");
        js.executeScript("$('.uid_link_list_btn').click();");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");
    }
        
  	//@Test(priority = 14)
    public void authorityMenu() {
    	driver.get(baseUrl + "/authority/menugroup.ct");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");
        assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
        assertTrue(driver.getPageSource().contains("�޴� ����"));
        System.out.println(TestBrowser + " ���� ���� > �޴� ���� > ����Ʈ ������ ���� : Pass");
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
        System.out.println(TestBrowser + " ���� ���� > �޴� ���� > ���� ���� : Pass");
        js.executeScript("$('.ac_input').prop('value', 'common')");
        js.executeScript("$('.uid_menu_search').click();");
        assertTrue(driver.getPageSource().contains("������"));
        System.out.println(TestBrowser + " ���� ���� > �޴� ���� > �˻� : Pass");
        js.executeScript("$('.ac_input').prop('value', '')");
        js.executeScript("$('.uid_menu_search').click();");
        js.executeScript("$('#uid_menu_list_tbody > tr > td:eq(1) > a')[0].click();");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");
        assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
        System.out.println(TestBrowser + " ���� ���� > �޴� ���� > �󼼺��� ������ ���� : Pass");
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
        System.out.println(TestBrowser + " ���� ���� > �޴� ���� > �󼼺��� ������ > �޴� �߰� : Pass");
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
        System.out.println(TestBrowser + " ���� ���� > �޴� ���� > �󼼺��� ������ > �޴� ���� ���� : Pass");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");
        js.executeScript("$('.uid_menu_del_btn:eq(1)').click();");
        elementWait("class", "ac_container_message_footer");
        assertTrue(driver.getPageSource().contains("���� ���� �� �ּ���."));
        js.executeScript("$('.uid_ok_btn').click();");
        elementWait("class", "ac_container_message_footer");
        assertTrue(driver.getPageSource().contains("���� �Ǿ����ϴ�."));
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println(TestBrowser + " ���� ���� > �޴� ���� > �󼼺��� ������ > �޴� ���� : Pass");        
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");
    }
    
  	//@Test(priority = 15)
    public void authorityGroupMng() {
    	driver.get(baseUrl + "/authority/authGroupList.ct");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");
        assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
        assertTrue(driver.getPageSource().contains("���� �׷� ����"));
        System.out.println(TestBrowser + " ���� ���� > ���� �׷� ���� > ����Ʈ ������ > ������ ���� : Pass");
        js.executeScript("$('tbody > tr:last-child > td:eq(1) > a')[0].click();");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");
        System.out.println(TestBrowser + " ���� ���� > ���� �׷� ���� > �󼼺��� ������ > ������ ���� : Pass");
        js.executeScript("$('.ac_toggle_btn:eq(0) > i')[0].click();");
        js.executeScript("$('.uid_authgroup_menu_add_btn').click();");
        elementWait("class", "ac_container_message_footer");
        assertTrue(driver.getPageSource().contains("�߰� �Ǿ����ϴ�."));
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println(TestBrowser + " ���� ���� > ���� �׷� ���� > ����Ʈ ������ > �޴� ���� �߰� : Pass");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");
        js.executeScript("$('.ac_toggle_btn:eq(1) > i')[0].click();");
        js.executeScript("$('.uid_authgroup_menu_delete_btn').click();");
        elementWait("class", "ac_container_message_footer");
        assertTrue(driver.getPageSource().contains("���� �Ǿ����ϴ�."));
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println(TestBrowser + " ���� ���� > ���� �׷� ���� > ����Ʈ ������ > �޴� ���� ���� : Pass");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");
        js.executeScript("$('.ac_toggle_btn:eq(2) > i')[0].click();");
        js.executeScript("$('.uid_authgroup_member_add_btn').click();");
        elementWait("class", "ac_container_message_footer");
        assertTrue(driver.getPageSource().contains("�߰� �Ǿ����ϴ�."));
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println(TestBrowser + " ���� ���� > ���� �׷� ���� > ����Ʈ ������ > ����� ���� �߰� : Pass");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");
        js.executeScript("$('.ac_toggle_btn:eq(3) > i')[0].click();");
        js.executeScript("$('.uid_authgroup_member_delete_btn').click();");
        elementWait("class", "ac_container_message_footer");
        assertTrue(driver.getPageSource().contains("�ش� �������� ������ �����Ͻðڽ��ϱ�?"));
        js.executeScript("$('.uid_ok_btn').click();");
        /*
        elementWait("class", "ac_container_message_footer");
        assertTrue(driver.getPageSource().contains("���� �Ǿ����ϴ�."));
        js.executeScript("$('.uid_ok_btn').click();");
        */
        System.out.println(TestBrowser + " ���� ���� > ���� �׷� ���� > ����Ʈ ������ > ����� ���� ���� : Pass");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");
    }
    
  	//@Test(priority = 16)
    public void groupListByAdmin() {
    	driver.get(baseUrl + "/authority/groupListByAdmin.ct");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");
        assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
        assertTrue(driver.getPageSource().contains("LDAP ����ڸ� �����ϼ���."));
        System.out.println(TestBrowser + " ���� ���� > ������ ���� ���� > ������ ���� : Pass");
        js.executeScript("$('.fa-chevron-down').click();");
        js.executeScript("$('.normal > li:eq(3)').click();");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");
        assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
        assertTrue(driver.getPageSource().contains("�׽�Ʈ ���� ���� ��� ���� �׷�"));
        System.out.println(TestBrowser + " ���� ���� > ������ ���� ���� : INTERNAL ���� ���Ѻ��� Pass");
        js.executeScript("$('.ac_container_tab_header > ul > li:eq(1) > a')[0].click();");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");
        assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
        assertTrue(driver.getPageSource().contains("LDAP ����ڸ� �����ϼ���."));
        System.out.println(TestBrowser + " ���� ���� > ������ ���� ���� > LDAP�� ���� : Pass");
        js.executeScript("$('.fa-chevron-down').click();");
        js.executeScript("$('.normal > li:eq(7)').click();");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");
        assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
        assertTrue(driver.getPageSource().contains("�ߺ����� ������ ���ٺҰ� Ȯ�ο�"));
        System.out.println(TestBrowser + " ���� ���� > ������ ���� ���� > LDAP ���� ���Ѻ��� : Pass");
    }
    
  	//@Test(priority = 17)
    public void ctActionLog() {
    	driver.get(baseUrl + "/authority/ctActionLogList.ct");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");
        assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
        assertTrue(driver.getPageSource().contains("�˻������ �����ϴ�."));
        System.out.println(TestBrowser + " ���� ���� > �׼� �α� ��ȸ > ������ ���� : Pass");
        if (TestBrowser.equals("ie")) {
            System.out.println(TestBrowser + " ���� ���� > �׼� �α� ��ȸ > �˻� : *** IE�� �˻����� ���� ***");
        } else {
        	js.executeScript("$('.ac_btn_text').eq(6).click();");
            elementWait("class", "uid_ctactionlog_pagination");
            assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
            System.out.println(TestBrowser + " ���� ���� > �׼� �α� ��ȸ > �˻� : Pass");
        }
        if (TestBrowser.equals("chrome") || TestBrowser.equals("opera")) {
        	elementWait("class", "uid_ctactionlog_parameter");
        	js.executeScript("$('.uid_ctactionlog_parameter:eq(0)').click();");
            handleMultipleWindows("Control Tower @ Cockpit");
            js.executeScript("$('.uid_confirm').click();");
            System.out.println(TestBrowser + " ���� ���� > �׼� �α� ��ȸ > Parameter ���� : Pass");
            handleMultipleWindows("Control Tower @ reboot");
            js.executeScript("$('.uid_ctactionlog_result:eq(0)').click();");
            handleMultipleWindows("Control Tower @ Cockpit");
            js.executeScript("$('.uid_confirm').click();");
            System.out.println(TestBrowser + " ���� ���� > �׼� �α� ��ȸ > Result ���� : Pass");
            handleMultipleWindows("Control Tower @ reboot");
        } else if (TestBrowser.equals("ie") || TestBrowser.equals("firefox")) {
            System.out.println(TestBrowser + " ���� ���� > �׼� �α� ��ȸ > *** IE�� Firefox�� �˾� ��� ����� �����ʾƼ� Parameter & Result Ȯ���������� ***");        	
        }        
    }
    
  	//@Test(priority = 18)
    public void ctMember() {
    	driver.get(baseUrl + "/authority/ctMember/list.ct");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");
        assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
        assertTrue(driver.getPageSource().contains("CT ����� ����Ʈ"));
        System.out.println(TestBrowser + " ���� ���� > CT ����� ���� > ������ ���� : Pass");
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
        System.out.println(TestBrowser + " ���� ���� > CT ����� ���� > ����� �߰� : Pass");
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
        System.out.println(TestBrowser + " ���� ���� > CT ����� ���� > ����� ���� : Pass");
        js.executeScript("$('.uid_ctmember_initpassword_btn:eq(0)').click();");
        elementWait("class", "ac_container_message_footer");
        assertTrue(driver.getPageSource().contains("���� ��й�ȣ�� �ʱ�ȭ"));
        js.executeScript("$('.uid_ok_btn').click();");
        elementWait("class", "ac_container_message_footer");
        assertTrue(driver.getPageSource().contains("��й�ȣ�� �ʱ�ȭ�Ǿ����ϴ�."));
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println(TestBrowser + " ���� ���� > CT ����� ���� > ��й�ȣ �ʱ�ȭ : Pass");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");
        js.executeScript("$('.uid_ctmember_delete_btn:eq(0)').click();");
        elementWait("class", "ac_container_message_footer");
        assertTrue(driver.getPageSource().contains("���� ���� �Ͻðڽ��ϱ�?"));
        js.executeScript("$('.uid_ok_btn').click();");
        elementWait("class", "ac_container_message_footer");
        assertTrue(driver.getPageSource().contains("�����Ǿ����ϴ�."));
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println(TestBrowser + " ���� ���� > CT ����� ���� > ����� ���� : Pass");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");
    }
    
  	//@Test(priority = 19)
    public void accountSearch() {
    	driver.get(baseUrl + "/account/gamedata/searchform.ct");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");
    	assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
    	assertTrue(driver.getPageSource().contains("���� �˻�"));
    	System.out.println(TestBrowser + " ���� ���� ���� > ���� �˻� > ������ ���� : Pass");
    	js.executeScript("$('.fa-circle-o:eq(0)').click();");
    	js.executeScript("$('textarea[name=searchKeyword]').val('����');");
    	js.executeScript("$('#uid_account_search_form_search_btn').click();");
    	System.out.println(TestBrowser + " ���� ���� ���� > ���� �˻� > �˻� : Pass");
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
        	assertFalse(driver.getPageSource().contains("�����Ͱ� �����ϴ�."));
        	assertTrue(driver.getPageSource().contains("9000115"));
        	System.out.println(TestBrowser + " ���� ���� ���� > ���� �˻� > ģ����� �˾� Ȯ�� : Pass");
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
    	System.out.println(TestBrowser + " ���� ���� ���� > ���� �˻� : Pass");
    }
    
  	//@Test(priority = 20)
    public void recovery() {
        driver.get(baseUrl + "/account/recovery.ct");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");
        assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
        assertTrue(driver.getPageSource().contains("��ϵ� ���� ���� �����մϴ�."));
        System.out.println(TestBrowser + " ���� ���� ���� > ���� ���� > ������ ���� : Pass");
    }
    
  	//@Test(priority = 21)
    public void uuidTracking() {
    	//���� ���� ���� > uuid���� ���� �̵�
        driver.get(baseUrl + "/historylog/uuidTracking.ct");
        //�ش� �޼����� ������ Fail
        assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
    }
    
    //@Test(priority = 22)
    public void clanList() throws Exception {
        driver.get(baseUrl + "/clan/clanList.ct");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");
        assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
        System.out.println(TestBrowser + " Ŭ�� ���� > Ŭ�� ����Ʈ > ������ ���� : Pass");
        js.executeScript("$('.ac_container_right > div > a')[0].click();");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");
        System.out.println(TestBrowser + " Ŭ�� ���� > Ŭ�� �߰� > ������ ���� : Pass");
        js.executeScript("$('.uid_duplicate_check_btn').click();");
        elementWait("class", "type_inner_space");
        js.executeScript("$('input[name=]').val('9000115');");
        js.executeScript("$('button[type=submit]')[1].click();");
        elementWait("class", "uid_clan_name_use_btn");
        js.executeScript("$('.uid_clan_name_use_btn').click();");
        elementWait("class", "ac_container_message_footer");
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println(TestBrowser + " Ŭ�� ���� > Ŭ�� �߰� > Ŭ���� �ߺ� Ȯ�� : Pass");
        js.executeScript("$('.uid_search_clan_master_btn').click();");
        js.executeScript("$('input[name=]').val('9000115');");
        js.executeScript("$('button[type=submit]')[1].click();");
        elementWait("class", "uid_use_clan_master_btn");
        js.executeScript("$('.uid_use_clan_master_btn').click();");
        elementWait("class", "ac_container_message_footer");
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println(TestBrowser + " Ŭ�� ���� > Ŭ�� �߰� > Ŭ���� �ߺ� Ȯ�� : Pass");
        js.executeScript("$('button[type=submit]').click();");
        elementWait("class", "ac_container_message_footer");
        js.executeScript("$('.uid_ok_btn').click();");
        elementWait("class", "ac_container_message_footer");
        js.executeScript("$('.uid_ok_btn').click();");        
        System.out.println(TestBrowser + " Ŭ�� ���� > Ŭ�� �߰� > Ŭ�� �߰� �Ϸ� : Pass");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");
        js.executeScript("$('input[name=name]').val('9000115');");
        js.executeScript("$('input[name=master_account_key]').val('9000115');");
        js.executeScript("$('.ac_combo_header_cell')[3].click();");
        js.executeScript("$('li[data-key=1]').click();");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");
        System.out.println(TestBrowser + " Ŭ�� ���� > Ŭ�� ����Ʈ > Ŭ�� �˻� : Pass");
        js.executeScript("$('.uid_clan_name_link')[0].click();");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");
        System.out.println(TestBrowser + " Ŭ�� ���� > Ŭ������ > �󼼺��� ������ ���� : Pass");
        js.executeScript("$('.uid_duplicate_check_btn').click();");
        js.executeScript("$('input[name=]').val('9000116');");
        js.executeScript("$('button[type=submit]')[2].click();");
        elementWait("class", "uid_clan_name_use_btn");
        js.executeScript("$('.uid_clan_name_use_btn').click();");
        elementWait("class", "ac_container_message_footer");
        assertTrue(driver.getPageSource().contains("�ش� Ŭ������ ����Ͻðڽ��ϱ�?"));
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println(TestBrowser + " Ŭ�� ���� > Ŭ������ > Ŭ���̸� ���� : Pass");
        js.executeScript("$('.fa-chevron-down').click();");
        js.executeScript("$('li[data-key=1]').click();");
        js.executeScript("$('button[type=submit]')[1].click();");
        elementWait("class", "ac_container_message_footer");
        assertTrue(driver.getPageSource().contains("Ŭ�������� �����Ͻðڽ��ϱ�?"));
        js.executeScript("$('.uid_ok_btn').click();");
        elementWait("class", "ac_container_message_footer");
        assertTrue(driver.getPageSource().contains("���� �Ǿ����ϴ�."));
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println(TestBrowser + " Ŭ�� ���� > Ŭ������ > Ŭ������ ���� : Pass");        
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
        System.out.println(TestBrowser + " Ŭ�� ���� > Ŭ������ > Ŭ���� �߰� : Pass");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");
        js.executeScript("$('.uid_expel_btn')[0].click();");
        elementWait("class", "ac_container_message_footer");
        assertTrue(driver.getPageSource().contains("�߹� �Ͻðڽ��ϱ�?"));
        js.executeScript("$('.uid_ok_btn').click();");
        elementWait("class", "ac_container_message_footer");
        assertTrue(driver.getPageSource().contains("�߹� �Ǿ����ϴ�."));
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println(TestBrowser + " Ŭ�� ���� > Ŭ������ > Ŭ���� �߹� : Pass");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");
        js.executeScript("$('.uid_clan_dismantling_btn').click();");
        elementWait("class", "ac_container_message_footer");
        assertTrue(driver.getPageSource().contains("Ŭ���� ��ü �Ͻðڽ��ϱ�?"));
        js.executeScript("$('.uid_ok_btn').click();");
        elementWait("class", "ac_container_message_footer");
        assertTrue(driver.getPageSource().contains("��ü �Ǿ����ϴ�."));
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println(TestBrowser + " Ŭ�� ���� > Ŭ������ > Ŭ�� ��ü : Pass");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");
        js.executeAsyncScript("$('.au_text_center').click();");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");       
        System.out.println(TestBrowser + " Ŭ�� ���� > Ŭ������ > Ŭ�� ����Ʈ �������� �̵� : Pass");
    }
    
    
  	@Test(priority = 23)
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
