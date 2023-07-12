package com.accountsservice.models.repo;


import com.accountsservice.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AccountsRepo extends JpaRepository<Account, Long> {

    @Query(nativeQuery = true, value = "select * from accounts a where a.account_code = :accountCode")
    public Account loadAccountByAccountCode(String accountCode);

}
