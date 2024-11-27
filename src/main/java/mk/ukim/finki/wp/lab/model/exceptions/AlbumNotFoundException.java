package mk.ukim.finki.wp.lab.model.exceptions;

public class AlbumNotFoundException extends RuntimeException{
    public AlbumNotFoundException(Long id) {
        super(String.format("Album with id %d was not found", id));
    }
}
