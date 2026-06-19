import java.io.*;

public class FileManager {

    public static void saveProducts(InventoryManager im) {
        try (PrintWriter pw = new PrintWriter(new FileWriter("products.txt"))) {
            for (Product p : im.getProducts()) {
                pw.println(p);
            }
        } catch (Exception e) {
            System.out.println("Error saving products.");
        }
    }

    public static void loadProducts(InventoryManager im) {
        try (BufferedReader br = new BufferedReader(new FileReader("products.txt"))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] d = line.split(",");

                Product p = new Product(
                        Integer.parseInt(d[0].trim()),
                        d[1].trim(),
                        Integer.parseInt(d[2].trim()),
                        Double.parseDouble(d[3].trim())
                );

                im.addProduct(p);
            }

        } catch (Exception e) {
            System.out.println("No product file found.");
        }
    }

    public static void saveTransactions(FinanceManager fm) {
        try (PrintWriter pw = new PrintWriter(new FileWriter("transactions.txt"))) {
            for (Transaction t : fm.getTransactions()) {
                pw.println(t);
            }
        } catch (Exception e) {
            System.out.println("Error saving transactions.");
        }
    }

    public static void loadTransactions(FinanceManager fm) {
        try (BufferedReader br = new BufferedReader(new FileReader("transactions.txt"))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] d = line.split(",");

                Transaction t = new Transaction(
                        d[0].trim(),
                        d[1].trim(),
                        Double.parseDouble(d[2].trim())
                );

                fm.addTransaction(t);
            }

        } catch (Exception e) {
            System.out.println("No transaction file found.");
        }
    }
}