package ssw.music.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ssw.music.domain.MusicRequest;

public interface MusicRequestRepository extends JpaRepository<MusicRequest, Long> {
}
