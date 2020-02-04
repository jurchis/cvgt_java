package com.fasttrack.cvgt.persistance;

import com.fasttrack.cvgt.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
