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
