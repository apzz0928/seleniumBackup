import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertTrue;
import static org.testng.AssertJUnit.fail;

import java.io.IOException;
import java.net.HttpURLConnection;
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
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;	



public class rctTestGameMng_firefox {		
	    private static WebDriver driver;
		private static String TestBrowser;
		private static JavascriptExecutor js;
		private static String baseUrl, nodeURL;
		private static StringBuffer verificationErrors = new StringBuffer();
		private static HttpURLConnection huc;
	  	private static int respCode;
	    private static String startAt; 
	    private static String endAt;  
	    private static String accountKey;
		
		@BeforeClass
		public void beforeTest() throws MalformedURLException {	
	  		TestBrowser = "firefox";
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
	  		} else if (TestBrowser.equals("opera")) {
	  			baseUrl = "https://rct-d-p.astorm.com";
		        nodeURL = "http://10.10.105.228:5557/wd/hub";		        
				DesiredCapabilities cap = DesiredCapabilities.operaBlink();
				cap.setBrowserName("operaBlink");
				cap.setPlatform(Platform.WINDOWS);
	  		} else if (TestBrowser.equals("ie")) {
	  			baseUrl = "https://rct-d-p.astorm.com";
		        nodeURL = "http://10.10.105.228:5558/wd/hub";		        
				DesiredCapabilities cap = DesiredCapabilities.internetExplorer();
				cap.setBrowserName("internet explorer");
				cap.setPlatform(Platform.WINDOWS);
	  		}
	  		//driver.manage().window().setSize(new Dimension(700, 900));
	  		driver.manage().window().maximize();
	  		js = (JavascriptExecutor) driver;   
	        startAt = "2017.06.05 00:00:00";
	        endAt = "2018.07.01 00:00:00";
	        accountKey = "9000115";
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
		}
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
		}
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
	  	
		@Test(priority = 0)
		public void Login() {
	        driver.get(baseUrl + "/login/form.ct");
	  		elementWait("class", "uid_login_type");
	        assertTrue(driver.getPageSource().contains("User ID"));
	        assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));        
	        System.out.println(TestBrowser + " �α��� ������ : Pass");
	        js.executeScript("$('input[name=j_username]').prop('value', 'apzz0928');");
	        js.executeScript("$('input[name=j_password]').prop('value', 'qordlf!@34');");
	        js.executeScript("$('.ac_btn_text')[0].click();");
	        System.out.println(TestBrowser + " �α��� : Pass");    
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	    }
	  	@Test(priority = 1)
	    public void langChange() {
	    	driver.get(baseUrl + "/common/locale/ko");
	        assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
	        if(TestBrowser.equals("firefox")){
	        	elementWait("class", "numberCell");
	        } else {
		        assertTrue(driver.getPageSource().contains("true"));	        	
	        }
	        System.out.println(TestBrowser + " ���� : Pass");
	        driver.get(baseUrl);
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
		}
	  	@Test(priority = 2)
	    public void sendMail() {
	        driver.get(baseUrl + "/gmcmd/sendMailForm.ct");
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	        assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
	        assertTrue(driver.getPageSource().contains("Ư�� �������� ������ �߼��ϴ� ����Դϴ�."));
	        System.out.println(TestBrowser + " ���� ���� > ���� ���� > ������ ���� : Pass");
	        js.executeScript("$('input[name=recvAccountKey]').val('350238');");
	        js.executeScript("$('input[name=sendNickName]').val('HerowarzTest');");
	        js.executeScript("$('input[name=mailTitle]').val('" + TestBrowser + " ���� �����Դϴ�.');");
	        js.executeScript("$('textarea[name=mailContents]').val('" + TestBrowser + " ���� �����Դϴ�.');");
	        js.executeScript("$('input[name=itemCode]').val('500001');");
	        js.executeScript("$('input[name=itemCount]').val('1');");
	        js.executeScript("$('input[name=gold]').val('100');");
	        js.executeScript("$('input[name=reason]').val('���� �׽�Ʈ �Դϴ�.');");
	        js.executeScript("$('.ac_btn_text')[1].click()");
	        elementWait("class", "ac_container_message_body");
	        assertTrue(driver.getPageSource().contains("GM ������ ȣ�� �Ǿ����ϴ�."));
	        js.executeScript("$('.uid_ok_btn').click();");
	        System.out.println(TestBrowser + " ���� ���� > ���� ���� > ���� : Pass");
	        elementWait("name", "keyword");
	        js.executeScript("$('input[name=keyward]').val('�˻��� �Դϴ�.');");
	        js.executeScript("$('.ac_btn_text')[2].click()");
	        elementWait("name", "keyword");
	        assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
	        System.out.println(TestBrowser + " ���� ���� > ���� ���� > �˻� : Pass");
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
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
	        js.executeScript("$('input[name=displayMessage]').val('" + TestBrowser + " ��ġ 1�� �׽�Ʈ �Դϴ�.');");
	        js.executeScript("$('.ac_btn_text')[0].click();");
	        elementWait("class", "ac_container_message_footer");
	        assertTrue(driver.getPageSource().contains("GM ������ ȣ�� �Ǿ����ϴ�."));
	        js.executeScript("$('.uid_ok_btn').click();");
	        System.out.println(TestBrowser + " ���� ���� > systemMessage > 1�� ��ġ ���� : Pass");
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	        elementWait("class", "uid_location");
	        elementWait("class", "fa-circle-o");
	        js.executeScript("$('.fa-circle-o')[1].click();");
	        js.executeScript("$('input[name=holdingTimeSec]').val('10');");
	        js.executeScript("$('input[name=displayMessage]').val('" + TestBrowser + " ��ġ 2�� �׽�Ʈ �Դϴ�.');");
	        js.executeScript("$('.ac_btn_text')[0].click();");
	        elementWait("class", "ac_container_message_footer");
	        assertTrue(driver.getPageSource().contains("GM ������ ȣ�� �Ǿ����ϴ�."));
	        js.executeScript("$('.uid_ok_btn').click();");
	        System.out.println(TestBrowser + " ���� ���� > systemMessage > 2�� ��ġ ���� : Pass");
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");     
	        elementWait("class", "uid_location");
	        js.executeScript("$('.fa-circle-o')[2].click();");
	        js.executeScript("$('input[name=holdingTimeSec]').val('10');");
	        js.executeScript("$('input[name=displayMessage]').val('" + TestBrowser + " ��ġ 3�� �׽�Ʈ �Դϴ�.');");
	        js.executeScript("$('.ac_btn_text')[0].click();");
	        elementWait("class", "ac_container_message_footer");
	        assertTrue(driver.getPageSource().contains("GM ������ ȣ�� �Ǿ����ϴ�."));
	        js.executeScript("$('.uid_ok_btn').click();");
	        System.out.println(TestBrowser + " ���� ���� > systemMessage > 3�� ��ġ ���� : Pass");
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");       
	        elementWait("class", "uid_location");
	        js.executeScript("$('.fa-circle-o')[3].click();");
	        js.executeScript("$('input[name=holdingTimeSec]').val('10');");
	        js.executeScript("$('input[name=displayMessage]').val('" + TestBrowser + " ��ġ 4�� �׽�Ʈ �Դϴ�.');");
	        js.executeScript("$('.ac_btn_text')[0].click();");
	        elementWait("class", "ac_container_message_footer");
	        assertTrue(driver.getPageSource().contains("GM ������ ȣ�� �Ǿ����ϴ�."));
	        js.executeScript("$('.uid_ok_btn').click();");
	        System.out.println(TestBrowser + " ���� ���� > systemMessage > 4�� ��ġ ���� : Pass");
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");       
	        elementWait("name", "keyword");
	        js.executeScript("$('input[name=keyward]').val('�˻��� �Դϴ�.');");
	        js.executeScript("$('.ac_btn_text')[1].click()");
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	        assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
	        System.out.println(TestBrowser + " ���� ���� > systemMessage > �˻� : Pass");
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	    }
	  	@Test(priority = 4)
	    public void ingameNotice_add() {
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
	        js.executeScript("$('input[name=name]').val('" + TestBrowser + " �ΰ��� ���� �����Դϴ�.');");
	        js.executeScript("$('input[name=desc]').val('" + TestBrowser + " �ΰ��� ���� �����Դϴ�.');");
	        js.executeScript("$('.fa-square-o')[0].click()");
	        js.executeScript("$('.fa-circle-o')[2].click();");
	        js.executeScript("$('.ac_input:eq(11)').val('10')");
	        js.executeScript("$('.ac_input:eq(12)').val('" + TestBrowser + " �׽�Ʈ ���� ����')");
	        js.executeScript("$('.ac_btn_text')[2].click();");
	        elementWait("class", "ac_container_message_footer");
	        assertTrue(driver.getPageSource().contains("�ԷµǾ����ϴ�."));
	        js.executeScript("$('.uid_ok_btn').click();");
	        System.out.println(TestBrowser + " ���� ���� > �ΰ��� ���� > ��� > Pass");
	  	}
	  	@Test(priority = 5)
	    public void ingameNotice_statusChange_start() {
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	        elementWait("class", "uid_notice_start_btn");
	        js.executeScript("$('.uid_notice_start_btn')[0].click();");
	        elementWait("class", "ac_container_message_footer");
	        assertTrue(driver.getPageSource().contains("���� �Ǿ����ϴ�."));
	        js.executeScript("$('.uid_ok_btn').click();");
	        System.out.println(TestBrowser + " ���� ���� > �ΰ��� ���� > ���� ���� : ���� > Pass");
	  	}
	  	@Test(priority = 6)
	    public void ingameNotice_statusChange_stop() {
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	        elementWait("class", "uid_notice_stop_btn");
	        js.executeScript("$('.uid_notice_stop_btn')[0].click();");
	        elementWait("class", "ac_container_message_footer");
	        assertTrue(driver.getPageSource().contains("���� �Ǿ����ϴ�."));
	        js.executeScript("$('.uid_ok_btn').click();");
	        System.out.println(TestBrowser + " ���� ���� > �ΰ��� ���� > ���� ���� : ���� > Pass");
	  	}
	  	@Test(priority = 7)
	    public void ingameNotice_statusChange_reStart() {
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	        elementWait("class", "uid_notice_start_btn");
	        js.executeScript("$('.uid_notice_start_btn')[0].click();");
	        elementWait("class", "ac_container_message_footer");
	        assertTrue(driver.getPageSource().contains("���� �Ǿ����ϴ�."));
	        js.executeScript("$('.uid_ok_btn').click();");
	        System.out.println(TestBrowser + " ���� ���� > �ΰ��� ���� > ���� ���� : ���� > Pass");
	  	}
	  	@Test(priority = 8)
	    public void ingameNotice_statusChange_end() {
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	        elementWait("class", "uid_notice_end_btn");
	        js.executeScript("$('.uid_notice_end_btn')[0].click();");
	        elementWait("class", "ac_container_message_footer");
	        assertTrue(driver.getPageSource().contains("���� �Ǿ����ϴ�."));
	        js.executeScript("$('.uid_ok_btn').click();");
	        System.out.println(TestBrowser + " ���� ���� > �ΰ��� ���� > ���� ���� : ���� > Pass");
	  	}
	  	@Test(priority = 9)
	    public void ingameNotice_history() {
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	        elementWait("class", "uid_history_show_btn");
	        js.executeScript("$('.uid_history_show_btn')[0].click();");
	        elementWait("class", "uid_layer_close");
	        js.executeScript("$('.uid_layer_close').click();");
	        System.out.println(TestBrowser + " ���� ���� > �ΰ��� ���� > �����丮 Ȯ�� > Pass");
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	    }
	  	@Test(priority = 10)
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
	        js.executeScript("$('input[name=reason]').val('" + TestBrowser + " KickUser �׽�Ʈ')");
	        js.executeScript("$('.uid_gmcmd_kickusercall_btn').click();");
	        elementWait("class", "ac_container_message_footer");
	        assertTrue(driver.getPageSource().contains("GM ������ ȣ�� �Ǿ����ϴ�."));
	        js.executeScript("$('.uid_ok_btn').click();");
	        System.out.println(TestBrowser + " ���� ���� > kickUser : Pass");
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	        elementWait("name", "keyword");
	        js.executeScript("$('input[name=keyword]').val('kickUser');");
	        js.executeScript("$('.uid_log_search_btn').click();");
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	        assertTrue(driver.getPageSource().contains("������ �Ұ��մϴ�."));
	        System.out.println(TestBrowser + " ���� ���� > kickUser > �˻� : Pass");
	    }
	  	@Test(priority = 11)
	    public void mailOut() {
	    	driver.get(baseUrl + "/gmcmd/mailoutform.ct");
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	        assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
	        System.out.println(TestBrowser + " ���� ���� > ���� �뷮 �߼� > ������ ���� : Pass");
	        driver.findElement(By.name("filedata")).sendKeys("C:\\Users\\Administrator\\Downloads\\�۰�����ι�÷��.xlsx");
	        js.executeScript("$('input[name=reason]').val('" + TestBrowser + " ���� �뷮 �߼� �׽�Ʈ �Դϴ�.');");
	        js.executeScript("$('.uid_mailout_submit_btn').click();");
	        elementWait("class", "ac_container_message_footer");
	        assertTrue(driver.getPageSource().contains("�뷮 ���� �߼��� ���۵˴ϴ�."));
	        js.executeScript("$('.uid_ok_btn').click();");
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	        elementWait("class", "ac_container_section_footer");
	        if(TestBrowser.equals("firefox")){
	        	System.out.println(TestBrowser + " �� ��ȿ�� ���� Pass");
	        } else {
		        assertTrue(driver.getPageSource().contains("(�߼ۿϷ�)"));	        	
	        }
	        System.out.println(TestBrowser + " ���� ���� > ���� �뷮 �߼� > �߼ۿϷ� : Pass");
	        elementWait("class", "au_text_center");
	        js.executeScript("$('.au_text_center').click();");
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	    }
	  	//���� �̺�Ʈ�� ����ȭ ��� ���������� ��� ����
	  	@Test(priority = 12)
	    public void getServerStatus() throws Exception {
	  		driver.get(baseUrl + "/cache/getServerStatus.ct");
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	        elementWait("class", "ac_container_pagination");
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
	        js.executeScript("$('.uid_ok_btn').click();");
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	        elementWait("class", "ac_container_pagination");
	        elementWait("id", "HC-AUTO_SHUTDOWN_CHECK_MINUTES.1");
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
	        js.executeScript("$('.uid_ok_btn').click();");
	        System.out.println("���� ���� > ���� ���� > ���� ���� : Pass");
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	        elementWait("name", "keyword");
	        elementWait("class", "ac_container_pagination");
	        js.executeScript("$('input[name=keyword]').val('�˻��� �Դϴ�.');");
	        js.executeScript("$('.ac_btn_text')[1].click();");
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	        elementWait("name", "keyword");
	        elementWait("class", "ac_container_pagination");
	        assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
	        System.out.println("���� ���� > ���� ���� > �˻� : Pass");
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	    }
	    @Test(priority = 13)
	    public void limitdrop_fileUpload() {
	  		driver.get(baseUrl + "/event/limitdrop/confUploadForm.ct");
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	        assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
	        assertTrue(driver.getPageSource().contains("������� �ʽ��ϴ�."));
	        System.out.println(TestBrowser + " ���� ���� > ������ ��� ���� > ������ ���� : Pass");
	        driver.findElement(By.name("filedata")).sendKeys("C:\\Users\\Administrator\\Downloads\\dropConfSample.xlsx");
	        js.executeScript("$('.au_text_center:Eq(1)').click();");
	        System.out.println(TestBrowser + " ���� ���� > ������ ��� ���� > ���� ���ε� : Pass");
	    }
	    @Test(priority = 14)
	    public void limitdrop_dropStateChangeOff() {
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	        elementWait("class", "uid_state_change_btn");
	        js.executeScript("$('.uid_state_change_btn').click();");
	        if(TestBrowser.equals("firefox")){
	        	System.out.println(TestBrowser + "�� ��� ����");	        	
	        } else {
	        	elementWait("class", "ac_container_message_footer");
	        }
	        if(TestBrowser.equals("firefox")){
	        	System.out.println(TestBrowser + "�� ��ȿ�� ���� ����");
	        } else {
	        	assertTrue(driver.getPageSource().contains("���ӿ� �ݿ��˴ϴ�."));
		        System.out.println("��ȿ�� ����");
	        }
	        js.executeScript("$('.uid_ok_btn').click();");
	        if(TestBrowser.equals("firefox")){
	        	System.out.println(TestBrowser + "�� ��� ����");	        	
	        } else {
	        	elementWait("class", "ac_container_message_footer");
	        }	        
	        assertTrue(driver.getPageSource().contains("reload�� ������ ���ӿ� �ݿ��˴ϴ�."));
	        js.executeScript("$('.uid_ok_btn').click();");
	        System.out.println(TestBrowser + " ���� ���� > ������ ��� ���� > ���� off�� ���� : Pass");
	    }
	    @Test(priority = 15)
	    public void limitdrop_dropStateChangeOn() {
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	        elementWait("class", "uid_state_change_btn");
	        js.executeScript("$('.uid_state_change_btn').click();");
	        if(TestBrowser.equals("firefox")){
	        	System.out.println(TestBrowser + "�� ��� ����");	        	
	        } else {
	        	elementWait("class", "ac_container_message_footer");
	        }
	        if(TestBrowser.equals("firefox")){
	        	System.out.println(TestBrowser + "�� ��ȿ�� ���� ����2");
	        } else {
	        	assertTrue(driver.getPageSource().contains("���ӿ� �ݿ��˴ϴ�."));
		        System.out.println("��ȿ�� ����");
	        }
	        js.executeScript("$('.uid_ok_btn').click();");
	        //elementWait("class", "ac_container_message_footer");
	        if(TestBrowser.equals("firefox")){
	        	System.out.println(TestBrowser + "�� ��ȿ�� ���� ����3");
	        } else {
	        	assertTrue(driver.getPageSource().contains("reload�� ������ ���ӿ� �ݿ��˴ϴ�."));
		        System.out.println("��ȿ�� ����");
	        }
	        js.executeScript("$('.uid_ok_btn').click();");
	        System.out.println(TestBrowser + " ���� ���� > ������ ��� ���� > ���� on���� ���� : Pass");
	    }
	    @Test(priority = 16)
	    public void limitdrop_gameserverReload() {
	    	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	        elementWait("class", "uid_reload_btn");
	        js.executeScript("$('.uid_reload_btn').click();");
	        elementWait("class", "ac_container_message_footer");
	        if(TestBrowser.equals("firefox")){
	        	System.out.println(TestBrowser + "�� ��ȿ�� ���� ����");
	        } else {
	        	assertTrue(driver.getPageSource().contains("���� ������ ���� �Ǿ����ϴ�."));
		        System.out.println("��ȿ�� ����");
	        }	        
	        js.executeScript("$('.uid_ok_btn').click();");
	        System.out.println(TestBrowser + " ���� ���� > ������ ��� ���� > reload : Pass");
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");       
	    }
	    @Test(priority = 17)
	    public void ingameGuide_add() {
	    	driver.get(baseUrl + "/gmcmd/ingameGuide/list.ct");
	        elementWait("id", "ac_layout_aside");
	        elementWait("class", "uid_ingame_guide_add_btn");
	        assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
	        System.out.println(TestBrowser + " ���� ���� > �ΰ��� ���̵� > ������ ���� : Pass");
	        js.executeScript("$('.uid_ingame_guide_add_btn').click();");
	        elementWait("class", "ac_container_section_footer");
	        elementWait("class", "uid_ingame_guide_save_btn");
	        js.executeScript("$('input[name=firstCategory]').prop('value', '�ΰ��� ���̵� �׽�Ʈ ��з� �Դϴ�.');");
	        js.executeScript("$('input[name=secondCategory]').prop('value', '�ΰ��� ���̵� �׽�Ʈ �Һз� �Դϴ�.');");
	        js.executeScript("setTimeout(function() { return; }, 4000);");
	        //���̵� �ۼ� ���̾� ���� �� ������ �κ� �ε��� �����Ƿ� �ε� �Ϸ� �� ���� �Է�â�� �Է��ϵ��� ����
	        js.executeScript("$('input[name=order]').val('0');");
	        js.executeScript("(function(){setTimeout(function(){$('.cke_wysiwyg_frame').contents().find('.cke_editable').text('���� �Է��Դϴ�.')}, 800);})();");
	        //���� �Է�â�� setTimeout���� �Է� �� �ԷµǱ����� Ŭ���ع����Ƿ� Ŭ���� �������� (��¥�� �޼��� ���̾� �ε��Ǳ������� �ִ� 10�� ��� ����) 
	        js.executeScript("(function(){setTimeout(function(){$('.uid_ingame_guide_save_btn').click();}, 2000);})();");
	        elementWait("class", "ac_container_message_footer");
	        js.executeScript("$('.uid_ok_btn').click();");
	        elementWait("class", "ac_container_message_footer");
	        js.executeScript("$('.uid_ok_btn').click();");
	        System.out.println(TestBrowser + " ���� ���� > �ΰ��� ���̵� > �ۼ� : Pass");
	    }
	    @Test(priority = 18)
	    public void ingameGuide_modify() {
	    	elementWait("id", "ac_layout_aside");
	        elementWait("class", "uid_ingame_guide_add_btn");
	        elementWait("class", "uid_ingameguide_edit_btn");
	        js.executeScript("$('.ac_btn_text.au_text_center').eq(2).click();");
            elementWait("class", "ac_container_section_footer");
	        elementWait("class", "uid_ingame_guide_save_btn");
	        js.executeScript("$('input[name=firstCategory]').prop('value', '--- ��з� ��з� ��з� ---');");
	        js.executeScript("$('input[name=secondCategory]').prop('value', '---�Һз� �Һз� �Һз�---');");
	        js.executeScript("$('.cke_wysiwyg_frame').contents().find('.cke_editable').text('--- ���� ���� ���� ---')");
	        elementValueWait("name", "secondCategory", "---�Һз� �Һз� �Һз�---");
	        js.executeScript("$('.uid_ingame_guide_save_btn').click();");
	        if(TestBrowser.equals("firefox")){
	        	System.out.println(TestBrowser + " �� ��ȿ�� �˻� ����");
	        } else {
		        elementWait("class", "ac_container_message_footer");
	        }
	        js.executeScript("$('.uid_ok_btn').click();");
	        if(TestBrowser.equals("firefox")){
	        	System.out.println(TestBrowser + " �� ��ȿ�� �˻� ����");
	        } else {
		        elementWait("class", "ac_container_message_footer");
	        }
	        js.executeScript("$('.uid_ok_btn').click();");
	        System.out.println(TestBrowser + " ���� ���� > �ΰ��� ���̵� > ���� : Pass");
	    }
	    @Test(priority = 19)
	    public void ingameGuide_delete() {
	    	elementWait("id", "ac_layout_aside");
	        elementWait("class", "uid_ingame_guide_add_btn");
	        js.executeScript("$('.ac_btn_text')[3].click();");
	        elementWait("class", "ac_container_message_footer");
	        js.executeScript("$('.uid_ok_btn').click();");
	        if(TestBrowser.equals("firefox")){
	        	System.out.println(TestBrowser + " �� ��ȿ�� �˻� ����");
	        } else {
		        elementWait("class", "ac_container_message_footer");
	        }
	        js.executeScript("$('.uid_ok_btn').click();");
	        System.out.println(TestBrowser + " ���� ���� > �ΰ��� ���̵� > ���� : Pass");
	        elementWait("id", "ac_layout_aside");
	    }
	  	@Test(priority = 20)
	    public void onlineUserCountInfo() {
	        driver.get(baseUrl + "/gmcmd/onlineUserCountInfo.ct");
	        elementWait("class", "ac_container_section_body");
	        assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
	        assertTrue(driver.getPageSource().contains("�¶��� ������ ��ȸ"));
	        System.out.println(TestBrowser + " ���� ���� > �¶��� ������ ��ȸ : Pass");
	    }
	    @Test(priority = 21)
	    public void slangForm_typeA_add() {
	        driver.get(baseUrl + "/gmcmd/slangForm.ct");
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	        assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
	        assertTrue(driver.getPageSource().contains("���� ǥ����"));
	        System.out.println(TestBrowser + " ���� ���� > ��Ģ�� ���� > ������ ���� : Pass");
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
	    }
	    @Test(priority = 22)
	    public void slangForm_typeA_modify() {
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
	    }
	  	@Test(priority = 23)
	    public void slangForm_typeA_delete() {
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
	  	}
	  	@Test(priority = 24)
	    public void slangForm_typeB_add() {
	  		driver.get(baseUrl + "/gmcmd/slangForm.ct");
	        js.executeScript("$('.ac_container_tab_header > ul > li:eq(1) > a')[0].click();");
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	        System.out.println(TestBrowser + " ���� ���� > ������ ���� > ������ ���� : Pass");
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
	  	}
	  	@Test(priority = 25)
	    public void slangForm_typeB_modify() {
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
	  	}
	  	@Test(priority = 26)
	    public void slangForm_typeB_delete() {
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
	    @Test(priority = 27)
	    public void pvpSchedule_add() {
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
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	        elementWait("class", "ac_container_section_footer");
	        System.out.println(TestBrowser + " ���� ���� > PvP������ ����Ʈ > ��� : Pass");
	    }
	    @Test(priority = 28)
	    public void pvpSchedule_add_sync() {
	    	elementWait("id", "uid_day_list");
	    	js.executeScript("(function(){setTimeout(function(){$('.uid_schedule_sync_btn').click()}, 1500);})();");
	        js.executeScript("(function(){setTimeout(function(){$('.uid_ok_btn').click()}, 1500);})();");
	        js.executeScript("(function(){setTimeout(function(){$('.uid_ok_btn').click()}, 1500);})();");
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	        elementWait("class", "ac_container_section_footer");
	        System.out.println(TestBrowser + " ���� ���� > PvP������ ����Ʈ > ����ȭ : Pass");
	    }
	    @Test(priority = 29)
	    public void pvpSchedule_delete() {
	        js.executeScript("$('.uid_schedule_del_btn:Eq(1)').click();");
	        js.executeScript("$('.uid_schedule_save_btn').click();");
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	        elementWait("class", "ac_container_section_footer");
	        System.out.println(TestBrowser + " ���� ���� > PvP������ ����Ʈ > ���� : Pass");
	    }
	    @Test(priority = 30)
	    public void pvpSchedule_delete_sync() throws InterruptedException {
	    	elementWait("id", "uid_day_list");
	    	js.executeScript("(function(){setTimeout(function(){$('.uid_schedule_sync_btn').click()}, 1500);})();");
	        js.executeScript("(function(){setTimeout(function(){$('.uid_ok_btn').click()}, 1500);})();");
	        js.executeScript("(function(){setTimeout(function(){$('.uid_ok_btn').click()}, 1500);})();");
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	        elementWait("class", "ac_container_section_footer");
	        System.out.println(TestBrowser + " ���� ���� > PvP������ ����Ʈ > ����ȭ : Pass");
	    }
	    @Test(priority = 31)
	    public void pvpSchedule_historyList() {
	        js.executeScript("$('.ac_container_tab_header > ul > li:eq(1) > a')[0].click();");
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	        elementWait("class", "ac_container_section_footer");
	        System.out.println(TestBrowser + " ���� ���� > PvP������ ����Ʈ > �����丮 ����Ʈ : Pass");
	    }
	    @Test(priority = 32)
	    public void pvpSchedule_historyView() {
	        js.executeScript("$('.au_text_center > tr > td:eq(1) > a')[0].click();");
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	        elementWait("class", "ac_container_section_footer");
	        assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
	        System.out.println(TestBrowser + " ���� ���� > PvP������ ����Ʈ > �����丮 �󼼺��� : Pass");
	        js.executeScript("$('.uid_link_list_btn').click();");
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	    }
	  	@Test(priority = 99)
	    public void Logout(){
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	        elementWait("class", "btn-logout");
	  		js.executeScript("$('.member-info > a')[0].click();");
	  		elementWait("class", "uid_login_type");
	        System.out.println(TestBrowser + " �α׾ƿ� : Pass");
	  	}
		@AfterClass
		public void afterTest() {
			System.out.println(TestBrowser + " AfterClass");
			if(TestBrowser.equals("firefox")){
				driver.quit();
			} else {
				driver.close();
			}
	        String verificationErrorString = verificationErrors.toString();
	        if (!"".equals(verificationErrorString)) {
	            fail(verificationErrorString);
	        }
		}		
}	