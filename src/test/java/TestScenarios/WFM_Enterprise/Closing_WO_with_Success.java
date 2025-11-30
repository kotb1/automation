package TestScenarios.WFM_Enterprise;

import BPM.FibGPONLesLinePro;
import Database.database;
import Pages.Creation;
import Pages.WorkList;
import Pages.WorkOrderDetails;
import Pages.WorkOrderHomePage;
import TestScenarios.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Closing_WO_with_Success extends TestBase
{
    private FibGPONLesLinePro request ;
    private String closure_date;
    private WorkList worklist;
    private String close_reason;
    private WorkOrderDetails WODetails;
    //we want to check the work order items
        //Assignment Tab
        //Customer, Technical Data
    private String Work_order_id;
    private WorkOrderHomePage workOrderHomePage;
    private String request_type;
    private Creation create;
    private database Data_base;
    @BeforeClass
    @Parameters({"url","username","password","request_type"})
    public void order_Creation(String URL,String username, String password,String request_type) throws Exception {
        create = new Creation();
        this.request_type= request_type;
        worklist = new WorkList(driver);
        workOrderHomePage = new WorkOrderHomePage(driver,username,password);
        create.converting_from_string_to_XML(create.get_creaion_by_request_type(this.request_type));
        String body = create.update_complain_number();
        this.Work_order_id = create.send_creation_request_Maintenance(body,URL);
        System.out.println(this.Work_order_id);
        workOrderHomePage.Search_work_orderBYid(Work_order_id);
        Assert.assertTrue(worklist.check_number_of_WOs_after_search());
        workOrderHomePage.wait_till_schedule();
        request = new FibGPONLesLinePro(driver);
    }
    @Test
    public void Check_Creation_Attributes() throws InterruptedException {
        //Check Request_type, Work Specification and Task Type
        //we want to add compound Name
        //we want to check the dispatching process to check it also
        Assert.assertEquals(workOrderHomePage.getRequest_Type_WorkOrderPage(),"Fiber GPON Leased...");
        Assert.assertEquals(workOrderHomePage.getTask_Type_WorkOrderPage(),"Infrastructure");
        Assert.assertEquals(workOrderHomePage.getWork_Spec_WorkOrderPage(),"Infrastructure");
    }

    @Test(dependsOnMethods = "Check_Creation_Attributes")
    public void starting_order() throws InterruptedException {
        //check for the same attributes then accept order
        //check for task type
        //check customer segment
        // check status

        workOrderHomePage.navigate_to_worklist();
        worklist.Search_zone_tasks(this.Work_order_id);
        Assert.assertTrue(worklist.check_number_of_WOs_after_search());
        Assert.assertEquals(worklist.getRequest_Type_WorkList(),"Fiber GPON Leased Line Problem");
        Assert.assertEquals(worklist.getTask_Type_WorkList(),"Infrastructure");
        Assert.assertEquals(worklist.getWork_Spec_WorkList(),"Infrastructure");
        Assert.assertEquals(worklist.check_label_and_get_value_status(),"Dispatched");
        worklist.Accept_Scheduled_order();
        worklist.navigate_to_mytasks();
        worklist.Search_my_tasks(this.Work_order_id);
        Assert.assertTrue(worklist.check_number_of_WOs_after_search());
        Assert.assertEquals(worklist.getRequest_Type_WorkList(),"Fiber GPON Leased Line Problem");
        Assert.assertEquals(worklist.getTask_Type_WorkList(),"Infrastructure");
        Assert.assertEquals(worklist.getWork_Spec_WorkList(),"Infrastructure");
        Assert.assertEquals(worklist.check_label_and_get_value_status(),"Started");
        worklist.start_order();
        worklist.navigate_To_BPM_Form(worklist.get_BPM_URL());
    }
    @Test(dependsOnMethods = "starting_order")
    @Parameters({"jdbc_database_url","database_username","database_password","username"})
    public void check_attributes_start_form(String jdbc_database_url,String database_username, String database_password,String username) throws SQLException, InterruptedException, XPathExpressionException {
        //check api response

        Data_base = new database(jdbc_database_url,database_username,database_password);
        String actual_work_order_id =request.check_label_and_get_value_WOID();
        String actual_work_request_type =request.check_label_and_get_value_request_type();
        String actual_service_id =request.check_label_and_get_value_ServiceID();
        String actual_organization =request.check_label_and_get_value_ORG();
        String actual_refrence_id =request.check_label_and_get_value_REFID();
        String actual_notes = request.check_label_and_get_value_notes();
        String actual_guideline = request.check_label_and_get_value_guideline();
        String actual_Task_id = request.check_label_and_get_value_TASKID();
        //String actual_TECH_name = request.check_label_and_get_value_TECHNAME();
        String actual_work_spec = request.check_label_and_get_value_WORKSPEC();
        String actual_reopen_count = request.check_label_and_get_value_ReopenCount();
        String actual_update_ticket_notes = request.check_label_and_get_value_update_tickte_notes();
        //String expected_update_ticket_notes = "-1;Ticket number not found";
        //Assert.assertEquals(actual_update_ticket_notes,expected_update_ticket_notes);
        Assert.assertEquals(actual_work_order_id,this.Work_order_id);
        Assert.assertEquals(actual_work_request_type,this.request_type);
        String city_code = create.get_value_by_attribute("cityCode");
        String service_number = create.get_value_by_attribute("telNo");
        String service_id = city_code+"-"+service_number;
        Assert.assertEquals(actual_service_id,service_id);
        String org = create.get_value_by_attribute("exchCode");
        Assert.assertEquals(actual_organization,org);
        String refrence_id = create.get_value_by_attribute("complainNo");
        Assert.assertEquals(actual_refrence_id,refrence_id);
        Assert.assertEquals(actual_reopen_count,"0");
        String tech_name= Data_base.get_technician_name_by_work_order_id(this.Work_order_id);
        String tech_number=Data_base.get_technician_mobileNumber_by_username(username);
        String notes = "Tech Name: "+tech_name+" | Tech Mobile Num: "+tech_number;
       // System.out.println(notes);
        Assert.assertEquals(actual_notes,notes);
        Assert.assertEquals(actual_guideline,"Call Enterprise team to validate");
        Assert.assertEquals(actual_work_spec,"Infrastructure");
        String Task_id = Data_base.get_taskID_by_work_order_id(this.Work_order_id);
        Assert.assertEquals(actual_Task_id,Task_id);
    }
    @Test(dependsOnMethods = "check_attributes_start_form")
    public void missing_mandatory_attributes() throws InterruptedException {
        request.submit_form();
        Assert.assertTrue(request.check_missing_mandatory_attributes_message_problem_fixed());
        //Assert.assertTrue(request.check_missing_mandatory_attributes_message_CLoseReason());
    }
    @Test(dependsOnMethods = "missing_mandatory_attributes")
    public void check_lists_are_initially_set_correct()
    {
        Assert.assertEquals(request.get_all_values_problem_fixed_list().size(),3);
        Assert.assertEquals(request.get_all_values_problem_fixed_list().get(0).getText(),"-- NONE --");
        Assert.assertEquals(request.get_all_values_problem_fixed_list().get(1).getText(),"Success");
        Assert.assertEquals(request.get_all_values_problem_fixed_list().get(2).getText(),"Fail");
        Assert.assertEquals(request.get_all_values_close_reason_list().size(),1);
        Assert.assertEquals(request.get_all_values_close_reason_list().get(0).getText(),"-- NONE --");
    }
    @Test(dependsOnMethods = "check_lists_are_initially_set_correct")
    public void check_success_close_reasons()
    {
        //check from database and UI
    }
    @Test(dependsOnMethods = "check_lists_are_initially_set_correct")
    public void check_fail_close_reasons()
    {
        //check from database and UI
    }
    @Test(dependsOnMethods = "check_success_close_reasons")
    public void choose_success_and_close_reason() throws InterruptedException {
        close_reason="تغيير بورت";
        request.select_success_problem_fixed();
        request.select_close_reason(close_reason);
        request.submit_form();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss a");
        closure_date = sdf.format(new Date());
        worklist.close_current_tab();
        worklist.navigate_to_WFM_tab();
        worklist.close_form();
        if(request.check_setting_success_close_code())
        {
            Assert.assertTrue(request.check_setting_success_close_code());
        }
        else {
            worklist.Search_my_tasks(this.Work_order_id);
            Assert.assertTrue(worklist.check_number_of_WOs_after_search());
            Assert.assertTrue(request.check_setting_success_close_code());
        }
    }
    @Test(dependsOnMethods = "choose_success_and_close_reason")
    @Parameters({"username","request_type"})
    public void check_closed_attributes(String username, String request_type) throws SQLException, InterruptedException {
        worklist.navigate_back_to_work_order();
        if(workOrderHomePage.navigate_to_closed_work_orders_tab())
        {
            workOrderHomePage.Search_work_orderBYid_closed_workOrder_tab(this.Work_order_id);
            Assert.assertTrue(worklist.check_number_of_WOs_after_search());
        }
        else Assert.assertTrue(false);
        WODetails = new WorkOrderDetails(driver);
        if(WODetails.NavigateToWorkOrderDetails(this.Work_order_id))
        {
            Assert.assertEquals(WODetails.check_order_is_closed(),"Close");
        }
        else Assert.fail();
        //check the status also after creation from FCC
        //check the customer segment
        //check ditractor flag
        //check the decription
        //check the assignment tab and the work order items tab
        String actual_Close_code = WODetails.check_Close_Code();
        String actual_CLosed_date =WODetails.check_Closed_Date();
        String actual_Closed_by =WODetails.check_Closed_By();
        String actual_Re_Open_Count= WODetails.check_Re_Open_Count();
        String actual_Notes=WODetails.check_Notes();
        String Description=WODetails.check_Description();
        String tech_name= Data_base.get_technician_name_by_work_order_id(this.Work_order_id);
        String tech_number=Data_base.get_technician_mobileNumber_by_username(username);
        String expected_notes = "Tech Name: "+tech_name+" | Tech Mobile Num: "+tech_number+"  null  null";
        String expected_close_code = Data_base.get_close_code_by_close_name(request_type,"Success",close_reason)+" "+close_reason;
        String expected_closed_by = username;
        Assert.assertTrue(WODetails.isWithinTwoMinutes(actual_CLosed_date,closure_date));
        Assert.assertEquals(actual_Close_code,expected_close_code);
        Assert.assertEquals(actual_Closed_by,expected_closed_by);
        Assert.assertEquals(actual_Notes,expected_notes);
        Assert.assertEquals(actual_Re_Open_Count,"0");
    }

}
