import java.util.Scanner;
public class BizTrackSystem {

    public static void main(String[] args) {

        if (!LoginSystem.login()) return;

        Scanner sc = new Scanner(System.in);

        InventoryManager im = new InventoryManager();
        FinanceManager fm = new FinanceManager();

        FileManager.loadProducts(im);
        FileManager.loadTransactions(fm);

        int choice;

        do {
            System.out.println("\n=== BizTrack System ===");
            System.out.println("1. Add Product");
            System.out.println("2. View Products");
            System.out.println("3. Remove Product");
            System.out.println("4. Add Transaction");
            System.out.println("5. View Transactions");
            System.out.println("6. Financial Report");
            System.out.println("7. Save Data");
            System.out.println("8. Search Product");
            System.out.println("9. Low Stock Report");
            System.out.println("0. Exit");

            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    System.out.print("Name: ");
                    String name = sc.nextLine();

                    System.out.print("Qty: ");
                    int qty = sc.nextInt();

                    System.out.print("Price: ");
                    double price = sc.nextDouble();

                    int id = (int)(Math.random() * 10000);

                    im.addProduct(new Product(id, name, qty, price));
                    break;

                case 2:
                    im.viewProducts();
                    break;

                case 3:
                    System.out.print("ID: ");
                    im.removeProduct(sc.nextInt());
                    break;

                case 4:
                    sc.nextLine();

                    System.out.print("Type (Income/Expense): ");
                    String type = sc.nextLine();

                    System.out.print("Desc: ");
                    String desc = sc.nextLine();

                    System.out.print("Amount: ");
                    double amt = sc.nextDouble();

                    fm.addTransaction(new Transaction(type, desc, amt));
                    break;

                case 5:
                    fm.viewTransactions();
                    break;

                case 6:
                    fm.financialReport();
                    break;

                case 7:
                    FileManager.saveProducts(im);
                    FileManager.saveTransactions(fm);
                    break;

                case 8:
                    sc.nextLine();
                    System.out.print("Search name: ");
                    im.searchProduct(sc.nextLine());
                    break;

                case 9:
                    im.lowStockReport();
                    break;
            }

        } while (choice != 0);

        FileManager.saveProducts(im);
        FileManager.saveTransactions(fm);

        System.out.println("Exited.");
    }
}