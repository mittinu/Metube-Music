package ssw.music.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import ssw.music.domain.PlayListItem;
import ssw.music.dto.PlayListView;
import ssw.music.interfaces.IPlayListView;

public interface PlayListItemRepository extends JpaRepository<PlayListItem, Integer> {

    //int musicId, String musicTitle, String artist
    // (m.id, m.title, m.artist)

    // @Query(value = "SELECT m.id, m.title, m.artist FROM PlayListItem as p" + 
    //             "INNER JOIN Music m on p.musicId = m.id")
	// List<PlayListView> playListViews(int pid);
}
