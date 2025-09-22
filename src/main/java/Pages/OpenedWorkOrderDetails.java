package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class OpenedWorkOrderDetails extends PageBase{
    public OpenedWorkOrderDetails(WebDriver driver) {
        super(driver);
    }


    //WebElement workOrderId =driver.findElement(By.xpath("//*[contains(@id,'pt:mr:') and contains(@id,':pt:lv2:0::of9')]"));
    WebElement customersegment=driver.findElement(By.cssSelector ("span[title='Priority']"));
    String AssignementTablink="Assignments";
    String EndTAskxpath="//img[@alt='End Task']";
    String ForceCloseList="//select[contains(@id,'soc16::content')]";
    String Force_close_statusName="//select[contains(@id,'soc17::content')]";
    String Force_close_Ok_button="OK";
    String WorkOrder_Status="//*[contains(@id, 'WfWorkOrder5WoStatus')]";
    String ReloadButton="Reload";



    public void forcecloseWOwithSuccess(String close_reason) throws InterruptedException {
        WebElement assignmentTab=driver.findElement(By.linkText(AssignementTablink));
        assignmentTab.click();
        driver.findElement(By.xpath(EndTAskxpath)).click();
        Thread.sleep(500);
        WebElement CloseCodedropDownList= driver.findElement(By.xpath(ForceCloseList));
        Select selectSuccess= new Select(CloseCodedropDownList);
        selectSuccess.selectByVisibleText("Success");
        Thread.sleep(300);
        WebElement StatusNamedropdownlist=driver.findElement(By.xpath(Force_close_statusName));
        Select StatusName= new Select(StatusNamedropdownlist);
        StatusName.selectByVisibleText(close_reason);
        WebElement OkButton=driver.findElement(By.linkText(Force_close_Ok_button));
        OkButton.click();
    }
    public String returncustomersegment()
    {
        return customersegment.getText().toString();
    }
    public void NavigateToWorkOrderDetails(String work_order_id)
    {
        WebElement workOrderId = driver.findElement(By.xpath("//span[normalize-space(text())='" + work_order_id + "']"));
        workOrderId.click();

    }
    public void forcecloseWOwithFail2(String close_reason) throws InterruptedException {

        WebElement assignmentTab=driver.findElement(By.linkText(AssignementTablink));
        assignmentTab.click();
        driver.findElement(By.xpath(EndTAskxpath)).click();
        WebElement firstDropdown = driver.findElement(By.xpath("//select[contains(@id,'soc16::content')]"));
        Select selectFirst = new Select(firstDropdown);

        // --- Case 1: Select Success ---
        selectFirst.selectByVisibleText("Success");
        Thread.sleep(125); // wait for reasons list to load
        WebElement successDropdown = driver.findElement(By.xpath("//select[contains(@id,'soc17::content')]"));
        Select successSelect = new Select(successDropdown);

        System.out.println("Success reasons:");
        for (WebElement option : successSelect.getOptions()) {
            System.out.println(" - " + option.getText());
        }

        // --- Case 2: Select Fail ---
        selectFirst.selectByVisibleText("Fail");
        Thread.sleep(125); // wait for reasons list to load
        WebElement failDropdown = driver.findElement(By.xpath("//select[contains(@id,'soc17::content')]"));
        Select failSelect = new Select(failDropdown);

        System.out.println("\nFail reasons:");
        for (WebElement option : failSelect.getOptions()) {
            System.out.println(" - " + option.getText());
        }

    }
    public void printAllIframes(WebDriver driver) {
        List<WebElement> iframes = driver.findElements(By.tagName("iframe"));
        System.out.println("Total iframes found: " + iframes.size());

        for (int i = 0; i < iframes.size(); i++) {
            WebElement frame = iframes.get(i);
            String frameId = frame.getAttribute("id");
            String frameName = frame.getAttribute("name");
            String frameClass = frame.getAttribute("class");

            System.out.println("\n--- Iframe " + i + " ---");
            System.out.println("ID: " + frameId + ", Name: " + frameName + ", Class: " + frameClass);

            // Switch into the iframe
            driver.switchTo().frame(frame);

            // Find selects inside this iframe
            List<WebElement> selects = driver.findElements(By.tagName("select"));
            System.out.println(" Select elements found: " + selects.size());

            for (WebElement selectEl : selects) {
                String selId = selectEl.getAttribute("id");
                String selName = selectEl.getAttribute("name");
                System.out.println("   Select ID: " + selId + ", Name: " + selName);

                try {
                    Select select = new Select(selectEl);
                    List<WebElement> options = select.getOptions();
                    System.out.println("   Options count: " + options.size());
                    for (WebElement opt : options) {
                        String text = opt.getText().trim();
                        if (text.isEmpty()) {
                            text = opt.getAttribute("title");
                        }
                        System.out.println("     - " + text);
                    }
                } catch (Exception e) {
                    System.out.println("   (Not a standard dropdown)");
                }
            }

            // You can add more types if needed:
            List<WebElement> inputs = driver.findElements(By.tagName("input"));
            System.out.println(" Input elements found: " + inputs.size());

            // Always switch back before moving to next iframe
            driver.switchTo().defaultContent();
        }
    }
    public void forcecloseWOwithFail(String close_reason) throws InterruptedException {
        WebElement assignmentTab=driver.findElement(By.linkText(AssignementTablink));
        assignmentTab.click();
        driver.findElement(By.xpath(EndTAskxpath)).click();
        Thread.sleep(500);
        WebElement CloseCodedropDownList= driver.findElement(By.xpath(ForceCloseList));
        Select selectSuccess= new Select(CloseCodedropDownList);
        selectSuccess.selectByVisibleText("Fail");
        Thread.sleep(300);
        WebElement StatusNamedropdownlist=driver.findElement(By.xpath(Force_close_statusName));
        Select StatusName= new Select(StatusNamedropdownlist);
        StatusName.selectByVisibleText(close_reason);
        WebElement OkButton=driver.findElement(By.linkText(Force_close_Ok_button));
        OkButton.click();
    }
    public String getWOStatus()
    {
        WebElement WOStatus = driver.findElement(By.xpath(WorkOrder_Status));
        return WOStatus.getText().toString();
    }
    public void reload()
    {
        WebElement reloadButton=driver.findElement(By.linkText(ReloadButton));
        reloadButton.click();
    }
}
