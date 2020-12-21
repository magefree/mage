package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/pdp13
 *
 * @author JayDi85
 */
public final class DuelsOfThePlaneswalkers2014Promos extends ExpansionSet {

    private static final DuelsOfThePlaneswalkers2014Promos instance = new DuelsOfThePlaneswalkers2014Promos();

    public static DuelsOfThePlaneswalkers2014Promos getInstance() {
        return instance;
    }

    private DuelsOfThePlaneswalkers2014Promos() {
        super("Duels of the Planeswalkers 2014 Promos", "PDP14", ExpansionSet.buildDate(2013, 1, 1), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Bonescythe Sliver", 1, Rarity.RARE, mage.cards.b.BonescytheSliver.class));
        cards.add(new SetCardInfo("Ogre Battledriver", 2, Rarity.RARE, mage.cards.o.OgreBattledriver.class));
        cards.add(new SetCardInfo("Scavenging Ooze", 3, Rarity.RARE, mage.cards.s.ScavengingOoze.class));
    }
}