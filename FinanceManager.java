
import java.util.ArrayList;

public class FinanceManager {

    private ArrayList<Transaction> transactions = new ArrayList<>();

    public void addTransaction(Transaction t) {
        transactions.add(t);
    }

    public void viewTransactions() {
        if (transactions.isEmpty()) {
            System.out.println("No transactions.");
            return;
        }

        for (Transaction t : transactions) {
            System.out.println(t.getType()
                    + " | " + t.getDescription()
                    + " | R" + t.getAmount());
        }
    }

    public double totalIncome() {
        double sum = 0;
        for (Transaction t : transactions) {
            if (t.getType().equalsIgnoreCase("Income")) {
                sum += t.getAmount();
            }
        }
        return sum;
    }

    public double totalExpenses() {
        double sum = 0;
        for (Transaction t : transactions) {
            if (t.getType().equalsIgnoreCase("Expense")) {
                sum += t.getAmount();
            }
        }
        return sum;
    }

    public void financialReport() {
        double income = totalIncome();
        double expense = totalExpenses();
        double profit = income - expense;

        System.out.println("\n--- FINANCIAL REPORT ---");
        System.out.println("Income: R" + income);
        System.out.println("Expenses: R" + expense);
        System.out.println("Profit: R" + profit);
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }
}