package ssw.music.services;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.util.List;

import ssw.music.domain.History;
import ssw.music.domain.Music;
import ssw.music.domain.PlayList;
import ssw.music.domain.PlayListItem;
import ssw.music.dto.AddHistory;
import ssw.music.repository.HistoryRepository;
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
    private int loginId = 99;

    public List<Music> findAll() {
        return musicRepository.findAll();
    }

    public Music findById(int id) {
        return musicRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("not found: " + id));
    }

    public History saveHistory(AddHistory history) {
        return historyRepository.save(history.toEntity());
    }

    public int getLoginId() {
        return loginId;
    }

    public List<History> findHistory() {
        return historyRepository.findAll();
    }

    public List<PlayList> getPlayList() {
        return playListRepository.findAll();
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
}
