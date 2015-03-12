package com.gaston.git.cqrsjava7.core.handling.queries;

import com.gaston.git.cqrsjava7.CqrsInterface;
import com.gaston.git.cqrsjava7.core.handling.ResponsibilityTask;

/**
 * Esta clase es el encargado de enviar queries al cqrs core para que se
 * ejecuten. Seria como el bus encargado de los queries.
 *
 * @author gaston
 */
public final class QueryGateway {

     public static  <T extends Object> T executeQuery(ResponsibilityTask command) throws Exception{
        return  CqrsInterface.executeQuery(command);
    }
}
