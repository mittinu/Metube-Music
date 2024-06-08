package ssw.music.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ssw.music.domain.MusicRequest;
import ssw.music.services.MusicRequestService;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class MusicRequestBoardController {

    @GetMapping("/musicRequestList")
    public String getMusicRequestList(Model model) {
        List<MusicRequest> musicRequests = musicRequestService.findAll();
        model.addAttribute("musicRequests", musicRequests);
        return "musicRequestList";
    }

    @GetMapping("/musicRequestBoard")
    public String getMusicRequestBoard(Model model) {
        return "musicRequestBoard";
    }


    @Autowired
    private MusicRequestService musicRequestService;

    @PostMapping("/musicRequest/")
    public String postMusicRequestBoard(@ModelAttribute MusicRequest musicRequest) {
        musicRequestService.saveMusicRequest(musicRequest);
        return "redirect:/musicRequestList";
    }

}
