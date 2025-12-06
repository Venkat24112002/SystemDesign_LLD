package ShipmentManagementSystem;

import java.util.*;

class Container {
    private String containerId;
    private String clientId;

    public Container(String containerId, String clientId) {
        this.containerId = containerId;
        this.clientId = clientId;
    }

    public String getContainerId() {
        return containerId;
    }

    public String getClientId(){
        return clientId;
    }

    public void setClientId(String ClientId) {
        this.clientId = ClientId;
    }
}

class Client {
    private String clientId;
    private String name;
    private Set<String> containerIds;

    public Client(String ClientId, String name) {
        this.clientId = ClientId;
        this.name = name;
        this.containerIds = new HashSet<>();
    }

    public String getClientId() {
        return clientId;
    }

    public void addContainer(String containerId) {
        containerIds.add(containerId);
    }

    public boolean removeContainer(String containerId) {
        return containerIds.remove(containerId);
    }
}

enum orderType{
  BUY, SElL
};

class Order {
    private String orderId;
    private String clientId;
    private String containerId;
    private orderType type;
    private double price;
    private boolean isExecuted;

    public Order(String orderId, String clientId, orderType type, double price, String conatainerId){
        this.clientId = clientId;
        this.price = price;
        this.type = type;
        this.clientId = clientId;
        this.price = price;
        this.isExecuted = false;
        this.containerId = conatainerId;
    }

    public String getOrderId() { return orderId; }
    public String getClientId() { return clientId; }
    public orderType getType() { return type; }

    public void setExecuted(boolean executed) {
        isExecuted = executed;
    }

    public String getContainerId() {
        return containerId;
    }

    public double getPrice() {return price;}
    public boolean isExecuted() { return isExecuted; }
}

class Trade {
    private String tradeId;
    private String buyerId;
    private String sellerId;
    private double price;
    private String containerId;

    public Trade(String tradeId, String buyerId, String sellerId, String containerId, double price) {
        this.tradeId = tradeId;
        this.buyerId = buyerId;
        this.sellerId = sellerId;
        this.price = price;
    }

    // Getters
    public String getTradeId() { return tradeId; }
    public String getBuyerId() { return buyerId; }
    public String getSellerId() { return sellerId; }
    public double getPrice() { return price; }

    public String getContainerId() {
        return containerId;
    }
}

public class ShippingYardSystem {
    private Map<String,Client> clients;
    private Map<String,Container> containers;
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

    public void addClient(String clientId, String name){
        clients.put(clientId, new Client(clientId,name));
    }

    public boolean addContainer(String clientId) {
        Client client = clients.get(clientId);
        String containerId = "C" + containerCounter++;
        Container container = new Container(containerId, clientId);
        containers.put(containerId,container);
        client.addContainer(containerId);
        return true;
    }

    public boolean removeContainers(String clientId,String containerId) {
        Client client = clients.get(clientId);
        clients.remove(containerId);
        containers.remove(containerId);
        return true;
    }


    public String placeBuyOrder(String clientId,String containerId, double price){
        Client client = clients.get(clientId);

        Container container = containers.get(containerId);

        if(container.getClientId().equals(clientId)) {
            System.out.println("Client already owns container");
        }

        String orderId = "ORD" + orderCounter++;
        Order order = new Order(orderId, clientId, orderType.BUY, price,containerId);
        buyOrders.add(order);

        System.out.println("Buy order placed: " + orderId + " for container " + containerId +
                " at $" + price + " by " + clientId);

        return orderId;
    }

    public String placeSellOrder(String clientId, String containerId, double price){
        Client client = clients.get(clientId);

        Container container = containers.get(containerId);

        if(!container.getClientId().equals(clientId)) {
            System.out.println("Client does not own this container");
        }

        String orderId = "ORD" + orderCounter++;
        Order order = new Order(orderId, clientId, orderType.SElL, price, containerId);
        sellOrders.add(order);

        return orderId;
    }

    private void matchOrders(){

        buyOrders.removeIf(Order::isExecuted);
        sellOrders.removeIf(Order::isExecuted);

        buyOrders.sort((a,b) -> Double.compare(b.getPrice(), a.getPrice()));
        sellOrders.sort((a,b) -> Double.compare(a.getPrice(), b.getPrice()));

        for(Order buyOrder : buyOrders){
            if(buyOrder.isExecuted()) continue;
            for(Order sellOrder : sellOrders) {
                if(sellOrder.isExecuted()) continue;
                if(canMatch(buyOrder, sellOrder)) {
                    executeTrade(buyOrder, sellOrder, sellOrder.getPrice());
                    buyOrder.setExecuted(true);
                    sellOrder.setExecuted(true);
                    break;
                }
            }
        }
    }


    private boolean canMatch(Order buyOrder, Order sellOrder) {
        // Rule 1: Must be for the same container
        if (!buyOrder.getContainerId().equals(sellOrder.getContainerId())) {
            return false;
        }

        // Rule 2: Seller must still own the container
        Container container = containers.get(sellOrder.getContainerId());
        if (container == null) {
            return false;
        }

        if (!container.getClientId().equals(sellOrder.getClientId())) {
            return false;
        }

        // Rule 3: Buy price must be >= sell price
        if (buyOrder.getPrice() < sellOrder.getPrice()) {
            return false;
        }

        return true; // All conditions met!
    }

    private void executeTrade(Order buyOrder, Order sellOrder, double price) {
        Client seller = clients.get(sellOrder.getClientId());
        Client buyer = clients.get(buyOrder.getClientId());

        String containerToTrade = buyOrder.getContainerId();
        String tradeId = "T" + tradeCounter++;
        Trade trade = new Trade(tradeId, buyOrder.getClientId(), sellOrder.getClientId(),
                containerToTrade, price);
        trades.add(trade);
        seller.removeContainer(containerToTrade);
        buyer.addContainer(containerToTrade);
        Container container = containers.get(containerToTrade);
        container.setClientId(seller.getClientId());
    }

    // Query Methods
//    public void printClientInfo(String clientId) {
//        Client client = clients.get(clientId);
//        if (client != null) {
//            System.out.println("Client: " + client.getName() + " has " + client.getContainerCount() +
//                    " containers: " + client.getContainerIds());
//        }
//    }
//
//    public void printAllClients() {
//        System.out.println("\n=== All Clients ===");
//        for (Client client : clients.values()) {
//            System.out.println(client.getName() + ": " + client.getContainerCount() +
//                    " containers " + client.getContainerIds());
//        }
//    }
//
//    public void printActiveOrders() {
//        System.out.println("\n=== Active Orders ===");
//        System.out.println("Buy Orders:");
//        for (Order order : buyOrders) {
//            if (!order.isExecuted()) {
//                System.out.println("  " + order.getOrderId() + ": " +
//                        clients.get(order.getClientId()).getName() +
//                        " wants to buy container " + order.getContainerId() + " at $" + order.getPrice());
//            }
//        }

//        System.out.println("Sell Orders:");
//        for (Order order : sellOrders) {
//            if (!order.isExecuted()) {
//                System.out.println("  " + order.getOrderId() + ": " +
//                        clients.get(order.getClientId()).getName() +
//                        " wants to sell container " + order.getContainerId() + " at $" + order.getPrice());
//            }
//        }
//    }
//
    public void printTrades() {
        System.out.println("\n=== All Trades (each for 1 container) ===");
        for (Trade trade : trades) {
            System.out.println(trade.getTradeId() + ": Container " + trade.getContainerId() +
                    " traded at $" + trade.getPrice() +
                    " from " + trade.getSellerId()+
                    " to " + trade.getBuyerId());
        }
    }

    public static void main(String[] args) {
        ShippingYardSystem system = new ShippingYardSystem();
        system.addClient("C1", "Amazon");
        system.addClient("C2", "Walmart");
        system.addClient("C3", "FedEx");

        system.addContainer("C1");  // Amazon has 5 containers
        system.addContainer("C2");  // Walmart has 3 containers
        system.addContainer("C3");

        system.placeSellOrder("C1", "C1", 100.0);  // Amazon sells container C1 at $100
        system.placeSellOrder("C1", "C2", 95.0);   // Amazon sells container C2 at $95
        system.placeSellOrder("C2", "C3", 110.0);

        system.placeBuyOrder("C3", "C1", 105.0);   // FedEx wants to buy container C1 at $105
        system.placeBuyOrder("C3", "C2", 90.0);    // FedEx wants to buy container C2 at $90
        system.placeBuyOrder("C2", "C1", 120.0);

        system.printTrades();
    }

}
