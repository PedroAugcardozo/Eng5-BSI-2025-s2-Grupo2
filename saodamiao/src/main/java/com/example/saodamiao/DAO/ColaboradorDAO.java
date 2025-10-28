package com.example.saodamiao.DAO;


import com.example.saodamiao.DTO.ColaboradorDTO;
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

    public Boolean CriarColaborador(ColaboradorDTO colaborador, Conexao conexao){
        String sql = "INSERT INTO (idColaborador, nome, cpf, email, dt_mat, telefone, uf, cidade, bairro, rua, cep) VALUES(#1, '#2', '#3', '#4', '#5', '#6', '#7', '#8', '#9', '#10', '#11'" +
                ")";
        sql = sql.replace("#1", String.valueOf(colaborador.getIdColaborador()));
        sql = sql.replace("#2", colaborador.getNome());
        sql = sql.replace("#3", colaborador.getCpf());
        sql = sql.replace("#4", colaborador.getEmail());
        sql = sql.replace("#5", String.valueOf(colaborador.getMat()));
        sql = sql.replace("#6", colaborador.getTelefone());
        sql = sql.replace("#7", colaborador.getUf());
        sql = sql.replace("#8", colaborador.getCidade());
        sql = sql.replace("#9", colaborador.getBairro());
        sql = sql.replace("#10", colaborador.getRua());
        sql = sql.replace("#11", colaborador.getCep());
        return conexao.manipular(sql);

    }
}
