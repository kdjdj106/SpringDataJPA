package study.datajpa.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.entity.Member;

import java.util.List;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
@Transactional
@Rollback(false)
class MemberJpaRepositoryTest {
    @Autowired MemberJpaRepository memberJpaRepository;

    @DisplayName("")
    @Test
    public void testMember() {
        //given
        Member member = new Member("memberA");
        Member saveMember = memberJpaRepository.save(member);
        Member findMember = memberJpaRepository.find(saveMember.getId());
        //when
        //then
        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        assertThat(findMember).isEqualTo(member);


    }

    @DisplayName("")
    @Test
    public void basicCRUD() {
        //given
        Member member = new Member("member1");
        Member member2 = new Member("member2");
        memberJpaRepository.save(member);
        memberJpaRepository.save(member2);

        Member findmember1 = memberJpaRepository.findById(member.getId()).get();
        Member findmember2 = memberJpaRepository.findById(member2.getId()).get();

        List<Member> all = memberJpaRepository.findAll();
        long count = memberJpaRepository.count();
        //when
        //then
        assertThat(findmember1).isEqualTo(member);
        assertThat(findmember2).isEqualTo(member2);

        assertThat(all.size()).isEqualTo(2);
        assertThat(count).isEqualTo(2);
    }
}