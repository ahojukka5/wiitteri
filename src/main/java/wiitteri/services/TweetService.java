package wiitteri.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import wiitteri.models.Account;
import wiitteri.models.Tweet;
import wiitteri.models.TweetKind;
import wiitteri.repositories.TweetRepository;

@Service
public class TweetService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    TweetRepository tweetRepository;

    @Autowired
    AccountService accountService;

    public Tweet createTweet(TweetKind kind, String content) {
        Tweet tweet = new Tweet(kind, accountService.getLoggedUser(), content);
        tweetRepository.save(tweet);
        return tweet;
    }

    public List<Tweet> getTweetsByTweeterIds() {
        return tweetRepository.findAll();
    }

    public List<Tweet> getWallTweetsByTweeterIds(List<Long> tweeterIds, Pageable p) {
        logger.debug("fetching all wall tweets ...");
        return tweetRepository.findByOwnerIdInAndKind(tweeterIds, TweetKind.WALL, p);
    }

    public void like(Long id) {
        Account user = accountService.getLoggedUser();
        Tweet tweet = tweetRepository.getOne(id);
        tweet.getLikes().add(user);
        tweetRepository.save(tweet);
    }

}
