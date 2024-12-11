package mk.ukim.finki.wp.lab.repository.inMemory;

import mk.ukim.finki.wp.lab.model.Artist;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class InMemoryArtistRepository {
    private List<Artist> artists;

    public InMemoryArtistRepository() {
        artists = new ArrayList<>();

        artists.add(new Artist("Snoop", "Dogg", "Bio of Snoop Dogg"));
        artists.add(new Artist("Bruno", "Mars", "Bio of Bruno Mars"));
        artists.add(new Artist("Justin", "Bieber", "Bio of Justin Bieber"));
        artists.add(new Artist("Lady", "Gaga", "Bio of Lady Gaga"));
        artists.add(new Artist("Michael", "Jackson", "Bio of Michael Jackson"));
    }

    public List<Artist> findAll(){
        return artists;
    }
    public Optional<Artist> findById(Long id){
        return artists.stream()
                .filter(artist -> artist.getId()==id)
                .findFirst();
    }
}
