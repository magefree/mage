package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 *
 * @author TheElk801
 */
public final class JiangYangguMuYanling extends ExpansionSet {

    private static final JiangYangguMuYanling instance = new JiangYangguMuYanling();

    public static JiangYangguMuYanling getInstance() {
        return instance;
    }

    private JiangYangguMuYanling() {
        super("Global Series: Jiang Yanggu & Mu Yanling", "GS1", ExpansionSet.buildDate(2018, 6, 22), SetType.SUPPLEMENTAL_STANDARD_LEGAL);
        this.blockName = "Global Series";
        this.hasBasicLands = true;
        cards.add(new SetCardInfo("Armored Whirl Turtle", 7, Rarity.COMMON, mage.cards.a.ArmoredWhirlTurtle.class));
        cards.add(new SetCardInfo("Breath of Fire", 33, Rarity.COMMON, mage.cards.b.BreathOfFire.class));
        cards.add(new SetCardInfo("Brilliant Plan", 17, Rarity.UNCOMMON, mage.cards.b.BrilliantPlan.class));
        cards.add(new SetCardInfo("Cleansing Screech", 37, Rarity.COMMON, mage.cards.c.CleansingScreech.class));
        cards.add(new SetCardInfo("Cloak of Mists", 13, Rarity.COMMON, mage.cards.c.CloakOfMists.class));
        cards.add(new SetCardInfo("Colorful Feiyi Sparrow", 2, Rarity.COMMON, mage.cards.c.ColorfulFeiyiSparrow.class));
        cards.add(new SetCardInfo("Drown in Shapelessness", 15, Rarity.COMMON, mage.cards.d.DrownInShapelessness.class));
        cards.add(new SetCardInfo("Earthshaking Si", 31, Rarity.COMMON, mage.cards.e.EarthshakingSi.class));
        cards.add(new SetCardInfo("Feiyi Snake", 24, Rarity.COMMON, mage.cards.f.FeiyiSnake.class));
        cards.add(new SetCardInfo("Ferocious Zheng", 28, Rarity.COMMON, mage.cards.f.FerociousZheng.class));
        cards.add(new SetCardInfo("Forest", 40, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Giant Spider", 27, Rarity.COMMON, mage.cards.g.GiantSpider.class));
        cards.add(new SetCardInfo("Island", 21, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Jiang Yanggu", 22, Rarity.MYTHIC, mage.cards.j.JiangYanggu.class));
        cards.add(new SetCardInfo("Meandering River", 19, Rarity.COMMON, mage.cards.m.MeanderingRiver.class));
        cards.add(new SetCardInfo("Mountain", 39, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mu Yanling", 1, Rarity.MYTHIC, mage.cards.m.MuYanling.class));
        cards.add(new SetCardInfo("Plains", 20, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Timber Gorge", 38, Rarity.COMMON, mage.cards.t.TimberGorge.class));
        cards.add(new SetCardInfo("Welkin Tern", 5, Rarity.COMMON, mage.cards.w.WelkinTern.class));
    }
}
