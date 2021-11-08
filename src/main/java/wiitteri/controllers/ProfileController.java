package wiitteri.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @GetMapping("/profiles/{handle}/follow")
    public String follow(Model model, @PathVariable String handle, RedirectAttributes redirectAttributes) {
        if (handle.equals(accountService.getHandle())) {
            logger.debug("User @" + handle + " tries to follow him/herself!");
            redirectAttributes.addFlashAttribute("errorMessage", "You cannot follow yourself!");
            return "redirect:/profiles/" + handle;
        }
        logger.debug(accountService.getHandle() + " is following user " + handle);
        accountService.follow(handle);
        model.addAttribute("handle", handle);
        redirectAttributes.addFlashAttribute("infoMessage", "You are now following user @" + handle);
        return "redirect:/profiles/" + handle;
    }

    @GetMapping("/profiles/{handle}/block")
    public String block(Model model, @PathVariable String handle, RedirectAttributes redirectAttributes) {
        logger.debug("Blocking user @" + handle);
        accountService.block(handle);
        redirectAttributes.addFlashAttribute("infoMessage", "User @" + handle + " is blocked");
        return "redirect:/home";
    }
}
