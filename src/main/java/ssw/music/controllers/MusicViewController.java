package ssw.music.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import lombok.RequiredArgsConstructor;
import ssw.music.domain.History;
import ssw.music.domain.Music;
import ssw.music.domain.PlayList;
import ssw.music.domain.PlayListItem;
import ssw.music.dto.AddHistory;
import ssw.music.dto.MusicListView;
import ssw.music.dto.PlayListRequest;
import ssw.music.dto.PlayListView;
import ssw.music.interfaces.IPlayListView;
import ssw.music.repository.PlayListItemRepository;
import ssw.music.repository.PlayListRepository;
import ssw.music.services.MusicService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.web.bind.annotation.RequestBody;




@RequiredArgsConstructor
@Controller
public class MusicViewController {
    
    private final MusicService musicService;
    private final PlayListRepository playListRepository;
    private final PlayListItemRepository playListItemRepository;
    
    // List<String> musics = new ArrayList<>(Arrays.asList("All Too Well", "Blank Space", "Love Story"));


    @GetMapping("/playmusic")
    public String playMusic(Model model) {
        return "playingPage";
    }

    @GetMapping("/musiclist")
    public String getMusics(Model model) {
        List<MusicListView> musics = musicService.findAll().stream().map(MusicListView::new).toList();
        List<PlayList> playLists = musicService.getPlayList().stream().toList();
        PlayListItem playListItem = new PlayListItem();
        model.addAttribute("musics", musics);    
        model.addAttribute("playlists", playLists);
        model.addAttribute("playListItem", playListItem);       
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

    @GetMapping("/history")
    public String getHistory(Model model) {

        List<History> histories = musicService.findHistory().stream().toList().reversed();

        model.addAttribute("histories", histories);

        return "history";
    }

    @GetMapping("/playlists")
    public String getPlayLists(Model model, PlayListRequest playListRequest) {
        List<PlayList> playLists = musicService.getPlayList().stream().toList();

        model.addAttribute("playLists", playLists);
        model.addAttribute("playListRequest", playListRequest);

        return "playList";
    }

    @PostMapping("/addplaylist")
    public String addPlayList(@ModelAttribute("PlayListRequest") PlayListRequest playListRequest) {
        PlayList playList = new PlayList(playListRequest.getTitle(), musicService.getLoginId());

        playListRepository.save(playList);

        return "redirect:/playlists";
    }

    @GetMapping("/playlist/{id}")
    public String getPlayList(@PathVariable("id") int id, Model model) {
        
        // 특정 플레이리스트 페이지를 보여주는 코드..
        //List<PlayListItem> playLists = playListItemRepository.findAll().stream().filter(p -> p.getPlayListId() == id).toList();
        List<PlayListView> playListViews = playListItemRepository.playListViews(id);
        model.addAttribute("playListViews", playListViews);

        return "playListPage";
    }

    @PostMapping("/addplaylistitem/{id}")
    public String addPlayListItem(@PathVariable("id") int id, @ModelAttribute("playListItem") PlayListItem playListItem) {
        
        // musicList.html 에서 input th:field 와 th:value 가 둘 다 적용이 되지 않아 일단 이렇게 함..
        PlayListItem item = new PlayListItem(id, musicService.getLoginId());

        playListItemRepository.save(item);
        
        return "redirect:/musiclist";
    }
    
}
