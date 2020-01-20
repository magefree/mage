package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/fnm
 */
public class FridayNightMagic2000 extends ExpansionSet {

    private static final FridayNightMagic2000 instance = new FridayNightMagic2000();

    public static FridayNightMagic2000 getInstance() {
        return instance;
    }

    private FridayNightMagic2000() {
        super("Friday Night Magic 2000", "FNM", ExpansionSet.buildDate(2000, 1, 1), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Giant Growth", 8, Rarity.RARE, mage.cards.g.GiantGrowth.class));
        cards.add(new SetCardInfo("Llanowar Elves", 11, Rarity.RARE, mage.cards.l.LlanowarElves.class));
        cards.add(new SetCardInfo("Longbow Archer", 3, Rarity.RARE, mage.cards.l.LongbowArcher.class));
        cards.add(new SetCardInfo("Mind Warp", 5, Rarity.RARE, mage.cards.m.MindWarp.class));
        cards.add(new SetCardInfo("Prodigal Sorcerer", 9, Rarity.RARE, mage.cards.p.ProdigalSorcerer.class));
        cards.add(new SetCardInfo("River Boa", 1, Rarity.RARE, mage.cards.r.RiverBoa.class));
        cards.add(new SetCardInfo("Shock", 6, Rarity.RARE, mage.cards.s.Shock.class));
        cards.add(new SetCardInfo("Staunch Defenders", 12, Rarity.RARE, mage.cards.s.StaunchDefenders.class));
        cards.add(new SetCardInfo("Stone Rain", 10, Rarity.RARE, mage.cards.s.StoneRain.class));
        cards.add(new SetCardInfo("Terror", 2, Rarity.RARE, mage.cards.t.Terror.class));
        cards.add(new SetCardInfo("Volcanic Geyser", 4, Rarity.RARE, mage.cards.v.VolcanicGeyser.class));
     }
}
