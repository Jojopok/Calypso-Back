package org.calypso.calypso.dto.auth;

public class UserDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String avatar;
    private Integer phoneNumber;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
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