package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;
import mage.cards.repository.CardCriteria;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.collation.BoosterCollator;
import mage.collation.BoosterStructure;
import mage.collation.CardRun;
import mage.collation.RarityConfiguration;
import mage.util.RandomUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author TheElk801
 */
public final class MurdersAtKarlovManor extends ExpansionSet {

    private static final MurdersAtKarlovManor instance = new MurdersAtKarlovManor();

    public static MurdersAtKarlovManor getInstance() {
        return instance;
    }

    private MurdersAtKarlovManor() {
        super("Murders at Karlov Manor", "MKM", ExpansionSet.buildDate(2024, 2, 9), SetType.EXPANSION);
        this.blockName = "Murders at Karlov Manor"; // for sorting in GUI
        this.hasBasicLands = true;
        this.hasBoosters = true;

        cards.add(new SetCardInfo("A Killer Among Us", 167, Rarity.UNCOMMON, mage.cards.a.AKillerAmongUs.class));
        cards.add(new SetCardInfo("Absolving Lammasu", 2, Rarity.UNCOMMON, mage.cards.a.AbsolvingLammasu.class));
        cards.add(new SetCardInfo("Aftermath Analyst", 148, Rarity.UNCOMMON, mage.cards.a.AftermathAnalyst.class));
        cards.add(new SetCardInfo("Agency Coroner", 75, Rarity.COMMON, mage.cards.a.AgencyCoroner.class));
        cards.add(new SetCardInfo("Agency Outfitter", 38, Rarity.UNCOMMON, mage.cards.a.AgencyOutfitter.class));
        cards.add(new SetCardInfo("Agrus Kos, Spirit of Justice", 184, Rarity.MYTHIC, mage.cards.a.AgrusKosSpiritOfJustice.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Agrus Kos, Spirit of Justice", 354, Rarity.MYTHIC, mage.cards.a.AgrusKosSpiritOfJustice.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Agrus Kos, Spirit of Justice", 383, Rarity.MYTHIC, mage.cards.a.AgrusKosSpiritOfJustice.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Airtight Alibi", 149, Rarity.COMMON, mage.cards.a.AirtightAlibi.class));
        cards.add(new SetCardInfo("Alley Assailant", 76, Rarity.COMMON, mage.cards.a.AlleyAssailant.class));
        cards.add(new SetCardInfo("Alquist Proft, Master Sleuth", 185, Rarity.MYTHIC, mage.cards.a.AlquistProftMasterSleuth.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Alquist Proft, Master Sleuth", 355, Rarity.MYTHIC, mage.cards.a.AlquistProftMasterSleuth.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Alquist Proft, Master Sleuth", 384, Rarity.MYTHIC, mage.cards.a.AlquistProftMasterSleuth.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Analyze the Pollen", 150, Rarity.RARE, mage.cards.a.AnalyzeThePollen.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Analyze the Pollen", 308, Rarity.RARE, mage.cards.a.AnalyzeThePollen.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Anzrag's Rampage", 111, Rarity.RARE, mage.cards.a.AnzragsRampage.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Anzrag's Rampage", 303, Rarity.RARE, mage.cards.a.AnzragsRampage.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Anzrag, the Quake-Mole", 186, Rarity.MYTHIC, mage.cards.a.AnzragTheQuakeMole.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Anzrag, the Quake-Mole", 356, Rarity.MYTHIC, mage.cards.a.AnzragTheQuakeMole.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Anzrag, the Quake-Mole", 385, Rarity.MYTHIC, mage.cards.a.AnzragTheQuakeMole.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Archdruid's Charm", 151, Rarity.RARE, mage.cards.a.ArchdruidsCharm.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Archdruid's Charm", 408, Rarity.RARE, mage.cards.a.ArchdruidsCharm.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Assassin's Trophy", 187, Rarity.RARE, mage.cards.a.AssassinsTrophy.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Assassin's Trophy", 412, Rarity.RARE, mage.cards.a.AssassinsTrophy.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Assemble the Players", 287, Rarity.RARE, mage.cards.a.AssembleThePlayers.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Assemble the Players", 3, Rarity.RARE, mage.cards.a.AssembleThePlayers.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Audience with Trostani", 152, Rarity.RARE, mage.cards.a.AudienceWithTrostani.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Audience with Trostani", 309, Rarity.RARE, mage.cards.a.AudienceWithTrostani.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Aurelia's Vindicator", 336, Rarity.MYTHIC, mage.cards.a.AureliasVindicator.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Aurelia's Vindicator", 377, Rarity.MYTHIC, mage.cards.a.AureliasVindicator.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Aurelia's Vindicator", 4, Rarity.MYTHIC, mage.cards.a.AureliasVindicator.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Aurelia, the Law Above", "317z", Rarity.RARE, mage.cards.a.AureliaTheLawAbove.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Aurelia, the Law Above", 188, Rarity.RARE, mage.cards.a.AureliaTheLawAbove.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Aurelia, the Law Above", 317, Rarity.RARE, mage.cards.a.AureliaTheLawAbove.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Aurelia, the Law Above", 357, Rarity.RARE, mage.cards.a.AureliaTheLawAbove.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Auspicious Arrival", 288, Rarity.COMMON, mage.cards.a.AuspiciousArrival.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Auspicious Arrival", 5, Rarity.COMMON, mage.cards.a.AuspiciousArrival.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Axebane Ferox", 153, Rarity.RARE, mage.cards.a.AxebaneFerox.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Axebane Ferox", 409, Rarity.RARE, mage.cards.a.AxebaneFerox.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Axebane Ferox", 428, Rarity.RARE, mage.cards.a.AxebaneFerox.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Barbed Servitor", 398, Rarity.RARE, mage.cards.b.BarbedServitor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Barbed Servitor", 77, Rarity.RARE, mage.cards.b.BarbedServitor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Basilica Stalker", 78, Rarity.COMMON, mage.cards.b.BasilicaStalker.class));
        cards.add(new SetCardInfo("Behind the Mask", 39, Rarity.COMMON, mage.cards.b.BehindTheMask.class));
        cards.add(new SetCardInfo("Benthic Criminologists", 40, Rarity.COMMON, mage.cards.b.BenthicCriminologists.class));
        cards.add(new SetCardInfo("Bite Down on Crime", 154, Rarity.COMMON, mage.cards.b.BiteDownOnCrime.class));
        cards.add(new SetCardInfo("Blood Spatter Analysis", 189, Rarity.RARE, mage.cards.b.BloodSpatterAnalysis.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Blood Spatter Analysis", 413, Rarity.RARE, mage.cards.b.BloodSpatterAnalysis.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Bolrac-Clan Basher", 112, Rarity.UNCOMMON, mage.cards.b.BolracClanBasher.class));
        cards.add(new SetCardInfo("Branch of Vitu-Ghazi", 258, Rarity.UNCOMMON, mage.cards.b.BranchOfVituGhazi.class));
        cards.add(new SetCardInfo("Break Out", 190, Rarity.UNCOMMON, mage.cards.b.BreakOut.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Break Out", "190+", Rarity.UNCOMMON, mage.cards.b.BreakOut.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Bubble Smuggler", 41, Rarity.COMMON, mage.cards.b.BubbleSmuggler.class));
        cards.add(new SetCardInfo("Burden of Proof", 42, Rarity.UNCOMMON, mage.cards.b.BurdenOfProof.class));
        cards.add(new SetCardInfo("Buried in the Garden", 191, Rarity.UNCOMMON, mage.cards.b.BuriedInTheGarden.class));
        cards.add(new SetCardInfo("Call a Surprise Witness", 289, Rarity.UNCOMMON, mage.cards.c.CallASurpriseWitness.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Call a Surprise Witness", 6, Rarity.UNCOMMON, mage.cards.c.CallASurpriseWitness.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Candlestick", 43, Rarity.UNCOMMON, mage.cards.c.Candlestick.class));
        cards.add(new SetCardInfo("Case File Auditor", 7, Rarity.UNCOMMON, mage.cards.c.CaseFileAuditor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Case File Auditor", "7+", Rarity.UNCOMMON, mage.cards.c.CaseFileAuditor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Case of the Burning Masks", 113, Rarity.UNCOMMON, mage.cards.c.CaseOfTheBurningMasks.class));
        cards.add(new SetCardInfo("Case of the Crimson Pulse", 114, Rarity.RARE, mage.cards.c.CaseOfTheCrimsonPulse.class));
        cards.add(new SetCardInfo("Case of the Filched Falcon", 44, Rarity.UNCOMMON, mage.cards.c.CaseOfTheFilchedFalcon.class));
        cards.add(new SetCardInfo("Case of the Gateway Express", 8, Rarity.UNCOMMON, mage.cards.c.CaseOfTheGatewayExpress.class));
        cards.add(new SetCardInfo("Case of the Gorgon's Kiss", 79, Rarity.UNCOMMON, mage.cards.c.CaseOfTheGorgonsKiss.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Case of the Gorgon's Kiss", "79+", Rarity.UNCOMMON, mage.cards.c.CaseOfTheGorgonsKiss.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Case of the Locked Hothouse", 155, Rarity.RARE, mage.cards.c.CaseOfTheLockedHothouse.class));
        cards.add(new SetCardInfo("Case of the Pilfered Proof", 9, Rarity.UNCOMMON, mage.cards.c.CaseOfThePilferedProof.class));
        cards.add(new SetCardInfo("Case of the Ransacked Lab", 45, Rarity.RARE, mage.cards.c.CaseOfTheRansackedLab.class));
        cards.add(new SetCardInfo("Case of the Shattered Pact", 1, Rarity.UNCOMMON, mage.cards.c.CaseOfTheShatteredPact.class));
        cards.add(new SetCardInfo("Case of the Stashed Skeleton", 80, Rarity.RARE, mage.cards.c.CaseOfTheStashedSkeleton.class));
        cards.add(new SetCardInfo("Case of the Trampled Garden", 156, Rarity.UNCOMMON, mage.cards.c.CaseOfTheTrampledGarden.class));
        cards.add(new SetCardInfo("Case of the Uneaten Feast", 10, Rarity.RARE, mage.cards.c.CaseOfTheUneatenFeast.class));
        cards.add(new SetCardInfo("Caught Red-Handed", 115, Rarity.UNCOMMON, mage.cards.c.CaughtRedHanded.class));
        cards.add(new SetCardInfo("Cease // Desist", 246, Rarity.UNCOMMON, mage.cards.c.CeaseDesist.class));
        cards.add(new SetCardInfo("Cerebral Confiscation", 81, Rarity.COMMON, mage.cards.c.CerebralConfiscation.class));
        cards.add(new SetCardInfo("Chalk Outline", 157, Rarity.UNCOMMON, mage.cards.c.ChalkOutline.class));
        cards.add(new SetCardInfo("Clandestine Meddler", 82, Rarity.UNCOMMON, mage.cards.c.ClandestineMeddler.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Clandestine Meddler", "82+", Rarity.UNCOMMON, mage.cards.c.ClandestineMeddler.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Coerced to Kill", 192, Rarity.UNCOMMON, mage.cards.c.CoercedToKill.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Coerced to Kill", 311, Rarity.UNCOMMON, mage.cards.c.CoercedToKill.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Cold Case Cracker", 46, Rarity.COMMON, mage.cards.c.ColdCaseCracker.class));
        cards.add(new SetCardInfo("Commercial District", 259, Rarity.RARE, mage.cards.c.CommercialDistrict.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Commercial District", 324, Rarity.RARE, mage.cards.c.CommercialDistrict.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Concealed Weapon", 117, Rarity.UNCOMMON, mage.cards.c.ConcealedWeapon.class));
        cards.add(new SetCardInfo("Connecting the Dots", 118, Rarity.RARE, mage.cards.c.ConnectingTheDots.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Connecting the Dots", 403, Rarity.RARE, mage.cards.c.ConnectingTheDots.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Conspiracy Unraveler", 341, Rarity.MYTHIC, mage.cards.c.ConspiracyUnraveler.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Conspiracy Unraveler", 379, Rarity.MYTHIC, mage.cards.c.ConspiracyUnraveler.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Conspiracy Unraveler", 47, Rarity.MYTHIC, mage.cards.c.ConspiracyUnraveler.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Convenient Target", 119, Rarity.UNCOMMON, mage.cards.c.ConvenientTarget.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Convenient Target", 305, Rarity.UNCOMMON, mage.cards.c.ConvenientTarget.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Cornered Crook", 120, Rarity.UNCOMMON, mage.cards.c.CorneredCrook.class));
        cards.add(new SetCardInfo("Coveted Falcon", 393, Rarity.RARE, mage.cards.c.CovetedFalcon.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Coveted Falcon", 48, Rarity.RARE, mage.cards.c.CovetedFalcon.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Crime Novelist", 121, Rarity.UNCOMMON, mage.cards.c.CrimeNovelist.class));
        cards.add(new SetCardInfo("Crimestopper Sprite", 49, Rarity.COMMON, mage.cards.c.CrimestopperSprite.class));
        cards.add(new SetCardInfo("Crowd-Control Warden", 193, Rarity.COMMON, mage.cards.c.CrowdControlWarden.class));
        cards.add(new SetCardInfo("Cryptex", 251, Rarity.RARE, mage.cards.c.Cryptex.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Cryptex", 422, Rarity.RARE, mage.cards.c.Cryptex.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Cryptic Coat", 394, Rarity.RARE, mage.cards.c.CrypticCoat.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Cryptic Coat", 50, Rarity.RARE, mage.cards.c.CrypticCoat.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Culvert Ambusher", 158, Rarity.UNCOMMON, mage.cards.c.CulvertAmbusher.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Culvert Ambusher", 351, Rarity.UNCOMMON, mage.cards.c.CulvertAmbusher.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Curious Cadaver", 194, Rarity.UNCOMMON, mage.cards.c.CuriousCadaver.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Curious Cadaver", 358, Rarity.UNCOMMON, mage.cards.c.CuriousCadaver.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Curious Inquiry", 51, Rarity.UNCOMMON, mage.cards.c.CuriousInquiry.class));
        cards.add(new SetCardInfo("Deadly Complication", 195, Rarity.UNCOMMON, mage.cards.d.DeadlyComplication.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Deadly Complication", 312, Rarity.UNCOMMON, mage.cards.d.DeadlyComplication.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Deadly Cover-Up", 399, Rarity.RARE, mage.cards.d.DeadlyCoverUp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Deadly Cover-Up", 83, Rarity.RARE, mage.cards.d.DeadlyCoverUp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Deduce", 293, Rarity.COMMON, mage.cards.d.Deduce.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Deduce", 52, Rarity.COMMON, mage.cards.d.Deduce.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Defenestrated Phantom", 11, Rarity.COMMON, mage.cards.d.DefenestratedPhantom.class));
        cards.add(new SetCardInfo("Delney, Streetwise Lookout", 12, Rarity.MYTHIC, mage.cards.d.DelneyStreetwiseLookout.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Delney, Streetwise Lookout", 337, Rarity.MYTHIC, mage.cards.d.DelneyStreetwiseLookout.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Delney, Streetwise Lookout", 378, Rarity.MYTHIC, mage.cards.d.DelneyStreetwiseLookout.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Demand Answers", 122, Rarity.COMMON, mage.cards.d.DemandAnswers.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Demand Answers", 306, Rarity.COMMON, mage.cards.d.DemandAnswers.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Detective's Satchel", 196, Rarity.UNCOMMON, mage.cards.d.DetectivesSatchel.class));
        cards.add(new SetCardInfo("Dog Walker", 197, Rarity.COMMON, mage.cards.d.DogWalker.class));
        cards.add(new SetCardInfo("Doorkeeper Thrull", 13, Rarity.RARE, mage.cards.d.DoorkeeperThrull.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Doorkeeper Thrull", 338, Rarity.RARE, mage.cards.d.DoorkeeperThrull.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Doppelgang", 198, Rarity.RARE, mage.cards.d.Doppelgang.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Doppelgang", 414, Rarity.RARE, mage.cards.d.Doppelgang.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Drag the Canal", 199, Rarity.RARE, mage.cards.d.DragTheCanal.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Drag the Canal", 415, Rarity.RARE, mage.cards.d.DragTheCanal.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dramatic Accusation", 294, Rarity.COMMON, mage.cards.d.DramaticAccusation.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dramatic Accusation", 53, Rarity.COMMON, mage.cards.d.DramaticAccusation.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Due Diligence", 14, Rarity.COMMON, mage.cards.d.DueDiligence.class));
        cards.add(new SetCardInfo("Elegant Parlor", 260, Rarity.RARE, mage.cards.e.ElegantParlor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Elegant Parlor", 325, Rarity.RARE, mage.cards.e.ElegantParlor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Eliminate the Impossible", 54, Rarity.UNCOMMON, mage.cards.e.EliminateTheImpossible.class));
        cards.add(new SetCardInfo("Escape Tunnel", 261, Rarity.COMMON, mage.cards.e.EscapeTunnel.class));
        cards.add(new SetCardInfo("Essence of Antiquity", 15, Rarity.UNCOMMON, mage.cards.e.EssenceOfAntiquity.class));
        cards.add(new SetCardInfo("Etrata, Deadly Fugitive", 200, Rarity.MYTHIC, mage.cards.e.EtrataDeadlyFugitive.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Etrata, Deadly Fugitive", 359, Rarity.MYTHIC, mage.cards.e.EtrataDeadlyFugitive.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Etrata, Deadly Fugitive", 386, Rarity.MYTHIC, mage.cards.e.EtrataDeadlyFugitive.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Evidence Examiner", 201, Rarity.UNCOMMON, mage.cards.e.EvidenceExaminer.class));
        cards.add(new SetCardInfo("Exit Specialist", 55, Rarity.UNCOMMON, mage.cards.e.ExitSpecialist.class));
        cards.add(new SetCardInfo("Expedited Inheritance", 123, Rarity.MYTHIC, mage.cards.e.ExpeditedInheritance.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Expedited Inheritance", 404, Rarity.MYTHIC, mage.cards.e.ExpeditedInheritance.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Expose the Culprit", 124, Rarity.UNCOMMON, mage.cards.e.ExposeTheCulprit.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Expose the Culprit", 307, Rarity.UNCOMMON, mage.cards.e.ExposeTheCulprit.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Extract a Confession", 84, Rarity.COMMON, mage.cards.e.ExtractAConfession.class));
        cards.add(new SetCardInfo("Ezrim, Agency Chief", 202, Rarity.RARE, mage.cards.e.EzrimAgencyChief.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ezrim, Agency Chief", 360, Rarity.RARE, mage.cards.e.EzrimAgencyChief.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Fae Flight", 295, Rarity.UNCOMMON, mage.cards.f.FaeFlight.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Fae Flight", 56, Rarity.UNCOMMON, mage.cards.f.FaeFlight.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Faerie Snoop", 203, Rarity.COMMON, mage.cards.f.FaerieSnoop.class));
        cards.add(new SetCardInfo("Fanatical Strength", 159, Rarity.COMMON, mage.cards.f.FanaticalStrength.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Fanatical Strength", 310, Rarity.COMMON, mage.cards.f.FanaticalStrength.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Felonious Rage", 125, Rarity.COMMON, mage.cards.f.FeloniousRage.class));
        cards.add(new SetCardInfo("Festerleech", 85, Rarity.UNCOMMON, mage.cards.f.Festerleech.class));
        cards.add(new SetCardInfo("Flotsam // Jetsam", 247, Rarity.UNCOMMON, mage.cards.f.FlotsamJetsam.class));
        cards.add(new SetCardInfo("Flourishing Bloom-Kin", 160, Rarity.UNCOMMON, mage.cards.f.FlourishingBloomKin.class));
        cards.add(new SetCardInfo("Forensic Gadgeteer", 342, Rarity.RARE, mage.cards.f.ForensicGadgeteer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forensic Gadgeteer", 57, Rarity.RARE, mage.cards.f.ForensicGadgeteer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forensic Researcher", 58, Rarity.UNCOMMON, mage.cards.f.ForensicResearcher.class));
        cards.add(new SetCardInfo("Forest", 276, Rarity.LAND, mage.cards.basiclands.Forest.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Forest", 285, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 286, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forum Familiar", 16, Rarity.UNCOMMON, mage.cards.f.ForumFamiliar.class));
        cards.add(new SetCardInfo("Frantic Scapegoat", 126, Rarity.UNCOMMON, mage.cards.f.FranticScapegoat.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Frantic Scapegoat", 347, Rarity.UNCOMMON, mage.cards.f.FranticScapegoat.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Fugitive Codebreaker", 127, Rarity.RARE, mage.cards.f.FugitiveCodebreaker.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Fugitive Codebreaker", 348, Rarity.RARE, mage.cards.f.FugitiveCodebreaker.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Furtive Courier", 59, Rarity.UNCOMMON, mage.cards.f.FurtiveCourier.class));
        cards.add(new SetCardInfo("Fuss // Bother", 248, Rarity.UNCOMMON, mage.cards.f.FussBother.class));
        cards.add(new SetCardInfo("Gadget Technician", 204, Rarity.COMMON, mage.cards.g.GadgetTechnician.class));
        cards.add(new SetCardInfo("Galvanize", 128, Rarity.COMMON, mage.cards.g.Galvanize.class));
        cards.add(new SetCardInfo("Gearbane Orangutan", 129, Rarity.COMMON, mage.cards.g.GearbaneOrangutan.class));
        cards.add(new SetCardInfo("Get a Leg Up", 161, Rarity.UNCOMMON, mage.cards.g.GetALegUp.class));
        cards.add(new SetCardInfo("Gleaming Geardrake", 205, Rarity.UNCOMMON, mage.cards.g.GleamingGeardrake.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gleaming Geardrake", 361, Rarity.UNCOMMON, mage.cards.g.GleamingGeardrake.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gleaming Geardrake", 424, Rarity.UNCOMMON, mage.cards.g.GleamingGeardrake.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Glint Weaver", 162, Rarity.UNCOMMON, mage.cards.g.GlintWeaver.class));
        cards.add(new SetCardInfo("Goblin Maskmaker", 130, Rarity.COMMON, mage.cards.g.GoblinMaskmaker.class));
        cards.add(new SetCardInfo("Granite Witness", 206, Rarity.COMMON, mage.cards.g.GraniteWitness.class));
        cards.add(new SetCardInfo("Gravestone Strider", 252, Rarity.COMMON, mage.cards.g.GravestoneStrider.class));
        cards.add(new SetCardInfo("Greenbelt Radical", 163, Rarity.UNCOMMON, mage.cards.g.GreenbeltRadical.class));
        cards.add(new SetCardInfo("Griffnaut Tracker", 17, Rarity.COMMON, mage.cards.g.GriffnautTracker.class));
        cards.add(new SetCardInfo("Haazda Vigilante", 18, Rarity.COMMON, mage.cards.h.HaazdaVigilante.class));
        cards.add(new SetCardInfo("Hard-Hitting Question", 164, Rarity.UNCOMMON, mage.cards.h.HardHittingQuestion.class));
        cards.add(new SetCardInfo("Harried Dronesmith", 131, Rarity.UNCOMMON, mage.cards.h.HarriedDronesmith.class));
        cards.add(new SetCardInfo("Hedge Maze", 262, Rarity.RARE, mage.cards.h.HedgeMaze.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hedge Maze", 326, Rarity.RARE, mage.cards.h.HedgeMaze.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hedge Whisperer", 165, Rarity.UNCOMMON, mage.cards.h.HedgeWhisperer.class));
        cards.add(new SetCardInfo("Hide in Plain Sight", 166, Rarity.RARE, mage.cards.h.HideInPlainSight.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hide in Plain Sight", 410, Rarity.RARE, mage.cards.h.HideInPlainSight.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Homicide Investigator", 343, Rarity.RARE, mage.cards.h.HomicideInvestigator.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Homicide Investigator", 86, Rarity.RARE, mage.cards.h.HomicideInvestigator.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hotshot Investigators", 60, Rarity.COMMON, mage.cards.h.HotshotInvestigators.class));
        cards.add(new SetCardInfo("Hunted Bonebrute", 400, Rarity.RARE, mage.cards.h.HuntedBonebrute.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hunted Bonebrute", 87, Rarity.RARE, mage.cards.h.HuntedBonebrute.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hustle // Bustle", 249, Rarity.UNCOMMON, mage.cards.h.HustleBustle.class));
        cards.add(new SetCardInfo("Ill-Timed Explosion", 207, Rarity.RARE, mage.cards.i.IllTimedExplosion.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ill-Timed Explosion", 416, Rarity.RARE, mage.cards.i.IllTimedExplosion.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Illicit Masquerade", 401, Rarity.RARE, mage.cards.i.IllicitMasquerade.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Illicit Masquerade", 88, Rarity.RARE, mage.cards.i.IllicitMasquerade.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Incinerator of the Guilty", 132, Rarity.MYTHIC, mage.cards.i.IncineratorOfTheGuilty.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Incinerator of the Guilty", 349, Rarity.MYTHIC, mage.cards.i.IncineratorOfTheGuilty.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Incinerator of the Guilty", 381, Rarity.MYTHIC, mage.cards.i.IncineratorOfTheGuilty.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Innocent Bystander", 133, Rarity.COMMON, mage.cards.i.InnocentBystander.class));
        cards.add(new SetCardInfo("Inside Source", 19, Rarity.COMMON, mage.cards.i.InsideSource.class));
        cards.add(new SetCardInfo("Insidious Roots", 208, Rarity.UNCOMMON, mage.cards.i.InsidiousRoots.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Insidious Roots", 313, Rarity.UNCOMMON, mage.cards.i.InsidiousRoots.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Intrude on the Mind", 296, Rarity.MYTHIC, mage.cards.i.IntrudeOnTheMind.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Intrude on the Mind", 61, Rarity.MYTHIC, mage.cards.i.IntrudeOnTheMind.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 273, Rarity.LAND, mage.cards.basiclands.Island.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Island", 279, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 280, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("It Doesn't Add Up", 299, Rarity.UNCOMMON, mage.cards.i.ItDoesntAddUp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("It Doesn't Add Up", 89, Rarity.UNCOMMON, mage.cards.i.ItDoesntAddUp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Izoni, Center of the Web", 209, Rarity.RARE, mage.cards.i.IzoniCenterOfTheWeb.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Izoni, Center of the Web", 362, Rarity.RARE, mage.cards.i.IzoniCenterOfTheWeb.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Jaded Analyst", 62, Rarity.COMMON, mage.cards.j.JadedAnalyst.class));
        cards.add(new SetCardInfo("Judith, Carnage Connoisseur", 210, Rarity.RARE, mage.cards.j.JudithCarnageConnoisseur.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Judith, Carnage Connoisseur", 363, Rarity.RARE, mage.cards.j.JudithCarnageConnoisseur.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Karlov Watchdog", 20, Rarity.UNCOMMON, mage.cards.k.KarlovWatchdog.class));
        cards.add(new SetCardInfo("Kaya, Spirits' Justice", 211, Rarity.MYTHIC, mage.cards.k.KayaSpiritsJustice.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kaya, Spirits' Justice", 335, Rarity.MYTHIC, mage.cards.k.KayaSpiritsJustice.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kellan, Inquisitive Prodigy", 212, Rarity.RARE, mage.cards.k.KellanInquisitiveProdigy.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kellan, Inquisitive Prodigy", 334, Rarity.RARE, mage.cards.k.KellanInquisitiveProdigy.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Knife", 134, Rarity.UNCOMMON, mage.cards.k.Knife.class));
        cards.add(new SetCardInfo("Kraul Whipcracker", 213, Rarity.UNCOMMON, mage.cards.k.KraulWhipcracker.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kraul Whipcracker", "213+", Rarity.UNCOMMON, mage.cards.k.KraulWhipcracker.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kraul Whipcracker", 364, Rarity.UNCOMMON, mage.cards.k.KraulWhipcracker.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kraul Whipcracker", 425, Rarity.UNCOMMON, mage.cards.k.KraulWhipcracker.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Krenko's Buzzcrusher", 136, Rarity.RARE, mage.cards.k.KrenkosBuzzcrusher.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Krenko's Buzzcrusher", 405, Rarity.RARE, mage.cards.k.KrenkosBuzzcrusher.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Krenko, Baron of Tin Street", 135, Rarity.RARE, mage.cards.k.KrenkoBaronOfTinStreet.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Krenko, Baron of Tin Street", 350, Rarity.RARE, mage.cards.k.KrenkoBaronOfTinStreet.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Krovod Haunch", 21, Rarity.UNCOMMON, mage.cards.k.KrovodHaunch.class));
        cards.add(new SetCardInfo("Kylox's Voltstrider", 215, Rarity.MYTHIC, mage.cards.k.KyloxsVoltstrider.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kylox's Voltstrider", 417, Rarity.MYTHIC, mage.cards.k.KyloxsVoltstrider.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kylox, Visionary Inventor", 214, Rarity.RARE, mage.cards.k.KyloxVisionaryInventor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kylox, Visionary Inventor", 365, Rarity.RARE, mage.cards.k.KyloxVisionaryInventor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Lamplight Phoenix", 137, Rarity.RARE, mage.cards.l.LamplightPhoenix.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Lamplight Phoenix", 406, Rarity.RARE, mage.cards.l.LamplightPhoenix.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Lazav, Wearer of Faces", "318z", Rarity.RARE, mage.cards.l.LazavWearerOfFaces.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Lazav, Wearer of Faces", 216, Rarity.RARE, mage.cards.l.LazavWearerOfFaces.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Lazav, Wearer of Faces", 318, Rarity.RARE, mage.cards.l.LazavWearerOfFaces.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Lazav, Wearer of Faces", 366, Rarity.RARE, mage.cards.l.LazavWearerOfFaces.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Lead Pipe", 90, Rarity.UNCOMMON, mage.cards.l.LeadPipe.class));
        cards.add(new SetCardInfo("Leering Onlooker", 91, Rarity.UNCOMMON, mage.cards.l.LeeringOnlooker.class));
        cards.add(new SetCardInfo("Leyline of the Guildpact", 217, Rarity.RARE, mage.cards.l.LeylineOfTheGuildpact.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Leyline of the Guildpact", 418, Rarity.RARE, mage.cards.l.LeylineOfTheGuildpact.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Lightning Helix", 218, Rarity.UNCOMMON, mage.cards.l.LightningHelix.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Lightning Helix", 426, Rarity.UNCOMMON, mage.cards.l.LightningHelix.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Living Conundrum", 63, Rarity.UNCOMMON, mage.cards.l.LivingConundrum.class));
        cards.add(new SetCardInfo("Long Goodbye", 423, Rarity.UNCOMMON, mage.cards.l.LongGoodbye.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Long Goodbye", 92, Rarity.UNCOMMON, mage.cards.l.LongGoodbye.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Lost in the Maze", 395, Rarity.RARE, mage.cards.l.LostInTheMaze.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Lost in the Maze", 64, Rarity.RARE, mage.cards.l.LostInTheMaze.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Loxodon Eavesdropper", 168, Rarity.COMMON, mage.cards.l.LoxodonEavesdropper.class));
        cards.add(new SetCardInfo("Lumbering Laundry", 253, Rarity.UNCOMMON, mage.cards.l.LumberingLaundry.class));
        cards.add(new SetCardInfo("Lush Portico", 263, Rarity.RARE, mage.cards.l.LushPortico.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Lush Portico", 327, Rarity.RARE, mage.cards.l.LushPortico.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Macabre Reconstruction", 93, Rarity.COMMON, mage.cards.m.MacabreReconstruction.class));
        cards.add(new SetCardInfo("Magnetic Snuffler", 254, Rarity.UNCOMMON, mage.cards.m.MagneticSnuffler.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Magnetic Snuffler", 376, Rarity.UNCOMMON, mage.cards.m.MagneticSnuffler.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Magnifying Glass", 255, Rarity.COMMON, mage.cards.m.MagnifyingGlass.class));
        cards.add(new SetCardInfo("Make Your Move", 22, Rarity.COMMON, mage.cards.m.MakeYourMove.class));
        cards.add(new SetCardInfo("Makeshift Binding", 23, Rarity.COMMON, mage.cards.m.MakeshiftBinding.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Makeshift Binding", 290, Rarity.COMMON, mage.cards.m.MakeshiftBinding.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Marketwatch Phantom", 24, Rarity.COMMON, mage.cards.m.MarketwatchPhantom.class));
        cards.add(new SetCardInfo("Massacre Girl, Known Killer", 344, Rarity.MYTHIC, mage.cards.m.MassacreGirlKnownKiller.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Massacre Girl, Known Killer", 380, Rarity.MYTHIC, mage.cards.m.MassacreGirlKnownKiller.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Massacre Girl, Known Killer", 94, Rarity.MYTHIC, mage.cards.m.MassacreGirlKnownKiller.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Meddling Youths", 219, Rarity.UNCOMMON, mage.cards.m.MeddlingYouths.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Meddling Youths", 367, Rarity.UNCOMMON, mage.cards.m.MeddlingYouths.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Melek, Reforged Researcher", 430, Rarity.MYTHIC, mage.cards.m.MelekReforgedResearcher.class));
        cards.add(new SetCardInfo("Meticulous Archive", 264, Rarity.RARE, mage.cards.m.MeticulousArchive.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Meticulous Archive", 328, Rarity.RARE, mage.cards.m.MeticulousArchive.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mistway Spy", 65, Rarity.UNCOMMON, mage.cards.m.MistwaySpy.class));
        cards.add(new SetCardInfo("Mountain", 275, Rarity.LAND, mage.cards.basiclands.Mountain.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 283, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 284, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Murder", 300, Rarity.COMMON, mage.cards.m.Murder.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Murder", 95, Rarity.COMMON, mage.cards.m.Murder.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Museum Nightwatch", 25, Rarity.COMMON, mage.cards.m.MuseumNightwatch.class));
        cards.add(new SetCardInfo("Neighborhood Guardian", 26, Rarity.UNCOMMON, mage.cards.n.NeighborhoodGuardian.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Neighborhood Guardian", 339, Rarity.UNCOMMON, mage.cards.n.NeighborhoodGuardian.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Nervous Gardener", 169, Rarity.COMMON, mage.cards.n.NervousGardener.class));
        cards.add(new SetCardInfo("Nightdrinker Moroii", 96, Rarity.UNCOMMON, mage.cards.n.NightdrinkerMoroii.class));
        cards.add(new SetCardInfo("Niv-Mizzet, Guildpact", "319z", Rarity.RARE, mage.cards.n.NivMizzetGuildpact.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Niv-Mizzet, Guildpact", 220, Rarity.RARE, mage.cards.n.NivMizzetGuildpact.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Niv-Mizzet, Guildpact", 319, Rarity.RARE, mage.cards.n.NivMizzetGuildpact.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Niv-Mizzet, Guildpact", 368, Rarity.RARE, mage.cards.n.NivMizzetGuildpact.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("No More Lies", 221, Rarity.UNCOMMON, mage.cards.n.NoMoreLies.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("No More Lies", 427, Rarity.UNCOMMON, mage.cards.n.NoMoreLies.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("No Witnesses", 27, Rarity.RARE, mage.cards.n.NoWitnesses.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("No Witnesses", 390, Rarity.RARE, mage.cards.n.NoWitnesses.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Not on My Watch", 28, Rarity.UNCOMMON, mage.cards.n.NotOnMyWatch.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Not on My Watch", 291, Rarity.UNCOMMON, mage.cards.n.NotOnMyWatch.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Novice Inspector", 29, Rarity.COMMON, mage.cards.n.NoviceInspector.class));
        cards.add(new SetCardInfo("Offender at Large", 138, Rarity.COMMON, mage.cards.o.OffenderAtLarge.class));
        cards.add(new SetCardInfo("Officious Interrogation", 222, Rarity.RARE, mage.cards.o.OfficiousInterrogation.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Officious Interrogation", 314, Rarity.RARE, mage.cards.o.OfficiousInterrogation.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("On the Job", 292, Rarity.COMMON, mage.cards.o.OnTheJob.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("On the Job", 30, Rarity.COMMON, mage.cards.o.OnTheJob.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Out Cold", 66, Rarity.COMMON, mage.cards.o.OutCold.class));
        cards.add(new SetCardInfo("Outrageous Robbery", 402, Rarity.RARE, mage.cards.o.OutrageousRobbery.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Outrageous Robbery", 97, Rarity.RARE, mage.cards.o.OutrageousRobbery.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Perimeter Enforcer", 31, Rarity.UNCOMMON, mage.cards.p.PerimeterEnforcer.class));
        cards.add(new SetCardInfo("Person of Interest", 139, Rarity.COMMON, mage.cards.p.PersonOfInterest.class));
        cards.add(new SetCardInfo("Persuasive Interrogators", 345, Rarity.UNCOMMON, mage.cards.p.PersuasiveInterrogators.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Persuasive Interrogators", 98, Rarity.UNCOMMON, mage.cards.p.PersuasiveInterrogators.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Pick Your Poison", 170, Rarity.COMMON, mage.cards.p.PickYourPoison.class));
        cards.add(new SetCardInfo("Plains", 272, Rarity.LAND, mage.cards.basiclands.Plains.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Plains", 277, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 278, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Polygraph Orb", 99, Rarity.UNCOMMON, mage.cards.p.PolygraphOrb.class));
        cards.add(new SetCardInfo("Pompous Gadabout", 171, Rarity.UNCOMMON, mage.cards.p.PompousGadabout.class));
        cards.add(new SetCardInfo("Presumed Dead", 100, Rarity.UNCOMMON, mage.cards.p.PresumedDead.class));
        cards.add(new SetCardInfo("Private Eye", 223, Rarity.UNCOMMON, mage.cards.p.PrivateEye.class));
        cards.add(new SetCardInfo("Proft's Eidetic Memory", 396, Rarity.RARE, mage.cards.p.ProftsEideticMemory.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Proft's Eidetic Memory", 67, Rarity.RARE, mage.cards.p.ProftsEideticMemory.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Projektor Inspector", 68, Rarity.COMMON, mage.cards.p.ProjektorInspector.class));
        cards.add(new SetCardInfo("Public Thoroughfare", 265, Rarity.COMMON, mage.cards.p.PublicThoroughfare.class));
        cards.add(new SetCardInfo("Push // Pull", 250, Rarity.UNCOMMON, mage.cards.p.PushPull.class));
        cards.add(new SetCardInfo("Pyrotechnic Performer", 140, Rarity.RARE, mage.cards.p.PyrotechnicPerformer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Pyrotechnic Performer", 407, Rarity.RARE, mage.cards.p.PyrotechnicPerformer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Rakdos, Patron of Chaos", "320z", Rarity.MYTHIC, mage.cards.r.RakdosPatronOfChaos.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Rakdos, Patron of Chaos", 224, Rarity.MYTHIC, mage.cards.r.RakdosPatronOfChaos.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Rakdos, Patron of Chaos", 320, Rarity.MYTHIC, mage.cards.r.RakdosPatronOfChaos.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Rakdos, Patron of Chaos", 369, Rarity.MYTHIC, mage.cards.r.RakdosPatronOfChaos.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Rakdos, Patron of Chaos", 387, Rarity.MYTHIC, mage.cards.r.RakdosPatronOfChaos.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Rakish Scoundrel", 225, Rarity.COMMON, mage.cards.r.RakishScoundrel.class));
        cards.add(new SetCardInfo("Raucous Theater", 266, Rarity.RARE, mage.cards.r.RaucousTheater.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Raucous Theater", 329, Rarity.RARE, mage.cards.r.RaucousTheater.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Reasonable Doubt", 69, Rarity.COMMON, mage.cards.r.ReasonableDoubt.class));
        cards.add(new SetCardInfo("Reckless Detective", 141, Rarity.UNCOMMON, mage.cards.r.RecklessDetective.class));
        cards.add(new SetCardInfo("Red Herring", 142, Rarity.COMMON, mage.cards.r.RedHerring.class));
        cards.add(new SetCardInfo("Reenact the Crime", 297, Rarity.RARE, mage.cards.r.ReenactTheCrime.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Reenact the Crime", 70, Rarity.RARE, mage.cards.r.ReenactTheCrime.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Relive the Past", 226, Rarity.RARE, mage.cards.r.ReliveThePast.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Relive the Past", 419, Rarity.RARE, mage.cards.r.ReliveThePast.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Repeat Offender", 101, Rarity.COMMON, mage.cards.r.RepeatOffender.class));
        cards.add(new SetCardInfo("Repulsive Mutation", 227, Rarity.UNCOMMON, mage.cards.r.RepulsiveMutation.class));
        cards.add(new SetCardInfo("Riftburst Hellion", 228, Rarity.COMMON, mage.cards.r.RiftburstHellion.class));
        cards.add(new SetCardInfo("Rope", 173, Rarity.UNCOMMON, mage.cards.r.Rope.class));
        cards.add(new SetCardInfo("Rot Farm Mortipede", 102, Rarity.COMMON, mage.cards.r.RotFarmMortipede.class));
        cards.add(new SetCardInfo("Rubblebelt Braggart", 143, Rarity.COMMON, mage.cards.r.RubblebeltBraggart.class));
        cards.add(new SetCardInfo("Rubblebelt Maverick", 174, Rarity.COMMON, mage.cards.r.RubblebeltMaverick.class));
        cards.add(new SetCardInfo("Rune-Brand Juggler", 229, Rarity.UNCOMMON, mage.cards.r.RuneBrandJuggler.class));
        cards.add(new SetCardInfo("Sample Collector", 175, Rarity.UNCOMMON, mage.cards.s.SampleCollector.class));
        cards.add(new SetCardInfo("Sanctuary Wall", 32, Rarity.UNCOMMON, mage.cards.s.SanctuaryWall.class));
        cards.add(new SetCardInfo("Sanguine Savior", 230, Rarity.COMMON, mage.cards.s.SanguineSavior.class));
        cards.add(new SetCardInfo("Sanitation Automaton", 256, Rarity.COMMON, mage.cards.s.SanitationAutomaton.class));
        cards.add(new SetCardInfo("Scene of the Crime", 267, Rarity.UNCOMMON, mage.cards.s.SceneOfTheCrime.class));
        cards.add(new SetCardInfo("Seasoned Consultant", 33, Rarity.COMMON, mage.cards.s.SeasonedConsultant.class));
        cards.add(new SetCardInfo("Shadowy Backstreet", 268, Rarity.RARE, mage.cards.s.ShadowyBackstreet.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Shadowy Backstreet", 330, Rarity.RARE, mage.cards.s.ShadowyBackstreet.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Shady Informant", 231, Rarity.COMMON, mage.cards.s.ShadyInformant.class));
        cards.add(new SetCardInfo("Sharp-Eyed Rookie", 176, Rarity.RARE, mage.cards.s.SharpEyedRookie.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sharp-Eyed Rookie", 353, Rarity.RARE, mage.cards.s.SharpEyedRookie.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Shock", 144, Rarity.COMMON, mage.cards.s.Shock.class));
        cards.add(new SetCardInfo("Slice from the Shadows", 103, Rarity.COMMON, mage.cards.s.SliceFromTheShadows.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Slice from the Shadows", 301, Rarity.COMMON, mage.cards.s.SliceFromTheShadows.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Slime Against Humanity", 177, Rarity.COMMON, mage.cards.s.SlimeAgainstHumanity.class));
        cards.add(new SetCardInfo("Slimy Dualleech", 104, Rarity.UNCOMMON, mage.cards.s.SlimyDualleech.class));
        cards.add(new SetCardInfo("Snarling Gorehound", 105, Rarity.COMMON, mage.cards.s.SnarlingGorehound.class));
        cards.add(new SetCardInfo("Soul Enervation", 106, Rarity.UNCOMMON, mage.cards.s.SoulEnervation.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Soul Enervation", 302, Rarity.UNCOMMON, mage.cards.s.SoulEnervation.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Soul Search", 232, Rarity.UNCOMMON, mage.cards.s.SoulSearch.class));
        cards.add(new SetCardInfo("Steamcore Scholar", 397, Rarity.RARE, mage.cards.s.SteamcoreScholar.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Steamcore Scholar", 71, Rarity.RARE, mage.cards.s.SteamcoreScholar.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sudden Setback", 72, Rarity.UNCOMMON, mage.cards.s.SuddenSetback.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sudden Setback", "72+", Rarity.UNCOMMON, mage.cards.s.SuddenSetback.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sumala Sentry", 233, Rarity.UNCOMMON, mage.cards.s.SumalaSentry.class));
        cards.add(new SetCardInfo("Surveillance Monitor", 73, Rarity.UNCOMMON, mage.cards.s.SurveillanceMonitor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Surveillance Monitor", "73+", Rarity.UNCOMMON, mage.cards.s.SurveillanceMonitor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Suspicious Detonation", 145, Rarity.COMMON, mage.cards.s.SuspiciousDetonation.class));
        cards.add(new SetCardInfo("Swamp", 274, Rarity.LAND, mage.cards.basiclands.Swamp.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 281, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 282, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tenth District Hero", 34, Rarity.RARE, mage.cards.t.TenthDistrictHero.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tenth District Hero", 391, Rarity.RARE, mage.cards.t.TenthDistrictHero.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Teysa, Opulent Oligarch", "321z", Rarity.RARE, mage.cards.t.TeysaOpulentOligarch.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Teysa, Opulent Oligarch", 234, Rarity.RARE, mage.cards.t.TeysaOpulentOligarch.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Teysa, Opulent Oligarch", 321, Rarity.RARE, mage.cards.t.TeysaOpulentOligarch.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Teysa, Opulent Oligarch", 370, Rarity.RARE, mage.cards.t.TeysaOpulentOligarch.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Chase Is On", 116, Rarity.COMMON, mage.cards.t.TheChaseIsOn.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Chase Is On", 304, Rarity.COMMON, mage.cards.t.TheChaseIsOn.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Pride of Hull Clade", 172, Rarity.MYTHIC, mage.cards.t.ThePrideOfHullClade.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Pride of Hull Clade", 352, Rarity.MYTHIC, mage.cards.t.ThePrideOfHullClade.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Pride of Hull Clade", 382, Rarity.MYTHIC, mage.cards.t.ThePrideOfHullClade.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("They Went This Way", 178, Rarity.COMMON, mage.cards.t.TheyWentThisWay.class));
        cards.add(new SetCardInfo("Thinking Cap", 257, Rarity.COMMON, mage.cards.t.ThinkingCap.class));
        cards.add(new SetCardInfo("Thundering Falls", 269, Rarity.RARE, mage.cards.t.ThunderingFalls.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Thundering Falls", 331, Rarity.RARE, mage.cards.t.ThunderingFalls.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tin Street Gossip", 235, Rarity.UNCOMMON, mage.cards.t.TinStreetGossip.class));
        cards.add(new SetCardInfo("Tolsimir, Midnight's Light", 236, Rarity.RARE, mage.cards.t.TolsimirMidnightsLight.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tolsimir, Midnight's Light", 371, Rarity.RARE, mage.cards.t.TolsimirMidnightsLight.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tomik, Wielder of Law", 431, Rarity.MYTHIC, mage.cards.t.TomikWielderOfLaw.class));
        cards.add(new SetCardInfo("Topiary Panther", 179, Rarity.COMMON, mage.cards.t.TopiaryPanther.class));
        cards.add(new SetCardInfo("Torch the Witness", 146, Rarity.UNCOMMON, mage.cards.t.TorchTheWitness.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Torch the Witness", "146+", Rarity.UNCOMMON, mage.cards.t.TorchTheWitness.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Toxin Analysis", 107, Rarity.COMMON, mage.cards.t.ToxinAnalysis.class));
        cards.add(new SetCardInfo("Treacherous Greed", 237, Rarity.RARE, mage.cards.t.TreacherousGreed.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Treacherous Greed", 420, Rarity.RARE, mage.cards.t.TreacherousGreed.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Trostani, Three Whispers", "322z", Rarity.MYTHIC, mage.cards.t.TrostaniThreeWhispers.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Trostani, Three Whispers", 238, Rarity.MYTHIC, mage.cards.t.TrostaniThreeWhispers.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Trostani, Three Whispers", 322, Rarity.MYTHIC, mage.cards.t.TrostaniThreeWhispers.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Trostani, Three Whispers", 372, Rarity.MYTHIC, mage.cards.t.TrostaniThreeWhispers.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Trostani, Three Whispers", 388, Rarity.MYTHIC, mage.cards.t.TrostaniThreeWhispers.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tunnel Tipster", 180, Rarity.COMMON, mage.cards.t.TunnelTipster.class));
        cards.add(new SetCardInfo("Unauthorized Exit", 298, Rarity.COMMON, mage.cards.u.UnauthorizedExit.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Unauthorized Exit", 74, Rarity.COMMON, mage.cards.u.UnauthorizedExit.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Undercity Eliminator", 108, Rarity.UNCOMMON, mage.cards.u.UndercityEliminator.class));
        cards.add(new SetCardInfo("Undercity Sewers", 270, Rarity.RARE, mage.cards.u.UndercitySewers.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Undercity Sewers", 332, Rarity.RARE, mage.cards.u.UndercitySewers.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Undercover Crocodelf", 239, Rarity.COMMON, mage.cards.u.UndercoverCrocodelf.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Undercover Crocodelf", "239+", Rarity.COMMON, mage.cards.u.UndercoverCrocodelf.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Underground Mortuary", 271, Rarity.RARE, mage.cards.u.UndergroundMortuary.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Underground Mortuary", 333, Rarity.RARE, mage.cards.u.UndergroundMortuary.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Undergrowth Recon", 181, Rarity.MYTHIC, mage.cards.u.UndergrowthRecon.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Undergrowth Recon", 411, Rarity.MYTHIC, mage.cards.u.UndergrowthRecon.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Unscrupulous Agent", 109, Rarity.COMMON, mage.cards.u.UnscrupulousAgent.class));
        cards.add(new SetCardInfo("Unyielding Gatekeeper", 35, Rarity.RARE, mage.cards.u.UnyieldingGatekeeper.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Unyielding Gatekeeper", 392, Rarity.RARE, mage.cards.u.UnyieldingGatekeeper.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Urgent Necropsy", 240, Rarity.MYTHIC, mage.cards.u.UrgentNecropsy.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Urgent Necropsy", 421, Rarity.MYTHIC, mage.cards.u.UrgentNecropsy.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Vannifar, Evolved Enigma", "323z", Rarity.MYTHIC, mage.cards.v.VannifarEvolvedEnigma.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Vannifar, Evolved Enigma", 241, Rarity.MYTHIC, mage.cards.v.VannifarEvolvedEnigma.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Vannifar, Evolved Enigma", 323, Rarity.MYTHIC, mage.cards.v.VannifarEvolvedEnigma.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Vannifar, Evolved Enigma", 373, Rarity.MYTHIC, mage.cards.v.VannifarEvolvedEnigma.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Vannifar, Evolved Enigma", 389, Rarity.MYTHIC, mage.cards.v.VannifarEvolvedEnigma.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Vein Ripper", 110, Rarity.MYTHIC, mage.cards.v.VeinRipper.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Vein Ripper", 346, Rarity.MYTHIC, mage.cards.v.VeinRipper.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Vein Ripper", 433, Rarity.MYTHIC, mage.cards.v.VeinRipper.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Vengeful Creeper", 182, Rarity.COMMON, mage.cards.v.VengefulCreeper.class));
        cards.add(new SetCardInfo("Vengeful Tracker", 147, Rarity.UNCOMMON, mage.cards.v.VengefulTracker.class));
        cards.add(new SetCardInfo("Vitu-Ghazi Inspector", 183, Rarity.COMMON, mage.cards.v.VituGhaziInspector.class));
        cards.add(new SetCardInfo("Voja, Jaws of the Conclave", 432, Rarity.MYTHIC, mage.cards.v.VojaJawsOfTheConclave.class));
        cards.add(new SetCardInfo("Warleader's Call", 242, Rarity.RARE, mage.cards.w.WarleadersCall.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Warleader's Call", 315, Rarity.RARE, mage.cards.w.WarleadersCall.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Wispdrinker Vampire", 243, Rarity.UNCOMMON, mage.cards.w.WispdrinkerVampire.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Wispdrinker Vampire", 374, Rarity.UNCOMMON, mage.cards.w.WispdrinkerVampire.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Wojek Investigator", 340, Rarity.RARE, mage.cards.w.WojekInvestigator.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Wojek Investigator", 36, Rarity.RARE, mage.cards.w.WojekInvestigator.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Wojek Investigator", "36+", Rarity.RARE, mage.cards.w.WojekInvestigator.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Wojek Investigator", 429, Rarity.RARE, mage.cards.w.WojekInvestigator.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Worldsoul's Rage", 244, Rarity.RARE, mage.cards.w.WorldsoulsRage.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Worldsoul's Rage", 316, Rarity.RARE, mage.cards.w.WorldsoulsRage.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Wrench", 37, Rarity.UNCOMMON, mage.cards.w.Wrench.class));
        cards.add(new SetCardInfo("Yarus, Roar of the Old Gods", 245, Rarity.RARE, mage.cards.y.YarusRoarOfTheOldGods.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Yarus, Roar of the Old Gods", 375, Rarity.RARE, mage.cards.y.YarusRoarOfTheOldGods.class, NON_FULL_USE_VARIOUS));
    }

    @Override
    protected void generateBoosterMap() {
        super.generateBoosterMap();
        
        CardInfo cardInfo;
        for( int cn = 19 ; cn < 29 ; cn++ ) {
            cardInfo = CardRepository.instance.findCard("SPG", "" + cn);
            if( cardInfo != null ) {
                inBoosterMap.put("SPG_" + cn, cardInfo);
            } else {
                throw new IllegalArgumentException("Card not found: " + "SPG_" + cn);
            }
        }
        String[] lstCards = {"XLN_91", "DKA_4", "MH2_191", "HOU_149", "RAV_277", "ARB_68", "DIS_173", "ISD_183", "DOM_130", "MH2_46", "RNA_182", "ONS_272", "SOM_96", "VOW_207", "MBS_10", "UMA_138", "DDU_50", "2X2_17", "KLD_221", "M14_213", "UMA_247", "CLB_85", "JOU_153", "APC_117", "STX_220", "SOI_262", "DIS_33", "DKA_143", "ELD_107", "C16_47", "STX_64", "M20_167", "DST_40", "ONS_89", "WAR_54", "MRD_99", "SOM_98", "C21_19", "MH1_21", "RTR_140"};
        for( String itm : lstCards ) {
            int i = itm.indexOf("_");
            cardInfo = CardRepository.instance.findCard(itm.substring(0,i), itm.substring(i+1));
            if( cardInfo != null ) {
                inBoosterMap.put(itm, cardInfo);
            } else {
                throw new IllegalArgumentException("Card not found: " + itm);

            }
        }
    }

    @Override
    public BoosterCollator createCollator() {
        return new MurdersAtKarlovManorCollator();
    }
}

// Booster collation info from https://www.lethe.xyz/mtg/collation/mkm.html
// Using Japanese collation plus other info inferred from various sources
class MurdersAtKarlovManorCollator implements BoosterCollator {

    private final CardRun commonA = new CardRun(false, "40", "40", "41", "41", "256", "256", "145", "145", "257", "257", "239", "239+", "183", "183");
    private final CardRun commonB = new CardRun(true, "154", "68", "125", "66", "169", "138", "74", "178", "130", "294", "174", "143", "149", "60", "128", "179", "138", "49", "168", "133", "69", "182", "129", "60", "169", "265", "130", "68", "154", "125", "66", "170", "128", "74", "255", "159", "139", "53", "178", "304", "52", "180", "122", "177", "62", "144", "149", "39", "142", "46", "174", "129", "178", "122", "68", "180", "130", "60", "177", "138", "46", "159", "133", "255", "49", "149", "144", "293", "174", "142", "168", "69", "139", "39", "128", "179", "298", "143", "154", "265", "66", "116", "169", "62", "182", "125", "53", "170", "129", "179", "306", "69", "180", "116", "62", "177", "143", "49", "168", "142", "255", "52", "170", "144", "39", "182", "139", "46", "265", "310", "133");
    private final CardRun commonC = new CardRun(true, "76", "230", "14", "75", "206", "19", "93", "197", "292", "102", "204", "11", "105", "261", "18", "107", "203", "5", "103", "193", "33", "300", "231", "17", "84", "22", "78", "24", "228", "81", "29", "252", "101", "23", "93", "193", "30", "102", "197", "17", "107", "261", "18", "78", "203", "33", "105", "206", "22", "109", "225", "19", "101", "204", "11", "103", "230", "25", "84", "228", "14", "95", "5", "75", "290", "252", "76", "24", "231", "81", "29", "101", "206", "33", "75", "225", "11", "105", "230", "14", "81", "193", "25", "301", "203", "18", "107", "204", "17", "109", "197", "22", "102", "261", "30", "95", "252", "288", "84", "19", "76", "29", "231", "78", "23", "228", "93", "24", "109", "225", "25");
    private final CardRun uncommonA = new CardRun(false, "2", "16", "20", "21", "32", "147", "37");
    private final CardRun uncommonB = new CardRun(true, "148", "72", "167", "223", "160", "104", "235", "96", "165", "43", "113", "59", "233", "42", "161", "44", "258", "191", "15", "250", "38", "229", "65", "196", "31", "73", "112", "163", "85", "120", "162", "227", "171", "108", "117", "92", "190", "164", "82", "141", "7+", "55", "121", "156", "100", "146", "175", "91", "134", "218", "173", "8", "201", "79", "131", "58", "167", "72+", "148", "223", "104", "160", "235", "165", "96", "59", "113", "43", "233", "44", "161", "191", "42", "15", "258", "250", "65", "229", "38", "196", "112", "73+", "120", "31", "85", "163", "117", "108", "162", "92", "171", "227", "82+", "141", "190+", "164", "7", "156", "121", "55", "175", "146+", "100", "218", "134", "8", "173", "91", "58", "201", "79+", "131");
    private final CardRun uncommonC = new CardRun(true, "253", "90", "194", "126", "232", "305", "208", "124", "213+", "56", "221", "9", "158", "115", "247", "51", "195", "1", "367", "63", "249", "26", "374", "54", "302", "6", "246", "89", "254", "291", "248", "157", "98", "192", "90", "205", "267", "99", "358", "253", "119", "364", "295", "232", "126", "221", "115", "313", "124", "247", "9", "351", "63", "312", "51", "249", "54", "219", "1", "26", "106", "246", "299", "243", "254", "289", "248", "28", "345", "157", "192", "99", "267", "361", "90", "253", "194", "119", "213", "56", "232", "347", "221", "307", "247", "115", "158", "51", "208", "9", "195", "63", "219", "54", "249", "106", "339", "243", "1", "246", "89", "248", "6", "376", "28", "98", "157", "205", "99", "311", "267");
    // ToDo: Implement showcase/variant printings for rares
    private final CardRun rare = new CardRun(false, "184", "185", "150", "150", "111", "111", "186", "151", "151", "187", "187", "3", "3", "152", "152", "4", "188", "188", "153", "153", "77", "77", "189", "189", "114", "114", "155", "155", "45", "45", "80", "80", "10", "10", "118", "118", "47", "48", "48", "251", "251", "50", "50", "83", "83", "12", "13", "13", "198", "198", "199", "199", "200", "123", "202", "202", "57", "57", "127", "127", "166", "166", "86", "86", "87", "87", "88", "88", "207", "207", "132", "61", "209", "209", "210", "210", "211", "212", "212", "135", "135", "136", "136", "215", "214", "214", "137", "137", "216", "216", "217", "217", "64", "64", "94", "220", "220", "27", "27", "222", "222", "97", "97", "67", "67", "140", "140", "224", "70", "70", "226", "226", "176", "176", "71", "71", "34", "34", "234", "234", "172", "236", "236", "237", "237", "238", "181", "35", "35", "240", "241", "110", "242", "242", "36", "36", "244", "244", "245", "245");
    // 7:1 regular:borderless - regular rare dual lands 14.58% of the time or borderless 2.08% of the time
    private final CardRun rareLand = new CardRun(false, "259", "259", "259", "259", "259", "259", "259", "324", "260", "260", "260", "260", "260", "260", "260", "325", "262", "262", "262", "262", "262", "262", "262", "326", "263", "263", "263", "263", "263", "263", "263", "327", "264", "264", "264", "264", "264", "264", "264", "328", "266", "266", "266", "266", "266", "266", "266", "329", "268", "268", "268", "268", "268", "268", "268", "330", "269", "269", "269", "269", "269", "269", "269", "331", "270", "270", "270", "270", "270", "270", "270", "332", "271", "271", "271", "271", "271", "271", "271", "333");
    private final CardRun land = new CardRun(false, "272", "273", "274", "275", "276", "277", "278", "279", "280", "281", "282", "283", "284", "285", "286");
    // I used 5:2 common:uncommon ratio, which is plausible based on observed rates.
    private final CardRun list = new CardRun(false, "DKA_4", "DKA_4", "DKA_4", "DKA_4", "DKA_4", "ISD_183", "ISD_183", "ISD_183", "ISD_183", "ISD_183", "MH2_46", "MH2_46", "MH2_46", "MH2_46", "MH2_46", "ONS_272", "ONS_272", "ONS_272", "ONS_272", "ONS_272", "SOM_96", "SOM_96", "SOM_96", "SOM_96", "SOM_96", "KLD_221", "KLD_221", "KLD_221", "KLD_221", "KLD_221", "APC_117", "APC_117", "APC_117", "APC_117", "APC_117", "SOI_262", "SOI_262", "SOI_262", "SOI_262", "SOI_262", "XLN_91", "XLN_91", "MH2_191", "MH2_191", "HOU_149", "HOU_149", "RAV_277", "RAV_277", "ARB_68", "ARB_68", "DIS_173", "DIS_173", "DOM_130", "DOM_130", "RNA_182", "RNA_182", "VOW_207", "VOW_207", "MBS_10", "MBS_10", "UMA_138", "UMA_138", "DDU_50", "DDU_50", "2X2_17", "2X2_17", "M14_213", "M14_213", "UMA_247", "UMA_247", "CLB_85", "CLB_85", "JOU_153", "JOU_153", "STX_220", "STX_220", "DIS_33", "DIS_33", "DKA_143", "DKA_143", "ELD_107", "ELD_107", "C16_47", "C16_47");
    // I used 2:1 rare:mythic ratio. No idea what ratio is.
    private final CardRun listRare = new CardRun(false, "STX_64", "STX_64", "DST_40", "DST_40", "ONS_89", "ONS_89", "WAR_54", "WAR_54", "MRD_99", "MRD_99", "SOM_98", "SOM_98", "C21_19", "C21_19", "M20_167", "MH1_21", "RTR_140");
    private final CardRun listGuest = new CardRun(false, "SPG_25", "SPG_27", "SPG_20", "SPG_28", "SPG_24", "SPG_19", "SPG_21", "SPG_26", "SPG_22", "SPG_23" );

    // because a foil card is independant from sorted runs, repeated as unsorted runs
    private final CardRun commonFoilBC = new CardRun(false, "154", "68", "125", "66", "169", "138", "74", "178", "130", "294", "174", "143", "149", "60", "128", "179", "138", "49", "168", "133", "69", "182", "129", "60", "169", "265", "130", "68", "154", "125", "66", "170", "128", "74", "255", "159", "139", "53", "178", "304", "52", "180", "122", "177", "62", "144", "149", "39", "142", "46", "174", "129", "178", "122", "68", "180", "130", "60", "177", "138", "46", "159", "133", "255", "49", "149", "144", "293", "174", "142", "168", "69", "139", "39", "128", "179", "298", "143", "154", "265", "66", "116", "169", "62", "182", "125", "53", "170", "129", "179", "306", "69", "180", "116", "62", "177", "143", "49", "168", "142", "255", "52", "170", "144", "39", "182", "139", "46", "265", "310", "133", "76", "230", "14", "75", "206", "19", "93", "197", "292", "102", "204", "11", "105", "261", "18", "107", "203", "5", "103", "193", "33", "300", "231", "17", "84", "22", "78", "24", "228", "81", "29", "252", "101", "23", "93", "193", "30", "102", "197", "17", "107", "261", "18", "78", "203", "33", "105", "206", "22", "109", "225", "19", "101", "204", "11", "103", "230", "25", "84", "228", "14", "95", "5", "75", "290", "252", "76", "24", "231", "81", "29", "101", "206", "33", "75", "225", "11", "105", "230", "14", "81", "193", "25", "301", "203", "18", "107", "204", "17", "109", "197", "22", "102", "261", "30", "95", "252", "288", "84", "19", "76", "29", "231", "78", "23", "228", "93", "24", "109", "225", "25");
    private final CardRun uncommonFoilB = new CardRun(false, "148", "72", "167", "223", "160", "104", "235", "96", "165", "43", "113", "59", "233", "42", "161", "44", "258", "191", "15", "250", "38", "229", "65", "196", "31", "73", "112", "163", "85", "120", "162", "227", "171", "108", "117", "92", "190", "164", "82", "141", "7+", "55", "121", "156", "100", "146", "175", "91", "134", "218", "173", "8", "201", "79", "131", "58", "167", "72+", "148", "223", "104", "160", "235", "165", "96", "59", "113", "43", "233", "44", "161", "191", "42", "15", "258", "250", "65", "229", "38", "196", "112", "73+", "120", "31", "85", "163", "117", "108", "162", "92", "171", "227", "82+", "141", "190+", "164", "7", "156", "121", "55", "175", "146+", "100", "218", "134", "8", "173", "91", "58", "201", "79+", "131");
    private final CardRun uncommonFoilC = new CardRun(false, "253", "90", "194", "126", "232", "305", "208", "124", "213+", "56", "221", "9", "158", "115", "247", "51", "195", "1", "367", "63", "249", "26", "374", "54", "302", "6", "246", "89", "254", "291", "248", "157", "98", "192", "90", "205", "267", "99", "358", "253", "119", "364", "295", "232", "126", "221", "115", "313", "124", "247", "9", "351", "63", "312", "51", "249", "54", "219", "1", "26", "106", "246", "299", "243", "254", "289", "248", "28", "345", "157", "192", "99", "267", "361", "90", "253", "194", "119", "213", "56", "232", "347", "221", "307", "247", "115", "158", "51", "208", "9", "195", "63", "219", "54", "249", "106", "339", "243", "1", "246", "89", "248", "6", "376", "28", "98", "157", "205", "99", "311", "267");

    private final BoosterStructure BBBBCCCC = new BoosterStructure(
            commonB, commonB, commonB, commonB,
            commonC, commonC, commonC, commonC
    );
    private final BoosterStructure ABBBCCCC = new BoosterStructure(
            commonA,
            commonB, commonB, commonB,
            commonC, commonC, commonC, commonC
    );
    private final BoosterStructure ABBBBCCC = new BoosterStructure(
            commonA,
            commonB, commonB, commonB, commonB,
            commonC, commonC, commonC
    );
    private final BoosterStructure BBBCCCC = new BoosterStructure(
            commonB, commonB, commonB,
            commonC, commonC, commonC, commonC
    );
    private final BoosterStructure BBBBCCC = new BoosterStructure(
            commonB, commonB, commonB, commonB,
            commonC, commonC, commonC
    );
    private final BoosterStructure ABBBCCC = new BoosterStructure(
            commonA,
            commonB, commonB, commonB,
            commonC, commonC, commonC
    );
    private final BoosterStructure BBBCCC = new BoosterStructure(
            commonB, commonB, commonB,
            commonC, commonC, commonC
    );

    private final BoosterStructure BBCC = new BoosterStructure(
            uncommonB, uncommonB,
            uncommonC, uncommonC
    );
    private final BoosterStructure ABBC = new BoosterStructure(
            uncommonA,
            uncommonB, uncommonB,
            uncommonC
    );
    private final BoosterStructure BCC = new BoosterStructure(
            uncommonB,
            uncommonC, uncommonC
    );
    private final BoosterStructure BBC = new BoosterStructure(
            uncommonB, uncommonB,
            uncommonC
    );
    private final BoosterStructure ABB = new BoosterStructure(
            uncommonA,
            uncommonB, uncommonB
    );

    private final BoosterStructure R1 = new BoosterStructure(rare);
    private final BoosterStructure R2 = new BoosterStructure(rare,rare);
    private final BoosterStructure Rd = new BoosterStructure(rare,rareLand);
    private final BoosterStructure L1 = new BoosterStructure(land);
    private final BoosterStructure Scu = new BoosterStructure(list);
    private final BoosterStructure Srm = new BoosterStructure(listRare);
    private final BoosterStructure Ssg = new BoosterStructure(listGuest);
    private final BoosterStructure fcA = new BoosterStructure(commonA);
    private final BoosterStructure fcBC = new BoosterStructure(commonFoilBC);
    private final BoosterStructure fuA = new BoosterStructure(uncommonA);
    private final BoosterStructure fuB = new BoosterStructure(uncommonFoilB);
    private final BoosterStructure fuC = new BoosterStructure(uncommonFoilC);
    private final BoosterStructure frR = R1;
    private final BoosterStructure frL = new BoosterStructure(rareLand);

    // In order for equal numbers of each common to exist, the average booster must contain:
    // 0.62 A commons ( 532 / 864)       6 Commons (  3 / 32 ) or ( 81 / 864)
    // 3.25 B commons (2812 / 864)       7 Commons ( 22 / 32 ) or (594 / 864) 
    // 3.25 C commons (2812 / 864)       8 Commons (  7 / 32 ) or (189 / 864) 9*21=189
    private final RarityConfiguration commonRuns6 = new RarityConfiguration(BBBCCC);
    // If commonRuns7 were combined would need 115x BBBCCCC, 115x BBBBCCC, 364x ABBBCCC
    private final RarityConfiguration commonRuns7A = new RarityConfiguration(ABBBCCC);
    private final RarityConfiguration commonRuns7BC = new RarityConfiguration(BBBCCCC, BBBBCCC);
    private final RarityConfiguration commonRuns8 = new RarityConfiguration(
        ABBBCCCC, ABBBCCCC, ABBBCCCC, ABBBCCCC,
        ABBBBCCC, ABBBBCCC, ABBBBCCC, ABBBBCCC,
        BBBBCCCC
    );
    private static final RarityConfiguration commonRuns(int runLength) {
        // return ( 6< runLength ? 8> runLength ? RandomUtil.nextInt(297) <115 ?
            // commonRuns7BC : commonRuns7A : commonRuns8 : commonRuns6 );
        if (runLength < 7) {
          return commonRuns6;
        } else if (runLength > 7) {
          return commonRuns8;
        } else if (RandomUtil.nextInt(297) < 115) {
          return commonRuns7BC;
        } else {
          return commonRuns7A;
        }
    }

    // In order for equal numbers of each uncommon to exist, the average booster must contain:
    // 0.25 A uncommons ( 49 / 200)       3 Uncommons (1 / 2) or (100 / 200)
    // 1.96 B uncommons (392 / 200)       4 Uncommons (1 / 2) or (100 / 200) 25*4=100
    // 1.30 C uncommons (259 / 200)       
    private final RarityConfiguration uncommonRuns3 = new RarityConfiguration(
        BCC, BCC, BCC, BCC, BCC, BCC, BCC, BCC, 
        BBC, BBC, BBC, BBC, BBC, BBC, BBC, BBC, BBC, BBC,
        BBC, BBC, BBC, BBC, BBC, BBC, BBC, BBC, BBC, BBC,
        BBC, BBC, BBC, BBC, BBC, BBC, BBC, BBC, BBC, BBC,
        BBC, BBC, BBC, BBC, BBC, BBC, BBC, BBC, BBC, BBC,
        BBC, BBC, BBC, BBC, BBC, BBC, BBC, BBC, BBC, BBC,
        BBC, BBC, BBC, BBC, BBC, BBC, BBC, BBC, BBC, BBC,
        BBC, BBC, BBC, BBC, BBC, BBC, BBC, BBC, BBC, BBC, BBC,
        ABB, ABB, ABB, ABB, ABB, ABB, ABB, ABB, ABB, ABB,
        ABB, ABB, ABB, ABB, ABB, ABB, ABB, ABB, ABB, ABB, ABB
    );
    private final RarityConfiguration uncommonRuns4 = new RarityConfiguration(
        BBCC, BBCC, BBCC, BBCC, BBCC, BBCC, BBCC, BBCC, BBCC, BBCC,
        BBCC, BBCC, BBCC, BBCC, BBCC, BBCC, BBCC, BBCC,
        ABBC, ABBC, ABBC, ABBC, ABBC, ABBC, ABBC
    );
    private static final RarityConfiguration uncommonRuns(int runLength) {
        return ( runLength > 3 ? uncommonRuns4 : uncommonRuns3 );
    }


    // 1/6 packs have rare land as second rare, estimate 1/4 packs have two rares
    private final RarityConfiguration rareRuns1 = new RarityConfiguration(R1);
    private final RarityConfiguration rareRuns2 = new RarityConfiguration(R2,Rd,Rd);
    private static final RarityConfiguration rareRuns(int runLength) {
        return ( runLength > 1 ? rareRuns2 : rareRuns1 );
    }

    private final RarityConfiguration landRuns = new RarityConfiguration(L1);
    // 1 in 8 Play Boosters (12.5%) features a card from Special Guests or The List
    // Special Guests cards in 1.56% of Play Boosters.
    // The List has rare and mythic rare reprints replacing a common card 1.56% of the time.
    // Which means 1/8 List packs have Special Guest, 1/8 have rare/mythic
    private final RarityConfiguration listRuns = new RarityConfiguration(
        Scu, Scu, Scu, Scu, Scu, Scu,
        Srm,
        Ssg
    );

    private final RarityConfiguration foilCommonRuns = new RarityConfiguration(
        fcA,  fcA,  fcA,  fcA,  fcA,  fcA,  fcA,
        fcBC, fcBC, fcBC, fcBC, fcBC, fcBC, fcBC, fcBC, fcBC, fcBC,
        fcBC, fcBC, fcBC, fcBC, fcBC, fcBC, fcBC, fcBC, fcBC, fcBC,
        fcBC, fcBC, fcBC, fcBC, fcBC, fcBC, fcBC, fcBC, fcBC, fcBC,
        fcBC, fcBC, fcBC, fcBC, fcBC, fcBC, fcBC,
        fcBC, fcBC, fcBC, fcBC, fcBC, fcBC, fcBC, fcBC, fcBC, fcBC,
        fcBC, fcBC, fcBC, fcBC, fcBC, fcBC, fcBC, fcBC, fcBC, fcBC,
        fcBC, fcBC, fcBC, fcBC, fcBC, fcBC, fcBC, fcBC, fcBC, fcBC,
        fcBC, fcBC, fcBC, fcBC, fcBC, fcBC, fcBC
    );
    private final RarityConfiguration foilUncommonRuns = new RarityConfiguration(
        fuA, fuA, fuA, fuA, fuA, fuA, fuA,
        fuB, fuB, fuB, fuB, fuB, fuB, fuB, fuB, fuB, fuB,
        fuB, fuB, fuB, fuB, fuB, fuB, fuB, fuB, fuB, fuB,
        fuB, fuB, fuB, fuB, fuB, fuB, fuB, fuB, fuB, fuB,
        fuB, fuB, fuB, fuB, fuB, fuB, fuB, fuB, fuB, fuB,
        fuB, fuB, fuB, fuB, fuB, fuB, fuB, fuB, fuB, fuB,
        fuB, fuB, fuB, fuB, fuB, fuB,
        fuC, fuC, fuC, fuC, fuC, fuC, fuC, fuC, fuC, fuC,
        fuC, fuC, fuC, fuC, fuC, fuC, fuC, fuC, fuC, fuC,
        fuC, fuC, fuC, fuC, fuC, fuC, fuC, fuC, fuC, fuC,
        fuC, fuC, fuC, fuC, fuC, fuC, fuC
    );
    private final RarityConfiguration foilRareRuns = new RarityConfiguration(frR);
    private final RarityConfiguration foilRareLandRuns = new RarityConfiguration(frL);
    private static final RarityConfiguration foilRuns() {
        // foil wildcard separate, received numbers from lethe for 540 packs
        // using 115 common, 49 uncommon, 15 rare, 1 rareland
        wildNum = RandomUtil.nextInt(180);
        if (wildNum < 115) {
            return foilCommonRuns;
        } else if (wildNum < 164) {
            return foilUncommonRuns;
        } else if (wildNum < 179) {
            return foilRareRuns;
        } else {
            return foilRareLandRuns;
        }
    }

    @Override
    public List<String> makeBooster() {
        List<String> booster = new ArrayList<>();
        // 1-2 rares, 3-4 uncommons, and 6-8 commons
        // There is not a wildcard slot; the wildcard simply appears as part of one of the normal runs
        int numCommon = 7;
        boolean wildRare = false;
        boolean wildUncommon = false;
        // observed the rate [of uncommon wildcard] at about 52% over 7 boxes
        // that is close to 50% - using 50% uncommon, 25% common, 25% rare
        int wildNum = RandomUtil.nextInt(4);
        if (wildNum < 2) {
            wildUncommon = true;
        } else if (wildNum < 3) {
            ++numCommon;
        } else {
            wildRare = true;
        }

        booster.addAll(landRuns.getNext().makeRun());

        // 1 in 8 Play Boosters (12.5%) features a card from Special Guests or The List instead of the seventh common card.
        if (RandomUtil.nextInt(8) == 1) {
            booster.addAll(listRuns.getNext().makeRun());
            --numCommon;
        }

        booster.addAll(foilRuns().getNext().makeRun());
        booster.addAll(rareRuns(wildRare ? 2 : 1).getNext().makeRun());
        booster.addAll(uncommonRuns(wildUncommon ? 4 : 3).getNext().makeRun());
        booster.addAll(commonRuns(numCommon).getNext().makeRun());

        return booster;
    }
}
