package com.gaston.git.cqrsjava7.core;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 *
 * @author gaston
 */
public class CqrsClassLoader extends ClassLoader {

    public CqrsClassLoader(ClassLoader parent) {
        super(parent);        
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        return super.loadClass(name);
    }

    public List<Class> findClassesBySuperclass(File directory, String packageName, Class superClazz) {
        List<Class> classes = new ArrayList<>();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                classes.addAll(findClassesBySuperclass(file, packageName + "." + file.getName(), superClazz));
            } else if (file.getName().endsWith(".class")) {
                try {
                    //-6 para restar .class
                    Class clazz = Class.forName(packageName + '.'
                            + file.getName().substring(0, file.getName().length() - 6));
                    if (superClazz.isAssignableFrom(clazz)) {
                        classes.add(clazz);
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        return classes;
    }

    /**
     * Retorna la lista de clases de un paquete y sus subpaquetes determinados
     * por el tipo de anotacion que llega como parametro
     *
     * @param directory Nombre del directorio actual de busqeuda
     * @param packageName Paquete base a bsucar las clases
     * @param annotation Tipo de anotacion para retornar las clases que la
     * contengan
     *
     * @return Listado de clases que tengan la anotacion
     */
    public List<Class> findClasses(File directory, String packageName, Class annotation) {
        List<Class> classes = new ArrayList<>();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                classes.addAll(findClasses(file, packageName + "." + file.getName(), annotation));
            } else if (file.getName().endsWith(".class")) {
                try {
                    //-6 para restar .class
                    Class clazz = Class.forName(packageName + '.'
                            + file.getName().substring(0, file.getName().length() - 6));
                    if (clazz.isAnnotationPresent(annotation)) {
                        classes.add(clazz);
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        return classes;
    }

    /**
     *
     * @param directoryName Nombre del directorio
     * @return Listado de paquetes en forma de directorios
     */
    public List<File> getDirectoryResources(String directoryName) throws Exception {
        try {
            List<File> dirs = new ArrayList<>();
            Enumeration<URL> resources = this.getResources(directoryName);
            while (resources.hasMoreElements()) {
                URL resource = resources.nextElement();
                dirs.add(new File(resource.getFile().replace("%20", " ")));
            }
            return dirs;
        } catch (IOException ex) {
            throw new Exception(ex.getMessage());
        }

    }
}
