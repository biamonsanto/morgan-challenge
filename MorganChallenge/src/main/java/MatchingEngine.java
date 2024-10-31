import java.util.*;

public class MatchingEngine {
    private List<Order> buyOrders;  // List of buy orders, sorted by price descending
    private List<Order> sellOrders; // List of sell orders, sorted by price ascending
    private Map<String, Order> orderMap;  // Map to track orders by unique ID for quick access

    // Constructor to initialize the lists and map
    public MatchingEngine() {
        buyOrders = new ArrayList<>();
        sellOrders = new ArrayList<>();
        orderMap = new HashMap<>();
    }

    // Method to add a new order to the order book
    public void addOrder(Order order) {
        orderMap.put(order.getId(), order); // Track order by ID in map
        if (order.getOrderType().equals("limit")) {
            addLimitOrder(order); // Adds limit orders to the book and tries to match
        } else if (order.getOrderType().equals("peg")) {
            addPeggedOrder(order); // Adds pegged orders with price matching lowest offer/highest bid
        } else {
            processMarketOrder(order); // Processes market orders immediately
        }
    }

    // Method to add limit orders and sort them in the appropriate list
    private void addLimitOrder(Order order) {
        if (order.getSide().equals("buy")) {
            buyOrders.add(order);
            buyOrders.sort((a, b) -> Double.compare(b.getPrice(), a.getPrice())); // Sort buy orders by price descending
        } else {
            sellOrders.add(order);
            sellOrders.sort(Comparator.comparingDouble(Order::getPrice)); // Sort sell orders by price ascending
        }
        matchOrders(); // Attempt to match orders after adding a new limit order
    }

    // Method to add pegged orders, setting the price to the best offer/bid available
    private void addPeggedOrder(Order order) {
        if (order.getSide().equals("buy")) {
            if (!sellOrders.isEmpty()) {
                order.setPrice(sellOrders.get(0).getPrice()); // Set price to lowest sell price
            }
            buyOrders.add(order);
            buyOrders.sort((a, b) -> Double.compare(b.getPrice(), a.getPrice()));
        } else {
            if (!buyOrders.isEmpty()) {
                order.setPrice(buyOrders.get(0).getPrice()); // Set price to highest buy price
            }
            sellOrders.add(order);
            sellOrders.sort(Comparator.comparingDouble(Order::getPrice));
        }
        matchOrders();
    }

    // Method to cancel an order by ID
    public void cancelOrder(String id) {
        Order order = orderMap.get(id);
        if (order != null) {
            if (order.getSide().equals("buy")) {
                buyOrders.remove(order);
            } else {
                sellOrders.remove(order);
            }
            orderMap.remove(id); // Remove from map after cancelling
            System.out.println("Order cancelled: " + id);
        } else {
            System.out.println("Order not found: " + id);
        }
    }

    // Method to modify an existing order by changing its quantity and price
    public void modifyOrder(String id, int newQty, double newPrice) {
        Order order = orderMap.get(id);
        if (order != null) {
            order.setQty(newQty);   // Update quantity
            order.setPrice(newPrice); // Update price

            // Remove and re-add the order to ensure it is sorted correctly
            if (order.getSide().equals("buy")) {
                buyOrders.remove(order);
                buyOrders.add(order);
                buyOrders.sort((a, b) -> Double.compare(b.getPrice(), a.getPrice()));
            } else {
                sellOrders.remove(order);
                sellOrders.add(order);
                sellOrders.sort(Comparator.comparingDouble(Order::getPrice));
            }

            System.out.println("Order modified: " + id);
        } else {
            System.out.println("Order not found: " + id);
        }
    }

    // Method to immediately execute a market order based on available prices
    public void processMarketOrder(Order order) {
        if (order.getSide().equals("buy")) {
            matchMarketBuy(order.getQty());
        } else {
            matchMarketSell(order.getQty());
        }
    }

    // Method to match buy and sell orders based on price, filling orders if prices overlap
    private void matchOrders() {
        while (!buyOrders.isEmpty() && !sellOrders.isEmpty()) {
            Order buyOrder = buyOrders.get(0);
            Order sellOrder = sellOrders.get(0);

            // Check if the highest buy price meets or exceeds the lowest sell price
            if (buyOrder.getPrice() >= sellOrder.getPrice()) {
                int tradeQty = Math.min(buyOrder.getQty(), sellOrder.getQty());
                System.out.println("Trade, price: " + sellOrder.getPrice() + ", qty: " + tradeQty);

                // Adjust quantities of matched orders
                buyOrder.setQty(buyOrder.getQty() - tradeQty);
                sellOrder.setQty(sellOrder.getQty() - tradeQty);

                // Remove fully filled orders from the list
                if (buyOrder.getQty() == 0) {
                    buyOrders.remove(0);
                }
                if (sellOrder.getQty() == 0) {
                    sellOrders.remove(0);
                }
            } else {
                break; // Exit if no orders match
            }
        }
    }

    // Helper method to process market buy orders
    private void matchMarketBuy(int qty) {
        while (qty > 0 && !sellOrders.isEmpty()) {
            Order sellOrder = sellOrders.get(0);
            int tradeQty = Math.min(qty, sellOrder.getQty());
            System.out.println("Trade, price: " + sellOrder.getPrice() + ", qty: " + tradeQty);

            // Adjust remaining quantity of the market order and sell order
            qty -= tradeQty;
            sellOrder.setQty(sellOrder.getQty() - tradeQty);

            if (sellOrder.getQty() == 0) {
                sellOrders.remove(0); // Remove fully filled sell order
            }
        }
    }

    // Helper method to process market sell orders
    private void matchMarketSell(int qty) {
        while (qty > 0 && !buyOrders.isEmpty()) {
            Order buyOrder = buyOrders.get(0);
            int tradeQty = Math.min(qty, buyOrder.getQty());
            System.out.println("Trade, price: " + buyOrder.getPrice() + ", qty: " + tradeQty);

            // Adjust remaining quantity of the market order and buy order
            qty -= tradeQty;
            buyOrder.setQty(buyOrder.getQty() - tradeQty);

            if (buyOrder.getQty() == 0) {
                buyOrders.remove(0); // Remove fully filled buy order
            }
        }
    }

    // Method to display the current state of the order book
    public void printOrderBook() {
        System.out.println("Order Book:");
        System.out.println("Buy Orders:");
        for (Order order : buyOrders) {
            System.out.println(order);
        }
        System.out.println("Sell Orders:");
        for (Order order : sellOrders) {
            System.out.println(order);
        }
    }
}
