package TestScenarios;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.*;

import java.util.concurrent.TimeUnit;


public  class TestBase {
    public static WebDriver driver;

    @BeforeSuite
    @Parameters({"SERVER_URL"})
    public void setupdriver(String URL)
    {
        //System.setProperty("webdriver.chrome.driver","D:\\Eclipse\\Eclipse Workspace\\kotb\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");
        WebDriverManager.chromedriver().setup();
        driver=  new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        //workorder management module
        driver.navigate().to(URL);

    }
    //@AfterSuite
    public void closedriver()
    {
        driver.quit();
    }


}
