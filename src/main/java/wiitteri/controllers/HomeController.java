package wiitteri.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import wiitteri.models.Image;
import wiitteri.services.AccountService;
import wiitteri.services.TweetService;

@Controller
public class HomeController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private TweetService tweetService;

    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("handle", accountService.getHandle());
        model.addAttribute("following", accountService.getFollowing());
        model.addAttribute("followers", accountService.getFollowers());
        List<Image> images = accountService.getImages();
        model.addAttribute("images", images);
        model.addAttribute("numberOfImages", images.size());
        model.addAttribute("canUploadImages", images.size() < 10);
        model.addAttribute("user", accountService.getLoggedUser());
        model.addAttribute("tweets", tweetService.getTweets());
        return "home";
    }

}
