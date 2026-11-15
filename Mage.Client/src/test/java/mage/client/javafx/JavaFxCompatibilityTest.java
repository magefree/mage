package mage.client.javafx;

import org.junit.Assert;
import org.junit.Assume;
import org.junit.Test;

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

/**
 * Compatibility tests for different JavaFX and JDK versions. Also can be used by devs to setup environment.
 * TODO: delete after release migrate to single openjdk 26+
 * <p>
 * Reason: JavaFX require strict compatibility between build JDK and runtime JavaFX versions, 
 * otherwise it can crash the JVM with SIGSEGV on linux and MacOS.
 * Official JavaFX's version support: [-2, current jdk], e.g. [24, 25, 26]
 * Older versions works as is and can fail, e.g. on inner classes changes 
 * like jdk.jsobject removed from JDK 26.
 * <p>
 * Usage example:
 * <pre>
 *     cd Mage.Client
 *     mvn test -Dtest=JavaFxCompatibilityTest
 * </pre>
 * <p>
 * IDE's run are different from mvn run:
 * - VS Code -- fail due to test runner using inner redhat's java 21 all the time
 * - IntelliJ IDEA -- pass due to test runner using the same JDK as Maven
 * - NetBeans -- pass due to usage of Maven instead of test runner
 *
 * @author JayDi85
 */
public class JavaFxCompatibilityTest {

    // add here any deps not related to javafx
    private static final List<String> ALLOWED_MISSING_PREFIXES = Arrays.asList(
            "com.sun.media.jfxmediaimpl.platform.ios.", // java 8 + javafx 11 skip (real javafx files taken from jdk)
            "com.sun.media.jfxmediaimpl.platform.osx." // java 8 + javafx 11 skip (real javafx files taken from jdk)
    );

    @Test
    public void test_JavaFxClassReferencesResolveOnCurrentJdk() throws Exception {
        // WARNING, this is for mvn test run, not IDE's
        assertRunsOnBuildJdk();
        assumeJavaFxComesFromMaven();

        List<File> javafxJars = findJavaFxJars();
        Assert.assertFalse("No JavaFX jars found on the classpath - check the pom.xml dependencies",
                javafxJars.isEmpty());

        // every type defined by JavaFX itself, so references between JavaFX modules are not reported
        Set<String> ownTypes = new HashSet<>();
        for (File jar : javafxJars) {
            ownTypes.addAll(collectDefinedTypes(jar));
        }

        Set<String> referenced = new LinkedHashSet<>();
        for (File jar : javafxJars) {
            referenced.addAll(collectReferencedTypes(jar));
        }

        System.out.println("JavaFX " + findJavaFxVersion(javafxJars)
                + " on Java " + System.getProperty("java.version")
                + " (" + System.getProperty("java.home") + ")"
                + ", scanned " + referenced.size() + " referenced types");

        Set<String> missing = new LinkedHashSet<>();
        for (String type : referenced) {
            if (ownTypes.contains(type) || isAllowedMissing(type)) {
                continue;
            }
            try {
                Class.forName(type, false, getClass().getClassLoader());
            } catch (ClassNotFoundException | LinkageError e) {
                missing.add(type);
            }
        }

        Assert.assertTrue(String.format(
                        "JavaFX (%s) references %d type(s) that do not exist on this JDK (%s). "
                                + "Using this combination can crash the JVM with SIGSEGV instead of throwing. "
                                + "Missing: %s",
                        findJavaFxVersion(javafxJars), missing.size(), System.getProperty("java.version"),
                        missing.stream().sorted().collect(Collectors.joining(", ", "[", "]"))),
                missing.isEmpty());
    }

    @Test
    public void test_JavaFxModulesUseSameVersion() {
        // WARNING, this is for mvn test run, not IDE's
        assertRunsOnBuildJdk();

        List<File> javafxJars = findJavaFxJars();
        Assert.assertFalse("No JavaFX jars found on the classpath - check the pom.xml dependencies",
                javafxJars.isEmpty());

        // JavaFX modules are built and tested as one set, mixing releases is not supported upstream
        Set<String> versions = new LinkedHashSet<>();
        for (File jar : javafxJars) {
            String version = extractVersionFromJarName(jar.getName());
            if (version != null) {
                versions.add(version);
            }
        }

        Assert.assertEquals(String.format("JavaFX modules must all come from the same release, found: %s",
                        versions.stream().sorted().collect(Collectors.joining(", ", "[", "]"))),
                1, versions.size());
    }

    // do not trust ide's run -- it's can use buitin java
    private static void assertRunsOnBuildJdk() {
        String javaHome = System.getProperty("java.home", "").replace('\\', '/');
        boolean isIdeBundledJre = javaHome.contains("/.vscode-server/")
                || javaHome.contains("/.vscode/extensions/")
                || javaHome.contains("/redhat.java");
        Assert.assertFalse(String.format(
                        "This test must run on the build JDK, but it was started on the IDE's bundled JRE (%s, %s). "
                                + "Run it from the command line instead: mvn test -Dtest=JavaFxCompatibilityTest",
                        System.getProperty("java.version"), javaHome),
                isIdeBundledJre);
    }

    // java 8 (oracle or zulu+fx) has builtin javafx, but not any other openjdk, 
    // so we need to check maven's javafx from pom.xml
    private static void assumeJavaFxComesFromMaven() {
        boolean bundled = false;
        try {
            // parent of the system class loader: bootstrap + extensions, never the classpath
            ClassLoader platformLoader = ClassLoader.getSystemClassLoader().getParent();
            Class.forName("javafx.application.Platform", false, platformLoader);
            bundled = true;
        } catch (ClassNotFoundException | LinkageError e) {
            // no JavaFX inside the JDK, the Maven jars are the ones that will be used
        }
        Assume.assumeFalse("This JDK ships its own JavaFX, so the Maven jars are shadowed"
                + " and never used at runtime - nothing to check here", bundled);
    }

    private static boolean isAllowedMissing(String type) {
        for (String prefix : ALLOWED_MISSING_PREFIXES) {
            if (type.startsWith(prefix)) {
                return true;
            }
        }
        return false;
    }

    private static List<File> findJavaFxJars() {
        List<File> res = new ArrayList<>();
        for (String entry : System.getProperty("java.class.path").split(File.pathSeparator)) {
            String normalized = entry.replace('\\', '/');
            if (normalized.contains("/org/openjfx/") && normalized.endsWith(".jar")) {
                File file = new File(entry);
                if (file.isFile()) {
                    res.add(file);
                }
            }
        }
        return res;
    }

    private static String findJavaFxVersion(List<File> jars) {
        for (File jar : jars) {
            String version = extractVersionFromJarName(jar.getName());
            if (version != null) {
                return version;
            }
        }
        return "unknown";
    }

    /**
     * Extracts "21.0.2" from names like "javafx-web-21.0.2-linux-aarch64.jar".
     */
    private static String extractVersionFromJarName(String jarName) {
        String name = jarName.endsWith(".jar") ? jarName.substring(0, jarName.length() - 4) : jarName;
        String[] parts = name.split("-");
        for (String part : parts) {
            if (!part.isEmpty() && Character.isDigit(part.charAt(0))) {
                return part;
            }
        }
        return null;
    }

    private static Set<String> collectDefinedTypes(File jar) throws IOException {
        Set<String> res = new LinkedHashSet<>();
        try (JarFile jarFile = new JarFile(jar)) {
            Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                String name = entry.getName();
                if (name.endsWith(".class")) {
                    res.add(name.substring(0, name.length() - 6).replace('/', '.'));
                }
            }
        }
        return res;
    }

    private static Set<String> collectReferencedTypes(File jar) throws IOException {
        Set<String> res = new LinkedHashSet<>();
        try (JarFile jarFile = new JarFile(jar)) {
            Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                if (!entry.getName().endsWith(".class")) {
                    continue;
                }
                try (InputStream in = jarFile.getInputStream(entry)) {
                    res.addAll(readClassReferences(new DataInputStream(in)));
                } catch (IOException | IllegalStateException e) {
                    // unreadable class file must not hide the real check, but must not be silent either
                    throw new IOException("Can't parse " + entry.getName() + " in " + jar.getName(), e);
                }
            }
        }
        return res;
    }

    /**
     * Reads the constant pool and returns every type the class refers to.
     * <p>
     * Reflection can't be used for this: it only sees signatures of an already loaded class, while
     * the references that matter here (JSObject among them) appear inside method bodies. The
     * constant pool layout used below has been stable since Java 1.0.
     */
    private static Set<String> readClassReferences(DataInputStream in) throws IOException {
        if (in.readInt() != 0xCAFEBABE) {
            throw new IllegalStateException("Not a class file");
        }
        in.readUnsignedShort(); // minor version
        in.readUnsignedShort(); // major version

        int poolCount = in.readUnsignedShort();
        String[] utf8 = new String[poolCount];
        int[] classNameIndexes = new int[poolCount];

        for (int i = 1; i < poolCount; i++) {
            int tag = in.readUnsignedByte();
            switch (tag) {
                case 1: // Utf8
                    utf8[i] = in.readUTF();
                    break;
                case 7: // Class
                case 8: // String
                case 16: // MethodType
                case 19: // Module
                case 20: // Package
                    int index = in.readUnsignedShort();
                    if (tag == 7) {
                        classNameIndexes[i] = index;
                    }
                    break;
                case 15: // MethodHandle
                    in.skipBytes(3);
                    break;
                case 3: // Integer
                case 4: // Float
                case 9: // Fieldref
                case 10: // Methodref
                case 11: // InterfaceMethodref
                case 12: // NameAndType
                case 17: // Dynamic
                case 18: // InvokeDynamic
                    in.skipBytes(4);
                    break;
                case 5: // Long
                case 6: // Double
                    in.skipBytes(8);
                    i++; // these take two constant pool slots
                    break;
                default:
                    throw new IllegalStateException("Unknown constant pool tag: " + tag);
            }
        }

        Set<String> res = new LinkedHashSet<>();
        for (int i = 1; i < poolCount; i++) {
            if (classNameIndexes[i] == 0) {
                continue;
            }
            String raw = utf8[classNameIndexes[i]];
            String type = toClassName(raw);
            if (type != null) {
                res.add(type);
            }
        }
        return res;
    }

    /**
     * Converts an internal name to a class name, unwrapping array types and skipping primitives.
     */
    private static String toClassName(String raw) {
        if (raw == null) {
            return null;
        }
        String name = raw;
        while (name.startsWith("[")) {
            name = name.substring(1);
        }
        if (name.startsWith("L") && name.endsWith(";")) {
            name = name.substring(1, name.length() - 1);
        }
        if (name.length() <= 1) {
            return null; // primitive descriptor of an array type
        }
        return name.replace('/', '.');
    }
}