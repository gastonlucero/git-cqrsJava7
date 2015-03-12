package com.gaston.git.cqrsjava7.core.handling.commands;

import com.gaston.git.cqrsjava7.CqrsInterface;
import com.gaston.git.cqrsjava7.core.handling.ResponsibilityTask;

/**
 * Esta clase es el encargado de enviar comandos al cqrs core para que se
 * ejecuten. Seria como el bus encargado de los comandos.
 *
 * @author gaston
 */
public final class CommandGateway {

    public static void executeCommand(ResponsibilityTask command) throws Exception {
        CqrsInterface.executeCommand(command);
    }
}
