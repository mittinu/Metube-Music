package ssw.music.services;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.util.List;

import ssw.music.domain.History;
import ssw.music.domain.Music;
import ssw.music.domain.PlayList;
import ssw.music.dto.AddHistory;
import ssw.music.repository.HistoryRepository;
import ssw.music.repository.MusicRepository;
import ssw.music.repository.PlayListRepository;

@RequiredArgsConstructor
@Service
public class MusicService {
    private final MusicRepository musicRepository;
    private final HistoryRepository historyRepository;
    private final PlayListRepository playListRepository;
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
}
