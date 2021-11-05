package mage.sets;

import mage.cards.Card;
import mage.cards.ExpansionSet;
import mage.cards.repository.CardCriteria;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.constants.Rarity;
import mage.constants.SetType;
import mage.util.RandomUtil;

import java.util.List;

/**
 * @author fireshoes
 */
public final class EldritchMoon extends ExpansionSet {

    private static final EldritchMoon instance = new EldritchMoon();

    public static EldritchMoon getInstance() {
        return instance;
    }

    private EldritchMoon() {
        super("Eldritch Moon", "EMN", ExpansionSet.buildDate(2016, 7, 22), SetType.EXPANSION);
        this.blockName = "Shadows over Innistrad";
        this.parentSet = ShadowsOverInnistrad.getInstance();
        this.hasBoosters = true;
        this.hasBasicLands = false;
        this.numBoosterLands = 1;
        this.numBoosterCommon = 9;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 8;
        this.ratioBoosterSpecialCommon = 8;
        this.numBoosterDoubleFaced = 1;

        cards.add(new SetCardInfo("Abandon Reason", 115, Rarity.UNCOMMON, mage.cards.a.AbandonReason.class));
        cards.add(new SetCardInfo("Abolisher of Bloodlines", 111, Rarity.RARE, mage.cards.a.AbolisherOfBloodlines.class));
        cards.add(new SetCardInfo("Abundant Maw", 1, Rarity.UNCOMMON, mage.cards.a.AbundantMaw.class));
        cards.add(new SetCardInfo("Advanced Stitchwing", 49, Rarity.UNCOMMON, mage.cards.a.AdvancedStitchwing.class));
        cards.add(new SetCardInfo("Alchemist's Greeting", 116, Rarity.COMMON, mage.cards.a.AlchemistsGreeting.class));
        cards.add(new SetCardInfo("Assembled Alphas", 117, Rarity.RARE, mage.cards.a.AssembledAlphas.class));
        cards.add(new SetCardInfo("Aurora of Emrakul", 193, Rarity.UNCOMMON, mage.cards.a.AuroraOfEmrakul.class));
        cards.add(new SetCardInfo("Backwoods Survivalists", 150, Rarity.COMMON, mage.cards.b.BackwoodsSurvivalists.class));
        cards.add(new SetCardInfo("Bedlam Reveler", 118, Rarity.RARE, mage.cards.b.BedlamReveler.class));
        cards.add(new SetCardInfo("Blessed Alliance", 13, Rarity.UNCOMMON, mage.cards.b.BlessedAlliance.class));
        cards.add(new SetCardInfo("Blood Mist", 119, Rarity.UNCOMMON, mage.cards.b.BloodMist.class));
        cards.add(new SetCardInfo("Bloodbriar", 151, Rarity.COMMON, mage.cards.b.Bloodbriar.class));
        cards.add(new SetCardInfo("Bloodhall Priest", 181, Rarity.RARE, mage.cards.b.BloodhallPriest.class));
        cards.add(new SetCardInfo("Bold Impaler", 120, Rarity.COMMON, mage.cards.b.BoldImpaler.class));
        cards.add(new SetCardInfo("Boon of Emrakul", 81, Rarity.COMMON, mage.cards.b.BoonOfEmrakul.class));
        cards.add(new SetCardInfo("Borrowed Grace", 14, Rarity.COMMON, mage.cards.b.BorrowedGrace.class));
        cards.add(new SetCardInfo("Borrowed Hostility", 121, Rarity.COMMON, mage.cards.b.BorrowedHostility.class));
        cards.add(new SetCardInfo("Borrowed Malevolence", 82, Rarity.COMMON, mage.cards.b.BorrowedMalevolence.class));
        cards.add(new SetCardInfo("Brazen Wolves", 122, Rarity.COMMON, mage.cards.b.BrazenWolves.class));
        cards.add(new SetCardInfo("Brisela, Voice of Nightmares", "15b", Rarity.MYTHIC, mage.cards.b.BriselaVoiceOfNightmares.class));
        cards.add(new SetCardInfo("Bruna, the Fading Light", "15a", Rarity.RARE, mage.cards.b.BrunaTheFadingLight.class));
        cards.add(new SetCardInfo("Campaign of Vengeance", 182, Rarity.UNCOMMON, mage.cards.c.CampaignOfVengeance.class));
        cards.add(new SetCardInfo("Cathar's Shield", 192, Rarity.COMMON, mage.cards.c.CatharsShield.class));
        cards.add(new SetCardInfo("Cemetery Recruitment", 83, Rarity.COMMON, mage.cards.c.CemeteryRecruitment.class));
        cards.add(new SetCardInfo("Certain Death", 84, Rarity.COMMON, mage.cards.c.CertainDeath.class));
        cards.add(new SetCardInfo("Chilling Grasp", 50, Rarity.UNCOMMON, mage.cards.c.ChillingGrasp.class));
        cards.add(new SetCardInfo("Chittering Host", "96b", Rarity.COMMON, mage.cards.c.ChitteringHost.class));
        cards.add(new SetCardInfo("Choking Restraints", 16, Rarity.COMMON, mage.cards.c.ChokingRestraints.class));
        cards.add(new SetCardInfo("Clear Shot", 152, Rarity.UNCOMMON, mage.cards.c.ClearShot.class));
        cards.add(new SetCardInfo("Coax from the Blind Eternities", 51, Rarity.RARE, mage.cards.c.CoaxFromTheBlindEternities.class));
        cards.add(new SetCardInfo("Collective Brutality", 85, Rarity.RARE, mage.cards.c.CollectiveBrutality.class));
        cards.add(new SetCardInfo("Collective Defiance", 123, Rarity.RARE, mage.cards.c.CollectiveDefiance.class));
        cards.add(new SetCardInfo("Collective Effort", 17, Rarity.RARE, mage.cards.c.CollectiveEffort.class));
        cards.add(new SetCardInfo("Conduit of Emrakul", 124, Rarity.UNCOMMON, mage.cards.c.ConduitOfEmrakul.class));
        cards.add(new SetCardInfo("Conduit of Storms", 124, Rarity.UNCOMMON, mage.cards.c.ConduitOfStorms.class));
        cards.add(new SetCardInfo("Contingency Plan", 52, Rarity.COMMON, mage.cards.c.ContingencyPlan.class));
        cards.add(new SetCardInfo("Convolute", 53, Rarity.COMMON, mage.cards.c.Convolute.class));
        cards.add(new SetCardInfo("Courageous Outrider", 18, Rarity.UNCOMMON, mage.cards.c.CourageousOutrider.class));
        cards.add(new SetCardInfo("Crop Sigil", 153, Rarity.UNCOMMON, mage.cards.c.CropSigil.class));
        cards.add(new SetCardInfo("Crossroads Consecrator", 154, Rarity.COMMON, mage.cards.c.CrossroadsConsecrator.class));
        cards.add(new SetCardInfo("Cryptbreaker", 86, Rarity.RARE, mage.cards.c.Cryptbreaker.class));
        cards.add(new SetCardInfo("Cryptolith Fragment", 193, Rarity.UNCOMMON, mage.cards.c.CryptolithFragment.class));
        cards.add(new SetCardInfo("Cultist's Staff", 194, Rarity.COMMON, mage.cards.c.CultistsStaff.class));
        cards.add(new SetCardInfo("Curious Homunculus", 54, Rarity.UNCOMMON, mage.cards.c.CuriousHomunculus.class));
        cards.add(new SetCardInfo("Dark Salvation", 87, Rarity.RARE, mage.cards.d.DarkSalvation.class));
        cards.add(new SetCardInfo("Dawn Gryff", 19, Rarity.COMMON, mage.cards.d.DawnGryff.class));
        cards.add(new SetCardInfo("Decimator of the Provinces", 2, Rarity.MYTHIC, mage.cards.d.DecimatorOfTheProvinces.class));
        cards.add(new SetCardInfo("Deploy the Gatewatch", 20, Rarity.MYTHIC, mage.cards.d.DeployTheGatewatch.class));
        cards.add(new SetCardInfo("Deranged Whelp", 125, Rarity.UNCOMMON, mage.cards.d.DerangedWhelp.class));
        cards.add(new SetCardInfo("Desperate Sentry", 21, Rarity.COMMON, mage.cards.d.DesperateSentry.class));
        cards.add(new SetCardInfo("Displace", 55, Rarity.COMMON, mage.cards.d.Displace.class));
        cards.add(new SetCardInfo("Distemper of the Blood", 126, Rarity.COMMON, mage.cards.d.DistemperOfTheBlood.class));
        cards.add(new SetCardInfo("Distended Mindbender", 3, Rarity.RARE, mage.cards.d.DistendedMindbender.class));
        cards.add(new SetCardInfo("Docent of Perfection", 56, Rarity.RARE, mage.cards.d.DocentOfPerfection.class));
        cards.add(new SetCardInfo("Drag Under", 57, Rarity.COMMON, mage.cards.d.DragUnder.class));
        cards.add(new SetCardInfo("Drogskol Shieldmate", 22, Rarity.UNCOMMON, mage.cards.d.DrogskolShieldmate.class));
        cards.add(new SetCardInfo("Dronepack Kindred", 148, Rarity.COMMON, mage.cards.d.DronepackKindred.class));
        cards.add(new SetCardInfo("Drownyard Behemoth", 4, Rarity.UNCOMMON, mage.cards.d.DrownyardBehemoth.class));
        cards.add(new SetCardInfo("Dusk Feaster", 88, Rarity.UNCOMMON, mage.cards.d.DuskFeaster.class));
        cards.add(new SetCardInfo("Elder Deep-Fiend", 5, Rarity.RARE, mage.cards.e.ElderDeepFiend.class));
        cards.add(new SetCardInfo("Eldritch Evolution", 155, Rarity.RARE, mage.cards.e.EldritchEvolution.class));
        cards.add(new SetCardInfo("Emrakul's Evangel", 156, Rarity.RARE, mage.cards.e.EmrakulsEvangel.class));
        cards.add(new SetCardInfo("Emrakul's Influence", 157, Rarity.UNCOMMON, mage.cards.e.EmrakulsInfluence.class));
        cards.add(new SetCardInfo("Emrakul, the Promised End", 6, Rarity.MYTHIC, mage.cards.e.EmrakulThePromisedEnd.class));
        cards.add(new SetCardInfo("Enlightened Maniac", 58, Rarity.COMMON, mage.cards.e.EnlightenedManiac.class));
        cards.add(new SetCardInfo("Erupting Dreadwolf", 142, Rarity.UNCOMMON, mage.cards.e.EruptingDreadwolf.class));
        cards.add(new SetCardInfo("Eternal Scourge", 7, Rarity.RARE, mage.cards.e.EternalScourge.class));
        cards.add(new SetCardInfo("Extricator of Flesh", 23, Rarity.UNCOMMON, mage.cards.e.ExtricatorOfFlesh.class));
        cards.add(new SetCardInfo("Extricator of Sin", 23, Rarity.UNCOMMON, mage.cards.e.ExtricatorOfSin.class));
        cards.add(new SetCardInfo("Exultant Cultist", 59, Rarity.COMMON, mage.cards.e.ExultantCultist.class));
        cards.add(new SetCardInfo("Faith Unbroken", 24, Rarity.UNCOMMON, mage.cards.f.FaithUnbroken.class));
        cards.add(new SetCardInfo("Faithbearer Paladin", 25, Rarity.COMMON, mage.cards.f.FaithbearerPaladin.class));
        cards.add(new SetCardInfo("Falkenrath Reaver", 127, Rarity.COMMON, mage.cards.f.FalkenrathReaver.class));
        cards.add(new SetCardInfo("Fibrous Entangler", 174, Rarity.UNCOMMON, mage.cards.f.FibrousEntangler.class));
        cards.add(new SetCardInfo("Field Creeper", 195, Rarity.COMMON, mage.cards.f.FieldCreeper.class));
        cards.add(new SetCardInfo("Fiend Binder", 26, Rarity.COMMON, mage.cards.f.FiendBinder.class));
        cards.add(new SetCardInfo("Final Iteration", 56, Rarity.RARE, mage.cards.f.FinalIteration.class));
        cards.add(new SetCardInfo("Fogwalker", 60, Rarity.COMMON, mage.cards.f.Fogwalker.class));
        cards.add(new SetCardInfo("Fortune's Favor", 61, Rarity.UNCOMMON, mage.cards.f.FortunesFavor.class));
        cards.add(new SetCardInfo("Foul Emissary", 158, Rarity.UNCOMMON, mage.cards.f.FoulEmissary.class));
        cards.add(new SetCardInfo("Furyblade Vampire", 128, Rarity.UNCOMMON, mage.cards.f.FurybladeVampire.class));
        cards.add(new SetCardInfo("Galvanic Bombardment", 129, Rarity.COMMON, mage.cards.g.GalvanicBombardment.class));
        cards.add(new SetCardInfo("Gavony Unhallowed", 89, Rarity.COMMON, mage.cards.g.GavonyUnhallowed.class));
        cards.add(new SetCardInfo("Geier Reach Sanitarium", 203, Rarity.RARE, mage.cards.g.GeierReachSanitarium.class));
        cards.add(new SetCardInfo("Geist of the Archives", 62, Rarity.UNCOMMON, mage.cards.g.GeistOfTheArchives.class));
        cards.add(new SetCardInfo("Geist of the Lonely Vigil", 27, Rarity.UNCOMMON, mage.cards.g.GeistOfTheLonelyVigil.class));
        cards.add(new SetCardInfo("Geist-Fueled Scarecrow", 196, Rarity.UNCOMMON, mage.cards.g.GeistFueledScarecrow.class));
        cards.add(new SetCardInfo("Gisa and Geralf", 183, Rarity.MYTHIC, mage.cards.g.GisaAndGeralf.class));
        cards.add(new SetCardInfo("Gisela, the Broken Blade", 28, Rarity.MYTHIC, mage.cards.g.GiselaTheBrokenBlade.class));
        cards.add(new SetCardInfo("Give No Ground", 29, Rarity.UNCOMMON, mage.cards.g.GiveNoGround.class));
        cards.add(new SetCardInfo("Gnarlwood Dryad", 159, Rarity.UNCOMMON, mage.cards.g.GnarlwoodDryad.class));
        cards.add(new SetCardInfo("Graf Harvest", 90, Rarity.UNCOMMON, mage.cards.g.GrafHarvest.class));
        cards.add(new SetCardInfo("Graf Rats", 91, Rarity.COMMON, mage.cards.g.GrafRats.class));
        cards.add(new SetCardInfo("Grapple with the Past", 160, Rarity.COMMON, mage.cards.g.GrappleWithThePast.class));
        cards.add(new SetCardInfo("Grim Flayer", 184, Rarity.MYTHIC, mage.cards.g.GrimFlayer.class));
        cards.add(new SetCardInfo("Grisly Anglerfish", 63, Rarity.UNCOMMON, mage.cards.g.GrislyAnglerfish.class));
        cards.add(new SetCardInfo("Grizzled Angler", 63, Rarity.UNCOMMON, mage.cards.g.GrizzledAngler.class));
        cards.add(new SetCardInfo("Guardian of Pilgrims", 30, Rarity.COMMON, mage.cards.g.GuardianOfPilgrims.class));
        cards.add(new SetCardInfo("Hamlet Captain", 161, Rarity.UNCOMMON, mage.cards.h.HamletCaptain.class));
        cards.add(new SetCardInfo("Hanweir Battlements", 204, Rarity.RARE, mage.cards.h.HanweirBattlements.class));
        cards.add(new SetCardInfo("Hanweir Garrison", "130a", Rarity.RARE, mage.cards.h.HanweirGarrison.class));
        cards.add(new SetCardInfo("Hanweir, the Writhing Township", "130b", Rarity.RARE, mage.cards.h.HanweirTheWrithingTownship.class));
        cards.add(new SetCardInfo("Harmless Offering", 131, Rarity.RARE, mage.cards.h.HarmlessOffering.class));
        cards.add(new SetCardInfo("Haunted Dead", 92, Rarity.UNCOMMON, mage.cards.h.HauntedDead.class));
        cards.add(new SetCardInfo("Heron's Grace Champion", 185, Rarity.RARE, mage.cards.h.HeronsGraceChampion.class));
        cards.add(new SetCardInfo("Howling Chorus", 168, Rarity.UNCOMMON, mage.cards.h.HowlingChorus.class));
        cards.add(new SetCardInfo("Identity Thief", 64, Rarity.RARE, mage.cards.i.IdentityThief.class));
        cards.add(new SetCardInfo("Impetuous Devils", 132, Rarity.RARE, mage.cards.i.ImpetuousDevils.class));
        cards.add(new SetCardInfo("Imprisoned in the Moon", 65, Rarity.RARE, mage.cards.i.ImprisonedInTheMoon.class));
        cards.add(new SetCardInfo("Incendiary Flow", 133, Rarity.UNCOMMON, mage.cards.i.IncendiaryFlow.class));
        cards.add(new SetCardInfo("Ingenious Skaab", 66, Rarity.COMMON, mage.cards.i.IngeniousSkaab.class));
        cards.add(new SetCardInfo("Insatiable Gorgers", 134, Rarity.UNCOMMON, mage.cards.i.InsatiableGorgers.class));
        cards.add(new SetCardInfo("Ironclad Slayer", 31, Rarity.COMMON, mage.cards.i.IroncladSlayer.class));
        cards.add(new SetCardInfo("Ironwright's Cleansing", 32, Rarity.COMMON, mage.cards.i.IronwrightsCleansing.class));
        cards.add(new SetCardInfo("Ishkanah, Grafwidow", 162, Rarity.MYTHIC, mage.cards.i.IshkanahGrafwidow.class));
        cards.add(new SetCardInfo("It of the Horrid Swarm", 8, Rarity.COMMON, mage.cards.i.ItOfTheHorridSwarm.class));
        cards.add(new SetCardInfo("It That Rides as One", 33, Rarity.UNCOMMON, mage.cards.i.ItThatRidesAsOne.class));
        cards.add(new SetCardInfo("Kessig Prowler", 163, Rarity.UNCOMMON, mage.cards.k.KessigProwler.class));
        cards.add(new SetCardInfo("Laboratory Brute", 67, Rarity.COMMON, mage.cards.l.LaboratoryBrute.class));
        cards.add(new SetCardInfo("Lashweed Lurker", 9, Rarity.UNCOMMON, mage.cards.l.LashweedLurker.class));
        cards.add(new SetCardInfo("Liliana's Elite", 94, Rarity.UNCOMMON, mage.cards.l.LilianasElite.class));
        cards.add(new SetCardInfo("Liliana, the Last Hope", 93, Rarity.MYTHIC, mage.cards.l.LilianaTheLastHope.class));
        cards.add(new SetCardInfo("Lone Rider", 33, Rarity.UNCOMMON, mage.cards.l.LoneRider.class));
        cards.add(new SetCardInfo("Long Road Home", 34, Rarity.UNCOMMON, mage.cards.l.LongRoadHome.class));
        cards.add(new SetCardInfo("Lunar Force", 68, Rarity.UNCOMMON, mage.cards.l.LunarForce.class));
        cards.add(new SetCardInfo("Lunarch Mantle", 35, Rarity.COMMON, mage.cards.l.LunarchMantle.class));
        cards.add(new SetCardInfo("Lupine Prototype", 197, Rarity.RARE, mage.cards.l.LupinePrototype.class));
        cards.add(new SetCardInfo("Make Mischief", 135, Rarity.COMMON, mage.cards.m.MakeMischief.class));
        cards.add(new SetCardInfo("Markov Crusader", 95, Rarity.UNCOMMON, mage.cards.m.MarkovCrusader.class));
        cards.add(new SetCardInfo("Mausoleum Wanderer", 69, Rarity.RARE, mage.cards.m.MausoleumWanderer.class));
        cards.add(new SetCardInfo("Mercurial Geists", 186, Rarity.UNCOMMON, mage.cards.m.MercurialGeists.class));
        cards.add(new SetCardInfo("Midnight Scavengers", "96a", Rarity.COMMON, mage.cards.m.MidnightScavengers.class));
        cards.add(new SetCardInfo("Mind's Dilation", 70, Rarity.MYTHIC, mage.cards.m.MindsDilation.class));
        cards.add(new SetCardInfo("Mirrorwing Dragon", 136, Rarity.MYTHIC, mage.cards.m.MirrorwingDragon.class));
        cards.add(new SetCardInfo("Mockery of Nature", 10, Rarity.UNCOMMON, mage.cards.m.MockeryOfNature.class));
        cards.add(new SetCardInfo("Mournwillow", 187, Rarity.UNCOMMON, mage.cards.m.Mournwillow.class));
        cards.add(new SetCardInfo("Murder", 97, Rarity.UNCOMMON, mage.cards.m.Murder.class));
        cards.add(new SetCardInfo("Nahiri's Wrath", 137, Rarity.MYTHIC, mage.cards.n.NahirisWrath.class));
        cards.add(new SetCardInfo("Nebelgast Herald", 71, Rarity.UNCOMMON, mage.cards.n.NebelgastHerald.class));
        cards.add(new SetCardInfo("Nephalia Academy", 205, Rarity.UNCOMMON, mage.cards.n.NephaliaAcademy.class));
        cards.add(new SetCardInfo("Niblis of Frost", 72, Rarity.RARE, mage.cards.n.NiblisOfFrost.class));
        cards.add(new SetCardInfo("Noose Constrictor", 164, Rarity.UNCOMMON, mage.cards.n.NooseConstrictor.class));
        cards.add(new SetCardInfo("Noosegraf Mob", 98, Rarity.RARE, mage.cards.n.NoosegrafMob.class));
        cards.add(new SetCardInfo("Oath of Liliana", 99, Rarity.RARE, mage.cards.o.OathOfLiliana.class));
        cards.add(new SetCardInfo("Olivia's Dragoon", 100, Rarity.COMMON, mage.cards.o.OliviasDragoon.class));
        cards.add(new SetCardInfo("Otherworldly Outburst", 138, Rarity.COMMON, mage.cards.o.OtherworldlyOutburst.class));
        cards.add(new SetCardInfo("Peace of Mind", 36, Rarity.UNCOMMON, mage.cards.p.PeaceOfMind.class));
        cards.add(new SetCardInfo("Permeating Mass", 165, Rarity.RARE, mage.cards.p.PermeatingMass.class));
        cards.add(new SetCardInfo("Prey Upon", 166, Rarity.COMMON, mage.cards.p.PreyUpon.class));
        cards.add(new SetCardInfo("Primal Druid", 167, Rarity.COMMON, mage.cards.p.PrimalDruid.class));
        cards.add(new SetCardInfo("Prophetic Ravings", 139, Rarity.COMMON, mage.cards.p.PropheticRavings.class));
        cards.add(new SetCardInfo("Providence", 37, Rarity.RARE, mage.cards.p.Providence.class));
        cards.add(new SetCardInfo("Prying Questions", 101, Rarity.UNCOMMON, mage.cards.p.PryingQuestions.class));
        cards.add(new SetCardInfo("Repel the Abominable", 38, Rarity.UNCOMMON, mage.cards.r.RepelTheAbominable.class));
        cards.add(new SetCardInfo("Ride Down", 188, Rarity.UNCOMMON, mage.cards.r.RideDown.class));
        cards.add(new SetCardInfo("Rise from the Grave", 102, Rarity.UNCOMMON, mage.cards.r.RiseFromTheGrave.class));
        cards.add(new SetCardInfo("Ruthless Disposal", 103, Rarity.UNCOMMON, mage.cards.r.RuthlessDisposal.class));
        cards.add(new SetCardInfo("Sanctifier of Souls", 39, Rarity.RARE, mage.cards.s.SanctifierOfSouls.class));
        cards.add(new SetCardInfo("Savage Alliance", 140, Rarity.UNCOMMON, mage.cards.s.SavageAlliance.class));
        cards.add(new SetCardInfo("Scour the Laboratory", 73, Rarity.UNCOMMON, mage.cards.s.ScourTheLaboratory.class));
        cards.add(new SetCardInfo("Selfless Spirit", 40, Rarity.RARE, mage.cards.s.SelflessSpirit.class));
        cards.add(new SetCardInfo("Shreds of Sanity", 141, Rarity.UNCOMMON, mage.cards.s.ShredsOfSanity.class));
        cards.add(new SetCardInfo("Shrill Howler", 168, Rarity.UNCOMMON, mage.cards.s.ShrillHowler.class));
        cards.add(new SetCardInfo("Sigarda's Aid", 41, Rarity.RARE, mage.cards.s.SigardasAid.class));
        cards.add(new SetCardInfo("Sigardian Priest", 42, Rarity.COMMON, mage.cards.s.SigardianPriest.class));
        cards.add(new SetCardInfo("Sinuous Predator", 163, Rarity.UNCOMMON, mage.cards.s.SinuousPredator.class));
        cards.add(new SetCardInfo("Skirsdag Supplicant", 104, Rarity.COMMON, mage.cards.s.SkirsdagSupplicant.class));
        cards.add(new SetCardInfo("Slayer's Cleaver", 198, Rarity.UNCOMMON, mage.cards.s.SlayersCleaver.class));
        cards.add(new SetCardInfo("Smoldering Werewolf", 142, Rarity.UNCOMMON, mage.cards.s.SmolderingWerewolf.class));
        cards.add(new SetCardInfo("Somberwald Stag", 169, Rarity.UNCOMMON, mage.cards.s.SomberwaldStag.class));
        cards.add(new SetCardInfo("Soul Separator", 199, Rarity.RARE, mage.cards.s.SoulSeparator.class));
        cards.add(new SetCardInfo("Spectral Reserves", 43, Rarity.COMMON, mage.cards.s.SpectralReserves.class));
        cards.add(new SetCardInfo("Spell Queller", 189, Rarity.RARE, mage.cards.s.SpellQueller.class));
        cards.add(new SetCardInfo("Spirit of the Hunt", 170, Rarity.RARE, mage.cards.s.SpiritOfTheHunt.class));
        cards.add(new SetCardInfo("Splendid Reclamation", 171, Rarity.RARE, mage.cards.s.SplendidReclamation.class));
        cards.add(new SetCardInfo("Spontaneous Mutation", 74, Rarity.COMMON, mage.cards.s.SpontaneousMutation.class));
        cards.add(new SetCardInfo("Spreading Flames", 143, Rarity.UNCOMMON, mage.cards.s.SpreadingFlames.class));
        cards.add(new SetCardInfo("Springsage Ritual", 172, Rarity.COMMON, mage.cards.s.SpringsageRitual.class));
        cards.add(new SetCardInfo("Steadfast Cathar", 44, Rarity.COMMON, mage.cards.s.SteadfastCathar.class));
        cards.add(new SetCardInfo("Stensia Banquet", 144, Rarity.COMMON, mage.cards.s.StensiaBanquet.class));
        cards.add(new SetCardInfo("Stensia Innkeeper", 145, Rarity.COMMON, mage.cards.s.StensiaInnkeeper.class));
        cards.add(new SetCardInfo("Stitcher's Graft", 200, Rarity.RARE, mage.cards.s.StitchersGraft.class));
        cards.add(new SetCardInfo("Strange Augmentation", 105, Rarity.COMMON, mage.cards.s.StrangeAugmentation.class));
        cards.add(new SetCardInfo("Stromkirk Condemned", 106, Rarity.RARE, mage.cards.s.StromkirkCondemned.class));
        cards.add(new SetCardInfo("Stromkirk Occultist", 146, Rarity.RARE, mage.cards.s.StromkirkOccultist.class));
        cards.add(new SetCardInfo("Subjugator Angel", 45, Rarity.UNCOMMON, mage.cards.s.SubjugatorAngel.class));
        cards.add(new SetCardInfo("Succumb to Temptation", 107, Rarity.COMMON, mage.cards.s.SuccumbToTemptation.class));
        cards.add(new SetCardInfo("Summary Dismissal", 75, Rarity.RARE, mage.cards.s.SummaryDismissal.class));
        cards.add(new SetCardInfo("Swift Spinner", 173, Rarity.COMMON, mage.cards.s.SwiftSpinner.class));
        cards.add(new SetCardInfo("Take Inventory", 76, Rarity.COMMON, mage.cards.t.TakeInventory.class));
        cards.add(new SetCardInfo("Tamiyo, Field Researcher", 190, Rarity.MYTHIC, mage.cards.t.TamiyoFieldResearcher.class));
        cards.add(new SetCardInfo("Tangleclaw Werewolf", 174, Rarity.UNCOMMON, mage.cards.t.TangleclawWerewolf.class));
        cards.add(new SetCardInfo("Tattered Haunter", 77, Rarity.COMMON, mage.cards.t.TatteredHaunter.class));
        cards.add(new SetCardInfo("Terrarion", 201, Rarity.COMMON, mage.cards.t.Terrarion.class));
        cards.add(new SetCardInfo("Thalia's Lancers", 47, Rarity.RARE, mage.cards.t.ThaliasLancers.class));
        cards.add(new SetCardInfo("Thalia, Heretic Cathar", 46, Rarity.RARE, mage.cards.t.ThaliaHereticCathar.class));
        cards.add(new SetCardInfo("Thermo-Alchemist", 147, Rarity.COMMON, mage.cards.t.ThermoAlchemist.class));
        cards.add(new SetCardInfo("Thirsting Axe", 202, Rarity.UNCOMMON, mage.cards.t.ThirstingAxe.class));
        cards.add(new SetCardInfo("Thraben Foulbloods", 108, Rarity.COMMON, mage.cards.t.ThrabenFoulbloods.class));
        cards.add(new SetCardInfo("Thraben Standard Bearer", 48, Rarity.COMMON, mage.cards.t.ThrabenStandardBearer.class));
        cards.add(new SetCardInfo("Tree of Perdition", 109, Rarity.MYTHIC, mage.cards.t.TreeOfPerdition.class));
        cards.add(new SetCardInfo("Turn Aside", 78, Rarity.COMMON, mage.cards.t.TurnAside.class));
        cards.add(new SetCardInfo("Ulrich of the Krallenhorde", 191, Rarity.MYTHIC, mage.cards.u.UlrichOfTheKrallenhorde.class));
        cards.add(new SetCardInfo("Ulrich, Uncontested Alpha", 191, Rarity.MYTHIC, mage.cards.u.UlrichUncontestedAlpha.class));
        cards.add(new SetCardInfo("Ulvenwald Abomination", 175, Rarity.COMMON, mage.cards.u.UlvenwaldAbomination.class));
        cards.add(new SetCardInfo("Ulvenwald Captive", 175, Rarity.COMMON, mage.cards.u.UlvenwaldCaptive.class));
        cards.add(new SetCardInfo("Ulvenwald Observer", 176, Rarity.RARE, mage.cards.u.UlvenwaldObserver.class));
        cards.add(new SetCardInfo("Unsubstantiate", 79, Rarity.UNCOMMON, mage.cards.u.Unsubstantiate.class));
        cards.add(new SetCardInfo("Vampire Cutthroat", 110, Rarity.UNCOMMON, mage.cards.v.VampireCutthroat.class));
        cards.add(new SetCardInfo("Vexing Scuttler", 11, Rarity.UNCOMMON, mage.cards.v.VexingScuttler.class));
        cards.add(new SetCardInfo("Vildin-Pack Outcast", 148, Rarity.COMMON, mage.cards.v.VildinPackOutcast.class));
        cards.add(new SetCardInfo("Voldaren Pariah", 111, Rarity.RARE, mage.cards.v.VoldarenPariah.class));
        cards.add(new SetCardInfo("Voracious Reader", 54, Rarity.UNCOMMON, mage.cards.v.VoraciousReader.class));
        cards.add(new SetCardInfo("Wailing Ghoul", 112, Rarity.COMMON, mage.cards.w.WailingGhoul.class));
        cards.add(new SetCardInfo("Waxing Moon", 177, Rarity.COMMON, mage.cards.w.WaxingMoon.class));
        cards.add(new SetCardInfo("Weaver of Lightning", 149, Rarity.UNCOMMON, mage.cards.w.WeaverOfLightning.class));
        cards.add(new SetCardInfo("Weirded Vampire", 113, Rarity.COMMON, mage.cards.w.WeirdedVampire.class));
        cards.add(new SetCardInfo("Wharf Infiltrator", 80, Rarity.RARE, mage.cards.w.WharfInfiltrator.class));
        cards.add(new SetCardInfo("Whispers of Emrakul", 114, Rarity.UNCOMMON, mage.cards.w.WhispersOfEmrakul.class));
        cards.add(new SetCardInfo("Wolfkin Bond", 178, Rarity.COMMON, mage.cards.w.WolfkinBond.class));
        cards.add(new SetCardInfo("Woodcutter's Grit", 179, Rarity.COMMON, mage.cards.w.WoodcuttersGrit.class));
        cards.add(new SetCardInfo("Woodland Patrol", 180, Rarity.COMMON, mage.cards.w.WoodlandPatrol.class));
        cards.add(new SetCardInfo("Wretched Gryff", 12, Rarity.COMMON, mage.cards.w.WretchedGryff.class));
    }

    // add common or uncommon double faced card to booster
    // 60/120 packs contain one of 4 common DFCs and 60/120 packs contain one of 10 uncommon DFCs
    @Override
    protected void addDoubleFace(List<Card> booster) {
        Rarity rarity;
        for (int i = 0; i < numBoosterDoubleFaced; i++) {
            if (RandomUtil.nextInt(120) < 60) {
                rarity = Rarity.COMMON;
            } else {
                rarity = Rarity.UNCOMMON;
            }
            addToBooster(booster, getSpecialCardsByRarity(rarity));
        }
    }

    // Then about an eighth of the packs will have a second double-faced card, which will be a rare or mythic rare
    // 10/12 of such packs contain one of 5 rare DFCs and 2/12 packs contain one of 2 mythic DFCs
    @Override
    protected void addSpecialCards(List<Card> booster, int number) {
        // number is here always 1
        Rarity rarity;
        if (RandomUtil.nextInt(12) < 10) {
            rarity = Rarity.RARE;
        } else {
            rarity = Rarity.MYTHIC;
        }
        addToBooster(booster, getSpecialCardsByRarity(rarity));
    }

    // xmage doesn't recognize meldable cards as DFCs, so have to add them manually for now
    private static final String[] commonMeldCards = {"Graf Rats", "Midnight Scavengers"};
    private static final String[] rareMeldCards = {"Bruna, the Fading Light", "Hanweir Battlements", "Hanweir Garrison"};
    private static final String[] mythicMeldCards = {"Gisela, the Broken Blade"};

    @Override
    protected List<CardInfo> findSpecialCardsByRarity(Rarity rarity) {
        List<CardInfo> cardInfos = super.findSpecialCardsByRarity(rarity);
        String[] meldCardNames = {};
        if (rarity == Rarity.COMMON) {
            meldCardNames = commonMeldCards;
        } else if (rarity == Rarity.RARE) {
            meldCardNames = rareMeldCards;
        } else if (rarity == Rarity.MYTHIC) {
            meldCardNames = mythicMeldCards;
        }
        for (String name : meldCardNames) {
            cardInfos.addAll(CardRepository.instance.findCards(new CardCriteria()
                    .setCodes(this.code)
                    .nameExact(name)));
        }
        return cardInfos;
    }
}
