import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Create an instance of the matching engine
        MatchingEngine engine = new MatchingEngine();
        Scanner scanner = new Scanner(System.in);
        String input;

        // Instructions for the user
        System.out.println("Enter an order in the format: 'limit/market/peg buy/sell qty [price]'. Type 'print' to view the order book. Type 'cancel order <id>' to cancel. 'modify order <id> <qty> <price>' to modify. Type 'exit' to quit.");

        while (true) {
            System.out.print(">> ");
            input = scanner.nextLine();

            // Exit the loop if the user enters 'exit'
            if (input.equalsIgnoreCase("exit")) {
                break;
            }

            String[] parts = input.split(" ");

            // Handle the "print" command to display the order book
            if (parts[0].equalsIgnoreCase("print")) {
                engine.printOrderBook();
            }
            // Handle the "cancel" command to cancel an order by ID
            else if (parts[0].equalsIgnoreCase("cancel")) {
                if (parts.length == 3 && parts[1].equalsIgnoreCase("order")) {
                    engine.cancelOrder(parts[2]);
                } else {
                    System.out.println("Invalid command for cancellation.");
                }
            }
            // Handle the "modify" command to modify an order by ID
            else if (parts[0].equalsIgnoreCase("modify")) {
                if (parts.length == 5 && parts[1].equalsIgnoreCase("order")) {
                    String id = parts[2];
                    int newQty = Integer.parseInt(parts[3]);
                    double newPrice = Double.parseDouble(parts[4]);
                    engine.modifyOrder(id, newQty, newPrice);
                } else {
                    System.out.println("Invalid command for modification.");
                }
            }
            // Handle order creation commands
            else {
                if (parts.length < 3) {
                    System.out.println("Invalid format. Please try again.");
                    continue;
                }

                String orderType = parts[0];  // "limit", "market", or "peg"
                String side = parts[1];       // "buy" or "sell"
                int qty;

                // Parse quantity, show error if it's invalid
                try {
                    qty = Integer.parseInt(parts[2]);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid quantity. Please try again.");
                    continue;
                }

                double price = 0.0;
                boolean isPegged = orderType.equals("peg");

                // Parse price for limit orders, show error if it's invalid
                if (orderType.equals("limit") && parts.length >= 4) {
                    try {
                        price = Double.parseDouble(parts[3]);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid price. Please try again.");
                        continue;
                    }
                }

                // Create a new order and add it to the matching engine
                Order order = new Order(orderType, side, qty, price, isPegged);
                engine.addOrder(order);
                System.out.println("Order created: " + order);
            }
        }

        // Close the scanner after exiting the loop
        scanner.close();
    }
}
