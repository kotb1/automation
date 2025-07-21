package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class OpenedWorkOrderDetails extends PageBase{
    public OpenedWorkOrderDetails(WebDriver driver) {
        super(driver);
    }


    WebElement workOrderId =driver.findElement(By.id("pt:mr:0:pt:lv2:0:of9"));
    WebElement customersegment=driver.findElement(By.cssSelector ("span[title='Priority']"));
    String AssignementTablink="Assignments";
    String EndTAskxpath="//img[@alt='End Task']";
    String ForceCloseList="//*[contains(@id, 'pt:soc16::content')]";
    String Force_close_statusName="//*[contains(@name, 'pt:soc17')]";
    String Force_close_Ok_button="OK";
    String WorkOrder_Status="//*[contains(@id, 'WfWorkOrder5WoStatus')]";
    String ReloadButton="Reload";



    public void forcecloseWOwithSuccess(String close_reason)
    {
        WebElement assignmentTab=driver.findElement(By.linkText(AssignementTablink));
        assignmentTab.click();
        driver.findElement(By.xpath(EndTAskxpath)).click();
        WebElement CloseCodedropDownList= driver.findElement(By.xpath(ForceCloseList));
        WebElement StatusNamedropdownlist=driver.findElement(By.xpath(Force_close_statusName));

        Select selectSuccess= new Select(CloseCodedropDownList);
        selectSuccess.selectByVisibleText("Success");
        Select StatusName= new Select(StatusNamedropdownlist);
        StatusName.selectByVisibleText(close_reason);
        WebElement OkButton=driver.findElement(By.linkText(Force_close_Ok_button));
        OkButton.click();
    }
    public String returncustomersegment()
    {
        return customersegment.getText().toString();
    }
    public void NavigateToWorkOrderDetails()
    {
        workOrderId.click();

    }
    public void forcecloseWOwithFail(String close_reason)
    {
        WebElement assignmentTab=driver.findElement(By.linkText(AssignementTablink));
        assignmentTab.click();
        // WebElement endTaskIcon=driver.findElement(By.xpath("//*[contains(@id, 'WfWork3PC')]"));

        //endTaskIcon.click();
        driver.findElement(By.xpath(EndTAskxpath)).click();
        //  driver.switchTo().frame("AFMaskingFrame");
        WebElement CloseCodedropDownList= driver.findElement(By.xpath(ForceCloseList));
        WebElement StatusNamedropdownlist=driver.findElement(By.xpath(Force_close_statusName));

        Select selectSuccess= new Select(CloseCodedropDownList);
        selectSuccess.selectByVisibleText("Fail");
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
