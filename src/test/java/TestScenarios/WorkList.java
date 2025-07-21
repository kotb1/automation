package TestScenarios;

import BPM.FTTHWrongCardPort;
import Pages.LoginPageWorkOrderManagement;
import Pages.WorkOrderHomePage;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class WorkList extends TestBase
{
    Pages.WorkList x;
    FTTHWrongCardPort y;
    //    WorkList()
//    {
//        x = new Pages.WorkList(driver);
//        y = new FTTHWrongCardPort(driver);
//    }
    @Test
    public void call_team_leader_no_physical_problem()
    {
        x = new Pages.WorkList(driver);
        y = new FTTHWrongCardPort(driver);
        x.Search_zone_tasks("9949638");
        x.Accept_Scheduled_order();
        x.navigate_to_mytasks();
        x.Search_my_tasks("9949638");
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
    }
}
