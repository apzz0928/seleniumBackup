import java.net.URL;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.util.Date;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.codeborne.selenide.WebDriverRunner;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.WebDriverRunner.*;

import com.codeborne.selenide.testng.ScreenShooter;


public class test_herowarz {
	private static String baseUrl, hubUrl;
	private static String TestBrowser;
	
	Date now = new Date();
    String nowTime = now.toString();
    
	@Parameters("browser")
	@BeforeClass
	public void beforeTest(String browser) throws MalformedURLException {
		baseUrl = "http://www.d.p.herowarz.com";
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
  		} /*else if(browser.equals("edge")) {
  			TestBrowser = "edge";
  			cap = DesiredCapabilities.edge();
	        RemoteWebDriver driver = new RemoteWebDriver(new URL(urlToRemoteWD),cap);
	        WebDriverRunner.setWebDriver(driver);
	  		driver.manage().window().setSize(new Dimension(1600, 1400));
  		}*/
    }
	public static void js(String javaScriptSource) {
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
  	public static void a123(){
  		String parentwindow = getWebDriver().getWindowHandle();
  		Set<String> handles = getWebDriver().getWindowHandles();
  		for(String childwindow : handles ){
  			if(!childwindow.equals(parentwindow)){
  				switchTo().window(childwindow);
  				close();
  				switchTo().window(parentwindow);
  			}
  		}
  	}
  	public static void url(String URL) throws Exception {
  		HttpURLConnection huc = null;
  		huc = (HttpURLConnection)(new URL(URL).openConnection());
  		huc.setRequestMethod("HEAD");
  		huc.connect();
  		int respCode = huc.getResponseCode ();
  		if(respCode >= 400){
  	        System.out.println(URL +" is a broken link " + respCode);
  		} else {
  	        System.out.println(URL +" is a valid link " + respCode);
  		}
  	}
  	public void write(String board){
  		$(".ua_title").setValue(nowTime);
        js("$('.uid_smarteditor_area iframe').contents().find('iframe').contents().find('body').text('" + TestBrowser + " / " + nowTime + "')");
        $(".btn-blue").click();
        confirm("등록이 완료되었습니다.");
        System.out.println(TestBrowser + "Community " + board +" write Pass");
  	}
  	public void  writeImg(String board){
  		js("$('.uid_smarteditor_area iframe').contents().find('iframe').contents().find('body').text('" + TestBrowser + " / " + nowTime + "')");
        js("$('.uid_smarteditor_area iframe').contents().find('.se2_photo').click();");
        windowTitle("이미지 업로드 :: SmartEditor2");
        $("#uploadInputBox").setValue("C:\\Users\\Administrator\\Downloads\\black.jpg");
        $("#btn_confirm").click();
        windowTitle(" :: 액션중독! - 최강의군단");
        $("label", 0).click();
        $("label", 2).click();
        $(".ua_title").setValue(nowTime);
        $(".btn-blue").click();
        confirm("등록이 완료되었습니다.");
        System.out.println(TestBrowser + "Community " + board +" write Pass");
  	}
  	public void comment(String board){
  		$(".uid_comment_content").setValue(TestBrowser);
        $(".uid_comment_writesave").click();
        $(".uid_comment_like").click();
        $(".uid_comment_like").click();
        confirm("최고군은 한번만 할 수 있습니다.");
        $(".uid_comment_reply").click();
        $(".uid_comment_content", 1).setValue(nowTime);
        $(".uid_comment_writesave", 1).click();
        $(".uid_comment_like", 1).click();
        $(".uid_comment_like", 1).click();
        confirm("최고군은 한번만 할 수 있습니다.");
        $(".uid_comment_delete", 1).click();
        dismiss("댓글을 삭제 하시겠습니까?");
        $(".uid_comment_delete", 1).click();
        confirm("댓글을 삭제 하시겠습니까?");
        $(".uid_comment_delete").click();
        dismiss("댓글을 삭제 하시겠습니까?");
        $(".uid_comment_delete").click();
        confirm("댓글을 삭제 하시겠습니까?");
        System.out.println(TestBrowser + "Community " + board +" comment Pass");
  	}

	@Test(priority = 0)
	public void login() {
        open(baseUrl);
        js("");
        $(By.linkText("액션중독! - 최강의 군단")).click();
        $(".uid_login_login").waitUntil(appear, 5000);
        $(".uid_cookie_checkbox").click();
        $(".uid_test_btn_ok").click();
//        $(".uid_login_login").click();
//        dismiss("아이디를 입력해주세요");
        $(".uid_login_id").setValue("영권1232");
//        $(".uid_login_login").click();
//        dismiss("비밀번호를 입력해주세요.");
//        $(".uid_login_password").setValue("qordlf13");
//        $(".uid_login_login").click();
//        confirm("아이디 또는 비밀번호가 일치하지 않습니다.");
        $(".uid_login_password").setValue("qordlf12");
        $(".uid_login_login").click();
    }
	//@Test(priority = 1)
	public void recent() {
        $(".recent").hover();
        $(By.linkText("새소식")).click();
        $(".subject", 0).click();
        $(".btn-white").click();
        System.out.println(TestBrowser + "Recent Notice Pass");
        $(".l-sub-update", 0).click();
        $(".view-h-txt", 0).click();
        $(".btn-white").click();
        System.out.println(TestBrowser + "Recent Update Pass");
        $(".l-sub-secret", 0).click();
        $(".thumb", 1).click();
        $(".btn-white").click();
        System.out.println(TestBrowser + "Recent Secret Pass");
        $(".l-sub-pink", 0).click();
        $(".btn-white").click();
        $(".thumb", 0).click();
        $(".btn-white").click();
        System.out.println(TestBrowser + "Recent Pink Pass");
        $(".l-sub-cospre", 0).click();
        $(".thumb", 0).click();
        $(".btn-white").click();
        System.out.println(TestBrowser + "Recent cospre Pass");
        $(".l-sub-tv", 0).click();
        $(".subject", 0).click();
        $(".btn-white").click();
        System.out.println(TestBrowser + "Recent Tv Pass");
    }
	//@Test(priority = 2)
	public void guide() throws Exception {
        open(baseUrl + "/about/herowarz.hero");
        System.out.println(TestBrowser + "Guide about Pass");
        $(".character > a", 0).click();
        $(".item-rpg > a", 0).click();
        $(".btn-highlight").click();
        $(".btn-close").click();
        System.out.println(TestBrowser + "Guide character highlight Pass");
        $(By.linkText("스킬")).click();
        js("$('.list > ul > li > a')[0].click();");
        //$(".list > ul > li > a", 0).click();
        js("$('.mask')[2].click();");
        //$(".mask", 2).click();
        System.out.println(TestBrowser + "Guide character skill Pass");
        $(By.linkText("프로필")).click();
        $(".edge", 2).click();
        $(".edge", 3).click();
        $(".edge", 4).click();
        $(".btn-skill > span").click();
        $(".btn > a").click();
        System.out.println(TestBrowser + "Guide character profile Pass");
        $(By.linkText("소셜")).click();
        url("http://www.youtube.com/embed/k-FsurQIm-E");
        $(By.linkText("춤")).click();
        url("http://www.youtube.com/embed/SG3SZouMMs8");
        $(By.linkText("터치")).click();
        url("http://www.youtube.com/embed/S6oSDEX0COU");
        $(By.linkText("목마")).click();
        url("http://www.youtube.com/embed/ikEQTgelnG0");
        $(By.linkText("앉기")).click();
        url("http://www.youtube.com/embed/ReOIRmunGMg");
        $(By.linkText("하이파이브")).click();
        url("http://www.youtube.com/embed/qQ-oAkwixWQ");
        $(By.linkText("기쁨")).click();
        url("http://www.youtube.com/embed/s0r8Ob2jXgw");
        $(By.linkText("슬픔")).click();
        url("http://www.youtube.com/embed/DTmDEClwhL8");
        $(By.linkText("인사")).click();
        url("http://www.youtube.com/embed/1LAuHywpQd0");
        $(By.linkText("포옹")).click();
        url("http://www.youtube.com/embed/s91yeO73KFI");
        System.out.println(TestBrowser + "Guide character social Pass");
        $(By.linkText("주메뉴 열기")).click();
        $(By.linkText("여행자 지도")).click();
        System.out.println(TestBrowser + "Guide map Pass");
        $(By.linkText("군단 연대기")).click();
        $("#lAboutFooter").waitUntil(visible, 5000);
        System.out.println(TestBrowser + "Guide chronicle Pass");
        $(By.linkText("최강의군단")).click();
	}
	//@Test(priority = 3)
	public void community() {
		if(TestBrowser == "internetExplorer"){
			$(By.linkText("커뮤니티")).hover();
	        $(By.linkText("자유 게시판")).click();
	        $(".community > a", 0).click();
		} else {
			open(baseUrl + "/community/FreeBoard/list.hero");
		}

        js("window.scrollBy(0,999);");
        $(".btn-blue").click();
        $("#cateDepth1").selectOptionContainingText("수다");
        write("freeboard");
        $(".btn-white").click();
        $(".comment-num", 3).click();
        comment("board");
        $(".btn-white").click();
        $(".l-sub-manual ").click();
        js("window.scrollBy(0,999)");
        $(".btn-blue").click();
        $("#cateDepth1").selectOptionContainingText("PVE 공략");
        $("#cateDepth2").selectOptionContainingText("톰");
        write("manual");
        comment("manual");
        $(".btn-white").click();
        /*
        $(".l-sub-boast").click();
        js("window.scrollBy(0,1999);");
        $(".btn-blue").click();
		open(baseUrl + "/community/write.hero?code=GetItem");
        writeImg("GetItem");
        comment("GetItem");
        $(".btn-white").click();
        $(".l-sub-collection").click();
        js("window.scrollBy(0,1999);");
        $(".btn-blue").click();
        writeImg("collection");
        comment("collection");
        $(".btn-white").click();
        $(".l-sub-fan").click();
        js("window.scrollBy(0,1999);");
        $(".btn-blue").click();
        writeImg("fan");
        comment("fan");
        $(".btn-white").click();
        */
	}
	//@Test(priority = 4)
	public void ranking() {
		if(TestBrowser == "internetExplorer"){
			$(By.linkText("랭킹")).hover();
			$(By.linkText("군단 랭킹")).click();
		} else {
			open(baseUrl + "/ranking/corpsRanking.hero");
		}
		for(int i=0;i<2;++i){
			$("#keyword").setValue("영권1232화란");
			$(".btn-srch").click();
			if(i==0){
				$(".ico-server-hyperion").click();
			} else if (i==1){
		        System.out.println(TestBrowser + " Ranking corps Pass");
		        $(".l-sub-character").click();
			}
		}
		for(int i=0;i<2;++i){
			$(By.linkText("통합능력치")).click();
			$("#keyword").setValue("영권1232화란");
			$(".btn-srch").click();
			$(By.linkText("전투력")).click();
			$("#keyword").setValue("영권1232화란");
			$(".btn-srch").click();
			$(By.linkText("생존력")).click();
			$("#keyword").setValue("영권1232화란");
			$(".btn-srch").click();
			if(i==0){
				$(".ico-server-hyperion").click();
			} else if (i==1){
		        System.out.println(TestBrowser + " Ranking character Pass");
		        $(".l-sub-pvp").click();				
			}
		}
        for(int i=0;i<2;++i){
    		$(By.linkText("시즌랭킹")).click();
            $("#keyword").setValue("영권1232화란");
    		$(".btn-srch").click();
    		$(By.linkText("주간랭킹")).click();
    		$("#keyword").setValue("영권1232화란");
    		$(".btn-srch").click();
    		$(By.linkText("월간랭킹")).click();
    		$("#keyword").setValue("영권1232화란");
    		$(".btn-srch").click();
    		if(i==0){
    			$(".ico-server-hyperion").click();
    		} else if (i==1){
    	        System.out.println(TestBrowser + " Ranking mflMaster Pass");
    	        $(".l-sub-pvp-all").click();
    		}
        }
        for(int i=0;i<2;++i){
    		$("#keyword").setValue("영권1232화란");
    		$(".btn-srch").click();
    		if (i==0){
    			$(".ico-server-hyperion").click();
    		} else if (i==1){
    			System.out.println(TestBrowser + " Ranking mfl Pass");
    	        $(".l-sub-liberation").click();
    		}
        }
		int a = 0;
		for(int i=1;i<5;++i){
			$(By.linkText(i + "인")).click();
			$("#keyword").setValue("영권1232화란");
			$(".btn-search").click();
			if(i==4 && a==0){
				$(".ico-server-hyperion").click();
				$("#keyword").setValue("영권1232화란");
				$(".btn-search").click();
				i = 1;
				a = 1;
			} else if (i==4 && a==1) {
				System.out.println(TestBrowser + " Ranking libaration Pass");
				$(".l-sub-backdraft").click();
			}
		}
		for(int i=0;i<2;++i){
			$("#keyword").setValue("영권1232화란");
			$(".btn-search").click();
			if(i==0){
				$(".ico-server-hyperion").click();
			} else if (i==1) {
				System.out.println(TestBrowser + " Ranking backdraft Pass");
				$(".l-sub-pantheon-time").click();
			}
		}
		for(int i=0;i<6;){
			switch(i){
				case 0: 
					$("#uid_stage_select").selectOptionContainingText("사마귀 부화장");
					break;
				case 1: 
					$("#uid_stage_select").selectOptionContainingText("바리데기 침실");
					break;
				case 2:
					$("#uid_stage_select").selectOptionContainingText("음속의 공간");
					break;
				case 3:
					$("#uid_stage_select").selectOptionContainingText("이글거리는 주차장");
					break;
				case 4:
					$("#uid_stage_select").selectOptionContainingText("비트 오페라하우스");
					break;
			}
			if(i<5){
				for(int z=1;z<5;z++){
					$(By.linkText(z + "인")).click();
					$("#keyword").setValue("영권1232화란");
					$(".btn-search").click();
				}				
			}
			i++;
			if(i == 5 && a == 1){
				$(".ico-server-hyperion").click();
				$("#uid_stage_select").selectOptionContainingText("사마귀 부화장");
				i = 0;
				a = 2;
			} else if (i == 5 && a == 2){
				System.out.println(TestBrowser + " Ranking pantheonTime Pass");
				$(".l-sub-raid").click();
			}
		}
		for(int i=0;i<2;++i){
			$("#keyword").setValue("영권1232화란");
			$(".btn-search").click();
			if(i==0){
				$(".ico-server-hyperion").click();
			} else if (i==1) {
				System.out.println(TestBrowser + " Ranking raid Pass");
				$(".l-sub-try-raid").click();
			}
		}
		for(int i=0;i<2;++i){
			$("#keyword").setValue("영권1232화란");
			$(".btn-search").click();
			if(i==0){
				$(".ico-server-hyperion").click();
			} else if (i==1) {
				System.out.println(TestBrowser + " Ranking try raid Pass");
			}
		}
	}
	//@Test(priority = 5)
	public void download() throws Exception {
		if(TestBrowser == "internetExplorer"){
			$(By.linkText("자료실")).hover();
			$(By.linkText("게임다운로드")).click();
		} else {
			open(baseUrl + "/download/game.hero");
		}
		url("http://download.herowarz.com/live/MCSetup.exe");
		System.out.println(TestBrowser + " download client Pass");
		$(".btn-download-game-pc").click();
		confirm("PC방에서만 다운로드가 가능합니다.");
		System.out.println(TestBrowser + " download pcroom client Pass");		
		url("http://download.herowarz.com/live/MCSetup_Chrome.exe");
		System.out.println(TestBrowser + " download chrome install Pass");
		url("http://static.herowarz.com/common/images/download/driver_nvidia.png");
		url("http://www.nvidia.co.kr/Download/index.aspx?lang=kr");
		System.out.println(TestBrowser + " download nvidia Pass");
		url("http://static.herowarz.com/common/images/download/driver_ati.png");
		url("http://support.amd.com/ko-kr/download");
		System.out.println(TestBrowser + " download radeon Pass");
		url("http://static.herowarz.com/common/images/download/driver_intel.png");
		url("https://downloadcenter.intel.com/ko/");
		System.out.println(TestBrowser + " download intel Pass");
		url("http://static.herowarz.com/common/images/download/driver_directx.png");
		url("http://www.microsoft.com/ko-kr/download/details.aspx?id=35");
		System.out.println(TestBrowser + " download directX Pass");
		$(".l-sub-movie").click();
		url("http://static.herowarz.com/editor/upload/2015/39/201509220807372_1st%20anniversary%20thanks.zip");
		comment("movie");
		js("window.scrollBy(0,-500);");
		js("window.scrollBy(0,-500);");
		js("window.scrollBy(0,-500);");
		$(".l-sub-gallery").click();
		$(".gallery-h").waitUntil(visible, 5000);
		$(".gallery-item", 0).click();
		url("http://static.d.p.herowarz.com/editor/upload/2016/40/B-d72478b7d0444cbdb1a973ac4062b884.png");
		$(".ico-rolling-rig").click();
		$(".ico-rolling-left").click();
		$(".btn-white").click();
		$(".gallery-h").waitUntil(visible, 5000);
		System.out.println(TestBrowser + " download gallery Pass");
		$(".l-sub-wallpaper").click();
		$(".gallery-h").waitUntil(visible, 5000);
		$(".gallery-item", 0).click();
		url("http://static.d.p.herowarz.com/editor/upload/2017/25/B-04fb00cf344d4cca972dafcd4871e4d1.jpg");
		url("http://static.d.p.herowarz.com/editor/upload/2017/25/B-dafd6c04e9694277b8f0dcae0e5e4d3c.jpg");
		$(".ico-rolling-rig").click();
		$(".ico-rolling-left").click();
		$(".btn-white").click();
		System.out.println(TestBrowser + " download wallpaper Pass");
		$(".l-sub-kit").click();
		$(".ir-txt-download").waitUntil(visible, 5000);
		url("http://static.d.p.herowarz.com/common/images/download/fankit_banner_20161221.jpg");
		url("http://static.d.p.herowarz.com/common/file/download/fankit/Herowarz_MFL_SignalMusic_2016.12.21.zip");
		System.out.println(TestBrowser + " download kit MFL BGM Pass");
		url("http://static.d.p.herowarz.com/common/images/download/fankit_banner_20141029.jpg");
		url("http://static.d.p.herowarz.com/filebox/herowarz_honeybook_v.1.1.zip");
		System.out.println(TestBrowser + " download kit honeyBook Pass");
		url("http://static.d.p.herowarz.com/common/images/download/fankit_banner_20141119.jpg");
		url("http://static.d.p.herowarz.com/files/fankit/Kit_201411.zip");
		System.out.println(TestBrowser + " download fan kit honeyBook Pass");
		url("http://static.d.p.herowarz.com/common/images/download/fankit_banner_2013.11.22.01.jpg");
		url("http://static.d.p.herowarz.com/files/fankit/Herowarz_fankit_2013.11.22_LBT.zip");
		System.out.println(TestBrowser + " download kit honeyBook Pass");
	}
	//@Test(priority = 6)
	public void cs() {
		if(TestBrowser == "internetExplorer"){
			$(By.linkText("고객센터")).hover();
			$(By.linkText("FAQ")).click();	
		} else {
			open(baseUrl + "/cs/home.hero");
		}
		$(".faq-tab-all").click();
		$(".q-subject", 0).click();
		$(".btn-white").click();
		$(".faq-tab-install").click();
		$(".q-subject", 0).click();
		$(".btn-white").click();
		$(By.linkText("게임이용")).click();
		$(".q-subject", 0).click();
		$(".btn-white").click();
		$(By.linkText("운영관련")).click();
		$(".q-subject", 0).click();
		$(".btn-white").click();
		$(By.linkText("계정/개인정보")).click();
		$(".q-subject", 0).click();
		$(".btn-white").click();
		$(By.linkText("결제관련")).click();
		System.out.println(TestBrowser + " cs FAQ Pass");
		$(".l-sub-inquiry").click();
		$(".h-cs").waitUntil(visible, 5000);
		$(By.linkText("환불 신청 문의")).click();
		js("window.scrollBy(0,9999);");
		$(".btn-white").click();
		$(".btn-blue").click();
		$(".h-help-txt").waitUntil(visible, 5000);
		$(".uid_refund_policy_layer_show").click();
		$(".uid_test_btn_ok").click();
		$(".uid_inquiry_submit").click();
		confirm("문의 내용에 고객센터로 문의하실 환불 신청 내용을 작성해 주세요.");
		$("#inquiryTitle").setValue(TestBrowser);
		$("#inquiryContent").setValue(nowTime);
		$(".uid_inquiry_submit").click();
		confirm("문의가 등록 되었습니다.");
		$(".h-txt-utilization").waitUntil(visible, 5000);
		js("window.scrollBy(0,-500);");
		js("window.scrollBy(0,-500);");
		js("window.scrollBy(0,-500);");
		$(".next").click();
		$(".h-txt-utilization").waitUntil(visible, 5000);
		$(".prev").click();
		$(".h-txt-utilization").waitUntil(visible, 5000);
		System.out.println(TestBrowser + " cs inquiry Pass");
		$(".l-sub-legal").click();
		$(".h-txt").waitUntil(visible, 5000);
		$(By.linkText("최강의군단 운영정책")).click();
		$(".h-txt").waitUntil(visible, 5000);
		$(By.linkText("캐시 이용약관")).click();
		$(".h-txt").waitUntil(visible, 5000);
		$(By.linkText("개인정보 처리방침")).click();
		$(".h-txt").waitUntil(visible, 5000);
		$(By.linkText("청소년 보호정책")).click();
		$(".h-txt").waitUntil(visible, 5000);
		System.out.println(TestBrowser + " cs policy Pass");
		open(baseUrl);
	}
	@Test(priority = 7)
	public void myPage() {
		$(".btn-mypage").waitUntil(visible, 5000).click();
		$(".h-txt-myinfo").waitUntil(visible, 5000);
		$(".btn", 2).click();
		$(".uid_main_serversetting_select").click();
		System.out.println(TestBrowser + " myPage serverChange Pass");
		if(TestBrowser == "internetExplorer"){
			
		} else {
			$(".btn", 3).click();
			windowTitle("액션중독! - 최강의 군단");
			int charValue = (int) (Math.random() * 5 + 1);
			if (charValue == 1){
				$(By.linkText("화란")).click();				
			} else if (charValue == 2){
				$(By.linkText("나그네")).click();
			} else if (charValue == 3){
				$(By.linkText("톰")).click();
			} else if (charValue == 4){
				$(By.linkText("오드리")).click();
			} else if (charValue == 5){
				$(By.linkText("로테")).click();
			}
			$(".uid_character_select").click();
			windowTitle(" :: 액션중독! - 최강의군단");
			getWebDriver().switchTo().defaultContent();
//			$(".btn", 3).click();
//			windowTitle("액션중독! - 최강의 군단");
//			$(By.linkText("나그네")).click();
//			$(".uid_character_select").click();
//			windowTitle(" :: 액션중독! - 최강의군단");
//			getWebDriver().switchTo().defaultContent();
			System.out.println(TestBrowser + " myPage characterChange Pass");
		}
//		$(".l-sub-jam").click();
//		$(".txt-has-jam").waitUntil(visible, 5000);
//		$("#fromDate").click();
//		$(".dp_caption").click();
//		$(".dp_previous").click();
//		$(".dp_previous").click();
//		$(".dp_month_0").click();
//		$("td", 5).click();
//		$("#toDate").click();
//		$(".dp_today", 1).click();
//		$(".btn-small").click();
//		$(".txt-has-jam").waitUntil(visible, 5000);
//		$(".tab-date", 0).click();
//		js("window.scrollBy(0,999);");
//		$(".next").click();
//		js("window.scrollBy(0,999);");
//		$(".prev").click();
//		$(".tab-date", 1).click();
//		js("window.scrollBy(0,999);");
//		$(".next").click();
//		js("window.scrollBy(0,999);");
//		$(".prev").click();
//		$(".tab-date", 2).click();
//		js("window.scrollBy(0,999);");
//		$(".next").click();
//		js("window.scrollBy(0,999);");
//		$(".prev").click();
//		System.out.println(TestBrowser + " myPage jam Pass");
//		$(".l-sub-coupon").click();
//		$("#btnRegCoupon").click();
//		confirm("쿠폰 번호를 정확하게 입력해주세요.");
//		$(".coupon-name").click();
//		$(".cancel-btn").click();
//		$(".live-coupon-btn").click();
//		$(".cancel-btn").click();
//		$(".tab", 1).click();
//		$(".h-help-txt").waitUntil(visible, 5000);
//		$(".tab", 0).click();
//		$(".h-help-txt").waitUntil(visible, 5000);
//		System.out.println(TestBrowser + " myPage coupon Pass");
		$(".l-sub-info").click();
		$(".h-txt-confirm-password").waitUntil(visible, 5000);
		$(".uid_submit_next_page").click();
		confirm("비밀번호을 입력해 주세요.");
		$("#label-pw").setValue("qordlf12");
		$(".uid_submit_next_page").click();
		$(".chk-input").click();
		if (TestBrowser == "internetExplorer"){
			
		} else {
			$(".uid_find_zipcode").click();
			windowTitle("액션중독! - 최강의 군단");
			$("#label-zip").setValue("천호동");
			$(".uid_zipcode_search").click();
			$("a", 2).click();
			windowTitle(" :: 액션중독! - 최강의군단");
			getWebDriver().switchTo().defaultContent();
		}
		$(".uid_find_zipcode").click();
		windowTitle("액션중독! - 최강의 군단");
		$("#label-zip").setValue("천호동");
		$(".uid_zipcode_search").click();
		$("a", 2).click();
		windowTitle(" :: 액션중독! - 최강의군단");
		getWebDriver().switchTo().defaultContent();
		$("#label-address2").setValue("123456");
		$(".uid_account_edit_submit").click();
		confirm("개인정보 수정이 완료되었습니다.");
		$(".l-sub-info").click();
		$("#label-pw").setValue("qordlf12");
		$(".uid_submit_next_page").click();
		$(".tab", 1).click();
		$("#label-cpw").setValue("qordlf12");
		$("#label-pw1").setValue("qordlf12");
		$("#label-pw2").setValue("qordlf12");
		$(".uid_change_password_submit").click();
		confirm("비밀번호 변경이 완료되었습니다. 이전 페이지로 이동합니다.");
		$(".l-sub-info").click();
		$("#label-pw").setValue("qordlf12");
		$(".uid_submit_next_page").click();
		$(".tab", 3).click();
		$(".h-txt-refer-myinfo").waitUntil(visible, 5000);
	}
	@AfterClass
	public void afterTest() {
		closeWebDriver();
	}
}