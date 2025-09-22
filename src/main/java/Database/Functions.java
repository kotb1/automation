package Database;

import java.sql.Connection;
import java.sql.DriverManager;

public class Functions
{
    public void Connect_to_Database()
    {
        String jdbcUrl = "jdbc:oracle:thin:@//10.19.35.91:1521/wmfdbdev"; // Replace accordingly
        String username = "mts_wfm_2017";
        String password = "mts2017A";
        try {
            Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
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
}
