package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/pdp13
 *
 * @author JayDi85
 */
public final class DuelsOfThePlaneswalkers2013Promos extends ExpansionSet {

    private static final DuelsOfThePlaneswalkers2013Promos instance = new DuelsOfThePlaneswalkers2013Promos();

    public static DuelsOfThePlaneswalkers2013Promos getInstance() {
        return instance;
    }

    private DuelsOfThePlaneswalkers2013Promos() {
        super("Duels of the Planeswalkers 2013 Promos", "PDP13", ExpansionSet.buildDate(2013, 1, 1), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Bonescythe Sliver", 10, Rarity.RARE, mage.cards.b.BonescytheSliver.class));
        cards.add(new SetCardInfo("Ogre Battledriver", 11, Rarity.RARE, mage.cards.o.OgreBattledriver.class));
        cards.add(new SetCardInfo("Scavenging Ooze", 12, Rarity.RARE, mage.cards.s.ScavengingOoze.class));
    }
}