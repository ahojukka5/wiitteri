package wiitteri;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private HttpSession session;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConnectionRepository connectionRepository;

    public Account getLoggedUser() {
        String loggedUsername = (String) session.getAttribute("username");
        return userRepository.findByUsername(loggedUsername);
    }

    public void follow(String otherUsername) {
        Account loggedUser = getLoggedUser();
        Account otherUser = userRepository.findByUsername(otherUsername);
        logger.debug(loggedUser.getUsername() + " is following " + otherUser.getUsername());
        Connection connection = new Connection(loggedUser, otherUser);
        connectionRepository.save(connection);
    }

    public void createAccount(String username, String password, String handle) {
        // TODO: hashing password here?
        String passwordHash = password;
        Account user = new Account(username, passwordHash, handle);
        userRepository.save(user);
    }

    public List<Connection> getFollowing() {
        return getLoggedUser().getFollowing().stream().filter(Connection::isActive).collect(Collectors.toList());
    }

    public List<Connection> getFollowers() {
        return getLoggedUser().getFollowers().stream().filter(Connection::isActive).collect(Collectors.toList());
    }

    public void block(String username) {
        Account loggedUser = getLoggedUser();
        Account otherUser = userRepository.findByUsername(username);
        Connection connection = connectionRepository.findByFromAndTo(otherUser, loggedUser);
        if (connection == null) {
            String me = loggedUser.getHandle();
            logger.error("Connection between " + username + " and " + me + " not found!");
            return;
        }
        connection.setActive(false);
        connectionRepository.save(connection);
    }

}
