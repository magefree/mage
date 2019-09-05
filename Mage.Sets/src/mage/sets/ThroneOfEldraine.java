package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class ThroneOfEldraine extends ExpansionSet {

    private static final ThroneOfEldraine instance = new ThroneOfEldraine();

    public static ThroneOfEldraine getInstance() {
        return instance;
    }

    private ThroneOfEldraine() {
        super("Throne of Eldraine", "ELD", ExpansionSet.buildDate(2019, 10, 4), SetType.EXPANSION);
        this.blockName = "Throne of Eldraine";
        this.hasBoosters = true;
        this.hasBasicLands = false;// false until basics officially spoiled
        this.numBoosterLands = 1;
        this.numBoosterCommon = 10;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 8;
        this.maxCardNumberInBooster = 269; // unconfirmed for now

        cards.add(new SetCardInfo("Arcane Signet", 331, Rarity.COMMON, mage.cards.a.ArcaneSignet.class));
        cards.add(new SetCardInfo("Bake into a Pie", 76, Rarity.COMMON, mage.cards.b.BakeIntoAPie.class));
        cards.add(new SetCardInfo("Chittering Witch", 319, Rarity.RARE, mage.cards.c.ChitteringWitch.class));
        cards.add(new SetCardInfo("Chulane, Teller of Tales", 326, Rarity.MYTHIC, mage.cards.c.ChulaneTellerOfTales.class));
        cards.add(new SetCardInfo("Crystal Slipper", 119, Rarity.COMMON, mage.cards.c.CrystalSlipper.class));
        cards.add(new SetCardInfo("Fireborn Knight", 210, Rarity.UNCOMMON, mage.cards.f.FirebornKnight.class));
        cards.add(new SetCardInfo("Gilded Goose", 160, Rarity.RARE, mage.cards.g.GildedGoose.class));
        cards.add(new SetCardInfo("Golden Egg", 220, Rarity.COMMON, mage.cards.g.GoldenEgg.class));
        cards.add(new SetCardInfo("Heraldic Banner", 222, Rarity.UNCOMMON, mage.cards.h.HeraldicBanner.class));
        cards.add(new SetCardInfo("Maraleaf Pixie", 196, Rarity.UNCOMMON, mage.cards.m.MaraleafPixie.class));
        cards.add(new SetCardInfo("Rankle, Master of Pranks", 356, Rarity.MYTHIC, mage.cards.r.RankleMasterOfPranks.class));
        cards.add(new SetCardInfo("Run Away Together", 62, Rarity.COMMON, mage.cards.r.RunAwayTogether.class));
        cards.add(new SetCardInfo("Slaying Fire", 143, Rarity.COMMON, mage.cards.s.SlayingFire.class));
        cards.add(new SetCardInfo("Tome Raider", 68, Rarity.COMMON, mage.cards.t.TomeRaider.class));
        cards.add(new SetCardInfo("Wishful Merfolk", 73, Rarity.COMMON, mage.cards.w.WishfulMerfolk.class));
        cards.add(new SetCardInfo("Witching Well", 74, Rarity.COMMON, mage.cards.w.WitchingWell.class));
    }
}
