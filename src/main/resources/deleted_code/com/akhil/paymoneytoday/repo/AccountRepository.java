package com.akhil.paymoneytoday.repo;

import com.akhil.paymoneytoday.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByName(String name);

    Optional<Account> findByEmail(String email);

    boolean existsByName(String name);

    boolean existsByEmail(String email);
}
