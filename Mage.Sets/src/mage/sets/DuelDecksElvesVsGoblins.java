package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author LevelX2
 */
public final class DuelDecksElvesVsGoblins extends ExpansionSet {

    private static final DuelDecksElvesVsGoblins instance = new DuelDecksElvesVsGoblins();

    public static DuelDecksElvesVsGoblins getInstance() {
        return instance;
    }

    private DuelDecksElvesVsGoblins() {
        super("Duel Decks: Elves vs. Goblins", "DD1", ExpansionSet.buildDate(2007, 11, 16), SetType.SUPPLEMENTAL);
        this.blockName = "Duel Decks";
        this.hasBasicLands = true;

        cards.add(new SetCardInfo("Akki Coalflinger", 33, Rarity.UNCOMMON, mage.cards.a.AkkiCoalflinger.class));
        cards.add(new SetCardInfo("Allosaurus Rider", 2, Rarity.RARE, mage.cards.a.AllosaurusRider.class));
        cards.add(new SetCardInfo("Ambush Commander", 1, Rarity.RARE, mage.cards.a.AmbushCommander.class));
        cards.add(new SetCardInfo("Boggart Shenanigans", 54, Rarity.UNCOMMON, mage.cards.b.BoggartShenanigans.class));
        cards.add(new SetCardInfo("Clickslither", 34, Rarity.RARE, mage.cards.c.Clickslither.class));
        cards.add(new SetCardInfo("Elvish Eulogist", 3, Rarity.COMMON, mage.cards.e.ElvishEulogist.class));
        cards.add(new SetCardInfo("Elvish Harbinger", 4, Rarity.UNCOMMON, mage.cards.e.ElvishHarbinger.class));
        cards.add(new SetCardInfo("Elvish Promenade", 20, Rarity.UNCOMMON, mage.cards.e.ElvishPromenade.class));
        cards.add(new SetCardInfo("Elvish Warrior", 5, Rarity.COMMON, mage.cards.e.ElvishWarrior.class));
        cards.add(new SetCardInfo("Emberwilde Augur", 35, Rarity.COMMON, mage.cards.e.EmberwildeAugur.class));
        cards.add(new SetCardInfo("Flamewave Invoker", 36, Rarity.UNCOMMON, mage.cards.f.FlamewaveInvoker.class));
        cards.add(new SetCardInfo("Forest", 28, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 29, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 30, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 31, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forgotten Cave", 57, Rarity.COMMON, mage.cards.f.ForgottenCave.class));
        cards.add(new SetCardInfo("Gempalm Incinerator", 37, Rarity.UNCOMMON, mage.cards.g.GempalmIncinerator.class));
        cards.add(new SetCardInfo("Gempalm Strider", 6, Rarity.UNCOMMON, mage.cards.g.GempalmStrider.class));
        cards.add(new SetCardInfo("Giant Growth", 21, Rarity.COMMON, mage.cards.g.GiantGrowth.class));
        cards.add(new SetCardInfo("Goblin Burrows", 58, Rarity.UNCOMMON, mage.cards.g.GoblinBurrows.class));
        cards.add(new SetCardInfo("Goblin Cohort", 38, Rarity.COMMON, mage.cards.g.GoblinCohort.class));
        cards.add(new SetCardInfo("Goblin Matron", 39, Rarity.UNCOMMON, mage.cards.g.GoblinMatron.class));
        cards.add(new SetCardInfo("Goblin Ringleader", 40, Rarity.UNCOMMON, mage.cards.g.GoblinRingleader.class));
        cards.add(new SetCardInfo("Goblin Sledder", 41, Rarity.COMMON, mage.cards.g.GoblinSledder.class));
        cards.add(new SetCardInfo("Goblin Warchief", 42, Rarity.UNCOMMON, mage.cards.g.GoblinWarchief.class));
        cards.add(new SetCardInfo("Harmonize", 22, Rarity.UNCOMMON, mage.cards.h.Harmonize.class));
        cards.add(new SetCardInfo("Heedless One", 7, Rarity.UNCOMMON, mage.cards.h.HeedlessOne.class));
        cards.add(new SetCardInfo("Ib Halfheart, Goblin Tactician", 43, Rarity.RARE, mage.cards.i.IbHalfheartGoblinTactician.class));
        cards.add(new SetCardInfo("Imperious Perfect", 8, Rarity.UNCOMMON, mage.cards.i.ImperiousPerfect.class));
        cards.add(new SetCardInfo("Llanowar Elves", 9, Rarity.COMMON, mage.cards.l.LlanowarElves.class));
        cards.add(new SetCardInfo("Lys Alana Huntmaster", 10, Rarity.COMMON, mage.cards.l.LysAlanaHuntmaster.class));
        cards.add(new SetCardInfo("Mogg Fanatic", 44, Rarity.UNCOMMON, mage.cards.m.MoggFanatic.class));
        cards.add(new SetCardInfo("Mogg War Marshal", 45, Rarity.COMMON, mage.cards.m.MoggWarMarshal.class));
        cards.add(new SetCardInfo("Moonglove Extract", 24, Rarity.COMMON, mage.cards.m.MoongloveExtract.class));
        cards.add(new SetCardInfo("Mountain", 59, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 60, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 61, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 62, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mudbutton Torchrunner", 46, Rarity.COMMON, mage.cards.m.MudbuttonTorchrunner.class));
        cards.add(new SetCardInfo("Raging Goblin", 47, Rarity.COMMON, mage.cards.r.RagingGoblin.class));
        cards.add(new SetCardInfo("Reckless One", 48, Rarity.UNCOMMON, mage.cards.r.RecklessOne.class));
        cards.add(new SetCardInfo("Siege-Gang Commander", 32, Rarity.RARE, mage.cards.s.SiegeGangCommander.class));
        cards.add(new SetCardInfo("Skirk Drill Sergeant", 49, Rarity.UNCOMMON, mage.cards.s.SkirkDrillSergeant.class));
        cards.add(new SetCardInfo("Skirk Fire Marshal", 50, Rarity.RARE, mage.cards.s.SkirkFireMarshal.class));
        cards.add(new SetCardInfo("Skirk Prospector", 51, Rarity.COMMON, mage.cards.s.SkirkProspector.class));
        cards.add(new SetCardInfo("Skirk Shaman", 52, Rarity.COMMON, mage.cards.s.SkirkShaman.class));
        cards.add(new SetCardInfo("Slate of Ancestry", 25, Rarity.RARE, mage.cards.s.SlateOfAncestry.class));
        cards.add(new SetCardInfo("Spitting Earth", 55, Rarity.COMMON, mage.cards.s.SpittingEarth.class));
        cards.add(new SetCardInfo("Stonewood Invoker", 11, Rarity.COMMON, mage.cards.s.StonewoodInvoker.class));
        cards.add(new SetCardInfo("Sylvan Messenger", 12, Rarity.UNCOMMON, mage.cards.s.SylvanMessenger.class));
        cards.add(new SetCardInfo("Tarfire", 56, Rarity.COMMON, mage.cards.t.Tarfire.class));
        cards.add(new SetCardInfo("Tar Pitcher", 53, Rarity.UNCOMMON, mage.cards.t.TarPitcher.class));
        cards.add(new SetCardInfo("Timberwatch Elf", 13, Rarity.COMMON, mage.cards.t.TimberwatchElf.class));
        cards.add(new SetCardInfo("Tranquil Thicket", 27, Rarity.COMMON, mage.cards.t.TranquilThicket.class));
        cards.add(new SetCardInfo("Voice of the Woods", 14, Rarity.RARE, mage.cards.v.VoiceOfTheWoods.class));
        cards.add(new SetCardInfo("Wellwisher", 15, Rarity.COMMON, mage.cards.w.Wellwisher.class));
        cards.add(new SetCardInfo("Wildsize", 23, Rarity.COMMON, mage.cards.w.Wildsize.class));
        cards.add(new SetCardInfo("Wirewood Herald", 16, Rarity.COMMON, mage.cards.w.WirewoodHerald.class));
        cards.add(new SetCardInfo("Wirewood Lodge", 26, Rarity.UNCOMMON, mage.cards.w.WirewoodLodge.class));
        cards.add(new SetCardInfo("Wirewood Symbiote", 17, Rarity.UNCOMMON, mage.cards.w.WirewoodSymbiote.class));
        cards.add(new SetCardInfo("Wood Elves", 18, Rarity.COMMON, mage.cards.w.WoodElves.class));
        cards.add(new SetCardInfo("Wren's Run Vanquisher", 19, Rarity.UNCOMMON, mage.cards.w.WrensRunVanquisher.class));
    }
}
