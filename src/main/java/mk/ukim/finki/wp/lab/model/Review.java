package mk.ukim.finki.wp.lab.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String reviewText;

    @ManyToOne
    private Song songReviewed;

    public Review() {
    }

    public Review(String reviewText) {
        this.reviewText = reviewText;
    }
}
