package mk.ukim.finki.wp.lab.repository;

import mk.ukim.finki.wp.lab.model.Album;
import mk.ukim.finki.wp.lab.model.Song;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class AlbumRepository {
    private List<Album> albums;

    public AlbumRepository() {
        albums = new ArrayList<>();

        albums.add(new Album("The Dark Side of the Moon", "Hip Hop", "1973"));
        albums.add(new Album("Thriller", "Pop", "1982"));
        albums.add(new Album("Back in Black", "Jazz", "1980"));
        albums.add(new Album("The Wall", "Rock", "1979"));
        albums.add(new Album("Abbey Road", "Electronic", "1969"));
    }

    public List<Album> findAll(){
        return albums;
    }

    public Optional<Album> findById(Long id){
        return albums.stream()
                .filter(album -> album.getId().equals(id))
                .findFirst();
    }

}
