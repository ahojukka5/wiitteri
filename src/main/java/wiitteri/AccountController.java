package wiitteri;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AccountController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AccountService accountService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String signup() {
        return "register";
    }

    @PostMapping("/register")
    public String post(@RequestParam String username, @RequestParam String password, @RequestParam String handle) {
        if (accountService.hasUser(username)) {
            return "redirect:/accounts";
        }
        String passwordHash = passwordEncoder.encode(password);
        logger.debug("Registering new user with username " + username + " and handle " + handle);
        accountService.createAccount(username, passwordHash, handle);
        return "redirect:/home";
    }
}
