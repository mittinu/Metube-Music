package ssw.music.dto;

import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ssw.music.domain.History;

@NoArgsConstructor // 기본 생성자 추가
@AllArgsConstructor // 모든 필드 값을 파라미터로 받는 생성자 추가..
@Getter
public class AddHistory {
    private int musicId;
    private int memberId;

    public History toEntity() {
        return History.builder()
                .musicId(musicId)
                .memberId(memberId)
                .build();
    }
}
