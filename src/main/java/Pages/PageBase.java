package Pages;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

public class PageBase {

    public    WebDriver driver;
    public PageBase(WebDriver driver)
    {
        this.driver=driver;
        PageFactory.initElements(driver,this);
    }
    public void click(WebElement button)
    {
        button.click();
    }
    public void settxt(WebElement txt,String value)
    {
        txt.sendKeys(value);
    }
}
