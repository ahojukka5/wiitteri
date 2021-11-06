package wiitteri.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import wiitteri.models.Account;
import wiitteri.models.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {

    List<Image> findByOwner(Account owner);

}
