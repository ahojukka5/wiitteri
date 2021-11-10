package wiitteri.services;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
        return tweetRepository.save(tweet);
    }

    public Tweet createTweet(Account user, String content) {
        Tweet tweet = new Tweet(TweetKind.WALL, user, content);
        return tweetRepository.save(tweet);
    }

    public Tweet getTweet(Long id) {
        return tweetRepository.getOne(id);
    }

    public List<Tweet> getWallTweetsByTweeterIds(List<Long> tweeterIds, Pageable p) {
        logger.debug("fetching all wall tweets ...");
        return tweetRepository.findByOwnerIdInAndKind(tweeterIds, TweetKind.WALL, p);
    }

    public Tweet like(Long id) {
        Account user = accountService.getLoggedUser();
        Tweet tweet = getTweet(id);
        return addLike(tweet, user);
    }

    public List<Tweet> getTweets(Account user) {
        Pageable p = PageRequest.of(0, 25, Sort.by("created").descending());
        return tweetRepository.findByOwnerAndKind(user, TweetKind.WALL, p);
    }

    public Tweet addComment(Long id, String content) {
        Tweet tweet = getTweet(id);
        return addComment(tweet, accountService.getLoggedUser(), content);
    }

    public Tweet addLike(Tweet tweet, Account user) {
        tweet.getLikes().add(user);
        return tweetRepository.save(tweet);
    }

    public Tweet addComment(Tweet tweet, Account account, String content) {
        Tweet comment = createComment(account, content);
        tweet.getComments().add(comment);
        return tweetRepository.save(tweet);
    }

    public Tweet createComment(Account account, String content) {
        Tweet comment = new Tweet(TweetKind.COMMENT, account, content);
        return tweetRepository.save(comment);
    }

}
