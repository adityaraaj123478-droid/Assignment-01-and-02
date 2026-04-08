import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class Assignment01and02 {
    // Thread-safe map for Product ID -> Stock Count
    private final ConcurrentHashMap<String, AtomicInteger> inventory = new ConcurrentHashMap<>();

    // Map for Product ID -> Ordered Waiting List (FIFO)
    private final Map<String, LinkedHashSet<Integer>> waitingLists = new ConcurrentHashMap<>();

    public void addProduct(String productId, int initialStock) {
        inventory.put(productId, new AtomicInteger(initialStock));
        waitingLists.put(productId, new LinkedHashSet<>());
    }

    public String purchaseItem(String productId, int userId) {
        AtomicInteger stock = inventory.get(productId);

        if (stock == null) return "Product not found.";

        // Attempt to decrement only if stock > 0 (Atomic operation)
        while (true) {
            int currentStock = stock.get();
            if (currentStock <= 0) {
                return addToWaitingList(productId, userId);
            }

            if (stock.compareAndSet(currentStock, currentStock - 1)) {
                return "Success! Purchase complete for User " + userId + ". Units remaining: " + (currentStock - 1);
            }
        }
    }

    private synchronized String addToWaitingList(String productId, int userId) {
        LinkedHashSet<Integer> list = waitingLists.get(productId);
        if (list.add(userId)) {
            return "Stock Out! User " + userId + " added to waiting list at position #" + list.size();
        }
        return "User " + userId + " is already in the waiting list.";
    }

    public int checkStock(String productId) {
        return inventory.containsKey(productId) ? inventory.get(productId).get() : 0;
    }

    public static void main(String[] args) {
        FlashSaleManager sale = new FlashSaleManager();
        sale.addProduct("IPHONE15", 2); // Small stock for testing

        System.out.println("Initial Stock: " + sale.checkStock("IPHONE15"));

        // Simulate purchases
        System.out.println(sale.purchaseItem("IPHONE15", 101));
        System.out.println(sale.purchaseItem("IPHONE15", 102));

        // This should trigger the waiting list
        System.out.println(sale.purchaseItem("IPHONE15", 103));
        System.out.println(sale.purchaseItem("IPHONE15", 104));
    }
}