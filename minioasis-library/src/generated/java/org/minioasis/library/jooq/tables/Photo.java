/*
 * This file is generated by jOOQ.
*/
package org.minioasis.library.jooq.tables;


import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;
import org.minioasis.library.jooq.Indexes;
import org.minioasis.library.jooq.Keys;
import org.minioasis.library.jooq.Public;
import org.minioasis.library.jooq.tables.records.PhotoRecord;


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
public class Photo extends TableImpl<PhotoRecord> {

    private static final long serialVersionUID = -1900282995;

    /**
     * The reference instance of <code>PUBLIC.PHOTO</code>
     */
    public static final Photo PHOTO = new Photo();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<PhotoRecord> getRecordType() {
        return PhotoRecord.class;
    }

    /**
     * The column <code>PUBLIC.PHOTO.ID</code>.
     */
    public final TableField<PhotoRecord, Long> ID = createField("ID", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>PUBLIC.PHOTO.DESCRIPTION</code>.
     */
    public final TableField<PhotoRecord, String> DESCRIPTION = createField("DESCRIPTION", org.jooq.impl.SQLDataType.VARCHAR(255), this, "");

    /**
     * The column <code>PUBLIC.PHOTO.IMG</code>.
     */
    public final TableField<PhotoRecord, byte[]> IMG = createField("IMG", org.jooq.impl.SQLDataType.BLOB, this, "");

    /**
     * The column <code>PUBLIC.PHOTO.LINK</code>.
     */
    public final TableField<PhotoRecord, String> LINK = createField("LINK", org.jooq.impl.SQLDataType.VARCHAR(255), this, "");

    /**
     * The column <code>PUBLIC.PHOTO.NAME</code>.
     */
    public final TableField<PhotoRecord, String> NAME = createField("NAME", org.jooq.impl.SQLDataType.VARCHAR(255), this, "");

    /**
     * The column <code>PUBLIC.PHOTO.SIZE</code>.
     */
    public final TableField<PhotoRecord, Integer> SIZE = createField("SIZE", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * Create a <code>PUBLIC.PHOTO</code> table reference
     */
    public Photo() {
        this(DSL.name("PHOTO"), null);
    }

    /**
     * Create an aliased <code>PUBLIC.PHOTO</code> table reference
     */
    public Photo(String alias) {
        this(DSL.name(alias), PHOTO);
    }

    /**
     * Create an aliased <code>PUBLIC.PHOTO</code> table reference
     */
    public Photo(Name alias) {
        this(alias, PHOTO);
    }

    private Photo(Name alias, Table<PhotoRecord> aliased) {
        this(alias, aliased, null);
    }

    private Photo(Name alias, Table<PhotoRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.PRIMARY_KEY_48);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<PhotoRecord> getPrimaryKey() {
        return Keys.CONSTRAINT_48;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<PhotoRecord>> getKeys() {
        return Arrays.<UniqueKey<PhotoRecord>>asList(Keys.CONSTRAINT_48);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Photo as(String alias) {
        return new Photo(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Photo as(Name alias) {
        return new Photo(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Photo rename(String name) {
        return new Photo(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Photo rename(Name name) {
        return new Photo(name, null);
    }
}