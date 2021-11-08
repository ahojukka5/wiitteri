package wiitteri.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import wiitteri.models.Tweet;

public interface TweetRepository extends JpaRepository<Tweet, Long> {

    List<Tweet> findByOwnerIdIn(List<Long> tweeterIds);

    List<Tweet> findByOwnerIdIn(List<Long> tweeterIds, Pageable p);

}
