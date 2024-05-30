package ssw.music.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import lombok.RequiredArgsConstructor;
import ssw.music.domain.History;
import ssw.music.domain.Member;
import ssw.music.domain.Music;
import ssw.music.domain.PlayList;
import ssw.music.domain.PlayListItem;
import ssw.music.dto.AddHistory;
import ssw.music.dto.BestMusic;
import ssw.music.dto.CurrentLoginMember;
import ssw.music.dto.HistoryView;
import ssw.music.dto.LoginMemberRequest;
import ssw.music.dto.MusicListView;
import ssw.music.dto.PlayListMemberNameView;
import ssw.music.dto.PlayListMusic;
import ssw.music.dto.PlayListRequest;
import ssw.music.repository.MemberRepository;
import ssw.music.repository.PlayListItemRepository;
import ssw.music.repository.PlayListRepository;
import ssw.music.services.MusicService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.RequestBody;


@RequiredArgsConstructor
@Controller
public class MusicViewController {
    
    private final MusicService musicService;
    private final PlayListRepository playListRepository;
    private final PlayListItemRepository playListItemRepository;
    private final MemberRepository memberRespository;


    @GetMapping("/playmusic")
    public String playMusic(Model model) {
        return "playingPage";
    }

    @GetMapping("/getLoginId/{id}")
    public String getLoginId(@RequestParam String param) {
        return new String();
    }

    @GetMapping("/postLoginId/{id}")
    public String postMethodName(@PathVariable("id") int loginId) {
        //TODO: process POST request
        
        musicService.setLoginId(loginId);

        return "redirect:/";
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
        model.addAttribute("currentLoginMember", musicService.getCurrentLoginMember());
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
        model.addAttribute("currentLoginMember", musicService.getCurrentLoginMember());
        return "music";
    }

    // @GetMapping("/addHistoryInPlayList/{id}")
    // public String postAddHistoryInPlayList(@PathVariable("id") int id) {
    //     //TODO: process POST request
    //     Music music = musicService.findById(id);
    //     AddHistory addHistory = new AddHistory(music.getId(), musicService.getLoginId());
    //     musicService.saveHistory(addHistory);  
        
    //     return "redirect:/";
    // }
    


    @GetMapping("/")
    public String getMain(Model model, LoginMemberRequest loginMember) {
        Boolean isLoginPage = true;
        List<Member> members = musicService.findMembers().stream().toList();
        List<BestMusic> bestMusics = musicService.getBestMusics();

        int currentLoginId = musicService.getLoginId();

        model.addAttribute("members", members);
        model.addAttribute("isLoginPage", isLoginPage);
        // 레이아웃에서 로그인한 사람의 id 를 받기 위해서..
        model.addAttribute("loginMember", loginMember);
        model.addAttribute("currentLoginId", currentLoginId);

        model.addAttribute("currentLoginMember", musicService.getCurrentLoginMember());
        // model.addAttribute("bestMusics", musicService.getBestMusics());
        model.addAttribute("bestMusics", bestMusics);

        return "main";
    }

    @PostMapping("/postLoginId")
    public String postLoginId(@ModelAttribute("loginMember") LoginMemberRequest loginMember) {
        //TODO: process POST request
        
        if (loginMember.getLoginId() == 0)
        {
            musicService.setLoginId(0);
            musicService.setLoginName("");
        }
        else
        {
            musicService.setLoginId(loginMember.getLoginId());
            String memberName = musicService.findMemberById(loginMember.getLoginId()).getName();
            musicService.setLoginName(memberName);
        }

        return "redirect:/";
    }
    

    @GetMapping("/history")
    public String getHistory(Model model) {

        List<History> histories = musicService.findHistory().stream().toList().reversed();

        List<HistoryView> historyViews = new ArrayList<HistoryView>();

        
        // History 클래스의 musicId 와 memberId 를 통해 객체 넘겨주기..
        for (History history : histories) {
            HistoryView historyView = new HistoryView(0, 0, "", "", "");
            historyView.setId(history.getId());
            historyView.setMusicId(history.getMusicId());
            historyView.setMusicTitle(musicService.findById(history.getMusicId()).getTitle());
            historyView.setArtist(musicService.findById(history.getMusicId()).getArtist());
            historyView.setMember(musicService.findMemberById(history.getMemberId()).getName());

            historyViews.add(historyView);
        }

        model.addAttribute("historyViews", historyViews);
        model.addAttribute("currentLoginMember", musicService.getCurrentLoginMember());

        return "history";
    }

    @GetMapping("/playlists")
    public String getPlayLists(Model model, PlayListRequest playListRequest) {
        List<PlayList> playLists = musicService.getPlayList().stream().toList();


        List<PlayListMemberNameView> playListMemberNameViews = new ArrayList<PlayListMemberNameView>();

        // 플레이리스트 페이지에서 플레이리스트 이름, 플레이리스트 만든 사람 보여주기 위한 작업..
        for (PlayList playList : playLists) {
            PlayListMemberNameView playListMemberNameView = new PlayListMemberNameView(0, "asdf", "asdf");
            playListMemberNameView.setPlayListId(playList.getId());
            playListMemberNameView.setPlayListTitle(musicService.getPlayListById(playList.getId()).getTitle());
            playListMemberNameView.setMemberName(musicService.findMemberById(playList.getMemberId()).getName());

            playListMemberNameViews.add(playListMemberNameView);
        }

        model.addAttribute("playListMemberNameViews", playListMemberNameViews);
        model.addAttribute("playListRequest", playListRequest);
        model.addAttribute("currentLoginMember", musicService.getCurrentLoginMember());

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
        model.addAttribute("currentLoginMember", musicService.getCurrentLoginMember());

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

    // api를 사용한 통신의 경우에는 @DeleteMapping을 통한 삭제가 가능하지만 form 태그나 uri로 직접 접근하는 경우에는 @DeleteMapping 사용이 불가..
    // 이때 hidden 타입의 input 태그를 이용하면 가능하긴함..
    @GetMapping("/deleteplaylist/{playListId}")
    public String deletePlayList(@PathVariable("playListId") int playListId, Model model) {
        musicService.deletePlayList(playListId);
        

        // 플레이리스트를 삭제하면 그 안에 포함되어 있는 음악들도 PlayListItem 테이블에서 삭제해야함..
        List<PlayListItem> playListItems = playListItemRepository.findAll().stream().toList();

        for (PlayListItem playListItem : playListItems) {
            if (playListItem.getPlayListId() == playListId)
            {
                playListItemRepository.deleteById(playListItem.getId());
            }
        }

        return "redirect:/playlists";
    }

    @GetMapping("/deleteplaylistitem/{playListId}/{musicId}")
    public String deletePlayListItem(RedirectAttributes redirect, @PathVariable("playListId") int playListId, @PathVariable("musicId") int musicId, Model model) {

         musicService.deletePlayListItem(playListId, musicId);

         redirect.addAttribute("id", playListId);

        return "redirect:/playlist/{id}";
    }
    
    
}
