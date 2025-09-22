package Pages;

import dev.failsafe.internal.util.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.concurrent.TimeUnit;

public class WorkOrderHomePage extends PageBase
{
    protected LoginPageWorkOrderManagement login;
    public String work_order_button_id="pt:homeMenu:1:CfgGovernrateGtd";
    public String work_order_TextField_id="//*[contains(@id,'pt:mr:') and contains(@id,':pt:it5::content')]";
    public String Search_text = "Search";
    public String navigation_right_menu ="pt:MenuITem";
    public String WorkList_text_button = "//td[normalize-space(text())='Work List']";


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

    public void navigate_to_worklist()
    {
        driver.findElement(By.id(navigation_right_menu)).click();
        driver.findElement(By.xpath(WorkList_text_button)).click();
    }
    public  void navigatetoWorkOrder ()
    {
        login=new LoginPageWorkOrderManagement(driver);
        login.loginfun("root","root1234");
        driver.findElement(By.id(work_order_button_id)).click();
    }

    public void Search_work_orderBYid(String work_order_id)
    {
        driver.findElement(By.xpath(work_order_TextField_id)).sendKeys(work_order_id);
        driver.findElement(By.linkText(Search_text)).click();
    }
    public void wait_till_schedule2() {
        long startTime = System.currentTimeMillis();
        long timeout = 60000; // 1 minute timeout

        while (System.currentTimeMillis() - startTime < timeout) {
            try {
                // Check if Scheduled appears
                if (driver.findElement(By.xpath("//span[text()='Scheduled']")).isDisplayed()) {
                    System.out.println("Scheduled element found!");
                    break; // Exit if found
                }
            } catch (NoSuchElementException e) {
                System.out.println("Scheduled not found, retrying...");
            }

            // Click Search
            try {
                driver.findElement(By.linkText("Search")).click();
                System.out.println("Clicked Search button.");
            } catch (NoSuchElementException e) {
                System.out.println("Search link not found.");
            }

            // Wait 2 seconds before retry
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

    }

    }
    public void wait_till_schedule ()
    {
        while (true) {
            try {
                // Try to find and check if the element is displayed
                if (driver.findElement(By.xpath("//span[text()='Scheduled']")).isDisplayed()) {
                    System.out.println("Scheduled element found!");
                    break; // Exit the loop if it's found
                }
            } catch (NoSuchElementException e) {
                // Element not found — it's okay, just retry
                System.out.println("Scheduled not found, retrying...");
            }

            // Click Search (assuming it's always visible)
            try {
                driver.findElement(By.linkText("Search")).click();
            } catch (NoSuchElementException e) {
                System.out.println("Search link not found.");
            }

            // Wait a bit before retrying
            try {
                Thread.sleep(2000); // 2 seconds
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // good practice
            }
        }
    }

}
