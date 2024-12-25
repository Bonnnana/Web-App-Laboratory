package mk.ukim.finki.wp.lab.bootstrap;
import java.util.Random;
import jakarta.annotation.PostConstruct;
import mk.ukim.finki.wp.lab.model.Album;
import mk.ukim.finki.wp.lab.model.Artist;
import mk.ukim.finki.wp.lab.model.Song;
import mk.ukim.finki.wp.lab.repository.jpa.AlbumRepository;
import mk.ukim.finki.wp.lab.repository.jpa.SongRepository;
import mk.ukim.finki.wp.lab.repository.jpa.ArtistRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataHolder {

    public static List<Artist> artistList=null;
    public static List<Album> albumList=null;
    public static List<Song> songList=null;
    private final SongRepository songRepository;
    private final AlbumRepository albumRepository;
    private final ArtistRepository artistRepository;

    public DataHolder(SongRepository songRepository, AlbumRepository albumRepository, ArtistRepository artistRepository) {
        this.songRepository = songRepository;
        this.albumRepository = albumRepository;
        this.artistRepository = artistRepository;
    }

    @PostConstruct
    public void init(){
        artistList=new ArrayList<>();
        artistList.add(new Artist("Bruno", "Mars", "Bio Bruno Mars"));
        artistList.add(new Artist("Jane", "Smith", "Bio Jane Smith"));
        artistList.add(new Artist("Michael", "Johnson", "Bio Michael Jackson"));
        artistList.add(new Artist("Emily", "Davis", "Bio Emily Davis"));
        artistList.add(new Artist("Justin", "Bieber", "Bio Justin Bieber"));
        this.artistRepository.saveAll(artistList);



        List<Album> albumList=new ArrayList<>();
        Album album1=new Album("Album1", "Pop", "1969");
        Album album2=new Album("Album2", "Rock", "1975");
        Album album3=new Album("Album3", "Jazz", "1991");
        Album album4=new Album("Album4", "Hip Hop", "2020");
        Album album5=new Album("Album5", "Rock", "1975");
        if(this.albumRepository.count()==0){


            albumList.add(album1);
            albumList.add(album2);
            albumList.add(album3);
            albumList.add(album4);
            albumList.add(album5);

            this.albumRepository.saveAll(albumList);
        }
        songList=new ArrayList<>();
        if(this.songRepository.count() == 0){
            songList.add(new Song("track1", "Song 1", "Rock", 1975, new ArrayList<>(), album1));
            songList.add(new Song("track2", "Song 2", "Pop", 1971, new ArrayList<>(), album2));
            songList.add(new Song( "track3", "Song 3", "Rock", 1977, new ArrayList<>(), album3));
            songList.add(new Song("track4", "Song 4", "Pop", 1982, new ArrayList<>(), album4));
            songList.add(new Song("track5", "Song 5", "Grunge", 1991, new ArrayList<>(),album5));


            this.songRepository.saveAll(songList);
        }

    }
}