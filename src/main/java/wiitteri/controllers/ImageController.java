package wiitteri.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import wiitteri.models.Account;
import wiitteri.models.Image;
import wiitteri.repositories.AccountRepository;
import wiitteri.repositories.ImageRepository;
import wiitteri.services.AccountService;

@Controller
public class ImageController {

    @Autowired
    AccountService userService;

    @Autowired
    ImageService imageService;

    @GetMapping("/{handle}/images")
    public String listImages(Model model, @PathVariable String handle) {
        Account user = accountService.findUserByHandle(handle);
        model.addAttribute("handle", handle);
        model.addAttribute("images", imageService.getImages(user));
        return "images";
    }

    @GetMapping(path = "/images/{id}", produces = "image/png")
    @ResponseBody
    public byte[] getImage(@PathVariable Long id) {
        return imageService.getImage(id).getContent();
    }

    @PostMapping("/{username}/images")
    public String save(@PathVariable String username, @RequestParam MultipartFile file,
            @RequestParam String description) throws IOException {
        Account owner = userService.getLoggedUser();
        Image image = new Image(description, owner, file.getBytes());
        imageRepository.save(image);
        return "redirect:/" + owner.getUsername() + "/images";
    }
}
