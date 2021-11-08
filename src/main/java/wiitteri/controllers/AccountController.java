package wiitteri.controllers;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import wiitteri.services.AccountService;

@Controller
public class AccountController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AccountService accountService;

    @GetMapping("/register")
    public String signup() {
        return "register";
    }

    @PostMapping("/register")
    public String post(HttpServletRequest request, @RequestParam String name, @RequestParam String username,
            @RequestParam String password, @RequestParam String handle) {
        if (accountService.hasUser(username)) {
            return "redirect:/register";
        }
        logger.debug("Registering new user with username " + username + " and handle @" + handle);
        accountService.createAccount(name, username, password, handle);
        try {
            request.login(username, password);
        } catch (ServletException e) {
            logger.error("Failed to login user " + username);
            return "redirect:/";
        }
        return "redirect:/home";
    }
}
