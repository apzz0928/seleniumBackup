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


public class rctTestAuthMng_chrome {		
	    private static WebDriver driver;
		private static String TestBrowser;
		private static JavascriptExecutor js;
		private static String baseUrl, nodeURL;
		private static StringBuffer verificationErrors = new StringBuffer();
		private static HttpURLConnection huc;
	  	private static int respCode;
	    
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
	    };
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
	    public void authorityMenu_groupOrder() {
	        driver.get(baseUrl + "/authority/menugroup.ct");
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	        assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
	        assertTrue(driver.getPageSource().contains("메뉴 관리"));
	        System.out.println("권한 관리 > 메뉴 관리 > 리스트 페이지 접근 : Pass");
	        js.executeScript("$('.uid_group_order_btn').eq(0).click();");
	        elementWait("class", "uid_ok_btn");
	        js.executeScript("$('.uid_ok_btn').click();");
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	        elementWait("class", "uid_group_order_btn");
	        js.executeScript("$('.uid_group_order_btn').eq(1).click();");
	        elementWait("class", "uid_ok_btn");
	        js.executeScript("$('.uid_ok_btn').click();");
	        System.out.println("권한 관리 > 메뉴 관리 > 순서 변경 : Pass");
	  	}
	  	@Test(priority = 3)
	    public void authorityMenu_keywordSearch() {
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	        elementWait("class", "uid_menu_search_keyword");
	        js.executeScript("$('.ac_input').prop('value', 'common');");
	        js.executeScript("$('.uid_menu_search').click();");
	        assertTrue(driver.getPageSource().contains("공통기능"));
	        js.executeScript("$('.ac_input').prop('value', '')");
	        js.executeScript("$('.uid_menu_search').click();");
	        System.out.println("권한 관리 > 메뉴 관리 > 검색 : Pass");
	  	}
	  	@Test(priority = 4)
	    public void authorityMenu_detailMenu_add() {
	        js.executeScript("$('.uid_menu_title > a')[0].click();");
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	        elementWait("class", "uid_show_layer_menu_add_btn");
	        assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
	        System.out.println("권한 관리 > 메뉴 관리 > 상세보기 페이지 접근 : Pass");
	        js.executeScript("$('.uid_show_layer_menu_add_btn').click();");
	        js.executeScript("$('input[name=menuName]').prop('value', 'Test Menu Name!');");
	        js.executeScript("$('input[name=menuNameKo]').prop('value', 'Test Menu Ko!');");
	        js.executeScript("$('input[name=menuNameEn]').prop('value', 'Test Menu En!');");
	        js.executeScript("$('input[name=menuNameJa]').prop('value', 'Test Menu Ja!');");
	        js.executeScript("$('input[name=menuNameZh]').prop('value', 'Test Menu Zn!');");
	        js.executeScript("$('input[name=menuDesc]').prop('value', 'Test Menu Desc!');");
	        js.executeScript("$('input[name=menuURI]').val('/test.ct');");
	        js.executeScript("$('input[name=menuHide]').val('0');");
	        js.executeScript("$('input[name=menuOrder]').val('1');");
	        js.executeScript("$('.uid_layer_menu_add_btn').click();");
	        elementWait("class", "ac_container_message_footer");
	        js.executeScript("$('.uid_ok_btn').click();");
	        System.out.println("권한 관리 > 메뉴 관리 > 상세보기 페이지 > 메뉴 추가 : Pass");
	  	}
	  	@Test(priority = 5)
	    public void authorityMenu_detailMenu_menuOrder() {
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	        elementWait("class", "uid_menu_order_btn");
	        js.executeScript("$('.uid_menu_order_btn')[2].click();");
	        elementWait("class", "uid_ok_btn");
	        assertTrue(driver.getPageSource().contains("변경 되었습니다."));
	        js.executeScript("$('.uid_ok_btn').click();");
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	        elementWait("class", "uid_menu_order_btn");
	        js.executeScript("$('.uid_menu_order_btn')[3].click();");
	        elementWait("class", "ac_container_message_footer");
	        assertTrue(driver.getPageSource().contains("변경 되었습니다."));
	        js.executeScript("$('.uid_ok_btn').click();");
	        System.out.println("권한 관리 > 메뉴 관리 > 상세보기 페이지 > 메뉴 순서 변경 : Pass");
	  	}
	  	@Test(priority = 6)
	    public void authorityMenu_detailMenu_edit() {
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	        elementWait("class", "uid_menu_edit_btn");
	        js.executeScript("$('.uid_menu_edit_btn')[1].click();");
	        elementWait("class", "uid_layer_menu_edit_btn");
	        js.executeScript("$('input[name=menuName]').prop('value', 'Edit Test Menu Name!!!!!!');");
	        js.executeScript("$('input[name=menuNameKo]').prop('value', 'Edit Test Menu Ko!!!!!!');");
	        js.executeScript("$('input[name=menuNameEn]').prop('value', 'Edit Test Menu En!!!!!!');");
	        js.executeScript("$('input[name=menuNameJa]').prop('value', 'Edit Test Menu Ja!!!!!!');");
	        js.executeScript("$('input[name=menuNameZh]').prop('value', 'Edit Test Menu Zn!!!!!!');");
	        js.executeScript("$('input[name=menuDesc]').prop('value', 'Edit Test Menu Desc!!!!!!');");
	        js.executeScript("$('input[name=menuURI]').val('/editTest.ct!!!!!!!!');");
	        js.executeScript("$('.uid_layer_menu_edit_btn').click();");
	        elementWait("class", "ac_container_message_footer");
	        js.executeScript("$('.uid_ok_btn').click();");
	        System.out.println("권한 관리 > 메뉴 관리 > 상세보기 페이지 > 메뉴 수정 : Pass");    
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	    }
	  	@Test(priority = 7)
	    public void authorityMenu_detailMenu_del() {
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	        elementWait("class", "uid_menu_del_btn");
	        js.executeScript("$('.uid_menu_del_btn')[1].click();");
	        elementWait("class", "ac_container_message_footer");
	        elementWait("class", "uid_ok_btn");
	        assertTrue(driver.getPageSource().contains("이점 유의 해 주세요."));
	        js.executeScript("$('.uid_ok_btn').click();");
	        elementWait("class", "ac_container_message_footer");
	        elementWait("class", "uid_ok_btn");
	        assertTrue(driver.getPageSource().contains("삭제 되었습니다."));
	        js.executeScript("$('.uid_ok_btn').click();");
	        System.out.println("권한 관리 > 메뉴 관리 > 상세보기 페이지 > 메뉴 삭제 : Pass");        
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	    }
	   @Test(priority = 8)
	    public void authorityGroupMng() {
	        driver.get(baseUrl + "/authority/authGroupList.ct");
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	        assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
	        assertTrue(driver.getPageSource().contains("권한 그룹 관리"));
	        System.out.println("권한 관리 > 권한 그룹 관리 > 리스트 페이지 > 페이지 접근 : Pass");
	        js.executeScript("$('tbody > tr:last-child > td:eq(1) > a')[0].click();");
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	        elementWait("class", "uid_authgroup_menu_add_btn");
	        System.out.println("권한 관리 > 권한 그룹 관리 > 상세보기 페이지 > 페이지 접근 : Pass");
	  	}
	  	@Test(priority = 9)
	    public void authorityGroupMng_addMenu() {
	        js.executeScript("$('.ac_toggle_btn > i').eq(0).click();");
	        js.executeScript("$('.uid_authgroup_menu_add_btn').click();");
	        elementWait("class", "ac_container_message_footer");
	        assertTrue(driver.getPageSource().contains("추가 되었습니다."));
	        js.executeScript("$('.uid_ok_btn').click();");
	        System.out.println("권한 관리 > 권한 그룹 관리 > 리스트 페이지 > 메뉴 권한 추가 : Pass");
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	        elementWait("class", "uid_authgroup_menu_delete_btn");
	        elementWait("class", "ac_container_tab_selected");
	  	}
	  	@Test(priority = 10)
	    public void authorityGroupMng_delMenu() {
	        js.executeScript("$('.ac_toggle_btn:eq(1) > i')[0].click();");
	        js.executeScript("$('.uid_authgroup_menu_delete_btn').click();");
	        elementWait("class", "ac_container_message_footer");
	        assertTrue(driver.getPageSource().contains("삭제 되었습니다."));
	        js.executeScript("$('.uid_ok_btn').click();");
	        System.out.println("권한 관리 > 권한 그룹 관리 > 리스트 페이지 > 메뉴 권한 삭제 : Pass");
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	        elementWait("class", "ac_container_tab_selected");
	        elementWait("class", "uid_authgroup_member_add_btn");
	  	}
	  	@Test(priority = 11)
	    public void authorityGroupMng_addUser() throws InterruptedException {
	        js.executeScript("$('.ac_toggle_btn:eq(2) > i')[0].click();");
	        js.executeScript("$('.uid_authgroup_member_add_btn').click();");
	        elementWait("class", "ac_container_message_footer");
	        assertTrue(driver.getPageSource().contains("추가 되었습니다."));
	        js.executeScript("$('.uid_ok_btn').click();");
	        System.out.println("권한 관리 > 권한 그룹 관리 > 리스트 페이지 > 사용자 권한 추가 : Pass");
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	        elementWait("class", "uid_authgroup_member_delete_btn");
	  	}
	  	@Test(priority = 12)
	    public void authorityGroupMng_delUser() throws InterruptedException {
	        js.executeScript("$('.ac_toggle_btn:eq(3) > i')[0].click();");
	        js.executeScript("$('.uid_authgroup_member_delete_btn').click();");
	        elementWait("class", "ac_container_message_footer");
	        assertTrue(driver.getPageSource().contains("해당 관리자의 권한을 삭제하시겠습니까?"));
	        js.executeScript("$('.uid_ok_btn').click();");
	        System.out.println("권한 관리 > 권한 그룹 관리 > 리스트 페이지 > 사용자 권한 삭제 : Pass");
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	        elementWait("class", "ac_container_tab_selected");
	  	}
	  	@Test(priority = 13)
	    public void groupListByAdmin() {
	    	driver.get(baseUrl + "/authority/groupListByAdmin.ct");
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	        //elementWait("class", "uid_ldapmember_combo");
	        assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
	        assertTrue(driver.getPageSource().contains("LDAP 사용자를 선택하세요."));
	        System.out.println("권한 관리 > 관리자 권한 보기 > 페이지 접근 : Pass");
	  	}
	  	@Test(priority = 14)
	    public void groupListByAdmin_internalUser() {
	        js.executeScript("$('.fa-chevron-down').click();");
	        js.executeScript("$('.normal > li:eq(3)').click();");
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	        elementWait("class", "uid_authgroup_member_delete_btn");
	        assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
	        System.out.println("권한 관리 > 관리자 권한 보기 : INTERNAL 계정 권한보기 Pass");
	  	}
	  	@Test(priority = 15)
	    public void groupListByAdmin_LDAPUser() {
	        js.executeScript("$('.ac_container_tab_header > ul > li:eq(1) > a')[0].click();");
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	        assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
	        System.out.println("권한 관리 > 관리자 권한 보기 > LDAP탭 접근 : Pass");
	        js.executeScript("$('.fa-chevron-down').click();");
	        js.executeScript("$('.normal > li:eq(7)').click();");
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	        elementWait("class", "uid_authgroup_member_delete_btn");
	        assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
	        System.out.println("권한 관리 > 관리자 권한 보기 > LDAP 계정 권한보기 : Pass");
	    }
	    @Test(priority = 16)
	    public void ctActionLog() {
	        driver.get(baseUrl + "/authority/ctActionLogList.ct");
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	        assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
	        assertTrue(driver.getPageSource().contains("검색결과가 없습니다."));
	        System.out.println("권한 관리 > 액션 로그 조회 > 페이지 접근 : Pass");
	        if (TestBrowser.equals("ie")) {
	            System.out.println("권한 관리 > 액션 로그 조회 > 검색 : *** IE는 검색하지 않음 ***");
	        } else {
	        	js.executeScript("$('.uid_search_btn').click();");
	            elementWait("class", "uid_ctactionlog_pagination");
	            assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
	            System.out.println("권한 관리 > 액션 로그 조회 > 검색 : Pass");
	        }
	        if (TestBrowser.equals("chrome") || TestBrowser.equals("opera")) {
	        	elementWait("class", "uid_ctactionlog_parameter");
	        	js.executeScript("$('.uid_ctactionlog_parameter:eq(0)').click();");
	            handleMultipleWindows("Control Tower @ Cockpit");
	            js.executeScript("$('.uid_confirm').click();");
	            System.out.println("권한 관리 > 액션 로그 조회 > Parameter 보기 : Pass");
	            handleMultipleWindows("Control Tower @ reboot");
	            js.executeScript("$('.uid_ctactionlog_result:eq(0)').click();");
	            handleMultipleWindows("Control Tower @ Cockpit");
	            js.executeScript("$('.uid_confirm').click();");
	            System.out.println("권한 관리 > 액션 로그 조회 > Result 보기 : Pass");
	            handleMultipleWindows("Control Tower @ reboot");
	        } else if (TestBrowser.equals("ie") || TestBrowser.equals("firefox")) {
	            System.out.println("권한 관리 > 액션 로그 조회 > *** IE랑 Firefox는 팝업 제어가 제대로 되지않아서 Parameter & Result 확인하지않음 ***");        	
	        }        
	    }
	    @Test(priority = 17)
	    public void ctMember_addMember() {
	        driver.get(baseUrl + "/authority/ctMember/list.ct");
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	        assertFalse(driver.getPageSource().contains("페이지 주소가 잘못 입력되었거나, 이미 삭제된 페이지입니다."));
	        assertTrue(driver.getPageSource().contains("CT 사용자 리스트"));
	        System.out.println("권한 관리 > CT 사용자 관리 > 페이지 접근 : Pass");
	        js.executeScript("$('.uid_ctmember_add_btn').click();");
	        elementWait("class", "au_space_top_0");
	        js.executeScript("$('input[name=id]').val('test유저');");
	        js.executeScript("$('input[name=name]').val('이름입니다.');");
	        js.executeScript("$('input[name=department]').val('부서입니다.');");
	        js.executeScript("$('input[name=tel]').val('010-0000-0000');");
	        js.executeScript("$('input[name=email]').val('mail@astorm.co.kr');");
	        js.executeScript("$('input[name=password]').val('password');");
	        js.executeScript("$('input[name=passwordRe]').val('password');");
	        js.executeScript("$('.uid_ctmember_save_btn').click();");
	        elementWait("class", "ac_container_message_footer");
	        assertTrue(driver.getPageSource().contains("저장 하시겠습니까?"));
	        js.executeScript("$('.uid_ok_btn').click();");
	        elementWait("class", "ac_container_message_footer");
	        assertTrue(driver.getPageSource().contains("저장되었습니다."));
	        js.executeScript("$('.uid_ok_btn').click();");
	        System.out.println("권한 관리 > CT 사용자 관리 > 사용자 추가 : Pass");
	    }
	    @Test(priority = 18)
	    public void ctMember_modifyMember() {
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	        elementWait("class", "uid_ctmember_edit_btn");
	        js.executeScript("$('.uid_ctmember_edit_btn:eq(0)').click();");
	        elementWait("class", "uid_ctmember_save_btn");
	        js.executeScript("$('input[name=name]').val('***** 이름 - 수정 *****');");
	        js.executeScript("$('input[name=department]').val('***** 부서 - 수정 *****');");
	        js.executeScript("$('input[name=tel]').val('***** 010-9999-9999 *****');");
	        js.executeScript("$('input[name=email]').val('***** 메일수정@astorm.co.kr *****');");
	        js.executeScript("$('.uid_ctmember_save_btn').click();");
	        elementWait("class", "ac_container_message_footer");
	        assertTrue(driver.getPageSource().contains("저장 하시겠습니까?"));
	        js.executeScript("$('.uid_ok_btn').click();");
	        elementWait("class", "ac_container_message_footer");
	        assertTrue(driver.getPageSource().contains("저장되었습니다."));
	        js.executeScript("$('.uid_ok_btn').click();");
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	        elementWait("class", "uid_ctmember_edit_btn");
	        System.out.println("권한 관리 > CT 사용자 관리 > 사용자 수정 : Pass");
	    }
	    @Test(priority = 19)
	    public void ctMember_resetPassword() {
	        js.executeScript("$('.uid_ctmember_initpassword_btn:eq(0)').click();");
	        elementWait("class", "ac_container_message_footer");
	        assertTrue(driver.getPageSource().contains("정말 비밀번호를 초기화"));
	        js.executeScript("$('.uid_ok_btn').click();");
	        elementWait("class", "ac_container_message_footer");
	        assertTrue(driver.getPageSource().contains("비밀번호가 초기화되었습니다."));
	        js.executeScript("$('.uid_ok_btn').click();");
	        System.out.println("권한 관리 > CT 사용자 관리 > 비밀번호 초기화 : Pass");
	    }
	    @Test(priority = 20)
	    public void ctMember_delMember() {
	        elementWait("id", "ac_layout_aside");
	        elementWait("id", "ac_layout_footer");
	        elementWait("class", "uid_ctmember_edit_btn");
	        js.executeScript("$('.uid_ctmember_delete_btn:eq(0)').click();");
	        elementWait("class", "ac_container_message_footer");
	        assertTrue(driver.getPageSource().contains("정말 삭제 하시겠습니까?"));
	        js.executeScript("$('.uid_ok_btn').click();");
	        elementWait("class", "ac_container_message_footer");
	        assertTrue(driver.getPageSource().contains("삭제되었습니다."));
	        js.executeScript("$('.uid_ok_btn').click();");
	        System.out.println("권한 관리 > CT 사용자 관리 > 사용자 삭제 : Pass");
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