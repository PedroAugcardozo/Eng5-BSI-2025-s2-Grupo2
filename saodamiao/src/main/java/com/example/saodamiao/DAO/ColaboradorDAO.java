package com.example.saodamiao.DAO;


import com.example.saodamiao.Model.Colaborador;
import com.example.saodamiao.Singleton.Conexao;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ColaboradorDAO {
    public Colaborador ResgatarColaborador(int id, Conexao conexao){
        String sql = "SELECT * FROM colaborador WHERE idcolaborador = '" + id +"'";
        Colaborador colaborador = null;
        try{
            ResultSet rs = conexao.consultar(sql);
            if(rs.next()){
                colaborador = new Colaborador();
                colaborador.setIdColaborador(rs.getInt("idcolaborador"));
                colaborador.setNome(rs.getString("nome"));
                colaborador.setCpf(rs.getString("cpf"));
                colaborador.setEmail(rs.getString("email"));
                colaborador.setMat(rs.getDate("dt_mat"));
                colaborador.setTelefone(rs.getString("telefone"));
                colaborador.setUf(rs.getString("uf"));
                colaborador.setCidade(rs.getString("cidade"));
                colaborador.setBairro(rs.getString("bairro"));
                colaborador.setRua(rs.getString("rua"));
                colaborador.setCep(rs.getString("cep"));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return colaborador;
    }
}
