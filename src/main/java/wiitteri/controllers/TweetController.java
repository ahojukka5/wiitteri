package wiitteri.controllers;

import java.net.MalformedURLException;
import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @GetMapping(path = "/tweets/{id}/like")
    public String like(@PathVariable Long id, RedirectAttributes redirectAttributes,
            @RequestHeader(value = "Referer", required = false) final String referer) throws MalformedURLException {
        tweetService.like(id);
        redirectAttributes.addFlashAttribute("infoMessage", "Liked image!");
        return "redirect:" + new URL(referer).getPath();
    }
}
