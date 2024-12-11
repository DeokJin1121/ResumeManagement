package inhatc.cse.resumemanagement.member.controller;

import inhatc.cse.resumemanagement.member.dto.MemberDto;
import inhatc.cse.resumemanagement.member.entity.Member;
import inhatc.cse.resumemanagement.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final PasswordEncoder passwordEncoder;   // 객체로 생성
    private final MemberService memberService;

    @GetMapping("/login")
    public String login() {
        return "member/login";
    }

    @GetMapping("/login/error")
    public String loginError(Model model) {
        model.addAttribute("loginErrorMsg", "아이디 또는 패스워드를 확인해주세요.");
        return "member/login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response,authentication);
        }
        return "redirect:/";
    }

    @GetMapping("/add")
    public String memberAddForm(Model model) {
        model.addAttribute("memberDto", new MemberDto());
        return "member/add";
    }


    @PostMapping("/add")
    public String memberNew(@Valid MemberDto memberDto,   // 웹에서 데이터를 들고 옴
                            BindingResult bindingResult,  // 바인딩 된 결과
                            Model model) {                // 웹에 보낼 데이터

        // valid 할 때 오류 발생시 처리
        if(bindingResult.hasErrors()) {
            return "member/add";
        }

        try {
            Member member = Member.createMember(memberDto, passwordEncoder);
            memberService.saveMember(member);
        } catch (IllegalStateException e) {
            // 예외 발생시 등록 페이지에 전달할 페이지
            model.addAttribute("errorMessage", e.getMessage());
            return "member/add";
        }
        return "member/login";
    }

    @GetMapping("/member/mypage")
    public String myPage(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        Member member = memberService.findByEmail(userDetails.getUsername());
        model.addAttribute("member", member);
        return "resume/resume_mypage";
    }
}
