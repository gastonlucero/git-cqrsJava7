package com.gaston.git.cqrsjava7.responsibility.handling.query;

import com.gaston.git.cqrsjava7.annotations.ResponsibilityHandler;
import com.gaston.git.cqrsjava7.core.handling.queries.QueryHandler;
import com.gaston.git.cqrsjava7.responsibility.task.TestTaskQuery;

/**
 * Esta clas representa el Handler asociado a un query determinado, aqui es
 * donde debe incluirse la logica asociada al query lanzado, en el metodo handle
 * como punto de entrada y retornando como valor de respuesta , un objecto deñ
 * tipo de dato especificado en la definicion de la clase(String en este caso)
 *
 * @author gaston
 */
@ResponsibilityHandler(responsibleFor = TestTaskQuery.class)
public class TestQueryHandler extends QueryHandler<TestTaskQuery, String> {

    @Override
    public String handle(TestTaskQuery query) throws Exception {
        System.out.println(query.toString());
        return "query ejecutado";
        //codigo ...
    }

}
