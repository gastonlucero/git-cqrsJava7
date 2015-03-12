package com.gaston.git.cqrsjava7.core.handling.queries;

import com.gaston.git.cqrsjava7.CqrsInterface;
import com.gaston.git.cqrsjava7.core.handling.ResponsibilityTask;
import java.util.concurrent.Callable;

/**
 * Thread que representa la ejecucion de un query determinado.AL momento de
 * ejecutarse el metodo call se obtiene el QueryHandler asociado al query a
 * ejecutar.Como un query representa una operacion que retorna un resultado, el
 * metodo retorna uan valor de tipo R, que representa cualquier tipo de clase
 * que se defina en la definicion del handler que manejara esa query
 *
 * @author gaston
 */
public class QueryTask<R extends Object> implements Callable<R> {

    private Thread thread;
    private Throwable exception;
    private ResponsibilityTask query;
    private R result;

    public QueryTask(ResponsibilityTask command) {
        this.query = command;
        this.thread = Thread.currentThread();
    }

    /**
     * Metodo que ejecuta la tarea y retorna el resultado de la ejecución
     *
     * @return Resultado de la ejecucion del query
     */
    @Override
    public R call() {
        try {
            PublicQueryHandler handler = CqrsInterface.getQueryHandler(getQuery());
            setResult((R) handler.handleQuery(getQuery()));
        } catch (Exception e) {
            setResult(null);
            exception = e;
        }
        return getResult();
    }

    /**
     * Obtiene el hilo de ejecución actual
     *
     * @return Hilo de ejecución que se podria usar para debug
     */
    public Thread getThread() {
        return thread;
    }

    /**
     * Hilo de ejecución
     *
     * @param thread
     */
    public void setThread(Thread thread) {
        this.thread = thread;
    }

    /**
     * Query a ejecutarse
     *
     * @return Query a ejecutar
     */
    public ResponsibilityTask getQuery() {
        return query;
    }

    /**
     * Establece el query a ejecutar en la tarea
     *
     * @param query query a ejecutar
     */
    public void setQuery(ResponsibilityTask query) {
        this.query = query;
    }

    /**
     * Resultado de la ejecución del query en el handler
     *
     * @return Resultado de la ejecución del qeury
     */
    public R getResult() {
        return result;
    }

    /**
     * Establece el resultado del query
     *
     * @param result Resultado del query
     */
    public void setResult(R result) {
        this.result = result;
    }

    /**
     * Obtiene la excepción que se pudo haber generado en la ejecución del
     * query lanzada desde el queryHandler
     *
     * @return Exception lanzada
     */
    public Throwable getException() {
        return exception;
    }

    /**
     * Setea la excepción lanzada
     *
     * @param exception
     */
    public void setException(Throwable exception) {
        this.exception = exception;
    }

}
