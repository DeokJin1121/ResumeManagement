package inhatc.cse.resumemanagement.member.entity;

import inhatc.cse.resumemanagement.common.entity.BaseEntity;
import inhatc.cse.resumemanagement.member.constant.Role;
import inhatc.cse.resumemanagement.member.dto.MemberDto;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member extends BaseEntity {       // 테이블인데 기본키가 없어서 오류 발생

    @Id     // 기본키 설정
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(unique = true, length = 50)
    private String email;

    @Column(nullable = false, length = 200)
    private String password;

    @Column(length = 200)
    private String address;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(length = 200)
    private String imgUrl; // 프로필 이미지 경로

    public static Member createMember(MemberDto memberDto, PasswordEncoder passwordEncoder) {

        Member member = Member.builder()
                .address(memberDto.getAddress())
                .email(memberDto.getEmail())
                .name(memberDto.getName())
                .password(passwordEncoder.encode(memberDto.getPassword()))
                .role(Role.USER)
                .build();
        return member;
    }
}
