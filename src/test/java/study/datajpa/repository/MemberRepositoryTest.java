package study.datajpa.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;
import study.datajpa.entity.Team;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(false)
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    TeamRepository teamRepository;

    @DisplayName("스프링데이타 JPA")
    @Test
    public void testMember() {
        //given
        Member member = new Member("memberA");
        Member saveMember = memberRepository.save(member);

        Member findMember = memberRepository.findById(member.getId()).get();

        assertThat(findMember.getId()).isEqualTo(saveMember.getId());
        assertThat(findMember.getUsername()).isEqualTo(saveMember.getUsername());
        assertThat(findMember).isEqualTo(saveMember);


        //when
        //then
    }

    @Test
    public void basicCRUD() {
        //given
        Member member = new Member("member1");
        Member member2 = new Member("member2");
        memberRepository.save(member);
        memberRepository.save(member2);

        Member findmember1 = memberRepository.findById(member.getId()).get();
        Member findmember2 = memberRepository.findById(member2.getId()).get();

        List<Member> all = memberRepository.findAll();
        long count = memberRepository.count();
        //when
        //then
        assertThat(findmember1).isEqualTo(member);
        assertThat(findmember2).isEqualTo(member2);

        assertThat(all.size()).isEqualTo(2);
        assertThat(count).isEqualTo(2);
    }


    @Test
    public void findByUsernameAndAgeGreaterThan() throws Exception {
        //given
        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("AAA", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);
        //when
        List<Member> result = memberRepository.findByUsernameAndAgeGreaterThan("AAA", 15);
        //then

        assertThat(result.get(0).getUsername()).isEqualTo("AAA");
        assertThat(result.get(0).getAge()).isEqualTo(20);
        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    public void testQueryFindUser() throws Exception {
        //given
        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("AAA", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);
        //when
        List<Member> result = memberRepository.findUser("AAA", 10);
        //then
        assertThat(result.get(0)).isEqualTo(m1);
    }

    @Test
    public void findMemberDto() throws Exception {
        //given
        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("AAA", 20);
        Team team = new Team("teamA");
        teamRepository.save(team);

        m1.setTeam(team);
        memberRepository.save(m1);
        memberRepository.save(m2);
        //when
        List<MemberDto> memberDto = memberRepository.findMemberDto();
        //then
        for (MemberDto dto : memberDto) {
            System.out.println("dto = " + dto.getTeamName() + " " + dto.getUsername() + " " + dto.getId());
        }
    }

    @Test
    public void findByNames() throws Exception {
        //given
        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("AAA", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);
        //when
        List<Member> result = memberRepository.findByNames(Arrays.asList("AAA", "BBB"));
        //then
        for (Member member : result) {
            System.out.println("member = " + member);
        }
    }

    @Test
    public void returnType() throws Exception {
        //given
        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("BBB", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);
        //when
        Member aaa = memberRepository.findMemberByUsername("AAA");
        System.out.println("aaa = " + aaa);
        Optional<Member> aaa1 = memberRepository.findOptionalByUsername("AAA");
        System.out.println("aaa1 = " + aaa1);
        //then

    }

    @DisplayName("")
    @Test
    public void callCustom() throws Exception {
        //given
        List<Member> result = memberRepository.findMemberCustom();
        //when
        //then
    }

}