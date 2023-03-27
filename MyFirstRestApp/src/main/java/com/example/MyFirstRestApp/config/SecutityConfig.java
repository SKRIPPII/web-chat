package com.example.MyFirstRestApp.config;

import com.example.MyFirstRestApp.services.PersonDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecutityConfig extends WebSecurityConfigurerAdapter {
    private final PersonDetailsService personDetailsService;


    @Autowired
    public SecutityConfig(PersonDetailsService personDetailsService) {
        this.personDetailsService = personDetailsService;
    }
    @Override
    protected  void configure(HttpSecurity http) throws Exception{
        http.authorizeRequests()
                .antMatchers("/api/admin").hasRole("ADMIN")
                .antMatchers("/auth/login","/auth/reg","/error","/css/**","/js/**").permitAll()
                .anyRequest().hasAnyRole("USER","ADMIN")
                .and()
                .formLogin().loginPage("/auth/login")
                .loginProcessingUrl("/process_login")
                .defaultSuccessUrl("/api/hello",true)
                .failureUrl("/auth/login?error");
    }

    protected void configure(AuthenticationManagerBuilder managerBuilder) throws Exception {
        managerBuilder.userDetailsService(personDetailsService).passwordEncoder(getPasswordEncoder());
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
