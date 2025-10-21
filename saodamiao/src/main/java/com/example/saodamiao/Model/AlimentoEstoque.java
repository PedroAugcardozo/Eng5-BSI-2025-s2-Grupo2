package com.example.saodamiao.Model;

import com.example.saodamiao.DAO.AlimentoEstoqueDAO;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AlimentoEstoque {
    private long id_alimento;
    private LocalDate validade;
    private int quantidade;

    public Boolean atualizarEstoque(int id_alimento,int quantidade){
        try{
            AlimentoEstoqueDAO alimento = new AlimentoEstoqueDAO();
            return alimento.AtualizaQtde(id_alimento, quantidade);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
