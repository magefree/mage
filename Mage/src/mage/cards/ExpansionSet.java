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
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.logging.Logger;
import mage.Constants.Rarity;
import mage.util.Logging;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public abstract class ExpansionSet implements Serializable {

	private final static Logger logger = Logging.getLogger(ExpansionSet.class.getName());

	protected static Random rnd = new Random();

	protected String name;
	protected String code;
	protected String symbolCode;
	protected Date releaseDate;
	protected ExpansionSet parentSet;
	protected List<Class> cards;
	protected boolean core;
	protected Map<Rarity, List<Class>> rarities;

	protected String blockName;
	protected int numBoosterLands;
	protected int numBoosterCommon;
	protected int numBoosterUncommon;
	protected int numBoosterRare;
	protected int ratioBoosterMythic;

	public ExpansionSet(String name, String code, String symbolCode, String packageName, Date releaseDate, boolean core) {
		this.name = name;
		this.code = code;
		this.symbolCode = symbolCode;
		this.releaseDate = releaseDate;
		this.core = core;
		this.cards = getCardClassesForPackage(packageName);
		this.rarities = getCardsByRarity();
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

	public Date getReleaseDate() {
		return releaseDate;
	}
	
	public boolean isCore() {
		return core;
	}

	public Card createCard(Class clazz) {
		try {
			Constructor<?> con = clazz.getConstructor(new Class[]{UUID.class});
			return (Card) con.newInstance(new Object[] {null});
		} catch (Exception ex) {
			logger.log(Level.SEVERE, "Error creating card:" + clazz.getName(), ex);
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

	public String findCard(String name) {
		for (Card card: createCards()) {
			if (name.equals(card.getName()))
				return card.getClass().getCanonicalName();
		}
		return null;
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

	private Map<Rarity, List<Class>> getCardsByRarity() {
		Map<Rarity, List<Class>> cardsByRarity = new HashMap<Rarity, List<Class>>();

		for (Class clazz: cards) {
			Card card = createCard(clazz);
			if (!cardsByRarity.containsKey(card.getRarity()))
				cardsByRarity.put(card.getRarity(), new ArrayList<Class>());
			cardsByRarity.get(card.getRarity()).add(clazz);
		}

		return cardsByRarity;
	}

	public List<Card> createBooster() {
		List<Card> booster = new ArrayList<Card>();

		if (parentSet != null) {
			parentSet.getRandom(Rarity.LAND);
		}
		else {
			booster.add(getRandom(Rarity.LAND));
		}
		for (int i = 0; i < numBoosterCommon; i++) {
			booster.add(getRandom(Rarity.COMMON));
		}
		for (int i = 0; i < numBoosterUncommon; i++) {
			booster.add(getRandom(Rarity.UNCOMMON));
		}
		for (int i = 0; i < numBoosterRare; i++) {
			if (rnd.nextInt(ratioBoosterMythic) == 1) {
				booster.add(getRandom(Rarity.MYTHIC));
			}
			else {
				booster.add(getRandom(Rarity.RARE));
			}
		}

		return booster;
	}

	protected Card getRandom(Rarity rarity) {
		if (!rarities.containsKey(rarity))
			return null;
		int size = rarities.get(rarity).size();
		if (size > 0) {
			return createCard(rarities.get(rarity).get(rnd.nextInt(size)));
		}
		return null;
	}
}
