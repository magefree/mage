package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/pdtp
 *
 * @author JayDi85
 */
public final class DuelsOfThePlaneswalkers2009Promos extends ExpansionSet {

    private static final DuelsOfThePlaneswalkers2009Promos instance = new DuelsOfThePlaneswalkers2009Promos();

    public static DuelsOfThePlaneswalkers2009Promos getInstance() {
        return instance;
    }

    private DuelsOfThePlaneswalkers2009Promos() {
        super("Duels of the Planeswalkers 2009 Promos", "PDTP", ExpansionSet.buildDate(2009, 1, 1), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Garruk Wildspeaker", 1, Rarity.MYTHIC, mage.cards.g.GarrukWildspeaker.class));
    }
}