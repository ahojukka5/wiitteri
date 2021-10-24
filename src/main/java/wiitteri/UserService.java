package wiitteri;

import java.util.List;

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

    public User getLoggedUser() {
        String loggedUsername = (String) session.getAttribute("username");
        return userRepository.findByUsername(loggedUsername);
    }

    public void follow(String otherUsername) {
        User loggedUser = getLoggedUser();
        User otherUser = userRepository.findByUsername(otherUsername);
        logger.debug(loggedUser.getUsername() + " is following " + otherUser.getUsername());
        loggedUser.getFollowedUsers().add(otherUser);
        otherUser.getFollowers().add(loggedUser);
        userRepository.save(loggedUser);
    }

    public void createAccount(String username, String password, String handle) {
        // TODO: hashing password here?
        String passwordHash = password;
        User user = new User(username, passwordHash, handle);
        userRepository.save(user);
    }

    public List<User> getFollowedUsers() {
        return getLoggedUser().getFollowedUsers();
    }

    public List<User> getFollowers() {
        return getLoggedUser().getFollowers();
    }

}