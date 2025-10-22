package com.example.saodamiao.Control;


import com.example.saodamiao.DTO.AutenticacaoDTO;
import com.example.saodamiao.Model.Login;
import com.example.saodamiao.Singleton.Erro;
import com.example.saodamiao.Singleton.Singleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(name = "/login")
public class LoginControl {
    @PostMapping("/entrar")
    public ResponseEntity login (@RequestBody AutenticacaoDTO autenticacaoDTO){
        try{
            Login loginModel = new Login();
            loginModel = loginModel.buscarLogin(autenticacaoDTO.getLogin(), Singleton.Retorna());

            if(loginModel == null){
                return ResponseEntity.status(401).body(new Erro("Usuario nao encontrado"));
            }

            if(!loginModel.getLoginSenha().equals(autenticacaoDTO.getSenha())){
                return ResponseEntity.status(401).body(new Erro("Senha e/ou UsernName incorretos"));
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.status(500).body("falha ao realizar login");
    }
}
