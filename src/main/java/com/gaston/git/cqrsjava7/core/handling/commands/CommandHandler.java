package com.gaston.git.cqrsjava7.core.handling.commands;

import com.gaston.git.cqrsjava7.core.handling.ResponsibilityTask;

/**
 * La clase que herede de esta clase represetn un handler para un comando en
 * particular, la subclase debe tener el annotation ResposibilityHandler
 *
 * @author gaston
 */
public abstract class CommandHandler<C extends ResponsibilityTask> implements PublicCommandHandler<C> {

    @Override
    public void handleCommand(C command) throws Exception {
        handle(command);
        //abierto para funcionalidades extras
    }

    /**
     * Metodo a implementar por cada subclase
     *
     * @param command Comando a ejecutar
     * @throws Exception
     */
    public abstract void handle(C command) throws Exception;
}
