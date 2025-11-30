package BPM;

import Pages.PageBase;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

public class FibGPONLesLinePro extends PageBase
{
    String Request_Type_id_WorkOrderPage="//span[@title='Fiber GPON Leased Line Problem']";
    String Work_Spec_id_WorkOrderPage="//span[@title='Work Spec']";
    String Task_Type_id_WorkOrderPage="//span[@title='Task Type']";
    String Submit_id="SUBMIT";
    String Submit_name="Submit";
    String problem_fixed_list_xpath ="//li[.//l[text()='problem fixed'] and contains(., 'Please select an item in the list.')]";
    String Close_Reason_list_xpath="//li[.//l[text()='CloseReason'] and contains(., 'Please select an item in the list.')]";
    String select_problem_fixed_id = "CLOSE_CODE_CLODO";
    String select_close_reason_id="Close_Reason2_CLODO";
    String success_close_code_in_my_tasks_xpath = "//tr[.//label[text()='Close Code'] and .//span[text()='Success']]";
    String fail_close_code_in_my_tasks_xpath = "//tr[.//label[text()='Close Code'] and .//span[text()='Fail']]";


    String work_order_id_div_id = "DF4789_FPDIV";
    String request_type_div_id = "DF4790_FPDIV";
    String service_id_div_id = "DF4791_FPDIV";
    String organization_div_id = "DF4792_FPDIV";
    String refrence_id_div_id = "DF4830_FPDIV";


    public FibGPONLesLinePro(WebDriver driver) {
        super(driver);
    }
    public void submit_form() throws InterruptedException {
        try
        {
            driver.findElement(By.id(Submit_id)).click();
        }
        catch (org.openqa.selenium.NoSuchElementException e1)
        {
            driver.findElement(By.name(Submit_name)).click();
        }
        Thread.sleep(2000); // sa3at el form mabtel7a2sh te3mel submit wa el start mesh bet3ady asln
    }
    public boolean check_setting_fail_close_code()
    {
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//        WebElement row = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(fail_close_code_in_my_tasks_xpath)));
//        return row.isDisplayed();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try{
            WebElement row = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(fail_close_code_in_my_tasks_xpath)));

            return row.isDisplayed();
        }
        catch (TimeoutException e) {
            //System.out.println("Element not found within the wait time.");
            return false;
        } catch (NoSuchElementException e) {
            //System.out.println("Element does not exist in the DOM.");
            return false;
        }
    }
    public boolean check_setting_success_close_code()
    {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try{
            WebElement row = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(success_close_code_in_my_tasks_xpath)));

        return row.isDisplayed();
        }
        catch (TimeoutException e) {
            //System.out.println("Element not found within the wait time.");
            return false;
        } catch (NoSuchElementException e) {
            //System.out.println("Element does not exist in the DOM.");
            return false;
        }
    }
    public List<WebElement> get_all_values_problem_fixed_list()
    {
        WebElement selectElement = driver.findElement(By.id(select_problem_fixed_id));
        Select select = new Select(selectElement);
        List<WebElement> options = select.getOptions();
        return options;
    }
    public List<WebElement> get_all_values_close_reason_list()
    {
        WebElement selectElement = driver.findElement(By.id(select_close_reason_id));
        Select select = new Select(selectElement);
        List<WebElement> options = select.getOptions();
        return options;
    }
    public void  select_close_reason(String close_reason)
    {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement selectElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(select_close_reason_id)));
        Select select = new Select(selectElement);
        select.selectByVisibleText(close_reason);
    }
    public void select_none_problem_fixed()
    {
        WebElement selectElement = driver.findElement(By.id(select_problem_fixed_id));
        Select select = new Select(selectElement);
        select.selectByVisibleText("-- NONE --");
    }
    public void select_success_problem_fixed()
    {
        WebElement selectElement = driver.findElement(By.id(select_problem_fixed_id));
        Select select = new Select(selectElement);
        select.selectByVisibleText("Success");
    }
    public void select_fail_problem_fixed()
    {
        WebElement selectElement = driver.findElement(By.id(select_problem_fixed_id));
        Select select = new Select(selectElement);
        select.selectByVisibleText("Fail");
    }
    public boolean check_missing_mandatory_attributes_message_problem_fixed()
    {
        try
        {
            WebElement problem_fixed_list = driver.findElement(By.xpath(problem_fixed_list_xpath));
            return problem_fixed_list.isDisplayed();
        }catch (org.openqa.selenium.NoSuchElementException e1) {
            return false;
        }
    }
    public boolean check_missing_mandatory_attributes_message_CLoseReason()
    {
        try
        {
            WebElement Close_Reason_list = driver.findElement(By.xpath(Close_Reason_list_xpath));
            return Close_Reason_list.isDisplayed();
        }
        catch (org.openqa.selenium.NoSuchElementException e1) {
            return false;
        }
    }

    public String check_label_and_get_value_WOID() throws InterruptedException {
        return get_value_by_label("Work Order Id");
    }
    public String check_label_and_get_number_of_trails() throws InterruptedException {
        return get_value_by_label("Number of trials.");
    }
    public String check_label_and_get_value_request_type() throws InterruptedException {
        return get_value_by_label("Request Type");
    }
    public String check_label_and_get_value_ServiceID() throws InterruptedException {
        return get_value_by_label("Service Id");
    }
    public String check_label_and_get_value_ORG() throws InterruptedException {
        return get_value_by_label("Organization");
    }
    public String check_label_and_get_value_PlaceDesc() throws InterruptedException {
        return get_value_by_label("Place Desc");
    }
    public String check_label_and_get_value_REFID() throws InterruptedException {
        return get_value_by_label("Refrence Id");
    }
    public String check_label_and_get_value_notes() throws InterruptedException {
        return get_value_by_label("Notes");
    }
    public String check_label_and_get_value_guideline() throws InterruptedException {
        try
        {
            return get_value_by_label("guideline");
        }catch(org.openqa.selenium.NoSuchElementException e1)
        {
            return get_value_by_label("Guideline");
        }
    }
    public String check_label_and_get_value_TASKID() throws InterruptedException {
        return get_value_by_label("Task ID");
    }
    public String check_label_and_get_value_TECHNAME() throws InterruptedException {
        return get_value_by_label("Tech Name");
    }
    public String check_label_and_get_value_WORKSPEC() throws InterruptedException {
        return get_value_by_label("WORK SPEC");
    }
    public String check_label_and_get_value_update_tickte_notes() throws InterruptedException {
        return get_value_by_label("Update Ticket Notes response");
    }
    public String check_label_and_get_value_ReopenCount() throws InterruptedException {return get_value_by_label("Reopen Count");}
    public String get_value_by_label(String label_name) throws InterruptedException {
        String xpath_input = String.format("//label[.//l[text()='%s']]/following-sibling::input", label_name);        WebElement input = driver.findElement(By.xpath(xpath_input));
        String value = input.getAttribute("value");
        return value;
    }
    public String get_form_title()
    {
        String title = driver.findElement(By.xpath("//h4//l")).getText();
        return title;
    }
    public String get_Business_rule_team_leader()
    {
        try
        {
            WebElement alertDiv = driver.findElement(By.xpath("//div[@id='alertDiv']"));
            String alertTitle = alertDiv.findElement(By.xpath(".//strong//l")).getText();
            String alertMessage = alertDiv.findElement(By.xpath(".//p")).getText();
            String title = alertTitle + "\n" + alertMessage;
            return title;
        }catch(org.openqa.selenium.NoSuchElementException e1){
            return null;
        }

    }
    public void start_at_retry_calling_update_ticket()
    {
        while(true) {
            try {
                if (driver.findElement(By.linkText("retry update notes api")).isDisplayed()) {
                    System.out.println("Start link found.");
                    driver.findElement(By.linkText("retry update notes api")).click();
                    break;
                }
            } catch (org.openqa.selenium.NoSuchElementException e) {
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

}
