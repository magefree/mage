package mage.sets;

import mage.cards.CardGraphicInfo;
import mage.cards.ExpansionSet;
import mage.cards.FrameStyle;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 *
 * @author lopho
 */
public final class Unglued extends ExpansionSet {

    private static final Unglued instance = new Unglued();

    public static Unglued getInstance() {
        return instance;
    }

    private Unglued() {
        super("Unglued", "UGL", ExpansionSet.buildDate(1998, 8, 11), SetType.JOKESET);

        cards.add(new SetCardInfo("Chicken Egg", 41, Rarity.COMMON, mage.cards.c.ChickenEgg.class));
        cards.add(new SetCardInfo("Chicken a la King", 17, Rarity.RARE, mage.cards.c.ChickenALaKing.class));
        cards.add(new SetCardInfo("Elvish Impersonators", 56, Rarity.COMMON, mage.cards.e.ElvishImpersonators.class));
        cards.add(new SetCardInfo("Forest", 88, Rarity.LAND, mage.cards.basiclands.Forest.class, new CardGraphicInfo(FrameStyle.UGL_FULL_ART_BASIC, false)));
        cards.add(new SetCardInfo("Fowl Play", 24, Rarity.COMMON, mage.cards.f.FowlPlay.class));
        cards.add(new SetCardInfo("Gerrymandering", 59, Rarity.UNCOMMON, mage.cards.g.Gerrymandering.class));
        cards.add(new SetCardInfo("Goblin Tutor", 45, Rarity.UNCOMMON, mage.cards.g.GoblinTutor.class));
        cards.add(new SetCardInfo("Growth Spurt", 61, Rarity.COMMON, mage.cards.g.GrowthSpurt.class));
        cards.add(new SetCardInfo("Hungry Hungry Heifer", 63, Rarity.UNCOMMON, mage.cards.h.HungryHungryHeifer.class));
        cards.add(new SetCardInfo("Incoming!", 64, Rarity.RARE, mage.cards.i.Incoming.class));
        cards.add(new SetCardInfo("Island", 85, Rarity.LAND, mage.cards.basiclands.Island.class, new CardGraphicInfo(FrameStyle.UGL_FULL_ART_BASIC, false)));
        cards.add(new SetCardInfo("Jack-in-the-Mox", 75, Rarity.RARE, mage.cards.j.JackInTheMox.class));
        cards.add(new SetCardInfo("Jumbo Imp", 34, Rarity.UNCOMMON, mage.cards.j.JumboImp.class));
        cards.add(new SetCardInfo("Krazy Kow", 48, Rarity.COMMON, mage.cards.k.KrazyKow.class));
        cards.add(new SetCardInfo("Mine, Mine, Mine!", 65, Rarity.RARE, mage.cards.m.MineMineMine.class));
        cards.add(new SetCardInfo("Mountain", 87, Rarity.LAND, mage.cards.basiclands.Mountain.class, new CardGraphicInfo(FrameStyle.UGL_FULL_ART_BASIC, false)));
        cards.add(new SetCardInfo("Once More With Feeling", 11, Rarity.RARE, mage.cards.o.OnceMoreWithFeeling.class));
        cards.add(new SetCardInfo("Paper Tiger", 78, Rarity.COMMON, mage.cards.p.PaperTiger.class));
        cards.add(new SetCardInfo("Plains", 84, Rarity.LAND, mage.cards.basiclands.Plains.class, new CardGraphicInfo(FrameStyle.UGL_FULL_ART_BASIC, false)));
        cards.add(new SetCardInfo("Poultrygeist", 37, Rarity.COMMON, mage.cards.p.Poultrygeist.class));
        cards.add(new SetCardInfo("Rock Lobster", 79, Rarity.COMMON, mage.cards.r.RockLobster.class));
        cards.add(new SetCardInfo("Scissors Lizard", 80, Rarity.COMMON, mage.cards.s.ScissorsLizard.class));
        cards.add(new SetCardInfo("Spark Fiend", 51, Rarity.RARE, mage.cards.s.SparkFiend.class));
        cards.add(new SetCardInfo("Strategy, Schmategy", 52, Rarity.RARE, mage.cards.s.StrategySchmategy.class));
        cards.add(new SetCardInfo("Swamp", 86, Rarity.LAND, mage.cards.basiclands.Swamp.class, new CardGraphicInfo(FrameStyle.UGL_FULL_ART_BASIC, false)));
        cards.add(new SetCardInfo("The Cheese Stands Alone", 2, Rarity.RARE, mage.cards.t.TheCheeseStandsAlone.class));
        cards.add(new SetCardInfo("Timmy, Power Gamer", 68, Rarity.RARE, mage.cards.t.TimmyPowerGamer.class));
        cards.add(new SetCardInfo("Urza's Science Fair Project", 83, Rarity.UNCOMMON, mage.cards.u.UrzasScienceFairProject.class));
    }
}
