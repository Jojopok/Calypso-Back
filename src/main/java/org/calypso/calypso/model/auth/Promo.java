package org.calypso.calypso.model.auth;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "T_AUTH_Promo")
public class Promo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "type", length = 100, nullable = false)
    private String type;

    @Column(name = "city", length = 100, nullable = false)
    private String city;

    @Column(name = "begin_at", nullable = false)
    private int beginAt;

    @Column(name = "is_visible", nullable = false)
    private boolean isVisible;

    @ManyToMany(mappedBy = "promos")
    private Set<User> users;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getBeginAt() {
        return beginAt;
    }

    public void setBeginAt(int beginAt) {
        this.beginAt = beginAt;
    }

    public boolean getIsVisible() {
        return isVisible;
    }

    public void setIsVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}