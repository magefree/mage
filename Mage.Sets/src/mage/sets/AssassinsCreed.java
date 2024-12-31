package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class AssassinsCreed extends ExpansionSet {

    private static final AssassinsCreed instance = new AssassinsCreed();

    public static AssassinsCreed getInstance() {
        return instance;
    }

    private AssassinsCreed() {
        super("Assassin's Creed", "ACR", ExpansionSet.buildDate(2024, 7, 5), SetType.SUPPLEMENTAL_MODERN_LEGAL);
        this.blockName = "Assassin's Creed"; // for sorting in GUI
        this.hasBasicLands = true;
        this.hasBoosters = false;

        cards.add(new SetCardInfo("Abstergo Entertainment", 79, Rarity.RARE, mage.cards.a.AbstergoEntertainment.class));
        cards.add(new SetCardInfo("Achilles Davenport", 294, Rarity.RARE, mage.cards.a.AchillesDavenport.class));
        cards.add(new SetCardInfo("Adewale, Breaker of Chains", 44, Rarity.UNCOMMON, mage.cards.a.AdewaleBreakerOfChains.class));
        cards.add(new SetCardInfo("Adrestia", 68, Rarity.UNCOMMON, mage.cards.a.Adrestia.class));
        cards.add(new SetCardInfo("Arbaaz Mir", 46, Rarity.UNCOMMON, mage.cards.a.ArbaazMir.class));
        cards.add(new SetCardInfo("Arno Dorian", 47, Rarity.UNCOMMON, mage.cards.a.ArnoDorian.class));
        cards.add(new SetCardInfo("Assassin Den", 281, Rarity.UNCOMMON, mage.cards.a.AssassinDen.class));
        cards.add(new SetCardInfo("Assassin Gauntlet", 12, Rarity.UNCOMMON, mage.cards.a.AssassinGauntlet.class));
        cards.add(new SetCardInfo("Assassin Initiate", 22, Rarity.UNCOMMON, mage.cards.a.AssassinInitiate.class));
        cards.add(new SetCardInfo("Assassin's Trophy", 95, Rarity.RARE, mage.cards.a.AssassinsTrophy.class));
        cards.add(new SetCardInfo("Aveline de Grandpre", 40, Rarity.RARE, mage.cards.a.AvelineDeGrandpre.class));
        cards.add(new SetCardInfo("Aya of Alexandria", 48, Rarity.RARE, mage.cards.a.AyaOfAlexandria.class));
        cards.add(new SetCardInfo("Ballad of the Black Flag", 13, Rarity.UNCOMMON, mage.cards.b.BalladOfTheBlackFlag.class));
        cards.add(new SetCardInfo("Basim Ibn Ishaq", 49, Rarity.RARE, mage.cards.b.BasimIbnIshaq.class));
        cards.add(new SetCardInfo("Battlefield Improvisation", 276, Rarity.COMMON, mage.cards.b.BattlefieldImprovisation.class));
        cards.add(new SetCardInfo("Bayek of Siwa", 50, Rarity.RARE, mage.cards.b.BayekOfSiwa.class));
        cards.add(new SetCardInfo("Become Anonymous", 14, Rarity.UNCOMMON, mage.cards.b.BecomeAnonymous.class));
        cards.add(new SetCardInfo("Black Market Connections", 87, Rarity.RARE, mage.cards.b.BlackMarketConnections.class));
        cards.add(new SetCardInfo("Bleeding Effect", 51, Rarity.UNCOMMON, mage.cards.b.BleedingEffect.class));
        cards.add(new SetCardInfo("Brotherhood Ambushers", 285, Rarity.UNCOMMON, mage.cards.b.BrotherhoodAmbushers.class));
        cards.add(new SetCardInfo("Brotherhood Patriarch", 286, Rarity.COMMON, mage.cards.b.BrotherhoodPatriarch.class));
        cards.add(new SetCardInfo("Brotherhood Regalia", 71, Rarity.UNCOMMON, mage.cards.b.BrotherhoodRegalia.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Brotherhood Regalia", 255, Rarity.UNCOMMON, mage.cards.b.BrotherhoodRegalia.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Brotherhood Spy", 282, Rarity.UNCOMMON, mage.cards.b.BrotherhoodSpy.class));
        cards.add(new SetCardInfo("Bureau Headmaster", 296, Rarity.UNCOMMON, mage.cards.b.BureauHeadmaster.class));
        cards.add(new SetCardInfo("Caduceus, Staff of Hermes", 2, Rarity.RARE, mage.cards.c.CaduceusStaffOfHermes.class));
        cards.add(new SetCardInfo("Cathartic Reunion", 94, Rarity.UNCOMMON, mage.cards.c.CatharticReunion.class));
        cards.add(new SetCardInfo("Chain Assassination", 23, Rarity.UNCOMMON, mage.cards.c.ChainAssassination.class));
        cards.add(new SetCardInfo("Cleopatra, Exiled Pharaoh", 52, Rarity.MYTHIC, mage.cards.c.CleopatraExiledPharaoh.class));
        cards.add(new SetCardInfo("Coastal Piracy", 84, Rarity.UNCOMMON, mage.cards.c.CoastalPiracy.class));
        cards.add(new SetCardInfo("Conspiracy", 88, Rarity.RARE, mage.cards.c.Conspiracy.class));
        cards.add(new SetCardInfo("Cover of Darkness", 89, Rarity.RARE, mage.cards.c.CoverOfDarkness.class));
        cards.add(new SetCardInfo("Crystal Skull, Isu Spyglass", 15, Rarity.RARE, mage.cards.c.CrystalSkullIsuSpyglass.class));
        cards.add(new SetCardInfo("Desmond Miles", 24, Rarity.RARE, mage.cards.d.DesmondMiles.class));
        cards.add(new SetCardInfo("Desynchronization", 16, Rarity.RARE, mage.cards.d.Desynchronization.class));
        cards.add(new SetCardInfo("Detained by Legionnaires", 277, Rarity.COMMON, mage.cards.d.DetainedByLegionnaires.class));
        cards.add(new SetCardInfo("Distract the Guards", 3, Rarity.UNCOMMON, mage.cards.d.DistractTheGuards.class));
        cards.add(new SetCardInfo("Eagle Vision", 17, Rarity.UNCOMMON, mage.cards.e.EagleVision.class));
        cards.add(new SetCardInfo("Edward Kenway", 53, Rarity.MYTHIC, mage.cards.e.EdwardKenway.class));
        cards.add(new SetCardInfo("Eivor, Battle-Ready", 274, Rarity.MYTHIC, mage.cards.e.EivorBattleReady.class));
        cards.add(new SetCardInfo("Eivor, Wolf-Kissed", 54, Rarity.MYTHIC, mage.cards.e.EivorWolfKissed.class));
        cards.add(new SetCardInfo("Escape Detection", 18, Rarity.UNCOMMON, mage.cards.e.EscapeDetection.class));
        cards.add(new SetCardInfo("Escarpment Fortress", 278, Rarity.RARE, mage.cards.e.EscarpmentFortress.class));
        cards.add(new SetCardInfo("Evie Frye", 19, Rarity.RARE, mage.cards.e.EvieFrye.class));
        cards.add(new SetCardInfo("Excalibur, Sword of Eden", 72, Rarity.RARE, mage.cards.e.ExcaliburSwordOfEden.class));
        cards.add(new SetCardInfo("Ezio Auditore da Firenze", 25, Rarity.MYTHIC, mage.cards.e.EzioAuditoreDaFirenze.class));
        cards.add(new SetCardInfo("Ezio, Blade of Vengeance", 275, Rarity.MYTHIC, mage.cards.e.EzioBladeOfVengeance.class));
        cards.add(new SetCardInfo("Ezio, Brash Novice", 55, Rarity.UNCOMMON, mage.cards.e.EzioBrashNovice.class));
        cards.add(new SetCardInfo("Fatal Push", 90, Rarity.UNCOMMON, mage.cards.f.FatalPush.class));
        cards.add(new SetCardInfo("Fiery Islet", 112, Rarity.RARE, mage.cards.f.FieryIslet.class));
        cards.add(new SetCardInfo("Forest", 109, Rarity.LAND, mage.cards.basiclands.Forest.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Go for the Throat", 91, Rarity.UNCOMMON, mage.cards.g.GoForTheThroat.class));
        cards.add(new SetCardInfo("Haystack", 5, Rarity.UNCOMMON, mage.cards.h.Haystack.class));
        cards.add(new SetCardInfo("Headsplitter", 289, Rarity.COMMON, mage.cards.h.Headsplitter.class));
        cards.add(new SetCardInfo("Hemlock Vial", 26, Rarity.UNCOMMON, mage.cards.h.HemlockVial.class));
        cards.add(new SetCardInfo("Hidden Blade", 73, Rarity.UNCOMMON, mage.cards.h.HiddenBlade.class));
        cards.add(new SetCardInfo("Hidden Footblade", 34, Rarity.UNCOMMON, mage.cards.h.HiddenFootblade.class));
        cards.add(new SetCardInfo("Hired Blade", 299, Rarity.COMMON, mage.cards.h.HiredBlade.class));
        cards.add(new SetCardInfo("Hookblade Veteran", 283, Rarity.COMMON, mage.cards.h.HookbladeVeteran.class));
        cards.add(new SetCardInfo("Hookblade", 6, Rarity.UNCOMMON, mage.cards.h.Hookblade.class));
        cards.add(new SetCardInfo("Hunter's Bow", 41, Rarity.UNCOMMON, mage.cards.h.HuntersBow.class));
        cards.add(new SetCardInfo("Island", 103, Rarity.LAND, mage.cards.basiclands.Island.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Jackdaw", 58, Rarity.RARE, mage.cards.j.Jackdaw.class));
        cards.add(new SetCardInfo("Keen-Eyed Raven", 279, Rarity.UNCOMMON, mage.cards.k.KeenEyedRaven.class));
        cards.add(new SetCardInfo("Labyrinth Adversary", 290, Rarity.UNCOMMON, mage.cards.l.LabyrinthAdversary.class));
        cards.add(new SetCardInfo("Leonardo da Vinci", 20, Rarity.MYTHIC, mage.cards.l.LeonardoDaVinci.class));
        cards.add(new SetCardInfo("Loyal Inventor", 21, Rarity.UNCOMMON, mage.cards.l.LoyalInventor.class));
        cards.add(new SetCardInfo("Lydia Frye", 60, Rarity.UNCOMMON, mage.cards.l.LydiaFrye.class));
        cards.add(new SetCardInfo("Mary Read and Anne Bonny", 61, Rarity.RARE, mage.cards.m.MaryReadAndAnneBonny.class));
        cards.add(new SetCardInfo("Merciless Harlequin", 287, Rarity.COMMON, mage.cards.m.MercilessHarlequin.class));
        cards.add(new SetCardInfo("Misthios's Fury", 291, Rarity.COMMON, mage.cards.m.MisthiossFury.class));
        cards.add(new SetCardInfo("Mjolnir, Storm Hammer", 74, Rarity.RARE, mage.cards.m.MjolnirStormHammer.class));
        cards.add(new SetCardInfo("Mortify", 96, Rarity.UNCOMMON, mage.cards.m.Mortify.class));
        cards.add(new SetCardInfo("Mountain", 107, Rarity.LAND, mage.cards.basiclands.Mountain.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Murder", 92, Rarity.UNCOMMON, mage.cards.m.Murder.class));
        cards.add(new SetCardInfo("Nurturing Peatland", 114, Rarity.RARE, mage.cards.n.NurturingPeatland.class));
        cards.add(new SetCardInfo("Origin of the Hidden Ones", 36, Rarity.UNCOMMON, mage.cards.o.OriginOfTheHiddenOnes.class));
        cards.add(new SetCardInfo("Palazzo Archers", 42, Rarity.UNCOMMON, mage.cards.p.PalazzoArchers.class));
        cards.add(new SetCardInfo("Path to Exile", 81, Rarity.UNCOMMON, mage.cards.p.PathToExile.class));
        cards.add(new SetCardInfo("Petty Larceny", 28, Rarity.UNCOMMON, mage.cards.p.PettyLarceny.class));
        cards.add(new SetCardInfo("Phantom Blade", 29, Rarity.UNCOMMON, mage.cards.p.PhantomBlade.class));
        cards.add(new SetCardInfo("Plains", 101, Rarity.LAND, mage.cards.basiclands.Plains.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Poison-Blade Mentor", 288, Rarity.UNCOMMON, mage.cards.p.PoisonBladeMentor.class));
        cards.add(new SetCardInfo("Propaganda", 85, Rarity.UNCOMMON, mage.cards.p.Propaganda.class));
        cards.add(new SetCardInfo("Raven Clan War-Axe", 297, Rarity.RARE, mage.cards.r.RavenClanWarAxe.class));
        cards.add(new SetCardInfo("Reconnaissance", 82, Rarity.UNCOMMON, mage.cards.r.Reconnaissance.class));
        cards.add(new SetCardInfo("Reconstruct History", 97, Rarity.UNCOMMON, mage.cards.r.ReconstructHistory.class));
        cards.add(new SetCardInfo("Rest in Peace", 83, Rarity.RARE, mage.cards.r.RestInPeace.class));
        cards.add(new SetCardInfo("Restart Sequence", 30, Rarity.UNCOMMON, mage.cards.r.RestartSequence.class));
        cards.add(new SetCardInfo("Rooftop Bypass", 298, Rarity.RARE, mage.cards.r.RooftopBypass.class));
        cards.add(new SetCardInfo("Roshan, Hidden Magister", 32, Rarity.UNCOMMON, mage.cards.r.RoshanHiddenMagister.class));
        cards.add(new SetCardInfo("Royal Assassin", 93, Rarity.RARE, mage.cards.r.RoyalAssassin.class));
        cards.add(new SetCardInfo("Senu, Keen-Eyed Protector", 8, Rarity.RARE, mage.cards.s.SenuKeenEyedProtector.class));
        cards.add(new SetCardInfo("Settlement Blacksmith", 280, Rarity.UNCOMMON, mage.cards.s.SettlementBlacksmith.class));
        cards.add(new SetCardInfo("Shao Jun", 63, Rarity.UNCOMMON, mage.cards.s.ShaoJun.class));
        cards.add(new SetCardInfo("Sigurd, Jarl of Ravensthorpe", 66, Rarity.RARE, mage.cards.s.SigurdJarlOfRavensthorpe.class));
        cards.add(new SetCardInfo("Silent Clearing", 115, Rarity.RARE, mage.cards.s.SilentClearing.class));
        cards.add(new SetCardInfo("Smoke Bomb", 75, Rarity.UNCOMMON, mage.cards.s.SmokeBomb.class));
        cards.add(new SetCardInfo("Spartan Veteran", 292, Rarity.COMMON, mage.cards.s.SpartanVeteran.class));
        cards.add(new SetCardInfo("Staff of Eden, Vault's Key", 76, Rarity.MYTHIC, mage.cards.s.StaffOfEdenVaultsKey.class));
        cards.add(new SetCardInfo("Stone Quarry", 300, Rarity.UNCOMMON, mage.cards.s.StoneQuarry.class));
        cards.add(new SetCardInfo("Submerged Boneyard", 301, Rarity.UNCOMMON, mage.cards.s.SubmergedBoneyard.class));
        cards.add(new SetCardInfo("Sunbaked Canyon", 111, Rarity.RARE, mage.cards.s.SunbakedCanyon.class));
        cards.add(new SetCardInfo("Surtr, Fiery Jotun", 293, Rarity.RARE, mage.cards.s.SurtrFieryJotun.class));
        cards.add(new SetCardInfo("Swamp", 105, Rarity.LAND, mage.cards.basiclands.Swamp.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Sword of Feast and Famine", 99, Rarity.MYTHIC, mage.cards.s.SwordOfFeastAndFamine.class));
        cards.add(new SetCardInfo("Sword of Light and Shadow", 100, Rarity.MYTHIC, mage.cards.s.SwordOfLightAndShadow.class));
        cards.add(new SetCardInfo("Tax Collector", 9, Rarity.UNCOMMON, mage.cards.t.TaxCollector.class));
        cards.add(new SetCardInfo("Templar Knight", 10, Rarity.UNCOMMON, mage.cards.t.TemplarKnight.class));
        cards.add(new SetCardInfo("Temporal Trespass", 86, Rarity.MYTHIC, mage.cards.t.TemporalTrespass.class));
        cards.add(new SetCardInfo("Terminate", 98, Rarity.UNCOMMON, mage.cards.t.Terminate.class));
        cards.add(new SetCardInfo("The Animus", 69, Rarity.RARE, mage.cards.t.TheAnimus.class));
        cards.add(new SetCardInfo("The Capitoline Triad", 1, Rarity.MYTHIC, mage.cards.t.TheCapitolineTriad.class));
        cards.add(new SetCardInfo("The Revelations of Ezio", 31, Rarity.UNCOMMON, mage.cards.t.TheRevelationsOfEzio.class));
        cards.add(new SetCardInfo("The Spear of Leonidas", 38, Rarity.RARE, mage.cards.t.TheSpearOfLeonidas.class));
        cards.add(new SetCardInfo("Towering Viewpoint", 77, Rarity.UNCOMMON, mage.cards.t.ToweringViewpoint.class));
        cards.add(new SetCardInfo("Tranquilize", 284, Rarity.COMMON, mage.cards.t.Tranquilize.class));
        cards.add(new SetCardInfo("Viewpoint Synchronization", 43, Rarity.UNCOMMON, mage.cards.v.ViewpointSynchronization.class));
        cards.add(new SetCardInfo("Waterlogged Grove", 116, Rarity.RARE, mage.cards.w.WaterloggedGrove.class));
        cards.add(new SetCardInfo("Yggdrasil, Rebirth Engine", 78, Rarity.MYTHIC, mage.cards.y.YggdrasilRebirthEngine.class));
    }
}
