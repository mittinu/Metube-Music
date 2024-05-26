package ssw.music.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ssw.music.domain.PlayList;

public interface PlayListRepository extends JpaRepository<PlayList, Integer> {
    
}

