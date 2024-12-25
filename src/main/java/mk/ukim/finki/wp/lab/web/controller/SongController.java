package mk.ukim.finki.wp.lab.web.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mk.ukim.finki.wp.lab.model.Album;
import mk.ukim.finki.wp.lab.model.Song;
import mk.ukim.finki.wp.lab.service.AlbumService;
import mk.ukim.finki.wp.lab.service.SongService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;


import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/songs")
public class SongController {
    private final SongService songService;
    private final AlbumService albumService;

    public SongController(SongService songService, AlbumService albumService) {
        this.songService = songService;
        this.albumService = albumService;
    }

    @GetMapping
    public String getSongsPage(@RequestParam(required = false) Long albumId,
                               @RequestParam(required = false) String error,
                               Model model) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }

        // Fetch all albums for the dropdown
        List<Album> albums = this.albumService.findAll();
        model.addAttribute("albums", albums);

        // Fetch all songs for the main table
        List<Song> songs = this.songService.listSongs();
        model.addAttribute("songs", songs);

        // Fetch filtered songs if albumId is provided
        if (albumId != null) {
            List<Song> filteredSongs = this.songService.findByAlbumId(albumId);
            model.addAttribute("filteredSongs", filteredSongs);
            model.addAttribute("selectedAlbumId", albumId);
        }

        return "listSongs";
    }


    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public String saveSong(@RequestParam String trackId,
                           @RequestParam String title,
                           @RequestParam String genre,
                           @RequestParam Integer releaseYear,
                           @RequestParam Long albumId){

        this.songService.save(trackId, title, genre, releaseYear, albumId);
        return "redirect:/songs";
    }

    @GetMapping("/edit/{songId}")
    @PreAuthorize("hasRole('ADMIN')")
    public String editSong(@PathVariable Long songId,
                           @RequestParam String title,
                           @RequestParam String trackId,
                           @RequestParam String genre,
                           @RequestParam Integer releaseYear,
                           @RequestParam Long albumId) {
        if (this.songService.findById(songId).isPresent()) {

            this.songService.save(trackId, title, genre, releaseYear, albumId);
        }
        return "redirect:/songs";
    }


    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteSong(@PathVariable Long id){
        this.songService.deleteById(id);
        return "redirect:/songs";
    }

    @GetMapping("/edit-form/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String getEditSongForm(@PathVariable Long id, Model model){
        if(this.songService.findById(id).isPresent()){
            Song song = this.songService.findById(id).get();
            List<Album> albums = this.albumService.findAll();
            model.addAttribute("albums", albums);
            model.addAttribute("song", song);
            return "add-song";
        }
        return "redirect:/songs?error=SongNotFound";

    }

    @GetMapping("/add-form")
    @PreAuthorize("hasRole('ADMIN')")
    public String addSongPage(Model model){
        List<Album> albums = this.albumService.findAll();
        model.addAttribute("albums", albums);
        return "add-song";
    }

    @PostMapping("/submit")
    public String getArtistPage(HttpServletRequest req, Model model){

        String trackId = req.getParameter("trackId");
        if (trackId == null || trackId.isEmpty()) {
            return "redirect:/songs?error=SongNotSelected";
        }


        req.getSession().setAttribute("trackId", trackId);
        req.getSession().setAttribute("songId", songService.findByTrackId(trackId).getId());
        return "redirect:/artist";
    }

}
