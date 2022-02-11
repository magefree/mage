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

public final class RavnicaAllegiance extends ExpansionSet {

    private static final RavnicaAllegiance instance = new RavnicaAllegiance();

    public static RavnicaAllegiance getInstance() {
        return instance;
    }

    private RavnicaAllegiance() {
        super("Ravnica Allegiance", "RNA", ExpansionSet.buildDate(2019, 1, 25), SetType.EXPANSION);
        this.blockName = "Guilds of Ravnica";
        this.hasBoosters = true;
        this.numBoosterLands = 1;
        this.numBoosterCommon = 10;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 8;
        this.ratioBoosterSpecialLand = 1; // replace all basic lands
        this.maxCardNumberInBooster = 259;

        cards.add(new SetCardInfo("Absorb", 151, Rarity.RARE, mage.cards.a.Absorb.class));
        cards.add(new SetCardInfo("Act of Treason", 91, Rarity.COMMON, mage.cards.a.ActOfTreason.class));
        cards.add(new SetCardInfo("Aeromunculus", 152, Rarity.COMMON, mage.cards.a.Aeromunculus.class));
        cards.add(new SetCardInfo("Amplifire", 92, Rarity.RARE, mage.cards.a.Amplifire.class));
        cards.add(new SetCardInfo("Angel of Grace", 1, Rarity.MYTHIC, mage.cards.a.AngelOfGrace.class));
        cards.add(new SetCardInfo("Angelic Exaltation", 2, Rarity.UNCOMMON, mage.cards.a.AngelicExaltation.class));
        cards.add(new SetCardInfo("Applied Biomancy", 153, Rarity.COMMON, mage.cards.a.AppliedBiomancy.class));
        cards.add(new SetCardInfo("Archway Angel", 3, Rarity.UNCOMMON, mage.cards.a.ArchwayAngel.class));
        cards.add(new SetCardInfo("Arrester's Admonition", 31, Rarity.COMMON, mage.cards.a.ArrestersAdmonition.class));
        cards.add(new SetCardInfo("Arrester's Zeal", 4, Rarity.COMMON, mage.cards.a.ArrestersZeal.class));
        cards.add(new SetCardInfo("Awaken the Erstwhile", 61, Rarity.RARE, mage.cards.a.AwakenTheErstwhile.class));
        cards.add(new SetCardInfo("Axebane Beast", 121, Rarity.COMMON, mage.cards.a.AxebaneBeast.class));
        cards.add(new SetCardInfo("Azorius Guildgate", 243, Rarity.COMMON, mage.cards.a.AzoriusGuildgate.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Azorius Guildgate", 244, Rarity.COMMON, mage.cards.a.AzoriusGuildgate.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Azorius Knight-Arbiter", 154, Rarity.COMMON, mage.cards.a.AzoriusKnightArbiter.class));
        cards.add(new SetCardInfo("Azorius Locket", 231, Rarity.COMMON, mage.cards.a.AzoriusLocket.class));
        cards.add(new SetCardInfo("Azorius Skyguard", 155, Rarity.UNCOMMON, mage.cards.a.AzoriusSkyguard.class));
        cards.add(new SetCardInfo("Bankrupt in Blood", 62, Rarity.UNCOMMON, mage.cards.b.BankruptInBlood.class));
        cards.add(new SetCardInfo("Basilica Bell-Haunt", 156, Rarity.UNCOMMON, mage.cards.b.BasilicaBellHaunt.class));
        cards.add(new SetCardInfo("Bedeck // Bedazzle", 221, Rarity.RARE, mage.cards.b.BedeckBedazzle.class));
        cards.add(new SetCardInfo("Bedevil", 157, Rarity.RARE, mage.cards.b.Bedevil.class));
        cards.add(new SetCardInfo("Benthic Biomancer", 32, Rarity.RARE, mage.cards.b.BenthicBiomancer.class));
        cards.add(new SetCardInfo("Biogenic Ooze", 122, Rarity.MYTHIC, mage.cards.b.BiogenicOoze.class));
        cards.add(new SetCardInfo("Biogenic Upgrade", 123, Rarity.UNCOMMON, mage.cards.b.BiogenicUpgrade.class));
        cards.add(new SetCardInfo("Biomancer's Familiar", 158, Rarity.RARE, mage.cards.b.BiomancersFamiliar.class));
        cards.add(new SetCardInfo("Blade Juggler", 63, Rarity.COMMON, mage.cards.b.BladeJuggler.class));
        cards.add(new SetCardInfo("Bladebrand", 64, Rarity.COMMON, mage.cards.b.Bladebrand.class));
        cards.add(new SetCardInfo("Blood Crypt", 245, Rarity.RARE, mage.cards.b.BloodCrypt.class));
        cards.add(new SetCardInfo("Bloodmist Infiltrator", 65, Rarity.UNCOMMON, mage.cards.b.BloodmistInfiltrator.class));
        cards.add(new SetCardInfo("Bolrac-Clan Crusher", 159, Rarity.UNCOMMON, mage.cards.b.BolracClanCrusher.class));
        cards.add(new SetCardInfo("Breeding Pool", 246, Rarity.RARE, mage.cards.b.BreedingPool.class));
        cards.add(new SetCardInfo("Bring to Trial", 5, Rarity.COMMON, mage.cards.b.BringToTrial.class));
        cards.add(new SetCardInfo("Burn Bright", 93, Rarity.COMMON, mage.cards.b.BurnBright.class));
        cards.add(new SetCardInfo("Burning-Tree Vandal", 94, Rarity.COMMON, mage.cards.b.BurningTreeVandal.class));
        cards.add(new SetCardInfo("Captive Audience", 160, Rarity.MYTHIC, mage.cards.c.CaptiveAudience.class));
        cards.add(new SetCardInfo("Carnival // Carnage", 222, Rarity.UNCOMMON, mage.cards.c.CarnivalCarnage.class));
        cards.add(new SetCardInfo("Carrion Imp", 66, Rarity.COMMON, mage.cards.c.CarrionImp.class));
        cards.add(new SetCardInfo("Catacomb Crocodile", 67, Rarity.COMMON, mage.cards.c.CatacombCrocodile.class));
        cards.add(new SetCardInfo("Cavalcade of Calamity", 95, Rarity.UNCOMMON, mage.cards.c.CavalcadeOfCalamity.class));
        cards.add(new SetCardInfo("Charging War Boar", 271, Rarity.UNCOMMON, mage.cards.c.ChargingWarBoar.class));
        cards.add(new SetCardInfo("Chillbringer", 33, Rarity.COMMON, mage.cards.c.Chillbringer.class));
        cards.add(new SetCardInfo("Cindervines", 161, Rarity.RARE, mage.cards.c.Cindervines.class));
        cards.add(new SetCardInfo("Civic Stalwart", 6, Rarity.COMMON, mage.cards.c.CivicStalwart.class));
        cards.add(new SetCardInfo("Clamor Shaman", 96, Rarity.UNCOMMON, mage.cards.c.ClamorShaman.class));
        cards.add(new SetCardInfo("Clan Guildmage", 162, Rarity.UNCOMMON, mage.cards.c.ClanGuildmage.class));
        cards.add(new SetCardInfo("Clear the Mind", 34, Rarity.COMMON, mage.cards.c.ClearTheMind.class));
        cards.add(new SetCardInfo("Clear the Stage", 68, Rarity.UNCOMMON, mage.cards.c.ClearTheStage.class));
        cards.add(new SetCardInfo("Code of Constraint", 35, Rarity.UNCOMMON, mage.cards.c.CodeOfConstraint.class));
        cards.add(new SetCardInfo("Collision // Colossus", 223, Rarity.UNCOMMON, mage.cards.c.CollisionColossus.class));
        cards.add(new SetCardInfo("Combine Guildmage", 163, Rarity.UNCOMMON, mage.cards.c.CombineGuildmage.class));
        cards.add(new SetCardInfo("Consecrate // Consume", 224, Rarity.UNCOMMON, mage.cards.c.ConsecrateConsume.class));
        cards.add(new SetCardInfo("Concordia Pegasus", 7, Rarity.COMMON, mage.cards.c.ConcordiaPegasus.class));
        cards.add(new SetCardInfo("Consign to the Pit", 69, Rarity.COMMON, mage.cards.c.ConsignToThePit.class));
        cards.add(new SetCardInfo("Coral Commando", 36, Rarity.COMMON, mage.cards.c.CoralCommando.class));
        cards.add(new SetCardInfo("Cry of the Carnarium", 70, Rarity.UNCOMMON, mage.cards.c.CryOfTheCarnarium.class));
        cards.add(new SetCardInfo("Cult Guildmage", 164, Rarity.UNCOMMON, mage.cards.c.CultGuildmage.class));
        cards.add(new SetCardInfo("Dagger Caster", 97, Rarity.UNCOMMON, mage.cards.d.DaggerCaster.class));
        cards.add(new SetCardInfo("Dead Revels", 71, Rarity.COMMON, mage.cards.d.DeadRevels.class));
        cards.add(new SetCardInfo("Debtors' Transport", 72, Rarity.COMMON, mage.cards.d.DebtorsTransport.class));
        cards.add(new SetCardInfo("Deface", 98, Rarity.COMMON, mage.cards.d.Deface.class));
        cards.add(new SetCardInfo("Depose // Deploy", 225, Rarity.UNCOMMON, mage.cards.d.DeposeDeploy.class));
        cards.add(new SetCardInfo("Deputy of Detention", 165, Rarity.RARE, mage.cards.d.DeputyOfDetention.class));
        cards.add(new SetCardInfo("Domri's Nodorog", 272, Rarity.RARE, mage.cards.d.DomrisNodorog.class));
        cards.add(new SetCardInfo("Domri, Chaos Bringer", 166, Rarity.MYTHIC, mage.cards.d.DomriChaosBringer.class));
        cards.add(new SetCardInfo("Domri, City Smasher", 269, Rarity.MYTHIC, mage.cards.d.DomriCitySmasher.class));
        cards.add(new SetCardInfo("Dovin's Acuity", 168, Rarity.UNCOMMON, mage.cards.d.DovinsAcuity.class));
        cards.add(new SetCardInfo("Dovin's Automaton", 268, Rarity.UNCOMMON, mage.cards.d.DovinsAutomaton.class));
        cards.add(new SetCardInfo("Dovin's Dismissal", 267, Rarity.RARE, mage.cards.d.DovinsDismissal.class));
        cards.add(new SetCardInfo("Dovin, Architect of Law", 265, Rarity.MYTHIC, mage.cards.d.DovinArchitectOfLaw.class));
        cards.add(new SetCardInfo("Dovin, Grand Arbiter", 167, Rarity.MYTHIC, mage.cards.d.DovinGrandArbiter.class));
        cards.add(new SetCardInfo("Drill Bit", 73, Rarity.UNCOMMON, mage.cards.d.DrillBit.class));
        cards.add(new SetCardInfo("Electrodominance", 99, Rarity.RARE, mage.cards.e.Electrodominance.class));
        cards.add(new SetCardInfo("Elite Arrester", 266, Rarity.COMMON, mage.cards.e.EliteArrester.class));
        cards.add(new SetCardInfo("Emergency Powers", 169, Rarity.MYTHIC, mage.cards.e.EmergencyPowers.class));
        cards.add(new SetCardInfo("End-Raze Forerunners", 124, Rarity.RARE, mage.cards.e.EndRazeForerunners.class));
        cards.add(new SetCardInfo("Enraged Ceratok", 125, Rarity.UNCOMMON, mage.cards.e.EnragedCeratok.class));
        cards.add(new SetCardInfo("Essence Capture", 37, Rarity.UNCOMMON, mage.cards.e.EssenceCapture.class));
        cards.add(new SetCardInfo("Ethereal Absolution", 170, Rarity.RARE, mage.cards.e.EtherealAbsolution.class));
        cards.add(new SetCardInfo("Expose to Daylight", 8, Rarity.COMMON, mage.cards.e.ExposeToDaylight.class));
        cards.add(new SetCardInfo("Eyes Everywhere", 38, Rarity.UNCOMMON, mage.cards.e.EyesEverywhere.class));
        cards.add(new SetCardInfo("Faerie Duelist", 39, Rarity.COMMON, mage.cards.f.FaerieDuelist.class));
        cards.add(new SetCardInfo("Feral Maaka", 100, Rarity.COMMON, mage.cards.f.FeralMaaka.class));
        cards.add(new SetCardInfo("Final Payment", 171, Rarity.COMMON, mage.cards.f.FinalPayment.class));
        cards.add(new SetCardInfo("Fireblade Artist", 172, Rarity.UNCOMMON, mage.cards.f.FirebladeArtist.class));
        cards.add(new SetCardInfo("Flames of the Raze-Boar", 101, Rarity.UNCOMMON, mage.cards.f.FlamesOfTheRazeBoar.class));
        cards.add(new SetCardInfo("Font of Agonies", 74, Rarity.RARE, mage.cards.f.FontOfAgonies.class));
        cards.add(new SetCardInfo("Footlight Fiend", 216, Rarity.COMMON, mage.cards.f.FootlightFiend.class));
        cards.add(new SetCardInfo("Forbidding Spirit", 9, Rarity.UNCOMMON, mage.cards.f.ForbiddingSpirit.class));
        cards.add(new SetCardInfo("Forest", 264, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Frenzied Arynx", 173, Rarity.COMMON, mage.cards.f.FrenziedArynx.class));
        cards.add(new SetCardInfo("Frilled Mystic", 174, Rarity.UNCOMMON, mage.cards.f.FrilledMystic.class));
        cards.add(new SetCardInfo("Galloping Lizrog", 175, Rarity.UNCOMMON, mage.cards.g.GallopingLizrog.class));
        cards.add(new SetCardInfo("Gate Colossus", 232, Rarity.UNCOMMON, mage.cards.g.GateColossus.class));
        cards.add(new SetCardInfo("Gatebreaker Ram", 126, Rarity.UNCOMMON, mage.cards.g.GatebreakerRam.class));
        cards.add(new SetCardInfo("Gates Ablaze", 102, Rarity.UNCOMMON, mage.cards.g.GatesAblaze.class));
        cards.add(new SetCardInfo("Gateway Plaza", 247, Rarity.COMMON, mage.cards.g.GatewayPlaza.class));
        cards.add(new SetCardInfo("Gateway Sneak", 40, Rarity.UNCOMMON, mage.cards.g.GatewaySneak.class));
        cards.add(new SetCardInfo("Get the Point", 176, Rarity.COMMON, mage.cards.g.GetThePoint.class));
        cards.add(new SetCardInfo("Ghor-Clan Wrecker", 103, Rarity.COMMON, mage.cards.g.GhorClanWrecker.class));
        cards.add(new SetCardInfo("Gift of Strength", 127, Rarity.COMMON, mage.cards.g.GiftOfStrength.class));
        cards.add(new SetCardInfo("Glass of the Guildpact", 233, Rarity.RARE, mage.cards.g.GlassOfTheGuildpact.class));
        cards.add(new SetCardInfo("Goblin Gathering", 104, Rarity.COMMON, mage.cards.g.GoblinGathering.class));
        cards.add(new SetCardInfo("Godless Shrine", 248, Rarity.RARE, mage.cards.g.GodlessShrine.class));
        cards.add(new SetCardInfo("Grasping Thrull", 177, Rarity.COMMON, mage.cards.g.GraspingThrull.class));
        cards.add(new SetCardInfo("Gravel-Hide Goblin", 105, Rarity.COMMON, mage.cards.g.GravelHideGoblin.class));
        cards.add(new SetCardInfo("Grotesque Demise", 75, Rarity.COMMON, mage.cards.g.GrotesqueDemise.class));
        cards.add(new SetCardInfo("Growth Spiral", 178, Rarity.COMMON, mage.cards.g.GrowthSpiral.class));
        cards.add(new SetCardInfo("Growth-Chamber Guardian", 128, Rarity.RARE, mage.cards.g.GrowthChamberGuardian.class));
        cards.add(new SetCardInfo("Gruul Beastmaster", 129, Rarity.UNCOMMON, mage.cards.g.GruulBeastmaster.class));
        cards.add(new SetCardInfo("Gruul Guildgate", 249, Rarity.COMMON, mage.cards.g.GruulGuildgate.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gruul Guildgate", 250, Rarity.COMMON, mage.cards.g.GruulGuildgate.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gruul Locket", 234, Rarity.COMMON, mage.cards.g.GruulLocket.class));
        cards.add(new SetCardInfo("Gruul Spellbreaker", 179, Rarity.RARE, mage.cards.g.GruulSpellbreaker.class));
        cards.add(new SetCardInfo("Guardian Project", 130, Rarity.RARE, mage.cards.g.GuardianProject.class));
        cards.add(new SetCardInfo("Gutterbones", 76, Rarity.RARE, mage.cards.g.Gutterbones.class));
        cards.add(new SetCardInfo("Gyre Engineer", 180, Rarity.UNCOMMON, mage.cards.g.GyreEngineer.class));
        cards.add(new SetCardInfo("Haazda Officer", 10, Rarity.COMMON, mage.cards.h.HaazdaOfficer.class));
        cards.add(new SetCardInfo("Hackrobat", 181, Rarity.UNCOMMON, mage.cards.h.Hackrobat.class));
        cards.add(new SetCardInfo("Hallowed Fountain", 251, Rarity.RARE, mage.cards.h.HallowedFountain.class));
        cards.add(new SetCardInfo("Hero of Precinct One", 11, Rarity.RARE, mage.cards.h.HeroOfPrecinctOne.class));
        cards.add(new SetCardInfo("High Alert", 182, Rarity.UNCOMMON, mage.cards.h.HighAlert.class));
        cards.add(new SetCardInfo("Humongulus", 41, Rarity.COMMON, mage.cards.h.Humongulus.class));
        cards.add(new SetCardInfo("Hydroid Krasis", 183, Rarity.MYTHIC, mage.cards.h.HydroidKrasis.class));
        cards.add(new SetCardInfo("Ill-Gotten Inheritance", 77, Rarity.COMMON, mage.cards.i.IllGottenInheritance.class));
        cards.add(new SetCardInfo("Immolation Shaman", 106, Rarity.RARE, mage.cards.i.ImmolationShaman.class));
        cards.add(new SetCardInfo("Impassioned Orator", 12, Rarity.COMMON, mage.cards.i.ImpassionedOrator.class));
        cards.add(new SetCardInfo("Imperious Oligarch", 184, Rarity.COMMON, mage.cards.i.ImperiousOligarch.class));
        cards.add(new SetCardInfo("Incubation // Incongruity", 226, Rarity.UNCOMMON, mage.cards.i.IncubationIncongruity.class));
        cards.add(new SetCardInfo("Incubation Druid", 131, Rarity.RARE, mage.cards.i.IncubationDruid.class));
        cards.add(new SetCardInfo("Island", 261, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Judith, the Scourge Diva", 185, Rarity.RARE, mage.cards.j.JudithTheScourgeDiva.class));
        cards.add(new SetCardInfo("Junktroller", 235, Rarity.UNCOMMON, mage.cards.j.Junktroller.class));
        cards.add(new SetCardInfo("Justiciar's Portal", 13, Rarity.COMMON, mage.cards.j.JusticiarsPortal.class));
        cards.add(new SetCardInfo("Kaya's Wrath", 187, Rarity.RARE, mage.cards.k.KayasWrath.class));
        cards.add(new SetCardInfo("Kaya, Orzhov Usurper", 186, Rarity.MYTHIC, mage.cards.k.KayaOrzhovUsurper.class));
        cards.add(new SetCardInfo("Knight of Sorrows", 14, Rarity.COMMON, mage.cards.k.KnightOfSorrows.class));
        cards.add(new SetCardInfo("Knight of the Last Breath", 188, Rarity.UNCOMMON, mage.cards.k.KnightOfTheLastBreath.class));
        cards.add(new SetCardInfo("Lavinia, Azorius Renegade", 189, Rarity.RARE, mage.cards.l.LaviniaAzoriusRenegade.class));
        cards.add(new SetCardInfo("Lawmage's Binding", 190, Rarity.COMMON, mage.cards.l.LawmagesBinding.class));
        cards.add(new SetCardInfo("Light Up the Stage", 107, Rarity.UNCOMMON, mage.cards.l.LightUpTheStage.class));
        cards.add(new SetCardInfo("Lumbering Battlement", 15, Rarity.RARE, mage.cards.l.LumberingBattlement.class));
        cards.add(new SetCardInfo("Macabre Mockery", 191, Rarity.UNCOMMON, mage.cards.m.MacabreMockery.class));
        cards.add(new SetCardInfo("Mammoth Spider", 132, Rarity.COMMON, mage.cards.m.MammothSpider.class));
        cards.add(new SetCardInfo("Mass Manipulation", 42, Rarity.RARE, mage.cards.m.MassManipulation.class));
        cards.add(new SetCardInfo("Mesmerizing Benthid", 43, Rarity.MYTHIC, mage.cards.m.MesmerizingBenthid.class));
        cards.add(new SetCardInfo("Ministrant of Obligation", 16, Rarity.UNCOMMON, mage.cards.m.MinistrantOfObligation.class));
        cards.add(new SetCardInfo("Mirror March", 108, Rarity.RARE, mage.cards.m.MirrorMarch.class));
        cards.add(new SetCardInfo("Mortify", 192, Rarity.UNCOMMON, mage.cards.m.Mortify.class));
        cards.add(new SetCardInfo("Mountain", 263, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Nikya of the Old Ways", 193, Rarity.RARE, mage.cards.n.NikyaOfTheOldWays.class));
        cards.add(new SetCardInfo("Noxious Groodion", 78, Rarity.COMMON, mage.cards.n.NoxiousGroodion.class));
        cards.add(new SetCardInfo("Open the Gates", 133, Rarity.COMMON, mage.cards.o.OpenTheGates.class));
        cards.add(new SetCardInfo("Orzhov Enforcer", 79, Rarity.UNCOMMON, mage.cards.o.OrzhovEnforcer.class));
        cards.add(new SetCardInfo("Orzhov Guildgate", 252, Rarity.COMMON, mage.cards.o.OrzhovGuildgate.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Orzhov Guildgate", 253, Rarity.COMMON, mage.cards.o.OrzhovGuildgate.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Orzhov Locket", 236, Rarity.COMMON, mage.cards.o.OrzhovLocket.class));
        cards.add(new SetCardInfo("Orzhov Racketeers", 80, Rarity.UNCOMMON, mage.cards.o.OrzhovRacketeers.class));
        cards.add(new SetCardInfo("Persistent Petitioners", 44, Rarity.COMMON, mage.cards.p.PersistentPetitioners.class));
        cards.add(new SetCardInfo("Pestilent Spirit", 81, Rarity.RARE, mage.cards.p.PestilentSpirit.class));
        cards.add(new SetCardInfo("Pitiless Pontiff", 194, Rarity.UNCOMMON, mage.cards.p.PitilessPontiff.class));
        cards.add(new SetCardInfo("Plague Wight", 82, Rarity.COMMON, mage.cards.p.PlagueWight.class));
        cards.add(new SetCardInfo("Plains", 260, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plaza of Harmony", 254, Rarity.RARE, mage.cards.p.PlazaOfHarmony.class));
        cards.add(new SetCardInfo("Precognitive Perception", 45, Rarity.RARE, mage.cards.p.PrecognitivePerception.class));
        cards.add(new SetCardInfo("Priest of Forgotten Gods", 83, Rarity.RARE, mage.cards.p.PriestOfForgottenGods.class));
        cards.add(new SetCardInfo("Prime Speaker Vannifar", 195, Rarity.MYTHIC, mage.cards.p.PrimeSpeakerVannifar.class));
        cards.add(new SetCardInfo("Prowling Caracal", 17, Rarity.COMMON, mage.cards.p.ProwlingCaracal.class));
        cards.add(new SetCardInfo("Prying Eyes", 46, Rarity.COMMON, mage.cards.p.PryingEyes.class));
        cards.add(new SetCardInfo("Pteramander", 47, Rarity.UNCOMMON, mage.cards.p.Pteramander.class));
        cards.add(new SetCardInfo("Quench", 48, Rarity.COMMON, mage.cards.q.Quench.class));
        cards.add(new SetCardInfo("Rafter Demon", 196, Rarity.COMMON, mage.cards.r.RafterDemon.class));
        cards.add(new SetCardInfo("Ragefire", 270, Rarity.COMMON, mage.cards.r.Ragefire.class));
        cards.add(new SetCardInfo("Rakdos Firewheeler", 197, Rarity.UNCOMMON, mage.cards.r.RakdosFirewheeler.class));
        cards.add(new SetCardInfo("Rakdos Guildgate", 255, Rarity.COMMON, mage.cards.r.RakdosGuildgate.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Rakdos Guildgate", 256, Rarity.COMMON, mage.cards.r.RakdosGuildgate.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Rakdos Locket", 237, Rarity.COMMON, mage.cards.r.RakdosLocket.class));
        cards.add(new SetCardInfo("Rakdos Roustabout", 198, Rarity.COMMON, mage.cards.r.RakdosRoustabout.class));
        cards.add(new SetCardInfo("Rakdos Trumpeter", 84, Rarity.COMMON, mage.cards.r.RakdosTrumpeter.class));
        cards.add(new SetCardInfo("Rakdos, the Showstopper", 199, Rarity.MYTHIC, mage.cards.r.RakdosTheShowstopper.class));
        cards.add(new SetCardInfo("Rally to Battle", 18, Rarity.UNCOMMON, mage.cards.r.RallyToBattle.class));
        cards.add(new SetCardInfo("Rampage of the Clans", 134, Rarity.RARE, mage.cards.r.RampageOfTheClans.class));
        cards.add(new SetCardInfo("Rampaging Rendhorn", 135, Rarity.COMMON, mage.cards.r.RampagingRendhorn.class));
        cards.add(new SetCardInfo("Ravager Wurm", 200, Rarity.MYTHIC, mage.cards.r.RavagerWurm.class));
        cards.add(new SetCardInfo("Regenesis", 136, Rarity.UNCOMMON, mage.cards.r.Regenesis.class));
        cards.add(new SetCardInfo("Repudiate // Replicate", 227, Rarity.RARE, mage.cards.r.RepudiateReplicate.class));
        cards.add(new SetCardInfo("Resolute Watchdog", 19, Rarity.UNCOMMON, mage.cards.r.ResoluteWatchdog.class));
        cards.add(new SetCardInfo("Revival // Revenge", 228, Rarity.RARE, mage.cards.r.RevivalRevenge.class));
        cards.add(new SetCardInfo("Rhythm of the Wild", 201, Rarity.UNCOMMON, mage.cards.r.RhythmOfTheWild.class));
        cards.add(new SetCardInfo("Rix Maadi Reveler", 109, Rarity.RARE, mage.cards.r.RixMaadiReveler.class));
        cards.add(new SetCardInfo("Root Snare", 137, Rarity.COMMON, mage.cards.r.RootSnare.class));
        cards.add(new SetCardInfo("Rubble Reading", 110, Rarity.COMMON, mage.cards.r.RubbleReading.class));
        cards.add(new SetCardInfo("Rubble Slinger", 217, Rarity.COMMON, mage.cards.r.RubbleSlinger.class));
        cards.add(new SetCardInfo("Rubblebelt Recluse", 111, Rarity.COMMON, mage.cards.r.RubblebeltRecluse.class));
        cards.add(new SetCardInfo("Rubblebelt Runner", 202, Rarity.COMMON, mage.cards.r.RubblebeltRunner.class));
        cards.add(new SetCardInfo("Rumbling Ruin", 112, Rarity.UNCOMMON, mage.cards.r.RumblingRuin.class));
        cards.add(new SetCardInfo("Sage's Row Savant", 49, Rarity.COMMON, mage.cards.s.SagesRowSavant.class));
        cards.add(new SetCardInfo("Sagittars' Volley", 138, Rarity.COMMON, mage.cards.s.SagittarsVolley.class));
        cards.add(new SetCardInfo("Saruli Caretaker", 139, Rarity.COMMON, mage.cards.s.SaruliCaretaker.class));
        cards.add(new SetCardInfo("Sauroform Hybrid", 140, Rarity.COMMON, mage.cards.s.SauroformHybrid.class));
        cards.add(new SetCardInfo("Savage Smash", 203, Rarity.COMMON, mage.cards.s.SavageSmash.class));
        cards.add(new SetCardInfo("Scorchmark", 113, Rarity.COMMON, mage.cards.s.Scorchmark.class));
        cards.add(new SetCardInfo("Scrabbling Claws", 238, Rarity.UNCOMMON, mage.cards.s.ScrabblingClaws.class));
        cards.add(new SetCardInfo("Screaming Shield", 239, Rarity.UNCOMMON, mage.cards.s.ScreamingShield.class));
        cards.add(new SetCardInfo("Scuttlegator", 218, Rarity.COMMON, mage.cards.s.Scuttlegator.class));
        cards.add(new SetCardInfo("Senate Courier", 50, Rarity.COMMON, mage.cards.s.SenateCourier.class));
        cards.add(new SetCardInfo("Senate Griffin", 219, Rarity.COMMON, mage.cards.s.SenateGriffin.class));
        cards.add(new SetCardInfo("Senate Guildmage", 204, Rarity.UNCOMMON, mage.cards.s.SenateGuildmage.class));
        cards.add(new SetCardInfo("Sentinel's Mark", 20, Rarity.UNCOMMON, mage.cards.s.SentinelsMark.class));
        cards.add(new SetCardInfo("Seraph of the Scales", 205, Rarity.MYTHIC, mage.cards.s.SeraphOfTheScales.class));
        cards.add(new SetCardInfo("Sharktocrab", 206, Rarity.UNCOMMON, mage.cards.s.Sharktocrab.class));
        cards.add(new SetCardInfo("Shimmer of Possibility", 51, Rarity.COMMON, mage.cards.s.ShimmerOfPossibility.class));
        cards.add(new SetCardInfo("Silhana Wayfinder", 141, Rarity.UNCOMMON, mage.cards.s.SilhanaWayfinder.class));
        cards.add(new SetCardInfo("Simic Ascendancy", 207, Rarity.RARE, mage.cards.s.SimicAscendancy.class));
        cards.add(new SetCardInfo("Simic Guildgate", 257, Rarity.COMMON, mage.cards.s.SimicGuildgate.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Simic Guildgate", 258, Rarity.COMMON, mage.cards.s.SimicGuildgate.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Simic Locket", 240, Rarity.COMMON, mage.cards.s.SimicLocket.class));
        cards.add(new SetCardInfo("Skarrgan Hellkite", 114, Rarity.MYTHIC, mage.cards.s.SkarrganHellkite.class));
        cards.add(new SetCardInfo("Skatewing Spy", 52, Rarity.UNCOMMON, mage.cards.s.SkatewingSpy.class));
        cards.add(new SetCardInfo("Skewer the Critics", 115, Rarity.COMMON, mage.cards.s.SkewerTheCritics.class));
        cards.add(new SetCardInfo("Skitter Eel", 53, Rarity.COMMON, mage.cards.s.SkitterEel.class));
        cards.add(new SetCardInfo("Sky Tether", 21, Rarity.UNCOMMON, mage.cards.s.SkyTether.class));
        cards.add(new SetCardInfo("Slimebind", 54, Rarity.COMMON, mage.cards.s.Slimebind.class));
        cards.add(new SetCardInfo("Smelt-Ward Ignus", 116, Rarity.UNCOMMON, mage.cards.s.SmeltWardIgnus.class));
        cards.add(new SetCardInfo("Smothering Tithe", 22, Rarity.RARE, mage.cards.s.SmotheringTithe.class));
        cards.add(new SetCardInfo("Spawn of Mayhem", 85, Rarity.MYTHIC, mage.cards.s.SpawnOfMayhem.class));
        cards.add(new SetCardInfo("Spear Spewer", 117, Rarity.COMMON, mage.cards.s.SpearSpewer.class));
        cards.add(new SetCardInfo("Sphinx of Foresight", 55, Rarity.RARE, mage.cards.s.SphinxOfForesight.class));
        cards.add(new SetCardInfo("Sphinx of New Prahv", 208, Rarity.UNCOMMON, mage.cards.s.SphinxOfNewPrahv.class));
        cards.add(new SetCardInfo("Sphinx of the Guildpact", 241, Rarity.UNCOMMON, mage.cards.s.SphinxOfTheGuildpact.class));
        cards.add(new SetCardInfo("Sphinx's Insight", 209, Rarity.COMMON, mage.cards.s.SphinxsInsight.class));
        cards.add(new SetCardInfo("Spikewheel Acrobat", 118, Rarity.COMMON, mage.cards.s.SpikewheelAcrobat.class));
        cards.add(new SetCardInfo("Spire Mangler", 86, Rarity.UNCOMMON, mage.cards.s.SpireMangler.class));
        cards.add(new SetCardInfo("Spirit of the Spires", 23, Rarity.UNCOMMON, mage.cards.s.SpiritOfTheSpires.class));
        cards.add(new SetCardInfo("Steeple Creeper", 142, Rarity.COMMON, mage.cards.s.SteepleCreeper.class));
        cards.add(new SetCardInfo("Stomping Ground", 259, Rarity.RARE, mage.cards.s.StompingGround.class));
        cards.add(new SetCardInfo("Stony Strength", 143, Rarity.COMMON, mage.cards.s.StonyStrength.class));
        cards.add(new SetCardInfo("Storm Strike", 119, Rarity.COMMON, mage.cards.s.StormStrike.class));
        cards.add(new SetCardInfo("Summary Judgment", 24, Rarity.COMMON, mage.cards.s.SummaryJudgment.class));
        cards.add(new SetCardInfo("Sunder Shaman", 210, Rarity.UNCOMMON, mage.cards.s.SunderShaman.class));
        cards.add(new SetCardInfo("Swamp", 262, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swirling Torrent", 56, Rarity.UNCOMMON, mage.cards.s.SwirlingTorrent.class));
        cards.add(new SetCardInfo("Sylvan Brushstrider", 144, Rarity.COMMON, mage.cards.s.SylvanBrushstrider.class));
        cards.add(new SetCardInfo("Syndicate Guildmage", 211, Rarity.UNCOMMON, mage.cards.s.SyndicateGuildmage.class));
        cards.add(new SetCardInfo("Syndicate Messenger", 25, Rarity.COMMON, mage.cards.s.SyndicateMessenger.class));
        cards.add(new SetCardInfo("Tenth District Veteran", 26, Rarity.COMMON, mage.cards.t.TenthDistrictVeteran.class));
        cards.add(new SetCardInfo("Territorial Boar", 145, Rarity.COMMON, mage.cards.t.TerritorialBoar.class));
        cards.add(new SetCardInfo("Teysa Karlov", 212, Rarity.RARE, mage.cards.t.TeysaKarlov.class));
        cards.add(new SetCardInfo("The Haunt of Hightower", 273, Rarity.MYTHIC, mage.cards.t.TheHauntOfHightower.class));
        cards.add(new SetCardInfo("Theater of Horrors", 213, Rarity.RARE, mage.cards.t.TheaterOfHorrors.class));
        cards.add(new SetCardInfo("Thirsting Shade", 87, Rarity.COMMON, mage.cards.t.ThirstingShade.class));
        cards.add(new SetCardInfo("Thought Collapse", 57, Rarity.COMMON, mage.cards.t.ThoughtCollapse.class));
        cards.add(new SetCardInfo("Thrash // Threat", 229, Rarity.RARE, mage.cards.t.ThrashThreat.class));
        cards.add(new SetCardInfo("Tin Street Dodger", 120, Rarity.UNCOMMON, mage.cards.t.TinStreetDodger.class));
        cards.add(new SetCardInfo("Titanic Brawl", 146, Rarity.COMMON, mage.cards.t.TitanicBrawl.class));
        cards.add(new SetCardInfo("Tithe Taker", 27, Rarity.RARE, mage.cards.t.TitheTaker.class));
        cards.add(new SetCardInfo("Tome of the Guildpact", 242, Rarity.RARE, mage.cards.t.TomeOfTheGuildpact.class));
        cards.add(new SetCardInfo("Tower Defense", 147, Rarity.UNCOMMON, mage.cards.t.TowerDefense.class));
        cards.add(new SetCardInfo("Trollbred Guardian", 148, Rarity.UNCOMMON, mage.cards.t.TrollbredGuardian.class));
        cards.add(new SetCardInfo("Twilight Panther", 28, Rarity.COMMON, mage.cards.t.TwilightPanther.class));
        cards.add(new SetCardInfo("Unbreakable Formation", 29, Rarity.RARE, mage.cards.u.UnbreakableFormation.class));
        cards.add(new SetCardInfo("Undercity Scavenger", 88, Rarity.COMMON, mage.cards.u.UndercityScavenger.class));
        cards.add(new SetCardInfo("Undercity's Embrace", 89, Rarity.COMMON, mage.cards.u.UndercitysEmbrace.class));
        cards.add(new SetCardInfo("Verity Circle", 58, Rarity.RARE, mage.cards.v.VerityCircle.class));
        cards.add(new SetCardInfo("Vindictive Vampire", 90, Rarity.UNCOMMON, mage.cards.v.VindictiveVampire.class));
        cards.add(new SetCardInfo("Vizkopa Vampire", 220, Rarity.COMMON, mage.cards.v.VizkopaVampire.class));
        cards.add(new SetCardInfo("Wall of Lost Thoughts", 59, Rarity.UNCOMMON, mage.cards.w.WallOfLostThoughts.class));
        cards.add(new SetCardInfo("Warrant // Warden", 230, Rarity.RARE, mage.cards.w.WarrantWarden.class));
        cards.add(new SetCardInfo("Watchful Giant", 30, Rarity.COMMON, mage.cards.w.WatchfulGiant.class));
        cards.add(new SetCardInfo("Wilderness Reclamation", 149, Rarity.UNCOMMON, mage.cards.w.WildernessReclamation.class));
        cards.add(new SetCardInfo("Windstorm Drake", 60, Rarity.UNCOMMON, mage.cards.w.WindstormDrake.class));
        cards.add(new SetCardInfo("Wrecking Beast", 150, Rarity.COMMON, mage.cards.w.WreckingBeast.class));
        cards.add(new SetCardInfo("Zegana, Utopian Speaker", 214, Rarity.RARE, mage.cards.z.ZeganaUtopianSpeaker.class));
        cards.add(new SetCardInfo("Zhur-Taa Goblin", 215, Rarity.UNCOMMON, mage.cards.z.ZhurTaaGoblin.class));
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
        return new RavnicaAllegianceCollator();
    }
}

// Booster collation info from https://www.lethe.xyz/mtg/collation/rna.html
// Using USA collation for all rarities
// Foil rare sheet used for regular rares as regular rare sheet is not known
class RavnicaAllegianceCollator implements BoosterCollator {
    private final CardRun commonA = new CardRun(true, "54", "203", "220", "31", "113", "10", "44", "202", "17", "51", "119", "5", "36", "100", "7", "57", "117", "26", "39", "103", "6", "48", "217", "12", "41", "105", "220", "49", "111", "14", "54", "118", "10", "31", "203", "28", "44", "113", "5", "57", "202", "26", "46", "117", "7", "48", "217", "4", "51", "100", "14", "36", "105", "17", "41", "119", "6", "39", "103", "12", "46", "118", "28", "49", "111", "4");
    private final CardRun commonB = new CardRun(true, "127", "87", "145", "84", "133", "66", "135", "71", "140", "88", "138", "78", "142", "198", "143", "89", "145", "82", "121", "84", "127", "216", "135", "69", "133", "87", "132", "78", "143", "71", "140", "88", "138", "198", "144", "66", "142", "89", "127", "216", "133", "69", "145", "82", "121", "71", "135", "84", "132", "88", "144", "87", "143", "89", "140", "198", "142", "66", "138", "78", "144", "82", "121", "216", "132", "69");
    private final CardRun commonC1 = new CardRun(true, "98", "247", "153", "137", "231", "25", "34", "196", "115", "64", "146", "178", "8", "75", "209", "93", "50", "154", "234", "173", "236", "98", "53", "150", "218", "153", "247", "72", "240", "177", "25", "137", "196", "231", "64", "115", "178", "34", "209", "75", "8", "146", "154", "93", "177", "53", "72", "236", "173", "234", "150", "50", "218", "219", "240");
    private final CardRun commonC2 = new CardRun(true, "13", "176", "67", "33", "110", "63", "30", "171", "77", "94", "152", "237", "33", "110", "24", "139", "190", "104", "63", "13", "171", "91", "184", "77", "176", "30", "152", "104", "219", "67", "24", "139", "33", "110", "190", "237", "94", "67", "184", "91", "176", "13", "171", "237", "63", "104", "24", "139", "152", "77", "190", "91", "184", "30", "94");
    private final CardRun uncommonA = new CardRun(true, "62", "194", "52", "201", "125", "204", "3", "141", "159", "107", "180", "80", "192", "60", "181", "123", "175", "239", "129", "197", "97", "215", "90", "206", "40", "182", "148", "224", "2", "225", "141", "96", "174", "62", "125", "52", "201", "16", "194", "232", "68", "208", "107", "180", "90", "210", "38", "159", "148", "156", "18", "181", "129", "175", "80", "182", "123", "40", "215", "3", "192", "239", "60", "206", "97", "174", "68", "197", "38", "204", "2", "208", "232", "201", "141", "96", "225", "90", "210", "40", "180", "125", "16", "224", "148", "159", "107", "156", "62", "194", "52", "181", "18", "208", "239", "175", "3", "97", "182", "80", "123", "60", "174", "16", "192", "129", "204", "206", "96", "224", "68", "197", "38", "225", "18", "210", "232", "215", "2", "156");
    private final CardRun uncommonB = new CardRun(true, "102", "168", "120", "241", "70", "164", "149", "35", "226", "59", "126", "116", "162", "235", "20", "73", "222", "95", "56", "211", "136", "9", "112", "172", "238", "23", "86", "155", "19", "47", "188", "147", "223", "101", "241", "191", "20", "79", "164", "235", "37", "163", "149", "70", "102", "211", "120", "21", "86", "222", "65", "35", "168", "238", "126", "112", "162", "95", "9", "79", "226", "136", "23", "172", "59", "20", "116", "56", "19", "155", "73", "223", "120", "9", "164", "147", "241", "101", "47", "191", "211", "86", "162", "149", "35", "163", "95", "188", "102", "37", "235", "226", "70", "168", "238", "21", "155", "136", "79", "112", "47", "65", "223", "172", "101", "59", "23", "222", "19", "126", "116", "56", "191", "73", "188", "65", "37", "163", "147", "21");
    private final CardRun rare = new CardRun(true, "11", "160", "251", "151", "32", "170", "74", "193", "108", "166", "124", "207", "61", "213", "134", "42", "1", "245", "221", "99", "242", "187", "76", "233", "167", "157", "130", "179", "254", "165", "45", "15", "85", "229", "81", "22", "158", "128", "230", "55", "169", "27", "246", "189", "109", "212", "259", "32", "186", "227", "131", "161", "106", "83", "228", "11", "122", "185", "61", "214", "248", "151", "58", "99", "199", "207", "74", "221", "124", "193", "15", "92", "200", "42", "170", "251", "158", "29", "187", "259", "114", "179", "76", "157", "134", "22", "213", "254", "205", "128", "228", "108", "161", "45", "165", "81", "183", "245", "229", "212", "55", "242", "227", "109", "43", "130", "27", "189", "246", "106", "230", "233", "195", "131", "83", "214", "248", "29", "58", "92", "185");
    private final CardRun land = new CardRun(false, "243", "244", "249", "250", "252", "253", "255", "256", "257", "258");

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
