package mage.sets;

import mage.cards.ExpansionSet;
import mage.collation.BoosterCollator;
import mage.collation.BoosterStructure;
import mage.collation.CardRun;
import mage.collation.RarityConfiguration;
import mage.constants.Rarity;
import mage.constants.SetType;

import java.util.ArrayList;
import java.util.List;

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

        cards.add(new SetCardInfo("Acolyte of Affliction", 206, Rarity.UNCOMMON, mage.cards.a.AcolyteOfAffliction.class));
        cards.add(new SetCardInfo("Agonizing Remorse", 83, Rarity.UNCOMMON, mage.cards.a.AgonizingRemorse.class));
        cards.add(new SetCardInfo("Alirios, Enraptured", 42, Rarity.UNCOMMON, mage.cards.a.AliriosEnraptured.class));
        cards.add(new SetCardInfo("Allure of the Unknown", 207, Rarity.RARE, mage.cards.a.AllureOfTheUnknown.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Allure of the Unknown", 332, Rarity.RARE, mage.cards.a.AllureOfTheUnknown.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Alseid of Life's Bounty", 1, Rarity.UNCOMMON, mage.cards.a.AlseidOfLifesBounty.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Alseid of Life's Bounty", 353, Rarity.UNCOMMON, mage.cards.a.AlseidOfLifesBounty.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Altar of the Pantheon", 231, Rarity.COMMON, mage.cards.a.AltarOfThePantheon.class));
        cards.add(new SetCardInfo("Anax, Hardened in the Forge", 125, Rarity.UNCOMMON, mage.cards.a.AnaxHardenedInTheForge.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Anax, Hardened in the Forge", 264, Rarity.UNCOMMON, mage.cards.a.AnaxHardenedInTheForge.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Aphemia, the Cacophony", 311, Rarity.RARE, mage.cards.a.AphemiaTheCacophony.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Aphemia, the Cacophony", 84, Rarity.RARE, mage.cards.a.AphemiaTheCacophony.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Arasta of the Endless Web", 165, Rarity.RARE, mage.cards.a.ArastaOfTheEndlessWeb.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Arasta of the Endless Web", 325, Rarity.RARE, mage.cards.a.ArastaOfTheEndlessWeb.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Arasta of the Endless Web", 352, Rarity.RARE, mage.cards.a.ArastaOfTheEndlessWeb.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Archon of Falling Stars", 2, Rarity.UNCOMMON, mage.cards.a.ArchonOfFallingStars.class));
        cards.add(new SetCardInfo("Archon of Sun's Grace", 298, Rarity.RARE, mage.cards.a.ArchonOfSunsGrace.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Archon of Sun's Grace", 3, Rarity.RARE, mage.cards.a.ArchonOfSunsGrace.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Arena Trickster", 126, Rarity.COMMON, mage.cards.a.ArenaTrickster.class));
        cards.add(new SetCardInfo("Ashiok's Erasure", 304, Rarity.RARE, mage.cards.a.AshioksErasure.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ashiok's Erasure", 43, Rarity.RARE, mage.cards.a.AshioksErasure.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ashiok's Forerunner", 277, Rarity.RARE, mage.cards.a.AshioksForerunner.class));
        cards.add(new SetCardInfo("Ashiok, Nightmare Muse", 208, Rarity.MYTHIC, mage.cards.a.AshiokNightmareMuse.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ashiok, Nightmare Muse", 256, Rarity.MYTHIC, mage.cards.a.AshiokNightmareMuse.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ashiok, Sculptor of Fears", 274, Rarity.MYTHIC, mage.cards.a.AshiokSculptorOfFears.class));
        cards.add(new SetCardInfo("Aspect of Lamprey", 85, Rarity.COMMON, mage.cards.a.AspectOfLamprey.class));
        cards.add(new SetCardInfo("Aspect of Manticore", 127, Rarity.COMMON, mage.cards.a.AspectOfManticore.class));
        cards.add(new SetCardInfo("Athreos, Shroud-Veiled", 269, Rarity.MYTHIC, mage.cards.a.AthreosShroudVeiled.class));
        cards.add(new SetCardInfo("Atris, Oracle of Half-Truths", 209, Rarity.RARE, mage.cards.a.AtrisOracleOfHalfTruths.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Atris, Oracle of Half-Truths", 333, Rarity.RARE, mage.cards.a.AtrisOracleOfHalfTruths.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Banishing Light", 4, Rarity.UNCOMMON, mage.cards.b.BanishingLight.class));
        cards.add(new SetCardInfo("Blight-Breath Catoblepas", 86, Rarity.COMMON, mage.cards.b.BlightBreathCatoblepas.class));
        cards.add(new SetCardInfo("Blood Aspirant", 128, Rarity.UNCOMMON, mage.cards.b.BloodAspirant.class));
        cards.add(new SetCardInfo("Brine Giant", 44, Rarity.COMMON, mage.cards.b.BrineGiant.class));
        cards.add(new SetCardInfo("Bronze Sword", 232, Rarity.COMMON, mage.cards.b.BronzeSword.class));
        cards.add(new SetCardInfo("Bronzehide Lion", 210, Rarity.RARE, mage.cards.b.BronzehideLion.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Bronzehide Lion", 334, Rarity.RARE, mage.cards.b.BronzehideLion.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Calix, Destiny's Hand", 211, Rarity.MYTHIC, mage.cards.c.CalixDestinysHand.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Calix, Destiny's Hand", 257, Rarity.MYTHIC, mage.cards.c.CalixDestinysHand.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Callaphe, Beloved of the Sea", 260, Rarity.UNCOMMON, mage.cards.c.CallapheBelovedOfTheSea.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Callaphe, Beloved of the Sea", 45, Rarity.UNCOMMON, mage.cards.c.CallapheBelovedOfTheSea.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Captivating Unicorn", 6, Rarity.COMMON, mage.cards.c.CaptivatingUnicorn.class));
        cards.add(new SetCardInfo("Careless Celebrant", 129, Rarity.UNCOMMON, mage.cards.c.CarelessCelebrant.class));
        cards.add(new SetCardInfo("Chain to Memory", 46, Rarity.COMMON, mage.cards.c.ChainToMemory.class));
        cards.add(new SetCardInfo("Chainweb Aracnir", 167, Rarity.UNCOMMON, mage.cards.c.ChainwebAracnir.class));
        cards.add(new SetCardInfo("Cling to Dust", 87, Rarity.UNCOMMON, mage.cards.c.ClingToDust.class));
        cards.add(new SetCardInfo("Commanding Presence", 7, Rarity.UNCOMMON, mage.cards.c.CommandingPresence.class));
        cards.add(new SetCardInfo("Dalakos, Crafter of Wonders", 212, Rarity.RARE, mage.cards.d.DalakosCrafterOfWonders.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dalakos, Crafter of Wonders", 335, Rarity.RARE, mage.cards.d.DalakosCrafterOfWonders.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dawn Evangel", 8, Rarity.UNCOMMON, mage.cards.d.DawnEvangel.class));
        cards.add(new SetCardInfo("Daxos, Blessed by the Sun", 258, Rarity.UNCOMMON, mage.cards.d.DaxosBlessedByTheSun.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Daxos, Blessed by the Sun", 9, Rarity.UNCOMMON, mage.cards.d.DaxosBlessedByTheSun.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Daybreak Chimera", 10, Rarity.COMMON, mage.cards.d.DaybreakChimera.class));
        cards.add(new SetCardInfo("Deathbellow War Cry", 294, Rarity.RARE, mage.cards.d.DeathbellowWarCry.class));
        cards.add(new SetCardInfo("Demon of Loathing", 292, Rarity.RARE, mage.cards.d.DemonOfLoathing.class));
        cards.add(new SetCardInfo("Deny the Divine", 47, Rarity.COMMON, mage.cards.d.DenyTheDivine.class));
        cards.add(new SetCardInfo("Destiny Spinner", 168, Rarity.UNCOMMON, mage.cards.d.DestinySpinner.class));
        cards.add(new SetCardInfo("Devourer of Memory", 213, Rarity.UNCOMMON, mage.cards.d.DevourerOfMemory.class));
        cards.add(new SetCardInfo("Discordant Piper", 88, Rarity.COMMON, mage.cards.d.DiscordantPiper.class));
        cards.add(new SetCardInfo("Drag to the Underworld", 89, Rarity.UNCOMMON, mage.cards.d.DragToTheUnderworld.class));
        cards.add(new SetCardInfo("Dreadful Apathy", 11, Rarity.COMMON, mage.cards.d.DreadfulApathy.class));
        cards.add(new SetCardInfo("Dream Trawler", 214, Rarity.RARE, mage.cards.d.DreamTrawler.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dream Trawler", 336, Rarity.RARE, mage.cards.d.DreamTrawler.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dreamshaper Shaman", 130, Rarity.UNCOMMON, mage.cards.d.DreamshaperShaman.class));
        cards.add(new SetCardInfo("Dreamstalker Manticore", 131, Rarity.UNCOMMON, mage.cards.d.DreamstalkerManticore.class));
        cards.add(new SetCardInfo("Dryad of the Ilysian Grove", 169, Rarity.RARE, mage.cards.d.DryadOfTheIlysianGrove.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dryad of the Ilysian Grove", 326, Rarity.RARE, mage.cards.d.DryadOfTheIlysianGrove.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Eat to Extinction", 312, Rarity.RARE, mage.cards.e.EatToExtinction.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Eat to Extinction", 90, Rarity.RARE, mage.cards.e.EatToExtinction.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Eidolon of Inspiration", 271, Rarity.UNCOMMON, mage.cards.e.EidolonOfInspiration.class));
        cards.add(new SetCardInfo("Eidolon of Obstruction", 12, Rarity.RARE, mage.cards.e.EidolonOfObstruction.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Eidolon of Obstruction", 299, Rarity.RARE, mage.cards.e.EidolonOfObstruction.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Eidolon of Philosophy", 48, Rarity.COMMON, mage.cards.e.EidolonOfPhilosophy.class));
        cards.add(new SetCardInfo("Elite Instructor", 49, Rarity.COMMON, mage.cards.e.EliteInstructor.class));
        cards.add(new SetCardInfo("Elspeth Conquers Death", 13, Rarity.RARE, mage.cards.e.ElspethConquersDeath.class));
        cards.add(new SetCardInfo("Elspeth's Devotee", 272, Rarity.RARE, mage.cards.e.ElspethsDevotee.class));
        cards.add(new SetCardInfo("Elspeth's Nightmare", 91, Rarity.UNCOMMON, mage.cards.e.ElspethsNightmare.class));
        cards.add(new SetCardInfo("Elspeth, Sun's Nemesis", 14, Rarity.MYTHIC, mage.cards.e.ElspethSunsNemesis.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Elspeth, Sun's Nemesis", 255, Rarity.MYTHIC, mage.cards.e.ElspethSunsNemesis.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Elspeth, Undaunted Hero", 270, Rarity.MYTHIC, mage.cards.e.ElspethUndauntedHero.class));
        cards.add(new SetCardInfo("Enemy of Enlightenment", 92, Rarity.UNCOMMON, mage.cards.e.EnemyOfEnlightenment.class));
        cards.add(new SetCardInfo("Enigmatic Incarnation", 215, Rarity.RARE, mage.cards.e.EnigmaticIncarnation.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Enigmatic Incarnation", 337, Rarity.RARE, mage.cards.e.EnigmaticIncarnation.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Entrancing Lyre", 233, Rarity.UNCOMMON, mage.cards.e.EntrancingLyre.class));
        cards.add(new SetCardInfo("Erebos's Intervention", 313, Rarity.RARE, mage.cards.e.ErebossIntervention.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Erebos's Intervention", 94, Rarity.RARE, mage.cards.e.ErebossIntervention.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Erebos, Bleak-Hearted", 262, Rarity.MYTHIC, mage.cards.e.ErebosBleakHearted.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Erebos, Bleak-Hearted", 93, Rarity.MYTHIC, mage.cards.e.ErebosBleakHearted.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Escape Velocity", 132, Rarity.UNCOMMON, mage.cards.e.EscapeVelocity.class));
        cards.add(new SetCardInfo("Eutropia the Twice-Favored", 216, Rarity.UNCOMMON, mage.cards.e.EutropiaTheTwiceFavored.class));
        cards.add(new SetCardInfo("Fateful End", 133, Rarity.UNCOMMON, mage.cards.f.FatefulEnd.class));
        cards.add(new SetCardInfo("Favored of Iroas", 15, Rarity.UNCOMMON, mage.cards.f.FavoredOfIroas.class));
        cards.add(new SetCardInfo("Field of Ruin", 242, Rarity.UNCOMMON, mage.cards.f.FieldOfRuin.class));
        cards.add(new SetCardInfo("Final Death", 95, Rarity.COMMON, mage.cards.f.FinalDeath.class));
        cards.add(new SetCardInfo("Final Flare", 134, Rarity.COMMON, mage.cards.f.FinalFlare.class));
        cards.add(new SetCardInfo("Flicker of Fate", 16, Rarity.COMMON, mage.cards.f.FlickerOfFate.class));
        cards.add(new SetCardInfo("Flummoxed Cyclops", 135, Rarity.COMMON, mage.cards.f.FlummoxedCyclops.class));
        cards.add(new SetCardInfo("Forest", 254, Rarity.LAND, mage.cards.basiclands.Forest.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Forest", 286, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 287, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Fruit of Tizerus", 96, Rarity.COMMON, mage.cards.f.FruitOfTizerus.class));
        cards.add(new SetCardInfo("Funeral Rites", 97, Rarity.COMMON, mage.cards.f.FuneralRites.class));
        cards.add(new SetCardInfo("Furious Rise", 136, Rarity.UNCOMMON, mage.cards.f.FuriousRise.class));
        cards.add(new SetCardInfo("Gallia of the Endless Dance", 217, Rarity.RARE, mage.cards.g.GalliaOfTheEndlessDance.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gallia of the Endless Dance", 338, Rarity.RARE, mage.cards.g.GalliaOfTheEndlessDance.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gift of Strength", 171, Rarity.COMMON, mage.cards.g.GiftOfStrength.class));
        cards.add(new SetCardInfo("Glimpse of Freedom", 50, Rarity.UNCOMMON, mage.cards.g.GlimpseOfFreedom.class));
        cards.add(new SetCardInfo("Glory Bearers", 17, Rarity.COMMON, mage.cards.g.GloryBearers.class));
        cards.add(new SetCardInfo("Grasping Giant", 288, Rarity.RARE, mage.cards.g.GraspingGiant.class));
        cards.add(new SetCardInfo("Gravebreaker Lamia", 314, Rarity.RARE, mage.cards.g.GravebreakerLamia.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gravebreaker Lamia", 98, Rarity.RARE, mage.cards.g.GravebreakerLamia.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gray Merchant of Asphodel", 355, Rarity.UNCOMMON, mage.cards.g.GrayMerchantOfAsphodel.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gray Merchant of Asphodel", 99, Rarity.UNCOMMON, mage.cards.g.GrayMerchantOfAsphodel.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Grim Physician", 100, Rarity.COMMON, mage.cards.g.GrimPhysician.class));
        cards.add(new SetCardInfo("Haktos the Unscarred", 218, Rarity.RARE, mage.cards.h.HaktosTheUnscarred.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Haktos the Unscarred", 339, Rarity.RARE, mage.cards.h.HaktosTheUnscarred.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hateful Eidolon", 101, Rarity.UNCOMMON, mage.cards.h.HatefulEidolon.class));
        cards.add(new SetCardInfo("Heliod's Intervention", 19, Rarity.RARE, mage.cards.h.HeliodsIntervention.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Heliod's Intervention", 300, Rarity.RARE, mage.cards.h.HeliodsIntervention.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Heliod's Pilgrim", 20, Rarity.COMMON, mage.cards.h.HeliodsPilgrim.class));
        cards.add(new SetCardInfo("Heliod's Punishment", 21, Rarity.UNCOMMON, mage.cards.h.HeliodsPunishment.class));
        cards.add(new SetCardInfo("Heliod, Sun-Crowned", 18, Rarity.MYTHIC, mage.cards.h.HeliodSunCrowned.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Heliod, Sun-Crowned", 259, Rarity.MYTHIC, mage.cards.h.HeliodSunCrowned.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hero of the Games", 137, Rarity.COMMON, mage.cards.h.HeroOfTheGames.class));
        cards.add(new SetCardInfo("Hero of the Nyxborn", 219, Rarity.UNCOMMON, mage.cards.h.HeroOfTheNyxborn.class));
        cards.add(new SetCardInfo("Hero of the Pride", 22, Rarity.COMMON, mage.cards.h.HeroOfThePride.class));
        cards.add(new SetCardInfo("Hero of the Winds", 23, Rarity.UNCOMMON, mage.cards.h.HeroOfTheWinds.class));
        cards.add(new SetCardInfo("Heroes of the Revel", 138, Rarity.UNCOMMON, mage.cards.h.HeroesOfTheRevel.class));
        cards.add(new SetCardInfo("Hydra's Growth", 172, Rarity.UNCOMMON, mage.cards.h.HydrasGrowth.class));
        cards.add(new SetCardInfo("Hyrax Tower Scout", 173, Rarity.COMMON, mage.cards.h.HyraxTowerScout.class));
        cards.add(new SetCardInfo("Ichthyomorphosis", 51, Rarity.COMMON, mage.cards.i.Ichthyomorphosis.class));
        cards.add(new SetCardInfo("Idyllic Tutor", 24, Rarity.RARE, mage.cards.i.IdyllicTutor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Idyllic Tutor", 301, Rarity.RARE, mage.cards.i.IdyllicTutor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ilysian Caryatid", 174, Rarity.COMMON, mage.cards.i.IlysianCaryatid.class));
        cards.add(new SetCardInfo("Impending Doom", 139, Rarity.UNCOMMON, mage.cards.i.ImpendingDoom.class));
        cards.add(new SetCardInfo("Incendiary Oracle", 140, Rarity.COMMON, mage.cards.i.IncendiaryOracle.class));
        cards.add(new SetCardInfo("Indomitable Will", 25, Rarity.COMMON, mage.cards.i.IndomitableWill.class));
        cards.add(new SetCardInfo("Inevitable End", 102, Rarity.UNCOMMON, mage.cards.i.InevitableEnd.class));
        cards.add(new SetCardInfo("Infuriate", 141, Rarity.COMMON, mage.cards.i.Infuriate.class));
        cards.add(new SetCardInfo("Inspire Awe", 175, Rarity.COMMON, mage.cards.i.InspireAwe.class));
        cards.add(new SetCardInfo("Iroas's Blessing", 142, Rarity.COMMON, mage.cards.i.IroassBlessing.class));
        cards.add(new SetCardInfo("Ironscale Hydra", 296, Rarity.RARE, mage.cards.i.IronscaleHydra.class));
        cards.add(new SetCardInfo("Irreverent Revelers", 143, Rarity.COMMON, mage.cards.i.IrreverentRevelers.class));
        cards.add(new SetCardInfo("Island", 251, Rarity.LAND, mage.cards.basiclands.Island.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Island", 280, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 281, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Karametra's Blessing", 26, Rarity.COMMON, mage.cards.k.KarametrasBlessing.class));
        cards.add(new SetCardInfo("Kiora Bests the Sea God", 52, Rarity.MYTHIC, mage.cards.k.KioraBestsTheSeaGod.class));
        cards.add(new SetCardInfo("Klothys's Design", 176, Rarity.UNCOMMON, mage.cards.k.KlothyssDesign.class));
        cards.add(new SetCardInfo("Klothys, God of Destiny", 220, Rarity.MYTHIC, mage.cards.k.KlothysGodOfDestiny.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Klothys, God of Destiny", 268, Rarity.MYTHIC, mage.cards.k.KlothysGodOfDestiny.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kroxa, Titan of Death's Hunger", 221, Rarity.MYTHIC, mage.cards.k.KroxaTitanOfDeathsHunger.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kroxa, Titan of Death's Hunger", 340, Rarity.MYTHIC, mage.cards.k.KroxaTitanOfDeathsHunger.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kunoros, Hound of Athreos", 222, Rarity.RARE, mage.cards.k.KunorosHoundOfAthreos.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kunoros, Hound of Athreos", 341, Rarity.RARE, mage.cards.k.KunorosHoundOfAthreos.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Labyrinth of Skophos", 243, Rarity.RARE, mage.cards.l.LabyrinthOfSkophos.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Labyrinth of Skophos", 346, Rarity.RARE, mage.cards.l.LabyrinthOfSkophos.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Lagonna-Band Storyteller", 27, Rarity.UNCOMMON, mage.cards.l.LagonnaBandStoryteller.class));
        cards.add(new SetCardInfo("Lampad of Death's Vigil", 103, Rarity.COMMON, mage.cards.l.LampadOfDeathsVigil.class));
        cards.add(new SetCardInfo("Leonin of the Lost Pride", 28, Rarity.COMMON, mage.cards.l.LeoninOfTheLostPride.class));
        cards.add(new SetCardInfo("Loathsome Chimera", 177, Rarity.COMMON, mage.cards.l.LoathsomeChimera.class));
        cards.add(new SetCardInfo("Mantle of the Wolf", 178, Rarity.RARE, mage.cards.m.MantleOfTheWolf.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mantle of the Wolf", 327, Rarity.RARE, mage.cards.m.MantleOfTheWolf.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Medomai's Prophecy", 53, Rarity.UNCOMMON, mage.cards.m.MedomaisProphecy.class));
        cards.add(new SetCardInfo("Memory Drain", 54, Rarity.COMMON, mage.cards.m.MemoryDrain.class));
        cards.add(new SetCardInfo("Mindwrack Harpy", 276, Rarity.COMMON, mage.cards.m.MindwrackHarpy.class));
        cards.add(new SetCardInfo("Minion's Return", 104, Rarity.UNCOMMON, mage.cards.m.MinionsReturn.class));
        cards.add(new SetCardInfo("Mire Triton", 105, Rarity.UNCOMMON, mage.cards.m.MireTriton.class));
        cards.add(new SetCardInfo("Mire's Grasp", 106, Rarity.COMMON, mage.cards.m.MiresGrasp.class));
        cards.add(new SetCardInfo("Mirror Shield", 234, Rarity.UNCOMMON, mage.cards.m.MirrorShield.class));
        cards.add(new SetCardInfo("Mischievous Chimera", 223, Rarity.UNCOMMON, mage.cards.m.MischievousChimera.class));
        cards.add(new SetCardInfo("Mogis's Favor", 107, Rarity.COMMON, mage.cards.m.MogissFavor.class));
        cards.add(new SetCardInfo("Moss Viper", 179, Rarity.COMMON, mage.cards.m.MossViper.class));
        cards.add(new SetCardInfo("Mountain", 253, Rarity.LAND, mage.cards.basiclands.Mountain.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 284, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 285, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mystic Repeal", 180, Rarity.UNCOMMON, mage.cards.m.MysticRepeal.class));
        cards.add(new SetCardInfo("Nadir Kraken", 305, Rarity.RARE, mage.cards.n.NadirKraken.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Nadir Kraken", 55, Rarity.RARE, mage.cards.n.NadirKraken.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Naiad of Hidden Coves", 56, Rarity.COMMON, mage.cards.n.NaiadOfHiddenCoves.class));
        cards.add(new SetCardInfo("Nessian Boar", 181, Rarity.RARE, mage.cards.n.NessianBoar.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Nessian Boar", 328, Rarity.RARE, mage.cards.n.NessianBoar.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Nessian Hornbeetle", 182, Rarity.UNCOMMON, mage.cards.n.NessianHornbeetle.class));
        cards.add(new SetCardInfo("Nessian Wanderer", 183, Rarity.UNCOMMON, mage.cards.n.NessianWanderer.class));
        cards.add(new SetCardInfo("Nexus Wardens", 184, Rarity.COMMON, mage.cards.n.NexusWardens.class));
        cards.add(new SetCardInfo("Nightmare Shepherd", 108, Rarity.RARE, mage.cards.n.NightmareShepherd.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Nightmare Shepherd", 315, Rarity.RARE, mage.cards.n.NightmareShepherd.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Nylea's Forerunner", 186, Rarity.COMMON, mage.cards.n.NyleasForerunner.class));
        cards.add(new SetCardInfo("Nylea's Huntmaster", 187, Rarity.COMMON, mage.cards.n.NyleasHuntmaster.class));
        cards.add(new SetCardInfo("Nylea's Intervention", 188, Rarity.RARE, mage.cards.n.NyleasIntervention.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Nylea's Intervention", 329, Rarity.RARE, mage.cards.n.NyleasIntervention.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Nylea, Keen-Eyed", 185, Rarity.MYTHIC, mage.cards.n.NyleaKeenEyed.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Nylea, Keen-Eyed", 266, Rarity.MYTHIC, mage.cards.n.NyleaKeenEyed.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Nyx Herald", 189, Rarity.UNCOMMON, mage.cards.n.NyxHerald.class));
        cards.add(new SetCardInfo("Nyx Lotus", 235, Rarity.RARE, mage.cards.n.NyxLotus.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Nyx Lotus", 344, Rarity.RARE, mage.cards.n.NyxLotus.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Nyxbloom Ancient", 190, Rarity.MYTHIC, mage.cards.n.NyxbloomAncient.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Nyxbloom Ancient", 330, Rarity.MYTHIC, mage.cards.n.NyxbloomAncient.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Nyxborn Brute", 144, Rarity.COMMON, mage.cards.n.NyxbornBrute.class));
        cards.add(new SetCardInfo("Nyxborn Colossus", 191, Rarity.COMMON, mage.cards.n.NyxbornColossus.class));
        cards.add(new SetCardInfo("Nyxborn Courser", 29, Rarity.COMMON, mage.cards.n.NyxbornCourser.class));
        cards.add(new SetCardInfo("Nyxborn Marauder", 109, Rarity.COMMON, mage.cards.n.NyxbornMarauder.class));
        cards.add(new SetCardInfo("Nyxborn Seaguard", 57, Rarity.COMMON, mage.cards.n.NyxbornSeaguard.class));
        cards.add(new SetCardInfo("Omen of the Dead", 110, Rarity.COMMON, mage.cards.o.OmenOfTheDead.class));
        cards.add(new SetCardInfo("Omen of the Forge", 145, Rarity.COMMON, mage.cards.o.OmenOfTheForge.class));
        cards.add(new SetCardInfo("Omen of the Hunt", 192, Rarity.COMMON, mage.cards.o.OmenOfTheHunt.class));
        cards.add(new SetCardInfo("Omen of the Sea", 58, Rarity.COMMON, mage.cards.o.OmenOfTheSea.class));
        cards.add(new SetCardInfo("Omen of the Sun", 30, Rarity.COMMON, mage.cards.o.OmenOfTheSun.class));
        cards.add(new SetCardInfo("One with the Stars", 59, Rarity.UNCOMMON, mage.cards.o.OneWithTheStars.class));
        cards.add(new SetCardInfo("Oread of Mountain's Blaze", 146, Rarity.COMMON, mage.cards.o.OreadOfMountainsBlaze.class));
        cards.add(new SetCardInfo("Ox of Agonas", 147, Rarity.MYTHIC, mage.cards.o.OxOfAgonas.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ox of Agonas", 318, Rarity.MYTHIC, mage.cards.o.OxOfAgonas.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Phalanx Tactics", 31, Rarity.UNCOMMON, mage.cards.p.PhalanxTactics.class));
        cards.add(new SetCardInfo("Pharika's Libation", 111, Rarity.COMMON, mage.cards.p.PharikasLibation.class));
        cards.add(new SetCardInfo("Pharika's Spawn", 112, Rarity.UNCOMMON, mage.cards.p.PharikasSpawn.class));
        cards.add(new SetCardInfo("Pheres-Band Brawler", 193, Rarity.UNCOMMON, mage.cards.p.PheresBandBrawler.class));
        cards.add(new SetCardInfo("Phoenix of Ash", 148, Rarity.RARE, mage.cards.p.PhoenixOfAsh.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Phoenix of Ash", 319, Rarity.RARE, mage.cards.p.PhoenixOfAsh.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Pious Wayfarer", 32, Rarity.COMMON, mage.cards.p.PiousWayfarer.class));
        cards.add(new SetCardInfo("Plains", 250, Rarity.LAND, mage.cards.basiclands.Plains.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Plains", 278, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 279, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plummet", 194, Rarity.COMMON, mage.cards.p.Plummet.class));
        cards.add(new SetCardInfo("Polukranos, Unchained", 224, Rarity.MYTHIC, mage.cards.p.PolukranosUnchained.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Polukranos, Unchained", 342, Rarity.MYTHIC, mage.cards.p.PolukranosUnchained.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Portent of Betrayal", 149, Rarity.COMMON, mage.cards.p.PortentOfBetrayal.class));
        cards.add(new SetCardInfo("Protean Thaumaturge", 306, Rarity.RARE, mage.cards.p.ProteanThaumaturge.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Protean Thaumaturge", 60, Rarity.RARE, mage.cards.p.ProteanThaumaturge.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Purphoros's Intervention", 151, Rarity.RARE, mage.cards.p.PurphorossIntervention.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Purphoros's Intervention", 320, Rarity.RARE, mage.cards.p.PurphorossIntervention.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Purphoros, Bronze-Blooded", 150, Rarity.MYTHIC, mage.cards.p.PurphorosBronzeBlooded.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Purphoros, Bronze-Blooded", 265, Rarity.MYTHIC, mage.cards.p.PurphorosBronzeBlooded.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Rage-Scarred Berserker", 113, Rarity.COMMON, mage.cards.r.RageScarredBerserker.class));
        cards.add(new SetCardInfo("Relentless Pursuit", 195, Rarity.COMMON, mage.cards.r.RelentlessPursuit.class));
        cards.add(new SetCardInfo("Renata, Called to the Hunt", 196, Rarity.UNCOMMON, mage.cards.r.RenataCalledToTheHunt.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Renata, Called to the Hunt", 267, Rarity.UNCOMMON, mage.cards.r.RenataCalledToTheHunt.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Return to Nature", 197, Rarity.COMMON, mage.cards.r.ReturnToNature.class));
        cards.add(new SetCardInfo("Reverent Hoplite", 33, Rarity.UNCOMMON, mage.cards.r.ReverentHoplite.class));
        cards.add(new SetCardInfo("Revoke Existence", 34, Rarity.COMMON, mage.cards.r.RevokeExistence.class));
        cards.add(new SetCardInfo("Riptide Turtle", 61, Rarity.COMMON, mage.cards.r.RiptideTurtle.class));
        cards.add(new SetCardInfo("Rise to Glory", 225, Rarity.UNCOMMON, mage.cards.r.RiseToGlory.class));
        cards.add(new SetCardInfo("Rumbling Sentry", 35, Rarity.COMMON, mage.cards.r.RumblingSentry.class));
        cards.add(new SetCardInfo("Sage of Mysteries", 62, Rarity.UNCOMMON, mage.cards.s.SageOfMysteries.class));
        cards.add(new SetCardInfo("Satyr's Cunning", 152, Rarity.COMMON, mage.cards.s.SatyrsCunning.class));
        cards.add(new SetCardInfo("Scavenging Harpy", 114, Rarity.COMMON, mage.cards.s.ScavengingHarpy.class));
        cards.add(new SetCardInfo("Sea God's Scorn", 63, Rarity.UNCOMMON, mage.cards.s.SeaGodsScorn.class));
        cards.add(new SetCardInfo("Sentinel's Eyes", 36, Rarity.COMMON, mage.cards.s.SentinelsEyes.class));
        cards.add(new SetCardInfo("Serpent of Yawning Depths", 291, Rarity.RARE, mage.cards.s.SerpentOfYawningDepths.class));
        cards.add(new SetCardInfo("Setessan Champion", 198, Rarity.RARE, mage.cards.s.SetessanChampion.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Setessan Champion", 331, Rarity.RARE, mage.cards.s.SetessanChampion.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Setessan Petitioner", 199, Rarity.UNCOMMON, mage.cards.s.SetessanPetitioner.class));
        cards.add(new SetCardInfo("Setessan Skirmisher", 200, Rarity.COMMON, mage.cards.s.SetessanSkirmisher.class));
        cards.add(new SetCardInfo("Setessan Training", 201, Rarity.COMMON, mage.cards.s.SetessanTraining.class));
        cards.add(new SetCardInfo("Shadowspear", 236, Rarity.RARE, mage.cards.s.Shadowspear.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Shadowspear", 345, Rarity.RARE, mage.cards.s.Shadowspear.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Shatter the Sky", 302, Rarity.RARE, mage.cards.s.ShatterTheSky.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Shatter the Sky", 37, Rarity.RARE, mage.cards.s.ShatterTheSky.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Shimmerwing Chimera", 64, Rarity.UNCOMMON, mage.cards.s.ShimmerwingChimera.class));
        cards.add(new SetCardInfo("Shoal Kraken", 65, Rarity.UNCOMMON, mage.cards.s.ShoalKraken.class));
        cards.add(new SetCardInfo("Siona, Captain of the Pyleas", 226, Rarity.UNCOMMON, mage.cards.s.SionaCaptainOfThePyleas.class));
        cards.add(new SetCardInfo("Skola Grovedancer", 202, Rarity.COMMON, mage.cards.s.SkolaGrovedancer.class));
        cards.add(new SetCardInfo("Skophos Maze-Warden", 153, Rarity.UNCOMMON, mage.cards.s.SkophosMazeWarden.class));
        cards.add(new SetCardInfo("Skophos Warleader", 154, Rarity.COMMON, mage.cards.s.SkophosWarleader.class));
        cards.add(new SetCardInfo("Slaughter-Priest of Mogis", 227, Rarity.UNCOMMON, mage.cards.s.SlaughterPriestOfMogis.class));
        cards.add(new SetCardInfo("Sleep of the Dead", 66, Rarity.COMMON, mage.cards.s.SleepOfTheDead.class));
        cards.add(new SetCardInfo("Soul-Guide Lantern", 237, Rarity.UNCOMMON, mage.cards.s.SoulGuideLantern.class));
        cards.add(new SetCardInfo("Soulreaper of Mogis", 115, Rarity.COMMON, mage.cards.s.SoulreaperOfMogis.class));
        cards.add(new SetCardInfo("Sphinx Mindbreaker", 290, Rarity.RARE, mage.cards.s.SphinxMindbreaker.class));
        cards.add(new SetCardInfo("Staggering Insight", 228, Rarity.UNCOMMON, mage.cards.s.StaggeringInsight.class));
        cards.add(new SetCardInfo("Stampede Rider", 155, Rarity.COMMON, mage.cards.s.StampedeRider.class));
        cards.add(new SetCardInfo("Starlit Mantle", 67, Rarity.COMMON, mage.cards.s.StarlitMantle.class));
        cards.add(new SetCardInfo("Stern Dismissal", 68, Rarity.COMMON, mage.cards.s.SternDismissal.class));
        cards.add(new SetCardInfo("Stinging Lionfish", 69, Rarity.UNCOMMON, mage.cards.s.StingingLionfish.class));
        cards.add(new SetCardInfo("Storm Herald", 156, Rarity.RARE, mage.cards.s.StormHerald.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Storm Herald", 321, Rarity.RARE, mage.cards.s.StormHerald.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Storm's Wrath", 157, Rarity.RARE, mage.cards.s.StormsWrath.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Storm's Wrath", 322, Rarity.RARE, mage.cards.s.StormsWrath.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sunlit Hoplite", 273, Rarity.COMMON, mage.cards.s.SunlitHoplite.class));
        cards.add(new SetCardInfo("Sunmane Pegasus", 38, Rarity.COMMON, mage.cards.s.SunmanePegasus.class));
        cards.add(new SetCardInfo("Swamp", 252, Rarity.LAND, mage.cards.basiclands.Swamp.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 282, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 283, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sweet Oblivion", 70, Rarity.UNCOMMON, mage.cards.s.SweetOblivion.class));
        cards.add(new SetCardInfo("Swimmer in Nightmares", 275, Rarity.UNCOMMON, mage.cards.s.SwimmerInNightmares.class));
        cards.add(new SetCardInfo("Taranika, Akroan Veteran", 303, Rarity.RARE, mage.cards.t.TaranikaAkroanVeteran.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Taranika, Akroan Veteran", 39, Rarity.RARE, mage.cards.t.TaranikaAkroanVeteran.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tectonic Giant", 158, Rarity.RARE, mage.cards.t.TectonicGiant.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tectonic Giant", 323, Rarity.RARE, mage.cards.t.TectonicGiant.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Temple of Abandon", "347*", Rarity.RARE, mage.cards.t.TempleOfAbandon.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Temple of Abandon", 244, Rarity.RARE, mage.cards.t.TempleOfAbandon.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Temple of Abandon", 347, Rarity.RARE, mage.cards.t.TempleOfAbandon.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Temple of Deceit", 245, Rarity.RARE, mage.cards.t.TempleOfDeceit.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Temple of Deceit", 348, Rarity.RARE, mage.cards.t.TempleOfDeceit.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Temple of Enlightenment", 246, Rarity.RARE, mage.cards.t.TempleOfEnlightenment.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Temple of Enlightenment", 349, Rarity.RARE, mage.cards.t.TempleOfEnlightenment.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Temple of Malice", 247, Rarity.RARE, mage.cards.t.TempleOfMalice.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Temple of Malice", 350, Rarity.RARE, mage.cards.t.TempleOfMalice.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Temple of Plenty", 248, Rarity.RARE, mage.cards.t.TempleOfPlenty.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Temple of Plenty", 351, Rarity.RARE, mage.cards.t.TempleOfPlenty.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Temple Thief", 116, Rarity.COMMON, mage.cards.t.TempleThief.class));
        cards.add(new SetCardInfo("Terror of Mount Velus", 295, Rarity.RARE, mage.cards.t.TerrorOfMountVelus.class));
        cards.add(new SetCardInfo("Thassa's Intervention", 307, Rarity.RARE, mage.cards.t.ThassasIntervention.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Thassa's Intervention", 72, Rarity.RARE, mage.cards.t.ThassasIntervention.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Thassa's Oracle", 308, Rarity.RARE, mage.cards.t.ThassasOracle.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Thassa's Oracle", 73, Rarity.RARE, mage.cards.t.ThassasOracle.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Thassa, Deep-Dwelling", 261, Rarity.MYTHIC, mage.cards.t.ThassaDeepDwelling.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Thassa, Deep-Dwelling", 71, Rarity.MYTHIC, mage.cards.t.ThassaDeepDwelling.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Thaumaturge's Familiar", 238, Rarity.COMMON, mage.cards.t.ThaumaturgesFamiliar.class));
        cards.add(new SetCardInfo("The Akroan War", 124, Rarity.RARE, mage.cards.t.TheAkroanWar.class));
        cards.add(new SetCardInfo("The Binding of the Titans", 166, Rarity.UNCOMMON, mage.cards.t.TheBindingOfTheTitans.class));
        cards.add(new SetCardInfo("The Birth of Meletis", 5, Rarity.UNCOMMON, mage.cards.t.TheBirthOfMeletis.class));
        cards.add(new SetCardInfo("The First Iroan Games", 170, Rarity.RARE, mage.cards.t.TheFirstIroanGames.class));
        cards.add(new SetCardInfo("The Triumph of Anax", 160, Rarity.UNCOMMON, mage.cards.t.TheTriumphOfAnax.class));
        cards.add(new SetCardInfo("Thirst for Meaning", 354, Rarity.COMMON, mage.cards.t.ThirstForMeaning.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Thirst for Meaning", 74, Rarity.COMMON, mage.cards.t.ThirstForMeaning.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Threnody Singer", 75, Rarity.UNCOMMON, mage.cards.t.ThrenodySinger.class));
        cards.add(new SetCardInfo("Thrill of Possibility", 159, Rarity.COMMON, mage.cards.t.ThrillOfPossibility.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Thrill of Possibility", 356, Rarity.COMMON, mage.cards.t.ThrillOfPossibility.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Thryx, the Sudden Storm", 309, Rarity.RARE, mage.cards.t.ThryxTheSuddenStorm.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Thryx, the Sudden Storm", 76, Rarity.RARE, mage.cards.t.ThryxTheSuddenStorm.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Thundering Chariot", 239, Rarity.UNCOMMON, mage.cards.t.ThunderingChariot.class));
        cards.add(new SetCardInfo("Towering-Wave Mystic", 77, Rarity.COMMON, mage.cards.t.ToweringWaveMystic.class));
        cards.add(new SetCardInfo("Transcendent Envoy", 40, Rarity.COMMON, mage.cards.t.TranscendentEnvoy.class));
        cards.add(new SetCardInfo("Traveler's Amulet", 240, Rarity.COMMON, mage.cards.t.TravelersAmulet.class));
        cards.add(new SetCardInfo("Treacherous Blessing", 117, Rarity.RARE, mage.cards.t.TreacherousBlessing.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Treacherous Blessing", 316, Rarity.RARE, mage.cards.t.TreacherousBlessing.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Treeshaker Chimera", 297, Rarity.RARE, mage.cards.t.TreeshakerChimera.class));
        cards.add(new SetCardInfo("Triton Waverider", 78, Rarity.COMMON, mage.cards.t.TritonWaverider.class));
        cards.add(new SetCardInfo("Triumphant Surge", 41, Rarity.COMMON, mage.cards.t.TriumphantSurge.class));
        cards.add(new SetCardInfo("Tymaret Calls the Dead", 118, Rarity.RARE, mage.cards.t.TymaretCallsTheDead.class));
        cards.add(new SetCardInfo("Tymaret, Chosen from Death", 119, Rarity.UNCOMMON, mage.cards.t.TymaretChosenFromDeath.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tymaret, Chosen from Death", 263, Rarity.UNCOMMON, mage.cards.t.TymaretChosenFromDeath.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Underworld Breach", 161, Rarity.RARE, mage.cards.u.UnderworldBreach.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Underworld Breach", 324, Rarity.RARE, mage.cards.u.UnderworldBreach.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Underworld Charger", 120, Rarity.COMMON, mage.cards.u.UnderworldCharger.class));
        cards.add(new SetCardInfo("Underworld Dreams", 121, Rarity.UNCOMMON, mage.cards.u.UnderworldDreams.class));
        cards.add(new SetCardInfo("Underworld Fires", 162, Rarity.UNCOMMON, mage.cards.u.UnderworldFires.class));
        cards.add(new SetCardInfo("Underworld Rage-Hound", 163, Rarity.COMMON, mage.cards.u.UnderworldRageHound.class));
        cards.add(new SetCardInfo("Underworld Sentinel", 293, Rarity.RARE, mage.cards.u.UnderworldSentinel.class));
        cards.add(new SetCardInfo("Unknown Shores", 249, Rarity.COMMON, mage.cards.u.UnknownShores.class));
        cards.add(new SetCardInfo("Uro, Titan of Nature's Wrath", 229, Rarity.MYTHIC, mage.cards.u.UroTitanOfNaturesWrath.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Uro, Titan of Nature's Wrath", 343, Rarity.MYTHIC, mage.cards.u.UroTitanOfNaturesWrath.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Venomous Hierophant", 122, Rarity.COMMON, mage.cards.v.VenomousHierophant.class));
        cards.add(new SetCardInfo("Vexing Gull", 79, Rarity.COMMON, mage.cards.v.VexingGull.class));
        cards.add(new SetCardInfo("Victory's Envoy", 289, Rarity.RARE, mage.cards.v.VictorysEnvoy.class));
        cards.add(new SetCardInfo("Voracious Typhon", 203, Rarity.COMMON, mage.cards.v.VoraciousTyphon.class));
        cards.add(new SetCardInfo("Warbriar Blessing", 204, Rarity.COMMON, mage.cards.w.WarbriarBlessing.class));
        cards.add(new SetCardInfo("Warden of the Chained", 230, Rarity.UNCOMMON, mage.cards.w.WardenOfTheChained.class));
        cards.add(new SetCardInfo("Wavebreak Hippocamp", 310, Rarity.RARE, mage.cards.w.WavebreakHippocamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Wavebreak Hippocamp", 80, Rarity.RARE, mage.cards.w.WavebreakHippocamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Whirlwind Denial", 81, Rarity.UNCOMMON, mage.cards.w.WhirlwindDenial.class));
        cards.add(new SetCardInfo("Wings of Hubris", 241, Rarity.COMMON, mage.cards.w.WingsOfHubris.class));
        cards.add(new SetCardInfo("Witness of Tomorrows", 82, Rarity.COMMON, mage.cards.w.WitnessOfTomorrows.class));
        cards.add(new SetCardInfo("Woe Strider", 123, Rarity.RARE, mage.cards.w.WoeStrider.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Woe Strider", 317, Rarity.RARE, mage.cards.w.WoeStrider.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Wolfwillow Haven", 205, Rarity.UNCOMMON, mage.cards.w.WolfwillowHaven.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Wolfwillow Haven", 357, Rarity.UNCOMMON, mage.cards.w.WolfwillowHaven.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Wrap in Flames", 164, Rarity.COMMON, mage.cards.w.WrapInFlames.class));
    }

    @Override
    public BoosterCollator createCollator() {
        return new TherosBeyondDeathCollator();
    }
}

// Booster collation info from https://www.lethe.xyz/mtg/collation/thb.html
// Using USA collation for common/uncommon, rare collation inferred from other sets
class TherosBeyondDeathCollator implements BoosterCollator {
    private final CardRun commonA = new CardRun(true, "155", "29", "79", "127", "38", "57", "159", "41", "66", "140", "30", "78", "163", "28", "56", "137", "25", "68", "144", "20", "67", "146", "26", "49", "134", "40", "61", "159", "29", "51", "164", "17", "57", "149", "38", "66", "127", "30", "47", "144", "36", "79", "155", "41", "67", "137", "28", "78", "140", "25", "56", "163", "20", "49", "146", "40", "68", "134", "17", "51", "149", "26", "47", "164", "36", "61");
    private final CardRun commonB = new CardRun(true, "186", "85", "191", "116", "201", "103", "202", "115", "184", "120", "194", "110", "192", "88", "177", "113", "171", "86", "195", "109", "179", "114", "202", "85", "201", "103", "184", "116", "186", "115", "192", "110", "191", "114", "177", "120", "194", "88", "171", "113", "179", "86", "195", "109", "201", "85", "184", "116", "202", "110", "186", "103", "191", "115", "192", "114", "179", "113", "194", "109", "195", "86", "177", "88", "171", "120");
    private final CardRun commonC1 = new CardRun(true, "203", "154", "106", "77", "10", "174", "58", "16", "141", "238", "122", "46", "173", "152", "22", "240", "100", "74", "200", "142", "97", "11", "48", "203", "241", "154", "106", "35", "82", "174", "77", "10", "240", "141", "100", "58", "238", "122", "232", "152", "22", "46", "111", "173", "241", "16", "74", "97", "48", "11", "200", "142", "111", "82", "35");
    private final CardRun commonC2 = new CardRun(true, "44", "96", "197", "145", "232", "34", "126", "204", "249", "54", "135", "231", "187", "175", "44", "143", "95", "96", "197", "135", "107", "6", "32", "204", "126", "34", "54", "249", "145", "231", "187", "96", "6", "143", "44", "107", "34", "175", "135", "249", "95", "197", "54", "204", "126", "32", "6", "175", "95", "231", "145", "107", "187", "32", "143");
    private final CardRun uncommonA = new CardRun(true, "223", "65", "153", "8", "112", "227", "99", "167", "33", "138", "4", "189", "228", "45", "59", "180", "105", "1", "136", "196", "206", "139", "83", "89", "233", "31", "131", "91", "219", "193", "27", "133", "64", "199", "213", "264", "42", "153", "205", "8", "136", "4", "189", "33", "223", "2", "138", "112", "27", "233", "260", "180", "31", "59", "99", "131", "105", "267", "81", "139", "228", "167", "133", "219", "65", "1", "83", "125", "206", "193", "42", "91", "227", "89", "199", "153", "8", "81", "213", "64", "112", "223", "4", "136", "205", "105", "139", "99", "65", "2", "180", "228", "59", "1", "233", "45", "189", "227", "33", "196", "83", "138", "206", "42", "219", "167", "131", "31", "89", "193", "91", "125", "213", "199", "81", "27", "2", "64", "133", "205");
    private final CardRun uncommonB = new CardRun(true, "226", "101", "128", "183", "21", "234", "87", "50", "242", "176", "239", "132", "9", "216", "62", "119", "172", "160", "104", "69", "168", "225", "130", "237", "63", "15", "102", "166", "5", "129", "121", "53", "239", "70", "182", "128", "21", "234", "92", "69", "101", "160", "23", "230", "75", "130", "104", "172", "50", "7", "162", "87", "183", "226", "62", "216", "258", "132", "176", "237", "263", "15", "242", "63", "5", "225", "168", "129", "121", "53", "230", "21", "70", "102", "166", "128", "92", "234", "23", "183", "160", "104", "75", "226", "162", "7", "239", "182", "9", "132", "101", "69", "172", "216", "242", "50", "176", "87", "225", "62", "15", "168", "119", "237", "130", "5", "70", "102", "166", "63", "23", "129", "121", "53", "182", "7", "162", "230", "92", "75");
    private final CardRun rareA = new CardRun(false, "207", "84", "165", "3", "43", "209", "210", "212", "214", "169", "90", "12", "13", "215", "94", "217", "98", "218", "19", "24", "222", "243", "178", "55", "181", "108", "188", "235", "148", "60", "151", "198", "236", "37", "156", "157", "39", "158", "244", "245", "246", "247", "248", "72", "73", "124", "170", "76", "117", "118", "161", "80", "123", "207", "84", "165", "3", "43", "209", "210", "212", "214", "169", "90", "12", "13", "215", "94", "217", "98", "218", "19", "24", "222", "243", "178", "55", "181", "108", "188", "235", "148", "60", "151", "198", "236", "37", "156", "157", "39", "158", "244", "245", "246", "247", "248", "72", "73", "124", "170", "76", "117", "118", "161", "80", "123", "14", "18", "52", "71", "93", "147", "150", "185", "190", "208", "211", "220", "221", "224", "229");
    private final CardRun rareB = new CardRun(false, "207", "84", "165", "3", "43", "209", "210", "212", "214", "169", "90", "12", "13", "215", "94", "217", "98", "218", "19", "24", "222", "243", "178", "55", "181", "108", "188", "235", "148", "60", "151", "198", "236", "37", "156", "157", "39", "158", "244", "245", "246", "247", "248", "72", "73", "124", "170", "76", "117", "118", "161", "80", "123", "207", "84", "165", "3", "43", "209", "210", "212", "214", "169", "90", "12", "13", "215", "94", "217", "98", "218", "19", "24", "222", "243", "178", "55", "181", "108", "188", "235", "148", "60", "151", "198", "236", "37", "156", "157", "39", "158", "244", "245", "246", "247", "248", "72", "73", "124", "170", "76", "117", "118", "161", "80", "123", "255", "259", "52", "261", "262", "147", "265", "266", "190", "256", "257", "268", "221", "224", "229");
    private final CardRun land = new CardRun(false, "250", "251", "252", "253", "254");

    private final BoosterStructure AABBC1C1C1C1C1C1 = new BoosterStructure(
            commonA, commonA,
            commonB, commonB,
            commonC1, commonC1, commonC1, commonC1, commonC1, commonC1
    );
    private final BoosterStructure AAABBC1C1C1C1C1 = new BoosterStructure(
            commonA, commonA, commonA,
            commonB, commonB,
            commonC1, commonC1, commonC1, commonC1, commonC1
    );
    private final BoosterStructure AAAABBC2C2C2C2 = new BoosterStructure(
            commonA, commonA, commonA, commonA,
            commonB, commonB,
            commonC2, commonC2, commonC2, commonC2
    );
    private final BoosterStructure AAAABBBC2C2C2 = new BoosterStructure(
            commonA, commonA, commonA, commonA,
            commonB, commonB, commonB,
            commonC2, commonC2, commonC2
    );
    private final BoosterStructure AAAABBBBC2C2 = new BoosterStructure(
            commonA, commonA, commonA, commonA,
            commonB, commonB, commonB, commonB,
            commonC2, commonC2
    );
    private final BoosterStructure AAB = new BoosterStructure(uncommonA, uncommonA, uncommonB);
    private final BoosterStructure ABB = new BoosterStructure(uncommonA, uncommonB, uncommonB);
    private final BoosterStructure R1 = new BoosterStructure(rareA);
    private final BoosterStructure R2 = new BoosterStructure(rareB);
    private final BoosterStructure L1 = new BoosterStructure(land);

    // In order for equal numbers of each common to exist, the average booster must contain:
    // 3.27 A commons (36 / 11)
    // 2.18 B commons (24 / 11)
    // 2.73 C1 commons (30 / 11, or 60 / 11 in each C1 booster)
    // 1.82 C2 commons (20 / 11, or 40 / 11 in each C2 booster)
    // These numbers are the same for all sets with 101 commons in A/B/C1/C2 print runs
    // and with 10 common slots per booster
    private final RarityConfiguration commonRuns = new RarityConfiguration(
            AABBC1C1C1C1C1C1,
            AABBC1C1C1C1C1C1,
            AABBC1C1C1C1C1C1,
            AABBC1C1C1C1C1C1,
            AABBC1C1C1C1C1C1,
            AAABBC1C1C1C1C1,
            AAABBC1C1C1C1C1,
            AAABBC1C1C1C1C1,
            AAABBC1C1C1C1C1,
            AAABBC1C1C1C1C1,
            AAABBC1C1C1C1C1,

            AAAABBC2C2C2C2,
            AAAABBC2C2C2C2,
            AAAABBC2C2C2C2,
            AAAABBC2C2C2C2,
            AAAABBC2C2C2C2,
            AAAABBC2C2C2C2,
            AAAABBC2C2C2C2,
            AAAABBC2C2C2C2,
            AAAABBBC2C2C2,
            AAAABBBC2C2C2,
            AAAABBBBC2C2
    );
    private final RarityConfiguration uncommonRuns = new RarityConfiguration(AAB, ABB);
    private final RarityConfiguration rareRuns = new RarityConfiguration(R1, R1, R2);
    private final RarityConfiguration landRuns = new RarityConfiguration(L1);

    @Override
    public List<String> makeBooster() {
        List<String> booster = new ArrayList<>();
        booster.addAll(commonRuns.getNext().makeRun());
        booster.addAll(uncommonRuns.getNext().makeRun());
        booster.addAll(rareRuns.getNext().makeRun());
        booster.addAll(landRuns.getNext().makeRun());
        return booster;
    }
}
