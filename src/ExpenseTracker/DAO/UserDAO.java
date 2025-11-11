package ExpenseTracker.DAO;

import ExpenseTracker.DatabaseConnection;
import ExpenseTracker.Model.User;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

public class UserDAO {

    public void addUser(User user){
        String sql = "INSERT INTO users (name, email, password) VALUES (?, ?, ?)";

        try(Connection conn = DatabaseConnection.getconnection(); //resourced try block the resources given in the paranthesis are closed automatically after the try block is executed
        PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)){

            ps.setString(1,user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()){
                int generatedID = rs.getInt(1);
                user.setUserID(generatedID);
                System.out.println("User added successfully with ID: " + generatedID);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public boolean validateUser(String email, String password){
        String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
        try(Connection conn = DatabaseConnection.getconnection();
        PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1,email);
            stmt.setString(2,password);

            ResultSet rs = stmt.executeQuery();
            return rs.next();
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}
