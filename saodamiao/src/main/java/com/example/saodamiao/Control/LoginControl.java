package com.example.saodamiao.Control;


import com.example.saodamiao.Configuracao.TokenControl;
import com.example.saodamiao.DTO.AutenticacaoDTO;
import com.example.saodamiao.DTO.LoginResponseDTO;
import com.example.saodamiao.Model.Login;
import com.example.saodamiao.Singleton.Erro;
import com.example.saodamiao.Singleton.Singleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


/*
* SQL PARA RODAR AS COISA ADICIONADAS
* --=====================================================
-- 3. INSERT DO GESTOR
-- =====================================================
INSERT INTO gestor (idgestor, colaborador_idcolaborador)
VALUES (1, 1);

-- =====================================================
-- 4. INSERT DAS PERMISSÕES
-- =====================================================
INSERT INTO permissao (idpermissao, tipo_permissao, ativo) VALUES
(1, 'ROLE_ADMIN', 'S'),
(2, 'ROLE_GESTOR', 'S'),
(3, 'ROLE_COLABORADOR', 'S'),
(4, 'VENDA_BAZAR', 'S'),
(5, 'GERENCIAR_CESTAS', 'S'),
(6, 'GERENCIAR_ESTOQUE', 'S');

-- =====================================================
-- 5. INSERT DA PERMISSÃO DO USUÁRIO
-- =====================================================
INSERT INTO permissao_usuario
(colaborador_idcolaborador, gestor_idgestor, gestor_colaborador_idcolaborador, permissao_idpermissao, data_inicio, data_fim)
VALUES
(1, 1, 1, 1, CURRENT_DATE, NULL);

-- =====================================================
-- 6. VERIFICAÇÃO (OPCIONAL)
-- =====================================================
SELECT * FROM colaborador;
SELECT * FROM login;
SELECT * FROM gestor;
SELECT * FROM permissao;
SELECT * FROM permissao_usuario;
* */

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
            Login login = new Login();
            login = login.buscarLogin(autenticacaoDTO.getLogin(), Singleton.Retorna());

            if (login == null || VerificaAtivo(autenticacaoDTO.getLogin()) || !autenticacaoDTO.getSenha().equals(login.getLoginSenha())) {
                return ResponseEntity.status(401).body(new Erro("Usuário ou senha inválidos"));
            }

            String token = tokenControl.gerarToken(login);
            return ResponseEntity.ok(new LoginResponseDTO(token));

        } catch (Exception e) {
            return ResponseEntity.status(500).body(new Erro("Falha interna ao processar a solicitação"));
        }
    }

    public Boolean criarLogin(String userName, String senha, int id){
        login = new Login();
        return login.criarLogin(userName, id, senha, "S", Singleton.Retorna());
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
        if(!VerificaAtivo(userName) && login.todosLogins(Singleton.Retorna()).size() < 2){
            return ResponseEntity.status(400).body("Nao e possivel desativar o login");
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
