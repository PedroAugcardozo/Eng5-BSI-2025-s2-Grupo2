package com.example.saodamiao.Control;


import com.example.saodamiao.Configuracao.TokenControl;
import com.example.saodamiao.DTO.AutenticacaoDTO;
import com.example.saodamiao.DTO.LoginResponseDTO;
import com.example.saodamiao.Model.Login;
import com.example.saodamiao.Singleton.Erro;
import com.example.saodamiao.Singleton.Singleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(name = "/login")
public class LoginControl {

    Login login;

    @Autowired
    private TokenControl tokenControl;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/entrar")
    public ResponseEntity<?> login(@RequestBody AutenticacaoDTO autenticacaoDTO) {
        try {
            // 3. Busca direto pelo repositório
            Login login = new Login();
            login = login.buscarLogin(autenticacaoDTO.getLogin(), Singleton.Retorna());

            if (login == null || VerificaAtivo(autenticacaoDTO.getLogin()) || !passwordEncoder.matches(autenticacaoDTO.getSenha(), login.getLoginSenha())) {
                return ResponseEntity.status(401).body(new Erro("Usuário ou senha inválidos"));
            }

            String token = tokenControl.gerarToken(login);
            return ResponseEntity.ok(new LoginResponseDTO(token));

        } catch (Exception e) {
            return ResponseEntity.status(500).body(new Erro("Falha interna ao processar a solicitação"));
        }
    }

    @PostMapping("/registrar")
    public ResponseEntity CriarLogin(@RequestBody Login novoUsuario){
        login = new Login();
        login = login.buscarLogin(novoUsuario.getLoginUserName(), Singleton.Retorna());
        if(login == novoUsuario){
            return ResponseEntity.status(401).body("Usuario ou senha invalidos");
        }
    }

    public Boolean VerificaAtivo(String userName){
        login = new Login();
        login = login.buscarLogin(userName, Singleton.Retorna());
        if(login.getLoginAtivo() == "S"){
            return true;
        }
        return false;
    }

    @PutMapping("/mudarParaInativo")
    public ResponseEntity MudarParaInativo(@RequestBody String userName){
        login = new Login();
        login = login.buscarLogin(userName, Singleton.Retorna());
        if(!VerificaAtivo(userName)){
            return ResponseEntity.status(400).body("Nao e possivel desativar um login inativo");
        }
        login.setLoginAtivo("N");
        if(login.MudarAtividade(login, Singleton.Retorna())){
            return ResponseEntity.ok("Atualizado com sucesso");
        }
        return ResponseEntity.status(500).body("Operacao não realizada");
    }

    @PutMapping("/mudarParaAtivo")
    public ResponseEntity MudarParaAtivo(@RequestBody String userName){
        login = new Login();
        login = login.buscarLogin(userName, Singleton.Retorna());
        if(VerificaAtivo(userName)){
            return ResponseEntity.status(400).body("Nao e possivel ativar um login ativo");
        }
        login.setLoginAtivo("S");
        if(login.MudarAtividade(login, Singleton.Retorna())){
            return ResponseEntity.ok("Atualizado com sucesso");
        }
        return  ResponseEntity.status(500).body("Operacao não realizada");
    }
}
