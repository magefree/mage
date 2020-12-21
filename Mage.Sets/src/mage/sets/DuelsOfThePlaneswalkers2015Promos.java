package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/pdp14
 *
 * @author JayDi85
 */
public final class DuelsOfThePlaneswalkers2015Promos extends ExpansionSet {

    private static final DuelsOfThePlaneswalkers2015Promos instance = new DuelsOfThePlaneswalkers2015Promos();

    public static DuelsOfThePlaneswalkers2015Promos getInstance() {
        return instance;
    }

    private DuelsOfThePlaneswalkers2015Promos() {
        super("Duels of the Planeswalkers 2015 Promos", "PDP15", ExpansionSet.buildDate(2014, 1, 1), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Soul of Ravnica", 1, Rarity.MYTHIC, mage.cards.s.SoulOfRavnica.class));
        cards.add(new SetCardInfo("Soul of Zendikar", 2, Rarity.MYTHIC, mage.cards.s.SoulOfZendikar.class));
    }
}