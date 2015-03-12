package com.gaston.git.cqrsjava7.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Esta anotacion se utiliza para representar que una clase actua como handler
 * de un responsibilityTask, sea esta una accion command o una de query
 *
 * @author gaston
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = {ElementType.TYPE})
public @interface ResponsibilityHandler {

    /**
     * Clase que representa la accion que contiene los datos qque se deben manejar
     * por el handler
     * @return Nombre de la calse que representa la responsability a ejecutar
     */
    public Class responsibleFor();
}
