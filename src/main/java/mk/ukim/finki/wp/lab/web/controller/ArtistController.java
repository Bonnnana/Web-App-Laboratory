package mk.ukim.finki.wp.lab.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import mk.ukim.finki.wp.lab.model.Artist;
import mk.ukim.finki.wp.lab.service.ArtistService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import java.util.List;

@Controller
@RequestMapping("/artist")
public class ArtistController {
    private final ArtistService artistService;

    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    @GetMapping
    public String getArtistPage(HttpServletRequest request, Model model){

        List<Artist> artists = artistService.listArtists();
        model.addAttribute("artists", artists);
        String trackId = (String) request.getSession().getAttribute("trackId");
        if (trackId != null) {
            model.addAttribute("trackId", trackId);
        }

       return "artistsList";
    }

    @PostMapping
    protected String addArtist(HttpServletRequest request){
        long id = Long.parseLong(request.getParameter("artistId"));
        Artist artist = artistService.findById(id);
        request.getSession().setAttribute("selectedArtist",artist);
        return "redirect:/songDetails";

    }
}
