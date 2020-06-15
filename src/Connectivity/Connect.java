package Connectivity;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class Connect{
    String host = "jdbc:mysql://localhost:3306/FinalProjectData?serverTimezone=UTC";
    String uName = "root";
    String uPass = "christy123";
    java.sql.Connection conny = null;

    public Connect () {
        try {
            conny = DriverManager.getConnection(host, uName, uPass);
            System.out.println("Success connect to database!");
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
    }

    public PreparedStatement Prepstatement(String query) {
        try {
            PreparedStatement prepStat = conny.prepareStatement(query);
            return prepStat;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }




}
