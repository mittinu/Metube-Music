package ssw.music.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ssw.music.domain.Member;


public interface MemberRepository extends JpaRepository<Member, Integer> {
    
}
