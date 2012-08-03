package seguranca;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * SecureBeanFactory.
 * 
 * @author leonardo
 */
public class SecureBeanFactory {
    
    private static final Logger logger 
            = Logger.getLogger(SecureBeanFactory.class.getName());

    static {
        logger.setLevel(Level.OFF);
    }
    public static <T> T createSecureBean(Class<T> classeInterface
            , Object classeConcreta, List<Verificacao> verificacoesAdicionais) {
        
        InvocationHandler handler = new SecureBeanInvocationHandler(
                classeConcreta, verificacoesAdicionais);
        
        Class[] interfaces = new Class[]{classeInterface};
        T proxy = (T) Proxy.newProxyInstance(classeConcreta.getClass()
                .getClassLoader(), interfaces, handler);
        return proxy;
    }
    
    public static <T> T createSecureBean(Class<T> classeInterface
            , Object classeConcreta) {
        
        return createSecureBean(classeInterface, classeConcreta, null);
    }

    private static class SecureBeanInvocationHandler<T> 
    implements InvocationHandler {

        private T concreteBean;
        private List<Verificacao> verificacoesAdicionais;
        
        public SecureBeanInvocationHandler(T concreteBean) {
            this.concreteBean = concreteBean;
        }

        public SecureBeanInvocationHandler(T concreteBean, List<Verificacao> verificacoesAdicionais) {
            this.concreteBean = concreteBean;
            this.verificacoesAdicionais = verificacoesAdicionais;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) 
                throws Throwable {

            // obtem o metodo da classe concreta
            Object retorno = null;
            Method methodConcreto = concreteBean.getClass()
                    .getMethod(method.getName(), method.getParameterTypes());

            // verifica se o metodo necessida de permissao
            for (Annotation a : methodConcreto.getAnnotations()) {
                // System.out.println("annotation: " + a);
                if (a instanceof NecessitaDePermissao) {
                    NecessitaDePermissao np = (NecessitaDePermissao) a;
                    verificarPermissao(np.value());
                }
            }

            // retorno
            retorno = methodConcreto.invoke(concreteBean, args);
            return retorno;
        }

        private void verificarPermissao(String nome) throws Exception {
            logger.log(Level.INFO, "Verificando permissao para {0} ... ", nome);
            Sessao sessao = Sessao.getInstance();
            if (sessao==null || !sessao.isIniciado()) {
                throw new AcessoNaoPermitidoException("Sessao nao iniciado !");
            }

            if (!sessao.verificarTemAcesso(nome)) {
                logger.log(Level.INFO
                    , "Permissao para {0} ACESSO NAO AUTORIZADO ... ", nome);
                throw new AcessoNaoPermitidoException(
                    "Acesso nao permitido !");
            }
            logger.log(Level.INFO, "Permissao para {0} OK ... ", nome);

            logger.log(Level.INFO
                    , "Realizando verificacoes adicionais ... ", nome);
            if (verificacoesAdicionais != null) {
                for (Verificacao v : verificacoesAdicionais) {
                    if (v.isNecessario(nome)) {
                        
                        if (!v.verificar(nome)) {
                            throw new AcessoNaoPermitidoException(
                                    v.getMensagemDeAcessoNaoPermitido());
                        }
                        
                    }
                }
            }

            if (sessao.verificarNecessitaDaPermissaoDoGerente(nome)) {
                logger.log(Level.INFO
                        , "Solicitando autorizacao do gerente ... ", nome);
                if (sessao.solicitarAutorizacaoGerente(nome)) {
                    return; // OK
                }
                else {
                    logger.log(Level.INFO
                            , "Autorizacao do gerente invalido ... ", nome);
                    throw new AcessoNaoPermitidoException(
                            "Autorizacao do gerente invalido !");
                }
            }
            
        }
        
    }
    
}
