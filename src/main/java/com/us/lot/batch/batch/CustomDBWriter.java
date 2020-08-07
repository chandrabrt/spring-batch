package com.us.lot.batch.batch;

import com.us.lot.batch.entity.User;
import com.us.lot.batch.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author chandra khadka
 * @since 2020-08-07
 */
@Component("customDBWriter")
@Slf4j
public class CustomDBWriter implements ItemWriter<User> {
    private final UserRepository userRepository;

    public CustomDBWriter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void write(List<? extends User> users) throws Exception {
        log.info("Saving data for user: {}", users);
        userRepository.saveAll(users);
    }
}
