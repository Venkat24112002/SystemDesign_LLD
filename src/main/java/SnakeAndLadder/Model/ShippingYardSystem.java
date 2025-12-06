package SnakeAndLadder.Model;

import java.util.*;

// Simple Container class
class Container {
    private String containerId;
    private String clientId;

    public Container(String containerId, String clientId) {
        this.containerId = containerId;
        this.clientId = clientId;
    }

    public String getContainerId() { return containerId; }
    public String getClientId() { return clientId; }
    public void setClientId(String clientId) { this.clientId = clientId; }
}

// Client class
class Client {
    private String clientId;
    private String name;
    private Set<String> containerIds; // Store actual container IDs

    public Client(String clientId, String name) {
        this.clientId = clientId;
        this.name = name;
        this.containerIds = new HashSet<>();
    }

    public String getClientId() { return clientId; }
    public String getName() { return name; }
    public int getContainerCount() { return containerIds.size(); }
    public Set<String> getContainerIds() { return new HashSet<>(containerIds); }

    public void addContainer(String containerId) {
        containerIds.add(containerId);
    }

    public boolean removeContainer(String containerId) {
        return containerIds.remove(containerId);
    }

    public boolean hasEnoughContainers(int amount) {
        return containerIds.size() >= amount;
    }

    // Get N containers owned by this client
    public Set<String> getOwnedContainers(int amount) {
        Set<String> result = new HashSet<>();
        int count = 0;
        for (String containerId : containerIds) {
            if (count >= amount) break;
            result.add(containerId);
            count++;
        }
        return result;
    }
}

// Order class
class Order {
    private String orderId;
    private String clientId;
    private String type; // "BUY" or "SELL"
    private int quantity;
    private double price;
    private int filledQuantity;

    public Order(String orderId, String clientId, String type, int quantity, double price) {
        this.orderId = orderId;
        this.clientId = clientId;
        this.type = type;
        this.quantity = quantity;
        this.price = price;
        this.filledQuantity = 0;
    }

    // Getters and setters
    public String getOrderId() { return orderId; }
    public String getClientId() { return clientId; }
    public String getType() { return type; }
    public int getQuantity() { return quantity; }
    public double getPrice() { return price; }
    public int getFilledQuantity() { return filledQuantity; }
    public int getRemainingQuantity() { return quantity - filledQuantity; }

    public void addFilled(int amount) {
        this.filledQuantity += amount;
    }

    public boolean isFullyFilled() {
        return filledQuantity >= quantity;
    }
}

// Trade class
class Trade {
    private String tradeId;
    private String buyerId;
    private String sellerId;
    private int quantity;
    private double price;

    public Trade(String tradeId, String buyerId, String sellerId, int quantity, double price) {
        this.tradeId = tradeId;
        this.buyerId = buyerId;
        this.sellerId = sellerId;
        this.quantity = quantity;
        this.price = price;
    }

    // Getters
    public String getTradeId() { return tradeId; }
    public String getBuyerId() { return buyerId; }
    public String getSellerId() { return sellerId; }
    public int getQuantity() { return quantity; }
    public double getPrice() { return price; }
}

// Main System Class
public class ShippingYardSystem {
    private Map<String, Client> clients;
    private Map<String, Container> containers;
    private List<Order> buyOrders;
    private List<Order> sellOrders;
    private List<Trade> trades;
    private int orderCounter;
    private int tradeCounter;
    private int containerCounter;

    public ShippingYardSystem() {
        this.clients = new HashMap<>();
        this.containers = new HashMap<>();
        this.buyOrders = new ArrayList<>();
        this.sellOrders = new ArrayList<>();
        this.trades = new ArrayList<>();
        this.orderCounter = 1;
        this.tradeCounter = 1;
        this.containerCounter = 1;
    }

    // Phase 1: Basic Container Management
    public void addClient(String clientId, String name) {
        clients.put(clientId, new Client(clientId, name));
        System.out.println("Added client: " + name);
    }

    public boolean addContainers(String clientId, int amount) {
        Client client = clients.get(clientId);
        if (client == null) {
            System.out.println("Client not found");
            return false;
        }

        // Create actual container objects AND link them to client
        for (int i = 0; i < amount; i++) {
            String containerId = "C" + containerCounter++;
            Container container = new Container(containerId, clientId);
            containers.put(containerId, container);  // System tracking
            client.addContainer(containerId);        // Client tracking
        }

        System.out.println("Added " + amount + " containers to " + client.getName());
        return true;
    }

    public boolean removeContainers(String clientId, int amount) {
        Client client = clients.get(clientId);
        if (client == null) {
            System.out.println("Client not found");
            return false;
        }

        if (!client.hasEnoughContainers(amount)) {
            System.out.println("Not enough containers to remove");
            return false;
        }

        // Get specific containers to remove
        Set<String> containersToRemove = client.getOwnedContainers(amount);

        // Remove from both client and system
        for (String containerId : containersToRemove) {
            client.removeContainer(containerId);
            containers.remove(containerId);
        }

        System.out.println("Removed " + amount + " containers from " + client.getName());
        return true;
    }

    // Phase 2: Buy/Sell Orders
    public String placeBuyOrder(String clientId, int quantity, double price) {
        if (!clients.containsKey(clientId)) {
            System.out.println("Client not found");
            return null;
        }

        String orderId = "ORD" + orderCounter++;
        Order order = new Order(orderId, clientId, "BUY", quantity, price);
        buyOrders.add(order);

        System.out.println("Buy order placed: " + orderId + " for " + quantity + " containers at $" + price);

        // Try to match immediately
        matchOrders();
        return orderId;
    }

    public String placeSellOrder(String clientId, int quantity, double price) {
        Client client = clients.get(clientId);
        if (client == null) {
            System.out.println("Client not found");
            return null;
        }

        if (!client.hasEnoughContainers(quantity)) {
            System.out.println("Not enough containers to sell");
            return null;
        }

        String orderId = "ORD" + orderCounter++;
        Order order = new Order(orderId, clientId, "SELL", quantity, price);
        sellOrders.add(order);

        System.out.println("Sell order placed: " + orderId + " for " + quantity + " containers at $" + price);

        // Try to match immediately
        matchOrders();
        return orderId;
    }

    // Phase 3: Order Matching (Bidding System)
    private void matchOrders() {
        // Sort buy orders by price (highest first)
        buyOrders.sort((a, b) -> Double.compare(b.getPrice(), a.getPrice()));

        // Sort sell orders by price (lowest first)
        sellOrders.sort((a, b) -> Double.compare(a.getPrice(), b.getPrice()));

        Iterator<Order> buyIterator = buyOrders.iterator();
        while (buyIterator.hasNext()) {
            Order buyOrder = buyIterator.next();
            if (buyOrder.isFullyFilled()) {
                buyIterator.remove();
                continue;
            }

            Iterator<Order> sellIterator = sellOrders.iterator();
            while (sellIterator.hasNext()) {
                Order sellOrder = sellIterator.next();
                if (sellOrder.isFullyFilled()) {
                    sellIterator.remove();
                    continue;
                }

                // Check if trade can happen (buy price >= sell price)
                if (buyOrder.getPrice() >= sellOrder.getPrice()) {
                    int tradeQuantity = Math.min(buyOrder.getRemainingQuantity(),
                            sellOrder.getRemainingQuantity());
                    double tradePrice = sellOrder.getPrice(); // Use seller's price

                    // Execute the trade
                    executeTrade(buyOrder, sellOrder, tradeQuantity, tradePrice);

                    // Update order quantities
                    buyOrder.addFilled(tradeQuantity);
                    sellOrder.addFilled(tradeQuantity);

                    if (sellOrder.isFullyFilled()) {
                        sellIterator.remove();
                    }

                    if (buyOrder.isFullyFilled()) {
                        break;
                    }
                }
            }

            if (buyOrder.isFullyFilled()) {
                buyIterator.remove();
            }
        }
    }

    private void executeTrade(Order buyOrder, Order sellOrder, int quantity, double price) {
        String tradeId = "T" + tradeCounter++;
        Trade trade = new Trade(tradeId, buyOrder.getClientId(), sellOrder.getClientId(),
                quantity, price);
        trades.add(trade);

        // Transfer specific containers from seller to buyer
        transferContainers(sellOrder.getClientId(), buyOrder.getClientId(), quantity);

        System.out.println("Trade executed: " + tradeId + " - " + quantity +
                " containers at $" + price + " between " +
                clients.get(buyOrder.getClientId()).getName() + " and " +
                clients.get(sellOrder.getClientId()).getName());
    }

    private void transferContainers(String fromClientId, String toClientId, int quantity) {
        Client fromClient = clients.get(fromClientId);
        Client toClient = clients.get(toClientId);

        // Get specific containers to transfer
        Set<String> containersToTransfer = fromClient.getOwnedContainers(quantity);

        // Transfer ownership
        for (String containerId : containersToTransfer) {
            // Remove from seller
            fromClient.removeContainer(containerId);

            // Add to buyer
            toClient.addContainer(containerId);

            // Update container's client reference
            Container container = containers.get(containerId);
            container.setClientId(toClientId);
        }
    }

    // Query Methods
    public void printClientInfo(String clientId) {
        Client client = clients.get(clientId);
        if (client != null) {
            System.out.println("Client: " + client.getName() +
                    " has " + client.getContainerCount() + " containers");
        }
    }

    public void printAllOrders() {
        System.out.println("\n=== Active Buy Orders ===");
        for (Order order : buyOrders) {
            System.out.println(order.getOrderId() + ": " + order.getRemainingQuantity() +
                    " containers at $" + order.getPrice());
        }

        System.out.println("\n=== Active Sell Orders ===");
        for (Order order : sellOrders) {
            System.out.println(order.getOrderId() + ": " + order.getRemainingQuantity() +
                    " containers at $" + order.getPrice());
        }
    }

    public void printTrades() {
        System.out.println("\n=== All Trades ===");
        for (Trade trade : trades) {
            System.out.println(trade.getTradeId() + ": " + trade.getQuantity() +
                    " containers at $" + trade.getPrice() +
                    " (Buyer: " + clients.get(trade.getBuyerId()).getName() +
                    ", Seller: " + clients.get(trade.getSellerId()).getName() + ")");
        }
    }

    // Demo method
    public static void main(String[] args) {
        ShippingYardSystem system = new ShippingYardSystem();

        // Phase 1: Setup clients and containers
        system.addClient("C1", "Amazon");
        system.addClient("C2", "Walmart");
        system.addClient("C3", "FedEx");

        system.addContainers("C1", 100);
        system.addContainers("C2", 50);
        system.addContainers("C3", 75);

        System.out.println("\n=== Initial State ===");
        system.printClientInfo("C1");
        system.printClientInfo("C2");
        system.printClientInfo("C3");

        // Phase 2 & 3: Trading
        System.out.println("\n=== Placing Orders ===");
        system.placeSellOrder("C1", 20, 100.0);  // Amazon sells 20 at $100
        system.placeSellOrder("C2", 15, 95.0);   // Walmart sells 15 at $95
        system.placeBuyOrder("C3", 25, 105.0);   // FedEx buys 25 at $105
        system.placeBuyOrder("C3", 10, 90.0);    // FedEx buys 10 at $90

        System.out.println("\n=== Final State ===");
        system.printClientInfo("C1");
        system.printClientInfo("C2");
        system.printClientInfo("C3");

        system.printAllOrders();
        system.printTrades();
    }
}