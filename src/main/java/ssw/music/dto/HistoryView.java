package ssw.music.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HistoryView {
    private int id;
    private String musicTitle;
    private String artist;
    private String member;

    public HistoryView(int id, String musicTitle, String artist, String member) {
        this.id = id;
        this.musicTitle = musicTitle;
        this.artist = artist;
        this.member = member;
    }
}
