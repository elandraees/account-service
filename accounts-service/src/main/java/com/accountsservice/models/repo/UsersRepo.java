package com.accountsservice.models.repo;

import com.accountsservice.models.User;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepo extends JpaRepository<User, Long> {

    @Query(nativeQuery = true, value = "select * from users u where u.username = :username")
    public User loadUsername(String username);

    @Query(nativeQuery = true, value = "select * from users u where u.account_id = :accountId")
    public List<User> loadUsersForAccount(long accountId);

}
