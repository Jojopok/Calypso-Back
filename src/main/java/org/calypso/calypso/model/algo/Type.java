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

    @Column(name = "color", nullable = true)
    private String color;

    @Column(name = "logo", nullable = true)
    private String logo;

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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setAlgos(Set<Algo> algos) {
        this.algos = algos;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
}