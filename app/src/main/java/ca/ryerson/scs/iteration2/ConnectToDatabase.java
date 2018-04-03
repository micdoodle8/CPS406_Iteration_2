package ca.ryerson.scs.iteration2;
import java.sql.Connection;
import java.sql.DriverManager;


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
}
