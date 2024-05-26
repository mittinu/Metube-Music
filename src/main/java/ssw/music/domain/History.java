package ssw.music.domain;

import lombok.Builder;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name="history")
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private int id;

    @Column(name = "musicId", nullable = false)
    private int musicId;

    @Column(name = "memberId", nullable = false)
    private int memberId;

    @Builder
    public History(int musicId, int memberId) {
        this.musicId = musicId;
        this.memberId = memberId;
    }

    protected History()
    {

    }

    public int getId() {
        return id;
    }

    public int getMusicId() {
        return musicId;
    }

    public int getMemberId() {
        return memberId;
    }
}
