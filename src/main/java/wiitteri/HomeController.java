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
        model.addAttribute("username", loginService.getUsername());
        model.addAttribute("followedUsers", accountService.getFollowedUsers());
        model.addAttribute("followers", accountService.getFollowers());
        return "home";
    }

}
