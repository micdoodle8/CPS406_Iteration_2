package ca.ryerson.scs.iteration2;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.List;


/**
 * Created by Nikolas Maier on 2018-04-03.
 */

public class ConnectToDatabase {

    public static Connection conn = null;

    public void ConnectToDatabase(){
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdcb:sqlite:D:\\testdb.db");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void CloseConnection() {
        try {
            conn.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public Connection UseConnection(){
        return conn;
    }
    public ResultSet RetrieveData(String Query) {
        ResultSet rs = null;
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(Query);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (Exception e) {
            }
            ;
            try {
                if (rs != null) rs.close();
            } catch (Exception e) {
            }
            ;
        }
        return rs;
    }
    //This function works by sending the query with ? where the values would be
    //Utilise the setString method and a list of each value to edit the query
    //As we should never need to mass update the database this one at a time method should work
    public void AddData(String Query, List<String> Updates){
        Integer i = 1;
        try{
            PreparedStatement pstmt = conn.prepareStatement(Query);
            for (String s:Updates){
                pstmt.setString(i, s);
            }
            pstmt.executeUpdate();
        }catch (Exception e) {
        }
    }
}
