package org.calypso.calypso.model.algo;

import jakarta.persistence.*;
import org.calypso.calypso.model.auth.User;

@Entity
@Table(name = "T_ALGO_BUG")
public class Bug {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "content", columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(name = "is_resolved", nullable = false)
    private Boolean isResolved;

    @ManyToOne
    @JoinColumn(name = "algo_id", nullable = false)
    private Algo algo;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getIsResolved() {
        return isResolved;
    }

    public void setIsResolved(Boolean isResolved) {
        this.isResolved = isResolved;
    }

    public Algo getAlgo() {
        return algo;
    }

    public void setAlgo(Algo algo) {
        this.algo = algo;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}