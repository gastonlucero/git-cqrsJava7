package com.gaston.git.cqrsjava7.responsabilty.handling.command;

import com.gaston.git.cqrsjava7.core.handling.commands.CommandHandler;
import com.gaston.git.cqrsjava7.annotations.ResponsibilityHandler;
import com.gaston.git.cqrsjava7.responsibility.task.TestTaskCommand;
import org.apache.log4j.Logger;

/**
 * Esta clas representa el Handler asociado a un comando detreminado, aqui es
 * donde debe incluirse la logica asociada al comando lanzado, en el metodo handle
 * como punto de entrada
 *
 * @author gaston
 */
@ResponsibilityHandler(responsibleFor = TestTaskCommand.class)
public class TestCommandHandler extends CommandHandler<TestTaskCommand> {

    private static final Logger logger = Logger.getLogger("log");

    @Override
    public void handle(TestTaskCommand command) throws Exception {
        logger.info("Comando en TestCommandHandler " + command.toString());
        //codigo ...
    }

}
