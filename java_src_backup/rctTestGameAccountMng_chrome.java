import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertTrue;
import static org.testng.AssertJUnit.fail;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;

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


public class rctTestGameAccountMng_chrome {		
	    private static WebDriver driver;
		private static String TestBrowser;
		private static JavascriptExecutor js;
		private static String baseUrl, nodeURL;
		private static StringBuffer verificationErrors = new StringBuffer();
		private static HttpURLConnection huc;
	  	private static int respCode;
	  	private static int accountKey;
	    
		@BeforeClass
		public void beforeTest() throws MalformedURLException {	
	  		TestBrowser = "chrome";
	  		
	  		if(TestBrowser.equals("chrome")){
	  			baseUrl = "https://rct-d-p.astorm.com";
		        nodeURL = "http://10.10.105.228:5555/wd/hub";		        
				DesiredCapabilities cap = DesiredCapabilities.chrome();
				cap.setBrowserName("chrome");
				//cap.setPlatform(Platform.WINDOWS);
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
	  		driver.manage().window().maximize();
	        js = (JavascriptExecutor) driver;
	  		accountKey = 350238;
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
	            	System.out.println(TestBrowser + " ***** " + urlName +" : 링크 상태 HTTP : " + respCode + " *****");
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
	  	public static void handleMultipleWindows(String windowTitle) { 
	  		Set<String> windows = driver.getWindowHandles(); 
	  		for (String window : windows) { 
	  			driver.switchTo().window(window); 
	  			if (driver.getTitle().contains(windowTitle)) { 
	  				return; 
				} 
			} 
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
	    public void accountSearch() {
	    	driver.get(baseUrl + "/account/gamedata/searchform.ct");
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	    	assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
	    	assertTrue(driver.getPageSource().contains("계정 검색"));
	    	System.out.println(TestBrowser + " 게임 계정 관리 > 계정 검색 > 페이지 접근 : Pass");
	    	js.executeScript("$('.fa-circle-o:eq(0)').click();");
	    	js.executeScript("$('textarea[name=searchKeyword]').val('영권');");
	    	js.executeScript("$('#uid_account_search_form_search_btn').click();");
	    	System.out.println(TestBrowser + " 게임 계정 관리 > 계정 검색 > 검색 : Pass");
	  	}
	  	@Test(priority = 3)
	    public void accountSearch_friendList() {
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
	        	System.out.println(TestBrowser + " 게임 계정 관리 > 계정 검색 > 친구목록 팝업 확인 : Pass");
	        	driver.close();
	        	handleMultipleWindows("Control Tower @ reboot");
	    	}
	  	}
	  	@Test(priority = 4)
	    public void accountSearch_init() {
	  		elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	        elementWait("class", "ac_container_pagination");
	    	js.executeScript("$('.uid_game_account_init').eq(1).click();");
	    	//elementWait("class", "uid_ok_btn");
	        assertTrue(driver.getPageSource().contains("정말 초기화 하시겠습니까?"));
	        js.executeScript("$('.uid_ok_btn').click();");
	    	elementWait("class", "ac_container_message_footer");
	        js.executeScript("$('.uid_ok_btn').click();");
	    	System.out.println(TestBrowser + " 게임 계정 관리 > 계정 검색 > 계정 초기화 : Pass");
	  	}
	  	@Test(priority = 5)
	    public void accountSearch_info() {
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	        elementWait("class", "fa-chevron-down");
	    	js.executeScript("$('.fa-circle-o:eq(0)').click();");
	    	elementWait("name", "searchKeyword");
	    	js.executeScript("$('textarea[name=searchKeyword]').val('영권');");
	    	js.executeScript("$('#uid_account_search_form_search_btn').click();");
	        elementWait("class", "uid_friends_list_popup_show_link");
	    	js.executeScript("$('a')[39].click();");
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	        elementWait("class", "uid_friends_list_popup_show_link");
	        System.out.println(TestBrowser + " 게임 계정 관리 > 계정 검색 > 상세정보 보기 : Pass");
	  	}
	    @Test(priority = 6)
	    public void clanList() {
	        driver.get(baseUrl + "/clan/clanList.ct");
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	        assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
	        System.out.println(TestBrowser + " 클랜 관리 > 클랜 리스트 > 페이지 접근 : Pass");
	  	}
	  	@Test(priority = 7)
	    public void clanList_addClan() throws Exception {
	        js.executeScript("$('.ac_container_right > div > a')[0].click();");
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	        elementWait("class", "uid_duplicate_check_btn");
	        System.out.println(TestBrowser + " 클랜 관리 > 클랜 추가 > 페이지 접근 : Pass");
	        js.executeScript("$('.uid_duplicate_check_btn').click();");
	        elementWait("class", "type_inner_space");
	        js.executeScript("$('input[name=]').val('9000115');");
	        js.executeScript("$('button[type=submit]')[1].click();");
	        elementWait("class", "uid_clan_name_use_btn");
	        js.executeScript("$('.uid_clan_name_use_btn').click();");
	        elementWait("class", "ac_container_message_footer");
	        js.executeScript("$('.uid_ok_btn').click();");
	        System.out.println(TestBrowser + " 클랜 관리 > 클랜 추가 > 클랜명 중복 확인 : Pass");
	        js.executeScript("$('.uid_search_clan_master_btn').click();");
	        js.executeScript("$('input[name=]').val('9000115');");
	        js.executeScript("$('button[type=submit]')[1].click();");
	        elementWait("class", "uid_use_clan_master_btn");
	        js.executeScript("$('.uid_use_clan_master_btn').click();");
	        elementWait("class", "ac_container_message_footer");
	        js.executeScript("$('.uid_ok_btn').click();");
	        System.out.println(TestBrowser + " 클랜 관리 > 클랜 추가 > 클랜장 중복 확인 : Pass");
	        js.executeScript("$('button[type=submit]').click();");
	        elementWait("class", "ac_container_message_footer");
	        js.executeScript("$('.uid_ok_btn').click();");
	        elementWait("class", "ac_container_message_footer");
	        js.executeScript("$('.uid_ok_btn').click();");        
	        System.out.println(TestBrowser + " 클랜 관리 > 클랜 추가 > 클랜 추가 완료 : Pass");
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	        elementWait("class", "ac_container_pagination");
	        assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
	  	}
	  	@Test(priority = 8)
	    public void clanList_searchClan() {
	        js.executeScript("$('input[name=name]').val('9000115');");
	        js.executeScript("$('input[name=master_account_key]').val('9000115');");
	        js.executeScript("$('.fa-chevron-down').eq(1).click();");
	        js.executeScript("$('li[data-key=3]').click();");
	        js.executeScript("$('button[type=submit]').click();");
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	        assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
	        System.out.println(TestBrowser + " 클랜 관리 > 클랜 리스트 > 클랜 검색 : Pass");
	  	}
	  	@Test(priority = 9)
	    public void clanList_detailInfo() {
	        js.executeScript("$('.uid_clan_name_link')[0].click();");
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	        elementWait("class", "uid_duplicate_check_btn");
	        assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
	        System.out.println(TestBrowser + " 클랜 관리 > 클랜관리 > 상세보기 페이지 접근 : Pass");
	  	}
	  	@Test(priority = 10)
	    public void clanList_modifyName() {
	        js.executeScript("$('.uid_duplicate_check_btn').click();");
	        js.executeScript("$('input[name=]').val('9000116');");
	        js.executeScript("$('button[type=submit]')[2].click();");
	        elementWait("class", "uid_clan_name_use_btn");
	        js.executeScript("$('.uid_clan_name_use_btn').click();");
	        elementWait("class", "ac_container_message_footer");
	        assertTrue(driver.getPageSource().contains("해당 클랜명을 사용하시겠습니까?"));
	        js.executeScript("$('.uid_ok_btn').click();");
	        js.executeScript("$('button[type=submit]').eq(1).click();");
	        js.executeScript("$('.uid_ok_btn').click();");
	        js.executeScript("$('.uid_ok_btn').click();");
	        System.out.println(TestBrowser + " 클랜 관리 > 클랜관리 > 클랜이름 수정 : Pass");
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	        elementWait("class", "uid_duplicate_check_btn");
	        assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
	  	}
	  	@Test(priority = 11)
	    public void clanList_modifyStatus() {
	        js.executeScript("$('.fa-chevron-down').click();");
	        js.executeScript("$('li[data-key=1]').click();");
	        js.executeScript("$('button[type=submit]').eq(1).click();");
	        elementWait("class", "ac_container_message_footer");
	        assertTrue(driver.getPageSource().contains("클랜정보를 수정하시겠습니까?"));
	        js.executeScript("$('.uid_ok_btn').click();");
	        js.executeScript("$('.uid_ok_btn').click();");
	        System.out.println(TestBrowser + " 클랜 관리 > 클랜관리 > 클랜상태 수정 : Pass");  
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	        elementWait("class", "uid_duplicate_check_btn");
	        elementWait("class", "uid_add_clan_member_layer_btn");
	        assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
	  	}
	  	@Test(priority = 12)
	    public void clanList_addClanMember() throws InterruptedException {
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	        elementWait("class", "uid_duplicate_check_btn");
	        elementWait("class", "uid_add_clan_member_layer_btn");
	  		js.executeScript("$('.uid_add_clan_member_layer_btn').click();");
	  		js.executeScript("$('input[name=]').val('9000116');");
	        js.executeScript("$('button[type=submit]').eq(2).click();");
	        elementWait("class", "uid_add_clan_member_btn");
	        js.executeScript("$('.uid_add_clan_member_btn').click();");
	        assertTrue(driver.getPageSource().contains("추가 하시겠습니까?"));
	        elementWait("class", "ac_container_message_footer");
	        js.executeScript("$('.uid_ok_btn').click();");
	        elementWait("class", "ac_container_message_footer");
	        assertTrue(driver.getPageSource().contains("추가 되었습니다."));
	        js.executeScript("$('.uid_ok_btn').click();");
	        System.out.println(TestBrowser + " 클랜 관리 > 클랜관리 > 클랜원 추가 : Pass");
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	        elementWait("class", "uid_becrowned_master_btn");
	        assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
	  	}
	  	@Test(priority = 13)
	    public void clanList_changeClanMaster() {
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	        elementWait("class", "uid_becrowned_master_btn");
	  		js.executeScript("$('.uid_becrowned_master_btn').click();");
	        elementWait("class", "uid_ok_btn");
	        js.executeScript("$('.uid_ok_btn').click();");
	        elementWait("class", "uid_ok_btn");
	        js.executeScript("$('.uid_ok_btn').click();");
	        System.out.println(TestBrowser + " 클랜 관리 > 클랜관리 > 클랜장 변경 : Pass");
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	        elementWait("class", "uid_expel_btn");
	        assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
	  	}
	  	@Test(priority = 14)
	    public void clanList_clanMemberExpel() {
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	        elementWait("class", "uid_expel_btn");
	        js.executeScript("$('.uid_expel_btn').click();");
	        elementWait("class", "ac_container_message_footer");
	        assertTrue(driver.getPageSource().contains("추방 하시겠습니까?"));
	        elementWait("class", "uid_ok_btn");
	        js.executeScript("$('.uid_ok_btn').click();");
	        elementWait("class", "uid_ok_btn");
	        assertTrue(driver.getPageSource().contains("추방 되었습니다."));
	        js.executeScript("$('.uid_ok_btn').click();");
	        System.out.println(TestBrowser + " 클랜 관리 > 클랜관리 > 클랜원 추방 : Pass");
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	        elementWait("class", "uid_clan_dismantling_btn");
	    }
	  	@Test(priority = 15)
	    public void clanList_clanDismantling() {
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	        elementWait("class", "uid_clan_dismantling_btn");
	        js.executeScript("$('.uid_clan_dismantling_btn').click();");
	        elementWait("class", "ac_container_message_footer");
	        assertTrue(driver.getPageSource().contains("클랜을 해체 하시겠습니까?"));
	        js.executeScript("$('.uid_ok_btn').click();");
	        elementWait("class", "ac_container_message_footer");
	        assertTrue(driver.getPageSource().contains("해체 되었습니다."));
	        js.executeScript("$('.uid_ok_btn').click();");
	        System.out.println(TestBrowser + " 클랜 관리 > 클랜관리 > 클랜 해체 : Pass");
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	        elementWait("class", "ac_container_section_footer");
	        assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
	  	}
	    @Test(priority = 16)
	    public void gameBlock() {
	  		driver.get(baseUrl + "/account/gameBlock.ct");
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	        assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
	        System.out.println(TestBrowser + " 계정제재 관리 > 페이지 접근 : Pass");
	  	}
	  	@Test(priority = 17)
	    public void gameBlock_accountLock() {
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	        elementWait("class", "uid_form_main_type_combo");
	        elementWait("class", "uid_form_remark");
	        js.executeScript("$('textarea[name=blockForm]').prop('value', '" + accountKey + ", 2, 0, 사유');");
	        js.executeScript("$('input[name=remark]').prop('value', 'ak:" + accountKey + ", sev:2, time:0, rea:사유 "+ TestBrowser +"');");
	        js.executeScript("$('button[type=submit]').click();");
	        assertFalse(driver.getPageSource().contains("제재를 할수가 없습니다. 개발팀에 문의해주세요."));
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");	        
	        elementWait("class", "uid_form_main_type_combo");
	        System.out.println(TestBrowser + " 계정제재 관리 > 계정 잠금 > 계정 잠금 : Pass");
	  	}
	  	@Test(priority = 18)
	    public void gameBlock_accountUnLock() {
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	        elementWait("class", "uid_form_main_type_combo");
	        elementWait("class", "uid_form_remark");
	        js.executeScript("$('.fa-chevron-down').eq(1).click();");
	        js.executeScript("$('li[data-key=080102]').click();");
	        js.executeScript("$('textarea[name=blockForm]').prop('value', '" + accountKey + ", 2');");
	        js.executeScript("$('input[name=remark]').prop('value', 'ak:" + accountKey + ", sev:2 "+ TestBrowser +"');");
	        js.executeScript("$('button[type=submit]').click();");
	        assertFalse(driver.getPageSource().contains("제재를 할수가 없습니다. 개발팀에 문의해주세요."));
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");	        
	        elementWait("class", "uid_form_main_type_combo");
	        System.out.println(TestBrowser + " 계정제재 관리 > 계정 잠금 > 계정 잠금 취소 : Pass");
	  	}
	  	@Test(priority = 19)
	    public void gameBlock_characterLock() {
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	        elementWait("class", "uid_form_main_type_combo");
	        elementWait("class", "uid_form_remark");
	        js.executeScript("$('.fa-chevron-down').eq(0).click();");
	        js.executeScript("$('li[data-key=0802]').click();");
	        elementValueWait("name", "subCategory", "080201");
	        js.executeScript("$('.fa-chevron-down').eq(1).click();");
	        js.executeScript("$('li[data-key=080201]').click();");
	        js.executeScript("$('textarea[name=blockForm]').prop('value', '" + accountKey + ", 2, 1, 0, 사유');");
	        js.executeScript("$('input[name=remark]').prop('value', 'ak:" + accountKey + ", job:2, sev:1, time:0, rea:사유 "+ TestBrowser +"');");
	        js.executeScript("$('button[type=submit]').click();");
	        assertFalse(driver.getPageSource().contains("제재를 할수가 없습니다. 개발팀에 문의해주세요."));
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");	        
	        elementWait("class", "uid_form_main_type_combo");
	        System.out.println(TestBrowser + " 계정제재 관리 > 캐릭터 잠금 > 캐릭터 잠금 : Pass");
	  	}
	  	@Test(priority = 20)
	    public void gameBlock_characterUnlock() {
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	        elementWait("class", "uid_form_main_type_combo");
	        elementWait("class", "uid_form_remark");
	        js.executeScript("$('.fa-chevron-down').eq(0).click();");
	        js.executeScript("$('li[data-key=0802]').click();");
	        elementValueWait("name", "subCategory", "080201");
	        js.executeScript("$('.fa-chevron-down').eq(1).click();");
	        js.executeScript("$('li[data-key=080202]').click();");
	        js.executeScript("$('textarea[name=blockForm]').prop('value', '" + accountKey + ", 1, 2');");
	        js.executeScript("$('input[name=remark]').prop('value', 'ak:" + accountKey + ", sev:1, job:1 "+ TestBrowser +"');");
	        js.executeScript("$('button[type=submit]').click();");
	        assertFalse(driver.getPageSource().contains("제재를 할수가 없습니다. 개발팀에 문의해주세요."));
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");	        
	        elementWait("class", "uid_form_main_type_combo");
	        System.out.println(TestBrowser + " 계정제재 관리 > 계정 잠금 > 계정 잠금 취소 : Pass");
	  	}
	  	@Test(priority = 21)
	    public void gameBlock_tradeLock() {
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	        elementWait("class", "uid_form_main_type_combo");
	        elementWait("class", "uid_form_remark");
	        js.executeScript("$('.fa-chevron-down').eq(0).click();");
	        js.executeScript("$('li[data-key=0803]').click();");
	        elementValueWait("name", "subCategory", "080301");
	        js.executeScript("$('.fa-chevron-down').eq(1).click();");
	        js.executeScript("$('li[data-key=080301]').click();");
	        js.executeScript("$('textarea[name=blockForm]').prop('value', '" + accountKey + ", 2, 1, 0');");
	        js.executeScript("$('input[name=remark]').prop('value', 'ak:" + accountKey + ", job:2, sev:1, time:0, "+ TestBrowser +"');");
	        js.executeScript("$('button[type=submit]').click();");
	        assertFalse(driver.getPageSource().contains("제재를 할수가 없습니다. 개발팀에 문의해주세요."));
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");	        
	        elementWait("class", "uid_form_main_type_combo");
	        System.out.println(TestBrowser + " 계정제재 관리 > 거래 잠금 > 거래 잠금 : Pass");
	  	}
	  	@Test(priority = 22)
	    public void gameBlock_tradeUnlock() {
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	        elementWait("class", "uid_form_main_type_combo");
	        elementWait("class", "uid_form_remark");
	        js.executeScript("$('.fa-chevron-down').eq(0).click();");
	        js.executeScript("$('li[data-key=0803]').click();");
	        elementValueWait("name", "subCategory", "080301");
	        js.executeScript("$('.fa-chevron-down').eq(1).click();");
	        js.executeScript("$('li[data-key=080302]').click();");
	        js.executeScript("$('textarea[name=blockForm]').prop('value', '" + accountKey + ", 2, 1');");
	        js.executeScript("$('input[name=remark]').prop('value', 'ak:" + accountKey + ", job:2, sev:1, "+ TestBrowser +"');");
	        js.executeScript("$('button[type=submit]').click();");
	        assertFalse(driver.getPageSource().contains("제재를 할수가 없습니다. 개발팀에 문의해주세요."));
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");	        
	        elementWait("class", "uid_form_main_type_combo");
	        System.out.println(TestBrowser + " 계정제재 관리 > 거래 잠금 > 거래 잠금 : Pass");
	  	}
	  	@Test(priority = 23)
	    public void gameBlock_contentLock() {
	  		String content = null;
	        for(int i=80401; i<=80404 ; i++ ){
		        elementWait("id", "ac_layout_aside");
		        elementWait("id", "ac_layout_footer");
		        elementWait("class", "uid_form_main_type_combo");
		        elementWait("class", "uid_form_remark");
	        	js.executeScript("$('.fa-chevron-down').eq(0).click();");
		        js.executeScript("$('li[data-key=0804]').click();");
		        elementValueWait("name", "subCategory", "080401");
		        js.executeScript("$('.fa-chevron-down').eq(1).click();");
		        js.executeScript("$('li[data-key=0" + i + "]').click();");
		        js.executeScript("$('textarea[name=blockForm]').prop('value', '" + accountKey + ", 2, 1, 0');");
		        js.executeScript("$('input[name=remark]').prop('value', 'ak:" + accountKey + ", job:2, sev:1, time:0, "+ TestBrowser +"');");
		        js.executeScript("$('button[type=submit]').click();");
		        assertFalse(driver.getPageSource().contains("제재를 할수가 없습니다. 개발팀에 문의해주세요."));
		        elementWait("id", "ac_layout_aside");
		        elementWait("id", "ac_layout_footer");	        
		        elementWait("class", "uid_form_main_type_combo");
		  		switch(i){
		  		case 80401:
			  		content = "제3세계";
			  		break;
		  		case 80402:
		  			content = "해방";
			  		break;
		  		case 80403:
		  			content = "선수 육성 계획";
			  		break;
		  		case 80404:
		  			content = "PvP";
			  		break;
		  		}
		        System.out.println(TestBrowser + " 계정제재 관리 > 컨텐츠 잠금 > " + content + " 잠금 : Pass");
	        }
	  	}
	  	@Test(priority = 24)
	    public void gameBlock_contentUnlock() {
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	        elementWait("class", "uid_form_main_type_combo");
	        elementWait("class", "uid_form_remark");
	        js.executeScript("$('.fa-chevron-down').eq(0).click();");
	        js.executeScript("$('li[data-key=0804]').click();");
	        elementValueWait("name", "subCategory", "080401");
	        js.executeScript("$('.fa-chevron-down').eq(1).click();");
	        js.executeScript("$('li[data-key=080405]').click();");
	        js.executeScript("$('textarea[name=blockForm]').prop('value', '" + accountKey + ", 2, 1');");
	        js.executeScript("$('input[name=remark]').prop('value', 'ak:" + accountKey + ", job:2, sev:1, "+ TestBrowser +"');");
	        js.executeScript("$('button[type=submit]').click();");
	        assertFalse(driver.getPageSource().contains("제재를 할수가 없습니다. 개발팀에 문의해주세요."));
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");	        
	        elementWait("class", "uid_form_main_type_combo");
	        System.out.println(TestBrowser + " 계정제재 관리 > 컨텐츠 잠금 > 모든 컨텐츠 잠금 취소 : Pass");
	  	}
	  	@Test(priority = 25)
	    public void gameBlock_payLock() {
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	        elementWait("class", "uid_form_main_type_combo");
	        elementWait("class", "uid_form_remark");
	        js.executeScript("$('.fa-chevron-down').eq(0).click();");
	        js.executeScript("$('li[data-key=0805]').click();");
	        elementValueWait("name", "subCategory", "080501");
	        js.executeScript("$('.fa-chevron-down').eq(1).click();");
	        js.executeScript("$('li[data-key=080501]').click();");
	        js.executeScript("$('textarea[name=blockForm]').prop('value', '" + accountKey + ", 2, 1, 0');");
	        js.executeScript("$('input[name=remark]').prop('value', 'ak:" + accountKey + ", job:2, sev:1, time:0, "+ TestBrowser +"');");
	        js.executeScript("$('button[type=submit]').click();");
	        assertFalse(driver.getPageSource().contains("제재를 할수가 없습니다. 개발팀에 문의해주세요."));
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");	        
	        elementWait("class", "uid_form_main_type_combo");
	        System.out.println(TestBrowser + " 계정제재 관리 > 유료화 잠금 > 유료화 잠금 : Pass");
	  	}
	  	@Test(priority = 26)
	    public void gameBlock_payUnlock() {
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	        elementWait("class", "uid_form_main_type_combo");
	        elementWait("class", "uid_form_remark");
	        js.executeScript("$('.fa-chevron-down').eq(0).click();");
	        js.executeScript("$('li[data-key=0805]').click();");
	        elementValueWait("name", "subCategory", "080501");
	        js.executeScript("$('.fa-chevron-down').eq(1).click();");
	        js.executeScript("$('li[data-key=080502]').click();");
	        js.executeScript("$('textarea[name=blockForm]').prop('value', '" + accountKey + ", 2, 1, 0');");
	        js.executeScript("$('input[name=remark]').prop('value', 'ak:" + accountKey + ", job:2, sev:1, time:0, "+ TestBrowser +"');");
	        js.executeScript("$('button[type=submit]').click();");
	        assertFalse(driver.getPageSource().contains("제재를 할수가 없습니다. 개발팀에 문의해주세요."));
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");	        
	        elementWait("class", "uid_form_main_type_combo");
	        System.out.println(TestBrowser + " 계정제재 관리 > 유료화 잠금 > 유료화 잠금 취소 : Pass");
	  	}
	    @Test(priority = 27)
	    public void gameBlock_chatLock() {
	  		String content = null;
	  		//일반채팅 버그때문에 안되서 80602로 설정함
	        for(int i=80602; i<=80603 ; i++ ){
		        elementWait("id", "ac_layout_aside");
		        elementWait("id", "ac_layout_footer");
		        elementWait("class", "uid_form_main_type_combo");
		        elementWait("class", "uid_form_remark");
	        	js.executeScript("$('.fa-chevron-down').eq(0).click();");
		        js.executeScript("$('li[data-key=0806]').click();");
		        elementValueWait("name", "subCategory", "080601");
		        js.executeScript("$('.fa-chevron-down').eq(1).click();");
		        js.executeScript("$('li[data-key=0" + i + "]').click();");
		        if(i>80603){
		        	js.executeScript("$('textarea[name=blockForm]').prop('value', '" + accountKey + ", 2, 1, 0');");
			        js.executeScript("$('input[name=remark]').prop('value', 'ak:" + accountKey + ", job:2, sev:1, time:0, "+ TestBrowser +"');");
		        } else {
		        	js.executeScript("$('textarea[name=blockForm]').prop('value', '" + accountKey + ", 2, 1, 3, 0, 4');");
			        js.executeScript("$('input[name=remark]').prop('value', 'ak:" + accountKey + ", job:2, sev:1, ch:3, time:0, len:4 "+ TestBrowser +"');");
		        }
		        js.executeScript("$('button[type=submit]').click();");
		        assertFalse(driver.getPageSource().contains("제재를 할수가 없습니다. 개발팀에 문의해주세요."));
		        elementWait("id", "ac_layout_aside");
		        elementWait("id", "ac_layout_footer");	        
		        elementWait("class", "uid_form_main_type_combo");
		  		switch(i){
		  		case 80601:
			  		content = "일반 채팅 금지"; //버그
			  		break;
		  		case 80602:
		  			content = "해방";
			  		break;
		  		case 80603:
		  			content = "선수 육성 계획";
			  		break;
		  		}
		        System.out.println(TestBrowser + " 계정제재 관리 > 채팅 제재 > " + content + " : Pass");
	        }
	  	}
	    @Test(priority = 28)
	    public void gameBlock_chatUnlock() {
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	        elementWait("class", "uid_form_main_type_combo");
	        elementWait("class", "uid_form_remark");
	        js.executeScript("$('.fa-chevron-down').eq(0).click();");
	        js.executeScript("$('li[data-key=0806]').click();");
	        elementValueWait("name", "subCategory", "080601");
	        js.executeScript("$('.fa-chevron-down').eq(1).click();");
	        js.executeScript("$('li[data-key=080604]').click();");
	        js.executeScript("$('textarea[name=blockForm]').prop('value', '" + accountKey + ", 2, 1, 0');");
	        js.executeScript("$('input[name=remark]').prop('value', 'ak:" + accountKey + ", job:2, sev:1, time:0, "+ TestBrowser +"');");
	        js.executeScript("$('button[type=submit]').click();");
	        assertFalse(driver.getPageSource().contains("제재를 할수가 없습니다. 개발팀에 문의해주세요."));
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");	        
	        elementWait("class", "uid_form_main_type_combo");
	        System.out.println(TestBrowser + " 계정제재 관리 > 유료화 잠금 > 유료화 잠금 취소 : Pass");
	  	}
	    @Test(priority = 29)
	    public void gameBlock_mailLock() {
	  		String content = null;
	        for(int i=80701; i<=80702 ; i++ ){
		        elementWait("id", "ac_layout_aside");
		        elementWait("id", "ac_layout_footer");
		        elementWait("class", "uid_form_main_type_combo");
		        elementWait("class", "uid_form_remark");
	        	js.executeScript("$('.fa-chevron-down').eq(0).click();");
		        js.executeScript("$('li[data-key=0807]').click();");
		        elementValueWait("name", "subCategory", "080701");
		        js.executeScript("$('.fa-chevron-down').eq(1).click();");
		        js.executeScript("$('li[data-key=0" + i + "]').click();");
	        	js.executeScript("$('textarea[name=blockForm]').prop('value', '" + accountKey + ", 1, 0');");
		        js.executeScript("$('input[name=remark]').prop('value', 'ak:" + accountKey + ", sev:1, time:0, "+ TestBrowser +"');");
		        js.executeScript("$('button[type=submit]').click();");
		        assertFalse(driver.getPageSource().contains("제재를 할수가 없습니다. 개발팀에 문의해주세요."));
		        elementWait("id", "ac_layout_aside");
		        elementWait("id", "ac_layout_footer");	        
		        elementWait("class", "uid_form_main_type_combo");
		  		switch(i){
		  		case 80701:
			  		content = "메일 발송"; //버그
			  		break;
		  		case 80702:
		  			content = "수신";
			  		break;
		  		}
		        System.out.println(TestBrowser + " 계정제재 관리 > 메일 제재 > " + content + " 금지 : Pass");
	        }
	  	}
	    //@Test(priority = 30)
	    public void gameBlock_mailUnlock() {
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	        elementWait("class", "uid_form_main_type_combo");
	        elementWait("class", "uid_form_remark");
	        js.executeScript("$('.fa-chevron-down').eq(0).click();");
	        js.executeScript("$('li[data-key=0807]').click();");
	        elementValueWait("name", "subCategory", "080701");
	        js.executeScript("$('.fa-chevron-down').eq(1).click();");
	        js.executeScript("$('li[data-key=080703]').click();");
	        js.executeScript("$('textarea[name=blockForm]').prop('value', '" + accountKey + ", 1, 0');");
	        js.executeScript("$('input[name=remark]').prop('value', 'ak:" + accountKey + ", sev:1 "+ TestBrowser +"');");
	        js.executeScript("$('button[type=submit]').click();");
	        assertFalse(driver.getPageSource().contains("제재를 할수가 없습니다. 개발팀에 문의해주세요."));
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");	        
	        elementWait("class", "uid_form_main_type_combo");
	        System.out.println(TestBrowser + " 계정제재 관리 > 메일 제재 > 메일 제재 취소 : Pass");
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