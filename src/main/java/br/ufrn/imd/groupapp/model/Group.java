package br.ufrn.imd.groupapp.model;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class Group {
    private Long id;
    private String title;
    private List<User> userList;
}
