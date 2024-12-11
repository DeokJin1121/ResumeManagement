package inhatc.cse.resumemanagement.member.service;

import inhatc.cse.resumemanagement.member.entity.Member;
import inhatc.cse.resumemanagement.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;

    private void validateDupMember(Member member) {     // 사용자가 중복 여부

        Optional<Member> optMember = memberRepository.findByEmail(member.getEmail());
        if(optMember.isPresent()) {
            Member member1 = optMember.get();
            throw new IllegalStateException("이미 존재하는 사용자 입니다");
        }

    }

    public Member saveMember(Member member) {
        validateDupMember(member);
        Member m = memberRepository.save(member);
        return m;
    }

    @Override
    public UserDetails loadUserByUsername(String eamil) throws UsernameNotFoundException {

        Member member = memberRepository.findByEmail(eamil)
                .orElseThrow(() -> new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다." + eamil));

        log.info(member.toString());

        return User.builder()
                .username(member.getEmail())
                .password(member.getPassword())
                .roles(member.getRole().toString())
                .build();
    }

    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다: " + email));
    }

}

