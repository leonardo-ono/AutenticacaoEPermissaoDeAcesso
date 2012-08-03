package seguranca;

/**
 * Permite adicionar verificacoes adicionais 
 * no inicio da chamada de cada metodo.
 * 
 * @author leonardo
 */
public interface Verificacao {

    public boolean isNecessario(String id);
    public boolean verificar(String id);
    public String getMensagemDeAcessoNaoPermitido();
    
}
