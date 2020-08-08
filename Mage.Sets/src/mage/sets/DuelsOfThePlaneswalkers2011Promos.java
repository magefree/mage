package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/pdp11
 *
 * @author JayDi85
 */
public final class DuelsOfThePlaneswalkers2011Promos extends ExpansionSet {

    private static final DuelsOfThePlaneswalkers2011Promos instance = new DuelsOfThePlaneswalkers2011Promos();

    public static DuelsOfThePlaneswalkers2011Promos getInstance() {
        return instance;
    }

    private DuelsOfThePlaneswalkers2011Promos() {
        super("Duels of the Planeswalkers 2011 Promos", "PDP11", ExpansionSet.buildDate(2011, 1, 1), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Frost Titan", 1, Rarity.MYTHIC, mage.cards.f.FrostTitan.class));
        cards.add(new SetCardInfo("Grave Titan", 2, Rarity.MYTHIC, mage.cards.g.GraveTitan.class));
        cards.add(new SetCardInfo("Inferno Titan", 3, Rarity.MYTHIC, mage.cards.i.InfernoTitan.class));
    }
}