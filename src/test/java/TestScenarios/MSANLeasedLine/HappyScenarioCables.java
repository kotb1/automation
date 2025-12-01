package TestScenarios.MSANLeasedLine;

import BPM.MSANLasedProblem;
import Pages.*;
import TestScenarios.HomePage;
import TestScenarios.TestBase;
import org.testng.Assert;
import org.testng.annotations.Test;

public class HappyScenarioCables extends TestBase {

    Creation CreateMSANLasedPrb= new Creation();
    MSANLasedProblem MSANWorkFlow;
    TestScenarios.HomePage homeP=new HomePage();
    OpenedWorkOrderPage OpenMSANOrder;
    OpenedWorkOrderDetails MSANWODetails ;
    WorkList workList;
    LoginPageWorkOrderManagement LoginPage;
    WorkOrderHomePage HomePage;


    @Test(priority = 2)
    public void HappyScenariocables() throws Exception {

        LoginPage=new LoginPageWorkOrderManagement(driver);
        HomePage=new WorkOrderHomePage(driver);
        MSANLasedProblem flow=new MSANLasedProblem(driver);
        // LoginPage.loginfun("root","root1234");
        MSANWorkFlow=new MSANLasedProblem(driver);
        OpenMSANOrder= new OpenedWorkOrderPage(driver);
        MSANWODetails= new OpenedWorkOrderDetails(driver);
        workList=new WorkList(driver);
        String MSANRequestBody= CreateMSANLasedPrb.get_creaion_by_request_type("TDMLesLineProDV");
        System.out.println("order is created");
        CreateMSANLasedPrb.converting_from_string_to_XML(MSANRequestBody);
        String OrderNo=CreateMSANLasedPrb.send_creation_request_Maintenance( CreateMSANLasedPrb.update_complain_number());
        homeP.login_navigatetoOpenedWorkOrder();
        OpenMSANOrder.SearchforWorkOrder(OrderNo);
        MSANWODetails.NavigateToWorkOrderDetails(OrderNo);
        DispatcherModule dispatcher;
        String RequestType= OpenMSANOrder.returnRequestType();

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
        //fme task
        flow.FMEsetCloseCode_Reason("2","Major Fault");
        flow.SubmitForm();
        Thread.sleep(2000);
        workList.refresh_form(ReqURL);
        flow.SubmitCallTeamaForm();
        String validationError= flow.getBusinessRule();
        Assert.assertTrue(validationError.contains("You Can't Submit This Task"));
        driver.close();
        //team leader
        workList.navigate_to_WFM_tab();
        driver.navigate().refresh();
        workList.navigate_back_to_work_order();
        OpenMSANOrder.SearchforWorkOrder(OrderNo);
        MSANWODetails.NavigateToWorkOrderDetails(OrderNo);
        MSANWODetails.forcecloseWOwithFail("Major Fault");
        driver.navigate().refresh();

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
        else
        {

            // String Status= MSANWODetails.getWOStatus();
            MSANWODetails.waitForScheduledOrder();
            MSANWODetails.navigatetoWorkList();
            workList.Search_zone_tasks(OrderNo);
            workList.Accept_Scheduled_order();
            workList.navigate_to_mytasks();
            workList.Search_my_tasks(OrderNo);
        }








        workList.StartEnterprise();
        String ReqURL2= workList.get_BPM_URL();
        workList.navigate_To_BPM_Form(ReqURL2);

        flow.FMEsetCloseCode_Reason("1","تم حل المشكلة");
        flow.SubmitForm();
        workList.navigate_to_WFM_tab();

        driver.navigate().refresh();
        workList.navigate_back_to_work_order();
        ClosedWO closedMSANLasedWO= new ClosedWO(driver);
        closedMSANLasedWO.navigateToClosedWO();
        closedMSANLasedWO.SeacrhWithClosedWO(OrderNo);
        closedMSANLasedWO.navigateTOclosedWODetails();
        String closereason= closedMSANLasedWO.returnClose_reason();
        Assert.assertTrue(closereason.contains("تم حل المشكلة"));

    }

}
