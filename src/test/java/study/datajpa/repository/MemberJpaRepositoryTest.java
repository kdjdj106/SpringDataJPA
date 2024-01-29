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

    @DisplayName("")
    @Test
    public void paging() throws Exception {
        //given
        memberJpaRepository.save(new Member("member1", 10));
        memberJpaRepository.save(new Member("member2", 10));
        memberJpaRepository.save(new Member("member3", 10));
        memberJpaRepository.save(new Member("member4", 10));
        memberJpaRepository.save(new Member("member5", 10));
        memberJpaRepository.save(new Member("member6", 10));
        memberJpaRepository.save(new Member("member7", 10));
        int age = 10;
        int offset = 0;
        int limit = 3;

        //when
        List<Member> members = memberJpaRepository.findByPage(age, offset, limit);
        for (Member member : members) {
            System.out.println("member = " + member);
        }
        long totalCount = memberJpaRepository.totalCount(age);
        //then

        assertThat(members.size()).isEqualTo(3);
        assertThat(totalCount).isEqualTo(7);
    }
}