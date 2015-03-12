package com.gaston.git.cqrsjava7.core.handling.queries;

import com.gaston.git.cqrsjava7.core.handling.ResponsibilityTask;

/**
 * Interfaz a implementar por cada clase que herede de CommandHandler
 *
 * @author gaston
 */
public interface PublicQueryHandler<C extends ResponsibilityTask, T extends Object> {

    /**
     * Este metodo se debe implementar en cada subclase y es donde se encuentra
     * la logica asociado a la ejecución del query
     *
     * @param query Query a ejecutar
     * @throws Exception Exception que puede lanzar el commandHanlder
     */
    public T handleQuery(C query) throws Exception;
}
