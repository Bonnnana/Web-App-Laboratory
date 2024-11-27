package mk.ukim.finki.wp.lab.model.exceptions;

public class SongNotFoundException extends RuntimeException{
    public SongNotFoundException(Long id) {
        super(String.format("Song with id %d was not found", id));
    }
}
