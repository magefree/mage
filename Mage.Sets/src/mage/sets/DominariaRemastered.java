package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;
import mage.collation.BoosterCollator;
import mage.collation.BoosterStructure;
import mage.collation.CardRun;
import mage.collation.RarityConfiguration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author TheElk801
 */
public class DominariaRemastered extends ExpansionSet {

    private static final DominariaRemastered instance = new DominariaRemastered();

    public static DominariaRemastered getInstance() {
        return instance;
    }

    private DominariaRemastered() {
        super("Dominaria Remastered", "DMR", ExpansionSet.buildDate(2023, 1, 13), SetType.SUPPLEMENTAL);
        this.hasBoosters = true;
        this.hasBasicLands = true;
        this.maxCardNumberInBooster = 261;
        this.numBoosterCommon = 10;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 7; // 60 rare, 20 mythic

        cards.add(new SetCardInfo("Absorb", 186, Rarity.RARE, mage.cards.a.Absorb.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Absorb", 354, Rarity.RARE, mage.cards.a.Absorb.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Absorb", 443, Rarity.RARE, mage.cards.a.Absorb.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Aquamoeba", 38, Rarity.COMMON, mage.cards.a.Aquamoeba.class));
        cards.add(new SetCardInfo("Arboria", 149, Rarity.RARE, mage.cards.a.Arboria.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Arboria", 335, Rarity.RARE, mage.cards.a.Arboria.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Arboria", 438, Rarity.RARE, mage.cards.a.Arboria.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Arcades Sabboth", 187, Rarity.RARE, mage.cards.a.ArcadesSabboth.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Arcades Sabboth", 355, Rarity.RARE, mage.cards.a.ArcadesSabboth.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Arcanis the Omnipotent", 39, Rarity.RARE, mage.cards.a.ArcanisTheOmnipotent.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Arcanis the Omnipotent", 280, Rarity.RARE, mage.cards.a.ArcanisTheOmnipotent.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Assault // Battery", 210, Rarity.UNCOMMON, mage.cards.a.AssaultBattery.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Auramancer", 1, Rarity.COMMON, mage.cards.a.Auramancer.class));
        cards.add(new SetCardInfo("Avarax", 112, Rarity.COMMON, mage.cards.a.Avarax.class));
        cards.add(new SetCardInfo("Aven Fateshaper", 40, Rarity.UNCOMMON, mage.cards.a.AvenFateshaper.class));
        cards.add(new SetCardInfo("Aven Fisher", 41, Rarity.COMMON, mage.cards.a.AvenFisher.class));
        cards.add(new SetCardInfo("Battle Screech", 2, Rarity.UNCOMMON, mage.cards.b.BattleScreech.class));
        cards.add(new SetCardInfo("Battlefield Scrounger", 150, Rarity.COMMON, mage.cards.b.BattlefieldScrounger.class));
        cards.add(new SetCardInfo("Birds of Paradise", 151, Rarity.RARE, mage.cards.b.BirdsOfParadise.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Birds of Paradise", 336, Rarity.RARE, mage.cards.b.BirdsOfParadise.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Birds of Paradise", 439, Rarity.RARE, mage.cards.b.BirdsOfParadise.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Body Snatcher", 75, Rarity.RARE, mage.cards.b.BodySnatcher.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Body Snatcher", 298, Rarity.RARE, mage.cards.b.BodySnatcher.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Break Asunder", 152, Rarity.COMMON, mage.cards.b.BreakAsunder.class));
        cards.add(new SetCardInfo("Cackling Fiend", 76, Rarity.COMMON, mage.cards.c.CacklingFiend.class));
        cards.add(new SetCardInfo("Call of the Herd", 153, Rarity.UNCOMMON, mage.cards.c.CallOfTheHerd.class));
        cards.add(new SetCardInfo("Chain Lightning", 113, Rarity.COMMON, mage.cards.c.ChainLightning.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Chain Lightning", 316, Rarity.COMMON, mage.cards.c.ChainLightning.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Chainer's Edict", 78, Rarity.UNCOMMON, mage.cards.c.ChainersEdict.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Chainer's Edict", 300, Rarity.UNCOMMON, mage.cards.c.ChainersEdict.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Chainer's Edict", 425, Rarity.UNCOMMON, mage.cards.c.ChainersEdict.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Chainer, Dementia Master", 77, Rarity.RARE, mage.cards.c.ChainerDementiaMaster.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Chainer, Dementia Master", 299, Rarity.RARE, mage.cards.c.ChainerDementiaMaster.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Chainer, Dementia Master", 424, Rarity.RARE, mage.cards.c.ChainerDementiaMaster.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Circular Logic", 42, Rarity.UNCOMMON, mage.cards.c.CircularLogic.class));
        cards.add(new SetCardInfo("Cleric of the Forward Order", 3, Rarity.COMMON, mage.cards.c.ClericOfTheForwardOrder.class));
        cards.add(new SetCardInfo("Clifftop Retreat", 241, Rarity.RARE, mage.cards.c.ClifftopRetreat.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Clifftop Retreat", 393, Rarity.RARE, mage.cards.c.ClifftopRetreat.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Cloud of Faeries", 43, Rarity.COMMON, mage.cards.c.CloudOfFaeries.class));
        cards.add(new SetCardInfo("Coal Stoker", 114, Rarity.COMMON, mage.cards.c.CoalStoker.class));
        cards.add(new SetCardInfo("Confiscate", 44, Rarity.UNCOMMON, mage.cards.c.Confiscate.class));
        cards.add(new SetCardInfo("Congregate", 4, Rarity.UNCOMMON, mage.cards.c.Congregate.class));
        cards.add(new SetCardInfo("Counterspell", 45, Rarity.COMMON, mage.cards.c.Counterspell.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Counterspell", 281, Rarity.COMMON, mage.cards.c.Counterspell.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Counterspell", 457, Rarity.RARE, mage.cards.c.Counterspell.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Crawlspace", 217, Rarity.RARE, mage.cards.c.Crawlspace.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Crawlspace", 375, Rarity.RARE, mage.cards.c.Crawlspace.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Crop Rotation", 154, Rarity.UNCOMMON, mage.cards.c.CropRotation.class));
        cards.add(new SetCardInfo("Crosis's Catacombs", 242, Rarity.UNCOMMON, mage.cards.c.CrosissCatacombs.class));
        cards.add(new SetCardInfo("Cryptic Gateway", 218, Rarity.RARE, mage.cards.c.CrypticGateway.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Cryptic Gateway", 376, Rarity.RARE, mage.cards.c.CrypticGateway.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Damping Sphere", 219, Rarity.UNCOMMON, mage.cards.d.DampingSphere.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Damping Sphere", 377, Rarity.UNCOMMON, mage.cards.d.DampingSphere.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Darigaaz's Caldera", 243, Rarity.UNCOMMON, mage.cards.d.DarigaazsCaldera.class));
        cards.add(new SetCardInfo("Dark Depths", 244, Rarity.MYTHIC, mage.cards.d.DarkDepths.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dark Depths", 394, Rarity.MYTHIC, mage.cards.d.DarkDepths.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Dark Depths", 454, Rarity.MYTHIC, mage.cards.d.DarkDepths.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dark Withering", 79, Rarity.UNCOMMON, mage.cards.d.DarkWithering.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dark Withering", 301, Rarity.UNCOMMON, mage.cards.d.DarkWithering.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Deadapult", 115, Rarity.UNCOMMON, mage.cards.d.Deadapult.class));
        cards.add(new SetCardInfo("Deadwood Treefolk", 155, Rarity.UNCOMMON, mage.cards.d.DeadwoodTreefolk.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Deadwood Treefolk", 337, Rarity.UNCOMMON, mage.cards.d.DeadwoodTreefolk.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Decimate", 188, Rarity.RARE, mage.cards.d.Decimate.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Decimate", 356, Rarity.RARE, mage.cards.d.Decimate.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Decimate", 444, Rarity.RARE, mage.cards.d.Decimate.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Deep Analysis", 46, Rarity.COMMON, mage.cards.d.DeepAnalysis.class));
        cards.add(new SetCardInfo("Denizen of the Deep", 47, Rarity.RARE, mage.cards.d.DenizenOfTheDeep.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Denizen of the Deep", 282, Rarity.RARE, mage.cards.d.DenizenOfTheDeep.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Denizen of the Deep", 417, Rarity.RARE, mage.cards.d.DenizenOfTheDeep.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Divine Sacrament", 5, Rarity.RARE, mage.cards.d.DivineSacrament.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Divine Sacrament", 262, Rarity.RARE, mage.cards.d.DivineSacrament.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Dodecapod", 220, Rarity.UNCOMMON, mage.cards.d.Dodecapod.class));
        cards.add(new SetCardInfo("Dragon Blood", 221, Rarity.UNCOMMON, mage.cards.d.DragonBlood.class));
        cards.add(new SetCardInfo("Dragon Engine", 222, Rarity.COMMON, mage.cards.d.DragonEngine.class));
        cards.add(new SetCardInfo("Dragon Whelp", 116, Rarity.UNCOMMON, mage.cards.d.DragonWhelp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dragon Whelp", 317, Rarity.UNCOMMON, mage.cards.d.DragonWhelp.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Dralnu's Crusade", 189, Rarity.UNCOMMON, mage.cards.d.DralnusCrusade.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dralnu's Crusade", 357, Rarity.UNCOMMON, mage.cards.d.DralnusCrusade.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Dread Return", 80, Rarity.UNCOMMON, mage.cards.d.DreadReturn.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dread Return", 302, Rarity.UNCOMMON, mage.cards.d.DreadReturn.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Drifting Meadow", 245, Rarity.COMMON, mage.cards.d.DriftingMeadow.class));
        cards.add(new SetCardInfo("Dromar's Cavern", 246, Rarity.UNCOMMON, mage.cards.d.DromarsCavern.class));
        cards.add(new SetCardInfo("Duress", 81, Rarity.COMMON, mage.cards.d.Duress.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Duress", 303, Rarity.COMMON, mage.cards.d.Duress.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Elvish Aberration", 156, Rarity.COMMON, mage.cards.e.ElvishAberration.class));
        cards.add(new SetCardInfo("Elvish Spirit Guide", 157, Rarity.UNCOMMON, mage.cards.e.ElvishSpiritGuide.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Elvish Spirit Guide", 338, Rarity.UNCOMMON, mage.cards.e.ElvishSpiritGuide.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Ember Beast", 117, Rarity.COMMON, mage.cards.e.EmberBeast.class));
        cards.add(new SetCardInfo("Emerald Charm", 158, Rarity.COMMON, mage.cards.e.EmeraldCharm.class));
        cards.add(new SetCardInfo("Empty the Warrens", 118, Rarity.COMMON, mage.cards.e.EmptyTheWarrens.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Empty the Warrens", 318, Rarity.COMMON, mage.cards.e.EmptyTheWarrens.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Enlightened Tutor", 6, Rarity.RARE, mage.cards.e.EnlightenedTutor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Enlightened Tutor", 263, Rarity.RARE, mage.cards.e.EnlightenedTutor.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Enlightened Tutor", 412, Rarity.RARE, mage.cards.e.EnlightenedTutor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Entomb", 82, Rarity.RARE, mage.cards.e.Entomb.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Entomb", 304, Rarity.RARE, mage.cards.e.Entomb.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Entomb", 426, Rarity.RARE, mage.cards.e.Entomb.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Evil Eye of Orms-by-Gore", 83, Rarity.COMMON, mage.cards.e.EvilEyeOfOrmsByGore.class));
        cards.add(new SetCardInfo("Exploration", 159, Rarity.RARE, mage.cards.e.Exploration.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Exploration", 339, Rarity.RARE, mage.cards.e.Exploration.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Fa'adiyah Seer", 160, Rarity.COMMON, mage.cards.f.FaadiyahSeer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Fa'adiyah Seer", 340, Rarity.COMMON, mage.cards.f.FaadiyahSeer.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Faceless Butcher", 84, Rarity.UNCOMMON, mage.cards.f.FacelessButcher.class));
        cards.add(new SetCardInfo("Fact or Fiction", 48, Rarity.UNCOMMON, mage.cards.f.FactOrFiction.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Fact or Fiction", 283, Rarity.UNCOMMON, mage.cards.f.FactOrFiction.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Festering Goblin", 85, Rarity.COMMON, mage.cards.f.FesteringGoblin.class));
        cards.add(new SetCardInfo("Fire // Ice", 215, Rarity.UNCOMMON, mage.cards.f.FireIce.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Fireblast", 119, Rarity.UNCOMMON, mage.cards.f.Fireblast.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Fireblast", 319, Rarity.UNCOMMON, mage.cards.f.Fireblast.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Flametongue Kavu", 120, Rarity.UNCOMMON, mage.cards.f.FlametongueKavu.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Flametongue Kavu", 320, Rarity.UNCOMMON, mage.cards.f.FlametongueKavu.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Flametongue Kavu", 432, Rarity.UNCOMMON, mage.cards.f.FlametongueKavu.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Flesh Reaver", 86, Rarity.UNCOMMON, mage.cards.f.FleshReaver.class));
        cards.add(new SetCardInfo("Floodgate", 49, Rarity.UNCOMMON, mage.cards.f.Floodgate.class));
        cards.add(new SetCardInfo("Force of Will", 50, Rarity.MYTHIC, mage.cards.f.ForceOfWill.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Force of Will", 284, Rarity.MYTHIC, mage.cards.f.ForceOfWill.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Force of Will", 418, Rarity.MYTHIC, mage.cards.f.ForceOfWill.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 410, Rarity.LAND, mage.cards.basiclands.Forest.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 411, Rarity.LAND, mage.cards.basiclands.Forest.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Forgotten Ancient", 161, Rarity.RARE, mage.cards.f.ForgottenAncient.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forgotten Ancient", 341, Rarity.RARE, mage.cards.f.ForgottenAncient.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Frantic Search", 51, Rarity.COMMON, mage.cards.f.FranticSearch.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Frantic Search", 285, Rarity.COMMON, mage.cards.f.FranticSearch.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Gamble", 121, Rarity.RARE, mage.cards.g.Gamble.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gamble", 321, Rarity.RARE, mage.cards.g.Gamble.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Gamble", 433, Rarity.RARE, mage.cards.g.Gamble.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gamekeeper", 162, Rarity.UNCOMMON, mage.cards.g.Gamekeeper.class));
        cards.add(new SetCardInfo("Gauntlet of Power", 223, Rarity.MYTHIC, mage.cards.g.GauntletOfPower.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gauntlet of Power", 378, Rarity.MYTHIC, mage.cards.g.GauntletOfPower.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Gauntlet of Power", 447, Rarity.MYTHIC, mage.cards.g.GauntletOfPower.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gempalm Incinerator", 122, Rarity.UNCOMMON, mage.cards.g.GempalmIncinerator.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gempalm Incinerator", 322, Rarity.UNCOMMON, mage.cards.g.GempalmIncinerator.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Gemstone Mine", 247, Rarity.RARE, mage.cards.g.GemstoneMine.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gemstone Mine", 395, Rarity.RARE, mage.cards.g.GemstoneMine.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Gemstone Mine", 455, Rarity.RARE, mage.cards.g.GemstoneMine.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gerrard's Verdict", 190, Rarity.UNCOMMON, mage.cards.g.GerrardsVerdict.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gerrard's Verdict", 358, Rarity.UNCOMMON, mage.cards.g.GerrardsVerdict.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Giant Spider", 163, Rarity.COMMON, mage.cards.g.GiantSpider.class));
        cards.add(new SetCardInfo("Glintwing Invoker", 52, Rarity.COMMON, mage.cards.g.GlintwingInvoker.class));
        cards.add(new SetCardInfo("Glory", 7, Rarity.RARE, mage.cards.g.Glory.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Glory", 264, Rarity.RARE, mage.cards.g.Glory.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Goblin Matron", 123, Rarity.COMMON, mage.cards.g.GoblinMatron.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Goblin Matron", 323, Rarity.COMMON, mage.cards.g.GoblinMatron.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Goblin Medics", 124, Rarity.COMMON, mage.cards.g.GoblinMedics.class));
        cards.add(new SetCardInfo("Goblin Turncoat", 87, Rarity.COMMON, mage.cards.g.GoblinTurncoat.class));
        cards.add(new SetCardInfo("Grapeshot", 125, Rarity.COMMON, mage.cards.g.Grapeshot.class));
        cards.add(new SetCardInfo("Griffin Guide", 8, Rarity.UNCOMMON, mage.cards.g.GriffinGuide.class));
        cards.add(new SetCardInfo("Grim Lavamancer", 126, Rarity.RARE, mage.cards.g.GrimLavamancer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Grim Lavamancer", 324, Rarity.RARE, mage.cards.g.GrimLavamancer.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Grim Lavamancer", 434, Rarity.RARE, mage.cards.g.GrimLavamancer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Helm of Awakening", 224, Rarity.RARE, mage.cards.h.HelmOfAwakening.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Helm of Awakening", 379, Rarity.RARE, mage.cards.h.HelmOfAwakening.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Helm of Awakening", 448, Rarity.RARE, mage.cards.h.HelmOfAwakening.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hermetic Study", 53, Rarity.COMMON, mage.cards.h.HermeticStudy.class));
        cards.add(new SetCardInfo("High Tide", 54, Rarity.UNCOMMON, mage.cards.h.HighTide.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("High Tide", 286, Rarity.UNCOMMON, mage.cards.h.HighTide.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("High Tide", 419, Rarity.UNCOMMON, mage.cards.h.HighTide.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hinterland Harbor", 248, Rarity.RARE, mage.cards.h.HinterlandHarbor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hinterland Harbor", 396, Rarity.RARE, mage.cards.h.HinterlandHarbor.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Horseshoe Crab", 55, Rarity.COMMON, mage.cards.h.HorseshoeCrab.class));
        cards.add(new SetCardInfo("Howl from Beyond", 88, Rarity.COMMON, mage.cards.h.HowlFromBeyond.class));
        cards.add(new SetCardInfo("Hunting Grounds", 191, Rarity.MYTHIC, mage.cards.h.HuntingGrounds.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hunting Grounds", 359, Rarity.MYTHIC, mage.cards.h.HuntingGrounds.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Hunting Grounds", 445, Rarity.MYTHIC, mage.cards.h.HuntingGrounds.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hyalopterous Lemure", 89, Rarity.COMMON, mage.cards.h.HyalopterousLemure.class));
        cards.add(new SetCardInfo("Icatian Javelineers", 9, Rarity.COMMON, mage.cards.i.IcatianJavelineers.class));
        cards.add(new SetCardInfo("Ichor Slick", 90, Rarity.COMMON, mage.cards.i.IchorSlick.class));
        cards.add(new SetCardInfo("Icy Manipulator", 225, Rarity.UNCOMMON, mage.cards.i.IcyManipulator.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Icy Manipulator", 380, Rarity.UNCOMMON, mage.cards.i.IcyManipulator.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Illusion // Reality", 213, Rarity.UNCOMMON, mage.cards.i.IllusionReality.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Improvised Armor", 10, Rarity.UNCOMMON, mage.cards.i.ImprovisedArmor.class));
        cards.add(new SetCardInfo("Impulse", 56, Rarity.COMMON, mage.cards.i.Impulse.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Impulse", 287, Rarity.COMMON, mage.cards.i.Impulse.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Invigorating Boon", 164, Rarity.UNCOMMON, mage.cards.i.InvigoratingBoon.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Invigorating Boon", 342, Rarity.UNCOMMON, mage.cards.i.InvigoratingBoon.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 404, Rarity.LAND, mage.cards.basiclands.Island.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 405, Rarity.LAND, mage.cards.basiclands.Island.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Isolated Chapel", 249, Rarity.RARE, mage.cards.i.IsolatedChapel.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Isolated Chapel", 397, Rarity.RARE, mage.cards.i.IsolatedChapel.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Jalum Tome", 226, Rarity.COMMON, mage.cards.j.JalumTome.class));
        cards.add(new SetCardInfo("Jester's Cap", 227, Rarity.RARE, mage.cards.j.JestersCap.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Jester's Cap", 381, Rarity.RARE, mage.cards.j.JestersCap.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Jester's Cap", 449, Rarity.RARE, mage.cards.j.JestersCap.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Jolrael, Mwonvuli Recluse", 165, Rarity.RARE, mage.cards.j.JolraelMwonvuliRecluse.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Jolrael, Mwonvuli Recluse", 343, Rarity.RARE, mage.cards.j.JolraelMwonvuliRecluse.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Juggernaut", 228, Rarity.COMMON, mage.cards.j.Juggernaut.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Juggernaut", 382, Rarity.COMMON, mage.cards.j.Juggernaut.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Kamahl, Fist of Krosa", 166, Rarity.MYTHIC, mage.cards.k.KamahlFistOfKrosa.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kamahl, Fist of Krosa", 344, Rarity.MYTHIC, mage.cards.k.KamahlFistOfKrosa.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Kavu Primarch", 167, Rarity.COMMON, mage.cards.k.KavuPrimarch.class));
        cards.add(new SetCardInfo("Kjeldoran Gargoyle", 11, Rarity.COMMON, mage.cards.k.KjeldoranGargoyle.class));
        cards.add(new SetCardInfo("Krosan Restorer", 168, Rarity.COMMON, mage.cards.k.KrosanRestorer.class));
        cards.add(new SetCardInfo("Last Chance", 127, Rarity.MYTHIC, mage.cards.l.LastChance.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Last Chance", 325, Rarity.MYTHIC, mage.cards.l.LastChance.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Last Chance", 435, Rarity.MYTHIC, mage.cards.l.LastChance.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Leaden Fists", 57, Rarity.COMMON, mage.cards.l.LeadenFists.class));
        cards.add(new SetCardInfo("Legacy Weapon", 229, Rarity.MYTHIC, mage.cards.l.LegacyWeapon.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Legacy Weapon", 383, Rarity.MYTHIC, mage.cards.l.LegacyWeapon.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Legacy Weapon", 450, Rarity.MYTHIC, mage.cards.l.LegacyWeapon.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Lieutenant Kirtar", 12, Rarity.RARE, mage.cards.l.LieutenantKirtar.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Lieutenant Kirtar", 265, Rarity.RARE, mage.cards.l.LieutenantKirtar.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Life // Death", 216, Rarity.UNCOMMON, mage.cards.l.LifeDeath.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Lightning Reflexes", 128, Rarity.COMMON, mage.cards.l.LightningReflexes.class));
        cards.add(new SetCardInfo("Lightning Rift", 129, Rarity.UNCOMMON, mage.cards.l.LightningRift.class));
        cards.add(new SetCardInfo("Lotus Blossom", 230, Rarity.RARE, mage.cards.l.LotusBlossom.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Lotus Blossom", 384, Rarity.RARE, mage.cards.l.LotusBlossom.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Lotus Blossom", 451, Rarity.RARE, mage.cards.l.LotusBlossom.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Lull", 169, Rarity.COMMON, mage.cards.l.Lull.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Lull", 345, Rarity.COMMON, mage.cards.l.Lull.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Lyra Dawnbringer", 13, Rarity.MYTHIC, mage.cards.l.LyraDawnbringer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Lyra Dawnbringer", 266, Rarity.MYTHIC, mage.cards.l.LyraDawnbringer.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Lyra Dawnbringer", 413, Rarity.MYTHIC, mage.cards.l.LyraDawnbringer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Macetail Hystrodon", 130, Rarity.COMMON, mage.cards.m.MacetailHystrodon.class));
        cards.add(new SetCardInfo("Man-o'-War", 58, Rarity.COMMON, mage.cards.m.ManOWar.class));
        cards.add(new SetCardInfo("Maze of Ith", 250, Rarity.RARE, mage.cards.m.MazeOfIth.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Maze of Ith", 398, Rarity.RARE, mage.cards.m.MazeOfIth.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Maze of Ith", 456, Rarity.RARE, mage.cards.m.MazeOfIth.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mesa Enchantress", 14, Rarity.UNCOMMON, mage.cards.m.MesaEnchantress.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mesa Enchantress", 267, Rarity.UNCOMMON, mage.cards.m.MesaEnchantress.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Millikin", 231, Rarity.UNCOMMON, mage.cards.m.Millikin.class));
        cards.add(new SetCardInfo("Mind Stone", 232, Rarity.COMMON, mage.cards.m.MindStone.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mind Stone", 385, Rarity.COMMON, mage.cards.m.MindStone.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Mindslicer", 91, Rarity.RARE, mage.cards.m.Mindslicer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mindslicer", 305, Rarity.RARE, mage.cards.m.Mindslicer.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Mishra's Factory", 251, Rarity.UNCOMMON, mage.cards.m.MishrasFactory.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mishra's Factory", 399, Rarity.UNCOMMON, mage.cards.m.MishrasFactory.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Mogg War Marshal", 131, Rarity.COMMON, mage.cards.m.MoggWarMarshal.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mogg War Marshal", 326, Rarity.COMMON, mage.cards.m.MoggWarMarshal.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Momentary Blink", 15, Rarity.COMMON, mage.cards.m.MomentaryBlink.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Momentary Blink", 268, Rarity.COMMON, mage.cards.m.MomentaryBlink.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 408, Rarity.LAND, mage.cards.basiclands.Mountain.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 409, Rarity.LAND, mage.cards.basiclands.Mountain.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Mystic Enforcer", 192, Rarity.UNCOMMON, mage.cards.m.MysticEnforcer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mystic Enforcer", 360, Rarity.UNCOMMON, mage.cards.m.MysticEnforcer.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Mystic Remora", 59, Rarity.RARE, mage.cards.m.MysticRemora.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mystic Remora", 288, Rarity.RARE, mage.cards.m.MysticRemora.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Mystic Remora", 420, Rarity.RARE, mage.cards.m.MysticRemora.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mystic Zealot", 16, Rarity.COMMON, mage.cards.m.MysticZealot.class));
        cards.add(new SetCardInfo("Mystical Tutor", 60, Rarity.RARE, mage.cards.m.MysticalTutor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mystical Tutor", 289, Rarity.RARE, mage.cards.m.MysticalTutor.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Mystical Tutor", 421, Rarity.RARE, mage.cards.m.MysticalTutor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Nantuko Monastery", 252, Rarity.UNCOMMON, mage.cards.n.NantukoMonastery.class));
        cards.add(new SetCardInfo("Nantuko Shade", 92, Rarity.RARE, mage.cards.n.NantukoShade.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Nantuko Shade", 306, Rarity.RARE, mage.cards.n.NantukoShade.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Nature's Lore", 170, Rarity.UNCOMMON, mage.cards.n.NaturesLore.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Nature's Lore", 346, Rarity.UNCOMMON, mage.cards.n.NaturesLore.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Necrosavant", 93, Rarity.UNCOMMON, mage.cards.n.Necrosavant.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Necrosavant", 307, Rarity.UNCOMMON, mage.cards.n.Necrosavant.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Night // Day", 214, Rarity.UNCOMMON, mage.cards.n.NightDay.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Nightscape Familiar", 94, Rarity.COMMON, mage.cards.n.NightscapeFamiliar.class));
        cards.add(new SetCardInfo("No Mercy", 95, Rarity.MYTHIC, mage.cards.n.NoMercy.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("No Mercy", 308, Rarity.MYTHIC, mage.cards.n.NoMercy.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("No Mercy", 427, Rarity.MYTHIC, mage.cards.n.NoMercy.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Nomad Decoy", 17, Rarity.COMMON, mage.cards.n.NomadDecoy.class));
        cards.add(new SetCardInfo("Nut Collector", 171, Rarity.MYTHIC, mage.cards.n.NutCollector.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Nut Collector", 347, Rarity.MYTHIC, mage.cards.n.NutCollector.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Nut Collector", 440, Rarity.MYTHIC, mage.cards.n.NutCollector.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Obsessive Search", 61, Rarity.COMMON, mage.cards.o.ObsessiveSearch.class));
        cards.add(new SetCardInfo("Opposition", 62, Rarity.RARE, mage.cards.o.Opposition.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Opposition", 290, Rarity.RARE, mage.cards.o.Opposition.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Order // Chaos", 212, Rarity.UNCOMMON, mage.cards.o.OrderChaos.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Orim's Thunder", 18, Rarity.COMMON, mage.cards.o.OrimsThunder.class));
        cards.add(new SetCardInfo("Ornithopter", 233, Rarity.COMMON, mage.cards.o.Ornithopter.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ornithopter", 386, Rarity.COMMON, mage.cards.o.Ornithopter.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Overmaster", 132, Rarity.RARE, mage.cards.o.Overmaster.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Overmaster", 327, Rarity.RARE, mage.cards.o.Overmaster.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Oversold Cemetery", 96, Rarity.RARE, mage.cards.o.OversoldCemetery.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Oversold Cemetery", 309, Rarity.RARE, mage.cards.o.OversoldCemetery.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Oversold Cemetery", 428, Rarity.RARE, mage.cards.o.OversoldCemetery.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ovinize", 63, Rarity.COMMON, mage.cards.o.Ovinize.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ovinize", 291, Rarity.COMMON, mage.cards.o.Ovinize.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Ovinomancer", 64, Rarity.UNCOMMON, mage.cards.o.Ovinomancer.class));
        cards.add(new SetCardInfo("Pacifism", 19, Rarity.COMMON, mage.cards.p.Pacifism.class));
        cards.add(new SetCardInfo("Pain // Suffering", 209, Rarity.UNCOMMON, mage.cards.p.PainSuffering.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Pashalik Mons", 133, Rarity.RARE, mage.cards.p.PashalikMons.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Pashalik Mons", 328, Rarity.RARE, mage.cards.p.PashalikMons.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Penumbra Bobcat", 172, Rarity.COMMON, mage.cards.p.PenumbraBobcat.class));
        cards.add(new SetCardInfo("Peregrine Drake", 65, Rarity.COMMON, mage.cards.p.PeregrineDrake.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Peregrine Drake", 292, Rarity.COMMON, mage.cards.p.PeregrineDrake.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Phantom Flock", 20, Rarity.COMMON, mage.cards.p.PhantomFlock.class));
        cards.add(new SetCardInfo("Phantom Nishoba", 193, Rarity.RARE, mage.cards.p.PhantomNishoba.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Phantom Nishoba", 361, Rarity.RARE, mage.cards.p.PhantomNishoba.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Phyrexian Debaser", 97, Rarity.COMMON, mage.cards.p.PhyrexianDebaser.class));
        cards.add(new SetCardInfo("Phyrexian Ghoul", 98, Rarity.COMMON, mage.cards.p.PhyrexianGhoul.class));
        cards.add(new SetCardInfo("Phyrexian Rager", 99, Rarity.COMMON, mage.cards.p.PhyrexianRager.class));
        cards.add(new SetCardInfo("Phyrexian Scuta", 100, Rarity.UNCOMMON, mage.cards.p.PhyrexianScuta.class));
        cards.add(new SetCardInfo("Plains", 402, Rarity.LAND, mage.cards.basiclands.Plains.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 403, Rarity.LAND, mage.cards.basiclands.Plains.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Polluted Mire", 253, Rarity.COMMON, mage.cards.p.PollutedMire.class));
        cards.add(new SetCardInfo("Primal Boost", 173, Rarity.COMMON, mage.cards.p.PrimalBoost.class));
        cards.add(new SetCardInfo("Pyre Zombie", 194, Rarity.RARE, mage.cards.p.PyreZombie.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Pyre Zombie", 362, Rarity.RARE, mage.cards.p.PyreZombie.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Quicksilver Dagger", 195, Rarity.UNCOMMON, mage.cards.q.QuicksilverDagger.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Quicksilver Dagger", 363, Rarity.UNCOMMON, mage.cards.q.QuicksilverDagger.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Radha, Heir to Keld", 196, Rarity.UNCOMMON, mage.cards.r.RadhaHeirToKeld.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Radha, Heir to Keld", 364, Rarity.UNCOMMON, mage.cards.r.RadhaHeirToKeld.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Radha, Heir to Keld", 446, Rarity.UNCOMMON, mage.cards.r.RadhaHeirToKeld.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Radiant's Judgment", 21, Rarity.COMMON, mage.cards.r.RadiantsJudgment.class));
        cards.add(new SetCardInfo("Recoil", 197, Rarity.UNCOMMON, mage.cards.r.Recoil.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Recoil", 365, Rarity.UNCOMMON, mage.cards.r.Recoil.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Remedy", 22, Rarity.COMMON, mage.cards.r.Remedy.class));
        cards.add(new SetCardInfo("Remote Isle", 254, Rarity.COMMON, mage.cards.r.RemoteIsle.class));
        cards.add(new SetCardInfo("Renewed Faith", 23, Rarity.COMMON, mage.cards.r.RenewedFaith.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Renewed Faith", 269, Rarity.COMMON, mage.cards.r.RenewedFaith.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Ridgetop Raptor", 134, Rarity.COMMON, mage.cards.r.RidgetopRaptor.class));
        cards.add(new SetCardInfo("Rith's Grove", 255, Rarity.UNCOMMON, mage.cards.r.RithsGrove.class));
        cards.add(new SetCardInfo("Rith, the Awakener", 198, Rarity.RARE, mage.cards.r.RithTheAwakener.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Rith, the Awakener", 366, Rarity.RARE, mage.cards.r.RithTheAwakener.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Royal Assassin", 101, Rarity.RARE, mage.cards.r.RoyalAssassin.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Royal Assassin", 310, Rarity.RARE, mage.cards.r.RoyalAssassin.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Sandstorm", 174, Rarity.COMMON, mage.cards.s.Sandstorm.class));
        cards.add(new SetCardInfo("Saproling Symbiosis", 175, Rarity.RARE, mage.cards.s.SaprolingSymbiosis.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Saproling Symbiosis", 348, Rarity.RARE, mage.cards.s.SaprolingSymbiosis.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Savannah Lions", 24, Rarity.COMMON, mage.cards.s.SavannahLions.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Savannah Lions", 270, Rarity.COMMON, mage.cards.s.SavannahLions.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Sawtooth Loon", 199, Rarity.UNCOMMON, mage.cards.s.SawtoothLoon.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sawtooth Loon", 367, Rarity.UNCOMMON, mage.cards.s.SawtoothLoon.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Serra Angel", 25, Rarity.UNCOMMON, mage.cards.s.SerraAngel.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Serra Angel", 271, Rarity.UNCOMMON, mage.cards.s.SerraAngel.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Serra Avatar", 26, Rarity.MYTHIC, mage.cards.s.SerraAvatar.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Serra Avatar", 272, Rarity.MYTHIC, mage.cards.s.SerraAvatar.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Seton's Desire", 176, Rarity.COMMON, mage.cards.s.SetonsDesire.class));
        cards.add(new SetCardInfo("Sevinne's Reclamation", 27, Rarity.RARE, mage.cards.s.SevinnesReclamation.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sevinne's Reclamation", 273, Rarity.RARE, mage.cards.s.SevinnesReclamation.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Shivan Dragon", 135, Rarity.RARE, mage.cards.s.ShivanDragon.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Shivan Dragon", 329, Rarity.RARE, mage.cards.s.ShivanDragon.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Siege-Gang Commander", 136, Rarity.RARE, mage.cards.s.SiegeGangCommander.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Siege-Gang Commander", 330, Rarity.RARE, mage.cards.s.SiegeGangCommander.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Siege-Gang Commander", 436, Rarity.RARE, mage.cards.s.SiegeGangCommander.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Skirk Prospector", 137, Rarity.COMMON, mage.cards.s.SkirkProspector.class));
        cards.add(new SetCardInfo("Slice and Dice", 138, Rarity.UNCOMMON, mage.cards.s.SliceAndDice.class));
        cards.add(new SetCardInfo("Slippery Karst", 256, Rarity.COMMON, mage.cards.s.SlipperyKarst.class));
        cards.add(new SetCardInfo("Smoldering Crater", 257, Rarity.COMMON, mage.cards.s.SmolderingCrater.class));
        cards.add(new SetCardInfo("Snap", 66, Rarity.COMMON, mage.cards.s.Snap.class));
        cards.add(new SetCardInfo("Sneak Attack", 139, Rarity.MYTHIC, mage.cards.s.SneakAttack.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sneak Attack", 331, Rarity.MYTHIC, mage.cards.s.SneakAttack.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Sol'kanar the Swamp King", 200, Rarity.RARE, mage.cards.s.SolkanarTheSwampKing.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sol'kanar the Swamp King", 368, Rarity.RARE, mage.cards.s.SolkanarTheSwampKing.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Solar Blast", 140, Rarity.COMMON, mage.cards.s.SolarBlast.class));
        cards.add(new SetCardInfo("Spark Spray", 141, Rarity.COMMON, mage.cards.s.SparkSpray.class));
        cards.add(new SetCardInfo("Spectral Lynx", 28, Rarity.UNCOMMON, mage.cards.s.SpectralLynx.class));
        cards.add(new SetCardInfo("Spinal Embrace", 201, Rarity.RARE, mage.cards.s.SpinalEmbrace.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Spinal Embrace", 369, Rarity.RARE, mage.cards.s.SpinalEmbrace.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Spirit Link", 29, Rarity.COMMON, mage.cards.s.SpiritLink.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Spirit Link", 274, Rarity.COMMON, mage.cards.s.SpiritLink.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Spiritmonger", 202, Rarity.UNCOMMON, mage.cards.s.Spiritmonger.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Spiritmonger", 370, Rarity.UNCOMMON, mage.cards.s.Spiritmonger.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Spite // Malice", 208, Rarity.UNCOMMON, mage.cards.s.SpiteMalice.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Squirrel Nest", 177, Rarity.UNCOMMON, mage.cards.s.SquirrelNest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Squirrel Nest", 349, Rarity.UNCOMMON, mage.cards.s.SquirrelNest.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Stand // Deliver", 207, Rarity.UNCOMMON, mage.cards.s.StandDeliver.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Stonewood Invoker", 178, Rarity.COMMON, mage.cards.s.StonewoodInvoker.class));
        cards.add(new SetCardInfo("Storm Entity", 142, Rarity.UNCOMMON, mage.cards.s.StormEntity.class));
        cards.add(new SetCardInfo("Street Wraith", 102, Rarity.COMMON, mage.cards.s.StreetWraith.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Street Wraith", 311, Rarity.COMMON, mage.cards.s.StreetWraith.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Street Wraith", 429, Rarity.COMMON, mage.cards.s.StreetWraith.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Stroke of Genius", 67, Rarity.RARE, mage.cards.s.StrokeOfGenius.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Stroke of Genius", 293, Rarity.RARE, mage.cards.s.StrokeOfGenius.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Subterranean Scout", 143, Rarity.COMMON, mage.cards.s.SubterraneanScout.class));
        cards.add(new SetCardInfo("Sulfur Falls", 258, Rarity.RARE, mage.cards.s.SulfurFalls.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sulfur Falls", 400, Rarity.RARE, mage.cards.s.SulfurFalls.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Sulfuric Vortex", 144, Rarity.RARE, mage.cards.s.SulfuricVortex.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sulfuric Vortex", 332, Rarity.RARE, mage.cards.s.SulfuricVortex.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Sun Clasp", 30, Rarity.COMMON, mage.cards.s.SunClasp.class));
        cards.add(new SetCardInfo("Suq'Ata Lancer", 145, Rarity.COMMON, mage.cards.s.SuqAtaLancer.class));
        cards.add(new SetCardInfo("Swamp", 406, Rarity.LAND, mage.cards.basiclands.Swamp.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 407, Rarity.LAND, mage.cards.basiclands.Swamp.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Swords to Plowshares", 31, Rarity.UNCOMMON, mage.cards.s.SwordsToPlowshares.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swords to Plowshares", 275, Rarity.UNCOMMON, mage.cards.s.SwordsToPlowshares.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Sylvan Library", 179, Rarity.MYTHIC, mage.cards.s.SylvanLibrary.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sylvan Library", 350, Rarity.MYTHIC, mage.cards.s.SylvanLibrary.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Sylvan Library", 441, Rarity.MYTHIC, mage.cards.s.SylvanLibrary.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Symbiotic Beast", 180, Rarity.COMMON, mage.cards.s.SymbioticBeast.class));
        cards.add(new SetCardInfo("Tatyova, Benthic Druid", 203, Rarity.UNCOMMON, mage.cards.t.TatyovaBenthicDruid.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tatyova, Benthic Druid", 371, Rarity.UNCOMMON, mage.cards.t.TatyovaBenthicDruid.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Terminal Moraine", 259, Rarity.COMMON, mage.cards.t.TerminalMoraine.class));
        cards.add(new SetCardInfo("Terravore", 181, Rarity.UNCOMMON, mage.cards.t.Terravore.class));
        cards.add(new SetCardInfo("Terror", 103, Rarity.COMMON, mage.cards.t.Terror.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Terror", 312, Rarity.COMMON, mage.cards.t.Terror.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Test of Endurance", 32, Rarity.MYTHIC, mage.cards.t.TestOfEndurance.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Test of Endurance", 276, Rarity.MYTHIC, mage.cards.t.TestOfEndurance.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Test of Endurance", 414, Rarity.MYTHIC, mage.cards.t.TestOfEndurance.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Thieving Magpie", 68, Rarity.UNCOMMON, mage.cards.t.ThievingMagpie.class));
        cards.add(new SetCardInfo("Thran Golem", 234, Rarity.UNCOMMON, mage.cards.t.ThranGolem.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Thran Golem", 387, Rarity.UNCOMMON, mage.cards.t.ThranGolem.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Tiana, Ship's Caretaker", 204, Rarity.UNCOMMON, mage.cards.t.TianaShipsCaretaker.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tiana, Ship's Caretaker", 372, Rarity.UNCOMMON, mage.cards.t.TianaShipsCaretaker.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Time Stretch", 69, Rarity.MYTHIC, mage.cards.t.TimeStretch.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Time Stretch", 294, Rarity.MYTHIC, mage.cards.t.TimeStretch.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Time Stretch", 422, Rarity.MYTHIC, mage.cards.t.TimeStretch.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tormod's Crypt", 235, Rarity.UNCOMMON, mage.cards.t.TormodsCrypt.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tormod's Crypt", 388, Rarity.UNCOMMON, mage.cards.t.TormodsCrypt.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Treva's Ruins", 260, Rarity.UNCOMMON, mage.cards.t.TrevasRuins.class));
        cards.add(new SetCardInfo("Triskelion", 236, Rarity.RARE, mage.cards.t.Triskelion.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Triskelion", 389, Rarity.RARE, mage.cards.t.Triskelion.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Triskelion", 452, Rarity.RARE, mage.cards.t.Triskelion.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Turnabout", 70, Rarity.UNCOMMON, mage.cards.t.Turnabout.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Turnabout", 295, Rarity.UNCOMMON, mage.cards.t.Turnabout.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Twisted Experiment", 104, Rarity.COMMON, mage.cards.t.TwistedExperiment.class));
        cards.add(new SetCardInfo("Umbilicus", 237, Rarity.RARE, mage.cards.u.Umbilicus.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Umbilicus", 390, Rarity.RARE, mage.cards.u.Umbilicus.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Undead Gladiator", 105, Rarity.UNCOMMON, mage.cards.u.UndeadGladiator.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Undead Gladiator", 313, Rarity.UNCOMMON, mage.cards.u.UndeadGladiator.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Undying Rage", 146, Rarity.COMMON, mage.cards.u.UndyingRage.class));
        cards.add(new SetCardInfo("Urborg Syphon-Mage", 106, Rarity.COMMON, mage.cards.u.UrborgSyphonMage.class));
        cards.add(new SetCardInfo("Urborg Uprising", 107, Rarity.COMMON, mage.cards.u.UrborgUprising.class));
        cards.add(new SetCardInfo("Urza's Blueprints", 238, Rarity.RARE, mage.cards.u.UrzasBlueprints.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Urza's Blueprints", 391, Rarity.RARE, mage.cards.u.UrzasBlueprints.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Urza's Incubator", 239, Rarity.MYTHIC, mage.cards.u.UrzasIncubator.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Urza's Incubator", 392, Rarity.MYTHIC, mage.cards.u.UrzasIncubator.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Urza's Incubator", 453, Rarity.MYTHIC, mage.cards.u.UrzasIncubator.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Urza, Lord High Artificer", 71, Rarity.MYTHIC, mage.cards.u.UrzaLordHighArtificer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Urza, Lord High Artificer", 296, Rarity.MYTHIC, mage.cards.u.UrzaLordHighArtificer.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Urza, Lord High Artificer", 423, Rarity.MYTHIC, mage.cards.u.UrzaLordHighArtificer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Valduk, Keeper of the Flame", 147, Rarity.UNCOMMON, mage.cards.v.ValdukKeeperOfTheFlame.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Valduk, Keeper of the Flame", 333, Rarity.UNCOMMON, mage.cards.v.ValdukKeeperOfTheFlame.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Vampiric Tutor", 108, Rarity.MYTHIC, mage.cards.v.VampiricTutor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Vampiric Tutor", 314, Rarity.MYTHIC, mage.cards.v.VampiricTutor.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Vampiric Tutor", 430, Rarity.MYTHIC, mage.cards.v.VampiricTutor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Veiled Serpent", 72, Rarity.COMMON, mage.cards.v.VeiledSerpent.class));
        cards.add(new SetCardInfo("Vexing Sphinx", 73, Rarity.RARE, mage.cards.v.VexingSphinx.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Vexing Sphinx", 297, Rarity.RARE, mage.cards.v.VexingSphinx.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Vigilant Sentry", 33, Rarity.COMMON, mage.cards.v.VigilantSentry.class));
        cards.add(new SetCardInfo("Voice of All", 34, Rarity.UNCOMMON, mage.cards.v.VoiceOfAll.class));
        cards.add(new SetCardInfo("Wall of Junk", 240, Rarity.UNCOMMON, mage.cards.w.WallOfJunk.class));
        cards.add(new SetCardInfo("Wax // Wane", 211, Rarity.UNCOMMON, mage.cards.w.WaxWane.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Werebear", 182, Rarity.COMMON, mage.cards.w.Werebear.class));
        cards.add(new SetCardInfo("Whitemane Lion", 35, Rarity.COMMON, mage.cards.w.WhitemaneLion.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Whitemane Lion", 277, Rarity.COMMON, mage.cards.w.WhitemaneLion.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Wild Dogs", 183, Rarity.COMMON, mage.cards.w.WildDogs.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Wild Dogs", 351, Rarity.COMMON, mage.cards.w.WildDogs.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Wild Growth", 184, Rarity.COMMON, mage.cards.w.WildGrowth.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Wild Growth", 352, Rarity.COMMON, mage.cards.w.WildGrowth.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Windborn Muse", 36, Rarity.RARE, mage.cards.w.WindbornMuse.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Windborn Muse", 278, Rarity.RARE, mage.cards.w.WindbornMuse.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Windborn Muse", 415, Rarity.RARE, mage.cards.w.WindbornMuse.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Woodland Cemetery", 261, Rarity.RARE, mage.cards.w.WoodlandCemetery.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Woodland Cemetery", 401, Rarity.RARE, mage.cards.w.WoodlandCemetery.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Worldgorger Dragon", 148, Rarity.MYTHIC, mage.cards.w.WorldgorgerDragon.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Worldgorger Dragon", 334, Rarity.MYTHIC, mage.cards.w.WorldgorgerDragon.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Worldgorger Dragon", 437, Rarity.MYTHIC, mage.cards.w.WorldgorgerDragon.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Worldly Tutor", 185, Rarity.RARE, mage.cards.w.WorldlyTutor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Worldly Tutor", 353, Rarity.RARE, mage.cards.w.WorldlyTutor.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Worldly Tutor", 442, Rarity.RARE, mage.cards.w.WorldlyTutor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Wormfang Drake", 74, Rarity.COMMON, mage.cards.w.WormfangDrake.class));
        cards.add(new SetCardInfo("Wrath of God", 37, Rarity.RARE, mage.cards.w.WrathOfGod.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Wrath of God", 279, Rarity.RARE, mage.cards.w.WrathOfGod.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Wrath of God", 416, Rarity.RARE, mage.cards.w.WrathOfGod.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Wretched Anurid", 109, Rarity.COMMON, mage.cards.w.WretchedAnurid.class));
        cards.add(new SetCardInfo("Xira Arien", 205, Rarity.RARE, mage.cards.x.XiraArien.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Xira Arien", 373, Rarity.RARE, mage.cards.x.XiraArien.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Yawgmoth, Thran Physician", 110, Rarity.MYTHIC, mage.cards.y.YawgmothThranPhysician.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Yawgmoth, Thran Physician", 315, Rarity.MYTHIC, mage.cards.y.YawgmothThranPhysician.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Yawgmoth, Thran Physician", 431, Rarity.MYTHIC, mage.cards.y.YawgmothThranPhysician.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Zombie Infestation", 111, Rarity.UNCOMMON, mage.cards.z.ZombieInfestation.class));
        cards.add(new SetCardInfo("Zur the Enchanter", 206, Rarity.RARE, mage.cards.z.ZurTheEnchanter.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Zur the Enchanter", 374, Rarity.RARE, mage.cards.z.ZurTheEnchanter.class, RETRO_ART_USE_VARIOUS));
    }

   @Override
   public BoosterCollator createCollator() {
       return new DominariaRemasteredCollator();
   }
}

// Booster collation info from https://www.lethe.xyz/mtg/collation/dmr.html
// 1/4 of packs contain an old frame rare/mythic
// 6% of packs contain a borderles C/U and 17% of packs contain a borderless card
// using borderless for 1/3 of the printings of R/M with borderless variations works with those numbers
// retro has 3:1 individual common:uncommon
class DominariaRemasteredCollator implements BoosterCollator {
    private final CardRun commonA = new CardRun(true, "56", "29", "226", "89", "150", "134", "45", "19", "98", "167", "114", "43", "23", "104", "173", "128", "52", "1", "97", "168", "143", "57", "17", "87", "160", "141", "55", "15", "83", "184", "125", "66", "30", "102", "156", "146", "63", "35", "94", "176", "130", "58", "21", "76", "174", "124", "38", "3", "106", "182", "117", "65", "29", "109", "173", "118", "53", "24", "107", "158", "112", "56", "9", "89", "150", "134", "43", "15", "98", "167", "141", "45", "1", "104", "180", "128", "52", "23", "87", "168", "143", "57", "17", "97", "160", "114", "63", "19", "76", "184", "125", "66", "21", "102", "156", "146", "55", "30", "94", "176", "124", "58", "35", "83", "174", "130", "38", "3", "106", "182", "112", "65", "9", "109", "158", "118", "53", "24", "107", "180", "117");
    private final CardRun commonB = new CardRun(true, "226", "152", "140", "41", "22", "228", "257", "88", "178", "131", "61", "18", "256", "232", "81", "172", "137", "20", "254", "85", "74", "163", "72", "16", "245", "90", "152", "145", "33", "46", "222", "259", "183", "99", "123", "233", "61", "140", "253", "18", "169", "103", "131", "41", "22", "257", "228", "88", "178", "51", "11", "113", "256", "232", "81", "172", "137", "74", "254", "85", "163", "20", "145", "72", "16", "245", "90", "152", "46", "33", "123", "233", "259", "99", "183", "113", "51", "11", "222", "253", "103", "169", "131", "41", "22", "228", "257", "88", "178", "140", "61", "18", "254", "232", "81", "172", "137", "46", "20", "256", "85", "163", "16", "72", "245", "90", "169", "145", "74", "33", "233", "253", "99", "183", "123", "51", "11", "222", "113", "259", "103");
    private final CardRun uncommonA = new CardRun(true, "251", "164", "44", "207", "129", "170", "80", "122", "221", "14", "157", "8", "93", "234", "213", "119", "2", "54", "260", "220", "120", "154", "49", "215", "10", "231", "153", "42", "252", "219", "84", "216", "70", "225", "214", "79", "255", "64", "210", "142", "251", "44", "207", "164", "129", "170", "80", "221", "122", "157", "14", "93", "8", "213", "119", "234", "54", "2", "260", "49", "120", "154", "220", "215", "10", "42", "153", "231", "252", "219", "84", "216", "70", "225", "214", "79", "255", "64", "210", "142", "251", "44", "164", "207", "129", "170", "80", "221", "122", "14", "157", "8", "93", "213", "234", "119", "54", "2", "260", "220", "215", "154", "49", "120", "10", "231", "153", "42", "219", "252", "84", "216", "70", "225", "214", "79", "255", "64", "210", "142");
    private final CardRun uncommonB = new CardRun(true, "246", "48", "155", "209", "40", "86", "28", "208", "115", "212", "34", "196", "190", "240", "199", "105", "197", "25", "162", "242", "116", "189", "243", "138", "181", "111", "202", "78", "68", "31", "147", "235", "204", "100", "203", "177", "4", "211", "192", "246", "195", "48", "209", "40", "155", "212", "28", "208", "115", "196", "86", "34", "190", "240", "199", "105", "162", "25", "197", "243", "189", "116", "242", "138", "181", "111", "202", "68", "78", "203", "147", "235", "204", "100", "31", "177", "211", "4", "192", "195", "246", "48", "155", "209", "40", "86", "28", "208", "115", "212", "34", "196", "190", "240", "199", "105", "162", "25", "197", "243", "189", "116", "242", "138", "181", "111", "202", "68", "78", "203", "147", "235", "204", "100", "31", "177", "4", "211", "192", "195");
    private final CardRun uncommonC = new CardRun(false, "425", "432", "419", "446", "429");
    private final CardRun rare = new CardRun(false, "186", "186", "186", "186", "443", "443", "149", "149", "149", "149", "438", "438", "187", "187", "187", "187", "187", "187", "39", "39", "39", "39", "39", "39", "151", "151", "151", "151", "439", "439", "75", "75", "75", "75", "75", "75", "77", "77", "77", "77", "424", "424", "241", "241", "241", "241", "241", "241", "217", "217", "217", "217", "217", "217", "218", "218", "218", "218", "218", "218", "244", "244", "454", "188", "188", "188", "188", "444", "444", "47", "47", "47", "47", "417", "417", "5", "5", "5", "5", "5", "5", "6", "6", "6", "6", "412", "412", "82", "82", "82", "82", "426", "426", "159", "159", "159", "159", "159", "159", "50", "50", "418", "161", "161", "161", "161", "161", "161", "121", "121", "121", "121", "433", "433", "223", "223", "447", "247", "247", "247", "247", "455", "455", "7", "7", "7", "7", "7", "7", "126", "126", "126", "126", "434", "434", "224", "224", "224", "224", "448", "448", "248", "248", "248", "248", "248", "248", "191", "191", "445", "249", "249", "249", "249", "249", "249", "227", "227", "227", "227", "449", "449", "165", "165", "165", "165", "165", "165", "166", "166", "166", "127", "127", "435", "229", "229", "450", "12", "12", "12", "12", "12", "12", "230", "230", "230", "230", "451", "451", "13", "13", "413", "250", "250", "250", "250", "456", "456", "91", "91", "91", "91", "91", "91", "60", "60", "60", "60", "421", "421", "59", "59", "59", "59", "420", "420", "92", "92", "92", "92", "92", "92", "95", "95", "427", "171", "171", "440", "62", "62", "62", "62", "62", "62", "132", "132", "132", "132", "132", "132", "96", "96", "96", "96", "428", "428", "133", "133", "133", "133", "133", "133", "193", "193", "193", "193", "193", "193", "194", "194", "194", "194", "194", "194", "198", "198", "198", "198", "198", "198", "101", "101", "101", "101", "101", "101", "175", "175", "175", "175", "175", "175", "26", "26", "26", "27", "27", "27", "27", "27", "27", "135", "135", "135", "135", "135", "135", "136", "136", "136", "136", "436", "436", "139", "139", "139", "200", "200", "200", "200", "200", "200", "201", "201", "201", "201", "201", "201", "67", "67", "67", "67", "67", "67", "258", "258", "258", "258", "258", "258", "144", "144", "144", "144", "144", "144", "179", "179", "441", "32", "32", "414", "69", "69", "422", "236", "236", "236", "236", "452", "452", "237", "237", "237", "237", "237", "237", "71", "71", "423", "238", "238", "238", "238", "238", "238", "239", "239", "453", "108", "108", "430", "73", "73", "73", "73", "73", "73", "36", "36", "36", "36", "415", "415", "261", "261", "261", "261", "261", "261", "148", "148", "437", "185", "185", "185", "185", "442", "442", "37", "37", "37", "37", "416", "416", "205", "205", "205", "205", "205", "205", "110", "110", "431", "206", "206", "206", "206", "206", "206");
    private final CardRun rareOld = new CardRun(false, "354", "354", "335", "335", "355", "355", "280", "280", "336", "336", "298", "298", "299", "299", "393", "393", "375", "375", "376", "376", "394", "356", "356", "282", "282", "262", "262", "263", "263", "304", "304", "339", "339", "284", "341", "341", "321", "321", "378", "395", "395", "264", "264", "324", "324", "379", "379", "396", "396", "359", "397", "397", "381", "381", "343", "343", "344", "325", "383", "265", "265", "384", "384", "266", "398", "398", "305", "305", "289", "289", "288", "288", "306", "306", "308", "347", "290", "290", "327", "327", "309", "309", "328", "328", "361", "361", "362", "362", "366", "366", "310", "310", "348", "348", "272", "273", "273", "329", "329", "330", "330", "331", "368", "368", "369", "369", "293", "293", "400", "400", "332", "332", "350", "276", "294", "389", "389", "390", "390", "296", "391", "391", "392", "314", "297", "297", "278", "278", "401", "401", "334", "353", "353", "279", "279", "373", "373", "315", "374", "374");
    private final CardRun retro = new CardRun(false, "300", "316", "316", "316", "281", "281", "281", "377", "301", "337", "317", "357", "302", "303", "303", "303", "338", "318", "318", "318", "340", "340", "340", "283", "319", "320", "285", "285", "285", "322", "358", "323", "323", "323", "286", "380", "287", "287", "287", "342", "382", "382", "382", "345", "345", "345", "267", "385", "385", "385", "399", "326", "326", "326", "268", "268", "268", "360", "346", "307", "386", "386", "386", "291", "291", "291", "292", "292", "292", "363", "364", "365", "269", "269", "269", "270", "270", "270", "367", "271", "274", "274", "274", "370", "349", "311", "311", "311", "275", "371", "312", "312", "312", "387", "372", "388", "295", "313", "333", "277", "277", "277", "351", "351", "351", "352", "352", "352");
    private final CardRun common = new CardRun(false, "38", "1", "112", "41", "150", "152", "76", "113", "3", "43", "114", "45", "46", "222", "245", "81", "156", "117", "158", "118", "83", "160", "85", "51", "163", "52", "123", "124", "87", "125", "53", "55", "88", "89", "9", "90", "56", "226", "228", "167", "11", "168", "57", "128", "169", "130", "58", "232", "131", "15", "16", "94", "17", "61", "18", "233", "63", "19", "172", "65", "20", "97", "98", "99", "253", "173", "21", "22", "254", "23", "134", "174", "24", "176", "137", "256", "257", "66", "140", "141", "29", "178", "102", "143", "30", "145", "180", "259", "103", "104", "146", "106", "107", "72", "33", "182", "35", "183", "184", "74", "109");
    private final CardRun land = new CardRun(false, "402", "403", "404", "405", "406", "407", "408", "409", "410", "411");

    private final BoosterStructure AAAAAABBB = new BoosterStructure(
            commonA, commonA, commonA, commonA, commonA, commonA,
            commonB, commonB, commonB
    );
    private final BoosterStructure AAAAABBBB = new BoosterStructure(
            commonA, commonA, commonA, commonA, commonA,
            commonB, commonB, commonB, commonB
    );
    private final BoosterStructure AAA = new BoosterStructure(uncommonA, uncommonA, uncommonA);
    private final BoosterStructure BBB = new BoosterStructure(uncommonB, uncommonB, uncommonB);
    private final BoosterStructure AAC = new BoosterStructure(uncommonA, uncommonA, uncommonC);
    private final BoosterStructure BBC = new BoosterStructure(uncommonB, uncommonB, uncommonC);

    private final BoosterStructure OR = new BoosterStructure(retro,rare);
    private final BoosterStructure CO = new BoosterStructure(common,rareOld);
    private final BoosterStructure L1 = new BoosterStructure(land);

    private final RarityConfiguration commonRuns = new RarityConfiguration(
            AAAAAABBB, AAAAAABBB,
            AAAAABBBB, AAAAABBBB, AAAAABBBB
    );

    private final RarityConfiguration uncommonRuns = new RarityConfiguration(
            AAA, AAA, AAA, AAA, AAA, AAA, AAA, AAA, AAA, AAA,
            AAA, AAA, AAA, AAA, AAA, AAA, AAA, AAA, AAA, AAA,
            AAA, AAA, AAA, AAA, AAA, AAA, AAA, AAA, AAA, AAA,
            AAA, AAA, AAA, AAA, AAA, AAA, AAA, AAA, AAA, AAA,
            AAA, AAA, AAA, AAA, AAA, AAA, AAA, 
            BBB, BBB, BBB, BBB, BBB, BBB, BBB, BBB, BBB, BBB,
            BBB, BBB, BBB, BBB, BBB, BBB, BBB, BBB, BBB, BBB,
            BBB, BBB, BBB, BBB, BBB, BBB, BBB, BBB, BBB, BBB,
            BBB, BBB, BBB, BBB, BBB, BBB, BBB, BBB, BBB, BBB,
            BBB, BBB, BBB, BBB, BBB, BBB, BBB,
            AAC, AAC, AAC, 
            BBC, BBC, BBC
    );

    private final RarityConfiguration rareRuns = new RarityConfiguration(OR,OR,OR,CO);
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
