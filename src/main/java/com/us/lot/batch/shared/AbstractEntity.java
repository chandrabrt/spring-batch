package com.us.lot.batch.shared;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.util.Date;

/**
 * @author chandra khadka
 * @since 2020-08-07
 */
@Setter
@Getter
@EqualsAndHashCode(callSuper = true)
@MappedSuperclass
public class AbstractEntity extends AbstractPersistable<Long> {

    @CreatedDate
    @Column(name = "created_date")
    private Date createdDate = new Date();

    @LastModifiedDate
    @Column(name = "last_modified_date")
    private Date lastModifiedDate = new Date();

    @Version
    private int version;
}