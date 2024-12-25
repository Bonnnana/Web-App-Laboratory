package mk.ukim.finki.wp.lab.service.impl;

import mk.ukim.finki.wp.lab.model.Album;
import mk.ukim.finki.wp.lab.model.Artist;
import mk.ukim.finki.wp.lab.model.Review;
import mk.ukim.finki.wp.lab.model.Song;
import mk.ukim.finki.wp.lab.model.exceptions.AlbumNotFoundException;
import mk.ukim.finki.wp.lab.model.exceptions.ArtistNotFoundException;
import mk.ukim.finki.wp.lab.model.exceptions.SongNotFoundException;
import mk.ukim.finki.wp.lab.repository.jpa.AlbumRepository;
import mk.ukim.finki.wp.lab.repository.jpa.ArtistRepository;
import mk.ukim.finki.wp.lab.repository.jpa.ReviewRepository;
import mk.ukim.finki.wp.lab.repository.jpa.SongRepository;
import mk.ukim.finki.wp.lab.service.SongService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SongServiceImpl implements SongService {

    private final SongRepository songRepository;
    private final AlbumRepository albumRepository;
    private final ArtistRepository artistRepository;
    private final ReviewRepository reviewRepository;


    public SongServiceImpl(SongRepository songRepository, AlbumRepository albumRepository, ArtistRepository artistRepository, ReviewRepository reviewRepository) {
        this.songRepository = songRepository;
        this.albumRepository = albumRepository;
        this.artistRepository = artistRepository;
        this.reviewRepository = reviewRepository;
    }

    @Override
    public List<Song> listSongs() {
        return songRepository.findAll();
    }

    public List<Song> findByAlbumId(Long albumId) {
        return this.songRepository.findAllByAlbum_Id(albumId);
    }

    @Override
    public List<Review> getReviewsById(Long id) {
        Song song = songRepository.findById(id).orElseThrow();
        return song.getSongReviews();
    }

    @Override
    public void addReviewToSong(Long id, String reviewText) {
        Song song= songRepository.findById(id)
                .orElseThrow(() -> new SongNotFoundException(id));;
        Review review = new Review(reviewText);
        review.setSongReviewed(song);

        song.getSongReviews().add(review);

        reviewRepository.save(review);
        songRepository.save(song);

    }


    @Override
    public Artist addArtistToSong(Artist a, Song s) {
        Long songId = s.getId();
        Long artistId = a.getId();

        Song song = songRepository.findById(songId)
                .orElseThrow(() -> new SongNotFoundException(songId));
        Artist artist = artistRepository.findById(artistId)
                .orElseThrow(() -> new ArtistNotFoundException(artistId));

        // Add the artist to the song's performers list
        if (!song.getPerformers().contains(artist)) {
            song.getPerformers().add(artist);
        }

        // Add the song to the artist's songs list
        if (!artist.getSongs().contains(song)) {
            artist.getSongs().add(song);
        }

        songRepository.save(song);
        artistRepository.save(artist);

        return artist;
    }

    @Override
    public Song findByTrackId(String trackId) {
        return this.songRepository.findByTrackId(trackId);
    }

    @Override
    public List<Song> searchByTitle(String title) {

        return this.songRepository.findByTitleLike(title);
    }

    @Override
    public Optional<Song> findById(Long id) {
        return this.songRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        this.songRepository.deleteById(id);
    }

    @Override
    public Integer songViews(Long id) {

        Song song = this.songRepository.findById(id).orElseThrow(() -> new SongNotFoundException(id));;
        if (song!=null){
            Integer views = song.getViews();
            views++;
            song.setViews(views);

            // Persist the changes to the database
            this.songRepository.save(song);

            return song.getViews();
        }else{
            throw new SongNotFoundException(id);
        }

    }

    public Optional<Song> save(String trackId, String title, String genre, Integer releaseYear, Long albumId) {

        Album album = this.albumRepository.findById(albumId)
                .orElseThrow(()-> new AlbumNotFoundException(albumId));

        Song song = songRepository.findByTrackId(trackId);

        if (song != null) {
            song.setTitle(title);
            song.setGenre(genre);
            song.setReleaseYear(releaseYear);
            song.setAlbum(album);
        } else {
            song = new Song(trackId, title, genre, releaseYear, new ArrayList<>(), album);
        }

        return Optional.of(this.songRepository.save(song));
    }
}
