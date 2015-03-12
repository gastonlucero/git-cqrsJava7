package com.gaston.git.cqrsjava7.core.handling.queries;

import com.gaston.git.cqrsjava7.core.handling.ResponsibilityTask;

/**
 * La clase que herede de esta clase represetn un handler para un query en
 * particular, la subclase debe tener el annotation ResposibilityHandler
 *
 * @author gaston
 */
public abstract class QueryHandler<C extends ResponsibilityTask, T extends Object>
        implements PublicQueryHandler<C, T> {

    @Override
    public T handleQuery(C query) throws Exception {
        return this.handle(query);
    }

   /**
     * Metodo a implementar por cada subclase
     *
     * @param query Query a ejecutar
     * @throws Exception
     */
    public abstract T handle(C query) throws Exception;
}
