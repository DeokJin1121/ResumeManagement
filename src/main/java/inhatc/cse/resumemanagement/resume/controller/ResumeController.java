package inhatc.cse.resumemanagement.resume.controller;

import inhatc.cse.resumemanagement.member.entity.Member;
import inhatc.cse.resumemanagement.member.service.MemberService;
import inhatc.cse.resumemanagement.resume.dto.ResumeDto;
import inhatc.cse.resumemanagement.resume.entity.Resume;
import inhatc.cse.resumemanagement.resume.service.FileStorageService;
import inhatc.cse.resumemanagement.resume.service.ResumeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/resume")
public class ResumeController {

    private final MemberService memberService;
    private final FileStorageService fileStorageService;
    private final ResumeService resumeService; // 이력서 저장 서비스


    @GetMapping("/resume_list")
    public String resumeList(Model model, Principal principal) {
        // 현재 로그인된 사용자의 이메일 또는 ID 가져오기
        String email = principal.getName();
        Member member = memberService.findByEmail(email);

        // 해당 사용자의 이력서를 가져오기
        List<Resume> resumes = resumeService.findByMemberId(member.getId());

        // 데이터를 모델에 추가
        model.addAttribute("resumes", resumes);

        return "resume/resume_list"; // 해당 HTML로 데이터 전달
    }

    @GetMapping("/resume_editor")
    public String showResumeEditor(Model model) {
        model.addAttribute("resumeDto", new ResumeDto()); // 비어 있는 DTO로 화면 출력 가능
        return "resume/resume_editor";
    }

    @PostMapping("/save")
    public String saveResume(@ModelAttribute @Valid ResumeDto resumeDto,
                             @RequestParam("file") MultipartFile file,
                             BindingResult result,
                             Model model,
                             Principal principal) { // 현재 로그인한 사용자 정보 가져오기
        if (result.hasErrors()) {
            return "resume/resume_editor";
        }

        try {
            // 파일 저장
            String filePath = fileStorageService.saveFile(file);
            resumeDto.setFileUrl(filePath);

            // 현재 로그인한 사용자의 이메일
            String userEmail = principal.getName();

            // 이력서 저장
            resumeService.saveResume(resumeDto, userEmail);

            return "redirect:/resume/resume_list";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "저장 중 오류가 발생했습니다.");
            return "resume/resume_editor";
        }
    }


    @GetMapping("/resume_mypage")
    public String resumeMypage(Model model, Principal principal) {
        // 현재 로그인한 사용자의 이메일 가져오기
        String email = principal.getName();

        // 사용자 정보 가져오기
        Member member = memberService.findByEmail(email);

        // 사용자 정보를 모델에 추가
        model.addAttribute("member", member);

        return "resume/resume_mypage"; // 템플릿 파일 경로
    }

}
