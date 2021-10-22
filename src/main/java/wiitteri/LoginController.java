package wiitteri;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AccountRepository accountRepository;

    @GetMapping("/login")
    public String login_get() {
        return "login";
    }

    @PostMapping("/login")
    public String login_post(@RequestParam String username, @RequestParam String password) {
        logger.debug("username: " + username);
        logger.debug("password length: " + password.length());
        Account account = accountRepository.findByUsername(username);
        if (account != null) {
            logger.debug("Account with username " + username + " found.");
        } else {
            logger.debug("Account with username " + username + " not found.");
        }
        return "redirect:/";
    }
}
