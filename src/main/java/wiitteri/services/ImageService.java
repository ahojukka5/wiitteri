package wiitteri.services;

import java.util.List;

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
        return imageRepository.findAll();
    }

    public Image getImage(Long id) {
        return imageRepository.getOne(id);
    }

    public void deleteImage(Long id) {
        Image image = imageRepository.getOne(id);
        imageRepository.delete(image);
    }

}
