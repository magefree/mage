package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;
import mage.collation.BoosterCollator;
import mage.collation.BoosterStructure;
import mage.collation.CardRun;
import mage.collation.RarityConfiguration;
import mage.util.RandomUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author North
 */
public final class Invasion extends ExpansionSet {

    private static final Invasion instance = new Invasion();

    public static Invasion getInstance() {
        return instance;
    }

    private Invasion() {
        super("Invasion", "INV", ExpansionSet.buildDate(2000, 9, 2), SetType.EXPANSION);
        this.blockName = "Invasion";
        this.hasBoosters = true;
        this.rotationSet = true;
        this.numBoosterLands = 0;
        this.numBoosterCommon = 11;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 0;
        cards.add(new SetCardInfo("Absorb", 226, Rarity.RARE, mage.cards.a.Absorb.class));
        cards.add(new SetCardInfo("Addle", 91, Rarity.UNCOMMON, mage.cards.a.Addle.class));
        cards.add(new SetCardInfo("Aether Rift", 227, Rarity.RARE, mage.cards.a.AetherRift.class));
        cards.add(new SetCardInfo("Aggressive Urge", 181, Rarity.COMMON, mage.cards.a.AggressiveUrge.class));
        cards.add(new SetCardInfo("Agonizing Demise", 92, Rarity.COMMON, mage.cards.a.AgonizingDemise.class));
        cards.add(new SetCardInfo("Alabaster Leech", 1, Rarity.RARE, mage.cards.a.AlabasterLeech.class));
        cards.add(new SetCardInfo("Alloy Golem", 297, Rarity.UNCOMMON, mage.cards.a.AlloyGolem.class));
        cards.add(new SetCardInfo("Ancient Kavu", 136, Rarity.COMMON, mage.cards.a.AncientKavu.class));
        cards.add(new SetCardInfo("Ancient Spring", 319, Rarity.COMMON, mage.cards.a.AncientSpring.class));
        cards.add(new SetCardInfo("Andradite Leech", 93, Rarity.RARE, mage.cards.a.AndraditeLeech.class));
        cards.add(new SetCardInfo("Angelic Shield", 228, Rarity.UNCOMMON, mage.cards.a.AngelicShield.class));
        cards.add(new SetCardInfo("Angel of Mercy", 2, Rarity.UNCOMMON, mage.cards.a.AngelOfMercy.class));
        cards.add(new SetCardInfo("Annihilate", 94, Rarity.UNCOMMON, mage.cards.a.Annihilate.class));
        cards.add(new SetCardInfo("Archaeological Dig", 320, Rarity.UNCOMMON, mage.cards.a.ArchaeologicalDig.class));
        cards.add(new SetCardInfo("Ardent Soldier", 3, Rarity.COMMON, mage.cards.a.ArdentSoldier.class));
        cards.add(new SetCardInfo("Armadillo Cloak", 229, Rarity.COMMON, mage.cards.a.ArmadilloCloak.class));
        cards.add(new SetCardInfo("Armored Guardian", 230, Rarity.RARE, mage.cards.a.ArmoredGuardian.class));
        cards.add(new SetCardInfo("Artifact Mutation", 231, Rarity.RARE, mage.cards.a.ArtifactMutation.class));
        cards.add(new SetCardInfo("Assault // Battery", 295, Rarity.UNCOMMON, mage.cards.a.AssaultBattery.class));
        cards.add(new SetCardInfo("Atalya, Samite Master", 4, Rarity.RARE, mage.cards.a.AtalyaSamiteMaster.class));
        cards.add(new SetCardInfo("Aura Mutation", 232, Rarity.RARE, mage.cards.a.AuraMutation.class));
        cards.add(new SetCardInfo("Aura Shards", 233, Rarity.UNCOMMON, mage.cards.a.AuraShards.class));
        cards.add(new SetCardInfo("Backlash", 234, Rarity.UNCOMMON, mage.cards.b.Backlash.class));
        cards.add(new SetCardInfo("Barrin's Spite", 235, Rarity.RARE, mage.cards.b.BarrinsSpite.class));
        cards.add(new SetCardInfo("Barrin's Unmaking", 46, Rarity.COMMON, mage.cards.b.BarrinsUnmaking.class));
        cards.add(new SetCardInfo("Benalish Emissary", 5, Rarity.UNCOMMON, mage.cards.b.BenalishEmissary.class));
        cards.add(new SetCardInfo("Benalish Heralds", 6, Rarity.UNCOMMON, mage.cards.b.BenalishHeralds.class));
        cards.add(new SetCardInfo("Benalish Lancer", 7, Rarity.COMMON, mage.cards.b.BenalishLancer.class));
        cards.add(new SetCardInfo("Benalish Trapper", 8, Rarity.COMMON, mage.cards.b.BenalishTrapper.class));
        cards.add(new SetCardInfo("Bend or Break", 137, Rarity.RARE, mage.cards.b.BendOrBreak.class));
        cards.add(new SetCardInfo("Bind", 182, Rarity.RARE, mage.cards.b.Bind.class));
        cards.add(new SetCardInfo("Blazing Specter", 236, Rarity.RARE, mage.cards.b.BlazingSpecter.class));
        cards.add(new SetCardInfo("Blinding Light", 9, Rarity.UNCOMMON, mage.cards.b.BlindingLight.class));
        cards.add(new SetCardInfo("Blind Seer", 47, Rarity.RARE, mage.cards.b.BlindSeer.class));
        cards.add(new SetCardInfo("Bloodstone Cameo", 298, Rarity.UNCOMMON, mage.cards.b.BloodstoneCameo.class));
        cards.add(new SetCardInfo("Blurred Mongoose", 183, Rarity.RARE, mage.cards.b.BlurredMongoose.class));
        cards.add(new SetCardInfo("Bog Initiate", 95, Rarity.COMMON, mage.cards.b.BogInitiate.class));
        cards.add(new SetCardInfo("Breaking Wave", 48, Rarity.RARE, mage.cards.b.BreakingWave.class));
        cards.add(new SetCardInfo("Breath of Darigaaz", 138, Rarity.UNCOMMON, mage.cards.b.BreathOfDarigaaz.class));
        cards.add(new SetCardInfo("Callous Giant", 139, Rarity.RARE, mage.cards.c.CallousGiant.class));
        cards.add(new SetCardInfo("Canopy Surge", 184, Rarity.UNCOMMON, mage.cards.c.CanopySurge.class));
        cards.add(new SetCardInfo("Capashen Unicorn", 10, Rarity.COMMON, mage.cards.c.CapashenUnicorn.class));
        cards.add(new SetCardInfo("Captain Sisay", 237, Rarity.RARE, mage.cards.c.CaptainSisay.class));
        cards.add(new SetCardInfo("Cauldron Dance", 238, Rarity.UNCOMMON, mage.cards.c.CauldronDance.class));
        cards.add(new SetCardInfo("Chaotic Strike", 140, Rarity.UNCOMMON, mage.cards.c.ChaoticStrike.class));
        cards.add(new SetCardInfo("Charging Troll", 239, Rarity.UNCOMMON, mage.cards.c.ChargingTroll.class));
        cards.add(new SetCardInfo("Chromatic Sphere", 299, Rarity.UNCOMMON, mage.cards.c.ChromaticSphere.class));
        cards.add(new SetCardInfo("Cinder Shade", 240, Rarity.UNCOMMON, mage.cards.c.CinderShade.class));
        cards.add(new SetCardInfo("Coalition Victory", 241, Rarity.RARE, mage.cards.c.CoalitionVictory.class));
        cards.add(new SetCardInfo("Coastal Tower", 321, Rarity.UNCOMMON, mage.cards.c.CoastalTower.class));
        cards.add(new SetCardInfo("Collapsing Borders", 141, Rarity.RARE, mage.cards.c.CollapsingBorders.class));
        cards.add(new SetCardInfo("Collective Restraint", 49, Rarity.RARE, mage.cards.c.CollectiveRestraint.class));
        cards.add(new SetCardInfo("Cremate", 96, Rarity.UNCOMMON, mage.cards.c.Cremate.class));
        cards.add(new SetCardInfo("Crimson Acolyte", 11, Rarity.COMMON, mage.cards.c.CrimsonAcolyte.class));
        cards.add(new SetCardInfo("Crosis's Attendant", 300, Rarity.UNCOMMON, mage.cards.c.CrosissAttendant.class));
        cards.add(new SetCardInfo("Crosis, the Purger", 242, Rarity.RARE, mage.cards.c.CrosisThePurger.class));
        cards.add(new SetCardInfo("Crown of Flames", 142, Rarity.COMMON, mage.cards.c.CrownOfFlames.class));
        cards.add(new SetCardInfo("Crusading Knight", 12, Rarity.RARE, mage.cards.c.CrusadingKnight.class));
        cards.add(new SetCardInfo("Crypt Angel", 97, Rarity.RARE, mage.cards.c.CryptAngel.class));
        cards.add(new SetCardInfo("Cursed Flesh", 98, Rarity.COMMON, mage.cards.c.CursedFlesh.class));
        cards.add(new SetCardInfo("Darigaaz's Attendant", 301, Rarity.UNCOMMON, mage.cards.d.DarigaazsAttendant.class));
        cards.add(new SetCardInfo("Darigaaz, the Igniter", 243, Rarity.RARE, mage.cards.d.DarigaazTheIgniter.class));
        cards.add(new SetCardInfo("Defiling Tears", 99, Rarity.UNCOMMON, mage.cards.d.DefilingTears.class));
        cards.add(new SetCardInfo("Death or Glory", 13, Rarity.RARE, mage.cards.d.DeathOrGlory.class));
        cards.add(new SetCardInfo("Desperate Research", 100, Rarity.RARE, mage.cards.d.DesperateResearch.class));
        cards.add(new SetCardInfo("Devouring Strossus", 101, Rarity.RARE, mage.cards.d.DevouringStrossus.class));
        cards.add(new SetCardInfo("Dismantling Blow", 14, Rarity.COMMON, mage.cards.d.DismantlingBlow.class));
        cards.add(new SetCardInfo("Disrupt", 51, Rarity.UNCOMMON, mage.cards.d.Disrupt.class));
        cards.add(new SetCardInfo("Distorting Wake", 52, Rarity.RARE, mage.cards.d.DistortingWake.class));
        cards.add(new SetCardInfo("Divine Presence", 15, Rarity.RARE, mage.cards.d.DivinePresence.class));
        cards.add(new SetCardInfo("Do or Die", 102, Rarity.RARE, mage.cards.d.DoOrDie.class));
        cards.add(new SetCardInfo("Drake-Skull Cameo", 302, Rarity.UNCOMMON, mage.cards.d.DrakeSkullCameo.class));
        cards.add(new SetCardInfo("Dream Thrush", 53, Rarity.COMMON, mage.cards.d.DreamThrush.class));
        cards.add(new SetCardInfo("Dredge", 103, Rarity.UNCOMMON, mage.cards.d.Dredge.class));
        cards.add(new SetCardInfo("Dromar's Attendant", 303, Rarity.UNCOMMON, mage.cards.d.DromarsAttendant.class));
        cards.add(new SetCardInfo("Dromar, the Banisher", 244, Rarity.RARE, mage.cards.d.DromarTheBanisher.class));
        cards.add(new SetCardInfo("Dueling Grounds", 245, Rarity.RARE, mage.cards.d.DuelingGrounds.class));
        cards.add(new SetCardInfo("Duskwalker", 104, Rarity.COMMON, mage.cards.d.Duskwalker.class));
        cards.add(new SetCardInfo("Elfhame Palace", 322, Rarity.UNCOMMON, mage.cards.e.ElfhamePalace.class));
        cards.add(new SetCardInfo("Elfhame Sanctuary", 185, Rarity.UNCOMMON, mage.cards.e.ElfhameSanctuary.class));
        cards.add(new SetCardInfo("Elvish Champion", 186, Rarity.RARE, mage.cards.e.ElvishChampion.class));
        cards.add(new SetCardInfo("Empress Galina", 54, Rarity.RARE, mage.cards.e.EmpressGalina.class));
        cards.add(new SetCardInfo("Essence Leak", 55, Rarity.UNCOMMON, mage.cards.e.EssenceLeak.class));
        cards.add(new SetCardInfo("Exclude", 56, Rarity.COMMON, mage.cards.e.Exclude.class));
        cards.add(new SetCardInfo("Exotic Curse", 105, Rarity.COMMON, mage.cards.e.ExoticCurse.class));
        cards.add(new SetCardInfo("Explosive Growth", 187, Rarity.COMMON, mage.cards.e.ExplosiveGrowth.class));
        cards.add(new SetCardInfo("Fact or Fiction", 57, Rarity.UNCOMMON, mage.cards.f.FactOrFiction.class));
        cards.add(new SetCardInfo("Faerie Squadron", 58, Rarity.COMMON, mage.cards.f.FaerieSquadron.class));
        cards.add(new SetCardInfo("Fertile Ground", 188, Rarity.COMMON, mage.cards.f.FertileGround.class));
        cards.add(new SetCardInfo("Fight or Flight", 16, Rarity.RARE, mage.cards.f.FightOrFlight.class));
        cards.add(new SetCardInfo("Firebrand Ranger", 143, Rarity.UNCOMMON, mage.cards.f.FirebrandRanger.class));
        cards.add(new SetCardInfo("Firescreamer", 106, Rarity.COMMON, mage.cards.f.Firescreamer.class));
        cards.add(new SetCardInfo("Fires of Yavimaya", 246, Rarity.UNCOMMON, mage.cards.f.FiresOfYavimaya.class));
        cards.add(new SetCardInfo("Forest", 347, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 348, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 349, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 350, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Frenzied Tilling", 247, Rarity.COMMON, mage.cards.f.FrenziedTilling.class));
        cards.add(new SetCardInfo("Galina's Knight", 248, Rarity.COMMON, mage.cards.g.GalinasKnight.class));
        cards.add(new SetCardInfo("Geothermal Crevice", 323, Rarity.COMMON, mage.cards.g.GeothermalCrevice.class));
        cards.add(new SetCardInfo("Ghitu Fire", 144, Rarity.RARE, mage.cards.g.GhituFire.class));
        cards.add(new SetCardInfo("Glimmering Angel", 17, Rarity.COMMON, mage.cards.g.GlimmeringAngel.class));
        cards.add(new SetCardInfo("Global Ruin", 18, Rarity.RARE, mage.cards.g.GlobalRuin.class));
        cards.add(new SetCardInfo("Goblin Spy", 145, Rarity.UNCOMMON, mage.cards.g.GoblinSpy.class));
        cards.add(new SetCardInfo("Goham Djinn", 107, Rarity.UNCOMMON, mage.cards.g.GohamDjinn.class));
        cards.add(new SetCardInfo("Halam Djinn", 146, Rarity.UNCOMMON, mage.cards.h.HalamDjinn.class));
        cards.add(new SetCardInfo("Hanna, Ship's Navigator", 249, Rarity.RARE, mage.cards.h.HannaShipsNavigator.class));
        cards.add(new SetCardInfo("Harrow", 189, Rarity.COMMON, mage.cards.h.Harrow.class));
        cards.add(new SetCardInfo("Harsh Judgment", 19, Rarity.RARE, mage.cards.h.HarshJudgment.class));
        cards.add(new SetCardInfo("Hate Weaver", 108, Rarity.UNCOMMON, mage.cards.h.HateWeaver.class));
        cards.add(new SetCardInfo("Heroes' Reunion", 250, Rarity.UNCOMMON, mage.cards.h.HeroesReunion.class));
        cards.add(new SetCardInfo("Holy Day", 20, Rarity.COMMON, mage.cards.h.HolyDay.class));
        cards.add(new SetCardInfo("Hooded Kavu", 147, Rarity.COMMON, mage.cards.h.HoodedKavu.class));
        cards.add(new SetCardInfo("Horned Cheetah", 251, Rarity.UNCOMMON, mage.cards.h.HornedCheetah.class));
        cards.add(new SetCardInfo("Hunting Kavu", 252, Rarity.UNCOMMON, mage.cards.h.HuntingKavu.class));
        cards.add(new SetCardInfo("Hypnotic Cloud", 109, Rarity.COMMON, mage.cards.h.HypnoticCloud.class));
        cards.add(new SetCardInfo("Irrigation Ditch", 324, Rarity.COMMON, mage.cards.i.IrrigationDitch.class));
        cards.add(new SetCardInfo("Island", 335, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 336, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 337, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 338, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Jade Leech", 190, Rarity.RARE, mage.cards.j.JadeLeech.class));
        cards.add(new SetCardInfo("Juntu Stakes", 304, Rarity.RARE, mage.cards.j.JuntuStakes.class));
        cards.add(new SetCardInfo("Kangee, Aerie Keeper", 253, Rarity.RARE, mage.cards.k.KangeeAerieKeeper.class));
        cards.add(new SetCardInfo("Kavu Aggressor", 148, Rarity.COMMON, mage.cards.k.KavuAggressor.class));
        cards.add(new SetCardInfo("Kavu Chameleon", 191, Rarity.UNCOMMON, mage.cards.k.KavuChameleon.class));
        cards.add(new SetCardInfo("Kavu Climber", 192, Rarity.COMMON, mage.cards.k.KavuClimber.class));
        cards.add(new SetCardInfo("Kavu Lair", 193, Rarity.RARE, mage.cards.k.KavuLair.class));
        cards.add(new SetCardInfo("Kavu Monarch", 149, Rarity.RARE, mage.cards.k.KavuMonarch.class));
        cards.add(new SetCardInfo("Kavu Runner", 150, Rarity.UNCOMMON, mage.cards.k.KavuRunner.class));
        cards.add(new SetCardInfo("Kavu Scout", 151, Rarity.COMMON, mage.cards.k.KavuScout.class));
        cards.add(new SetCardInfo("Kavu Titan", 194, Rarity.RARE, mage.cards.k.KavuTitan.class));
        cards.add(new SetCardInfo("Keldon Necropolis", 325, Rarity.RARE, mage.cards.k.KeldonNecropolis.class));
        cards.add(new SetCardInfo("Liberate", 21, Rarity.UNCOMMON, mage.cards.l.Liberate.class));
        cards.add(new SetCardInfo("Lightning Dart", 152, Rarity.UNCOMMON, mage.cards.l.LightningDart.class));
        cards.add(new SetCardInfo("Llanowar Cavalry", 195, Rarity.COMMON, mage.cards.l.LlanowarCavalry.class));
        cards.add(new SetCardInfo("Llanowar Elite", 196, Rarity.COMMON, mage.cards.l.LlanowarElite.class));
        cards.add(new SetCardInfo("Llanowar Knight", 254, Rarity.COMMON, mage.cards.l.LlanowarKnight.class));
        cards.add(new SetCardInfo("Llanowar Vanguard", 197, Rarity.COMMON, mage.cards.l.LlanowarVanguard.class));
        cards.add(new SetCardInfo("Loafing Giant", 153, Rarity.RARE, mage.cards.l.LoafingGiant.class));
        cards.add(new SetCardInfo("Lobotomy", 255, Rarity.UNCOMMON, mage.cards.l.Lobotomy.class));
        cards.add(new SetCardInfo("Lotus Guardian", 305, Rarity.RARE, mage.cards.l.LotusGuardian.class));
        cards.add(new SetCardInfo("Mages' Contest", 154, Rarity.RARE, mage.cards.m.MagesContest.class));
        cards.add(new SetCardInfo("Mana Maze", 59, Rarity.RARE, mage.cards.m.ManaMaze.class));
        cards.add(new SetCardInfo("Maniacal Rage", 155, Rarity.COMMON, mage.cards.m.ManiacalRage.class));
        cards.add(new SetCardInfo("Manipulate Fate", 60, Rarity.UNCOMMON, mage.cards.m.ManipulateFate.class));
        cards.add(new SetCardInfo("Marauding Knight", 110, Rarity.RARE, mage.cards.m.MaraudingKnight.class));
        cards.add(new SetCardInfo("Metathran Aerostat", 61, Rarity.RARE, mage.cards.m.MetathranAerostat.class));
        cards.add(new SetCardInfo("Metathran Transport", 62, Rarity.UNCOMMON, mage.cards.m.MetathranTransport.class));
        cards.add(new SetCardInfo("Metathran Zombie", 63, Rarity.COMMON, mage.cards.m.MetathranZombie.class));
        cards.add(new SetCardInfo("Meteor Storm", 256, Rarity.RARE, mage.cards.m.MeteorStorm.class));
        cards.add(new SetCardInfo("Might Weaver", 198, Rarity.UNCOMMON, mage.cards.m.MightWeaver.class));
        cards.add(new SetCardInfo("Molimo, Maro-Sorcerer", 199, Rarity.RARE, mage.cards.m.MolimoMaroSorcerer.class));
        cards.add(new SetCardInfo("Mountain", 343, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 344, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 345, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 346, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mourning", 111, Rarity.COMMON, mage.cards.m.Mourning.class));
        cards.add(new SetCardInfo("Nightscape Apprentice", 112, Rarity.COMMON, mage.cards.n.NightscapeApprentice.class));
        cards.add(new SetCardInfo("Nightscape Master", 113, Rarity.RARE, mage.cards.n.NightscapeMaster.class));
        cards.add(new SetCardInfo("Noble Panther", 257, Rarity.RARE, mage.cards.n.NoblePanther.class));
        cards.add(new SetCardInfo("Nomadic Elf", 200, Rarity.COMMON, mage.cards.n.NomadicElf.class));
        cards.add(new SetCardInfo("Obliterate", 156, Rarity.RARE, mage.cards.o.Obliterate.class));
        cards.add(new SetCardInfo("Obsidian Acolyte", 22, Rarity.COMMON, mage.cards.o.ObsidianAcolyte.class));
        cards.add(new SetCardInfo("Opt", 64, Rarity.COMMON, mage.cards.o.Opt.class));
        cards.add(new SetCardInfo("Ordered Migration", 258, Rarity.UNCOMMON, mage.cards.o.OrderedMigration.class));
        cards.add(new SetCardInfo("Orim's Touch", 23, Rarity.COMMON, mage.cards.o.OrimsTouch.class));
        cards.add(new SetCardInfo("Overabundance", 259, Rarity.RARE, mage.cards.o.Overabundance.class));
        cards.add(new SetCardInfo("Overload", 157, Rarity.COMMON, mage.cards.o.Overload.class));
        cards.add(new SetCardInfo("Pain // Suffering", 294, Rarity.UNCOMMON, mage.cards.p.PainSuffering.class));
        cards.add(new SetCardInfo("Phantasmal Terrain", 65, Rarity.COMMON, mage.cards.p.PhantasmalTerrain.class));
        cards.add(new SetCardInfo("Phyrexian Altar", 306, Rarity.RARE, mage.cards.p.PhyrexianAltar.class));
        cards.add(new SetCardInfo("Phyrexian Battleflies", 114, Rarity.COMMON, mage.cards.p.PhyrexianBattleflies.class));
        cards.add(new SetCardInfo("Phyrexian Delver", 115, Rarity.RARE, mage.cards.p.PhyrexianDelver.class));
        cards.add(new SetCardInfo("Phyrexian Infiltrator", 116, Rarity.RARE, mage.cards.p.PhyrexianInfiltrator.class));
        cards.add(new SetCardInfo("Phyrexian Lens", 307, Rarity.RARE, mage.cards.p.PhyrexianLens.class));
        cards.add(new SetCardInfo("Phyrexian Reaper", 117, Rarity.COMMON, mage.cards.p.PhyrexianReaper.class));
        cards.add(new SetCardInfo("Phyrexian Slayer", 118, Rarity.COMMON, mage.cards.p.PhyrexianSlayer.class));
        cards.add(new SetCardInfo("Pincer Spider", 201, Rarity.COMMON, mage.cards.p.PincerSpider.class));
        cards.add(new SetCardInfo("Plague Spitter", 119, Rarity.UNCOMMON, mage.cards.p.PlagueSpitter.class));
        cards.add(new SetCardInfo("Plague Spores", 260, Rarity.COMMON, mage.cards.p.PlagueSpores.class));
        cards.add(new SetCardInfo("Plains", 331, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 332, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 333, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 334, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Planar Portal", 308, Rarity.RARE, mage.cards.p.PlanarPortal.class));
        cards.add(new SetCardInfo("Pledge of Loyalty", 24, Rarity.UNCOMMON, mage.cards.p.PledgeOfLoyalty.class));
        cards.add(new SetCardInfo("Pouncing Kavu", 158, Rarity.COMMON, mage.cards.p.PouncingKavu.class));
        cards.add(new SetCardInfo("Power Armor", 309, Rarity.UNCOMMON, mage.cards.p.PowerArmor.class));
        cards.add(new SetCardInfo("Prison Barricade", 25, Rarity.COMMON, mage.cards.p.PrisonBarricade.class));
        cards.add(new SetCardInfo("Probe", 66, Rarity.COMMON, mage.cards.p.Probe.class));
        cards.add(new SetCardInfo("Prohibit", 67, Rarity.COMMON, mage.cards.p.Prohibit.class));
        cards.add(new SetCardInfo("Protective Sphere", 26, Rarity.COMMON, mage.cards.p.ProtectiveSphere.class));
        cards.add(new SetCardInfo("Psychic Battle", 68, Rarity.RARE, mage.cards.p.PsychicBattle.class));
        cards.add(new SetCardInfo("Pulse of Llanowar", 202, Rarity.UNCOMMON, mage.cards.p.PulseOfLlanowar.class));
        cards.add(new SetCardInfo("Pure Reflection", 27, Rarity.RARE, mage.cards.p.PureReflection.class));
        cards.add(new SetCardInfo("Pyre Zombie", 261, Rarity.RARE, mage.cards.p.PyreZombie.class));
        cards.add(new SetCardInfo("Quirion Elves", 203, Rarity.COMMON, mage.cards.q.QuirionElves.class));
        cards.add(new SetCardInfo("Quirion Sentinel", 204, Rarity.COMMON, mage.cards.q.QuirionSentinel.class));
        cards.add(new SetCardInfo("Quirion Trailblazer", 205, Rarity.COMMON, mage.cards.q.QuirionTrailblazer.class));
        cards.add(new SetCardInfo("Rage Weaver", 159, Rarity.UNCOMMON, mage.cards.r.RageWeaver.class));
        cards.add(new SetCardInfo("Raging Kavu", 262, Rarity.RARE, mage.cards.r.RagingKavu.class));
        cards.add(new SetCardInfo("Rainbow Crow", 69, Rarity.UNCOMMON, mage.cards.r.RainbowCrow.class));
        cards.add(new SetCardInfo("Rampant Elephant", 28, Rarity.COMMON, mage.cards.r.RampantElephant.class));
        cards.add(new SetCardInfo("Ravenous Rats", 120, Rarity.COMMON, mage.cards.r.RavenousRats.class));
        cards.add(new SetCardInfo("Razorfoot Griffin", 29, Rarity.COMMON, mage.cards.r.RazorfootGriffin.class));
        cards.add(new SetCardInfo("Reckless Assault", 263, Rarity.RARE, mage.cards.r.RecklessAssault.class));
        cards.add(new SetCardInfo("Reckless Spite", 121, Rarity.UNCOMMON, mage.cards.r.RecklessSpite.class));
        cards.add(new SetCardInfo("Recoil", 264, Rarity.COMMON, mage.cards.r.Recoil.class));
        cards.add(new SetCardInfo("Recover", 122, Rarity.COMMON, mage.cards.r.Recover.class));
        cards.add(new SetCardInfo("Repulse", 70, Rarity.COMMON, mage.cards.r.Repulse.class));
        cards.add(new SetCardInfo("Restock", 206, Rarity.RARE, mage.cards.r.Restock.class));
        cards.add(new SetCardInfo("Restrain", 30, Rarity.COMMON, mage.cards.r.Restrain.class));
        cards.add(new SetCardInfo("Reviving Dose", 31, Rarity.COMMON, mage.cards.r.RevivingDose.class));
        cards.add(new SetCardInfo("Reviving Vapors", 265, Rarity.UNCOMMON, mage.cards.r.RevivingVapors.class));
        cards.add(new SetCardInfo("Rewards of Diversity", 32, Rarity.UNCOMMON, mage.cards.r.RewardsOfDiversity.class));
        cards.add(new SetCardInfo("Reya Dawnbringer", 33, Rarity.RARE, mage.cards.r.ReyaDawnbringer.class));
        cards.add(new SetCardInfo("Riptide Crab", 266, Rarity.UNCOMMON, mage.cards.r.RiptideCrab.class));
        cards.add(new SetCardInfo("Rith's Attendant", 310, Rarity.UNCOMMON, mage.cards.r.RithsAttendant.class));
        cards.add(new SetCardInfo("Rith, the Awakener", 267, Rarity.RARE, mage.cards.r.RithTheAwakener.class));
        cards.add(new SetCardInfo("Rogue Kavu", 160, Rarity.COMMON, mage.cards.r.RogueKavu.class));
        cards.add(new SetCardInfo("Rooting Kavu", 207, Rarity.UNCOMMON, mage.cards.r.RootingKavu.class));
        cards.add(new SetCardInfo("Rout", 34, Rarity.RARE, mage.cards.r.Rout.class));
        cards.add(new SetCardInfo("Ruby Leech", 161, Rarity.RARE, mage.cards.r.RubyLeech.class));
        cards.add(new SetCardInfo("Ruham Djinn", 35, Rarity.UNCOMMON, mage.cards.r.RuhamDjinn.class));
        cards.add(new SetCardInfo("Sabertooth Nishoba", 268, Rarity.RARE, mage.cards.s.SabertoothNishoba.class));
        cards.add(new SetCardInfo("Salt Marsh", 326, Rarity.UNCOMMON, mage.cards.s.SaltMarsh.class));
        cards.add(new SetCardInfo("Samite Archer", 269, Rarity.UNCOMMON, mage.cards.s.SamiteArcher.class));
        cards.add(new SetCardInfo("Samite Ministration", 36, Rarity.UNCOMMON, mage.cards.s.SamiteMinistration.class));
        cards.add(new SetCardInfo("Sapphire Leech", 71, Rarity.RARE, mage.cards.s.SapphireLeech.class));
        cards.add(new SetCardInfo("Saproling Infestation", 208, Rarity.RARE, mage.cards.s.SaprolingInfestation.class));
        cards.add(new SetCardInfo("Saproling Symbiosis", 209, Rarity.RARE, mage.cards.s.SaprolingSymbiosis.class));
        cards.add(new SetCardInfo("Savage Offensive", 162, Rarity.COMMON, mage.cards.s.SavageOffensive.class));
        cards.add(new SetCardInfo("Scarred Puma", 163, Rarity.COMMON, mage.cards.s.ScarredPuma.class));
        cards.add(new SetCardInfo("Scavenged Weaponry", 123, Rarity.COMMON, mage.cards.s.ScavengedWeaponry.class));
        cards.add(new SetCardInfo("Scorching Lava", 164, Rarity.COMMON, mage.cards.s.ScorchingLava.class));
        cards.add(new SetCardInfo("Scouting Trek", 210, Rarity.UNCOMMON, mage.cards.s.ScoutingTrek.class));
        cards.add(new SetCardInfo("Searing Rays", 165, Rarity.UNCOMMON, mage.cards.s.SearingRays.class));
        cards.add(new SetCardInfo("Seashell Cameo", 311, Rarity.UNCOMMON, mage.cards.s.SeashellCameo.class));
        cards.add(new SetCardInfo("Seer's Vision", 270, Rarity.UNCOMMON, mage.cards.s.SeersVision.class));
        cards.add(new SetCardInfo("Serpentine Kavu", 211, Rarity.COMMON, mage.cards.s.SerpentineKavu.class));
        cards.add(new SetCardInfo("Shackles", 37, Rarity.COMMON, mage.cards.s.Shackles.class));
        cards.add(new SetCardInfo("Shimmering Wings", 72, Rarity.COMMON, mage.cards.s.ShimmeringWings.class));
        cards.add(new SetCardInfo("Shivan Emissary", 166, Rarity.UNCOMMON, mage.cards.s.ShivanEmissary.class));
        cards.add(new SetCardInfo("Shivan Harvest", 167, Rarity.UNCOMMON, mage.cards.s.ShivanHarvest.class));
        cards.add(new SetCardInfo("Shivan Oasis", 327, Rarity.UNCOMMON, mage.cards.s.ShivanOasis.class));
        cards.add(new SetCardInfo("Shivan Zombie", 271, Rarity.COMMON, mage.cards.s.ShivanZombie.class));
        cards.add(new SetCardInfo("Shoreline Raider", 73, Rarity.COMMON, mage.cards.s.ShorelineRaider.class));
        cards.add(new SetCardInfo("Simoon", 272, Rarity.UNCOMMON, mage.cards.s.Simoon.class));
        cards.add(new SetCardInfo("Skittish Kavu", 168, Rarity.UNCOMMON, mage.cards.s.SkittishKavu.class));
        cards.add(new SetCardInfo("Skizzik", 169, Rarity.RARE, mage.cards.s.Skizzik.class));
        cards.add(new SetCardInfo("Sky Weaver", 74, Rarity.UNCOMMON, mage.cards.s.SkyWeaver.class));
        cards.add(new SetCardInfo("Sleeper's Robe", 273, Rarity.UNCOMMON, mage.cards.s.SleepersRobe.class));
        cards.add(new SetCardInfo("Slimy Kavu", 170, Rarity.COMMON, mage.cards.s.SlimyKavu.class));
        cards.add(new SetCardInfo("Slinking Serpent", 274, Rarity.UNCOMMON, mage.cards.s.SlinkingSerpent.class));
        cards.add(new SetCardInfo("Smoldering Tar", 275, Rarity.UNCOMMON, mage.cards.s.SmolderingTar.class));
        cards.add(new SetCardInfo("Soul Burn", 124, Rarity.COMMON, mage.cards.s.SoulBurn.class));
        cards.add(new SetCardInfo("Sparring Golem", 312, Rarity.UNCOMMON, mage.cards.s.SparringGolem.class));
        cards.add(new SetCardInfo("Spinal Embrace", 276, Rarity.RARE, mage.cards.s.SpinalEmbrace.class));
        cards.add(new SetCardInfo("Spirit of Resistance", 38, Rarity.RARE, mage.cards.s.SpiritOfResistance.class));
        cards.add(new SetCardInfo("Spirit Weaver", 39, Rarity.UNCOMMON, mage.cards.s.SpiritWeaver.class));
        cards.add(new SetCardInfo("Spite // Malice", 293, Rarity.UNCOMMON, mage.cards.s.SpiteMalice.class));
        cards.add(new SetCardInfo("Spreading Plague", 125, Rarity.RARE, mage.cards.s.SpreadingPlague.class));
        cards.add(new SetCardInfo("Stalking Assassin", 277, Rarity.RARE, mage.cards.s.StalkingAssassin.class));
        cards.add(new SetCardInfo("Stand // Deliver", 292, Rarity.UNCOMMON, mage.cards.s.StandDeliver.class));
        cards.add(new SetCardInfo("Stand or Fall", 171, Rarity.RARE, mage.cards.s.StandOrFall.class));
        cards.add(new SetCardInfo("Sterling Grove", 278, Rarity.UNCOMMON, mage.cards.s.SterlingGrove.class));
        cards.add(new SetCardInfo("Stormscape Apprentice", 75, Rarity.COMMON, mage.cards.s.StormscapeApprentice.class));
        cards.add(new SetCardInfo("Stormscape Master", 76, Rarity.RARE, mage.cards.s.StormscapeMaster.class));
        cards.add(new SetCardInfo("Strength of Unity", 40, Rarity.COMMON, mage.cards.s.StrengthOfUnity.class));
        cards.add(new SetCardInfo("Stun", 172, Rarity.COMMON, mage.cards.s.Stun.class));
        cards.add(new SetCardInfo("Sulam Djinn", 212, Rarity.UNCOMMON, mage.cards.s.SulamDjinn.class));
        cards.add(new SetCardInfo("Sulfur Vent", 328, Rarity.COMMON, mage.cards.s.SulfurVent.class));
        cards.add(new SetCardInfo("Sunscape Apprentice", 41, Rarity.COMMON, mage.cards.s.SunscapeApprentice.class));
        cards.add(new SetCardInfo("Sunscape Master", 42, Rarity.RARE, mage.cards.s.SunscapeMaster.class));
        cards.add(new SetCardInfo("Swamp", 339, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 340, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 341, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 342, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sway of Illusion", 77, Rarity.UNCOMMON, mage.cards.s.SwayOfIllusion.class));
        cards.add(new SetCardInfo("Tainted Well", 126, Rarity.COMMON, mage.cards.t.TaintedWell.class));
        cards.add(new SetCardInfo("Tangle", 213, Rarity.UNCOMMON, mage.cards.t.Tangle.class));
        cards.add(new SetCardInfo("Tectonic Instability", 173, Rarity.RARE, mage.cards.t.TectonicInstability.class));
        cards.add(new SetCardInfo("Teferi's Care", 43, Rarity.UNCOMMON, mage.cards.t.TeferisCare.class));
        cards.add(new SetCardInfo("Teferi's Moat", 279, Rarity.RARE, mage.cards.t.TeferisMoat.class));
        cards.add(new SetCardInfo("Teferi's Response", 78, Rarity.RARE, mage.cards.t.TeferisResponse.class));
        cards.add(new SetCardInfo("Tek", 313, Rarity.RARE, mage.cards.t.Tek.class));
        cards.add(new SetCardInfo("Temporal Distortion", 79, Rarity.RARE, mage.cards.t.TemporalDistortion.class));
        cards.add(new SetCardInfo("Thicket Elemental", 214, Rarity.RARE, mage.cards.t.ThicketElemental.class));
        cards.add(new SetCardInfo("Thornscape Apprentice", 215, Rarity.COMMON, mage.cards.t.ThornscapeApprentice.class));
        cards.add(new SetCardInfo("Thornscape Master", 216, Rarity.RARE, mage.cards.t.ThornscapeMaster.class));
        cards.add(new SetCardInfo("Thunderscape Apprentice", 174, Rarity.COMMON, mage.cards.t.ThunderscapeApprentice.class));
        cards.add(new SetCardInfo("Thunderscape Master", 175, Rarity.RARE, mage.cards.t.ThunderscapeMaster.class));
        cards.add(new SetCardInfo("Tidal Visionary", 80, Rarity.COMMON, mage.cards.t.TidalVisionary.class));
        cards.add(new SetCardInfo("Tigereye Cameo", 314, Rarity.UNCOMMON, mage.cards.t.TigereyeCameo.class));
        cards.add(new SetCardInfo("Tinder Farm", 329, Rarity.COMMON, mage.cards.t.TinderFarm.class));
        cards.add(new SetCardInfo("Tolarian Emissary", 81, Rarity.UNCOMMON, mage.cards.t.TolarianEmissary.class));
        cards.add(new SetCardInfo("Tower Drake", 82, Rarity.COMMON, mage.cards.t.TowerDrake.class));
        cards.add(new SetCardInfo("Tranquility", 217, Rarity.COMMON, mage.cards.t.Tranquility.class));
        cards.add(new SetCardInfo("Traveler's Cloak", 83, Rarity.COMMON, mage.cards.t.TravelersCloak.class));
        cards.add(new SetCardInfo("Treefolk Healer", 218, Rarity.UNCOMMON, mage.cards.t.TreefolkHealer.class));
        cards.add(new SetCardInfo("Trench Wurm", 127, Rarity.UNCOMMON, mage.cards.t.TrenchWurm.class));
        cards.add(new SetCardInfo("Treva's Attendant", 315, Rarity.UNCOMMON, mage.cards.t.TrevasAttendant.class));
        cards.add(new SetCardInfo("Treva, the Renewer", 280, Rarity.RARE, mage.cards.t.TrevaTheRenewer.class));
        cards.add(new SetCardInfo("Tribal Flames", 176, Rarity.COMMON, mage.cards.t.TribalFlames.class));
        cards.add(new SetCardInfo("Troll-Horn Cameo", 316, Rarity.UNCOMMON, mage.cards.t.TrollHornCameo.class));
        cards.add(new SetCardInfo("Tsabo's Assassin", 128, Rarity.RARE, mage.cards.t.TsabosAssassin.class));
        cards.add(new SetCardInfo("Tsabo's Decree", 129, Rarity.RARE, mage.cards.t.TsabosDecree.class));
        cards.add(new SetCardInfo("Tsabo's Web", 317, Rarity.RARE, mage.cards.t.TsabosWeb.class));
        cards.add(new SetCardInfo("Tsabo Tavoc", 281, Rarity.RARE, mage.cards.t.TsaboTavoc.class));
        cards.add(new SetCardInfo("Turf Wound", 177, Rarity.COMMON, mage.cards.t.TurfWound.class));
        cards.add(new SetCardInfo("Twilight's Call", 130, Rarity.RARE, mage.cards.t.TwilightsCall.class));
        cards.add(new SetCardInfo("Undermine", 282, Rarity.RARE, mage.cards.u.Undermine.class));
        cards.add(new SetCardInfo("Urborg Drake", 283, Rarity.UNCOMMON, mage.cards.u.UrborgDrake.class));
        cards.add(new SetCardInfo("Urborg Emissary", 131, Rarity.UNCOMMON, mage.cards.u.UrborgEmissary.class));
        cards.add(new SetCardInfo("Urborg Phantom", 132, Rarity.COMMON, mage.cards.u.UrborgPhantom.class));
        cards.add(new SetCardInfo("Urborg Shambler", 133, Rarity.UNCOMMON, mage.cards.u.UrborgShambler.class));
        cards.add(new SetCardInfo("Urborg Skeleton", 134, Rarity.COMMON, mage.cards.u.UrborgSkeleton.class));
        cards.add(new SetCardInfo("Urborg Volcano", 330, Rarity.UNCOMMON, mage.cards.u.UrborgVolcano.class));
        cards.add(new SetCardInfo("Urza's Filter", 318, Rarity.RARE, mage.cards.u.UrzasFilter.class));
        cards.add(new SetCardInfo("Urza's Rage", 178, Rarity.RARE, mage.cards.u.UrzasRage.class));
        cards.add(new SetCardInfo("Utopia Tree", 219, Rarity.RARE, mage.cards.u.UtopiaTree.class));
        cards.add(new SetCardInfo("Verdeloth the Ancient", 220, Rarity.RARE, mage.cards.v.VerdelothTheAncient.class));
        cards.add(new SetCardInfo("Verduran Emissary", 221, Rarity.UNCOMMON, mage.cards.v.VerduranEmissary.class));
        cards.add(new SetCardInfo("Viashino Grappler", 179, Rarity.COMMON, mage.cards.v.ViashinoGrappler.class));
        cards.add(new SetCardInfo("Vicious Kavu", 284, Rarity.UNCOMMON, mage.cards.v.ViciousKavu.class));
        cards.add(new SetCardInfo("Vigorous Charge", 222, Rarity.COMMON, mage.cards.v.VigorousCharge.class));
        cards.add(new SetCardInfo("Vile Consumption", 285, Rarity.RARE, mage.cards.v.VileConsumption.class));
        cards.add(new SetCardInfo("Vodalian Hypnotist", 84, Rarity.UNCOMMON, mage.cards.v.VodalianHypnotist.class));
        cards.add(new SetCardInfo("Vodalian Merchant", 85, Rarity.COMMON, mage.cards.v.VodalianMerchant.class));
        cards.add(new SetCardInfo("Vodalian Serpent", 86, Rarity.COMMON, mage.cards.v.VodalianSerpent.class));
        cards.add(new SetCardInfo("Vodalian Zombie", 286, Rarity.COMMON, mage.cards.v.VodalianZombie.class));
        cards.add(new SetCardInfo("Void", 287, Rarity.RARE, mage.cards.v.Void.class));
        cards.add(new SetCardInfo("Voracious Cobra", 288, Rarity.UNCOMMON, mage.cards.v.VoraciousCobra.class));
        cards.add(new SetCardInfo("Wallop", 223, Rarity.UNCOMMON, mage.cards.w.Wallop.class));
        cards.add(new SetCardInfo("Wandering Stream", 224, Rarity.COMMON, mage.cards.w.WanderingStream.class));
        cards.add(new SetCardInfo("Wash Out", 87, Rarity.UNCOMMON, mage.cards.w.WashOut.class));
        cards.add(new SetCardInfo("Wax // Wane", 296, Rarity.UNCOMMON, mage.cards.w.WaxWane.class));
        cards.add(new SetCardInfo("Wayfaring Giant", 44, Rarity.UNCOMMON, mage.cards.w.WayfaringGiant.class));
        cards.add(new SetCardInfo("Well-Laid Plans", 88, Rarity.RARE, mage.cards.w.WellLaidPlans.class));
        cards.add(new SetCardInfo("Whip Silk", 225, Rarity.COMMON, mage.cards.w.WhipSilk.class));
        cards.add(new SetCardInfo("Wings of Hope", 289, Rarity.COMMON, mage.cards.w.WingsOfHope.class));
        cards.add(new SetCardInfo("Winnow", 45, Rarity.RARE, mage.cards.w.Winnow.class));
        cards.add(new SetCardInfo("Worldly Counsel", 89, Rarity.COMMON, mage.cards.w.WorldlyCounsel.class));
        cards.add(new SetCardInfo("Yavimaya Barbarian", 290, Rarity.COMMON, mage.cards.y.YavimayaBarbarian.class));
        cards.add(new SetCardInfo("Yavimaya Kavu", 291, Rarity.UNCOMMON, mage.cards.y.YavimayaKavu.class));
        cards.add(new SetCardInfo("Yawgmoth's Agenda", 135, Rarity.RARE, mage.cards.y.YawgmothsAgenda.class));
        cards.add(new SetCardInfo("Zanam Djinn", 90, Rarity.UNCOMMON, mage.cards.z.ZanamDjinn.class));
        cards.add(new SetCardInfo("Zap", 180, Rarity.COMMON, mage.cards.z.Zap.class));
    }

    @Override
    public BoosterCollator createCollator() {
        return new InvasionCollator();
    }
}

// Booster collation info from https://www.lethe.xyz/mtg/collation/inv.html
// Using US collation - commons only
class InvasionCollator implements BoosterCollator {

    private final CardRun commonA = new CardRun(true, "124", "37", "174", "56", "203", "118", "8", "176", "82", "215", "98", "28", "164", "75", "211", "124", "29", "158", "66", "189", "105", "28", "174", "73", "203", "92", "29", "180", "66", "211", "104", "40", "136", "56", "192", "118", "17", "164", "82", "201", "92", "40", "180", "58", "192", "104", "17", "176", "73", "201", "105", "37", "136", "58", "189", "98", "8", "158", "75", "215");
    private final CardRun commonB = new CardRun(true, "134", "26", "323", "64", "195", "114", "14", "179", "86", "319", "122", "41", "151", "83", "204", "328", "25", "160", "53", "181", "134", "10", "177", "89", "200", "123", "324", "179", "64", "204", "106", "26", "170", "329", "195", "123", "41", "323", "86", "196", "328", "14", "151", "89", "181", "106", "10", "160", "329", "200", "122", "324", "177", "83", "196", "114", "25", "170", "53", "319");
    private final CardRun commonC = new CardRun(true, "109", "20", "157", "72", "225", "132", "30", "172", "85", "197", "126", "31", "142", "63", "225", "109", "3", "162", "65", "224", "111", "23", "163", "46", "222", "132", "3", "172", "72", "217", "126", "23", "142", "46", "197", "95", "20", "162", "63", "222", "111", "31", "157", "65", "217", "95", "30", "163", "85", "224");
    private final CardRun commonD = new CardRun(true, "112", "248", "147", "247", "205", "286", "7", "155", "290", "187", "264", "22", "271", "67", "254", "120", "260", "148", "80", "289", "117", "11", "229", "70", "286", "120", "248", "148", "289", "187", "112", "11", "260", "80", "188", "290", "22", "247", "70", "205", "264", "7", "147", "254", "188", "117", "271", "155", "67", "229");
    private final CardRun uncommon = new CardRun(false, "91", "297", "228", "2", "94", "320", "295", "233", "234", "5", "6", "9", "298", "138", "184", "238", "140", "239", "299", "240", "321", "96", "300", "301", "99", "51", "302", "103", "303", "322", "185", "55", "57", "143", "246", "145", "107", "146", "108", "250", "251", "252", "191", "150", "21", "152", "255", "60", "62", "198", "258", "294", "119", "24", "309", "202", "159", "69", "121", "265", "32", "266", "310", "207", "35", "326", "269", "36", "210", "165", "311", "270", "166", "167", "327", "272", "168", "74", "273", "274", "275", "312", "39", "293", "292", "278", "212", "77", "213", "43", "314", "81", "218", "127", "315", "316", "283", "131", "133", "330", "221", "284", "84", "288", "223", "87", "296", "44", "291", "90");
    private final CardRun rare = new CardRun(false, "226", "227", "1", "93", "230", "231", "4", "232", "235", "137", "182", "236", "47", "183", "48", "139", "237", "241", "141", "49", "242", "12", "97", "50", "243", "13", "100", "101", "52", "15", "102", "244", "245", "186", "54", "16", "144", "18", "249", "19", "190", "304", "253", "193", "149", "194", "325", "153", "305", "154", "59", "110", "61", "256", "199", "113", "257", "156", "259", "306", "115", "116", "307", "308", "68", "27", "261", "262", "263", "206", "33", "267", "34", "161", "268", "71", "208", "209", "169", "276", "38", "125", "277", "171", "76", "42", "173", "279", "78", "313", "79", "214", "216", "175", "280", "128", "129", "317", "281", "130", "282", "318", "178", "219", "220", "285", "287", "88", "45", "135");

    // either A then B, or B then A
    private final BoosterStructure AAABB = new BoosterStructure(
            commonA, commonA, commonA,
            commonB, commonB
    );
    private final BoosterStructure AABBB = new BoosterStructure(
            commonA, commonA,
            commonB, commonB, commonB
    );
    private final BoosterStructure AAAAB = new BoosterStructure(
            commonA, commonA, commonA, commonA,
            commonB
    );
    private final BoosterStructure ABBBB = new BoosterStructure(
            commonA,
            commonB, commonB, commonB, commonB
    );
    private final BoosterStructure BBBAA = new BoosterStructure(
            commonB, commonB, commonB,
            commonA, commonA
    );
    private final BoosterStructure BBAAA = new BoosterStructure(
            commonB, commonB,
            commonA, commonA, commonA
    );
    private final BoosterStructure BBBBA = new BoosterStructure(
            commonB, commonB, commonB, commonB,
            commonA
    );
    private final BoosterStructure BAAAA = new BoosterStructure(
            commonB,
            commonA, commonA, commonA, commonA
    );

    // either C then D, or D then C
    private final BoosterStructure CCCDDD = new BoosterStructure(
            commonC, commonC, commonC,
            commonD, commonD, commonD
    );
    private final BoosterStructure CCCCDD = new BoosterStructure(
            commonC, commonC, commonC, commonC,
            commonD, commonD
    );
    private final BoosterStructure CCDDDD = new BoosterStructure(
            commonC, commonC,
            commonD, commonD, commonD, commonD
    );
    private final BoosterStructure DDDCCC = new BoosterStructure(
            commonD, commonD, commonD,
            commonC, commonC, commonC
    );
    private final BoosterStructure DDDDCC = new BoosterStructure(
            commonD, commonD, commonD, commonD,
            commonC, commonC
    );
    private final BoosterStructure DDCCCC = new BoosterStructure(
            commonD, commonD,
            commonC, commonC, commonC, commonC
    );

    private final BoosterStructure U3 = new BoosterStructure(uncommon, uncommon, uncommon);
    private final BoosterStructure R1 = new BoosterStructure(rare);

    // no definitive ratio here, just "most" packs 3-2
    private final RarityConfiguration commonRunsAB = new RarityConfiguration(
        AAAAB,
        AAABB, AAABB, AAABB, AAABB, AAABB, AAABB, AAABB, AAABB, AAABB,
        AABBB, AABBB, AABBB, AABBB, AABBB, AABBB, AABBB, AABBB, AABBB,
        ABBBB,
        BBBBA,
        BBBAA, BBBAA, BBBAA, BBBAA, BBBAA, BBBAA, BBBAA, BBBAA, BBBAA,
        BBAAA, BBAAA, BBAAA, BBAAA, BBAAA, BBAAA, BBAAA, BBAAA, BBAAA,
        BAAAA
    );

    // some evidence for 1/6 packs 4-2
    private final RarityConfiguration commonRunsCD = new RarityConfiguration(
        CCCCDD,
        CCCDDD, CCCDDD, CCCDDD, CCCDDD, CCCDDD,
        CCCDDD, CCCDDD, CCCDDD, CCCDDD, CCCDDD,
        CCDDDD,
        DDDDCC,
        DDDCCC, DDDCCC, DDDCCC, DDDCCC, DDDCCC,
        DDDCCC, DDDCCC, DDDCCC, DDDCCC, DDDCCC,
        DDCCCC
    );

    private final RarityConfiguration uncommonRuns = new RarityConfiguration(U3);
    private final RarityConfiguration rareRuns = new RarityConfiguration(R1);

    @Override
    public List<String> makeBooster() {
        List<String> booster = new ArrayList<>();
        // either A/B or C/D can be first in the pack
        if (RandomUtil.nextBoolean()) {
            booster.addAll(commonRunsAB.getNext().makeRun());
            booster.addAll(commonRunsCD.getNext().makeRun());
        } else {
            booster.addAll(commonRunsCD.getNext().makeRun());
            booster.addAll(commonRunsAB.getNext().makeRun());
        }
        booster.addAll(uncommonRuns.getNext().makeRun());
        booster.addAll(rareRuns.getNext().makeRun());
        return booster;
    }
}
