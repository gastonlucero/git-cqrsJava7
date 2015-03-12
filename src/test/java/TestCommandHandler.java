
import com.gaston.git.cqrsjava7.core.handling.commands.CommandGateway;
import com.gaston.git.cqrsjava7.core.CqrsContext;
import com.gaston.git.cqrsjava7.core.handling.queries.QueryGateway;
import com.gaston.git.cqrsjava7.responsibility.task.TestTaskCommand;
import com.gaston.git.cqrsjava7.responsibility.task.TestTaskQuery;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author gaston
 */
public class TestCommandHandler {

    public TestCommandHandler() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @Before
    public void setUp() {
        new CqrsContext();
    }

    @Test
    public void testSendQuery() {
        try {
            TestTaskQuery query = new TestTaskQuery("id", "gaston");
            assert QueryGateway.executeQuery(query).equals("query ejecutado");
        } catch (Exception e) {

        }
    }

    @Test
    public void testSendCommand() {
        try {
            TestTaskCommand command = new TestTaskCommand("id", "gaston");
            CommandGateway.executeCommand(command);
            CommandGateway.executeCommand(command);
            CommandGateway.executeCommand(command);
        } catch (Exception e) {

        }
    }

}
