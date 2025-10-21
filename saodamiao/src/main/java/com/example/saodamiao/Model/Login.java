package com.example.saodamiao.Model;

import lombok.Data;

@Data
public class Login {
    private int idColaborador;
    private String loginUserName;
    private String loginAtivo;
    private String loginSenha;

    public Login(int idColaborador, String loginUserName, String loginSenha, String loginAtivo) {
        this.idColaborador = idColaborador;
        this.loginUserName = loginUserName;
        this.loginSenha = loginSenha;
        this.loginAtivo = loginAtivo;
    }

    public int getIdColaborador() {
        return idColaborador;
    }

    public void setIdColaborador(int idColaborador) {
        this.idColaborador = idColaborador;
    }

    public String getLoginUserName() {
        return loginUserName;
    }

    public void setLoginUserName(String loginUserName) {
        this.loginUserName = loginUserName;
    }

    public String getLoginSenha() {
        return loginSenha;
    }

    public void setLoginSenha(String loginSenha) {
        this.loginSenha = loginSenha;
    }

    public String getLoginAtivo() {
        return loginAtivo;
    }

    public void setLoginAtivo(String loginAtivo) {
        this.loginAtivo = loginAtivo;
    }
}
