package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class Foundations extends ExpansionSet {

    private static final Foundations instance = new Foundations();

    public static Foundations getInstance() {
        return instance;
    }

    private Foundations() {
        super("Foundations", "FDN", ExpansionSet.buildDate(2024, 11, 15), SetType.EXPANSION);
        this.blockName = "Foundations"; // for sorting in GUI
        this.hasBasicLands = true;
        this.hasBoosters = false; // temporary

        cards.add(new SetCardInfo("Angelic Edict", 490, Rarity.COMMON, mage.cards.a.AngelicEdict.class));
        cards.add(new SetCardInfo("Anthem of Champions", 116, Rarity.RARE, mage.cards.a.AnthemOfChampions.class));
        cards.add(new SetCardInfo("Bloodtithe Collector", 516, Rarity.UNCOMMON, mage.cards.b.BloodtitheCollector.class));
        cards.add(new SetCardInfo("Crossway Troublemakers", 518, Rarity.RARE, mage.cards.c.CrosswayTroublemakers.class));
        cards.add(new SetCardInfo("Day of Judgment", 140, Rarity.RARE, mage.cards.d.DayOfJudgment.class));
        cards.add(new SetCardInfo("Elspeth's Smite", 493, Rarity.UNCOMMON, mage.cards.e.ElspethsSmite.class));
        cards.add(new SetCardInfo("Felidar Savior", 12, Rarity.COMMON, mage.cards.f.FelidarSavior.class));
        cards.add(new SetCardInfo("Helpful Hunter", 16, Rarity.COMMON, mage.cards.h.HelpfulHunter.class));
        cards.add(new SetCardInfo("Hero's Downfall", 175, Rarity.UNCOMMON, mage.cards.h.HerosDownfall.class));
        cards.add(new SetCardInfo("Highborn Vampire", 522, Rarity.COMMON, mage.cards.h.HighbornVampire.class));
        cards.add(new SetCardInfo("Ingenious Leonin", 495, Rarity.UNCOMMON, mage.cards.i.IngeniousLeonin.class));
        cards.add(new SetCardInfo("Jazal Goldmane", 497, Rarity.RARE, mage.cards.j.JazalGoldmane.class));
        cards.add(new SetCardInfo("Leonin Skyhunter", 498, Rarity.UNCOMMON, mage.cards.l.LeoninSkyhunter.class));
        cards.add(new SetCardInfo("Leonin Vanguard", 499, Rarity.UNCOMMON, mage.cards.l.LeoninVanguard.class));
        cards.add(new SetCardInfo("Llanowar Elves", 227, Rarity.COMMON, mage.cards.l.LlanowarElves.class));
        cards.add(new SetCardInfo("Moment of Craving", 524, Rarity.COMMON, mage.cards.m.MomentOfCraving.class));
        cards.add(new SetCardInfo("Moment of Triumph", 500, Rarity.COMMON, mage.cards.m.MomentOfTriumph.class));
        cards.add(new SetCardInfo("Offer Immortality", 525, Rarity.COMMON, mage.cards.o.OfferImmortality.class));
        cards.add(new SetCardInfo("Omniscience", 161, Rarity.MYTHIC, mage.cards.o.Omniscience.class));
        cards.add(new SetCardInfo("Pacifism", 501, Rarity.COMMON, mage.cards.p.Pacifism.class));
        cards.add(new SetCardInfo("Plains", 273, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Prideful Parent", 21, Rarity.COMMON, mage.cards.p.PridefulParent.class));
        cards.add(new SetCardInfo("Savannah Lions", 146, Rarity.UNCOMMON, mage.cards.s.SavannahLions.class));
        cards.add(new SetCardInfo("Stromkirk Bloodthief", 185, Rarity.UNCOMMON, mage.cards.s.StromkirkBloodthief.class));
        cards.add(new SetCardInfo("Swamp", 277, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Uncharted Haven", 564, Rarity.COMMON, mage.cards.u.UnchartedHaven.class));
        cards.add(new SetCardInfo("Untamed Hunger", 529, Rarity.COMMON, mage.cards.u.UntamedHunger.class));
        cards.add(new SetCardInfo("Vampire Interloper", 530, Rarity.COMMON, mage.cards.v.VampireInterloper.class));
        cards.add(new SetCardInfo("Vampire Neonate", 531, Rarity.COMMON, mage.cards.v.VampireNeonate.class));
        cards.add(new SetCardInfo("Vampire Spawn", 532, Rarity.COMMON, mage.cards.v.VampireSpawn.class));
        cards.add(new SetCardInfo("Vengeful Bloodwitch", 76, Rarity.UNCOMMON, mage.cards.v.VengefulBloodwitch.class));
    }
}
