package ssw.music.dto;

import lombok.Getter;
import ssw.music.domain.Music;

@Getter
public class MusicListView {
    private final int id;
    private final String title;
    private final String artist;

    public MusicListView(Music music)
    {
        this.id = music.getId();
        this.title = music.getTitle();
        this.artist = music.getArtist();
    }
}
