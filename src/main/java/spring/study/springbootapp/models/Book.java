package spring.study.springbootapp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
public class Book {

    @Id
    @SequenceGenerator(name = "seq1", sequenceName = "seq1", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq1")
    protected Long id;

    @NonNull
    @NotNull
    @Size(min = 2, max = 255, message = "Name should be between 2 and 255 chars")
    @Column(name = "book_name")
    private String bookName;

    @NonNull
    @NotNull
    @Size(min = 2, max = 255, message = "Name should be between 2 and 255 chars")
    @Column(name = "author_name")
    private String authorName;

    @NonNull
    @NotNull
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "publication_date")
    private Date publicationDate;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person owner;

    @Column(name = "was_taken_at")
    @Temporal(TemporalType.DATE)
    private Date wasTakenAt;


    public boolean hasOwner(){
        return getOwner() != null;
    }
    public boolean isBookExpired(){
        if (getWasTakenAt() == null){
            return false;
        }
        long expirationThreshold = 10 * 24 * 60 * 60 * 1000L;
        long timeDifference = System.currentTimeMillis() - getWasTakenAt().getTime();
        return timeDifference > expirationThreshold;
    }
}
