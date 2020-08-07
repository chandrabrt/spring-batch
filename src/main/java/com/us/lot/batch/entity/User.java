package com.us.lot.batch.entity;

import com.us.lot.batch.shared.AbstractEntity;
import lombok.Data;

import javax.persistence.Entity;

/**
 * @author chandra khadka
 * @since 2020-08-06
 */
@Data
@Entity
public class User extends AbstractEntity {

    private String name;

    private String department;

    private double salary;
}
