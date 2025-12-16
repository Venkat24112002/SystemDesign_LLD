package MediatorDesignPattern;


import java.util.HashMap;
import java.util.Map;

interface AuctionMediator{
    void placeBid(Bidder bidder,int amount);
}

abstract class Bidder {
    public AuctionMediator mediator;
    public String name;

    public Bidder(AuctionMediator mediator, String name) {
        this.mediator = mediator;
        this.name = name;
    }

    public abstract void bid(int amount);

    public String getName() {
        return name;
    }
}

class Auction implements AuctionMediator {

    private Map<Bidder, Integer> bids = new HashMap<>();

    @Override
    public void placeBid(Bidder bidder, int amount) {
        bids.put(bidder, amount);
        System.out.println(bidder.getName() + " placed bid: " + amount);
    }

    public void showWinner() {
        Bidder winner = null;
        int highestBid = 0;

        for (Map.Entry<Bidder, Integer> entry : bids.entrySet()) {
            if (entry.getValue() > highestBid) {
                highestBid = entry.getValue();
                winner = entry.getKey();
            }
        }

        System.out.println("Winner is " + winner.getName()
                + " with bid " + highestBid);
    }
}


class AuctionBidder extends Bidder {

    public AuctionBidder(AuctionMediator mediator, String name) {
        super(mediator, name);
    }

    @Override
    public void bid(int amount) {
        mediator.placeBid(this, amount);
    }
}

public class MediatorPatternDemo {
    public static void main(String[] args) {

        Auction auction = new Auction();

        Bidder john = new AuctionBidder(auction, "John");
        Bidder alice = new AuctionBidder(auction, "Alice");
        Bidder bob = new AuctionBidder(auction, "Bob");

        john.bid(100);
        alice.bid(150);
        bob.bid(200);

        auction.showWinner();
    }
}
