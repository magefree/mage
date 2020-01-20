package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/f16
 */
public class FridayNightMagic2016 extends ExpansionSet {

    private static final FridayNightMagic2016 instance = new FridayNightMagic2016();

    public static FridayNightMagic2016 getInstance() {
        return instance;
    }

    private FridayNightMagic2016() {
        super("Friday Night Magic 2016", "F16", ExpansionSet.buildDate(2016, 1, 1), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Blighted Fen", 4, Rarity.RARE, mage.cards.b.BlightedFen.class));
        cards.add(new SetCardInfo("Call the Bloodline", 12, Rarity.RARE, mage.cards.c.CallTheBloodline.class));
        cards.add(new SetCardInfo("Clash of Wills", 2, Rarity.RARE, mage.cards.c.ClashOfWills.class));
        cards.add(new SetCardInfo("Crumbling Vestige", 8, Rarity.RARE, mage.cards.c.CrumblingVestige.class));
        cards.add(new SetCardInfo("Fiery Temper", 11, Rarity.RARE, mage.cards.f.FieryTemper.class));
        cards.add(new SetCardInfo("Flaying Tendrils", 9, Rarity.RARE, mage.cards.f.FlayingTendrils.class));
        cards.add(new SetCardInfo("Goblin Warchief", 5, Rarity.RARE, mage.cards.g.GoblinWarchief.class));
        cards.add(new SetCardInfo("Nissa's Pilgrimage", 1, Rarity.RARE, mage.cards.n.NissasPilgrimage.class));
        cards.add(new SetCardInfo("Rise from the Tides", 10, Rarity.RARE, mage.cards.r.RiseFromTheTides.class));
        cards.add(new SetCardInfo("Smash to Smithereens", 3, Rarity.RARE, mage.cards.s.SmashToSmithereens.class));
        cards.add(new SetCardInfo("Spatial Contortion", 7, Rarity.RARE, mage.cards.s.SpatialContortion.class));
        cards.add(new SetCardInfo("Sylvan Scrying", 6, Rarity.RARE, mage.cards.s.SylvanScrying.class));
     }
}
