package inhatc.cse.resumemanagement.resume.entity;

import inhatc.cse.resumemanagement.common.entity.BaseEntity;
import inhatc.cse.resumemanagement.member.entity.Member;
import inhatc.cse.resumemanagement.resume.dto.ResumeDto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Resume extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "resume_id")
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, length = 50)
    private String author;

    @Column(nullable = false, length = 50)
    private String category;

    @Column(nullable = false, length = 500)
    private String memo;

    @Column(length = 1000)
    private String fileUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    public static Resume createResume(ResumeDto resumeDto, Member member) {
        return Resume.builder()
                .title(resumeDto.getTitle())
                .author(resumeDto.getAuthor())
                .category(resumeDto.getCategory())
                .memo(resumeDto.getMemo())
                .fileUrl(resumeDto.getFileUrl())
                .member(member)
                .build();
    }
}
