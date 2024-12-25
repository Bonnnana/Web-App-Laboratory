package mk.ukim.finki.wp.lab.repository.jpa;

import mk.ukim.finki.wp.lab.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {
    List<Song> findAllByAlbum_Id(Long albumId);
    Optional<Song> findById(Long id);
    Song findByTrackId(String id);
    List<Song> findByTitleLike(String title);
}
