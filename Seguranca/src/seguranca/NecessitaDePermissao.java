package seguranca;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author leonardo
 */
@Retention (RetentionPolicy.RUNTIME)  
@Target (ElementType.METHOD)  
public @interface NecessitaDePermissao {
    String value();
}
