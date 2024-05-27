package ssw.music.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ssw.music.domain.PlayList;
import ssw.music.dto.PlayListRequest;
import ssw.music.repository.PlayListRepository;
import ssw.music.services.MusicService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;




@RequiredArgsConstructor
@Controller
public class MusicApiController {
    private final MusicService musicService;
    private final PlayListRepository playListRepository;

    @GetMapping("/musiclistasa")
    public String getMusicList() {
        return "this is music list..";
    }

    
}
