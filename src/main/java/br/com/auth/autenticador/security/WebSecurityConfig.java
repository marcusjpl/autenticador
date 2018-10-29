package br.com.auth.autenticador.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
//             authorizeRequests()
//               	 .antMatchers("/css/**","/js/**","/less/**","/vendor/**","/dist/**").permitAll()
//                  //.antMatchers("/dados-acesso").hasAnyRole("EDITOR")
//                  //.antMatchers("/lista-usuarios").hasAnyRole("ADMIN")
//                  .anyRequest()
//                  .authenticated()
//                  .and()
//             .formLogin()
//                  .loginPage("/configuracao/login")
//                  .defaultSuccessUrl("/configuracao")
//                  .permitAll()
//                  .and()
//             .logout().logoutSuccessUrl("/configuracao/login")
//                  .permitAll()
//                  .and()
//                  
//             .rememberMe();
    }
    
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder builder) throws Exception {
        builder
             .inMemoryAuthentication()
             .withUser("marcus").password("$2a$10$RkTVD0vVnTO9PcU2VbSOButxB3bavOmic/.cuhp4.0a9uml5Vg.bm").roles("EDITOR", "ADMIN")
             .and()
             .withUser("admin").password("$2a$10$Ea9L4OmqxVsfJ2vIfdkixeLIov4ObrUTQweks1jR6bT6P4kzRh2r6").roles("EDITOR", "ADMIN");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
