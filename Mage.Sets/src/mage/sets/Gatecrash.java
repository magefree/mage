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
 * @author LevelX2
 */
public final class Gatecrash extends ExpansionSet {

    private static final Gatecrash instance = new Gatecrash();

    public static Gatecrash getInstance() {
        return instance;
    }

    private Gatecrash() {
        super("Gatecrash", "GTC", ExpansionSet.buildDate(2013, 2, 1), SetType.EXPANSION);
        this.blockName = "Return to Ravnica";
        this.parentSet = ReturnToRavnica.getInstance();
        this.hasBasicLands = false;
        this.hasBoosters = true;
        this.numBoosterLands = 1;
        this.numBoosterCommon = 10;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 8;
        cards.add(new SetCardInfo("Act of Treason", 85, Rarity.COMMON, mage.cards.a.ActOfTreason.class));
        cards.add(new SetCardInfo("Adaptive Snapjaw", 113, Rarity.COMMON, mage.cards.a.AdaptiveSnapjaw.class));
        cards.add(new SetCardInfo("Aerial Maneuver", 1, Rarity.COMMON, mage.cards.a.AerialManeuver.class));
        cards.add(new SetCardInfo("Aetherize", 29, Rarity.UNCOMMON, mage.cards.a.Aetherize.class));
        cards.add(new SetCardInfo("Agoraphobia", 30, Rarity.UNCOMMON, mage.cards.a.Agoraphobia.class));
        cards.add(new SetCardInfo("Alms Beast", 141, Rarity.RARE, mage.cards.a.AlmsBeast.class));
        cards.add(new SetCardInfo("Alpha Authority", 114, Rarity.UNCOMMON, mage.cards.a.AlphaAuthority.class));
        cards.add(new SetCardInfo("Angelic Edict", 2, Rarity.COMMON, mage.cards.a.AngelicEdict.class));
        cards.add(new SetCardInfo("Angelic Skirmisher", 3, Rarity.RARE, mage.cards.a.AngelicSkirmisher.class));
        cards.add(new SetCardInfo("Armored Transport", 226, Rarity.COMMON, mage.cards.a.ArmoredTransport.class));
        cards.add(new SetCardInfo("Arrows of Justice", 211, Rarity.UNCOMMON, mage.cards.a.ArrowsOfJustice.class));
        cards.add(new SetCardInfo("Assault Griffin", 4, Rarity.COMMON, mage.cards.a.AssaultGriffin.class));
        cards.add(new SetCardInfo("Assemble the Legion", 142, Rarity.RARE, mage.cards.a.AssembleTheLegion.class));
        cards.add(new SetCardInfo("Aurelia's Fury", 144, Rarity.MYTHIC, mage.cards.a.AureliasFury.class));
        cards.add(new SetCardInfo("Aurelia, the Warleader", 143, Rarity.MYTHIC, mage.cards.a.AureliaTheWarleader.class));
        cards.add(new SetCardInfo("Balustrade Spy", 57, Rarity.COMMON, mage.cards.b.BalustradeSpy.class));
        cards.add(new SetCardInfo("Bane Alley Broker", 145, Rarity.UNCOMMON, mage.cards.b.BaneAlleyBroker.class));
        cards.add(new SetCardInfo("Basilica Guards", 5, Rarity.COMMON, mage.cards.b.BasilicaGuards.class));
        cards.add(new SetCardInfo("Basilica Screecher", 58, Rarity.COMMON, mage.cards.b.BasilicaScreecher.class));
        cards.add(new SetCardInfo("Beckon Apparition", 212, Rarity.COMMON, mage.cards.b.BeckonApparition.class));
        cards.add(new SetCardInfo("Biomass Mutation", 213, Rarity.RARE, mage.cards.b.BiomassMutation.class));
        cards.add(new SetCardInfo("Bioshift", 214, Rarity.COMMON, mage.cards.b.Bioshift.class));
        cards.add(new SetCardInfo("Biovisionary", 146, Rarity.RARE, mage.cards.b.Biovisionary.class));
        cards.add(new SetCardInfo("Blind Obedience", 6, Rarity.RARE, mage.cards.b.BlindObedience.class));
        cards.add(new SetCardInfo("Bomber Corps", 86, Rarity.COMMON, mage.cards.b.BomberCorps.class));
        cards.add(new SetCardInfo("Borborygmos Enraged", 147, Rarity.MYTHIC, mage.cards.b.BorborygmosEnraged.class));
        cards.add(new SetCardInfo("Boros Charm", 148, Rarity.UNCOMMON, mage.cards.b.BorosCharm.class));
        cards.add(new SetCardInfo("Boros Elite", 7, Rarity.UNCOMMON, mage.cards.b.BorosElite.class));
        cards.add(new SetCardInfo("Boros Guildgate", 239, Rarity.COMMON, mage.cards.b.BorosGuildgate.class));
        cards.add(new SetCardInfo("Boros Keyrune", 227, Rarity.UNCOMMON, mage.cards.b.BorosKeyrune.class));
        cards.add(new SetCardInfo("Boros Reckoner", 215, Rarity.RARE, mage.cards.b.BorosReckoner.class));
        cards.add(new SetCardInfo("Breeding Pool", 240, Rarity.RARE, mage.cards.b.BreedingPool.class));
        cards.add(new SetCardInfo("Burning-Tree Emissary", 216, Rarity.UNCOMMON, mage.cards.b.BurningTreeEmissary.class));
        cards.add(new SetCardInfo("Burst of Strength", 115, Rarity.COMMON, mage.cards.b.BurstOfStrength.class));
        cards.add(new SetCardInfo("Call of the Nightwing", 149, Rarity.UNCOMMON, mage.cards.c.CallOfTheNightwing.class));
        cards.add(new SetCardInfo("Cartel Aristocrat", 150, Rarity.UNCOMMON, mage.cards.c.CartelAristocrat.class));
        cards.add(new SetCardInfo("Cinder Elemental", 87, Rarity.UNCOMMON, mage.cards.c.CinderElemental.class));
        cards.add(new SetCardInfo("Clan Defiance", 151, Rarity.RARE, mage.cards.c.ClanDefiance.class));
        cards.add(new SetCardInfo("Clinging Anemones", 31, Rarity.COMMON, mage.cards.c.ClingingAnemones.class));
        cards.add(new SetCardInfo("Cloudfin Raptor", 32, Rarity.COMMON, mage.cards.c.CloudfinRaptor.class));
        cards.add(new SetCardInfo("Coerced Confession", 217, Rarity.UNCOMMON, mage.cards.c.CoercedConfession.class));
        cards.add(new SetCardInfo("Consuming Aberration", 152, Rarity.RARE, mage.cards.c.ConsumingAberration.class));
        cards.add(new SetCardInfo("Contaminated Ground", 59, Rarity.COMMON, mage.cards.c.ContaminatedGround.class));
        cards.add(new SetCardInfo("Corpse Blockade", 60, Rarity.COMMON, mage.cards.c.CorpseBlockade.class));
        cards.add(new SetCardInfo("Court Street Denizen", 8, Rarity.COMMON, mage.cards.c.CourtStreetDenizen.class));
        cards.add(new SetCardInfo("Crackling Perimeter", 88, Rarity.UNCOMMON, mage.cards.c.CracklingPerimeter.class));
        cards.add(new SetCardInfo("Crocanura", 116, Rarity.COMMON, mage.cards.c.Crocanura.class));
        cards.add(new SetCardInfo("Crowned Ceratok", 117, Rarity.UNCOMMON, mage.cards.c.CrownedCeratok.class));
        cards.add(new SetCardInfo("Crypt Ghast", 61, Rarity.RARE, mage.cards.c.CryptGhast.class));
        cards.add(new SetCardInfo("Daring Skyjek", 9, Rarity.COMMON, mage.cards.d.DaringSkyjek.class));
        cards.add(new SetCardInfo("Deathcult Rogue", 218, Rarity.COMMON, mage.cards.d.DeathcultRogue.class));
        cards.add(new SetCardInfo("Deathpact Angel", 153, Rarity.MYTHIC, mage.cards.d.DeathpactAngel.class));
        cards.add(new SetCardInfo("Death's Approach", 62, Rarity.COMMON, mage.cards.d.DeathsApproach.class));
        cards.add(new SetCardInfo("Debtor's Pulpit", 10, Rarity.UNCOMMON, mage.cards.d.DebtorsPulpit.class));
        cards.add(new SetCardInfo("Devour Flesh", 63, Rarity.COMMON, mage.cards.d.DevourFlesh.class));
        cards.add(new SetCardInfo("Diluvian Primordial", 33, Rarity.RARE, mage.cards.d.DiluvianPrimordial.class));
        cards.add(new SetCardInfo("Dimir Charm", 154, Rarity.UNCOMMON, mage.cards.d.DimirCharm.class));
        cards.add(new SetCardInfo("Dimir Guildgate", 241, Rarity.COMMON, mage.cards.d.DimirGuildgate.class));
        cards.add(new SetCardInfo("Dimir Keyrune", 228, Rarity.UNCOMMON, mage.cards.d.DimirKeyrune.class));
        cards.add(new SetCardInfo("Dinrova Horror", 155, Rarity.UNCOMMON, mage.cards.d.DinrovaHorror.class));
        cards.add(new SetCardInfo("Disciple of the Old Ways", 118, Rarity.COMMON, mage.cards.d.DiscipleOfTheOldWays.class));
        cards.add(new SetCardInfo("Domri Rade", 156, Rarity.MYTHIC, mage.cards.d.DomriRade.class));
        cards.add(new SetCardInfo("Drakewing Krasis", 157, Rarity.COMMON, mage.cards.d.DrakewingKrasis.class));
        cards.add(new SetCardInfo("Duskmantle Guildmage", 158, Rarity.UNCOMMON, mage.cards.d.DuskmantleGuildmage.class));
        cards.add(new SetCardInfo("Duskmantle Seer", 159, Rarity.MYTHIC, mage.cards.d.DuskmantleSeer.class));
        cards.add(new SetCardInfo("Dutiful Thrull", 11, Rarity.COMMON, mage.cards.d.DutifulThrull.class));
        cards.add(new SetCardInfo("Dying Wish", 64, Rarity.UNCOMMON, mage.cards.d.DyingWish.class));
        cards.add(new SetCardInfo("Elusive Krasis", 160, Rarity.UNCOMMON, mage.cards.e.ElusiveKrasis.class));
        cards.add(new SetCardInfo("Ember Beast", 89, Rarity.COMMON, mage.cards.e.EmberBeast.class));
        cards.add(new SetCardInfo("Enter the Infinite", 34, Rarity.MYTHIC, mage.cards.e.EnterTheInfinite.class));
        cards.add(new SetCardInfo("Executioner's Swing", 161, Rarity.COMMON, mage.cards.e.ExecutionersSwing.class));
        cards.add(new SetCardInfo("Experiment One", 119, Rarity.UNCOMMON, mage.cards.e.ExperimentOne.class));
        cards.add(new SetCardInfo("Fathom Mage", 162, Rarity.RARE, mage.cards.f.FathomMage.class));
        cards.add(new SetCardInfo("Firefist Striker", 90, Rarity.UNCOMMON, mage.cards.f.FirefistStriker.class));
        cards.add(new SetCardInfo("Firemane Avenger", 163, Rarity.RARE, mage.cards.f.FiremaneAvenger.class));
        cards.add(new SetCardInfo("Five-Alarm Fire", 91, Rarity.RARE, mage.cards.f.FiveAlarmFire.class));
        cards.add(new SetCardInfo("Forced Adaptation", 120, Rarity.COMMON, mage.cards.f.ForcedAdaptation.class));
        cards.add(new SetCardInfo("Fortress Cyclops", 164, Rarity.UNCOMMON, mage.cards.f.FortressCyclops.class));
        cards.add(new SetCardInfo("Foundry Champion", 165, Rarity.RARE, mage.cards.f.FoundryChampion.class));
        cards.add(new SetCardInfo("Foundry Street Denizen", 92, Rarity.COMMON, mage.cards.f.FoundryStreetDenizen.class));
        cards.add(new SetCardInfo("Frenzied Tilling", 166, Rarity.UNCOMMON, mage.cards.f.FrenziedTilling.class));
        cards.add(new SetCardInfo("Frilled Oculus", 35, Rarity.COMMON, mage.cards.f.FrilledOculus.class));
        cards.add(new SetCardInfo("Frontline Medic", 12, Rarity.RARE, mage.cards.f.FrontlineMedic.class));
        cards.add(new SetCardInfo("Furious Resistance", 93, Rarity.COMMON, mage.cards.f.FuriousResistance.class));
        cards.add(new SetCardInfo("Gateway Shade", 65, Rarity.UNCOMMON, mage.cards.g.GatewayShade.class));
        cards.add(new SetCardInfo("Ghor-Clan Rampager", 167, Rarity.UNCOMMON, mage.cards.g.GhorClanRampager.class));
        cards.add(new SetCardInfo("Giant Adephage", 121, Rarity.MYTHIC, mage.cards.g.GiantAdephage.class));
        cards.add(new SetCardInfo("Gideon, Champion of Justice", 13, Rarity.MYTHIC, mage.cards.g.GideonChampionOfJustice.class));
        cards.add(new SetCardInfo("Gift of Orzhova", 219, Rarity.UNCOMMON, mage.cards.g.GiftOfOrzhova.class));
        cards.add(new SetCardInfo("Glaring Spotlight", 229, Rarity.RARE, mage.cards.g.GlaringSpotlight.class));
        cards.add(new SetCardInfo("Godless Shrine", 242, Rarity.RARE, mage.cards.g.GodlessShrine.class));
        cards.add(new SetCardInfo("Greenside Watcher", 122, Rarity.COMMON, mage.cards.g.GreensideWatcher.class));
        cards.add(new SetCardInfo("Gridlock", 36, Rarity.UNCOMMON, mage.cards.g.Gridlock.class));
        cards.add(new SetCardInfo("Grisly Spectacle", 66, Rarity.COMMON, mage.cards.g.GrislySpectacle.class));
        cards.add(new SetCardInfo("Ground Assault", 168, Rarity.UNCOMMON, mage.cards.g.GroundAssault.class));
        cards.add(new SetCardInfo("Gruul Charm", 169, Rarity.UNCOMMON, mage.cards.g.GruulCharm.class));
        cards.add(new SetCardInfo("Gruul Guildgate", 243, Rarity.COMMON, mage.cards.g.GruulGuildgate.class));
        cards.add(new SetCardInfo("Gruul Keyrune", 230, Rarity.UNCOMMON, mage.cards.g.GruulKeyrune.class));
        cards.add(new SetCardInfo("Gruul Ragebeast", 170, Rarity.RARE, mage.cards.g.GruulRagebeast.class));
        cards.add(new SetCardInfo("Guardian of the Gateless", 14, Rarity.UNCOMMON, mage.cards.g.GuardianOfTheGateless.class));
        cards.add(new SetCardInfo("Guildscorn Ward", 15, Rarity.COMMON, mage.cards.g.GuildscornWard.class));
        cards.add(new SetCardInfo("Gutter Skulk", 67, Rarity.COMMON, mage.cards.g.GutterSkulk.class));
        cards.add(new SetCardInfo("Gyre Sage", 123, Rarity.RARE, mage.cards.g.GyreSage.class));
        cards.add(new SetCardInfo("Hands of Binding", 37, Rarity.COMMON, mage.cards.h.HandsOfBinding.class));
        cards.add(new SetCardInfo("Hellkite Tyrant", 94, Rarity.MYTHIC, mage.cards.h.HellkiteTyrant.class));
        cards.add(new SetCardInfo("Hellraiser Goblin", 95, Rarity.UNCOMMON, mage.cards.h.HellraiserGoblin.class));
        cards.add(new SetCardInfo("High Priest of Penance", 171, Rarity.RARE, mage.cards.h.HighPriestOfPenance.class));
        cards.add(new SetCardInfo("Hindervines", 124, Rarity.UNCOMMON, mage.cards.h.Hindervines.class));
        cards.add(new SetCardInfo("Hold the Gates", 16, Rarity.UNCOMMON, mage.cards.h.HoldTheGates.class));
        cards.add(new SetCardInfo("Holy Mantle", 17, Rarity.UNCOMMON, mage.cards.h.HolyMantle.class));
        cards.add(new SetCardInfo("Homing Lightning", 96, Rarity.UNCOMMON, mage.cards.h.HomingLightning.class));
        cards.add(new SetCardInfo("Horror of the Dim", 68, Rarity.COMMON, mage.cards.h.HorrorOfTheDim.class));
        cards.add(new SetCardInfo("Hydroform", 172, Rarity.COMMON, mage.cards.h.Hydroform.class));
        cards.add(new SetCardInfo("Illness in the Ranks", 69, Rarity.UNCOMMON, mage.cards.i.IllnessInTheRanks.class));
        cards.add(new SetCardInfo("Illusionist's Bracers", 231, Rarity.RARE, mage.cards.i.IllusionistsBracers.class));
        cards.add(new SetCardInfo("Immortal Servitude", 220, Rarity.RARE, mage.cards.i.ImmortalServitude.class));
        cards.add(new SetCardInfo("Incursion Specialist", 38, Rarity.UNCOMMON, mage.cards.i.IncursionSpecialist.class));
        cards.add(new SetCardInfo("Ivy Lane Denizen", 125, Rarity.COMMON, mage.cards.i.IvyLaneDenizen.class));
        cards.add(new SetCardInfo("Keymaster Rogue", 39, Rarity.COMMON, mage.cards.k.KeymasterRogue.class));
        cards.add(new SetCardInfo("Killing Glare", 70, Rarity.UNCOMMON, mage.cards.k.KillingGlare.class));
        cards.add(new SetCardInfo("Kingpin's Pet", 173, Rarity.COMMON, mage.cards.k.KingpinsPet.class));
        cards.add(new SetCardInfo("Knight of Obligation", 18, Rarity.UNCOMMON, mage.cards.k.KnightOfObligation.class));
        cards.add(new SetCardInfo("Knight Watch", 19, Rarity.COMMON, mage.cards.k.KnightWatch.class));
        cards.add(new SetCardInfo("Last Thoughts", 40, Rarity.COMMON, mage.cards.l.LastThoughts.class));
        cards.add(new SetCardInfo("Lazav, Dimir Mastermind", 174, Rarity.MYTHIC, mage.cards.l.LazavDimirMastermind.class));
        cards.add(new SetCardInfo("Legion Loyalist", 97, Rarity.RARE, mage.cards.l.LegionLoyalist.class));
        cards.add(new SetCardInfo("Leyline Phantom", 41, Rarity.COMMON, mage.cards.l.LeylinePhantom.class));
        cards.add(new SetCardInfo("Lord of the Void", 71, Rarity.MYTHIC, mage.cards.l.LordOfTheVoid.class));
        cards.add(new SetCardInfo("Luminate Primordial", 20, Rarity.RARE, mage.cards.l.LuminatePrimordial.class));
        cards.add(new SetCardInfo("Madcap Skills", 98, Rarity.COMMON, mage.cards.m.MadcapSkills.class));
        cards.add(new SetCardInfo("Mark for Death", 99, Rarity.UNCOMMON, mage.cards.m.MarkForDeath.class));
        cards.add(new SetCardInfo("Martial Glory", 175, Rarity.COMMON, mage.cards.m.MartialGlory.class));
        cards.add(new SetCardInfo("Massive Raid", 100, Rarity.COMMON, mage.cards.m.MassiveRaid.class));
        cards.add(new SetCardInfo("Master Biomancer", 176, Rarity.MYTHIC, mage.cards.m.MasterBiomancer.class));
        cards.add(new SetCardInfo("Mental Vapors", 72, Rarity.UNCOMMON, mage.cards.m.MentalVapors.class));
        cards.add(new SetCardInfo("Merciless Eviction", 177, Rarity.RARE, mage.cards.m.MercilessEviction.class));
        cards.add(new SetCardInfo("Merfolk of the Depths", 221, Rarity.UNCOMMON, mage.cards.m.MerfolkOfTheDepths.class));
        cards.add(new SetCardInfo("Metropolis Sprite", 42, Rarity.COMMON, mage.cards.m.MetropolisSprite.class));
        cards.add(new SetCardInfo("Midnight Recovery", 73, Rarity.COMMON, mage.cards.m.MidnightRecovery.class));
        cards.add(new SetCardInfo("Millennial Gargoyle", 232, Rarity.COMMON, mage.cards.m.MillennialGargoyle.class));
        cards.add(new SetCardInfo("Miming Slime", 126, Rarity.UNCOMMON, mage.cards.m.MimingSlime.class));
        cards.add(new SetCardInfo("Mindeye Drake", 43, Rarity.UNCOMMON, mage.cards.m.MindeyeDrake.class));
        cards.add(new SetCardInfo("Mind Grind", 178, Rarity.RARE, mage.cards.m.MindGrind.class));
        cards.add(new SetCardInfo("Molten Primordial", 101, Rarity.RARE, mage.cards.m.MoltenPrimordial.class));
        cards.add(new SetCardInfo("Mortus Strider", 179, Rarity.COMMON, mage.cards.m.MortusStrider.class));
        cards.add(new SetCardInfo("Mugging", 102, Rarity.COMMON, mage.cards.m.Mugging.class));
        cards.add(new SetCardInfo("Murder Investigation", 21, Rarity.UNCOMMON, mage.cards.m.MurderInvestigation.class));
        cards.add(new SetCardInfo("Mystic Genesis", 180, Rarity.RARE, mage.cards.m.MysticGenesis.class));
        cards.add(new SetCardInfo("Naturalize", 127, Rarity.COMMON, mage.cards.n.Naturalize.class));
        cards.add(new SetCardInfo("Nav Squad Commandos", 22, Rarity.COMMON, mage.cards.n.NavSquadCommandos.class));
        cards.add(new SetCardInfo("Nightveil Specter", 222, Rarity.RARE, mage.cards.n.NightveilSpecter.class));
        cards.add(new SetCardInfo("Nimbus Swimmer", 181, Rarity.UNCOMMON, mage.cards.n.NimbusSwimmer.class));
        cards.add(new SetCardInfo("Obzedat, Ghost Council", 182, Rarity.MYTHIC, mage.cards.o.ObzedatGhostCouncil.class));
        cards.add(new SetCardInfo("Ogre Slumlord", 74, Rarity.RARE, mage.cards.o.OgreSlumlord.class));
        cards.add(new SetCardInfo("One Thousand Lashes", 183, Rarity.UNCOMMON, mage.cards.o.OneThousandLashes.class));
        cards.add(new SetCardInfo("Ooze Flux", 128, Rarity.RARE, mage.cards.o.OozeFlux.class));
        cards.add(new SetCardInfo("Ordruun Veteran", 184, Rarity.UNCOMMON, mage.cards.o.OrdruunVeteran.class));
        cards.add(new SetCardInfo("Orzhov Charm", 185, Rarity.UNCOMMON, mage.cards.o.OrzhovCharm.class));
        cards.add(new SetCardInfo("Orzhov Guildgate", 244, Rarity.COMMON, mage.cards.o.OrzhovGuildgate.class));
        cards.add(new SetCardInfo("Orzhov Keyrune", 233, Rarity.UNCOMMON, mage.cards.o.OrzhovKeyrune.class));
        cards.add(new SetCardInfo("Paranoid Delusions", 186, Rarity.COMMON, mage.cards.p.ParanoidDelusions.class));
        cards.add(new SetCardInfo("Pit Fight", 223, Rarity.COMMON, mage.cards.p.PitFight.class));
        cards.add(new SetCardInfo("Predator's Rapport", 129, Rarity.COMMON, mage.cards.p.PredatorsRapport.class));
        cards.add(new SetCardInfo("Primal Visitation", 187, Rarity.COMMON, mage.cards.p.PrimalVisitation.class));
        cards.add(new SetCardInfo("Prime Speaker Zegana", 188, Rarity.MYTHIC, mage.cards.p.PrimeSpeakerZegana.class));
        cards.add(new SetCardInfo("Prophetic Prism", 234, Rarity.COMMON, mage.cards.p.PropheticPrism.class));
        cards.add(new SetCardInfo("Psychic Strike", 189, Rarity.COMMON, mage.cards.p.PsychicStrike.class));
        cards.add(new SetCardInfo("Purge the Profane", 190, Rarity.COMMON, mage.cards.p.PurgeTheProfane.class));
        cards.add(new SetCardInfo("Rapid Hybridization", 44, Rarity.UNCOMMON, mage.cards.r.RapidHybridization.class));
        cards.add(new SetCardInfo("Razortip Whip", 235, Rarity.COMMON, mage.cards.r.RazortipWhip.class));
        cards.add(new SetCardInfo("Realmwright", 45, Rarity.RARE, mage.cards.r.Realmwright.class));
        cards.add(new SetCardInfo("Righteous Charge", 23, Rarity.UNCOMMON, mage.cards.r.RighteousCharge.class));
        cards.add(new SetCardInfo("Riot Gear", 236, Rarity.COMMON, mage.cards.r.RiotGear.class));
        cards.add(new SetCardInfo("Ripscale Predator", 103, Rarity.UNCOMMON, mage.cards.r.RipscalePredator.class));
        cards.add(new SetCardInfo("Rubblebelt Raiders", 224, Rarity.RARE, mage.cards.r.RubblebeltRaiders.class));
        cards.add(new SetCardInfo("Rubblehulk", 191, Rarity.RARE, mage.cards.r.Rubblehulk.class));
        cards.add(new SetCardInfo("Ruination Wurm", 192, Rarity.COMMON, mage.cards.r.RuinationWurm.class));
        cards.add(new SetCardInfo("Rust Scarab", 130, Rarity.UNCOMMON, mage.cards.r.RustScarab.class));
        cards.add(new SetCardInfo("Sacred Foundry", 245, Rarity.RARE, mage.cards.s.SacredFoundry.class));
        cards.add(new SetCardInfo("Sage's Row Denizen", 46, Rarity.COMMON, mage.cards.s.SagesRowDenizen.class));
        cards.add(new SetCardInfo("Sapphire Drake", 47, Rarity.UNCOMMON, mage.cards.s.SapphireDrake.class));
        cards.add(new SetCardInfo("Scab-Clan Charger", 131, Rarity.COMMON, mage.cards.s.ScabClanCharger.class));
        cards.add(new SetCardInfo("Scatter Arc", 48, Rarity.COMMON, mage.cards.s.ScatterArc.class));
        cards.add(new SetCardInfo("Scorchwalker", 104, Rarity.COMMON, mage.cards.s.Scorchwalker.class));
        cards.add(new SetCardInfo("Sepulchral Primordial", 75, Rarity.RARE, mage.cards.s.SepulchralPrimordial.class));
        cards.add(new SetCardInfo("Serene Remembrance", 132, Rarity.UNCOMMON, mage.cards.s.SereneRemembrance.class));
        cards.add(new SetCardInfo("Shadow Alley Denizen", 76, Rarity.COMMON, mage.cards.s.ShadowAlleyDenizen.class));
        cards.add(new SetCardInfo("Shadow Slice", 77, Rarity.COMMON, mage.cards.s.ShadowSlice.class));
        cards.add(new SetCardInfo("Shambleshark", 193, Rarity.COMMON, mage.cards.s.Shambleshark.class));
        cards.add(new SetCardInfo("Shattering Blow", 225, Rarity.COMMON, mage.cards.s.ShatteringBlow.class));
        cards.add(new SetCardInfo("Shielded Passage", 24, Rarity.COMMON, mage.cards.s.ShieldedPassage.class));
        cards.add(new SetCardInfo("Signal the Clans", 194, Rarity.RARE, mage.cards.s.SignalTheClans.class));
        cards.add(new SetCardInfo("Simic Charm", 195, Rarity.UNCOMMON, mage.cards.s.SimicCharm.class));
        cards.add(new SetCardInfo("Simic Fluxmage", 49, Rarity.UNCOMMON, mage.cards.s.SimicFluxmage.class));
        cards.add(new SetCardInfo("Simic Guildgate", 246, Rarity.COMMON, mage.cards.s.SimicGuildgate.class));
        cards.add(new SetCardInfo("Simic Keyrune", 237, Rarity.UNCOMMON, mage.cards.s.SimicKeyrune.class));
        cards.add(new SetCardInfo("Simic Manipulator", 50, Rarity.RARE, mage.cards.s.SimicManipulator.class));
        cards.add(new SetCardInfo("Skarrg Goliath", 133, Rarity.RARE, mage.cards.s.SkarrgGoliath.class));
        cards.add(new SetCardInfo("Skarrg Guildmage", 196, Rarity.UNCOMMON, mage.cards.s.SkarrgGuildmage.class));
        cards.add(new SetCardInfo("Skinbrand Goblin", 105, Rarity.COMMON, mage.cards.s.SkinbrandGoblin.class));
        cards.add(new SetCardInfo("Skullcrack", 106, Rarity.UNCOMMON, mage.cards.s.Skullcrack.class));
        cards.add(new SetCardInfo("Skyblinder Staff", 238, Rarity.COMMON, mage.cards.s.SkyblinderStaff.class));
        cards.add(new SetCardInfo("Skygames", 51, Rarity.COMMON, mage.cards.s.Skygames.class));
        cards.add(new SetCardInfo("Skyknight Legionnaire", 197, Rarity.COMMON, mage.cards.s.SkyknightLegionnaire.class));
        cards.add(new SetCardInfo("Slate Street Ruffian", 78, Rarity.COMMON, mage.cards.s.SlateStreetRuffian.class));
        cards.add(new SetCardInfo("Slaughterhorn", 134, Rarity.COMMON, mage.cards.s.Slaughterhorn.class));
        cards.add(new SetCardInfo("Smite", 25, Rarity.COMMON, mage.cards.s.Smite.class));
        cards.add(new SetCardInfo("Smog Elemental", 79, Rarity.UNCOMMON, mage.cards.s.SmogElemental.class));
        cards.add(new SetCardInfo("Soul Ransom", 198, Rarity.RARE, mage.cards.s.SoulRansom.class));
        cards.add(new SetCardInfo("Spark Trooper", 199, Rarity.RARE, mage.cards.s.SparkTrooper.class));
        cards.add(new SetCardInfo("Spell Rupture", 52, Rarity.COMMON, mage.cards.s.SpellRupture.class));
        cards.add(new SetCardInfo("Spire Tracer", 135, Rarity.COMMON, mage.cards.s.SpireTracer.class));
        cards.add(new SetCardInfo("Stolen Identity", 53, Rarity.RARE, mage.cards.s.StolenIdentity.class));
        cards.add(new SetCardInfo("Stomping Ground", 247, Rarity.RARE, mage.cards.s.StompingGround.class));
        cards.add(new SetCardInfo("Structural Collapse", 107, Rarity.COMMON, mage.cards.s.StructuralCollapse.class));
        cards.add(new SetCardInfo("Sunhome Guildmage", 200, Rarity.UNCOMMON, mage.cards.s.SunhomeGuildmage.class));
        cards.add(new SetCardInfo("Sylvan Primordial", 136, Rarity.RARE, mage.cards.s.SylvanPrimordial.class));
        cards.add(new SetCardInfo("Syndicate Enforcer", 80, Rarity.COMMON, mage.cards.s.SyndicateEnforcer.class));
        cards.add(new SetCardInfo("Syndic of Tithes", 26, Rarity.COMMON, mage.cards.s.SyndicOfTithes.class));
        cards.add(new SetCardInfo("Thespian's Stage", 248, Rarity.RARE, mage.cards.t.ThespiansStage.class));
        cards.add(new SetCardInfo("Thrull Parasite", 81, Rarity.UNCOMMON, mage.cards.t.ThrullParasite.class));
        cards.add(new SetCardInfo("Tin Street Market", 108, Rarity.COMMON, mage.cards.t.TinStreetMarket.class));
        cards.add(new SetCardInfo("Totally Lost", 54, Rarity.COMMON, mage.cards.t.TotallyLost.class));
        cards.add(new SetCardInfo("Tower Defense", 137, Rarity.UNCOMMON, mage.cards.t.TowerDefense.class));
        cards.add(new SetCardInfo("Towering Thunderfist", 109, Rarity.COMMON, mage.cards.t.ToweringThunderfist.class));
        cards.add(new SetCardInfo("Treasury Thrull", 201, Rarity.RARE, mage.cards.t.TreasuryThrull.class));
        cards.add(new SetCardInfo("Truefire Paladin", 202, Rarity.UNCOMMON, mage.cards.t.TruefirePaladin.class));
        cards.add(new SetCardInfo("Undercity Informer", 82, Rarity.UNCOMMON, mage.cards.u.UndercityInformer.class));
        cards.add(new SetCardInfo("Undercity Plague", 83, Rarity.RARE, mage.cards.u.UndercityPlague.class));
        cards.add(new SetCardInfo("Unexpected Results", 203, Rarity.RARE, mage.cards.u.UnexpectedResults.class));
        cards.add(new SetCardInfo("Urban Evolution", 204, Rarity.UNCOMMON, mage.cards.u.UrbanEvolution.class));
        cards.add(new SetCardInfo("Urbis Protector", 27, Rarity.UNCOMMON, mage.cards.u.UrbisProtector.class));
        cards.add(new SetCardInfo("Verdant Haven", 138, Rarity.COMMON, mage.cards.v.VerdantHaven.class));
        cards.add(new SetCardInfo("Viashino Shanktail", 110, Rarity.UNCOMMON, mage.cards.v.ViashinoShanktail.class));
        cards.add(new SetCardInfo("Vizkopa Confessor", 205, Rarity.UNCOMMON, mage.cards.v.VizkopaConfessor.class));
        cards.add(new SetCardInfo("Vizkopa Guildmage", 206, Rarity.UNCOMMON, mage.cards.v.VizkopaGuildmage.class));
        cards.add(new SetCardInfo("Voidwalk", 55, Rarity.UNCOMMON, mage.cards.v.Voidwalk.class));
        cards.add(new SetCardInfo("Warmind Infantry", 111, Rarity.COMMON, mage.cards.w.WarmindInfantry.class));
        cards.add(new SetCardInfo("Wasteland Viper", 139, Rarity.UNCOMMON, mage.cards.w.WastelandViper.class));
        cards.add(new SetCardInfo("Watery Grave", 249, Rarity.RARE, mage.cards.w.WateryGrave.class));
        cards.add(new SetCardInfo("Way of the Thief", 56, Rarity.COMMON, mage.cards.w.WayOfTheThief.class));
        cards.add(new SetCardInfo("Whispering Madness", 207, Rarity.RARE, mage.cards.w.WhisperingMadness.class));
        cards.add(new SetCardInfo("Wight of Precinct Six", 84, Rarity.UNCOMMON, mage.cards.w.WightOfPrecinctSix.class));
        cards.add(new SetCardInfo("Wildwood Rebirth", 140, Rarity.COMMON, mage.cards.w.WildwoodRebirth.class));
        cards.add(new SetCardInfo("Wojek Halberdiers", 208, Rarity.COMMON, mage.cards.w.WojekHalberdiers.class));
        cards.add(new SetCardInfo("Wrecking Ogre", 112, Rarity.RARE, mage.cards.w.WreckingOgre.class));
        cards.add(new SetCardInfo("Zameck Guildmage", 209, Rarity.UNCOMMON, mage.cards.z.ZameckGuildmage.class));
        cards.add(new SetCardInfo("Zarichi Tiger", 28, Rarity.COMMON, mage.cards.z.ZarichiTiger.class));
        cards.add(new SetCardInfo("Zhur-Taa Swine", 210, Rarity.COMMON, mage.cards.z.ZhurTaaSwine.class));
    }

    @Override
    public BoosterCollator createCollator() {
        return new GatecrashCollator();
    }
}

// Booster collation info from https://www.lethe.xyz/mtg/collation/gtc.html
// Using USA collation for all rarities
// Foil rare sheet used for regular rares as regular rare sheet is not known
class GatecrashCollator implements BoosterCollator {
    private final CardRun commonA = new CardRun(true, "109", "58", "56", "135", "26", "19", "67", "118", "48", "107", "2", "76", "131", "41", "1", "58", "118", "100", "42", "2", "63", "129", "104", "41", "26", "76", "116", "111", "42", "25", "134", "107", "67", "35", "131", "9", "109", "37", "122", "63", "108", "32", "1", "135", "77", "111", "48", "25", "116", "37", "100", "19", "59", "122", "56", "108", "11", "32", "129", "59", "104", "9", "35", "77", "134", "11");
    private final CardRun commonB = new CardRun(true, "157", "175", "189", "187", "173", "172", "197", "186", "210", "102", "173", "223", "189", "225", "157", "190", "192", "186", "208", "172", "66", "161", "187", "218", "175", "193", "212", "210", "179", "197", "214", "66", "102", "161", "186", "157", "223", "197", "212", "218", "172", "192", "225", "173", "179", "193", "187", "208", "102", "190", "189", "214", "210", "175", "66", "190", "193", "225", "218", "223", "161", "214", "208", "179", "192", "212");
    private final CardRun commonC1 = new CardRun(true, "73", "40", "241", "127", "52", "78", "89", "243", "140", "92", "60", "226", "80", "115", "5", "52", "246", "28", "86", "8", "140", "241", "234", "73", "92", "28", "239", "51", "60", "115", "234", "244", "54", "4", "238", "105", "40", "243", "8", "127", "226", "54", "239", "4", "113", "105", "80", "246", "5", "89", "51", "78", "244", "113", "86");
    private final CardRun commonC2 = new CardRun(true, "22", "138", "68", "98", "125", "15", "31", "232", "85", "120", "22", "46", "235", "98", "62", "57", "15", "93", "236", "125", "31", "68", "24", "235", "93", "39", "62", "232", "138", "46", "85", "57", "24", "236", "120", "39", "68", "15", "235", "31", "93", "120", "232", "24", "85", "236", "138", "39", "22", "98", "57", "125", "62", "46", "238");
    private final CardRun uncommonA = new CardRun(true, "154", "166", "183", "160", "211", "217", "167", "30", "183", "230", "154", "227", "209", "219", "196", "217", "200", "160", "130", "206", "166", "155", "148", "181", "205", "167", "149", "211", "221", "130", "30", "206", "217", "209", "230", "211", "205", "155", "160", "196", "227", "183", "149", "181", "166", "200", "30", "219", "154", "221", "167", "148", "130", "219", "181", "227", "155", "230", "206", "221", "200", "149", "196", "205", "209", "148");
    private final CardRun uncommonB = new CardRun(true, "114", "17", "44", "103", "64", "124", "23", "36", "106", "137", "10", "29", "81", "103", "21", "124", "47", "106", "23", "29", "69", "132", "99", "10", "55", "103", "72", "137", "47", "21", "88", "64", "132", "55", "17", "72", "90", "124", "36", "99", "69", "81", "23", "44", "88", "114", "29", "72", "99", "17", "137", "44", "64", "90", "21", "36", "69", "114", "47", "88", "106", "10", "132", "81", "55", "90");
    private final CardRun uncommonC = new CardRun(true, "150", "169", "27", "145", "164", "237", "185", "216", "228", "202", "195", "185", "169", "158", "202", "237", "150", "168", "228", "164", "204", "233", "70", "216", "145", "184", "195", "96", "233", "169", "228", "184", "237", "70", "96", "150", "216", "158", "164", "195", "70", "27", "185", "168", "145", "202", "204", "27", "96", "233", "168", "158", "184", "204");
    private final CardRun uncommonD = new CardRun(true, "110", "84", "117", "38", "18", "87", "14", "139", "79", "95", "126", "16", "119", "49", "82", "110", "7", "84", "38", "119", "87", "16", "65", "49", "126", "79", "7", "139", "43", "95", "84", "14", "79", "119", "110", "38", "7", "82", "117", "95", "18", "65", "126", "87", "43", "82", "16", "117", "18", "49", "139", "65", "43", "14");
    private final CardRun rare = new CardRun(true, "3", "75", "153", "203", "45", "178", "20", "177", "220", "74", "207", "12", "213", "33", "198", "231", "194", "199", "53", "152", "128", "180", "71", "215", "162", "248", "152", "194", "97", "224", "174", "249", "222", "123", "191", "3", "121", "203", "178", "74", "146", "91", "142", "248", "112", "165", "201", "123", "163", "91", "151", "50", "170", "207", "247", "229", "12", "142", "249", "191", "94", "159", "6", "220", "83", "176", "245", "97", "242", "188", "133", "201", "53", "240", "75", "171", "156", "112", "141", "182", "50", "177", "146", "245", "143", "136", "222", "61", "163", "215", "170", "133", "20", "165", "33", "141", "34", "240", "199", "147", "13", "144", "101", "242", "213", "136", "151", "162", "45", "229", "83", "171", "247", "224", "101", "128", "6", "198", "61", "180", "231");
    private final CardRun land = new CardRun(false, "RTR_250", "RTR_251", "RTR_252", "RTR_253", "RTR_254", "RTR_255", "RTR_256", "RTR_257", "RTR_258", "RTR_259", "RTR_260", "RTR_261", "RTR_262", "RTR_263", "RTR_264", "RTR_265", "RTR_266", "RTR_267", "RTR_268", "RTR_269", "RTR_270", "RTR_271", "RTR_272", "RTR_273", "RTR_274");

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
    private final BoosterStructure ABC = new BoosterStructure(uncommonA, uncommonB, uncommonC);
    private final BoosterStructure ABD = new BoosterStructure(uncommonA, uncommonB, uncommonD);
    private final BoosterStructure ACD = new BoosterStructure(uncommonA, uncommonC, uncommonD);
    private final BoosterStructure BCD = new BoosterStructure(uncommonB, uncommonC, uncommonD);
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
    // In order for equal numbers of each uncommon to exist, the average booster must contain:
    // 0.825 A uncommons (33 / 40)
    // 0.825 B uncommons (33 / 40)
    // 0.675 C uncommons (27 / 40)
    // 0.675 D uncommons (27 / 40)
    // These numbers are the same for all sets with 80 uncommons in asymmetrical A/B/C/D print runs
    private final RarityConfiguration uncommonRuns = new RarityConfiguration(
            ABC, ABD, ABC, ABD, ABC, ABD, ABC, ABD, ABC, ABD, ABC, ABD, ABC,
            ABD, ABC, ABD, ABC, ABD, ABC, ABD, ABC, ABD, ABC, ABD, ABC, ABD,
            ACD, BCD, ACD, BCD, ACD, BCD, ACD,
            BCD, ACD, BCD, ACD, BCD, ACD, BCD
    );
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
