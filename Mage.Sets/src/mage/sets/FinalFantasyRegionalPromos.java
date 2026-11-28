package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/rfin
 *
 * @author DarioBarrabinoDev
 */
public final class FinalFantasyRegionalPromos extends ExpansionSet {

    private static final FinalFantasyRegionalPromos instance = new FinalFantasyRegionalPromos();

    public static FinalFantasyRegionalPromos getInstance() {
        return instance;
    }

    private FinalFantasyRegionalPromos() {
        super("Final Fantasy Regional Promos", "RFIN", ExpansionSet.buildDate(2025, 6, 13), SetType.PROMOTIONAL);
        hasBasicLands = false;

        cards.add(new SetCardInfo("Arcane Denial", "J2", Rarity.RARE, mage.cards.a.ArcaneDenial.class));
        cards.add(new SetCardInfo("Force of Negation", "J1", Rarity.RARE, mage.cards.f.ForceOfNegation.class));
    }
}
