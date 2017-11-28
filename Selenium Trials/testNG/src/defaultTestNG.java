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
import org.openqa.selenium.JavascriptExecutor; //js를 사용하기 위해 추가
import org.openqa.selenium.Alert; //alert 처리용
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
		baseUrl = "http://www.d.p.herowarz.com";
		liveUrl = "http://www.herowarz.com";
		devUrl = "http://www.d.p.herowarz.com";
		huc = null;
		respCode = 200;
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
	//alert 취소 처리
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
	//커뮤니티 글쓰기
	public static void boardWrite(String Category, String subCategory) throws Exception{
		if(subCategory.equals("")){
			new Select(driver.findElement(By.id("cmGroupList"))).selectByVisibleText(Category);
    		System.out.println (Category + " > 글쓰기 구분 선택 > Pass");
    	    js.executeScript("$('input[name=title]').prop('value', '질문이 있어요~')");
    		System.out.println (Category + " > 글쓰기 제목 입력 > Pass");
    	    //driver.findElement(By.cssSelector("button.se2_photo.ico_btn")).click();
    	    js.executeScript("$('.uid_smarteditor_area iframe').contents().find('iframe').contents().find('body').html('요즘 복귀 할만한가요!?!?');");
    		System.out.println (Category + " > 글쓰기 내용 입력 > Pass");
    		Thread.sleep(1000);
    	    js.executeScript("$('.uid_smarteditor_area iframe').contents().find('.se2_multy > li > button > span:eq(0)').click()");
    	    System.out.println (Category + " > 이미지 업로드 버튼 클릭 > Pass");
    	    handleMultipleWindows("이미지 업로드 :: SmartEditor2");
    		System.out.println (Category + " > 글쓰기 파일첨부 > 자식 윈도우로 포커스 변경 > Pass");
    	    driver.findElement(By.id("uploadInputBox")).sendKeys("C:\\Users\\Administrator\\Desktop\\black.jpg");
    	    driver.findElement(By.id("btn_confirm")).click();
    	    handleMultipleWindows(" :: 액션중독! - 최강의군단");
    		System.out.println (Category + " > 파일첨부 > 부모 윈도우로 포커스 변경 > Pass");
    		Thread.sleep(2000);
    	    js.executeScript("$('.btn-small')[1].click();");
		} else {
			new Select(driver.findElement(By.id("cmGroupList"))).selectByVisibleText(Category);
    		System.out.println ("커뮤니티 > " + Category + " > 글쓰기 구분 선택 > Pass");
    	    new Select(driver.findElement(By.id("cateDepth1"))).selectByVisibleText(subCategory);
    		System.out.println ("커뮤니티 > " + Category + " > 글쓰기 1차 카테고리 선택 > Pass");
    		js.executeScript("$('input[name=title]').prop('value', '질문이 있어요~')");
    		js.executeScript("$('.uid_smarteditor_area iframe').contents().find('iframe').contents().find('body').html('요즘 복귀 할만한가요!?!?');");
  		//js.executeScript("var org = new Array();org.push('html 내용');oEditors.getById['ir1'].exec('PASTE_HTML', org);");
  		js.executeScript("$('.btn-small')[1].click();");
		}      		
	}
	//운영서버 게시글 작성 및 확인
	public static void liveWrite(String Category, String subCategory) throws Exception{
		js.executeScript("$('.btn-small')[2].click();");
		Thread.sleep(1000);
		assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
		assertTrue(driver.getPageSource().contains(Category));
		System.out.println(Category + " > 글쓰기 페이지 : Pass");
		Thread.sleep(1000);
		boardWrite(Category, subCategory);
		Thread.sleep(1000);
		acceptAlert(Category + " > 게시글 등록 완료 메세지");
		Thread.sleep(1000);
	}
	//개발서버 게시글 작성 및 확인
	public static void devWrite(String Category, String subCategory) throws Exception{
		assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
		assertTrue(driver.getPageSource().contains(Category));
		System.out.println("커뮤니티 > " + Category + " > 글쓰기 페이지 : Pass");
		Thread.sleep(1000);
		boardWrite(Category, subCategory);
		Thread.sleep(1000);
		acceptAlert(Category + " > 게시글 등록 완료 메세지");
		Thread.sleep(1000);
	}
	//게시글 삭제
	public static void boardDelete() throws Exception{
		js.executeScript("$('.m-button > .g-left > a')[1].click();");
		acceptAlert("게시글 삭제 확인 메세지 : ");
		acceptAlert("게시글 삭제 완료 메세지 : ");
		Thread.sleep(1000);
	}
	//댓글 작성
	public static void commentWrite(String Category) throws Exception {
	  	js.executeScript("$('.uid_comment_writesave').click();");
	  	Thread.sleep(1000);
	  	acceptAlert(Category + " > 댓글 미입력 메세지");
	  	js.executeScript("$('textarea').prop('value', 'ㅎㅎㅎㅎ');");
	  	js.executeScript("$('.uid_comment_writesave').click();");
	  	System.out.println(Category + " > 댓글 작성 : Pass");
	  	Thread.sleep(1000);
	}
	//대댓글 작성
	public static void commentWrite2(String Category) throws Exception {
		Thread.sleep(1000);
	  	js.executeScript("$('.uid_comment_reply')[0].click();");
	  	Thread.sleep(1000);
	  	js.executeScript("$('textarea[name=content]').eq(1).prop('value', 'ㅎㅎㅎㅎ');");
	  	Thread.sleep(1000);
	  	js.executeScript("$('.uid_comment_writesave').eq(1).click();");
	  	System.out.println(Category +" > 대댓글 작성 : Pass");
	  	Thread.sleep(1000);
	}
	//댓글 삭제
	public static void delComment(String Category) throws Exception {
		js.executeScript("$('.uid_comment_delete')[0].click();");
		Thread.sleep(1000);
	  	dismissAlert("커뮤니티 > " + Category + " > 댓글삭제 confirm 취소");
	  	js.executeScript("$('.uid_comment_delete')[0].click();");
	  	Thread.sleep(1000);
	  	acceptAlert("커뮤니티 > " + Category + " > 댓글삭제 confirm 확인");
	  	System.out.println("커뮤니티 > "+ Category +" > 댓글 삭제 : Pass");
	  	Thread.sleep(1000);
	}
	//대댓글 삭제
	public static void delComment2(String Category) throws Exception {
		js.executeScript("$('.uid_comment_delete')[1].click();");
	  	Thread.sleep(1000);
	  	acceptAlert(Category + " > 댓글삭제 confirm 확인");
	  	System.out.println(Category +" > 댓글 삭제 : Pass");
	  	Thread.sleep(1000);
	}
	//최고군
	public static void like(String Category) throws Exception {
		Thread.sleep(1000);
	  	js.executeScript("$('.uid_comment_like')[0].click();");
	  	System.out.println(Category + " > 최고군 : Pass");
	}
	//사라져
	public static void blind (String Category) throws Exception {
		js.executeScript("$('.uid_comment_blind')[1].click();");
	  	js.executeScript("$('#blindType-3').click();");
	  	js.executeScript("$('.uid_blindcomment_confirm').click();");
	  	dismissAlert("사라져 confirm 취소");
	  	js.executeScript("$('.uid_blindcomment_confirm').click();");
	  	acceptAlert(Category + " > 사라져 confirm 확인");
	  	acceptAlert(Category + " > 사라져 완료");
	  	System.out.println("커뮤니티 > "+ Category +" > 사라져 : Pass");
	  	Thread.sleep(1000);
	}
	//미리어드 
	public static void liberationRanking(String serverName, int numberOfPeople) throws Exception {
		Thread.sleep(1000);
		assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
		assertTrue(driver.getPageSource().contains("시간을 기준으로 선정됩니다."));
		System.out.println("랭킹 > 해방랭킹 > " + serverName + " > " + numberOfPeople + "인 랭킹 리스트 : Pass");
		js.executeScript("$('input[name=keyword]').val('asdasd')");
		js.executeScript("$('.btn-search').click();");
		assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
		System.out.println("랭킹 > 해방랭킹 > " + serverName + " > " + numberOfPeople + "인 랭킹 리스트 검색 : Pass");
		Thread.sleep(1000);
	}	
	@Test(singleThreaded=true)
	public void A_Login() throws Exception {
		driver.get(baseUrl + "/index.main");
		Thread.sleep(1000);
		assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));  
		assertTrue(driver.getPageSource().contains("아이디 저장"));
		System.out.println("메인페이지 > 접근 : Pass");    	
		if(baseUrl.equals(liveUrl)){
			js.executeScript("$('.uid_login_id').val('apzz0928');");
		} else {
			js.executeScript("$('.uid_login_id').val('영권1240');");
		}
		js.executeScript("$('.uid_login_password').val('qordlf12');");
		Thread.sleep(1000);    	
		js.executeScript("$('.uid_login_login').eq(0).click();");
		Thread.sleep(1000);
		assertTrue(driver.getPageSource().contains("마이페이지"));
		System.out.println("메인페이지 > 로그인 : Pass");
		Thread.sleep(1000);
	}
	//@Test
	public void B_recent_a_notice() throws Exception {
		driver.get(baseUrl + "/index.main?c=n");
		js.executeScript("$('.gnb-list > .list-recent > .notice > a')[0].click();");
		Thread.sleep(1000);
		assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));  
		assertTrue(driver.getPageSource().contains("[공지]"));
		System.out.println("새소식 > 공지사항 > 리스트 : Pass");
		js.executeScript("$('.subject')[0].click();");
		Thread.sleep(1000);
		assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
		System.out.println("새소식 > 공지사항 > 상세보기 : Pass");
		Thread.sleep(1000);
	}
	//@Test
	public void B_recent_b_update() throws Exception {
		driver.get(baseUrl + "/index.main?c=n");
		js.executeScript("$('.gnb-list > .list-recent > .update > a')[0].click();");
		Thread.sleep(1000);
		assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));  
		assertTrue(driver.getPageSource().contains("업데이트 안내"));
		System.out.println("새소식 > 업데이트 > 리스트 : Pass");
		js.executeScript("$('.view-h-txt')[0].click();");
		Thread.sleep(1000);
		assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다.")); 
		System.out.println("새소식 > 업데이트 > 상세보기 : Pass");
		Thread.sleep(1000);
	}
	//@Test
	public void B_recent_c_avatarsale() throws Exception {
		driver.get(baseUrl + "/index.main?c=n");
		js.executeScript("$('.gnb-list > .list-recent > .event-store > a')[0].click();");
		Thread.sleep(1000);
		assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));  
		assertTrue(driver.getPageSource().contains("패키지 자세히 보기"));
		System.out.println("새소식 > 백화점 소식 > 패키지 자세히 보기 : Pass");
		js.executeScript("$('#uid_mtab_link > ul > .gift > a')[0].click();");
		Thread.sleep(1000);
		System.out.println("새소식 > 백화점 소식 > 사은품 안내 탭 : Pass");
		assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다.")); 
		js.executeScript("$('.btn > a')[0].click();");
		Thread.sleep(1000);
		assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
		js.executeScript("$('.btn-close')[2].click();");
		Thread.sleep(1000);
		System.out.println("새소식 > 백화점 소식 > 사은품 수령내역 확인 : Pass");
		switch (TestBrowser) {
			case "chrome":
				js.executeScript("$('.uid_avatarsale_disable_btn')[0].click();");
				Thread.sleep(1000);
		        acceptAlert("2개 사은품 수령 불가 메세지");
		        js.executeScript("$('.uid_avatarsale_disable_btn')[1].click();");
		        Thread.sleep(1000);
		        acceptAlert("3개 사은품 수령 불가 메세지");
		        js.executeScript("$('.uid_avatarsale_disable_btn')[2].click();");
		        Thread.sleep(1000);
		        acceptAlert("4개 사은품 수령 불가 메세지");
		        System.out.println("새소식 > 백화점 소식 > 사은품 안내 : Pass");
		        break;
			case "ie":
				System.out.println("IE는 사은품 수령 테스트 N/A");
				break;
			case "firefox":
				System.out.println("firefox는 사은품 수령 테스트 N/A");
				break;
		}
		Thread.sleep(1000);
	}
	//@Test
	public void B_recent_d_eventList() throws Exception {
	  	driver.get(baseUrl + "/index.main?c=n");
	  	js.executeScript("$('.gnb-list > .list-recent > .event > a')[0].click();");
	  	Thread.sleep(1000);
	    assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
	    assertTrue(driver.getPageSource().contains("진행중인 이벤트"));
	    System.out.println("새소식 > 이벤트 > 리스트 : Pass");
	    js.executeScript("$('.tab-block > a')[1].click();");
	    Thread.sleep(1000);
	    assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
	    assertTrue(driver.getPageSource().contains("행운의 주인공을 확인해보세요!"));
	    System.out.println("새소식 > 이벤트 > 당첨자보기 : Pass");
	    Thread.sleep(1000);        
	}
	//@Test
	public void B_recent_e_secretNote() throws Exception {
		driver.get(baseUrl + "/index.main?c=n");
		js.executeScript("$('.gnb-list > .list-recent > .secret > a')[0].click();");
		Thread.sleep(1000);
		assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
		assertTrue(driver.getPageSource().contains("편]"));
		System.out.println("새소식 > 시크릿노트 > 리스트 : Pass");
		js.executeScript("$('.board-gallery-list > ul > li > a')[0].click();");
		Thread.sleep(1000);
		assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
		assertTrue(driver.getPageSource().contains("조회"));
		System.out.println("새소식 > 시크릿노트 > 상세보기 : Pass");
		Thread.sleep(1000);        
	}
	//@Test
	public void B_recent_f_pinkDiary() throws Exception {
		driver.get(baseUrl + "/index.main?c=n");
		js.executeScript("$('.nav-left > .reset-ua > li > a')[5].click();");
		Thread.sleep(1000);
		assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
		assertTrue(driver.getPageSource().contains("조회"));
		System.out.println("새소식 > 핑크다이어리 > 상세보기 : Pass");
		driver.get(baseUrl + "/community/PinkDiary/list.hero");
		Thread.sleep(1000);
		assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
		assertTrue(driver.getPageSource().contains("검색"));
		System.out.println("새소식 > 시크릿노트 > 리스트 : Pass");
		Thread.sleep(1000);        
	}
	//@Test
	public void B_recent_g_costumePlay() throws Exception {
		driver.get(baseUrl + "/index.main?c=n");
		js.executeScript("$('.gnb-list > .list-recent > .cospre > a')[0].click();");
		Thread.sleep(1000);
		assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
		assertTrue(driver.getPageSource().contains("검색"));
		System.out.println("새소식 > 최강코스프레 > 리스트 : Pass");
		if(baseUrl.equals(liveUrl)){
			js.executeScript("$('.thumb > img')[4].click();");
			Thread.sleep(1000);
			assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
			assertTrue(driver.getPageSource().contains("바리공주"));
		} else {
			js.executeScript("$('.thumb > img')[0].click();");
			Thread.sleep(1000);
			assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
			assertTrue(driver.getPageSource().contains("ㅁㄴㅇㅁㅇ"));
		}
		System.out.println("새소식 > 최강코스프레 > 상세보기 : Pass");
		Thread.sleep(1000);        
	}
	//@Test
	public void B_recent_h_herowarzTv() throws Exception {
		js.executeScript("$('.nav-left > .reset-ua > li > a')[7].click();");
		Thread.sleep(1000);
		assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
		assertTrue(driver.getPageSource().contains("검색"));
		System.out.println("새소식 > 최군TV > 리스트 : Pass");
		js.executeScript("$('.row-notice > .cell-subject > a')[0].click();");
		Thread.sleep(1000);
		assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
		assertTrue(driver.getPageSource().contains("조회"));
		System.out.println("새소식 > 최군TV > 상세보기 : Pass");
		Thread.sleep(1000);        
	}
	//@Test
	public void C_about_a_herowarz() throws Exception {
		driver.get(baseUrl + "/about/herowarz.hero");
		Thread.sleep(1000);
		assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
		assertTrue(driver.getPageSource().contains("홈페이지 바로가기"));
		System.out.println("게임소개 > 게임소개 : Pass");
		Thread.sleep(1000);
	}
	//@Test
	public void C_about_b_character() throws Exception {
		js.executeScript("$('.inner > a')[0].click();");
		Thread.sleep(1000);
		assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
		assertTrue(driver.getPageSource().contains("COPYRIGHT ⓒ A.STORM. ALL RIGHTS RESERVED."));
		System.out.println("게임소개 > 캐릭터 소개  > 캐릭터 리스트 > RPG : Pass");
		js.executeScript("$('.item-rpg > a')[0].click();");
		Thread.sleep(1000);
		assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
		assertTrue(driver.getPageSource().contains("닉네임"));
		System.out.println("게임소개 > 캐릭터 소개  > 상세보기 > RPG : Pass");
		Thread.sleep(1000);
		js.executeScript("$('#gnb > ul > li > a')[1].click();");
		Thread.sleep(1000);
		js.executeScript("$('.index-tab > a')[1].click();");
		Thread.sleep(1000);
		assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
		assertTrue(driver.getPageSource().contains("COPYRIGHT ⓒ A.STORM. ALL RIGHTS RESERVED."));
		System.out.println("게임소개 > 캐릭터 소개  > 리스트 > AOS : Pass");
		js.executeScript("$('.item-aos > a')[0].click();");
		Thread.sleep(2500);
		assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
		assertTrue(driver.getPageSource().contains("대표스킬"));
		System.out.println("게임소개 > 캐릭터 소개  > 상세보기 > AOS : Pass");
		js.executeScript("$('#layerPvp > .btn-close').click()");
		Thread.sleep(1000);
		js.executeScript("$('.index-tab > a')[2].click();");
		Thread.sleep(2500);
		assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
		assertTrue(driver.getPageSource().contains("COPYRIGHT ⓒ A.STORM. ALL RIGHTS RESERVED."));
		System.out.println("게임소개 > 캐릭터 소개  > 리스트 > SIDEKICK : Pass");
		if(baseUrl.equals(liveUrl)){
			js.executeScript("$('.list > ul > .item-sidekick > a')[0].click();");
			Thread.sleep(2000);
			assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
			assertTrue(driver.getPageSource().contains("획득방법"));
			System.out.println("게임소개 > 캐릭터 소개  > 상세보기 > SIDEKICK : Pass");
			Thread.sleep(1000);
			js.executeScript("$('#layerSidekick > .btn-close').click()");
		}
		Thread.sleep(1000);
	}
	//@Test
	public void C_about_c_map() throws Exception {
		js.executeScript("$('#gnb > ul > li > a')[2].click();");
		Thread.sleep(1000);
		assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
		assertTrue(driver.getPageSource().contains("홈페이지 바로가기"));
		System.out.println("게임소개 > 여행자 지도 > 리스트 : Pass");
		Thread.sleep(1000);
		js.executeScript("$('map > area')[10].click();");
		Thread.sleep(1000);
		assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
		assertTrue(driver.getPageSource().contains("신이 다니는 어둠의 길"));
		js.executeScript("$('#content-third > button').click();");
		System.out.println("게임소개 > 여행자 지도 > 상세보기 : Pass");
	}
	//@Test
	public void C_about_d_chronicle() throws Exception {
		driver.get(baseUrl + "/index.main?c=n");
		js.executeScript("$('#gnb > ul > li > a')[3].click();");
		Thread.sleep(1000);
		assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
		assertTrue(driver.getPageSource().contains("COPYRIGHT ⓒ A.STORM. ALL RIGHTS RESERVED."));
		System.out.println("게임소개 > 군단 연대기 : Pass");
		Thread.sleep(1000);
	}  
	//@Test
	public void D_gameGuide_a_beginner() throws Exception {
		driver.get(baseUrl + "/index.main?c=n");
		Thread.sleep(1000);
		js.executeScript("$('.list-gameinfo > .beginner > a')[0].click();");
		Thread.sleep(1000);
		assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
		assertTrue(driver.getPageSource().contains("신규선수 튜토리얼"));
		System.out.println("게임정보 > 초보자 가이드 > 상세보기 : Pass");
		Thread.sleep(1000);
		js.executeScript("$('.view-util > a')[0].click();");
		Thread.sleep(1000);
		assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
		assertTrue(driver.getPageSource().contains("스토리"));
		System.out.println("게임정보 > 초보자 가이드 > 리스트 : Pass");
		js.executeScript("$('.list-gameinfo > .gameguide > a')[0].click();");
		Thread.sleep(1000);
		assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
		assertTrue(driver.getPageSource().contains("게임가이드 검색"));
		System.out.println("게임정보 > 게임 가이드/스토리 > 리스트 : Pass");
		Thread.sleep(1000);
	}  
	//@Test
	public void E_community_a_freeBoard() throws Exception {
		liveBoardName = "자유 게시판";
		devBoardName = "자유 게시판";
		driver.get(baseUrl + "/index.main?c=n");
    	js.executeScript("$('.list-community > .free > a')[0].click();");
    	Thread.sleep(1000);
    	assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
    	assertTrue(driver.getPageSource().contains("수다"));
    	System.out.println("커뮤니티 > 자유 게시판 > 리스트 : Pass");
    	Thread.sleep(1000);
    	if(baseUrl.equals(liveUrl)){//Live에서는 특정 게시글에만 댓글 작성
    		js.executeScript("$('select[name=type]').prop('value', '4')");
    		js.executeScript("$('input[name=keyword]').prop('value', '줍줍')");
    		js.executeScript("$('.btn-search').click();");
    		Thread.sleep(1000);
    		System.out.println("커뮤니티 > " + liveBoardName + " > 검색 : Pass");
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
	    	//게시판 글쓰기
	    	if(baseUrl.equals(liveUrl)){ 
	    		liveWrite(liveBoardName, "수다");
	    		Thread.sleep(1000);
	    		boardDelete();
	    	} else if (baseUrl.equals(devUrl)) {
	    		driver.get(baseUrl + "/community/write.hero?code=FreeBoard");
	    		Thread.sleep(1000);
	    		devWrite(devBoardName, "수다");
	    		boardDelete();
	    	}	
	        break;
		case "ie":
			System.out.println("IE는 게시글 작성 관련 테스트 N/A");
			break;
		case "firefox":
			System.out.println("firefox는 게시글 작성 관련 테스트 N/A");
			break;
    	}
    	Thread.sleep(1000);
	}
	//@Test
	public void E_community_b_strategy() throws Exception {
		liveBoardName = "공략 게시판";
		devBoardName = "공략 게시판";
		driver.get(baseUrl + "/index.main?c=n");
		js.executeScript("$('.list-community > .target > a')[0].click();");
		Thread.sleep(1000);
		assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
		assertTrue(driver.getPageSource().contains("PVE 공략"));
		System.out.println("커뮤니티 > 공략 게시판 > 리스트 : Pass");
		Thread.sleep(1000);
		Thread.sleep(1000);
		if(baseUrl.equals(liveUrl)){//Live에서는 특정 게시글에만 댓글 작성
			js.executeScript("$('select[name=type]').prop('value', '4')");
			js.executeScript("$('input[name=keyword]').prop('value', '나무가족')");
			js.executeScript("$('.btn-search').click();");
			Thread.sleep(1000);
			System.out.println("커뮤니티 > " + liveBoardName + " > 검색 : Pass");
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
	    	//게시판 글쓰기 및 삭제
	    	if(baseUrl.equals(liveUrl)){ 
	    		liveWrite(liveBoardName, "기타");
	    		Thread.sleep(1000);
	    		boardDelete();
	    	} else if (baseUrl.equals(devUrl)) {
	    		driver.get(baseUrl + "/community/write.hero?code=FreeBoard");
	    		Thread.sleep(1000);
	    		devWrite(devBoardName, "기타");
	    		Thread.sleep(1000);
	    		boardDelete();
	    	}	
	        break;
		case "ie":
			System.out.println("IE는 게시글 작성 관련 테스트 N/A");
			break;
		case "firefox":
			System.out.println("firefox는 게시글 작성 관련 테스트 N/A");
			break;
		}
	Thread.sleep(1000);
	}
	//@Test
	public void E_community_c_getItem() throws Exception {
		liveBoardName = "득템 게시판";
		devBoardName = "득템 게시판";
		driver.get(baseUrl + "/index.main?c=n");
		js.executeScript("$('.list-community > .pick > a')[0].click();");
		Thread.sleep(1000);
		assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
		assertTrue(driver.getPageSource().contains("득템 베스트"));
		System.out.println("커뮤니티 > 득템 게시판 > 리스트 : Pass");
		Thread.sleep(1000);
		Thread.sleep(1000);
		if(baseUrl.equals(liveUrl)){//Live에서는 특정 게시글에만 댓글 작성
			js.executeScript("$('select[name=type]').prop('value', '4');");
			js.executeScript("$('input[name=keyword]').prop('value', '이탕');");
			js.executeScript("$('.btn-search').click();");
			Thread.sleep(1000);
			System.out.println("커뮤니티 > " + liveBoardName + " > 검색 : Pass");
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
	    	//게시판 글쓰기 및 삭제
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
			System.out.println("IE는 게시글 작성 관련 테스트 N/A");
			break;
		case "firefox":
			System.out.println("firefox는 게시글 작성 관련 테스트 N/A");
			break;
		}
		Thread.sleep(1000);
	}	
	//@Test
	public void E_community_d_collection() throws Exception {
		liveBoardName = "스샷 게시판";
		devBoardName = "컬렉션 게시판";
		driver.get(baseUrl + "/index.main?c=n");
		js.executeScript("$('.list-community > .collection > a')[0].click();");
		Thread.sleep(1000);
		assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
		assertTrue(driver.getPageSource().contains("컬렉션 베스트"));
		System.out.println("커뮤니티 > 스샷 게시판 > 리스트 : Pass");
		Thread.sleep(1000);
		Thread.sleep(1000);
		if(baseUrl.equals(liveUrl)){//Live에서는 특정 게시글에만 댓글 작성
			js.executeScript("$('select[name=type]').prop('value', '4');");
			js.executeScript("$('input[name=keyword]').prop('value', '화형');");
			js.executeScript("$('.btn-search').click();");
			Thread.sleep(1000);
			System.out.println("커뮤니티 > " + liveBoardName + " > 검색 : Pass");
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
	    	//게시판 글쓰기 및 삭제
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
			System.out.println("IE는 게시글 작성 관련 테스트 N/A");
			break;
		case "firefox":
			System.out.println("firefox는 게시글 작성 관련 테스트 N/A");
			break;
		}
		Thread.sleep(1000);
	}
	//@Test
	public void E_community_e_fanArt() throws Exception {
		liveBoardName = "팬아트 게시판";
		devBoardName = "팬아트 게시판";
		js.executeScript("$('.list-community > .fan > a')[0].click();");
		Thread.sleep(1000);
		assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
		assertTrue(driver.getPageSource().contains("팬아트 베스트"));
		System.out.println("커뮤니티 > 팬아트 게시판 > 리스트 : Pass");
		Thread.sleep(1000);
		Thread.sleep(1000);
		if(baseUrl.equals(liveUrl)){//Live에서는 특정 게시글에만 댓글 작성
			js.executeScript("$('select[name=type]').prop('value', '4');");
			js.executeScript("$('input[name=keyword]').prop('value', '이탕');");
			js.executeScript("$('.btn-search').click();");
			Thread.sleep(1000);
			System.out.println("커뮤니티 > " + liveBoardName + " > 검색 : Pass");
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
	    	//게시판 글쓰기 및 삭제
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
			System.out.println("IE는 게시글 작성 관련 테스트 N/A");
			break;
		case "firefox":
			System.out.println("firefox는 게시글 작성 관련 테스트 N/A");
			break;
		}
		Thread.sleep(1000);
	}
	//@Test
	public void F_ranking_a_corps() throws Exception {
		js.executeScript("$('.list-ranking > .legion > a')[0].click();");
		Thread.sleep(1000);
		assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
		assertTrue(driver.getPageSource().contains("보유한 캐릭터의 경험치총합으로 순위가 결정됩니다."));
		System.out.println("랭킹 > 군단랭킹 > 미리어드 > 리스트 : Pass");
		Thread.sleep(1000);
		js.executeScript("$('input[name=keyword]').val('123123')");
		js.executeScript("$('.input-append > button').click();");
		Thread.sleep(1000);
		assertTrue(driver.getPageSource().contains("다시 한번 확인하시기 바랍니다"));
		System.out.println("랭킹 > 군단랭킹 > 미리어드 > 검색 : Pass");
		if(baseUrl.equals(liveUrl)){
			js.executeScript("$('.ranking-server-tab > ul > li > a')[1].click();");
			Thread.sleep(1000);
			assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
			assertTrue(driver.getPageSource().contains("보유한 캐릭터의 경험치총합으로 순위가 결정됩니다."));
			System.out.println("랭킹 > 군단랭킹 > 히페리온 > 리스트 : Pass");
			Thread.sleep(1000);
			js.executeScript("$('input[name=keyword]').val('123123')");
			js.executeScript("$('.input-append > button').click();");
			Thread.sleep(1000);
			assertTrue(driver.getPageSource().contains("다시 한번 확인하시기 바랍니다"));
			System.out.println("랭킹 > 군단랭킹 > 히페리온 > 검색 : Pass");
		}        	        	
	}
	//@Test
	public void F_ranking_b_character() throws Exception {
		js.executeScript("$('.list-ranking > .cha > a')[0].click();");
		Thread.sleep(1000);
		assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
		assertTrue(driver.getPageSource().contains("접속자 데이터로만 제공되고 있습니다."));
		System.out.println("랭킹 > 캐릭터랭킹 > 미리어드 > 리스트 : Pass");
		Thread.sleep(1000);
		js.executeScript("$('input[name=keyword]').val('123123')");
		js.executeScript("$('.input-append > button').click();");
		Thread.sleep(1000);
		assertTrue(driver.getPageSource().contains("다시 한번 확인하시기 바랍니다"));
		System.out.println("랭킹 > 군단랭킹 > 미리어드 > 검색 : Pass");
		if(baseUrl.equals(liveUrl)){
			js.executeScript("$('.ranking-server-tab > ul > li > a')[1].click();");
			Thread.sleep(1000);
			assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
			assertTrue(driver.getPageSource().contains("접속자 데이터로만 제공되고 있습니다."));
			System.out.println("랭킹 > 군단랭킹 > 히페리온 > 리스트 : Pass");
			Thread.sleep(1000);
			js.executeScript("$('input[name=keyword]').val('123123')");
			js.executeScript("$('.input-append > button').click();");
			Thread.sleep(1000);
			assertTrue(driver.getPageSource().contains("다시 한번 확인하시기 바랍니다"));
			System.out.println("랭킹 > 군단랭킹 > 히페리온 > 검색 : Pass");
		}        	        	
	}	
	//@Test
	public void F_ranking_c_mflCharacter() throws Exception {
		js.executeScript("$('.list-ranking > .mfl > a')[0].click();");
		Thread.sleep(1000);
		assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
		assertTrue(driver.getPageSource().contains("캐릭터의 플레이수로 순위가 결정됩니다."));
		System.out.println("랭킹 > mfl캐릭터랭킹 > 미리어드 > 리스트 : Pass");
		Thread.sleep(1000);
		js.executeScript("$('input[name=keyword]').val('123123')");
		js.executeScript("$('.input-append > button').click();");
		Thread.sleep(1000);
		assertTrue(driver.getPageSource().contains("캐릭터의 플레이수로 순위가 결정됩니다."));
		System.out.println("랭킹 > mfl캐릭터랭킹 > 미리어드 > 검색 : Pass");
		if(baseUrl.equals(liveUrl)){
			js.executeScript("$('.ranking-server-tab > ul > li > a')[1].click();");
			Thread.sleep(1000);
			assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
			assertTrue(driver.getPageSource().contains("캐릭터의 플레이수로 순위가 결정됩니다."));
			System.out.println("랭킹 > mfl캐릭터랭킹 > 히페리온 > 리스트 : Pass");
			Thread.sleep(1000);
			js.executeScript("$('input[name=keyword]').val('123123')");
			js.executeScript("$('.input-append > button').click();");
			Thread.sleep(1000);
			assertTrue(driver.getPageSource().contains("캐릭터의 플레이수로 순위가 결정됩니다."));
			System.out.println("랭킹 > mfl캐릭터랭킹 > 히페리온 > 검색 : Pass");
		}
	}
	//@Test
	public void F_ranking_d_mflCharacter() throws Exception {
		js.executeScript("$('.has-sub > ul > li > a')[1].click();");
		Thread.sleep(1000);
		assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
		assertTrue(driver.getPageSource().contains("RP를 기준으로 산정됩니다."));
		System.out.println("랭킹 > mfl통합랭킹 > 미리어드 > 리스트 : Pass");
		Thread.sleep(1000);
		js.executeScript("$('input[name=keyword]').val('123123')");
		js.executeScript("$('.input-append > button').click();");
		Thread.sleep(1000);
		assertTrue(driver.getPageSource().contains("내역이 없습니다."));
		System.out.println("랭킹 > mfl통합랭킹 > 미리어드 > 검색 : Pass");
		if(baseUrl.equals(liveUrl)){
			js.executeScript("$('.ranking-server-tab > ul > li > a')[1].click();");
			Thread.sleep(1000);
			assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
  	       assertTrue(driver.getPageSource().contains("RP를 기준으로 산정됩니다."));
  	       System.out.println("랭킹 > mfl통합랭킹 > 히페리온 > 리스트 : Pass");
  	       Thread.sleep(1000);
  	       js.executeScript("$('input[name=keyword]').val('123123')");
  	       js.executeScript("$('.input-append > button').click();");
  	       Thread.sleep(1000);
  	       assertTrue(driver.getPageSource().contains("내역이 없습니다."));
  	       System.out.println("랭킹 > mfl통합랭킹 > 히페리온 > 검색 : Pass");
		}
	}
	//@Test
	public void F_ranking_e_libaration() throws Exception {
		driver.get(baseUrl + "/index.main?c=n");
		js.executeScript("$('.list-ranking > .liberation > a')[0].click();");
		Thread.sleep(1000);
		js.executeScript("$('.num-list > a')[0].click();");
		liberationRanking("미리어드", 1);
		js.executeScript("$('.num-list > a')[1].click();");
		liberationRanking("미리어드", 2);
		js.executeScript("$('.num-list > a')[2].click();");
		liberationRanking("미리어드", 3);
		js.executeScript("$('.num-list > a')[3].click();");
		liberationRanking("미리어드", 4);
		if(baseUrl.equals(liveUrl)){
			js.executeScript("$('.ranking-server-tab > ul > li > a')[1].click();");
			Thread.sleep(1000);
			js.executeScript("$('.num-list > a')[0].click();");
			liberationRanking("히페리온", 1);
			js.executeScript("$('.num-list > a')[1].click();");
			liberationRanking("히페리온", 2);
			js.executeScript("$('.num-list > a')[2].click();");
			liberationRanking("히페리온", 3);
			js.executeScript("$('.num-list > a')[3].click();");
			liberationRanking("히페리온", 4);
		}
	}
	//@Test
	public void F_ranking_f_backdraft() throws Exception {
		driver.get(baseUrl + "/index.main?c=n");
		js.executeScript("$('.list-ranking > .backdraft > a')[0].click();");
		Thread.sleep(1000);
		assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
		assertTrue(driver.getPageSource().contains("역류를 클리어하면 랭킹에 기록됩니다."));
		System.out.println("랭킹 > 역류랭킹 > 미리어드 > 리스트 : Pass");
		Thread.sleep(1000);
		js.executeScript("$('input[name=keyword]').val('123123')");
		js.executeScript("$('.btn-search').click();");
		Thread.sleep(1000);
		assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
		System.out.println("랭킹 > 역류랭킹 > 미리어드 > 검색 : Pass");
		if(baseUrl.equals(liveUrl)){
			js.executeScript("$('.ranking-server-tab > ul > li > a')[1].click();");
			Thread.sleep(1000);
			assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
			assertTrue(driver.getPageSource().contains("역류를 클리어하면 랭킹에 기록됩니다."));
			System.out.println("랭킹 > 역류랭킹 > 히페리온 > 리스트 : Pass");
			Thread.sleep(1000);
			js.executeScript("$('input[name=keyword]').val('123123')");
			js.executeScript("$('.btn-search').click();");
			Thread.sleep(1000);
			assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
			System.out.println("랭킹 > 역류랭킹 > 히페리온 > 검색 : Pass");
		}
	}
	//@Test
	public void F_ranking_g_pantheon() throws Exception {
		driver.get(baseUrl + "/index.main?c=n");
		js.executeScript("$('.list-ranking > .pantheon-time > a')[0].click();");
		Thread.sleep(1000);
		assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
		assertTrue(driver.getPageSource().contains("시간을 기준으로 선정됩니다."));
		System.out.println("랭킹 > 만신전EX랭킹 > 미리어드 > 리스트 : Pass");
		Thread.sleep(1000);
		js.executeScript("$('input[name=keyword]').val('123123')");
		js.executeScript("$('.btn-search').click();");
		Thread.sleep(1000);
		assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
		System.out.println("랭킹 > 만신전EX랭킹 > 미리어드 > 검색 : Pass");
		if(baseUrl.equals(liveUrl)){
			js.executeScript("$('.ranking-server-tab > ul > li > a')[1].click();");
			Thread.sleep(1000);
			assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
			assertTrue(driver.getPageSource().contains("시간을 기준으로 선정됩니다."));
			System.out.println("랭킹 > 만신전EX랭킹 > 히페리온 > 리스트 : Pass");
			Thread.sleep(1000);
			js.executeScript("$('input[name=keyword]').val('123123')");
			js.executeScript("$('.btn-search').click();");
			Thread.sleep(1000);
			assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
			System.out.println("랭킹 > 만신전EX랭킹 > 히페리온 > 검색 : Pass");
		}
	}
	//@Test
	public void F_ranking_h_raid() throws Exception {
		driver.get(baseUrl + "/index.main?c=n");
		js.executeScript("$('.list-ranking > .raid > a')[0].click();");
		Thread.sleep(1000);
		assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
		assertTrue(driver.getPageSource().contains("역습을 클리어하면 랭킹에 기록됩니다."));
		System.out.println("랭킹 > 역습랭킹 > 미리어드 > 리스트 : Pass");
		Thread.sleep(1000);
		js.executeScript("$('input[name=keyword]').val('123123')");
		js.executeScript("$('.btn-search').click();");
		Thread.sleep(1000);
		assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
		System.out.println("랭킹 > 역습랭킹 > 미리어드 > 검색 : Pass");
		if(baseUrl.equals(liveUrl)){
			js.executeScript("$('.ranking-server-tab > ul > li > a')[1].click();");
			Thread.sleep(1000);
			assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
			assertTrue(driver.getPageSource().contains("역습을 클리어하면 랭킹에 기록됩니다."));
			System.out.println("랭킹 > 역습랭킹 > 히페리온 > 리스트 : Pass");
			Thread.sleep(1000);
			js.executeScript("$('input[name=keyword]').val('123123')");
			js.executeScript("$('.btn-search').click();");
			Thread.sleep(1000);
			assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
			System.out.println("랭킹 > 역습랭킹 > 히페리온 > 검색 : Pass");
		}
	}
	//@Test
	public void F_ranking_i_tryRaid() throws Exception {
		driver.get(baseUrl + "/index.main?c=n");
		js.executeScript("$('.list-ranking > .try-raid > a')[0].click();");
		Thread.sleep(1000);
		assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
		assertTrue(driver.getPageSource().contains("역습을 시도하면 랭킹에 기록됩니다."));
		System.out.println("랭킹 > 역습시도랭킹 > 미리어드 > 리스트 : Pass");
		Thread.sleep(1000);
		js.executeScript("$('input[name=keyword]').val('123123')");
		js.executeScript("$('.btn-search').click();");
		Thread.sleep(1000);
		assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
		System.out.println("랭킹 > 역습시도랭킹 > 미리어드 > 검색 : Pass");
		if(baseUrl.equals(liveUrl)){
			js.executeScript("$('.ranking-server-tab > ul > li > a')[1].click();");
			Thread.sleep(1000);
			assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
			assertTrue(driver.getPageSource().contains("역습을 시도하면 랭킹에 기록됩니다."));
			System.out.println("랭킹 > 역습시도랭킹 > 히페리온 > 리스트 : Pass");
			Thread.sleep(1000);
			js.executeScript("$('input[name=keyword]').val('123123')");
			js.executeScript("$('.btn-search').click();");
			Thread.sleep(1000);
			assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
			System.out.println("랭킹 > 역습시도랭킹 > 히페리온 > 검색 : Pass");
		}
	}
	//@Test
	public void G_download_a_download() throws Exception {
		driver.get(baseUrl + "/index.main?c=n");
		js.executeScript("$('.list-download > .download > a')[0].click();");
		Thread.sleep(1000);
		assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
		assertTrue(driver.getPageSource().contains("다운로드와 동시에 게임이 설치됩니다."));
		System.out.println("자료실 > 게임다운로드 > 페이지 접근 : Pass");
		Thread.sleep(1000);
		brokenLinkCheck("클라이언트 다운로드", "http://download.herowarz.com/live/MCSetup.exe");
		brokenLinkCheck("크롬실행기 다운로드", "http://download.herowarz.com/live/MCSetup_Chrome.exe");
		brokenLinkCheck("Nvidia 다운로드", "http://www.nvidia.co.kr/Download/index.aspx?lang=kr");
		brokenLinkCheck("ATI 다운로드", "http://support.amd.com/ko-kr/download");
		brokenLinkCheck("Intel 다운로드", "https://downloadcenter.intel.com/ko/");
		brokenLinkCheck("DirectX 다운로드", "https://www.microsoft.com/ko-kr/download/details.aspx?id=35");
		System.out.println("자료실 > 게임다운로드 > 다운로드 링크 확인 : Pass");
	}
	//@Test
	public void G_download_b_movie() throws Exception {
		liveBoardName = "자료실 > 동영상";
		devBoardName = "자료실 > 동영상";
		driver.get(baseUrl + "/index.main?c=n");
		js.executeScript("$('.list-download > .movie > a')[0].click();");
		Thread.sleep(1000);
		assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
		assertTrue(driver.getPageSource().contains("불이익을 받으실 수 있습니다."));
		if(baseUrl.equals(liveUrl)){
			System.out.println(liveBoardName + " > 다운로드 링크 확인 : Pass");
	     } else {
	    	 System.out.println(devBoardName + " > 다운로드 링크 확인 : Pass");
	     }
		Thread.sleep(1000);
		if(baseUrl.equals(liveUrl)){
			brokenLinkCheck("영상 다운로드", "http://static.herowarz.com/editor/upload/2015/39/201509220807372_1st%20anniversary%20thanks.zip");
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
		System.out.println("자료실 > 동영상 > 상세보기 페이지 댓글작성 : Pass");
		Thread.sleep(1000);
	}
	//@Test
	public void G_download_c_gallery() throws Exception {
		driver.get(baseUrl + "/index.main?c=n");
		js.executeScript("$('.list-download > .gallery > a')[0].click();");
		Thread.sleep(1000);
		assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
		assertTrue(driver.getPageSource().contains("2016. 09"));
		System.out.println("자료실 > 갤러리 > 리스트 : Pass");
		Thread.sleep(1000);
		js.executeScript("$('.gallery-item:eq(0) > img').click();");
		Thread.sleep(1000);
		assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
		assertTrue(driver.getPageSource().contains("목록"));
		if(baseUrl.equals(liveUrl)){
			brokenLinkCheck("원본 다운로드", "http://static.herowarz.com/editor/upload/2016/40/B-58c00bd8d5dc40158b348f9dd1ece35f.png");
			brokenLinkCheck("갤러리 썸네일", "http://static.herowarz.com/editor/upload/2016/40/B-fd22ea3d649d47c2a2b61a83b29844c0.png");
			System.out.println("자료실 > 갤러리 > 상세보기 : Pass");
			Thread.sleep(1000);
		} else {
			brokenLinkCheck("원본 다운로드", "http://static.d.p.herowarz.com/editor/upload/2016/40/B-d72478b7d0444cbdb1a973ac4062b884.png");
			brokenLinkCheck("갤러리 썸네일", "http://static.d.p.herowarz.com/editor/upload/2016/40/B-97b209d605f142588195f301cd69741e.png");
			System.out.println("자료실 > 갤러리 > 상세보기 : Pass");
			Thread.sleep(1000);
		}           
	}
	//@Test
	public void G_download_d_wallpaper() throws Exception {
		driver.get(baseUrl + "/index.main?c=n");
		js.executeScript("$('.list-download > .wallpaper > a')[0].click();");
		Thread.sleep(1000);
		assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
		if(baseUrl.equals(liveUrl)){
			assertTrue(driver.getPageSource().contains("2015. 08"));
		} else {
			assertTrue(driver.getPageSource().contains("2016. 01"));
		}
		System.out.println("자료실 > 월페이퍼 > 리스트 : Pass");
		Thread.sleep(1000);
		js.executeScript("$('.gallery-item:eq(0) > img').click();");
		Thread.sleep(1000);
 	    assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
 	    assertTrue(driver.getPageSource().contains("목록"));
 	    if(baseUrl.equals(liveUrl)){
 	    	brokenLinkCheck("1920x1080", "http://static.herowarz.com/editor/upload/2015/35/201508271418012_loading_ch_summerfive05.jpg");
 	    	brokenLinkCheck("1600x1200", "http://static.herowarz.com/editor/upload/2015/35/201508271418013_loading_ch_summerfive05.jpg");
 	    	brokenLinkCheck("월페이퍼 썸네일", "http://static.herowarz.com/editor/upload/2015/35/201508271418011_120loading_ch_summerfive05.jpg");
 	    } else {
 	    	brokenLinkCheck("1920x1080", "http://static.d.p.herowarz.com/editor/upload/2017/25/B-04fb00cf344d4cca972dafcd4871e4d1.jpg");
 	    	brokenLinkCheck("1600x1200", "http://static.d.p.herowarz.com/editor/upload/2017/25/B-dafd6c04e9694277b8f0dcae0e5e4d3c.jpg");
 	    	brokenLinkCheck("월페이퍼 썸네일", "http://static.d.p.herowarz.com/editor/upload/2017/25/B-24c1c4466b404669b5ee1eae30dafa4c.jpg");
 	    }           
 	    System.out.println("자료실 > 월페이퍼 > 상세보기 : Pass");
 	    Thread.sleep(1000);
	}
	//@Test
	public void G_download_e_kit() throws Exception {
		driver.get(baseUrl + "/index.main?c=n");
		js.executeScript("$('.list-download > .kit > a')[0].click();");
		Thread.sleep(1000);
		assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
		assertTrue(driver.getPageSource().contains("과도하게 변형하여 사용할 수 없습니다."));
		System.out.println("자료실 > 월페이퍼 > 페이지 접근 : Pass");
		Thread.sleep(1000);
		if(baseUrl.equals(liveUrl)){
			brokenLinkCheck("MFL BGM킷", "http://static.herowarz.com/common/file/download/fankit/Herowarz_MFL_SignalMusic_2016.12.21.zip");
			brokenLinkCheck("최강의군단 꿀북", "http://static.herowarz.com/filebox/herowarz_honeybook_v.1.1.zip");
			brokenLinkCheck("팬사이트킷", "http://static.herowarz.com/files/fankit/Kit_201411.zip");
			brokenLinkCheck("LBT 팬사이트킷", "http://static.herowarz.com/files/fankit/Herowarz_fankit_2013.11.22_LBT.zip");
		} else {
			brokenLinkCheck("MFL BGM킷", "http://static.d.p.herowarz.com/common/file/download/fankit/Herowarz_MFL_SignalMusic_2016.12.21.zip");
			//brokenLinkCheck("최강의군단 꿀북", "http://static.d.p.herowarz.com/filebox/herowarz_honeybook_v.1.1.zip");
			//brokenLinkCheck("팬사이트킷", "http://static.d.p.herowarz.com/editor/upload/2015/35/201508271418011_120loading_ch_summerfive05.jpg");
			//brokenLinkCheck("LBT 팬사이트킷", "http://static.d.p.herowarz.com/editor/upload/2015/35/201508271418011_120loading_ch_summerfive05.jpg");
		}
		System.out.println("자료실 > 팬사이트킷 > 다운로드 링크 확인 : Pass");
		Thread.sleep(1000);
	}
	//@Test
	public void H_cs_a_faq() throws Exception {
		driver.get(baseUrl + "/index.main?c=n");
		js.executeScript("$('.list-cs > .faq > a')[0].click();");
		Thread.sleep(1000);
		assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
		assertTrue(driver.getPageSource().contains("설치/실행"));
		System.out.println("고객센터 > faq > 리스트 : Pass");
		Thread.sleep(1000);
		js.executeScript("$('.question > a')[0].click()");
		Thread.sleep(1000);
		assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
		assertTrue(driver.getPageSource().contains("조회"));
		System.out.println("고객센터 > faq > 상세보기 : Pass");
	}
	//@Test
	public void H_cs_b_inquiry() throws Exception {
		driver.get(baseUrl + "/index.main?c=n");
		js.executeScript("$('.list-cs > .inquiry > a')[0].click();");
		Thread.sleep(1000);
		assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
		assertTrue(driver.getPageSource().contains("확인해 주세요!"));
		System.out.println("고객센터 > 1:1문의 > 리스트 : Pass");
		Thread.sleep(1000);
		if(baseUrl.equals(devUrl)){
			js.executeScript("$('.section-btn > a')[0].click();");
			Thread.sleep(1000);
			assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
			assertTrue(driver.getPageSource().contains("문의내용을 정확히 입력해 주시기 바랍니다."));
			System.out.println("고객센터 > faq > 상세보기 : Pass");
			new Select(driver.findElement(By.id("inquiryCategoryFirst"))).selectByVisibleText("기타");
			System.out.println ("1:1문의  > 1차 분류 선택 > Pass");
			new Select(driver.findElement(By.id("inquiryCategorySecond"))).selectByVisibleText("기타");
    		System.out.println ("1:1문의  > 2차 분류 선택 > Pass");
    		Thread.sleep(1000);
    		js.executeScript("$('#inquiryTitle').val('테스트 문의 입니다.');");
    		js.executeScript("$('#inquiryContent').val('테스트 문의 입니다.');");
    	    driver.findElement(By.name("uploadfile")).sendKeys("C:\\Users\\Administrator\\Desktop\\아이템\\1.jpg");
    	    Thread.sleep(1000);
    	    js.executeScript("$('.uid_inquiry_submit').click();");
    	    Thread.sleep(1000);
    	    acceptAlert("문의 등록 메세지");
    		System.out.println ("1:1문의  > 등록 > Pass");
    		Thread.sleep(1000);    		
		}
      js.executeScript("$('.cell-subject > a')[0].click();");
      Thread.sleep(1000);
      assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
      assertTrue(driver.getPageSource().contains("목록"));
      System.out.println ("1:1문의  > 상세보기 > Pass");
      Thread.sleep(1000);
	}

	@AfterMethod
	public void testEnd() throws Exception {
		Thread.sleep(2000);
		driver.close();  //driver.quit() 사용시 오류나는 경우 있음
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}
}
