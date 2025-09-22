package BPM;

import Pages.PageBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class FTTHChgONTLoc extends PageBase
{
    String customer_reachable_id = "CustomerReachable_visit_info";
    String VisitOnSameDay_id ="NEWVisitOnSameDay_visit_info";
    String Submit_id = "Submit";
    public FTTHChgONTLoc(WebDriver driver) {
        super(driver);
    }
    public void select_yes_customer_reachable()
    {
        Select select = new Select(driver.findElement(By.id(customer_reachable_id)));
        select.selectByValue("yes");
        System.out.println("yes customer");
    }
    public void select_no_customer_reachable()
    {
        Select select = new Select(driver.findElement(By.id(customer_reachable_id)));
        select.selectByValue("no");
    }
    public void select_yes_VisitOnSameDay()
    {
        Select select = new Select(driver.findElement(By.id(VisitOnSameDay_id)));
        select.selectByValue("yes");
        System.out.println("yes same visit");
    }
    public void select_no_VisitOnSameDay()
    {
        Select select = new Select(driver.findElement(By.id(VisitOnSameDay_id)));
        select.selectByValue("no");
    }
    public void submit_form()
    {
        driver.findElement(By.id(Submit_id)).click();
        System.out.println("submit");
    }


}
