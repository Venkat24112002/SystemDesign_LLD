package MusicSystem;

public class Song {
    private String title;
    private Integer id;

    public Song(String title, Integer id) {
        this.title = title;
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}
