package BookMyShow;

import java.util.ArrayList;
import java.util.List;

public class Show {
    int showId;
    Screen screen;
    List<Integer> bookedSeatIds = new ArrayList<>();
    int showStartTime;
    Movie movie;

    public int getShowId() {
        return showId;
    }

    public int getShowStartTime() {
        return showStartTime;
    }

    public List<Integer> getBookedSeatIds() {
        return bookedSeatIds;
    }

    public void setShowId(int showId) {
        this.showId = showId;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public void setScreen(Screen screen) {
        this.screen = screen;
    }
}
