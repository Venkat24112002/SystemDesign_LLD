package BookMyShow;

import java.util.ArrayList;
import java.util.List;

public class Theatre {

    int theatreId;
    String address;
    City city;
    List<Screen> screens = new ArrayList<>();
    List<Show> shows = new ArrayList<>();

    public int getTheatreId() {
        return theatreId;
    }

    public List<Screen> getScreens() {
        return screens;
    }

    public List<Show> getShows() {
        return shows;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public void setTheatreId(int theatreId) {
        this.theatreId = theatreId;
    }

    public void setShows(List<Show> shows) {
        this.shows = shows;
    }

    public void setScreens(List<Screen> screens) {
        this.screens = screens;
    }
}
