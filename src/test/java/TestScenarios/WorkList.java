package TestScenarios;

import BPM.FTTHChgONTLoc;
import BPM.FTTHWrongCardPort;
import Pages.Creation;
import Pages.LoginPageWorkOrderManagement;
import Pages.OpenedWorkOrderDetails;
import Pages.WorkOrderHomePage;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class WorkList extends TestBase
{
    Pages.WorkList x;
    FTTHWrongCardPort y;
    WorkList()
    {
        x = new Pages.WorkList(driver);
        y = new FTTHWrongCardPort(driver);
    }
    /*@Test
    public void call_team_leader_no_physical_problem()
    {
        driver.findElement(By.id("pt:username::content")).sendKeys("root");
        driver.findElement(By.id("pt:password::content")).sendKeys("root1234");
        driver.findElement(By.className("xfv")).click();
        driver.findElement(By.id("pt:homeMenu:4:CfgGovernrateGtd")).click();
        x = new Pages.WorkList(driver);
        y = new FTTHWrongCardPort(driver);
        x.Search_zone_tasks("9949684");
        x.Accept_Scheduled_order();
        x.navigate_to_mytasks();
        x.Search_my_tasks("9949684");
        x.Start_order();
        String FormUrl=x.get_BPM_URL();
        x.navigate_To_BPM_Form(FormUrl);
        y.select_no_problem_Exist();
        y.submit_form();
        x.refresh_form(FormUrl);
        String form_label=y.get_form_label();
        y.submit_form();
        String validation_rule=y.get_validation_error();
        Assert.assertTrue(driver.findElement(new By.ByLinkText("The Root")).getText().contains("The Root"));
    }*/

    @Test
    public void fail_from_assignment_tab_FTTHCHONTLOC() throws Exception
    {
        Creation create = new Creation();
        x = new Pages.WorkList(driver);
        create.converting_from_string_to_XML(create.get_creaion_by_request_type("FTTHChgONTLoc"));
        String body = create.update_complain_number();
        String Work_order_id = create.send_creation_request_Maintenance(body);
        System.out.println(Work_order_id);
        WorkOrderHomePage workOrderHomePage = new WorkOrderHomePage(driver);
        workOrderHomePage.Search_work_orderBYid(Work_order_id);
        workOrderHomePage.wait_till_schedule();
        //driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        workOrderHomePage.navigate_to_worklist();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        x.navigate_to_zonetasks();
        x.Search_zone_tasks(Work_order_id);
        x.Accept_Scheduled_order();
        x.navigate_to_mytasks();
        x.Search_my_tasks(Work_order_id);
        x.Start_as_confirm_visit();
        String iframe= x.get_BPM_URL();
        x.navigate_To_BPM_Form(iframe);
        FTTHChgONTLoc y = new FTTHChgONTLoc(driver);

        y.select_yes_customer_reachable();
        y.select_yes_VisitOnSameDay();

        y.submit_form();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        driver.close();
        x.navigate_to_WFM_tab();
        WebElement closeBtn = driver.findElement(By.className("x1dz"));
        closeBtn.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        x.Search_my_tasks(Work_order_id);
        x.Start_order();
        String iframe2= x.get_BPM_URL();
        x.navigate_To_BPM_Form(iframe2);
        y.submit_form();
        x.refresh_form(iframe2);
        y.submit_form();
        x.refresh_form(iframe2);
        System.out.println(iframe);
        System.out.println(iframe2);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        driver.close();
        x.navigate_to_WFM_tab();
        WebElement closeBtn2 = driver.findElement(By.className("x1dz"));
        closeBtn2.click();
        driver.findElement(By.id("pt:MenuITem")).click();
        driver.findElement(By.xpath("//td[normalize-space(text())='Work Orders']")).click();
        workOrderHomePage.Search_work_orderBYid(Work_order_id);
        OpenedWorkOrderDetails Wo_details = new OpenedWorkOrderDetails(driver);
        Wo_details.NavigateToWorkOrderDetails(Work_order_id);
        Wo_details.forcecloseWOwithFail("Major Fault");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement div = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.x15p")));
        String text = (String) ((JavascriptExecutor) driver)
                .executeScript("return arguments[0].textContent;", div);
        Assert.assertEquals(text.trim(), "Cannot close with fail close code");
    }
    /*@Test
    public void test_fail_button() throws Exception
    {
        x = new Pages.WorkList(driver);
        WorkOrderHomePage workOrderHomePage = new WorkOrderHomePage(driver);
        workOrderHomePage.Search_work_orderBYid("9952342");
        OpenedWorkOrderDetails Wo_details = new OpenedWorkOrderDetails(driver);
        Wo_details.NavigateToWorkOrderDetails("9952342");
        Wo_details.forcecloseWOwithFail("Major Fault");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement div = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.x15p")));
        String text = (String) ((JavascriptExecutor) driver)
                .executeScript("return arguments[0].textContent;", div);
        Assert.assertTrue(text.trim().equals("Cannot close with fail close code"));
    }*/
}
