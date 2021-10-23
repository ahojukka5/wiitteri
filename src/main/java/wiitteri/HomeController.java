package wiitteri;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private LoginService loginService;

    @Autowired
    private AccountService accountService;

    @GetMapping("/home")
    public String home(Model model) {
        String username = loginService.getUsername();
        List<Account> followedUsers = accountService.getFollowedUsers();
        logger.debug("logged in user: " + username);
        logger.debug("user is following number of other users: " + followedUsers.size());
        model.addAttribute("username", username);
        model.addAttribute("followedUsers", followedUsers);
        return "home";
    }

}
