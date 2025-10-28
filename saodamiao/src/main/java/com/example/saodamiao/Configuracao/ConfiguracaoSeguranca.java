package com.example.saodamiao.Configuracao;

// IMPORTS NECESSÁRIOS
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod; // <-- Importe HttpMethod
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer; // <-- Importe Customizer
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter; // <-- Importe este
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class ConfiguracaoSeguranca {
    @Autowired
    private SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .cors(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        //ROTAS PÚBLICAS
                        //Permite acesso total a estas rotas específicas
                        .requestMatchers(HttpMethod.POST, "/entrar").permitAll()

                        //ROTAS DE "AÇÃO" (exigem permissão específica)
                        //Exige que o "crachá" do usuário contenha a string exata "VENDA_BAZAR"
                        .requestMatchers("/vendas/**").hasAuthority(PermissaoConstantes.VENDA_BAZAR)

                        //ROTAS DE "PAPEL" (Roles)
                        //Exige que o "crachá" contenha "ROLE_ADMIN"
                        .requestMatchers("/colaboradores/**").hasAuthority(PermissaoConstantes.ROLE_ADMIN)
                        .requestMatchers("/permissoes/**").hasAuthority(PermissaoConstantes.ROLE_ADMIN)

                        //ROTAS DE "PAPEL" MÚLTIPLO
                        //Exige "ROLE_ADMIN" OU "ROLE_GESTOR"
                        .requestMatchers("/relatorios/**").hasAnyAuthority(PermissaoConstantes.ROLE_ADMIN, PermissaoConstantes.ROLE_GESTOR)

                        //REGRA FINAL (Pega-Tudo)
                        //Qualquer outra requisição não listada acima (ex: GET /alimentos)
                        //deve estar, no mínimo, autenticada.
                        .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){return new BCryptPasswordEncoder();}

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // Em produção, NUNCA use "addAllowedOrigin("*")"
        // Especifiquem o domínio do front-end: ex: "http://meu-site.com"
        configuration.addAllowedOrigin("*");
        configuration.addAllowedMethod("*"); // Permite POST, GET, PUT, DELETE, etc.
        configuration.addAllowedHeader("*"); // Permite cabeçalhos como Authorization, Content-Type

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Aplica para todas as rotas
        return source;
    }
}