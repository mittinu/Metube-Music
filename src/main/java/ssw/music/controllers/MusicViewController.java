package ssw.music.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;
import ssw.music.domain.History;
import ssw.music.domain.Music;
import ssw.music.domain.PlayList;
import ssw.music.domain.PlayListItem;
import ssw.music.dto.AddHistory;
import ssw.music.dto.MusicListView;
import ssw.music.dto.PlayListMusic;
import ssw.music.dto.PlayListRequest;
import ssw.music.repository.PlayListItemRepository;
import ssw.music.repository.PlayListRepository;
import ssw.music.services.MusicService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;



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
    public String getMusics(@RequestParam(value = "isAlreadyContains", required=false) String isAlreadyContains, Model model) {
        List<MusicListView> musics = musicService.findAll().stream().map(MusicListView::new).toList();
        List<PlayList> playLists = musicService.getPlayList().stream().toList();
        PlayListItem playListItem = new PlayListItem();
        PlayListMusic playListMusic = new PlayListMusic();
        model.addAttribute("isAlreadyContains", isAlreadyContains);
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
        List<PlayListItem> playListItems = playListItemRepository.findAll().stream().
                filter(p -> p.getPlayListId() == id).toList();

        List<Music> musics = new ArrayList<Music>();

        for (PlayListItem item : playListItems) {
            musics.add(musicService.findById(item.getMusicId()));
        }

        // 특정 플레이리스트에 들어가면 해당 플레이리스트 타이틀이 뜨도록..
        PlayList playList = new PlayList();
        List<PlayList> playLists = playListRepository.findAll().stream().filter(p -> p.getId() == id).toList();

        for (PlayList pl : playLists) {
            if (pl.getId() == id)
            {
                playList = pl;
            }
        }

        model.addAttribute("playList", playList);
        model.addAttribute("musics", musics);

        return "playListPage";
    }

    @PostMapping("/addplaylistitem/{id}/{musicId}")
    public String addPlayListItem(RedirectAttributes redirect, @PathVariable("id") int playListId, @PathVariable("musicId") int musicId, @ModelAttribute("playListItem") PlayListItem playListItem) {
        Boolean isAlreadyInPlayList = false;
        // musicList.html 에서 input th:field 와 th:value 가 둘 다 적용이 되지 않아 일단 이렇게 함..
        PlayListItem item = new PlayListItem(playListId, musicId);

        // PlayListItem 테이블에서 item 과 playListId 와 musicId 가 모두 같은 것이 있는지 보면 됨..
        // List<PlayList> playLists = playListRepository.findAll().stream().filter(p -> p.getId() == playListId).toList();

        List<PlayListItem> playListItems = playListItemRepository.findAll().stream().filter(p -> (p.getPlayListId() == playListId) && (p.getMusicId() == musicId)).toList();
        if (playListItems.size() == 0)
        {
            playListItemRepository.save(item);
        }
        else
        {
            redirect.addAttribute("isAlreadyContains", "true");
        }

        

        return "redirect:/musiclist";
    }
    
}
