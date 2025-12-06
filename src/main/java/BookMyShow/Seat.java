package BookMyShow;

public class Seat {

    public int seatId;
    public int row;
    SeatCategory seatCategory;

    public int getSeatId() {
        return seatId;
    }

    public SeatCategory getSeatCategory() {
        return seatCategory;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setSeatCategory(SeatCategory seatCategory) {
        this.seatCategory = seatCategory;
    }

    public void setSeatId(int seatId) {
        this.seatId = seatId;
    }
}
