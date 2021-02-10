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
        super("Theros Beyond Death", "THB", ExpansionSet.buildDate(2020, 1, 24), SetType.EXPANSION, new TherosBeyondDeathCollator());
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
        cards.add(new SetCardInfo("Temple Thief", 116, Rarity.COMMON, mage.cards.t.TempleThief.class));
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
}

// Booster collation info from https://www.lethe.xyz/mtg/collation/thb.html
class TherosBeyondDeathCollator implements BoosterCollator {

    private static class TherosBeyondDeathRun extends CardRun {
        private static final TherosBeyondDeathRun commonA = new TherosBeyondDeathRun(true, "Stampede Rider", "Nyxborn Courser", "Vexing Gull", "Aspect of Manticore", "Sunmane Pegasus", "Nyxborn Seaguard", "Thrill of Possibility", "Triumphant Surge", "Sleep of the Dead", "Incendiary Oracle", "Omen of the Sun", "Triton Waverider", "Underworld Rage-Hound", "Leonin of the Lost Pride", "Naiad of Hidden Coves", "Hero of the Games", "Indomitable Will", "Stern Dismissal", "Nyxborn Brute", "Heliod's Pilgrim", "Starlit Mantle", "Oread of Mountain's Blaze", "Karametra's Blessing", "Elite Instructor", "Final Flare", "Transcendent Envoy", "Riptide Turtle", "Thrill of Possibility", "Nyxborn Courser", "Ichthyomorphosis", "Wrap in Flames", "Glory Bearers", "Nyxborn Seaguard", "Portent of Betrayal", "Sunmane Pegasus", "Sleep of the Dead", "Aspect of Manticore", "Omen of the Sun", "Deny the Divine", "Nyxborn Brute", "Sentinel's Eyes", "Vexing Gull", "Stampede Rider", "Triumphant Surge", "Starlit Mantle", "Hero of the Games", "Leonin of the Lost Pride", "Triton Waverider", "Incendiary Oracle", "Indomitable Will", "Naiad of Hidden Coves", "Underworld Rage-Hound", "Heliod's Pilgrim", "Elite Instructor", "Oread of Mountain's Blaze", "Transcendent Envoy", "Stern Dismissal", "Final Flare", "Glory Bearers", "Ichthyomorphosis", "Portent of Betrayal", "Karametra's Blessing", "Deny the Divine", "Wrap in Flames", "Sentinel's Eyes", "Riptide Turtle");
        private static final TherosBeyondDeathRun commonB = new TherosBeyondDeathRun(true, "Nylea's Forerunner", "Aspect of Lamprey", "Nyxborn Colossus", "Temple Thief", "Setessan Training", "Lampad of Death's Vigil", "Skola Grovedancer", "Soulreaper of Mogis", "Nexus Wardens", "Underworld Charger", "Plummet", "Omen of the Dead", "Omen of the Hunt", "Discordant Piper", "Loathsome Chimera", "Rage-Scarred Berserker", "Gift of Strength", "Blight-Breath Catoblepas", "Relentless Pursuit", "Nyxborn Marauder", "Moss Viper", "Scavenging Harpy", "Skola Grovedancer", "Aspect of Lamprey", "Setessan Training", "Lampad of Death's Vigil", "Nexus Wardens", "Temple Thief", "Nylea's Forerunner", "Soulreaper of Mogis", "Omen of the Hunt", "Omen of the Dead", "Nyxborn Colossus", "Scavenging Harpy", "Loathsome Chimera", "Underworld Charger", "Plummet", "Discordant Piper", "Gift of Strength", "Rage-Scarred Berserker", "Moss Viper", "Blight-Breath Catoblepas", "Relentless Pursuit", "Nyxborn Marauder", "Setessan Training", "Aspect of Lamprey", "Nexus Wardens", "Temple Thief", "Skola Grovedancer", "Omen of the Dead", "Nylea's Forerunner", "Lampad of Death's Vigil", "Nyxborn Colossus", "Soulreaper of Mogis", "Omen of the Hunt", "Scavenging Harpy", "Moss Viper", "Rage-Scarred Berserker", "Plummet", "Nyxborn Marauder", "Relentless Pursuit", "Blight-Breath Catoblepas", "Loathsome Chimera", "Discordant Piper", "Gift of Strength", "Underworld Charger");
        private static final TherosBeyondDeathRun commonC1 = new TherosBeyondDeathRun(true, "Voracious Typhon", "Skophos Warleader", "Mire's Grasp", "Towering-Wave Mystic", "Daybreak Chimera", "Ilysian Caryatid", "Omen of the Sea", "Flicker of Fate", "Infuriate", "Thaumaturge's Familiar", "Venomous Hierophant", "Chain to Memory", "Hyrax Tower Scout", "Satyr's Cunning", "Hero of the Pride", "Traveler's Amulet", "Grim Physician", "Thirst for Meaning", "Setessan Skirmisher", "Iroas's Blessing", "Funeral Rites", "Dreadful Apathy", "Eidolon of Philosophy", "Voracious Typhon", "Wings of Hubris", "Skophos Warleader", "Mire's Grasp", "Rumbling Sentry", "Witness of Tomorrows", "Ilysian Caryatid", "Towering-Wave Mystic", "Daybreak Chimera", "Traveler's Amulet", "Infuriate", "Grim Physician", "Omen of the Sea", "Thaumaturge's Familiar", "Venomous Hierophant", "Bronze Sword", "Satyr's Cunning", "Hero of the Pride", "Chain to Memory", "Pharika's Libation", "Hyrax Tower Scout", "Wings of Hubris", "Flicker of Fate", "Thirst for Meaning", "Funeral Rites", "Eidolon of Philosophy", "Dreadful Apathy", "Setessan Skirmisher", "Iroas's Blessing", "Pharika's Libation", "Witness of Tomorrows", "Rumbling Sentry");
        private static final TherosBeyondDeathRun commonC2 = new TherosBeyondDeathRun(true, "Brine Giant", "Fruit of Tizerus", "Return to Nature", "Omen of the Forge", "Bronze Sword", "Revoke Existence", "Arena Trickster", "Warbriar Blessing", "Unknown Shores", "Memory Drain", "Flummoxed Cyclops", "Altar of the Pantheon", "Nylea's Huntmaster", "Inspire Awe", "Brine Giant", "Irreverent Revelers", "Final Death", "Fruit of Tizerus", "Return to Nature", "Flummoxed Cyclops", "Mogis's Favor", "Captivating Unicorn", "Pious Wayfarer", "Warbriar Blessing", "Arena Trickster", "Revoke Existence", "Memory Drain", "Unknown Shores", "Omen of the Forge", "Altar of the Pantheon", "Nylea's Huntmaster", "Fruit of Tizerus", "Captivating Unicorn", "Irreverent Revelers", "Brine Giant", "Mogis's Favor", "Revoke Existence", "Inspire Awe", "Flummoxed Cyclops", "Unknown Shores", "Final Death", "Return to Nature", "Memory Drain", "Warbriar Blessing", "Arena Trickster", "Pious Wayfarer", "Captivating Unicorn", "Inspire Awe", "Final Death", "Altar of the Pantheon", "Omen of the Forge", "Mogis's Favor", "Nylea's Huntmaster", "Pious Wayfarer", "Irreverent Revelers");
        private static final TherosBeyondDeathRun uncommonA = new TherosBeyondDeathRun(true, "Mischievous Chimera", "Shoal Kraken", "Skophos Maze-Warden", "Dawn Evangel", "Pharika's Spawn", "Slaughter-Priest of Mogis", "Gray Merchant of Asphodel", "Chainweb Aracnir", "Reverent Hoplite", "Heroes of the Revel", "Banishing Light", "Nyx Herald", "Staggering Insight", "Callaphe, Beloved of the Sea", "One with the Stars", "Mystic Repeal", "Mire Triton", "Alseid of Life's Bounty", "Furious Rise", "Renata, Called to the Hunt", "Acolyte of Affliction", "Impending Doom", "Agonizing Remorse", "Drag to the Underworld", "Entrancing Lyre", "Phalanx Tactics", "Dreamstalker Manticore", "Elspeth's Nightmare", "Hero of the Nyxborn", "Pheres-Band Brawler", "Lagonna-Band Storyteller", "Fateful End", "Shimmerwing Chimera", "Setessan Petitioner", "Devourer of Memory", "Anax, Hardened in the Forge", "Alirios, Enraptured", "Skophos Maze-Warden", "Wolfwillow Haven", "Dawn Evangel", "Furious Rise", "Banishing Light", "Nyx Herald", "Reverent Hoplite", "Mischievous Chimera", "Archon of Falling Stars", "Heroes of the Revel", "Pharika's Spawn", "Lagonna-Band Storyteller", "Entrancing Lyre", "Callaphe, Beloved of the Sea", "Mystic Repeal", "Phalanx Tactics", "One with the Stars", "Gray Merchant of Asphodel", "Dreamstalker Manticore", "Mire Triton", "Renata, Called to the Hunt", "Whirlwind Denial", "Impending Doom", "Staggering Insight", "Chainweb Aracnir", "Fateful End", "Hero of the Nyxborn", "Shoal Kraken", "Alseid of Life's Bounty", "Agonizing Remorse", "Anax, Hardened in the Forge", "Acolyte of Affliction", "Pheres-Band Brawler", "Alirios, Enraptured", "Elspeth's Nightmare", "Slaughter-Priest of Mogis", "Drag to the Underworld", "Setessan Petitioner", "Skophos Maze-Warden", "Dawn Evangel", "Whirlwind Denial", "Devourer of Memory", "Shimmerwing Chimera", "Pharika's Spawn", "Mischievous Chimera", "Banishing Light", "Furious Rise", "Wolfwillow Haven", "Mire Triton", "Impending Doom", "Gray Merchant of Asphodel", "Shoal Kraken", "Archon of Falling Stars", "Mystic Repeal", "Staggering Insight", "One with the Stars", "Alseid of Life's Bounty", "Entrancing Lyre", "Callaphe, Beloved of the Sea", "Nyx Herald", "Slaughter-Priest of Mogis", "Reverent Hoplite", "Renata, Called to the Hunt", "Agonizing Remorse", "Heroes of the Revel", "Acolyte of Affliction", "Alirios, Enraptured", "Hero of the Nyxborn", "Chainweb Aracnir", "Dreamstalker Manticore", "Phalanx Tactics", "Drag to the Underworld", "Pheres-Band Brawler", "Elspeth's Nightmare", "Anax, Hardened in the Forge", "Devourer of Memory", "Setessan Petitioner", "Whirlwind Denial", "Lagonna-Band Storyteller", "Archon of Falling Stars", "Shimmerwing Chimera", "Fateful End", "Wolfwillow Haven");
        private static final TherosBeyondDeathRun uncommonB = new TherosBeyondDeathRun(true, "Siona, Captain of the Pyleas", "Hateful Eidolon", "Blood Aspirant", "Nessian Wanderer", "Heliod's Punishment", "Mirror Shield", "Cling to Dust", "Glimpse of Freedom", "Field of Ruin", "Klothys's Design", "Thundering Chariot", "Escape Velocity", "Daxos, Blessed by the Sun", "Eutropia the Twice-Favored", "Sage of Mysteries", "Tymaret, Chosen from Death", "Hydra's Growth", "The Triumph of Anax", "Minion's Return", "Stinging Lionfish", "Destiny Spinner", "Rise to Glory", "Dreamshaper Shaman", "Soul-Guide Lantern", "Sea God's Scorn", "Favored of Iroas", "Inevitable End", "The Binding of the Titans", "The Birth of Meletis", "Careless Celebrant", "Underworld Dreams", "Medomai's Prophecy", "Thundering Chariot", "Sweet Oblivion", "Nessian Hornbeetle", "Blood Aspirant", "Heliod's Punishment", "Mirror Shield", "Enemy of Enlightenment", "Stinging Lionfish", "Hateful Eidolon", "The Triumph of Anax", "Hero of the Winds", "Warden of the Chained", "Threnody Singer", "Dreamshaper Shaman", "Minion's Return", "Hydra's Growth", "Glimpse of Freedom", "Commanding Presence", "Underworld Fires", "Cling to Dust", "Nessian Wanderer", "Siona, Captain of the Pyleas", "Sage of Mysteries", "Eutropia the Twice-Favored", "Daxos, Blessed by the Sun", "Escape Velocity", "Klothys's Design", "Soul-Guide Lantern", "Tymaret, Chosen from Death", "Favored of Iroas", "Field of Ruin", "Sea God's Scorn", "The Birth of Meletis", "Rise to Glory", "Destiny Spinner", "Careless Celebrant", "Underworld Dreams", "Medomai's Prophecy", "Warden of the Chained", "Heliod's Punishment", "Sweet Oblivion", "Inevitable End", "The Binding of the Titans", "Blood Aspirant", "Enemy of Enlightenment", "Mirror Shield", "Hero of the Winds", "Nessian Wanderer", "The Triumph of Anax", "Minion's Return", "Threnody Singer", "Siona, Captain of the Pyleas", "Underworld Fires", "Commanding Presence", "Thundering Chariot", "Nessian Hornbeetle", "Daxos, Blessed by the Sun", "Escape Velocity", "Hateful Eidolon", "Stinging Lionfish", "Hydra's Growth", "Eutropia the Twice-Favored", "Field of Ruin", "Glimpse of Freedom", "Klothys's Design", "Cling to Dust", "Rise to Glory", "Sage of Mysteries", "Favored of Iroas", "Destiny Spinner", "Tymaret, Chosen from Death", "Soul-Guide Lantern", "Dreamshaper Shaman", "The Birth of Meletis", "Sweet Oblivion", "Inevitable End", "The Binding of the Titans", "Sea God's Scorn", "Hero of the Winds", "Careless Celebrant", "Underworld Dreams", "Medomai's Prophecy", "Nessian Hornbeetle", "Commanding Presence", "Underworld Fires", "Warden of the Chained", "Enemy of Enlightenment", "Threnody Singer");
        private static final TherosBeyondDeathRun rare = new TherosBeyondDeathRun(false, "Allure of the Unknown", "Aphemia, the Cacophony", "Arasta of the Endless Web", "Archon of Sun's Grace", "Ashiok's Erasure", "Atris, Oracle of Half-Truths", "Bronzehide Lion", "Dalakos, Crafter of Wonders", "Dream Trawler", "Dryad of the Ilysian Grove", "Eat to Extinction", "Eidolon of Obstruction", "Elspeth Conquers Death", "Enigmatic Incarnation", "Erebos's Intervention", "Gallia of the Endless Dance", "Gravebreaker Lamia", "Haktos the Unscarred", "Heliod's Intervention", "Idyllic Tutor", "Kunoros, Hound of Athreos", "Labyrinth of Skophos", "Mantle of the Wolf", "Nadir Kraken", "Nessian Boar", "Nightmare Shepherd", "Nylea's Intervention", "Nyx Lotus", "Phoenix of Ash", "Protean Thaumaturge", "Purphoros's Intervention", "Setessan Champion", "Shadowspear", "Shatter the Sky", "Storm Herald", "Storm's Wrath", "Taranika, Akroan Veteran", "Tectonic Giant", "Temple of Abandon", "Temple of Deceit", "Temple of Enlightenment", "Temple of Malice", "Temple of Plenty", "Thassa's Intervention", "Thassa's Oracle", "The Akroan War", "The First Iroan Games", "Thryx, the Sudden Storm", "Treacherous Blessing", "Tymaret Calls the Dead", "Underworld Breach", "Wavebreak Hippocamp", "Woe Strider");
        private static final TherosBeyondDeathRun mythic = new TherosBeyondDeathRun(false, "Ashiok, Nightmare Muse", "Calix, Destiny's Hand", "Elspeth, Sun's Nemesis", "Erebos, Bleak-Hearted", "Heliod, Sun-Crowned", "Kiora Bests the Sea God", "Klothys, God of Destiny", "Kroxa, Titan of Death's Hunger", "Nylea, Keen-Eyed", "Nyxbloom Ancient", "Ox of Agonas", "Polukranos, Unchained", "Purphoros, Bronze-Blooded", "Thassa, Deep-Dwelling", "Uro, Titan of Nature's Wrath");
        private static final TherosBeyondDeathRun land = new TherosBeyondDeathRun(false, "Plains", "Island", "Swamp", "Mountain", "Forest");

        private TherosBeyondDeathRun(boolean keepOrder, String... names) {
            super(keepOrder, names);
        }
    }

    private static class TherosBeyondDeathStructure extends BoosterStructure {
        private static final TherosBeyondDeathStructure C1 = new TherosBeyondDeathStructure(
                TherosBeyondDeathRun.commonA,
                TherosBeyondDeathRun.commonA,
                TherosBeyondDeathRun.commonB,
                TherosBeyondDeathRun.commonB,
                TherosBeyondDeathRun.commonC1,
                TherosBeyondDeathRun.commonC1,
                TherosBeyondDeathRun.commonC1,
                TherosBeyondDeathRun.commonC1,
                TherosBeyondDeathRun.commonC1,
                TherosBeyondDeathRun.commonC1
        );
        private static final TherosBeyondDeathStructure C2 = new TherosBeyondDeathStructure(
                TherosBeyondDeathRun.commonA,
                TherosBeyondDeathRun.commonA,
                TherosBeyondDeathRun.commonA,
                TherosBeyondDeathRun.commonB,
                TherosBeyondDeathRun.commonB,
                TherosBeyondDeathRun.commonC1,
                TherosBeyondDeathRun.commonC1,
                TherosBeyondDeathRun.commonC1,
                TherosBeyondDeathRun.commonC1,
                TherosBeyondDeathRun.commonC1
        );
        private static final TherosBeyondDeathStructure C3 = new TherosBeyondDeathStructure(
                TherosBeyondDeathRun.commonA,
                TherosBeyondDeathRun.commonA,
                TherosBeyondDeathRun.commonA,
                TherosBeyondDeathRun.commonA,
                TherosBeyondDeathRun.commonB,
                TherosBeyondDeathRun.commonB,
                TherosBeyondDeathRun.commonC2,
                TherosBeyondDeathRun.commonC2,
                TherosBeyondDeathRun.commonC2,
                TherosBeyondDeathRun.commonC2
        );
        private static final TherosBeyondDeathStructure C4 = new TherosBeyondDeathStructure(
                TherosBeyondDeathRun.commonA,
                TherosBeyondDeathRun.commonA,
                TherosBeyondDeathRun.commonA,
                TherosBeyondDeathRun.commonA,
                TherosBeyondDeathRun.commonB,
                TherosBeyondDeathRun.commonB,
                TherosBeyondDeathRun.commonB,
                TherosBeyondDeathRun.commonC2,
                TherosBeyondDeathRun.commonC2,
                TherosBeyondDeathRun.commonC2
        );
        private static final TherosBeyondDeathStructure C5 = new TherosBeyondDeathStructure(
                TherosBeyondDeathRun.commonA,
                TherosBeyondDeathRun.commonA,
                TherosBeyondDeathRun.commonA,
                TherosBeyondDeathRun.commonA,
                TherosBeyondDeathRun.commonB,
                TherosBeyondDeathRun.commonB,
                TherosBeyondDeathRun.commonB,
                TherosBeyondDeathRun.commonB,
                TherosBeyondDeathRun.commonC2,
                TherosBeyondDeathRun.commonC2
        );
        private static final TherosBeyondDeathStructure U1 = new TherosBeyondDeathStructure(
                TherosBeyondDeathRun.uncommonA,
                TherosBeyondDeathRun.uncommonB,
                TherosBeyondDeathRun.uncommonB
        );
        private static final TherosBeyondDeathStructure U2 = new TherosBeyondDeathStructure(
                TherosBeyondDeathRun.uncommonA,
                TherosBeyondDeathRun.uncommonA,
                TherosBeyondDeathRun.uncommonB
        );
        private static final TherosBeyondDeathStructure R1 = new TherosBeyondDeathStructure(
                TherosBeyondDeathRun.rare
        );
        private static final TherosBeyondDeathStructure M1 = new TherosBeyondDeathStructure(
                TherosBeyondDeathRun.mythic
        );
        private static final TherosBeyondDeathStructure L1 = new TherosBeyondDeathStructure(
                TherosBeyondDeathRun.land
        );

        private TherosBeyondDeathStructure(CardRun... runs) {
            super(runs);
        }
    }

    private final RarityConfiguration commonRuns = new RarityConfiguration(
            false,
            TherosBeyondDeathStructure.C1,
            TherosBeyondDeathStructure.C2,
            TherosBeyondDeathStructure.C3,
            TherosBeyondDeathStructure.C4,
            TherosBeyondDeathStructure.C5,
            TherosBeyondDeathStructure.C1,
            TherosBeyondDeathStructure.C2,
            TherosBeyondDeathStructure.C3,
            TherosBeyondDeathStructure.C4,
            TherosBeyondDeathStructure.C5,
            TherosBeyondDeathStructure.C4,
            TherosBeyondDeathStructure.C5
    );
    private final RarityConfiguration uncommonRuns = new RarityConfiguration(
            TherosBeyondDeathStructure.U1,
            TherosBeyondDeathStructure.U2
    );
    private final RarityConfiguration rareRuns = new RarityConfiguration(
            false,
            TherosBeyondDeathStructure.R1,
            TherosBeyondDeathStructure.R1,
            TherosBeyondDeathStructure.R1,
            TherosBeyondDeathStructure.R1,
            TherosBeyondDeathStructure.R1,
            TherosBeyondDeathStructure.R1,
            TherosBeyondDeathStructure.R1,
            TherosBeyondDeathStructure.M1
    );
    private final RarityConfiguration landRuns = new RarityConfiguration(
            TherosBeyondDeathStructure.L1
    );


    @Override
    public void shuffle() {
        commonRuns.shuffle();
        uncommonRuns.shuffle();
        rareRuns.shuffle();
        landRuns.shuffle();
    }

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
