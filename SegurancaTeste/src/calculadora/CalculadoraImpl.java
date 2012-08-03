package calculadora;

import seguranca.NecessitaDePermissao;

/**
 * CalculadoraImpl.
 * @author leonardo
 */
public class CalculadoraImpl implements CalculadoraInterface {

    @Override
    @NecessitaDePermissao("somar")
    public int somar(int a, int b) {
        return a+b;
    }

    @Override
    @NecessitaDePermissao("subtrair")
    public int subtrair(int a, int b) {
        return a-b;
    }
    
}
