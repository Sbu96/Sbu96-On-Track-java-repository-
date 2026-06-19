
import java.util.ArrayList;

public class InventoryManager {

    private ArrayList<Product> products = new ArrayList<>();

    public void addProduct(Product product) {
        products.add(product);
    }

    public void viewProducts() {
        if (products.isEmpty()) {
            System.out.println("No products available.");
            return;
        }

        for (Product p : products) {
            System.out.println("ID: " + p.getId()
                    + " | Name: " + p.getName()
                    + " | Qty: " + p.getQuantity()
                    + " | Price: R" + p.getPrice());
        }
    }

    public Product findById(int id) {
        for (Product p : products) {
            if (p.getId() == id) return p;
        }
        return null;
    }

    public void removeProduct(int id) {
        Product p = findById(id);
        if (p != null) {
            products.remove(p);
            System.out.println("Product removed.");
        } else {
            System.out.println("Product not found.");
        }
    }

    public void searchProduct(String name) {
        boolean found = false;

        for (Product p : products) {
            if (p.getName().equalsIgnoreCase(name)) {
                System.out.println("FOUND: " + p);
                found = true;
            }
        }

        if (!found) {
            System.out.println("Product not found.");
        }
    }

    public void lowStockReport() {
        boolean found = false;

        for (Product p : products) {
            if (p.getQuantity() <= 5) {
                System.out.println("LOW STOCK: " + p.getName()
                        + " (" + p.getQuantity() + ")");
                found = true;
            }
        }

        if (!found) {
            System.out.println("No low stock items.");
        }
    }

    public ArrayList<Product> getProducts() {
        return products;
    }
}