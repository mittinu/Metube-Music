package ssw.music.dto;

import lombok.Getter;
import ssw.music.domain.MusicRequest;

@Getter
public class MusicRequestDto {

    private final Long id;

    public MusicRequestDto(MusicRequest musicRequest)
    {
        this.id = musicRequest.getId();
    }
}
