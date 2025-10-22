package com.example.saodamiao.DAO;

import com.example.saodamiao.Model.Colaborador;
import com.example.saodamiao.Model.Login;
import com.example.saodamiao.Singleton.Conexao;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDAO {
    public Login ResgatarLogin(String login, Conexao conexao){
        String sql = "SELECT * FROM login WHERE log_username = '#2'";
        Login login1 = null;
        sql = sql.replace("#2", login.toLowerCase());
        try{
            ResultSet rs = conexao.consultar(sql);
            if(rs.next()){
                login1 = new Login();
                login1.setLoginUserName(rs.getString("log_username"));
                login1.setLoginAtivo(rs.getString("log_ativo"));
                login1.setLoginSenha(rs.getString("log_senha"));
                login1.setIdColaborador(rs.getInt("colaborador_idcolaborador"));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return login1;
    }
}
