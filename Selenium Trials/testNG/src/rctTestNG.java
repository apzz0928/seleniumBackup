//import static org.testng.AssertJUnit.assertFalse;
//import static org.testng.AssertJUnit.assertTrue;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
//import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import static com.codeborne.selenide.Selenide.*;
//import static com.codeborne.selenide.Condition.*;
 
public class rctTestNG {
	public static WebDriver driver;
	public String URL, Node;
	protected ThreadLocal<RemoteWebDriver> threadDriver = null;
  	private static JavascriptExecutor js; //js����� ���� �߰�
 
	@Parameters("browser")
	@BeforeTest
	public void launchbrowser(String browser) throws MalformedURLException {
        js = (JavascriptExecutor) driver;
		String URL = "http://www.calculator.net/";
		//String URL = "https://rct-d-p.astorm.com/";
 
		if (browser.equalsIgnoreCase("chrome")) {
			System.out.println(" Executing on CHROME");
			DesiredCapabilities cap = DesiredCapabilities.chrome();
			cap.setBrowserName("chrome");
			String Node = "http://10.10.105.228:5555/wd/hub";
			driver = new RemoteWebDriver(new URL(Node), cap);
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
 
			// Launch website
			driver.navigate().to(URL);
			driver.manage().window().setSize(new Dimension(1000, 1000));
	  		//driver.manage().window().maximize();
		} else if (browser.equalsIgnoreCase("firefox")) {
			System.out.println(" Executing on FireFox");
			String Node = "http://10.10.105.228:5556/wd/hub";
			DesiredCapabilities cap = DesiredCapabilities.firefox();
			cap.setBrowserName("firefox");
 
			driver = new RemoteWebDriver(new URL(Node), cap);
			// Puts an Implicit wait, Will wait for 10 seconds before throwing
			// exception
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
 
			// Launch website
			driver.navigate().to(URL);
	  		driver.manage().window().setSize(new Dimension(1000, 1000));
			//driver.manage().window().maximize();
		} else {
			throw new IllegalArgumentException("The Browser Type is Undefined");
		}
	}
	
	
    //@Test
    public void A_Login() throws Exception {
    	//�α���
        //driver.get(baseUrl + "/login/form.ct");
        //Thread.sleep(1000);
        //�ش� �޼����� ������ Pass
        //assertTrue(driver.getPageSource().contains("User ID"));
        //�ش� �޼����� ������ Fail
        //assertFalse(driver.getPageSource().contains("������ �ּҰ� �߸� �ԷµǾ��ų�, �̹� ������ �������Դϴ�."));        
        System.out.println("�α��� ������ : Pass");
        Thread.sleep(1000);
        $("input[name=j_username]").setValue("apzz0928");
        $("input[name=j_password]").setValue("qordlf!@34");
        $(".ac_btn_text:eq(0)").click();
        js.executeScript("$('input[name=j_username]').prop('value', 'apzz0928')");
        js.executeScript("$('input[name=j_password]').prop('value', 'qordlf!@34')");
        js.executeScript("$('.ac_btn_text')[0].click();");
        /*
        driver.findElement(By.name("j_username")).clear();
        driver.findElement(By.name("j_username")).sendKeys("apzz0928");
        driver.findElement(By.name("j_password")).clear();
        driver.findElement(By.name("j_password")).sendKeys("qordlf!@34");
        driver.findElement(By.cssSelector("button.ac_btn_inner")).click();
        */
        System.out.println("�α��� : Pass");
        Thread.sleep(1000);        
    }
	
 
	@Test
	public void calculatepercent() {
		// Click on Math Calculators
		js.executeScript("var sel = document.createElement('SCRIPT');");
		js.executeScript("sel.setAttribute('src', 'https://code.jquery.com/jquery-1.12.4.js');");
		js.executeScript("document.querySelector('body').appendChild(sel);");
		$(".hhmat:eq(0)").click();
		//driver.findElement(By.xpath("//a[contains(text(),'Math')]")).click();
		// Click on Percent Calculators
		driver.findElement(
				By.xpath("//a[contains(text(),'Percentage Calculator')]"))
				.click();
		// Enter value 17 in the first number of the percent Calculator
		driver.findElement(By.id("cpar1")).sendKeys("17");
		// Enter value 35 in the second number of the percent Calculator
		driver.findElement(By.id("cpar2")).sendKeys("35");
 
		// Click Calculate Button
		driver.findElement(
				By.xpath("(//input[contains(@value,'Calculate')])[1]")).click();
		// Get the Result Text based on its xpath
		String result = driver.findElement(
				By.xpath(".//*[@id='content']/p[2]/font/b")).getText();
		// Print a Log In message to the screen
		System.out.println(" The Result is " + result);
		if (result.equals("5.95")) {
			System.out.println(" The Result is Pass");
		} else {
			System.out.println(" The Result is Fail");
		}
	}
 
	@AfterTest
	public void closeBrowser() throws InterruptedException {
		Thread.sleep(3000);
		driver.quit();
	}
}