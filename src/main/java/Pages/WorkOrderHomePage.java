package Pages;

import dev.failsafe.internal.util.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.concurrent.TimeUnit;

public class WorkOrderHomePage extends PageBase
{
    protected LoginPageWorkOrderManagement login;

    public WorkOrderHomePage(WebDriver driver) {
        super(driver);
        navigatetoWorkOrder();

    }
   // @FindBy(id="pt:homeMenu:1:pt_li1")
  //  WebElement WorkOrderPage=driver.findElement(By.id("pt:homeMenu:1:CfgGovernrateGtd"));
  //public WebElement logeduser= driver.findElement(new By.ByLinkText("root"));
  //  public WebElement NOROWSFOUND=driver.findElement(By.className("xb7 p_AFSelected"));
 //   WebElement WorkOrdertx=driver.findElement(By.id("pt:mr:0:pt:it5::content"));
 //   WebElement search=driver.findElement(By.linkText("Search"));


    public  void navigatetoWorkOrder ()
    {
        login=new LoginPageWorkOrderManagement(driver);
        login.loginfun("root","root1234");

    }
}
