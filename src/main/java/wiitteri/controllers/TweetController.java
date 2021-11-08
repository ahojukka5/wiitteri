package wiitteri.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import wiitteri.services.TweetService;

@Controller
public class TweetController {

    @Autowired
    private TweetService tweetService;

    @PostMapping("/tweets")
    public String newTweet(@RequestParam String content) {
        if (!content.isEmpty()) {
            tweetService.createTweet(content);
        }
        return "redirect:/home";
    }
}
