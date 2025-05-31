package umi.fs.hopital;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager(PasswordEncoder passwordEncoder) {
        String encodedPassword = passwordEncoder.encode("SDIA");
        System.out.println(encodedPassword);
        return new InMemoryUserDetailsManager(
                org.springframework.security.core.userdetails.User.withUsername("user1").password(encodedPassword).roles("USER").build(),
                org.springframework.security.core.userdetails.User.withUsername("user2").password(encodedPassword).roles("USER").build(),
                org.springframework.security.core.userdetails.User.withUsername("admin").password(encodedPassword).roles("USER", "ADMIN").build()
        );
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/index", true)
                        .failureUrl("/login?error=true")
                        .permitAll())
                .logout(logout -> logout.logoutUrl("/logout").logoutSuccessUrl("/login?logout").permitAll())
                .exceptionHandling(eh -> eh.accessDeniedPage("/error"))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/error", "/webjars/**", "/css/**").permitAll()
                        .requestMatchers("/deletePatient/**", "/admin/**").hasRole("ADMIN")
                        .requestMatchers("/user/**").hasRole("USER")
                        .anyRequest().authenticated()
                )
                .build();
    }
}