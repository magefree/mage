package mage.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

/**
 * @author North
 */
public final class ClassScanner {

    private static final Logger logger = Logger.getLogger(ClassScanner.class);

    private static void checkClassForInclusion(List<Class> cards, Class type, String name, ClassLoader cl) {
        try {
            Class clazz = Class.forName(name, true, cl);
            if (clazz.getEnclosingClass() == null && type.isAssignableFrom(clazz)) {
                cards.add(clazz);
            }
        } catch (ClassNotFoundException ex) {
            // ignored
        }
    }

    public static List<Class> findClasses(ClassLoader classLoader, List<String> packages, Class<?> type) {
        List<Class> cards = new ArrayList<>();
        try {
            if (classLoader == null) classLoader = Thread.currentThread().getContextClassLoader();
            assert classLoader != null;

            Map<String, String> dirs = new HashMap<>();
            Set<String> jars = new TreeSet<>();
            for (String packageName : packages) {
                String path = packageName.replace('.', '/');
                Enumeration<URL> resources = classLoader.getResources(path);

                while (resources.hasMoreElements()) {
                    URL resource = resources.nextElement();
                    String filePath = resource.getFile();
                    if (filePath.startsWith("file:")) {
                        filePath = filePath.substring("file:".length(), filePath.lastIndexOf('!'));
                        jars.add(filePath);
                    } else {
                        try {
                            filePath = resource.toURI().getPath();
                        } catch (URISyntaxException e) {
                            throw new RuntimeException(e);
                        }
                        dirs.put(filePath, packageName);
                    }
                }
            }

            // run by IDE - load classes from disk
            for (Map.Entry<String, String> dir : dirs.entrySet()) {
                cards.addAll(findClassesInDir(classLoader, new File(dir.getKey()), dir.getValue(), type));
            }

            // run by launcher - load classes from jar
            for (String filePath : jars) {
                File file = new File(CardUtil.urlDecode(filePath));
                cards.addAll(findClassesInJar(classLoader, file, packages, type));
            }
        } catch (IOException ex) {
        }
        return cards;
    }

    private static List<Class> findClassesInDir(ClassLoader classLoader, File directory, String packageName, Class<?> type) {
        if (directory == null || !directory.exists()) return new ArrayList<>();

        File[] files = directory.listFiles();
        if (files == null) return new ArrayList<>();

        long start = System.currentTimeMillis();
        List<Class> res = Arrays.stream(files)
                .parallel()
                .filter(file -> file.getName().endsWith(".class"))
                .map(file -> {
                    String name = packageName + '.' + file.getName().substring(0, file.getName().length() - 6);
                    return resolveClassIfAssignable(type, name, classLoader);
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        // load set classes only, card classes are loaded indirectly by static import in the set
        logger.debug("Class files total processing time, ms: " + (System.currentTimeMillis() - start));
        logger.debug("Class files loaded, count: " + res.size());
        return res;
    }

    private static Class<?> resolveClassIfAssignable(Class<?> type, String name, ClassLoader cl) {
        try {
            Class<?> clazz = Class.forName(name, true, cl);
            if (clazz.getEnclosingClass() == null && type.isAssignableFrom(clazz)) {
                return clazz;
            }
        } catch (ClassNotFoundException ignore) {
        }
        return null;
    }


    private static List<Class> findClassesInJar(ClassLoader classLoader, File file, List<String> packages, Class<?> type) {
        if (!file.exists()) return new ArrayList<>();

        long start = System.currentTimeMillis();
        List<Class> result = new ArrayList<>();

        try (JarInputStream jarFile = new JarInputStream(new FileInputStream(file))) {
            List<JarEntry> classEntries = new ArrayList<>();
            JarEntry jarEntry;

            // collect all class entries from the JAR
            while ((jarEntry = jarFile.getNextJarEntry()) != null) {
                if (jarEntry.getName().endsWith(".class")) {
                    String className = jarEntry.getName().replace(".class", "").replace('/', '.');
                    int packageNameEnd = className.lastIndexOf('.');
                    String packageName = packageNameEnd != -1 ? className.substring(0, packageNameEnd) : "";
                    if (packages.contains(packageName)) {
                        classEntries.add(jarEntry);
                    }
                }
            }

            // process and init all classes
            result = classEntries
                    .parallelStream()
                    .map(entry -> {
                        String className = entry.getName().replace(".class", "").replace('/', '.');
                        return resolveClassIfAssignable(type, className, classLoader);
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        } catch (IOException ex) {
            logger.error("Error reading JAR file: " + file.getPath(), ex);
        }

        // load set classes only, card classes are loaded indirectly by static import in the set
        logger.debug("Jar files total processing time, ms: " + (System.currentTimeMillis() - start));
        logger.debug("Jar classes loaded, count: " + result.size());
        return result;
    }
}
