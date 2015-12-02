package com.nishadh.mailfy.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * Created by nishadh on 12/2/15.
 */
@Entity
public class OAuthToken {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    Integer id;
    String tokenValue;

    @ManyToOne(fetch = FetchType.EAGER) // Get User Immediately
    User user;
    Date createdAt;
    Date validTill;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTokenValue() {
        return tokenValue;
    }

    public void setTokenValue(String tokenValue) {
        this.tokenValue = tokenValue;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getValidTill() {
        return validTill;
    }

    public void setValidTill(Date validTill) {
        this.validTill = validTill;
    }
}
