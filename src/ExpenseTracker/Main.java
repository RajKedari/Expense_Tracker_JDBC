package ExpenseTracker;

import ExpenseTracker.DAO.UserDAO;
import ExpenseTracker.DAO.ExpenseDAO;
import ExpenseTracker.Model.User;
import ExpenseTracker.Model.Expense;//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.sql.Date;
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        UserDAO userDAO = new UserDAO();
        ExpenseDAO expenseDAO = new ExpenseDAO();

        System.out.println("===== EXPENSE TRACKER =====");
        System.out.println("1.Register");
        System.out.println("2.Login");
        System.out.println("Choose Option: ");
        int choice = sc.nextInt();
        sc.nextLine();

        if(choice == 1){
            System.out.println("Enter name: ");
            String name = sc.nextLine();

            System.out.println("Enter email: ");
            String email = sc.nextLine();

            System.out.println("Enter password: ");
            String password = sc.nextLine();

            User newUser = new User(name, email, password);
            userDAO.addUser(newUser);
        }
        else if (choice == 2){
            System.out.println("Enter Email: ");
            String email = sc.nextLine();

            System.out.println("Enter Password: ");
            String password = sc.nextLine();

            boolean isValid = userDAO.validateUser(email,password);
            if(isValid) {
                System.out.println("Login Successful!");

                System.out.print("Enter your user ID: ");
                int userID = sc.nextInt();
                sc.nextLine();

                int option;
                do{
                    System.out.println("\n====Expense Menu====");
                    System.out.println("1.Add Expense");
                    System.out.println("2.View All Expenses");
                    System.out.println("3.Update Expense");
                    System.out.println("4.Delete Expense");
                    System.out.println("5.Expense Summary");
                    System.out.println("5.Exit");
                    System.out.println("Choose: ");
                    option = sc.nextInt();
                    sc.nextLine();

                    switch (option){
                        case 1:
                            System.out.print("Enter Category: ");
                            String category = sc.nextLine();

                            System.out.print("Enter amount: ");
                            double amount = sc.nextDouble();
                            sc.nextLine();
                            System.out.print("Enter description: ");
                            String desc = sc.nextLine();

                            Date date = new Date(System.currentTimeMillis());
                            Expense expense = new Expense(userID, category, amount, date, desc);
                            expenseDAO.addExpense(expense);
                            break;

                        case 2:
                            List<Expense> expenses = expenseDAO.getExpensesByUser(userID);
                            System.out.println("\n====Your Expenses====");
                            for (Expense e: expenses){
                                System.out.println("ID: "+e.getExpenseID()+
                                        "| Category: "+e.getCategory()+
                                        "| Amount: RS."+ e.getAmount()+
                                        "|Date: "+ e.getExpenseDate()+
                                        "|Desc: "+e.getDescription());
                            }
                            break;

                        case 3:
                            System.out.println("Enter the expense ID to be updated: ");
                            int updateID = sc.nextInt();
                            sc.nextLine();

                            System.out.println("Enter new category: ");
                            String newCat = sc.nextLine();
                            System.out.println("Enter new amount: ");
                            double newAmt = sc.nextDouble();
                            sc.nextLine();
                            System.out.println("Enter new description: ");
                            String newDesc = sc.nextLine();

                            java.sql.Date newDate = new java.sql.Date(System.currentTimeMillis());
                            boolean updated = expenseDAO.updateExpense(updateID, newCat, newAmt, newDate, newDesc);

                            if(updated){
                                System.out.println("Expense Updated Successfully!!");
                            }else{
                                System.out.println("Expense not found or update failed.");
                            }
                            break;

                        case 4:
                            System.out.println("Enter expense ID to be deleted: ");
                            int delID = sc.nextInt();
                            sc.nextLine();

                            boolean deleted = expenseDAO.deleteExpense(delID);
                            if(deleted){
                                System.out.println("Expense deleted successfully!!");
                            }else{
                                System.out.println("Expense not found or delete failed.");
                            }
                            break;

                        case 5:
                            System.out.println("\n===== Expense Summary =====");
                            double total = expenseDAO.getTotalExpense(userID);
                            System.out.println("💰 Total spent: ₹" + total);

                            Map<String, Double> categoryTotals = expenseDAO.getCategoryWiseTotal(userID);
                            System.out.println("\n📂 Category-wise Spending:");
                            for (Map.Entry<String, Double> entry : categoryTotals.entrySet()) {
                                System.out.println(entry.getKey() + " : ₹" + entry.getValue());
                            }

                            double monthly = expenseDAO.getMonthlyTotal(userID);
                            System.out.println("🗓️ This month’s spending: ₹" + monthly);

                            expenseDAO.getCategoryWiseTotal(userID);
                            break;

                        case 6:
                            System.out.println("Exiting...");
                            break;

                        default:
                            System.out.println("Invalid choice");
                    }
                }while(option!=6);


            }else{
                System.out.println("Invalid Credentials. Please try again.");
            }

        }else{
            System.out.println("Invalid option!");
        }

        sc.close();

    }
}