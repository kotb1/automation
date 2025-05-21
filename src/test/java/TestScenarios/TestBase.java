package TestScenarios;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import java.util.concurrent.TimeUnit;


public  class TestBase {
    public static WebDriver driver;

    @BeforeSuite
    public void setupdriver()
    {
        System.setProperty("webdriver.chrome.driver","D:\\Eclipse\\Eclipse Workspace\\kotb\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");
        driver=  new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
        //workorder management module
        driver.navigate().to("http://10.19.35.91:8003/WorkOrder/faces/UIShell");

    }
    //@AfterSuite
    public void closedriver()
    {
        driver.quit();
    }


}
