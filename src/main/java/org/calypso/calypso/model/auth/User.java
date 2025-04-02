package org.calypso.calypso.model.auth;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "T_AUTH_User")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", length = 250, nullable = false)
    private String firstName;

    @Column(name = "last_name", length = 250, nullable = false)
    private String lastName;

    @Column(name = "password", length = 250, nullable = false)
    private String password;

    @Column(name = "email", length = 250, nullable = false)
    private String email;

    @Column(name = "avatar", length = 250)
    private String avatar;

    @Column(name = "phone_number")
    private Integer phoneNumber;

    @Column(name = "odyssey_link", length = 250, nullable = false)
    private String odysseyLink;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "T_AUTH_Role_User",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    // Méthode pour récupérer uniquement les IDs des rôles
    public Set<Long> getRoleIds() {
        return roles.stream()
                .map(Role::getId) // Récupère les IDs des rôles
                .collect(Collectors.toSet());
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "T_AUTH_Promo_User",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "promo_id")
    )
    private Set<Promo> promos = new HashSet<>();

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Integer phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getOdysseyLink() {
        return odysseyLink;
    }

    public void setOdysseyLink(String odysseyLink) {
        this.odysseyLink = odysseyLink;
    }

    public Set<Promo> getPromos() {
        return promos;
    }

    public void setPromos(Set<Promo> promos) {
        this.promos = promos;
    }


    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getRole()))
                .collect(Collectors.toSet());
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}