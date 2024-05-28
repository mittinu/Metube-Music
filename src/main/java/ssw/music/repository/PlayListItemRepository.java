package ssw.music.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import ssw.music.domain.PlayListItem;
import ssw.music.dto.PlayListView;
import ssw.music.interfaces.IPlayListView;

public interface PlayListItemRepository extends JpaRepository<PlayListItem, Integer> {

    //int musicId, String musicTitle, String artist

    @Query(value = "SELECT new PlayListView (m.id, m.title, m.artist) FROM play_list_item p " + 
                "INNER JOIN musics m on p.musicId = m.id WHERE p.playListId = :pid")
	List<PlayListView> playListViews(int pid);
}
