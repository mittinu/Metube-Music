package ssw.music.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ssw.music.domain.History;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class MusicRequestBoardController {

    @GetMapping("/musicRequestBoard")
    public String getMusicRequestBoard(Model model) {


        return "musicRequestBoard";
    }
}
