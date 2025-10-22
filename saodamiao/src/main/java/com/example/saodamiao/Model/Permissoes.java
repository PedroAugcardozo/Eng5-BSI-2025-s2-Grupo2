package com.example.saodamiao.Model;

import com.example.saodamiao.DAO.PermissoesDAO;
import com.example.saodamiao.Singleton.Conexao;
import lombok.Data;

import java.sql.Date;
import java.util.List;

@Data
public class Permissoes {
    private int idColaborador;
    private int IdGestor;
    private int idGestorColaborador;
    private int idPermissao;
    private Date dataInicio;
    private Date dataFim;

    PermissoesDAO permissoesDAO;
    public Permissoes(){}

    public Permissoes(int idColaborador,int idGestorColaborador, int idGestor, int idPermissao, Date dataInicio, Date dataFim) {
        this.idColaborador = idColaborador;
        IdGestor = idGestor;
        this.idPermissao = idPermissao;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.idGestorColaborador = idGestorColaborador;
    }
    public boolean CriarNoBanco(Permissoes entidade, Conexao conexao){
        permissoesDAO = new PermissoesDAO();
        return permissoesDAO.CriarPermissao(entidade, conexao);
    }
    public Permissoes BuscarUm(int idColaborador, Conexao conexao){
        permissoesDAO = new PermissoesDAO();
        return permissoesDAO.BuscarUm(idColaborador, conexao);
    }
    public List<Permissoes> BuscarTodos (Conexao conexao){
        permissoesDAO = new PermissoesDAO();
        return permissoesDAO.BuscarTodos(conexao);
    }
    public Boolean AtualizarPermissao(Permissoes permissao, Conexao conexao){
        permissoesDAO = new PermissoesDAO();
        return permissoesDAO.AtualizarPermissao(permissao, conexao);
    }
    public Boolean DeletarPermissao(int idColaborador, Conexao conexao){
        permissoesDAO = new PermissoesDAO();
        return permissoesDAO.DeletarPermissao(idColaborador, conexao);
    }
}
