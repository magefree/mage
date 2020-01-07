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
        cards.add(new SetCardInfo("Anax, Hardened in the Forge", 125, Rarity.UNCOMMON, mage.cards.a.AnaxHardenedInTheForge.class));
        cards.add(new SetCardInfo("Aphemia, the Cacophony", 84, Rarity.RARE, mage.cards.a.AphemiaTheCacophony.class));
        cards.add(new SetCardInfo("Arasta of the Endless Web", 165, Rarity.RARE, mage.cards.a.ArastaOfTheEndlessWeb.class));
        cards.add(new SetCardInfo("Archon of Falling Stars", 2, Rarity.UNCOMMON, mage.cards.a.ArchonOfFallingStars.class));
        cards.add(new SetCardInfo("Archon of Sun's Grace", 3, Rarity.RARE, mage.cards.a.ArchonOfSunsGrace.class));
        cards.add(new SetCardInfo("Arena Trickster", 126, Rarity.COMMON, mage.cards.a.ArenaTrickster.class));
        cards.add(new SetCardInfo("Ashiok's Erasure", 43, Rarity.RARE, mage.cards.a.AshioksErasure.class));
        cards.add(new SetCardInfo("Ashiok, Nightmare Muse", 208, Rarity.MYTHIC, mage.cards.a.AshiokNightmareMuse.class));
        cards.add(new SetCardInfo("Ashiok, Sculptor of Fears", 274, Rarity.MYTHIC, mage.cards.a.AshiokSculptorOfFears.class));
        cards.add(new SetCardInfo("Athreos, Shroud-Veiled", 269, Rarity.MYTHIC, mage.cards.a.AthreosShroudVeiled.class));
        cards.add(new SetCardInfo("Atris, Oracle of Half-Truths", 209, Rarity.RARE, mage.cards.a.AtrisOracleOfHalfTruths.class));
        cards.add(new SetCardInfo("Banishing Light", 4, Rarity.UNCOMMON, mage.cards.b.BanishingLight.class));
        cards.add(new SetCardInfo("Blood Aspirant", 128, Rarity.UNCOMMON, mage.cards.b.BloodAspirant.class));
        cards.add(new SetCardInfo("Brine Giant", 44, Rarity.COMMON, mage.cards.b.BrineGiant.class));
        cards.add(new SetCardInfo("Careless Celebrant", 129, Rarity.UNCOMMON, mage.cards.c.CarelessCelebrant.class));
        cards.add(new SetCardInfo("Chainweb Aracnir", 167, Rarity.UNCOMMON, mage.cards.c.ChainwebAracnir.class));
        cards.add(new SetCardInfo("Cling to Dust", 87, Rarity.UNCOMMON, mage.cards.c.ClingToDust.class));
        cards.add(new SetCardInfo("Commanding Presence", 7, Rarity.UNCOMMON, mage.cards.c.CommandingPresence.class));
        cards.add(new SetCardInfo("Dalakos, Crafter of Wonders", 212, Rarity.RARE, mage.cards.d.DalakosCrafterOfWonders.class));
        cards.add(new SetCardInfo("Dawn Evangel", 8, Rarity.UNCOMMON, mage.cards.d.DawnEvangel.class));
        cards.add(new SetCardInfo("Daxos, Blessed by the Sun", 9, Rarity.UNCOMMON, mage.cards.d.DaxosBlessedByTheSun.class));
        cards.add(new SetCardInfo("Deathbellow War Cry", 294, Rarity.RARE, mage.cards.d.DeathbellowWarCry.class));
        cards.add(new SetCardInfo("Demon of Loathing", 292, Rarity.RARE, mage.cards.d.DemonOfLoathing.class));
        cards.add(new SetCardInfo("Devourer of Memory", 213, Rarity.UNCOMMON, mage.cards.d.DevourerOfMemory.class));
        cards.add(new SetCardInfo("Drag to the Underworld", 89, Rarity.UNCOMMON, mage.cards.d.DragToTheUnderworld.class));
        cards.add(new SetCardInfo("Eidolon of Philosophy", 48, Rarity.COMMON, mage.cards.e.EidolonOfPhilosophy.class));
        cards.add(new SetCardInfo("Elspeth, Sun's Nemesis", 14, Rarity.MYTHIC, mage.cards.e.ElspethSunsNemesis.class));
        cards.add(new SetCardInfo("Elspeth, Undaunted Hero", 270, Rarity.MYTHIC, mage.cards.e.ElspethUndauntedHero.class));
        cards.add(new SetCardInfo("Erebos's Intervention", 94, Rarity.RARE, mage.cards.e.ErebossIntervention.class));
        cards.add(new SetCardInfo("Erebos, Bleak-Hearted", 93, Rarity.MYTHIC, mage.cards.e.ErebosBleakHearted.class));
        cards.add(new SetCardInfo("Escape Velocity", 132, Rarity.UNCOMMON, mage.cards.e.EscapeVelocity.class));
        cards.add(new SetCardInfo("Eutropia the Twice-Favored", 216, Rarity.UNCOMMON, mage.cards.e.EutropiaTheTwiceFavored.class));
        cards.add(new SetCardInfo("Fateful End", 133, Rarity.UNCOMMON, mage.cards.f.FatefulEnd.class));
        cards.add(new SetCardInfo("Field of Ruin", 242, Rarity.UNCOMMON, mage.cards.f.FieldOfRuin.class));
        cards.add(new SetCardInfo("Final Death", 95, Rarity.COMMON, mage.cards.f.FinalDeath.class));
        cards.add(new SetCardInfo("Forest", 254, Rarity.LAND, mage.cards.basiclands.Forest.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Gallia of the Endless Dance", 217, Rarity.RARE, mage.cards.g.GalliaOfTheEndlessDance.class));
        cards.add(new SetCardInfo("Glimpse of Freedom", 50, Rarity.UNCOMMON, mage.cards.g.GlimpseOfFreedom.class));
        cards.add(new SetCardInfo("Grasping Giant", 288, Rarity.RARE, mage.cards.g.GraspingGiant.class));
        cards.add(new SetCardInfo("Gray Merchant of Asphodel", 99, Rarity.UNCOMMON, mage.cards.g.GrayMerchantOfAsphodel.class));
        cards.add(new SetCardInfo("Haktos the Unscarred", 218, Rarity.RARE, mage.cards.h.HaktosTheUnscarred.class));
        cards.add(new SetCardInfo("Heliod, Sun-Crowned", 18, Rarity.MYTHIC, mage.cards.h.HeliodSunCrowned.class));
        cards.add(new SetCardInfo("Hero of the Winds", 23, Rarity.UNCOMMON, mage.cards.h.HeroOfTheWinds.class));
        cards.add(new SetCardInfo("Idyllic Tutor", 24, Rarity.RARE, mage.cards.i.IdyllicTutor.class));
        cards.add(new SetCardInfo("Indomitable Will", 25, Rarity.COMMON, mage.cards.i.IndomitableWill.class));
        cards.add(new SetCardInfo("Inevitable End", 102, Rarity.UNCOMMON, mage.cards.i.InevitableEnd.class));
        cards.add(new SetCardInfo("Inspire Awe", 175, Rarity.COMMON, mage.cards.i.InspireAwe.class));
        cards.add(new SetCardInfo("Ironscale Hydra", 296, Rarity.RARE, mage.cards.i.IronscaleHydra.class));
        cards.add(new SetCardInfo("Island", 251, Rarity.LAND, mage.cards.basiclands.Island.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Klothys's Design", 176, Rarity.UNCOMMON, mage.cards.k.KlothyssDesign.class));
        cards.add(new SetCardInfo("Klothys, God of Destiny", 220, Rarity.MYTHIC, mage.cards.k.KlothysGodOfDestiny.class));
        cards.add(new SetCardInfo("Kunoros, Hound of Athreos", 222, Rarity.RARE, mage.cards.k.KunorosHoundOfAthreos.class));
        cards.add(new SetCardInfo("Leonin of the Lost Pride", 28, Rarity.COMMON, mage.cards.l.LeoninOfTheLostPride.class));
        cards.add(new SetCardInfo("Mantle of the Wolf", 178, Rarity.RARE, mage.cards.m.MantleOfTheWolf.class));
        cards.add(new SetCardInfo("Memory Drain", 54, Rarity.COMMON, mage.cards.m.MemoryDrain.class));
        cards.add(new SetCardInfo("Mire Triton", 105, Rarity.UNCOMMON, mage.cards.m.MireTriton.class));
        cards.add(new SetCardInfo("Mire's Grasp", 106, Rarity.COMMON, mage.cards.m.MiresGrasp.class));
        cards.add(new SetCardInfo("Mountain", 253, Rarity.LAND, mage.cards.basiclands.Mountain.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Nadir Kraken", 55, Rarity.RARE, mage.cards.n.NadirKraken.class));
        cards.add(new SetCardInfo("Naiad of Hidden Coves", 56, Rarity.COMMON, mage.cards.n.NaiadOfHiddenCoves.class));
        cards.add(new SetCardInfo("Nessian Boar", 181, Rarity.RARE, mage.cards.n.NessianBoar.class));
        cards.add(new SetCardInfo("Nylea's Intervention", 188, Rarity.RARE, mage.cards.n.NyleasIntervention.class));
        cards.add(new SetCardInfo("Nylea, Keen-Eyed", 185, Rarity.MYTHIC, mage.cards.n.NyleaKeenEyed.class));
        cards.add(new SetCardInfo("Nyx Herald", 189, Rarity.UNCOMMON, mage.cards.n.NyxHerald.class));
        cards.add(new SetCardInfo("Nyx Lotus", 235, Rarity.RARE, mage.cards.n.NyxLotus.class));
        cards.add(new SetCardInfo("Nyxborn Colossus", 191, Rarity.COMMON, mage.cards.n.NyxbornColossus.class));
        cards.add(new SetCardInfo("Nyxborn Courser", 29, Rarity.COMMON, mage.cards.n.NyxbornCourser.class));
        cards.add(new SetCardInfo("Omen of the Forge", 145, Rarity.COMMON, mage.cards.o.OmenOfTheForge.class));
        cards.add(new SetCardInfo("Omen of the Sea", 58, Rarity.COMMON, mage.cards.o.OmenOfTheSea.class));
        cards.add(new SetCardInfo("Omen of the Sun", 30, Rarity.COMMON, mage.cards.o.OmenOfTheSun.class));
        cards.add(new SetCardInfo("One with the Stars", 59, Rarity.UNCOMMON, mage.cards.o.OneWithTheStars.class));
        cards.add(new SetCardInfo("Oread of Mountain's Blaze", 146, Rarity.COMMON, mage.cards.o.OreadOfMountainsBlaze.class));
        cards.add(new SetCardInfo("Ox of Agonas", 147, Rarity.MYTHIC, mage.cards.o.OxOfAgonas.class));
        cards.add(new SetCardInfo("Pharika's Spawn", 112, Rarity.UNCOMMON, mage.cards.p.PharikasSpawn.class));
        cards.add(new SetCardInfo("Pious Wayfarer", 32, Rarity.COMMON, mage.cards.p.PiousWayfarer.class));
        cards.add(new SetCardInfo("Plains", 250, Rarity.LAND, mage.cards.basiclands.Plains.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Polukranos, Unchained", 224, Rarity.MYTHIC, mage.cards.p.PolukranosUnchained.class));
        cards.add(new SetCardInfo("Purphoros's Intervention", 151, Rarity.RARE, mage.cards.p.PurphorossIntervention.class));
        cards.add(new SetCardInfo("Purphoros, Bronze-Blooded", 150, Rarity.MYTHIC, mage.cards.p.PurphorosBronzeBlooded.class));
        cards.add(new SetCardInfo("Renata, Called to the Hunt", 196, Rarity.UNCOMMON, mage.cards.r.RenataCalledToTheHunt.class));
        cards.add(new SetCardInfo("Reverent Hoplite", 33, Rarity.UNCOMMON, mage.cards.r.ReverentHoplite.class));
        cards.add(new SetCardInfo("Revoke Existence", 34, Rarity.COMMON, mage.cards.r.RevokeExistence.class));
        cards.add(new SetCardInfo("Rise to Glory", 225, Rarity.UNCOMMON, mage.cards.r.RiseToGlory.class));
        cards.add(new SetCardInfo("Serpent of Yawning Depths", 291, Rarity.RARE, mage.cards.s.SerpentOfYawningDepths.class));
        cards.add(new SetCardInfo("Setessan Champion", 198, Rarity.RARE, mage.cards.s.SetessanChampion.class));
        cards.add(new SetCardInfo("Setessan Petitioner", 199, Rarity.UNCOMMON, mage.cards.s.SetessanPetitioner.class));
        cards.add(new SetCardInfo("Shimmerwing Chimera", 64, Rarity.UNCOMMON, mage.cards.s.ShimmerwingChimera.class));
        cards.add(new SetCardInfo("Shoal Kraken", 65, Rarity.UNCOMMON, mage.cards.s.ShoalKraken.class));
        cards.add(new SetCardInfo("Siona, Captain of the Pyleas", 226, Rarity.UNCOMMON, mage.cards.s.SionaCaptainOfThePyleas.class));
        cards.add(new SetCardInfo("Sphinx Mindbreaker", 290, Rarity.RARE, mage.cards.s.SphinxMindbreaker.class));
        cards.add(new SetCardInfo("Staggering Insight", 228, Rarity.UNCOMMON, mage.cards.s.StaggeringInsight.class));
        cards.add(new SetCardInfo("Stinging Lionfish", 69, Rarity.UNCOMMON, mage.cards.s.StingingLionfish.class));
        cards.add(new SetCardInfo("Storm's Wrath", 157, Rarity.RARE, mage.cards.s.StormsWrath.class));
        cards.add(new SetCardInfo("Swamp", 252, Rarity.LAND, mage.cards.basiclands.Swamp.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Sweet Oblivion", 70, Rarity.UNCOMMON, mage.cards.s.SweetOblivion.class));
        cards.add(new SetCardInfo("Tectonic Giant", 158, Rarity.RARE, mage.cards.t.TectonicGiant.class));
        cards.add(new SetCardInfo("Temple of Abandon", 244, Rarity.RARE, mage.cards.t.TempleOfAbandon.class));
        cards.add(new SetCardInfo("Temple of Deceit", 245, Rarity.RARE, mage.cards.t.TempleOfDeceit.class));
        cards.add(new SetCardInfo("Temple of Enlightenment", 246, Rarity.RARE, mage.cards.t.TempleOfEnlightenment.class));
        cards.add(new SetCardInfo("Temple of Malice", 247, Rarity.RARE, mage.cards.t.TempleOfMalice.class));
        cards.add(new SetCardInfo("Temple of Plenty", 248, Rarity.RARE, mage.cards.t.TempleOfPlenty.class));
        cards.add(new SetCardInfo("Terror of Mount Velus", 295, Rarity.RARE, mage.cards.t.TerrorOfMountVelus.class));
        cards.add(new SetCardInfo("Thassa, Deep-Dwelling", 71, Rarity.MYTHIC, mage.cards.t.ThassaDeepDwelling.class));
        cards.add(new SetCardInfo("The Akroan War", 124, Rarity.RARE, mage.cards.t.TheAkroanWar.class));
        cards.add(new SetCardInfo("The Binding of the Titans", 166, Rarity.UNCOMMON, mage.cards.t.TheBindingOfTheTitans.class));
        cards.add(new SetCardInfo("The Birth of Meletis", 5, Rarity.UNCOMMON, mage.cards.t.TheBirthOfMeletis.class));
        cards.add(new SetCardInfo("The Triumph of Anax", 160, Rarity.UNCOMMON, mage.cards.t.TheTriumphOfAnax.class));
        cards.add(new SetCardInfo("Thirst for Meaning", 74, Rarity.COMMON, mage.cards.t.ThirstForMeaning.class));
        cards.add(new SetCardInfo("Towering-Wave Mystic", 77, Rarity.COMMON, mage.cards.t.ToweringWaveMystic.class));
        cards.add(new SetCardInfo("Treacherous Blessing", 316, Rarity.RARE, mage.cards.t.TreacherousBlessing.class));
        cards.add(new SetCardInfo("Treeshaker Chimera", 297, Rarity.RARE, mage.cards.t.TreeshakerChimera.class));
        cards.add(new SetCardInfo("Tymaret Calls the Dead", 118, Rarity.RARE, mage.cards.t.TymaretCallsTheDead.class));
        cards.add(new SetCardInfo("Tymaret, Chosen from Death", 119, Rarity.UNCOMMON, mage.cards.t.TymaretChosenFromDeath.class));
        cards.add(new SetCardInfo("Underworld Breach", 161, Rarity.RARE, mage.cards.u.UnderworldBreach.class));
        cards.add(new SetCardInfo("Underworld Rage-Hound", 163, Rarity.COMMON, mage.cards.u.UnderworldRageHound.class));
        cards.add(new SetCardInfo("Underworld Sentinel", 293, Rarity.RARE, mage.cards.u.UnderworldSentinel.class));
        cards.add(new SetCardInfo("Vexing Gull", 79, Rarity.COMMON, mage.cards.v.VexingGull.class));
        cards.add(new SetCardInfo("Victory's Envoy", 289, Rarity.RARE, mage.cards.v.VictorysEnvoy.class));
        cards.add(new SetCardInfo("Warden of the Chained", 230, Rarity.UNCOMMON, mage.cards.w.WardenOfTheChained.class));
        cards.add(new SetCardInfo("Wavebreak Hippocamp", 80, Rarity.RARE, mage.cards.w.WavebreakHippocamp.class));
        cards.add(new SetCardInfo("Woe Strider", 123, Rarity.RARE, mage.cards.w.WoeStrider.class));
    }
}
