package org.calypso.calypso.model.algo;

import jakarta.persistence.*;

@Entity
@Table(name = "T_ALGO_Difficulty")
public class Difficulty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "difficulty", length = 250, nullable = false)
    private String difficulty;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }
}