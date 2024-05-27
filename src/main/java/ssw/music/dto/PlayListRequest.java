package ssw.music.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayListRequest {
    private String title;
    private int memberId;
}
