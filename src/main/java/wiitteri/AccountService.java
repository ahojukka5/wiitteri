package wiitteri;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    private HttpSession session;

    @Autowired
    private AccountRepository accountRepository;

    public void follow(String otherUsername) {
        String loggedUsername = (String) session.getAttribute("username");
        Account loggedUser = accountRepository.findByUsername(loggedUsername);
        Account otherUser = accountRepository.findByUsername(otherUsername);
        loggedUser.follow(otherUser);
    }

    public void createAccount(String username, String password, String handle) {
        // TODO: hashing password here?
        String passwordHash = password;
        Account account = new Account(username, passwordHash, handle);
        accountRepository.save(account);
    }

}
