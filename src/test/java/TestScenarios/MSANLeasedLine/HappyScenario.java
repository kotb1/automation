package TestScenarios.MSANLeasedLine;

import BPM.MSANLasedProblem;
import Pages.*;
import TestScenarios.HomePage;
import TestScenarios.OpenedWorkOrder;
import TestScenarios.TestBase;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.time.Duration;

public class HappyScenario extends TestBase {

    Creation CreateMSANLasedPrb= new Creation();
    MSANLasedProblem MSANWorkFlow;
    HomePage homeP=new HomePage();
    OpenedWorkOrderPage OpenMSANOrder;
    OpenedWorkOrderDetails MSANWODetails ;
    WorkList workList;
    LoginPageWorkOrderManagement LoginPage;
    WorkOrderHomePage HomePage;
    DispatcherModule dispatcher;

    @Parameters({"url","username","password","request_type"})

    @Test(priority = 2)
    public void HappyScenario(String url,String username, String Password,String RequestType1) throws Exception {

        LoginPage=new LoginPageWorkOrderManagement(driver);
        HomePage=new WorkOrderHomePage(driver,username,Password);
        MSANLasedProblem flow=new MSANLasedProblem(driver);
        // LoginPage.loginfun("root","root1234");
        MSANWorkFlow=new MSANLasedProblem(driver);
        OpenMSANOrder= new OpenedWorkOrderPage(driver);
        MSANWODetails= new OpenedWorkOrderDetails(driver);
        workList=new WorkList(driver);
        String MSANRequestBody= CreateMSANLasedPrb.get_creaion_by_request_type(RequestType1);
        System.out.println("order is created");
        CreateMSANLasedPrb.converting_from_string_to_XML(MSANRequestBody);
        String OrderNo=CreateMSANLasedPrb.send_creation_request_Maintenance( CreateMSANLasedPrb.update_complain_number(),url);
        // homeP.login_navigatetoOpenedWorkOrder();
        OpenMSANOrder.SearchforWorkOrder(OrderNo);
        String RequestType= OpenMSANOrder.returnRequestType();
        System.out.println(RequestType);
        MSANWODetails.NavigateToWorkOrderDetails(OrderNo);

        if(RequestType.contains("Dual"))
        {
            dispatcher=new DispatcherModule(driver);
            MSANWODetails.waitForAssignOrder();
            MSANWODetails.navigatetoDispatcher();
            dispatcher=new DispatcherModule(driver);
            dispatcher.navigateToTaskQueue();
            dispatcher.navigateToPendingTask();
            dispatcher.AssignWorkOrder("MBKGZ",OrderNo,"root");
            dispatcher.navigatebackOWorkOrder();
            HomePage.navigate_to_worklist();
            workList.navigate_to_mytasks();
            workList.Search_my_tasks(OrderNo);
            workList.Accept_Scheduled_order();

        }
        else{

            // String Status= MSANWODetails.getWOStatus();
            MSANWODetails.waitForScheduledOrder();
            MSANWODetails.navigatetoWorkList();
            workList.Search_zone_tasks(OrderNo);
            workList.Accept_Scheduled_order();
            workList.navigate_to_mytasks();
            workList.Search_my_tasks(OrderNo);
        }

        workList.StartEnterprise();
        String ReqURL= workList.get_BPM_URL();
        workList.navigate_To_BPM_Form(ReqURL);

        flow.FMEsetCloseCode_Reason("1","تغيير مودم او راوتر");
        flow.SubmitForm();
        //   workList.refresh_form(ReqURL);

        workList.navigate_to_WFM_tab();

        driver.navigate().refresh();
        workList.navigate_back_to_work_order();
        ClosedWO closedMSANLasedWO= new ClosedWO(driver);
        closedMSANLasedWO.navigateToClosedWO();
        closedMSANLasedWO.SeacrhWithClosedWO(OrderNo);
        closedMSANLasedWO.navigateTOclosedWODetails();
        String closereason= closedMSANLasedWO.returnClose_reason();
        Assert.assertTrue(closereason.contains("تغيير مودم او راوتر"));

    }






}