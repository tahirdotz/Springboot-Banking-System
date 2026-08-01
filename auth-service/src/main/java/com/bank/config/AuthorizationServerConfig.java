package com.bank.config;

@Configuration
public class AuthorizationServerConfig {
    @Bean
    SecurityFilterChain authServerSecurityFilterChain(HttpSecurity http) throws Exception {
        Oauth2AuthorizationServerConfiguration.applyDefaultSecurity(http);


        return http.csrf(csrf -> csrf.disable()).build();
    }
}
