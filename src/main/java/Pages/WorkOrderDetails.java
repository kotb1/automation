package Pages;

import BPM.FibGPONLesLinePro;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.sql.SQLOutput;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class WorkOrderDetails extends PageBase{
    public WorkOrderDetails(WebDriver driver) {
        super(driver);
    }


    //WebElement workOrderId =driver.findElement(By.xpath("//*[contains(@id,'pt:mr:') and contains(@id,':pt:lv2:0::of9')]"));
    //WebElement customersegment=driver.findElement(By.cssSelector ("span[title='Priority']"));
    String AssignementTablink="Assignments";
    String EndTAskxpath="//img[@alt='End Task']";
    String ForceCloseList="//select[contains(@id,'soc16::content')]";
    String Force_close_statusName="//select[contains(@id,'soc17::content')]";
    String Force_close_Ok_button="OK";
    String WorkOrder_Status="//*[contains(@id, 'WfWorkOrder5WoStatus')]";
    String ReloadButton="Reload";

    public boolean check_force_close_button()
    {
        WebElement assignmentTab=driver.findElement(By.linkText(AssignementTablink));
        assignmentTab.click();
        try
        {
            driver.findElement(By.xpath(EndTAskxpath)).click();
            return true;
        }catch (Exception e1)
        {
            return false;
        }
    }

    public boolean forcecloseWOwithSuccess(String close_reason) throws InterruptedException {
        try
        {
            WebElement assignmentTab=driver.findElement(By.linkText(AssignementTablink));
            assignmentTab.click();
            try
            {
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
                //System.out.println("ana hena aho3");
                return true;
            }catch (NoSuchElementException e1)
            {
                return false;
            }
        }catch (NoSuchElementException | ElementClickInterceptedException e3)
        {
            try
            {
                WebElement CloseCodedropDownList= driver.findElement(By.xpath(ForceCloseList));
                Select selectSuccess= new Select(CloseCodedropDownList);
                selectSuccess.selectByVisibleText("Success");
                Thread.sleep(800);
                WebElement StatusNamedropdownlist=driver.findElement(By.xpath(Force_close_statusName));
                Select StatusName= new Select(StatusNamedropdownlist);
                StatusName.selectByVisibleText(close_reason);
                WebElement OkButton=driver.findElement(By.linkText(Force_close_Ok_button));
                OkButton.click();
                //System.out.println("ana hena aho");
                Thread.sleep(5000);
                WebElement OkButton2=driver.findElement(By.linkText(Force_close_Ok_button));

                if(OkButton2.isDisplayed())
                {
                    OkButton2.click();
                    //Thread.sleep(3000);
                    //System.out.println("ana hena aho2");
                    return true;
                }
                else return false;
            }catch (NoSuchElementException e2)
            {
                return false;
            }
        }
    }
//    public String returncustomersegment()
//    {
//        return customersegment.getText().toString();
//    }
    public boolean NavigateToWorkOrderDetails(String work_order_id)
    {
        WebElement workOrderId = driver.findElement(By.xpath("//span[normalize-space(text())='" + work_order_id + "']"));
        workOrderId.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            WebElement header = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//h1[contains(normalize-space(text()), 'Edit Work Order')]")
            ));
            return header.isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }

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
    public boolean forcecloseWOwithFail(String close_reason) throws InterruptedException {
        try
        {
            WebElement assignmentTab=driver.findElement(By.linkText(AssignementTablink));
            assignmentTab.click();
            try
            {
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
                System.out.println("ana hena aho3");
                return true;
            }catch (NoSuchElementException e1)
            {
                return false;
            }
        }catch (NoSuchElementException | ElementClickInterceptedException e3)
        {
            try
            {
                WebElement CloseCodedropDownList= driver.findElement(By.xpath(ForceCloseList));
                Select selectSuccess= new Select(CloseCodedropDownList);
                selectSuccess.selectByVisibleText("Fail");
                Thread.sleep(2000);
                WebElement StatusNamedropdownlist=driver.findElement(By.xpath(Force_close_statusName));
                Select StatusName= new Select(StatusNamedropdownlist);
                StatusName.selectByVisibleText(close_reason);
                WebElement OkButton=driver.findElement(By.linkText(Force_close_Ok_button));
                OkButton.click();
                //System.out.println("ana hena aho");
                Thread.sleep(5000);
                WebElement OkButton2=driver.findElement(By.linkText(Force_close_Ok_button));

                if(OkButton2.isDisplayed())
                {
                    OkButton2.click();
                    //Thread.sleep(3000);
                    //System.out.println("ana hena aho2");
                    return true;
                }
                else return false;
            }catch (NoSuchElementException e2)
            {
                return false;
            }
        }

    }
    public boolean check_only_one_row_in_assignment_tab() throws InterruptedException {
        Thread.sleep(3000);
        WebElement table = driver.findElement(By.xpath("//table[@class='x14q x15f']"));
        List<WebElement> rows = table.findElements(By.xpath(".//tbody/tr"));
        System.out.println(rows.size());
        if (rows.size() == 1) {
            return true;
        } else {
            return false;
        }

    }
    public String get_close_code_assignment_tab()
    {
            WebElement table = driver.findElement(By.xpath("//table[@class='x14q x15f']"));

// Locate the first row
            WebElement firstRow = table.findElement(By.xpath(".//tbody/tr[1]"));

// Locate spans inside 9th and 10th td
            WebElement spanIn9thTd = firstRow.findElement(By.xpath("./td[10]//span"));

// Print or use the text
            return spanIn9thTd.getText();
    }
    public String get_task_status_assignment_tab()
    {
        WebElement table = driver.findElement(By.xpath("//table[@class='x14q x15f']"));

// Locate the first row
        WebElement firstRow = table.findElement(By.xpath(".//tbody/tr[1]"));

// Locate spans inside 9th and 10th td
        WebElement spanIn9thTd = firstRow.findElement(By.xpath("./td[9]//span"));

// Print or use the text
        return spanIn9thTd.getText();

    }
    public boolean validate_fail_closure(String close_reason)
    {
        WebElement assignmentTab=driver.findElement(By.linkText(AssignementTablink));
        assignmentTab.click();
        try
        {
            driver.findElement(By.xpath(EndTAskxpath)).click();
            Thread.sleep(800);
            WebElement CloseCodedropDownList= driver.findElement(By.xpath(ForceCloseList));
            String actual_close_code=CloseCodedropDownList.getAttribute("title");
            WebElement StatusNamedropdownlist=driver.findElement(By.xpath(Force_close_statusName));
            String actual_close_reason=StatusNamedropdownlist.getAttribute("title");
            System.out.println(actual_close_reason);
            System.out.println(actual_close_code);
            if(actual_close_code.equalsIgnoreCase("FAIL") && actual_close_reason.equals(close_reason))
            {
                return true;
            }
            else return false;
        }catch (NoSuchElementException | InterruptedException e1)
        {
            return false;
        }
    }
    public boolean check_validation_message_if_success() throws InterruptedException {
        WebElement OkButton=driver.findElement(By.linkText(Force_close_Ok_button));
        OkButton.click();
        //System.out.println("ana hena aho");
        Thread.sleep(5000);
        WebElement message = driver.findElement(By.xpath("//div[contains(text(), 'Call Enterprise Teamleader To Close')]"));
        if (message.isDisplayed()) {
            System.out.println("Message found: " + message.getText());
            WebElement OkButton2=driver.findElement(By.linkText(Force_close_Ok_button));
            if(OkButton2.isDisplayed())
            {
                OkButton2.click();
                //Thread.sleep(3000);
                //System.out.println("ana hena aho2");
                return true;
            }
            else return false;
        }else return false;
    }
    public boolean validate_success_closure(String close_reason)
    {
        WebElement assignmentTab=driver.findElement(By.linkText(AssignementTablink));
        assignmentTab.click();
        try
        {
            driver.findElement(By.xpath(EndTAskxpath)).click();
            Thread.sleep(500);
            WebElement CloseCodedropDownList= driver.findElement(By.xpath(ForceCloseList));
            String actual_close_code=CloseCodedropDownList.getAttribute("title");
            WebElement StatusNamedropdownlist=driver.findElement(By.xpath(Force_close_statusName));
            String actual_close_reason=StatusNamedropdownlist.getAttribute("title");
            System.out.println(actual_close_reason);
            System.out.println(actual_close_code);
            if(actual_close_code.equalsIgnoreCase("SUCCESS") && actual_close_reason.equals(close_reason))
            {
                return true;
            }
            else return false;
        }catch (NoSuchElementException | InterruptedException e1)
        {
            return false;
        }
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




    public String get_value_by_label(String label_name)
    {
        String xpath_input = String.format("//label[normalize-space(text())='%s']/ancestor::td/following-sibling::td//*", label_name);
        WebElement input = driver.findElement(By.xpath(xpath_input));
        String value = input.getText();
        if(value.equals(""))
        {
            value = input.getAttribute("textContent").trim();
        }
        return value;
    }

    public String check_order_is_closed()
    {
        return get_value_by_label("Stage");
    }
    public String check_Closed_Date()
    {
        return get_value_by_label("Closed Date");
    }
    public String check_Re_Open_Count	()
    {
        System.out.println(get_value_by_label("Re Open Count"));
        return get_value_by_label("Re Open Count");
    }
    public String check_Notes	()
    {
        return get_value_by_label("Notes");
    }
    public String check_Description()
    {
        return get_value_by_label("Description");
    }
    public String check_Close_Code()
    {
        return get_value_by_label("Close Code");
    }
    public String check_Closed_By()
    {
        return get_value_by_label("Closed By");
    }
    public boolean isWithinTwoMinutes(String date1, String date2) {
        try {
            System.out.println(date1);
            System.out.println(date2);
            // Define the date format
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a");

            // Parse both dates
            Date d1 = sdf.parse(date1);
            Date d2 = sdf.parse(date2);

            // Calculate difference in milliseconds
            long diffMillis = Math.abs(d1.getTime() - d2.getTime());

            // Convert to minutes
            long diffMinutes = diffMillis / (1000 * 60);

            // Return true if difference ≤ 2 minutes
            return diffMinutes <= 2;

        } catch (Exception e) {
            e.printStackTrace();
            return false; // Return false if parsing fails
        }
    }

}
