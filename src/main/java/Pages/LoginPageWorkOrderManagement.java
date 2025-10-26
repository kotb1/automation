package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPageWorkOrderManagement extends  PageBase
{
    public LoginPageWorkOrderManagement(WebDriver driver)
    {
        super(driver);
    }

   // @FindBy(id = "pt:username::content")


    public void loginfun(String username, String Password)
    {
        WebElement usernametxt= driver.findElement(By.id("pt:username::content"));
        // @FindBy(id="pt:password::content")
        WebElement passwordtxt=driver.findElement(By.id("pt:password::content"));
        // @FindBy(id="pt:login")
        WebElement SubmitButton= driver.findElement(By.id("pt:login"));
        settxt(usernametxt,username);
        settxt(passwordtxt,Password);
        click(SubmitButton);

    }

}
