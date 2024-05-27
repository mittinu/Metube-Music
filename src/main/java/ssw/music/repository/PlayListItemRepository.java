package ssw.music.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ssw.music.domain.PlayListItem;

public interface PlayListItemRepository extends JpaRepository<PlayListItem, Integer> {
    
}
