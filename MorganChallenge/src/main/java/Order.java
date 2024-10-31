import java.util.UUID;

public class Order {
    String id;           // Unique identifier for each order
    String orderType;     // Order type: "market", "limit", or "peg"
    String side;          // Side of the order: "buy" or "sell"
    int qty;              // Quantity of the asset in the order
    double price;         // Price for limit orders; ignored for market orders
    boolean isPegged;     // True if the order is pegged to bid/offer prices

    // Constructor to initialize an order with the specified parameters
    public Order(String orderType, String side, int qty, double price, boolean isPegged) {
        this.id = UUID.randomUUID().toString();  // Generate a unique ID for the order
        this.orderType = orderType;
        this.side = side;
        this.qty = qty;
        this.price = price;
        this.isPegged = isPegged;
    }

    // Getter for order ID
    public String getId() {
        return id;
    }

    // Getter for the order type
    public String getOrderType() {
        return orderType;
    }

    // Getter for the side of the order (buy/sell)
    public String getSide() {
        return side;
    }

    // Getter for the quantity of the order
    public int getQty() {
        return qty;
    }

    // Setter for the quantity, allowing updates to this field
    public void setQty(int qty) {
        this.qty = qty;
    }

    // Getter for the price of the order
    public double getPrice() {
        return price;
    }

    // Setter for the price, allowing updates to this field
    public void setPrice(double price) {
        this.price = price;
    }

    // Check if the order is pegged
    public boolean isPegged() {
        return isPegged;
    }

    // Override the toString method to provide a readable representation of the order
    @Override
    public String toString() {
        return side + " " + qty + " @ " + price + " (ID: " + id + ")";
    }
}
