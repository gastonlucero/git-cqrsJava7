package com.gaston.git.cqrsjava7.core.commons;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import org.apache.log4j.Logger;

/**
 * Clase encargada de manejar los accesos a las propiedades del archivo de
 * recursos de la aplicación
 *
 * @author gaston
 */
public final class ResourcesManager {

    private static final Logger logger = Logger.getLogger("log");

    /**
     * Nombre del archivo de porpiedades que se encuentra en el classpath
     */
    private static final String PROPERTY_FILE_NAME = "core.properties";

    /**
     * Separador de los valores dentro de una propiedad
     */
    private static final String SEPARATOR = ",";

    /**
     * Caracter que delimita el inicio de una lista de valores
     */
    private static final String BEGIN_PROPERTY = "[";

    /**
     * Caracter que delimita el fin de una lista de valores
     */
    private static final String END_PROPERTY = "]";

    private static Properties properties;

    public ResourcesManager() {
        loadProperties();
    }

    /**
     * Metodo que carga las propiedades de la aplicación
     */
    public void loadProperties() {
        try {
            properties = new Properties();
            properties.load(getClass().getClassLoader().getResourceAsStream(PROPERTY_FILE_NAME));
        } catch (IOException e) {
            logger.error("Error al cargar properties", e);
        }
    }

    /**
     * Metodo que dada una key retorna el value asociado en el archivo de
     * propiedades. Si la clave no exista se lanza una excepción
     *
     * @param key Clave a buscar
     * @return Valor asociado a la key
     */
    public static String getPropertyValue(String key) {
        if (!properties.containsValue(key)) {
            logger.error("No existe valor para la propiedad " + key);
        }
        return properties.getProperty(key);
    }

    public static int getPropertyValueAsInt(String key) {
        return Integer.valueOf(getPropertyValue(key));
    }

    /**
     * Metodo que dada una key retorna el value asociado en el archivo de
     * propiedades en forma de lista de String. Si la clave no exista se lanza
     * una excepción
     *
     * @param key Clave a buscar
     * @return Valor asociado a la key
     */
    public static List<String> getPropertyAsList(String key) {
        List<String> result;
        String listProperty = properties.getProperty(key);
        if (listProperty.startsWith(BEGIN_PROPERTY) && listProperty.endsWith(END_PROPERTY)) {
            listProperty = listProperty.substring(1, listProperty.length() - 1);
            result = Arrays.asList(listProperty.split(SEPARATOR));
        } else {
            result = new ArrayList<>();
        }
        return result;
    }
}
