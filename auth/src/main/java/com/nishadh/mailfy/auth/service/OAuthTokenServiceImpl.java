package com.nishadh.mailfy.auth.service;

import com.nishadh.mailfy.model.OAuthToken;
import com.nishadh.mailfy.model.User;
import com.nishadh.mailfy.service.OAuthTokenService;
import org.ops4j.pax.cdi.api.OsgiServiceProvider;
import org.ops4j.pax.cdi.api.Properties;
import org.ops4j.pax.cdi.api.Property;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.UUID;

/**
 * Created by nishadh on 12/2/15.
 */
@OsgiServiceProvider(classes = {OAuthTokenService.class})
@Singleton
@Transactional
public class OAuthTokenServiceImpl implements OAuthTokenService {
    @PersistenceContext(unitName="mailfy")
    EntityManager em;

    @Override
    public OAuthToken findToken(String tokenValue) throws NoResultException {
        if (tokenValue.trim().isEmpty()) {
            throw new NoResultException();
        }

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<OAuthToken> cq = cb.createQuery(OAuthToken.class);
        Root e = cq.from(OAuthToken.class);
        cq.where(cb.equal(e.get("tokenValue"), tokenValue));
        return em.createQuery(cq).getSingleResult();
    }

    @Override
    public void deleteToken(String tokenValue) throws NoResultException {
        if (tokenValue.trim().isEmpty()) {
            throw new NoResultException();
        }

        OAuthToken token = findToken(tokenValue);
        em.remove(token);
        em.flush();
    }

    @Override
    public String generateToken(User user, Integer validDays) {
        OAuthToken oAuthToken = new OAuthToken();
        oAuthToken.setUser(user);
        oAuthToken.setTokenValue(UUID.randomUUID().toString());
        oAuthToken.setCreatedAt(new Date());
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, validDays);
        oAuthToken.setValidTill(cal.getTime());

        em.persist(oAuthToken);
        em.flush();
        return oAuthToken.getTokenValue();
    }
}
