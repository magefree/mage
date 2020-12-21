package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/pdp11
 *
 * @author JayDi85
 */
public final class DuelsOfThePlaneswalkers2012Promos extends ExpansionSet {

    private static final DuelsOfThePlaneswalkers2012Promos instance = new DuelsOfThePlaneswalkers2012Promos();

    public static DuelsOfThePlaneswalkers2012Promos getInstance() {
        return instance;
    }

    private DuelsOfThePlaneswalkers2012Promos() {
        super("Duels of the Planeswalkers 2012 Promos", "PDP12", ExpansionSet.buildDate(2011, 1, 1), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Frost Titan", 1, Rarity.MYTHIC, mage.cards.f.FrostTitan.class));
        cards.add(new SetCardInfo("Grave Titan", 2, Rarity.MYTHIC, mage.cards.g.GraveTitan.class));
        cards.add(new SetCardInfo("Inferno Titan", 3, Rarity.MYTHIC, mage.cards.i.InfernoTitan.class));
    }
}