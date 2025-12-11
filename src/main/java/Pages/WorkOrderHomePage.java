package Pages;

import dev.failsafe.internal.util.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class WorkOrderHomePage extends PageBase
{
    protected LoginPageWorkOrderManagement login;
    public String work_order_button_id="pt:homeMenu:1:CfgGovernrateGtd";
    public String work_order_TextField_id="//*[contains(@id,'pt:mr:') and contains(@id,':pt:it5::content')]";
    public String Search_text = "Search";
    public String navigation_right_menu ="pt:MenuITem";
    public String WorkList_text_button = "//*[@id=\"pt:homeMenu:4:CfgGovernrateGtd\"]";
    public String closed_work_order_tab_xpath="//a[text()='Closed Work Orders']";
    public String opened_work_order_tab_xpath="//a[text()='Opened Work Orders']";
    public String opened_work_order_home_page_xpath="//span[normalize-space(text())='Work Order >']";


    public WorkOrderHomePage(WebDriver driver,String username, String password) {
        super(driver);
        navigatetoWorkOrder(username,password);

    }
    public void navigatetoWOPage()
    {
        driver.findElement(By.id(work_order_button_id)).click();

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
    public  void navigatetoWorkOrder (String username, String password)
    {
        login=new LoginPageWorkOrderManagement(driver);
        login.loginfun(username,password);
        driver.findElement(By.id(work_order_button_id)).click();
        //explicit 3ala dieh
        //WebDriverWait w =new WebDriverWait(driver,5);
        //w.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("span.promoInfo")));
    }

    public void Search_work_orderBYid(String work_order_id)
    {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        WebElement work_order_texfield = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath(work_order_TextField_id)
        ));
        work_order_texfield.sendKeys(work_order_id);

        //driver.findElement(By.xpath(work_order_TextField_id));
        driver.findElement(By.linkText(Search_text)).click();
    }
    public void Search_work_orderBYid_closed_workOrder_tab(String work_order_id)
    {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement work_order_texfield = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//input[contains(@id, 'it19::content') and @type='text']")
        ));
        work_order_texfield.sendKeys(work_order_id);

        //driver.findElement(By.xpath(work_order_TextField_id));
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
    public void  wait_till_schedule ()
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
    public boolean navigate_to_closed_work_orders_tab()
    {
        if(driver.findElement(By.xpath(closed_work_order_tab_xpath)).isDisplayed())
        {
            driver.findElement(By.xpath(closed_work_order_tab_xpath)).click();
            return true;
        }
        else return false;
    }
    public boolean navigate_to_opened_work_orders_tab()
    {
        if(driver.findElement(By.xpath(opened_work_order_tab_xpath)).isDisplayed())
        {
            driver.findElement(By.xpath(opened_work_order_tab_xpath)).click();
            return true;
        }
        else return false;
    }
    public boolean navigate_to_work_order_home_page()
    {
        if(driver.findElement(By.xpath(opened_work_order_home_page_xpath)).isDisplayed())
        {
            driver.findElement(By.xpath(opened_work_order_home_page_xpath)).click();
            return true;
        }
        else return false;
    }
    public String getRequest_Type_WorkOrderPage() throws InterruptedException {
        return get_value_by_label("Request Type");
    }
    public String getTask_Type_WorkOrderPage() throws InterruptedException {
        System.out.println(get_value_by_label("Task Type"));
        return get_value_by_label("Task Type");
    }
    public String getWork_Spec_WorkOrderPage() throws InterruptedException {
        return get_value_by_label("Work Spec");
    }
    public String get_value_by_label(String label_name) throws InterruptedException {
        String xpath_input = String.format("//label[normalize-space(text())='%s']/ancestor::td/following-sibling::td//span", label_name);
        WebElement input = driver.findElement(By.xpath(xpath_input));
        String value = input.getText();
        return value;
    }
    public void navigate_to_SYS_Admin()
    {
        driver.findElement(By.xpath("//img[@src='/WorkOrder/jheadstart/images/menu_modules.png']")).click();
        driver.findElement(By.xpath("//td[normalize-space(text())='System Administration']")).click();
    }

}
