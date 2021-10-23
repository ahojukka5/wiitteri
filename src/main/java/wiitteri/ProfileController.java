package wiitteri;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ProfileController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    AccountService accountService;

    @GetMapping("/{username}")
    public String profile(Model model, @PathVariable String username) {
        model.addAttribute("username", username);
        return "profile";
    }

    @GetMapping("/{username}/follow")
    public String follow(Model model, @PathVariable String username) {
        logger.debug("Following user " + username);
        accountService.follow(username);
        model.addAttribute("username", username);
        return "redirect:/" + username;
    }
}
