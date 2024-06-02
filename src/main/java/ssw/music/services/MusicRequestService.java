package ssw.music.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import ssw.music.domain.MusicRequest;
import ssw.music.repository.MusicRequestRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MusicRequestService {

    private final MusicRequestRepository musicRequestRepository;

    public void saveMusicRequest(MusicRequest musicRequest) {
        musicRequestRepository.save(musicRequest);
    }

    public List<MusicRequest> findAll() {
        return musicRequestRepository.findAll();
    }
}
