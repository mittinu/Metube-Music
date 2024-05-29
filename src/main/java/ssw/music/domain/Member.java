package ssw.music.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;

@Entity(name="member")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    @Builder
    public Member(String name) {
        this.name = name;
    }

    protected Member()
    {

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
