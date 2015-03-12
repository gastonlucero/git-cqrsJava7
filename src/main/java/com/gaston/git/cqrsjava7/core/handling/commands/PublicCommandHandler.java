package com.gaston.git.cqrsjava7.core.handling.commands;

import com.gaston.git.cqrsjava7.core.handling.ResponsibilityTask;

/**
 * Interfaz a implementar por cada clase que herede de CommandHandler
 *
 * @author gaston
 */
public interface PublicCommandHandler<C extends ResponsibilityTask> {

    /**
     * Este metodo se debe implementar en cada subclase y es donde se encuentra
     * la logica asociado a la ejecución del comando
     *
     * @param command Comando a ejecutar
     * @throws Exception Exception que puede lanzar el commandHanlder
     */
    public void handleCommand(C command) throws Exception;
}
