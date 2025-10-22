package com.example.saodamiao.Control;

import com.example.saodamiao.Model.Colaborador;
import com.example.saodamiao.Singleton.Singleton;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ColaboradorControl {

    public Colaborador BuscarColaborador(int idColaborador){
        Colaborador colaborador = new Colaborador();
        return colaborador.BuscarColaborador(idColaborador, Singleton.Retorna());
    }
}
