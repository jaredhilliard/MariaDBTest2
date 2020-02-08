

/*import static java.lang.System.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;*/
import java.sql.*;
import java.text.SimpleDateFormat;

/**
 *
 * @author pooya
 */
public class MariaDBTest2 {
    public static void main(String[] args) {
        System.out.println("I'm gonna try to connect to the database.");
/*        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection(
                    "jdbc:mysql://52.15.77.198:3306/project","mysql","mysql");
//here sonoo is database name, root is username and password
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("SELECT * FROM week_data");
            while(rs.next())
                System.out.println(rs.getDate(1)+"  "+rs.getFloat(2)+"  "+rs.getFloat(3)+"  "+rs.getFloat(4)+"  "+rs.getFloat(5)+"  "+rs.getInt(6));
            con.close();
        }catch(Exception e){ System.out.println(e);}*/

        System.out.println(Controller.getDataByWeek("2020-02-08"));
    }
}

class Controller {
    static String dbUrl = "jdbc:mysql://52.15.77.198:3306/project";
    static String username = "mysql";
    static String password = "mysql";

     static String getDataByWeek(String s) {
        String result = null;

        try {Connection con = DriverManager.getConnection(dbUrl, username, password);
            String query = "SELECT * FROM week_data WHERE week_date = ?";
            //String result = null;

            PreparedStatement selectStmt = con.prepareStatement(query);

            //hard coded version, check how prepared statements work
            //Statement stmt=con.createStatement();
            //ResultSet rs=stmt.executeQuery("SELECT * FROM week_data where week_date = \"2020-02-08\"");

            //fine as a string.
            //Date date = new SimpleDateFormat("YYYY-MM-DD").parse(s);
            selectStmt.setDate(1, java.sql.Date.valueOf(s));
            //return selectStmt.toString();

            ResultSet rs = selectStmt.executeQuery();

            if (rs.next() == false) {return "Empty";}


            result = rs.getString("week_date") + ", " +
                        rs.getString("high") + ", " +
                        rs.getString("low") + ", " +
                        rs.getString("open") + ", " +
                        rs.getString("close") + ", " +
                        rs.getString("volume");

        }

        catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
}
