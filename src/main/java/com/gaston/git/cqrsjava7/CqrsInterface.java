package com.gaston.git.cqrsjava7;

import com.gaston.git.cqrsjava7.core.handling.ResponsibilityTask;
import com.gaston.git.cqrsjava7.core.handling.commands.PublicCommandHandler;
import com.gaston.git.cqrsjava7.core.handling.queries.PublicQueryHandler;
import com.gaston.git.cqrsjava7.core.CqrsContext;

/**
 * Clase de entrada a los metodos expuestos por el core principal
 *
 * @author gaston
 */
public class CqrsInterface {

    private final static CqrsInterface instance = new CqrsInterface();

    /**
     * Clase principal
     */
    private CqrsContext context;

    public CqrsInterface() {
    }

    public static void setContext(CqrsContext context) {
        instance.context = context;

    }

    public static void executeCommand(ResponsibilityTask command) throws Exception {
        instance.context.executeCommandTask(command);
    }

    public static PublicCommandHandler getCommandHandler(ResponsibilityTask command) {
        return instance.context.getCommandHandler(command);
    }

    public static <T extends Object> T executeQuery(ResponsibilityTask command) throws Exception {
        return instance.context.executeQueryTask(command);
    }

    public static PublicQueryHandler getQueryHandler(ResponsibilityTask query) {
        return instance.context.getQueryHandler(query);
    }
}
