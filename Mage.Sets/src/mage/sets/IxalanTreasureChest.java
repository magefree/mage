package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/pxtc
 */
public class IxalanTreasureChest extends ExpansionSet {

    private static final IxalanTreasureChest instance = new IxalanTreasureChest();

    public static IxalanTreasureChest getInstance() {
        return instance;
    }

    private IxalanTreasureChest() {
        super("Ixalan Treasure Chest", "PXTC", ExpansionSet.buildDate(2017, 11, 24), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Arguel's Blood Fast", 90, Rarity.RARE, mage.cards.a.ArguelsBloodFast.class));
        cards.add(new SetCardInfo("Conqueror's Galleon", 234, Rarity.RARE, mage.cards.c.ConquerorsGalleon.class));
        cards.add(new SetCardInfo("Dowsing Dagger", 235, Rarity.RARE, mage.cards.d.DowsingDagger.class));
        cards.add(new SetCardInfo("Growing Rites of Itlimoc", 191, Rarity.RARE, mage.cards.g.GrowingRitesOfItlimoc.class));
        cards.add(new SetCardInfo("Legion's Landing", 22, Rarity.RARE, mage.cards.l.LegionsLanding.class));
        cards.add(new SetCardInfo("Primal Amulet", 243, Rarity.RARE, mage.cards.p.PrimalAmulet.class));
        cards.add(new SetCardInfo("Search for Azcanta", 74, Rarity.RARE, mage.cards.s.SearchForAzcanta.class));
        cards.add(new SetCardInfo("Thaumatic Compass", 249, Rarity.RARE, mage.cards.t.ThaumaticCompass.class));
        cards.add(new SetCardInfo("Treasure Map", 250, Rarity.RARE, mage.cards.t.TreasureMap.class));
        cards.add(new SetCardInfo("Vance's Blasting Cannons", 173, Rarity.RARE, mage.cards.v.VancesBlastingCannons.class));
     }
}
