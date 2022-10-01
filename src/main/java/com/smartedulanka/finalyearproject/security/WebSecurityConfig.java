package com.smartedulanka.finalyearproject.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


        @Bean
        public UserDetailsService userDetailsService() {
            return new CustomUserDetailsService();
        }

        @Bean
        public BCryptPasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }

        @Bean
        public DaoAuthenticationProvider authenticationProvider() {

            DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
            authProvider.setUserDetailsService(userDetailsService());
            authProvider.setPasswordEncoder(passwordEncoder());

            return authProvider;
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {

            auth.authenticationProvider(authenticationProvider());
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {

                 http.csrf().disable()

                .authorizeRequests()
                .antMatchers("/users","/forum.html","/advancedLevelScienceEnglishMedium.html","/advancedLevelScienceSinhalaMedium.html","/advancedLevelScienceTamilMedium.html","/olympiadEnglishMedium.html","/olympiadSinhalaMedium.html","/olympiadTamilMedium.html","/advancedLevelCommerceEnglish","/advancedlevelCommerceSinhala","/advancedlevelCommerceTamil","/advancedLevelEnglishStreams.html","/advancedLevelSinhalaStreams.html","/primarySectionSinhalaMedium.html","/ordinaryLevelSinhalaMedium.html","/otherGovernmentExamsSinhalaMedium.html","/primarySectionEnglishMedium.html","/ordinaryLevelEnglishMedium.html","/otherGovernmentExamsEnglishMedium.html","/primarySectionTamilMedium.html","/ordinaryLevelTamilMedium.html","/advancedLevelTamilStreams.html","/OtherGovernmentExamsTamilMedium.html","/otherGovernmentExamsTamilMedium.html").authenticated()
                .antMatchers("/admin.html").hasAuthority("ADMIN")

                .antMatchers("/forum.html").hasAnyAuthority("ADMIN","USER")
                .antMatchers("/","/uploadFile").permitAll()
                .and().formLogin()
                . permitAll()

                .loginPage("/login")
                .usernameParameter("email")

            .and()
            .logout().permitAll()
            .and()
            .exceptionHandling().accessDeniedPage("/403");

        }




    }
