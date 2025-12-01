package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class DispatcherModule extends PageBase{
    public DispatcherModule(WebDriver driver) {
        super(driver);
    }
    String Task_queue_id="pt:homeMenu:2:CfgGovernrateGtd";
    String pending_task_xpath="//*[contains(@id, 'tab2::disAcr')]";
    String OrgTxt_xpath="//*[contains(@id, 'it8::content')]";
    String WOTxt_xpath="//*[contains(@id, 'it11::content')]";
    String Search_linktxt="Search";
    String Check_box="//*[contains(@id, 'sbc1::content')]";
    String Assign_linktxt="Assign";
    String worker_id_txt="//*[contains(@id, 'qryId1:val00::content')]";
    String iframe_assign_id="dataForm::__af_Z_maskingframe";
    String search_assign="//*[contains(@id, 'qryId1:_search')]";
    String root="//*[contains(@id, 'li10')]";
    String Assig_button="//*[contains(@id,'bAssign12')]/a/span";
    String modules_icon_xpath="//*[@id=\"pt:pt_m2\"]";
    String work_order_xpath="//*[@id=\"pt:i0:1:cmi0\"]/td[2]";
    public void navigateToTaskQueue() throws InterruptedException {
        Thread.sleep(2000);
        driver.findElement(By.id(Task_queue_id)).click();
    }
    public void navigateToPendingTask() throws InterruptedException {
        Thread.sleep(2000);
        driver.findElement(By.xpath(pending_task_xpath)).click();
    }

    public void AssignWorkOrder(String Org, String Work_order, String workerId)
    {
        driver.findElement(By.xpath(OrgTxt_xpath)).sendKeys(Org);
        driver.findElement(By.xpath(WOTxt_xpath)).sendKeys(Work_order);
        driver.findElement(By.linkText(Search_linktxt)).click();
        driver.findElement(By.xpath(Check_box)).click();
        driver.findElement(By.linkText(Assign_linktxt)).click();
        WebElement iframe= driver.findElement(By.id(iframe_assign_id));
      //  driver.switchTo().frame(iframe);
        driver.findElement(By.xpath(worker_id_txt)).sendKeys(workerId);
        driver.findElement(By.xpath(search_assign)).click();
        driver.findElement(By.xpath(root)).click();
        driver.findElement(By.xpath(Assig_button)).click();

    }
    public void navigatebackOWorkOrder()
    {
        driver.findElement(By.xpath(modules_icon_xpath)).click();
        driver.findElement(By.xpath(work_order_xpath)).click();
    }
}
