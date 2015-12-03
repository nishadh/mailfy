package com.nishadh.mailfy.persistence.service;

import com.nishadh.mailfy.model.EmailAlert;
import com.nishadh.mailfy.model.User;
import com.nishadh.mailfy.service.EmailAlertService;
import org.ops4j.pax.cdi.api.OsgiServiceProvider;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

/**
 * Created by nishadh on 12/2/15.
 */

@OsgiServiceProvider(classes = {EmailAlertService.class})
@Singleton
@Transactional
public class EmailAlertServiceImpl implements EmailAlertService {
    @PersistenceContext(unitName="mailfy")
    EntityManager em;

    @Override
    public void create(EmailAlert emailAlert) {
        em.persist(emailAlert);
        em.flush();
    }

    @Override
    public void update(EmailAlert emailAlert) {

    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public EmailAlert findById(Integer id) {
        return em.find(EmailAlert.class, id);
    }

    @Override
    public List<EmailAlert> findByUser(User user) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<EmailAlert> cq = cb.createQuery(EmailAlert.class);
        Root<EmailAlert> emailRoot = cq.from(EmailAlert.class);
        Join<EmailAlert, User> userRoot = emailRoot.join("user");
        cq.where(cb.equal(userRoot.get("id"), user.getId()));
        return em.createQuery(cq).getResultList();
    }

    @Override
    public List<EmailAlert> findByDate(Date date) {
        return null;
    }

    @Override
    public List<EmailAlert> findAll() {
        return null;
    }
}
