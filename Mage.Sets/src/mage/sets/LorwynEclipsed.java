package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class LorwynEclipsed extends ExpansionSet {

    private static final LorwynEclipsed instance = new LorwynEclipsed();

    public static LorwynEclipsed getInstance() {
        return instance;
    }

    private LorwynEclipsed() {
        super("Lorwyn Eclipsed", "TLA", ExpansionSet.buildDate(2026, 1, 23), SetType.EXPANSION);
        this.blockName = "Lorwyn Eclipsed"; // for sorting in GUI
        this.hasBasicLands = true;

        cards.add(new SetCardInfo("Bitterbloom Bearer", 310, Rarity.MYTHIC, mage.cards.b.BitterbloomBearer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Bitterbloom Bearer", 352, Rarity.MYTHIC, mage.cards.b.BitterbloomBearer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Bitterbloom Bearer", 88, Rarity.MYTHIC, mage.cards.b.BitterbloomBearer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Blood Crypt", 262, Rarity.RARE, mage.cards.b.BloodCrypt.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Blood Crypt", 349, Rarity.RARE, mage.cards.b.BloodCrypt.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hallowed Fountain", 265, Rarity.RARE, mage.cards.h.HallowedFountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hallowed Fountain", 347, Rarity.RARE, mage.cards.h.HallowedFountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Overgrown Tomb", 266, Rarity.RARE, mage.cards.o.OvergrownTomb.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Overgrown Tomb", 350, Rarity.RARE, mage.cards.o.OvergrownTomb.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Steam Vents", 267, Rarity.RARE, mage.cards.s.SteamVents.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Steam Vents", 348, Rarity.RARE, mage.cards.s.SteamVents.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Temple Garden", 268, Rarity.RARE, mage.cards.t.TempleGarden.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Temple Garden", 351, Rarity.RARE, mage.cards.t.TempleGarden.class, NON_FULL_USE_VARIOUS));
    }
}
