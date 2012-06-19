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

package mage.server.util;

import java.net.URL;
import java.net.URLClassLoader;

/**
 *
 * @author BetaSteward_at_googlemail.com
 *
 * some code courtesy of http://tech.puredanger.com/2006/11/09/classloader/
 *
 */
public class PluginClassLoader extends URLClassLoader {

    public PluginClassLoader(){
        super(new URL[0], PluginClassLoader.class.getClassLoader());
    }

    @Override
    public void addURL(URL url) {
        super.addURL(url);
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        // First check whether it's already been loaded, if so use it
        Class loadedClass = findLoadedClass(name);

        // Not loaded, try to load it
        if (loadedClass == null) {
            try {
                // Ignore parent delegation and just try to load locally
                loadedClass = findClass(name);
            } catch (ClassNotFoundException e) {
                // Swallow exception - does not exist locally
            }

            // If not found locally, use normal parent delegation in URLClassloader
            if (loadedClass == null) {
                // throws ClassNotFoundException if not found in delegation hierarchy at all
                loadedClass = super.loadClass(name);
            }
        }
        // will never return null (ClassNotFoundException will be thrown)
        return loadedClass;
    }


}
