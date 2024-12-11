package inhatc.cse.resumemanagement.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${uploadPath}") // 클라이언트가 접근할 경로
    private String uploadPath;

    @Value("${resumeImgLocation}") // 서버에 저장되는 실제 경로
    private String fileStorageLocation;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resume-images/**") // 브라우저에서 접근하는 URL 패턴
                .addResourceLocations("file:///" + fileStorageLocation + "/"); // 서버의 실제 파일 경로
    }
}
