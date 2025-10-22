package com.example.saodamiao.Control;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.example.saodamiao.Model.Colaborador;
import com.example.saodamiao.Model.Login;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@RestController
public class TokenControl {

    @Value("${api.seguranca.token.segredo}")
    private String segredo;

    public String gerarToken(Login login){
        try{
            Algorithm algorithm = Algorithm.HMAC256(segredo);
            String token = JWT.create()
                    .withIssuer("saodamiao")
                    .withSubject(login.getLoginUserName())
                    .withExpiresAt(gerarDataExpiracao())
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException e) {
            throw new RuntimeException("Erro ao gerar token",e);
        }
    }

    private Instant gerarDataExpiracao(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
