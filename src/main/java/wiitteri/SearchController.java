package wiitteri;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SearchController {

    @Autowired
    private SearchService searchService;

    @GetMapping("/search")
    public String serch(Model model, @RequestParam String q) {
        model.addAttribute("searchString", q);
        model.addAttribute("users", searchService.search(q));
        return "search";
    }

}
