
package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author LevelX2
 */
public final class DuelDecksAjaniVsNicolBolas extends ExpansionSet {

    private static final DuelDecksAjaniVsNicolBolas instance = new DuelDecksAjaniVsNicolBolas();

    public static DuelDecksAjaniVsNicolBolas getInstance() {
        return instance;
    }

    private DuelDecksAjaniVsNicolBolas() {
        super("Duel Decks: Ajani vs. Nicol Bolas", "DDH", ExpansionSet.buildDate(2011, 9, 2), SetType.SUPPLEMENTAL);
        this.blockName = "Duel Decks";
        this.hasBasicLands = true;

        cards.add(new SetCardInfo("Ageless Entity", 18, Rarity.RARE, mage.cards.a.AgelessEntity.class));
        cards.add(new SetCardInfo("Agonizing Demise", 66, Rarity.COMMON, mage.cards.a.AgonizingDemise.class));
        cards.add(new SetCardInfo("Ajani's Mantra", 22, Rarity.COMMON, mage.cards.a.AjanisMantra.class));
        cards.add(new SetCardInfo("Ajani's Pridemate", 9, Rarity.UNCOMMON, mage.cards.a.AjanisPridemate.class));
        cards.add(new SetCardInfo("Ajani Vengeant", 1, Rarity.MYTHIC, mage.cards.a.AjaniVengeant.class));
        cards.add(new SetCardInfo("Behemoth Sledge", 28, Rarity.UNCOMMON, mage.cards.b.BehemothSledge.class));
        cards.add(new SetCardInfo("Blazing Specter", 52, Rarity.RARE, mage.cards.b.BlazingSpecter.class));
        cards.add(new SetCardInfo("Brackwater Elemental", 46, Rarity.COMMON, mage.cards.b.BrackwaterElemental.class));
        cards.add(new SetCardInfo("Briarhorn", 14, Rarity.UNCOMMON, mage.cards.b.Briarhorn.class));
        cards.add(new SetCardInfo("Canyon Wildcat", 6, Rarity.COMMON, mage.cards.c.CanyonWildcat.class));
        cards.add(new SetCardInfo("Countersquall", 59, Rarity.UNCOMMON, mage.cards.c.Countersquall.class));
        cards.add(new SetCardInfo("Cruel Ultimatum", 69, Rarity.RARE, mage.cards.c.CruelUltimatum.class));
        cards.add(new SetCardInfo("Crumbling Necropolis", 74, Rarity.UNCOMMON, mage.cards.c.CrumblingNecropolis.class));
        cards.add(new SetCardInfo("Deep Analysis", 65, Rarity.UNCOMMON, mage.cards.d.DeepAnalysis.class));
        cards.add(new SetCardInfo("Dimir Cutpurse", 49, Rarity.RARE, mage.cards.d.DimirCutpurse.class));
        cards.add(new SetCardInfo("Elder Mastery", 68, Rarity.UNCOMMON, mage.cards.e.ElderMastery.class));
        cards.add(new SetCardInfo("Essence Warden", 3, Rarity.COMMON, mage.cards.e.EssenceWarden.class));
        cards.add(new SetCardInfo("Evolving Wilds", 32, Rarity.COMMON, mage.cards.e.EvolvingWilds.class));
        cards.add(new SetCardInfo("Fire-Field Ogre", 53, Rarity.UNCOMMON, mage.cards.f.FireFieldOgre.class));
        cards.add(new SetCardInfo("Firemane Angel", 21, Rarity.RARE, mage.cards.f.FiremaneAngel.class));
        cards.add(new SetCardInfo("Fleetfoot Panther", 12, Rarity.UNCOMMON, mage.cards.f.FleetfootPanther.class));
        cards.add(new SetCardInfo("Forest", 38, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 39, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Graypelt Refuge", 33, Rarity.UNCOMMON, mage.cards.g.GraypeltRefuge.class));
        cards.add(new SetCardInfo("Grazing Gladehart", 11, Rarity.COMMON, mage.cards.g.GrazingGladehart.class));
        cards.add(new SetCardInfo("Griffin Guide", 25, Rarity.UNCOMMON, mage.cards.g.GriffinGuide.class));
        cards.add(new SetCardInfo("Grixis Charm", 63, Rarity.UNCOMMON, mage.cards.g.GrixisCharm.class));
        cards.add(new SetCardInfo("Hellfire Mongrel", 48, Rarity.UNCOMMON, mage.cards.h.HellfireMongrel.class));
        cards.add(new SetCardInfo("Icy Manipulator", 64, Rarity.UNCOMMON, mage.cards.i.IcyManipulator.class));
        cards.add(new SetCardInfo("Igneous Pouncer", 57, Rarity.COMMON, mage.cards.i.IgneousPouncer.class));
        cards.add(new SetCardInfo("Island", 79, Rarity.LAND, mage.cards.basiclands.Island.class));
        cards.add(new SetCardInfo("Jade Mage", 7, Rarity.UNCOMMON, mage.cards.j.JadeMage.class));
        cards.add(new SetCardInfo("Jhessian Zombies", 56, Rarity.COMMON, mage.cards.j.JhessianZombies.class));
        cards.add(new SetCardInfo("Jungle Shrine", 34, Rarity.UNCOMMON, mage.cards.j.JungleShrine.class));
        cards.add(new SetCardInfo("Kazandu Refuge", 35, Rarity.UNCOMMON, mage.cards.k.KazanduRefuge.class));
        cards.add(new SetCardInfo("Kird Ape", 2, Rarity.UNCOMMON, mage.cards.k.KirdApe.class));
        cards.add(new SetCardInfo("Lead the Stampede", 24, Rarity.UNCOMMON, mage.cards.l.LeadTheStampede.class));
        cards.add(new SetCardInfo("Lightning Helix", 23, Rarity.UNCOMMON, mage.cards.l.LightningHelix.class));
        cards.add(new SetCardInfo("Loam Lion", 5, Rarity.UNCOMMON, mage.cards.l.LoamLion.class));
        cards.add(new SetCardInfo("Loxodon Hierarch", 15, Rarity.RARE, mage.cards.l.LoxodonHierarch.class));
        cards.add(new SetCardInfo("Marisi's Twinclaws", 17, Rarity.UNCOMMON, mage.cards.m.MarisisTwinclaws.class));
        cards.add(new SetCardInfo("Morgue Toad", 47, Rarity.COMMON, mage.cards.m.MorgueToad.class));
        cards.add(new SetCardInfo("Moroii", 51, Rarity.UNCOMMON, mage.cards.m.Moroii.class));
        cards.add(new SetCardInfo("Mountain", 41, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 80, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Nacatl Hunt-Pride", 20, Rarity.UNCOMMON, mage.cards.n.NacatlHuntPride.class));
        cards.add(new SetCardInfo("Naya Charm", 29, Rarity.UNCOMMON, mage.cards.n.NayaCharm.class));
        cards.add(new SetCardInfo("Nicol Bolas, Planeswalker", 42, Rarity.MYTHIC, mage.cards.n.NicolBolasPlaneswalker.class));
        cards.add(new SetCardInfo("Nightscape Familiar", 44, Rarity.COMMON, mage.cards.n.NightscapeFamiliar.class));
        cards.add(new SetCardInfo("Obelisk of Grixis", 60, Rarity.COMMON, mage.cards.o.ObeliskOfGrixis.class));
        cards.add(new SetCardInfo("Ogre Savant", 55, Rarity.COMMON, mage.cards.o.OgreSavant.class));
        cards.add(new SetCardInfo("Pain // Suffering", 72, Rarity.UNCOMMON, mage.cards.p.PainSuffering.class));
        cards.add(new SetCardInfo("Plains", 40, Rarity.LAND, mage.cards.basiclands.Plains.class));
        cards.add(new SetCardInfo("Pride of Lions", 19, Rarity.UNCOMMON, mage.cards.p.PrideOfLions.class));
        cards.add(new SetCardInfo("Profane Command", 70, Rarity.RARE, mage.cards.p.ProfaneCommand.class));
        cards.add(new SetCardInfo("Qasali Pridemage", 10, Rarity.COMMON, mage.cards.q.QasaliPridemage.class));
        cards.add(new SetCardInfo("Recoil", 61, Rarity.COMMON, mage.cards.r.Recoil.class));
        cards.add(new SetCardInfo("Recumbent Bliss", 26, Rarity.COMMON, mage.cards.r.RecumbentBliss.class));
        cards.add(new SetCardInfo("Rise // Fall", 73, Rarity.UNCOMMON, mage.cards.r.RiseFall.class));
        cards.add(new SetCardInfo("Rupture Spire", 75, Rarity.COMMON, mage.cards.r.RuptureSpire.class));
        cards.add(new SetCardInfo("Sapseep Forest", 36, Rarity.UNCOMMON, mage.cards.s.SapseepForest.class));
        cards.add(new SetCardInfo("Searing Meditation", 27, Rarity.RARE, mage.cards.s.SearingMeditation.class));
        cards.add(new SetCardInfo("Shriekmaw", 54, Rarity.UNCOMMON, mage.cards.s.Shriekmaw.class));
        cards.add(new SetCardInfo("Slave of Bolas", 67, Rarity.UNCOMMON, mage.cards.s.SlaveOfBolas.class));
        cards.add(new SetCardInfo("Slavering Nulls", 45, Rarity.UNCOMMON, mage.cards.s.SlaveringNulls.class));
        cards.add(new SetCardInfo("Spite // Malice", 71, Rarity.UNCOMMON, mage.cards.s.SpiteMalice.class));
        cards.add(new SetCardInfo("Spitemare", 16, Rarity.UNCOMMON, mage.cards.s.Spitemare.class));
        cards.add(new SetCardInfo("Steamcore Weird", 50, Rarity.COMMON, mage.cards.s.SteamcoreWeird.class));
        cards.add(new SetCardInfo("Surveilling Sprite", 43, Rarity.COMMON, mage.cards.s.SurveillingSprite.class));
        cards.add(new SetCardInfo("Swamp", 77, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 78, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sylvan Bounty", 30, Rarity.COMMON, mage.cards.s.SylvanBounty.class));
        cards.add(new SetCardInfo("Sylvan Ranger", 8, Rarity.COMMON, mage.cards.s.SylvanRanger.class));
        cards.add(new SetCardInfo("Terramorphic Expanse", 76, Rarity.COMMON, mage.cards.t.TerramorphicExpanse.class));
        cards.add(new SetCardInfo("Titanic Ultimatum", 31, Rarity.RARE, mage.cards.t.TitanicUltimatum.class));
        cards.add(new SetCardInfo("Undermine", 62, Rarity.RARE, mage.cards.u.Undermine.class));
        cards.add(new SetCardInfo("Vapor Snag", 58, Rarity.COMMON, mage.cards.v.VaporSnag.class));
        cards.add(new SetCardInfo("Vitu-Ghazi, the City-Tree", 37, Rarity.UNCOMMON, mage.cards.v.VituGhaziTheCityTree.class));
        cards.add(new SetCardInfo("Wild Nacatl", 4, Rarity.COMMON, mage.cards.w.WildNacatl.class));
        cards.add(new SetCardInfo("Woolly Thoctar", 13, Rarity.UNCOMMON, mage.cards.w.WoollyThoctar.class));
    }
}
