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
import org.openqa.selenium.firefox.FirefoxDriver; 
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.JavascriptExecutor; //js�� ����ϱ� ���� �߰�
import org.openqa.selenium.Alert; //alert ó����
import org.testng.annotations.Test; 
import org.testng.annotations.BeforeMethod; 
import org.testng.annotations.AfterMethod;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class defaultTestNG {
	private static WebDriver driver;
	private static String TestBrowser;
	private static StringBuffer verificationErrors = new StringBuffer();
	private static String CHROMEDRIVER_FILE_PATH;
	private static String FIREFOXDRIVER_FILE_PATH;
	private static String INTERNETEXPLORER_FILE_PATH;
  	private static JavascriptExecutor js; 
  	private static String baseUrl;
  	private static String liveUrl;
  	private static String devUrl;
  	private static String liveBoardName;
  	private static String devBoardName;
  	private static HttpURLConnection huc;
  	private static int respCode;

  	@BeforeMethod
  	public void testReady() {
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
		baseUrl = "http://www.d.p.herowarz.com";
		liveUrl = "http://www.herowarz.com";
		devUrl = "http://www.d.p.herowarz.com";
		huc = null;
		respCode = 200;
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
	//Ŀ�´�Ƽ �۾���
	public static void boardWrite(String Category, String subCategory) throws Exception{
		if(subCategory.equals("")){
			new Select(driver.findElement(By.id("cmGroupList"))).selectByVisibleText(Category);
    		System.out.println (Category + " > �۾��� ���� ���� > Pass");
    	    js.executeScript("$('input[name=title]').prop('value', '������ �־��~')");
    		System.out.println (Category + " > �۾��� ���� �Է� > Pass");
    	    //driver.findElement(By.cssSelector("button.se2_photo.ico_btn")).click();
    	    js.executeScript("$('.uid_smarteditor_area iframe').contents().find('iframe').contents().find('body').html('���� ���� �Ҹ��Ѱ���!?!?');");
    		System.out.println (Category + " > �۾��� ���� �Է� > Pass");
    		Thread.sleep(1000);
    	    js.executeScript("$('.uid_smarteditor_area iframe').contents().find('.se2_multy > li > button > span:eq(0)').click()");
    	    System.out.println (Category + " > �̹��� ���ε� ��ư Ŭ�� > Pass");
    	    handleMultipleWindows("�̹��� ���ε� :: SmartEditor2");
    		System.out.println (Category + " > �۾��� ����÷�� > �ڽ� ������� ��Ŀ�� ���� > Pass");
    	    driver.findElement(By.id("uploadInputBox")).sendKeys("C:\\Users\\Administrator\\Desktop\\black.jpg");
    	    driver.findElement(By.id("btn_confirm")).click();
    	    handleMultipleWindows(" :: �׼��ߵ�! - �ְ��Ǳ���");
    		System.out.println (Category + " > ����÷�� > �θ� ������� ��Ŀ�� ���� > Pass");
    		Thread.sleep(2000);
    	    js.executeScript("$('.btn-small')[1].click();");
		} else {
			new Select(driver.findElement(By.id("cmGroupList"))).selectByVisibleText(Category);
    		System.out.println ("Ŀ�´�Ƽ > " + Category + " > �۾��� ���� ���� > Pass");
    	    new Select(driver.findElement(By.id("cateDepth1"))).selectByVisibleText(subCategory);
    		System.out.println ("Ŀ�´�Ƽ > " + Category + " > �۾��� 1�� ī�װ��� ���� > Pass");
    		js.executeScript("$('input[name=title]').prop('value', '������ �־��~')");
    		js.executeScript("$('.uid_smarteditor_area iframe').contents().find('iframe').contents().find('body').html('���� ���� �Ҹ��Ѱ���!?!?');");
  		//js.executeScript("var org = new Array();org.push('html ����');oEditors.getById['ir1'].exec('PASTE_HTML', org);");
  		js.executeScript("$('.btn-small')[1].click();");
		}      		
	}
	//����� �Խñ� �ۼ� �� Ȯ��
	public static void liveWrite(String Category, String subCategory) throws Exception{
		js.executeScript("$('.btn-small')[2].click();");
		Thread.sleep(1000);
		assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
		assertTrue(driver.getPageSource().contains(Category));
		System.out.println(Category + " > �۾��� ������ : Pass");
		Thread.sleep(1000);
		boardWrite(Category, subCategory);
		Thread.sleep(1000);
		acceptAlert(Category + " > �Խñ� ��� �Ϸ� �޼���");
		Thread.sleep(1000);
	}
	//���߼��� �Խñ� �ۼ� �� Ȯ��
	public static void devWrite(String Category, String subCategory) throws Exception{
		assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
		assertTrue(driver.getPageSource().contains(Category));
		System.out.println("Ŀ�´�Ƽ > " + Category + " > �۾��� ������ : Pass");
		Thread.sleep(1000);
		boardWrite(Category, subCategory);
		Thread.sleep(1000);
		acceptAlert(Category + " > �Խñ� ��� �Ϸ� �޼���");
		Thread.sleep(1000);
	}
	//�Խñ� ����
	public static void boardDelete() throws Exception{
		js.executeScript("$('.m-button > .g-left > a')[1].click();");
		acceptAlert("�Խñ� ���� Ȯ�� �޼��� : ");
		acceptAlert("�Խñ� ���� �Ϸ� �޼��� : ");
		Thread.sleep(1000);
	}
	//��� �ۼ�
	public static void commentWrite(String Category) throws Exception {
	  	js.executeScript("$('.uid_comment_writesave').click();");
	  	Thread.sleep(1000);
	  	acceptAlert(Category + " > ��� ���Է� �޼���");
	  	js.executeScript("$('textarea').prop('value', '��������');");
	  	js.executeScript("$('.uid_comment_writesave').click();");
	  	System.out.println(Category + " > ��� �ۼ� : Pass");
	  	Thread.sleep(1000);
	}
	//���� �ۼ�
	public static void commentWrite2(String Category) throws Exception {
		Thread.sleep(1000);
	  	js.executeScript("$('.uid_comment_reply')[0].click();");
	  	Thread.sleep(1000);
	  	js.executeScript("$('textarea[name=content]').eq(1).prop('value', '��������');");
	  	Thread.sleep(1000);
	  	js.executeScript("$('.uid_comment_writesave').eq(1).click();");
	  	System.out.println(Category +" > ���� �ۼ� : Pass");
	  	Thread.sleep(1000);
	}
	//��� ����
	public static void delComment(String Category) throws Exception {
		js.executeScript("$('.uid_comment_delete')[0].click();");
		Thread.sleep(1000);
	  	dismissAlert("Ŀ�´�Ƽ > " + Category + " > ��ۻ��� confirm ���");
	  	js.executeScript("$('.uid_comment_delete')[0].click();");
	  	Thread.sleep(1000);
	  	acceptAlert("Ŀ�´�Ƽ > " + Category + " > ��ۻ��� confirm Ȯ��");
	  	System.out.println("Ŀ�´�Ƽ > "+ Category +" > ��� ���� : Pass");
	  	Thread.sleep(1000);
	}
	//���� ����
	public static void delComment2(String Category) throws Exception {
		js.executeScript("$('.uid_comment_delete')[1].click();");
	  	Thread.sleep(1000);
	  	acceptAlert(Category + " > ��ۻ��� confirm Ȯ��");
	  	System.out.println(Category +" > ��� ���� : Pass");
	  	Thread.sleep(1000);
	}
	//�ְ���
	public static void like(String Category) throws Exception {
		Thread.sleep(1000);
	  	js.executeScript("$('.uid_comment_like')[0].click();");
	  	System.out.println(Category + " > �ְ��� : Pass");
	}
	//�����
	public static void blind (String Category) throws Exception {
		js.executeScript("$('.uid_comment_blind')[1].click();");
	  	js.executeScript("$('#blindType-3').click();");
	  	js.executeScript("$('.uid_blindcomment_confirm').click();");
	  	dismissAlert("����� confirm ���");
	  	js.executeScript("$('.uid_blindcomment_confirm').click();");
	  	acceptAlert(Category + " > ����� confirm Ȯ��");
	  	acceptAlert(Category + " > ����� �Ϸ�");
	  	System.out.println("Ŀ�´�Ƽ > "+ Category +" > ����� : Pass");
	  	Thread.sleep(1000);
	}
	//�̸���� 
	public static void liberationRanking(String serverName, int numberOfPeople) throws Exception {
		Thread.sleep(1000);
		assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
		assertTrue(driver.getPageSource().contains("�ð��� �������� �����˴ϴ�."));
		System.out.println("��ŷ > �ع淩ŷ > " + serverName + " > " + numberOfPeople + "�� ��ŷ ����Ʈ : Pass");
		js.executeScript("$('input[name=keyword]').val('asdasd')");
		js.executeScript("$('.btn-search').click();");
		assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
		System.out.println("��ŷ > �ع淩ŷ > " + serverName + " > " + numberOfPeople + "�� ��ŷ ����Ʈ �˻� : Pass");
		Thread.sleep(1000);
	}	
	@Test(singleThreaded=true)
	public void A_Login() throws Exception {
		driver.get(baseUrl + "/index.main");
		Thread.sleep(1000);
		assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));  
		assertTrue(driver.getPageSource().contains("���̵� ����"));
		System.out.println("���������� > ���� : Pass");    	
		if(baseUrl.equals(liveUrl)){
			js.executeScript("$('.uid_login_id').val('apzz0928');");
		} else {
			js.executeScript("$('.uid_login_id').val('����1240');");
		}
		js.executeScript("$('.uid_login_password').val('qordlf12');");
		Thread.sleep(1000);    	
		js.executeScript("$('.uid_login_login').eq(0).click();");
		Thread.sleep(1000);
		assertTrue(driver.getPageSource().contains("����������"));
		System.out.println("���������� > �α��� : Pass");
		Thread.sleep(1000);
	}
	//@Test
	public void B_recent_a_notice() throws Exception {
		driver.get(baseUrl + "/index.main?c=n");
		js.executeScript("$('.gnb-list > .list-recent > .notice > a')[0].click();");
		Thread.sleep(1000);
		assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));  
		assertTrue(driver.getPageSource().contains("[����]"));
		System.out.println("���ҽ� > �������� > ����Ʈ : Pass");
		js.executeScript("$('.subject')[0].click();");
		Thread.sleep(1000);
		assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
		System.out.println("���ҽ� > �������� > �󼼺��� : Pass");
		Thread.sleep(1000);
	}
	//@Test
	public void B_recent_b_update() throws Exception {
		driver.get(baseUrl + "/index.main?c=n");
		js.executeScript("$('.gnb-list > .list-recent > .update > a')[0].click();");
		Thread.sleep(1000);
		assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));  
		assertTrue(driver.getPageSource().contains("������Ʈ �ȳ�"));
		System.out.println("���ҽ� > ������Ʈ > ����Ʈ : Pass");
		js.executeScript("$('.view-h-txt')[0].click();");
		Thread.sleep(1000);
		assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�.")); 
		System.out.println("���ҽ� > ������Ʈ > �󼼺��� : Pass");
		Thread.sleep(1000);
	}
	//@Test
	public void B_recent_c_avatarsale() throws Exception {
		driver.get(baseUrl + "/index.main?c=n");
		js.executeScript("$('.gnb-list > .list-recent > .event-store > a')[0].click();");
		Thread.sleep(1000);
		assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));  
		assertTrue(driver.getPageSource().contains("��Ű�� �ڼ��� ����"));
		System.out.println("���ҽ� > ��ȭ�� �ҽ� > ��Ű�� �ڼ��� ���� : Pass");
		js.executeScript("$('#uid_mtab_link > ul > .gift > a')[0].click();");
		Thread.sleep(1000);
		System.out.println("���ҽ� > ��ȭ�� �ҽ� > ����ǰ �ȳ� �� : Pass");
		assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�.")); 
		js.executeScript("$('.btn > a')[0].click();");
		Thread.sleep(1000);
		assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
		js.executeScript("$('.btn-close')[2].click();");
		Thread.sleep(1000);
		System.out.println("���ҽ� > ��ȭ�� �ҽ� > ����ǰ ���ɳ��� Ȯ�� : Pass");
		switch (TestBrowser) {
			case "chrome":
				js.executeScript("$('.uid_avatarsale_disable_btn')[0].click();");
				Thread.sleep(1000);
		        acceptAlert("2�� ����ǰ ���� �Ұ� �޼���");
		        js.executeScript("$('.uid_avatarsale_disable_btn')[1].click();");
		        Thread.sleep(1000);
		        acceptAlert("3�� ����ǰ ���� �Ұ� �޼���");
		        js.executeScript("$('.uid_avatarsale_disable_btn')[2].click();");
		        Thread.sleep(1000);
		        acceptAlert("4�� ����ǰ ���� �Ұ� �޼���");
		        System.out.println("���ҽ� > ��ȭ�� �ҽ� > ����ǰ �ȳ� : Pass");
		        break;
			case "ie":
				System.out.println("IE�� ����ǰ ���� �׽�Ʈ N/A");
				break;
			case "firefox":
				System.out.println("firefox�� ����ǰ ���� �׽�Ʈ N/A");
				break;
		}
		Thread.sleep(1000);
	}
	//@Test
	public void B_recent_d_eventList() throws Exception {
	  	driver.get(baseUrl + "/index.main?c=n");
	  	js.executeScript("$('.gnb-list > .list-recent > .event > a')[0].click();");
	  	Thread.sleep(1000);
	    assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
	    assertTrue(driver.getPageSource().contains("�������� �̺�Ʈ"));
	    System.out.println("���ҽ� > �̺�Ʈ > ����Ʈ : Pass");
	    js.executeScript("$('.tab-block > a')[1].click();");
	    Thread.sleep(1000);
	    assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
	    assertTrue(driver.getPageSource().contains("����� ���ΰ��� Ȯ���غ�����!"));
	    System.out.println("���ҽ� > �̺�Ʈ > ��÷�ں��� : Pass");
	    Thread.sleep(1000);        
	}
	//@Test
	public void B_recent_e_secretNote() throws Exception {
		driver.get(baseUrl + "/index.main?c=n");
		js.executeScript("$('.gnb-list > .list-recent > .secret > a')[0].click();");
		Thread.sleep(1000);
		assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
		assertTrue(driver.getPageSource().contains("��]"));
		System.out.println("���ҽ� > ��ũ����Ʈ > ����Ʈ : Pass");
		js.executeScript("$('.board-gallery-list > ul > li > a')[0].click();");
		Thread.sleep(1000);
		assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
		assertTrue(driver.getPageSource().contains("��ȸ"));
		System.out.println("���ҽ� > ��ũ����Ʈ > �󼼺��� : Pass");
		Thread.sleep(1000);        
	}
	//@Test
	public void B_recent_f_pinkDiary() throws Exception {
		driver.get(baseUrl + "/index.main?c=n");
		js.executeScript("$('.nav-left > .reset-ua > li > a')[5].click();");
		Thread.sleep(1000);
		assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
		assertTrue(driver.getPageSource().contains("��ȸ"));
		System.out.println("���ҽ� > ��ũ���̾ > �󼼺��� : Pass");
		driver.get(baseUrl + "/community/PinkDiary/list.hero");
		Thread.sleep(1000);
		assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
		assertTrue(driver.getPageSource().contains("�˻�"));
		System.out.println("���ҽ� > ��ũ����Ʈ > ����Ʈ : Pass");
		Thread.sleep(1000);        
	}
	//@Test
	public void B_recent_g_costumePlay() throws Exception {
		driver.get(baseUrl + "/index.main?c=n");
		js.executeScript("$('.gnb-list > .list-recent > .cospre > a')[0].click();");
		Thread.sleep(1000);
		assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
		assertTrue(driver.getPageSource().contains("�˻�"));
		System.out.println("���ҽ� > �ְ��ڽ����� > ����Ʈ : Pass");
		if(baseUrl.equals(liveUrl)){
			js.executeScript("$('.thumb > img')[4].click();");
			Thread.sleep(1000);
			assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
			assertTrue(driver.getPageSource().contains("�ٸ�����"));
		} else {
			js.executeScript("$('.thumb > img')[0].click();");
			Thread.sleep(1000);
			assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
			assertTrue(driver.getPageSource().contains("����������"));
		}
		System.out.println("���ҽ� > �ְ��ڽ����� > �󼼺��� : Pass");
		Thread.sleep(1000);        
	}
	//@Test
	public void B_recent_h_herowarzTv() throws Exception {
		js.executeScript("$('.nav-left > .reset-ua > li > a')[7].click();");
		Thread.sleep(1000);
		assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
		assertTrue(driver.getPageSource().contains("�˻�"));
		System.out.println("���ҽ� > �ֱ�TV > ����Ʈ : Pass");
		js.executeScript("$('.row-notice > .cell-subject > a')[0].click();");
		Thread.sleep(1000);
		assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
		assertTrue(driver.getPageSource().contains("��ȸ"));
		System.out.println("���ҽ� > �ֱ�TV > �󼼺��� : Pass");
		Thread.sleep(1000);        
	}
	//@Test
	public void C_about_a_herowarz() throws Exception {
		driver.get(baseUrl + "/about/herowarz.hero");
		Thread.sleep(1000);
		assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
		assertTrue(driver.getPageSource().contains("Ȩ������ �ٷΰ���"));
		System.out.println("���ӼҰ� > ���ӼҰ� : Pass");
		Thread.sleep(1000);
	}
	//@Test
	public void C_about_b_character() throws Exception {
		js.executeScript("$('.inner > a')[0].click();");
		Thread.sleep(1000);
		assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
		assertTrue(driver.getPageSource().contains("COPYRIGHT �� A.STORM. ALL RIGHTS RESERVED."));
		System.out.println("���ӼҰ� > ĳ���� �Ұ�  > ĳ���� ����Ʈ > RPG : Pass");
		js.executeScript("$('.item-rpg > a')[0].click();");
		Thread.sleep(1000);
		assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
		assertTrue(driver.getPageSource().contains("�г���"));
		System.out.println("���ӼҰ� > ĳ���� �Ұ�  > �󼼺��� > RPG : Pass");
		Thread.sleep(1000);
		js.executeScript("$('#gnb > ul > li > a')[1].click();");
		Thread.sleep(1000);
		js.executeScript("$('.index-tab > a')[1].click();");
		Thread.sleep(1000);
		assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
		assertTrue(driver.getPageSource().contains("COPYRIGHT �� A.STORM. ALL RIGHTS RESERVED."));
		System.out.println("���ӼҰ� > ĳ���� �Ұ�  > ����Ʈ > AOS : Pass");
		js.executeScript("$('.item-aos > a')[0].click();");
		Thread.sleep(2500);
		assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
		assertTrue(driver.getPageSource().contains("��ǥ��ų"));
		System.out.println("���ӼҰ� > ĳ���� �Ұ�  > �󼼺��� > AOS : Pass");
		js.executeScript("$('#layerPvp > .btn-close').click()");
		Thread.sleep(1000);
		js.executeScript("$('.index-tab > a')[2].click();");
		Thread.sleep(2500);
		assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
		assertTrue(driver.getPageSource().contains("COPYRIGHT �� A.STORM. ALL RIGHTS RESERVED."));
		System.out.println("���ӼҰ� > ĳ���� �Ұ�  > ����Ʈ > SIDEKICK : Pass");
		if(baseUrl.equals(liveUrl)){
			js.executeScript("$('.list > ul > .item-sidekick > a')[0].click();");
			Thread.sleep(2000);
			assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
			assertTrue(driver.getPageSource().contains("ȹ����"));
			System.out.println("���ӼҰ� > ĳ���� �Ұ�  > �󼼺��� > SIDEKICK : Pass");
			Thread.sleep(1000);
			js.executeScript("$('#layerSidekick > .btn-close').click()");
		}
		Thread.sleep(1000);
	}
	//@Test
	public void C_about_c_map() throws Exception {
		js.executeScript("$('#gnb > ul > li > a')[2].click();");
		Thread.sleep(1000);
		assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
		assertTrue(driver.getPageSource().contains("Ȩ������ �ٷΰ���"));
		System.out.println("���ӼҰ� > ������ ���� > ����Ʈ : Pass");
		Thread.sleep(1000);
		js.executeScript("$('map > area')[10].click();");
		Thread.sleep(1000);
		assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
		assertTrue(driver.getPageSource().contains("���� �ٴϴ� ����� ��"));
		js.executeScript("$('#content-third > button').click();");
		System.out.println("���ӼҰ� > ������ ���� > �󼼺��� : Pass");
	}
	//@Test
	public void C_about_d_chronicle() throws Exception {
		driver.get(baseUrl + "/index.main?c=n");
		js.executeScript("$('#gnb > ul > li > a')[3].click();");
		Thread.sleep(1000);
		assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
		assertTrue(driver.getPageSource().contains("COPYRIGHT �� A.STORM. ALL RIGHTS RESERVED."));
		System.out.println("���ӼҰ� > ���� ����� : Pass");
		Thread.sleep(1000);
	}  
	//@Test
	public void D_gameGuide_a_beginner() throws Exception {
		driver.get(baseUrl + "/index.main?c=n");
		Thread.sleep(1000);
		js.executeScript("$('.list-gameinfo > .beginner > a')[0].click();");
		Thread.sleep(1000);
		assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
		assertTrue(driver.getPageSource().contains("�űԼ��� Ʃ�丮��"));
		System.out.println("�������� > �ʺ��� ���̵� > �󼼺��� : Pass");
		Thread.sleep(1000);
		js.executeScript("$('.view-util > a')[0].click();");
		Thread.sleep(1000);
		assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
		assertTrue(driver.getPageSource().contains("���丮"));
		System.out.println("�������� > �ʺ��� ���̵� > ����Ʈ : Pass");
		js.executeScript("$('.list-gameinfo > .gameguide > a')[0].click();");
		Thread.sleep(1000);
		assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
		assertTrue(driver.getPageSource().contains("���Ӱ��̵� �˻�"));
		System.out.println("�������� > ���� ���̵�/���丮 > ����Ʈ : Pass");
		Thread.sleep(1000);
	}  
	//@Test
	public void E_community_a_freeBoard() throws Exception {
		liveBoardName = "���� �Խ���";
		devBoardName = "���� �Խ���";
		driver.get(baseUrl + "/index.main?c=n");
    	js.executeScript("$('.list-community > .free > a')[0].click();");
    	Thread.sleep(1000);
    	assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
    	assertTrue(driver.getPageSource().contains("����"));
    	System.out.println("Ŀ�´�Ƽ > ���� �Խ��� > ����Ʈ : Pass");
    	Thread.sleep(1000);
    	if(baseUrl.equals(liveUrl)){//Live������ Ư�� �Խñۿ��� ��� �ۼ�
    		js.executeScript("$('select[name=type]').prop('value', '4')");
    		js.executeScript("$('input[name=keyword]').prop('value', '����')");
    		js.executeScript("$('.btn-search').click();");
    		Thread.sleep(1000);
    		System.out.println("Ŀ�´�Ƽ > " + liveBoardName + " > �˻� : Pass");
    	}
    	switch (TestBrowser) {
    	case "chrome":
    		js.executeScript("$('.cell-subject > a')[0].click();");
	    	Thread.sleep(1000);
	    	if(baseUrl.equals(liveUrl)){
	    		commentWrite(liveBoardName);
	        	like(liveBoardName);
	        	commentWrite2(liveBoardName);
	        	//blind(liveCategory);
	        	delComment2(liveBoardName);            	
	        	delComment(liveBoardName);
	    	} else {
	    		//commentWrite(devBoardName);
	        	//like(devBoardName);
	        	//commentWrite2(devBoardName);
	        	//blind(devCategory);
	        	//delComment2(devBoardName); 
	        	//delComment(devBoardName); 
	    	}
	    	//�Խ��� �۾���
	    	if(baseUrl.equals(liveUrl)){ 
	    		liveWrite(liveBoardName, "����");
	    		Thread.sleep(1000);
	    		boardDelete();
	    	} else if (baseUrl.equals(devUrl)) {
	    		driver.get(baseUrl + "/community/write.hero?code=FreeBoard");
	    		Thread.sleep(1000);
	    		devWrite(devBoardName, "����");
	    		boardDelete();
	    	}	
	        break;
		case "ie":
			System.out.println("IE�� �Խñ� �ۼ� ���� �׽�Ʈ N/A");
			break;
		case "firefox":
			System.out.println("firefox�� �Խñ� �ۼ� ���� �׽�Ʈ N/A");
			break;
    	}
    	Thread.sleep(1000);
	}
	//@Test
	public void E_community_b_strategy() throws Exception {
		liveBoardName = "���� �Խ���";
		devBoardName = "���� �Խ���";
		driver.get(baseUrl + "/index.main?c=n");
		js.executeScript("$('.list-community > .target > a')[0].click();");
		Thread.sleep(1000);
		assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
		assertTrue(driver.getPageSource().contains("PVE ����"));
		System.out.println("Ŀ�´�Ƽ > ���� �Խ��� > ����Ʈ : Pass");
		Thread.sleep(1000);
		Thread.sleep(1000);
		if(baseUrl.equals(liveUrl)){//Live������ Ư�� �Խñۿ��� ��� �ۼ�
			js.executeScript("$('select[name=type]').prop('value', '4')");
			js.executeScript("$('input[name=keyword]').prop('value', '��������')");
			js.executeScript("$('.btn-search').click();");
			Thread.sleep(1000);
			System.out.println("Ŀ�´�Ƽ > " + liveBoardName + " > �˻� : Pass");
		}
		switch (TestBrowser) {
		case "chrome":
			js.executeScript("$('.cell-subject > a')[0].click();");
	    	Thread.sleep(1000);
	    	if(baseUrl.equals(liveUrl)){
	    		commentWrite(liveBoardName);
	        	like(liveBoardName);
	        	commentWrite2(liveBoardName);
	        	//blind(liveBoardName);
	        	delComment2(liveBoardName);            	
	        	delComment(liveBoardName);
	    	} else {
	    		commentWrite(devBoardName);
	        	like(devBoardName);
	        	commentWrite2(devBoardName);
	        	//blind(devBoardName);
	        	delComment2(devBoardName); 
	        	delComment(devBoardName); 
	    	}
	    	//�Խ��� �۾��� �� ����
	    	if(baseUrl.equals(liveUrl)){ 
	    		liveWrite(liveBoardName, "��Ÿ");
	    		Thread.sleep(1000);
	    		boardDelete();
	    	} else if (baseUrl.equals(devUrl)) {
	    		driver.get(baseUrl + "/community/write.hero?code=FreeBoard");
	    		Thread.sleep(1000);
	    		devWrite(devBoardName, "��Ÿ");
	    		Thread.sleep(1000);
	    		boardDelete();
	    	}	
	        break;
		case "ie":
			System.out.println("IE�� �Խñ� �ۼ� ���� �׽�Ʈ N/A");
			break;
		case "firefox":
			System.out.println("firefox�� �Խñ� �ۼ� ���� �׽�Ʈ N/A");
			break;
		}
	Thread.sleep(1000);
	}
	//@Test
	public void E_community_c_getItem() throws Exception {
		liveBoardName = "���� �Խ���";
		devBoardName = "���� �Խ���";
		driver.get(baseUrl + "/index.main?c=n");
		js.executeScript("$('.list-community > .pick > a')[0].click();");
		Thread.sleep(1000);
		assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
		assertTrue(driver.getPageSource().contains("���� ����Ʈ"));
		System.out.println("Ŀ�´�Ƽ > ���� �Խ��� > ����Ʈ : Pass");
		Thread.sleep(1000);
		Thread.sleep(1000);
		if(baseUrl.equals(liveUrl)){//Live������ Ư�� �Խñۿ��� ��� �ۼ�
			js.executeScript("$('select[name=type]').prop('value', '4');");
			js.executeScript("$('input[name=keyword]').prop('value', '����');");
			js.executeScript("$('.btn-search').click();");
			Thread.sleep(1000);
			System.out.println("Ŀ�´�Ƽ > " + liveBoardName + " > �˻� : Pass");
		}    	
		switch (TestBrowser) {
		case "chrome":
			js.executeScript("$('.thumb > a')[3].click();");
	    	Thread.sleep(1000);
	    	if(baseUrl.equals(liveUrl)){
	    		commentWrite(liveBoardName);
	        	like(liveBoardName);
	        	commentWrite2(liveBoardName);
	        	//blind(liveBoardName);
	        	delComment2(liveBoardName);            	
	        	delComment(liveBoardName);
	    	} else {
	    		commentWrite(devBoardName);
	        	like(devBoardName);
	        	commentWrite2(devBoardName);
	        	//blind(devBoardName);
	        	delComment2(devBoardName); 
	        	delComment(devBoardName); 
	    	}
	    	//�Խ��� �۾��� �� ����
	    	if(baseUrl.equals(liveUrl)){ 
	    		liveWrite(liveBoardName, "");
	    		boardDelete();
	    	} else if (baseUrl.equals(devUrl)) {
	    		driver.get(baseUrl + "/community/write.hero?code=GetItem");
	    		Thread.sleep(1000);
	    		devWrite(devBoardName, "");
	    		boardDelete();	    	
	    	}	
	    	break;
		case "ie":
			System.out.println("IE�� �Խñ� �ۼ� ���� �׽�Ʈ N/A");
			break;
		case "firefox":
			System.out.println("firefox�� �Խñ� �ۼ� ���� �׽�Ʈ N/A");
			break;
		}
		Thread.sleep(1000);
	}	
	//@Test
	public void E_community_d_collection() throws Exception {
		liveBoardName = "���� �Խ���";
		devBoardName = "�÷��� �Խ���";
		driver.get(baseUrl + "/index.main?c=n");
		js.executeScript("$('.list-community > .collection > a')[0].click();");
		Thread.sleep(1000);
		assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
		assertTrue(driver.getPageSource().contains("�÷��� ����Ʈ"));
		System.out.println("Ŀ�´�Ƽ > ���� �Խ��� > ����Ʈ : Pass");
		Thread.sleep(1000);
		Thread.sleep(1000);
		if(baseUrl.equals(liveUrl)){//Live������ Ư�� �Խñۿ��� ��� �ۼ�
			js.executeScript("$('select[name=type]').prop('value', '4');");
			js.executeScript("$('input[name=keyword]').prop('value', 'ȭ��');");
			js.executeScript("$('.btn-search').click();");
			Thread.sleep(1000);
			System.out.println("Ŀ�´�Ƽ > " + liveBoardName + " > �˻� : Pass");
		}
		switch (TestBrowser) {
		case "chrome":
			js.executeScript("$('.thumb > a')[3].click();");
	    	Thread.sleep(1000);
	    	if(baseUrl.equals(liveUrl)){
	    		commentWrite(liveBoardName);
	        	like(liveBoardName);
	        	commentWrite2(liveBoardName);
	        	//blind(liveBoardName);
	        	delComment2(liveBoardName);            	
	        	delComment(liveBoardName);
	    	} else {
	    		commentWrite(devBoardName);
	        	like(devBoardName);
	        	commentWrite2(devBoardName);
	        	//blind(devBoardName);
	        	delComment2(devBoardName); 
	        	delComment(devBoardName); 
	    	}
	    	//�Խ��� �۾��� �� ����
	    	if(baseUrl.equals(liveUrl)){ 
	    		liveWrite(liveBoardName, "");
	    		boardDelete();
	    	} else if (baseUrl.equals(devUrl)) {
	    		driver.get(baseUrl + "/community/write.hero?code=Collection");
	    		Thread.sleep(1000);
	    		devWrite(devBoardName, "");
	    		boardDelete();	    	
	    	}	
	    	break;
		case "ie":
			System.out.println("IE�� �Խñ� �ۼ� ���� �׽�Ʈ N/A");
			break;
		case "firefox":
			System.out.println("firefox�� �Խñ� �ۼ� ���� �׽�Ʈ N/A");
			break;
		}
		Thread.sleep(1000);
	}
	//@Test
	public void E_community_e_fanArt() throws Exception {
		liveBoardName = "�Ҿ�Ʈ �Խ���";
		devBoardName = "�Ҿ�Ʈ �Խ���";
		js.executeScript("$('.list-community > .fan > a')[0].click();");
		Thread.sleep(1000);
		assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
		assertTrue(driver.getPageSource().contains("�Ҿ�Ʈ ����Ʈ"));
		System.out.println("Ŀ�´�Ƽ > �Ҿ�Ʈ �Խ��� > ����Ʈ : Pass");
		Thread.sleep(1000);
		Thread.sleep(1000);
		if(baseUrl.equals(liveUrl)){//Live������ Ư�� �Խñۿ��� ��� �ۼ�
			js.executeScript("$('select[name=type]').prop('value', '4');");
			js.executeScript("$('input[name=keyword]').prop('value', '����');");
			js.executeScript("$('.btn-search').click();");
			Thread.sleep(1000);
			System.out.println("Ŀ�´�Ƽ > " + liveBoardName + " > �˻� : Pass");
		}
		switch (TestBrowser) {
		case "chrome":
			js.executeScript("$('.thumb > a')[3].click();");
	    	Thread.sleep(1000);
	    	if(baseUrl.equals(liveUrl)){
	    		commentWrite(liveBoardName);
	        	like(liveBoardName);
	        	commentWrite2(liveBoardName);
	        	//blind(liveBoardName);
	        	delComment2(liveBoardName);            	
	        	delComment(liveBoardName);
	    	} else {
	    		commentWrite(devBoardName);
	        	like(devBoardName);
	        	commentWrite2(devBoardName);
	        	//blind(devBoardName);
	        	delComment2(devBoardName); 
	        	delComment(devBoardName); 
	    	}
	    	//�Խ��� �۾��� �� ����
	    	if(baseUrl.equals(liveUrl)){ 
	    		liveWrite(liveBoardName, "");
	    		boardDelete();
	    	} else if (baseUrl.equals(devUrl)) {
	    		driver.get(baseUrl + "/community/write.hero?code=FanArt");
	    		Thread.sleep(1000);
	    		devWrite(devBoardName, "");
	    		boardDelete();	    	
	    	}	
	    	break;
		case "ie":
			System.out.println("IE�� �Խñ� �ۼ� ���� �׽�Ʈ N/A");
			break;
		case "firefox":
			System.out.println("firefox�� �Խñ� �ۼ� ���� �׽�Ʈ N/A");
			break;
		}
		Thread.sleep(1000);
	}
	//@Test
	public void F_ranking_a_corps() throws Exception {
		js.executeScript("$('.list-ranking > .legion > a')[0].click();");
		Thread.sleep(1000);
		assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
		assertTrue(driver.getPageSource().contains("������ ĳ������ ����ġ�������� ������ �����˴ϴ�."));
		System.out.println("��ŷ > ���ܷ�ŷ > �̸���� > ����Ʈ : Pass");
		Thread.sleep(1000);
		js.executeScript("$('input[name=keyword]').val('123123')");
		js.executeScript("$('.input-append > button').click();");
		Thread.sleep(1000);
		assertTrue(driver.getPageSource().contains("�ٽ� �ѹ� Ȯ���Ͻñ� �ٶ��ϴ�"));
		System.out.println("��ŷ > ���ܷ�ŷ > �̸���� > �˻� : Pass");
		if(baseUrl.equals(liveUrl)){
			js.executeScript("$('.ranking-server-tab > ul > li > a')[1].click();");
			Thread.sleep(1000);
			assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
			assertTrue(driver.getPageSource().contains("������ ĳ������ ����ġ�������� ������ �����˴ϴ�."));
			System.out.println("��ŷ > ���ܷ�ŷ > ���丮�� > ����Ʈ : Pass");
			Thread.sleep(1000);
			js.executeScript("$('input[name=keyword]').val('123123')");
			js.executeScript("$('.input-append > button').click();");
			Thread.sleep(1000);
			assertTrue(driver.getPageSource().contains("�ٽ� �ѹ� Ȯ���Ͻñ� �ٶ��ϴ�"));
			System.out.println("��ŷ > ���ܷ�ŷ > ���丮�� > �˻� : Pass");
		}        	        	
	}
	//@Test
	public void F_ranking_b_character() throws Exception {
		js.executeScript("$('.list-ranking > .cha > a')[0].click();");
		Thread.sleep(1000);
		assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
		assertTrue(driver.getPageSource().contains("������ �����ͷθ� �����ǰ� �ֽ��ϴ�."));
		System.out.println("��ŷ > ĳ���ͷ�ŷ > �̸���� > ����Ʈ : Pass");
		Thread.sleep(1000);
		js.executeScript("$('input[name=keyword]').val('123123')");
		js.executeScript("$('.input-append > button').click();");
		Thread.sleep(1000);
		assertTrue(driver.getPageSource().contains("�ٽ� �ѹ� Ȯ���Ͻñ� �ٶ��ϴ�"));
		System.out.println("��ŷ > ���ܷ�ŷ > �̸���� > �˻� : Pass");
		if(baseUrl.equals(liveUrl)){
			js.executeScript("$('.ranking-server-tab > ul > li > a')[1].click();");
			Thread.sleep(1000);
			assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
			assertTrue(driver.getPageSource().contains("������ �����ͷθ� �����ǰ� �ֽ��ϴ�."));
			System.out.println("��ŷ > ���ܷ�ŷ > ���丮�� > ����Ʈ : Pass");
			Thread.sleep(1000);
			js.executeScript("$('input[name=keyword]').val('123123')");
			js.executeScript("$('.input-append > button').click();");
			Thread.sleep(1000);
			assertTrue(driver.getPageSource().contains("�ٽ� �ѹ� Ȯ���Ͻñ� �ٶ��ϴ�"));
			System.out.println("��ŷ > ���ܷ�ŷ > ���丮�� > �˻� : Pass");
		}        	        	
	}	
	//@Test
	public void F_ranking_c_mflCharacter() throws Exception {
		js.executeScript("$('.list-ranking > .mfl > a')[0].click();");
		Thread.sleep(1000);
		assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
		assertTrue(driver.getPageSource().contains("ĳ������ �÷��̼��� ������ �����˴ϴ�."));
		System.out.println("��ŷ > mflĳ���ͷ�ŷ > �̸���� > ����Ʈ : Pass");
		Thread.sleep(1000);
		js.executeScript("$('input[name=keyword]').val('123123')");
		js.executeScript("$('.input-append > button').click();");
		Thread.sleep(1000);
		assertTrue(driver.getPageSource().contains("ĳ������ �÷��̼��� ������ �����˴ϴ�."));
		System.out.println("��ŷ > mflĳ���ͷ�ŷ > �̸���� > �˻� : Pass");
		if(baseUrl.equals(liveUrl)){
			js.executeScript("$('.ranking-server-tab > ul > li > a')[1].click();");
			Thread.sleep(1000);
			assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
			assertTrue(driver.getPageSource().contains("ĳ������ �÷��̼��� ������ �����˴ϴ�."));
			System.out.println("��ŷ > mflĳ���ͷ�ŷ > ���丮�� > ����Ʈ : Pass");
			Thread.sleep(1000);
			js.executeScript("$('input[name=keyword]').val('123123')");
			js.executeScript("$('.input-append > button').click();");
			Thread.sleep(1000);
			assertTrue(driver.getPageSource().contains("ĳ������ �÷��̼��� ������ �����˴ϴ�."));
			System.out.println("��ŷ > mflĳ���ͷ�ŷ > ���丮�� > �˻� : Pass");
		}
	}
	//@Test
	public void F_ranking_d_mflCharacter() throws Exception {
		js.executeScript("$('.has-sub > ul > li > a')[1].click();");
		Thread.sleep(1000);
		assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
		assertTrue(driver.getPageSource().contains("RP�� �������� �����˴ϴ�."));
		System.out.println("��ŷ > mfl���շ�ŷ > �̸���� > ����Ʈ : Pass");
		Thread.sleep(1000);
		js.executeScript("$('input[name=keyword]').val('123123')");
		js.executeScript("$('.input-append > button').click();");
		Thread.sleep(1000);
		assertTrue(driver.getPageSource().contains("������ �����ϴ�."));
		System.out.println("��ŷ > mfl���շ�ŷ > �̸���� > �˻� : Pass");
		if(baseUrl.equals(liveUrl)){
			js.executeScript("$('.ranking-server-tab > ul > li > a')[1].click();");
			Thread.sleep(1000);
			assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
  	       assertTrue(driver.getPageSource().contains("RP�� �������� �����˴ϴ�."));
  	       System.out.println("��ŷ > mfl���շ�ŷ > ���丮�� > ����Ʈ : Pass");
  	       Thread.sleep(1000);
  	       js.executeScript("$('input[name=keyword]').val('123123')");
  	       js.executeScript("$('.input-append > button').click();");
  	       Thread.sleep(1000);
  	       assertTrue(driver.getPageSource().contains("������ �����ϴ�."));
  	       System.out.println("��ŷ > mfl���շ�ŷ > ���丮�� > �˻� : Pass");
		}
	}
	//@Test
	public void F_ranking_e_libaration() throws Exception {
		driver.get(baseUrl + "/index.main?c=n");
		js.executeScript("$('.list-ranking > .liberation > a')[0].click();");
		Thread.sleep(1000);
		js.executeScript("$('.num-list > a')[0].click();");
		liberationRanking("�̸����", 1);
		js.executeScript("$('.num-list > a')[1].click();");
		liberationRanking("�̸����", 2);
		js.executeScript("$('.num-list > a')[2].click();");
		liberationRanking("�̸����", 3);
		js.executeScript("$('.num-list > a')[3].click();");
		liberationRanking("�̸����", 4);
		if(baseUrl.equals(liveUrl)){
			js.executeScript("$('.ranking-server-tab > ul > li > a')[1].click();");
			Thread.sleep(1000);
			js.executeScript("$('.num-list > a')[0].click();");
			liberationRanking("���丮��", 1);
			js.executeScript("$('.num-list > a')[1].click();");
			liberationRanking("���丮��", 2);
			js.executeScript("$('.num-list > a')[2].click();");
			liberationRanking("���丮��", 3);
			js.executeScript("$('.num-list > a')[3].click();");
			liberationRanking("���丮��", 4);
		}
	}
	//@Test
	public void F_ranking_f_backdraft() throws Exception {
		driver.get(baseUrl + "/index.main?c=n");
		js.executeScript("$('.list-ranking > .backdraft > a')[0].click();");
		Thread.sleep(1000);
		assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
		assertTrue(driver.getPageSource().contains("������ Ŭ�����ϸ� ��ŷ�� ��ϵ˴ϴ�."));
		System.out.println("��ŷ > ������ŷ > �̸���� > ����Ʈ : Pass");
		Thread.sleep(1000);
		js.executeScript("$('input[name=keyword]').val('123123')");
		js.executeScript("$('.btn-search').click();");
		Thread.sleep(1000);
		assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
		System.out.println("��ŷ > ������ŷ > �̸���� > �˻� : Pass");
		if(baseUrl.equals(liveUrl)){
			js.executeScript("$('.ranking-server-tab > ul > li > a')[1].click();");
			Thread.sleep(1000);
			assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
			assertTrue(driver.getPageSource().contains("������ Ŭ�����ϸ� ��ŷ�� ��ϵ˴ϴ�."));
			System.out.println("��ŷ > ������ŷ > ���丮�� > ����Ʈ : Pass");
			Thread.sleep(1000);
			js.executeScript("$('input[name=keyword]').val('123123')");
			js.executeScript("$('.btn-search').click();");
			Thread.sleep(1000);
			assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
			System.out.println("��ŷ > ������ŷ > ���丮�� > �˻� : Pass");
		}
	}
	//@Test
	public void F_ranking_g_pantheon() throws Exception {
		driver.get(baseUrl + "/index.main?c=n");
		js.executeScript("$('.list-ranking > .pantheon-time > a')[0].click();");
		Thread.sleep(1000);
		assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
		assertTrue(driver.getPageSource().contains("�ð��� �������� �����˴ϴ�."));
		System.out.println("��ŷ > ������EX��ŷ > �̸���� > ����Ʈ : Pass");
		Thread.sleep(1000);
		js.executeScript("$('input[name=keyword]').val('123123')");
		js.executeScript("$('.btn-search').click();");
		Thread.sleep(1000);
		assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
		System.out.println("��ŷ > ������EX��ŷ > �̸���� > �˻� : Pass");
		if(baseUrl.equals(liveUrl)){
			js.executeScript("$('.ranking-server-tab > ul > li > a')[1].click();");
			Thread.sleep(1000);
			assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
			assertTrue(driver.getPageSource().contains("�ð��� �������� �����˴ϴ�."));
			System.out.println("��ŷ > ������EX��ŷ > ���丮�� > ����Ʈ : Pass");
			Thread.sleep(1000);
			js.executeScript("$('input[name=keyword]').val('123123')");
			js.executeScript("$('.btn-search').click();");
			Thread.sleep(1000);
			assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
			System.out.println("��ŷ > ������EX��ŷ > ���丮�� > �˻� : Pass");
		}
	}
	//@Test
	public void F_ranking_h_raid() throws Exception {
		driver.get(baseUrl + "/index.main?c=n");
		js.executeScript("$('.list-ranking > .raid > a')[0].click();");
		Thread.sleep(1000);
		assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
		assertTrue(driver.getPageSource().contains("������ Ŭ�����ϸ� ��ŷ�� ��ϵ˴ϴ�."));
		System.out.println("��ŷ > ������ŷ > �̸���� > ����Ʈ : Pass");
		Thread.sleep(1000);
		js.executeScript("$('input[name=keyword]').val('123123')");
		js.executeScript("$('.btn-search').click();");
		Thread.sleep(1000);
		assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
		System.out.println("��ŷ > ������ŷ > �̸���� > �˻� : Pass");
		if(baseUrl.equals(liveUrl)){
			js.executeScript("$('.ranking-server-tab > ul > li > a')[1].click();");
			Thread.sleep(1000);
			assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
			assertTrue(driver.getPageSource().contains("������ Ŭ�����ϸ� ��ŷ�� ��ϵ˴ϴ�."));
			System.out.println("��ŷ > ������ŷ > ���丮�� > ����Ʈ : Pass");
			Thread.sleep(1000);
			js.executeScript("$('input[name=keyword]').val('123123')");
			js.executeScript("$('.btn-search').click();");
			Thread.sleep(1000);
			assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
			System.out.println("��ŷ > ������ŷ > ���丮�� > �˻� : Pass");
		}
	}
	//@Test
	public void F_ranking_i_tryRaid() throws Exception {
		driver.get(baseUrl + "/index.main?c=n");
		js.executeScript("$('.list-ranking > .try-raid > a')[0].click();");
		Thread.sleep(1000);
		assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
		assertTrue(driver.getPageSource().contains("������ �õ��ϸ� ��ŷ�� ��ϵ˴ϴ�."));
		System.out.println("��ŷ > �����õ���ŷ > �̸���� > ����Ʈ : Pass");
		Thread.sleep(1000);
		js.executeScript("$('input[name=keyword]').val('123123')");
		js.executeScript("$('.btn-search').click();");
		Thread.sleep(1000);
		assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
		System.out.println("��ŷ > �����õ���ŷ > �̸���� > �˻� : Pass");
		if(baseUrl.equals(liveUrl)){
			js.executeScript("$('.ranking-server-tab > ul > li > a')[1].click();");
			Thread.sleep(1000);
			assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
			assertTrue(driver.getPageSource().contains("������ �õ��ϸ� ��ŷ�� ��ϵ˴ϴ�."));
			System.out.println("��ŷ > �����õ���ŷ > ���丮�� > ����Ʈ : Pass");
			Thread.sleep(1000);
			js.executeScript("$('input[name=keyword]').val('123123')");
			js.executeScript("$('.btn-search').click();");
			Thread.sleep(1000);
			assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
			System.out.println("��ŷ > �����õ���ŷ > ���丮�� > �˻� : Pass");
		}
	}
	//@Test
	public void G_download_a_download() throws Exception {
		driver.get(baseUrl + "/index.main?c=n");
		js.executeScript("$('.list-download > .download > a')[0].click();");
		Thread.sleep(1000);
		assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
		assertTrue(driver.getPageSource().contains("�ٿ�ε�� ���ÿ� ������ ��ġ�˴ϴ�."));
		System.out.println("�ڷ�� > ���Ӵٿ�ε� > ������ ���� : Pass");
		Thread.sleep(1000);
		brokenLinkCheck("Ŭ���̾�Ʈ �ٿ�ε�", "http://download.herowarz.com/live/MCSetup.exe");
		brokenLinkCheck("ũ�ҽ���� �ٿ�ε�", "http://download.herowarz.com/live/MCSetup_Chrome.exe");
		brokenLinkCheck("Nvidia �ٿ�ε�", "http://www.nvidia.co.kr/Download/index.aspx?lang=kr");
		brokenLinkCheck("ATI �ٿ�ε�", "http://support.amd.com/ko-kr/download");
		brokenLinkCheck("Intel �ٿ�ε�", "https://downloadcenter.intel.com/ko/");
		brokenLinkCheck("DirectX �ٿ�ε�", "https://www.microsoft.com/ko-kr/download/details.aspx?id=35");
		System.out.println("�ڷ�� > ���Ӵٿ�ε� > �ٿ�ε� ��ũ Ȯ�� : Pass");
	}
	//@Test
	public void G_download_b_movie() throws Exception {
		liveBoardName = "�ڷ�� > ������";
		devBoardName = "�ڷ�� > ������";
		driver.get(baseUrl + "/index.main?c=n");
		js.executeScript("$('.list-download > .movie > a')[0].click();");
		Thread.sleep(1000);
		assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
		assertTrue(driver.getPageSource().contains("�������� ������ �� �ֽ��ϴ�."));
		if(baseUrl.equals(liveUrl)){
			System.out.println(liveBoardName + " > �ٿ�ε� ��ũ Ȯ�� : Pass");
	     } else {
	    	 System.out.println(devBoardName + " > �ٿ�ε� ��ũ Ȯ�� : Pass");
	     }
		Thread.sleep(1000);
		if(baseUrl.equals(liveUrl)){
			brokenLinkCheck("���� �ٿ�ε�", "http://static.herowarz.com/editor/upload/2015/39/201509220807372_1st%20anniversary%20thanks.zip");
			commentWrite(liveBoardName);
			commentWrite2(liveBoardName);
			like(liveBoardName);
			delComment2(liveBoardName);
			delComment(liveBoardName);
		} else {
			commentWrite(devBoardName);
			commentWrite2(devBoardName);
			like(devBoardName);
			delComment2(devBoardName);
			delComment(devBoardName);
		}
		System.out.println("�ڷ�� > ������ > �󼼺��� ������ ����ۼ� : Pass");
		Thread.sleep(1000);
	}
	//@Test
	public void G_download_c_gallery() throws Exception {
		driver.get(baseUrl + "/index.main?c=n");
		js.executeScript("$('.list-download > .gallery > a')[0].click();");
		Thread.sleep(1000);
		assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
		assertTrue(driver.getPageSource().contains("2016. 09"));
		System.out.println("�ڷ�� > ������ > ����Ʈ : Pass");
		Thread.sleep(1000);
		js.executeScript("$('.gallery-item:eq(0) > img').click();");
		Thread.sleep(1000);
		assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
		assertTrue(driver.getPageSource().contains("���"));
		if(baseUrl.equals(liveUrl)){
			brokenLinkCheck("���� �ٿ�ε�", "http://static.herowarz.com/editor/upload/2016/40/B-58c00bd8d5dc40158b348f9dd1ece35f.png");
			brokenLinkCheck("������ �����", "http://static.herowarz.com/editor/upload/2016/40/B-fd22ea3d649d47c2a2b61a83b29844c0.png");
			System.out.println("�ڷ�� > ������ > �󼼺��� : Pass");
			Thread.sleep(1000);
		} else {
			brokenLinkCheck("���� �ٿ�ε�", "http://static.d.p.herowarz.com/editor/upload/2016/40/B-d72478b7d0444cbdb1a973ac4062b884.png");
			brokenLinkCheck("������ �����", "http://static.d.p.herowarz.com/editor/upload/2016/40/B-97b209d605f142588195f301cd69741e.png");
			System.out.println("�ڷ�� > ������ > �󼼺��� : Pass");
			Thread.sleep(1000);
		}           
	}
	//@Test
	public void G_download_d_wallpaper() throws Exception {
		driver.get(baseUrl + "/index.main?c=n");
		js.executeScript("$('.list-download > .wallpaper > a')[0].click();");
		Thread.sleep(1000);
		assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
		if(baseUrl.equals(liveUrl)){
			assertTrue(driver.getPageSource().contains("2015. 08"));
		} else {
			assertTrue(driver.getPageSource().contains("2016. 01"));
		}
		System.out.println("�ڷ�� > �������� > ����Ʈ : Pass");
		Thread.sleep(1000);
		js.executeScript("$('.gallery-item:eq(0) > img').click();");
		Thread.sleep(1000);
 	    assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
 	    assertTrue(driver.getPageSource().contains("���"));
 	    if(baseUrl.equals(liveUrl)){
 	    	brokenLinkCheck("1920x1080", "http://static.herowarz.com/editor/upload/2015/35/201508271418012_loading_ch_summerfive05.jpg");
 	    	brokenLinkCheck("1600x1200", "http://static.herowarz.com/editor/upload/2015/35/201508271418013_loading_ch_summerfive05.jpg");
 	    	brokenLinkCheck("�������� �����", "http://static.herowarz.com/editor/upload/2015/35/201508271418011_120loading_ch_summerfive05.jpg");
 	    } else {
 	    	brokenLinkCheck("1920x1080", "http://static.d.p.herowarz.com/editor/upload/2017/25/B-04fb00cf344d4cca972dafcd4871e4d1.jpg");
 	    	brokenLinkCheck("1600x1200", "http://static.d.p.herowarz.com/editor/upload/2017/25/B-dafd6c04e9694277b8f0dcae0e5e4d3c.jpg");
 	    	brokenLinkCheck("�������� �����", "http://static.d.p.herowarz.com/editor/upload/2017/25/B-24c1c4466b404669b5ee1eae30dafa4c.jpg");
 	    }           
 	    System.out.println("�ڷ�� > �������� > �󼼺��� : Pass");
 	    Thread.sleep(1000);
	}
	//@Test
	public void G_download_e_kit() throws Exception {
		driver.get(baseUrl + "/index.main?c=n");
		js.executeScript("$('.list-download > .kit > a')[0].click();");
		Thread.sleep(1000);
		assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
		assertTrue(driver.getPageSource().contains("�����ϰ� �����Ͽ� ����� �� �����ϴ�."));
		System.out.println("�ڷ�� > �������� > ������ ���� : Pass");
		Thread.sleep(1000);
		if(baseUrl.equals(liveUrl)){
			brokenLinkCheck("MFL BGMŶ", "http://static.herowarz.com/common/file/download/fankit/Herowarz_MFL_SignalMusic_2016.12.21.zip");
			brokenLinkCheck("�ְ��Ǳ��� �ܺ�", "http://static.herowarz.com/filebox/herowarz_honeybook_v.1.1.zip");
			brokenLinkCheck("�һ���ƮŶ", "http://static.herowarz.com/files/fankit/Kit_201411.zip");
			brokenLinkCheck("LBT �һ���ƮŶ", "http://static.herowarz.com/files/fankit/Herowarz_fankit_2013.11.22_LBT.zip");
		} else {
			brokenLinkCheck("MFL BGMŶ", "http://static.d.p.herowarz.com/common/file/download/fankit/Herowarz_MFL_SignalMusic_2016.12.21.zip");
			//brokenLinkCheck("�ְ��Ǳ��� �ܺ�", "http://static.d.p.herowarz.com/filebox/herowarz_honeybook_v.1.1.zip");
			//brokenLinkCheck("�һ���ƮŶ", "http://static.d.p.herowarz.com/editor/upload/2015/35/201508271418011_120loading_ch_summerfive05.jpg");
			//brokenLinkCheck("LBT �һ���ƮŶ", "http://static.d.p.herowarz.com/editor/upload/2015/35/201508271418011_120loading_ch_summerfive05.jpg");
		}
		System.out.println("�ڷ�� > �һ���ƮŶ > �ٿ�ε� ��ũ Ȯ�� : Pass");
		Thread.sleep(1000);
	}
	//@Test
	public void H_cs_a_faq() throws Exception {
		driver.get(baseUrl + "/index.main?c=n");
		js.executeScript("$('.list-cs > .faq > a')[0].click();");
		Thread.sleep(1000);
		assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
		assertTrue(driver.getPageSource().contains("��ġ/����"));
		System.out.println("�������� > faq > ����Ʈ : Pass");
		Thread.sleep(1000);
		js.executeScript("$('.question > a')[0].click()");
		Thread.sleep(1000);
		assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
		assertTrue(driver.getPageSource().contains("��ȸ"));
		System.out.println("�������� > faq > �󼼺��� : Pass");
	}
	//@Test
	public void H_cs_b_inquiry() throws Exception {
		driver.get(baseUrl + "/index.main?c=n");
		js.executeScript("$('.list-cs > .inquiry > a')[0].click();");
		Thread.sleep(1000);
		assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
		assertTrue(driver.getPageSource().contains("Ȯ���� �ּ���!"));
		System.out.println("�������� > 1:1���� > ����Ʈ : Pass");
		Thread.sleep(1000);
		if(baseUrl.equals(devUrl)){
			js.executeScript("$('.section-btn > a')[0].click();");
			Thread.sleep(1000);
			assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
			assertTrue(driver.getPageSource().contains("���ǳ����� ��Ȯ�� �Է��� �ֽñ� �ٶ��ϴ�."));
			System.out.println("�������� > faq > �󼼺��� : Pass");
			new Select(driver.findElement(By.id("inquiryCategoryFirst"))).selectByVisibleText("��Ÿ");
			System.out.println ("1:1����  > 1�� �з� ���� > Pass");
			new Select(driver.findElement(By.id("inquiryCategorySecond"))).selectByVisibleText("��Ÿ");
    		System.out.println ("1:1����  > 2�� �з� ���� > Pass");
    		Thread.sleep(1000);
    		js.executeScript("$('#inquiryTitle').val('�׽�Ʈ ���� �Դϴ�.');");
    		js.executeScript("$('#inquiryContent').val('�׽�Ʈ ���� �Դϴ�.');");
    	    driver.findElement(By.name("uploadfile")).sendKeys("C:\\Users\\Administrator\\Desktop\\������\\1.jpg");
    	    Thread.sleep(1000);
    	    js.executeScript("$('.uid_inquiry_submit').click();");
    	    Thread.sleep(1000);
    	    acceptAlert("���� ��� �޼���");
    		System.out.println ("1:1����  > ��� > Pass");
    		Thread.sleep(1000);    		
		}
      js.executeScript("$('.cell-subject > a')[0].click();");
      Thread.sleep(1000);
      assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));
      assertTrue(driver.getPageSource().contains("���"));
      System.out.println ("1:1����  > �󼼺��� > Pass");
      Thread.sleep(1000);
	}

	@AfterMethod
	public void testEnd() throws Exception {
		Thread.sleep(2000);
		driver.close();  //driver.quit() ���� �������� ��� ����
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}
}