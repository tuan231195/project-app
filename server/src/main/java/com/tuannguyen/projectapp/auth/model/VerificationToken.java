package com.tuannguyen.projectapp.auth.model;

import com.tuannguyen.projectapp.auth.entity.User;
import com.tuannguyen.projectapp.util.model.DateUtils;

import javax.persistence.*;
import java.security.Timestamp;
import java.util.Calendar;
import java.util.Date;

@Entity
@Table(name = "user_verify")
public class VerificationToken {
    private static final int EXPIRATION = DateUtils.SECONDS_IN_DAYS;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String token;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private TokenType type;

    @ManyToOne(targetEntity = User.class)
    @JoinColumns({
            @JoinColumn(name = "email", referencedColumnName = "email"),
            @JoinColumn(name = "username", referencedColumnName = "username")
    })
    private User user;

    @Column(name = "email", insertable = false, updatable = false)
    private String email;

    @Column(name = "username", insertable = false, updatable = false)
    private String username;

    @Temporal(TemporalType.DATE)
    private Date created;

    @Transient
    private Date expiryDate;

    @PostLoad
    private void afterLoad() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(created);
        cal.add(Calendar.SECOND, EXPIRATION);
        expiryDate = cal.getTime();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public enum TokenType {
        PASSWORD, REGISTRATION;

        public static TokenType getEnum(String value) {
            for (TokenType v : values())
                if (v.name().equalsIgnoreCase(value)) return v;
            throw new IllegalArgumentException();
        }
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public TokenType getType() {
        return type;
    }

    public void setType(TokenType type) {
        this.type = type;
    }
}