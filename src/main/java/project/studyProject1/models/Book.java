package project.studyProject1.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
public class Book extends BasicEntity{

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
    @Min(value = 0, message = "Year should be between 2000 and 2023")
    @Max(value = 2023, message = "Year should be between 2000 and 2023")
    @Column(name = "publication_year")
    private Integer publicationYear;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person owner;



}
