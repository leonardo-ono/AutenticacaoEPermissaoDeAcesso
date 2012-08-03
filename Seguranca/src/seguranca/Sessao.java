package seguranca;

/**
 *
 * @author leonardo
 */
public abstract class Sessao {
    
    private static Sessao sessao;
    private static Class<? extends Sessao> sessaoClass;

    public static Class<? extends Sessao> getSessaoClass() {
        return sessaoClass;
    }

    public static void setSessaoClass(Class<? extends Sessao> sessaoClass) {
        Sessao.sessaoClass = sessaoClass;
    }
    
    public abstract boolean isIniciado();
    
    public abstract void logar(String login, String senha) throws Exception;
    public abstract void efetuarLogoff(String login, String senha) throws Exception;
    
    public abstract boolean verificarTemAcesso(String idDaPermissao);
    public abstract boolean verificarNecessitaDaPermissaoDoGerente(String idDaPermissao);
    public abstract boolean solicitarAutorizacaoGerente(String idDaPermissao) throws Exception;

    public synchronized static Sessao getInstance() {
        if (sessao == null) {
            try {
                sessao = sessaoClass.newInstance();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
        return sessao;
    }
    
}
