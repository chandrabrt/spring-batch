package com.us.lot.batch.batch;

import com.us.lot.batch.entity.User;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;

/**
 * @author chandra khadka
 * @since 2020-08-06
 */
@Component
public class UserFieldSetMapper implements FieldSetMapper<User> {

    @Override
    public User mapFieldSet(FieldSet fieldSet) throws BindException {
        final User user = new User();
        user.setDepartment(fieldSet.readString("department"));
        user.setName(fieldSet.readString("name"));
        user.setSalary(fieldSet.readDouble("salary"));
        return user;
    }
}
