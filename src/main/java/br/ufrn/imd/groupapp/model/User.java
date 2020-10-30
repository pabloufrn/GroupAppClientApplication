package br.ufrn.imd.groupapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@ToString
@AllArgsConstructor
@Data
public class User {
    private Long id;
    private String name;
    private Group group;
}
