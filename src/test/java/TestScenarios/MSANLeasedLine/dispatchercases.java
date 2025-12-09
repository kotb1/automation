package TestScenarios.MSANLeasedLine;

import BPM.MSANLasedProblem;
import Pages.*;
import TestScenarios.HomePage;
import TestScenarios.TestBase;
import org.testng.annotations.Test;

public class dispatchercases extends TestBase {

    Creation CreateMSANLasedPrb= new Creation();
    MSANLasedProblem MSANWorkFlow;
    TestScenarios.HomePage homeP=new HomePage();
    OpenedWorkOrderPage OpenMSANOrder;
    OpenedWorkOrderDetails MSANWODetails ;
    WorkList workList;
    LoginPageWorkOrderManagement LoginPage;
    WorkOrderHomePage HomePage;
    DispatcherModule dispatcher;


    @Test(priority = 2)
    public void Dispatcher() throws Exception {

        LoginPage = new LoginPageWorkOrderManagement(driver);
        HomePage = new WorkOrderHomePage(driver);
        MSANLasedProblem flow = new MSANLasedProblem(driver);
        MSANWorkFlow = new MSANLasedProblem(driver);
        OpenMSANOrder = new OpenedWorkOrderPage(driver);
        MSANWODetails = new OpenedWorkOrderDetails(driver);
        workList = new WorkList(driver);
        String MSANRequestBody = CreateMSANLasedPrb.get_creaion_by_request_type("MSANLesLineProDV");
        System.out.println("order is created");
        CreateMSANLasedPrb.converting_from_string_to_XML(MSANRequestBody);
        String OrderNo = CreateMSANLasedPrb.send_creation_request_Maintenance(CreateMSANLasedPrb.update_complain_number());
        homeP.login_navigatetoOpenedWorkOrder();
        OpenMSANOrder.SearchforWorkOrder(OrderNo);
        MSANWODetails.NavigateToWorkOrderDetails(OrderNo);
        MSANWODetails.waitForAssignOrder();
        MSANWODetails.navigatetoDispatcher();
        dispatcher=new DispatcherModule(driver);
        dispatcher.navigateToTaskQueue();
        dispatcher.navigateToPendingTask();
        dispatcher.AssignWorkOrder("MBKGZ",OrderNo,"root");
    }
}
