package wiitteri;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByUsername(String username);

    List<Account> findByUsernameContainingIgnoreCase(String username);
}
