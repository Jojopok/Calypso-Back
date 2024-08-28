package org.calypso.calypso.model;

import jakarta.persistence.*;

@Entity
@Table(name = "T_AUTH_User")
public class User {

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

    @Column(name = "odyssey_link", length = 250)
    private String odysseyLink;

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
}