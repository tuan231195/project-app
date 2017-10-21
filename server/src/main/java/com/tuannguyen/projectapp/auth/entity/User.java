package com.tuannguyen.projectapp.auth.entity;

import com.tuannguyen.projectapp.auth.model.Authority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "user")
public class User implements Serializable {
    public static String DISPLAY_TABLE_NAME = "User";
    public static String TABLE_NAME = "user";
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    private String title;

    @OneToOne
    @JoinColumn(name = "access_level")
    private AccessLevel accessLevel;

    @Transient
    private List<Authority> authorities;

    @PostLoad
    public void loadAuthority() {
        if (accessLevel != null)
            authorities = Collections.singletonList(new Authority(accessLevel));
    }

    @Column(name = "username")
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserDetails toUserDetails() {
        return new org.springframework.security.core.userdetails.User(username, password, isActive(), true, true, true, getAuthorities());
    }

    public List<Authority> getAuthorities() {
        return authorities;
    }

    public boolean isActive() {
        return !(AccessLevel.NEW.equals(accessLevel.getName()) || userStatus == UserStatus.INACTIVE);
    }

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "lastname")
    private String lastName;

    @Column(name = "status")
    private UserStatus userStatus;

    @Column(name = "password")
    private String password;

    @Column(name = "joinDate")
    @Temporal(TemporalType.DATE)
    private Date joinedDate;

    private String email;

    @Column(name = "street_1")
    private String street1;

    @Column(name = "street_2")
    private String street2;
    private String city;
    private String state;
    private String country;

    @Column(name = "postcode")
    private int postCode;

    private Date dob;
    private String phone;

    public String getId() {
        return id;
    }

    public AccessLevel getAccessLevel() {
        return accessLevel;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public UserStatus getUserStatus() {
        return userStatus;
    }

    public String getPassword() {
        return password;
    }

    public Date getJoinedDate() {
        return joinedDate;
    }

    public String getEmail() {
        return email;
    }

    public String getStreet1() {
        return street1;
    }

    public String getStreet2() {
        return street2;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getCountry() {
        return country;
    }

    public int getPostCode() {
        return postCode;
    }

    public Date getDob() {
        return dob;
    }

    public String getPhone() {
        return phone;
    }

    public void setAccessLevel(AccessLevel accessLevel) {
        this.accessLevel = accessLevel;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setJoinedDate(Date joinedDate) {
        this.joinedDate = joinedDate;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setStreet1(String street1) {
        this.street1 = street1;
    }

    public void setStreet2(String street2) {
        this.street2 = street2;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setPostCode(int postCode) {
        this.postCode = postCode;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
