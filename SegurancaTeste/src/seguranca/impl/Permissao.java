package seguranca.impl;

/**
 *
 * @author leonardo
 */
public class Permissao {

    private int id;
    private String descricao;
    private boolean necessariaAutorizacaoDoGerente;

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isNecessariaAutorizacaoDoGerente() {
        return necessariaAutorizacaoDoGerente;
    }

    public void setNecessariaAutorizacaoDoGerente(boolean necessariaAutorizacaoDoGerente) {
        this.necessariaAutorizacaoDoGerente = necessariaAutorizacaoDoGerente;
    }

    @Override
    public String toString() {
        return "Permissao{" + "id=" + id + ", descricao=" + descricao + '}';
    }
    
}
