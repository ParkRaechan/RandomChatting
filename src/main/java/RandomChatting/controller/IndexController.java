package RandomChatting.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

//홈-매핑
@Controller
public class IndexController {
    @GetMapping("/")
    public String home(){
        return "home";
    }

    @GetMapping("/index")
    public String index(){return "index";}
}
