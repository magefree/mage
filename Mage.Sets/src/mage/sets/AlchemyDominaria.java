package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author karapuzz14
 */
public final class AlchemyDominaria extends ExpansionSet {

    private static final AlchemyDominaria instance = new AlchemyDominaria();

    public static AlchemyDominaria getInstance() {
        return instance;
    }

    private AlchemyDominaria() {
        super("Alchemy: Dominaria", "YDMU", ExpansionSet.buildDate(2022, 10, 5), SetType.MAGIC_ARENA);
        this.blockName = "Alchemy";
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Ancestral Recall", 32, Rarity.MYTHIC, mage.cards.a.AncestralRecall.class));
        cards.add(new SetCardInfo("Black Lotus", 35, Rarity.MYTHIC, mage.cards.b.BlackLotus.class));
        cards.add(new SetCardInfo("Bloodsprout Talisman", 21, Rarity.RARE, mage.cards.b.BloodsproutTalisman.class));
        cards.add(new SetCardInfo("Marwyn's Kindred", 16, Rarity.MYTHIC, mage.cards.m.MarwynsKindred.class));
        cards.add(new SetCardInfo("Mox Emerald", 36, Rarity.MYTHIC, mage.cards.m.MoxEmerald.class));
        cards.add(new SetCardInfo("Mox Jet", 37, Rarity.MYTHIC, mage.cards.m.MoxJet.class));
        cards.add(new SetCardInfo("Mox Pearl", 38, Rarity.MYTHIC, mage.cards.m.MoxPearl.class));
        cards.add(new SetCardInfo("Mox Ruby", 39, Rarity.MYTHIC, mage.cards.m.MoxRuby.class));
        cards.add(new SetCardInfo("Mox Sapphire", 40, Rarity.MYTHIC, mage.cards.m.MoxSapphire.class));
        cards.add(new SetCardInfo("Oracle of the Alpha", 4, Rarity.MYTHIC, mage.cards.o.OracleOfTheAlpha.class));
        cards.add(new SetCardInfo("Pull of the Mist Moon", 3, Rarity.UNCOMMON, mage.cards.p.PullOfTheMistMoon.class));
        cards.add(new SetCardInfo("Slimefoot, Thallid Transplant", 26, Rarity.RARE, mage.cards.s.SlimefootThallidTransplant.class));
        cards.add(new SetCardInfo("Time Walk", 33, Rarity.MYTHIC, mage.cards.t.TimeWalk.class));
        cards.add(new SetCardInfo("Timetwister", 34, Rarity.MYTHIC, mage.cards.t.Timetwister.class));
        cards.add(new SetCardInfo("Vinesoul Spider", 18, Rarity.UNCOMMON, mage.cards.v.VinesoulSpider.class));
        cards.add(new SetCardInfo("Wandering Treefolk", 19, Rarity.UNCOMMON, mage.cards.w.WanderingTreefolk.class));



    }
}
