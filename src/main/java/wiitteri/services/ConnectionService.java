package wiitteri.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import wiitteri.models.Account;
import wiitteri.models.Connection;
import wiitteri.repositories.ConnectionRepository;

@Service
public class ConnectionService {

    @Autowired
    ConnectionRepository connectionRepository;

    public Connection connect(Account user1, Account user2) {
        Connection connection = new Connection(user1, user2);
        return connectionRepository.save(connection);
    }

    public void block(Connection connection) {
        connection.setActive(false);
    }

}
