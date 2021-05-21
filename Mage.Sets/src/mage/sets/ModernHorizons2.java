package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class ModernHorizons2 extends ExpansionSet {

    private static final ModernHorizons2 instance = new ModernHorizons2();

    public static ModernHorizons2 getInstance() {
        return instance;
    }

    private ModernHorizons2() {
        super("Modern Horizons 2", "MH2", ExpansionSet.buildDate(2021, 6, 11), SetType.SUPPLEMENTAL_MODERN_LEGAL);
        this.blockName = "Modern Horizons 2";
        this.hasBasicLands = false; // temporary
        this.hasBoosters = true;
        this.numBoosterLands = 1;
        this.numBoosterCommon = 11;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 8;
        this.maxCardNumberInBooster = 303;

        cards.add(new SetCardInfo("Arid Mesa", 244, Rarity.RARE, mage.cards.a.AridMesa.class));
        cards.add(new SetCardInfo("Brainstone", 223, Rarity.UNCOMMON, mage.cards.b.Brainstone.class));
        cards.add(new SetCardInfo("Cabal Coffers", 301, Rarity.MYTHIC, mage.cards.c.CabalCoffers.class));
        cards.add(new SetCardInfo("Counterspell", 267, Rarity.UNCOMMON, mage.cards.c.Counterspell.class));
        cards.add(new SetCardInfo("Diamond Lion", 225, Rarity.RARE, mage.cards.d.DiamondLion.class));
        cards.add(new SetCardInfo("Flametongue Yearling", 125, Rarity.UNCOMMON, mage.cards.f.FlametongueYearling.class));
        cards.add(new SetCardInfo("Marsh Flats", 248, Rarity.RARE, mage.cards.m.MarshFlats.class));
        cards.add(new SetCardInfo("Misty Rainforest", 250, Rarity.RARE, mage.cards.m.MistyRainforest.class));
        cards.add(new SetCardInfo("Rishadan Dockhand", 59, Rarity.RARE, mage.cards.r.RishadanDockhand.class));
        cards.add(new SetCardInfo("Sanctum Prelate", 491, Rarity.MYTHIC, mage.cards.s.SanctumPrelate.class));
        cards.add(new SetCardInfo("Scalding Tarn", 254, Rarity.RARE, mage.cards.s.ScaldingTarn.class));
        cards.add(new SetCardInfo("Timeless Dragon", 35, Rarity.RARE, mage.cards.t.TimelessDragon.class));
        cards.add(new SetCardInfo("Urza's Saga", 259, Rarity.RARE, mage.cards.u.UrzasSaga.class));
        cards.add(new SetCardInfo("Verdant Catacombs", 260, Rarity.RARE, mage.cards.v.VerdantCatacombs.class));
    }
}
