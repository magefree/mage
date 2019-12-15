package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class TherosBeyondDeath extends ExpansionSet {

    private static final TherosBeyondDeath instance = new TherosBeyondDeath();

    public static TherosBeyondDeath getInstance() {
        return instance;
    }

    private TherosBeyondDeath() {
        super("Theros: Beyond Death", "THB", ExpansionSet.buildDate(2020, 1, 24), SetType.EXPANSION);
        this.blockName = "Theros: Beyond Death";
        this.hasBoosters = true;
        this.numBoosterLands = 1;
        this.numBoosterCommon = 10;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 8;
        this.maxCardNumberInBooster = 254;

        cards.add(new SetCardInfo("Commanding Presence", 7, Rarity.UNCOMMON, mage.cards.c.CommandingPresence.class));
        cards.add(new SetCardInfo("Daxos, Blessed by the Sun", 9, Rarity.UNCOMMON, mage.cards.d.DaxosBlessedByTheSun.class));
        cards.add(new SetCardInfo("Eidolon of Philosophy", 48, Rarity.COMMON, mage.cards.e.EidolonOfPhilosophy.class));
        cards.add(new SetCardInfo("Elspeth, Sun's Nemesis", 14, Rarity.MYTHIC, mage.cards.e.ElspethSunsNemesis.class));
        cards.add(new SetCardInfo("Elspeth, Undaunted Hero", 270, Rarity.MYTHIC, mage.cards.e.ElspethUndauntedHero.class));
        cards.add(new SetCardInfo("Forest", 254, Rarity.LAND, mage.cards.basiclands.Forest.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Hero of the Winds", 23, Rarity.UNCOMMON, mage.cards.h.HeroOfTheWinds.class));
        cards.add(new SetCardInfo("Indomitable Will", 25, Rarity.COMMON, mage.cards.i.IndomitableWill.class));
        cards.add(new SetCardInfo("Inevitable End", 102, Rarity.UNCOMMON, mage.cards.i.InevitableEnd.class));
        cards.add(new SetCardInfo("Island", 251, Rarity.LAND, mage.cards.basiclands.Island.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Klothys's Design", 176, Rarity.UNCOMMON, mage.cards.k.KlothyssDesign.class));
        cards.add(new SetCardInfo("Leonin of the Lost Pride", 28, Rarity.COMMON, mage.cards.l.LeoninOfTheLostPride.class));
        cards.add(new SetCardInfo("Memory Drain", 54, Rarity.COMMON, mage.cards.m.MemoryDrain.class));
        cards.add(new SetCardInfo("Mire's Grasp", 106, Rarity.COMMON, mage.cards.m.MiresGrasp.class));
        cards.add(new SetCardInfo("Mountain", 253, Rarity.LAND, mage.cards.basiclands.Mountain.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Nyxborn Colossus", 191, Rarity.COMMON, mage.cards.n.NyxbornColossus.class));
        cards.add(new SetCardInfo("Nyxborn Courser", 29, Rarity.COMMON, mage.cards.n.NyxbornCourser.class));
        cards.add(new SetCardInfo("Plains", 250, Rarity.LAND, mage.cards.basiclands.Plains.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Revoke Existence", 34, Rarity.COMMON, mage.cards.r.RevokeExistence.class));
        cards.add(new SetCardInfo("Setessan Champion", 198, Rarity.RARE, mage.cards.s.SetessanChampion.class));
        cards.add(new SetCardInfo("Staggering Insight", 228, Rarity.UNCOMMON, mage.cards.s.StaggeringInsight.class));
        cards.add(new SetCardInfo("Swamp", 252, Rarity.LAND, mage.cards.basiclands.Swamp.class, FULL_ART_BFZ_VARIOUS));
    }
}
