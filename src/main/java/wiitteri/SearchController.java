package wiitteri;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SearchController {

    @GetMapping("/search")
    public String serch(Model model, @RequestParam String q) {
        model.addAttribute("searchString", q);
        return "search";
    }

}
