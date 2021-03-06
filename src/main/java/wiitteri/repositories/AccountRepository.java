package wiitteri.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import wiitteri.models.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByUsername(String username);

    Account findByHandle(String handle);

    List<Account> findByUsernameContainingIgnoreCase(String username);

    List<Account> findByNameContainingIgnoreCase(String name);
}
