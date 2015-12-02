package com.nishadh.mailfy.service;

import com.nishadh.mailfy.model.EmailAlert;
import com.nishadh.mailfy.model.User;

import java.util.Date;
import java.util.List;

/**
 * Created by nishadh on 12/2/15.
 */
public interface EmailAlertService {
    void create(EmailAlert emailAlert);
    void update(EmailAlert emailAlert);
    void delete(Integer id);

    EmailAlert findById(Integer id);
    List<EmailAlert> findByUser(User user);
    List<EmailAlert> findByDate(Date date);
    List<EmailAlert> findAll();
}
