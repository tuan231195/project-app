package com.tuannguyen.projectapp.auth.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tuannguyen.projectapp.core.json.BaseDTO;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserForm implements BaseDTO {
    @Unique(tableName = "user", fieldName = "username")
    @NotEmpty(message = "Username must not be empty")
    private String username;

    @NotEmpty(message = "Email must not be empty")
    @Unique(tableName = "user", fieldName = "email")
    @Email
    private String email;

    @NotEmpty(message = "Password must not be empty")
    @Password(minLength = 4)
    private String password;

    @NotEmpty(message = "Confirm password must not be empty")
    @Password(minLength = 4)
    private String confirmPassword;

    private String street1;

    private String street2;

    private String city;

    private String state;

    private String country;

    private int postCode;

    private Date dob;

    private String phone;

    private String title;

    @NotEmpty(message = "Last name must not be empty")
    private String lastName;

    @NotEmpty(message = "First name must not be empty")
    private String firstName;

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    @JsonIgnore
    public String getConfirmPassword() {
        return confirmPassword;
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

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getTitle() {
        return title;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    @JsonProperty
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @JsonIgnore
    public String getPassword() {
        return this.password;
    }

    @Override
    public List<String> getRequiredFields() {
        return new ArrayList<>(requiredFields);
    }

    private List<String> requiredFields = new ArrayList<>(Arrays.asList(
            "username", "email", "title", "firstName", "lastName"
    ));
}
