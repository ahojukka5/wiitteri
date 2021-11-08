package wiitteri.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import wiitteri.models.Tweet;

public interface TweetRepository extends JpaRepository<Tweet, Long> {

}
