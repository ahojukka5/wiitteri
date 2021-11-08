package wiitteri.services;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import wiitteri.models.Account;
import wiitteri.models.Connection;
import wiitteri.models.Image;
import wiitteri.repositories.AccountRepository;
import wiitteri.repositories.ConnectionRepository;
import wiitteri.repositories.ImageRepository;

@Service
public class AccountService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ConnectionRepository connectionRepository;

    @Autowired
    private ImageRepository imageRepository;

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

    public void follow(String handle) {
        Account loggedUser = getLoggedUser();
        Account otherUser = accountRepository.findByHandle(handle);
        logger.debug(loggedUser.getUsername() + " is following " + otherUser.getUsername());
        Connection connection = new Connection(loggedUser, otherUser);
        connectionRepository.save(connection);
    }

    public void createAccount(String name, String username, String password, String handle) {
        String passwordHash = passwordEncoder.encode(password);
        Account user = new Account(name, username, passwordHash, handle);
        accountRepository.save(user);
    }

    public List<Connection> getFollowing() {
        return getLoggedUser().getFollowing().stream().filter(Connection::isActive).collect(Collectors.toList());
    }

    public List<Connection> getFollowers() {
        return getLoggedUser().getFollowers().stream().filter(Connection::isActive).collect(Collectors.toList());
    }

    public void block(String handle) {
        Account loggedUser = getLoggedUser();
        Account otherUser = accountRepository.findByHandle(handle);
        Connection connection = connectionRepository.findByFromAndTo(otherUser, loggedUser);
        if (connection == null) {
            String me = loggedUser.getHandle();
            logger.error("Connection between @" + handle + " and @" + me + " not found!");
            return;
        }
        connection.setActive(false);
        connectionRepository.save(connection);
    }

    public boolean hasUser(String username) {
        return accountRepository.findByUsername(username) != null;
    }

    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    public String getHandle() {
        return getLoggedUser().getHandle();
    }

    public boolean isFollowing(String handle) {
        Account loggedUser = getLoggedUser();
        Account otherUser = accountRepository.findByHandle(handle);
        logger.debug("Checking is " + loggedUser.getHandle() + " following " + otherUser.getHandle() + " ...");
        if (loggedUser == otherUser) {
            return true;
        }
        Connection connection = connectionRepository.findByFromAndTo(loggedUser, otherUser);
        if (connection != null) {
            logger.debug("Yes, following since " + connection.getCreated());
        } else {
            logger.debug("Connection not found");
        }
        return connection != null;
    }

    public Account findUserByHandle(String handle) {
        return accountRepository.findByHandle(handle);
    }

    public List<Image> getImages(Account user) {
        return imageRepository.findByOwner(user);
    }

    public List<Image> getImages() {
        return getImages(getLoggedUser());
    }

    public void addImage(byte[] bytes, String description) {
        Image image = new Image(description, getLoggedUser(), bytes);
        imageRepository.save(image);
    }

    public void updateProfileImage(Image profileImage) {
        Account user = getLoggedUser();
        user.setProfileImage(profileImage);
        accountRepository.save(user);
    }

}
