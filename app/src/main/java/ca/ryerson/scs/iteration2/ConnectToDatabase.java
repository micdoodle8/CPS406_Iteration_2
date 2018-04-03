package ca.ryerson.scs.iteration2;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;


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
}
