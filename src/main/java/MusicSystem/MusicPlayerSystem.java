package MusicSystem;

import java.util.*;

public class MusicPlayerSystem {
    private int songIdCounter = 1;
    private int userIdCounter = 1;

    private final Map<Integer, Song> songs = new HashMap<>();
    private final Map<Integer, User> users = new HashMap<>();
    // SongId -> Set of unique Users who played the song (for analytics)
    private final Map<Integer, Set<Integer>> songUserPlays = new HashMap<>();

    public int addSong(String title) {
        Song song = new Song(title,songIdCounter);
        songs.put(songIdCounter,song);
        songIdCounter++;
        return song.getId();
    }

    public int addUser(String name) {
        User user = new User(name,userIdCounter);
        users.put(userIdCounter,user);
        userIdCounter++;
        return user.getUserId();
    }

    public void playSong(int songId, int userId) {
        if(!songs.containsKey(songId)) {
            return;
        }
        if(!users.containsKey(userId)) {
            return;
        }
        songUserPlays.putIfAbsent(songId, new HashSet<>());
        songUserPlays.get(songId).add(userId);

        users.get(userId).playSong(songId);
    }

    public void printMostPlayedSongs() {
        List<SongStat> stats = new ArrayList<>();
        for(Map.Entry<Integer,Song> entry: songs.entrySet()) {
            int songId = entry.getKey();
            int playCount = songUserPlays.get(songId).size();
            stats.add(new SongStat(songId, entry.getValue().getTitle(), playCount));
        }
        stats.sort((a,b) -> Integer.compare(b.plays,a.plays));
        for (SongStat stat : stats) {
            System.out.println(stat.title + " -- unique users: " + stat.plays);
        }
    }

    private static class SongStat {
        int id;
        String title;
        int plays;
        SongStat(int i, String t, int p) { id = i; title = t; plays = p; }
    }

}

