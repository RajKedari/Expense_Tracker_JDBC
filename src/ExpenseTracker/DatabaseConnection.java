package ExpenseTracker;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/expense_tracker";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Admin@123";

    public static Connection getconnection(){
        try{
            Connection conn = DriverManager.getConnection(URL,USERNAME,PASSWORD);
            return conn;
    }catch(SQLException e){
            System.out.println("Database connection failed!");
            e.printStackTrace();
            return null;
        }
    }
}
