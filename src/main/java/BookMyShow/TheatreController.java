package BookMyShow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TheatreController {

    Map<City, List<Theatre>> cityVsTheatre;
    List<Theatre> allTheatre;

    public TheatreController(){
        cityVsTheatre = new HashMap<>();
        allTheatre = new ArrayList<>();
    }

    void addTheatre(Theatre theatre, City city) {

        allTheatre.add(theatre);

        List<Theatre> theatres = cityVsTheatre.getOrDefault(city, new ArrayList<>());
        theatres.add(theatre);
        cityVsTheatre.put(city, theatres);
    }


    Map<Theatre, List<Show>> getAllShow(Movie movie, City city){

        Map<Theatre,List<Show>> result= new HashMap<>();
        List<Theatre> theatres = cityVsTheatre.get(city);

        for(Theatre theatre : theatres){

            List<Show> givenMovieShows = new ArrayList<>();
            List<Show> shows = theatre.getShows();

            for(Show show : shows){
                if(show.movie.getMoveiId() == movie.getMoveiId()) {
                    givenMovieShows.add(show);
                }
            }
            if(!givenMovieShows.isEmpty()){
                result.put(theatre,givenMovieShows);
            }
        }

        return result;
    }

}
