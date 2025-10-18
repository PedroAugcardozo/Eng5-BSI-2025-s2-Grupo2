package com.example.saodamiao.Control;

import com.example.saodamiao.DTO.ParametrizacaoDTO;
import com.example.saodamiao.DTO.ParametrizacaoFormDTO;
import com.example.saodamiao.Model.Parametrizacao;
import com.example.saodamiao.Singleton.Erro;
import com.example.saodamiao.Singleton.Singleton;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/Parametrizacao")
@CrossOrigin(origins = "*")
public class ParametrizacaoControl {

    @GetMapping(value = "/home")
    ResponseEntity<Object> buscarParametrizacao(){

        Parametrizacao parametrizacao = new Parametrizacao();
        parametrizacao = parametrizacao.getParametrizacaoDAO().pegarParametro(Singleton.Retorna());
        if(Singleton.Retorna().getEstadoConexao())
        {
            if(parametrizacao != null)
            {   ParametrizacaoDTO  parametrizacaoDTO = new ParametrizacaoDTO(parametrizacao);
                Singleton.Retorna().CloseConnection();
                return ResponseEntity.ok(parametrizacaoDTO);
            }
            else
            {
                Singleton.Retorna().CloseConnection();
                return ResponseEntity.badRequest().body(new Erro("nenhuma empresa cadastrada"));
            }

        }
        return ResponseEntity.status(500).body(new Erro(Singleton.Retorna().getMensagemErro()));
    }

    @PostMapping(value = "/inserir")
    ResponseEntity<Object> InserirParametrizacao(@RequestBody ParametrizacaoFormDTO parametrizacaoFormDTO){

        Parametrizacao parametrizacao1 = parametrizacaoFormDTO.toParametrizacao();
        if(!Singleton.Retorna().StartTransaction())
        {
            return ResponseEntity.status(500).body(new Erro(Singleton.Retorna().getMensagemErro()));
        }
        if(!parametrizacao1.getParametrizacaoDAO().gravar(parametrizacao1,Singleton.Retorna()))
        {
            Singleton.Retorna().Rollback(); // apenas para finalizar a transação
            Singleton.Retorna().CloseConnection();
            return ResponseEntity.badRequest().body(new Erro("Problema ao gravar no banco de dados"));
        }
        Singleton.Retorna().Commit();
        Singleton.Retorna().CloseConnection();
        return ResponseEntity.ok(parametrizacaoFormDTO);
    }
}







/*if(!parametro.getParametrizacaoDAO().gravar(parametro,Singleton.Retorna()))
        {
        Singleton.Retorna().Rollback(); // apenas para finalizar a transação
                Singleton.Retorna().CloseConnection();
                return ResponseEntity.badRequest().body(new Erro("Problema ao gravar no banco de dados"));
        }
        Singleton.Retorna().Commit();
            Singleton.Retorna().CloseConnection();
            return ResponseEntity.ok(parametro);
public ResponseEntity<Object> InsereAlimento(@RequestBody AlimentoDTO alimentoDTO) {
    Alimento alimento = alimentoDTO.toAlimento();

    if(!Singleton.Retorna().StartTransaction())
        return ResponseEntity.status(500).body(new Erro(Singleton.Retorna().getMensagemErro()));

    if(!alimento.getAlimentoDAO().gravar(alimento, Singleton.Retorna())) {
        Singleton.Retorna().Rollback(); // apenas para finalizar a transação
        Singleton.Retorna().CloseConnection();
        return ResponseEntity.badRequest().body(new Erro("Problema ao gravar no banco de dados"));
    }
    Singleton.Retorna().Commit();
    Singleton.Retorna().CloseConnection();
    return ResponseEntity.ok(alimentoDTO);*/
