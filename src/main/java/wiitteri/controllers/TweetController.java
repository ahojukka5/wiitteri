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

import wiitteri.models.TweetKind;
import wiitteri.services.TweetService;

@Controller
public class TweetController {

    @Autowired
    private TweetService tweetService;

    @PostMapping("/tweets")
    public String newTweet(@RequestParam String content) {
        if (!content.isEmpty()) {
            tweetService.createTweet(TweetKind.WALL, content);
        }
        return "redirect:/home";
    }

    @GetMapping(path = "/tweets/{id}/like")
    public String like(@PathVariable Long id, RedirectAttributes redirectAttributes,
            @RequestHeader(value = "Referer", required = false) final String referer) throws MalformedURLException {
        tweetService.like(id);
        redirectAttributes.addFlashAttribute("infoMessage", "Liked tweet!");
        return "redirect:" + new URL(referer).getPath();
    }

    @PostMapping(path = "/tweets/{id}/add_comment")
    public String addComment(@PathVariable Long id, @RequestParam String content, RedirectAttributes redirectAttributes,
            @RequestHeader(value = "Referer", required = false) final String referer) throws MalformedURLException {
        tweetService.addComment(id, content);
        redirectAttributes.addFlashAttribute("infoMessage", "Tweet commented!");
        return "redirect:" + new URL(referer).getPath();
    }
}
