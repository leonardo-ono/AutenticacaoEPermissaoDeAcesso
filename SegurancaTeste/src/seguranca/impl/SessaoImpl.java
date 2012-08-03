package seguranca.impl;

import javax.swing.JOptionPane;
import seguranca.*;

/**
 *
 * @author leonardo
 */
public class SessaoImpl extends Sessao {

    private static SessaoImpl sessao;
    private static Usuario usuario;

    static {
        Sessao.setSessaoClass(SessaoImpl.class);
    }
    
    public Usuario getUsuario() {
        return usuario;
    }
    
    @Override
    public void logar(String login, String senha) throws Exception {
        if (login.equals("leo") && senha.equals("leo")) {
            criarUsuarioFicticio();
            return;
        }
        throw new AcessoNaoPermitidoException("Autenticacao invalida !");
    }
    
    private void criarUsuarioFicticio() {
        usuario = new Usuario();
        usuario.setId(1);
        usuario.setNome("leo");
        usuario.setLogin("leo");
        usuario.setSenha("leo");
        
        Permissao permissaoSomar = new Permissao();
        permissaoSomar.setId(1);
        permissaoSomar.setDescricao("somar");
        permissaoSomar.setNecessariaAutorizacaoDoGerente(false);

        Permissao permissaoSubtrair = new Permissao();
        permissaoSubtrair.setId(2);
        permissaoSubtrair.setDescricao("subtrair");
        permissaoSubtrair.setNecessariaAutorizacaoDoGerente(true);
        
        usuario.getPermissoes().add(permissaoSomar);
        usuario.getPermissoes().add(permissaoSubtrair);
    }
    
    @Override
    public boolean verificarTemAcesso(String id) {
        for (Permissao p : usuario.getPermissoes()) {
            if (p.getDescricao().equals(id)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isIniciado() {
        return (usuario != null);
    }

    @Override
    public void efetuarLogoff(String login, String senha) throws Exception {
        usuario = null;
    }

    @Override
    public boolean verificarNecessitaDaPermissaoDoGerente(String idDaPermissao) {
        for (Permissao p : usuario.getPermissoes()) {
            if (p.getDescricao().equals(idDaPermissao)) {
                return p.isNecessariaAutorizacaoDoGerente();
            }
        }
        return true;
    }

    @Override
    public boolean solicitarAutorizacaoGerente(String idDaPermissao) throws Exception {
        String senha = JOptionPane.showInputDialog("Senha do gerente para acessar [" + idDaPermissao + "] (senha=\"1234\"):", "");
        return (senha.equals("1234"));
    }
    
}
