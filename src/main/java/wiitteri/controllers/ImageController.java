package wiitteri.controllers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import wiitteri.services.ImageService;

@Controller
public class ImageController {

    @Autowired
    ImageService imageService;

    @GetMapping("/{handle}/images")
    public String listImages(Model model, @PathVariable String handle) {
        model.addAttribute("handle", handle);
        model.addAttribute("images", imageService.getImagesByHandle(handle));
        return "images";
    }

    @GetMapping(path = "/images/{id}", produces = "image/png")
    @ResponseBody
    public byte[] getImage(@PathVariable Long id) {
        return imageService.getImage(id).getContent();
    }

    @GetMapping(path = "/images/{id}/delete")
    public String deleteImage(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        imageService.deleteImage(id);
        redirectAttributes.addFlashAttribute("infoMessage", "Image deleted succesfully!");
        return "redirect:/home";
    }

    @GetMapping(path = "/images/{id}/use_as_profile_picture")
    public String useAsProfilePicture(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        imageService.useAsProfilePicture(id);
        redirectAttributes.addFlashAttribute("infoMessage", "Profile picture changed!");
        return "redirect:/home";
    }

    @GetMapping(path = "/images/{id}/like")
    public String like(@PathVariable Long id, RedirectAttributes redirectAttributes,
            @RequestHeader(value = "Referer", required = false) final String referer) throws MalformedURLException {
        imageService.like(id);
        redirectAttributes.addFlashAttribute("infoMessage", "Liked image!");
        return "redirect:" + new URL(referer).getPath();
    }

    @PostMapping(path = "/images/{id}/add_comment")
    public String addComment(@PathVariable Long id, @RequestParam String content, RedirectAttributes redirectAttributes,
            @RequestHeader(value = "Referer", required = false) final String referer) throws MalformedURLException {
        imageService.addComment(id, content);
        redirectAttributes.addFlashAttribute("infoMessage", "Image commented!");
        return "redirect:" + new URL(referer).getPath();
    }

    @PostMapping("/images")
    public String save(@RequestParam MultipartFile file, @RequestParam String description,
            RedirectAttributes redirectAttributes) throws IOException {
        if (description.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Adding image failed: description field is empty!");
            return "redirect:/home";
        }
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Adding image failed: image not found!");
            return "redirect:/home";
        }
        if (imageService.numberOfImages() >= 10) {
            redirectAttributes.addFlashAttribute("errorMessage", "Adding image failed: maximum of 10 images exceeded!");
            return "redirect:/home";
        }
        imageService.addImage(file.getBytes(), description);
        redirectAttributes.addFlashAttribute("infoMessage", "New image added!");
        return "redirect:/home";
    }
}
