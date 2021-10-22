package wiitteri;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RootController {

    @Autowired
    private LoginService loginService;

    @GetMapping("/")
    public String index(Model model) {
        if (loginService.isLogged()) {
            return "redirect:/home";
        } else {
            return "redirect:/login";
        }
    }
}
