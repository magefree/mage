package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/pdp14
 *
 * @author JayDi85
 */
public final class DuelsOfThePlaneswalkers2014Promos extends ExpansionSet {

    private static final DuelsOfThePlaneswalkers2014Promos instance = new DuelsOfThePlaneswalkers2014Promos();

    public static DuelsOfThePlaneswalkers2014Promos getInstance() {
        return instance;
    }

    private DuelsOfThePlaneswalkers2014Promos() {
        super("Duels of the Planeswalkers 2014 Promos", "PDP14", ExpansionSet.buildDate(2014, 1, 1), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Soul of Ravnica", 1, Rarity.MYTHIC, mage.cards.s.SoulOfRavnica.class));
        cards.add(new SetCardInfo("Soul of Zendikar", 1, Rarity.MYTHIC, mage.cards.s.SoulOfZendikar.class));
    }
}