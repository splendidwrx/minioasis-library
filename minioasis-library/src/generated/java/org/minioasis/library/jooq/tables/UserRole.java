/*
 * This file is generated by jOOQ.
*/
package org.minioasis.library.jooq.tables;


import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.ForeignKey;
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
import org.minioasis.library.jooq.tables.records.UserRoleRecord;


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
public class UserRole extends TableImpl<UserRoleRecord> {

    private static final long serialVersionUID = 1399850336;

    /**
     * The reference instance of <code>PUBLIC.USER_ROLE</code>
     */
    public static final UserRole USER_ROLE = new UserRole();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<UserRoleRecord> getRecordType() {
        return UserRoleRecord.class;
    }

    /**
     * The column <code>PUBLIC.USER_ROLE.USER_ID</code>.
     */
    public final TableField<UserRoleRecord, Long> USER_ID = createField("USER_ID", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>PUBLIC.USER_ROLE.ROLE_ID</code>.
     */
    public final TableField<UserRoleRecord, Long> ROLE_ID = createField("ROLE_ID", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * Create a <code>PUBLIC.USER_ROLE</code> table reference
     */
    public UserRole() {
        this(DSL.name("USER_ROLE"), null);
    }

    /**
     * Create an aliased <code>PUBLIC.USER_ROLE</code> table reference
     */
    public UserRole(String alias) {
        this(DSL.name(alias), USER_ROLE);
    }

    /**
     * Create an aliased <code>PUBLIC.USER_ROLE</code> table reference
     */
    public UserRole(Name alias) {
        this(alias, USER_ROLE);
    }

    private UserRole(Name alias, Table<UserRoleRecord> aliased) {
        this(alias, aliased, null);
    }

    private UserRole(Name alias, Table<UserRoleRecord> aliased, Field<?>[] parameters) {
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
        return Arrays.<Index>asList(Indexes.FK859N2JVI8IVHUI0RL0ESWS6O_INDEX_B, Indexes.FKA68196081FVOVJHKEK5M97N3Y_INDEX_B, Indexes.PRIMARY_KEY_BC);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<UserRoleRecord> getPrimaryKey() {
        return Keys.CONSTRAINT_BC;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<UserRoleRecord>> getKeys() {
        return Arrays.<UniqueKey<UserRoleRecord>>asList(Keys.CONSTRAINT_BC);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<UserRoleRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<UserRoleRecord, ?>>asList(Keys.FK859N2JVI8IVHUI0RL0ESWS6O, Keys.FKA68196081FVOVJHKEK5M97N3Y);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserRole as(String alias) {
        return new UserRole(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserRole as(Name alias) {
        return new UserRole(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public UserRole rename(String name) {
        return new UserRole(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public UserRole rename(Name name) {
        return new UserRole(name, null);
    }
}