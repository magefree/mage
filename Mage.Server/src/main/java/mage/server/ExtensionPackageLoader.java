/*
 *  Copyright 2016 Lymia <lymia@lymiahugs.com>. All rights reserved.
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

package mage.server;

import mage.server.util.PluginClassLoader;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * @author Lymia
 */
public class ExtensionPackageLoader {
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

        PluginClassLoader classLoader = new PluginClassLoader();
        for(File f : packagesDirectory.listFiles()) classLoader.addURL(f.toURI().toURL());

        try {
            return (ExtensionPackage) Class.forName(entryPoint, false, classLoader).newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Entry point class not found!", e);
        } catch (ClassCastException e) {
            throw new RuntimeException("Entry point not an instance of ExtensionPackage.", e);
        }
    }
}
