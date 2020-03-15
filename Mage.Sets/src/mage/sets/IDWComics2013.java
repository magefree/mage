package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/pi13
 */
public class IDWComics2013 extends ExpansionSet {

    private static final IDWComics2013 instance = new IDWComics2013();

    public static IDWComics2013 getInstance() {
        return instance;
    }

    private IDWComics2013() {
        super("IDW Comics 2013", "PI13", ExpansionSet.buildDate(2013, 1, 1), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Corrupt", 12, Rarity.RARE, mage.cards.c.Corrupt.class));
        cards.add(new SetCardInfo("Gaze of Granite", 14, Rarity.RARE, mage.cards.g.GazeOfGranite.class));
        cards.add(new SetCardInfo("High Tide", 13, Rarity.RARE, mage.cards.h.HighTide.class));
        cards.add(new SetCardInfo("Ogre Arsonist", 11, Rarity.RARE, mage.cards.o.OgreArsonist.class));
        cards.add(new SetCardInfo("Voidmage Husher", 10, Rarity.RARE, mage.cards.v.VoidmageHusher.class));
     }
}
