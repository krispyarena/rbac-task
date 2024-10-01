package org.demo.amniltask.configuration;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.demo.amniltask.model.Admin;
import org.demo.amniltask.service.impl.CustomDetailsImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.util.Set;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "signup").permitAll()
                        .requestMatchers("/admin/**","/").hasAnyAuthority("admin")
                        .anyRequest().authenticated())

                .logout(logout -> logout.logoutSuccessUrl("/login?logout = true")
                .logoutUrl("/logout")
                .invalidateHttpSession(true)
                        .permitAll())

                .formLogin(form->form.loginPage("/login")
                .successHandler(new AuthenticationRedirect())
                        .failureUrl("/login?error=true"))

                .csrf(AbstractHttpConfigurer::disable)

                .sessionManagement(session -> session
                .maximumSessions(1)
                        .maxSessionsPreventsLogin(true))

                .build();

    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomDetailsImpl();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    public AuthenticationSuccessHandler successHandler(){
        return new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

                Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

                System.out.println(roles);
                System.out.println(roles.contains("admin"));

                if(roles.contains("admin")){
                    response.sendRedirect("/");
                }
                else {
                    response.sendRedirect("/studentPage");
                }
            }
        };
    }
}
