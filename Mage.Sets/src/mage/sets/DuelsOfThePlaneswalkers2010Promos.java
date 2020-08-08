package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/pdp10
 *
 * @author JayDi85
 */
public final class DuelsOfThePlaneswalkers2010Promos extends ExpansionSet {

    private static final DuelsOfThePlaneswalkers2010Promos instance = new DuelsOfThePlaneswalkers2010Promos();

    public static DuelsOfThePlaneswalkers2010Promos getInstance() {
        return instance;
    }

    private DuelsOfThePlaneswalkers2010Promos() {
        super("Duels of the Planeswalkers 2010 Promos", "PDP10", ExpansionSet.buildDate(2010, 1, 1), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Liliana Vess", 1, Rarity.MYTHIC, mage.cards.l.LilianaVess.class));
        cards.add(new SetCardInfo("Nissa Revane", 2, Rarity.MYTHIC, mage.cards.n.NissaRevane.class));
    }
}