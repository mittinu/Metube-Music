package ssw.music.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import lombok.RequiredArgsConstructor;
import ssw.music.domain.History;
import ssw.music.domain.Music;
import ssw.music.dto.AddHistory;
import ssw.music.dto.MusicListView;
import ssw.music.services.MusicService;
import java.util.List;
import org.springframework.web.bind.annotation.RequestParam;


@RequiredArgsConstructor
@Controller
public class MusicViewController {
    
    private final MusicService musicService;
    
    
    // List<String> musics = new ArrayList<>(Arrays.asList("All Too Well", "Blank Space", "Love Story"));


    @GetMapping("/playmusic")
    public String playMusic(Model model) {
        return "playingPage";
    }

    @GetMapping("/musiclist")
    public String getMusics(Model model) {
        List<MusicListView> musics = musicService.findAll().stream().map(MusicListView::new).toList();
        model.addAttribute("musics", musics);            
        return "musicList";
    }

    // 특정 음악에 접속한 순간 히스토리에 올려야함..
    @GetMapping("/music/{id}")
    public String getMusic(@PathVariable("id") int id, Model model) {
        
        Music music = musicService.findById(id);
        
        // 히스토리 객체를 생성해 저장..
        AddHistory addHistory = new AddHistory(music.getId(), musicService.getLoginId());
        musicService.saveHistory(addHistory);        

        model.addAttribute("music", music);
        return "music";
    }

    @GetMapping("/")
    public String getMain() {
        return "main";
    }

    @GetMapping("/playlist")
    public String getPlayList(Model model) {
        return "playList";
    }

    @GetMapping("/history")
    public String getHistory(Model model) {

        List<History> histories = musicService.findHistory().stream().toList().reversed();

        model.addAttribute("histories", histories);

        return "history";
    }
}
