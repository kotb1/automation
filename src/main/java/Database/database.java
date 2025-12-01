package Database;

import java.sql.*;

public class database
{
    private Connection connection;
    String Fcc_Integ_Url= "http://10.1.1.80:7004/FccIntegration/FccIntegrationPort?WSDL";
    public database(String jdbcUrl,String username,String password)
    {
        //String jdbcUrl = "jdbc:oracle:thin:@//10.19.35.91:1521/wmfdbdev"; // Replace accordingly
        //String username = "mts_wfm_2017";
        //String password = "mts2017A";
        try {
            connection = DriverManager.getConnection(jdbcUrl, username, password);
            System.out.println("Connected to Oracle Database!");

            //Statement stmt = connection.createStatement();
            //ResultSet rs = stmt.executeQuery("SELECT * FROM your_table");

           /* while (rs.next()) {
                System.out.println(rs.getString(1)); // Adjust based on column
            }

            connection.close();*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public String get_close_code_by_close_name (String request_type, String status, String close_name) throws SQLException {
        if(status.equalsIgnoreCase("Success"))
        {
            String query = "select close_code from bs_cfg_req_close where request_type=? and category=? and close_name= ?";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, request_type);
            pstmt.setString(2, "1");
            pstmt.setString(3, close_name);
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            return rs.getString(1);
        } else if (status.equalsIgnoreCase("Fail")) {
            String query = "select close_code from bs_cfg_req_close where request_type=? and category=? and close_name= ?";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, request_type);
            pstmt.setString(2, "2");
            pstmt.setString(3, close_name);
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            return rs.getString(1);
        }
        else
            return "";
    }
    public String get_technician_name_by_work_order_id(String work_order_id) throws SQLException {
        String query = "SELECT CURRENT_WORKER FROM WF_WORK_ORDER WHERE work_order_id = ?";
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setString(1, work_order_id);
        ResultSet rs = pstmt.executeQuery();
        rs.next();
        //System.out.println(rs.getString(1));
        // ResultSet rs = stmt.executeQuery("select EMP_ROLE_NAME from bs_cfg_mobile_users where user_name = 'alaa.mts'");
        return rs.getString(1);
    }
    public String get_technician_mobileNumber_by_username(String username) throws SQLException {
        String query = "SELECT TEL_NO FROM bs_cfg_mobile_users WHERE user_name = ?";
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setString(1, username);
        ResultSet rs = pstmt.executeQuery();
        rs.next();
        // ResultSet rs = stmt.executeQuery("select EMP_ROLE_NAME from bs_cfg_mobile_users where user_name = 'alaa.mts'");
        return rs.getString(1);
    }
    public String get_taskID_by_work_order_id(String work_order_id) throws SQLException {
        String query = "select work_id from wf_work_order_item where work_order_id = ? ORDER BY work_id DESC";
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setString(1, work_order_id);
        ResultSet rs = pstmt.executeQuery();
        rs.next();
        // ResultSet rs = stmt.executeQuery("select EMP_ROLE_NAME from bs_cfg_mobile_users where user_name = 'alaa.mts'");
        return rs.getString(1);
    }
    public boolean update_close_ready_to_completed(String work_order_id) throws SQLException {
        String query = "UPDATE wf_work_order SET WO_STATUS = 'Completed' where work_order_id = ?";
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setString(1, work_order_id);
        int rs = pstmt.executeUpdate();
        return rs>0;
    }
    public boolean archive_work_order(String WO,String username)
    {
        CallableStatement cstmt = null;
        try {

            // 2️⃣ Prepare the callable statement
            // Example: PROCEDURE MY_PROCEDURE(p_name IN VARCHAR2, p_id IN NUMBER)
            String sql = "{ call ARCHIVE_WORK_ORDER(?, ?, ?) }";
            cstmt = connection.prepareCall(sql);

            // 3️⃣ Set input parameters
            cstmt.setString(1, WO);
            cstmt.registerOutParameter(2, Types.VARCHAR);
            cstmt.setString(3, username);

            // 4️⃣ Execute
            cstmt.execute();

            // 5️⃣ Commit (optional if autocommit = true)
            //connection.commit();
            return true; // success

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean make_update_ticket_info_down() throws SQLException {
        String query = "UPDATE SYSTEM_URLS_INTEGRATION SET URL = 'Completed' where SYSTEM_NAME = ?";
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setString(1, "Fcc_Integ_Url");
        int rs = pstmt.executeUpdate();
        return rs>0;
    }
    public boolean make_update_ticket_info_UP() throws SQLException {
        String query = "UPDATE SYSTEM_URLS_INTEGRATION SET URL = ? where SYSTEM_NAME = ?";
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setString(1, Fcc_Integ_Url);
        pstmt.setString(2, "Fcc_Integ_Url");
        int rs = pstmt.executeUpdate();
        return rs>0;
    }
}
