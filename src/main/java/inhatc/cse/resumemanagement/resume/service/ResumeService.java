package inhatc.cse.resumemanagement.resume.service;

import inhatc.cse.resumemanagement.member.entity.Member;
import inhatc.cse.resumemanagement.member.service.MemberService;
import inhatc.cse.resumemanagement.resume.dto.ResumeDto;
import inhatc.cse.resumemanagement.resume.entity.Resume;
import inhatc.cse.resumemanagement.resume.repository.ResumeRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResumeService {

    private final ResumeRepository resumeRepository;
    private final MemberService memberService;

    public List<Resume> findByMemberId(Long memberId) {
        return resumeRepository.findAllByMemberId(memberId);
    }

    public void saveResume(ResumeDto resumeDto, String userEmail) {
        // 사용자 정보 가져오기
        Member member = memberService.findByEmail(userEmail);

        // Resume 엔티티 생성 및 저장
        Resume resume = Resume.builder()
                .title(resumeDto.getTitle())
                .author(resumeDto.getAuthor())
                .category(resumeDto.getCategory())
                .memo(resumeDto.getMemo())
                .fileUrl(resumeDto.getFileUrl())
                .member(member) // Member 정보 설정
                .build();

        resumeRepository.save(resume);
    }




}