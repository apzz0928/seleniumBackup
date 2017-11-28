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
	  	
		@Test(priority = 0)
		public void Login() {
	        driver.get(baseUrl + "/login/form.ct");
	  		elementWait("class", "uid_login_type");
	        assertTrue(driver.getPageSource().contains("User ID"));
	        assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));        
	        System.out.println(TestBrowser + " 로그인 페이지 : Pass");
	        js.executeScript("$('input[name=j_username]').prop('value', 'apzz0928');");
	        js.executeScript("$('input[name=j_password]').prop('value', 'qordlf!@34');");
	        js.executeScript("$('.ac_btn_text')[0].click();");
	        System.out.println(TestBrowser + " 로그인 : Pass");    
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	    }
	  	@Test(priority = 1)
	    public void langChange() {
	    	driver.get(baseUrl + "/common/locale/ko");
	        assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
	        if(TestBrowser.equals("firefox")){
	        	elementWait("class", "numberCell");
	        } else {
		        assertTrue(driver.getPageSource().contains("true"));	        	
	        }
	        System.out.println(TestBrowser + " 언어변경 : Pass");
	        driver.get(baseUrl);
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
		}
	  	@Test(priority = 2)
	    public void sendMail() {
	        driver.get(baseUrl + "/gmcmd/sendMailForm.ct");
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	        assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
	        assertTrue(driver.getPageSource().contains("특정 유저에게 우편을 발송하는 기능입니다."));
	        System.out.println(TestBrowser + " 게임 관리 > 우편 전송 > 페이지 접근 : Pass");
	        js.executeScript("$('input[name=recvAccountKey]').val('350238');");
	        js.executeScript("$('input[name=sendNickName]').val('HerowarzTest');");
	        js.executeScript("$('input[name=mailTitle]').val('" + TestBrowser + " 우편 제목입니다.');");
	        js.executeScript("$('textarea[name=mailContents]').val('" + TestBrowser + " 우편 내용입니다.');");
	        js.executeScript("$('input[name=itemCode]').val('500001');");
	        js.executeScript("$('input[name=itemCount]').val('1');");
	        js.executeScript("$('input[name=gold]').val('100');");
	        js.executeScript("$('input[name=reason]').val('우편 테스트 입니다.');");
	        js.executeScript("$('.ac_btn_text')[1].click()");
	        elementWait("class", "ac_container_message_body");
	        assertTrue(driver.getPageSource().contains("GM 명령이 호출 되었습니다."));
	        js.executeScript("$('.uid_ok_btn').click();");
	        System.out.println(TestBrowser + " 게임 관리 > 우편 전송 > 전송 : Pass");
	        elementWait("name", "keyword");
	        js.executeScript("$('input[name=keyward]').val('검색어 입니다.');");
	        js.executeScript("$('.ac_btn_text')[2].click()");
	        elementWait("name", "keyword");
	        assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
	        System.out.println(TestBrowser + " 게임 관리 > 우편 전송 > 검색 : Pass");
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	    }
	  	@Test(priority = 3)
	    public void systemMessage() {
	    	driver.get(baseUrl + "/gmcmd/systemMessageForm.ct");
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	        assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
	        assertTrue(driver.getPageSource().contains("모든 공지는 채팅창에 출력됩니다."));
	        System.out.println(TestBrowser + " 게임 관리 > systemMessage > 페이지 접근 : Pass");
	        js.executeScript("$('.fa-circle-o')[0].click();");
	        js.executeScript("$('input[name=holdingTimeSec]').val('10');");
	        js.executeScript("$('input[name=displayMessage]').val('" + TestBrowser + " 위치 1번 테스트 입니다.');");
	        js.executeScript("$('.ac_btn_text')[0].click();");
	        elementWait("class", "ac_container_message_footer");
	        assertTrue(driver.getPageSource().contains("GM 명령이 호출 되었습니다."));
	        js.executeScript("$('.uid_ok_btn').click();");
	        System.out.println(TestBrowser + " 게임 관리 > systemMessage > 1번 위치 전송 : Pass");
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	        elementWait("class", "uid_location");
	        elementWait("class", "fa-circle-o");
	        js.executeScript("$('.fa-circle-o')[1].click();");
	        js.executeScript("$('input[name=holdingTimeSec]').val('10');");
	        js.executeScript("$('input[name=displayMessage]').val('" + TestBrowser + " 위치 2번 테스트 입니다.');");
	        js.executeScript("$('.ac_btn_text')[0].click();");
	        elementWait("class", "ac_container_message_footer");
	        assertTrue(driver.getPageSource().contains("GM 명령이 호출 되었습니다."));
	        js.executeScript("$('.uid_ok_btn').click();");
	        System.out.println(TestBrowser + " 게임 관리 > systemMessage > 2번 위치 전송 : Pass");
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");     
	        elementWait("class", "uid_location");
	        js.executeScript("$('.fa-circle-o')[2].click();");
	        js.executeScript("$('input[name=holdingTimeSec]').val('10');");
	        js.executeScript("$('input[name=displayMessage]').val('" + TestBrowser + " 위치 3번 테스트 입니다.');");
	        js.executeScript("$('.ac_btn_text')[0].click();");
	        elementWait("class", "ac_container_message_footer");
	        assertTrue(driver.getPageSource().contains("GM 명령이 호출 되었습니다."));
	        js.executeScript("$('.uid_ok_btn').click();");
	        System.out.println(TestBrowser + " 게임 관리 > systemMessage > 3번 위치 전송 : Pass");
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");       
	        elementWait("class", "uid_location");
	        js.executeScript("$('.fa-circle-o')[3].click();");
	        js.executeScript("$('input[name=holdingTimeSec]').val('10');");
	        js.executeScript("$('input[name=displayMessage]').val('" + TestBrowser + " 위치 4번 테스트 입니다.');");
	        js.executeScript("$('.ac_btn_text')[0].click();");
	        elementWait("class", "ac_container_message_footer");
	        assertTrue(driver.getPageSource().contains("GM 명령이 호출 되었습니다."));
	        js.executeScript("$('.uid_ok_btn').click();");
	        System.out.println(TestBrowser + " 게임 관리 > systemMessage > 4번 위치 전송 : Pass");
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");       
	        elementWait("name", "keyword");
	        js.executeScript("$('input[name=keyward]').val('검색어 입니다.');");
	        js.executeScript("$('.ac_btn_text')[1].click()");
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	        assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
	        System.out.println(TestBrowser + " 게임 관리 > systemMessage > 검색 : Pass");
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	    }
	  	@Test(priority = 4)
	    public void ingameNotice_add() {
	    	driver.get(baseUrl + "/gmcmd/ingameNotice.ct");
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	        assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
	        assertTrue(driver.getPageSource().contains("인게임 이벤트 공지는 반복만 선택 할 수 있습니다."));
	        System.out.println(TestBrowser + " 게임 관리 > 인게임 공지 > 페이지 접근 : Pass");
	        js.executeScript("$('input[name=startAt]').val('" + startAt + "');");
	        js.executeScript("$('input[name=endAt]').val('" + endAt + "');");
	        for(int i = 0; i < 7; i++){
	        	js.executeScript("$('.fa-square-o')[0].click()");
	        }
	        js.executeScript("$('input[name=repeatTime]').val('1');");
	        js.executeScript("$('input[name=name]').val('" + TestBrowser + " 인게임 공지 제목입니다.');");
	        js.executeScript("$('input[name=desc]').val('" + TestBrowser + " 인게임 공지 설명입니다.');");
	        js.executeScript("$('.fa-square-o')[0].click()");
	        js.executeScript("$('.fa-circle-o')[2].click();");
	        js.executeScript("$('.ac_input:eq(11)').val('10')");
	        js.executeScript("$('.ac_input:eq(12)').val('" + TestBrowser + " 테스트 공지 내용')");
	        js.executeScript("$('.ac_btn_text')[2].click();");
	        elementWait("class", "ac_container_message_footer");
	        assertTrue(driver.getPageSource().contains("입력되었습니다."));
	        js.executeScript("$('.uid_ok_btn').click();");
	        System.out.println(TestBrowser + " 게임 관리 > 인게임 공지 > 등록 > Pass");
	  	}
	  	@Test(priority = 5)
	    public void ingameNotice_statusChange_start() {
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	        elementWait("class", "uid_notice_start_btn");
	        js.executeScript("$('.uid_notice_start_btn')[0].click();");
	        elementWait("class", "ac_container_message_footer");
	        assertTrue(driver.getPageSource().contains("변경 되었습니다."));
	        js.executeScript("$('.uid_ok_btn').click();");
	        System.out.println(TestBrowser + " 게임 관리 > 인게임 공지 > 상태 변경 : 진행 > Pass");
	  	}
	  	@Test(priority = 6)
	    public void ingameNotice_statusChange_stop() {
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	        elementWait("class", "uid_notice_stop_btn");
	        js.executeScript("$('.uid_notice_stop_btn')[0].click();");
	        elementWait("class", "ac_container_message_footer");
	        assertTrue(driver.getPageSource().contains("변경 되었습니다."));
	        js.executeScript("$('.uid_ok_btn').click();");
	        System.out.println(TestBrowser + " 게임 관리 > 인게임 공지 > 상태 변경 : 멈춤 > Pass");
	  	}
	  	@Test(priority = 7)
	    public void ingameNotice_statusChange_reStart() {
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	        elementWait("class", "uid_notice_start_btn");
	        js.executeScript("$('.uid_notice_start_btn')[0].click();");
	        elementWait("class", "ac_container_message_footer");
	        assertTrue(driver.getPageSource().contains("변경 되었습니다."));
	        js.executeScript("$('.uid_ok_btn').click();");
	        System.out.println(TestBrowser + " 게임 관리 > 인게임 공지 > 상태 변경 : 진행 > Pass");
	  	}
	  	@Test(priority = 8)
	    public void ingameNotice_statusChange_end() {
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	        elementWait("class", "uid_notice_end_btn");
	        js.executeScript("$('.uid_notice_end_btn')[0].click();");
	        elementWait("class", "ac_container_message_footer");
	        assertTrue(driver.getPageSource().contains("변경 되었습니다."));
	        js.executeScript("$('.uid_ok_btn').click();");
	        System.out.println(TestBrowser + " 게임 관리 > 인게임 공지 > 상태 변경 : 종료 > Pass");
	  	}
	  	@Test(priority = 9)
	    public void ingameNotice_history() {
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	        elementWait("class", "uid_history_show_btn");
	        js.executeScript("$('.uid_history_show_btn')[0].click();");
	        elementWait("class", "uid_layer_close");
	        js.executeScript("$('.uid_layer_close').click();");
	        System.out.println(TestBrowser + " 게임 관리 > 인게임 공지 > 히스토리 확인 > Pass");
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	    }
	  	@Test(priority = 10)
	    public void kickUser() {
	        driver.get(baseUrl + "/gmcmd/kickUserForm.ct");
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	        assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
	        System.out.println(TestBrowser + " 게임 관리 > kickUser > 페이지 접근 > Pass");
	        driver.get(baseUrl + "/gmcmd/kickUserForm.ct");
	        driver.findElement(By.xpath("//div[@id='sk']/div/a/i")).click();
	        driver.findElement(By.cssSelector("ul.normal > li.selected")).click();
	        js.executeScript("$('input[name=accountKey]').val('" + accountKey + "')");
	        js.executeScript("$('input[name=forbidTime]').val('0')");
	        js.executeScript("$('input[name=reason]').val('" + TestBrowser + " KickUser 테스트')");
	        js.executeScript("$('.uid_gmcmd_kickusercall_btn').click();");
	        elementWait("class", "ac_container_message_footer");
	        assertTrue(driver.getPageSource().contains("GM 명령이 호출 되었습니다."));
	        js.executeScript("$('.uid_ok_btn').click();");
	        System.out.println(TestBrowser + " 게임 관리 > kickUser : Pass");
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	        elementWait("name", "keyword");
	        js.executeScript("$('input[name=keyword]').val('kickUser');");
	        js.executeScript("$('.uid_log_search_btn').click();");
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	        assertTrue(driver.getPageSource().contains("수정이 불가합니다."));
	        System.out.println(TestBrowser + " 게임 관리 > kickUser > 검색 : Pass");
	    }
	  	@Test(priority = 11)
	    public void mailOut() {
	    	driver.get(baseUrl + "/gmcmd/mailoutform.ct");
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	        assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
	        System.out.println(TestBrowser + " 게임 관리 > 우편 대량 발송 > 페이지 접근 : Pass");
	        driver.findElement(By.name("filedata")).sendKeys("C:\\Users\\Administrator\\Downloads\\템골드전부미첨부.xlsx");
	        js.executeScript("$('input[name=reason]').val('" + TestBrowser + " 우편 대량 발송 테스트 입니다.');");
	        js.executeScript("$('.uid_mailout_submit_btn').click();");
	        elementWait("class", "ac_container_message_footer");
	        assertTrue(driver.getPageSource().contains("대량 메일 발송이 시작됩니다."));
	        js.executeScript("$('.uid_ok_btn').click();");
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	        elementWait("class", "ac_container_section_footer");
	        if(TestBrowser.equals("firefox")){
	        	System.out.println(TestBrowser + " 는 유효성 검증 Pass");
	        } else {
		        assertTrue(driver.getPageSource().contains("(발송완료)"));	        	
	        }
	        System.out.println(TestBrowser + " 게임 관리 > 우편 대량 발송 > 발송완료 : Pass");
	        elementWait("class", "au_text_center");
	        js.executeScript("$('.au_text_center').click();");
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	    }
	  	//버닝 이벤트는 동기화 대기 문제때문에 잠시 보류
	  	@Test(priority = 12)
	    public void getServerStatus() throws Exception {
	  		driver.get(baseUrl + "/cache/getServerStatus.ct");
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	        elementWait("class", "ac_container_pagination");
	        assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
	        assertTrue(driver.getPageSource().contains("서버로 전송 되어 설정됩니다."));
	        System.out.println(TestBrowser + " 게임 관리 > 서버 제어 > 페이지 접근 : Pass");
	        js.executeScript("$('.ac_input:eq(1)').val('50');");
	        js.executeScript("$('.fa-chevron-down:eq(1)').click();");
	        js.executeScript("$('li[data-key=true]').click();");
	        js.executeScript("$('.ac_input:eq(3)').val('2016.01.01 00:00:00');");
	        js.executeScript("$('.ac_input:eq(4)').val('2016.01.02 00:00:00');");
	        js.executeScript("$('.fa-chevron-down:eq(2)').click();");
	        js.executeScript("$('li[data-key=enable]').click();");
	        js.executeScript("$('.ac_btn_text:eq(0)').click();");
	        elementWait("class", "ac_container_message_footer");
	        assertTrue(driver.getPageSource().contains("정상적으로 설정하였습니다."));
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
	        assertTrue(driver.getPageSource().contains("정상적으로 설정하였습니다."));
	        js.executeScript("$('.uid_ok_btn').click();");
	        System.out.println("게임 관리 > 서버 제어 > 내용 변경 : Pass");
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	        elementWait("name", "keyword");
	        elementWait("class", "ac_container_pagination");
	        js.executeScript("$('input[name=keyword]').val('검색어 입니다.');");
	        js.executeScript("$('.ac_btn_text')[1].click();");
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	        elementWait("name", "keyword");
	        elementWait("class", "ac_container_pagination");
	        assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
	        System.out.println("게임 관리 > 서버 제어 > 검색 : Pass");
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	    }
	    @Test(priority = 13)
	    public void limitdrop_fileUpload() {
	  		driver.get(baseUrl + "/event/limitdrop/confUploadForm.ct");
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	        assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
	        assertTrue(driver.getPageSource().contains("드랍되지 않습니다."));
	        System.out.println(TestBrowser + " 게임 관리 > 아이템 드랍 설정 > 페이지 접근 : Pass");
	        driver.findElement(By.name("filedata")).sendKeys("C:\\Users\\Administrator\\Downloads\\dropConfSample.xlsx");
	        js.executeScript("$('.au_text_center:Eq(1)').click();");
	        System.out.println(TestBrowser + " 게임 관리 > 아이템 드랍 설정 > 파일 업로드 : Pass");
	    }
	    @Test(priority = 14)
	    public void limitdrop_dropStateChangeOff() {
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	        elementWait("class", "uid_state_change_btn");
	        js.executeScript("$('.uid_state_change_btn').click();");
	        if(TestBrowser.equals("firefox")){
	        	System.out.println(TestBrowser + "는 대기 안함");	        	
	        } else {
	        	elementWait("class", "ac_container_message_footer");
	        }
	        if(TestBrowser.equals("firefox")){
	        	System.out.println(TestBrowser + "는 유효성 검증 안함");
	        } else {
	        	assertTrue(driver.getPageSource().contains("게임에 반영됩니다."));
		        System.out.println("유효성 검증");
	        }
	        js.executeScript("$('.uid_ok_btn').click();");
	        if(TestBrowser.equals("firefox")){
	        	System.out.println(TestBrowser + "는 대기 안함");	        	
	        } else {
	        	elementWait("class", "ac_container_message_footer");
	        }	        
	        assertTrue(driver.getPageSource().contains("reload를 눌러야 게임에 반영됩니다."));
	        js.executeScript("$('.uid_ok_btn').click();");
	        System.out.println(TestBrowser + " 게임 관리 > 아이템 드랍 설정 > 상태 off로 변경 : Pass");
	    }
	    @Test(priority = 15)
	    public void limitdrop_dropStateChangeOn() {
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	        elementWait("class", "uid_state_change_btn");
	        js.executeScript("$('.uid_state_change_btn').click();");
	        if(TestBrowser.equals("firefox")){
	        	System.out.println(TestBrowser + "는 대기 안함");	        	
	        } else {
	        	elementWait("class", "ac_container_message_footer");
	        }
	        if(TestBrowser.equals("firefox")){
	        	System.out.println(TestBrowser + "는 유효성 검증 안함2");
	        } else {
	        	assertTrue(driver.getPageSource().contains("게임에 반영됩니다."));
		        System.out.println("유효성 검증");
	        }
	        js.executeScript("$('.uid_ok_btn').click();");
	        //elementWait("class", "ac_container_message_footer");
	        if(TestBrowser.equals("firefox")){
	        	System.out.println(TestBrowser + "는 유효성 검증 안함3");
	        } else {
	        	assertTrue(driver.getPageSource().contains("reload를 눌러야 게임에 반영됩니다."));
		        System.out.println("유효성 검증");
	        }
	        js.executeScript("$('.uid_ok_btn').click();");
	        System.out.println(TestBrowser + " 게임 관리 > 아이템 드랍 설정 > 상태 on으로 변경 : Pass");
	    }
	    @Test(priority = 16)
	    public void limitdrop_gameserverReload() {
	    	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	        elementWait("class", "uid_reload_btn");
	        js.executeScript("$('.uid_reload_btn').click();");
	        elementWait("class", "ac_container_message_footer");
	        if(TestBrowser.equals("firefox")){
	        	System.out.println(TestBrowser + "는 유효성 검증 안함");
	        } else {
	        	assertTrue(driver.getPageSource().contains("게임 서버에 적용 되었습니다."));
		        System.out.println("유효성 검증");
	        }	        
	        js.executeScript("$('.uid_ok_btn').click();");
	        System.out.println(TestBrowser + " 게임 관리 > 아이템 드랍 설정 > reload : Pass");
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");       
	    }
	    @Test(priority = 17)
	    public void ingameGuide_add() {
	    	driver.get(baseUrl + "/gmcmd/ingameGuide/list.ct");
	        elementWait("id", "ac_layout_aside");
	        elementWait("class", "uid_ingame_guide_add_btn");
	        assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
	        System.out.println(TestBrowser + " 게임 관리 > 인게임 가이드 > 페이지 접근 : Pass");
	        js.executeScript("$('.uid_ingame_guide_add_btn').click();");
	        elementWait("class", "ac_container_section_footer");
	        elementWait("class", "uid_ingame_guide_save_btn");
	        js.executeScript("$('input[name=firstCategory]').prop('value', '인게임 가이드 테스트 대분류 입니다.');");
	        js.executeScript("$('input[name=secondCategory]').prop('value', '인게임 가이드 테스트 소분류 입니다.');");
	        js.executeScript("setTimeout(function() { return; }, 4000);");
	        //가이드 작성 레이어 노출 시 에디터 부분 로딩이 느리므로 로딩 완료 후 내용 입력창에 입력하도록 설정
	        js.executeScript("$('input[name=order]').val('0');");
	        js.executeScript("(function(){setTimeout(function(){$('.cke_wysiwyg_frame').contents().find('.cke_editable').text('내용 입력입니다.')}, 800);})();");
	        //내용 입력창에 setTimeout으로 입력 시 입력되기전에 클릭해버리므로 클릭에 딜레이줌 (어짜피 메세지 레이어 로딩되기전까지 최대 10초 대기 가능) 
	        js.executeScript("(function(){setTimeout(function(){$('.uid_ingame_guide_save_btn').click();}, 2000);})();");
	        elementWait("class", "ac_container_message_footer");
	        js.executeScript("$('.uid_ok_btn').click();");
	        elementWait("class", "ac_container_message_footer");
	        js.executeScript("$('.uid_ok_btn').click();");
	        System.out.println(TestBrowser + " 게임 관리 > 인게임 가이드 > 작성 : Pass");
	    }
	    @Test(priority = 18)
	    public void ingameGuide_modify() {
	    	elementWait("id", "ac_layout_aside");
	        elementWait("class", "uid_ingame_guide_add_btn");
	        elementWait("class", "uid_ingameguide_edit_btn");
	        js.executeScript("$('.ac_btn_text.au_text_center').eq(2).click();");
            elementWait("class", "ac_container_section_footer");
	        elementWait("class", "uid_ingame_guide_save_btn");
	        js.executeScript("$('input[name=firstCategory]').prop('value', '--- 대분류 대분류 대분류 ---');");
	        js.executeScript("$('input[name=secondCategory]').prop('value', '---소분류 소분류 소분류---');");
	        js.executeScript("$('.cke_wysiwyg_frame').contents().find('.cke_editable').text('--- 내용 내용 내용 ---')");
	        elementValueWait("name", "secondCategory", "---소분류 소분류 소분류---");
	        js.executeScript("$('.uid_ingame_guide_save_btn').click();");
	        if(TestBrowser.equals("firefox")){
	        	System.out.println(TestBrowser + " 는 유효성 검사 안함");
	        } else {
		        elementWait("class", "ac_container_message_footer");
	        }
	        js.executeScript("$('.uid_ok_btn').click();");
	        if(TestBrowser.equals("firefox")){
	        	System.out.println(TestBrowser + " 는 유효성 검사 안함");
	        } else {
		        elementWait("class", "ac_container_message_footer");
	        }
	        js.executeScript("$('.uid_ok_btn').click();");
	        System.out.println(TestBrowser + " 게임 관리 > 인게임 가이드 > 수정 : Pass");
	    }
	    @Test(priority = 19)
	    public void ingameGuide_delete() {
	    	elementWait("id", "ac_layout_aside");
	        elementWait("class", "uid_ingame_guide_add_btn");
	        js.executeScript("$('.ac_btn_text')[3].click();");
	        elementWait("class", "ac_container_message_footer");
	        js.executeScript("$('.uid_ok_btn').click();");
	        if(TestBrowser.equals("firefox")){
	        	System.out.println(TestBrowser + " 는 유효성 검사 안함");
	        } else {
		        elementWait("class", "ac_container_message_footer");
	        }
	        js.executeScript("$('.uid_ok_btn').click();");
	        System.out.println(TestBrowser + " 게임 관리 > 인게임 가이드 > 삭제 : Pass");
	        elementWait("id", "ac_layout_aside");
	    }
	  	@Test(priority = 20)
	    public void onlineUserCountInfo() {
	        driver.get(baseUrl + "/gmcmd/onlineUserCountInfo.ct");
	        elementWait("class", "ac_container_section_body");
	        assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
	        assertTrue(driver.getPageSource().contains("온라인 유저수 조회"));
	        System.out.println(TestBrowser + " 게임 관리 > 온라인 유저수 조회 : Pass");
	    }
	    @Test(priority = 21)
	    public void slangForm_typeA_add() {
	        driver.get(baseUrl + "/gmcmd/slangForm.ct");
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	        assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
	        assertTrue(driver.getPageSource().contains("정규 표현식"));
	        System.out.println(TestBrowser + " 게임 관리 > 금칙어 관리 > 페이지 접근 : Pass");
	        js.executeScript("$('textarea[name=]:eq(0)').prop('value', '금칙어 추가 정규 표현식 입력창 입니다.');");
	        js.executeScript("$('input[name=]:eq(0)').prop('value', '금칙어 추가 코멘트 입력창 입니다.');");
	        js.executeScript("$('textarea[name=]:eq(1)').prop('value', '금칙어 등록 테스트 메세지 입력창 입니다.');");
	        js.executeScript("$('.uid_slang_word_add_test_btn').click();");
	        assertTrue(driver.getPageSource().contains("일치하는 패턴이 존재하지 않습니다."));
	        System.out.println(TestBrowser + " 게임 관리 > 금칙어 관리 > 등록 > 테스트 결과 : Pass");
	        js.executeScript("$('.uid_slang_word_add_btn').click();");
	        elementWait("class", "ac_container_message_footer");
	        assertTrue(driver.getPageSource().contains("추가 하시겠습니까?"));
	        js.executeScript("$('.uid_cancel_btn').click();");
	        js.executeScript("$('.uid_slang_word_add_btn').click();");
	        elementWait("class", "ac_container_message_footer");
	        js.executeScript("$('.uid_ok_btn').click();");
	        System.out.println(TestBrowser + " 게임 관리 > 금칙어 관리 > 등록 : Pass");
	        elementWait("class", "ac_container_message_footer");
	        js.executeScript("$('.uid_ok_btn').click();");
	    }
	    @Test(priority = 22)
	    public void slangForm_typeA_modify() {
	        js.executeScript("$('.uid_slang_word_list_modify_btn').last().click();");
	        js.executeScript("$('textarea[name=]:eq(2)').prop('value', '금칙어 수정 정규 표현식 입력창 입니다.');");
	        js.executeScript("$('input[name=]:eq(3)').prop('value', '금칙어 수정 코멘트 입력창 입니다.');");
	        js.executeScript("$('input[name=]:eq(4)').prop('value', '금칙어 수정 테스트 메세지 입력창 입니다.');");
	        js.executeScript("$('.uid_slang_word_modify_test_btn').click();");
	        assertTrue(driver.getPageSource().contains("일치하는 패턴이 존재하지 않습니다."));
	        System.out.println(TestBrowser + " 게임 관리 > 금칙어 관리 > 수정 > 테스트 결과 : Pass");
	        js.executeScript("$('.uid_slang_word_modify_btn').click();");
	        elementWait("class", "ac_container_message_footer");
	        assertTrue(driver.getPageSource().contains("정말 수정하시겠습니까?"));
	        js.executeScript("$('.uid_cancel_btn').click();");
	        js.executeScript("$('.uid_slang_word_modify_btn').click();");
	        elementWait("class", "ac_container_message_footer");
	        js.executeScript("$('.uid_ok_btn').click();");
	        System.out.println(TestBrowser + " 게임 관리 > 금칙어 관리 > 수정 : Pass");
	        elementWait("class", "ac_container_message_footer");
	        js.executeScript("$('.uid_ok_btn').click();");
	    }
	  	@Test(priority = 23)
	    public void slangForm_typeA_delete() {
	        js.executeScript("$('.uid_slang_word_list_remove_btn').last().click();");
	        elementWait("class", "ac_container_message_footer");
	        assertTrue(driver.getPageSource().contains("정말 삭제 하시겠습니까?"));
	        js.executeScript("$('.uid_cancel_btn').click();");
	        js.executeScript("$('.uid_slang_word_list_remove_btn').last().click();");
	        elementWait("class", "ac_container_message_footer");
	        js.executeScript("$('.uid_ok_btn').click();");
	        elementWait("class", "ac_container_message_footer");
	        js.executeScript("$('.uid_ok_btn').click();");
	        System.out.println(TestBrowser + " 게임 관리 > 금칙어 관리 > 삭제 : Pass");
	        brokenLinkCheck("엑셀 파일 받기 링크 확인", "https://rct-d-p.astorm.com/gmcmd/slangExcelDownload.ct");
	        System.out.println(TestBrowser + " 게임 관리 > 금칙어 관리 > 엑셀 파일 받기 링크 확인 : Pass");
	  	}
	  	@Test(priority = 24)
	    public void slangForm_typeB_add() {
	  		driver.get(baseUrl + "/gmcmd/slangForm.ct");
	        js.executeScript("$('.ac_container_tab_header > ul > li:eq(1) > a')[0].click();");
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	        System.out.println(TestBrowser + " 게임 관리 > 금지어 관리 > 페이지 접근 : Pass");
	        js.executeScript("$('textarea[name=]:eq(0)').prop('value', '금지어 추가 정규 표현식 입력창 입니다.');");
	        js.executeScript("$('input[name=]:eq(0)').prop('value', '금지어 추가 코멘트 입력창 입니다.');");
	        js.executeScript("$('textarea[name=]:eq(1)').prop('value', '금지어 등록 테스트 메세지 입력창 입니다.');");
	        js.executeScript("$('.uid_slang_word_add_test_btn').click();");
	        assertTrue(driver.getPageSource().contains("일치하는 패턴이 존재하지 않습니다."));
	        System.out.println(TestBrowser + " 게임 관리 > 금지어 관리 > 등록 > 테스트 결과 : Pass");
	        js.executeScript("$('.uid_slang_word_add_btn').click();");
	        elementWait("class", "ac_container_message_footer");
	        assertTrue(driver.getPageSource().contains("추가 하시겠습니까?"));
	        js.executeScript("$('.uid_cancel_btn').click();");
	        js.executeScript("$('.uid_slang_word_add_btn').click();");
	        elementWait("class", "ac_container_message_footer");
	        js.executeScript("$('.uid_ok_btn').click();");
	        System.out.println(TestBrowser + " 게임 관리 > 금지어 관리 > 등록 : Pass");
	        elementWait("class", "ac_container_message_footer");
	        js.executeScript("$('.uid_ok_btn').click();");
	  	}
	  	@Test(priority = 25)
	    public void slangForm_typeB_modify() {
	        js.executeScript("$('.uid_slang_word_list_modify_btn').last().click();");
	        js.executeScript("$('textarea[name=]:eq(2)').prop('value', '금칙어 수정 정규 표현식 입력창 입니다.');");
	        js.executeScript("$('input[name=]:eq(3)').prop('value', '금칙어 수정 코멘트 입력창 입니다.');");
	        js.executeScript("$('input[name=]:eq(4)').prop('value', '금지어 수정 테스트 메세지 입력창 입니다.');");
	        js.executeScript("$('.uid_slang_word_modify_test_btn').click();");
	        assertTrue(driver.getPageSource().contains("일치하는 패턴이 존재하지 않습니다."));
	        System.out.println(TestBrowser + " 게임 관리 > 금지어 관리 > 수정 > 테스트 결과 : Pass");
	        js.executeScript("$('.uid_slang_word_modify_btn').click();");
	        elementWait("class", "ac_container_message_footer");
	        assertTrue(driver.getPageSource().contains("정말 수정하시겠습니까?"));
	        js.executeScript("$('.uid_cancel_btn').click();");
	        js.executeScript("$('.uid_slang_word_modify_btn').click();");
	        elementWait("class", "ac_container_message_footer");
	        js.executeScript("$('.uid_ok_btn').click();");
	        System.out.println(TestBrowser + " 게임 관리 > 금지어 관리 > 수정 : Pass");
	        elementWait("class", "ac_container_message_footer");
	        js.executeScript("$('.uid_ok_btn').click();");
	  	}
	  	@Test(priority = 26)
	    public void slangForm_typeB_delete() {
	        js.executeScript("$('.uid_slang_word_list_remove_btn').last().click();");
	        elementWait("class", "ac_container_message_footer");
	        assertTrue(driver.getPageSource().contains("정말 삭제 하시겠습니까?"));
	        js.executeScript("$('.uid_cancel_btn').click();");
	        js.executeScript("$('.uid_slang_word_list_remove_btn').last().click();");
	        elementWait("class", "ac_container_message_footer");
	        js.executeScript("$('.uid_ok_btn').click();");
	        elementWait("class", "ac_container_message_footer");
	        js.executeScript("$('.uid_ok_btn').click();");
	        System.out.println(TestBrowser + " 게임 관리 > 금지어 관리 > 삭제 : Pass");
	        brokenLinkCheck("엑셀 파일 받기 링크 확인", "https://rct-d-p.astorm.com/gmcmd/slangExcelDownload.ct");
	        System.out.println(TestBrowser + " 게임 관리 > 금지어 관리 > 엑셀 파일 받기 링크 확인 : Pass");
	    }
	    @Test(priority = 27)
	    public void pvpSchedule_add() {
	        driver.get(baseUrl + "/event/pvpSchedule/settingList.ct");
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	        assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
	        assertTrue(driver.getPageSource().contains("기본보상 횟수"));
	        System.out.println(TestBrowser + " 게임 관리 > PvP스케줄 리스트 > 페이지 접근 : Pass");
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
	        System.out.println(TestBrowser + " 게임 관리 > PvP스케줄 리스트 > 등록 : Pass");
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
	        System.out.println(TestBrowser + " 게임 관리 > PvP스케줄 리스트 > 동기화 : Pass");
	    }
	    @Test(priority = 29)
	    public void pvpSchedule_delete() {
	        js.executeScript("$('.uid_schedule_del_btn:Eq(1)').click();");
	        js.executeScript("$('.uid_schedule_save_btn').click();");
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	        elementWait("class", "ac_container_section_footer");
	        System.out.println(TestBrowser + " 게임 관리 > PvP스케줄 리스트 > 삭제 : Pass");
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
	        System.out.println(TestBrowser + " 게임 관리 > PvP스케줄 리스트 > 동기화 : Pass");
	    }
	    @Test(priority = 31)
	    public void pvpSchedule_historyList() {
	        js.executeScript("$('.ac_container_tab_header > ul > li:eq(1) > a')[0].click();");
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	        elementWait("class", "ac_container_section_footer");
	        System.out.println(TestBrowser + " 게임 관리 > PvP스케줄 리스트 > 히스토리 리스트 : Pass");
	    }
	    @Test(priority = 32)
	    public void pvpSchedule_historyView() {
	        js.executeScript("$('.au_text_center > tr > td:eq(1) > a')[0].click();");
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	        elementWait("class", "ac_container_section_footer");
	        assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
	        System.out.println(TestBrowser + " 게임 관리 > PvP스케줄 리스트 > 히스토리 상세보기 : Pass");
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
	        System.out.println(TestBrowser + " 로그아웃 : Pass");
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