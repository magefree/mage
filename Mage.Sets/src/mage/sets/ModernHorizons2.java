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
        this.hasBasicLands = true;
        this.hasBoosters = true;
        this.numBoosterLands = 1;
        this.numBoosterCommon = 11;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 8;
        this.maxCardNumberInBooster = 303;

        cards.add(new SetCardInfo("Arid Mesa", 244, Rarity.RARE, mage.cards.a.AridMesa.class));
        cards.add(new SetCardInfo("Bottle Golems", 222, Rarity.COMMON, mage.cards.b.BottleGolems.class));
        cards.add(new SetCardInfo("Brainstone", 223, Rarity.UNCOMMON, mage.cards.b.Brainstone.class));
        cards.add(new SetCardInfo("Break Ties", 8, Rarity.COMMON, mage.cards.b.BreakTies.class));
        cards.add(new SetCardInfo("Cabal Coffers", 301, Rarity.MYTHIC, mage.cards.c.CabalCoffers.class));
        cards.add(new SetCardInfo("Chance Encounter", 277, Rarity.RARE, mage.cards.c.ChanceEncounter.class));
        cards.add(new SetCardInfo("Counterspell", 267, Rarity.UNCOMMON, mage.cards.c.Counterspell.class));
        cards.add(new SetCardInfo("Dakkon, Shadow Slayer", 192, Rarity.MYTHIC, mage.cards.d.DakkonShadowSlayer.class));
        cards.add(new SetCardInfo("Diamond Lion", 225, Rarity.RARE, mage.cards.d.DiamondLion.class));
        cards.add(new SetCardInfo("Extruder", 296, Rarity.UNCOMMON, mage.cards.e.Extruder.class));
        cards.add(new SetCardInfo("Flametongue Yearling", 125, Rarity.UNCOMMON, mage.cards.f.FlametongueYearling.class));
        cards.add(new SetCardInfo("Forest", 489, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Fractured Sanity", 44, Rarity.RARE, mage.cards.f.FracturedSanity.class));
        cards.add(new SetCardInfo("Grief", 87, Rarity.MYTHIC, mage.cards.g.Grief.class));
        cards.add(new SetCardInfo("Island", 483, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kitchen Imp", 89, Rarity.COMMON, mage.cards.k.KitchenImp.class));
        cards.add(new SetCardInfo("Late to Dinner", 19, Rarity.COMMON, mage.cards.l.LateToDinner.class));
        cards.add(new SetCardInfo("Lucid Dreams", 50, Rarity.UNCOMMON, mage.cards.l.LucidDreams.class));
        cards.add(new SetCardInfo("Marsh Flats", 248, Rarity.RARE, mage.cards.m.MarshFlats.class));
        cards.add(new SetCardInfo("Misty Rainforest", 250, Rarity.RARE, mage.cards.m.MistyRainforest.class));
        cards.add(new SetCardInfo("Mogg Salvage", 282, Rarity.UNCOMMON, mage.cards.m.MoggSalvage.class));
        cards.add(new SetCardInfo("Mountain", 487, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Patriarch's Bidding", 275, Rarity.RARE, mage.cards.p.PatriarchsBidding.class));
        cards.add(new SetCardInfo("Plains", 481, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Priest of Fell Rites", 208, Rarity.RARE, mage.cards.p.PriestOfFellRites.class));
        cards.add(new SetCardInfo("Prismatic Ending", 25, Rarity.UNCOMMON, mage.cards.p.PrismaticEnding.class));
        cards.add(new SetCardInfo("Profane Tutor", 97, Rarity.RARE, mage.cards.p.ProfaneTutor.class));
        cards.add(new SetCardInfo("Rishadan Dockhand", 59, Rarity.RARE, mage.cards.r.RishadanDockhand.class));
        cards.add(new SetCardInfo("Sanctum Prelate", 491, Rarity.MYTHIC, mage.cards.s.SanctumPrelate.class));
        cards.add(new SetCardInfo("Scalding Tarn", 254, Rarity.RARE, mage.cards.s.ScaldingTarn.class));
        cards.add(new SetCardInfo("Spreading Insurrection", 142, Rarity.UNCOMMON, mage.cards.s.SpreadingInsurrection.class));
        cards.add(new SetCardInfo("Squirrel Mob", 286, Rarity.RARE, mage.cards.s.SquirrelMob.class));
        cards.add(new SetCardInfo("Squirrel Sanctuary", 174, Rarity.UNCOMMON, mage.cards.s.SquirrelSanctuary.class));
        cards.add(new SetCardInfo("Squirrel Sovereign", 175, Rarity.UNCOMMON, mage.cards.s.SquirrelSovereign.class));
        cards.add(new SetCardInfo("Swamp", 485, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Thrasta, Tempest's Roar", 178, Rarity.MYTHIC, mage.cards.t.ThrastaTempestsRoar.class));
        cards.add(new SetCardInfo("Timeless Dragon", 35, Rarity.RARE, mage.cards.t.TimelessDragon.class));
        cards.add(new SetCardInfo("Tourach's Canticle", 103, Rarity.COMMON, mage.cards.t.TourachsCanticle.class));
        cards.add(new SetCardInfo("Unmarked Grave", 106, Rarity.RARE, mage.cards.u.UnmarkedGrave.class));
        cards.add(new SetCardInfo("Urza's Saga", 259, Rarity.RARE, mage.cards.u.UrzasSaga.class));
        cards.add(new SetCardInfo("Verdant Catacombs", 260, Rarity.RARE, mage.cards.v.VerdantCatacombs.class));
        cards.add(new SetCardInfo("Void Mirror", 242, Rarity.RARE, mage.cards.v.VoidMirror.class));
        cards.add(new SetCardInfo("Wonder", 271, Rarity.RARE, mage.cards.w.Wonder.class));
        cards.add(new SetCardInfo("Yusri, Fortune's Flame", 218, Rarity.RARE, mage.cards.y.YusriFortunesFlame.class));
    }
}
