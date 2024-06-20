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
        super("Assassin's Creed", "ACR", ExpansionSet.buildDate(2024, 7, 5), SetType.EXPANSION);
        this.blockName = "Assassin's Creed"; // for sorting in GUI
        this.hasBasicLands = false;
        this.hasBoosters = false;

        cards.add(new SetCardInfo("Abstergo Entertainment", 79, Rarity.RARE, mage.cards.a.AbstergoEntertainment.class));
        cards.add(new SetCardInfo("Achilles Davenport", 294, Rarity.RARE, mage.cards.a.AchillesDavenport.class));
        cards.add(new SetCardInfo("Assassin Den", 281, Rarity.UNCOMMON, mage.cards.a.AssassinDen.class));
        cards.add(new SetCardInfo("Assassin's Trophy", 166, Rarity.RARE, mage.cards.a.AssassinsTrophy.class));
        cards.add(new SetCardInfo("Aya of Alexandria", 48, Rarity.RARE, mage.cards.a.AyaOfAlexandria.class));
        cards.add(new SetCardInfo("Basim Ibn Ishaq", 49, Rarity.RARE, mage.cards.b.BasimIbnIshaq.class));
        cards.add(new SetCardInfo("Bayek of Siwa", 50, Rarity.RARE, mage.cards.b.BayekOfSiwa.class));
        cards.add(new SetCardInfo("Black Market Connections", 87, Rarity.RARE, mage.cards.b.BlackMarketConnections.class));
        cards.add(new SetCardInfo("Brotherhood Ambushers", 285, Rarity.UNCOMMON, mage.cards.b.BrotherhoodAmbushers.class));
        cards.add(new SetCardInfo("Cathartic Reunion", 94, Rarity.UNCOMMON, mage.cards.c.CatharticReunion.class));
        cards.add(new SetCardInfo("Chain Assassination", 23, Rarity.UNCOMMON, mage.cards.c.ChainAssassination.class));
        cards.add(new SetCardInfo("Cleopatra, Exiled Pharaoh", 52, Rarity.MYTHIC, mage.cards.c.CleopatraExiledPharaoh.class));
        cards.add(new SetCardInfo("Coastal Piracy", 84, Rarity.UNCOMMON, mage.cards.c.CoastalPiracy.class));
        cards.add(new SetCardInfo("Conspiracy", 88, Rarity.RARE, mage.cards.c.Conspiracy.class));
        cards.add(new SetCardInfo("Cover of Darkness", 89, Rarity.RARE, mage.cards.c.CoverOfDarkness.class));
        cards.add(new SetCardInfo("Desynchronization", 16, Rarity.RARE, mage.cards.d.Desynchronization.class));
        cards.add(new SetCardInfo("Detained by Legionnaires", 277, Rarity.COMMON, mage.cards.d.DetainedByLegionnaires.class));
        cards.add(new SetCardInfo("Eagle Vision", 17, Rarity.UNCOMMON, mage.cards.e.EagleVision.class));
        cards.add(new SetCardInfo("Eivor, Battle-Ready", 274, Rarity.MYTHIC, mage.cards.e.EivorBattleReady.class));
        cards.add(new SetCardInfo("Escarpment Fortress", 278, Rarity.RARE, mage.cards.e.EscarpmentFortress.class));
        cards.add(new SetCardInfo("Ezio, Blade of Vengeance", 275, Rarity.MYTHIC, mage.cards.e.EzioBladeOfVengeance.class));
        cards.add(new SetCardInfo("Fatal Push", 90, Rarity.UNCOMMON, mage.cards.f.FatalPush.class));
        cards.add(new SetCardInfo("Fiery Islet", 112, Rarity.RARE, mage.cards.f.FieryIslet.class));
        cards.add(new SetCardInfo("Go for the Throat", 91, Rarity.UNCOMMON, mage.cards.g.GoForTheThroat.class));
        cards.add(new SetCardInfo("Haystack", 5, Rarity.UNCOMMON, mage.cards.h.Haystack.class));
        cards.add(new SetCardInfo("Headsplitter", 289, Rarity.COMMON, mage.cards.h.Headsplitter.class));
        cards.add(new SetCardInfo("Hemlock Vial", 26, Rarity.UNCOMMON, mage.cards.h.HemlockVial.class));
        cards.add(new SetCardInfo("Hidden Blade", 73, Rarity.UNCOMMON, mage.cards.h.HiddenBlade.class));
        cards.add(new SetCardInfo("Hidden Footblade", 34, Rarity.UNCOMMON, mage.cards.h.HiddenFootblade.class));
        cards.add(new SetCardInfo("Hired Blade", 299, Rarity.COMMON, mage.cards.h.HiredBlade.class));
        cards.add(new SetCardInfo("Hookblade", 6, Rarity.UNCOMMON, mage.cards.h.Hookblade.class));
        cards.add(new SetCardInfo("Hookblade Veteran", 283, Rarity.COMMON, mage.cards.h.HookbladeVeteran.class));
        cards.add(new SetCardInfo("Hunter's Bow", 41, Rarity.UNCOMMON, mage.cards.h.HuntersBow.class));
        cards.add(new SetCardInfo("Keen-Eyed Raven", 279, Rarity.UNCOMMON, mage.cards.k.KeenEyedRaven.class));
        cards.add(new SetCardInfo("Labyrinth Adversary", 290, Rarity.UNCOMMON, mage.cards.l.LabyrinthAdversary.class));
        cards.add(new SetCardInfo("Lydia Frye", 60, Rarity.UNCOMMON, mage.cards.l.LydiaFrye.class));
        cards.add(new SetCardInfo("Mary Read and Anne Bonny", 61, Rarity.RARE, mage.cards.m.MaryReadAndAnneBonny.class));
        cards.add(new SetCardInfo("Merciless Harlequin", 287, Rarity.COMMON, mage.cards.m.MercilessHarlequin.class));
        cards.add(new SetCardInfo("Mortify", 96, Rarity.UNCOMMON, mage.cards.m.Mortify.class));
        cards.add(new SetCardInfo("Murder", 92, Rarity.COMMON, mage.cards.m.Murder.class));
        cards.add(new SetCardInfo("Nurturing Peatland", 114, Rarity.RARE, mage.cards.n.NurturingPeatland.class));
        cards.add(new SetCardInfo("Origin of the Hidden Ones", 36, Rarity.UNCOMMON, mage.cards.o.OriginOfTheHiddenOnes.class));
        cards.add(new SetCardInfo("Path to Exile", 81, Rarity.UNCOMMON, mage.cards.p.PathToExile.class));
        cards.add(new SetCardInfo("Poison-Blade Mentor", 288, Rarity.UNCOMMON, mage.cards.p.PoisonBladeMentor.class));
        cards.add(new SetCardInfo("Propaganda", 85, Rarity.UNCOMMON, mage.cards.p.Propaganda.class));
        cards.add(new SetCardInfo("Raven Clan War-Axe", 297, Rarity.RARE, mage.cards.r.RavenClanWarAxe.class));
        cards.add(new SetCardInfo("Reconnaissance", 82, Rarity.UNCOMMON, mage.cards.r.Reconnaissance.class));
        cards.add(new SetCardInfo("Reconstruct History", 97, Rarity.UNCOMMON, mage.cards.r.ReconstructHistory.class));
        cards.add(new SetCardInfo("Rest in Peace", 83, Rarity.RARE, mage.cards.r.RestInPeace.class));
        cards.add(new SetCardInfo("Restart Sequence", 30, Rarity.UNCOMMON, mage.cards.r.RestartSequence.class));
        cards.add(new SetCardInfo("Royal Assassin", 93, Rarity.RARE, mage.cards.r.RoyalAssassin.class));
        cards.add(new SetCardInfo("Settlement Blacksmith", 280, Rarity.UNCOMMON, mage.cards.s.SettlementBlacksmith.class));
        cards.add(new SetCardInfo("Silent Clearing", 115, Rarity.RARE, mage.cards.s.SilentClearing.class));
        cards.add(new SetCardInfo("Smoke Bomb", 75, Rarity.UNCOMMON, mage.cards.s.SmokeBomb.class));
        cards.add(new SetCardInfo("Stone Quarry", 300, Rarity.UNCOMMON, mage.cards.s.StoneQuarry.class));
        cards.add(new SetCardInfo("Submerged Boneyard", 301, Rarity.UNCOMMON, mage.cards.s.SubmergedBoneyard.class));
        cards.add(new SetCardInfo("Sunbaked Canyon", 111, Rarity.RARE, mage.cards.s.SunbakedCanyon.class));
        cards.add(new SetCardInfo("Surtr, Fiery Jotun", 293, Rarity.RARE, mage.cards.s.SurtrFieryJotun.class));
        cards.add(new SetCardInfo("Sword of Feast and Famine", 99, Rarity.MYTHIC, mage.cards.s.SwordOfFeastAndFamine.class));
        cards.add(new SetCardInfo("Sword of Light and Shadow", 100, Rarity.MYTHIC, mage.cards.s.SwordOfLightAndShadow.class));
        cards.add(new SetCardInfo("Templar Knight", 10, Rarity.UNCOMMON, mage.cards.t.TemplarKnight.class));
        cards.add(new SetCardInfo("Temporal Trespass", 86, Rarity.MYTHIC, mage.cards.t.TemporalTrespass.class));
        cards.add(new SetCardInfo("Terminate", 98, Rarity.UNCOMMON, mage.cards.t.Terminate.class));
        cards.add(new SetCardInfo("The Animus", 69, Rarity.RARE, mage.cards.t.TheAnimus.class));
        cards.add(new SetCardInfo("The Spear of Leonidas", 38, Rarity.RARE, mage.cards.t.TheSpearOfLeonidas.class));
        cards.add(new SetCardInfo("Towering Viewpoint", 77, Rarity.UNCOMMON, mage.cards.t.ToweringViewpoint.class));
        cards.add(new SetCardInfo("Tranquilize", 284, Rarity.COMMON, mage.cards.t.Tranquilize.class));
        cards.add(new SetCardInfo("Waterlogged Grove", 116, Rarity.RARE, mage.cards.w.WaterloggedGrove.class));
    }
}
