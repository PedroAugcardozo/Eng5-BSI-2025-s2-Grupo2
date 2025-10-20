package com.example.saodamiao.Control;

import com.example.saodamiao.Model.AlimentoEstoque;
import com.example.saodamiao.Model.ItensVenda;

public class EstoqueControl{

    //É necessario passar apenas o valor alterado atualizar, por exemplo: -3 Oleos ou 4 farinhas
    public Boolean AtualizarEstoqueAlimento(int idAlimento, int qtde){
        try{
            AlimentoEstoque alimento = new AlimentoEstoque();
            return alimento.atualizarEstoque();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //é necessario passar o valor total (buscar o valor no banco e fazer a conta de qtde no seu metodo), por Exemplo 45 Oleos ou 15 farinhas
    public Boolean AtualizarEstoqueItem(int idItem, int qtde){
        try{
            ItensVenda itens = new ItensVenda(idItem, qtde);
            return itens.AtualizarEstoque(idItem, qtde);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
