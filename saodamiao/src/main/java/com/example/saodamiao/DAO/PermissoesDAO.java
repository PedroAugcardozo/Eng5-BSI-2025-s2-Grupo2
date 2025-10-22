package com.example.saodamiao.DAO;

import com.example.saodamiao.Model.Permissoes;
import com.example.saodamiao.Singleton.Conexao;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PermissoesDAO {

    public Boolean CriarPermissao(Permissoes entidade, Conexao conexao){
        String sql = "INSERT INTO permissao_usuario (colaborador_idcolaborador, gestor_colaborador_idcolaborador, gestor_idgestor, permissao_idpermissao, data_inicio, data_fim) values ('#2',#3,#4,#5,%6";
        sql = sql.replace("#1", String.valueOf(entidade.getIdColaborador()));
        sql = sql.replace("#2", String.valueOf(entidade.getIdGestorColaborador()));
        sql = sql.replace("#3", String.valueOf(entidade.getIdGestor()));
        sql = sql.replace("#4", String.valueOf(entidade.getIdPermissao()));
        sql = sql.replace("#5", String.valueOf((entidade.getDataInicio())));
        sql = sql.replace("#6", String.valueOf(entidade.getDataFim()));

        return conexao.manipular(sql);
    }

    public  Permissoes BuscarUm(int idColaborador, Conexao conexao){
        String sql = "SELECT * FROM permissao_usuario WHERE colaborador_idcolaborador " + idColaborador;
        Permissoes permissao = null;
        try{
            ResultSet rs = conexao.consultar(sql);
            if(rs.next()){
                permissao = new Permissoes();
                permissao.setIdColaborador(idColaborador);
                permissao.setIdGestor(rs.getInt("gestor_idgestor"));
                permissao.setIdGestorColaborador(rs.getInt("gestor_colaborador_idcolaborador"));
                permissao.setIdPermissao(rs.getInt("permissao_idpermissao"));
                permissao.setDataInicio(rs.getDate("data_inicio"));
                permissao.setDataFim(rs.getDate("data_fim"));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return permissao;
    }

    public List<Permissoes> BuscarTodos(Conexao conexao){
        String sql = "SELECT * FROM permissao_usuario";
        List<Permissoes> permissoes = new ArrayList<>();
        try{
            ResultSet rs = conexao.consultar(sql);
            while(rs.next()){
                Permissoes permissao = new Permissoes();
                permissao.setIdColaborador(rs.getInt("colaborador_idcolaborador"));
                permissao.setIdGestor(rs.getInt("gestor_idgestor"));
                permissao.setIdGestorColaborador(rs.getInt("gestor_colaborador_idcolaborador"));
                permissao.setIdPermissao(rs.getInt("permissao_idpermissao"));
                permissao.setDataInicio(rs.getDate("data_inicio"));
                permissao.setDataFim(rs.getDate("data_fim"));

                permissoes.add(permissao);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return permissoes;
    }

    public Boolean AtualizarPermissao(Permissoes permissao, Conexao conexao){
        String sql = "UPDATE permissao_usuario SET " +
                "gestor_idgestor = " + permissao.getIdGestor() + ", " +
                "gestor_colaborador_idcolaborador = " + permissao.getIdGestorColaborador() + ", " +
                "permissao_idpermissao = " + permissao.getIdPermissao() + ", " +
                "data_inicio = '" + permissao.getDataInicio() + "', " +
                "data_fim = " + (permissao.getDataFim() != null ? "'" + permissao.getDataFim() + "'" : "NULL") +
                " WHERE colaborador_idcolaborador = " + permissao.getIdColaborador();
        return conexao.manipular(sql);
    }

    public Boolean DeletarPermissao(int idColaborador, Conexao conexao){
        String sql = "DELETE FROM permissao_usuario WHERE colaborador_idcolaborador = "+ idColaborador;
        return conexao.manipular(sql);
    }
}
