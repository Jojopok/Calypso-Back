package org.calypso.calypso.model.algo;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "T_ALGO_Type")
public class Type {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "type", length = 250, nullable = false)
    private String type;

    @ManyToMany(mappedBy = "types")
    private Set<Algo> algos;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Set<Algo> getAlgos() {
        return algos;
    }

    public void setAlgos(Set<Algo> algos) {
        this.algos = algos;
    }
}