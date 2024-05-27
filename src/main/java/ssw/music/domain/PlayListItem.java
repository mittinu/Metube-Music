package ssw.music.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;

@Entity(name="play_list_item")
public class PlayListItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private int id;

    @Column(name = "play_list_id", nullable = false)
    private int playListId;

    @Column(name = "music_id", nullable = false)
    private int musicId;

    // List<Music> 을 담을 컬럼도 필요함..

    @Builder
    public PlayListItem(int playListId, int memeberId) {
        this.playListId = playListId;
        this.musicId = memeberId;
    }

    public PlayListItem()
    {

    }

    public int getId() {
        return id;
    }

    public int getPlayListId() {
        return playListId;
    }

    public int getMusicId() {
        return musicId;
    }
}
