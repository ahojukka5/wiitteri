package wiitteri.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import wiitteri.models.Account;
import wiitteri.models.Connection;

public interface ConnectionRepository extends JpaRepository<Connection, Long> {

    Connection findByFromAndTo(Account from, Account to);

}
