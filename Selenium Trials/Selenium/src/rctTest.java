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
        accountKey = "350238";
        startAt = "2017.06.05 00:00:00";
        endAt = "2017.07.01 00:00:00";
        startAt1 = "2017-06-05 00:00:00";
        endAt1 = "2017-07-01 00:00:00";
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
  	
  	//브라우저에 따라 새로고침 대기시간 변경
  	public static void refreshReady () throws Exception {
        if(TestBrowser.equals("chrome")){
        	Thread.sleep(1000);
        } else if (TestBrowser.equals("firefox")) {
            Thread.sleep(3000);        	
        } else if (TestBrowser.equals("ie")) {
        	Thread.sleep(6000);
        }
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
  		
    @Test
    public void A_Login() throws Exception {
    	//로그인
        driver.get(baseUrl + "/login/form.ct");
        Thread.sleep(1000);
        //해당 메세지가 있으면 Pass
        assertTrue(driver.getPageSource().contains("User ID"));
        //해당 메세지가 있으면 Fail
        assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));        
        System.out.println("로그인 페이지 : Pass");
        js.executeScript("$('input[name=j_username]').prop('value', 'apzz0928')");
        js.executeScript("$('input[name=j_password]').prop('value', 'qordlf!@34')");
        js.executeScript("$('.ac_btn_text')[0].click();");
        System.out.println("로그인 : Pass");
        Thread.sleep(1000);        
    }
    
    @Test
    public void B_lang_change() throws Exception {
    	driver.get(baseUrl + "/common/locale/ko");
        assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
        assertTrue(driver.getPageSource().contains("true"));
        System.out.println("언어변경 : Pass");
        Thread.sleep(1000);
        driver.get(baseUrl);
        Thread.sleep(1000);
	}
    
    //@Test
    public void gameMng_A_sendMail() throws Exception {
        driver.get(baseUrl + "/gmcmd/sendMailForm.ct");
        refreshReady();
        assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
        assertTrue(driver.getPageSource().contains("특정 유저에게 우편을 발송하는 기능입니다."));
        System.out.println("게임 관리 > 우편 전송 > 페이지 접근 : Pass");
        driver.findElement(By.xpath("//div[@id='sk']/div/a/i")).click();
        driver.findElement(By.cssSelector("ul.normal > li.selected")).click();
        js.executeScript("$('input[name=recvAccountKey]').val('350238');");
        js.executeScript("$('input[name=sendNickName]').val('HerowarzTest');");
        js.executeScript("$('input[name=mailTitle]').val('우편 제목입니다.');");
        js.executeScript("$('textarea[name=mailContents]').val('우편 내용입니다.');");
        js.executeScript("$('input[name=itemCode]').val('500001');");
        js.executeScript("$('input[name=itemCount]').val('1');");
        js.executeScript("$('input[name=gold]').val('100');");
        js.executeScript("$('input[name=reason]').val('우편 테스트 입니다.');");
        js.executeScript("$('.ac_btn_text')[1].click()");
        Thread.sleep(1000);
        assertTrue(driver.getPageSource().contains("GM 명령이 호출 되었습니다."));
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println("게임 관리 > 우편 전송 > 전송 : Pass");
        refreshReady();
        js.executeScript("$('input[name=keyward]').val('검색어 입니다.');");
        js.executeScript("$('.ac_btn_text')[2].click()");
        Thread.sleep(1000);
        assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
        System.out.println("게임 관리 > 우편 전송 > 검색 : Pass");
    }
    
    //@Test
    public void gameMng_B_systemMessage() throws Exception {
    	driver.get(baseUrl + "/gmcmd/systemMessageForm.ct");
        Thread.sleep(1000);
        assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
        assertTrue(driver.getPageSource().contains("모든 공지는 채팅창에 출력됩니다."));
        System.out.println("게임 관리 > systemMessage > 페이지 접근 : Pass");
        js.executeScript("$('.fa-circle-o')[1].click();");
        js.executeScript("$('input[name=displayMessage]').val('게임 공지 테스트 입니다.')");
        js.executeScript("$('.fa-circle-o')[3].click();");
        js.executeScript("$('.ac_btn_text')[0].click();");
        Thread.sleep(1000);
        assertTrue(driver.getPageSource().contains("GM 명령이 호출 되었습니다."));
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println("게임 관리 > systemMessage > 전송 : Pass");
        refreshReady();
        js.executeScript("$('input[name=keyward]').val('검색어 입니다.');");
        js.executeScript("$('.ac_btn_text')[1].click()");
        refreshReady();
        assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
        System.out.println("게임 관리 > systemMessage > 검색 : Pass");
        Thread.sleep(1000);
    }    
    //@Test
    public void gameMng_C_ingameNotice() throws Exception {
    	//게임관리 > 인게임 공지로 이동
        driver.get(baseUrl + "/gmcmd/ingameNotice.ct");
        Thread.sleep(1000);
        assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
        assertTrue(driver.getPageSource().contains("인게임 이벤트 공지는 반복만 선택 할 수 있습니다."));
        System.out.println("게임 관리 > 인게임 공지 > 페이지 접근 : Pass");
        js.executeScript("$('input[name=startAt]').val('" + startAt + "');");
        js.executeScript("$('input[name=endAt]').val('" + endAt + "');");
        for(int i = 0; i < 7; i++){
        	js.executeScript("$('.fa-square-o')[0].click()");
        }
        js.executeScript("$('input[name=repeatTime]').val('1');");
        js.executeScript("$('input[name=name]').val('인게임 공지 제목입니다.');");
        js.executeScript("$('input[name=desc]').val('인게임 공지 설명입니다.');");
        js.executeScript("$('.fa-square-o')[0].click()");
        js.executeScript("$('.fa-circle-o')[2].click();");
        js.executeScript("$('.ac_input:eq(11)').val('10')");
        js.executeScript("$('.ac_input:eq(12)').val('테스트 공지 내용')");
        js.executeScript("$('.ac_btn_text')[2].click();");
        Thread.sleep(1000);
        assertTrue(driver.getPageSource().contains("입력되었습니다."));
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println("게임 관리 > 인게임 공지 > 등록 > Pass");
        refreshReady();
        js.executeScript("$('.ac_btn_text')[3].click();");
        assertTrue(driver.getPageSource().contains("변경 되었습니다."));
        refreshReady();
        js.executeScript("$('.uid_ok_btn').click();");
        refreshReady();
        js.executeScript("$('.ac_btn_text')[3].click();");
        refreshReady();
        assertTrue(driver.getPageSource().contains("변경 되었습니다."));
        js.executeScript("$('.uid_ok_btn').click();");
        refreshReady();
        js.executeScript("$('.ac_btn_text')[3].click();");
        assertTrue(driver.getPageSource().contains("변경 되었습니다."));
        refreshReady();
        js.executeScript("$('.uid_ok_btn').click();");
        refreshReady();
        js.executeScript("$('.ac_btn_text')[4].click();");
        assertTrue(driver.getPageSource().contains("변경 되었습니다."));
        refreshReady();
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println("게임 관리 > 인게임 공지 > 상태 변경 > Pass");
        refreshReady();
        js.executeScript("$('.ac_btn_text')[3].click();");
        refreshReady();
        js.executeScript("$('.uid_layer_close').click();");
        System.out.println("게임 관리 > 인게임 공지 > 히스토리 확인 > Pass");
    }
    //@Test
    public void gameMng_D_kickUser() throws Exception {
    	//게임관리 > kickUser로 이동
        driver.get(baseUrl + "/gmcmd/kickUserForm.ct");
        Thread.sleep(1000);
        assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
        System.out.println("게임 관리 > kickUser > 페이지 접근 > Pass");
        driver.get(baseUrl + "/gmcmd/kickUserForm.ct");
        driver.findElement(By.xpath("//div[@id='sk']/div/a/i")).click();
        driver.findElement(By.cssSelector("ul.normal > li.selected")).click();
        js.executeScript("$('input[name=accountKey]').val('" + accountKey + "')");
        js.executeScript("$('input[name=forbidTime]').val('0')");
        js.executeScript("$('input[name=reason]').val('KickUser 테스트')");
        Thread.sleep(1000);   
        driver.findElement(By.cssSelector("button.ac_btn_inner")).click();
        Thread.sleep(1000);
        assertTrue(driver.getPageSource().contains("GM 명령이 호출 되었습니다."));
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println("게임 관리 > kickUser : Pass");
        Thread.sleep(1000);   
    }
    //@Test
    public void gameMng_F_mailOut() throws Exception {
    	driver.get(baseUrl + "/gmcmd/mailoutform.ct");
        refreshReady();
        assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
        System.out.println("게임 관리 > 우편 대량 발송 > 페이지 접근 : Pass");
        driver.findElement(By.xpath("//div[@id='sk']/div/a/i")).click();
        driver.findElement(By.cssSelector("ul.including > li")).click();
        driver.findElement(By.name("filedata")).sendKeys("C:\\Users\\Administrator\\Downloads\\템골드전부미첨부.xlsx");
        driver.findElement(By.name("reason")).sendKeys("우편 대량 발송 테스트 입니다.");
        driver.findElement(By.cssSelector("button.ac_btn_inner")).click();
        Thread.sleep(1000);
        assertTrue(driver.getPageSource().contains("대량 메일 발송이 시작됩니다."));
        driver.findElement(By.cssSelector("button.ac_btn.uid_ok_btn")).click();
        Thread.sleep(3000);
        assertTrue(driver.getPageSource().contains("(발송완료)"));
        System.out.println("게임 관리 > 우편 대량 발송 > 발송완료 : Pass");
        driver.findElement(By.cssSelector("span.ac_btn_text.au_text_center")).click();
        Thread.sleep(1000);   
    }
    
    //@Test
    public void gameMng_G_burningEvent() throws Exception {
    	driver.get(baseUrl + "/event/burning/list.ct");
        refreshReady();
        assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
        assertTrue(driver.getPageSource().contains("합산되오니 주의해주세요."));
        System.out.println("게임 관리 > 버닝 이벤트 > 페이지 접근 : Pass");
        js.executeScript("$('.au_space_right_5 > a > div > div > div > span')[0].click();");
        refreshReady();
        js.executeScript("$('.fa-chevron-down:eq(1)').click();");
        js.executeScript("$('li[data-key=0]').click();");
        js.executeScript("$('input[name=]:eq(0)').val('" + startAt1 + "');");
        js.executeScript("$('input[name=]:eq(1)').val('" + endAt1 + "');");
        Thread.sleep(1000);
        js.executeScript("$('.ac_btn_text:eq(2)').click();");
        assertTrue(driver.getPageSource().contains("추가 되었습니다."));
        Thread.sleep(1000);
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println("게임 관리 > 버닝 이벤트 > 등록 : Pass");
        refreshReady();
        js.executeScript("$('.uid_sync_burning_conf_btn').click();");
        Thread.sleep(1000);
        assertTrue(driver.getPageSource().contains("게임 서버에 적용하시겠습니까?"));
        js.executeScript("$('.uid_ok_btn').click();");
        Thread.sleep(1000);
        assertTrue(driver.getPageSource().contains("확인하세요."));
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println("게임 관리 > 버닝 이벤트 > 동기화 : Pass");
        refreshReady();
        js.executeScript("$('.uid_del_burning_conf_btn').click();");
        assertTrue(driver.getPageSource().contains("이벤트를 다시 등록해야 합니다."));
        Thread.sleep(1000);
        js.executeScript("$('.uid_ok_btn').click();"); 
        Thread.sleep(1000);
        assertTrue(driver.getPageSource().contains("이벤트가 취소 되었습니다."));
        js.executeScript("$('.uid_ok_btn').click();"); 
        System.out.println("게임 관리 > 버닝 이벤트 > 삭제 : Pass");
        refreshReady();
        js.executeScript("$('.uid_sync_burning_conf_btn').click();");
        Thread.sleep(1000);
        assertTrue(driver.getPageSource().contains("게임 서버에 적용하시겠습니까?"));
        js.executeScript("$('.uid_ok_btn').click();");
        Thread.sleep(1000);
        assertTrue(driver.getPageSource().contains("확인하세요."));
        js.executeScript("$('.uid_ok_btn').click();");
        refreshReady();
        js.executeScript("$('.ac_container_tab_header > ul > li > a')[1].click();");
        refreshReady();
        assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
        System.out.println("게임 관리 > 버닝 이벤트 > 종료 탭 : Pass");
        Thread.sleep(1000);
    }
    
    //@Test
    public void gameMng_H_getServerStatus() throws Exception {
        driver.get(baseUrl + "/cache/getServerStatus.ct");
        refreshReady();
        assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
        assertTrue(driver.getPageSource().contains("서버로 전송 되어 설정됩니다."));
        System.out.println("게임 관리 > 서버 제어 > 페이지 접근 : Pass");
        js.executeScript("$('.ac_input:eq(1)').val('50');");
        js.executeScript("$('.fa-chevron-down:eq(1)').click();");
        js.executeScript("$('li[data-key=true]').click();");
        js.executeScript("$('.ac_input:eq(3)').val('2016.01.01 00:00:00');");
        js.executeScript("$('.ac_input:eq(4)').val('2016.01.02 00:00:00');");
        js.executeScript("$('.fa-chevron-down:eq(2)').click();");
        js.executeScript("$('li[data-key=enable]').click();");
        js.executeScript("$('.ac_btn_text:eq(0)').click();");
        Thread.sleep(2000);
        assertTrue(driver.getPageSource().contains("정상적으로 설정하였습니다."));
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
        assertTrue(driver.getPageSource().contains("정상적으로 설정하였습니다."));
        js.executeScript("$('.uid_ok_btn').click();");
        refreshReady();
        System.out.println("게임 관리 > 서버 제어 > 내용 변경 : Pass");
        js.executeScript("$('input[name=keyward]').val('검색어 입니다.');");
        js.executeScript("$('.ac_btn_text')[1].click();");
        refreshReady();
        assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
        System.out.println("게임 관리 > 서버 제어 > 검색 : Pass");
    }
    
    //@Test
    public void gameMng_I_limitdrop() throws Exception {
    	driver.get(baseUrl + "/event/limitdrop/confUploadForm.ct");
        refreshReady();
        assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
        assertTrue(driver.getPageSource().contains("드랍되지 않습니다."));
        System.out.println("게임 관리 > 아이템 드랍 설정 > 페이지 접근 : Pass");
        driver.findElement(By.name("filedata")).sendKeys("C:\\Users\\Administrator\\Downloads\\dropConfSample.xlsx");
        js.executeScript("$('.au_text_center:Eq(1)').click();");
        refreshReady();
        js.executeScript("$('.au_text_center:Eq(3)').click();");
        Thread.sleep(1000);
        assertTrue(driver.getPageSource().contains("게임에 반영됩니다."));
        js.executeScript("$('.uid_ok_btn').click();");
        Thread.sleep(1000);
        assertTrue(driver.getPageSource().contains("reload를 눌러야 게임에 반영됩니다."));
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println("게임 관리 > 아이템 드랍 설정 > 상태 off로 변경 : Pass");
        refreshReady();
        js.executeScript("$('.au_text_center:Eq(3)').click();");
        Thread.sleep(1000);
        assertTrue(driver.getPageSource().contains("게임에 반영됩니다."));
        js.executeScript("$('.uid_ok_btn').click();");
        Thread.sleep(1000);
        assertTrue(driver.getPageSource().contains("reload를 눌러야 게임에 반영됩니다."));
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println("게임 관리 > 아이템 드랍 설정 > 상태 on으로 변경 : Pass");
        refreshReady();
        js.executeScript("$('.au_text_center:Eq(4)').click();");
        Thread.sleep(1000);
        assertTrue(driver.getPageSource().contains("게임 서버에 적용 되었습니다."));
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println("게임 관리 > 아이템 드랍 설정 > reload : Pass");
        refreshReady();        
    }
    
    //@Test
    public void gameMng_J_ingameGuide() throws Exception {
        driver.get(baseUrl + "/gmcmd/ingameGuide/list.ct");
        refreshReady();
        assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
        System.out.println("게임 관리 > 인게임 가이드 > 페이지 접근 : Pass");
        js.executeScript("$('.ac_btn_text:eq(31)').click();");
        Thread.sleep(1000);
        js.executeScript("$('input[name=firstCategory]').prop('value', '인게임 가이드 테스트 대분류 입니다.');");
        js.executeScript("$('input[name=secondCategory]').prop('value', '인게임 가이드 테스트 소분류 입니다.');");
        js.executeScript("$('.cke_wysiwyg_frame').contents().find('.cke_editable').text('내용 입력입니다.')");
        js.executeScript("$('input[name=order]').val('0');");
        js.executeScript("$('.uid_ingame_guide_save_btn').click();");
        Thread.sleep(1000);
        js.executeScript("$('.uid_ok_btn').click();");
        Thread.sleep(1000);
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println("게임 관리 > 인게임 가이드 > 작성 : Pass");
        refreshReady();
        js.executeScript("$('.ac_btn_text.au_text_center').eq(2).click();");
        Thread.sleep(1000);
        js.executeScript("$('input[name=firstCategory]').prop('value', '--- 대분류 대분류 대분류 ---');");
        js.executeScript("$('input[name=secondCategory]').prop('value', '---소분류 소분류 소분류---');");
        js.executeScript("$('.cke_wysiwyg_frame').contents().find('.cke_editable').text('--- 내용 내용 내용 ---')");
        js.executeScript("$('.uid_ingame_guide_save_btn').click();");
        Thread.sleep(1000);
        js.executeScript("$('.uid_ok_btn').click();");
        //저장되었습니다.
        Thread.sleep(1000);
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println("게임 관리 > 인게임 가이드 > 수정 : Pass");
        refreshReady();
        //인게임 가이드 삭제
        js.executeScript("$('.ac_btn_text')[3].click();");
        //삭제하시겠습니까?
        Thread.sleep(1000);
        js.executeScript("$('.uid_ok_btn').click();");
        Thread.sleep(1000);
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println("게임 관리 > 인게임 가이드 > 삭제 : Pass");
        refreshReady();
    }
    
    //@Test
    public void gameMng_K_onlineUserCountInfo() throws Exception {
    	//게임관리 > 온라인 유저수 조회로 이동
        driver.get(baseUrl + "/gmcmd/onlineUserCountInfo.ct");
        refreshReady();
        assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
        assertTrue(driver.getPageSource().contains("온라인 유저수 조회"));
        System.out.println("게임 관리 > 온라인 유저수 조회 : Pass");
    }
    
    //@Test
    public void gameMng_L_slangForm() throws Exception {
        driver.get(baseUrl + "/gmcmd/slangForm.ct");
        refreshReady();
        assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
        assertTrue(driver.getPageSource().contains("정규 표현식"));
        System.out.println("게임 관리 > 금칙어 관리 > 페이지 접근 : Pass");
        //금칙어 관리 테스트 후 추가
        js.executeScript("$('textarea[name=]:eq(0)').prop('value', '금칙어 추가 정규 표현식 입력창 입니다.');");
        js.executeScript("$('input[name=]:eq(0)').prop('value', '금칙어 추가 코멘트 입력창 입니다.');");
        js.executeScript("$('textarea[name=]:eq(1)').prop('value', '금칙어 등록 테스트 메세지 입력창 입니다.');");
        js.executeScript("$('.uid_slang_word_add_test_btn').click();");
        assertTrue(driver.getPageSource().contains("일치하는 패턴이 존재하지 않습니다."));
        System.out.println("게임 관리 > 금칙어 관리 > 등록 > 테스트 결과 : Pass");
        Thread.sleep(1000);
        js.executeScript("$('.uid_slang_word_add_btn').click();");
        Thread.sleep(1000);
        assertTrue(driver.getPageSource().contains("추가 하시겠습니까?"));
        js.executeScript("$('.uid_cancel_btn').click();");
        js.executeScript("$('.uid_slang_word_add_btn').click();");
        Thread.sleep(1000);
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println("게임 관리 > 금칙어 관리 > 등록 : Pass");
        Thread.sleep(1000);
        js.executeScript("$('.uid_ok_btn').click();");
        //금칙어 관리 테스트 후 수정
        js.executeScript("$('.uid_slang_word_list_modify_btn').last().click();");
        js.executeScript("$('textarea[name=]:eq(2)').prop('value', '금칙어 수정 정규 표현식 입력창 입니다.');");
        js.executeScript("$('input[name=]:eq(3)').prop('value', '금칙어 수정 코멘트 입력창 입니다.');");
        js.executeScript("$('input[name=]:eq(4)').prop('value', '금칙어 수정 테스트 메세지 입력창 입니다.');");
        js.executeScript("$('.uid_slang_word_modify_test_btn').click();");
        assertTrue(driver.getPageSource().contains("일치하는 패턴이 존재하지 않습니다."));
        System.out.println("게임 관리 > 금칙어 관리 > 수정 > 테스트 결과 : Pass");
        Thread.sleep(1000);
        js.executeScript("$('.uid_slang_word_modify_btn').click();");
        Thread.sleep(1000);
        assertTrue(driver.getPageSource().contains("정말 수정하시겠습니까?"));
        js.executeScript("$('.uid_cancel_btn').click();");
        js.executeScript("$('.uid_slang_word_modify_btn').click();");
        Thread.sleep(1000);
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println("게임 관리 > 금칙어 관리 > 수정 : Pass");
        Thread.sleep(1000);
        js.executeScript("$('.uid_ok_btn').click();");
        //금칙어 관리 삭제
        js.executeScript("$('.uid_slang_word_list_remove_btn').last().click();");
        Thread.sleep(1000);
        assertTrue(driver.getPageSource().contains("정말 삭제 하시겠습니까?"));
        js.executeScript("$('.uid_cancel_btn').click();");
        js.executeScript("$('.uid_slang_word_list_remove_btn').last().click();");
        Thread.sleep(1000);
        js.executeScript("$('.uid_ok_btn').click();");
        Thread.sleep(1000);
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println("게임 관리 > 금칙어 관리 > 삭제 : Pass");
        brokenLinkCheck("엑셀 파일 받기 링크 확인", "https://rct-d-p.astorm.com/gmcmd/slangExcelDownload.ct");
        System.out.println("게임 관리 > 금칙어 관리 > 엑셀 파일 받기 링크 확인 : Pass");        
        //금지어 관리 테스트 후 추가       
        js.executeScript("$('.ac_container_tab_header > ul > li:eq(1) > a')[0].click();");
        refreshReady();
        js.executeScript("$('textarea[name=]:eq(0)').prop('value', '금지어 추가 정규 표현식 입력창 입니다.');");
        js.executeScript("$('input[name=]:eq(0)').prop('value', '금지어 추가 코멘트 입력창 입니다.');");
        js.executeScript("$('textarea[name=]:eq(1)').prop('value', '금지어 등록 테스트 메세지 입력창 입니다.');");
        js.executeScript("$('.uid_slang_word_add_test_btn').click();");
        assertTrue(driver.getPageSource().contains("일치하는 패턴이 존재하지 않습니다."));
        System.out.println("게임 관리 > 금지어 관리 > 등록 > 테스트 결과 : Pass");
        Thread.sleep(1000);
        js.executeScript("$('.uid_slang_word_add_btn').click();");
        Thread.sleep(1000);
        assertTrue(driver.getPageSource().contains("추가 하시겠습니까?"));
        js.executeScript("$('.uid_cancel_btn').click();");
        js.executeScript("$('.uid_slang_word_add_btn').click();");
        Thread.sleep(1000);
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println("게임 관리 > 금지어 관리 > 등록 : Pass");
        Thread.sleep(1000);
        js.executeScript("$('.uid_ok_btn').click();");
        //금칙어 관리 테스트 후 수정
        js.executeScript("$('.uid_slang_word_list_modify_btn').last().click();");
        js.executeScript("$('textarea[name=]:eq(2)').prop('value', '금칙어 수정 정규 표현식 입력창 입니다.');");
        js.executeScript("$('input[name=]:eq(3)').prop('value', '금칙어 수정 코멘트 입력창 입니다.');");
        js.executeScript("$('input[name=]:eq(4)').prop('value', '금지어 수정 테스트 메세지 입력창 입니다.');");
        js.executeScript("$('.uid_slang_word_modify_test_btn').click();");
        assertTrue(driver.getPageSource().contains("일치하는 패턴이 존재하지 않습니다."));
        System.out.println("게임 관리 > 금지어 관리 > 수정 > 테스트 결과 : Pass");
        Thread.sleep(1000);
        js.executeScript("$('.uid_slang_word_modify_btn').click();");
        Thread.sleep(1000);
        assertTrue(driver.getPageSource().contains("정말 수정하시겠습니까?"));
        js.executeScript("$('.uid_cancel_btn').click();");
        js.executeScript("$('.uid_slang_word_modify_btn').click();");
        Thread.sleep(1000);
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println("게임 관리 > 금지어 관리 > 수정 : Pass");
        Thread.sleep(1000);
        js.executeScript("$('.uid_ok_btn').click();");
        //금칙어 관리 삭제
        js.executeScript("$('.uid_slang_word_list_remove_btn').last().click();");
        Thread.sleep(1000);
        assertTrue(driver.getPageSource().contains("정말 삭제 하시겠습니까?"));
        js.executeScript("$('.uid_cancel_btn').click();");
        js.executeScript("$('.uid_slang_word_list_remove_btn').last().click();");
        Thread.sleep(1000);
        js.executeScript("$('.uid_ok_btn').click();");
        Thread.sleep(1000);
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println("게임 관리 > 금지어 관리 > 삭제 : Pass");
        brokenLinkCheck("엑셀 파일 받기 링크 확인", "https://rct-d-p.astorm.com/gmcmd/slangExcelDownload.ct");
        System.out.println("게임 관리 > 금지어 관리 > 엑셀 파일 받기 링크 확인 : Pass");
    }
    
   //@Test
    public void gameMng_M_pvpSchedule() throws Exception {
        driver.get(baseUrl + "/event/pvpSchedule/settingList.ct");
        refreshReady();
        assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
        assertTrue(driver.getPageSource().contains("기본보상 횟수"));
        System.out.println("게임 관리 > PvP스케줄 리스트 > 페이지 접근 : Pass");
        js.executeScript("$('.uid_schedule_add_btn:eq(0)').click();");
        js.executeScript("$('input[name=]:eq(5)').prop('value', '07:00:00');");
        js.executeScript("$('input[name=]:eq(6)').prop('value', '08:00:00');");
        js.executeScript("$('input[name=]:eq(7)').prop('value', '1');");
        js.executeScript("$('input[name=]:eq(8)').prop('value', '500001');");
        js.executeScript("$('input[name=]:eq(9)').prop('value', '1');");
        js.executeScript("$('.uid_schedule_save_btn').click();");
        System.out.println("게임 관리 > PvP스케줄 리스트 > 등록 : Pass");
        refreshReady();
        js.executeScript("$('.uid_schedule_sync_btn').click();");
        Thread.sleep(1000);
        js.executeScript("$('.uid_ok_btn').click();");
        Thread.sleep(1000);
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println("게임 관리 > PvP스케줄 리스트 > 동기화 : Pass");
        refreshReady();
        js.executeScript("$('.uid_schedule_del_btn:Eq(1)').click();");
        js.executeScript("$('.uid_schedule_save_btn').click();");
        System.out.println("게임 관리 > PvP스케줄 리스트 > 삭제 : Pass");
        refreshReady();
        js.executeScript("$('.uid_schedule_sync_btn').click();");
        Thread.sleep(1000);
        js.executeScript("$('.uid_ok_btn').click();");
        Thread.sleep(1000);
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println("게임 관리 > PvP스케줄 리스트 > 삭제 후 동기화 : Pass");
        refreshReady();
        js.executeScript("$('.ac_container_tab_header > ul > li:eq(1) > a')[0].click();");
        refreshReady();
        System.out.println("게임 관리 > PvP스케줄 리스트 > 히스토리 리스트 : Pass");
        js.executeScript("$('.au_text_center > tr > td:eq(1) > a')[0].click();");
        refreshReady();
        assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
        System.out.println("게임 관리 > PvP스케줄 리스트 > 히스토리 상세보기 : Pass");
        js.executeScript("$('.uid_link_list_btn').click();");
        refreshReady();
    }
        
    //@Test
    public void authMng_A_authorityMenu() throws Exception {
        driver.get(baseUrl + "/authority/menugroup.ct");
        refreshReady();
        assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
        assertTrue(driver.getPageSource().contains("메뉴 관리"));
        System.out.println("권한 관리 > 메뉴 관리 > 리스트 페이지 접근 : Pass");
        //메뉴 관리 리스트 순서 변경 및 검색 확인
        js.executeScript("$('.uid_group_order_btn:eq(0) > button > div > div > div > span:eq(0)').click();");
        Thread.sleep(1000);
        js.executeScript("$('.uid_ok_btn').click();");
        refreshReady();
        js.executeScript("$('.uid_group_order_btn:eq(1) > button > div > div > div > span:eq(0)').click();");
        Thread.sleep(1000);
        js.executeScript("$('.uid_ok_btn').click();");
        refreshReady();
        System.out.println("권한 관리 > 메뉴 관리 > 순서 변경 : Pass");
        js.executeScript("$('.ac_input').prop('value', 'common')");
        js.executeScript("$('.uid_menu_search').click();");
        assertTrue(driver.getPageSource().contains("공통기능"));
        System.out.println("권한 관리 > 메뉴 관리 > 검색 : Pass");
        js.executeScript("$('.ac_input').prop('value', '')");
        js.executeScript("$('.uid_menu_search').click();");
        js.executeScript("$('#uid_menu_list_tbody > tr > td:eq(1) > a')[0].click();");
        refreshReady();
        assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
        System.out.println("권한 관리 > 메뉴 관리 > 상세보기 페이지 접근 : Pass");
        js.executeScript("$('.uid_show_layer_menu_add_btn').click();");
        js.executeScript("$('div > input').prop('value', 'Test Menu1');");
        js.executeScript("$('input[name=menuURI]').val('/test.ct');");
        js.executeScript("$('.uid_layer_menu_add_btn').click();");
        Thread.sleep(1000);
        assertTrue(driver.getPageSource().contains("숫자만 입력 가능 합니다."));
        js.executeScript("$('.uid_ok_btn').click();");
        js.executeScript("$('input[name=menuHide]').val('0');");
        js.executeScript("$('.uid_layer_menu_add_btn').click();");
        Thread.sleep(1000);
        assertTrue(driver.getPageSource().contains("숫자만 입력 가능 합니다."));
        js.executeScript("$('.uid_ok_btn').click();");
        js.executeScript("$('input[name=menuOrder]').val('1');");
        js.executeScript("$('.uid_layer_menu_add_btn').click();");
        Thread.sleep(1000);
        assertTrue(driver.getPageSource().contains("추가 되었습니다."));
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println("권한 관리 > 메뉴 관리 > 상세보기 페이지 > 메뉴 추가 : Pass");
        refreshReady();
        js.executeScript("$('.uid_menu_order_btn:eq(2)').click();");
        Thread.sleep(1000);
        assertTrue(driver.getPageSource().contains("변경 되었습니다."));
        js.executeScript("$('.uid_ok_btn').click();");
        refreshReady();
        js.executeScript("$('.uid_menu_order_btn:eq(3)').click();");
        Thread.sleep(1000);
        assertTrue(driver.getPageSource().contains("변경 되었습니다."));
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println("권한 관리 > 메뉴 관리 > 상세보기 페이지 > 메뉴 순서 변경 : Pass");
        refreshReady();
        js.executeScript("$('.uid_menu_del_btn:eq(1)').click();");
        Thread.sleep(1000);
        assertTrue(driver.getPageSource().contains("이점 유의 해 주세요."));
        js.executeScript("$('.uid_ok_btn').click();");
        Thread.sleep(1000);
        assertTrue(driver.getPageSource().contains("삭제 되었습니다."));
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println("권한 관리 > 메뉴 관리 > 상세보기 페이지 > 메뉴 삭제 : Pass");        
        refreshReady();
    }
    
    //@Test
    public void authMng_B_authorityGroupMng() throws Exception {
        driver.get(baseUrl + "/authority/authGroupList.ct");
        refreshReady();
        assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
        assertTrue(driver.getPageSource().contains("권한 그룹 관리"));
        System.out.println("권한 관리 > 권한 그룹 관리 > 리스트 페이지 > 페이지 접근 : Pass");
        //권한 관리 > 권한 그룹 상세 정보로 이동
        js.executeScript("$('tbody > tr:last-child > td:eq(1) > a')[0].click();");
        refreshReady();
        System.out.println("권한 관리 > 권한 그룹 관리 > 상세보기 페이지 > 페이지 접근 : Pass");
        js.executeScript("$('.ac_toggle_btn:eq(0) > i')[0].click();");
        js.executeScript("$('.uid_authgroup_menu_add_btn').click();");
        Thread.sleep(1000);
        assertTrue(driver.getPageSource().contains("추가 되었습니다."));
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println("권한 관리 > 권한 그룹 관리 > 리스트 페이지 > 메뉴 권한 추가 : Pass");
        refreshReady();
        js.executeScript("$('.ac_toggle_btn:eq(1) > i')[0].click();");
        js.executeScript("$('.uid_authgroup_menu_delete_btn').click();");
        Thread.sleep(1000);
        assertTrue(driver.getPageSource().contains("삭제 되었습니다."));
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println("권한 관리 > 권한 그룹 관리 > 리스트 페이지 > 메뉴 권한 삭제 : Pass");
        refreshReady();
        js.executeScript("$('.ac_toggle_btn:eq(2) > i')[0].click();");
        js.executeScript("$('.uid_authgroup_member_add_btn').click();");
        Thread.sleep(1000);
        assertTrue(driver.getPageSource().contains("추가 되었습니다."));
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println("권한 관리 > 권한 그룹 관리 > 리스트 페이지 > 사용자 권한 추가 : Pass");
        refreshReady();
        js.executeScript("$('.ac_toggle_btn:eq(3) > i')[0].click();");
        js.executeScript("$('.uid_authgroup_member_delete_btn').click();");
        Thread.sleep(1000);
        assertTrue(driver.getPageSource().contains("해당 관리자의 권한을 삭제하시겠습니까?"));
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println("권한 관리 > 권한 그룹 관리 > 리스트 페이지 > 사용자 권한 삭제 : Pass");
        refreshReady();
    }
    
    //@Test 
    public void authMng_C_groupListByAdmin() throws Exception {
    	driver.get(baseUrl + "/authority/groupListByAdmin.ct");
        refreshReady();
        assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
        assertTrue(driver.getPageSource().contains("LDAP 사용자를 선택하세요."));
        System.out.println("권한 관리 > 관리자 권한 보기 > 페이지 접근 : Pass");
        js.executeScript("$('.fa-chevron-down').click();");
        js.executeScript("$('.normal > li:eq(3)').click();");
        refreshReady();
        assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
        assertTrue(driver.getPageSource().contains("테스트 계정 전용 모든 권한 그룹"));
        System.out.println("권한 관리 > 관리자 권한 보기 : INTERNAL 계정 권한보기 Pass");
        js.executeScript("$('.ac_container_tab_header > ul > li:eq(1) > a')[0].click();");
        refreshReady();
        assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
        assertTrue(driver.getPageSource().contains("LDAP 사용자를 선택하세요."));
        System.out.println("권한 관리 > 관리자 권한 보기 > LDAP탭 접근 : Pass");
        js.executeScript("$('.fa-chevron-down').click();");
        js.executeScript("$('.normal > li:eq(6)').click();");
        refreshReady();
        assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
        assertTrue(driver.getPageSource().contains("중복권한 페이지 접근불가 확인용"));
        System.out.println("권한 관리 > 관리자 권한 보기 > LDAP 계정 권한보기 : Pass");
    }
    
    //@Test 
    public void authMng_D_ctActionLog() throws Exception {
        driver.get(baseUrl + "/authority/ctActionLogList.ct");
        refreshReady();
        assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
        assertTrue(driver.getPageSource().contains("검색결과가 없습니다."));
        System.out.println("권한 관리 > 액션 로그 조회 > 페이지 접근 : Pass");
        if (TestBrowser.equals("chrome")) {
        	js.executeScript("$('.ac_btn_text').eq(6).click();");
            Thread.sleep(2000);
            assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
            System.out.println("권한 관리 > 액션 로그 조회 > 검색 : Pass");
            js.executeScript("$('.uid_ctactionlog_parameter:eq(0)').click();");
            handleMultipleWindows("Control Tower @ Cockpit");
            js.executeScript("$('.uid_confirm').click();");
            System.out.println("권한 관리 > 액션 로그 조회 > Parameter 보기 : Pass");
            handleMultipleWindows("Control Tower @ reboot");
            js.executeScript("$('.uid_ctactionlog_result').click();");
            handleMultipleWindows("Control Tower @ Cockpit");
            js.executeScript("$('.uid_confirm').click();");
            System.out.println("권한 관리 > 액션 로그 조회 > Result 보기 : Pass");
            handleMultipleWindows("Control Tower @ reboot");
        } else {
        	
        }        
    }
    
    //@Test 
    public void authMng_E_ctMember() throws Exception {
        driver.get(baseUrl + "/authority/ctMember/list.ct");
        refreshReady();
        assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
        assertTrue(driver.getPageSource().contains("CT 사용자 리스트"));
        System.out.println("권한 관리 > CT 사용자 관리 > 페이지 접근 : Pass");
        js.executeScript("$('.uid_ctmember_add_btn').click();");
        Thread.sleep(1000);
        js.executeScript("$('input[name=id]').val('test유저');");
        js.executeScript("$('input[name=name]').val('이름입니다.');");
        js.executeScript("$('input[name=department]').val('부서입니다.');");
        js.executeScript("$('input[name=tel]').val('010-0000-0000');");
        js.executeScript("$('input[name=email]').val('mail@astorm.co.kr');");
        js.executeScript("$('input[name=password]').val('password');");
        js.executeScript("$('input[name=passwordRe]').val('password');");
        js.executeScript("$('.uid_ctmember_save_btn').click();");
        assertTrue(driver.getPageSource().contains("저장 하시겠습니까?"));
        js.executeScript("$('.uid_ok_btn').click();");
        Thread.sleep(1000);
        assertTrue(driver.getPageSource().contains("저장되었습니다."));
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println("권한 관리 > CT 사용자 관리 > 사용자 추가 : Pass");
        Thread.sleep(1000);
        js.executeScript("$('.uid_ctmember_edit_btn:eq(0)').click();");
        Thread.sleep(1000);
        js.executeScript("$('input[name=name]').val('***** 이름 - 수정 *****');");
        js.executeScript("$('input[name=department]').val('***** 부서 - 수정 *****');");
        js.executeScript("$('input[name=tel]').val('***** 010-9999-9999 *****');");
        js.executeScript("$('input[name=email]').val('***** 메일수정@astorm.co.kr *****');");
        js.executeScript("$('.uid_ctmember_save_btn').click();");
        Thread.sleep(1000);
        assertTrue(driver.getPageSource().contains("저장 하시겠습니까?"));
        js.executeScript("$('.uid_ok_btn').click();");
        Thread.sleep(1000);
        assertTrue(driver.getPageSource().contains("저장되었습니다."));
        js.executeScript("$('.uid_ok_btn').click();");
        Thread.sleep(1000);
        System.out.println("권한 관리 > CT 사용자 관리 > 사용자 수정 : Pass");
        //비밀번호 초기화
        js.executeScript("$('.uid_ctmember_initpassword_btn:eq(0)').click();");
        Thread.sleep(1000);
        assertTrue(driver.getPageSource().contains("정말 비밀번호를 초기화"));
        js.executeScript("$('.uid_ok_btn').click();");
        Thread.sleep(1000);
        assertTrue(driver.getPageSource().contains("비밀번호가 초기화되었습니다."));
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println("권한 관리 > CT 사용자 관리 > 비밀번호 초기화 : Pass");
        Thread.sleep(1000);
        js.executeScript("$('.uid_ctmember_delete_btn:eq(0)').click();");
        Thread.sleep(1000);
        assertTrue(driver.getPageSource().contains("정말 삭제 하시겠습니까?"));
        js.executeScript("$('.uid_ok_btn').click();");
        Thread.sleep(1000);
        assertTrue(driver.getPageSource().contains("삭제되었습니다."));
        js.executeScript("$('.uid_ok_btn').click();");
        System.out.println("권한 관리 > CT 사용자 관리 > 사용자 삭제 : Pass");
        Thread.sleep(1000);
    }
    
    @Test 
    public void gameAccountMng_A_accountSearch() throws Exception {
    	driver.get(baseUrl + "/account/gamedata/searchform.ct");
        refreshReady();
    	assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
    	assertTrue(driver.getPageSource().contains("계정 검색"));
    	System.out.println("게임 계정 관리 > 계정 검색 > 페이지 접근 : Pass");
    	js.executeScript("$('.fa-circle-o:eq(0)').click();");
    	js.executeScript("$('textarea[name=searchKeyword]').val('영권');");
    	js.executeScript("$('#uid_account_search_form_search_btn').click();");
    	System.out.println("게임 계정 관리 > 계정 검색 > 검색 : Pass");
    	refreshReady();
    	if(TestBrowser.equals("chrome")){
        	js.executeScript("$('.ac_notice > tr:eq(2) > td:eq(12) > div').click();");
        	refreshReady();
        	handleMultipleWindows("Control Tower @ Cockpit");
        	js.executeScript("$('input[name=]').prop('value', '9000115');");
        	js.executeScript("$('.au_text_center:eq(0)').click();");
        	Thread.sleep(1000);
        	assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
        	//assertFalse(driver.getPageSource().contains("데이터가 없습니다."));
        	assertTrue(driver.getPageSource().contains("9000115"));
        	System.out.println("게임 계정 관리 > 계정 검색 > 친구목록 팝업 확인 : Pass");
        	refreshReady();
        	driver.close();
        	handleMultipleWindows("Control Tower @ reboot");
    	}
    	js.executeScript("$('.uid_game_account_init:eq(1)').click();");
    	Thread.sleep(1000);
        assertTrue(driver.getPageSource().contains("정말 초기화 하시겠습니까?"));
        js.executeScript("$('.uid_ok_btn').click();");
        Thread.sleep(1000);
        acceptAlert("계정 초기화 완료 메세지");
        /* 수정 전까지 alert으로 대체
        assertTrue(driver.getPageSource().contains("초기화 되었습니다."));
        js.executeScript("$('.uid_ok_btn').click();");
        */
    	refreshReady();
    	js.executeScript("$('.fa-circle-o:eq(0)').click();");
    	js.executeScript("$('textarea[name=searchKeyword]').val('영권');");
    	js.executeScript("$('#uid_account_search_form_search_btn').click();");
    	Thread.sleep(1000);
    	js.executeScript("$('.ac_notice > tr:eq(2) > td:eq(1) > a')[0].click();");
    	System.out.println("게임 계정 관리 > 계정 검색 : Pass");
    }
    
    @Test 
    public void gameAccountMng_B_recovery() throws Exception {
        driver.get(baseUrl + "/account/recovery.ct");
    	refreshReady();
        assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
        assertTrue(driver.getPageSource().contains("페니도 별도 복구 가능합니다."));
        System.out.println("게임 계정 관리 > 계정 복구 > 페이지 접근 : Pass");
    	
        //
    }
    
    //@Test 
    public void gameAccountMng_B_uuidTracking() throws Exception {
    	//게임 계정 관리 > uuid추적 으로 이동
        driver.get(baseUrl + "/historylog/uuidTracking.ct");
        Thread.sleep(1000);
        //해당 메세지가 있으면 Fail
        assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
        //
    }
    
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
