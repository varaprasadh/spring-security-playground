package com.vara.springsecurityplayground.config;


import com.vara.springsecurityplayground.filters.RobotAuthenticationProvider;
import com.vara.springsecurityplayground.filters.RobotFilter;
import com.vara.springsecurityplayground.filters.RobotLoginConfigurer;
import com.vara.springsecurityplayground.filters.VaraAuthenticationProvider;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, AuthenticationEventPublisher authenticationEventPublisher) throws Exception {
        {
            httpSecurity.getSharedObject(AuthenticationManagerBuilder.class).authenticationEventPublisher(authenticationEventPublisher);
        }

//        AuthenticationManager authenticationManager = new ProviderManager(new RobotAuthenticationProvider(List.of("beep-boop", "boop-boop")));

        return httpSecurity
                .authorizeRequests()
                .antMatchers("/marketing").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin(withDefaults())
                .oauth2Login(withDefaults())
                .apply(new RobotLoginConfigurer())
                .and()
//                .addFilterBefore(new RobotFilter(authenticationManager), UsernamePasswordAuthenticationFilter.class)
                .authenticationProvider(new VaraAuthenticationProvider())
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new InMemoryUserDetailsManager(
                User.builder().username("user").password("{noop}password").authorities("ROLE_USER").build()
        );

    }

    @Bean
    public ApplicationListener<AuthenticationSuccessEvent> successEventApplicationListener() {
        return event -> {
            System.out.println(
                    String.format("success [%s] %s", event.getAuthentication().getClass().getName(), event.getAuthentication().getName())
            );
        };
    }
}

