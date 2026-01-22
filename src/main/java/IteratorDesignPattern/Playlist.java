package IteratorDesignPattern;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

interface PlaylistIterator{
    public boolean hasNext();
    public String next();
}

class SimplePlayListIterator implements PlaylistIterator {
    private Playlist playlist;
    private int index;

    public SimplePlayListIterator(Playlist playlist){
        this.playlist = playlist;
        index = 0;
    }

    @Override
    public boolean hasNext(){
        return index < playlist.songs.size();
    }

    @Override
    public String next(){
        String song = playlist.songs.get(index);
        index++;
        return song;
    }
}

class ShuffledPlayListIterator implements PlaylistIterator {
    private Playlist playlist;
    private int index;
    private List<String> shuffledSongs;

    public ShuffledPlayListIterator(Playlist playlist){
        this.playlist = playlist;
        index = 0;
        this.shuffledSongs = playlist.songs;
        Collections.shuffle(shuffledSongs);
    }

    @Override
    public boolean hasNext(){
        return index < shuffledSongs.size();
    }

    @Override
    public String next(){
        String song = shuffledSongs.get(index);
        index++;
        return song;
    }
}

public class Playlist {
    List<String> songs;

    public Playlist(){
        songs = new ArrayList<>();
    }

    public void addSong(String song) {
        songs.add(song);
    }

    public PlaylistIterator iterator(String type){
        switch (type) {
            case "simple":
                return new SimplePlayListIterator(this);
            case "shuffled":
                return new ShuffledPlayListIterator(this);
            default:
                return null;
        }
    }


    public static void main(String[] args) {
        // Create a playlist
        Playlist playlist = new Playlist();
        playlist.addSong("Song 1");
        playlist.addSong("Song 2 Fav");
        playlist.addSong("Song 3");
        playlist.addSong("Song 4 Fav");
        playlist.addSong("Song 5");

        // Simple Playlist Iterator
        System.out.println("Simple Playlist:");
        PlaylistIterator simpleIterator = playlist.iterator("simple");
        while (simpleIterator.hasNext()) {
            System.out.println("Playing: " + simpleIterator.next());
        }

        // Shuffled Playlist Iterator
        System.out.println("nShuffled Playlist:");
        PlaylistIterator shuffledIterator = playlist.iterator("shuffled");
        while (shuffledIterator.hasNext()) {
            System.out.println("Playing: " + shuffledIterator.next());
        }

    }

}
