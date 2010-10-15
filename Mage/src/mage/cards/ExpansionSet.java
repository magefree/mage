/*
* Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without modification, are
* permitted provided that the following conditions are met:
*
*    1. Redistributions of source code must retain the above copyright notice, this list of
*       conditions and the following disclaimer.
*
*    2. Redistributions in binary form must reproduce the above copyright notice, this list
*       of conditions and the following disclaimer in the documentation and/or other materials
*       provided with the distribution.
*
* THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
* FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
* SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
* NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
* ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
* The views and conclusions contained in the software and documentation are those of the
* authors and should not be interpreted as representing official policies, either expressed
* or implied, of BetaSteward_at_googlemail.com.
*/

package mage.cards;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public abstract class ExpansionSet implements Serializable {

	protected String name;
	protected String code;
	protected String symbolCode;
	protected List<Class> cards;

	public ExpansionSet(String name, String code, String symbolCode, String packageName) {
		this.name = name;
		this.code = code;
		this.symbolCode = symbolCode;
		this.cards = getCardClassesForPackage(packageName);
	}

	public List<Class> getCards() {
		return cards;
	}

	public String getName() {
		return name;
	}

	public String getCode() {
		return code;
	}

	public String getSymbolCode() {
		return symbolCode;
	}

	public Card createCard(Class clazz) {
		try {
			Constructor<?> con = clazz.getConstructor(new Class[]{UUID.class});
			return (Card) con.newInstance(new Object[] {null});
		} catch (Exception ex) {
			Logger.getLogger(ExpansionSet.class.getName()).log(Level.SEVERE, "Error creating card:" + clazz.getName(), ex);
			return null;
		}
	}

	public Set<Card> createCards() {
		Set<Card> created = new HashSet<Card>();
		for (Class clazz: cards) {
			created.add(createCard(clazz));
		}
		return created;
	}

	@Override
	public String toString() {
		return name;
	}

	protected ArrayList<Class> getCardClassesForPackage(String packageName) {
		ArrayList<Class> classes = new ArrayList<Class>();
		// Get a File object for the package
		File directory = null;
		String fullPath;
		String relPath = packageName.replace('.', '/');
		URL resource = ClassLoader.getSystemClassLoader().getResource(relPath);
		if (resource == null) {
			throw new RuntimeException("No resource for " + relPath);
		}
		fullPath = resource.getFile();
		directory = new File(fullPath);

		try {
			String jarPath = fullPath.replaceFirst("[.]jar[!].*", ".jar").replaceFirst("file:", "").replaceAll("%20", " ");
			JarFile jarFile = new JarFile(jarPath);
			Enumeration<JarEntry> entries = jarFile.entries();
			while(entries.hasMoreElements()) {
				JarEntry entry = entries.nextElement();
				String entryName = entry.getName();
				if(entryName.startsWith(relPath) && entryName.length() > (relPath.length() + "/".length())) {
					String className = entryName.replace('/', '.').replace('\\', '.').replace(".class", "");
					try {
						Class clazz = Class.forName(className);
						if (CardImpl.class.isAssignableFrom(clazz)) {
							classes.add(clazz);
						}
					}
					catch (ClassNotFoundException e) {
						throw new RuntimeException("ClassNotFoundException loading " + className);
					}
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(packageName + " (" + directory + ") does not appear to be a valid package", e);
		}
		return classes;
	}

}
