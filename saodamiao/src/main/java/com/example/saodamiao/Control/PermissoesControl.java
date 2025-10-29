package com.example.saodamiao.Control;

import com.example.saodamiao.DTO.AtribuirPermissaoDTO;
import com.example.saodamiao.Model.Colaborador;
import com.example.saodamiao.Model.Login;
import com.example.saodamiao.Model.Permissoes;
import com.example.saodamiao.Singleton.Erro;
import com.example.saodamiao.Singleton.Singleton;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/permissoes")
public class PermissoesControl {
    Permissoes permissoes;

    @PostMapping("/inserirpermissao")
    public ResponseEntity InserirPermissao(@RequestBody AtribuirPermissaoDTO dto){
        //aqui eu procuro o id do usuario logado
        var authenticacao = SecurityContextHolder.getContext().getAuthentication();
        Login usuarioLogado = (Login) authenticacao.getPrincipal();
        int idGestorLogado = usuarioLogado.getIdColaborador();

        Colaborador colaborador = new Colaborador();
        colaborador = colaborador.BuscarPorCpf(dto.getCpfColaborador(), Singleton.Retorna());
        if(colaborador == null){
            return ResponseEntity.badRequest().body("colaborador nao encontrado");
        }

        Permissoes permissoes = new Permissoes();
        permissoes = permissoes.BuscarPermissaoPorNome(dto.getNomePermissao(), Singleton.Retorna());
        if(permissoes == null){
            return ResponseEntity.badRequest().body("Permissao nao encontrada");
        }

        if(permissoes.InserirPermissaoUsuario(colaborador.getIdColaborador(), permissoes.getIdPermissao(), idGestorLogado, Singleton.Retorna())){
            return ResponseEntity.ok().body("Permissao Inserida no Colaborador");
        }
        return ResponseEntity.badRequest().body("Erro ao inserir Permissao");
    }

    @PostMapping
    public ResponseEntity DeletarPermissao(@RequestBody AtribuirPermissaoDTO dto){
        Colaborador colaborador = new Colaborador();
        colaborador = colaborador.BuscarPorCpf(dto.getCpfColaborador(), Singleton.Retorna());
        if(colaborador == null){
            return ResponseEntity.badRequest().body("colaborador nao encontrado");
        }

        Permissoes permissoes = new Permissoes();
        permissoes = permissoes.BuscarPermissaoPorNome(dto.getNomePermissao(), Singleton.Retorna());
        if(permissoes == null){
            return ResponseEntity.badRequest().body("Permissao nao encontrada");
        }

        if(permissoes.DeletarPermissaoUsuario(colaborador.getIdColaborador(), permissoes.getIdPermissao(), Singleton.Retorna())){
            return ResponseEntity.ok().body("Permissao Deletada");
        }
        return ResponseEntity.badRequest().body("erro ao deletar permissao");
    }
}
