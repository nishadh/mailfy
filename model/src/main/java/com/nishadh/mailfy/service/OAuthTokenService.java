package com.nishadh.mailfy.service;

import com.nishadh.mailfy.model.OAuthToken;
import com.nishadh.mailfy.model.User;

import javax.persistence.NoResultException;

/**
 * Created by nishadh on 12/2/15.
 */
public interface OAuthTokenService {
    OAuthToken findToken(String tokenValue) throws NoResultException;
    void deleteToken(String tokenValue) throws NoResultException;
    String generateToken(User user, Integer validDays);
}
