package ExpenseTracker.Model;

import java.sql.Date;

public class Expense {
    private int expenseID;
    private int userID;
    private String category;
    private double amount;
    private Date expenseDate;
    private String description;

    public Expense(int userID, String category, double amount, Date expenseDate, String description) {
        this.userID = userID;
        this.category = category;
        this.amount = amount;
        this.expenseDate = expenseDate;
        this.description = description;
    }

    public int getExpenseID() {
        return expenseID;
    }

    public int getUserID() {
        return userID;
    }

    public String getCategory() {
        return category;
    }

    public double getAmount() {
        return amount;
    }

    public Date getExpenseDate() {
        return expenseDate;
    }

    public String getDescription() {
        return description;
    }

    public void setExpenseID(int expenseID) {
        this.expenseID = expenseID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setExpenseDate(Date expenseDate) {
        this.expenseDate = expenseDate;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
