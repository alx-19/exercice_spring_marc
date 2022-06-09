package com.alex.buildindingAndco.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public UserDetailsService userDetailsService() {
        return new MyUserDetailsServiceImpl();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setPasswordEncoder(passwordEncoder());
        authProvider.setUserDetailsService(userDetailsService());
        return authProvider;
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) {
        authenticationManagerBuilder.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().and().authorizeRequests()
                .antMatchers(HttpMethod.POST, "/v1/managers/**").hasAnyRole("USER","ADMIN")
                .antMatchers(HttpMethod.POST, "/v1/technicians/**").hasAnyRole("USER","ADMIN")
                .antMatchers(HttpMethod.POST, "/v1/vehicles/**").hasAnyRole("USER","ADMIN")
                .antMatchers(HttpMethod.POST, "/v1/worksites/**").hasAnyRole("USER","ADMIN")
                .antMatchers(HttpMethod.POST, "/v1/adresses/**").hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/v1/managers/**").hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/v1/technicians/**").hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/v1/vehicles/**").hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/v1/worksites/**").hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/v1/adresses/**").hasAnyRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .and().csrf().disable();

    }

}
