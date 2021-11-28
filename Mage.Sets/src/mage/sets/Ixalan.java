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
 * @author fireshoes
 */
public final class Ixalan extends ExpansionSet {

    private static final Ixalan instance = new Ixalan();

    public static Ixalan getInstance() {
        return instance;
    }

    private Ixalan() {
        super("Ixalan", "XLN", ExpansionSet.buildDate(2017, 9, 29), SetType.EXPANSION);
        this.blockName = "Ixalan";
        this.hasBoosters = true;
        this.hasBasicLands = true;
        this.numBoosterLands = 1;
        this.numBoosterCommon = 10;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 9.4;
        this.numBoosterDoubleFaced = -1;
        this.maxCardNumberInBooster = 279;

        cards.add(new SetCardInfo("Adanto, the First Fort", 22, Rarity.RARE, mage.cards.a.AdantoTheFirstFort.class));
        cards.add(new SetCardInfo("Adanto Vanguard", 1, Rarity.UNCOMMON, mage.cards.a.AdantoVanguard.class));
        cards.add(new SetCardInfo("Admiral Beckett Brass", 217, Rarity.MYTHIC, mage.cards.a.AdmiralBeckettBrass.class));
        cards.add(new SetCardInfo("Air Elemental", 45, Rarity.UNCOMMON, mage.cards.a.AirElemental.class));
        cards.add(new SetCardInfo("Ancient Brontodon", 175, Rarity.COMMON, mage.cards.a.AncientBrontodon.class));
        cards.add(new SetCardInfo("Angrath's Marauders", 132, Rarity.RARE, mage.cards.a.AngrathsMarauders.class));
        cards.add(new SetCardInfo("Anointed Deacon", 89, Rarity.COMMON, mage.cards.a.AnointedDeacon.class));
        cards.add(new SetCardInfo("Arcane Adaptation", 46, Rarity.RARE, mage.cards.a.ArcaneAdaptation.class));
        cards.add(new SetCardInfo("Arguel's Blood Fast", 90, Rarity.RARE, mage.cards.a.ArguelsBloodFast.class));
        cards.add(new SetCardInfo("Ashes of the Abhorrent", 2, Rarity.RARE, mage.cards.a.AshesOfTheAbhorrent.class));
        cards.add(new SetCardInfo("Atzocan Archer", 176, Rarity.UNCOMMON, mage.cards.a.AtzocanArcher.class));
        cards.add(new SetCardInfo("Axis of Mortality", 3, Rarity.MYTHIC, mage.cards.a.AxisOfMortality.class));
        cards.add(new SetCardInfo("Azcanta, the Sunken Ruin", 74, Rarity.RARE, mage.cards.a.AzcantaTheSunkenRuin.class));
        cards.add(new SetCardInfo("Belligerent Brontodon", 218, Rarity.UNCOMMON, mage.cards.b.BelligerentBrontodon.class));
        cards.add(new SetCardInfo("Bellowing Aegisaur", 4, Rarity.UNCOMMON, mage.cards.b.BellowingAegisaur.class));
        cards.add(new SetCardInfo("Bishop of Rebirth", 5, Rarity.RARE, mage.cards.b.BishopOfRebirth.class));
        cards.add(new SetCardInfo("Bishop of the Bloodstained", 91, Rarity.UNCOMMON, mage.cards.b.BishopOfTheBloodstained.class));
        cards.add(new SetCardInfo("Bishop's Soldier", 6, Rarity.COMMON, mage.cards.b.BishopsSoldier.class));
        cards.add(new SetCardInfo("Blight Keeper", 92, Rarity.COMMON, mage.cards.b.BlightKeeper.class));
        cards.add(new SetCardInfo("Blinding Fog", 177, Rarity.COMMON, mage.cards.b.BlindingFog.class));
        cards.add(new SetCardInfo("Bloodcrazed Paladin", 93, Rarity.RARE, mage.cards.b.BloodcrazedPaladin.class));
        cards.add(new SetCardInfo("Blossom Dryad", 178, Rarity.COMMON, mage.cards.b.BlossomDryad.class));
        cards.add(new SetCardInfo("Bonded Horncrest", 133, Rarity.UNCOMMON, mage.cards.b.BondedHorncrest.class));
        cards.add(new SetCardInfo("Boneyard Parley", 94, Rarity.MYTHIC, mage.cards.b.BoneyardParley.class));
        cards.add(new SetCardInfo("Brazen Buccaneers", 134, Rarity.COMMON, mage.cards.b.BrazenBuccaneers.class));
        cards.add(new SetCardInfo("Bright Reprisal", 7, Rarity.UNCOMMON, mage.cards.b.BrightReprisal.class));
        cards.add(new SetCardInfo("Burning Sun's Avatar", 135, Rarity.RARE, mage.cards.b.BurningSunsAvatar.class));
        cards.add(new SetCardInfo("Call to the Feast", 219, Rarity.UNCOMMON, mage.cards.c.CallToTheFeast.class));
        cards.add(new SetCardInfo("Cancel", 47, Rarity.COMMON, mage.cards.c.Cancel.class));
        cards.add(new SetCardInfo("Captain Lannery Storm", 136, Rarity.RARE, mage.cards.c.CaptainLanneryStorm.class));
        cards.add(new SetCardInfo("Captivating Crew", 137, Rarity.RARE, mage.cards.c.CaptivatingCrew.class));
        cards.add(new SetCardInfo("Carnage Tyrant", 179, Rarity.MYTHIC, mage.cards.c.CarnageTyrant.class));
        cards.add(new SetCardInfo("Castaway's Despair", 281, Rarity.COMMON, mage.cards.c.CastawaysDespair.class));
        cards.add(new SetCardInfo("Charging Monstrosaur", 138, Rarity.UNCOMMON, mage.cards.c.ChargingMonstrosaur.class));
        cards.add(new SetCardInfo("Chart a Course", 48, Rarity.UNCOMMON, mage.cards.c.ChartACourse.class));
        cards.add(new SetCardInfo("Cobbled Wings", 233, Rarity.COMMON, mage.cards.c.CobbledWings.class));
        cards.add(new SetCardInfo("Colossal Dreadmaw", 180, Rarity.COMMON, mage.cards.c.ColossalDreadmaw.class));
        cards.add(new SetCardInfo("Commune with Dinosaurs", 181, Rarity.COMMON, mage.cards.c.CommuneWithDinosaurs.class));
        cards.add(new SetCardInfo("Conqueror's Foothold", 234, Rarity.RARE, mage.cards.c.ConquerorsFoothold.class));
        cards.add(new SetCardInfo("Conqueror's Galleon", 234, Rarity.RARE, mage.cards.c.ConquerorsGalleon.class));
        cards.add(new SetCardInfo("Contract Killing", 95, Rarity.COMMON, mage.cards.c.ContractKilling.class));
        cards.add(new SetCardInfo("Costly Plunder", 96, Rarity.COMMON, mage.cards.c.CostlyPlunder.class));
        cards.add(new SetCardInfo("Crash the Ramparts", 182, Rarity.COMMON, mage.cards.c.CrashTheRamparts.class));
        cards.add(new SetCardInfo("Crushing Canopy", 183, Rarity.COMMON, mage.cards.c.CrushingCanopy.class));
        cards.add(new SetCardInfo("Daring Saboteur", 49, Rarity.RARE, mage.cards.d.DaringSaboteur.class));
        cards.add(new SetCardInfo("Dark Nourishment", 97, Rarity.UNCOMMON, mage.cards.d.DarkNourishment.class));
        cards.add(new SetCardInfo("Deadeye Plunderers", 220, Rarity.UNCOMMON, mage.cards.d.DeadeyePlunderers.class));
        cards.add(new SetCardInfo("Deadeye Quartermaster", 50, Rarity.UNCOMMON, mage.cards.d.DeadeyeQuartermaster.class));
        cards.add(new SetCardInfo("Deadeye Tormentor", 98, Rarity.COMMON, mage.cards.d.DeadeyeTormentor.class));
        cards.add(new SetCardInfo("Deadeye Tracker", 99, Rarity.RARE, mage.cards.d.DeadeyeTracker.class));
        cards.add(new SetCardInfo("Deathgorge Scavenger", 184, Rarity.RARE, mage.cards.d.DeathgorgeScavenger.class));
        cards.add(new SetCardInfo("Deathless Ancient", 100, Rarity.UNCOMMON, mage.cards.d.DeathlessAncient.class));
        cards.add(new SetCardInfo("Deeproot Champion", 185, Rarity.RARE, mage.cards.d.DeeprootChampion.class));
        cards.add(new SetCardInfo("Deeproot Warrior", 186, Rarity.COMMON, mage.cards.d.DeeprootWarrior.class));
        cards.add(new SetCardInfo("Deeproot Waters", 51, Rarity.UNCOMMON, mage.cards.d.DeeprootWaters.class));
        cards.add(new SetCardInfo("Demolish", 139, Rarity.COMMON, mage.cards.d.Demolish.class));
        cards.add(new SetCardInfo("Demystify", 8, Rarity.COMMON, mage.cards.d.Demystify.class));
        cards.add(new SetCardInfo("Depths of Desire", 52, Rarity.COMMON, mage.cards.d.DepthsOfDesire.class));
        cards.add(new SetCardInfo("Desperate Castaways", 101, Rarity.COMMON, mage.cards.d.DesperateCastaways.class));
        cards.add(new SetCardInfo("Dinosaur Stampede", 140, Rarity.UNCOMMON, mage.cards.d.DinosaurStampede.class));
        cards.add(new SetCardInfo("Dire Fleet Captain", 221, Rarity.UNCOMMON, mage.cards.d.DireFleetCaptain.class));
        cards.add(new SetCardInfo("Dire Fleet Hoarder", 102, Rarity.COMMON, mage.cards.d.DireFleetHoarder.class));
        cards.add(new SetCardInfo("Dire Fleet Interloper", 103, Rarity.COMMON, mage.cards.d.DireFleetInterloper.class));
        cards.add(new SetCardInfo("Dire Fleet Ravager", 104, Rarity.MYTHIC, mage.cards.d.DireFleetRavager.class));
        cards.add(new SetCardInfo("Dive Down", 53, Rarity.COMMON, mage.cards.d.DiveDown.class));
        cards.add(new SetCardInfo("Dowsing Dagger", 235, Rarity.RARE, mage.cards.d.DowsingDagger.class));
        cards.add(new SetCardInfo("Dragonskull Summit", 252, Rarity.RARE, mage.cards.d.DragonskullSummit.class));
        cards.add(new SetCardInfo("Dreamcaller Siren", 54, Rarity.RARE, mage.cards.d.DreamcallerSiren.class));
        cards.add(new SetCardInfo("Drover of the Mighty", 187, Rarity.UNCOMMON, mage.cards.d.DroverOfTheMighty.class));
        cards.add(new SetCardInfo("Drowned Catacomb", 253, Rarity.RARE, mage.cards.d.DrownedCatacomb.class));
        cards.add(new SetCardInfo("Dual Shot", 141, Rarity.COMMON, mage.cards.d.DualShot.class));
        cards.add(new SetCardInfo("Duress", 105, Rarity.COMMON, mage.cards.d.Duress.class));
        cards.add(new SetCardInfo("Duskborne Skymarcher", 9, Rarity.UNCOMMON, mage.cards.d.DuskborneSkymarcher.class));
        cards.add(new SetCardInfo("Dusk Legion Dreadnought", 236, Rarity.UNCOMMON, mage.cards.d.DuskLegionDreadnought.class));
        cards.add(new SetCardInfo("Elaborate Firecannon", 237, Rarity.UNCOMMON, mage.cards.e.ElaborateFirecannon.class));
        cards.add(new SetCardInfo("Emergent Growth", 188, Rarity.UNCOMMON, mage.cards.e.EmergentGrowth.class));
        cards.add(new SetCardInfo("Emissary of Sunrise", 10, Rarity.UNCOMMON, mage.cards.e.EmissaryOfSunrise.class));
        cards.add(new SetCardInfo("Emperor's Vanguard", 189, Rarity.RARE, mage.cards.e.EmperorsVanguard.class));
        cards.add(new SetCardInfo("Encampment Keeper", 11, Rarity.COMMON, mage.cards.e.EncampmentKeeper.class));
        cards.add(new SetCardInfo("Entrancing Melody", 55, Rarity.RARE, mage.cards.e.EntrancingMelody.class));
        cards.add(new SetCardInfo("Fathom Fleet Captain", 106, Rarity.RARE, mage.cards.f.FathomFleetCaptain.class));
        cards.add(new SetCardInfo("Fathom Fleet Cutthroat", 107, Rarity.COMMON, mage.cards.f.FathomFleetCutthroat.class));
        cards.add(new SetCardInfo("Fathom Fleet Firebrand", 142, Rarity.COMMON, mage.cards.f.FathomFleetFirebrand.class));
        cards.add(new SetCardInfo("Favorable Winds", 56, Rarity.UNCOMMON, mage.cards.f.FavorableWinds.class));
        cards.add(new SetCardInfo("Fell Flagship", 238, Rarity.RARE, mage.cards.f.FellFlagship.class));
        cards.add(new SetCardInfo("Field of Ruin", 254, Rarity.UNCOMMON, mage.cards.f.FieldOfRuin.class));
        cards.add(new SetCardInfo("Fiery Cannonade", 143, Rarity.UNCOMMON, mage.cards.f.FieryCannonade.class));
        cards.add(new SetCardInfo("Fire Shrine Keeper", 144, Rarity.COMMON, mage.cards.f.FireShrineKeeper.class));
        cards.add(new SetCardInfo("Firecannon Blast", 145, Rarity.COMMON, mage.cards.f.FirecannonBlast.class));
        cards.add(new SetCardInfo("Fleet Swallower", 57, Rarity.RARE, mage.cards.f.FleetSwallower.class));
        cards.add(new SetCardInfo("Forest", 276, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 277, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 278, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 279, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Frenzied Raptor", 146, Rarity.COMMON, mage.cards.f.FrenziedRaptor.class));
        cards.add(new SetCardInfo("Gilded Sentinel", 239, Rarity.COMMON, mage.cards.g.GildedSentinel.class));
        cards.add(new SetCardInfo("Gishath, Sun's Avatar", 222, Rarity.MYTHIC, mage.cards.g.GishathSunsAvatar.class));
        cards.add(new SetCardInfo("Glacial Fortress", 255, Rarity.RARE, mage.cards.g.GlacialFortress.class));
        cards.add(new SetCardInfo("Glorifier of Dusk", 12, Rarity.UNCOMMON, mage.cards.g.GlorifierOfDusk.class));
        cards.add(new SetCardInfo("Goring Ceratops", 13, Rarity.RARE, mage.cards.g.GoringCeratops.class));
        cards.add(new SetCardInfo("Grasping Current", 282, Rarity.RARE, mage.cards.g.GraspingCurrent.class));
        cards.add(new SetCardInfo("Grazing Whiptail", 190, Rarity.COMMON, mage.cards.g.GrazingWhiptail.class));
        cards.add(new SetCardInfo("Grim Captain's Call", 108, Rarity.UNCOMMON, mage.cards.g.GrimCaptainsCall.class));
        cards.add(new SetCardInfo("Growing Rites of Itlimoc", 191, Rarity.RARE, mage.cards.g.GrowingRitesOfItlimoc.class));
        cards.add(new SetCardInfo("Headstrong Brute", 147, Rarity.COMMON, mage.cards.h.HeadstrongBrute.class));
        cards.add(new SetCardInfo("Headwater Sentries", 58, Rarity.COMMON, mage.cards.h.HeadwaterSentries.class));
        cards.add(new SetCardInfo("Heartless Pillage", 109, Rarity.UNCOMMON, mage.cards.h.HeartlessPillage.class));
        cards.add(new SetCardInfo("Herald of Secret Streams", 59, Rarity.RARE, mage.cards.h.HeraldOfSecretStreams.class));
        cards.add(new SetCardInfo("Hierophant's Chalice", 240, Rarity.COMMON, mage.cards.h.HierophantsChalice.class));
        cards.add(new SetCardInfo("Hijack", 148, Rarity.COMMON, mage.cards.h.Hijack.class));
        cards.add(new SetCardInfo("Hostage Taker", 223, Rarity.RARE, mage.cards.h.HostageTaker.class));
        cards.add(new SetCardInfo("Huatli, Dinosaur Knight", 285, Rarity.MYTHIC, mage.cards.h.HuatliDinosaurKnight.class));
        cards.add(new SetCardInfo("Huatli's Snubhorn", 286, Rarity.COMMON, mage.cards.h.HuatlisSnubhorn.class));
        cards.add(new SetCardInfo("Huatli's Spurring", 287, Rarity.UNCOMMON, mage.cards.h.HuatlisSpurring.class));
        cards.add(new SetCardInfo("Huatli, Warrior Poet", 224, Rarity.MYTHIC, mage.cards.h.HuatliWarriorPoet.class));
        cards.add(new SetCardInfo("Imperial Aerosaur", 14, Rarity.UNCOMMON, mage.cards.i.ImperialAerosaur.class));
        cards.add(new SetCardInfo("Imperial Lancer", 15, Rarity.UNCOMMON, mage.cards.i.ImperialLancer.class));
        cards.add(new SetCardInfo("Inspiring Cleric", 16, Rarity.UNCOMMON, mage.cards.i.InspiringCleric.class));
        cards.add(new SetCardInfo("Island", 264, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 265, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 266, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 267, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Itlimoc, Cradle of the Sun", 191, Rarity.RARE, mage.cards.i.ItlimocCradleOfTheSun.class));
        cards.add(new SetCardInfo("Ixalan's Binding", 17, Rarity.UNCOMMON, mage.cards.i.IxalansBinding.class));
        cards.add(new SetCardInfo("Ixalli's Diviner", 192, Rarity.COMMON, mage.cards.i.IxallisDiviner.class));
        cards.add(new SetCardInfo("Ixalli's Keeper", 193, Rarity.COMMON, mage.cards.i.IxallisKeeper.class));
        cards.add(new SetCardInfo("Jace, Cunning Castaway", 60, Rarity.MYTHIC, mage.cards.j.JaceCunningCastaway.class));
        cards.add(new SetCardInfo("Jace, Ingenious Mind-Mage", 280, Rarity.MYTHIC, mage.cards.j.JaceIngeniousMindMage.class));
        cards.add(new SetCardInfo("Jace's Sentinel", 283, Rarity.UNCOMMON, mage.cards.j.JacesSentinel.class));
        cards.add(new SetCardInfo("Jade Guardian", 194, Rarity.COMMON, mage.cards.j.JadeGuardian.class));
        cards.add(new SetCardInfo("Jungle Delver", 195, Rarity.COMMON, mage.cards.j.JungleDelver.class));
        cards.add(new SetCardInfo("Kinjalli's Caller", 18, Rarity.COMMON, mage.cards.k.KinjallisCaller.class));
        cards.add(new SetCardInfo("Kinjalli's Sunwing", 19, Rarity.RARE, mage.cards.k.KinjallisSunwing.class));
        cards.add(new SetCardInfo("Kitesail Freebooter", 110, Rarity.UNCOMMON, mage.cards.k.KitesailFreebooter.class));
        cards.add(new SetCardInfo("Kopala, Warden of Waves", 61, Rarity.RARE, mage.cards.k.KopalaWardenOfWaves.class));
        cards.add(new SetCardInfo("Kumena's Speaker", 196, Rarity.UNCOMMON, mage.cards.k.KumenasSpeaker.class));
        cards.add(new SetCardInfo("Legion Conquistador", 20, Rarity.COMMON, mage.cards.l.LegionConquistador.class));
        cards.add(new SetCardInfo("Legion's Judgment", 21, Rarity.COMMON, mage.cards.l.LegionsJudgment.class));
        cards.add(new SetCardInfo("Legion's Landing", 22, Rarity.RARE, mage.cards.l.LegionsLanding.class));
        cards.add(new SetCardInfo("Lightning-Rig Crew", 150, Rarity.UNCOMMON, mage.cards.l.LightningRigCrew.class));
        cards.add(new SetCardInfo("Lightning Strike", 149, Rarity.UNCOMMON, mage.cards.l.LightningStrike.class));
        cards.add(new SetCardInfo("Lookout's Dispersal", 62, Rarity.UNCOMMON, mage.cards.l.LookoutsDispersal.class));
        cards.add(new SetCardInfo("Looming Altisaur", 23, Rarity.COMMON, mage.cards.l.LoomingAltisaur.class));
        cards.add(new SetCardInfo("Lost Vale", 235, Rarity.RARE, mage.cards.l.LostVale.class));
        cards.add(new SetCardInfo("Lurking Chupacabra", 111, Rarity.UNCOMMON, mage.cards.l.LurkingChupacabra.class));
        cards.add(new SetCardInfo("Makeshift Munitions", 151, Rarity.UNCOMMON, mage.cards.m.MakeshiftMunitions.class));
        cards.add(new SetCardInfo("Marauding Looter", 225, Rarity.UNCOMMON, mage.cards.m.MaraudingLooter.class));
        cards.add(new SetCardInfo("March of the Drowned", 112, Rarity.COMMON, mage.cards.m.MarchOfTheDrowned.class));
        cards.add(new SetCardInfo("Mark of the Vampire", 113, Rarity.COMMON, mage.cards.m.MarkOfTheVampire.class));
        cards.add(new SetCardInfo("Mavren Fein, Dusk Apostle", 24, Rarity.RARE, mage.cards.m.MavrenFeinDuskApostle.class));
        cards.add(new SetCardInfo("Merfolk Branchwalker", 197, Rarity.UNCOMMON, mage.cards.m.MerfolkBranchwalker.class));
        cards.add(new SetCardInfo("Mountain", 272, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 273, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 274, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 275, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Navigator's Ruin", 63, Rarity.UNCOMMON, mage.cards.n.NavigatorsRuin.class));
        cards.add(new SetCardInfo("New Horizons", 198, Rarity.COMMON, mage.cards.n.NewHorizons.class));
        cards.add(new SetCardInfo("Nest Robber", 152, Rarity.COMMON, mage.cards.n.NestRobber.class));
        cards.add(new SetCardInfo("Old-Growth Dryads", 199, Rarity.RARE, mage.cards.o.OldGrowthDryads.class));
        cards.add(new SetCardInfo("One With the Wind", 64, Rarity.COMMON, mage.cards.o.OneWithTheWind.class));
        cards.add(new SetCardInfo("Opt", 65, Rarity.COMMON, mage.cards.o.Opt.class));
        cards.add(new SetCardInfo("Otepec Huntmaster", 153, Rarity.UNCOMMON, mage.cards.o.OtepecHuntmaster.class));
        cards.add(new SetCardInfo("Overflowing Insight", 66, Rarity.MYTHIC, mage.cards.o.OverflowingInsight.class));
        cards.add(new SetCardInfo("Paladin of the Bloodstained", 25, Rarity.COMMON, mage.cards.p.PaladinOfTheBloodstained.class));
        cards.add(new SetCardInfo("Perilous Voyage", 67, Rarity.UNCOMMON, mage.cards.p.PerilousVoyage.class));
        cards.add(new SetCardInfo("Pillar of Origins", 241, Rarity.UNCOMMON, mage.cards.p.PillarOfOrigins.class));
        cards.add(new SetCardInfo("Pious Interdiction", 26, Rarity.COMMON, mage.cards.p.PiousInterdiction.class));
        cards.add(new SetCardInfo("Pirate's Cutlass", 242, Rarity.COMMON, mage.cards.p.PiratesCutlass.class));
        cards.add(new SetCardInfo("Pirate's Prize", 68, Rarity.COMMON, mage.cards.p.PiratesPrize.class));
        cards.add(new SetCardInfo("Plains", 260, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 261, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 262, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 263, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Pounce", 200, Rarity.COMMON, mage.cards.p.Pounce.class));
        cards.add(new SetCardInfo("Priest of the Wakening Sun", 27, Rarity.RARE, mage.cards.p.PriestOfTheWakeningSun.class));
        cards.add(new SetCardInfo("Primal Amulet", 243, Rarity.RARE, mage.cards.p.PrimalAmulet.class));
        cards.add(new SetCardInfo("Primal Wellspring", 243, Rarity.RARE, mage.cards.p.PrimalWellspring.class));
        cards.add(new SetCardInfo("Prosperous Pirates", 69, Rarity.COMMON, mage.cards.p.ProsperousPirates.class));
        cards.add(new SetCardInfo("Prying Blade", 244, Rarity.COMMON, mage.cards.p.PryingBlade.class));
        cards.add(new SetCardInfo("Pterodon Knight", 28, Rarity.COMMON, mage.cards.p.PterodonKnight.class));
        cards.add(new SetCardInfo("Queen's Agent", 114, Rarity.COMMON, mage.cards.q.QueensAgent.class));
        cards.add(new SetCardInfo("Queen's Bay Soldier", 115, Rarity.COMMON, mage.cards.q.QueensBaySoldier.class));
        cards.add(new SetCardInfo("Queen's Commission", 29, Rarity.COMMON, mage.cards.q.QueensCommission.class));
        cards.add(new SetCardInfo("Raging Swordtooth", 226, Rarity.UNCOMMON, mage.cards.r.RagingSwordtooth.class));
        cards.add(new SetCardInfo("Raiders' Wake", 116, Rarity.UNCOMMON, mage.cards.r.RaidersWake.class));
        cards.add(new SetCardInfo("Rallying Roar", 30, Rarity.UNCOMMON, mage.cards.r.RallyingRoar.class));
        cards.add(new SetCardInfo("Rampaging Ferocidon", 154, Rarity.RARE, mage.cards.r.RampagingFerocidon.class));
        cards.add(new SetCardInfo("Ranging Raptors", 201, Rarity.UNCOMMON, mage.cards.r.RangingRaptors.class));
        cards.add(new SetCardInfo("Raptor Companion", 31, Rarity.COMMON, mage.cards.r.RaptorCompanion.class));
        cards.add(new SetCardInfo("Raptor Hatchling", 155, Rarity.UNCOMMON, mage.cards.r.RaptorHatchling.class));
        cards.add(new SetCardInfo("Ravenous Daggertooth", 202, Rarity.COMMON, mage.cards.r.RavenousDaggertooth.class));
        cards.add(new SetCardInfo("Regisaur Alpha", 227, Rarity.RARE, mage.cards.r.RegisaurAlpha.class));
        cards.add(new SetCardInfo("Repeating Barrage", 156, Rarity.RARE, mage.cards.r.RepeatingBarrage.class));
        cards.add(new SetCardInfo("Revel in Riches", 117, Rarity.RARE, mage.cards.r.RevelInRiches.class));
        cards.add(new SetCardInfo("Rigging Runner", 157, Rarity.UNCOMMON, mage.cards.r.RiggingRunner.class));
        cards.add(new SetCardInfo("Rile", 158, Rarity.COMMON, mage.cards.r.Rile.class));
        cards.add(new SetCardInfo("Ripjaw Raptor", 203, Rarity.RARE, mage.cards.r.RipjawRaptor.class));
        cards.add(new SetCardInfo("River Heralds' Boon", 204, Rarity.COMMON, mage.cards.r.RiverHeraldsBoon.class));
        cards.add(new SetCardInfo("Ritual of Rejuvenation", 32, Rarity.COMMON, mage.cards.r.RitualOfRejuvenation.class));
        cards.add(new SetCardInfo("River Sneak", 70, Rarity.UNCOMMON, mage.cards.r.RiverSneak.class));
        cards.add(new SetCardInfo("River's Rebuke", 71, Rarity.RARE, mage.cards.r.RiversRebuke.class));
        cards.add(new SetCardInfo("Rootbound Crag", 256, Rarity.RARE, mage.cards.r.RootboundCrag.class));
        cards.add(new SetCardInfo("Rowdy Crew", 159, Rarity.MYTHIC, mage.cards.r.RowdyCrew.class));
        cards.add(new SetCardInfo("Ruin Raider", 118, Rarity.RARE, mage.cards.r.RuinRaider.class));
        cards.add(new SetCardInfo("Rummaging Goblin", 160, Rarity.COMMON, mage.cards.r.RummagingGoblin.class));
        cards.add(new SetCardInfo("Run Aground", 72, Rarity.COMMON, mage.cards.r.RunAground.class));
        cards.add(new SetCardInfo("Ruthless Knave", 119, Rarity.UNCOMMON, mage.cards.r.RuthlessKnave.class));
        cards.add(new SetCardInfo("Sailor of Means", 73, Rarity.COMMON, mage.cards.s.SailorOfMeans.class));
        cards.add(new SetCardInfo("Sanctum Seeker", 120, Rarity.RARE, mage.cards.s.SanctumSeeker.class));
        cards.add(new SetCardInfo("Sanguine Sacrament", 33, Rarity.RARE, mage.cards.s.SanguineSacrament.class));
        cards.add(new SetCardInfo("Savage Stomp", 205, Rarity.UNCOMMON, mage.cards.s.SavageStomp.class));
        cards.add(new SetCardInfo("Search for Azcanta", 74, Rarity.RARE, mage.cards.s.SearchForAzcanta.class));
        cards.add(new SetCardInfo("Seekers' Squire", 121, Rarity.UNCOMMON, mage.cards.s.SeekersSquire.class));
        cards.add(new SetCardInfo("Sentinel Totem", 245, Rarity.UNCOMMON, mage.cards.s.SentinelTotem.class));
        cards.add(new SetCardInfo("Settle the Wreckage", 34, Rarity.RARE, mage.cards.s.SettleTheWreckage.class));
        cards.add(new SetCardInfo("Shadowed Caravel", 246, Rarity.RARE, mage.cards.s.ShadowedCaravel.class));
        cards.add(new SetCardInfo("Shaper Apprentice", 75, Rarity.COMMON, mage.cards.s.ShaperApprentice.class));
        cards.add(new SetCardInfo("Shapers of Nature", 228, Rarity.UNCOMMON, mage.cards.s.ShapersOfNature.class));
        cards.add(new SetCardInfo("Shapers' Sanctuary", 206, Rarity.RARE, mage.cards.s.ShapersSanctuary.class));
        cards.add(new SetCardInfo("Sheltering Light", 35, Rarity.UNCOMMON, mage.cards.s.ShelteringLight.class));
        cards.add(new SetCardInfo("Shining Aerosaur", 36, Rarity.COMMON, mage.cards.s.ShiningAerosaur.class));
        cards.add(new SetCardInfo("Shipwreck Looter", 76, Rarity.COMMON, mage.cards.s.ShipwreckLooter.class));
        cards.add(new SetCardInfo("Shore Keeper", 77, Rarity.COMMON, mage.cards.s.ShoreKeeper.class));
        cards.add(new SetCardInfo("Siren Lookout", 78, Rarity.COMMON, mage.cards.s.SirenLookout.class));
        cards.add(new SetCardInfo("Siren Stormtamer", 79, Rarity.UNCOMMON, mage.cards.s.SirenStormtamer.class));
        cards.add(new SetCardInfo("Siren's Ruse", 80, Rarity.COMMON, mage.cards.s.SirensRuse.class));
        cards.add(new SetCardInfo("Skittering Heartstopper", 122, Rarity.COMMON, mage.cards.s.SkitteringHeartstopper.class));
        cards.add(new SetCardInfo("Skulduggery", 123, Rarity.COMMON, mage.cards.s.Skulduggery.class));
        cards.add(new SetCardInfo("Sky Terror", 229, Rarity.UNCOMMON, mage.cards.s.SkyTerror.class));
        cards.add(new SetCardInfo("Skyblade of the Legion", 37, Rarity.COMMON, mage.cards.s.SkybladeOfTheLegion.class));
        cards.add(new SetCardInfo("Skymarch Bloodletter", 124, Rarity.COMMON, mage.cards.s.SkymarchBloodletter.class));
        cards.add(new SetCardInfo("Slash of Talons", 38, Rarity.COMMON, mage.cards.s.SlashOfTalons.class));
        cards.add(new SetCardInfo("Sleek Schooner", 247, Rarity.UNCOMMON, mage.cards.s.SleekSchooner.class));
        cards.add(new SetCardInfo("Slice in Twain", 207, Rarity.UNCOMMON, mage.cards.s.SliceInTwain.class));
        cards.add(new SetCardInfo("Snapping Sailback", 208, Rarity.UNCOMMON, mage.cards.s.SnappingSailback.class));
        cards.add(new SetCardInfo("Sorcerous Spyglass", 248, Rarity.RARE, mage.cards.s.SorcerousSpyglass.class));
        cards.add(new SetCardInfo("Spell Pierce", 81, Rarity.COMMON, mage.cards.s.SpellPierce.class));
        cards.add(new SetCardInfo("Spell Swindle", 82, Rarity.RARE, mage.cards.s.SpellSwindle.class));
        cards.add(new SetCardInfo("Spike-Tailed Ceratops", 209, Rarity.COMMON, mage.cards.s.SpikeTailedCeratops.class));
        cards.add(new SetCardInfo("Spires of Orazca", 249, Rarity.RARE, mage.cards.s.SpiresOfOrazca.class));
        cards.add(new SetCardInfo("Spitfire Bastion", 173, Rarity.RARE, mage.cards.s.SpitfireBastion.class));
        cards.add(new SetCardInfo("Spreading Rot", 125, Rarity.COMMON, mage.cards.s.SpreadingRot.class));
        cards.add(new SetCardInfo("Star of Extinction", 161, Rarity.MYTHIC, mage.cards.s.StarOfExtinction.class));
        cards.add(new SetCardInfo("Steadfast Armasaur", 39, Rarity.UNCOMMON, mage.cards.s.SteadfastArmasaur.class));
        cards.add(new SetCardInfo("Stone Quarry", 289, Rarity.COMMON, mage.cards.s.StoneQuarry.class));
        cards.add(new SetCardInfo("Storm Fleet Aerialist", 83, Rarity.UNCOMMON, mage.cards.s.StormFleetAerialist.class));
        cards.add(new SetCardInfo("Storm Fleet Arsonist", 162, Rarity.UNCOMMON, mage.cards.s.StormFleetArsonist.class));
        cards.add(new SetCardInfo("Storm Fleet Pyromancer", 163, Rarity.COMMON, mage.cards.s.StormFleetPyromancer.class));
        cards.add(new SetCardInfo("Storm Fleet Spy", 84, Rarity.UNCOMMON, mage.cards.s.StormFleetSpy.class));
        cards.add(new SetCardInfo("Storm Sculptor", 85, Rarity.COMMON, mage.cards.s.StormSculptor.class));
        cards.add(new SetCardInfo("Sunbird's Invocation", 165, Rarity.RARE, mage.cards.s.SunbirdsInvocation.class));
        cards.add(new SetCardInfo("Sun-Blessed Mount", 288, Rarity.RARE, mage.cards.s.SunBlessedMount.class));
        cards.add(new SetCardInfo("Sun-Crowned Hunters", 164, Rarity.COMMON, mage.cards.s.SunCrownedHunters.class));
        cards.add(new SetCardInfo("Sunpetal Grove", 257, Rarity.RARE, mage.cards.s.SunpetalGrove.class));
        cards.add(new SetCardInfo("Sunrise Seeker", 40, Rarity.COMMON, mage.cards.s.SunriseSeeker.class));
        cards.add(new SetCardInfo("Sure Strike", 166, Rarity.COMMON, mage.cards.s.SureStrike.class));
        cards.add(new SetCardInfo("Swamp", 268, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 269, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 270, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 271, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swashbuckling", 167, Rarity.COMMON, mage.cards.s.Swashbuckling.class));
        cards.add(new SetCardInfo("Sword-Point Diplomacy", 126, Rarity.RARE, mage.cards.s.SwordPointDiplomacy.class));
        cards.add(new SetCardInfo("Tempest Caller", 86, Rarity.UNCOMMON, mage.cards.t.TempestCaller.class));
        cards.add(new SetCardInfo("Temple of Aclazotz", 90, Rarity.RARE, mage.cards.t.TempleOfAclazotz.class));
        cards.add(new SetCardInfo("Territorial Hammerskull", 41, Rarity.COMMON, mage.cards.t.TerritorialHammerskull.class));
        cards.add(new SetCardInfo("Thaumatic Compass", 249, Rarity.RARE, mage.cards.t.ThaumaticCompass.class));
        cards.add(new SetCardInfo("Thrash of Raptors", 168, Rarity.COMMON, mage.cards.t.ThrashOfRaptors.class));
        cards.add(new SetCardInfo("Thundering Spineback", 210, Rarity.UNCOMMON, mage.cards.t.ThunderingSpineback.class));
        cards.add(new SetCardInfo("Tilonalli's Knight", 169, Rarity.COMMON, mage.cards.t.TilonallisKnight.class));
        cards.add(new SetCardInfo("Tilonalli's Skinshifter", 170, Rarity.RARE, mage.cards.t.TilonallisSkinshifter.class));
        cards.add(new SetCardInfo("Tishana's Wayfinder", 211, Rarity.COMMON, mage.cards.t.TishanasWayfinder.class));
        cards.add(new SetCardInfo("Tishana, Voice of Thunder", 230, Rarity.MYTHIC, mage.cards.t.TishanaVoiceOfThunder.class));
        cards.add(new SetCardInfo("Tocatli Honor Guard", 42, Rarity.RARE, mage.cards.t.TocatliHonorGuard.class));
        cards.add(new SetCardInfo("Treasure Cove", 250, Rarity.RARE, mage.cards.t.TreasureCove.class));
        cards.add(new SetCardInfo("Treasure Map", 250, Rarity.RARE, mage.cards.t.TreasureMap.class));
        cards.add(new SetCardInfo("Trove of Temptation", 171, Rarity.UNCOMMON, mage.cards.t.TroveOfTemptation.class));
        cards.add(new SetCardInfo("Unclaimed Territory", 258, Rarity.UNCOMMON, mage.cards.u.UnclaimedTerritory.class));
        cards.add(new SetCardInfo("Unfriendly Fire", 172, Rarity.COMMON, mage.cards.u.UnfriendlyFire.class));
        cards.add(new SetCardInfo("Unknown Shores", 259, Rarity.COMMON, mage.cards.u.UnknownShores.class));
        cards.add(new SetCardInfo("Vampire's Zeal", 43, Rarity.COMMON, mage.cards.v.VampiresZeal.class));
        cards.add(new SetCardInfo("Vance's Blasting Cannons", 173, Rarity.RARE, mage.cards.v.VancesBlastingCannons.class));
        cards.add(new SetCardInfo("Vanquish the Weak", 127, Rarity.COMMON, mage.cards.v.VanquishTheWeak.class));
        cards.add(new SetCardInfo("Vanquisher's Banner", 251, Rarity.RARE, mage.cards.v.VanquishersBanner.class));
        cards.add(new SetCardInfo("Verdant Rebirth", 212, Rarity.UNCOMMON, mage.cards.v.VerdantRebirth.class));
        cards.add(new SetCardInfo("Verdant Sun's Avatar", 213, Rarity.RARE, mage.cards.v.VerdantSunsAvatar.class));
        cards.add(new SetCardInfo("Vicious Conquistador", 128, Rarity.UNCOMMON, mage.cards.v.ViciousConquistador.class));
        cards.add(new SetCardInfo("Vineshaper Mystic", 214, Rarity.UNCOMMON, mage.cards.v.VineshaperMystic.class));
        cards.add(new SetCardInfo("Vona, Butcher of Magan", 231, Rarity.MYTHIC, mage.cards.v.VonaButcherOfMagan.class));
        cards.add(new SetCardInfo("Vraska, Relic Seeker", 232, Rarity.MYTHIC, mage.cards.v.VraskaRelicSeeker.class));
        cards.add(new SetCardInfo("Vraska's Contempt", 129, Rarity.RARE, mage.cards.v.VraskasContempt.class));
        cards.add(new SetCardInfo("Wakening Sun's Avatar", 44, Rarity.MYTHIC, mage.cards.w.WakeningSunsAvatar.class));
        cards.add(new SetCardInfo("Waker of the Wilds", 215, Rarity.RARE, mage.cards.w.WakerOfTheWilds.class));
        cards.add(new SetCardInfo("Walk the Plank", 130, Rarity.UNCOMMON, mage.cards.w.WalkThePlank.class));
        cards.add(new SetCardInfo("Wanted Scoundrels", 131, Rarity.UNCOMMON, mage.cards.w.WantedScoundrels.class));
        cards.add(new SetCardInfo("Watertrap Weaver", 87, Rarity.COMMON, mage.cards.w.WatertrapWeaver.class));
        cards.add(new SetCardInfo("Wildgrowth Walker", 216, Rarity.UNCOMMON, mage.cards.w.WildgrowthWalker.class));
        cards.add(new SetCardInfo("Wily Goblin", 174, Rarity.UNCOMMON, mage.cards.w.WilyGoblin.class));
        cards.add(new SetCardInfo("Wind Strider", 88, Rarity.COMMON, mage.cards.w.WindStrider.class));
        cards.add(new SetCardInfo("Woodland Stream", 284, Rarity.COMMON, mage.cards.w.WoodlandStream.class));
    }

    @Override
    public BoosterCollator createCollator() {
        return new IxalanCollator();
    }
}

// Booster collation info from https://www.lethe.xyz/mtg/collation/xln.html
// Using USA collation for common/uncommon, rare collation inferred from other sets
class IxalanCollator implements BoosterCollator {
    private final CardRun commonA = new CardRun(true, "142", "20", "102", "169", "23", "114", "134", "21", "123", "146", "28", "115", "160", "6", "112", "158", "36", "107", "152", "40", "101", "164", "43", "98", "166", "21", "122", "142", "37", "102", "141", "20", "113", "146", "38", "114", "134", "23", "115", "144", "28", "92", "169", "29", "123", "158", "6", "112", "152", "36", "98", "160", "40", "107", "164", "43", "101", "144", "37", "122", "141", "38", "113", "166", "29", "92");
    private final CardRun commonB = new CardRun(true, "64", "182", "78", "194", "75", "198", "76", "193", "77", "202", "52", "181", "73", "209", "68", "192", "65", "178", "85", "204", "69", "186", "77", "194", "64", "193", "78", "202", "75", "198", "73", "182", "76", "181", "85", "178", "52", "204", "68", "209", "65", "192", "69", "186", "78", "202", "75", "198", "64", "182", "76", "181", "73", "193", "65", "194", "77", "204", "69", "178", "85", "209", "68", "192", "52", "186");
    private final CardRun commonC1 = new CardRun(true, "125", "96", "58", "240", "41", "103", "167", "80", "259", "72", "89", "242", "127", "195", "175", "211", "244", "147", "18", "163", "125", "31", "233", "81", "41", "88", "240", "96", "145", "103", "11", "72", "259", "167", "58", "127", "242", "89", "163", "80", "195", "31", "175", "200", "244", "147", "18", "211", "11", "233", "200", "81", "32", "88", "145");
    private final CardRun commonC2 = new CardRun(true, "139", "124", "8", "190", "239", "172", "47", "180", "8", "95", "148", "87", "177", "25", "239", "168", "183", "124", "53", "26", "139", "190", "105", "172", "32", "47", "180", "148", "95", "177", "25", "239", "168", "105", "26", "183", "172", "53", "190", "8", "124", "139", "25", "177", "95", "47", "87", "148", "26", "183", "87", "105", "168", "53", "180");
    private final CardRun uncommonA = new CardRun(true, "216", "83", "116", "247", "151", "196", "16", "79", "236", "162", "188", "35", "56", "110", "245", "174", "214", "39", "108", "237", "171", "212", "30", "67", "128", "218", "150", "216", "83", "131", "254", "140", "207", "9", "51", "119", "241", "162", "196", "63", "109", "247", "151", "214", "15", "62", "108", "237", "157", "212", "79", "116", "236", "174", "188", "39", "56", "110", "245", "150", "216", "67", "131", "254", "171", "196", "30", "83", "128", "218", "140", "16", "51", "119", "241", "162", "207", "9", "63", "109", "245", "150", "35", "56", "110", "247", "151", "214", "15", "62", "108", "237", "157", "39", "79", "109", "254", "174", "188", "16", "51", "131", "241", "140", "35", "63", "119", "236", "171", "212", "30", "67", "116", "218", "207", "9", "62", "128", "157", "15");
    private final CardRun uncommonB = new CardRun(true, "111", "7", "50", "187", "220", "1", "226", "91", "45", "208", "149", "121", "4", "210", "10", "228", "155", "229", "97", "86", "197", "153", "130", "12", "48", "176", "221", "133", "17", "100", "84", "201", "138", "111", "7", "70", "205", "225", "143", "14", "91", "50", "187", "149", "121", "4", "45", "208", "226", "155", "1", "219", "86", "210", "153", "97", "258", "84", "176", "220", "133", "17", "229", "48", "197", "138", "100", "10", "70", "201", "228", "143", "14", "111", "50", "205", "153", "130", "12", "84", "187", "219", "155", "1", "91", "45", "208", "226", "97", "4", "86", "197", "221", "149", "7", "121", "228", "210", "225", "258", "10", "48", "176", "220", "133", "17", "100", "229", "138", "221", "12", "258", "201", "219", "14", "225", "130", "70", "205", "143");
    private final CardRun rare = new CardRun(false, "2", "5", "13", "19", "22", "24", "27", "33", "34", "42", "46", "49", "54", "55", "57", "59", "61", "71", "74", "82", "90", "93", "99", "106", "117", "118", "120", "126", "129", "132", "135", "136", "137", "154", "156", "165", "170", "173", "184", "185", "189", "191", "199", "203", "206", "213", "215", "223", "227", "234", "235", "238", "243", "246", "248", "249", "250", "251", "252", "253", "255", "256", "257", "2", "5", "13", "19", "22", "24", "27", "33", "34", "42", "46", "49", "54", "55", "57", "59", "61", "71", "74", "82", "90", "93", "99", "106", "117", "118", "120", "126", "129", "132", "135", "136", "137", "154", "156", "165", "170", "173", "184", "185", "189", "191", "199", "203", "206", "213", "215", "223", "227", "234", "235", "238", "243", "246", "248", "249", "250", "251", "252", "253", "255", "256", "257", "3", "44", "60", "66", "94", "104", "159", "161", "179", "217", "222", "224", "230", "231", "232");
    private final CardRun land = new CardRun(false, "260", "261", "262", "263", "264", "265", "266", "267", "268", "269", "270", "271", "272", "273", "274", "275", "276", "277", "278", "279");

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
    private final BoosterStructure AAAABBBBC2C2 = new BoosterStructure(
            commonA, commonA, commonA, commonA,
            commonB, commonB, commonB, commonB,
            commonC2, commonC2
    );
    private final BoosterStructure AAB = new BoosterStructure(uncommonA, uncommonA, uncommonB);
    private final BoosterStructure ABB = new BoosterStructure(uncommonA, uncommonB, uncommonB);
    private final BoosterStructure R1 = new BoosterStructure(rare);
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
            AAAABBC2C2C2C2,
            AAAABBBBC2C2,
            AAAABBBBC2C2
    );
    private final RarityConfiguration uncommonRuns = new RarityConfiguration(AAB, ABB);
    private final RarityConfiguration rareRuns = new RarityConfiguration(R1);
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
