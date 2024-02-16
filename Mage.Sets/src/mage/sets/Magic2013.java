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
 *
 * @author North
 */
public final class Magic2013 extends ExpansionSet {

    private static final Magic2013 instance = new Magic2013();

    public static Magic2013 getInstance() {
        return instance;
    }

    private Magic2013() {
        super("Magic 2013", "M13", ExpansionSet.buildDate(2012, 7, 13), SetType.CORE);
        this.hasBoosters = true;
        this.numBoosterLands = 1;
        this.numBoosterCommon = 10;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 8;
        cards.add(new SetCardInfo("Acidic Slime", 159, Rarity.UNCOMMON, mage.cards.a.AcidicSlime.class));
        cards.add(new SetCardInfo("Ajani, Caller of the Pride", 1, Rarity.MYTHIC, mage.cards.a.AjaniCallerOfThePride.class));
        cards.add(new SetCardInfo("Ajani's Sunstriker", 2, Rarity.COMMON, mage.cards.a.AjanisSunstriker.class));
        cards.add(new SetCardInfo("Akroma's Memorial", 200, Rarity.MYTHIC, mage.cards.a.AkromasMemorial.class));
        cards.add(new SetCardInfo("Angelic Benediction", 4, Rarity.UNCOMMON, mage.cards.a.AngelicBenediction.class));
        cards.add(new SetCardInfo("Angel's Mercy", 3, Rarity.COMMON, mage.cards.a.AngelsMercy.class));
        cards.add(new SetCardInfo("Arbor Elf", 160, Rarity.COMMON, mage.cards.a.ArborElf.class));
        cards.add(new SetCardInfo("Archaeomancer", 41, Rarity.COMMON, mage.cards.a.Archaeomancer.class));
        cards.add(new SetCardInfo("Arctic Aven", 42, Rarity.UNCOMMON, mage.cards.a.ArcticAven.class));
        cards.add(new SetCardInfo("Arms Dealer", 120, Rarity.UNCOMMON, mage.cards.a.ArmsDealer.class));
        cards.add(new SetCardInfo("Attended Knight", 5, Rarity.COMMON, mage.cards.a.AttendedKnight.class));
        cards.add(new SetCardInfo("Augur of Bolas", 43, Rarity.UNCOMMON, mage.cards.a.AugurOfBolas.class));
        cards.add(new SetCardInfo("Aven Squire", 6, Rarity.COMMON, mage.cards.a.AvenSquire.class));
        cards.add(new SetCardInfo("Battleflight Eagle", 7, Rarity.COMMON, mage.cards.b.BattleflightEagle.class));
        cards.add(new SetCardInfo("Battle of Wits", 44, Rarity.RARE, mage.cards.b.BattleOfWits.class));
        cards.add(new SetCardInfo("Bladetusk Boar", 121, Rarity.COMMON, mage.cards.b.BladetuskBoar.class));
        cards.add(new SetCardInfo("Bloodhunter Bat", 82, Rarity.COMMON, mage.cards.b.BloodhunterBat.class));
        cards.add(new SetCardInfo("Blood Reckoning", 81, Rarity.UNCOMMON, mage.cards.b.BloodReckoning.class));
        cards.add(new SetCardInfo("Bloodthrone Vampire", 83, Rarity.COMMON, mage.cards.b.BloodthroneVampire.class));
        cards.add(new SetCardInfo("Bond Beetle", 161, Rarity.COMMON, mage.cards.b.BondBeetle.class));
        cards.add(new SetCardInfo("Boundless Realms", 162, Rarity.RARE, mage.cards.b.BoundlessRealms.class));
        cards.add(new SetCardInfo("Bountiful Harvest", 163, Rarity.COMMON, mage.cards.b.BountifulHarvest.class));
        cards.add(new SetCardInfo("Canyon Minotaur", 122, Rarity.COMMON, mage.cards.c.CanyonMinotaur.class));
        cards.add(new SetCardInfo("Captain of the Watch", 8, Rarity.RARE, mage.cards.c.CaptainOfTheWatch.class));
        cards.add(new SetCardInfo("Captain's Call", 9, Rarity.COMMON, mage.cards.c.CaptainsCall.class));
        cards.add(new SetCardInfo("Cathedral of War", 221, Rarity.RARE, mage.cards.c.CathedralOfWar.class));
        cards.add(new SetCardInfo("Centaur Courser", 164, Rarity.COMMON, mage.cards.c.CentaurCourser.class));
        cards.add(new SetCardInfo("Chandra's Fury", 124, Rarity.COMMON, mage.cards.c.ChandrasFury.class));
        cards.add(new SetCardInfo("Chandra, the Firebrand", 123, Rarity.MYTHIC, mage.cards.c.ChandraTheFirebrand.class));
        cards.add(new SetCardInfo("Chronomaton", 201, Rarity.UNCOMMON, mage.cards.c.Chronomaton.class));
        cards.add(new SetCardInfo("Cleaver Riot", 125, Rarity.UNCOMMON, mage.cards.c.CleaverRiot.class));
        cards.add(new SetCardInfo("Clock of Omens", 202, Rarity.UNCOMMON, mage.cards.c.ClockOfOmens.class));
        cards.add(new SetCardInfo("Clone", 45, Rarity.RARE, mage.cards.c.Clone.class));
        cards.add(new SetCardInfo("Courtly Provocateur", 46, Rarity.UNCOMMON, mage.cards.c.CourtlyProvocateur.class));
        cards.add(new SetCardInfo("Cower in Fear", 84, Rarity.UNCOMMON, mage.cards.c.CowerInFear.class));
        cards.add(new SetCardInfo("Craterize", 126, Rarity.COMMON, mage.cards.c.Craterize.class));
        cards.add(new SetCardInfo("Crimson Muckwader", 127, Rarity.UNCOMMON, mage.cards.c.CrimsonMuckwader.class));
        cards.add(new SetCardInfo("Crippling Blight", 85, Rarity.COMMON, mage.cards.c.CripplingBlight.class));
        cards.add(new SetCardInfo("Crusader of Odric", 10, Rarity.UNCOMMON, mage.cards.c.CrusaderOfOdric.class));
        cards.add(new SetCardInfo("Dark Favor", 86, Rarity.COMMON, mage.cards.d.DarkFavor.class));
        cards.add(new SetCardInfo("Deadly Recluse", 165, Rarity.COMMON, mage.cards.d.DeadlyRecluse.class));
        cards.add(new SetCardInfo("Diabolic Revelation", 87, Rarity.RARE, mage.cards.d.DiabolicRevelation.class));
        cards.add(new SetCardInfo("Disciple of Bolas", 88, Rarity.RARE, mage.cards.d.DiscipleOfBolas.class));
        cards.add(new SetCardInfo("Disentomb", 89, Rarity.COMMON, mage.cards.d.Disentomb.class));
        cards.add(new SetCardInfo("Divination", 47, Rarity.COMMON, mage.cards.d.Divination.class));
        cards.add(new SetCardInfo("Divine Favor", 11, Rarity.COMMON, mage.cards.d.DivineFavor.class));
        cards.add(new SetCardInfo("Divine Verdict", 12, Rarity.COMMON, mage.cards.d.DivineVerdict.class));
        cards.add(new SetCardInfo("Door to Nothingness", 203, Rarity.RARE, mage.cards.d.DoorToNothingness.class));
        cards.add(new SetCardInfo("Downpour", 48, Rarity.COMMON, mage.cards.d.Downpour.class));
        cards.add(new SetCardInfo("Dragon Hatchling", 128, Rarity.COMMON, mage.cards.d.DragonHatchling.class));
        cards.add(new SetCardInfo("Dragonskull Summit", 222, Rarity.RARE, mage.cards.d.DragonskullSummit.class));
        cards.add(new SetCardInfo("Drowned Catacomb", 223, Rarity.RARE, mage.cards.d.DrownedCatacomb.class));
        cards.add(new SetCardInfo("Duress", 90, Rarity.COMMON, mage.cards.d.Duress.class));
        cards.add(new SetCardInfo("Duskdale Wurm", 166, Rarity.UNCOMMON, mage.cards.d.DuskdaleWurm.class));
        cards.add(new SetCardInfo("Duskmantle Prowler", 91, Rarity.UNCOMMON, mage.cards.d.DuskmantleProwler.class));
        cards.add(new SetCardInfo("Duty-Bound Dead", 92, Rarity.COMMON, mage.cards.d.DutyBoundDead.class));
        cards.add(new SetCardInfo("Elderscale Wurm", 167, Rarity.MYTHIC, mage.cards.e.ElderscaleWurm.class));
        cards.add(new SetCardInfo("Elixir of Immortality", 204, Rarity.UNCOMMON, mage.cards.e.ElixirOfImmortality.class));
        cards.add(new SetCardInfo("Elvish Archdruid", 168, Rarity.RARE, mage.cards.e.ElvishArchdruid.class));
        cards.add(new SetCardInfo("Elvish Visionary", 169, Rarity.COMMON, mage.cards.e.ElvishVisionary.class));
        cards.add(new SetCardInfo("Encrust", 49, Rarity.COMMON, mage.cards.e.Encrust.class));
        cards.add(new SetCardInfo("Erase", 13, Rarity.COMMON, mage.cards.e.Erase.class));
        cards.add(new SetCardInfo("Essence Drain", 93, Rarity.COMMON, mage.cards.e.EssenceDrain.class));
        cards.add(new SetCardInfo("Essence Scatter", 50, Rarity.COMMON, mage.cards.e.EssenceScatter.class));
        cards.add(new SetCardInfo("Evolving Wilds", 224, Rarity.COMMON, mage.cards.e.EvolvingWilds.class));
        cards.add(new SetCardInfo("Faerie Invaders", 51, Rarity.COMMON, mage.cards.f.FaerieInvaders.class));
        cards.add(new SetCardInfo("Faith's Reward", 14, Rarity.RARE, mage.cards.f.FaithsReward.class));
        cards.add(new SetCardInfo("Farseek", 170, Rarity.COMMON, mage.cards.f.Farseek.class));
        cards.add(new SetCardInfo("Fervor", 129, Rarity.RARE, mage.cards.f.Fervor.class));
        cards.add(new SetCardInfo("Fire Elemental", 130, Rarity.COMMON, mage.cards.f.FireElemental.class));
        cards.add(new SetCardInfo("Firewing Phoenix", 131, Rarity.RARE, mage.cards.f.FirewingPhoenix.class));
        cards.add(new SetCardInfo("Flames of the Firebrand", 132, Rarity.UNCOMMON, mage.cards.f.FlamesOfTheFirebrand.class));
        cards.add(new SetCardInfo("Flinthoof Boar", 171, Rarity.UNCOMMON, mage.cards.f.FlinthoofBoar.class));
        cards.add(new SetCardInfo("Fog", 172, Rarity.COMMON, mage.cards.f.Fog.class));
        cards.add(new SetCardInfo("Fog Bank", 52, Rarity.UNCOMMON, mage.cards.f.FogBank.class));
        cards.add(new SetCardInfo("Forest", 246, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 247, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 248, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 249, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Fungal Sprouting", 173, Rarity.UNCOMMON, mage.cards.f.FungalSprouting.class));
        cards.add(new SetCardInfo("Furnace Whelp", 133, Rarity.UNCOMMON, mage.cards.f.FurnaceWhelp.class));
        cards.add(new SetCardInfo("Garruk, Primal Hunter", 174, Rarity.MYTHIC, mage.cards.g.GarrukPrimalHunter.class));
        cards.add(new SetCardInfo("Garruk's Packleader", 175, Rarity.UNCOMMON, mage.cards.g.GarruksPackleader.class));
        cards.add(new SetCardInfo("Gem of Becoming", 205, Rarity.UNCOMMON, mage.cards.g.GemOfBecoming.class));
        cards.add(new SetCardInfo("Giant Scorpion", 94, Rarity.COMMON, mage.cards.g.GiantScorpion.class));
        cards.add(new SetCardInfo("Gilded Lotus", 206, Rarity.RARE, mage.cards.g.GildedLotus.class));
        cards.add(new SetCardInfo("Glacial Fortress", 225, Rarity.RARE, mage.cards.g.GlacialFortress.class));
        cards.add(new SetCardInfo("Glorious Charge", 15, Rarity.COMMON, mage.cards.g.GloriousCharge.class));
        cards.add(new SetCardInfo("Goblin Arsonist", 134, Rarity.COMMON, mage.cards.g.GoblinArsonist.class));
        cards.add(new SetCardInfo("Goblin Battle Jester", 135, Rarity.COMMON, mage.cards.g.GoblinBattleJester.class));
        cards.add(new SetCardInfo("Griffin Protector", 16, Rarity.COMMON, mage.cards.g.GriffinProtector.class));
        cards.add(new SetCardInfo("Ground Seal", 176, Rarity.RARE, mage.cards.g.GroundSeal.class));
        cards.add(new SetCardInfo("Guardian Lions", 17, Rarity.COMMON, mage.cards.g.GuardianLions.class));
        cards.add(new SetCardInfo("Guardians of Akrasa", 18, Rarity.COMMON, mage.cards.g.GuardiansOfAkrasa.class));
        cards.add(new SetCardInfo("Hamletback Goliath", 136, Rarity.RARE, mage.cards.h.HamletbackGoliath.class));
        cards.add(new SetCardInfo("Harbor Bandit", 95, Rarity.UNCOMMON, mage.cards.h.HarborBandit.class));
        cards.add(new SetCardInfo("Harbor Serpent", 53, Rarity.COMMON, mage.cards.h.HarborSerpent.class));
        cards.add(new SetCardInfo("Healer of the Pride", 19, Rarity.UNCOMMON, mage.cards.h.HealerOfThePride.class));
        cards.add(new SetCardInfo("Hellion Crucible", 226, Rarity.RARE, mage.cards.h.HellionCrucible.class));
        cards.add(new SetCardInfo("Hydrosurge", 54, Rarity.COMMON, mage.cards.h.Hydrosurge.class));
        cards.add(new SetCardInfo("Index", 55, Rarity.COMMON, mage.cards.i.Index.class));
        cards.add(new SetCardInfo("Intrepid Hero", 20, Rarity.RARE, mage.cards.i.IntrepidHero.class));
        cards.add(new SetCardInfo("Island", 234, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 235, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 236, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 237, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Jace, Memory Adept", 56, Rarity.MYTHIC, mage.cards.j.JaceMemoryAdept.class));
        cards.add(new SetCardInfo("Jace's Phantasm", 57, Rarity.UNCOMMON, mage.cards.j.JacesPhantasm.class));
        cards.add(new SetCardInfo("Jayemdae Tome", 207, Rarity.UNCOMMON, mage.cards.j.JayemdaeTome.class));
        cards.add(new SetCardInfo("Kindled Fury", 137, Rarity.COMMON, mage.cards.k.KindledFury.class));
        cards.add(new SetCardInfo("Kitesail", 208, Rarity.UNCOMMON, mage.cards.k.Kitesail.class));
        cards.add(new SetCardInfo("Knight of Glory", 21, Rarity.UNCOMMON, mage.cards.k.KnightOfGlory.class));
        cards.add(new SetCardInfo("Knight of Infamy", 96, Rarity.UNCOMMON, mage.cards.k.KnightOfInfamy.class));
        cards.add(new SetCardInfo("Kraken Hatchling", 58, Rarity.COMMON, mage.cards.k.KrakenHatchling.class));
        cards.add(new SetCardInfo("Krenko, Mob Boss", 138, Rarity.RARE, mage.cards.k.KrenkoMobBoss.class));
        cards.add(new SetCardInfo("Krenko's Command", 139, Rarity.COMMON, mage.cards.k.KrenkosCommand.class));
        cards.add(new SetCardInfo("Liliana of the Dark Realms", 97, Rarity.MYTHIC, mage.cards.l.LilianaOfTheDarkRealms.class));
        cards.add(new SetCardInfo("Liliana's Shade", 98, Rarity.COMMON, mage.cards.l.LilianasShade.class));
        cards.add(new SetCardInfo("Magmaquake", 140, Rarity.RARE, mage.cards.m.Magmaquake.class));
        cards.add(new SetCardInfo("Mark of Mutiny", 141, Rarity.UNCOMMON, mage.cards.m.MarkOfMutiny.class));
        cards.add(new SetCardInfo("Mark of the Vampire", 99, Rarity.COMMON, mage.cards.m.MarkOfTheVampire.class));
        cards.add(new SetCardInfo("Master of the Pearl Trident", 59, Rarity.RARE, mage.cards.m.MasterOfThePearlTrident.class));
        cards.add(new SetCardInfo("Merfolk of the Pearl Trident", 60, Rarity.COMMON, mage.cards.m.MerfolkOfThePearlTrident.class));
        cards.add(new SetCardInfo("Mindclaw Shaman", 142, Rarity.UNCOMMON, mage.cards.m.MindclawShaman.class));
        cards.add(new SetCardInfo("Mind Rot", 100, Rarity.COMMON, mage.cards.m.MindRot.class));
        cards.add(new SetCardInfo("Mind Sculpt", 61, Rarity.COMMON, mage.cards.m.MindSculpt.class));
        cards.add(new SetCardInfo("Mogg Flunkies", 143, Rarity.COMMON, mage.cards.m.MoggFlunkies.class));
        cards.add(new SetCardInfo("Mountain", 242, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 243, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 244, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 245, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Murder", 101, Rarity.COMMON, mage.cards.m.Murder.class));
        cards.add(new SetCardInfo("Mutilate", 102, Rarity.RARE, mage.cards.m.Mutilate.class));
        cards.add(new SetCardInfo("Mwonvuli Beast Tracker", 177, Rarity.UNCOMMON, mage.cards.m.MwonvuliBeastTracker.class));
        cards.add(new SetCardInfo("Naturalize", 178, Rarity.COMMON, mage.cards.n.Naturalize.class));
        cards.add(new SetCardInfo("Nefarox, Overlord of Grixis", 103, Rarity.RARE, mage.cards.n.NefaroxOverlordOfGrixis.class));
        cards.add(new SetCardInfo("Negate", 62, Rarity.COMMON, mage.cards.n.Negate.class));
        cards.add(new SetCardInfo("Nicol Bolas, Planeswalker", 199, Rarity.MYTHIC, mage.cards.n.NicolBolasPlaneswalker.class));
        cards.add(new SetCardInfo("Oblivion Ring", 22, Rarity.UNCOMMON, mage.cards.o.OblivionRing.class));
        cards.add(new SetCardInfo("Odric, Master Tactician", 23, Rarity.RARE, mage.cards.o.OdricMasterTactician.class));
        cards.add(new SetCardInfo("Omniscience", 63, Rarity.MYTHIC, mage.cards.o.Omniscience.class));
        cards.add(new SetCardInfo("Pacifism", 24, Rarity.COMMON, mage.cards.p.Pacifism.class));
        cards.add(new SetCardInfo("Phylactery Lich", 104, Rarity.RARE, mage.cards.p.PhylacteryLich.class));
        cards.add(new SetCardInfo("Phyrexian Hulk", 209, Rarity.UNCOMMON, mage.cards.p.PhyrexianHulk.class));
        cards.add(new SetCardInfo("Pillarfield Ox", 25, Rarity.COMMON, mage.cards.p.PillarfieldOx.class));
        cards.add(new SetCardInfo("Plains", 230, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 231, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 232, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 233, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Planar Cleansing", 26, Rarity.RARE, mage.cards.p.PlanarCleansing.class));
        cards.add(new SetCardInfo("Plummet", 179, Rarity.COMMON, mage.cards.p.Plummet.class));
        cards.add(new SetCardInfo("Predatory Rampage", 180, Rarity.RARE, mage.cards.p.PredatoryRampage.class));
        cards.add(new SetCardInfo("Prey Upon", 181, Rarity.COMMON, mage.cards.p.PreyUpon.class));
        cards.add(new SetCardInfo("Primal Clay", 210, Rarity.UNCOMMON, mage.cards.p.PrimalClay.class));
        cards.add(new SetCardInfo("Primal Huntbeast", 182, Rarity.COMMON, mage.cards.p.PrimalHuntbeast.class));
        cards.add(new SetCardInfo("Primordial Hydra", 183, Rarity.MYTHIC, mage.cards.p.PrimordialHydra.class));
        cards.add(new SetCardInfo("Prized Elephant", 27, Rarity.UNCOMMON, mage.cards.p.PrizedElephant.class));
        cards.add(new SetCardInfo("Public Execution", 105, Rarity.UNCOMMON, mage.cards.p.PublicExecution.class));
        cards.add(new SetCardInfo("Quirion Dryad", 184, Rarity.RARE, mage.cards.q.QuirionDryad.class));
        cards.add(new SetCardInfo("Rain of Blades", 28, Rarity.UNCOMMON, mage.cards.r.RainOfBlades.class));
        cards.add(new SetCardInfo("Rancor", 185, Rarity.UNCOMMON, mage.cards.r.Rancor.class));
        cards.add(new SetCardInfo("Ranger's Path", 186, Rarity.COMMON, mage.cards.r.RangersPath.class));
        cards.add(new SetCardInfo("Ravenous Rats", 106, Rarity.COMMON, mage.cards.r.RavenousRats.class));
        cards.add(new SetCardInfo("Reckless Brute", 144, Rarity.COMMON, mage.cards.r.RecklessBrute.class));
        cards.add(new SetCardInfo("Redirect", 64, Rarity.RARE, mage.cards.r.Redirect.class));
        cards.add(new SetCardInfo("Reliquary Tower", 227, Rarity.UNCOMMON, mage.cards.r.ReliquaryTower.class));
        cards.add(new SetCardInfo("Reverberate", 145, Rarity.RARE, mage.cards.r.Reverberate.class));
        cards.add(new SetCardInfo("Revive", 187, Rarity.UNCOMMON, mage.cards.r.Revive.class));
        cards.add(new SetCardInfo("Rewind", 65, Rarity.UNCOMMON, mage.cards.r.Rewind.class));
        cards.add(new SetCardInfo("Rhox Faithmender", 29, Rarity.RARE, mage.cards.r.RhoxFaithmender.class));
        cards.add(new SetCardInfo("Ring of Evos Isle", 211, Rarity.UNCOMMON, mage.cards.r.RingOfEvosIsle.class));
        cards.add(new SetCardInfo("Ring of Kalonia", 212, Rarity.UNCOMMON, mage.cards.r.RingOfKalonia.class));
        cards.add(new SetCardInfo("Ring of Thune", 213, Rarity.UNCOMMON, mage.cards.r.RingOfThune.class));
        cards.add(new SetCardInfo("Ring of Valkas", 214, Rarity.UNCOMMON, mage.cards.r.RingOfValkas.class));
        cards.add(new SetCardInfo("Ring of Xathrid", 215, Rarity.UNCOMMON, mage.cards.r.RingOfXathrid.class));
        cards.add(new SetCardInfo("Rise from the Grave", 107, Rarity.UNCOMMON, mage.cards.r.RiseFromTheGrave.class));
        cards.add(new SetCardInfo("Roaring Primadox", 188, Rarity.UNCOMMON, mage.cards.r.RoaringPrimadox.class));
        cards.add(new SetCardInfo("Rootbound Crag", 228, Rarity.RARE, mage.cards.r.RootboundCrag.class));
        cards.add(new SetCardInfo("Rummaging Goblin", 146, Rarity.COMMON, mage.cards.r.RummagingGoblin.class));
        cards.add(new SetCardInfo("Safe Passage", 30, Rarity.COMMON, mage.cards.s.SafePassage.class));
        cards.add(new SetCardInfo("Sands of Delirium", 216, Rarity.RARE, mage.cards.s.SandsOfDelirium.class));
        cards.add(new SetCardInfo("Scroll Thief", 66, Rarity.COMMON, mage.cards.s.ScrollThief.class));
        cards.add(new SetCardInfo("Searing Spear", 147, Rarity.COMMON, mage.cards.s.SearingSpear.class));
        cards.add(new SetCardInfo("Sentinel Spider", 189, Rarity.COMMON, mage.cards.s.SentinelSpider.class));
        cards.add(new SetCardInfo("Serpent's Gift", 190, Rarity.COMMON, mage.cards.s.SerpentsGift.class));
        cards.add(new SetCardInfo("Serra Angel", 31, Rarity.UNCOMMON, mage.cards.s.SerraAngel.class));
        cards.add(new SetCardInfo("Serra Avatar", 32, Rarity.MYTHIC, mage.cards.s.SerraAvatar.class));
        cards.add(new SetCardInfo("Serra Avenger", 33, Rarity.RARE, mage.cards.s.SerraAvenger.class));
        cards.add(new SetCardInfo("Servant of Nefarox", 108, Rarity.COMMON, mage.cards.s.ServantOfNefarox.class));
        cards.add(new SetCardInfo("Shimian Specter", 109, Rarity.RARE, mage.cards.s.ShimianSpecter.class));
        cards.add(new SetCardInfo("Show of Valor", 34, Rarity.COMMON, mage.cards.s.ShowOfValor.class));
        cards.add(new SetCardInfo("Sign in Blood", 110, Rarity.COMMON, mage.cards.s.SignInBlood.class));
        cards.add(new SetCardInfo("Silklash Spider", 191, Rarity.RARE, mage.cards.s.SilklashSpider.class));
        cards.add(new SetCardInfo("Silvercoat Lion", 35, Rarity.COMMON, mage.cards.s.SilvercoatLion.class));
        cards.add(new SetCardInfo("Sleep", 67, Rarity.UNCOMMON, mage.cards.s.Sleep.class));
        cards.add(new SetCardInfo("Slumbering Dragon", 148, Rarity.RARE, mage.cards.s.SlumberingDragon.class));
        cards.add(new SetCardInfo("Smelt", 149, Rarity.COMMON, mage.cards.s.Smelt.class));
        cards.add(new SetCardInfo("Spelltwine", 68, Rarity.RARE, mage.cards.s.Spelltwine.class));
        cards.add(new SetCardInfo("Sphinx of Uthuun", 69, Rarity.RARE, mage.cards.s.SphinxOfUthuun.class));
        cards.add(new SetCardInfo("Spiked Baloth", 192, Rarity.COMMON, mage.cards.s.SpikedBaloth.class));
        cards.add(new SetCardInfo("Staff of Nin", 217, Rarity.RARE, mage.cards.s.StaffOfNin.class));
        cards.add(new SetCardInfo("Stormtide Leviathan", 70, Rarity.RARE, mage.cards.s.StormtideLeviathan.class));
        cards.add(new SetCardInfo("Stuffy Doll", 218, Rarity.RARE, mage.cards.s.StuffyDoll.class));
        cards.add(new SetCardInfo("Sublime Archangel", 36, Rarity.MYTHIC, mage.cards.s.SublimeArchangel.class));
        cards.add(new SetCardInfo("Sunpetal Grove", 229, Rarity.RARE, mage.cards.s.SunpetalGrove.class));
        cards.add(new SetCardInfo("Swamp", 238, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 239, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 240, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 241, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Switcheroo", 71, Rarity.UNCOMMON, mage.cards.s.Switcheroo.class));
        cards.add(new SetCardInfo("Talrand's Invocation", 73, Rarity.UNCOMMON, mage.cards.t.TalrandsInvocation.class));
        cards.add(new SetCardInfo("Talrand, Sky Summoner", 72, Rarity.RARE, mage.cards.t.TalrandSkySummoner.class));
        cards.add(new SetCardInfo("Thragtusk", 193, Rarity.RARE, mage.cards.t.Thragtusk.class));
        cards.add(new SetCardInfo("Thundermaw Hellkite", 150, Rarity.MYTHIC, mage.cards.t.ThundermawHellkite.class));
        cards.add(new SetCardInfo("Timberpack Wolf", 194, Rarity.COMMON, mage.cards.t.TimberpackWolf.class));
        cards.add(new SetCardInfo("Titanic Growth", 195, Rarity.COMMON, mage.cards.t.TitanicGrowth.class));
        cards.add(new SetCardInfo("Torch Fiend", 151, Rarity.UNCOMMON, mage.cards.t.TorchFiend.class));
        cards.add(new SetCardInfo("Tormented Soul", 111, Rarity.COMMON, mage.cards.t.TormentedSoul.class));
        cards.add(new SetCardInfo("Tormod's Crypt", 219, Rarity.UNCOMMON, mage.cards.t.TormodsCrypt.class));
        cards.add(new SetCardInfo("Touch of the Eternal", 37, Rarity.RARE, mage.cards.t.TouchOfTheEternal.class));
        cards.add(new SetCardInfo("Trading Post", 220, Rarity.RARE, mage.cards.t.TradingPost.class));
        cards.add(new SetCardInfo("Tricks of the Trade", 74, Rarity.COMMON, mage.cards.t.TricksOfTheTrade.class));
        cards.add(new SetCardInfo("Trumpet Blast", 152, Rarity.COMMON, mage.cards.t.TrumpetBlast.class));
        cards.add(new SetCardInfo("Turn to Slag", 153, Rarity.COMMON, mage.cards.t.TurnToSlag.class));
        cards.add(new SetCardInfo("Unsummon", 75, Rarity.COMMON, mage.cards.u.Unsummon.class));
        cards.add(new SetCardInfo("Vampire Nighthawk", 112, Rarity.UNCOMMON, mage.cards.v.VampireNighthawk.class));
        cards.add(new SetCardInfo("Vampire Nocturnus", 113, Rarity.MYTHIC, mage.cards.v.VampireNocturnus.class));
        cards.add(new SetCardInfo("Vastwood Gorger", 196, Rarity.COMMON, mage.cards.v.VastwoodGorger.class));
        cards.add(new SetCardInfo("Vedalken Entrancer", 76, Rarity.COMMON, mage.cards.v.VedalkenEntrancer.class));
        cards.add(new SetCardInfo("Veilborn Ghoul", 114, Rarity.UNCOMMON, mage.cards.v.VeilbornGhoul.class));
        cards.add(new SetCardInfo("Vile Rebirth", 115, Rarity.COMMON, mage.cards.v.VileRebirth.class));
        cards.add(new SetCardInfo("Void Stalker", 77, Rarity.RARE, mage.cards.v.VoidStalker.class));
        cards.add(new SetCardInfo("Volcanic Geyser", 154, Rarity.UNCOMMON, mage.cards.v.VolcanicGeyser.class));
        cards.add(new SetCardInfo("Volcanic Strength", 155, Rarity.COMMON, mage.cards.v.VolcanicStrength.class));
        cards.add(new SetCardInfo("Walking Corpse", 116, Rarity.COMMON, mage.cards.w.WalkingCorpse.class));
        cards.add(new SetCardInfo("Wall of Fire", 156, Rarity.COMMON, mage.cards.w.WallOfFire.class));
        cards.add(new SetCardInfo("Warclamp Mastiff", 40, Rarity.COMMON, mage.cards.w.WarclampMastiff.class));
        cards.add(new SetCardInfo("War Falcon", 38, Rarity.COMMON, mage.cards.w.WarFalcon.class));
        cards.add(new SetCardInfo("War Priest of Thune", 39, Rarity.UNCOMMON, mage.cards.w.WarPriestOfThune.class));
        cards.add(new SetCardInfo("Watercourser", 78, Rarity.COMMON, mage.cards.w.Watercourser.class));
        cards.add(new SetCardInfo("Welkin Tern", 79, Rarity.COMMON, mage.cards.w.WelkinTern.class));
        cards.add(new SetCardInfo("Wild Guess", 157, Rarity.COMMON, mage.cards.w.WildGuess.class));
        cards.add(new SetCardInfo("Wind Drake", 80, Rarity.COMMON, mage.cards.w.WindDrake.class));
        cards.add(new SetCardInfo("Wit's End", 117, Rarity.RARE, mage.cards.w.WitsEnd.class));
        cards.add(new SetCardInfo("Worldfire", 158, Rarity.MYTHIC, mage.cards.w.Worldfire.class));
        cards.add(new SetCardInfo("Xathrid Gorgon", 118, Rarity.RARE, mage.cards.x.XathridGorgon.class));
        cards.add(new SetCardInfo("Yeva, Nature's Herald", 197, Rarity.RARE, mage.cards.y.YevaNaturesHerald.class));
        cards.add(new SetCardInfo("Yeva's Forcemage", 198, Rarity.COMMON, mage.cards.y.YevasForcemage.class));
        cards.add(new SetCardInfo("Zombie Goliath", 119, Rarity.COMMON, mage.cards.z.ZombieGoliath.class));
    }

    @Override
    public BoosterCollator createCollator() {
        return new Magic2013Collator();
    }
}

// Booster collation info from https://www.lethe.xyz/mtg/collation/m13.html
// Using USA collation
class Magic2013Collator implements BoosterCollator {
    private final CardRun commonA = new CardRun(true, "86", "186", "146", "9", "62", "100", "25", "75", "179", "152", "98", "17", "76", "155", "170", "157", "89", "18", "62", "137", "195", "124", "100", "30", "41", "146", "172", "35", "82", "89", "61", "48", "178", "25", "30", "98", "53", "137", "186", "35", "143", "75", "108", "152", "172", "34", "76", "157", "86", "9", "178", "124", "61", "170", "108", "17", "155", "48", "179", "34", "82", "195", "41", "18", "143", "53");
    private final CardRun commonB = new CardRun(true, "93", "40", "55", "147", "60", "101", "181", "11", "58", "130", "99", "164", "121", "15", "40", "110", "60", "55", "93", "13", "130", "147", "165", "54", "99", "11", "189", "153", "3", "58", "93", "164", "78", "130", "40", "165", "110", "13", "55", "101", "153", "54", "15", "189", "78", "99", "181", "3", "147", "164", "60", "121", "11", "110", "189", "54", "13", "153", "181", "15", "101", "78", "58", "121", "165", "3");
    private final CardRun commonC1 = new CardRun(true, "194", "85", "135", "115", "74", "6", "196", "160", "144", "94", "66", "5", "90", "198", "192", "156", "47", "38", "119", "194", "122", "94", "80", "2", "182", "79", "139", "83", "224", "160", "16", "47", "144", "119", "198", "85", "38", "139", "74", "192", "135", "2", "90", "79", "196", "115", "122", "6", "80", "182", "5", "156", "83", "66", "16");
    private final CardRun commonC2 = new CardRun(true, "49", "24", "163", "106", "51", "134", "12", "7", "190", "92", "149", "128", "24", "111", "169", "50", "116", "134", "163", "51", "224", "49", "106", "149", "161", "111", "12", "126", "24", "169", "190", "116", "7", "128", "92", "49", "161", "51", "126", "106", "50", "190", "163", "149", "111", "92", "134", "7", "161", "12", "116", "169", "126", "50", "128");
    private final CardRun uncommonA = new CardRun(true, "159", "10", "207", "201", "81", "73", "185", "127", "212", "4", "96", "43", "175", "215", "133", "39", "114", "67", "166", "73", "127", "95", "201", "212", "28", "188", "151", "114", "215", "213", "10", "185", "67", "91", "207", "141", "42", "19", "166", "214", "96", "71", "151", "39", "205", "91", "159", "42", "142", "19", "214", "211", "188", "81", "133", "4", "205", "213", "175", "71", "142", "28", "211", "43", "95", "141");
    private final CardRun uncommonB = new CardRun(true, "154", "107", "65", "210", "171", "208", "31", "84", "57", "132", "173", "219", "202", "107", "52", "21", "154", "187", "208", "112", "46", "31", "22", "177", "125", "84", "204", "52", "227", "21", "187", "112", "120", "209", "46", "27", "210", "105", "125", "171", "219", "65", "202", "132", "227", "173", "204", "57", "22", "120", "105", "27", "209", "177");
    private final CardRun rareA = new CardRun(true, "20", "70", "162", "228", "14", "37", "88", "29", "45", "229", "129", "109", "217", "136", "72", "14", "118", "88", "228", "20", "193", "45", "70", "229", "136", "162", "129", "29", "109", "118", "37", "193", "72", "228", "45", "88", "20", "129", "136", "70", "109", "14", "162", "229", "72", "37", "228", "193", "29", "20", "118", "45", "129", "14", "70", "217", "162", "109", "37", "29", "229", "88", "193", "136", "118", "72");
    private final CardRun rareB = new CardRun(true, "26", "148", "184", "220", "68", "168", "77", "131", "223", "64", "218", "222", "184", "168", "220", "102", "68", "148", "103", "222", "64", "131", "218", "102", "77", "26", "184", "223", "103", "131", "68", "64", "148", "222", "102", "184", "218", "168", "131", "26", "103", "220", "77", "148", "223", "218", "68", "26", "222", "220", "64", "103", "168", "102", "223");
    private final CardRun rareC = new CardRun(true, "33", "59", "221", "87", "23", "206", "191", "226", "117", "69", "33", "217", "225", "221", "140", "87", "197", "59", "138", "226", "69", "216", "206", "180", "140", "59", "23", "221", "117", "191", "225", "216", "33", "197", "138", "87", "180", "226", "206", "117", "59", "191", "140", "23", "180", "216", "221", "69", "206", "197", "117", "33", "180", "138", "216", "226", "197", "225", "69", "23", "191", "87", "217", "138", "225", "140");
    private final CardRun rareD = new CardRun(true, "145", "104", "183", "203", "8", "36", "123", "113", "176", "1", "97", "44", "145", "183", "200", "203", "104", "56", "158", "174", "8", "199", "44", "113", "145", "1", "203", "176", "63", "123", "200", "8", "167", "104", "199", "150", "44", "36", "176", "77", "203", "145", "32", "174", "104", "63", "150", "8", "44", "176", "97", "167", "158", "32", "56");
    private final CardRun land = new CardRun(false, "230", "231", "232", "233", "234", "235", "236", "237", "238", "239", "240", "241", "242", "243", "244", "245", "246", "247", "248", "249");

    private final BoosterStructure AAABC1C1C1C1C1C1 = new BoosterStructure(
            commonA, commonA, commonA,
            commonB,
            commonC1, commonC1, commonC1, commonC1, commonC1, commonC1
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
    private final BoosterStructure AAABBBBC2C2C2 = new BoosterStructure(
            commonA, commonA, commonA,
            commonB, commonB, commonB, commonB,
            commonC2, commonC2, commonC2
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
    private final BoosterStructure AAB = new BoosterStructure(uncommonA, uncommonA, uncommonB);
    private final BoosterStructure ABB = new BoosterStructure(uncommonA, uncommonB, uncommonB);
    private final BoosterStructure R1 = new BoosterStructure(rareA);
    private final BoosterStructure R2 = new BoosterStructure(rareB);
    private final BoosterStructure R3 = new BoosterStructure(rareC);
    private final BoosterStructure R4 = new BoosterStructure(rareD);
    private final BoosterStructure L1 = new BoosterStructure(land);

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
            AAABBBBC2C2C2,
            AAABBBBC2C2C2,
            AAAABBC2C2C2C2,
            AAAABBC2C2C2C2,
            AAAABBC2C2C2C2,
            AAAABBC2C2C2C2,
            AAAABBBC2C2C2,
            AAAABBBC2C2C2
    );
    // In order for equal numbers of each uncommon to exist, the average booster must contain:
    // 1.65 A uncommons (33 / 20)
    // 1.35 B uncommons (27 / 20)
    // These numbers are the same for all sets with 60 uncommons in asymmetrical A/B print runs
    private final RarityConfiguration uncommonRuns = new RarityConfiguration(
            AAB, AAB, AAB, AAB, AAB, AAB, AAB, AAB, AAB, AAB, AAB, AAB, AAB,
            ABB, ABB, ABB, ABB, ABB, ABB, ABB
    );

    private final RarityConfiguration rareRuns = new RarityConfiguration(
            R1, R1, R1, R1, R1, R1,
            R2, R2, R2, R2, R2,
            R3, R3, R3, R3, R3, R3,
            R4, R4, R4, R4, R4
    );

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
