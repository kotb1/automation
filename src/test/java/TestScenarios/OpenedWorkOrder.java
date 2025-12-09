package TestScenarios;

import Pages.LoginPageWorkOrderManagement;
import Pages.WorkOrderHomePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class OpenedWorkOrder extends TestBase{


    LoginPageWorkOrderManagement loginobj;
    WorkOrderHomePage Homepageobj;



    public void searchforWO()
    {
        loginobj = new LoginPageWorkOrderManagement(driver);
        Homepageobj=new WorkOrderHomePage(driver);
        // System.out.println("da5el 3ala login ");
        //Homepageobj.navigatetoWorkOrder();
        System.out.println("5alast login ");

        Assert.assertTrue(driver.findElement(new By.ByLinkText("The Root")).getText().contains("The Root"));

    }
}
