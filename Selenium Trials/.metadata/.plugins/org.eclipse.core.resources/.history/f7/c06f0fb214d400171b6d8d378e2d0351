import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.codeborne.selenide.WebDriverRunner;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.WebDriverRunner.*;

import com.codeborne.selenide.testng.ScreenShooter;


public class gameAccountMng_Selenide {
	private static String baseUrl, hubUrl;
	private static String TestBrowser;
	private static String accountKey;
	
	@Parameters("browser")
	@BeforeClass
	public void beforeTest(String browser) throws MalformedURLException {
		baseUrl = "https://rct-d-p.astorm.com";
		hubUrl = "http://10.10.105.228:4444/wd/hub";
  		
  		String urlToRemoteWD = hubUrl;
  		DesiredCapabilities cap;
  		ScreenShooter.captureSuccessfulTests = false;
  		if(browser.equals("chrome")){
  			TestBrowser = "chrome";
  			cap = DesiredCapabilities.chrome();
	        RemoteWebDriver driver = new RemoteWebDriver(new URL(urlToRemoteWD),cap);
	        WebDriverRunner.setWebDriver(driver);
	  		driver.manage().window().setSize(new Dimension(1600, 1400));
  		} else if(browser.equals("firefox")) {
  			TestBrowser = "firefox";
  			cap = DesiredCapabilities.firefox();
	        RemoteWebDriver driver = new RemoteWebDriver(new URL(urlToRemoteWD),cap);
	        WebDriverRunner.setWebDriver(driver);
	  		driver.manage().window().setSize(new Dimension(1600, 1400));
  		} else if(browser.equals("internetExplorer")) {
  			TestBrowser = "internetExplorer";
  			cap = DesiredCapabilities.internetExplorer();
  			cap.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true); //IE 실행을 위한 보안 관련 설정 임시 변경
  			cap.setCapability(InternetExplorerDriver.NATIVE_EVENTS, false); //ie text 입력 속도 향상을 위한 부분
  			cap.setCapability("requireWindowFocus", true); //ie text 입력 속도 향상을 위한 부분
  			cap.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true); //ie 캐시 삭제를 위한 부분
	        RemoteWebDriver driver = new RemoteWebDriver(new URL(urlToRemoteWD),cap);
	        WebDriverRunner.setWebDriver(driver);
	  		driver.manage().window().setSize(new Dimension(1600, 1400));
  		} 
  		accountKey = "350238";
        
    }
	private static void js(String javaScriptSource) {
	    executeJavaScript(javaScriptSource);
	}
  	public static void windowTitle(String windowTitle) { 
  		WebDriverRunner.getWebDriver();
  		Set<String> windows = getWebDriver().getWindowHandles();
  		for (String window : windows) { 
  			switchTo().window(window); 
  			if (getWebDriver().getTitle().contains(windowTitle)) { 
  				return; 
			} 
		} 
	}
	@Test(priority = 0)
	public void Login() {
        open(baseUrl + "/login/form.ct");
        $(By.name("id")).setValue("apzz0928");
        $(By.name("password")).setValue("qordlf12");
        $(".uid_internal_login_submit_btn").click();
        open(baseUrl + "/common/locale/ko");
        System.out.println(TestBrowser + " Login : Pass");
    }
	//@Test(priority = 1)
	public void accountSearch() {
        open(baseUrl + "/account/gamedata/searchform.ct");
        $(".menu-title").waitUntil(text("전체 메뉴"), 3000);
        $(".fa-circle-o", 0).click();
        $("textarea[name=searchKeyword]").setValue("영권");
        $("#uid_account_search_form_search_btn").click();
        $(".uid_game_account_init", 0).click();
        $(".uid_ok_btn").click();
        $(".uid_ok_btn").click();
        $(".menu-title").waitUntil(text("전체 메뉴"), 3000);
        $(".fa-circle-o", 0).click();
        $("textarea[name=searchKeyword]").setValue("영권");
        $("#uid_account_search_form_search_btn").click();
        $(".uid_account_search_page_page", 2).waitUntil(text("1"), 3000);
        js("document.querySelectorAll('.uid_friends_list_popup_show_link')[2].click();");	
        windowTitle("Control Tower @ Cockpit");
        $(".ac_input").setValue("9000115");
        $(".ac_btn_text", 0).click();
        $(".ac_input").setValue("");
        $(".ac_btn_text", 0).click();
        windowTitle("Control Tower @ reboot");
        $(By.linkText("영권1237")).click();
        $(".menu-title").waitUntil(text("전체 메뉴"), 3000);
        System.out.println(TestBrowser + " accountSearch : Pass");
    }
	//@Test(priority = 2)
	public void accountBlock() {
		open(baseUrl + "/account/gameBlock.ct");
        $(".menu-title").waitUntil(text("전체 메뉴"), 3000);
        $("textarea[name=blockForm]").setValue(accountKey + ", 2, 0, 사유");
        $("input[name=remark]").setValue("ak:" + accountKey + ", sev:2, time:0, rea:사유 "+ TestBrowser);
        $(".ac_btn_text", 0).click();
        System.out.println(TestBrowser + " account Block : Pass");
        open(baseUrl + "/account/gameBlock.ct");
        $(".menu-title").waitUntil(text("전체 메뉴"), 3000);
        $(".fa-chevron-down", 1).click();
        $(".menu-title").waitUntil(text("전체 메뉴"), 3000);
        $("li[data-key='080102']").click();
        $("textarea[name=blockForm]").setValue(accountKey + ", 2");
        $("input[name=remark]").setValue("ak:" + accountKey + ", sev:2, "+ TestBrowser);
        $(".ac_btn_text", 0).click();
        System.out.println(TestBrowser + " account UnBlock : Pass");
        open(baseUrl + "/account/gameBlock.ct");
        $(".menu-title").waitUntil(text("전체 메뉴"), 3000);
        $(".fa-chevron-down", 0).click();
        $("li[data-key='0802']").click();
        $(".menu-title").waitUntil(text("전체 메뉴"), 3000);
        $(".fa-chevron-down", 1).click();
        $("li[data-key='080201']").click();
        $("textarea[name=blockForm]").setValue(accountKey + ", 2, 1, 0, 사유");
        $("input[name=remark]").setValue("ak:" + accountKey + ", job:2, sev:1, time:0, rea:사유 "+ TestBrowser);
        $(".ac_btn_text", 0).click();
        System.out.println(TestBrowser + " character Block : Pass");
        open(baseUrl + "/account/gameBlock.ct");
        $(".menu-title").waitUntil(text("전체 메뉴"), 3000);
        $(".fa-chevron-down", 0).shouldBe(visible).click();
        $("li[data-key='0802']").shouldBe(visible).click();
        $(".menu-title").waitUntil(text("전체 메뉴"), 3000);
        $(".fa-chevron-down", 1).shouldBe(visible).click();
        $("li[data-key='080202']").shouldBe(visible).click();
        $("textarea[name=blockForm]").setValue(accountKey + ", 1, 2");
        $("input[name=remark]").setValue("ak:" + accountKey + ", sev:2, job:1 "+ TestBrowser);
        $(".ac_btn_text", 0).click();
        System.out.println(TestBrowser + " character UnBlock : Pass");
        open(baseUrl + "/account/gameBlock.ct");
        $(".menu-title").waitUntil(text("전체 메뉴"), 3000);
        $(".fa-chevron-down", 0).click();
        $("li[data-key='0803']").click();
        $(".menu-title").waitUntil(text("전체 메뉴"), 3000);
        js("$('input[name=subCategory]').eq(0).val('080301');");
        $("textarea[name=blockForm]").setValue(accountKey + ", 2, 1, 0");
        $("input[name=remark]").setValue("ak:" + accountKey + ", job:2, sev:1, time:0, "+ TestBrowser);
        $(".ac_btn_text", 0).click();
        System.out.println(TestBrowser + " trade Block : Pass");
        /*
        open(baseUrl + "/account/gameBlock.ct");
        $(".fa-chevron-down", 0).click();
        $("li[data-key='0803']").click();
        js("$('input[name=subCategory]').eq(0).val('080302');");
        $("textarea[name=blockForm]").setValue(accountKey + ", 2, 1");
        $("input[name=remark]").setValue("ak:" + accountKey + ", job:2, sev:1, "+ TestBrowser);
        $(".ac_btn_text", 0).click();
        System.out.println(TestBrowser + " trade UnBlock : Pass");
        */
        String content = null;
        for(int i=80401;i<=80404;i++){
        	open(baseUrl + "/account/gameBlock.ct");
            $(".menu-title").waitUntil(text("전체 메뉴"), 3000);
            $(".fa-chevron-down", 0).click();
            $("li[data-key='0804']").click();
            $(".menu-title").waitUntil(text("전체 메뉴"), 3000);
            $(".fa-chevron-down", 1).click();
            $("textarea[name=blockForm]").setValue(accountKey + ", 2, 1, 0");
            $("input[name=remark]").setValue("ak:" + accountKey + ", job:2, sev:1, time:0, "+ TestBrowser);
            $("li[data-key='0" + i + "']").shouldBe(appear).click();
            js("document.querySelector('.ac_btn_text').click();");
            //$(".ac_btn_text", 0).click();       
            switch(i){
	  		case 80401:
		  		content = "Three world";
		  		break;
	  		case 80402:
	  			content = "liberation";
		  		break;
	  		case 80403:
	  			content = "Training";
		  		break;
	  		case 80404:
	  			content = "PvP";
		  		break;
	  		}
            System.out.println(TestBrowser + " content Block : " + content +" : Pass");
        }
        open(baseUrl + "/account/gameBlock.ct");
        $(".menu-title").waitUntil(text("전체 메뉴"), 3000);
        $(".fa-chevron-down", 0).click();
        $("li[data-key='0804']").click();
        $(".menu-title").waitUntil(text("전체 메뉴"), 3000);
        $(".fa-chevron-down", 1).shouldBe(visible).click();
        $("li[data-key='080405']").shouldBe(visible).click();
        $(".ac_btn_text").waitUntil(text("제재"), 3000);
        $("textarea[name=blockForm]").setValue(accountKey + ", 2, 1, 0");
        $("input[name=remark]").setValue("ak:" + accountKey + ", job:2, sev:1, time:0, "+ TestBrowser);
        $(".ac_btn_text", 0).click();
        System.out.println(TestBrowser + " content UnBlock : Pass");
        open(baseUrl + "/account/gameBlock.ct");
        $(".menu-title").waitUntil(text("전체 메뉴"), 3000);
        $(".fa-chevron-down", 0).click();
        $("li[data-key='0805']").click();
        $(".menu-title").waitUntil(text("전체 메뉴"), 3000);
        $(".fa-chevron-down", 1).click();
        $("li[data-key='080501']").click();
        $(".ac_btn_text").waitUntil(text("제재"), 3000);
        $("textarea[name=blockForm]").setValue(accountKey + ", 2, 1, 0");
        $("input[name=remark]").setValue("ak:" + accountKey + ", job:2, sev:1, time:0, "+ TestBrowser);
        $(".ac_btn_text", 0).click();
        System.out.println(TestBrowser + " pay Block : Pass");
        open(baseUrl + "/account/gameBlock.ct");
        $(".menu-title").waitUntil(text("전체 메뉴"), 3000);
        $(".fa-chevron-down", 0).click();
        $("li[data-key='0805']").click();
        $(".menu-title").waitUntil(text("전체 메뉴"), 3000);
        $(".fa-chevron-down", 1).click();
        $("li[data-key='080502']").click();
        $(".ac_btn_text").waitUntil(text("제재"), 3000);
        $("textarea[name=blockForm]").setValue(accountKey + ", 2, 1, 0");
        $("input[name=remark]").setValue("ak:" + accountKey + ", job:2, sev:1, time:0, "+ TestBrowser);
        $(".ac_btn_text", 0).click();
        System.out.println(TestBrowser + " pay UnBlock : Pass");
        for(int i=80602;i<=80604;i++){ //일반 채팅 금지 버그있어서 80602부터
        	open(baseUrl + "/account/gameBlock.ct");
            $(".menu-title").waitUntil(text("전체 메뉴"), 3000);
            $(".fa-chevron-down", 0).click();
            $("li[data-key='0806']").click();
            $(".menu-title").waitUntil(text("전체 메뉴"), 3000);
            $(".fa-chevron-down", 1).click();
            $("li[data-key='0" + i + "']").click();
            $(".ac_btn_text").waitUntil(text("제재"), 3000);
            if(i == 80601){
            	$("textarea[name=blockForm]").setValue(accountKey + "");
                $("input[name=remark]").setValue("ak:" + accountKey + ""+ TestBrowser);	
            } else if (i == 80602){
            	$("textarea[name=blockForm]").setValue(accountKey + ", 2, 1, 3, 0, 4");
                $("input[name=remark]").setValue("ak:" + accountKey + ", job:2, sev:1, ch:3, time:0, len:4, "+ TestBrowser);	
            } else if (i == 80603){
            	$("textarea[name=blockForm]").setValue(accountKey + ", 2, 1, 3, 0, 4");
                $("input[name=remark]").setValue("ak:" + accountKey + ", job:2, sev:1, ch:3, time:0, len:4, "+ TestBrowser);
            } else if (i == 80604){
            	$("textarea[name=blockForm]").setValue(accountKey + ", 2, 1, 0");
                $("input[name=remark]").setValue("ak:" + accountKey + ", job:2, sev:1, time:0, "+ TestBrowser);
            }
            $(".ac_btn_text", 0).click();
	  		switch(i){
	  		case 80601:
	  			content = "normal Chat Block"; //버그
		  		break;
	  		case 80602:
	  			content = "Chat CD Block";
		  		break;
	  		case 80603:
	  			content = "Chat Silence Block";
		  		break;
	  		case 80604:
	  			content = "Chat UnBlock";
	  		}
            System.out.println(TestBrowser + " " + content + "  : Pass");
        }
        for(int i=80701;i<=80702;i++){
            open(baseUrl + "/account/gameBlock.ct");
            $(".menu-title").waitUntil(text("전체 메뉴"), 3000);
            $(".fa-chevron-down", 0).click();
            $("li[data-key='0807']").click();
            $(".menu-title").waitUntil(text("전체 메뉴"), 3000);
            $(".fa-chevron-down", 1).click();
            $("li[data-key='0" + i +"']").click();
            $(".ac_btn_text").waitUntil(text("제재"), 3000);
            if(i < 80703){
                $("textarea[name=blockForm]").setValue(accountKey + ", 1, 0");
                $("input[name=remark]").setValue("ak:" + accountKey + ", sev:1, time:0, "+ TestBrowser);
            } else if (i == 80703) {
            	$("textarea[name=blockForm]").setValue(accountKey + ", 1");
                $("input[name=remark]").setValue("ak:" + accountKey + ", sev:1, "+ TestBrowser);
                $(".ac_btn_text", 0).click();	
            }
            $(".ac_btn_text", 0).click();
            switch(i){
	  		case 80701:
	  			content = "send";
		  		break;
	  		case 80702:
	  			content = "receive";
		  		break;
	  		case 80703:
	  			content = "Un";
		  		break;
            }
            System.out.println(TestBrowser + " mail " + content + " Block : Pass");
        }
    }
	//@Test(priority = 3)
	public void clan() {
        open(baseUrl + "/clan/clanList.ct");
        $(".menu-title").waitUntil(text("전체 메뉴"), 3000);
        System.out.println(TestBrowser + " clan List : Pass");
        /*
        $(".ac_btn_text", 1).click();
        $(".menu-title").waitUntil(text("전체 메뉴"), 3000);
        $(".ac_btn_text", 0).click();
        $(".tmp_title").waitUntil(text("클랜명 검색"), 3000);
        $(".ac_input", 4).setValue("9000115");
        $(".ac_btn_text", 4).click();
        $(".uid_clan_name_use_btn").click();
        $(".uid_ok_btn").click();
        $(".ac_btn_text", 1).click();
        $(".tmp_title").waitUntil(text("클랜장 검색"), 3000);
        $(".ac_input", 4).setValue("9000115");
        $(".ac_btn_text", 4).click();
        $(".uid_use_clan_master_btn").click();
        $(".uid_ok_btn").click();
        $(".ac_btn_text", 3).click();
        $(".uid_ok_btn").click();
        $(".menu-title").waitUntil(text("전체 메뉴"), 3000);
        System.out.println(TestBrowser + " clan add : Pass");
        */
        $(".ac_input", 1).setValue("123456789");
        $(".ac_btn_text", 0).click();
        $(".menu-title").waitUntil(text("전체 메뉴"), 3000);
        $(By.linkText("123456789")).shouldBe(visible).click();
        $(".menu-title").waitUntil(text("전체 메뉴"), 3000);
        $(".uid_duplicate_check_btn").click();
        $(".tmp_title").waitUntil(text("클랜명 검색"), 3000);
        $(".ac_input", 2).setValue("987654321");
        $(".ac_btn_text", 7).click();
        $(".ac_btn_text", 8).click();
        $(".uid_ok_btn").click();
        $(".ac_btn_text", 2).click();
        $(".uid_ok_btn").click();
        $(".uid_ok_btn").click();
        $(".menu-title").waitUntil(text("전체 메뉴"), 3000);
        System.out.println(TestBrowser + " clan name edit : Pass");
        $(".fa-chevron-down").click();
        $("li[data-key='1']").click();
        $(".ac_btn_text", 2).click();
        $(".uid_ok_btn").click();
        $(".uid_ok_btn").click();
        $(".menu-title").waitUntil(text("전체 메뉴"), 3000);
        System.out.println(TestBrowser + " clan status edit : Pass");
        $(".uid_add_clan_member_layer_btn").click();
        $(".tmp_title").waitUntil(text("클랜원 추가"), 3000);
        $(".ac_input", 2).setValue("9000116");
        $(".uid_add_clan_member_search_btn").click();
        $(".ac_btn_text", 8).click();
        $(".uid_ok_btn").click();
        $(".uid_ok_btn").click();
        $(".uid_add_clan_member_layer_btn").click();
        $(".tmp_title").waitUntil(text("클랜원 추가"), 3000);
        $(".ac_input", 2).setValue("350238");
        $(".uid_add_clan_member_search_btn").click();
        $(".ac_btn_text", 9).click();
        $(".uid_add_clan_member_btn").click();
        $(".uid_ok_btn").click();
        $(".uid_ok_btn").click();
        $(".menu-title").waitUntil(text("전체 메뉴"), 3000);
        System.out.println(TestBrowser + " clan add member : Pass");    
        $(".uid_expel_btn", 0).click();
        $(".uid_ok_btn").click();
        $(".uid_ok_btn").click();
        $(".menu-title").waitUntil(text("전체 메뉴"), 3000);
        System.out.println(TestBrowser + " clan expel member1 : Pass");
        $(".uid_becrowned_master_btn", 0).click();
        $(".uid_ok_btn").click();
        $(".uid_ok_btn").click();
        $(".menu-title").waitUntil(text("전체 메뉴"), 3000);
        System.out.println(TestBrowser + " clan change member : Pass");   
        $(".uid_expel_btn", 0).click();
        $(".uid_ok_btn").click();
        $(".uid_ok_btn").click();
        $(".menu-title").waitUntil(text("전체 메뉴"), 3000);
        System.out.println(TestBrowser + " clan expel member2 : Pass");
        /*
        //클랜 해체
        $(".uid_clan_dismantling_btn").click();
        $(".uid_ok_btn").click();
        $(".uid_ok_btn").click();
        System.out.println(TestBrowser + " clan dismantling member2 : Pass");
        */
        //클랜 상태 원복 써놓기
        $(".ac_btn_text", 3).click();
        $(".ac_input", 1).setValue("987654321");
        $(".ac_btn_text", 0).click();
        $(".menu-title").waitUntil(text("전체 메뉴"), 3000);
        $(By.linkText("987654321")).click();
        $(".menu-title").waitUntil(text("전체 메뉴"), 3000);
        $(".uid_duplicate_check_btn").click();
        $(".tmp_title").waitUntil(text("클랜명 검색"), 3000);
        $(".ac_input", 2).setValue("123456789");
        $(".ac_btn_text", 7).click();
        $(".ac_btn_text", 8).click();
        $(".uid_ok_btn").click();
        $(".ac_btn_text", 2).click();
        $(".uid_ok_btn").click();
        $(".uid_ok_btn").click();
        $(".menu-title").waitUntil(text("전체 메뉴"), 3000);
        $(".fa-chevron-down").click();
        $("li[data-key='3']").click();
        $(".ac_btn_text", 2).click();
        $(".uid_ok_btn").click();
        $(".uid_ok_btn").click();
        $(".menu-title").waitUntil(text("전체 메뉴"), 3000);
        $(".uid_add_clan_member_layer_btn").click();
        $(".tmp_title").waitUntil(text("클랜원 추가"), 3000);
        $(".ac_input", 2).setValue("9000115");
        $(".uid_add_clan_member_search_btn").click();
        $(".ac_btn_text", 8).click();
        $(".uid_ok_btn").click();
        $(".uid_ok_btn").click();
        $(".uid_becrowned_master_btn", 0).click();
        $(".uid_ok_btn").click();
        $(".uid_ok_btn").click();
        $(".menu-title").waitUntil(text("전체 메뉴"), 3000);
        $(".uid_expel_btn", 0).click();
        $(".uid_ok_btn").click();
        $(".uid_ok_btn").click();
        $(".menu-title").waitUntil(text("전체 메뉴"), 3000);
    }
	//@Test(priority = 4)
	public void promptTest() throws InterruptedException {
        open("https://rct-d-p.astorm.com/account/gamedata/gameAccountInfo.ct?sk=1&accountKey=56");
        $(".uid_inven_equip_link", 0).click();
        $(".ac_container_tab").waitUntil(visible, 5000);
        $(".link-inven-equip").click();
        $(".uid_iteminfo_link").waitUntil(visible, 5000);
        $(".uncommon").click();
        $(".ac_container_layer_header").waitUntil(visible, 5000);
        $(".au_text_center", 16).click();
        //$(By.linkText("삭제")).click();
        Thread.sleep(5000);
        prompt("삭제 사유를 입력하세요.", "prompt 테스트");
        Thread.sleep(5000);
        $(".uid_ok_btn").click();
        Thread.sleep(5000);
    }
	//@Test(priority = 5)
	public void clan_account() throws InterruptedException {
        open("https://rct-d-p.astorm.com/clan/clanDetail.ct?sk=1&clan_key=562");
        $(".uid_add_clan_member_layer_btn").click();
        for(int i=20000;i<30201;i++){
        	$(".ac_input", 2).setValue("" + i + "");
            System.out.println(TestBrowser + " " + i + "입력");
            $(".uid_add_clan_member_search_btn").click();
            Thread.sleep(500);
        }

    }
	//@Test(priority = 6)
	public void clan_expel() {
        open("https://rct-d-p.astorm.com/clan/clanDetail.ct?sk=1&clan_key=322");
        for(int i=0;i<=25;i++){
            $(".uid_expel_btn", 0).click();
            $(".uid_ok_btn").click();
            refresh();
        }
    }	
	@Test(priority = 7)
	public void clan_add() {
		String ak[];
		ak = new String[25];
		ak[0]="333"; ak[1]="444"; ak[2]="504"; ak[3]="518"; ak[4]="560"; ak[5]="1111"; ak[6]="1234"; ak[7]="1319"; 
		ak[8]="1322"; ak[9]="10021"; ak[10]="10081"; ak[11]="10089"; ak[12]="10110"; ak[13]="10119"; ak[14]="10245"; 
		ak[15]="10381"; ak[16]="10382"; ak[17]="10409"; ak[18]="10501"; ak[19]="10502"; ak[20]="10514";
		ak[21]="10503"; ak[22]="10516"; ak[23]="10518"; ak[24]="10712";
        open("https://rct-d-p.astorm.com/clan/clanDetail.ct?sk=1&clan_key=322");
        for(int i=0;i<ak.length;i++){
        	$(".uid_add_clan_member_layer_btn").click();
            $(".ac_input", 2).setValue(ak[i]);
            System.out.println(ak[i]);
            $(".uid_add_clan_member_search_btn").click();
            $(".uid_add_clan_member_btn").click();
            $(".uid_ok_btn").click();
            refresh();
        }
    }	
	@AfterClass
	public void afterTest() {
		closeWebDriver();
	}
}
