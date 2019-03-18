

package mage.server;

import mage.server.util.PluginClassLoader;
import mage.util.StreamUtils;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * @author Lymia
 */
public final class ExtensionPackageLoader {
    public static ExtensionPackage loadExtension(File directory) throws IOException {
        if(!directory.exists     ()) throw new RuntimeException("File not found "+directory);
        if(!directory.isDirectory()) throw new RuntimeException(directory+" is not a directory");

        File entryPointFile = new File(directory, "entryPoint");
        if(!entryPointFile.exists() || !entryPointFile.isFile())
            throw new RuntimeException("Entry point definition not found.");

        File packagesDirectory = new File(directory, "packages");
        if(!packagesDirectory.exists() || !packagesDirectory.isDirectory())
            throw new RuntimeException("Packages directory not found.");

        Scanner entryPointReader = new Scanner(entryPointFile);
        String entryPoint = entryPointReader.nextLine().trim();
        entryPointReader.close();

        PluginClassLoader classLoader = null;
        try {
            classLoader = new PluginClassLoader();
            for(File f : packagesDirectory.listFiles()) {
                classLoader.addURL(f.toURI().toURL());
            }
            return (ExtensionPackage) Class.forName(entryPoint, false, classLoader).newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Entry point class not found!", e);
        } catch (ClassCastException e) {
            throw new RuntimeException("Entry point not an instance of ExtensionPackage.", e);
        } finally {
            StreamUtils.closeQuietly(classLoader);
        }
    }
}
