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

import mage.Constants.Rarity;
import mage.Constants.SetType;
import org.apache.log4j.Logger;

import java.io.*;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLDecoder;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

/**
 * @author BetaSteward_at_googlemail.com
 */
public abstract class ExpansionSet implements Serializable {

    private final static Logger logger = Logger.getLogger(ExpansionSet.class);

    protected static Random rnd = new Random();

    protected String name;
    protected String code;
    protected String symbolCode;
    protected Date releaseDate;
    protected ExpansionSet parentSet;
    protected List<Card> cards;
    protected SetType setType;
    protected Map<Rarity, List<Card>> rarities;

    protected String blockName;
    protected boolean hasBoosters = false;
    protected int numBoosterLands;
    protected int numBoosterCommon;
    protected int numBoosterUncommon;
    protected int numBoosterRare;
    protected int numBoosterDoubleFaced;
    protected int ratioBoosterMythic;

    protected String packageName;

    public ExpansionSet(String name, String code, String symbolCode, String packageName, Date releaseDate, SetType setType) {
        this.name = name;
        this.code = code;
        this.symbolCode = symbolCode;
        this.releaseDate = releaseDate;
        this.setType = setType;
        this.packageName = packageName;
        this.cards = getCardClassesForPackage(packageName);
        this.rarities = getCardsByRarity();
    }

    public List<Card> getCards() {
        /*if (cards == null) {
            synchronized (this) {
                if (cards == null) {
                    this.cards = getCardClassesForPackage(packageName);
                    this.rarities = getCardsByRarity();
                }
            }
        }*/
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

    private Card createCard(Class clazz) {
        try {
            Constructor<?> con = clazz.getConstructor(new Class[]{UUID.class});
            return (Card) con.newInstance(new Object[]{null});
        } catch (Exception ex) {
            logger.fatal("Error creating card:" + clazz.getName(), ex);
            return null;
        }
    }

    @Override
    public String toString() {
        return name;
    }

    public Card findCard(String name) {
        for (Card card : getCards()) {
            if (name.equalsIgnoreCase(card.getName())) {
                Card newCard = card.copy();
                newCard.assignNewId();
                return newCard;
            }
        }
        return null;
    }

    public Card findCard(int cardNum) {
        for (Card card : getCards()) {
            if (cardNum == card.getCardNumber()) {
                Card newCard = card.copy();
                newCard.assignNewId();
                return newCard;
            }
        }
        return null;
    }

    public Card findCard(String name, boolean random) {
        List<Card> foundCards = new ArrayList<Card>();
        for (Card card : getCards()) {
            if (name.equalsIgnoreCase(card.getName())) {
                foundCards.add(card);
            }
        }
        if (foundCards.size() > 0) {
            Card newCard = foundCards.get(rnd.nextInt(foundCards.size())).copy();
            newCard.assignNewId();
            return newCard;
        }
        return null;
    }

    public String findCardName(int cardNum) {
        for (Card card : getCards()) {
            if (card.getCardNumber() == cardNum)
                return card.getClass().getCanonicalName();
        }
        return null;
    }

    private List<Card> getCardClassesForPackage(String packageName) {
        ClassLoader classLoader = this.getClass().getClassLoader();
        assert classLoader != null;
        String path = packageName.replace(".", "/");
        Enumeration<URL> resources = null;
        try {
            resources = classLoader.getResources(path);
        } catch (IOException e) {
            logger.fatal("Error loading resource - " + path, e);
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
                logger.fatal("Error decoding director - " + resource.getFile(), e);
            }
        }
        List<Class> classes = new ArrayList<Class>();
        if (isLoadingFromJar) {
            if (jarPath.contains("!")) {
                jarPath = jarPath.substring(0, jarPath.lastIndexOf('!'));
            }
            String filePathElement = "file:";
            if (jarPath.startsWith(filePathElement)) {
                try {
                    jarPath = URLDecoder.decode(jarPath.substring(jarPath.indexOf(filePathElement) + filePathElement.length()), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    logger.fatal("Error decoding file - " + jarPath, e);
                }
            }
            try {
                classes.addAll(findClassesInJar(new File(jarPath), path));
            } catch (ClassNotFoundException e) {
                logger.fatal("Error loading classes - " + jarPath, e);
            }
        } else { // faster but doesn't work for jars
            for (File directory : dirs) {
                try {
                    classes.addAll(findClasses(directory, packageName));
                } catch (ClassNotFoundException e) {
                    logger.fatal("Error loading classes - " + jarPath, e);
                }
            }
        }
        List<Card> newCards = new ArrayList<Card>();
        for (Class clazz : classes) {
            if (clazz.getPackage().getName().equals(packageName)) {
                Card card = createCard(clazz);
                if (card.isNightCard()) {
                    // skip second face of double-faced cards
                    continue;
                }
                newCards.add(card);
            }
        }
        return newCards;
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
            logger.fatal("Error loading classes - " + file, e);
        }

        return classes;
    }

    private Map<Rarity, List<Card>> getCardsByRarity() {
        Map<Rarity, List<Card>> cardsByRarity = new HashMap<Rarity, List<Card>>();

        for (Card card : getCards()) {
            if (!cardsByRarity.containsKey(card.getRarity()))
                cardsByRarity.put(card.getRarity(), new ArrayList<Card>());
            cardsByRarity.get(card.getRarity()).add(card);
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
        for (int i = 0; i < numBoosterDoubleFaced; i++) {
            addToBoosterDoubleFaced(booster, this);
        }

        return booster;
    }

    protected void addToBooster(List<Card> booster, ExpansionSet set, Rarity rarity) {
        Card card = set.getRandom(rarity);
        if (card != null) {
            card = checkNotDoubleFaced(card, set, rarity);
            card = checkNotDuplicate(card, booster, set, rarity);
            Card newCard = card.copy();
            newCard.assignNewId();
            booster.add(newCard);
        }
    }

    protected void addToBoosterDoubleFaced(List<Card> booster, ExpansionSet set) {
        Card card = set.getRandomDoubleFaced();
        if (card != null) {
            Card newCard = card.copy();
            newCard.assignNewId();
            booster.add(newCard);
        }
    }

    /**
     * Checks that card doesn't already exist in the booster. If so, tries to generate new one several times.
     *
     * @param cardToCheck
     * @param booster
     * @param set
     * @param rarity
     * @return
     */
    private Card checkNotDuplicate(Card cardToCheck, List<Card> booster, ExpansionSet set, Rarity rarity) {
        Card card = cardToCheck;
        boolean duplicate = true;
        int retryCount = 5;
        while (duplicate && retryCount > 0) {
            if (!rarity.equals(Rarity.LAND)) {
                // check for duplicates
                if (hasCardByName(booster, card.getName())) {
                    card = set.getRandom(rarity);
                } else {
                    duplicate = false; // no such card yet
                }
            } else {
                duplicate = false;
            }
            retryCount--;
        }
        return card;
    }

    /**
     * Checks that card is not double faced. If so, tries to generate new one several times.
     *
     * @param cardToCheck
     * @param set
     * @param rarity
     * @return
     */
    private Card checkNotDoubleFaced(Card cardToCheck, ExpansionSet set, Rarity rarity) {
        int retryCount = 100;
        Card card = cardToCheck;
        while (card.canTransform()) {
            card = set.getRandom(rarity);
            retryCount--;
            if (retryCount <= 0) {
                logger.warn("Couldn't find non double faced card");
                break;
            }
        }
        return card;
    }

    protected boolean hasCardByName(List<Card> booster, String name) {
        for (Card card : booster) {
            if (card.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    protected Card getRandom(Rarity rarity) {
        if (!rarities.containsKey(rarity))
            return null;
        int size = rarities.get(rarity).size();
        if (size > 0) {
            return rarities.get(rarity).get(rnd.nextInt(size)).copy();
        }
        return null;
    }

    protected Card getRandomDoubleFaced() {
        int size = getCards().size();
        if (size > 0) {
            Card card = cards.get(rnd.nextInt(size));
            int retryCount = 1000;
            while (!card.canTransform()) {
                card = cards.get(rnd.nextInt(size));
                retryCount--;
                if (retryCount <= 0) {
                    logger.warn("Couldn't find double-faced card.");
                    break;
                }
            }
            return card;
        }
        return null;
    }

    public Map<Rarity, List<Card>> getRarities() {
        /*if (rarities == null) {
            this.rarities = getCardsByRarity();
        }*/
        return rarities;
    }
}
