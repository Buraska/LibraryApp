package spring.study.springbootapp.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchParams {
    public Integer pageNum;

    public String query;

    public String sortBy;


}
