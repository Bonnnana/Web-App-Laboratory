package mk.ukim.finki.wp.lab.repository;

import mk.ukim.finki.wp.lab.model.Album;
import mk.ukim.finki.wp.lab.model.Artist;
import mk.ukim.finki.wp.lab.model.Song;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository

public class SongRepository {
    private List<Song> songs;
    private final AlbumRepository albumRepository;

    public SongRepository(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
        List<Album> albums = albumRepository.findAll();

        songs = new ArrayList<>();

        songs.add(new Song("track1", "Song1", "Hip Hop", 2004, new ArrayList<>(), albums.get(0)));
        songs.add(new Song("track2", "Song2", "Pop", 1999, new ArrayList<>(), albums.get(1)));
        songs.add(new Song("track3", "Song3", "Jazz", 2015, new ArrayList<>(), albums.get(2)));
        songs.add(new Song("track4", "Song4", "Rock", 2013, new ArrayList<>(), albums.get(3)));
        songs.add(new Song("track5", "Song5", "Electronic", 1982, new ArrayList<>(), albums.get(4)));
    }

    public List<Song> findAll(){
        return songs;
    }

    public Song findByTrackId(String trackId){
        return songs.stream()
                .filter(song -> song.getTrackId().equals(trackId))
                .findFirst()
                .orElse(null);
    }

    public Artist addArtistToSong(Artist artist, Song song){
        song.getPerformers().add(artist);
        return artist;
    }

    public List<Song> findByTitle(String title){
        return songs.stream()
                .filter(song -> song.getTitle() != null &&
                        song.getTitle().toLowerCase().contains(title.toLowerCase()))
                .toList();
    }
    public Optional<Song> findById(Long id){
        return songs.stream()
                .filter(song -> song.getId().equals(id))
                .findFirst();
    }

    public Optional<Song> save(String trackId, String title, String genre,
                               Integer releaseYear, Album album){
        songs.removeIf(i-> i.getTrackId().equals(trackId));
        Song song = new Song(trackId, title, genre, releaseYear, new ArrayList<>(), album);
        songs.add(song);
        return Optional.of(song);
    }

    public void deleteById(Long id){
        songs.removeIf(i-> i.getId().equals(id));
    }
}
