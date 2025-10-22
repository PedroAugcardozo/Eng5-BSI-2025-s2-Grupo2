package com.example.saodamiao.Control;

import com.example.saodamiao.Model.Permissoes;
import com.example.saodamiao.Singleton.Erro;
import com.example.saodamiao.Singleton.Singleton;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/permissoes")
public class PermissoesControl {

    @PostMapping("/criar")
    public ResponseEntity CriarPermissao(@RequestBody Permissoes permissao){
        try{
            Permissoes permissoes = new Permissoes();
            if(Singleton.Retorna().StartTransaction()){
                return ResponseEntity.status(500).body(new Erro("falha ao iniciar o banco"));
            }
            if(permissoes.CriarNoBanco(permissao, Singleton.Retorna())){
                return ResponseEntity.status(200).body("Permissao criada com sucesso");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.status(500).body(new Erro("falha ao criar permissao"));
    }

    @GetMapping("/buscarUm")
    public ResponseEntity BuscarPermissao(@RequestBody Permissoes permissoes){
        try{
            Permissoes permissao = new Permissoes();
            if(Singleton.Retorna().StartTransaction()){
                return ResponseEntity.status(500).body("Erro ao abrir o banco de dados");
            }
            return ResponseEntity.ok(permissao);
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/buscarTodas")
    public ResponseEntity BuscarTodasAsPermissoes(){
        try{
            Permissoes permissoes = new Permissoes();
            if(Singleton.Retorna().StartTransaction()){
                return ResponseEntity.status(500).body("Erro ao abrir o banco de dados");
            }
            return ResponseEntity.ok(permissoes.BuscarTodos(Singleton.Retorna()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/atualizar")
    public ResponseEntity AtualizarPermissao(@RequestBody Permissoes novaPermissao){
        try{
            Permissoes permissoes = new Permissoes();
            if(Singleton.Retorna().StartTransaction()){
                return ResponseEntity.status(500).body("Erro ao abrir o banco de dados");
            }
            if(permissoes.AtualizarPermissao(novaPermissao, Singleton.Retorna())){
                return ResponseEntity.ok("Atualizado com sucesso");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @DeleteMapping("/deletar")
    public ResponseEntity DeletarPermissao(){
        try{
            Permissoes permissoes = new Permissoes();
            if(Singleton.Retorna().StartTransaction()){
                return ResponseEntity.status(500).body("Erro ao abrir o banco de dados");
            }
            if(permissoes.DeletarPermissao(permissoes.getIdColaborador(), Singleton.Retorna())){
                return ResponseEntity.ok("Permissao Deletada com sucesso");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.status(500).body("erro ao deletar a permissao ");
    }
}
