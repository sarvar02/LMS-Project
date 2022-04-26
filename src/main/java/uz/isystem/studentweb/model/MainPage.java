package uz.isystem.studentweb.model;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class MainPage {

    @GetMapping("/home")
    public String mainPage(Model model){
        return "main";
    }
}
