package inhatc.cse.resumemanagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // 로그인 설정
        http.formLogin(form -> form
                .loginPage("/member/login")
                .defaultSuccessUrl("/")  // 로그인 성공 시 이동할 URL
                .failureUrl("/member/login/error") // 로그인 실패 시 이동할 URL
                .usernameParameter("email")
                .passwordParameter("password")
                .permitAll()
        );

        // 로그아웃 설정
        http.logout(logout -> logout
                .logoutUrl("/member/logout") // 로그아웃 URL
                .logoutSuccessUrl("/") // 로그아웃 성공 시 이동할 URL
                .permitAll()
        );

        // URL 접근 권한 설정
        http.authorizeHttpRequests(request -> request
                .requestMatchers("/css/**", "/js/**", "/img/**", "/static/**").permitAll() // 정적 리소스 허용
                .requestMatchers("/", "/member/**", "/resume-images/**", "/resume/**").permitAll() // 비로그인 접근 허용
                .requestMatchers("/admin/**").hasRole("ADMIN") // 관리자 페이지는 ADMIN 권한만 접근 가능
                .anyRequest().authenticated() // 나머지 요청은 인증 필요
        );

        // 인증/인가 예외 처리
        http.exceptionHandling(exception -> exception
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint()));

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
