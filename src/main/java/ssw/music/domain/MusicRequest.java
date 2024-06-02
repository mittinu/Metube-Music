package ssw.music.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "music_request")
@Builder
public class MusicRequest{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String albumTitle;

    @Column(nullable = false)
    private String artist;

    @Column(nullable = false)
    private String releaseDate;

    @Column(nullable = false)
    private String genre;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "MusicRequest{" +
                "id=" + id +
                ", albumTitle='" + albumTitle + '\'' +
                ", artist='" + artist + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", genre='" + genre + '\'' +
                '}';
    }


}
