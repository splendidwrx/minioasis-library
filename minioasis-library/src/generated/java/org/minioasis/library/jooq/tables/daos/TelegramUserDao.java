/*
 * This file is generated by jOOQ.
*/
package org.minioasis.library.jooq.tables.daos;


import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;
import org.minioasis.library.jooq.tables.TelegramUser;
import org.minioasis.library.jooq.tables.records.TelegramUserRecord;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.8"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class TelegramUserDao extends DAOImpl<TelegramUserRecord, org.minioasis.library.jooq.tables.pojos.TelegramUser, Long> {

    /**
     * Create a new TelegramUserDao without any configuration
     */
    public TelegramUserDao() {
        super(TelegramUser.TELEGRAM_USER, org.minioasis.library.jooq.tables.pojos.TelegramUser.class);
    }

    /**
     * Create a new TelegramUserDao with an attached configuration
     */
    public TelegramUserDao(Configuration configuration) {
        super(TelegramUser.TELEGRAM_USER, org.minioasis.library.jooq.tables.pojos.TelegramUser.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Long getId(org.minioasis.library.jooq.tables.pojos.TelegramUser object) {
        return object.getId();
    }

    /**
     * Fetch records that have <code>ID IN (values)</code>
     */
    public List<org.minioasis.library.jooq.tables.pojos.TelegramUser> fetchById(Long... values) {
        return fetch(TelegramUser.TELEGRAM_USER.ID, values);
    }

    /**
     * Fetch a unique record that has <code>ID = value</code>
     */
    public org.minioasis.library.jooq.tables.pojos.TelegramUser fetchOneById(Long value) {
        return fetchOne(TelegramUser.TELEGRAM_USER.ID, value);
    }

    /**
     * Fetch records that have <code>CARD_KEY IN (values)</code>
     */
    public List<org.minioasis.library.jooq.tables.pojos.TelegramUser> fetchByCardKey(String... values) {
        return fetch(TelegramUser.TELEGRAM_USER.CARD_KEY, values);
    }

    /**
     * Fetch records that have <code>CHAT_ID IN (values)</code>
     */
    public List<org.minioasis.library.jooq.tables.pojos.TelegramUser> fetchByChatId(Long... values) {
        return fetch(TelegramUser.TELEGRAM_USER.CHAT_ID, values);
    }

    /**
     * Fetch records that have <code>DONT_REMIND_AGAIN IN (values)</code>
     */
    public List<org.minioasis.library.jooq.tables.pojos.TelegramUser> fetchByDontRemindAgain(Boolean... values) {
        return fetch(TelegramUser.TELEGRAM_USER.DONT_REMIND_AGAIN, values);
    }

    /**
     * Fetch records that have <code>REMIND_AGAIN_IN_THE_LAST_DAY IN (values)</code>
     */
    public List<org.minioasis.library.jooq.tables.pojos.TelegramUser> fetchByRemindAgainInTheLastDay(Boolean... values) {
        return fetch(TelegramUser.TELEGRAM_USER.REMIND_AGAIN_IN_THE_LAST_DAY, values);
    }

    /**
     * Fetch records that have <code>REMINDER IN (values)</code>
     */
    public List<org.minioasis.library.jooq.tables.pojos.TelegramUser> fetchByReminder(Boolean... values) {
        return fetch(TelegramUser.TELEGRAM_USER.REMINDER, values);
    }

    /**
     * Fetch records that have <code>SENDME_ANNOUCEMENT IN (values)</code>
     */
    public List<org.minioasis.library.jooq.tables.pojos.TelegramUser> fetchBySendmeAnnoucement(Boolean... values) {
        return fetch(TelegramUser.TELEGRAM_USER.SENDME_ANNOUCEMENT, values);
    }

    /**
     * Fetch records that have <code>SENDME_ARTICLE IN (values)</code>
     */
    public List<org.minioasis.library.jooq.tables.pojos.TelegramUser> fetchBySendmeArticle(Boolean... values) {
        return fetch(TelegramUser.TELEGRAM_USER.SENDME_ARTICLE, values);
    }

    /**
     * Fetch records that have <code>SENDME_EVENTS IN (values)</code>
     */
    public List<org.minioasis.library.jooq.tables.pojos.TelegramUser> fetchBySendmeEvents(Boolean... values) {
        return fetch(TelegramUser.TELEGRAM_USER.SENDME_EVENTS, values);
    }

    /**
     * Fetch records that have <code>SENDME_NEW_RELEASE IN (values)</code>
     */
    public List<org.minioasis.library.jooq.tables.pojos.TelegramUser> fetchBySendmeNewRelease(Boolean... values) {
        return fetch(TelegramUser.TELEGRAM_USER.SENDME_NEW_RELEASE, values);
    }

    /**
     * Fetch records that have <code>SENDME_PROMOTION IN (values)</code>
     */
    public List<org.minioasis.library.jooq.tables.pojos.TelegramUser> fetchBySendmePromotion(Boolean... values) {
        return fetch(TelegramUser.TELEGRAM_USER.SENDME_PROMOTION, values);
    }
}
