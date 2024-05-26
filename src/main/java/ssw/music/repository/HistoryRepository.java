package ssw.music.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ssw.music.domain.History;

// 데이터베이스와 연결하기 위한 것..
public interface HistoryRepository extends JpaRepository<History, Integer> {
    
}
