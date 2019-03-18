
package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class DuelDecksElvesVsInventors extends ExpansionSet {

    private static final DuelDecksElvesVsInventors instance = new DuelDecksElvesVsInventors();

    public static DuelDecksElvesVsInventors getInstance() {
        return instance;
    }

    private DuelDecksElvesVsInventors() {
        super("Duel Decks: Elves vs. Inventors", "DDU", ExpansionSet.buildDate(2018, 4, 6), SetType.SUPPLEMENTAL);
        this.blockName = "Duel Decks";

        cards.add(new SetCardInfo("Artificer's Epiphany", 36, Rarity.COMMON, mage.cards.a.ArtificersEpiphany.class));
        cards.add(new SetCardInfo("Barrage Ogre", 44, Rarity.UNCOMMON, mage.cards.b.BarrageOgre.class));
        cards.add(new SetCardInfo("Darksteel Citadel", 65, Rarity.UNCOMMON, mage.cards.d.DarksteelCitadel.class));
        cards.add(new SetCardInfo("Darksteel Plate", 52, Rarity.RARE, mage.cards.d.DarksteelPlate.class));
        cards.add(new SetCardInfo("Dwynen's Elite", 3, Rarity.UNCOMMON, mage.cards.d.DwynensElite.class));
        cards.add(new SetCardInfo("Dwynen, Gilt-Leaf Daen", 2, Rarity.RARE, mage.cards.d.DwynenGiltLeafDaen.class));
        cards.add(new SetCardInfo("Elvish Aberration", 4, Rarity.COMMON, mage.cards.e.ElvishAberration.class));
        cards.add(new SetCardInfo("Elvish Archdruid", 5, Rarity.RARE, mage.cards.e.ElvishArchdruid.class));
        cards.add(new SetCardInfo("Elvish Branchbender", 6, Rarity.COMMON, mage.cards.e.ElvishBranchbender.class));
        cards.add(new SetCardInfo("Elvish Mystic", 7, Rarity.COMMON, mage.cards.e.ElvishMystic.class));
        cards.add(new SetCardInfo("Elvish Vanguard", 8, Rarity.COMMON, mage.cards.e.ElvishVanguard.class));
        cards.add(new SetCardInfo("Etherium Sculptor", 37, Rarity.COMMON, mage.cards.e.EtheriumSculptor.class));
        cards.add(new SetCardInfo("Ezuri's Archers", 9, Rarity.COMMON, mage.cards.e.EzurisArchers.class));
        cards.add(new SetCardInfo("Ezuri, Renegade Leader", 1, Rarity.MYTHIC, mage.cards.e.EzuriRenegadeLeader.class));
        cards.add(new SetCardInfo("Faerie Mechanist", 38, Rarity.COMMON, mage.cards.f.FaerieMechanist.class));
        cards.add(new SetCardInfo("Fierce Empath", 10, Rarity.COMMON, mage.cards.f.FierceEmpath.class));
        cards.add(new SetCardInfo("Filigree Familiar", 53, Rarity.UNCOMMON, mage.cards.f.FiligreeFamiliar.class));
        cards.add(new SetCardInfo("Forest", 31, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 32, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 33, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 34, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Foundry of the Consuls", 66, Rarity.UNCOMMON, mage.cards.f.FoundryOfTheConsuls.class));
        cards.add(new SetCardInfo("Galvanic Blast", 45, Rarity.COMMON, mage.cards.g.GalvanicBlast.class));
        cards.add(new SetCardInfo("Ghirapur Gearcrafter", 46, Rarity.COMMON, mage.cards.g.GhirapurGearcrafter.class));
        cards.add(new SetCardInfo("Gladehart Cavalry", 11, Rarity.RARE, mage.cards.g.GladehartCavalry.class));
        cards.add(new SetCardInfo("Goblin Welder", 35, Rarity.MYTHIC, mage.cards.g.GoblinWelder.class));
        cards.add(new SetCardInfo("Great Furnace", 67, Rarity.COMMON, mage.cards.g.GreatFurnace.class));
        cards.add(new SetCardInfo("Ichor Wellspring", 54, Rarity.COMMON, mage.cards.i.IchorWellspring.class));
        cards.add(new SetCardInfo("Inventor's Goggles", 55, Rarity.COMMON, mage.cards.i.InventorsGoggles.class));
        cards.add(new SetCardInfo("Island", 73, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 74, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ivy Lane Denizen", 12, Rarity.COMMON, mage.cards.i.IvyLaneDenizen.class));
        cards.add(new SetCardInfo("Jagged-Scar Archers", 13, Rarity.UNCOMMON, mage.cards.j.JaggedScarArchers.class));
        cards.add(new SetCardInfo("Krosan Tusker", 14, Rarity.COMMON, mage.cards.k.KrosanTusker.class));
        cards.add(new SetCardInfo("Kujar Seedsculptor", 15, Rarity.COMMON, mage.cards.k.KujarSeedsculptor.class));
        cards.add(new SetCardInfo("Lead the Stampede", 16, Rarity.COMMON, mage.cards.l.LeadTheStampede.class));
        cards.add(new SetCardInfo("Leaf Gilder", 17, Rarity.COMMON, mage.cards.l.LeafGilder.class));
        cards.add(new SetCardInfo("Llanowar Empath", 18, Rarity.COMMON, mage.cards.l.LlanowarEmpath.class));
        cards.add(new SetCardInfo("Maverick Thopterist", 50, Rarity.UNCOMMON, mage.cards.m.MaverickThopterist.class));
        cards.add(new SetCardInfo("Mountain", 75, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 76, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mycosynth Wellspring", 56, Rarity.COMMON, mage.cards.m.MycosynthWellspring.class));
        cards.add(new SetCardInfo("Myr Battlesphere", 57, Rarity.RARE, mage.cards.m.MyrBattlesphere.class));
        cards.add(new SetCardInfo("Myr Sire", 58, Rarity.COMMON, mage.cards.m.MyrSire.class));
        cards.add(new SetCardInfo("Naturalize", 19, Rarity.COMMON, mage.cards.n.Naturalize.class));
        cards.add(new SetCardInfo("Nature's Way", 20, Rarity.UNCOMMON, mage.cards.n.NaturesWay.class));
        cards.add(new SetCardInfo("Neurok Replica", 59, Rarity.COMMON, mage.cards.n.NeurokReplica.class));
        cards.add(new SetCardInfo("Nissa's Judgment", 21, Rarity.UNCOMMON, mage.cards.n.NissasJudgment.class));
        cards.add(new SetCardInfo("Oran-Rief, the Vastwood", 28, Rarity.RARE, mage.cards.o.OranRiefTheVastwood.class));
        cards.add(new SetCardInfo("Phyrexia's Core", 68, Rarity.UNCOMMON, mage.cards.p.PhyrexiasCore.class));
        cards.add(new SetCardInfo("Pia and Kiran Nalaar", 47, Rarity.RARE, mage.cards.p.PiaAndKiranNalaar.class));
        cards.add(new SetCardInfo("Pyrite Spellbomb", 60, Rarity.COMMON, mage.cards.p.PyriteSpellbomb.class));
        cards.add(new SetCardInfo("Reclusive Artificer", 51, Rarity.UNCOMMON, mage.cards.r.ReclusiveArtificer.class));
        cards.add(new SetCardInfo("Regal Force", 22, Rarity.RARE, mage.cards.r.RegalForce.class));
        cards.add(new SetCardInfo("Riddlesmith", 39, Rarity.UNCOMMON, mage.cards.r.Riddlesmith.class));
        cards.add(new SetCardInfo("Scuttling Doom Engine", 61, Rarity.RARE, mage.cards.s.ScuttlingDoomEngine.class));
        cards.add(new SetCardInfo("Seat of the Synod", 69, Rarity.COMMON, mage.cards.s.SeatOfTheSynod.class));
        cards.add(new SetCardInfo("Shivan Reef", 70, Rarity.RARE, mage.cards.s.ShivanReef.class));
        cards.add(new SetCardInfo("Shrapnel Blast", 48, Rarity.UNCOMMON, mage.cards.s.ShrapnelBlast.class));
        cards.add(new SetCardInfo("Solemn Simulacrum", 62, Rarity.RARE, mage.cards.s.SolemnSimulacrum.class));
        cards.add(new SetCardInfo("Swiftwater Cliffs", 71, Rarity.COMMON, mage.cards.s.SwiftwaterCliffs.class));
        cards.add(new SetCardInfo("Sylvan Advocate", 23, Rarity.RARE, mage.cards.s.SylvanAdvocate.class));
        cards.add(new SetCardInfo("Talara's Battalion", 24, Rarity.RARE, mage.cards.t.TalarasBattalion.class));
        cards.add(new SetCardInfo("Temple of Epiphany", 72, Rarity.RARE, mage.cards.t.TempleOfEpiphany.class));
        cards.add(new SetCardInfo("Thopter Assembly", 63, Rarity.RARE, mage.cards.t.ThopterAssembly.class));
        cards.add(new SetCardInfo("Tranquil Thicket", 29, Rarity.COMMON, mage.cards.t.TranquilThicket.class));
        cards.add(new SetCardInfo("Treasure Mage", 40, Rarity.UNCOMMON, mage.cards.t.TreasureMage.class));
        cards.add(new SetCardInfo("Treetop Village", 30, Rarity.UNCOMMON, mage.cards.t.TreetopVillage.class));
        cards.add(new SetCardInfo("Trinket Mage", 41, Rarity.COMMON, mage.cards.t.TrinketMage.class));
        cards.add(new SetCardInfo("Trophy Mage", 42, Rarity.UNCOMMON, mage.cards.t.TrophyMage.class));
        cards.add(new SetCardInfo("Viridian Shaman", 25, Rarity.UNCOMMON, mage.cards.v.ViridianShaman.class));
        cards.add(new SetCardInfo("Voyager Staff", 64, Rarity.UNCOMMON, mage.cards.v.VoyagerStaff.class));
        cards.add(new SetCardInfo("Welding Sparks", 49, Rarity.COMMON, mage.cards.w.WeldingSparks.class));
        cards.add(new SetCardInfo("Whirler Rogue", 43, Rarity.UNCOMMON, mage.cards.w.WhirlerRogue.class));
        cards.add(new SetCardInfo("Wildheart Invoker", 26, Rarity.COMMON, mage.cards.w.WildheartInvoker.class));
        cards.add(new SetCardInfo("Yeva, Nature's Herald", 27, Rarity.RARE, mage.cards.y.YevaNaturesHerald.class));
    }
}
