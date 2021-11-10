package wiitteri.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import wiitteri.models.Account;
import wiitteri.models.Image;
import wiitteri.models.Tweet;
import wiitteri.models.TweetKind;
import wiitteri.repositories.ImageRepository;

@Service
public class ImageService {

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    AccountService accountService;

    @Autowired
    TweetService tweetService;

    public List<Image> getImages(Account user) {
        return imageRepository.findByOwner(user);
    }

    public List<Image> getImages() {
        return imageRepository.findByOwner(accountService.getLoggedUser());
    }

    public Image getImage(Long id) {
        return imageRepository.getOne(id);
    }

    public void deleteImage(Long id) {
        Image image = getImage(id);
        if (image.equals(accountService.getProfileImage())) {
            accountService.setProfileImage(null);
        }
        imageRepository.delete(image);
    }

    public int numberOfImages(Account user) {
        return getImages(user).size();
    }

    public int numberOfImages() {
        return getImages(accountService.getLoggedUser()).size();
    }

    public List<Image> getImagesByHandle(String handle) {
        return getImages(accountService.findUserByHandle(handle));
    }

    public Image addImage(Account user, byte[] bytes, String description) {
        Image image = new Image(description, user, bytes);
        return imageRepository.save(image);
    }

    public void addImage(byte[] bytes, String description) {
        addImage(accountService.getLoggedUser(), bytes, description);
    }

    public void useAsProfilePicture(Long id) {
        Image profileImage = getImage(id);
        accountService.updateProfileImage(profileImage);
    }

    public Image like(Long id) {
        Account user = accountService.getLoggedUser();
        Image image = getImage(id);
        return addLike(image, user);
    }

    public void addComment(Long id, String content) {
        Image image = getImage(id);
        Tweet comment = tweetService.createTweet(TweetKind.COMMENT, content);
        image.getComments().add(comment);
        imageRepository.save(image);
    }

    public Image addLike(Image image, Account user) {
        image.getLikes().add(user);
        return imageRepository.save(image);
    }

}
