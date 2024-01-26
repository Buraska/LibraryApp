package spring.study.springbootapp.models;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "PERSON")
public class Person{
    public Person(Long id, @NonNull String name, @NonNull Date dateOfBirth) {
        this.id = id;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
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


    @NotNull
    @Column(name = "books_limit")
    private int booksLimit = 3;

    @NonNull
    @NotNull(message = "Field cannot be empty")
    @Column(name = "date_of_birth")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd") //TODO date validation
    private Date dateOfBirth;


    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Valid
    @OneToMany(mappedBy = "owner")
    private List<Book> books = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "person_role",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> authorities;


    @Column(unique = true)
    private String username;

    private String password;

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthYear=" + dateOfBirth +
                '}';
    }
}
