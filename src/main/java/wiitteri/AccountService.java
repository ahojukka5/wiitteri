package wiitteri;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private HttpSession session;

    @Autowired
    private AccountRepository accountRepository;

    public Account getLoggedUser() {
        String loggedUsername = (String) session.getAttribute("username");
        return accountRepository.findByUsername(loggedUsername);
    }

    public void follow(String otherUsername) {
        Account loggedUser = getLoggedUser();
        Account otherUser = accountRepository.findByUsername(otherUsername);
        logger.debug(loggedUser.getUsername() + " is following " + otherUser.getUsername());
        loggedUser.getFollowedUsers().add(otherUser);
        otherUser.getFollowers().add(loggedUser);
        accountRepository.save(loggedUser);
    }

    public void createAccount(String username, String password, String handle) {
        // TODO: hashing password here?
        String passwordHash = password;
        Account account = new Account(username, passwordHash, handle);
        accountRepository.save(account);
    }

    public List<Account> getFollowedUsers() {
        return getLoggedUser().getFollowedUsers();
    }

    public List<Account> getFollowers() {
        return getLoggedUser().getFollowers();
    }

}
