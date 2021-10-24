package wiitteri;

import java.util.ArrayList;
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

    @Autowired
    private FollowersRepository followersRepository;

    public User getLoggedUser() {
        String loggedUsername = (String) session.getAttribute("username");
        return userRepository.findByUsername(loggedUsername);
    }

    public void follow(String otherUsername) {
        User loggedUser = getLoggedUser();
        User otherUser = userRepository.findByUsername(otherUsername);
        logger.debug(loggedUser.getUsername() + " is following " + otherUser.getUsername());
        // loggedUser.getFollowing().add(otherUser);
        // otherUser.getFollowers().add(loggedUser);
        // userRepository.save(loggedUser);
        Followers followers = new Followers(loggedUser, otherUser);
        followersRepository.save(followers);
    }

    public void createAccount(String username, String password, String handle) {
        // TODO: hashing password here?
        String passwordHash = password;
        User user = new User(username, passwordHash, handle);
        userRepository.save(user);
    }

    public List<User> getFollowing() {
        List<User> following = new ArrayList<>();
        for (Followers f : getLoggedUser().getFollowing()) {
            following.add(f.getTo());
        }
        return following;
    }

    public List<User> getFollowers() {
        List<User> followers = new ArrayList<>();
        for (Followers f : getLoggedUser().getFollowers()) {
            followers.add(f.getFrom());
        }
        return followers;
    }

}
