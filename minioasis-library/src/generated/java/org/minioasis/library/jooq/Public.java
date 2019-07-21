/*
 * This file is generated by jOOQ.
*/
package org.minioasis.library.jooq;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Catalog;
import org.jooq.Sequence;
import org.jooq.Table;
import org.jooq.impl.SchemaImpl;
import org.minioasis.library.jooq.tables.Account;
import org.minioasis.library.jooq.tables.Attachment;
import org.minioasis.library.jooq.tables.AttachmentCheckout;
import org.minioasis.library.jooq.tables.Biblio;
import org.minioasis.library.jooq.tables.BiblioTag;
import org.minioasis.library.jooq.tables.Checkout;
import org.minioasis.library.jooq.tables.FormData;
import org.minioasis.library.jooq.tables.Groups;
import org.minioasis.library.jooq.tables.Holiday;
import org.minioasis.library.jooq.tables.Image;
import org.minioasis.library.jooq.tables.Item;
import org.minioasis.library.jooq.tables.ItemStatus;
import org.minioasis.library.jooq.tables.JournalEntry;
import org.minioasis.library.jooq.tables.JournalEntryLine;
import org.minioasis.library.jooq.tables.Location;
import org.minioasis.library.jooq.tables.Patron;
import org.minioasis.library.jooq.tables.PatronType;
import org.minioasis.library.jooq.tables.Publisher;
import org.minioasis.library.jooq.tables.Reservation;
import org.minioasis.library.jooq.tables.Series;
import org.minioasis.library.jooq.tables.Tag;
import org.minioasis.library.jooq.tables.TelegramUser;


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
public class Public extends SchemaImpl {

    private static final long serialVersionUID = -753150633;

    /**
     * The reference instance of <code>PUBLIC</code>
     */
    public static final Public PUBLIC = new Public();

    /**
     * The table <code>PUBLIC.ACCOUNT</code>.
     */
    public final Account ACCOUNT = org.minioasis.library.jooq.tables.Account.ACCOUNT;

    /**
     * The table <code>PUBLIC.ATTACHMENT</code>.
     */
    public final Attachment ATTACHMENT = org.minioasis.library.jooq.tables.Attachment.ATTACHMENT;

    /**
     * The table <code>PUBLIC.ATTACHMENT_CHECKOUT</code>.
     */
    public final AttachmentCheckout ATTACHMENT_CHECKOUT = org.minioasis.library.jooq.tables.AttachmentCheckout.ATTACHMENT_CHECKOUT;

    /**
     * The table <code>PUBLIC.BIBLIO</code>.
     */
    public final Biblio BIBLIO = org.minioasis.library.jooq.tables.Biblio.BIBLIO;

    /**
     * The table <code>PUBLIC.BIBLIO_TAG</code>.
     */
    public final BiblioTag BIBLIO_TAG = org.minioasis.library.jooq.tables.BiblioTag.BIBLIO_TAG;

    /**
     * The table <code>PUBLIC.CHECKOUT</code>.
     */
    public final Checkout CHECKOUT = org.minioasis.library.jooq.tables.Checkout.CHECKOUT;

    /**
     * The table <code>PUBLIC.FORM_DATA</code>.
     */
    public final FormData FORM_DATA = org.minioasis.library.jooq.tables.FormData.FORM_DATA;

    /**
     * The table <code>PUBLIC.GROUPS</code>.
     */
    public final Groups GROUPS = org.minioasis.library.jooq.tables.Groups.GROUPS;

    /**
     * The table <code>PUBLIC.HOLIDAY</code>.
     */
    public final Holiday HOLIDAY = org.minioasis.library.jooq.tables.Holiday.HOLIDAY;

    /**
     * The table <code>PUBLIC.IMAGE</code>.
     */
    public final Image IMAGE = org.minioasis.library.jooq.tables.Image.IMAGE;

    /**
     * The table <code>PUBLIC.ITEM</code>.
     */
    public final Item ITEM = org.minioasis.library.jooq.tables.Item.ITEM;

    /**
     * The table <code>PUBLIC.ITEM_STATUS</code>.
     */
    public final ItemStatus ITEM_STATUS = org.minioasis.library.jooq.tables.ItemStatus.ITEM_STATUS;

    /**
     * The table <code>PUBLIC.JOURNAL_ENTRY</code>.
     */
    public final JournalEntry JOURNAL_ENTRY = org.minioasis.library.jooq.tables.JournalEntry.JOURNAL_ENTRY;

    /**
     * The table <code>PUBLIC.JOURNAL_ENTRY_LINE</code>.
     */
    public final JournalEntryLine JOURNAL_ENTRY_LINE = org.minioasis.library.jooq.tables.JournalEntryLine.JOURNAL_ENTRY_LINE;

    /**
     * The table <code>PUBLIC.LOCATION</code>.
     */
    public final Location LOCATION = org.minioasis.library.jooq.tables.Location.LOCATION;

    /**
     * The table <code>PUBLIC.PATRON</code>.
     */
    public final Patron PATRON = org.minioasis.library.jooq.tables.Patron.PATRON;

    /**
     * The table <code>PUBLIC.PATRON_TYPE</code>.
     */
    public final PatronType PATRON_TYPE = org.minioasis.library.jooq.tables.PatronType.PATRON_TYPE;

    /**
     * The table <code>PUBLIC.PUBLISHER</code>.
     */
    public final Publisher PUBLISHER = org.minioasis.library.jooq.tables.Publisher.PUBLISHER;

    /**
     * The table <code>PUBLIC.RESERVATION</code>.
     */
    public final Reservation RESERVATION = org.minioasis.library.jooq.tables.Reservation.RESERVATION;

    /**
     * The table <code>PUBLIC.SERIES</code>.
     */
    public final Series SERIES = org.minioasis.library.jooq.tables.Series.SERIES;

    /**
     * The table <code>PUBLIC.TAG</code>.
     */
    public final Tag TAG = org.minioasis.library.jooq.tables.Tag.TAG;

    /**
     * The table <code>PUBLIC.TELEGRAM_USER</code>.
     */
    public final TelegramUser TELEGRAM_USER = org.minioasis.library.jooq.tables.TelegramUser.TELEGRAM_USER;

    /**
     * No further instances allowed
     */
    private Public() {
        super("PUBLIC", null);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Catalog getCatalog() {
        return DefaultCatalog.DEFAULT_CATALOG;
    }

    @Override
    public final List<Sequence<?>> getSequences() {
        List result = new ArrayList();
        result.addAll(getSequences0());
        return result;
    }

    private final List<Sequence<?>> getSequences0() {
        return Arrays.<Sequence<?>>asList(
            Sequences.HIBERNATE_SEQUENCE);
    }

    @Override
    public final List<Table<?>> getTables() {
        List result = new ArrayList();
        result.addAll(getTables0());
        return result;
    }

    private final List<Table<?>> getTables0() {
        return Arrays.<Table<?>>asList(
            Account.ACCOUNT,
            Attachment.ATTACHMENT,
            AttachmentCheckout.ATTACHMENT_CHECKOUT,
            Biblio.BIBLIO,
            BiblioTag.BIBLIO_TAG,
            Checkout.CHECKOUT,
            FormData.FORM_DATA,
            Groups.GROUPS,
            Holiday.HOLIDAY,
            Image.IMAGE,
            Item.ITEM,
            ItemStatus.ITEM_STATUS,
            JournalEntry.JOURNAL_ENTRY,
            JournalEntryLine.JOURNAL_ENTRY_LINE,
            Location.LOCATION,
            Patron.PATRON,
            PatronType.PATRON_TYPE,
            Publisher.PUBLISHER,
            Reservation.RESERVATION,
            Series.SERIES,
            Tag.TAG,
            TelegramUser.TELEGRAM_USER);
    }
}
