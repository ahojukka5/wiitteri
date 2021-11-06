package wiitteri.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import wiitteri.services.AccountService;

@Controller
public class HomeController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("username", accountService.getUsername());
        model.addAttribute("following", accountService.getFollowing());
        model.addAttribute("followers", accountService.getFollowers());
        return "home";
    }

}
