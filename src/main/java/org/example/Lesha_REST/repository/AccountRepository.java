package org.example.Lesha_REST.repository;


import org.example.Lesha_REST.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> getAccountById(long id);
    Optional<Account> findFirstBy();
}
