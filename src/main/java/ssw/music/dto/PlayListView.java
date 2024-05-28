package ssw.music.dto;

import lombok.Getter;
import lombok.Setter;
import ssw.music.domain.Music;
import ssw.music.domain.PlayListItem;
import ssw.music.interfaces.IPlayListView;

@Getter
@Setter
public class PlayListView {
    private final int musicId;
    private final String musicTitle;
    private final String artist;

    public PlayListView(int musicId, String musicTitle, String artist)
    {
        this.musicId = musicId;
        this.musicTitle = musicTitle;
        this.artist = artist;
    }
}
