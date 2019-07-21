/*
 * This file is generated by jOOQ.
*/
package org.minioasis.library.jooq.tables.pojos;


import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

import javax.annotation.Generated;


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
public class Item implements Serializable {

    private static final long serialVersionUID = 1294972378;

    private Long       id;
    private String     active;
    private String     barcode;
    private String     checked;
    private Date       created;
    private Timestamp  expired;
    private Date       firstCheckin;
    private Timestamp  lastCheckin;
    private String     lastFullRenewPerson;
    private BigDecimal price;
    private String     shelfMark;
    private String     source;
    private Boolean    borrowable;
    private Boolean    reservable;
    private String     state;
    private Date       publishingDate;
    private String     volumeNo;
    private Long       biblioId;
    private Long       itemStatusId;
    private Long       locationId;

    public Item() {}

    public Item(Item value) {
        this.id = value.id;
        this.active = value.active;
        this.barcode = value.barcode;
        this.checked = value.checked;
        this.created = value.created;
        this.expired = value.expired;
        this.firstCheckin = value.firstCheckin;
        this.lastCheckin = value.lastCheckin;
        this.lastFullRenewPerson = value.lastFullRenewPerson;
        this.price = value.price;
        this.shelfMark = value.shelfMark;
        this.source = value.source;
        this.borrowable = value.borrowable;
        this.reservable = value.reservable;
        this.state = value.state;
        this.publishingDate = value.publishingDate;
        this.volumeNo = value.volumeNo;
        this.biblioId = value.biblioId;
        this.itemStatusId = value.itemStatusId;
        this.locationId = value.locationId;
    }

    public Item(
        Long       id,
        String     active,
        String     barcode,
        String     checked,
        Date       created,
        Timestamp  expired,
        Date       firstCheckin,
        Timestamp  lastCheckin,
        String     lastFullRenewPerson,
        BigDecimal price,
        String     shelfMark,
        String     source,
        Boolean    borrowable,
        Boolean    reservable,
        String     state,
        Date       publishingDate,
        String     volumeNo,
        Long       biblioId,
        Long       itemStatusId,
        Long       locationId
    ) {
        this.id = id;
        this.active = active;
        this.barcode = barcode;
        this.checked = checked;
        this.created = created;
        this.expired = expired;
        this.firstCheckin = firstCheckin;
        this.lastCheckin = lastCheckin;
        this.lastFullRenewPerson = lastFullRenewPerson;
        this.price = price;
        this.shelfMark = shelfMark;
        this.source = source;
        this.borrowable = borrowable;
        this.reservable = reservable;
        this.state = state;
        this.publishingDate = publishingDate;
        this.volumeNo = volumeNo;
        this.biblioId = biblioId;
        this.itemStatusId = itemStatusId;
        this.locationId = locationId;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getActive() {
        return this.active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getBarcode() {
        return this.barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getChecked() {
        return this.checked;
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }

    public Date getCreated() {
        return this.created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Timestamp getExpired() {
        return this.expired;
    }

    public void setExpired(Timestamp expired) {
        this.expired = expired;
    }

    public Date getFirstCheckin() {
        return this.firstCheckin;
    }

    public void setFirstCheckin(Date firstCheckin) {
        this.firstCheckin = firstCheckin;
    }

    public Timestamp getLastCheckin() {
        return this.lastCheckin;
    }

    public void setLastCheckin(Timestamp lastCheckin) {
        this.lastCheckin = lastCheckin;
    }

    public String getLastFullRenewPerson() {
        return this.lastFullRenewPerson;
    }

    public void setLastFullRenewPerson(String lastFullRenewPerson) {
        this.lastFullRenewPerson = lastFullRenewPerson;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getShelfMark() {
        return this.shelfMark;
    }

    public void setShelfMark(String shelfMark) {
        this.shelfMark = shelfMark;
    }

    public String getSource() {
        return this.source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Boolean getBorrowable() {
        return this.borrowable;
    }

    public void setBorrowable(Boolean borrowable) {
        this.borrowable = borrowable;
    }

    public Boolean getReservable() {
        return this.reservable;
    }

    public void setReservable(Boolean reservable) {
        this.reservable = reservable;
    }

    public String getState() {
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getPublishingDate() {
        return this.publishingDate;
    }

    public void setPublishingDate(Date publishingDate) {
        this.publishingDate = publishingDate;
    }

    public String getVolumeNo() {
        return this.volumeNo;
    }

    public void setVolumeNo(String volumeNo) {
        this.volumeNo = volumeNo;
    }

    public Long getBiblioId() {
        return this.biblioId;
    }

    public void setBiblioId(Long biblioId) {
        this.biblioId = biblioId;
    }

    public Long getItemStatusId() {
        return this.itemStatusId;
    }

    public void setItemStatusId(Long itemStatusId) {
        this.itemStatusId = itemStatusId;
    }

    public Long getLocationId() {
        return this.locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Item (");

        sb.append(id);
        sb.append(", ").append(active);
        sb.append(", ").append(barcode);
        sb.append(", ").append(checked);
        sb.append(", ").append(created);
        sb.append(", ").append(expired);
        sb.append(", ").append(firstCheckin);
        sb.append(", ").append(lastCheckin);
        sb.append(", ").append(lastFullRenewPerson);
        sb.append(", ").append(price);
        sb.append(", ").append(shelfMark);
        sb.append(", ").append(source);
        sb.append(", ").append(borrowable);
        sb.append(", ").append(reservable);
        sb.append(", ").append(state);
        sb.append(", ").append(publishingDate);
        sb.append(", ").append(volumeNo);
        sb.append(", ").append(biblioId);
        sb.append(", ").append(itemStatusId);
        sb.append(", ").append(locationId);

        sb.append(")");
        return sb.toString();
    }
}
