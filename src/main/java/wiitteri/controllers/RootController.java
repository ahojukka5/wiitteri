package wiitteri.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import wiitteri.services.AccountService;

@Controller
public class RootController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/")
    public String index(Model model) {
        if (accountService.isLogged()) {
            return "redirect:/home";
        }
        return "landing";
    }
}
