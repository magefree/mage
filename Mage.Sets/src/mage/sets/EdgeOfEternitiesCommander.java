package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class EdgeOfEternitiesCommander extends ExpansionSet {

    private static final EdgeOfEternitiesCommander instance = new EdgeOfEternitiesCommander();

    public static EdgeOfEternitiesCommander getInstance() {
        return instance;
    }

    private EdgeOfEternitiesCommander() {
        super("Edge of Eternities Commander", "EOC", ExpansionSet.buildDate(2025, 8, 1), SetType.SUPPLEMENTAL);
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Hearthhull, the Worldseed", 1, Rarity.MYTHIC, mage.cards.h.HearthhullTheWorldseed.class));
        cards.add(new SetCardInfo("Inspirit, Flagship Vessel", 2, Rarity.MYTHIC, mage.cards.i.InspiritFlagshipVessel.class));
        cards.add(new SetCardInfo("Kilo, Apogee Mind", 3, Rarity.MYTHIC, mage.cards.k.KiloApogeeMind.class));
        cards.add(new SetCardInfo("Szarel, Genesis Shepherd", 4, Rarity.MYTHIC, mage.cards.s.SzarelGenesisShepherd.class));
        cards.add(new SetCardInfo("Baloth Prime", 13, Rarity.RARE, mage.cards.b.BalothPrime.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Baloth Prime", 33, Rarity.RARE, mage.cards.b.BalothPrime.class, NON_FULL_USE_VARIOUS));
    }
}
