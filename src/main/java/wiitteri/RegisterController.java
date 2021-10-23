package wiitteri;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegisterController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String signup() {
        return "register";
    }

    @PostMapping("/register")
    public String post(@RequestParam String username, @RequestParam String password, @RequestParam String handle) {
        logger.debug("username: " + username);
        logger.debug("password length: " + password.length());
        logger.debug("handle: " + handle);
        userService.createAccount(username, password, handle);
        return "redirect:/home";
    }
}
