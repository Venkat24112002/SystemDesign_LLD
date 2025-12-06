package BookMyShow;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BookMyShow {

    MovieController movieController;
    TheatreController theatreController;

    BookMyShow(){
        movieController = new MovieController();
        theatreController = new TheatreController();
    }

    public static void main(String args[]){

        BookMyShow bookMyShow = new BookMyShow();

        bookMyShow.initialize();


        //user1
        bookMyShow.createBooking(City.BANAGLORE, "BAAHUBALI");
        //user2
        bookMyShow.createBooking(City.BANAGLORE, "BAAHUBALI");

    }

    public void initialize() {
        createMovies();
        createTheatre();
    }

    public void createBooking(City userCity, String movieName){

        //1 search movieNamebyLocation;
        List<Movie> movies = movieController.getMoviesByCity(userCity);

        Movie interestedMovie = null;

        for(Movie movie:movies){

            if(movie.getMovieName().equals(movieName)){
                interestedMovie = movie;
            }
        }

        Map<Theatre,List<Show>> showTheatreWise = theatreController.getAllShow(interestedMovie,userCity);

        Map.Entry<Theatre,List<Show>> entry = showTheatreWise.entrySet().iterator().next();
        List<Show> shows = entry.getValue();
        Show interested = shows.get(0);


        int seatNumber = 30;

        List<Integer> bookseats = interested.bookedSeatIds;
        if(!bookseats.contains(seatNumber)){

            bookseats.add(seatNumber);

            Booking booking = new Booking();
            List<Seat> myBookedSeats = new ArrayList<>();
            for(Seat screenSeat : interested.screen.getSeats()){
                if(screenSeat.getSeatId() == seatNumber) {
                    myBookedSeats.add(screenSeat);
                }
                booking.setBookedSeats(myBookedSeats);
                booking.setShow(interested);
            }
        } else {
            System.out.println("seat already booked, try again");
            return;
        }
        System.out.println("BOOKING SUCCESSFUL");
    }




    private void createMovies(){

        Movie interstellar = new Movie();
        interstellar.setMoveiId(1);
        interstellar.setMovieName("Interstellar");
        interstellar.setMovieDuration(200);


        Movie bahubali = new Movie();
        bahubali.setMovieName("Bahubali");
        bahubali.setMovieDuration(200);
        bahubali.setMoveiId(2);

        movieController.addMovie(interstellar,City.BANAGLORE);
        movieController.addMovie(interstellar,City.DELHI);

        movieController.addMovie(bahubali,City.BANAGLORE);
        movieController.addMovie(bahubali, City.DELHI);

    }

    private void createTheatre(){


        Movie intersteller = movieController.getMovieByName("Interstellar");
        Movie bahubali = movieController.getMovieByName("Bahubali");

        Theatre inoxTheatre = new Theatre();
        inoxTheatre.setTheatreId(1);
        inoxTheatre.setCity(City.BANAGLORE);
        inoxTheatre.setScreens(createScreen());
        List<Show> inoxShows = new ArrayList<>();
        Show inoxMorning = createShows(1, inoxTheatre.getScreens().get(0),intersteller,12);
        Show inoxEvening = createShows(2,inoxTheatre.getScreens().get(0), bahubali, 14);
        inoxShows.add(inoxMorning);inoxShows.add(inoxEvening);
        inoxTheatre.setShows(inoxShows);

        Theatre pvrTheatre = new Theatre();
        pvrTheatre.setTheatreId(2);
        pvrTheatre.setCity(City.DELHI);
        pvrTheatre.setScreens(createScreen());
        List<Show> pvrShows = new ArrayList<>();
        Show pvrMorning = createShows(3,pvrTheatre.getScreens().get(0),intersteller,12);
        Show pvrEvening = createShows(4,pvrTheatre.getScreens().get(0),bahubali, 14);

        pvrShows.add(pvrMorning);pvrShows.add(pvrEvening);

        theatreController.addTheatre(inoxTheatre,City.BANAGLORE);
        theatreController.addTheatre(pvrTheatre,City.DELHI);


    }

    private Show createShows(int showId, Screen screen,Movie movie, int showStartTime){

        Show show = new Show();
        show.setShowId(showId);
        show.setScreen(screen);
        show.setMovie(movie);
        show.showStartTime = showStartTime;
        return show;
    }

    private List<Screen> createScreen(){
        List<Screen> screenList = new ArrayList<>();
        Screen s = new Screen();
        s.setScreenId(1);
        s.setSeats(createSeats());
        screenList.add(s);
        return screenList;
    }

    //creating 100 seats
    private List<Seat> createSeats() {

        //creating 100 seats for testing purpose, this can be generalised
        List<Seat> seats = new ArrayList<>();

        //1 to 40 : SILVER
        for (int i = 0; i < 40; i++) {
            Seat seat = new Seat();
            seat.setSeatId(i);
            seat.setSeatCategory(SeatCategory.SILVER);
            seats.add(seat);
        }

        //41 to 70 : SILVER
        for (int i = 40; i < 70; i++) {
            Seat seat = new Seat();
            seat.setSeatId(i);
            seat.setSeatCategory(SeatCategory.GOLD);
            seats.add(seat);
        }

        //1 to 40 : SILVER
        for (int i = 70; i < 100; i++) {
            Seat seat = new Seat();
            seat.setSeatId(i);
            seat.setSeatCategory(SeatCategory.PLATINUM);
            seats.add(seat);
        }

        return seats;
    }


}
