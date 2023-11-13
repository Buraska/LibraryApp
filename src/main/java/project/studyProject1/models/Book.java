package project.studyProject1.models;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import project.studyProject1.dao.PersonDao;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Book extends BasicEntity {

    @NonNull
    @NotNull
    @Size(min = 2, max = 255, message = "Name should be between 2 and 255 chars")
    private String bookName;

    @NonNull
    @NotNull
    @Size(min = 2, max = 255, message = "Name should be between 2 and 255 chars")
    private String authorName;

    @NonNull
    @NotNull
    @Min(value = 0, message = "Year should be between 2000 and 2023")
    @Max(value = 2023, message = "Year should be between 2000 and 2023")
    private Integer publicationYear;

    private Long bookOwnerId;
    private Person bookOwner;



}
