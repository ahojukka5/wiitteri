package wiitteri;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SignupController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AccountService accountService;

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    @PostMapping("/signup")
    public String post(@RequestParam String username, @RequestParam String password, @RequestParam String handle) {
        logger.debug("username: " + username);
        logger.debug("password length: " + password.length());
        logger.debug("handle: " + handle);
        accountService.createAccount(username, password, handle);
        return "redirect:/home";
    }
}
