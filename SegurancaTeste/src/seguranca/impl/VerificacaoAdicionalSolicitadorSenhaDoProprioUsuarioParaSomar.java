package seguranca.impl;

import javax.swing.JOptionPane;
import seguranca.Sessao;
import seguranca.Verificacao;

/**
 * Verificacao adicional apenas para o metodo somar.
 * 
 * @author leonardo
 */
public class VerificacaoAdicionalSolicitadorSenhaDoProprioUsuarioParaSomar implements Verificacao {
    
        private SessaoImpl sessao = (SessaoImpl) Sessao.getInstance();
        
        @Override
        public boolean isNecessario(String id) {
            return id.equals("somar");
        }

        @Override
        public boolean verificar(String id) {
            String senha = JOptionPane.showInputDialog("Entre com a senha do usuario (senha=\"leo\"):", "");
            return (senha.equals(sessao.getUsuario().getSenha()));
        }

        @Override
        public String getMensagemDeAcessoNaoPermitido() {
            return "Acesso negado para verificacao adicional !";
        }
        
}
