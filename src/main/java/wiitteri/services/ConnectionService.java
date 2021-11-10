package wiitteri.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import wiitteri.models.Account;
import wiitteri.models.Connection;
import wiitteri.repositories.ConnectionRepository;

@Service
public class ConnectionService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ConnectionRepository connectionRepository;

    public Connection connect(Account user1, Account user2) {
        Connection connection = new Connection(user1, user2);
        return connectionRepository.save(connection);
    }

    public Connection block(Connection connection) {
        connection.setActive(false);
        return connectionRepository.save(connection);
    }

    public void block(Account loggedUser, Account otherUser) {
        Connection connection = connectionRepository.findByFromAndTo(otherUser, loggedUser);
        if (connection == null) {
            String me = loggedUser.getHandle();
            logger.error("Connection between @" + otherUser.getHandle() + " and @" + me + " not found!");
            return;
        }
        connection.setActive(false);
        connectionRepository.save(connection);
    }

    public boolean hasConnection(Account from, Account to) {
        logger.debug("Checking is " + from.getHandle() + " following " + to.getHandle() + " ...");
        if (from == to) {
            return true;
        }
        Connection connection = connectionRepository.findByFromAndTo(from, to);
        if (connection != null) {
            logger.debug("Yes, following since " + connection.getCreated());
        } else {
            logger.debug("Connection not found");
        }
        return connection != null;
    }

}
