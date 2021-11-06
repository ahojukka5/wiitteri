package wiitteri;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private HttpSession session;

    @Autowired
    private UserRepository userRepository;

    public void login(String username, String password) {
        Account account = userRepository.findByUsername(username);
        if (account != null) {
            logger.debug("Account with username " + username + " found.");
            session.setAttribute("username", username);
        } else {
            logger.debug("Account with username " + username + " not found.");
        }
    }

    public void logout() {
        session.removeAttribute("username");
    }

    public String getUsername() {
        return (String) session.getAttribute("username");
    }

    public boolean isLogged() {
        return getUsername() != null;
    }

}
