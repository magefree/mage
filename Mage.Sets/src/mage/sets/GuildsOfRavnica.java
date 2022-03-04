package mage.sets;

import mage.cards.ExpansionSet;
import mage.collation.BoosterCollator;
import mage.collation.BoosterStructure;
import mage.collation.CardRun;
import mage.collation.RarityConfiguration;
import mage.cards.repository.CardInfo;
import mage.constants.Rarity;
import mage.constants.SetType;

import java.util.ArrayList;
import java.util.List;

public final class GuildsOfRavnica extends ExpansionSet {

    private static final GuildsOfRavnica instance = new GuildsOfRavnica();

    public static GuildsOfRavnica getInstance() {
        return instance;
    }

    private GuildsOfRavnica() {
        super("Guilds of Ravnica", "GRN", ExpansionSet.buildDate(2018, 10, 5), SetType.EXPANSION);
        this.blockName = "Guilds of Ravnica";
        this.hasBoosters = true;
        this.numBoosterLands = 1;
        this.numBoosterCommon = 10;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 8;
        this.ratioBoosterSpecialLand = 1; // replace all basic lands
        this.maxCardNumberInBooster = 259;

        cards.add(new SetCardInfo("Affectionate Indrik", 121, Rarity.UNCOMMON, mage.cards.a.AffectionateIndrik.class));
        cards.add(new SetCardInfo("Arboretum Elemental", 122, Rarity.UNCOMMON, mage.cards.a.ArboretumElemental.class));
        cards.add(new SetCardInfo("Arclight Phoenix", 91, Rarity.MYTHIC, mage.cards.a.ArclightPhoenix.class));
        cards.add(new SetCardInfo("Artful Takedown", 151, Rarity.COMMON, mage.cards.a.ArtfulTakedown.class));
        cards.add(new SetCardInfo("Assassin's Trophy", 152, Rarity.RARE, mage.cards.a.AssassinsTrophy.class));
        cards.add(new SetCardInfo("Assure // Assemble", 221, Rarity.RARE, mage.cards.a.AssureAssemble.class));
        cards.add(new SetCardInfo("Attendant of Vraska", 271, Rarity.UNCOMMON, mage.cards.a.AttendantOfVraska.class));
        cards.add(new SetCardInfo("Aurelia, Exemplar of Justice", 153, Rarity.MYTHIC, mage.cards.a.AureliaExemplarOfJustice.class));
        cards.add(new SetCardInfo("Barging Sergeant", 92, Rarity.COMMON, mage.cards.b.BargingSergeant.class));
        cards.add(new SetCardInfo("Barrier of Bones", 61, Rarity.COMMON, mage.cards.b.BarrierOfBones.class));
        cards.add(new SetCardInfo("Bartizan Bats", 62, Rarity.COMMON, mage.cards.b.BartizanBats.class));
        cards.add(new SetCardInfo("Beacon Bolt", 154, Rarity.UNCOMMON, mage.cards.b.BeaconBolt.class));
        cards.add(new SetCardInfo("Beamsplitter Mage", 155, Rarity.UNCOMMON, mage.cards.b.BeamsplitterMage.class));
        cards.add(new SetCardInfo("Beast Whisperer", 123, Rarity.RARE, mage.cards.b.BeastWhisperer.class));
        cards.add(new SetCardInfo("Blade Instructor", 1, Rarity.COMMON, mage.cards.b.BladeInstructor.class));
        cards.add(new SetCardInfo("Blood Operative", 63, Rarity.RARE, mage.cards.b.BloodOperative.class));
        cards.add(new SetCardInfo("Book Devourer", 93, Rarity.UNCOMMON, mage.cards.b.BookDevourer.class));
        cards.add(new SetCardInfo("Boros Challenger", 156, Rarity.UNCOMMON, mage.cards.b.BorosChallenger.class));
        cards.add(new SetCardInfo("Boros Guildgate", 243, Rarity.COMMON, mage.cards.b.BorosGuildgate.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Boros Guildgate", 244, Rarity.COMMON, mage.cards.b.BorosGuildgate.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Boros Locket", 231, Rarity.COMMON, mage.cards.b.BorosLocket.class));
        cards.add(new SetCardInfo("Bounty Agent", 2, Rarity.RARE, mage.cards.b.BountyAgent.class));
        cards.add(new SetCardInfo("Bounty of Might", 124, Rarity.RARE, mage.cards.b.BountyOfMight.class));
        cards.add(new SetCardInfo("Burglar Rat", 64, Rarity.COMMON, mage.cards.b.BurglarRat.class));
        cards.add(new SetCardInfo("Camaraderie", 157, Rarity.RARE, mage.cards.c.Camaraderie.class));
        cards.add(new SetCardInfo("Candlelight Vigil", 3, Rarity.COMMON, mage.cards.c.CandlelightVigil.class));
        cards.add(new SetCardInfo("Capture Sphere", 31, Rarity.COMMON, mage.cards.c.CaptureSphere.class));
        cards.add(new SetCardInfo("Centaur Peacemaker", 158, Rarity.COMMON, mage.cards.c.CentaurPeacemaker.class));
        cards.add(new SetCardInfo("Chamber Sentry", 232, Rarity.RARE, mage.cards.c.ChamberSentry.class));
        cards.add(new SetCardInfo("Chance for Glory", 159, Rarity.MYTHIC, mage.cards.c.ChanceForGlory.class));
        cards.add(new SetCardInfo("Charnel Troll", 160, Rarity.RARE, mage.cards.c.CharnelTroll.class));
        cards.add(new SetCardInfo("Chemister's Insight", 32, Rarity.UNCOMMON, mage.cards.c.ChemistersInsight.class));
        cards.add(new SetCardInfo("Child of Night", 65, Rarity.COMMON, mage.cards.c.ChildOfNight.class));
        cards.add(new SetCardInfo("Chromatic Lantern", 233, Rarity.RARE, mage.cards.c.ChromaticLantern.class));
        cards.add(new SetCardInfo("Circuitous Route", 125, Rarity.UNCOMMON, mage.cards.c.CircuitousRoute.class));
        cards.add(new SetCardInfo("Citywatch Sphinx", 33, Rarity.UNCOMMON, mage.cards.c.CitywatchSphinx.class));
        cards.add(new SetCardInfo("Citywide Bust", 4, Rarity.RARE, mage.cards.c.CitywideBust.class));
        cards.add(new SetCardInfo("Collar the Culprit", 5, Rarity.COMMON, mage.cards.c.CollarTheCulprit.class));
        cards.add(new SetCardInfo("Command the Storm", 94, Rarity.COMMON, mage.cards.c.CommandTheStorm.class));
        cards.add(new SetCardInfo("Conclave Cavalier", 161, Rarity.UNCOMMON, mage.cards.c.ConclaveCavalier.class));
        cards.add(new SetCardInfo("Conclave Guildmage", 162, Rarity.UNCOMMON, mage.cards.c.ConclaveGuildmage.class));
        cards.add(new SetCardInfo("Conclave Tribunal", 6, Rarity.UNCOMMON, mage.cards.c.ConclaveTribunal.class));
        cards.add(new SetCardInfo("Connive // Concoct", 222, Rarity.RARE, mage.cards.c.ConniveConcoct.class));
        cards.add(new SetCardInfo("Cosmotronic Wave", 95, Rarity.COMMON, mage.cards.c.CosmotronicWave.class));
        cards.add(new SetCardInfo("Crackling Drake", 163, Rarity.UNCOMMON, mage.cards.c.CracklingDrake.class));
        cards.add(new SetCardInfo("Creeping Chill", 66, Rarity.UNCOMMON, mage.cards.c.CreepingChill.class));
        cards.add(new SetCardInfo("Crush Contraband", 7, Rarity.UNCOMMON, mage.cards.c.CrushContraband.class));
        cards.add(new SetCardInfo("Crushing Canopy", 126, Rarity.COMMON, mage.cards.c.CrushingCanopy.class));
        cards.add(new SetCardInfo("Darkblade Agent", 164, Rarity.COMMON, mage.cards.d.DarkbladeAgent.class));
        cards.add(new SetCardInfo("Dawn of Hope", 8, Rarity.RARE, mage.cards.d.DawnOfHope.class));
        cards.add(new SetCardInfo("Dazzling Lights", 34, Rarity.COMMON, mage.cards.d.DazzlingLights.class));
        cards.add(new SetCardInfo("Dead Weight", 67, Rarity.COMMON, mage.cards.d.DeadWeight.class));
        cards.add(new SetCardInfo("Deadly Visit", 68, Rarity.COMMON, mage.cards.d.DeadlyVisit.class));
        cards.add(new SetCardInfo("Deafening Clarion", 165, Rarity.RARE, mage.cards.d.DeafeningClarion.class));
        cards.add(new SetCardInfo("Demotion", 9, Rarity.UNCOMMON, mage.cards.d.Demotion.class));
        cards.add(new SetCardInfo("Devious Cover-Up", 35, Rarity.COMMON, mage.cards.d.DeviousCoverUp.class));
        cards.add(new SetCardInfo("Devkarin Dissident", 127, Rarity.COMMON, mage.cards.d.DevkarinDissident.class));
        cards.add(new SetCardInfo("Dimir Guildgate", 245, Rarity.COMMON, mage.cards.d.DimirGuildgate.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dimir Guildgate", 246, Rarity.COMMON, mage.cards.d.DimirGuildgate.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dimir Informant", 36, Rarity.COMMON, mage.cards.d.DimirInformant.class));
        cards.add(new SetCardInfo("Dimir Locket", 234, Rarity.COMMON, mage.cards.d.DimirLocket.class));
        cards.add(new SetCardInfo("Dimir Spybug", 166, Rarity.UNCOMMON, mage.cards.d.DimirSpybug.class));
        cards.add(new SetCardInfo("Direct Current", 96, Rarity.COMMON, mage.cards.d.DirectCurrent.class));
        cards.add(new SetCardInfo("Discovery // Dispersal", 223, Rarity.UNCOMMON, mage.cards.d.DiscoveryDispersal.class));
        cards.add(new SetCardInfo("Disdainful Stroke", 37, Rarity.COMMON, mage.cards.d.DisdainfulStroke.class));
        cards.add(new SetCardInfo("Disinformation Campaign", 167, Rarity.UNCOMMON, mage.cards.d.DisinformationCampaign.class));
        cards.add(new SetCardInfo("District Guide", 128, Rarity.UNCOMMON, mage.cards.d.DistrictGuide.class));
        cards.add(new SetCardInfo("Divine Visitation", 10, Rarity.MYTHIC, mage.cards.d.DivineVisitation.class));
        cards.add(new SetCardInfo("Doom Whisperer", 69, Rarity.MYTHIC, mage.cards.d.DoomWhisperer.class));
        cards.add(new SetCardInfo("Douser of Lights", 70, Rarity.COMMON, mage.cards.d.DouserOfLights.class));
        cards.add(new SetCardInfo("Dream Eater", 38, Rarity.MYTHIC, mage.cards.d.DreamEater.class));
        cards.add(new SetCardInfo("Drowned Secrets", 39, Rarity.RARE, mage.cards.d.DrownedSecrets.class));
        cards.add(new SetCardInfo("Electrostatic Field", 97, Rarity.UNCOMMON, mage.cards.e.ElectrostaticField.class));
        cards.add(new SetCardInfo("Emmara, Soul of the Accord", 168, Rarity.RARE, mage.cards.e.EmmaraSoulOfTheAccord.class));
        cards.add(new SetCardInfo("Enhanced Surveillance", 40, Rarity.UNCOMMON, mage.cards.e.EnhancedSurveillance.class));
        cards.add(new SetCardInfo("Erratic Cyclops", 98, Rarity.RARE, mage.cards.e.ErraticCyclops.class));
        cards.add(new SetCardInfo("Erstwhile Trooper", 169, Rarity.COMMON, mage.cards.e.ErstwhileTrooper.class));
        cards.add(new SetCardInfo("Etrata, the Silencer", 170, Rarity.RARE, mage.cards.e.EtrataTheSilencer.class));
        cards.add(new SetCardInfo("Expansion // Explosion", 224, Rarity.RARE, mage.cards.e.ExpansionExplosion.class));
        cards.add(new SetCardInfo("Experimental Frenzy", 99, Rarity.RARE, mage.cards.e.ExperimentalFrenzy.class));
        cards.add(new SetCardInfo("Fearless Halberdier", 100, Rarity.COMMON, mage.cards.f.FearlessHalberdier.class));
        cards.add(new SetCardInfo("Find // Finality", 225, Rarity.RARE, mage.cards.f.FindFinality.class));
        cards.add(new SetCardInfo("Fire Urchin", 101, Rarity.COMMON, mage.cards.f.FireUrchin.class));
        cards.add(new SetCardInfo("Firemind's Research", 171, Rarity.RARE, mage.cards.f.FiremindsResearch.class));
        cards.add(new SetCardInfo("Flight of Equenauts", 11, Rarity.UNCOMMON, mage.cards.f.FlightOfEquenauts.class));
        cards.add(new SetCardInfo("Flower // Flourish", 226, Rarity.UNCOMMON, mage.cards.f.FlowerFlourish.class));
        cards.add(new SetCardInfo("Forest", 264, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Fresh-Faced Recruit", 216, Rarity.COMMON, mage.cards.f.FreshFacedRecruit.class));
        cards.add(new SetCardInfo("Garrison Sergeant", 172, Rarity.COMMON, mage.cards.g.GarrisonSergeant.class));
        cards.add(new SetCardInfo("Gatekeeper Gargoyle", 235, Rarity.UNCOMMON, mage.cards.g.GatekeeperGargoyle.class));
        cards.add(new SetCardInfo("Gateway Plaza", 247, Rarity.COMMON, mage.cards.g.GatewayPlaza.class));
        cards.add(new SetCardInfo("Generous Stray", 129, Rarity.COMMON, mage.cards.g.GenerousStray.class));
        cards.add(new SetCardInfo("Gird for Battle", 12, Rarity.UNCOMMON, mage.cards.g.GirdForBattle.class));
        cards.add(new SetCardInfo("Glaive of the Guildpact", 236, Rarity.UNCOMMON, mage.cards.g.GlaiveOfTheGuildpact.class));
        cards.add(new SetCardInfo("Glowspore Shaman", 173, Rarity.UNCOMMON, mage.cards.g.GlowsporeShaman.class));
        cards.add(new SetCardInfo("Goblin Banneret", 102, Rarity.UNCOMMON, mage.cards.g.GoblinBanneret.class));
        cards.add(new SetCardInfo("Goblin Cratermaker", 103, Rarity.UNCOMMON, mage.cards.g.GoblinCratermaker.class));
        cards.add(new SetCardInfo("Goblin Electromancer", 174, Rarity.COMMON, mage.cards.g.GoblinElectromancer.class));
        cards.add(new SetCardInfo("Goblin Locksmith", 104, Rarity.COMMON, mage.cards.g.GoblinLocksmith.class));
        cards.add(new SetCardInfo("Golgari Findbroker", 175, Rarity.UNCOMMON, mage.cards.g.GolgariFindbroker.class));
        cards.add(new SetCardInfo("Golgari Guildgate", 248, Rarity.COMMON, mage.cards.g.GolgariGuildgate.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Golgari Guildgate", 249, Rarity.COMMON, mage.cards.g.GolgariGuildgate.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Golgari Locket", 237, Rarity.COMMON, mage.cards.g.GolgariLocket.class));
        cards.add(new SetCardInfo("Golgari Raiders", 130, Rarity.UNCOMMON, mage.cards.g.GolgariRaiders.class));
        cards.add(new SetCardInfo("Grappling Sundew", 131, Rarity.UNCOMMON, mage.cards.g.GrapplingSundew.class));
        cards.add(new SetCardInfo("Gravitic Punch", 105, Rarity.COMMON, mage.cards.g.GraviticPunch.class));
        cards.add(new SetCardInfo("Gruesome Menagerie", 71, Rarity.RARE, mage.cards.g.GruesomeMenagerie.class));
        cards.add(new SetCardInfo("Guild Summit", 41, Rarity.UNCOMMON, mage.cards.g.GuildSummit.class));
        cards.add(new SetCardInfo("Guildmages' Forum", 250, Rarity.RARE, mage.cards.g.GuildmagesForum.class));
        cards.add(new SetCardInfo("Haazda Marshal", 13, Rarity.UNCOMMON, mage.cards.h.HaazdaMarshal.class));
        cards.add(new SetCardInfo("Hammer Dropper", 176, Rarity.COMMON, mage.cards.h.HammerDropper.class));
        cards.add(new SetCardInfo("Hatchery Spider", 132, Rarity.RARE, mage.cards.h.HatcherySpider.class));
        cards.add(new SetCardInfo("Healer's Hawk", 14, Rarity.COMMON, mage.cards.h.HealersHawk.class));
        cards.add(new SetCardInfo("Hellkite Whelp", 106, Rarity.UNCOMMON, mage.cards.h.HellkiteWhelp.class));
        cards.add(new SetCardInfo("Hired Poisoner", 72, Rarity.COMMON, mage.cards.h.HiredPoisoner.class));
        cards.add(new SetCardInfo("Hitchclaw Recluse", 133, Rarity.COMMON, mage.cards.h.HitchclawRecluse.class));
        cards.add(new SetCardInfo("House Guildmage", 177, Rarity.UNCOMMON, mage.cards.h.HouseGuildmage.class));
        cards.add(new SetCardInfo("Hunted Witness", 15, Rarity.COMMON, mage.cards.h.HuntedWitness.class));
        cards.add(new SetCardInfo("Hypothesizzle", 178, Rarity.COMMON, mage.cards.h.Hypothesizzle.class));
        cards.add(new SetCardInfo("Impervious Greatwurm", 273, Rarity.MYTHIC, mage.cards.i.ImperviousGreatwurm.class));
        cards.add(new SetCardInfo("Inescapable Blaze", 107, Rarity.UNCOMMON, mage.cards.i.InescapableBlaze.class));
        cards.add(new SetCardInfo("Invert // Invent", 228, Rarity.UNCOMMON, mage.cards.i.InvertInvent.class));
        cards.add(new SetCardInfo("Inspiring Unicorn", 16, Rarity.UNCOMMON, mage.cards.i.InspiringUnicorn.class));
        cards.add(new SetCardInfo("Integrity // Intervention", 227, Rarity.UNCOMMON, mage.cards.i.IntegrityIntervention.class));
        cards.add(new SetCardInfo("Intrusive Packbeast", 17, Rarity.COMMON, mage.cards.i.IntrusivePackbeast.class));
        cards.add(new SetCardInfo("Ionize", 179, Rarity.RARE, mage.cards.i.Ionize.class));
        cards.add(new SetCardInfo("Ironshell Beetle", 134, Rarity.COMMON, mage.cards.i.IronshellBeetle.class));
        cards.add(new SetCardInfo("Island", 261, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Izoni, Thousand-Eyed", 180, Rarity.RARE, mage.cards.i.IzoniThousandEyed.class));
        cards.add(new SetCardInfo("Izzet Guildgate", 251, Rarity.COMMON, mage.cards.i.IzzetGuildgate.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Izzet Guildgate", 252, Rarity.COMMON, mage.cards.i.IzzetGuildgate.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Izzet Locket", 238, Rarity.COMMON, mage.cards.i.IzzetLocket.class));
        cards.add(new SetCardInfo("Join Shields", 181, Rarity.UNCOMMON, mage.cards.j.JoinShields.class));
        cards.add(new SetCardInfo("Justice Strike", 182, Rarity.UNCOMMON, mage.cards.j.JusticeStrike.class));
        cards.add(new SetCardInfo("Knight of Autumn", 183, Rarity.RARE, mage.cards.k.KnightOfAutumn.class));
        cards.add(new SetCardInfo("Kraul Foragers", 135, Rarity.COMMON, mage.cards.k.KraulForagers.class));
        cards.add(new SetCardInfo("Kraul Harpooner", 136, Rarity.UNCOMMON, mage.cards.k.KraulHarpooner.class));
        cards.add(new SetCardInfo("Kraul Raider", 270, Rarity.COMMON, mage.cards.k.KraulRaider.class));
        cards.add(new SetCardInfo("Kraul Swarm", 73, Rarity.UNCOMMON, mage.cards.k.KraulSwarm.class));
        cards.add(new SetCardInfo("Lava Coil", 108, Rarity.UNCOMMON, mage.cards.l.LavaCoil.class));
        cards.add(new SetCardInfo("Lazav, the Multifarious", 184, Rarity.MYTHIC, mage.cards.l.LazavTheMultifarious.class));
        cards.add(new SetCardInfo("League Guildmage", 185, Rarity.UNCOMMON, mage.cards.l.LeagueGuildmage.class));
        cards.add(new SetCardInfo("Leapfrog", 42, Rarity.COMMON, mage.cards.l.Leapfrog.class));
        cards.add(new SetCardInfo("Ledev Champion", 186, Rarity.UNCOMMON, mage.cards.l.LedevChampion.class));
        cards.add(new SetCardInfo("Ledev Guardian", 18, Rarity.COMMON, mage.cards.l.LedevGuardian.class));
        cards.add(new SetCardInfo("Legion Guildmage", 187, Rarity.UNCOMMON, mage.cards.l.LegionGuildmage.class));
        cards.add(new SetCardInfo("Legion Warboss", 109, Rarity.RARE, mage.cards.l.LegionWarboss.class));
        cards.add(new SetCardInfo("Light of the Legion", 19, Rarity.RARE, mage.cards.l.LightOfTheLegion.class));
        cards.add(new SetCardInfo("Lotleth Giant", 74, Rarity.UNCOMMON, mage.cards.l.LotlethGiant.class));
        cards.add(new SetCardInfo("Loxodon Restorer", 20, Rarity.COMMON, mage.cards.l.LoxodonRestorer.class));
        cards.add(new SetCardInfo("Luminous Bonds", 21, Rarity.COMMON, mage.cards.l.LuminousBonds.class));
        cards.add(new SetCardInfo("Maniacal Rage", 110, Rarity.COMMON, mage.cards.m.ManiacalRage.class));
        cards.add(new SetCardInfo("March of the Multitudes", 188, Rarity.MYTHIC, mage.cards.m.MarchOfTheMultitudes.class));
        cards.add(new SetCardInfo("Mausoleum Secrets", 75, Rarity.RARE, mage.cards.m.MausoleumSecrets.class));
        cards.add(new SetCardInfo("Maximize Altitude", 43, Rarity.COMMON, mage.cards.m.MaximizeAltitude.class));
        cards.add(new SetCardInfo("Maximize Velocity", 111, Rarity.COMMON, mage.cards.m.MaximizeVelocity.class));
        cards.add(new SetCardInfo("Mephitic Vapors", 76, Rarity.COMMON, mage.cards.m.MephiticVapors.class));
        cards.add(new SetCardInfo("Midnight Reaper", 77, Rarity.RARE, mage.cards.m.MidnightReaper.class));
        cards.add(new SetCardInfo("Might of the Masses", 137, Rarity.UNCOMMON, mage.cards.m.MightOfTheMasses.class));
        cards.add(new SetCardInfo("Mission Briefing", 44, Rarity.RARE, mage.cards.m.MissionBriefing.class));
        cards.add(new SetCardInfo("Mnemonic Betrayal", 189, Rarity.MYTHIC, mage.cards.m.MnemonicBetrayal.class));
        cards.add(new SetCardInfo("Molderhulk", 190, Rarity.UNCOMMON, mage.cards.m.Molderhulk.class));
        cards.add(new SetCardInfo("Moodmark Painter", 78, Rarity.COMMON, mage.cards.m.MoodmarkPainter.class));
        cards.add(new SetCardInfo("Mountain", 263, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Murmuring Mystic", 45, Rarity.UNCOMMON, mage.cards.m.MurmuringMystic.class));
        cards.add(new SetCardInfo("Muse Drake", 46, Rarity.COMMON, mage.cards.m.MuseDrake.class));
        cards.add(new SetCardInfo("Narcomoeba", 47, Rarity.RARE, mage.cards.n.Narcomoeba.class));
        cards.add(new SetCardInfo("Necrotic Wound", 79, Rarity.UNCOMMON, mage.cards.n.NecroticWound.class));
        cards.add(new SetCardInfo("Never Happened", 80, Rarity.COMMON, mage.cards.n.NeverHappened.class));
        cards.add(new SetCardInfo("Nightveil Predator", 191, Rarity.UNCOMMON, mage.cards.n.NightveilPredator.class));
        cards.add(new SetCardInfo("Nightveil Sprite", 48, Rarity.UNCOMMON, mage.cards.n.NightveilSprite.class));
        cards.add(new SetCardInfo("Niv-Mizzet, Parun", 192, Rarity.RARE, mage.cards.n.NivMizzetParun.class));
        cards.add(new SetCardInfo("Notion Rain", 193, Rarity.COMMON, mage.cards.n.NotionRain.class));
        cards.add(new SetCardInfo("Nullhide Ferox", 138, Rarity.MYTHIC, mage.cards.n.NullhideFerox.class));
        cards.add(new SetCardInfo("Ochran Assassin", 194, Rarity.UNCOMMON, mage.cards.o.OchranAssassin.class));
        cards.add(new SetCardInfo("Omnispell Adept", 49, Rarity.RARE, mage.cards.o.OmnispellAdept.class));
        cards.add(new SetCardInfo("Ornery Goblin", 112, Rarity.COMMON, mage.cards.o.OrneryGoblin.class));
        cards.add(new SetCardInfo("Overgrown Tomb", 253, Rarity.RARE, mage.cards.o.OvergrownTomb.class));
        cards.add(new SetCardInfo("Pack's Favor", 139, Rarity.COMMON, mage.cards.p.PacksFavor.class));
        cards.add(new SetCardInfo("Parhelion Patrol", 22, Rarity.COMMON, mage.cards.p.ParhelionPatrol.class));
        cards.add(new SetCardInfo("Passwall Adept", 50, Rarity.COMMON, mage.cards.p.PasswallAdept.class));
        cards.add(new SetCardInfo("Pause for Reflection", 140, Rarity.COMMON, mage.cards.p.PauseForReflection.class));
        cards.add(new SetCardInfo("Pelt Collector", 141, Rarity.RARE, mage.cards.p.PeltCollector.class));
        cards.add(new SetCardInfo("Pilfering Imp", 81, Rarity.UNCOMMON, mage.cards.p.PilferingImp.class));
        cards.add(new SetCardInfo("Piston-Fist Cyclops", 217, Rarity.COMMON, mage.cards.p.PistonFistCyclops.class));
        cards.add(new SetCardInfo("Pitiless Gorgon", 218, Rarity.COMMON, mage.cards.p.PitilessGorgon.class));
        cards.add(new SetCardInfo("Plaguecrafter", 82, Rarity.UNCOMMON, mage.cards.p.Plaguecrafter.class));
        cards.add(new SetCardInfo("Plains", 260, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Portcullis Vine", 142, Rarity.COMMON, mage.cards.p.PortcullisVine.class));
        cards.add(new SetCardInfo("Precision Bolt", 267, Rarity.COMMON, mage.cards.p.PrecisionBolt.class));
        cards.add(new SetCardInfo("Prey Upon", 143, Rarity.COMMON, mage.cards.p.PreyUpon.class));
        cards.add(new SetCardInfo("Price of Fame", 83, Rarity.UNCOMMON, mage.cards.p.PriceOfFame.class));
        cards.add(new SetCardInfo("Quasiduplicate", 51, Rarity.RARE, mage.cards.q.Quasiduplicate.class));
        cards.add(new SetCardInfo("Radical Idea", 52, Rarity.COMMON, mage.cards.r.RadicalIdea.class));
        cards.add(new SetCardInfo("Ral's Dispersal", 266, Rarity.RARE, mage.cards.r.RalsDispersal.class));
        cards.add(new SetCardInfo("Ral's Staticaster", 268, Rarity.UNCOMMON, mage.cards.r.RalsStaticaster.class));
        cards.add(new SetCardInfo("Ral, Caller of Storms", 265, Rarity.MYTHIC, mage.cards.r.RalCallerOfStorms.class));
        cards.add(new SetCardInfo("Ral, Izzet Viceroy", 195, Rarity.MYTHIC, mage.cards.r.RalIzzetViceroy.class));
        cards.add(new SetCardInfo("Rampaging Monument", 239, Rarity.UNCOMMON, mage.cards.r.RampagingMonument.class));
        cards.add(new SetCardInfo("Response // Resurgence", 229, Rarity.RARE, mage.cards.r.ResponseResurgence.class));
        cards.add(new SetCardInfo("Rhizome Lurcher", 196, Rarity.COMMON, mage.cards.r.RhizomeLurcher.class));
        cards.add(new SetCardInfo("Righteous Blow", 23, Rarity.COMMON, mage.cards.r.RighteousBlow.class));
        cards.add(new SetCardInfo("Risk Factor", 113, Rarity.RARE, mage.cards.r.RiskFactor.class));
        cards.add(new SetCardInfo("Ritual of Soot", 84, Rarity.RARE, mage.cards.r.RitualOfSoot.class));
        cards.add(new SetCardInfo("Roc Charger", 24, Rarity.UNCOMMON, mage.cards.r.RocCharger.class));
        cards.add(new SetCardInfo("Rosemane Centaur", 197, Rarity.COMMON, mage.cards.r.RosemaneCentaur.class));
        cards.add(new SetCardInfo("Rubblebelt Boar", 114, Rarity.COMMON, mage.cards.r.RubblebeltBoar.class));
        cards.add(new SetCardInfo("Runaway Steam-Kin", 115, Rarity.RARE, mage.cards.r.RunawaySteamKin.class));
        cards.add(new SetCardInfo("Sacred Foundry", 254, Rarity.RARE, mage.cards.s.SacredFoundry.class));
        cards.add(new SetCardInfo("Selective Snare", 53, Rarity.UNCOMMON, mage.cards.s.SelectiveSnare.class));
        cards.add(new SetCardInfo("Selesnya Guildgate", 255, Rarity.COMMON, mage.cards.s.SelesnyaGuildgate.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Selesnya Guildgate", 256, Rarity.COMMON, mage.cards.s.SelesnyaGuildgate.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Selesnya Locket", 240, Rarity.COMMON, mage.cards.s.SelesnyaLocket.class));
        cards.add(new SetCardInfo("Severed Strands", 85, Rarity.COMMON, mage.cards.s.SeveredStrands.class));
        cards.add(new SetCardInfo("Siege Wurm", 144, Rarity.COMMON, mage.cards.s.SiegeWurm.class));
        cards.add(new SetCardInfo("Silent Dart", 241, Rarity.UNCOMMON, mage.cards.s.SilentDart.class));
        cards.add(new SetCardInfo("Sinister Sabotage", 54, Rarity.UNCOMMON, mage.cards.s.SinisterSabotage.class));
        cards.add(new SetCardInfo("Skyknight Legionnaire", 198, Rarity.COMMON, mage.cards.s.SkyknightLegionnaire.class));
        cards.add(new SetCardInfo("Skyline Scout", 25, Rarity.COMMON, mage.cards.s.SkylineScout.class));
        cards.add(new SetCardInfo("Smelt-Ward Minotaur", 116, Rarity.UNCOMMON, mage.cards.s.SmeltWardMinotaur.class));
        cards.add(new SetCardInfo("Sonic Assault", 199, Rarity.COMMON, mage.cards.s.SonicAssault.class));
        cards.add(new SetCardInfo("Spinal Centipede", 86, Rarity.COMMON, mage.cards.s.SpinalCentipede.class));
        cards.add(new SetCardInfo("Sprouting Renewal", 145, Rarity.UNCOMMON, mage.cards.s.SproutingRenewal.class));
        cards.add(new SetCardInfo("Status // Statue", 230, Rarity.UNCOMMON, mage.cards.s.StatusStatue.class));
        cards.add(new SetCardInfo("Steam Vents", 257, Rarity.RARE, mage.cards.s.SteamVents.class));
        cards.add(new SetCardInfo("Street Riot", 117, Rarity.UNCOMMON, mage.cards.s.StreetRiot.class));
        cards.add(new SetCardInfo("Sumala Woodshaper", 200, Rarity.COMMON, mage.cards.s.SumalaWoodshaper.class));
        cards.add(new SetCardInfo("Sunhome Stalwart", 26, Rarity.UNCOMMON, mage.cards.s.SunhomeStalwart.class));
        cards.add(new SetCardInfo("Sure Strike", 118, Rarity.COMMON, mage.cards.s.SureStrike.class));
        cards.add(new SetCardInfo("Swamp", 262, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swarm Guildmage", 201, Rarity.UNCOMMON, mage.cards.s.SwarmGuildmage.class));
        cards.add(new SetCardInfo("Swathcutter Giant", 202, Rarity.UNCOMMON, mage.cards.s.SwathcutterGiant.class));
        cards.add(new SetCardInfo("Swiftblade Vindicator", 203, Rarity.RARE, mage.cards.s.SwiftbladeVindicator.class));
        cards.add(new SetCardInfo("Sworn Companions", 27, Rarity.COMMON, mage.cards.s.SwornCompanions.class));
        cards.add(new SetCardInfo("Tajic, Legion's Edge", 204, Rarity.RARE, mage.cards.t.TajicLegionsEdge.class));
        cards.add(new SetCardInfo("Take Heart", 28, Rarity.COMMON, mage.cards.t.TakeHeart.class));
        cards.add(new SetCardInfo("Temple Garden", 258, Rarity.RARE, mage.cards.t.TempleGarden.class));
        cards.add(new SetCardInfo("Tenth District Guard", 29, Rarity.COMMON, mage.cards.t.TenthDistrictGuard.class));
        cards.add(new SetCardInfo("Thief of Sanity", 205, Rarity.RARE, mage.cards.t.ThiefOfSanity.class));
        cards.add(new SetCardInfo("Thought Erasure", 206, Rarity.UNCOMMON, mage.cards.t.ThoughtErasure.class));
        cards.add(new SetCardInfo("Thoughtbound Phantasm", 55, Rarity.UNCOMMON, mage.cards.t.ThoughtboundPhantasm.class));
        cards.add(new SetCardInfo("Thousand-Year Storm", 207, Rarity.MYTHIC, mage.cards.t.ThousandYearStorm.class));
        cards.add(new SetCardInfo("Torch Courier", 119, Rarity.COMMON, mage.cards.t.TorchCourier.class));
        cards.add(new SetCardInfo("Trostani Discordant", 208, Rarity.MYTHIC, mage.cards.t.TrostaniDiscordant.class));
        cards.add(new SetCardInfo("Truefire Captain", 209, Rarity.UNCOMMON, mage.cards.t.TruefireCaptain.class));
        cards.add(new SetCardInfo("Undercity Necrolisk", 87, Rarity.UNCOMMON, mage.cards.u.UndercityNecrolisk.class));
        cards.add(new SetCardInfo("Undercity Uprising", 210, Rarity.COMMON, mage.cards.u.UndercityUprising.class));
        cards.add(new SetCardInfo("Underrealm Lich", 211, Rarity.MYTHIC, mage.cards.u.UnderrealmLich.class));
        cards.add(new SetCardInfo("Unexplained Disappearance", 56, Rarity.COMMON, mage.cards.u.UnexplainedDisappearance.class));
        cards.add(new SetCardInfo("Unmoored Ego", 212, Rarity.RARE, mage.cards.u.UnmooredEgo.class));
        cards.add(new SetCardInfo("Urban Utopia", 146, Rarity.COMMON, mage.cards.u.UrbanUtopia.class));
        cards.add(new SetCardInfo("Vedalken Mesmerist", 57, Rarity.COMMON, mage.cards.v.VedalkenMesmerist.class));
        cards.add(new SetCardInfo("Veiled Shade", 88, Rarity.COMMON, mage.cards.v.VeiledShade.class));
        cards.add(new SetCardInfo("Venerated Loxodon", 30, Rarity.RARE, mage.cards.v.VeneratedLoxodon.class));
        cards.add(new SetCardInfo("Vernadi Shieldmate", 219, Rarity.COMMON, mage.cards.v.VernadiShieldmate.class));
        cards.add(new SetCardInfo("Vicious Rumors", 89, Rarity.COMMON, mage.cards.v.ViciousRumors.class));
        cards.add(new SetCardInfo("Vigorspore Wurm", 147, Rarity.COMMON, mage.cards.v.VigorsporeWurm.class));
        cards.add(new SetCardInfo("Vivid Revival", 148, Rarity.RARE, mage.cards.v.VividRevival.class));
        cards.add(new SetCardInfo("Vraska's Stoneglare", 272, Rarity.RARE, mage.cards.v.VraskasStoneglare.class));
        cards.add(new SetCardInfo("Vraska, Golgari Queen", 213, Rarity.MYTHIC, mage.cards.v.VraskaGolgariQueen.class));
        cards.add(new SetCardInfo("Vraska, Regal Gorgon", 269, Rarity.MYTHIC, mage.cards.v.VraskaRegalGorgon.class));
        cards.add(new SetCardInfo("Wall of Mist", 58, Rarity.COMMON, mage.cards.w.WallOfMist.class));
        cards.add(new SetCardInfo("Wand of Vertebrae", 242, Rarity.UNCOMMON, mage.cards.w.WandOfVertebrae.class));
        cards.add(new SetCardInfo("Wary Okapi", 149, Rarity.COMMON, mage.cards.w.WaryOkapi.class));
        cards.add(new SetCardInfo("Watcher in the Mist", 59, Rarity.COMMON, mage.cards.w.WatcherInTheMist.class));
        cards.add(new SetCardInfo("Watery Grave", 259, Rarity.RARE, mage.cards.w.WateryGrave.class));
        cards.add(new SetCardInfo("Wee Dragonauts", 214, Rarity.UNCOMMON, mage.cards.w.WeeDragonauts.class));
        cards.add(new SetCardInfo("Whisper Agent", 220, Rarity.COMMON, mage.cards.w.WhisperAgent.class));
        cards.add(new SetCardInfo("Whispering Snitch", 90, Rarity.UNCOMMON, mage.cards.w.WhisperingSnitch.class));
        cards.add(new SetCardInfo("Wild Ceratok", 150, Rarity.COMMON, mage.cards.w.WildCeratok.class));
        cards.add(new SetCardInfo("Wishcoin Crab", 60, Rarity.COMMON, mage.cards.w.WishcoinCrab.class));
        cards.add(new SetCardInfo("Wojek Bodyguard", 120, Rarity.COMMON, mage.cards.w.WojekBodyguard.class));
        cards.add(new SetCardInfo("Worldsoul Colossus", 215, Rarity.UNCOMMON, mage.cards.w.WorldsoulColossus.class));
    }

    @Override
    protected List<CardInfo> findSpecialCardsByRarity(Rarity rarity) {
        List<CardInfo> cardInfos = super.findSpecialCardsByRarity(rarity);
        if (rarity == Rarity.LAND) {
            // Gateway Plaza is a normal common
            cardInfos.removeIf(cardInfo -> "Gateway Plaza".equals(cardInfo.getName()));
        }
        return cardInfos;
    }

    @Override
    public BoosterCollator createCollator() {
        return new GuildsOfRavnicaCollator();
    }
}

// Booster collation info from https://www.lethe.xyz/mtg/collation/grn.html
// Using USA collation for all rarities
// Foil rare sheet used for regular rares as regular rare sheet is not known
class GuildsOfRavnicaCollator implements BoosterCollator {
    private final CardRun commonA = new CardRun(true, "14", "60", "118", "5", "34", "101", "18", "37", "92", "29", "42", "100", "27", "43", "119", "15", "56", "95", "17", "52", "104", "20", "60", "118", "23", "57", "112", "25", "58", "120", "28", "36", "114", "5", "50", "111", "14", "34", "101", "17", "43", "92", "29", "37", "119", "15", "57", "100", "23", "42", "104", "18", "56", "95", "27", "58", "120", "28", "52", "112", "20", "36", "114", "25", "50", "111");
    private final CardRun commonB = new CardRun(true, "126", "61", "129", "62", "127", "89", "147", "85", "150", "65", "146", "64", "149", "78", "133", "70", "139", "88", "135", "62", "142", "80", "126", "61", "127", "89", "129", "85", "150", "86", "147", "64", "139", "65", "146", "62", "142", "70", "135", "78", "149", "88", "147", "80", "133", "61", "127", "89", "126", "85", "129", "86", "150", "65", "149", "70", "146", "78", "139", "88", "135", "64", "142", "80", "133", "86");
    private final CardRun commonC1 = new CardRun(true, "105", "234", "158", "164", "35", "169", "198", "220", "3", "199", "196", "216", "76", "1", "94", "31", "237", "110", "72", "219", "178", "143", "176", "234", "21", "3", "217", "218", "216", "151", "200", "35", "196", "198", "164", "158", "220", "31", "169", "110", "72", "1", "199", "237", "94", "76", "21", "219", "217", "218", "176", "151", "200", "178", "143");
    private final CardRun commonC2 = new CardRun(true, "105", "59", "140", "197", "238", "68", "134", "22", "172", "46", "240", "67", "210", "96", "247", "174", "144", "231", "193", "140", "59", "240", "96", "238", "68", "134", "197", "172", "46", "247", "67", "210", "22", "231", "144", "174", "193", "240", "59", "140", "197", "172", "68", "238", "96", "134", "46", "247", "67", "210", "22", "231", "144", "174", "193");
    private final CardRun uncommonA = new CardRun(true, "235", "108", "190", "6", "136", "32", "155", "83", "227", "13", "130", "90", "191", "103", "209", "48", "226", "24", "45", "186", "41", "185", "9", "228", "79", "182", "116", "161", "236", "177", "48", "137", "24", "163", "235", "97", "154", "121", "201", "122", "181", "6", "130", "155", "108", "191", "87", "156", "136", "45", "185", "103", "190", "32", "186", "137", "209", "13", "163", "116", "227", "83", "226", "90", "121", "228", "41", "236", "154", "9", "161", "97", "177", "79", "181", "122", "186", "6", "201", "108", "155", "136", "182", "45", "156", "48", "227", "87", "103", "191", "130", "235", "190", "32", "185", "83", "209", "13", "116", "226", "90", "182", "24", "163", "137", "236", "154", "41", "228", "9", "161", "122", "201", "79", "177", "97", "181", "87", "121", "156");
    private final CardRun uncommonB = new CardRun(true, "167", "128", "53", "187", "107", "16", "206", "145", "242", "102", "173", "26", "125", "166", "66", "55", "230", "12", "175", "131", "214", "93", "40", "162", "239", "106", "223", "33", "7", "194", "81", "167", "107", "12", "55", "215", "16", "117", "74", "187", "54", "202", "82", "102", "206", "242", "33", "173", "11", "145", "53", "230", "241", "175", "73", "40", "93", "223", "128", "66", "106", "166", "125", "162", "7", "187", "131", "194", "54", "202", "239", "117", "26", "214", "81", "16", "74", "167", "107", "242", "55", "215", "82", "12", "241", "173", "145", "230", "53", "102", "206", "11", "131", "166", "73", "40", "125", "194", "239", "66", "54", "26", "162", "128", "223", "33", "106", "81", "214", "74", "175", "7", "93", "215", "82", "202", "117", "11", "241", "73");
    private final CardRun rare = new CardRun(true, "152", "63", "51", "2", "153", "123", "253", "39", "69", "171", "98", "221", "71", "171", "4", "159", "124", "152", "44", "232", "179", "99", "157", "75", "179", "8", "184", "132", "221", "47", "138", "180", "109", "160", "77", "180", "233", "188", "141", "157", "49", "254", "183", "113", "222", "84", "183", "258", "189", "148", "160", "63", "19", "192", "115", "165", "30", "192", "2", "195", "39", "222", "71", "91", "229", "257", "168", "98", "229", "4", "207", "44", "165", "75", "232", "203", "123", "170", "99", "203", "8", "208", "47", "168", "77", "38", "204", "124", "224", "109", "204", "233", "211", "49", "170", "84", "259", "205", "132", "225", "113", "205", "250", "213", "51", "224", "30", "253", "212", "141", "254", "115", "212", "258", "19", "257", "225", "259", "10", "250", "148");
    private final CardRun land = new CardRun(false, "243", "244", "245", "246", "248", "249", "251", "252", "255", "256");

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
            AAAABBBC2C2C2,
            AAAABBBC2C2C2,
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
