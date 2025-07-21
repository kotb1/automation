package Pages;

import org.openqa.selenium.*;

import java.time.Duration;
import java.util.ArrayList;

public class WorkList
{
    public WebDriver driver;
    public WorkList(WebDriver driver)
    {
        this.driver=driver;
    }
    public void Start_order()
    {
        while(true) {
            try {
                if (driver.findElement(By.linkText("start task")).isDisplayed()) {
                    System.out.println("Start link found.");
                    driver.findElement(By.linkText("start task")).click();
                    break;
                }
            } catch (NoSuchElementException e) {
                System.out.println("Start link not found.");
            }
            driver.findElement(By.linkText("Search")).click();
            try {
                Thread.sleep(2000); // 2 seconds
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // good practice
            }
        }
    }
    public void Accept_Scheduled_order()
    {
        // driver.findElement(By.id("pt:homeMenu:4:CfgGovernrateGtd")).click();
        //this.Search_zone_tasks(Work_order_id);
        driver.findElement(By.linkText("Accept Task")).click();
        driver.findElement(By.linkText("Yes")).click();
    }
    public void Accept_Assigned_order(String Work_order_id)
    {
        //driver.findElement(By.id("pt:homeMenu:4:CfgGovernrateGtd")).click();
        driver.findElement(By.linkText("Accept Task")).click();
        driver.findElement(By.linkText("Yes")).click();
    }
    public void Search_zone_tasks(String Work_order_id)
    {
        driver.findElement(By.id("pt:mr:0:pt:qryId1:val20::content")).sendKeys(Work_order_id);
        driver.findElement(By.linkText("Search")).click();
    }
    public void Search_my_tasks(String Work_order_id)
    {
        driver.findElement(By.id("pt:mr:0:pt:q1:val20::content")).sendKeys(Work_order_id);
        driver.findElement(By.linkText("Search")).click();
    }
    public void refresh_form(String iframeUrl)
    {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        boolean success = false;
        driver.get(iframeUrl);
        while (!success) {
            try {
                System.out.println("da5alt ya5oya") ;
                WebElement body = driver.findElement(By.tagName("body"));
                String bodyText = body.getText().trim();

                if (bodyText.equalsIgnoreCase("Please Wait , There is work in Progress")) {
                    Thread.sleep(2000);
                    driver.get(iframeUrl);
                } else {
                    success = true;
                    break;
                }
            } catch (Exception e) {
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
            }
        }
    }
    public String get_BPM_URL()
    {
        WebElement iframe = driver.findElement(By.xpath("//iframe[@title='BPM_INTEGRATION_API']"));
        String iframeUrl = iframe.getAttribute("src");
        return iframeUrl;
    }
    public void navigate_to_mytasks()
    {
        driver.findElement(By.linkText("My Tasks")).click();
    }
    public void navigate_to_zonetasks()
    {
        driver.findElement(By.linkText("Zone Tasks")).click();
    }

    public void navigate_To_BPM_Form(String FrameUrl)
    {
        ((JavascriptExecutor) driver).executeScript("window.open(arguments[0], '_blank');", FrameUrl);
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
    }
}
