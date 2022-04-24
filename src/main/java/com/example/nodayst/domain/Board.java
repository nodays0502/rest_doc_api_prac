package com.example.nodayst.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "BOARD")
@Getter
@NoArgsConstructor
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String title;

    String desc;

    public Board(Long id, String title, String desc) {
        this.id = id;
        this.title = title;
        this.desc = desc;
    }
    public Board(String title, String desc) {
        this.title = title;
        this.desc = desc;
    }
    public void changeTitle(String title){
        this.title = title;
    }
    public void changeDesc(String desc){
        this.desc = desc;
    }
}
