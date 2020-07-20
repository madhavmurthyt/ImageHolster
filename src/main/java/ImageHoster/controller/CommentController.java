package ImageHoster.controller;

import ImageHoster.model.Comment;
import ImageHoster.model.Image;

import ImageHoster.model.User;
import ImageHoster.service.CommentService;
import ImageHoster.service.ImageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private ImageService imageService;

    //creatComments has been implemented to retrieve image from path variable and session objects respective
    //retrieve comments text from requestparam
    //create a comment object , set the right values for each comment variables
    //call the createComment method to call the repository class to save it in the db
    @RequestMapping(value = "/image/{imageId}/{imageTitle}/comments", method = RequestMethod.POST)
    public String createComments(@PathVariable("imageId") Integer imageId, Comment newComment, @PathVariable("imageTitle") String imageTitle, @RequestParam("comment") String comments, HttpSession session) {

        if (!comments.equals("") && comments != null) {
            User user = (User) session.getAttribute("loggeduser");
            Image image = imageService.getImage(imageId);
            newComment.setCreatedDate(new Date());
            newComment.setText(comments);
            newComment.setUser(user);
            newComment.setImage(image);
            commentService.createComment(newComment);
        }
        return "redirect:/images/" + imageId + "/" + imageTitle;
    }
}
