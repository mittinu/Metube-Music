package ssw.music.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class MusicApiController {
    @GetMapping("/musiclistasa")
    public String getMusicList() {
        return "this is music list..";
    }
}
