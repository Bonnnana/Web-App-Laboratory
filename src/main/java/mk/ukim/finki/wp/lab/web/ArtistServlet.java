package mk.ukim.finki.wp.lab.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mk.ukim.finki.wp.lab.model.Artist;
import mk.ukim.finki.wp.lab.model.Song;
import mk.ukim.finki.wp.lab.service.ArtistService;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name="artistServlet", urlPatterns="/artistServlet")
public class ArtistServlet extends HttpServlet {
    private final ArtistService artistService;
    private final SpringTemplateEngine springTemplateEngine;

    public ArtistServlet(ArtistService artistService, SpringTemplateEngine springTemplateEngine) {
        this.artistService = artistService;
        this.springTemplateEngine = springTemplateEngine;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        IWebExchange webExchange = JakartaServletWebApplication
                .buildApplication(getServletContext())
                .buildExchange(req, resp);
        WebContext context = new WebContext(webExchange);

        List<Artist> artists = artistService.listArtists();
        context.setVariable("artists", artists);
        String trackId = (String) req.getSession().getAttribute("trackId");
        context.setVariable("trackId", trackId);

        springTemplateEngine.process("artistsList.html",context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long id = Long.parseLong(req.getParameter("artistId"));
        Artist artist = artistService.findById(id);
        req.getSession().setAttribute("selectedArtist",artist);
        resp.sendRedirect("/songDetails");
    }
}
