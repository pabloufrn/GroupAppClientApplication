package br.ufrn.imd.groupapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.Date;

@Data
@AllArgsConstructor
public class Message {
    private Long id;
    private String text;
    private String author;
    private Group group;
    private Date date;
}