

/*import static java.lang.System.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;*/
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

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
        System.out.println("First I'm going to test the getDataByWeek method.");
        System.out.println(Controller.getDataByWeek("2020-02-08"));
        System.out.println("Next I'll test the getDataByDay method.");
        System.out.println(Controller.getDataByDay("2020-02-01"));
        System.out.println("Next I'll test the getDataBy5Min method.");
        System.out.println(Controller.getDataBy5Min("2020-02-01 09:00:00"));
        System.out.println("Now testing the getAll5Min method");
        ArrayList<String> TempList = Controller.getAll5Min();
        int i = 0;
        while(i < TempList.size()){
            System.out.println(TempList.get(i));
            i++;
        }
        System.out.println();
        System.out.println("Now testing the getAllDay method");
        TempList = Controller.getAllDay();
        i = 0;
        while(i < TempList.size()){
            System.out.println(TempList.get(i));
            i++;
        }
        System.out.println();
        System.out.println("Now testing the getAllWeek method");
        TempList = Controller.getAllWeek();
        i = 0;
        while(i < TempList.size()){
            System.out.println(TempList.get(i));
            i++;
        }

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

    static String getDataByDay(String s) {
        String result = null;

        try {Connection con = DriverManager.getConnection(dbUrl, username, password);
            String query = "SELECT * FROM day_data WHERE day_date = ?";

            PreparedStatement selectStmt = con.prepareStatement(query);
            selectStmt.setDate(1, java.sql.Date.valueOf(s));

            ResultSet rs = selectStmt.executeQuery();

            if (rs.next() == false) {return "Empty";}

            result = rs.getString("day_date") + ", " +
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

    static String getDataBy5Min(String s) {
        String result = null;
        String [] timeAndDate = s.split(" ");
        String date = timeAndDate[0];
        String time = timeAndDate[1];


        try {Connection con = DriverManager.getConnection(dbUrl, username, password);
            String query = "SELECT * FROM five_min_data WHERE five_min_date = ? and five_min_time = ?";

            PreparedStatement selectStmt = con.prepareStatement(query);
            selectStmt.setDate(1, java.sql.Date.valueOf(date));
            selectStmt.setTime(2,java.sql.Time.valueOf(time));

            ResultSet rs = selectStmt.executeQuery();

            if (rs.next() == false) {return "Empty";}

            result = rs.getString("five_min_date") + ", " +
                    rs.getString("five_min_time") + ", " +
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

    static ArrayList<String> getAll5Min() {
        ArrayList result = new ArrayList<String>();

        try {Connection con = DriverManager.getConnection(dbUrl, username, password);
            String query = "SELECT * FROM five_min_data";

            PreparedStatement selectStmt = con.prepareStatement(query);

            ResultSet rs = selectStmt.executeQuery();

            while (rs.next()) {
                    String temp = rs.getString("five_min_date") + " " +
                            rs.getString("five_min_time") + ", " +
                            rs.getString("high") + ", " +
                            rs.getString("low") + ", " +
                            rs.getString("open") + ", " +
                            rs.getString("close") + ", " +
                            rs.getString("volume");
                    result.add(temp);
                }

        }

        catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    static ArrayList<String> getAllDay() {
        ArrayList result = new ArrayList<String>();

        try {Connection con = DriverManager.getConnection(dbUrl, username, password);
            String query = "SELECT * FROM day_data";

            PreparedStatement selectStmt = con.prepareStatement(query);

            ResultSet rs = selectStmt.executeQuery();

            while (rs.next()) {
                String temp = rs.getString("day_date") + ", " +
                        rs.getString("high") + ", " +
                        rs.getString("low") + ", " +
                        rs.getString("open") + ", " +
                        rs.getString("close") + ", " +
                        rs.getString("volume");
                result.add(temp);
            }

        }

        catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    static ArrayList<String> getAllWeek() {
        ArrayList result = new ArrayList<String>();

        try {Connection con = DriverManager.getConnection(dbUrl, username, password);
            String query = "SELECT * FROM week_data";

            PreparedStatement selectStmt = con.prepareStatement(query);

            ResultSet rs = selectStmt.executeQuery();

            while (rs.next()) {
                String temp = rs.getString("week_date") + ", " +
                        rs.getString("high") + ", " +
                        rs.getString("low") + ", " +
                        rs.getString("open") + ", " +
                        rs.getString("close") + ", " +
                        rs.getString("volume");
                result.add(temp);
            }

        }

        catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
}
