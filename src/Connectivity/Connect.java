package Connectivity;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class Connect{
    private String host = "jdbc:mysql://localhost:3306/FinalProjectData?serverTimezone=UTC";
    private String uName = "root";
    private String uPass = "christy123";
    private java.sql.Connection conny = null;

    public Connect () {
        //to connect this java program with mysql database.
        try {
            conny = DriverManager.getConnection(host, uName, uPass);
            System.out.println("Success connect to database!");
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
    }

    //to make it simple so we can access those database from any class just by calling this function
    //and only have to write the blob text.
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
