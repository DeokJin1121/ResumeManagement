package inhatc.cse.resumemanagement.resume.dto;

import inhatc.cse.resumemanagement.common.entity.BaseEntity;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResumeDto extends BaseEntity {

    private Long resumeId;

    @NotBlank(message = "제목은 필수 입력 값입니다.")
    private String title;

    @NotBlank(message = "작성자는 필수 입력 값입니다.")
    private String author;

    @NotBlank(message = "카테고리는 필수 입력 값입니다.")
    private String category;

    private String memo;

    private MultipartFile file; // 이력서 파일 URL
    private String fileUrl; // 파일 경로

    private Long memberId; // 사용자 ID 추가

    private String fileName; // 파일 이름 필드 추가

}
