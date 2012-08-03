package seguranca.impl;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author leonardo
 */
public class Usuario {

    private int id;
    private String nome;
    private String login;
    private String senha;
    
    private List<Permissao> permissoes = new ArrayList<Permissao>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public List<Permissao> getPermissoes() {
        return permissoes;
    }

    @Override
    public String toString() {
        return "Usuario{" + "id=" + id + ", nome=" + nome 
                + ", login=" + login + ", senha=" + senha + '}';
    }
    
}
