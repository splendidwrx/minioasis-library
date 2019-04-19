/*
 * This file is generated by jOOQ.
*/
package org.minioasis.library.jooq.tables.daos;


import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;
import org.minioasis.library.jooq.tables.Biblio;
import org.minioasis.library.jooq.tables.records.BiblioRecord;


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
public class BiblioDao extends DAOImpl<BiblioRecord, org.minioasis.library.jooq.tables.pojos.Biblio, Long> {

    /**
     * Create a new BiblioDao without any configuration
     */
    public BiblioDao() {
        super(Biblio.BIBLIO, org.minioasis.library.jooq.tables.pojos.Biblio.class);
    }

    /**
     * Create a new BiblioDao with an attached configuration
     */
    public BiblioDao(Configuration configuration) {
        super(Biblio.BIBLIO, org.minioasis.library.jooq.tables.pojos.Biblio.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Long getId(org.minioasis.library.jooq.tables.pojos.Biblio object) {
        return object.getId();
    }

    /**
     * Fetch records that have <code>ID IN (values)</code>
     */
    public List<org.minioasis.library.jooq.tables.pojos.Biblio> fetchById(Long... values) {
        return fetch(Biblio.BIBLIO.ID, values);
    }

    /**
     * Fetch a unique record that has <code>ID = value</code>
     */
    public org.minioasis.library.jooq.tables.pojos.Biblio fetchOneById(Long value) {
        return fetchOne(Biblio.BIBLIO.ID, value);
    }

    /**
     * Fetch records that have <code>ACTIVE IN (values)</code>
     */
    public List<org.minioasis.library.jooq.tables.pojos.Biblio> fetchByActive(String... values) {
        return fetch(Biblio.BIBLIO.ACTIVE, values);
    }

    /**
     * Fetch records that have <code>AUTHOR IN (values)</code>
     */
    public List<org.minioasis.library.jooq.tables.pojos.Biblio> fetchByAuthor(String... values) {
        return fetch(Biblio.BIBLIO.AUTHOR, values);
    }

    /**
     * Fetch records that have <code>BIBLIO_TYPE IN (values)</code>
     */
    public List<org.minioasis.library.jooq.tables.pojos.Biblio> fetchByBiblioType(String... values) {
        return fetch(Biblio.BIBLIO.BIBLIO_TYPE, values);
    }

    /**
     * Fetch records that have <code>BINDING IN (values)</code>
     */
    public List<org.minioasis.library.jooq.tables.pojos.Biblio> fetchByBinding(String... values) {
        return fetch(Biblio.BIBLIO.BINDING, values);
    }

    /**
     * Fetch records that have <code>CLASS_MARK IN (values)</code>
     */
    public List<org.minioasis.library.jooq.tables.pojos.Biblio> fetchByClassMark(String... values) {
        return fetch(Biblio.BIBLIO.CLASS_MARK, values);
    }

    /**
     * Fetch records that have <code>DESCRIPTION IN (values)</code>
     */
    public List<org.minioasis.library.jooq.tables.pojos.Biblio> fetchByDescription(String... values) {
        return fetch(Biblio.BIBLIO.DESCRIPTION, values);
    }

    /**
     * Fetch records that have <code>EDITION IN (values)</code>
     */
    public List<org.minioasis.library.jooq.tables.pojos.Biblio> fetchByEdition(String... values) {
        return fetch(Biblio.BIBLIO.EDITION, values);
    }

    /**
     * Fetch records that have <code>ISBN IN (values)</code>
     */
    public List<org.minioasis.library.jooq.tables.pojos.Biblio> fetchByIsbn(String... values) {
        return fetch(Biblio.BIBLIO.ISBN, values);
    }

    /**
     * Fetch records that have <code>CODEN IN (values)</code>
     */
    public List<org.minioasis.library.jooq.tables.pojos.Biblio> fetchByCoden(String... values) {
        return fetch(Biblio.BIBLIO.CODEN, values);
    }

    /**
     * Fetch records that have <code>ISSN IN (values)</code>
     */
    public List<org.minioasis.library.jooq.tables.pojos.Biblio> fetchByIssn(String... values) {
        return fetch(Biblio.BIBLIO.ISSN, values);
    }

    /**
     * Fetch records that have <code>LANGUAGE IN (values)</code>
     */
    public List<org.minioasis.library.jooq.tables.pojos.Biblio> fetchByLanguage(String... values) {
        return fetch(Biblio.BIBLIO.LANGUAGE, values);
    }

    /**
     * Fetch records that have <code>NOTE IN (values)</code>
     */
    public List<org.minioasis.library.jooq.tables.pojos.Biblio> fetchByNote(String... values) {
        return fetch(Biblio.BIBLIO.NOTE, values);
    }

    /**
     * Fetch records that have <code>PAGES IN (values)</code>
     */
    public List<org.minioasis.library.jooq.tables.pojos.Biblio> fetchByPages(Integer... values) {
        return fetch(Biblio.BIBLIO.PAGES, values);
    }

    /**
     * Fetch records that have <code>PUBLICATION_PLACE IN (values)</code>
     */
    public List<org.minioasis.library.jooq.tables.pojos.Biblio> fetchByPublicationPlace(String... values) {
        return fetch(Biblio.BIBLIO.PUBLICATION_PLACE, values);
    }

    /**
     * Fetch records that have <code>PUBLISHING_YEAR IN (values)</code>
     */
    public List<org.minioasis.library.jooq.tables.pojos.Biblio> fetchByPublishingYear(Integer... values) {
        return fetch(Biblio.BIBLIO.PUBLISHING_YEAR, values);
    }

    /**
     * Fetch records that have <code>TOPIC IN (values)</code>
     */
    public List<org.minioasis.library.jooq.tables.pojos.Biblio> fetchByTopic(String... values) {
        return fetch(Biblio.BIBLIO.TOPIC, values);
    }

    /**
     * Fetch records that have <code>TITLE IN (values)</code>
     */
    public List<org.minioasis.library.jooq.tables.pojos.Biblio> fetchByTitle(String... values) {
        return fetch(Biblio.BIBLIO.TITLE, values);
    }

    /**
     * Fetch records that have <code>UPDATED IN (values)</code>
     */
    public List<org.minioasis.library.jooq.tables.pojos.Biblio> fetchByUpdated(Timestamp... values) {
        return fetch(Biblio.BIBLIO.UPDATED, values);
    }

    /**
     * Fetch records that have <code>IMAGE_ID IN (values)</code>
     */
    public List<org.minioasis.library.jooq.tables.pojos.Biblio> fetchByImageId(Long... values) {
        return fetch(Biblio.BIBLIO.IMAGE_ID, values);
    }

    /**
     * Fetch records that have <code>PUBLISHER_ID IN (values)</code>
     */
    public List<org.minioasis.library.jooq.tables.pojos.Biblio> fetchByPublisherId(Long... values) {
        return fetch(Biblio.BIBLIO.PUBLISHER_ID, values);
    }

    /**
     * Fetch records that have <code>SERIES_ID IN (values)</code>
     */
    public List<org.minioasis.library.jooq.tables.pojos.Biblio> fetchBySeriesId(Long... values) {
        return fetch(Biblio.BIBLIO.SERIES_ID, values);
    }
}