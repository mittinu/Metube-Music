package ssw.music.services;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import ssw.music.domain.History;
import ssw.music.domain.Member;
import ssw.music.domain.Music;
import ssw.music.domain.PlayList;
import ssw.music.domain.PlayListItem;
import ssw.music.dto.AddHistory;
import ssw.music.dto.BestMusic;
import ssw.music.dto.CurrentLoginMember;
import ssw.music.repository.HistoryRepository;
import ssw.music.repository.MemberRepository;
import ssw.music.repository.MusicRepository;
import ssw.music.repository.PlayListItemRepository;
import ssw.music.repository.PlayListRepository;

@RequiredArgsConstructor
@Service
public class MusicService {
    private final MusicRepository musicRepository;
    private final HistoryRepository historyRepository;
    private final PlayListRepository playListRepository;
    private final PlayListItemRepository playListItemRepository;
    private final MemberRepository memberRepository;
    
    private int loginId = 0;
    private String loginName = " ";
    
    public int getLoginId() {
        return loginId;
    }

    public void setLoginId(int loginId) {
        this.loginId = loginId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }


    public List<Music> findAll() {
        return musicRepository.findAll();
    }

    public Music findById(int id) {
        return musicRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("not found: " + id));
    }

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findMemberById(int id) {
        return memberRepository.findById(id).orElse(new Member("no Member"));
    }

    public History saveHistory(AddHistory history) {
        return historyRepository.save(history.toEntity());
    }


    public List<History> findHistory() {
        return historyRepository.findAll();
    }

    public List<PlayList> getPlayList() {
        return playListRepository.findAll();
    }

    public PlayList getPlayListById(int id) {
        return playListRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("not found: " + id));
    }

    public void deletePlayList(int id) {
        playListRepository.deleteById(id);
    }

    public void deletePlayListItem(int playListId, int musicId) {
        List<PlayListItem> playListItems = playListItemRepository.findAll().stream().filter(p -> (p.getPlayListId() == playListId) && (p.getMusicId() == musicId)).toList();
        for (PlayListItem playListItem : playListItems) {
            playListItemRepository.deleteById(playListItem.getId());
        }
    }

    public CurrentLoginMember getCurrentLoginMember() {
        CurrentLoginMember currentLoginMember = new CurrentLoginMember();
        currentLoginMember.setCurrentLoginId(loginId);
        currentLoginMember.setCurrentLoginName(loginName);

        return currentLoginMember;
    }

    public List<BestMusic> getBestMusics() {
        
        // (음악아이디, 개수)의 형식으로 가장 많은 개수를 가진 음악 3개를 뽑는거..
        Map<Integer, Integer> historyCount = new HashMap<Integer, Integer>();
        
        List<History> histories = historyRepository.findAll();

        List<BestMusic> bestMusics = new ArrayList<BestMusic>();

        for (int i = 0; i < histories.size(); i++) {
            if (historyCount.containsKey(histories.get(i).getMusicId()))
            {
                historyCount.put(histories.get(i).getMusicId(), historyCount.get(histories.get(i).getMusicId()) + 1);
            }
            else
            {
                historyCount.put(histories.get(i).getMusicId(), 1);
            }
        }

        // {"음악ID" : "재생횟수"} 로 된 historyCount Map 을 Value 값을 기준으로 정렬..
        List<Integer> keySet = new ArrayList<>(historyCount.keySet());

        keySet.sort((n1, n2) -> historyCount.get(n1).compareTo(historyCount.get(n2)));

        for (Integer integer : keySet) {
            BestMusic bestMusic = new BestMusic();
            bestMusic.setMusicId(integer);
            bestMusic.setMusicTitle(musicRepository.findById(integer)
            .orElseThrow(() -> new IllegalArgumentException("not found: ")).getTitle());
            bestMusic.setArtist(musicRepository.findById(integer)
            .orElseThrow(() -> new IllegalArgumentException("not found: ")).getArtist());

            bestMusics.add(bestMusic);
        }

        // 상위 3개만 리턴..
        return bestMusics.reversed().subList(0, 3);
    }
}
