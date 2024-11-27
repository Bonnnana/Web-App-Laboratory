package mk.ukim.finki.wp.lab.model;

import lombok.Data;

@Data
public class Artist {
    private long id;
    private String firstName;
    private  String lastName;
    private String bio;

    public Artist(long id, String firstName, String lastName, String bio) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.bio = bio;
    }


}
