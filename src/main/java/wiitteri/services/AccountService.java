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

@Service
public class AccountService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ConnectionService connectionService;

    @Autowired
    private ImageService imageService;

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
        connectionService.connect(loggedUser, otherUser);
    }

    public Account createAccount(String name, String username, String password, String handle) {
        String passwordHash = passwordEncoder.encode(password);
        Account user = new Account(name, username, passwordHash, handle);
        return accountRepository.save(user);
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
        connectionService.block(loggedUser, otherUser);
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
        return connectionService.hasConnection(loggedUser, otherUser);
    }

    public Account findUserByHandle(String handle) {
        return accountRepository.findByHandle(handle);
    }

    public List<Image> getImages(Account user) {
        return imageService.findByOwner(user);
    }

    public List<Image> getImages() {
        return getImages(getLoggedUser());
    }

    public Image addImage(byte[] bytes, String description) {
        return imageService.addImage(getLoggedUser(), description, bytes);
    }

    public void updateProfileImage(Image profileImage) {
        Account user = getLoggedUser();
        user.setProfileImage(profileImage);
        accountRepository.save(user);
    }

    public Image getProfileImage() {
        return getLoggedUser().getProfileImage();
    }

    public Account setProfileImage(Account user, Image image) {
        user.setProfileImage(image);
        return accountRepository.save(user);
    }

    public Account setProfileImage(Image image) {
        return setProfileImage(getLoggedUser(), image);
    }

}
