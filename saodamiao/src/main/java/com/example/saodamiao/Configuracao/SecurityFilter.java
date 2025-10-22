// Crie este arquivo no mesmo pacote da sua ConfiguracaoSeguranca
package com.example.saodamiao.Configuracao;

import com.example.saodamiao.Model.Login;
import com.example.saodamiao.Singleton.Singleton;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component // <-- Informa ao Spring que esta é uma classe gerenciada por ele
public class SecurityFilter extends OncePerRequestFilter {


    @Autowired
    private com.example.saodamiao.Configuracao.TokenControl tokenControl; // Seu serviço que valida o token

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 1. Pega o token do cabeçalho
        var token = this.recuperarToken(request);

        // 2. Se houver um token
        if (token != null) {
            try {
                // 3. Valida o token e pega o "subject" (que deve ser o login)
                var login = tokenControl.ValidarToken(token); // Você precisa ter esse método!

                // 4. Busca o usuário no banco de dados
                Login login1 = new Login();
                login1 = login1.buscarLogin(login1.getLoginUserName(), Singleton.Retorna()); // Seu repositório precisa ter esse método

                // 5. Se o usuário existir, informa ao Spring que ele está autenticado
                if (login1 != null) {
                    var authentication = new UsernamePasswordAuthenticationToken(
                            login1.getLoginSenha(), login1, null
                    );

                    // 6. Coloca o usuário no "Contexto de Segurança" do Spring
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (Exception e) {
                // Se o token for inválido, expirado, etc.
                // Apenas limpamos o contexto e deixamos o Spring bloquear (com 403)
                SecurityContextHolder.clearContext();
            }
        }

        // 7. Continua a cadeia de filtros (essencial!)
        filterChain.doFilter(request, response);
    }

    // Método auxiliar para extrair o token
    private String recuperarToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;
        }
        return authHeader.replace("Bearer ", "");
    }
}