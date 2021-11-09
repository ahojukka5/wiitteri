package wiitteri.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import wiitteri.models.Account;
import wiitteri.models.Tweet;
import wiitteri.models.TweetKind;

public interface TweetRepository extends JpaRepository<Tweet, Long> {

    List<Tweet> findByOwnerIdIn(List<Long> tweeterIds);

    List<Tweet> findByOwnerIdInAndKind(List<Long> tweeterIds, TweetKind kind, Pageable p);

    List<Tweet> findByOwnerAndKind(Account user, TweetKind wall);

}
