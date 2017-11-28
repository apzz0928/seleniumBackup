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
import org.openqa.selenium.JavascriptExecutor; //js를 사용하기 위해 추가
import org.openqa.selenium.Alert; //alert 처리용
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
  		CHROMEDRIVER_FILE_PATH = "D:/Selenium/driver/chromedriver.exe"; //크롬 드라이버 파일 경로
  		FIREFOXDRIVER_FILE_PATH = "D:/Selenium/driver/geckodriver.exe"; //파이어폭스 드라이버 파일 경로
  		INTERNETEXPLORER_FILE_PATH = "D:/Selenium/driver/IEDriverServer32.exe"; //IE 드라이버 파일 경로  		
  		
  		if(TestBrowser.equals("chrome")){
  		//테스트 브라우저를 크롬으로 설정
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
        //javaScript를 쓸 수 있도록 해줌
        js = (JavascriptExecutor) driver;        
        baseUrl = "https://rct-d-p.astorm.com";
    }
 
  	//프롬프트창 입력 후 확인
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
    
  	//alert 확인 처리
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
  	
  	//입력된 URL 정상 여부 확인
  	public static boolean brokenLinkCheck (String urlName, String urlLink){
        try {
            huc = (HttpURLConnection)(new URL(urlLink).openConnection());
            huc.setRequestMethod("HEAD");
            huc.connect();
            respCode = huc.getResponseCode();
            if(respCode >= 400 && respCode != 503){ //자료실 다이렉트x 다운로드 링크 503나서 예외처리 그래도 http코드는 볼수있게함
            	System.out.println("***** " + urlName +" : 링크 상태 HTTP : " + respCode + " *****");
            	fail();
            } else {
            	System.out.println(urlName +" : 링크 상태 HTTP : " + respCode);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
		return false;
    }

  	//window 스위칭용
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
        elementWait("name", "j_username"); //해당 element 찾을때까지  최대 10초 대기 후 에도 없으면 종료
        assertTrue(driver.getPageSource().contains("User ID"));
        assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));        
        System.out.println("로그인 페이지 : Pass");
        js.executeScript("$('input[name=j_username]').prop('value', 'apzz0928')");
        js.executeScript("$('input[name=j_password]').prop('value', 'qordlf!@34')");
        elementValueWait("name", "j_username", "apzz0928");
        js.executeScript("$('.ac_btn_text')[0].click();");
        elementWait("id", "ac_layout_aside");
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
    
  	//@Test(priority = 2)
    public void accountSearch() throws Exception {
    	driver.get(baseUrl + "/account/gamedata/searchform.ct");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");
    	assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
    	assertTrue(driver.getPageSource().contains("계정 검색"));
    	System.out.println("게임 계정 관리 > 계정 검색 > 페이지 접근 : Pass");
    	js.executeScript("$('.fa-circle-o:eq(0)').click();");
    	js.executeScript("$('textarea[name=searchKeyword]').val('영권');");
    	js.executeScript("$('#uid_account_search_form_search_btn').click();");
    	System.out.println("게임 계정 관리 > 계정 검색 > 검색 : Pass");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");
        elementWait("class", "ac_container_pagination");
    	if(TestBrowser.equals("chrome")){
        	js.executeScript("$('.ac_notice > tr:eq(2) > td:eq(12) > div').click();");
        	handleMultipleWindows("Control Tower @ Cockpit");
        	elementWait("class", "ac_initalize_area_friends_list_pop");
        	js.executeScript("$('input[name=]').prop('value', '군단명');");
        	js.executeScript("$('.au_text_center:eq(0)').click();");
        	assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
        	assertTrue(driver.getPageSource().contains("데이터가 없습니다."));
        	assertTrue(driver.getPageSource().contains("군단명"));
        	System.out.println("게임 계정 관리 > 계정 검색 > 친구목록 팝업 확인 : Pass");
        	driver.close();
        	handleMultipleWindows("Control Tower @ reboot");
    	}
    	js.executeScript("$('.uid_game_account_init:eq(1)').click();");
    	elementWait("class", "ac_container_message_footer");
        assertTrue(driver.getPageSource().contains("정말 초기화 하시겠습니까?"));
        js.executeScript("$('.uid_ok_btn').click();");
    	elementWait("class", "ac_container_message_footer");
        js.executeScript("$('.uid_ok_btn').click();");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");
    	js.executeScript("$('.fa-circle-o:eq(0)').click();");
    	js.executeScript("$('textarea[name=searchKeyword]').val('영권');");
    	js.executeScript("$('#uid_account_search_form_search_btn').click();");
        elementWait("class", "ac_container_pagination");
    	js.executeScript("$('.ac_notice > tr:eq(2) > td:eq(1) > a')[0].click();");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");
    	System.out.println("게임 계정 관리 > 계정 검색 : Pass");
  	}
    
  	//@Test(priority = 3)
    public void recovery() throws Exception {
        driver.get(baseUrl + "/account/recovery.ct");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");
        assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
        assertTrue(driver.getPageSource().contains("페니도 별도 복구 가능합니다."));
        System.out.println("게임 계정 관리 > 계정 복구 > 페이지 접근 : Pass");
    }
    
  	//@Test(priority = 4)
    public void uuidTracking() throws Exception {
    	//게임 계정 관리 > uuid추적 으로 이동
        driver.get(baseUrl + "/historylog/uuidTracking.ct");
        //해당 메세지가 있으면 Fail
        assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
    }
  	
  	@Test(priority = 5)
    public void clanList() throws Exception {
        driver.get(baseUrl + "/clan/clanList.ct");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");
        assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
        System.out.println("클랜 관리 > 클랜 리스트 > 페이지 접근 : Pass");
        js.executeScript("$('.ac_container_right > div > a')[0].click();");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");
        System.out.println("클랜 관리 > 클랜 추가 > 페이지 접근 : Pass");
        js.executeScript("$('.uid_duplicate_check_btn').click();");
        elementWait("class", "type_inner_space");
        js.executeScript("$('input[name=]').val('9000115');");
        js.executeScript("$('button[type=submit]')[1].click();");
        elementWait("class", "uid_clan_name_use_btn");
        js.executeScript("$('.uid_clan_name_use_btn').click();");
        elementWait("class", "ac_container_message_footer");
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println("클랜 관리 > 클랜 추가 > 클랜명 중복 확인 : Pass");
        js.executeScript("$('.uid_search_clan_master_btn').click();");
        js.executeScript("$('input[name=]').val('9000115');");
        js.executeScript("$('button[type=submit]')[1].click();");
        elementWait("class", "uid_use_clan_master_btn");
        js.executeScript("$('.uid_use_clan_master_btn').click();");
        elementWait("class", "ac_container_message_footer");
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println("클랜 관리 > 클랜 추가 > 클랜장 중복 확인 : Pass");
        js.executeScript("$('button[type=submit]').click();");
        elementWait("class", "ac_container_message_footer");
        js.executeScript("$('.uid_ok_btn').click();");
        elementWait("class", "ac_container_message_footer");
        js.executeScript("$('.uid_ok_btn').click();");        
        System.out.println("클랜 관리 > 클랜 추가 > 클랜 추가 완료 : Pass");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");
        js.executeScript("$('input[name=name]').val('9000115');");
        js.executeScript("$('input[name=master_account_key]').val('9000115');");
        js.executeScript("$('.ac_combo_header_cell')[3].click();");
        js.executeScript("$('li[data-key=1]').click();");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");
        System.out.println("클랜 관리 > 클랜 리스트 > 클랜 검색 : Pass");
        js.executeScript("$('.uid_clan_name_link')[0].click();");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");
        System.out.println("클랜 관리 > 클랜관리 > 상세보기 페이지 접근 : Pass");
        js.executeScript("$('.uid_duplicate_check_btn').click();");
        js.executeScript("$('input[name=]').val('9000116');");
        js.executeScript("$('button[type=submit]')[2].click();");
        elementWait("class", "uid_clan_name_use_btn");
        js.executeScript("$('.uid_clan_name_use_btn').click();");
        elementWait("class", "ac_container_message_footer");
        assertTrue(driver.getPageSource().contains("해당 클랜명을 사용하시겠습니까?"));
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println("클랜 관리 > 클랜관리 > 클랜이름 수정 : Pass");
        js.executeScript("$('.fa-chevron-down').click();");
        js.executeScript("$('li[data-key=1]').click();");
        js.executeScript("$('button[type=submit]')[1].click();");
        elementWait("class", "ac_container_message_footer");
        assertTrue(driver.getPageSource().contains("클랜정보를 수정하시겠습니까?"));
        js.executeScript("$('.uid_ok_btn').click();");
        elementWait("class", "ac_container_message_footer");
        assertTrue(driver.getPageSource().contains("수정 되었습니다."));
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println("클랜 관리 > 클랜관리 > 클랜상태 수정 : Pass");        
        js.executeScript("$('.uid_add_clan_member_layer_btn').click();");
        js.executeScript("$('input[name=]').val('9000116');");
        js.executeScript("$('button[type=submit]')[2].click();");
        elementWait("class", "uid_add_clan_member_btn");
        js.executeScript("$('.uid_add_clan_member_btn').click();");
        assertTrue(driver.getPageSource().contains("추가 하시겠습니까?"));
        elementWait("class", "ac_container_message_footer");
        js.executeScript("$('.uid_ok_btn').click();");
        elementWait("class", "ac_container_message_footer");
        assertTrue(driver.getPageSource().contains("추가 되었습니다."));
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println("클랜 관리 > 클랜관리 > 클랜원 추가 : Pass");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");
        js.executeScript("$('.uid_expel_btn')[0].click();");
        elementWait("class", "ac_container_message_footer");
        assertTrue(driver.getPageSource().contains("추방 하시겠습니까?"));
        js.executeScript("$('.uid_ok_btn').click();");
        elementWait("class", "ac_container_message_footer");
        assertTrue(driver.getPageSource().contains("추방 되었습니다."));
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println("클랜 관리 > 클랜관리 > 클랜원 추방 : Pass");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");
        js.executeScript("$('.uid_clan_dismantling_btn').click();");
        elementWait("class", "ac_container_message_footer");
        assertTrue(driver.getPageSource().contains("클랜을 해체 하시겠습니까?"));
        js.executeScript("$('.uid_ok_btn').click();");
        elementWait("class", "ac_container_message_footer");
        assertTrue(driver.getPageSource().contains("해체 되었습니다."));
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println("클랜 관리 > 클랜관리 > 클랜 해체 : Pass");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");
        js.executeAsyncScript("$('.au_text_center').click();");
        elementWait("id", "ac_layout_aside");
        elementWait("id", "ac_layout_footer");       
        System.out.println("클랜 관리 > 클랜관리 > 클랜 리스트 페이지로 이동 : Pass");
    }
    
  	//@Test(priority = 6)
    public void Logout(){
  		js.executeScript("$('.member-info > a')[0].click();");
  		elementWait("class", "uid_login_type");
  	};
    
    @AfterClass
    public static void tearDown() throws Exception {
    	//테스트 완료 3초 후 브라우저 종료
        Thread.sleep(3000);
    	driver.close();  //driver.quit() 사용시 오류나는 경우 있음
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }
  	
}
