package wiitteri;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ConnectionRepository connectionRepository;

    public Account getLoggedUser() {
        String loggedUsername = getUsername();
        return accountRepository.findByUsername(loggedUsername);
    }

    public String getUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public boolean isLogged() {
        String username = getUsername();
        if (username != "anonymousUser") {
            logger.debug("User is logged in with username " + username);
            return true;
        }
        logger.debug("User is not logged in");
        return false;
    }

    public void follow(String otherUsername) {
        Account loggedUser = getLoggedUser();
        Account otherUser = accountRepository.findByUsername(otherUsername);
        logger.debug(loggedUser.getUsername() + " is following " + otherUser.getUsername());
        Connection connection = new Connection(loggedUser, otherUser);
        connectionRepository.save(connection);
    }

    public void createAccount(String username, String password, String handle) {
        String passwordHash = passwordEncoder.encode(password);
        Account user = new Account(username, passwordHash, handle);
        accountRepository.save(user);
    }

    public List<Connection> getFollowing() {
        return getLoggedUser().getFollowing().stream().filter(Connection::isActive).collect(Collectors.toList());
    }

    public List<Connection> getFollowers() {
        return getLoggedUser().getFollowers().stream().filter(Connection::isActive).collect(Collectors.toList());
    }

    public void block(String username) {
        Account loggedUser = getLoggedUser();
        Account otherUser = accountRepository.findByUsername(username);
        Connection connection = connectionRepository.findByFromAndTo(otherUser, loggedUser);
        if (connection == null) {
            String me = loggedUser.getHandle();
            logger.error("Connection between " + username + " and " + me + " not found!");
            return;
        }
        connection.setActive(false);
        connectionRepository.save(connection);
    }

    public boolean hasUser(String username) {
        return accountRepository.findByUsername(username) != null;
    }

}
