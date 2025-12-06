package BookMyShow;

public class Movie {
    private int moveiId;
    private String movieName;
    private int movieDuration;

    public int getMoveiId() {
        return moveiId;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMoveiId(int moveiId) {
        this.moveiId = moveiId;
    }

    public void setMovieDuration(int movieDuration) {
        this.movieDuration = movieDuration;
    }

    public int getMovieDuration() {
        return movieDuration;
    }
}
