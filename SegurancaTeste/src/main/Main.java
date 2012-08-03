package main;

import calculadora.CalculadoraImpl;
import calculadora.CalculadoraInterface;
import java.util.ArrayList;
import java.util.List;
import seguranca.SecureBeanFactory;
import seguranca.Sessao;
import seguranca.Verificacao;
import seguranca.impl.VerificacaoAdicionalSolicitadorSenhaDoProprioUsuarioParaSomar;

/**
 *
 * @author leonardo
 */
public class Main {

    public static void main(String[] args) {
        
        // Carrega a implementacao da sessao
        try {
            Class.forName("seguranca.impl.SessaoImpl");
        } catch (ClassNotFoundException ex) {
            System.err.println(ex.getMessage());
            System.exit(-1);
        }
        
        // Obtem o objeto de sessao do usuario
        Sessao sessao = Sessao.getInstance();
        
        // Cria verificacao adicional
        // Neste exemplo, somente para o metodo somar, 
        // pede a senha do proprio usuario antes
        List<Verificacao> verificacoesAdicionais = new ArrayList<Verificacao>();
        verificacoesAdicionais.add(new VerificacaoAdicionalSolicitadorSenhaDoProprioUsuarioParaSomar());
        
        // Cria uma calculadora proxy com interceptadores que verifica
        // a permissao de cada metodo anotado com @NecessitaDePermissao
        CalculadoraImpl calculadoraConcreta = new CalculadoraImpl();
        CalculadoraInterface calculadora = SecureBeanFactory
            .createSecureBean(CalculadoraInterface.class
                , calculadoraConcreta, verificacoesAdicionais);

        // Faz a autenticacao
        try {
            sessao.logar("leo", "leo");
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            System.exit(-1);
        }

        // invoca o metodo somar
        try {
            System.out.println("soma=" + calculadora.somar(1, 2));
        }
        catch (Exception ex) {
            System.err.println("somar: " + ex.getCause());
            System.exit(-1);
        }
        
        // invoca o metodo subtrair
        try {
            System.out.println("subtrair=" + calculadora.subtrair(5, 2));
        }
        catch (Exception ex) {
            System.err.println("subtrair: " + ex.getCause());
            System.exit(-1);
        }
    }
    
}
