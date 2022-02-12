package mage.sets;

import mage.cards.Card;
import mage.cards.ExpansionSet;
import mage.cards.repository.CardCriteria;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.collation.BoosterCollator;
import mage.collation.BoosterStructure;
import mage.collation.CardRun;
import mage.collation.RarityConfiguration;
import mage.constants.Rarity;
import mage.constants.SetType;
import mage.util.RandomUtil;

import java.util.ArrayList;
import java.util.List;

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
        this.numBoosterLands = 0;
        this.numBoosterCommon = 10;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.numBoosterSpecial = 1;
        this.ratioBoosterMythic = 7;
        this.maxCardNumberInBooster = 303;

        cards.add(new SetCardInfo("Abiding Grace", 1, Rarity.UNCOMMON, mage.cards.a.AbidingGrace.class));
        cards.add(new SetCardInfo("Abundant Harvest", 147, Rarity.COMMON, mage.cards.a.AbundantHarvest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Abundant Harvest", 354, Rarity.COMMON, mage.cards.a.AbundantHarvest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Academy Manufactor", 219, Rarity.RARE, mage.cards.a.AcademyManufactor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Academy Manufactor", 469, Rarity.RARE, mage.cards.a.AcademyManufactor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Aeromoeba", 37, Rarity.COMMON, mage.cards.a.Aeromoeba.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Aeromoeba", 389, Rarity.COMMON, mage.cards.a.Aeromoeba.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Aeve, Progenitor Ooze", 148, Rarity.RARE, mage.cards.a.AeveProgenitorOoze.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Aeve, Progenitor Ooze", 409, Rarity.RARE, mage.cards.a.AeveProgenitorOoze.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Aeve, Progenitor Ooze", 459, Rarity.RARE, mage.cards.a.AeveProgenitorOoze.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Altar of the Goyf", 220, Rarity.UNCOMMON, mage.cards.a.AltarOfTheGoyf.class));
        cards.add(new SetCardInfo("Angelic Curator", 262, Rarity.UNCOMMON, mage.cards.a.AngelicCurator.class));
        cards.add(new SetCardInfo("Arcbound Javelineer", 2, Rarity.UNCOMMON, mage.cards.a.ArcboundJavelineer.class));
        cards.add(new SetCardInfo("Arcbound Mouser", 3, Rarity.COMMON, mage.cards.a.ArcboundMouser.class));
        cards.add(new SetCardInfo("Arcbound Prototype", 4, Rarity.COMMON, mage.cards.a.ArcboundPrototype.class));
        cards.add(new SetCardInfo("Arcbound Shikari", 184, Rarity.UNCOMMON, mage.cards.a.ArcboundShikari.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Arcbound Shikari", 360, Rarity.UNCOMMON, mage.cards.a.ArcboundShikari.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Arcbound Slasher", 111, Rarity.COMMON, mage.cards.a.ArcboundSlasher.class));
        cards.add(new SetCardInfo("Arcbound Tracker", 112, Rarity.COMMON, mage.cards.a.ArcboundTracker.class));
        cards.add(new SetCardInfo("Arcbound Whelp", 113, Rarity.UNCOMMON, mage.cards.a.ArcboundWhelp.class));
        cards.add(new SetCardInfo("Archfiend of Sorrows", 74, Rarity.UNCOMMON, mage.cards.a.ArchfiendOfSorrows.class));
        cards.add(new SetCardInfo("Archon of Cruelty", 342, Rarity.MYTHIC, mage.cards.a.ArchonOfCruelty.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Archon of Cruelty", 75, Rarity.MYTHIC, mage.cards.a.ArchonOfCruelty.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Arcus Acolyte", 185, Rarity.UNCOMMON, mage.cards.a.ArcusAcolyte.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Arcus Acolyte", 361, Rarity.UNCOMMON, mage.cards.a.ArcusAcolyte.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Arid Mesa", 244, Rarity.RARE, mage.cards.a.AridMesa.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Arid Mesa", 436, Rarity.RARE, mage.cards.a.AridMesa.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Arid Mesa", 475, Rarity.RARE, mage.cards.a.AridMesa.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Asmoranomardicadaistinaculdacar", 186, Rarity.RARE, mage.cards.a.Asmoranomardicadaistinaculdacar.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Asmoranomardicadaistinaculdacar", 417, Rarity.RARE, mage.cards.a.Asmoranomardicadaistinaculdacar.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Asmoranomardicadaistinaculdacar", 463, Rarity.RARE, mage.cards.a.Asmoranomardicadaistinaculdacar.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Bannerhide Krushok", 149, Rarity.COMMON, mage.cards.b.BannerhideKrushok.class));
        cards.add(new SetCardInfo("Barbed Spike", 5, Rarity.UNCOMMON, mage.cards.b.BarbedSpike.class));
        cards.add(new SetCardInfo("Batterbone", 221, Rarity.UNCOMMON, mage.cards.b.Batterbone.class));
        cards.add(new SetCardInfo("Battle Plan", 114, Rarity.COMMON, mage.cards.b.BattlePlan.class));
        cards.add(new SetCardInfo("Blacksmith's Skill", 381, Rarity.COMMON, mage.cards.b.BlacksmithsSkill.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Blacksmith's Skill", 6, Rarity.COMMON, mage.cards.b.BlacksmithsSkill.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Blazing Rootwalla", 115, Rarity.UNCOMMON, mage.cards.b.BlazingRootwalla.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Blazing Rootwalla", 404, Rarity.UNCOMMON, mage.cards.b.BlazingRootwalla.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Blessed Respite", 150, Rarity.UNCOMMON, mage.cards.b.BlessedRespite.class));
        cards.add(new SetCardInfo("Bloodbraid Marauder", 116, Rarity.RARE, mage.cards.b.BloodbraidMarauder.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Bloodbraid Marauder", 454, Rarity.RARE, mage.cards.b.BloodbraidMarauder.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Blossoming Calm", 327, Rarity.UNCOMMON, mage.cards.b.BlossomingCalm.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Blossoming Calm", 7, Rarity.UNCOMMON, mage.cards.b.BlossomingCalm.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Bone Shards", 395, Rarity.COMMON, mage.cards.b.BoneShards.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Bone Shards", 76, Rarity.COMMON, mage.cards.b.BoneShards.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Bone Shredder", 272, Rarity.UNCOMMON, mage.cards.b.BoneShredder.class));
        cards.add(new SetCardInfo("Bottle Golems", 222, Rarity.COMMON, mage.cards.b.BottleGolems.class));
        cards.add(new SetCardInfo("Braids, Cabal Minion", 273, Rarity.RARE, mage.cards.b.BraidsCabalMinion.class));
        cards.add(new SetCardInfo("Brainstone", 223, Rarity.UNCOMMON, mage.cards.b.Brainstone.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Brainstone", 426, Rarity.UNCOMMON, mage.cards.b.Brainstone.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Break Ties", 8, Rarity.COMMON, mage.cards.b.BreakTies.class));
        cards.add(new SetCardInfo("Break the Ice", 77, Rarity.UNCOMMON, mage.cards.b.BreakTheIce.class));
        cards.add(new SetCardInfo("Breathless Knight", 187, Rarity.COMMON, mage.cards.b.BreathlessKnight.class));
        cards.add(new SetCardInfo("Breya's Apprentice", 117, Rarity.RARE, mage.cards.b.BreyasApprentice.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Breya's Apprentice", 455, Rarity.RARE, mage.cards.b.BreyasApprentice.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Burdened Aerialist", 38, Rarity.COMMON, mage.cards.b.BurdenedAerialist.class));
        cards.add(new SetCardInfo("Cabal Coffers", 301, Rarity.MYTHIC, mage.cards.c.CabalCoffers.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Cabal Coffers", 325, Rarity.MYTHIC, mage.cards.c.CabalCoffers.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Cabal Initiate", 78, Rarity.COMMON, mage.cards.c.CabalInitiate.class));
        cards.add(new SetCardInfo("Calibrated Blast", 118, Rarity.RARE, mage.cards.c.CalibratedBlast.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Calibrated Blast", 405, Rarity.RARE, mage.cards.c.CalibratedBlast.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Calibrated Blast", 456, Rarity.RARE, mage.cards.c.CalibratedBlast.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Caprichrome", 9, Rarity.UNCOMMON, mage.cards.c.Caprichrome.class));
        cards.add(new SetCardInfo("Captain Ripley Vance", 119, Rarity.UNCOMMON, mage.cards.c.CaptainRipleyVance.class));
        cards.add(new SetCardInfo("Captured by Lagacs", 188, Rarity.COMMON, mage.cards.c.CapturedByLagacs.class));
        cards.add(new SetCardInfo("Carth the Lion", 189, Rarity.RARE, mage.cards.c.CarthTheLion.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Carth the Lion", 418, Rarity.RARE, mage.cards.c.CarthTheLion.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Carth the Lion", 464, Rarity.RARE, mage.cards.c.CarthTheLion.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Chainer, Nightmare Adept", 289, Rarity.RARE, mage.cards.c.ChainerNightmareAdept.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Chainer, Nightmare Adept", 419, Rarity.RARE, mage.cards.c.ChainerNightmareAdept.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Chance Encounter", 277, Rarity.RARE, mage.cards.c.ChanceEncounter.class));
        cards.add(new SetCardInfo("Chatterfang, Squirrel General", 151, Rarity.MYTHIC, mage.cards.c.ChatterfangSquirrelGeneral.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Chatterfang, Squirrel General", 316, Rarity.MYTHIC, mage.cards.c.ChatterfangSquirrelGeneral.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Chatterfang, Squirrel General", 410, Rarity.MYTHIC, mage.cards.c.ChatterfangSquirrelGeneral.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Chatterstorm", 152, Rarity.COMMON, mage.cards.c.Chatterstorm.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Chatterstorm", 411, Rarity.COMMON, mage.cards.c.Chatterstorm.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Chef's Kiss", 120, Rarity.RARE, mage.cards.c.ChefsKiss.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Chef's Kiss", 457, Rarity.RARE, mage.cards.c.ChefsKiss.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Chitterspitter", 153, Rarity.RARE, mage.cards.c.Chitterspitter.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Chitterspitter", 460, Rarity.RARE, mage.cards.c.Chitterspitter.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Chrome Courier", 190, Rarity.COMMON, mage.cards.c.ChromeCourier.class));
        cards.add(new SetCardInfo("Clattering Augur", 79, Rarity.UNCOMMON, mage.cards.c.ClatteringAugur.class));
        cards.add(new SetCardInfo("Combine Chrysalis", 191, Rarity.UNCOMMON, mage.cards.c.CombineChrysalis.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Combine Chrysalis", 362, Rarity.UNCOMMON, mage.cards.c.CombineChrysalis.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Constable of the Realm", 10, Rarity.UNCOMMON, mage.cards.c.ConstableOfTheRealm.class));
        cards.add(new SetCardInfo("Counterspell", 267, Rarity.UNCOMMON, mage.cards.c.Counterspell.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Counterspell", 308, Rarity.RARE, mage.cards.c.Counterspell.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Crack Open", 154, Rarity.COMMON, mage.cards.c.CrackOpen.class));
        cards.add(new SetCardInfo("Cursed Totem", 295, Rarity.RARE, mage.cards.c.CursedTotem.class));
        cards.add(new SetCardInfo("Dakkon, Shadow Slayer", 192, Rarity.MYTHIC, mage.cards.d.DakkonShadowSlayer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dakkon, Shadow Slayer", 304, Rarity.MYTHIC, mage.cards.d.DakkonShadowSlayer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dakkon, Shadow Slayer", 363, Rarity.MYTHIC, mage.cards.d.DakkonShadowSlayer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Damn", 396, Rarity.RARE, mage.cards.d.Damn.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Damn", 449, Rarity.RARE, mage.cards.d.Damn.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Damn", 80, Rarity.RARE, mage.cards.d.Damn.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Darkmoss Bridge", 245, Rarity.COMMON, mage.cards.d.DarkmossBridge.class));
        cards.add(new SetCardInfo("Dauthi Voidwalker", 397, Rarity.RARE, mage.cards.d.DauthiVoidwalker.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dauthi Voidwalker", 450, Rarity.RARE, mage.cards.d.DauthiVoidwalker.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dauthi Voidwalker", 81, Rarity.RARE, mage.cards.d.DauthiVoidwalker.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Deepwood Denizen", 155, Rarity.COMMON, mage.cards.d.DeepwoodDenizen.class));
        cards.add(new SetCardInfo("Dermotaxi", 224, Rarity.RARE, mage.cards.d.Dermotaxi.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dermotaxi", 378, Rarity.RARE, mage.cards.d.Dermotaxi.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Diamond Lion", 225, Rarity.RARE, mage.cards.d.DiamondLion.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Diamond Lion", 427, Rarity.RARE, mage.cards.d.DiamondLion.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Diamond Lion", 470, Rarity.RARE, mage.cards.d.DiamondLion.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dihada's Ploy", 193, Rarity.COMMON, mage.cards.d.DihadasPloy.class));
        cards.add(new SetCardInfo("Discerning Taste", 82, Rarity.COMMON, mage.cards.d.DiscerningTaste.class));
        cards.add(new SetCardInfo("Disciple of the Sun", 11, Rarity.COMMON, mage.cards.d.DiscipleOfTheSun.class));
        cards.add(new SetCardInfo("Dragon's Rage Channeler", 121, Rarity.UNCOMMON, mage.cards.d.DragonsRageChanneler.class));
        cards.add(new SetCardInfo("Dress Down", 334, Rarity.RARE, mage.cards.d.DressDown.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dress Down", 39, Rarity.RARE, mage.cards.d.DressDown.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Drey Keeper", 194, Rarity.COMMON, mage.cards.d.DreyKeeper.class));
        cards.add(new SetCardInfo("Drossforge Bridge", 246, Rarity.COMMON, mage.cards.d.DrossforgeBridge.class));
        cards.add(new SetCardInfo("Duskshell Crawler", 156, Rarity.COMMON, mage.cards.d.DuskshellCrawler.class));
        cards.add(new SetCardInfo("Echoing Return", 83, Rarity.COMMON, mage.cards.e.EchoingReturn.class));
        cards.add(new SetCardInfo("Enchantress's Presence", 283, Rarity.RARE, mage.cards.e.EnchantresssPresence.class));
        cards.add(new SetCardInfo("Endurance", 157, Rarity.MYTHIC, mage.cards.e.Endurance.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Endurance", 317, Rarity.MYTHIC, mage.cards.e.Endurance.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Esper Sentinel", 12, Rarity.RARE, mage.cards.e.EsperSentinel.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Esper Sentinel", 328, Rarity.RARE, mage.cards.e.EsperSentinel.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Etherium Spinner", 40, Rarity.COMMON, mage.cards.e.EtheriumSpinner.class));
        cards.add(new SetCardInfo("Ethersworn Sphinx", 195, Rarity.UNCOMMON, mage.cards.e.EtherswornSphinx.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ethersworn Sphinx", 364, Rarity.UNCOMMON, mage.cards.e.EtherswornSphinx.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Extruder", 296, Rarity.UNCOMMON, mage.cards.e.Extruder.class));
        cards.add(new SetCardInfo("Fae Offering", 158, Rarity.UNCOMMON, mage.cards.f.FaeOffering.class));
        cards.add(new SetCardInfo("Fairgrounds Patrol", 13, Rarity.COMMON, mage.cards.f.FairgroundsPatrol.class));
        cards.add(new SetCardInfo("Faithless Salvaging", 122, Rarity.COMMON, mage.cards.f.FaithlessSalvaging.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Faithless Salvaging", 349, Rarity.COMMON, mage.cards.f.FaithlessSalvaging.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Fast // Furious", 123, Rarity.UNCOMMON, mage.cards.f.FastFurious.class));
        cards.add(new SetCardInfo("Feast of Sanity", 84, Rarity.UNCOMMON, mage.cards.f.FeastOfSanity.class));
        cards.add(new SetCardInfo("Filigree Attendant", 41, Rarity.UNCOMMON, mage.cards.f.FiligreeAttendant.class));
        cards.add(new SetCardInfo("Fire // Ice", 290, Rarity.RARE, mage.cards.f.FireIce.class));
        cards.add(new SetCardInfo("Flame Blitz", 124, Rarity.UNCOMMON, mage.cards.f.FlameBlitz.class));
        cards.add(new SetCardInfo("Flame Rift", 278, Rarity.UNCOMMON, mage.cards.f.FlameRift.class));
        cards.add(new SetCardInfo("Flametongue Yearling", 125, Rarity.UNCOMMON, mage.cards.f.FlametongueYearling.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Flametongue Yearling", 350, Rarity.UNCOMMON, mage.cards.f.FlametongueYearling.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Flay Essence", 85, Rarity.UNCOMMON, mage.cards.f.FlayEssence.class));
        cards.add(new SetCardInfo("Floodhound", 335, Rarity.COMMON, mage.cards.f.Floodhound.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Floodhound", 42, Rarity.COMMON, mage.cards.f.Floodhound.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Flourishing Strike", 159, Rarity.COMMON, mage.cards.f.FlourishingStrike.class));
        cards.add(new SetCardInfo("Fodder Tosser", 226, Rarity.COMMON, mage.cards.f.FodderTosser.class));
        cards.add(new SetCardInfo("Forest", 489, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 490, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Foul Watcher", 43, Rarity.COMMON, mage.cards.f.FoulWatcher.class));
        cards.add(new SetCardInfo("Foundation Breaker", 160, Rarity.UNCOMMON, mage.cards.f.FoundationBreaker.class));
        cards.add(new SetCardInfo("Foundry Helix", 196, Rarity.COMMON, mage.cards.f.FoundryHelix.class));
        cards.add(new SetCardInfo("Fractured Sanity", 336, Rarity.RARE, mage.cards.f.FracturedSanity.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Fractured Sanity", 44, Rarity.RARE, mage.cards.f.FracturedSanity.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Funnel-Web Recluse", 161, Rarity.COMMON, mage.cards.f.FunnelWebRecluse.class));
        cards.add(new SetCardInfo("Fury", 126, Rarity.MYTHIC, mage.cards.f.Fury.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Fury", 313, Rarity.MYTHIC, mage.cards.f.Fury.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gaea's Will", 162, Rarity.RARE, mage.cards.g.GaeasWill.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gaea's Will", 412, Rarity.RARE, mage.cards.g.GaeasWill.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gaea's Will", 461, Rarity.RARE, mage.cards.g.GaeasWill.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Galvanic Relay", 127, Rarity.COMMON, mage.cards.g.GalvanicRelay.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Galvanic Relay", 406, Rarity.COMMON, mage.cards.g.GalvanicRelay.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gargadon", 128, Rarity.COMMON, mage.cards.g.Gargadon.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gargadon", 351, Rarity.COMMON, mage.cards.g.Gargadon.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Garth One-Eye", 197, Rarity.MYTHIC, mage.cards.g.GarthOneEye.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Garth One-Eye", 365, Rarity.MYTHIC, mage.cards.g.GarthOneEye.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Garth One-Eye", 420, Rarity.MYTHIC, mage.cards.g.GarthOneEye.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("General Ferrous Rokiric", 198, Rarity.RARE, mage.cards.g.GeneralFerrousRokiric.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("General Ferrous Rokiric", 366, Rarity.RARE, mage.cards.g.GeneralFerrousRokiric.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Geyadrone Dihada", 199, Rarity.MYTHIC, mage.cards.g.GeyadroneDihada.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Geyadrone Dihada", 305, Rarity.MYTHIC, mage.cards.g.GeyadroneDihada.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Geyadrone Dihada", 367, Rarity.MYTHIC, mage.cards.g.GeyadroneDihada.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ghost-Lit Drifter", 45, Rarity.UNCOMMON, mage.cards.g.GhostLitDrifter.class));
        cards.add(new SetCardInfo("Gilt-Blade Prowler", 86, Rarity.COMMON, mage.cards.g.GiltBladeProwler.class));
        cards.add(new SetCardInfo("Glimmer Bairn", 163, Rarity.COMMON, mage.cards.g.GlimmerBairn.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Glimmer Bairn", 413, Rarity.COMMON, mage.cards.g.GlimmerBairn.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Glimpse of Tomorrow", 129, Rarity.RARE, mage.cards.g.GlimpseOfTomorrow.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Glimpse of Tomorrow", 407, Rarity.RARE, mage.cards.g.GlimpseOfTomorrow.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Glimpse of Tomorrow", 458, Rarity.RARE, mage.cards.g.GlimpseOfTomorrow.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Glinting Creeper", 164, Rarity.UNCOMMON, mage.cards.g.GlintingCreeper.class));
        cards.add(new SetCardInfo("Glorious Enforcer", 14, Rarity.UNCOMMON, mage.cards.g.GloriousEnforcer.class));
        cards.add(new SetCardInfo("Goblin Anarchomancer", 200, Rarity.COMMON, mage.cards.g.GoblinAnarchomancer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Goblin Anarchomancer", 421, Rarity.COMMON, mage.cards.g.GoblinAnarchomancer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Goblin Bombardment", 279, Rarity.RARE, mage.cards.g.GoblinBombardment.class));
        cards.add(new SetCardInfo("Goblin Traprunner", 130, Rarity.UNCOMMON, mage.cards.g.GoblinTraprunner.class));
        cards.add(new SetCardInfo("Goldmire Bridge", 247, Rarity.COMMON, mage.cards.g.GoldmireBridge.class));
        cards.add(new SetCardInfo("Gorilla Shaman", 280, Rarity.UNCOMMON, mage.cards.g.GorillaShaman.class));
        cards.add(new SetCardInfo("Gouged Zealot", 131, Rarity.COMMON, mage.cards.g.GougedZealot.class));
        cards.add(new SetCardInfo("Graceful Restoration", 201, Rarity.UNCOMMON, mage.cards.g.GracefulRestoration.class));
        cards.add(new SetCardInfo("Greed", 274, Rarity.UNCOMMON, mage.cards.g.Greed.class));
        cards.add(new SetCardInfo("Grief", 311, Rarity.MYTHIC, mage.cards.g.Grief.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Grief", 87, Rarity.MYTHIC, mage.cards.g.Grief.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Grist, the Hunger Tide", 202, Rarity.MYTHIC, mage.cards.g.GristTheHungerTide.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Grist, the Hunger Tide", 306, Rarity.MYTHIC, mage.cards.g.GristTheHungerTide.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Grist, the Hunger Tide", 368, Rarity.MYTHIC, mage.cards.g.GristTheHungerTide.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Guardian Kirin", 15, Rarity.COMMON, mage.cards.g.GuardianKirin.class));
        cards.add(new SetCardInfo("Hard Evidence", 46, Rarity.COMMON, mage.cards.h.HardEvidence.class));
        cards.add(new SetCardInfo("Harmonic Prodigy", 132, Rarity.RARE, mage.cards.h.HarmonicProdigy.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Harmonic Prodigy", 352, Rarity.RARE, mage.cards.h.HarmonicProdigy.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Healer's Flock", 16, Rarity.UNCOMMON, mage.cards.h.HealersFlock.class));
        cards.add(new SetCardInfo("Hell Mongrel", 88, Rarity.COMMON, mage.cards.h.HellMongrel.class));
        cards.add(new SetCardInfo("Herd Baloth", 165, Rarity.UNCOMMON, mage.cards.h.HerdBaloth.class));
        cards.add(new SetCardInfo("Hunting Pack", 284, Rarity.UNCOMMON, mage.cards.h.HuntingPack.class));
        cards.add(new SetCardInfo("Ignoble Hierarch", 166, Rarity.RARE, mage.cards.i.IgnobleHierarch.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ignoble Hierarch", 355, Rarity.RARE, mage.cards.i.IgnobleHierarch.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ignoble Hierarch", 414, Rarity.RARE, mage.cards.i.IgnobleHierarch.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Imperial Recruiter", 281, Rarity.MYTHIC, mage.cards.i.ImperialRecruiter.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Imperial Recruiter", 314, Rarity.MYTHIC, mage.cards.i.ImperialRecruiter.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Inevitable Betrayal", 390, Rarity.RARE, mage.cards.i.InevitableBetrayal.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Inevitable Betrayal", 446, Rarity.RARE, mage.cards.i.InevitableBetrayal.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Inevitable Betrayal", 47, Rarity.RARE, mage.cards.i.InevitableBetrayal.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 483, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 484, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Jade Avenger", 167, Rarity.COMMON, mage.cards.j.JadeAvenger.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Jade Avenger", 356, Rarity.COMMON, mage.cards.j.JadeAvenger.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Jewel-Eyed Cobra", 168, Rarity.COMMON, mage.cards.j.JewelEyedCobra.class));
        cards.add(new SetCardInfo("Junk Winder", 48, Rarity.UNCOMMON, mage.cards.j.JunkWinder.class));
        cards.add(new SetCardInfo("Kaldra Compleat", 227, Rarity.MYTHIC, mage.cards.k.KaldraCompleat.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kaldra Compleat", 379, Rarity.MYTHIC, mage.cards.k.KaldraCompleat.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kaleidoscorch", 133, Rarity.UNCOMMON, mage.cards.k.Kaleidoscorch.class));
        cards.add(new SetCardInfo("Karmic Guide", 263, Rarity.RARE, mage.cards.k.KarmicGuide.class));
        cards.add(new SetCardInfo("Kitchen Imp", 343, Rarity.COMMON, mage.cards.k.KitchenImp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kitchen Imp", 89, Rarity.COMMON, mage.cards.k.KitchenImp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Knighted Myr", 17, Rarity.COMMON, mage.cards.k.KnightedMyr.class));
        cards.add(new SetCardInfo("Landscaper Colos", 18, Rarity.COMMON, mage.cards.l.LandscaperColos.class));
        cards.add(new SetCardInfo("Late to Dinner", 19, Rarity.COMMON, mage.cards.l.LateToDinner.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Late to Dinner", 329, Rarity.COMMON, mage.cards.l.LateToDinner.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Lazotep Chancellor", 203, Rarity.UNCOMMON, mage.cards.l.LazotepChancellor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Lazotep Chancellor", 369, Rarity.UNCOMMON, mage.cards.l.LazotepChancellor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Legion Vanguard", 90, Rarity.UNCOMMON, mage.cards.l.LegionVanguard.class));
        cards.add(new SetCardInfo("Lens Flare", 20, Rarity.COMMON, mage.cards.l.LensFlare.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Lens Flare", 330, Rarity.COMMON, mage.cards.l.LensFlare.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Lightning Spear", 134, Rarity.COMMON, mage.cards.l.LightningSpear.class));
        cards.add(new SetCardInfo("Liquimetal Torque", 228, Rarity.UNCOMMON, mage.cards.l.LiquimetalTorque.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Liquimetal Torque", 428, Rarity.UNCOMMON, mage.cards.l.LiquimetalTorque.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Loathsome Curator", 91, Rarity.COMMON, mage.cards.l.LoathsomeCurator.class));
        cards.add(new SetCardInfo("Lonis, Cryptozoologist", 204, Rarity.RARE, mage.cards.l.LonisCryptozoologist.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Lonis, Cryptozoologist", 370, Rarity.RARE, mage.cards.l.LonisCryptozoologist.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Lose Focus", 49, Rarity.COMMON, mage.cards.l.LoseFocus.class));
        cards.add(new SetCardInfo("Lucid Dreams", 50, Rarity.UNCOMMON, mage.cards.l.LucidDreams.class));
        cards.add(new SetCardInfo("Magus of the Bridge", 344, Rarity.RARE, mage.cards.m.MagusOfTheBridge.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Magus of the Bridge", 92, Rarity.RARE, mage.cards.m.MagusOfTheBridge.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Marble Gargoyle", 21, Rarity.COMMON, mage.cards.m.MarbleGargoyle.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Marble Gargoyle", 382, Rarity.COMMON, mage.cards.m.MarbleGargoyle.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Marsh Flats", 248, Rarity.RARE, mage.cards.m.MarshFlats.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Marsh Flats", 437, Rarity.RARE, mage.cards.m.MarshFlats.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Marsh Flats", 476, Rarity.RARE, mage.cards.m.MarshFlats.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Master of Death", 205, Rarity.RARE, mage.cards.m.MasterOfDeath.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Master of Death", 465, Rarity.RARE, mage.cards.m.MasterOfDeath.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mental Journey", 51, Rarity.COMMON, mage.cards.m.MentalJourney.class));
        cards.add(new SetCardInfo("Millikin", 297, Rarity.UNCOMMON, mage.cards.m.Millikin.class));
        cards.add(new SetCardInfo("Mine Collapse", 135, Rarity.COMMON, mage.cards.m.MineCollapse.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mine Collapse", 408, Rarity.COMMON, mage.cards.m.MineCollapse.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mirari's Wake", 291, Rarity.MYTHIC, mage.cards.m.MirarisWake.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mirari's Wake", 320, Rarity.MYTHIC, mage.cards.m.MirarisWake.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mishra's Factory", 302, Rarity.UNCOMMON, mage.cards.m.MishrasFactory.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mishra's Factory", 326, Rarity.RARE, mage.cards.m.MishrasFactory.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mistvault Bridge", 249, Rarity.COMMON, mage.cards.m.MistvaultBridge.class));
        cards.add(new SetCardInfo("Misty Rainforest", 250, Rarity.RARE, mage.cards.m.MistyRainforest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Misty Rainforest", 438, Rarity.RARE, mage.cards.m.MistyRainforest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Misty Rainforest", 477, Rarity.RARE, mage.cards.m.MistyRainforest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Moderation", 206, Rarity.RARE, mage.cards.m.Moderation.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Moderation", 371, Rarity.RARE, mage.cards.m.Moderation.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mogg Salvage", 282, Rarity.UNCOMMON, mage.cards.m.MoggSalvage.class));
        cards.add(new SetCardInfo("Monoskelion", 229, Rarity.UNCOMMON, mage.cards.m.Monoskelion.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Monoskelion", 429, Rarity.UNCOMMON, mage.cards.m.Monoskelion.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mount Velus Manticore", 136, Rarity.COMMON, mage.cards.m.MountVelusManticore.class));
        cards.add(new SetCardInfo("Mountain", 487, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 488, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Murktide Regent", 337, Rarity.MYTHIC, mage.cards.m.MurktideRegent.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Murktide Regent", 52, Rarity.MYTHIC, mage.cards.m.MurktideRegent.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Myr Scrapling", 230, Rarity.COMMON, mage.cards.m.MyrScrapling.class));
        cards.add(new SetCardInfo("Mystic Redaction", 338, Rarity.UNCOMMON, mage.cards.m.MysticRedaction.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mystic Redaction", 53, Rarity.UNCOMMON, mage.cards.m.MysticRedaction.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Necrogoyf", 398, Rarity.RARE, mage.cards.n.Necrogoyf.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Necrogoyf", 451, Rarity.RARE, mage.cards.n.Necrogoyf.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Necrogoyf", 93, Rarity.RARE, mage.cards.n.Necrogoyf.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Necromancer's Familiar", 94, Rarity.UNCOMMON, mage.cards.n.NecromancersFamiliar.class));
        cards.add(new SetCardInfo("Nested Shambler", 399, Rarity.COMMON, mage.cards.n.NestedShambler.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Nested Shambler", 95, Rarity.COMMON, mage.cards.n.NestedShambler.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Nettlecyst", 231, Rarity.RARE, mage.cards.n.Nettlecyst.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Nettlecyst", 471, Rarity.RARE, mage.cards.n.Nettlecyst.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Nevinyrral's Disk", 298, Rarity.RARE, mage.cards.n.NevinyrralsDisk.class));
        cards.add(new SetCardInfo("Nykthos Paragon", 22, Rarity.RARE, mage.cards.n.NykthosParagon.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Nykthos Paragon", 331, Rarity.RARE, mage.cards.n.NykthosParagon.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Obsidian Charmaw", 137, Rarity.RARE, mage.cards.o.ObsidianCharmaw.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Obsidian Charmaw", 353, Rarity.RARE, mage.cards.o.ObsidianCharmaw.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Orchard Strider", 169, Rarity.COMMON, mage.cards.o.OrchardStrider.class));
        cards.add(new SetCardInfo("Ornithopter of Paradise", 232, Rarity.COMMON, mage.cards.o.OrnithopterOfParadise.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ornithopter of Paradise", 430, Rarity.COMMON, mage.cards.o.OrnithopterOfParadise.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Out of Time", 23, Rarity.RARE, mage.cards.o.OutOfTime.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Out of Time", 383, Rarity.RARE, mage.cards.o.OutOfTime.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Out of Time", 442, Rarity.RARE, mage.cards.o.OutOfTime.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Parcel Myr", 54, Rarity.COMMON, mage.cards.p.ParcelMyr.class));
        cards.add(new SetCardInfo("Patchwork Gnomes", 299, Rarity.UNCOMMON, mage.cards.p.PatchworkGnomes.class));
        cards.add(new SetCardInfo("Patriarch's Bidding", 275, Rarity.RARE, mage.cards.p.PatriarchsBidding.class));
        cards.add(new SetCardInfo("Persist", 345, Rarity.RARE, mage.cards.p.Persist.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Persist", 400, Rarity.RARE, mage.cards.p.Persist.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Persist", 96, Rarity.RARE, mage.cards.p.Persist.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Phantasmal Dreadmaw", 339, Rarity.COMMON, mage.cards.p.PhantasmalDreadmaw.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Phantasmal Dreadmaw", 55, Rarity.COMMON, mage.cards.p.PhantasmalDreadmaw.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Piercing Rays", 24, Rarity.COMMON, mage.cards.p.PiercingRays.class));
        cards.add(new SetCardInfo("Piru, the Volatile", 207, Rarity.RARE, mage.cards.p.PiruTheVolatile.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Piru, the Volatile", 422, Rarity.RARE, mage.cards.p.PiruTheVolatile.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Piru, the Volatile", 466, Rarity.RARE, mage.cards.p.PiruTheVolatile.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 481, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 482, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Power Depot", 251, Rarity.UNCOMMON, mage.cards.p.PowerDepot.class));
        cards.add(new SetCardInfo("Priest of Fell Rites", 208, Rarity.RARE, mage.cards.p.PriestOfFellRites.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Priest of Fell Rites", 372, Rarity.RARE, mage.cards.p.PriestOfFellRites.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Prismatic Ending", 25, Rarity.UNCOMMON, mage.cards.p.PrismaticEnding.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Prismatic Ending", 384, Rarity.UNCOMMON, mage.cards.p.PrismaticEnding.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Profane Tutor", 401, Rarity.RARE, mage.cards.p.ProfaneTutor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Profane Tutor", 452, Rarity.RARE, mage.cards.p.ProfaneTutor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Profane Tutor", 97, Rarity.RARE, mage.cards.p.ProfaneTutor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Prophetic Titan", 209, Rarity.UNCOMMON, mage.cards.p.PropheticTitan.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Prophetic Titan", 373, Rarity.UNCOMMON, mage.cards.p.PropheticTitan.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Quirion Ranger", 285, Rarity.UNCOMMON, mage.cards.q.QuirionRanger.class));
        cards.add(new SetCardInfo("Radiant Epicure", 98, Rarity.UNCOMMON, mage.cards.r.RadiantEpicure.class));
        cards.add(new SetCardInfo("Ragavan, Nimble Pilferer", 138, Rarity.MYTHIC, mage.cards.r.RagavanNimblePilferer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ragavan, Nimble Pilferer", 315, Rarity.MYTHIC, mage.cards.r.RagavanNimblePilferer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Rakdos Headliner", 210, Rarity.UNCOMMON, mage.cards.r.RakdosHeadliner.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Rakdos Headliner", 374, Rarity.UNCOMMON, mage.cards.r.RakdosHeadliner.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ravenous Squirrel", 211, Rarity.UNCOMMON, mage.cards.r.RavenousSquirrel.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ravenous Squirrel", 375, Rarity.UNCOMMON, mage.cards.r.RavenousSquirrel.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Raving Visionary", 56, Rarity.UNCOMMON, mage.cards.r.RavingVisionary.class));
        cards.add(new SetCardInfo("Razortide Bridge", 252, Rarity.COMMON, mage.cards.r.RazortideBridge.class));
        cards.add(new SetCardInfo("Recalibrate", 57, Rarity.COMMON, mage.cards.r.Recalibrate.class));
        cards.add(new SetCardInfo("Resurgent Belief", 26, Rarity.RARE, mage.cards.r.ResurgentBelief.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Resurgent Belief", 385, Rarity.RARE, mage.cards.r.ResurgentBelief.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Resurgent Belief", 443, Rarity.RARE, mage.cards.r.ResurgentBelief.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Revolutionist", 139, Rarity.COMMON, mage.cards.r.Revolutionist.class));
        cards.add(new SetCardInfo("Rift Sower", 170, Rarity.COMMON, mage.cards.r.RiftSower.class));
        cards.add(new SetCardInfo("Riptide Laboratory", 303, Rarity.RARE, mage.cards.r.RiptideLaboratory.class));
        cards.add(new SetCardInfo("Rise and Shine", 340, Rarity.RARE, mage.cards.r.RiseAndShine.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Rise and Shine", 58, Rarity.RARE, mage.cards.r.RiseAndShine.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Rishadan Dockhand", 391, Rarity.RARE, mage.cards.r.RishadanDockhand.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Rishadan Dockhand", 447, Rarity.RARE, mage.cards.r.RishadanDockhand.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Rishadan Dockhand", 59, Rarity.RARE, mage.cards.r.RishadanDockhand.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Road // Ruin", 212, Rarity.UNCOMMON, mage.cards.r.RoadRuin.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Road // Ruin", 376, Rarity.UNCOMMON, mage.cards.r.RoadRuin.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Rustvale Bridge", 253, Rarity.COMMON, mage.cards.r.RustvaleBridge.class));
        cards.add(new SetCardInfo("Said // Done", 60, Rarity.UNCOMMON, mage.cards.s.SaidDone.class));
        cards.add(new SetCardInfo("Sanctifier en-Vec", 27, Rarity.RARE, mage.cards.s.SanctifierEnVec.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sanctifier en-Vec", 386, Rarity.RARE, mage.cards.s.SanctifierEnVec.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sanctifier en-Vec", 444, Rarity.RARE, mage.cards.s.SanctifierEnVec.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sanctuary Raptor", 233, Rarity.UNCOMMON, mage.cards.s.SanctuaryRaptor.class));
        cards.add(new SetCardInfo("Sanctum Prelate", 491, Rarity.MYTHIC, mage.cards.s.SanctumPrelate.class));
        cards.add(new SetCardInfo("Sanctum Weaver", 171, Rarity.RARE, mage.cards.s.SanctumWeaver.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sanctum Weaver", 462, Rarity.RARE, mage.cards.s.SanctumWeaver.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Scalding Tarn", 254, Rarity.RARE, mage.cards.s.ScaldingTarn.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Scalding Tarn", 439, Rarity.RARE, mage.cards.s.ScaldingTarn.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Scalding Tarn", 478, Rarity.RARE, mage.cards.s.ScaldingTarn.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Scion of Draco", 234, Rarity.MYTHIC, mage.cards.s.ScionOfDraco.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Scion of Draco", 323, Rarity.MYTHIC, mage.cards.s.ScionOfDraco.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Scion of Draco", 431, Rarity.MYTHIC, mage.cards.s.ScionOfDraco.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Scour the Desert", 28, Rarity.UNCOMMON, mage.cards.s.ScourTheDesert.class));
        cards.add(new SetCardInfo("Scurry Oak", 172, Rarity.UNCOMMON, mage.cards.s.ScurryOak.class));
        cards.add(new SetCardInfo("Scuttletide", 61, Rarity.UNCOMMON, mage.cards.s.Scuttletide.class));
        cards.add(new SetCardInfo("Sea Drake", 268, Rarity.UNCOMMON, mage.cards.s.SeaDrake.class));
        cards.add(new SetCardInfo("Seal of Cleansing", 264, Rarity.UNCOMMON, mage.cards.s.SealOfCleansing.class));
        cards.add(new SetCardInfo("Seal of Removal", 269, Rarity.UNCOMMON, mage.cards.s.SealOfRemoval.class));
        cards.add(new SetCardInfo("Search the Premises", 29, Rarity.RARE, mage.cards.s.SearchThePremises.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Search the Premises", 332, Rarity.RARE, mage.cards.s.SearchThePremises.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Serra's Emissary", 30, Rarity.MYTHIC, mage.cards.s.SerrasEmissary.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Serra's Emissary", 333, Rarity.MYTHIC, mage.cards.s.SerrasEmissary.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Shardless Agent", 292, Rarity.RARE, mage.cards.s.ShardlessAgent.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Shardless Agent", 321, Rarity.RARE, mage.cards.s.ShardlessAgent.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Shardless Agent", 423, Rarity.RARE, mage.cards.s.ShardlessAgent.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Shattered Ego", 62, Rarity.COMMON, mage.cards.s.ShatteredEgo.class));
        cards.add(new SetCardInfo("Silverbluff Bridge", 255, Rarity.COMMON, mage.cards.s.SilverbluffBridge.class));
        cards.add(new SetCardInfo("Sinister Starfish", 99, Rarity.COMMON, mage.cards.s.SinisterStarfish.class));
        cards.add(new SetCardInfo("Skirge Familiar", 276, Rarity.UNCOMMON, mage.cards.s.SkirgeFamiliar.class));
        cards.add(new SetCardInfo("Skophos Reaver", 140, Rarity.COMMON, mage.cards.s.SkophosReaver.class));
        cards.add(new SetCardInfo("Skyblade's Boon", 31, Rarity.UNCOMMON, mage.cards.s.SkybladesBoon.class));
        cards.add(new SetCardInfo("Slag Strider", 141, Rarity.UNCOMMON, mage.cards.s.SlagStrider.class));
        cards.add(new SetCardInfo("Slagwoods Bridge", 256, Rarity.COMMON, mage.cards.s.SlagwoodsBridge.class));
        cards.add(new SetCardInfo("Smell Fear", 173, Rarity.COMMON, mage.cards.s.SmellFear.class));
        cards.add(new SetCardInfo("So Shiny", 63, Rarity.COMMON, mage.cards.s.SoShiny.class));
        cards.add(new SetCardInfo("Sojourner's Companion", 235, Rarity.COMMON, mage.cards.s.SojournersCompanion.class));
        cards.add(new SetCardInfo("Sol Talisman", 236, Rarity.RARE, mage.cards.s.SolTalisman.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sol Talisman", 432, Rarity.RARE, mage.cards.s.SolTalisman.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sol Talisman", 472, Rarity.RARE, mage.cards.s.SolTalisman.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Solitary Confinement", 265, Rarity.RARE, mage.cards.s.SolitaryConfinement.class));
        cards.add(new SetCardInfo("Solitude", 307, Rarity.MYTHIC, mage.cards.s.Solitude.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Solitude", 32, Rarity.MYTHIC, mage.cards.s.Solitude.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Soul Snare", 266, Rarity.UNCOMMON, mage.cards.s.SoulSnare.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Soul Snare", 387, Rarity.UNCOMMON, mage.cards.s.SoulSnare.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Soul of Migration", 33, Rarity.COMMON, mage.cards.s.SoulOfMigration.class));
        cards.add(new SetCardInfo("Specimen Collector", 64, Rarity.UNCOMMON, mage.cards.s.SpecimenCollector.class));
        cards.add(new SetCardInfo("Spreading Insurrection", 142, Rarity.UNCOMMON, mage.cards.s.SpreadingInsurrection.class));
        cards.add(new SetCardInfo("Squirrel Mob", 286, Rarity.RARE, mage.cards.s.SquirrelMob.class));
        cards.add(new SetCardInfo("Squirrel Sanctuary", 174, Rarity.UNCOMMON, mage.cards.s.SquirrelSanctuary.class));
        cards.add(new SetCardInfo("Squirrel Sovereign", 175, Rarity.UNCOMMON, mage.cards.s.SquirrelSovereign.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Squirrel Sovereign", 415, Rarity.UNCOMMON, mage.cards.s.SquirrelSovereign.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Steel Dromedary", 237, Rarity.UNCOMMON, mage.cards.s.SteelDromedary.class));
        cards.add(new SetCardInfo("Steelfin Whale", 65, Rarity.COMMON, mage.cards.s.SteelfinWhale.class));
        cards.add(new SetCardInfo("Step Through", 392, Rarity.COMMON, mage.cards.s.StepThrough.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Step Through", 66, Rarity.COMMON, mage.cards.s.StepThrough.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sterling Grove", 293, Rarity.RARE, mage.cards.s.SterlingGrove.class));
        cards.add(new SetCardInfo("Storm God's Oracle", 213, Rarity.COMMON, mage.cards.s.StormGodsOracle.class));
        cards.add(new SetCardInfo("Strike It Rich", 143, Rarity.UNCOMMON, mage.cards.s.StrikeItRich.class));
        cards.add(new SetCardInfo("Subtlety", 309, Rarity.MYTHIC, mage.cards.s.Subtlety.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Subtlety", 67, Rarity.MYTHIC, mage.cards.s.Subtlety.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sudden Edict", 100, Rarity.UNCOMMON, mage.cards.s.SuddenEdict.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sudden Edict", 346, Rarity.UNCOMMON, mage.cards.s.SuddenEdict.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Suspend", 448, Rarity.RARE, mage.cards.s.Suspend.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Suspend", 68, Rarity.RARE, mage.cards.s.Suspend.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Svyelun of Sea and Sky", 310, Rarity.MYTHIC, mage.cards.s.SvyelunOfSeaAndSky.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Svyelun of Sea and Sky", 393, Rarity.MYTHIC, mage.cards.s.SvyelunOfSeaAndSky.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Svyelun of Sea and Sky", 69, Rarity.MYTHIC, mage.cards.s.SvyelunOfSeaAndSky.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 485, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 486, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sweep the Skies", 70, Rarity.UNCOMMON, mage.cards.s.SweepTheSkies.class));
        cards.add(new SetCardInfo("Sword of Hearth and Home", 238, Rarity.MYTHIC, mage.cards.s.SwordOfHearthAndHome.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sword of Hearth and Home", 324, Rarity.MYTHIC, mage.cards.s.SwordOfHearthAndHome.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sword of Hearth and Home", 433, Rarity.MYTHIC, mage.cards.s.SwordOfHearthAndHome.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sylvan Anthem", 176, Rarity.RARE, mage.cards.s.SylvanAnthem.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sylvan Anthem", 357, Rarity.RARE, mage.cards.s.SylvanAnthem.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sythis, Harvest's Hand", 214, Rarity.RARE, mage.cards.s.SythisHarvestsHand.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sythis, Harvest's Hand", 377, Rarity.RARE, mage.cards.s.SythisHarvestsHand.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tanglepool Bridge", 257, Rarity.COMMON, mage.cards.t.TanglepoolBridge.class));
        cards.add(new SetCardInfo("Tavern Scoundrel", 144, Rarity.COMMON, mage.cards.t.TavernScoundrel.class));
        cards.add(new SetCardInfo("Terminal Agony", 215, Rarity.COMMON, mage.cards.t.TerminalAgony.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Terminal Agony", 424, Rarity.COMMON, mage.cards.t.TerminalAgony.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Terramorph", 177, Rarity.UNCOMMON, mage.cards.t.Terramorph.class));
        cards.add(new SetCardInfo("Territorial Kavu", 216, Rarity.RARE, mage.cards.t.TerritorialKavu.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Territorial Kavu", 425, Rarity.RARE, mage.cards.t.TerritorialKavu.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Territorial Kavu", 467, Rarity.RARE, mage.cards.t.TerritorialKavu.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Underworld Cookbook", 240, Rarity.UNCOMMON, mage.cards.t.TheUnderworldCookbook.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Underworld Cookbook", 434, Rarity.UNCOMMON, mage.cards.t.TheUnderworldCookbook.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Thornglint Bridge", 258, Rarity.COMMON, mage.cards.t.ThornglintBridge.class));
        cards.add(new SetCardInfo("Thought Monitor", 341, Rarity.RARE, mage.cards.t.ThoughtMonitor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Thought Monitor", 71, Rarity.RARE, mage.cards.t.ThoughtMonitor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Thraben Watcher", 34, Rarity.UNCOMMON, mage.cards.t.ThrabenWatcher.class));
        cards.add(new SetCardInfo("Thrasta, Tempest's Roar", 178, Rarity.MYTHIC, mage.cards.t.ThrastaTempestsRoar.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Thrasta, Tempest's Roar", 318, Rarity.MYTHIC, mage.cards.t.ThrastaTempestsRoar.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tide Shaper", 394, Rarity.UNCOMMON, mage.cards.t.TideShaper.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tide Shaper", 72, Rarity.UNCOMMON, mage.cards.t.TideShaper.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Timeless Dragon", 35, Rarity.RARE, mage.cards.t.TimelessDragon.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Timeless Dragon", 388, Rarity.RARE, mage.cards.t.TimelessDragon.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Timeless Dragon", 445, Rarity.RARE, mage.cards.t.TimelessDragon.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Timeless Witness", 179, Rarity.UNCOMMON, mage.cards.t.TimelessWitness.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Timeless Witness", 358, Rarity.UNCOMMON, mage.cards.t.TimelessWitness.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tireless Provisioner", 180, Rarity.UNCOMMON, mage.cards.t.TirelessProvisioner.class));
        cards.add(new SetCardInfo("Titania, Protector of Argoth", 287, Rarity.MYTHIC, mage.cards.t.TitaniaProtectorOfArgoth.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Titania, Protector of Argoth", 319, Rarity.MYTHIC, mage.cards.t.TitaniaProtectorOfArgoth.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Titania, Protector of Argoth", 416, Rarity.MYTHIC, mage.cards.t.TitaniaProtectorOfArgoth.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tizerus Charger", 101, Rarity.COMMON, mage.cards.t.TizerusCharger.class));
        cards.add(new SetCardInfo("Tormod's Cryptkeeper", 239, Rarity.COMMON, mage.cards.t.TormodsCryptkeeper.class));
        cards.add(new SetCardInfo("Tourach's Canticle", 103, Rarity.COMMON, mage.cards.t.TourachsCanticle.class));
        cards.add(new SetCardInfo("Tourach, Dread Cantor", 102, Rarity.MYTHIC, mage.cards.t.TourachDreadCantor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tourach, Dread Cantor", 312, Rarity.MYTHIC, mage.cards.t.TourachDreadCantor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tourach, Dread Cantor", 402, Rarity.MYTHIC, mage.cards.t.TourachDreadCantor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tragic Fall", 104, Rarity.COMMON, mage.cards.t.TragicFall.class));
        cards.add(new SetCardInfo("Unbounded Potential", 36, Rarity.COMMON, mage.cards.u.UnboundedPotential.class));
        cards.add(new SetCardInfo("Underworld Hermit", 105, Rarity.UNCOMMON, mage.cards.u.UnderworldHermit.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Underworld Hermit", 347, Rarity.UNCOMMON, mage.cards.u.UnderworldHermit.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Unholy Heat", 145, Rarity.COMMON, mage.cards.u.UnholyHeat.class));
        cards.add(new SetCardInfo("Unmarked Grave", 106, Rarity.RARE, mage.cards.u.UnmarkedGrave.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Unmarked Grave", 453, Rarity.RARE, mage.cards.u.UnmarkedGrave.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Upheaval", 270, Rarity.RARE, mage.cards.u.Upheaval.class));
        cards.add(new SetCardInfo("Urban Daggertooth", 181, Rarity.COMMON, mage.cards.u.UrbanDaggertooth.class));
        cards.add(new SetCardInfo("Urza's Saga", 259, Rarity.RARE, mage.cards.u.UrzasSaga.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Urza's Saga", 380, Rarity.RARE, mage.cards.u.UrzasSaga.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Vectis Gloves", 241, Rarity.UNCOMMON, mage.cards.v.VectisGloves.class));
        cards.add(new SetCardInfo("Vedalken Infiltrator", 73, Rarity.UNCOMMON, mage.cards.v.VedalkenInfiltrator.class));
        cards.add(new SetCardInfo("Verdant Catacombs", 260, Rarity.RARE, mage.cards.v.VerdantCatacombs.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Verdant Catacombs", 440, Rarity.RARE, mage.cards.v.VerdantCatacombs.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Verdant Catacombs", 479, Rarity.RARE, mage.cards.v.VerdantCatacombs.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Verdant Command", 182, Rarity.RARE, mage.cards.v.VerdantCommand.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Verdant Command", 359, Rarity.RARE, mage.cards.v.VerdantCommand.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Vermin Gorger", 107, Rarity.COMMON, mage.cards.v.VerminGorger.class));
        cards.add(new SetCardInfo("Viashino Lashclaw", 146, Rarity.COMMON, mage.cards.v.ViashinoLashclaw.class));
        cards.add(new SetCardInfo("Vile Entomber", 108, Rarity.UNCOMMON, mage.cards.v.VileEntomber.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Vile Entomber", 403, Rarity.UNCOMMON, mage.cards.v.VileEntomber.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Vindicate", 294, Rarity.RARE, mage.cards.v.Vindicate.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Vindicate", 322, Rarity.RARE, mage.cards.v.Vindicate.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Void Mirror", 242, Rarity.RARE, mage.cards.v.VoidMirror.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Void Mirror", 435, Rarity.RARE, mage.cards.v.VoidMirror.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Void Mirror", 473, Rarity.RARE, mage.cards.v.VoidMirror.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Wavesifter", 217, Rarity.COMMON, mage.cards.w.Wavesifter.class));
        cards.add(new SetCardInfo("Wonder", 271, Rarity.RARE, mage.cards.w.Wonder.class));
        cards.add(new SetCardInfo("World-Weary", 109, Rarity.COMMON, mage.cards.w.WorldWeary.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("World-Weary", 348, Rarity.COMMON, mage.cards.w.WorldWeary.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Wren's Run Hydra", 183, Rarity.UNCOMMON, mage.cards.w.WrensRunHydra.class));
        cards.add(new SetCardInfo("Yavimaya Elder", 288, Rarity.UNCOMMON, mage.cards.y.YavimayaElder.class));
        cards.add(new SetCardInfo("Yavimaya, Cradle of Growth", 261, Rarity.RARE, mage.cards.y.YavimayaCradleOfGrowth.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Yavimaya, Cradle of Growth", 441, Rarity.RARE, mage.cards.y.YavimayaCradleOfGrowth.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Yavimaya, Cradle of Growth", 480, Rarity.RARE, mage.cards.y.YavimayaCradleOfGrowth.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Young Necromancer", 110, Rarity.UNCOMMON, mage.cards.y.YoungNecromancer.class));
        cards.add(new SetCardInfo("Yusri, Fortune's Flame", 218, Rarity.RARE, mage.cards.y.YusriFortunesFlame.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Yusri, Fortune's Flame", 468, Rarity.RARE, mage.cards.y.YusriFortunesFlame.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Yusri, Fortune's Flame", 492, Rarity.RARE, mage.cards.y.YusriFortunesFlame.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Zabaz, the Glimmerwasp", 243, Rarity.RARE, mage.cards.z.ZabazTheGlimmerwasp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Zabaz, the Glimmerwasp", 474, Rarity.RARE, mage.cards.z.ZabazTheGlimmerwasp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Zuran Orb", 300, Rarity.UNCOMMON, mage.cards.z.ZuranOrb.class));
    }

    @Override
    protected void addSpecialCards(List<Card> booster, int number) {
        // number is here always 1
        Rarity rarity;
        int rarityKey = RandomUtil.nextInt(120);
        if (rarityKey < 4) {
            rarity = Rarity.MYTHIC;
        } else if (rarityKey < 40) {
            rarity = Rarity.RARE;
        } else {
            rarity = Rarity.UNCOMMON;
        }
        List<CardInfo> reprintCards = getSpecialCardsByRarity(rarity);
        addToBooster(booster, reprintCards);
    }

    @Override
    protected List<CardInfo> findSpecialCardsByRarity(Rarity rarity) {
        return CardRepository.instance.findCards(new CardCriteria()
                .setCodes(this.code)
                .rarities(rarity)
                .minCardNumber(262)
                .maxCardNumber(maxCardNumberInBooster));
    }

    @Override
    public BoosterCollator createCollator() {
        return new ModernHorizons2Collator();
    }
}

// Booster collation info from https://www.lethe.xyz/mtg/collation/mh2.html
// Using US collation
// TODO: add reprint variants (waiting on more info for this)
class ModernHorizons2Collator implements BoosterCollator {

    private final CardRun commonA = new CardRun(true, "62", "134", "38", "156", "136", "168", "43", "170", "145", "65", "154", "112", "49", "169", "131", "51", "155", "144", "46", "181", "114", "40", "173", "111", "57", "159", "140", "54", "149", "139", "62", "161", "136", "38", "168", "134", "63", "146", "156", "65", "145", "170", "43", "131", "169", "51", "114", "154", "49", "112", "155", "46", "140", "181", "40", "144", "173", "63", "139", "159", "57", "111", "161", "54", "146", "149");
    private final CardRun commonB = new CardRun(true, "15", "395", "8", "95", "21", "89", "381", "88", "36", "86", "4", "109", "20", "91", "17", "107", "18", "78", "33", "99", "329", "76", "15", "95", "8", "101", "21", "88", "6", "86", "4", "343", "36", "109", "17", "91", "18", "78", "330", "107", "15", "99", "19", "399", "33", "76", "8", "88", "382", "86", "6", "101", "4", "348", "36", "91", "17", "89", "18", "107", "20", "78", "19", "99", "33", "101");
    private final CardRun commonC1 = new CardRun(true, "3", "246", "24", "253", "190", "103", "226", "187", "239", "252", "230", "255", "11", "213", "249", "83", "256", "104", "194", "13", "82", "24", "257", "193", "245", "3", "196", "235", "246", "188", "222", "190", "253", "187", "252", "230", "226", "255", "11", "239", "103", "249", "194", "104", "13", "82", "213", "256", "196", "245", "83", "193", "257", "188", "235");
    private final CardRun commonC2 = new CardRun(true, "424", "163", "349", "167", "37", "152", "430", "200", "354", "135", "247", "258", "55", "217", "127", "351", "163", "122", "215", "152", "66", "356", "42", "408", "200", "147", "232", "37", "406", "247", "55", "258", "217", "128", "335", "167", "215", "411", "66", "413", "122", "135", "421", "147", "232", "389", "127", "339", "258", "247", "217", "222", "392", "128", "42");
    private final CardRun uncommonA = new CardRun(true, "5", "429", "172", "125", "110", "369", "2", "228", "165", "123", "28", "185", "64", "150", "98", "14", "164", "376", "94", "237", "191", "143", "251", "108", "360", "221", "113", "179", "85", "124", "241", "16", "79", "56", "195", "77", "220", "2", "110", "174", "50", "5", "203", "404", "229", "172", "125", "28", "228", "150", "361", "123", "98", "64", "165", "143", "94", "14", "164", "191", "237", "251", "212", "221", "403", "124", "179", "16", "85", "184", "113", "241", "79", "56", "364", "77", "174", "220", "50", "2", "110", "150", "28", "115", "229", "5", "350", "203", "172", "428", "64", "98", "123", "165", "185", "94", "14", "143", "212", "164", "221", "362", "251", "108", "124", "237", "358", "16", "113", "184", "220", "85", "195", "174", "79", "241", "56", "77", "115", "50");
    private final CardRun uncommonB = new CardRun(true, "74", "373", "141", "60", "183", "31", "70", "233", "10", "130", "211", "72", "180", "133", "41", "160", "346", "25", "121", "48", "210", "90", "177", "201", "34", "73", "9", "434", "119", "105", "1", "426", "53", "84", "175", "45", "141", "327", "60", "142", "209", "74", "61", "158", "233", "72", "133", "180", "10", "130", "375", "160", "31", "90", "48", "183", "210", "70", "384", "100", "41", "121", "201", "9", "73", "240", "177", "34", "45", "84", "415", "61", "119", "1", "53", "347", "223", "141", "60", "209", "142", "7", "158", "74", "133", "180", "31", "394", "211", "10", "233", "130", "183", "70", "100", "48", "160", "90", "25", "374", "121", "41", "177", "9", "240", "73", "201", "34", "175", "45", "223", "84", "338", "1", "119", "105", "61", "158", "142", "7");
    private final CardRun rareA = new CardRun(false, "12", "12", "22", "22", "23", "23", "26", "26", "27", "27", "29", "29", "30", "32", "35", "35", "39", "39", "44", "44", "47", "47", "52", "58", "58", "59", "59", "67", "68", "68", "69", "71", "71", "75", "80", "80", "81", "81", "87", "92", "92", "93", "93", "96", "96", "97", "97", "102", "106", "106", "116", "116", "117", "117", "118", "118", "120", "120", "126", "129", "129", "132", "132", "137", "137", "138", "148", "148", "151", "153", "153", "157", "162", "162", "166", "166", "171", "171", "176", "176", "178", "182", "182", "186", "186", "189", "189", "192", "197", "198", "198", "199", "202", "204", "204", "205", "205", "206", "206", "207", "207", "208", "208", "214", "214", "216", "216", "218", "218", "219", "219", "224", "224", "225", "225", "227", "231", "231", "234", "236", "236", "238", "242", "242", "243", "243", "244", "244", "248", "248", "250", "250", "254", "254", "259", "259", "260", "260", "261", "261");
    private final CardRun rareB = new CardRun(false, "328", "328", "331", "331", "332", "332", "333", "334", "334", "336", "336", "337", "340", "340", "341", "341", "342", "344", "344", "345", "345", "352", "352", "353", "353", "355", "355", "357", "357", "359", "359", "363", "365", "366", "366", "367", "368", "370", "370", "371", "371", "372", "372", "377", "377", "378", "378", "379", "380", "380", "383", "383", "385", "385", "386", "386", "388", "388", "390", "390", "391", "391", "393", "396", "396", "397", "397", "398", "398", "400", "400", "401", "401", "402", "405", "405", "407", "407", "409", "409", "410", "412", "412", "414", "414", "417", "417", "418", "418", "420", "422", "422", "425", "425", "427", "427", "431", "432", "432", "433", "435", "435", "436", "436", "437", "437", "438", "438", "439", "439", "440", "440", "441", "441");
    private final CardRun rareC = new CardRun(false, "304", "305", "306", "307", "309", "310", "311", "312", "313", "315", "316", "317", "318", "323", "324");
    private final CardRun reprint = new CardRun(false, "262", "264", "266", "267", "268", "269", "272", "274", "276", "278", "280", "282", "284", "285", "288", "296", "297", "299", "300", "302", "262", "264", "266", "267", "268", "269", "272", "274", "276", "278", "280", "282", "284", "285", "288", "296", "297", "299", "300", "302", "262", "264", "266", "267", "268", "269", "272", "274", "276", "278", "280", "282", "284", "285", "288", "296", "297", "299", "300", "302", "262", "264", "266", "267", "268", "269", "272", "274", "276", "278", "280", "282", "284", "285", "288", "296", "297", "299", "300", "302", "263", "265", "270", "271", "273", "275", "277", "279", "283", "286", "289", "290", "292", "293", "294", "295", "298", "303", "263", "265", "270", "271", "273", "275", "277", "279", "283", "286", "289", "290", "292", "293", "294", "295", "298", "303", "281", "287", "291", "301");

    private final BoosterStructure AAABC1C1C1C1C1C1 = new BoosterStructure(
            commonA, commonA, commonA,
            commonB,
            commonC1, commonC1, commonC1, commonC1, commonC1,commonC1
    );
    private final BoosterStructure AAABBC1C1C1C1C1 = new BoosterStructure(
            commonA, commonA, commonA,
            commonB, commonB,
            commonC1, commonC1, commonC1, commonC1, commonC1
    );
    private final BoosterStructure AAABBBC2C2C2C2 = new BoosterStructure(
            commonA, commonA, commonA,
            commonB, commonB, commonB,
            commonC2, commonC2, commonC2, commonC2
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
    private final BoosterStructure AAA = new BoosterStructure(uncommonA, uncommonA, uncommonA);
    private final BoosterStructure BBB = new BoosterStructure(uncommonB, uncommonB, uncommonB);
    private final BoosterStructure R1 = new BoosterStructure(rareA);
    private final BoosterStructure R2 = new BoosterStructure(rareB);
    private final BoosterStructure R3 = new BoosterStructure(rareC);
    private final BoosterStructure RP1 = new BoosterStructure(reprint);

    // In order for equal numbers of each common to exist, the average booster must contain:
    // 3.27 A commons (36 / 11)
    // 2.18 B commons (24 / 11)
    // 2.73 C1 commons (30 / 11, or 60 / 11 in each C1 booster)
    // 1.82 C2 commons (20 / 11, or 40 / 11 in each C2 booster)
    // These numbers are the same for all sets with 101 commons in A/B/C1/C2 print runs
    // and with 10 common slots per booster
    private final RarityConfiguration commonRuns = new RarityConfiguration(
            AAABC1C1C1C1C1C1,
            AAABC1C1C1C1C1C1,
            AAABC1C1C1C1C1C1,
            AAABC1C1C1C1C1C1,
            AAABC1C1C1C1C1C1,
            AAABBC1C1C1C1C1,
            AAABBC1C1C1C1C1,
            AAABBC1C1C1C1C1,
            AAABBC1C1C1C1C1,
            AAABBC1C1C1C1C1,
            AAABBC1C1C1C1C1,

            AAABBBC2C2C2C2,
            AAABBBC2C2C2C2,
            AAABBBC2C2C2C2,
            AAABBBC2C2C2C2,
            AAABBBC2C2C2C2,
            AAAABBC2C2C2C2,
            AAAABBC2C2C2C2,
            AAAABBBC2C2C2,
            AAAABBBC2C2C2,
            AAAABBBC2C2C2,
            AAAABBBC2C2C2
    );
    private final RarityConfiguration uncommonRuns = new RarityConfiguration(AAA, BBB);
    private final RarityConfiguration rareRuns = new RarityConfiguration(
            R1, R1, R1,
            R1, R1, R1,
            R1, R1, R1,
            R1, R1, R1,
            R1, R1, R1,
            R1, R1, R1,
            R1, R1, R1,
            R1, R1, R1,
            R1, R1, R1,
            R1, R1, R1,
            R1, R1, R1,
            R1, R1, R1,
            R1, R1, R1,
            R1, R1, R1,
            R1, R1, R1,
            R1, R1, R1,
            R1, R1, R1,
            R1, R1, R1,
            R1, R1, R1,
            R1, R1, R1,
            R1, R1, R1,
            R1, R1, R1,
            R1, R1, R1,
            R1, R1, R1,
            R1, R1, R1,
            R1, R1, R1,
            R1, R1, R1,
            R1, R1, R1,
            R1, R1, R1,
            R1, R1, R1,
            R1,
            R2, R2,
            R3
    );
    private final RarityConfiguration reprintRuns = new RarityConfiguration(RP1);

    @Override
    public List<String> makeBooster() {
        List<String> booster = new ArrayList<>();
        booster.addAll(commonRuns.getNext().makeRun());
        booster.addAll(uncommonRuns.getNext().makeRun());
        booster.addAll(rareRuns.getNext().makeRun());
        booster.addAll(reprintRuns.getNext().makeRun());
        return booster;
    }
}
