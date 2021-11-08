package wiitteri.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import wiitteri.models.Tweet;
import wiitteri.repositories.TweetRepository;

@Service
public class TweetService {

    @Autowired
    TweetRepository tweetRepository;

    @Autowired
    AccountService accountService;

    public void createTweet(String content) {
        Tweet tweet = new Tweet(accountService.getLoggedUser(), content);
        tweetRepository.save(tweet);
    }

    public List<Tweet> getTweets() {
        return tweetRepository.findAll();
    }

}
