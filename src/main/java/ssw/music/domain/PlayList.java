// package ssw.music.domain;

// import jakarta.persistence.Column;
// import jakarta.persistence.Entity;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
// import jakarta.persistence.Id;
// import lombok.Builder;

// @Entity(name="musics")
// public class PlayList {
//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     @Column(name = "id", updatable = false)
//     private int id;

//     @Column(name = "title", nullable = false)
//     private String title;

//     @Column(name = "member", nullable = false)
//     private String member;

//     // List<Music> 을 담을 컬럼도 필요함..

//     @Builder
//     public PlayList(String title, String memeber) {
//         this.title = title;
//         this.member = memeber;
//     }

//     protected PlayList()
//     {

//     }

//     public int getId() {
//         return id;
//     }

//     public String getTitle() {
//         return title;
//     }

//     public String getMember() {
//         return member;
//     }
// }
