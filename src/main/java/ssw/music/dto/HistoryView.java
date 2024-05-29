package ssw.music.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HistoryView {
    private int id;
    private int musicId;
    private String musicTitle;
    private String artist;
    private String member;

    public HistoryView(int id, int musicId,String musicTitle, String artist, String member) {
        this.id = id;
        this.musicId = musicId;
        this.musicTitle = musicTitle;
        this.artist = artist;
        this.member = member;
    }
}
