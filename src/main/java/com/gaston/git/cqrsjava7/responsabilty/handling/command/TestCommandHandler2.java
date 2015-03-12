package com.gaston.git.cqrsjava7.responsabilty.handling.command;

import com.gaston.git.cqrsjava7.core.handling.commands.CommandHandler;
import com.gaston.git.cqrsjava7.annotations.ResponsibilityHandler;
import com.gaston.git.cqrsjava7.responsibility.task.TestTaskQuery;

/**
 *
 * @author gaston
 */
@ResponsibilityHandler(responsibleFor = TestTaskQuery.class)
public class TestCommandHandler2 extends CommandHandler<TestTaskQuery> {

    @Override
    public void handle(TestTaskQuery command) throws Exception {
        System.out.println(command.toString());
        //codigo ...
    }

}
