package com.demo.security;

import com.demo.utilities.security.SecurityRoles;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration      // Because it's a configuration class
@EnableWebSecurity  // To enable the security
public class SecurityConfig extends WebSecurityConfigurerAdapter {  // Must extend this class to implement the spring security configuration aspect

    /**
     * In here we indicate how to look for the users
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        // --- Those users should be put in DB afterward. This is just the first steps
        auth.inMemoryAuthentication().withUser("admin").password("admin").roles(SecurityRoles.ADMIN.getName(), SecurityRoles.USER.getName()); // inMemoryAuthentication puts the user in the memory instead of DB. Next we indicate which user then we gives him some roles.
        auth.inMemoryAuthentication().withUser("user").password("1234").roles(SecurityRoles.USER.getName());

    }

    /**
     * In here we indicate what to do to authenticate users and define accessibilities to actions
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.formLogin();   // Authenticate by formular
        http.authorizeRequests().antMatchers("/operations").hasRole(SecurityRoles.USER.getName());
        http.authorizeRequests().antMatchers("/save").hasRole(SecurityRoles.ADMIN.getName());
        // TODO Continue the accessibilities..

    }

}
