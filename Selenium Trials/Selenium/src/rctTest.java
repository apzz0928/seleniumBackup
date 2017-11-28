import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;

import org.junit.*;

import static org.junit.Assert.*;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.Alert;
//import org.openqa.selenium.ie.InternetExplorerDriver;
import org.junit.runners.MethodSorters;



@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class rctTest {
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
    private static String accountKey;
    private static String startAt; 
    private static String endAt;  
    private static String startAt1; 
    private static String endAt1;  

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
        accountKey = "350238";
        startAt = "2017.06.05 00:00:00";
        endAt = "2017.07.01 00:00:00";
        startAt1 = "2017-06-05 00:00:00";
        endAt1 = "2017-07-01 00:00:00";
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
  	
  	//�������� ���� ���ΰ�ħ ���ð� ����
  	public static void refreshReady () throws Exception {
        if(TestBrowser.equals("chrome")){
        	Thread.sleep(1000);
        } else if (TestBrowser.equals("firefox")) {
            Thread.sleep(3000);        	
        } else if (TestBrowser.equals("ie")) {
        	Thread.sleep(6000);
        }
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
    
    @Test
    public void B_lang_change() throws Exception {
    	driver.get(baseUrl + "/common/locale/ko");
        assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
        assertTrue(driver.getPageSource().contains("true"));
        System.out.println("���� : Pass");
        Thread.sleep(1000);
        driver.get(baseUrl);
        Thread.sleep(1000);
	}
    
    //@Test
    public void gameMng_A_sendMail() throws Exception {
        driver.get(baseUrl + "/gmcmd/sendMailForm.ct");
        refreshReady();
        assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
        assertTrue(driver.getPageSource().contains("Ư�� �������� ������ �߼��ϴ� ����Դϴ�."));
        System.out.println("���� ���� > ���� ���� > ������ ���� : Pass");
        driver.findElement(By.xpath("//div[@id='sk']/div/a/i")).click();
        driver.findElement(By.cssSelector("ul.normal > li.selected")).click();
        js.executeScript("$('input[name=recvAccountKey]').val('350238');");
        js.executeScript("$('input[name=sendNickName]').val('HerowarzTest');");
        js.executeScript("$('input[name=mailTitle]').val('���� �����Դϴ�.');");
        js.executeScript("$('textarea[name=mailContents]').val('���� �����Դϴ�.');");
        js.executeScript("$('input[name=itemCode]').val('500001');");
        js.executeScript("$('input[name=itemCount]').val('1');");
        js.executeScript("$('input[name=gold]').val('100');");
        js.executeScript("$('input[name=reason]').val('���� �׽�Ʈ �Դϴ�.');");
        js.executeScript("$('.ac_btn_text')[1].click()");
        Thread.sleep(1000);
        assertTrue(driver.getPageSource().contains("GM ������ ȣ�� �Ǿ����ϴ�."));
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println("���� ���� > ���� ���� > ���� : Pass");
        refreshReady();
        js.executeScript("$('input[name=keyward]').val('�˻��� �Դϴ�.');");
        js.executeScript("$('.ac_btn_text')[2].click()");
        Thread.sleep(1000);
        assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
        System.out.println("���� ���� > ���� ���� > �˻� : Pass");
    }
    
    //@Test
    public void gameMng_B_systemMessage() throws Exception {
    	driver.get(baseUrl + "/gmcmd/systemMessageForm.ct");
        Thread.sleep(1000);
        assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
        assertTrue(driver.getPageSource().contains("��� ������ ä��â�� ��µ˴ϴ�."));
        System.out.println("���� ���� > systemMessage > ������ ���� : Pass");
        js.executeScript("$('.fa-circle-o')[1].click();");
        js.executeScript("$('input[name=displayMessage]').val('���� ���� �׽�Ʈ �Դϴ�.')");
        js.executeScript("$('.fa-circle-o')[3].click();");
        js.executeScript("$('.ac_btn_text')[0].click();");
        Thread.sleep(1000);
        assertTrue(driver.getPageSource().contains("GM ������ ȣ�� �Ǿ����ϴ�."));
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println("���� ���� > systemMessage > ���� : Pass");
        refreshReady();
        js.executeScript("$('input[name=keyward]').val('�˻��� �Դϴ�.');");
        js.executeScript("$('.ac_btn_text')[1].click()");
        refreshReady();
        assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
        System.out.println("���� ���� > systemMessage > �˻� : Pass");
        Thread.sleep(1000);
    }    
    //@Test
    public void gameMng_C_ingameNotice() throws Exception {
    	//���Ӱ��� > �ΰ��� ������ �̵�
        driver.get(baseUrl + "/gmcmd/ingameNotice.ct");
        Thread.sleep(1000);
        assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
        assertTrue(driver.getPageSource().contains("�ΰ��� �̺�Ʈ ������ �ݺ��� ���� �� �� �ֽ��ϴ�."));
        System.out.println("���� ���� > �ΰ��� ���� > ������ ���� : Pass");
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
        Thread.sleep(1000);
        assertTrue(driver.getPageSource().contains("�ԷµǾ����ϴ�."));
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println("���� ���� > �ΰ��� ���� > ��� > Pass");
        refreshReady();
        js.executeScript("$('.ac_btn_text')[3].click();");
        assertTrue(driver.getPageSource().contains("���� �Ǿ����ϴ�."));
        refreshReady();
        js.executeScript("$('.uid_ok_btn').click();");
        refreshReady();
        js.executeScript("$('.ac_btn_text')[3].click();");
        refreshReady();
        assertTrue(driver.getPageSource().contains("���� �Ǿ����ϴ�."));
        js.executeScript("$('.uid_ok_btn').click();");
        refreshReady();
        js.executeScript("$('.ac_btn_text')[3].click();");
        assertTrue(driver.getPageSource().contains("���� �Ǿ����ϴ�."));
        refreshReady();
        js.executeScript("$('.uid_ok_btn').click();");
        refreshReady();
        js.executeScript("$('.ac_btn_text')[4].click();");
        assertTrue(driver.getPageSource().contains("���� �Ǿ����ϴ�."));
        refreshReady();
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println("���� ���� > �ΰ��� ���� > ���� ���� > Pass");
        refreshReady();
        js.executeScript("$('.ac_btn_text')[3].click();");
        refreshReady();
        js.executeScript("$('.uid_layer_close').click();");
        System.out.println("���� ���� > �ΰ��� ���� > �����丮 Ȯ�� > Pass");
    }
    //@Test
    public void gameMng_D_kickUser() throws Exception {
    	//���Ӱ��� > kickUser�� �̵�
        driver.get(baseUrl + "/gmcmd/kickUserForm.ct");
        Thread.sleep(1000);
        assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
        System.out.println("���� ���� > kickUser > ������ ���� > Pass");
        driver.get(baseUrl + "/gmcmd/kickUserForm.ct");
        driver.findElement(By.xpath("//div[@id='sk']/div/a/i")).click();
        driver.findElement(By.cssSelector("ul.normal > li.selected")).click();
        js.executeScript("$('input[name=accountKey]').val('" + accountKey + "')");
        js.executeScript("$('input[name=forbidTime]').val('0')");
        js.executeScript("$('input[name=reason]').val('KickUser �׽�Ʈ')");
        Thread.sleep(1000);   
        driver.findElement(By.cssSelector("button.ac_btn_inner")).click();
        Thread.sleep(1000);
        assertTrue(driver.getPageSource().contains("GM ������ ȣ�� �Ǿ����ϴ�."));
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println("���� ���� > kickUser : Pass");
        Thread.sleep(1000);   
    }
    //@Test
    public void gameMng_F_mailOut() throws Exception {
    	driver.get(baseUrl + "/gmcmd/mailoutform.ct");
        refreshReady();
        assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
        System.out.println("���� ���� > ���� �뷮 �߼� > ������ ���� : Pass");
        driver.findElement(By.xpath("//div[@id='sk']/div/a/i")).click();
        driver.findElement(By.cssSelector("ul.including > li")).click();
        driver.findElement(By.name("filedata")).sendKeys("C:\\Users\\Administrator\\Downloads\\�۰�����ι�÷��.xlsx");
        driver.findElement(By.name("reason")).sendKeys("���� �뷮 �߼� �׽�Ʈ �Դϴ�.");
        driver.findElement(By.cssSelector("button.ac_btn_inner")).click();
        Thread.sleep(1000);
        assertTrue(driver.getPageSource().contains("�뷮 ���� �߼��� ���۵˴ϴ�."));
        driver.findElement(By.cssSelector("button.ac_btn.uid_ok_btn")).click();
        Thread.sleep(3000);
        assertTrue(driver.getPageSource().contains("(�߼ۿϷ�)"));
        System.out.println("���� ���� > ���� �뷮 �߼� > �߼ۿϷ� : Pass");
        driver.findElement(By.cssSelector("span.ac_btn_text.au_text_center")).click();
        Thread.sleep(1000);   
    }
    
    //@Test
    public void gameMng_G_burningEvent() throws Exception {
    	driver.get(baseUrl + "/event/burning/list.ct");
        refreshReady();
        assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
        assertTrue(driver.getPageSource().contains("�ջ�ǿ��� �������ּ���."));
        System.out.println("���� ���� > ���� �̺�Ʈ > ������ ���� : Pass");
        js.executeScript("$('.au_space_right_5 > a > div > div > div > span')[0].click();");
        refreshReady();
        js.executeScript("$('.fa-chevron-down:eq(1)').click();");
        js.executeScript("$('li[data-key=0]').click();");
        js.executeScript("$('input[name=]:eq(0)').val('" + startAt1 + "');");
        js.executeScript("$('input[name=]:eq(1)').val('" + endAt1 + "');");
        Thread.sleep(1000);
        js.executeScript("$('.ac_btn_text:eq(2)').click();");
        assertTrue(driver.getPageSource().contains("�߰� �Ǿ����ϴ�."));
        Thread.sleep(1000);
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println("���� ���� > ���� �̺�Ʈ > ��� : Pass");
        refreshReady();
        js.executeScript("$('.uid_sync_burning_conf_btn').click();");
        Thread.sleep(1000);
        assertTrue(driver.getPageSource().contains("���� ������ �����Ͻðڽ��ϱ�?"));
        js.executeScript("$('.uid_ok_btn').click();");
        Thread.sleep(1000);
        assertTrue(driver.getPageSource().contains("Ȯ���ϼ���."));
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println("���� ���� > ���� �̺�Ʈ > ����ȭ : Pass");
        refreshReady();
        js.executeScript("$('.uid_del_burning_conf_btn').click();");
        assertTrue(driver.getPageSource().contains("�̺�Ʈ�� �ٽ� ����ؾ� �մϴ�."));
        Thread.sleep(1000);
        js.executeScript("$('.uid_ok_btn').click();"); 
        Thread.sleep(1000);
        assertTrue(driver.getPageSource().contains("�̺�Ʈ�� ��� �Ǿ����ϴ�."));
        js.executeScript("$('.uid_ok_btn').click();"); 
        System.out.println("���� ���� > ���� �̺�Ʈ > ���� : Pass");
        refreshReady();
        js.executeScript("$('.uid_sync_burning_conf_btn').click();");
        Thread.sleep(1000);
        assertTrue(driver.getPageSource().contains("���� ������ �����Ͻðڽ��ϱ�?"));
        js.executeScript("$('.uid_ok_btn').click();");
        Thread.sleep(1000);
        assertTrue(driver.getPageSource().contains("Ȯ���ϼ���."));
        js.executeScript("$('.uid_ok_btn').click();");
        refreshReady();
        js.executeScript("$('.ac_container_tab_header > ul > li > a')[1].click();");
        refreshReady();
        assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
        System.out.println("���� ���� > ���� �̺�Ʈ > ���� �� : Pass");
        Thread.sleep(1000);
    }
    
    //@Test
    public void gameMng_H_getServerStatus() throws Exception {
        driver.get(baseUrl + "/cache/getServerStatus.ct");
        refreshReady();
        assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
        assertTrue(driver.getPageSource().contains("������ ���� �Ǿ� �����˴ϴ�."));
        System.out.println("���� ���� > ���� ���� > ������ ���� : Pass");
        js.executeScript("$('.ac_input:eq(1)').val('50');");
        js.executeScript("$('.fa-chevron-down:eq(1)').click();");
        js.executeScript("$('li[data-key=true]').click();");
        js.executeScript("$('.ac_input:eq(3)').val('2016.01.01 00:00:00');");
        js.executeScript("$('.ac_input:eq(4)').val('2016.01.02 00:00:00');");
        js.executeScript("$('.fa-chevron-down:eq(2)').click();");
        js.executeScript("$('li[data-key=enable]').click();");
        js.executeScript("$('.ac_btn_text:eq(0)').click();");
        Thread.sleep(2000);
        assertTrue(driver.getPageSource().contains("���������� �����Ͽ����ϴ�."));
        js.executeScript("$('.uid_ok_btn').click();");
        refreshReady();
        js.executeScript("$('.ac_input:eq(1)').val('60');");
        js.executeScript("$('.fa-chevron-down:eq(1)').click();");
        js.executeScript("$('li[data-key=false]').click();");
        js.executeScript("$('.ac_input:eq(3)').val('" + startAt + "');");
        js.executeScript("$('.ac_input:eq(4)').val('" + endAt + "');");
        js.executeScript("$('.fa-chevron-down:eq(2)').click();");
        js.executeScript("$('li[data-key=disable]').click();");
        js.executeScript("$('.ac_btn_text:eq(0)').click();");
        Thread.sleep(2000);
        assertTrue(driver.getPageSource().contains("���������� �����Ͽ����ϴ�."));
        js.executeScript("$('.uid_ok_btn').click();");
        refreshReady();
        System.out.println("���� ���� > ���� ���� > ���� ���� : Pass");
        js.executeScript("$('input[name=keyward]').val('�˻��� �Դϴ�.');");
        js.executeScript("$('.ac_btn_text')[1].click();");
        refreshReady();
        assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
        System.out.println("���� ���� > ���� ���� > �˻� : Pass");
    }
    
    //@Test
    public void gameMng_I_limitdrop() throws Exception {
    	driver.get(baseUrl + "/event/limitdrop/confUploadForm.ct");
        refreshReady();
        assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
        assertTrue(driver.getPageSource().contains("������� �ʽ��ϴ�."));
        System.out.println("���� ���� > ������ ��� ���� > ������ ���� : Pass");
        driver.findElement(By.name("filedata")).sendKeys("C:\\Users\\Administrator\\Downloads\\dropConfSample.xlsx");
        js.executeScript("$('.au_text_center:Eq(1)').click();");
        refreshReady();
        js.executeScript("$('.au_text_center:Eq(3)').click();");
        Thread.sleep(1000);
        assertTrue(driver.getPageSource().contains("���ӿ� �ݿ��˴ϴ�."));
        js.executeScript("$('.uid_ok_btn').click();");
        Thread.sleep(1000);
        assertTrue(driver.getPageSource().contains("reload�� ������ ���ӿ� �ݿ��˴ϴ�."));
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println("���� ���� > ������ ��� ���� > ���� off�� ���� : Pass");
        refreshReady();
        js.executeScript("$('.au_text_center:Eq(3)').click();");
        Thread.sleep(1000);
        assertTrue(driver.getPageSource().contains("���ӿ� �ݿ��˴ϴ�."));
        js.executeScript("$('.uid_ok_btn').click();");
        Thread.sleep(1000);
        assertTrue(driver.getPageSource().contains("reload�� ������ ���ӿ� �ݿ��˴ϴ�."));
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println("���� ���� > ������ ��� ���� > ���� on���� ���� : Pass");
        refreshReady();
        js.executeScript("$('.au_text_center:Eq(4)').click();");
        Thread.sleep(1000);
        assertTrue(driver.getPageSource().contains("���� ������ ���� �Ǿ����ϴ�."));
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println("���� ���� > ������ ��� ���� > reload : Pass");
        refreshReady();        
    }
    
    //@Test
    public void gameMng_J_ingameGuide() throws Exception {
        driver.get(baseUrl + "/gmcmd/ingameGuide/list.ct");
        refreshReady();
        assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
        System.out.println("���� ���� > �ΰ��� ���̵� > ������ ���� : Pass");
        js.executeScript("$('.ac_btn_text:eq(31)').click();");
        Thread.sleep(1000);
        js.executeScript("$('input[name=firstCategory]').prop('value', '�ΰ��� ���̵� �׽�Ʈ ��з� �Դϴ�.');");
        js.executeScript("$('input[name=secondCategory]').prop('value', '�ΰ��� ���̵� �׽�Ʈ �Һз� �Դϴ�.');");
        js.executeScript("$('.cke_wysiwyg_frame').contents().find('.cke_editable').text('���� �Է��Դϴ�.')");
        js.executeScript("$('input[name=order]').val('0');");
        js.executeScript("$('.uid_ingame_guide_save_btn').click();");
        Thread.sleep(1000);
        js.executeScript("$('.uid_ok_btn').click();");
        Thread.sleep(1000);
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println("���� ���� > �ΰ��� ���̵� > �ۼ� : Pass");
        refreshReady();
        js.executeScript("$('.ac_btn_text.au_text_center').eq(2).click();");
        Thread.sleep(1000);
        js.executeScript("$('input[name=firstCategory]').prop('value', '--- ��з� ��з� ��з� ---');");
        js.executeScript("$('input[name=secondCategory]').prop('value', '---�Һз� �Һз� �Һз�---');");
        js.executeScript("$('.cke_wysiwyg_frame').contents().find('.cke_editable').text('--- ���� ���� ���� ---')");
        js.executeScript("$('.uid_ingame_guide_save_btn').click();");
        Thread.sleep(1000);
        js.executeScript("$('.uid_ok_btn').click();");
        //����Ǿ����ϴ�.
        Thread.sleep(1000);
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println("���� ���� > �ΰ��� ���̵� > ���� : Pass");
        refreshReady();
        //�ΰ��� ���̵� ����
        js.executeScript("$('.ac_btn_text')[3].click();");
        //�����Ͻðڽ��ϱ�?
        Thread.sleep(1000);
        js.executeScript("$('.uid_ok_btn').click();");
        Thread.sleep(1000);
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println("���� ���� > �ΰ��� ���̵� > ���� : Pass");
        refreshReady();
    }
    
    //@Test
    public void gameMng_K_onlineUserCountInfo() throws Exception {
    	//���Ӱ��� > �¶��� ������ ��ȸ�� �̵�
        driver.get(baseUrl + "/gmcmd/onlineUserCountInfo.ct");
        refreshReady();
        assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
        assertTrue(driver.getPageSource().contains("�¶��� ������ ��ȸ"));
        System.out.println("���� ���� > �¶��� ������ ��ȸ : Pass");
    }
    
    //@Test
    public void gameMng_L_slangForm() throws Exception {
        driver.get(baseUrl + "/gmcmd/slangForm.ct");
        refreshReady();
        assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
        assertTrue(driver.getPageSource().contains("���� ǥ����"));
        System.out.println("���� ���� > ��Ģ�� ���� > ������ ���� : Pass");
        //��Ģ�� ���� �׽�Ʈ �� �߰�
        js.executeScript("$('textarea[name=]:eq(0)').prop('value', '��Ģ�� �߰� ���� ǥ���� �Է�â �Դϴ�.');");
        js.executeScript("$('input[name=]:eq(0)').prop('value', '��Ģ�� �߰� �ڸ�Ʈ �Է�â �Դϴ�.');");
        js.executeScript("$('textarea[name=]:eq(1)').prop('value', '��Ģ�� ��� �׽�Ʈ �޼��� �Է�â �Դϴ�.');");
        js.executeScript("$('.uid_slang_word_add_test_btn').click();");
        assertTrue(driver.getPageSource().contains("��ġ�ϴ� ������ �������� �ʽ��ϴ�."));
        System.out.println("���� ���� > ��Ģ�� ���� > ��� > �׽�Ʈ ��� : Pass");
        Thread.sleep(1000);
        js.executeScript("$('.uid_slang_word_add_btn').click();");
        Thread.sleep(1000);
        assertTrue(driver.getPageSource().contains("�߰� �Ͻðڽ��ϱ�?"));
        js.executeScript("$('.uid_cancel_btn').click();");
        js.executeScript("$('.uid_slang_word_add_btn').click();");
        Thread.sleep(1000);
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println("���� ���� > ��Ģ�� ���� > ��� : Pass");
        Thread.sleep(1000);
        js.executeScript("$('.uid_ok_btn').click();");
        //��Ģ�� ���� �׽�Ʈ �� ����
        js.executeScript("$('.uid_slang_word_list_modify_btn').last().click();");
        js.executeScript("$('textarea[name=]:eq(2)').prop('value', '��Ģ�� ���� ���� ǥ���� �Է�â �Դϴ�.');");
        js.executeScript("$('input[name=]:eq(3)').prop('value', '��Ģ�� ���� �ڸ�Ʈ �Է�â �Դϴ�.');");
        js.executeScript("$('input[name=]:eq(4)').prop('value', '��Ģ�� ���� �׽�Ʈ �޼��� �Է�â �Դϴ�.');");
        js.executeScript("$('.uid_slang_word_modify_test_btn').click();");
        assertTrue(driver.getPageSource().contains("��ġ�ϴ� ������ �������� �ʽ��ϴ�."));
        System.out.println("���� ���� > ��Ģ�� ���� > ���� > �׽�Ʈ ��� : Pass");
        Thread.sleep(1000);
        js.executeScript("$('.uid_slang_word_modify_btn').click();");
        Thread.sleep(1000);
        assertTrue(driver.getPageSource().contains("���� �����Ͻðڽ��ϱ�?"));
        js.executeScript("$('.uid_cancel_btn').click();");
        js.executeScript("$('.uid_slang_word_modify_btn').click();");
        Thread.sleep(1000);
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println("���� ���� > ��Ģ�� ���� > ���� : Pass");
        Thread.sleep(1000);
        js.executeScript("$('.uid_ok_btn').click();");
        //��Ģ�� ���� ����
        js.executeScript("$('.uid_slang_word_list_remove_btn').last().click();");
        Thread.sleep(1000);
        assertTrue(driver.getPageSource().contains("���� ���� �Ͻðڽ��ϱ�?"));
        js.executeScript("$('.uid_cancel_btn').click();");
        js.executeScript("$('.uid_slang_word_list_remove_btn').last().click();");
        Thread.sleep(1000);
        js.executeScript("$('.uid_ok_btn').click();");
        Thread.sleep(1000);
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println("���� ���� > ��Ģ�� ���� > ���� : Pass");
        brokenLinkCheck("���� ���� �ޱ� ��ũ Ȯ��", "https://rct-d-p.astorm.com/gmcmd/slangExcelDownload.ct");
        System.out.println("���� ���� > ��Ģ�� ���� > ���� ���� �ޱ� ��ũ Ȯ�� : Pass");        
        //������ ���� �׽�Ʈ �� �߰�       
        js.executeScript("$('.ac_container_tab_header > ul > li:eq(1) > a')[0].click();");
        refreshReady();
        js.executeScript("$('textarea[name=]:eq(0)').prop('value', '������ �߰� ���� ǥ���� �Է�â �Դϴ�.');");
        js.executeScript("$('input[name=]:eq(0)').prop('value', '������ �߰� �ڸ�Ʈ �Է�â �Դϴ�.');");
        js.executeScript("$('textarea[name=]:eq(1)').prop('value', '������ ��� �׽�Ʈ �޼��� �Է�â �Դϴ�.');");
        js.executeScript("$('.uid_slang_word_add_test_btn').click();");
        assertTrue(driver.getPageSource().contains("��ġ�ϴ� ������ �������� �ʽ��ϴ�."));
        System.out.println("���� ���� > ������ ���� > ��� > �׽�Ʈ ��� : Pass");
        Thread.sleep(1000);
        js.executeScript("$('.uid_slang_word_add_btn').click();");
        Thread.sleep(1000);
        assertTrue(driver.getPageSource().contains("�߰� �Ͻðڽ��ϱ�?"));
        js.executeScript("$('.uid_cancel_btn').click();");
        js.executeScript("$('.uid_slang_word_add_btn').click();");
        Thread.sleep(1000);
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println("���� ���� > ������ ���� > ��� : Pass");
        Thread.sleep(1000);
        js.executeScript("$('.uid_ok_btn').click();");
        //��Ģ�� ���� �׽�Ʈ �� ����
        js.executeScript("$('.uid_slang_word_list_modify_btn').last().click();");
        js.executeScript("$('textarea[name=]:eq(2)').prop('value', '��Ģ�� ���� ���� ǥ���� �Է�â �Դϴ�.');");
        js.executeScript("$('input[name=]:eq(3)').prop('value', '��Ģ�� ���� �ڸ�Ʈ �Է�â �Դϴ�.');");
        js.executeScript("$('input[name=]:eq(4)').prop('value', '������ ���� �׽�Ʈ �޼��� �Է�â �Դϴ�.');");
        js.executeScript("$('.uid_slang_word_modify_test_btn').click();");
        assertTrue(driver.getPageSource().contains("��ġ�ϴ� ������ �������� �ʽ��ϴ�."));
        System.out.println("���� ���� > ������ ���� > ���� > �׽�Ʈ ��� : Pass");
        Thread.sleep(1000);
        js.executeScript("$('.uid_slang_word_modify_btn').click();");
        Thread.sleep(1000);
        assertTrue(driver.getPageSource().contains("���� �����Ͻðڽ��ϱ�?"));
        js.executeScript("$('.uid_cancel_btn').click();");
        js.executeScript("$('.uid_slang_word_modify_btn').click();");
        Thread.sleep(1000);
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println("���� ���� > ������ ���� > ���� : Pass");
        Thread.sleep(1000);
        js.executeScript("$('.uid_ok_btn').click();");
        //��Ģ�� ���� ����
        js.executeScript("$('.uid_slang_word_list_remove_btn').last().click();");
        Thread.sleep(1000);
        assertTrue(driver.getPageSource().contains("���� ���� �Ͻðڽ��ϱ�?"));
        js.executeScript("$('.uid_cancel_btn').click();");
        js.executeScript("$('.uid_slang_word_list_remove_btn').last().click();");
        Thread.sleep(1000);
        js.executeScript("$('.uid_ok_btn').click();");
        Thread.sleep(1000);
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println("���� ���� > ������ ���� > ���� : Pass");
        brokenLinkCheck("���� ���� �ޱ� ��ũ Ȯ��", "https://rct-d-p.astorm.com/gmcmd/slangExcelDownload.ct");
        System.out.println("���� ���� > ������ ���� > ���� ���� �ޱ� ��ũ Ȯ�� : Pass");
    }
    
   //@Test
    public void gameMng_M_pvpSchedule() throws Exception {
        driver.get(baseUrl + "/event/pvpSchedule/settingList.ct");
        refreshReady();
        assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
        assertTrue(driver.getPageSource().contains("�⺻���� Ƚ��"));
        System.out.println("���� ���� > PvP������ ����Ʈ > ������ ���� : Pass");
        js.executeScript("$('.uid_schedule_add_btn:eq(0)').click();");
        js.executeScript("$('input[name=]:eq(5)').prop('value', '07:00:00');");
        js.executeScript("$('input[name=]:eq(6)').prop('value', '08:00:00');");
        js.executeScript("$('input[name=]:eq(7)').prop('value', '1');");
        js.executeScript("$('input[name=]:eq(8)').prop('value', '500001');");
        js.executeScript("$('input[name=]:eq(9)').prop('value', '1');");
        js.executeScript("$('.uid_schedule_save_btn').click();");
        System.out.println("���� ���� > PvP������ ����Ʈ > ��� : Pass");
        refreshReady();
        js.executeScript("$('.uid_schedule_sync_btn').click();");
        Thread.sleep(1000);
        js.executeScript("$('.uid_ok_btn').click();");
        Thread.sleep(1000);
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println("���� ���� > PvP������ ����Ʈ > ����ȭ : Pass");
        refreshReady();
        js.executeScript("$('.uid_schedule_del_btn:Eq(1)').click();");
        js.executeScript("$('.uid_schedule_save_btn').click();");
        System.out.println("���� ���� > PvP������ ����Ʈ > ���� : Pass");
        refreshReady();
        js.executeScript("$('.uid_schedule_sync_btn').click();");
        Thread.sleep(1000);
        js.executeScript("$('.uid_ok_btn').click();");
        Thread.sleep(1000);
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println("���� ���� > PvP������ ����Ʈ > ���� �� ����ȭ : Pass");
        refreshReady();
        js.executeScript("$('.ac_container_tab_header > ul > li:eq(1) > a')[0].click();");
        refreshReady();
        System.out.println("���� ���� > PvP������ ����Ʈ > �����丮 ����Ʈ : Pass");
        js.executeScript("$('.au_text_center > tr > td:eq(1) > a')[0].click();");
        refreshReady();
        assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
        System.out.println("���� ���� > PvP������ ����Ʈ > �����丮 �󼼺��� : Pass");
        js.executeScript("$('.uid_link_list_btn').click();");
        refreshReady();
    }
        
    //@Test
    public void authMng_A_authorityMenu() throws Exception {
        driver.get(baseUrl + "/authority/menugroup.ct");
        refreshReady();
        assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
        assertTrue(driver.getPageSource().contains("�޴� ����"));
        System.out.println("���� ���� > �޴� ���� > ����Ʈ ������ ���� : Pass");
        //�޴� ���� ����Ʈ ���� ���� �� �˻� Ȯ��
        js.executeScript("$('.uid_group_order_btn:eq(0) > button > div > div > div > span:eq(0)').click();");
        Thread.sleep(1000);
        js.executeScript("$('.uid_ok_btn').click();");
        refreshReady();
        js.executeScript("$('.uid_group_order_btn:eq(1) > button > div > div > div > span:eq(0)').click();");
        Thread.sleep(1000);
        js.executeScript("$('.uid_ok_btn').click();");
        refreshReady();
        System.out.println("���� ���� > �޴� ���� > ���� ���� : Pass");
        js.executeScript("$('.ac_input').prop('value', 'common')");
        js.executeScript("$('.uid_menu_search').click();");
        assertTrue(driver.getPageSource().contains("������"));
        System.out.println("���� ���� > �޴� ���� > �˻� : Pass");
        js.executeScript("$('.ac_input').prop('value', '')");
        js.executeScript("$('.uid_menu_search').click();");
        js.executeScript("$('#uid_menu_list_tbody > tr > td:eq(1) > a')[0].click();");
        refreshReady();
        assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
        System.out.println("���� ���� > �޴� ���� > �󼼺��� ������ ���� : Pass");
        js.executeScript("$('.uid_show_layer_menu_add_btn').click();");
        js.executeScript("$('div > input').prop('value', 'Test Menu1');");
        js.executeScript("$('input[name=menuURI]').val('/test.ct');");
        js.executeScript("$('.uid_layer_menu_add_btn').click();");
        Thread.sleep(1000);
        assertTrue(driver.getPageSource().contains("���ڸ� �Է� ���� �մϴ�."));
        js.executeScript("$('.uid_ok_btn').click();");
        js.executeScript("$('input[name=menuHide]').val('0');");
        js.executeScript("$('.uid_layer_menu_add_btn').click();");
        Thread.sleep(1000);
        assertTrue(driver.getPageSource().contains("���ڸ� �Է� ���� �մϴ�."));
        js.executeScript("$('.uid_ok_btn').click();");
        js.executeScript("$('input[name=menuOrder]').val('1');");
        js.executeScript("$('.uid_layer_menu_add_btn').click();");
        Thread.sleep(1000);
        assertTrue(driver.getPageSource().contains("�߰� �Ǿ����ϴ�."));
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println("���� ���� > �޴� ���� > �󼼺��� ������ > �޴� �߰� : Pass");
        refreshReady();
        js.executeScript("$('.uid_menu_order_btn:eq(2)').click();");
        Thread.sleep(1000);
        assertTrue(driver.getPageSource().contains("���� �Ǿ����ϴ�."));
        js.executeScript("$('.uid_ok_btn').click();");
        refreshReady();
        js.executeScript("$('.uid_menu_order_btn:eq(3)').click();");
        Thread.sleep(1000);
        assertTrue(driver.getPageSource().contains("���� �Ǿ����ϴ�."));
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println("���� ���� > �޴� ���� > �󼼺��� ������ > �޴� ���� ���� : Pass");
        refreshReady();
        js.executeScript("$('.uid_menu_del_btn:eq(1)').click();");
        Thread.sleep(1000);
        assertTrue(driver.getPageSource().contains("���� ���� �� �ּ���."));
        js.executeScript("$('.uid_ok_btn').click();");
        Thread.sleep(1000);
        assertTrue(driver.getPageSource().contains("���� �Ǿ����ϴ�."));
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println("���� ���� > �޴� ���� > �󼼺��� ������ > �޴� ���� : Pass");        
        refreshReady();
    }
    
    //@Test
    public void authMng_B_authorityGroupMng() throws Exception {
        driver.get(baseUrl + "/authority/authGroupList.ct");
        refreshReady();
        assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
        assertTrue(driver.getPageSource().contains("���� �׷� ����"));
        System.out.println("���� ���� > ���� �׷� ���� > ����Ʈ ������ > ������ ���� : Pass");
        //���� ���� > ���� �׷� �� ������ �̵�
        js.executeScript("$('tbody > tr:last-child > td:eq(1) > a')[0].click();");
        refreshReady();
        System.out.println("���� ���� > ���� �׷� ���� > �󼼺��� ������ > ������ ���� : Pass");
        js.executeScript("$('.ac_toggle_btn:eq(0) > i')[0].click();");
        js.executeScript("$('.uid_authgroup_menu_add_btn').click();");
        Thread.sleep(1000);
        assertTrue(driver.getPageSource().contains("�߰� �Ǿ����ϴ�."));
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println("���� ���� > ���� �׷� ���� > ����Ʈ ������ > �޴� ���� �߰� : Pass");
        refreshReady();
        js.executeScript("$('.ac_toggle_btn:eq(1) > i')[0].click();");
        js.executeScript("$('.uid_authgroup_menu_delete_btn').click();");
        Thread.sleep(1000);
        assertTrue(driver.getPageSource().contains("���� �Ǿ����ϴ�."));
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println("���� ���� > ���� �׷� ���� > ����Ʈ ������ > �޴� ���� ���� : Pass");
        refreshReady();
        js.executeScript("$('.ac_toggle_btn:eq(2) > i')[0].click();");
        js.executeScript("$('.uid_authgroup_member_add_btn').click();");
        Thread.sleep(1000);
        assertTrue(driver.getPageSource().contains("�߰� �Ǿ����ϴ�."));
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println("���� ���� > ���� �׷� ���� > ����Ʈ ������ > ����� ���� �߰� : Pass");
        refreshReady();
        js.executeScript("$('.ac_toggle_btn:eq(3) > i')[0].click();");
        js.executeScript("$('.uid_authgroup_member_delete_btn').click();");
        Thread.sleep(1000);
        assertTrue(driver.getPageSource().contains("�ش� �������� ������ �����Ͻðڽ��ϱ�?"));
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println("���� ���� > ���� �׷� ���� > ����Ʈ ������ > ����� ���� ���� : Pass");
        refreshReady();
    }
    
    //@Test 
    public void authMng_C_groupListByAdmin() throws Exception {
    	driver.get(baseUrl + "/authority/groupListByAdmin.ct");
        refreshReady();
        assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
        assertTrue(driver.getPageSource().contains("LDAP ����ڸ� �����ϼ���."));
        System.out.println("���� ���� > ������ ���� ���� > ������ ���� : Pass");
        js.executeScript("$('.fa-chevron-down').click();");
        js.executeScript("$('.normal > li:eq(3)').click();");
        refreshReady();
        assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
        assertTrue(driver.getPageSource().contains("�׽�Ʈ ���� ���� ��� ���� �׷�"));
        System.out.println("���� ���� > ������ ���� ���� : INTERNAL ���� ���Ѻ��� Pass");
        js.executeScript("$('.ac_container_tab_header > ul > li:eq(1) > a')[0].click();");
        refreshReady();
        assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
        assertTrue(driver.getPageSource().contains("LDAP ����ڸ� �����ϼ���."));
        System.out.println("���� ���� > ������ ���� ���� > LDAP�� ���� : Pass");
        js.executeScript("$('.fa-chevron-down').click();");
        js.executeScript("$('.normal > li:eq(6)').click();");
        refreshReady();
        assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
        assertTrue(driver.getPageSource().contains("�ߺ����� ������ ���ٺҰ� Ȯ�ο�"));
        System.out.println("���� ���� > ������ ���� ���� > LDAP ���� ���Ѻ��� : Pass");
    }
    
    //@Test 
    public void authMng_D_ctActionLog() throws Exception {
        driver.get(baseUrl + "/authority/ctActionLogList.ct");
        refreshReady();
        assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
        assertTrue(driver.getPageSource().contains("�˻������ �����ϴ�."));
        System.out.println("���� ���� > �׼� �α� ��ȸ > ������ ���� : Pass");
        if (TestBrowser.equals("chrome")) {
        	js.executeScript("$('.ac_btn_text').eq(6).click();");
            Thread.sleep(2000);
            assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
            System.out.println("���� ���� > �׼� �α� ��ȸ > �˻� : Pass");
            js.executeScript("$('.uid_ctactionlog_parameter:eq(0)').click();");
            handleMultipleWindows("Control Tower @ Cockpit");
            js.executeScript("$('.uid_confirm').click();");
            System.out.println("���� ���� > �׼� �α� ��ȸ > Parameter ���� : Pass");
            handleMultipleWindows("Control Tower @ reboot");
            js.executeScript("$('.uid_ctactionlog_result').click();");
            handleMultipleWindows("Control Tower @ Cockpit");
            js.executeScript("$('.uid_confirm').click();");
            System.out.println("���� ���� > �׼� �α� ��ȸ > Result ���� : Pass");
            handleMultipleWindows("Control Tower @ reboot");
        } else {
        	
        }        
    }
    
    //@Test 
    public void authMng_E_ctMember() throws Exception {
        driver.get(baseUrl + "/authority/ctMember/list.ct");
        refreshReady();
        assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
        assertTrue(driver.getPageSource().contains("CT ����� ����Ʈ"));
        System.out.println("���� ���� > CT ����� ���� > ������ ���� : Pass");
        js.executeScript("$('.uid_ctmember_add_btn').click();");
        Thread.sleep(1000);
        js.executeScript("$('input[name=id]').val('test����');");
        js.executeScript("$('input[name=name]').val('�̸��Դϴ�.');");
        js.executeScript("$('input[name=department]').val('�μ��Դϴ�.');");
        js.executeScript("$('input[name=tel]').val('010-0000-0000');");
        js.executeScript("$('input[name=email]').val('mail@astorm.co.kr');");
        js.executeScript("$('input[name=password]').val('password');");
        js.executeScript("$('input[name=passwordRe]').val('password');");
        js.executeScript("$('.uid_ctmember_save_btn').click();");
        assertTrue(driver.getPageSource().contains("���� �Ͻðڽ��ϱ�?"));
        js.executeScript("$('.uid_ok_btn').click();");
        Thread.sleep(1000);
        assertTrue(driver.getPageSource().contains("����Ǿ����ϴ�."));
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println("���� ���� > CT ����� ���� > ����� �߰� : Pass");
        Thread.sleep(1000);
        js.executeScript("$('.uid_ctmember_edit_btn:eq(0)').click();");
        Thread.sleep(1000);
        js.executeScript("$('input[name=name]').val('***** �̸� - ���� *****');");
        js.executeScript("$('input[name=department]').val('***** �μ� - ���� *****');");
        js.executeScript("$('input[name=tel]').val('***** 010-9999-9999 *****');");
        js.executeScript("$('input[name=email]').val('***** ���ϼ���@astorm.co.kr *****');");
        js.executeScript("$('.uid_ctmember_save_btn').click();");
        Thread.sleep(1000);
        assertTrue(driver.getPageSource().contains("���� �Ͻðڽ��ϱ�?"));
        js.executeScript("$('.uid_ok_btn').click();");
        Thread.sleep(1000);
        assertTrue(driver.getPageSource().contains("����Ǿ����ϴ�."));
        js.executeScript("$('.uid_ok_btn').click();");
        Thread.sleep(1000);
        System.out.println("���� ���� > CT ����� ���� > ����� ���� : Pass");
        //��й�ȣ �ʱ�ȭ
        js.executeScript("$('.uid_ctmember_initpassword_btn:eq(0)').click();");
        Thread.sleep(1000);
        assertTrue(driver.getPageSource().contains("���� ��й�ȣ�� �ʱ�ȭ"));
        js.executeScript("$('.uid_ok_btn').click();");
        Thread.sleep(1000);
        assertTrue(driver.getPageSource().contains("��й�ȣ�� �ʱ�ȭ�Ǿ����ϴ�."));
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println("���� ���� > CT ����� ���� > ��й�ȣ �ʱ�ȭ : Pass");
        Thread.sleep(1000);
        js.executeScript("$('.uid_ctmember_delete_btn:eq(0)').click();");
        Thread.sleep(1000);
        assertTrue(driver.getPageSource().contains("���� ���� �Ͻðڽ��ϱ�?"));
        js.executeScript("$('.uid_ok_btn').click();");
        Thread.sleep(1000);
        assertTrue(driver.getPageSource().contains("�����Ǿ����ϴ�."));
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println("���� ���� > CT ����� ���� > ����� ���� : Pass");
        Thread.sleep(1000);
    }
    
    @Test 
    public void gameAccountMng_A_accountSearch() throws Exception {
    	driver.get(baseUrl + "/account/gamedata/searchform.ct");
        refreshReady();
    	assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
    	assertTrue(driver.getPageSource().contains("���� �˻�"));
    	System.out.println("���� ���� ���� > ���� �˻� > ������ ���� : Pass");
    	js.executeScript("$('.fa-circle-o:eq(0)').click();");
    	js.executeScript("$('textarea[name=searchKeyword]').val('����');");
    	js.executeScript("$('#uid_account_search_form_search_btn').click();");
    	System.out.println("���� ���� ���� > ���� �˻� > �˻� : Pass");
    	refreshReady();
    	if(TestBrowser.equals("chrome")){
        	js.executeScript("$('.ac_notice > tr:eq(2) > td:eq(12) > div').click();");
        	refreshReady();
        	handleMultipleWindows("Control Tower @ Cockpit");
        	js.executeScript("$('input[name=]').prop('value', '9000115');");
        	js.executeScript("$('.au_text_center:eq(0)').click();");
        	Thread.sleep(1000);
        	assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
        	//assertFalse(driver.getPageSource().contains("�����Ͱ� �����ϴ�."));
        	assertTrue(driver.getPageSource().contains("9000115"));
        	System.out.println("���� ���� ���� > ���� �˻� > ģ����� �˾� Ȯ�� : Pass");
        	refreshReady();
        	driver.close();
        	handleMultipleWindows("Control Tower @ reboot");
    	}
    	js.executeScript("$('.uid_game_account_init:eq(1)').click();");
    	Thread.sleep(1000);
        assertTrue(driver.getPageSource().contains("���� �ʱ�ȭ �Ͻðڽ��ϱ�?"));
        js.executeScript("$('.uid_ok_btn').click();");
        Thread.sleep(1000);
        acceptAlert("���� �ʱ�ȭ �Ϸ� �޼���");
        /* ���� ������ alert���� ��ü
        assertTrue(driver.getPageSource().contains("�ʱ�ȭ �Ǿ����ϴ�."));
        js.executeScript("$('.uid_ok_btn').click();");
        */
    	refreshReady();
    	js.executeScript("$('.fa-circle-o:eq(0)').click();");
    	js.executeScript("$('textarea[name=searchKeyword]').val('����');");
    	js.executeScript("$('#uid_account_search_form_search_btn').click();");
    	Thread.sleep(1000);
    	js.executeScript("$('.ac_notice > tr:eq(2) > td:eq(1) > a')[0].click();");
    	System.out.println("���� ���� ���� > ���� �˻� : Pass");
    }
    
    @Test 
    public void gameAccountMng_B_recovery() throws Exception {
        driver.get(baseUrl + "/account/recovery.ct");
    	refreshReady();
        assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
        assertTrue(driver.getPageSource().contains("��ϵ� ���� ���� �����մϴ�."));
        System.out.println("���� ���� ���� > ���� ���� > ������ ���� : Pass");
    	
        //
    }
    
    //@Test 
    public void gameAccountMng_B_uuidTracking() throws Exception {
    	//���� ���� ���� > uuid���� ���� �̵�
        driver.get(baseUrl + "/historylog/uuidTracking.ct");
        Thread.sleep(1000);
        //�ش� �޼����� ������ Fail
        assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
        //
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