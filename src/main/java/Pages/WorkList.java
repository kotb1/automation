package Pages;

import org.openqa.selenium.*;

import java.time.Duration;
import java.util.ArrayList;

public class WorkList
{

    public WebDriver driver;
    String closeformXpath="//*[contains(@id, 'close')]";
    String iframMyTask_task="//*[@id=\"dataForm::__af_Z_maskingframe\"]/iframe";
    //*[@id="dataForm::__af_Z_maskingframe"]/iframe
    String closecode_xpath="//*[contains(@id, 'soc8::content')]";
    String close_reason_xpath="//*[contains(@id,'of16')]";

    public WorkList(WebDriver driver)
    {
        this.driver=driver;
    }
    public void navigate_back_to_work_order()
    {
        driver.findElement(By.xpath("//*[@id=\"pt:MenuITem\"]/div/table/tbody/tr/td[2]/a")).click();
        driver.findElement(By.xpath("//*[@id=\"pt:i0t0:1:Items1\"]/td[2]")).click();
    }
    public void Start_order()
    {
        while(true) {
            try {
                if (driver.findElement(By.linkText("Start Task")).isDisplayed()) {
                    System.out.println("Start link found.");
                    driver.findElement(By.linkText("Start Task")).click();
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
    public void Start_as_confirm_visit()
    {
        while(true) {
            try {
                if (driver.findElement(By.linkText("Confirm Visit")).isDisplayed()) {
                    System.out.println("Start link found.");
                    driver.findElement(By.linkText("Confirm Visit")).click();
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
    public void Accept_Scheduled_order() throws InterruptedException {

        while(true) {
            try {
                if (driver.findElement(By.linkText("Accept Task")).isDisplayed()) {
                    System.out.println("Accept link found.");
                    driver.findElement(By.linkText("Accept Task")).click();
                    driver.findElement(By.linkText("Yes")).click();
                    driver.findElement(By.linkText("OK")).click();
                    break;
                }
            } catch (NoSuchElementException e) {
                System.out.println("Accept link not found.");
            }
            driver.findElement(By.linkText("Search")).click();
            try {
                Thread.sleep(2000); // 2 seconds
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // good practice
            }
        }







        // driver.findElement(By.id("pt:homeMenu:4:CfgGovernrateGtd")).click();
        //this.Search_zone_tasks(Work_order_id);
        /*Thread.sleep(2000); // 2 seconds

        driver.findElement(By.linkText("Accept Task")).click();
        Thread.sleep(2000); // 2 seconds
        driver.findElement(By.linkText("Yes")).click();
        driver.findElement(By.linkText("OK")).click();*/
    }
    public void Accept_Assigned_order(String Work_order_id)
    {
        //driver.findElement(By.id("pt:homeMenu:4:CfgGovernrateGtd")).click();
        driver.findElement(By.linkText("Accept Task")).click();
        driver.findElement(By.linkText("Yes")).click();
    }
    public void Search_zone_tasks(String Work_order_id)
    {
        driver.findElement(By.xpath("//*[contains(@id,'pt:mr:') and contains(@id,':pt:qryId1:val20::content')]")).clear();
        driver.findElement(By.xpath("//*[contains(@id,'pt:mr:') and contains(@id,':pt:qryId1:val20::content')]")).sendKeys(Work_order_id);
        driver.findElement(By.linkText("Search")).click();
    }
    public void Search_my_tasks(String Work_order_id)
    {
        //driver.findElement(By.id("pt:mr:0:pt:q1:val20::content")).sendKeys(Work_order_id);
        driver.findElement(By.xpath("//*[contains(@id,'pt:mr:') and contains(@id,':pt:q1:val20::content')]")).clear();
        driver.findElement(By.xpath("//*[contains(@id,'pt:mr:') and contains(@id,':pt:q1:val20::content')]")).sendKeys(Work_order_id);
        driver.findElement(By.linkText("Search")).click();
    }
    public void refresh_form(String iframeUrl)
    {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        boolean success = false;
        driver.get(iframeUrl);
        while (!success) {
            try {
                //System.out.println("da5alt ya5oya") ;
                WebElement body = driver.findElement(By.tagName("body"));
                String bodyText = body.getText().trim();

                if (bodyText.equalsIgnoreCase("Please Wait , There is work in Progress")) {
                    Thread.sleep(2000);
                    driver.get(iframeUrl);
                } else {
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
    public void navigate_to_WFM_tab()
    {
        //((JavascriptExecutor) driver).executeScript("window.open(arguments[0], '_blank');", FrameUrl);
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(0));
    }
    public void StartEnterprise()
    {
        while(true) {
            try {
                if (driver.findElement(By.linkText("start")).isDisplayed()) {
                    System.out.println("Start link found.");
                    driver.findElement(By.linkText("start")).click();
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
    public void CloseForm()
    {
        WebElement iframe = driver.findElement(By.xpath(iframMyTask_task));
        //Switch to the frame
        driver.switchTo().frame(iframe);
        driver.findElement(By.linkText("Close")).click();
    }
    public String returncloaseCode() throws InterruptedException {
        Thread.sleep(2000);

        WebElement closecode = driver.findElement(By.xpath(closecode_xpath));
        return  closecode.getText().toString();

    }
    public String returncloaseReason() throws InterruptedException {
        Thread.sleep(2000);
        WebElement closeReason = driver.findElement(By.xpath(close_reason_xpath));
        return  closeReason.getAttribute("title");

    }
}
