package wiitteri.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import wiitteri.models.Account;
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

    public List<Tweet> getTweetsByTweeterIds() {
        return tweetRepository.findAll();
    }

    public List<Tweet> getTweetsByTweeterIds(List<Long> tweeterIds, Pageable p) {
        return tweetRepository.findByOwnerIdIn(tweeterIds, p);
    }

    public void like(Long id) {
        Account user = accountService.getLoggedUser();
        Tweet tweet = tweetRepository.getOne(id);
        tweet.getLikes().add(user);
        tweetRepository.save(tweet);
    }

}
