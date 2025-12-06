package MusicSystem;

import java.util.LinkedList;

public class User {
    private String userName;
    private Integer userId;
    private LinkedList<Integer> lastThreeSongs;

    public User(String userName, Integer userId) {
        this.userName = userName;
        this.userId = userId;
        lastThreeSongs = new LinkedList<>();
    }

    public Integer getUserId() {
        return userId;
    }

    public void playSong(int songId) {
        if(lastThreeSongs.contains(songId)) {
            lastThreeSongs.remove(songId);
        }
        lastThreeSongs.addFirst(songId);
        if(lastThreeSongs.size() > 3)lastThreeSongs.pop();
    }
}
