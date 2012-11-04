/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

/**
 *
 * @author North
 */
public class ClassScanner {

    public static List<Class> findClasses(List<String> packages, Class<?> type) {
        List<Class> cards = new ArrayList<Class>();
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            assert classLoader != null;

            HashMap<String, String> dirs = new HashMap<String, String>();
            TreeSet<String> jars = new TreeSet<String>();
            for (String packageName : packages) {
                String path = packageName.replace('.', '/');
                Enumeration<URL> resources = classLoader.getResources(path);

                while (resources.hasMoreElements()) {
                    URL resource = resources.nextElement();
                    String filePath = resource.getFile();
                    if (filePath.startsWith("file:")) {
                        filePath = filePath.substring("file:".length(), filePath.lastIndexOf("!"));
                        jars.add(filePath);
                    } else {
                        dirs.put(filePath, packageName);
                    }
                }
            }

            for (String filePath : dirs.keySet()) {
                cards.addAll(findClasses(new File(filePath), dirs.get(filePath), type));
            }

            for (String filePath : jars) {
                File file = new File(URLDecoder.decode(filePath, "UTF-8"));
                cards.addAll(findClassesInJar(file, packages, type));
            }
        } catch (IOException ex) {
        }
        return cards;
    }

    private static List<Class> findClasses(File directory, String packageName, Class<?> type) {
        List<Class> cards = new ArrayList<Class>();
        if (!directory.exists()) {
            return cards;
        }

        for (File file : directory.listFiles()) {
            if (file.getName().endsWith(".class")) {
                try {
                    Class<?> clazz = Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6));
                    if (type.isAssignableFrom(clazz)) {
                        cards.add(clazz);
                    }
                } catch (ClassNotFoundException ex) {
                }
            }
        }
        return cards;
    }

    private static List<Class> findClassesInJar(File file, List<String> packages, Class<?> type) {
        List<Class> cards = new ArrayList<Class>();
        if (!file.exists()) {
            return cards;
        }
        JarInputStream jarFile = null;
        try {
            jarFile = new JarInputStream(new FileInputStream(file));
            while (true) {
                JarEntry jarEntry = jarFile.getNextJarEntry();
                if (jarEntry == null) {
                    break;
                }
                if (jarEntry.getName().endsWith(".class")) {
                    String className = jarEntry.getName().replace('/', '.').replace(".class", "");
                    int packageNameEnd = className.lastIndexOf('.');
                    String packageName = packageNameEnd != -1 ? className.substring(0, packageNameEnd) : "";
                    if (packages.contains(packageName)) {
                        Class<?> clazz;
                        try {
                            clazz = Class.forName(className);
                            if (type.isAssignableFrom(clazz)) {
                                cards.add(clazz);
                            }
                        } catch (ClassNotFoundException ex) {
                        }
                    }
                }
            }
        } catch (IOException ex) {
        } finally {
            try {
                jarFile.close();
            } catch (IOException ex) {
            }
        }
        return cards;
    }
}
