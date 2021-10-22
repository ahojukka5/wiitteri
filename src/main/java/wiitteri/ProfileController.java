package wiitteri;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ProfileController {

    @GetMapping("/{username}")
    public String profile(Model model, @PathVariable String username) {
        model.addAttribute("username", username);
        return "profile";
    }

}
