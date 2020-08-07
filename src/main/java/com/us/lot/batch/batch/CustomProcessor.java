package com.us.lot.batch.batch;

import com.us.lot.batch.entity.User;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

/**
 * @author chandra khadka
 * @since 2020-08-07
 */
@Component("customerProcessor")
public class CustomProcessor implements ItemProcessor<User, User> {

    @Override
    public User process(User item) throws Exception {
        final User user = new User();
        user.setName(item.getName());
        user.setSalary(item.getSalary());
        user.setDepartment(item.getDepartment());
        return user;
    }
}
