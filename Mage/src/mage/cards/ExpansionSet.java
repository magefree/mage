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

import java.io.UnsupportedEncodingException;
import mage.Constants.Rarity;
import mage.Constants.SetType;
import mage.util.Logging;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLDecoder;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
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
	protected SetType setType;
	protected Map<Rarity, List<Class>> rarities;

	protected String blockName;
	protected boolean hasBoosters = false;
	protected int numBoosterLands;
	protected int numBoosterCommon;
	protected int numBoosterUncommon;
	protected int numBoosterRare;
	protected int ratioBoosterMythic;

	public ExpansionSet(String name, String code, String symbolCode, String packageName, Date releaseDate, SetType setType) {
		this.name = name;
		this.code = code;
		this.symbolCode = symbolCode;
		this.releaseDate = releaseDate;
		this.setType = setType;
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

	public SetType getSetType() {
		return setType;
	}

	public Card createCard(Class clazz) {
		try {
			Constructor<?> con = clazz.getConstructor(new Class[]{UUID.class});
			return (Card) con.newInstance(new Object[]{null});
		} catch (Exception ex) {
			logger.log(Level.SEVERE, "Error creating card:" + clazz.getName(), ex);
			ex.printStackTrace();
			return null;
		}
	}

	public Set<Card> createCards() {
		Set<Card> created = new HashSet<Card>();
		for (Class clazz : cards) {
			created.add(createCard(clazz));
		}
		return created;
	}

	@Override
	public String toString() {
		return name;
	}

	public Card findCard(String name) {
		for (Card card : createCards()) {
			if (name.equals(card.getName()))
				return card;
		}
		return null;
	}

	public Card findCard(String name, boolean random) {
		List<Card> cards = new ArrayList<Card>();
		for (Card card : createCards()) {
			if (name.equals(card.getName())) {
				cards.add(card);
			}
		}
		if (cards.size() > 0) {
			return cards.get(rnd.nextInt(cards.size()));
		}
		return null;
	}

	public String findCard(int cardNum) {
		for (Card card : createCards()) {
			if (card.getCardNumber() == cardNum)
				return card.getClass().getCanonicalName();
		}
		return null;
	}

	private ArrayList<Class> getCardClassesForPackage(String packageName) {
		ClassLoader classLoader = this.getClass().getClassLoader();
		assert classLoader != null;
		String path = packageName.replace('.', '/');
		Enumeration<URL> resources = null;
		try {
			resources = classLoader.getResources(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		List<File> dirs = new ArrayList<File>();
		boolean isLoadingFromJar = false;
		String jarPath = null;
		while (resources.hasMoreElements()) {
			URL resource = resources.nextElement();
			if (resource.toString().startsWith("jar:")) {
				isLoadingFromJar = true;
				jarPath = resource.getFile();
				break;
			}
			try {
				dirs.add(new File(URLDecoder.decode(resource.getFile(), "UTF-8")));
			} catch (UnsupportedEncodingException e) {
				logger.log(Level.SEVERE, "Error decoding director - " + resource.getFile(), e);
				e.printStackTrace();
			}
		}
		ArrayList<Class> classes = new ArrayList<Class>();
		if (isLoadingFromJar) {
			if (jarPath.contains("!")) {
				jarPath = jarPath.substring(0, jarPath.lastIndexOf('!'));
			}
			if (jarPath.startsWith("file:/")) {
				try {
					jarPath = URLDecoder.decode(jarPath.substring(jarPath.indexOf("file:/") + "file:/".length()), "UTF-8");
				} catch (UnsupportedEncodingException e) {
					logger.log(Level.SEVERE, "Error decoding file - " + jarPath, e);
					e.printStackTrace();
				}
			}
			try {
				classes.addAll(findClassesInJar(new File(jarPath), path));
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		} else { // faster but doesn't work for jars
			for (File directory : dirs) {
				try {
					classes.addAll(findClasses(directory, packageName));
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
		return classes;
	}

	private static List<Class> findClasses(File directory, String packageName) throws ClassNotFoundException {
		List<Class> classes = new ArrayList<Class>();
		if (!directory.exists()) {
			return classes;
		}
		File[] files = directory.listFiles();
		if (files == null) {
			return classes;
		}
		for (File file : files) {
			if (file.isDirectory()) {
				assert !file.getName().contains(".");
				classes.addAll(findClasses(file, packageName + "." + file.getName()));
			} else if (file.getName().endsWith(".class")) {
				Class c = Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6));
				if (CardImpl.class.isAssignableFrom(c)) {
					classes.add(c);
				}
			}
		}
		return classes;
	}

	private static List<Class> findClassesInJar(File file, String packageName) throws ClassNotFoundException {
		List<Class> classes = new ArrayList<Class>();

		if (!file.exists()) {
			return classes;
		}

		try {
			URL url = file.toURL();
			URL[] urls = new URL[]{url};
			ClassLoader cl = new URLClassLoader(urls);

			JarInputStream jarFile = new JarInputStream(new FileInputStream(file));
			JarEntry jarEntry;

			while (true) {
				jarEntry = jarFile.getNextJarEntry();
				if (jarEntry == null) {
					break;
				}
				if ((jarEntry.getName().startsWith(packageName)) && (jarEntry.getName().endsWith(".class"))) {
					String clazz = jarEntry.getName().replaceAll("/", "\\.").replace(".class", "");
					Class c = cl.loadClass(clazz);
					if (CardImpl.class.isAssignableFrom(c)) {
						classes.add(c);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}


		return classes;
	}

	private Map<Rarity, List<Class>> getCardsByRarity() {
		Map<Rarity, List<Class>> cardsByRarity = new HashMap<Rarity, List<Class>>();

		for (Class clazz : cards) {
			Card card = createCard(clazz);
			if (!cardsByRarity.containsKey(card.getRarity()))
				cardsByRarity.put(card.getRarity(), new ArrayList<Class>());
			cardsByRarity.get(card.getRarity()).add(clazz);
		}

		return cardsByRarity;
	}

	public List<Card> createBooster() {
		List<Card> booster = new ArrayList<Card>();

		if (!hasBoosters)
			return booster;

		if (parentSet != null) {
			for (int i = 0; i < numBoosterLands; i++) {
				addToBooster(booster, parentSet, Rarity.LAND);
			}
		} else {
			for (int i = 0; i < numBoosterLands; i++) {
				addToBooster(booster, this, Rarity.LAND);
			}
		}
		for (int i = 0; i < numBoosterCommon; i++) {
			addToBooster(booster, this, Rarity.COMMON);
		}
		for (int i = 0; i < numBoosterUncommon; i++) {
			addToBooster(booster, this, Rarity.UNCOMMON);
		}
		for (int i = 0; i < numBoosterRare; i++) {
			if (ratioBoosterMythic > 0 && rnd.nextInt(ratioBoosterMythic) == 1) {
				addToBooster(booster, this, Rarity.MYTHIC);
			} else {
				addToBooster(booster, this, Rarity.RARE);
			}
		}

		return booster;
	}

	protected void addToBooster(List<Card> booster, ExpansionSet set, Rarity rarity) {
		Card card = set.getRandom(rarity);
		if (card != null)
			booster.add(card);
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
