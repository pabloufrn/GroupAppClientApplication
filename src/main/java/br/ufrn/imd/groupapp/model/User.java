package br.ufrn.imd.groupapp.model;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class User {
    private String name;
    private Long id;
    private Group group;
}
