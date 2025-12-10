package Pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class WorkList
{

    public WebDriver driver;
    public WorkList(WebDriver driver)
    {
        this.driver=driver;
    }
    String closeformXpath="//*[contains(@id, 'close')]";
    String iframMyTask_task="//*[@id=\"dataForm::__af_Z_maskingframe\"]/iframe";
    //*[@id="dataForm::__af_Z_maskingframe"]/iframe
    String closecode_xpath="//*[contains(@id, 'soc8::content')]";
    String close_reason_xpath="//*[contains(@id,'of16')]";
    public void navigate_back_to_work_order()
    {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement menu = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[@id='pt:MenuITem']//a[contains(@class,'x166')]")
        ));
        menu.click();

        //driver.findElement(By.linkText("pt:MenuITem")).click();
        driver.findElement(By.xpath("//tr[@id='pt:i0t0:1:Items1']//td[text()='Work Orders']")).click();
    }
    public boolean check_number_of_WOs_after_search() throws InterruptedException {
        List<WebElement> rows = driver.findElements(By.xpath("//div[@role='grid' and contains(@class,'xx8')]//span[@role='row']"));
        if(rows.size()==0)
        {
            Thread.sleep(2000);
            check_number_of_WOs_after_search();
        }
        return  rows.size()==1;
    }
    public void start_order()
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
    public void Accept_Scheduled_order()
    {
        // driver.findElement(By.id("pt:homeMenu:4:CfgGovernrateGtd")).click();
        //this.Search_zone_tasks(Work_order_id);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        boolean isYesFound = false;
        while(!isYesFound)
        {
            try{
                driver.findElement(By.linkText("Accept Task")).click();
                WebElement yesButton = wait.until(
                        ExpectedConditions.elementToBeClickable(By.linkText("Yes"))
                );
                yesButton.click();
                isYesFound = true;
                driver.findElement(By.linkText("OK")).click();
                break;
            }catch (TimeoutException e) {
                System.out.println("'Yes' button not found yet, retrying...");
            }
        }


        /*driver.findElement(By.linkText("Accept Task")).click();
        System.out.println("da5alt");
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
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//*[contains(@id,'pt:mr:') and contains(@id,':pt:qryId1:val20::content')]")
        )).clear();
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//*[contains(@id,'pt:mr:') and contains(@id,':pt:qryId1:val20::content')]")
        )).sendKeys(Work_order_id);
        //driver.findElement(By.xpath("//*[contains(@id,'pt:mr:') and contains(@id,':pt:qryId1:val20::content')]")).sendKeys(Work_order_id);
        driver.findElement(By.linkText("Search")).click();
    }
    public void Search_my_tasks(String Work_order_id) throws InterruptedException {
        //driver.findElement(By.id("pt:mr:0:pt:q1:val20::content")).sendKeys(Work_order_id);
        WebElement text_field = driver.findElement(By.xpath("//*[contains(@id,'pt:mr:') and contains(@id,':pt:q1:val20::content')]"));
        String value = text_field.getAttribute("value");
        if (value != null && !value.isEmpty()) {
            text_field.clear(); // Clear the field
            text_field.sendKeys(Work_order_id);
        } else {
            text_field.sendKeys(Work_order_id);
        }
        Thread.sleep(2000);
        //driver.findElement(By.xpath("//*[contains(@id,'pt:mr:') and contains(@id,':pt:q1:val20::content')]"))
        driver.findElement(By.linkText("Search")).click();
        Thread.sleep(2000);
    }
    public void navigate_to_SYS_Admin()
    {
        driver.findElement(By.xpath("//img[@src='/WorkOrder/jheadstart/images/menu_modules.png']")).click();
        driver.findElement(By.xpath("//td[normalize-space(text())='System Administration']")).click();
    }
    public void refresh_form(String iframeUrl)
    {
        //driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
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
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        WebElement iframe = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//iframe[@title='BPM_INTEGRATION_API']")
        ));
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
        public void close_current_tab()
        {
            ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
            driver.switchTo().window(tabs.get(1));
            driver.close();
        }
        public void close_form() throws InterruptedException {
            Thread.sleep(1000);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            WebElement close = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//*[contains(@id, 'd2') and contains(@id, 'close')]")
            ));
            close.click();
        }
    public String getRequest_Type_WorkList() throws InterruptedException {
        return get_value_by_label("Request Type");
    }
    public String getTask_Type_WorkList() throws InterruptedException {
        System.out.println(get_value_by_label("Task Type"));
        return get_value_by_label("Task Type");
    }
    public String getWork_Spec_WorkList() throws InterruptedException {
        return get_value_by_label("Work Specification");
    }
    public String check_label_and_get_value_status() throws InterruptedException {
        return get_value_by_label("Status");
    }
    public String get_value_by_label(String label_name) throws InterruptedException {
        try
        {
            String xpath_input = String.format("//div[@role='grid']//label[normalize-space(text())='%s']/ancestor::td/following-sibling::td//span", label_name);
            WebElement input = driver.findElement(By.xpath(xpath_input));
            String value = input.getText();
            return value;
        }catch(org.openqa.selenium.NoSuchElementException e1)
        {
            String xpath_input = String.format("//div[@role='grid']//label[normalize-space(text())='%s']/ancestor::td/following-sibling::td", label_name);
            WebElement input = driver.findElement(By.xpath(xpath_input));
            String value = input.getText();
            return value;
        }
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
