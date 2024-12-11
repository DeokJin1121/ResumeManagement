package inhatc.cse.resumemanagement.resume.repository;

import inhatc.cse.resumemanagement.resume.entity.Resume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResumeRepository extends JpaRepository<Resume, Long> {
    List<Resume> findAllByMemberId(Long memberId); // Member ID로 이력서 조회
}
