package com.nishadh.mailfy.persistence.service;

import com.nishadh.mailfy.model.User;
import com.nishadh.mailfy.service.UserService;
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
import java.security.MessageDigest;
import java.util.Collection;
import java.util.Optional;

/**
 * Created by nishadh on 12/2/15.
 */

@OsgiServiceProvider(classes = {UserService.class})
@Singleton
@Transactional
public class UserServiceImpl implements UserService {
    @PersistenceContext(unitName="mailfy")
    EntityManager em;

    @Override
    public User findById(Integer id) throws NoResultException
    {
        return em.find(User.class, id);
    }

    @Override
    public User findByUserEmail(String email) throws NoResultException  {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root e = cq.from(User.class);
        cq.where(cb.equal(e.get("email"), email));
        return em.createQuery(cq).getSingleResult();
    }

    @Override
    public User findByEmailAndPassword(String email, String password) throws NoResultException {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root e = cq.from(User.class);
        cq.where(cb.and(cb.equal(e.get("email"), email),
                cb.equal(e.get("password"), UserServiceImpl.sha256(password))));
        return em.createQuery(cq).getSingleResult();
    }

    @Override
    public void create(User user) {
        user.setPassword(UserServiceImpl.sha256(user.getPassword()));
        em.persist(user);
        em.flush();
    }

    @Override
    public void update(User user) {

    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public Collection<User> findAll() {
        return null;
    }

    public static String sha256(String base) {
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(base.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }
}