package ssw.music.dto;

import lombok.Getter;
import ssw.music.domain.Music;

@Getter
public class MusicView {
    private final int id;
    private final String title;
    private final String artist;
    private final String audioPath;

    public MusicView(Music music)
    {
        this.id = music.getId();
        this.title = music.getTitle();
        this.artist = music.getArtist();
        audioPath = this.title + Integer.toString(this.id) + ".mp3";
    }
}
