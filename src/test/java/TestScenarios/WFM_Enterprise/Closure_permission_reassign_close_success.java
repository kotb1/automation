package TestScenarios.WFM_Enterprise;

import BPM.FibGPONLesLinePro;
import Database.database;
import Pages.*;
import TestScenarios.TestBase;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import javax.xml.xpath.XPathExpressionException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Closure_permission_reassign_close_success extends TestBase
{
    private ArrayList<String>user_roles;
    private FibGPONLesLinePro request ;
    private SysAdmin system_administration;
    private String closure_date;
    private String BPM_URL;
    private WorkList worklist;
    private String close_reason;
    private WorkOrderDetails WODetails;
    private String Work_order_id;
    private WorkOrderHomePage workOrderHomePage;
    private String request_type;
    private Creation create;
    private database Data_base;
    private LoginPageWorkOrderManagement login;
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
    public void starting_order() throws InterruptedException {
        //check for the same attributes then accept order
        //check for task type
        //check customer segment

        workOrderHomePage.navigate_to_worklist();
        worklist.Search_zone_tasks(this.Work_order_id);
        Assert.assertTrue(worklist.check_number_of_WOs_after_search());
        Assert.assertEquals(worklist.getRequest_Type_WorkList(),"Fiber GPON Leased Line Problem");
        Assert.assertEquals(worklist.getTask_Type_WorkList(),"Fix");
        Assert.assertEquals(worklist.getWork_Spec_WorkList(),"Infrastructure");
        Assert.assertEquals(worklist.check_label_and_get_value_status(),"Dispatched");
        worklist.Accept_Scheduled_order();
        worklist.navigate_to_mytasks();
        worklist.Search_my_tasks(this.Work_order_id);
        Assert.assertTrue(worklist.check_number_of_WOs_after_search());
        Assert.assertEquals(worklist.getRequest_Type_WorkList(),"Fiber GPON Leased Line Problem");
        Assert.assertEquals(worklist.getTask_Type_WorkList(),"Fix");
        Assert.assertEquals(worklist.getWork_Spec_WorkList(),"Infrastructure");
        Assert.assertEquals(worklist.check_label_and_get_value_status(),"Started");
        worklist.start_order();
        BPM_URL=worklist.get_BPM_URL();
        worklist.navigate_To_BPM_Form(BPM_URL);
    }
    @Test(dependsOnMethods = "starting_order")
    @Parameters({"jdbc_database_url","database_username","database_password","username"})
    public void check_attributes_start_form(String jdbc_database_url,String database_username, String database_password,String username) throws SQLException, InterruptedException, XPathExpressionException {
        Data_base = new database(jdbc_database_url,database_username,database_password);
        String actual_work_order_id =request.check_label_and_get_value_WOID();
        String actual_work_request_type =request.check_label_and_get_value_request_type();
        String actual_service_id =request.check_label_and_get_value_ServiceID();
        String actual_organization =request.check_label_and_get_value_ORG();
        String actual_refrence_id =request.check_label_and_get_value_REFID();
        String actual_notes = request.check_label_and_get_value_notes();
        String actual_guideline = request.check_label_and_get_value_guideline();
        String actual_Task_id = request.check_label_and_get_value_TASKID();
        String actual_work_spec = request.check_label_and_get_value_WORKSPEC();
        String actual_reopen_count = request.check_label_and_get_value_ReopenCount();
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
        String notes="";
        if(tech_number == null)
        {
            notes = "Tech Name: "+tech_name+" | Tech Mobile Num: ";
        }
        else
            notes = "Tech Name: "+tech_name+" | Tech Mobile Num: "+tech_number;
        Assert.assertEquals(actual_notes,notes);
        Assert.assertEquals(actual_guideline,"Call Enterprise team to validate");
        Assert.assertEquals(actual_work_spec,"Infrastructure");
        String Task_id = Data_base.get_taskID_by_work_order_id(this.Work_order_id);
        Assert.assertEquals(actual_Task_id,Task_id);
    }
    @Test(dependsOnMethods = "check_attributes_start_form")
    public void missing_mandatory_attributes() throws InterruptedException {
        request.select_fail_problem_fixed();
        request.submit_form();
        Assert.assertFalse(request.check_missing_mandatory_attributes_message_problem_fixed());
        Assert.assertTrue(request.check_missing_mandatory_attributes_message_CLoseReason());
    }
    @Test(dependsOnMethods = "missing_mandatory_attributes")
    public void choose_fail_and_close_reason() throws InterruptedException {
        //request.select_fail_problem_fixed();
        close_reason="تفتيش هندسى";
        request.select_close_reason(close_reason);
        request.submit_form();
        worklist.refresh_form(BPM_URL);
//        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss a");
//        closure_date = sdf.format(new Date());

    }
    @Test(dependsOnMethods = "choose_fail_and_close_reason")
    public void check_call_team_leader_form() throws InterruptedException, SQLException, XPathExpressionException {
        Assert.assertEquals(request.get_form_title(),"Call Team leader to close");
        Assert.assertTrue(request.get_Business_rule_team_leader()==null);
        request.submit_form();
        String validtaion = "Business Role Validation Error!!\n"+ "\"You Can't Submit This Task Because the Control Moved To TeamLeader\"";
        Assert.assertEquals(request.get_Business_rule_team_leader(),validtaion);
        String actual_work_order_id =request.check_label_and_get_value_WOID();
        String actual_work_request_type =request.check_label_and_get_value_request_type();
        String actual_service_id =request.check_label_and_get_value_ServiceID();
        String actual_organization =request.check_label_and_get_value_ORG();
        String actual_place_desc = request.check_label_and_get_value_PlaceDesc();
        String actual_refrence_id =request.check_label_and_get_value_REFID();
        String actual_Task_id = request.check_label_and_get_value_TASKID();
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
        String actual_work_spec = request.check_label_and_get_value_WORKSPEC();
        Assert.assertEquals(actual_work_spec,"Infrastructure");
        String Task_id = Data_base.get_taskID_by_work_order_id(this.Work_order_id);
        Assert.assertEquals(actual_Task_id,Task_id);
        String Cabinet = create.get_value_by_attribute("CABINET_NO");
        String place_desc = org+"/"+Cabinet;
        Assert.assertEquals(actual_place_desc,place_desc);
        worklist.close_current_tab();
        worklist.navigate_to_WFM_tab();
        worklist.close_form();
        if(request.check_setting_fail_close_code())
        {
            Assert.assertTrue(request.check_setting_fail_close_code());
        }
        else {
            worklist.Search_my_tasks(this.Work_order_id);
            Assert.assertTrue(worklist.check_number_of_WOs_after_search());
            Assert.assertTrue(request.check_setting_fail_close_code());
        }
    }
    @Test(dependsOnMethods = "check_call_team_leader_form")
    @Parameters({"username"})
    public void get_user_roles(String username) throws InterruptedException {
        worklist.navigate_to_SYS_Admin();
        system_administration= new SysAdmin(driver);
        system_administration.navigate_to_users();
        system_administration.search_for_user(username);
        system_administration.edit_user();
        user_roles=system_administration.get_roles_user();
        system_administration.navigate_to_roles_through_menu();
    }
    @Test(dependsOnMethods = "get_user_roles")
    @Parameters({"Team_leader_username","Team_leader_password"})
    public void remove_permission_by_team_leader(String username,String password) throws InterruptedException {
        system_administration.logout();
        login = new LoginPageWorkOrderManagement(driver);
        login.loginfun(username,password);
        system_administration.navigate_to_roles();
        for (int i = 0; i < user_roles.size(); i++) {
            if(system_administration.clickSpanParentByText(user_roles.get(i)))
            {
                system_administration.navigate_to_role_Modules();
                if(system_administration.navigate_to_WO_Permissions())
                {
                    system_administration.add_remove_permissions_button();
                }
                system_administration.remove_permission_by_name("EnterPriseEndTask.View");

            }
        }

    }


//    @Test(dependsOnMethods = "check_call_team_leader_form")
//    @Parameters({"username","request_type"})
//    public void check_closed_attributes(String username, String request_type) throws SQLException {
//        worklist.navigate_back_to_work_order();
//        workOrderHomePage.Search_work_orderBYid(Work_order_id);
//        Assert.assertTrue(worklist.check_number_of_WOs_after_search());
//        WODetails = new WorkOrderDetails(driver);
//        if(WODetails.NavigateToWorkOrderDetails(this.Work_order_id))
//        {
//            Assert.assertEquals(WODetails.check_order_is_closed(),"Schedule");
//        }
//        else Assert.fail();
//        Assert.assertTrue(WODetails.check_force_close_button());
//        //check the status also after creation from FCC
//        //check the customer segment
//        //check ditractor flag
//        //check the decription
//        //check the assignment tab and the work order items tab
//
//
//
//
////        String actual_Close_code = WODetails.check_Close_Code();
////        String actual_CLosed_date =WODetails.check_Closed_Date();
////        String actual_Closed_by =WODetails.check_Closed_By();
////        String actual_Re_Open_Count= WODetails.check_Re_Open_Count();
////        String actual_Notes=WODetails.check_Notes();
////        String Description=WODetails.check_Description();
////        String tech_name= Data_base.get_technician_name_by_work_order_id(this.Work_order_id);
////        String tech_number=Data_base.get_technician_mobileNumber_by_username(username);
////        String expected_notes = "Tech Name: "+tech_name+" | Tech Mobile Num: "+tech_number;
////        String expected_close_code = Data_base.get_close_code_by_close_name(request_type,"Fail",close_reason)+" "+close_reason;
////        String expected_closed_by = username;
////        Assert.assertTrue(WODetails.isWithinTwoMinutes(actual_CLosed_date,closure_date));
////        Assert.assertEquals(actual_Close_code,expected_close_code);
////        Assert.assertEquals(actual_Closed_by,expected_closed_by);
////        Assert.assertEquals(actual_Notes,expected_notes);
////        Assert.assertEquals(actual_Re_Open_Count,"0");
//    }
}
