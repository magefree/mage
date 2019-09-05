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

        cards.add(new SetCardInfo("All That Glitters", 2, Rarity.UNCOMMON, mage.cards.a.AllThatGlitters.class));
        cards.add(new SetCardInfo("Arcane Signet", 331, Rarity.COMMON, mage.cards.a.ArcaneSignet.class));
        cards.add(new SetCardInfo("Bake into a Pie", 76, Rarity.COMMON, mage.cards.b.BakeIntoAPie.class));
        cards.add(new SetCardInfo("Belle of the Brawl", 78, Rarity.UNCOMMON, mage.cards.b.BelleOfTheBrawl.class));
        cards.add(new SetCardInfo("Chittering Witch", 319, Rarity.RARE, mage.cards.c.ChitteringWitch.class));
        cards.add(new SetCardInfo("Chulane, Teller of Tales", 326, Rarity.MYTHIC, mage.cards.c.ChulaneTellerOfTales.class));
        cards.add(new SetCardInfo("Command Tower", 333, Rarity.COMMON, mage.cards.c.CommandTower.class));
        cards.add(new SetCardInfo("Crystal Slipper", 119, Rarity.COMMON, mage.cards.c.CrystalSlipper.class));
        cards.add(new SetCardInfo("Embereth Paladin", 121, Rarity.COMMON, mage.cards.e.EmberethPaladin.class));
        cards.add(new SetCardInfo("Embereth Skyblazer", 321, Rarity.RARE, mage.cards.e.EmberethSkyblazer.class));
        cards.add(new SetCardInfo("Eye Collector", 86, Rarity.COMMON, mage.cards.e.EyeCollector.class));
        cards.add(new SetCardInfo("Fireborn Knight", 210, Rarity.UNCOMMON, mage.cards.f.FirebornKnight.class));
        cards.add(new SetCardInfo("Garruk, Cursed Huntsman", 270, Rarity.MYTHIC, mage.cards.g.GarrukCursedHuntsman.class));
        cards.add(new SetCardInfo("Gilded Goose", 160, Rarity.RARE, mage.cards.g.GildedGoose.class));
        cards.add(new SetCardInfo("Golden Egg", 220, Rarity.COMMON, mage.cards.g.GoldenEgg.class));
        cards.add(new SetCardInfo("Heraldic Banner", 222, Rarity.UNCOMMON, mage.cards.h.HeraldicBanner.class));
        cards.add(new SetCardInfo("Inspiring Veteran", 194, Rarity.UNCOMMON, mage.cards.i.InspiringVeteran.class));
        cards.add(new SetCardInfo("Maraleaf Pixie", 196, Rarity.UNCOMMON, mage.cards.m.MaraleafPixie.class));
        cards.add(new SetCardInfo("Rankle, Master of Pranks", 101, Rarity.MYTHIC, mage.cards.r.RankleMasterOfPranks.class));
        cards.add(new SetCardInfo("Run Away Together", 62, Rarity.COMMON, mage.cards.r.RunAwayTogether.class));
        cards.add(new SetCardInfo("Shining Armor", 29, Rarity.COMMON, mage.cards.s.ShiningArmor.class));
        cards.add(new SetCardInfo("Silverflame Ritual", 30, Rarity.COMMON, mage.cards.s.SilverflameRitual.class));
        cards.add(new SetCardInfo("Slaying Fire", 143, Rarity.UNCOMMON, mage.cards.s.SlayingFire.class));
        cards.add(new SetCardInfo("Steelbane Hydra", 322, Rarity.RARE, mage.cards.s.SteelbaneHydra.class));
        cards.add(new SetCardInfo("Taste of Death", 320, Rarity.RARE, mage.cards.t.TasteOfDeath.class));
        cards.add(new SetCardInfo("Tome Raider", 68, Rarity.COMMON, mage.cards.t.TomeRaider.class));
        cards.add(new SetCardInfo("Wishful Merfolk", 73, Rarity.COMMON, mage.cards.w.WishfulMerfolk.class));
        cards.add(new SetCardInfo("Witching Well", 74, Rarity.COMMON, mage.cards.w.WitchingWell.class));
    }
}
