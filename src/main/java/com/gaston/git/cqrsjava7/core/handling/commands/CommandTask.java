package com.gaston.git.cqrsjava7.core.handling.commands;

import com.gaston.git.cqrsjava7.CqrsInterface;
import com.gaston.git.cqrsjava7.core.handling.ResponsibilityTask;

/**
 * Thread que representa la ejecucion de un comando determinado.AL momento de
 * ejecutarse el metodo run se obtiene el CommandHandler asociado al comando a
 * ejecutar.Como un comando representa una operacion que no retorna respuesta
 * alguna es que se implementa Runnable en lugar de Callable
 *
 * @author gaston
 */
public class CommandTask implements Runnable {

    private Thread thread;
    private Throwable exception;
    private ResponsibilityTask command;

    public CommandTask(ResponsibilityTask command) {
        this.command = command;
        this.thread = Thread.currentThread();
    }

    @Override
    public void run() {
        try {
            PublicCommandHandler handler = CqrsInterface.getCommandHandler(getCommand());
            handler.handleCommand(getCommand());
        } catch (Exception e) {
            exception = e;
        }
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
     * Command a ejecutarse
     *
     * @return Command a ejecutrase
     */
    public ResponsibilityTask getCommand() {
        return command;
    }

    /**
     * Establece el command a ejecutar en la tarea
     *
     * @param commnad commanda ejecutar
     */
    public void setCommand(ResponsibilityTask command) {
        this.command = command;
    }

    /**
     * Obtiene la excepción que se pudo haber generado en la ejecución del
     * command lanzada desde el commandHandler
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
