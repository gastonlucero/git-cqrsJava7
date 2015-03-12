package com.gaston.git.cqrsjava7.core;

import com.gaston.git.cqrsjava7.core.handling.ResponsibilityTask;
import com.gaston.git.cqrsjava7.core.handling.commands.CommandHandler;
import com.gaston.git.cqrsjava7.core.handling.commands.CommandTask;
import com.gaston.git.cqrsjava7.CqrsInterface;
import com.gaston.git.cqrsjava7.core.handling.commands.PublicCommandHandler;
import com.gaston.git.cqrsjava7.core.handling.queries.PublicQueryHandler;
import com.gaston.git.cqrsjava7.core.handling.queries.QueryHandler;
import com.gaston.git.cqrsjava7.core.handling.queries.QueryTask;
import com.gaston.git.cqrsjava7.annotations.ResponsibilityHandler;
import com.gaston.git.cqrsjava7.core.commons.ResourcesManager;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.apache.log4j.Logger;

/**
 * Clase principal que maneja los thread pool de ejecucion de comandos y de
 * queries
 *
 * @author gaston
 */
public class CqrsContext {

    private static final Logger logger = Logger.getLogger("log");

    /**
     * Executor encargado de las tareas asociadas a queries
     */
    private ExecutorService queryExecutor;

    /**
     * Executor encargado de las tareas asociadas a commands
     */
    private ExecutorService commandExecutor;

    /**
     * Mapa que mantiene la correspondencia entre un comando y el handler
     * asociado al mismo. TODO:La relacion es de uno a uno actualmente, deberia
     * soportar multiples commands cada commandHandler
     */
    private Map<Class, PublicCommandHandler> commandHandler;

    /**
     * Mapa que mantiene la correspondencia entre un query y el handler asociado
     * al mismo. TODO:La relacion es de uno a uno actualmente, deberia soportar
     * multiples queries cada queryHandler
     */
    private Map<Class, PublicQueryHandler> queryHandler;

    private ResourcesManager resources;   
    
    private static final String COMMAND_POOL = "commandHandler.poolSize";
    private static final String QUERY_POOL = "queryHandler.poolSize";

    public CqrsContext() {
        try {
            logger.debug("Iniciando Cqrs core");
            resources = new ResourcesManager();
            long timestamp = System.currentTimeMillis();
            queryExecutor = Executors.newFixedThreadPool(ResourcesManager.getPropertyValueAsInt(COMMAND_POOL));
            commandExecutor = Executors.newFixedThreadPool(ResourcesManager.getPropertyValueAsInt(QUERY_POOL));
            queryHandler = Collections.synchronizedMap(new HashMap<Class, PublicQueryHandler>());
            commandHandler = Collections.synchronizedMap(new HashMap<Class, PublicCommandHandler>());   
            
            String packageBase = "com.gaston.git.cqrsjava7".replace("/", ".");
            URL resource = this.getClass().getClassLoader().getResource(packageBase.replace(".", "/"));
            for (Class clazzHandler : mappingHandlers(new File(resource.getFile().replace("%20", " ")), packageBase, CommandHandler.class)) {
                if (!commandHandler.containsKey(((ResponsibilityHandler) clazzHandler.getAnnotation(ResponsibilityHandler.class)).responsibleFor())) {
                    commandHandler.put(((ResponsibilityHandler) clazzHandler.getAnnotation(ResponsibilityHandler.class)).responsibleFor(),
                            (PublicCommandHandler) clazzHandler.newInstance());
                } else {
                    throw new IllegalAccessException("Un CommandHandler puede manejar solo un comando-Handler[" + clazzHandler.getSimpleName() + "]");
                }
            }
            for (Class clazzHandler : mappingHandlers(new File(resource.getFile().replace("%20", " ")), packageBase, QueryHandler.class)) {
                if (!queryHandler.containsKey(((ResponsibilityHandler) clazzHandler.getAnnotation(ResponsibilityHandler.class)).responsibleFor())) {
                    queryHandler.put(((ResponsibilityHandler) clazzHandler.getAnnotation(ResponsibilityHandler.class)).responsibleFor(),
                            (PublicQueryHandler) clazzHandler.newInstance());
                } else {
                    throw new IllegalAccessException("Un QueryHandler puede manejar solo un comando-Handler[" + clazzHandler.getSimpleName() + "]");
                }
            }
            logger.debug("Cqrs core Iniciado en " + (System.currentTimeMillis() - timestamp));
            
            CqrsInterface.setContext(this);
        } catch (InstantiationException | IllegalAccessException e) {
            logger.error("Error iniciando cqrsCore", e);
        }
    }

    private List<Class> mappingHandlers(File directory, String packageBase, Class clazzHandler) {
        List<Class> classes = new ArrayList<>();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                classes.addAll(mappingHandlers(file, packageBase + "." + file.getName(), clazzHandler));
            } else if (file.getName().endsWith(".class")) {
                try {
                    //-6 para restar .class
                    Class clazz = Class.forName(packageBase + '.'
                            + file.getName().substring(0, file.getName().length() - 6));
                    if (clazz.isAnnotationPresent(ResponsibilityHandler.class) && clazzHandler.isAssignableFrom(clazz)) {
                        classes.add(clazz);
                        logger.debug("Handler encontrado (" + clazzHandler.getSimpleName() + ")");
                    }
                } catch (ClassNotFoundException e) {
                    logger.error("La clase " + file.getName() + " no existe", e);
                }
            }
        }
        return classes;
    }

    /**
     * Metodo que ejecuta un comando de tipo query retornando una respuesta
     * determinada
     *
     * @param <T> Respuesta del query
     * @param query Comando asociado al query solicitado
     * @return Cualquier objecto resultado del query
     * @throws Exception Si se produce una excepcion en la ejcucion del hilo
     * este se interrupte y se lanza la exception
     */
    public <T extends Object> T executeQueryTask(ResponsibilityTask query) throws Exception {
        try {
            QueryTask<T> task = new QueryTask(query);
            Future<T> result = queryExecutor.submit(task);
            T t = result.get();
            return t;
        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            throw new Exception(e.getMessage(), e);
        }
    }

    public void executeCommandTask(ResponsibilityTask command) throws Exception {
        try {
            CommandTask task = new CommandTask(command);
            commandExecutor.execute(task);
        } catch (Exception e) {
            Thread.currentThread().interrupt();
            throw new Exception(e.getMessage());
        }
    }

    public PublicCommandHandler getCommandHandler(ResponsibilityTask command) {
        try {
            PublicCommandHandler handler = commandHandler.get(command.getClass());
            return handler;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public PublicQueryHandler getQueryHandler(ResponsibilityTask query) {
        try {
            PublicQueryHandler handler = queryHandler.get(query.getClass());
            return handler;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
