package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class FinalFantasy extends ExpansionSet {

    private static final FinalFantasy instance = new FinalFantasy();

    public static FinalFantasy getInstance() {
        return instance;
    }

    private FinalFantasy() {
        super("Final Fantasy", "FIN", ExpansionSet.buildDate(2025, 6, 13), SetType.EXPANSION);
        this.blockName = "Final Fantasy"; // for sorting in GUI
        this.hasBasicLands = false; // temporary

        cards.add(new SetCardInfo("Jumbo Cactuar", 191, Rarity.RARE, mage.cards.j.JumboCactuar.class));
        cards.add(new SetCardInfo("Sazh's Chocobo", 200, Rarity.UNCOMMON, mage.cards.s.SazhsChocobo.class));
        cards.add(new SetCardInfo("Sin, Spira's Punishment", 242, Rarity.RARE, mage.cards.s.SinSpirasPunishment.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sin, Spira's Punishment", 348, Rarity.RARE, mage.cards.s.SinSpirasPunishment.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sin, Spira's Punishment", 508, Rarity.RARE, mage.cards.s.SinSpirasPunishment.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Summon: Shiva", 78, Rarity.UNCOMMON, mage.cards.s.SummonShiva.class));
        cards.add(new SetCardInfo("Tonberry", 122, Rarity.UNCOMMON, mage.cards.t.Tonberry.class));
    }
}
