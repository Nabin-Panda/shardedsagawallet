package com.shardedsagawallet.shardedsagawallet.repositories;

import com.shardedsagawallet.shardedsagawallet.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
}
