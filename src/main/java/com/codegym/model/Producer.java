package com.codegym.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "producers")
public class Producer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    public Producer(String name) {
        this.name = name;
    }

    public Producer() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
