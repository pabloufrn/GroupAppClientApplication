package br.ufrn.imd.groupapp.model;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@ToString
@Data
public class Group {
    private Long id;
    private String title;
    private List<User> userList;

    public Group(String title) {
        this.title = title;
    }
}
