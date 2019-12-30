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
        super("Theros Beyond Death", "THB", ExpansionSet.buildDate(2020, 1, 24), SetType.EXPANSION);
        this.blockName = "Theros Beyond Death";
        this.hasBoosters = true;
        this.numBoosterLands = 1;
        this.numBoosterCommon = 10;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 8;
        this.maxCardNumberInBooster = 254;

        cards.add(new SetCardInfo("Allure of the Unknown", 207, Rarity.RARE, mage.cards.a.AllureOfTheUnknown.class));
        cards.add(new SetCardInfo("Ashiok, Nightmare Muse", 208, Rarity.MYTHIC, mage.cards.a.AshiokNightmareMuse.class));
        cards.add(new SetCardInfo("Ashiok, Sculptor of Fears", 274, Rarity.MYTHIC, mage.cards.a.AshiokSculptorOfFears.class));
        cards.add(new SetCardInfo("Athreos, Shroud-Veiled", 269, Rarity.MYTHIC, mage.cards.a.AthreosShroudVeiled.class));
        cards.add(new SetCardInfo("Brine Giant", 44, Rarity.COMMON, mage.cards.b.BrineGiant.class));
        cards.add(new SetCardInfo("Commanding Presence", 7, Rarity.UNCOMMON, mage.cards.c.CommandingPresence.class));
        cards.add(new SetCardInfo("Daxos, Blessed by the Sun", 9, Rarity.UNCOMMON, mage.cards.d.DaxosBlessedByTheSun.class));
        cards.add(new SetCardInfo("Deathbellow War Cry", 294, Rarity.RARE, mage.cards.d.DeathbellowWarCry.class));
        cards.add(new SetCardInfo("Demon of Loathing", 292, Rarity.RARE, mage.cards.d.DemonOfLoathing.class));
        cards.add(new SetCardInfo("Eidolon of Philosophy", 48, Rarity.COMMON, mage.cards.e.EidolonOfPhilosophy.class));
        cards.add(new SetCardInfo("Elspeth, Sun's Nemesis", 14, Rarity.MYTHIC, mage.cards.e.ElspethSunsNemesis.class));
        cards.add(new SetCardInfo("Elspeth, Undaunted Hero", 270, Rarity.MYTHIC, mage.cards.e.ElspethUndauntedHero.class));
        cards.add(new SetCardInfo("Field of Ruin", 242, Rarity.UNCOMMON, mage.cards.f.FieldOfRuin.class));
        cards.add(new SetCardInfo("Forest", 254, Rarity.LAND, mage.cards.basiclands.Forest.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Gallia of the Endless Dance", 217, Rarity.RARE, mage.cards.g.GalliaOfTheEndlessDance.class));
        cards.add(new SetCardInfo("Grasping Giant", 288, Rarity.RARE, mage.cards.g.GraspingGiant.class));
        cards.add(new SetCardInfo("Gray Merchant of Asphodel", 99, Rarity.UNCOMMON, mage.cards.g.GrayMerchantOfAsphodel.class));
        cards.add(new SetCardInfo("Hero of the Winds", 23, Rarity.UNCOMMON, mage.cards.h.HeroOfTheWinds.class));
        cards.add(new SetCardInfo("Indomitable Will", 25, Rarity.COMMON, mage.cards.i.IndomitableWill.class));
        cards.add(new SetCardInfo("Inevitable End", 102, Rarity.UNCOMMON, mage.cards.i.InevitableEnd.class));
        cards.add(new SetCardInfo("Ironscale Hydra", 296, Rarity.RARE, mage.cards.i.IronscaleHydra.class));
        cards.add(new SetCardInfo("Island", 251, Rarity.LAND, mage.cards.basiclands.Island.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Klothys's Design", 176, Rarity.UNCOMMON, mage.cards.k.KlothyssDesign.class));
        cards.add(new SetCardInfo("Klothys, God of Destiny", 220, Rarity.MYTHIC, mage.cards.k.KlothysGodOfDestiny.class));
        cards.add(new SetCardInfo("Leonin of the Lost Pride", 28, Rarity.COMMON, mage.cards.l.LeoninOfTheLostPride.class));
        cards.add(new SetCardInfo("Memory Drain", 54, Rarity.COMMON, mage.cards.m.MemoryDrain.class));
        cards.add(new SetCardInfo("Mire's Grasp", 106, Rarity.COMMON, mage.cards.m.MiresGrasp.class));
        cards.add(new SetCardInfo("Mountain", 253, Rarity.LAND, mage.cards.basiclands.Mountain.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Nyx Lotus", 235, Rarity.RARE, mage.cards.n.NyxLotus.class));
        cards.add(new SetCardInfo("Nyxborn Colossus", 191, Rarity.COMMON, mage.cards.n.NyxbornColossus.class));
        cards.add(new SetCardInfo("Nyxborn Courser", 29, Rarity.COMMON, mage.cards.n.NyxbornCourser.class));
        cards.add(new SetCardInfo("Plains", 250, Rarity.LAND, mage.cards.basiclands.Plains.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Revoke Existence", 34, Rarity.COMMON, mage.cards.r.RevokeExistence.class));
        cards.add(new SetCardInfo("Serpent of Yawning Depths", 291, Rarity.RARE, mage.cards.s.SerpentOfYawningDepths.class));
        cards.add(new SetCardInfo("Setessan Champion", 198, Rarity.RARE, mage.cards.s.SetessanChampion.class));
        cards.add(new SetCardInfo("Sphinx Mindbreaker", 290, Rarity.RARE, mage.cards.s.SphinxMindbreaker.class));
        cards.add(new SetCardInfo("Staggering Insight", 228, Rarity.UNCOMMON, mage.cards.s.StaggeringInsight.class));
        cards.add(new SetCardInfo("Swamp", 252, Rarity.LAND, mage.cards.basiclands.Swamp.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Temple of Abandon", 244, Rarity.RARE, mage.cards.t.TempleOfAbandon.class));
        cards.add(new SetCardInfo("Temple of Deceit", 245, Rarity.RARE, mage.cards.t.TempleOfDeceit.class));
        cards.add(new SetCardInfo("Temple of Enlightenment", 246, Rarity.RARE, mage.cards.t.TempleOfEnlightenment.class));
        cards.add(new SetCardInfo("Temple of Malice", 247, Rarity.RARE, mage.cards.t.TempleOfMalice.class));
        cards.add(new SetCardInfo("Temple of Plenty", 248, Rarity.RARE, mage.cards.t.TempleOfPlenty.class));
        cards.add(new SetCardInfo("Terror of Mount Velus", 295, Rarity.RARE, mage.cards.t.TerrorOfMountVelus.class));
        cards.add(new SetCardInfo("The Akroan War", 124, Rarity.RARE, mage.cards.t.TheAkroanWar.class));
        cards.add(new SetCardInfo("The Binding of the Titans", 166, Rarity.UNCOMMON, mage.cards.t.TheBindingOfTheTitans.class));
        cards.add(new SetCardInfo("Thirst for Meaning", 74, Rarity.COMMON, mage.cards.t.ThirstForMeaning.class));
        cards.add(new SetCardInfo("Treeshaker Chimera", 297, Rarity.RARE, mage.cards.t.TreeshakerChimera.class));
        cards.add(new SetCardInfo("Tymaret Calls the Dead", 118, Rarity.RARE, mage.cards.t.TymaretCallsTheDead.class));
        cards.add(new SetCardInfo("Underworld Rage-Hound", 163, Rarity.COMMON, mage.cards.u.UnderworldRageHound.class));
        cards.add(new SetCardInfo("Underworld Sentinel", 293, Rarity.RARE, mage.cards.u.UnderworldSentinel.class));
        cards.add(new SetCardInfo("Victory's Envoy", 289, Rarity.RARE, mage.cards.v.VictorysEnvoy.class));
        cards.add(new SetCardInfo("Woe Strider", 123, Rarity.RARE, mage.cards.w.WoeStrider.class));
    }
}
