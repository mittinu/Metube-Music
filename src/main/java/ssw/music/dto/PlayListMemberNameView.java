package ssw.music.dto;

import lombok.Getter;

@Getter
public class PlayListMemberNameView {
    private int playListId;
    private String playListTitle;
    private String memberName;

    public PlayListMemberNameView(int playListId, String playListTitle, String memberName) {
        this.playListId = playListId;
        this.playListTitle = playListTitle;
        this.memberName = memberName;
    }

    public void setPlayListId(int id) {
        this.playListId = id;
    }

    public void setPlayListTitle(String title) {
        this.playListTitle = title;
    }

    public void setMemberName(String name) {
        this.memberName = name;
    }
}
