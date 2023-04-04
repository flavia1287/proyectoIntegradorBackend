package com.dh.clinica.security;

import com.dh.clinica.controller.filter.AuthTokenFilter;
import com.dh.clinica.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UsuarioService usuarioService;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/turnos").hasAnyRole("USER", "ADMIN")
                .antMatchers("/odontologos").hasRole("ADMIN")
                .antMatchers("/pacientes").hasRole("ADMIN");

        //NO TOCAR: código de Stack Overflow para eliminar error de CORS
        http.cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configurationSource(request -> {
            CorsConfiguration config = new CorsConfiguration();
            config.addAllowedOrigin("http://127.0.0.1:5500");
            config.addAllowedMethod("POST");
            config.addAllowedMethod("GET");
            config.addAllowedMethod("PUT");
            config.addAllowedMethod("DELETE");
            config.addAllowedHeader("Content-Type");
            config.addAllowedHeader(HttpHeaders.SET_COOKIE);
            config.addAllowedHeader(HttpHeaders.AUTHORIZATION);
            config.addAllowedHeader("Origin");
            return config;
        }));
        http.csrf().disable();
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(usuarioService);
        return provider;
    }
}
