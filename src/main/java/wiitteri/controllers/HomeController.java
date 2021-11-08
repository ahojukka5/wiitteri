package wiitteri.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import wiitteri.models.Connection;
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
        List<Connection> following = accountService.getFollowing();
        model.addAttribute("following", following);
        model.addAttribute("followers", accountService.getFollowers());
        List<Image> images = accountService.getImages();
        model.addAttribute("images", images);
        model.addAttribute("numberOfImages", images.size());
        model.addAttribute("canUploadImages", images.size() < 10);
        model.addAttribute("user", accountService.getLoggedUser());

        // There probably is a more clever way to do this, but here we collect
        // all ids of who we follow + ourself, and get all tweets where owner_id
        // is in the list.
        List<Long> tweeterIds = new ArrayList<>();
        tweeterIds.add(accountService.getLoggedUser().getId());
        for (Connection f : following) {
            tweeterIds.add(f.getTo().getId());
        }
        Pageable p = PageRequest.of(0, 25, Sort.by("created").descending());
        model.addAttribute("tweets", tweetService.getTweetsByTweeterIds(tweeterIds, p));
        return "home";
    }

}
