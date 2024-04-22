package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/phpr
 */
public class HarperPrismBookPromos extends ExpansionSet {

    private static final HarperPrismBookPromos instance = new HarperPrismBookPromos();

    public static HarperPrismBookPromos getInstance() {
        return instance;
    }

    private HarperPrismBookPromos() {
        super("HarperPrism Book Promos", "PHPR", ExpansionSet.buildDate(1994, 9, 1), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Arena", 1, Rarity.RARE, mage.cards.a.Arena.class));
        cards.add(new SetCardInfo("Giant Badger", 4, Rarity.RARE, mage.cards.g.GiantBadger.class));
        cards.add(new SetCardInfo("Mana Crypt", 5, Rarity.RARE, mage.cards.m.ManaCrypt.class));
        cards.add(new SetCardInfo("Sewers of Estark", 2, Rarity.RARE, mage.cards.s.SewersOfEstark.class));
        cards.add(new SetCardInfo("Windseeker Centaur", 3, Rarity.RARE, mage.cards.w.WindseekerCentaur.class));
    }
}
