package wiitteri.services;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import wiitteri.models.Account;
import wiitteri.models.Image;
import wiitteri.repositories.ImageRepository;

@Service
public class ImageService {

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    AccountService accountService;

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

    public void addImage(Account user, byte[] bytes, String description) {
        accountService.addImage(bytes, description);
    }

    public void addImage(byte[] bytes, String description) {
        addImage(accountService.getLoggedUser(), bytes, description);
    }

    public void useAsProfilePicture(Long id) {
        Image profileImage = getImage(id);
        accountService.updateProfileImage(profileImage);
    }

    public void like(Long id) {
        Account user = accountService.getLoggedUser();
        Image image = getImage(id);
        image.getLikes().add(user);
        imageRepository.save(image);
    }
}
