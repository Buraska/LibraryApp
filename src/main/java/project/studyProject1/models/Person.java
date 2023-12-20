package project.studyProject1.models;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "PERSON")
public class Person extends BasicEntity{
    public Person(Long id, @NonNull String name, @NonNull Integer birthYear) {
        this.id = id;
        this.name = name;
        this.birthYear = birthYear;
    }

    public Person(Long id) {
        this.id = id;
    }

    @Id
    @SequenceGenerator(name = "seq1", sequenceName = "seq1", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq1")
    protected Long id;

    @NonNull
    @NotNull
    @Size(min = 1, max = 20, message = "Name should be from 1 to 20 characters")
    private String name;

    @NonNull
    @NotNull(message = "Field cannot be empty")
    @Min(value = 1900, message = "Value should be between 1900 and 2023")
    @Max(value = 2023, message = "Value should be between 1900 and 2023")
    @Column(name = "birth_year")
    private Integer birthYear;

    @Valid
    @OneToMany(mappedBy = "owner")

    private List<Book> books;

    public void addBook(Book book) {
        if (books == null){
            books = new ArrayList<>();
        }
        books.add(book);
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthYear=" + birthYear +
                '}';
    }
}
