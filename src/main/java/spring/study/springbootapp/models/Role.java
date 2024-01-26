package spring.study.springbootapp.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "role")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq1")
    @SequenceGenerator(name = "seq1", sequenceName = "seq1", allocationSize = 1)
    private Long id;

    private String authority;


    @Override
    public String getAuthority() {
        return this.authority;
    }
}
