package wiitteri.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import wiitteri.services.AccountService;

@Controller
public class ProfileController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    AccountService userService;

    @GetMapping("/profiles/{username}")
    public String profile(Model model, @PathVariable String username) {
        model.addAttribute("username", username);
        return "profile";
    }

    @GetMapping("/profiles/{username}/follow")
    public String follow(Model model, @PathVariable String username) {
        logger.debug("Following user " + username);
        userService.follow(username);
        model.addAttribute("username", username);
        return "redirect:/" + username;
    }

    @GetMapping("/profiles/{username}/block")
    public String block(Model model, @PathVariable String username) {
        logger.debug("Blocking user " + username);
        userService.block(username);
        return "redirect:/home";
    }
}
