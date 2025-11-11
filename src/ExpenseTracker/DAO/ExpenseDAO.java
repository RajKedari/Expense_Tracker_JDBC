package ExpenseTracker.DAO;

import ExpenseTracker.DatabaseConnection;
import ExpenseTracker.Model.Expense;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpenseDAO {

    public void addExpense(Expense expense){
        String sql = "INSERT INTO expenses (user_id, category, amount, expense_date, description) VALUES (?, ?, ?, ?, ?)";

        try(Connection conn = DatabaseConnection.getconnection();
        PreparedStatement ps = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)){
            ps.setInt(1,expense.getUserID());
            ps.setString(2,expense.getCategory());
            ps.setDouble(3,expense.getAmount());
            ps.setDate(4,expense.getExpenseDate());
            ps.setString(5,expense.getDescription());

            ps.executeUpdate();

            try(ResultSet rs = ps.getGeneratedKeys()){
                if(rs.next()){
                    expense.setExpenseID(rs.getInt(1));
                }
            }
            System.out.println("Expense added successfully!");
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public List<Expense> getExpensesByUser(int userID){
        List<Expense> expenses = new ArrayList<>();
        String sql = "SELECT * FROM expenses WHERE user_id = ? ORDER BY expense_date DESC";
        try(Connection conn = DatabaseConnection.getconnection();
        PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1,userID);
            try(ResultSet rs = ps.executeQuery()){
                while (rs.next()){
                    Expense exp = new Expense(rs.getInt("user_id"),
                            rs.getString("category"),
                            rs.getDouble("amount"),
                            rs.getDate("expense_date"),
                            rs.getString("description"));
                    exp.setExpenseID(rs.getInt("expense_id"));
                    expenses.add(exp);
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return expenses;
    }

    public boolean updateExpense(int expenseID, String category, double amount, java.sql.Date date, String description){
        String sql = "UPDATE expenses SET category = ?, amount = ?, expense_date = ?, description = ? WHERE expense_id = ?";
        try(Connection conn = DatabaseConnection.getconnection();
        PreparedStatement ps = conn.prepareStatement(sql)){

            ps.setString(1,category);
            ps.setDouble(2,amount);
            ps.setDate(3,date);
            ps.setString(4,description);
            ps.setInt(5,expenseID);

            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated > 0;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteExpense(int expenseID){
        String sql = "DELETE FROM expenses WHERE expense_id = ?";
        try(Connection conn = DatabaseConnection.getconnection();
        PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1,expenseID);
            int rowsDeleted  = ps.executeUpdate();
            return rowsDeleted > 0;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public double getTotalExpense(int userID){
        String sql = "SELECT SUM(amount) AS total FROM expenses WHERE user_id = ?";
        try(Connection conn = DatabaseConnection.getconnection();
        PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1,userID);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) return rs.getDouble("total");
        }catch(SQLException e){
            e.printStackTrace();
        }
        return 0.0;
    }

    public Map<String, Double> getCategoryWiseTotal(int userId) {
        Map<String, Double> categoryTotals = new HashMap<>();
        String sql = "SELECT category, SUM(amount) AS total FROM expenses WHERE user_id = ? GROUP BY category";
        try (Connection conn = DatabaseConnection.getconnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                categoryTotals.put(rs.getString("category"), rs.getDouble("total"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categoryTotals;
    }

    public double getMonthlyTotal(int userId) {
        String sql = "SELECT SUM(amount) AS total FROM expenses " +
                "WHERE user_id = ? AND MONTH(expense_date) = MONTH(CURDATE()) " +
                "AND YEAR(expense_date) = YEAR(CURDATE())";
        try (Connection conn = DatabaseConnection.getconnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getDouble("total");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }
}
