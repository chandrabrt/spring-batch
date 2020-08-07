package com.us.lot.batch.repository;

import com.us.lot.batch.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author chandra khadka
 * @since 2020-08-06
 */
public interface UserRepository extends JpaRepository<User, Long> {
}
