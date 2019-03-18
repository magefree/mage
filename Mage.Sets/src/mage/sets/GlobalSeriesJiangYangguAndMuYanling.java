package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class GlobalSeriesJiangYangguAndMuYanling extends ExpansionSet {

    private static final GlobalSeriesJiangYangguAndMuYanling instance = new GlobalSeriesJiangYangguAndMuYanling();

    public static GlobalSeriesJiangYangguAndMuYanling getInstance() {
        return instance;
    }

    private GlobalSeriesJiangYangguAndMuYanling() {
        super("Global Series: Jiang Yanggu & Mu Yanling", "GS1", ExpansionSet.buildDate(2018, 6, 22), SetType.SUPPLEMENTAL);
        this.blockName = "Global Series";
        this.hasBasicLands = true;

        cards.add(new SetCardInfo("Aggressive Instinct", 34, Rarity.COMMON, mage.cards.a.AggressiveInstinct.class));
        cards.add(new SetCardInfo("Ancestor Dragon", 12, Rarity.RARE, mage.cards.a.AncestorDragon.class));
        cards.add(new SetCardInfo("Armored Whirl Turtle", 7, Rarity.COMMON, mage.cards.a.ArmoredWhirlTurtle.class));
        cards.add(new SetCardInfo("Breath of Fire", 33, Rarity.COMMON, mage.cards.b.BreathOfFire.class));
        cards.add(new SetCardInfo("Brilliant Plan", 17, Rarity.UNCOMMON, mage.cards.b.BrilliantPlan.class));
        cards.add(new SetCardInfo("Cleansing Screech", 37, Rarity.COMMON, mage.cards.c.CleansingScreech.class));
        cards.add(new SetCardInfo("Cloak of Mists", 13, Rarity.COMMON, mage.cards.c.CloakOfMists.class));
        cards.add(new SetCardInfo("Colorful Feiyi Sparrow", 2, Rarity.COMMON, mage.cards.c.ColorfulFeiyiSparrow.class));
        cards.add(new SetCardInfo("Confidence from Strength", 35, Rarity.COMMON, mage.cards.c.ConfidenceFromStrength.class));
        cards.add(new SetCardInfo("Dragon's Presence", 16, Rarity.COMMON, mage.cards.d.DragonsPresence.class));
        cards.add(new SetCardInfo("Drown in Shapelessness", 15, Rarity.COMMON, mage.cards.d.DrownInShapelessness.class));
        cards.add(new SetCardInfo("Earth-Origin Yak", 9, Rarity.COMMON, mage.cards.e.EarthOriginYak.class));
        cards.add(new SetCardInfo("Earthshaking Si", 31, Rarity.COMMON, mage.cards.e.EarthshakingSi.class));
        cards.add(new SetCardInfo("Feiyi Snake", 24, Rarity.COMMON, mage.cards.f.FeiyiSnake.class));
        cards.add(new SetCardInfo("Ferocious Zheng", 28, Rarity.COMMON, mage.cards.f.FerociousZheng.class));
        cards.add(new SetCardInfo("Fire-Omen Crane", 29, Rarity.UNCOMMON, mage.cards.f.FireOmenCrane.class));
        cards.add(new SetCardInfo("Forest", 40, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Giant Spider", 27, Rarity.COMMON, mage.cards.g.GiantSpider.class));
        cards.add(new SetCardInfo("Hardened-Scale Armor", 32, Rarity.COMMON, mage.cards.h.HardenedScaleArmor.class));
        cards.add(new SetCardInfo("Heavenly Qilin", 6, Rarity.COMMON, mage.cards.h.HeavenlyQilin.class));
        cards.add(new SetCardInfo("Island", 21, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Jiang Yanggu", 22, Rarity.MYTHIC, mage.cards.j.JiangYanggu.class));
        cards.add(new SetCardInfo("Journey for the Elixir", 36, Rarity.RARE, mage.cards.j.JourneyForTheElixir.class));
        cards.add(new SetCardInfo("Leopard-Spotted Jiao", 23, Rarity.COMMON, mage.cards.l.LeopardSpottedJiao.class));
        cards.add(new SetCardInfo("Meandering River", 19, Rarity.COMMON, mage.cards.m.MeanderingRiver.class));
        cards.add(new SetCardInfo("Moon-Eating Dog", 10, Rarity.UNCOMMON, mage.cards.m.MoonEatingDog.class));
        cards.add(new SetCardInfo("Mountain", 39, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mu Yanling", 1, Rarity.MYTHIC, mage.cards.m.MuYanling.class));
        cards.add(new SetCardInfo("Nine-Tail White Fox", 8, Rarity.COMMON, mage.cards.n.NineTailWhiteFox.class));
        cards.add(new SetCardInfo("Plains", 20, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Purple-Crystal Crab", 3, Rarity.COMMON, mage.cards.p.PurpleCrystalCrab.class));
        cards.add(new SetCardInfo("Qilin's Blessing", 14, Rarity.COMMON, mage.cards.q.QilinsBlessing.class));
        cards.add(new SetCardInfo("Reckless Pangolin", 26, Rarity.COMMON, mage.cards.r.RecklessPangolin.class));
        cards.add(new SetCardInfo("Rhythmic Water Vortex", 18, Rarity.RARE, mage.cards.r.RhythmicWaterVortex.class));
        cards.add(new SetCardInfo("Sacred White Deer", 25, Rarity.UNCOMMON, mage.cards.s.SacredWhiteDeer.class));
        cards.add(new SetCardInfo("Screeching Phoenix", 30, Rarity.RARE, mage.cards.s.ScreechingPhoenix.class));
        cards.add(new SetCardInfo("Stormcloud Spirit", 11, Rarity.UNCOMMON, mage.cards.s.StormcloudSpirit.class));
        cards.add(new SetCardInfo("Timber Gorge", 38, Rarity.COMMON, mage.cards.t.TimberGorge.class));
        cards.add(new SetCardInfo("Vivid Flying Fish", 4, Rarity.COMMON, mage.cards.v.VividFlyingFish.class));
        cards.add(new SetCardInfo("Welkin Tern", 5, Rarity.COMMON, mage.cards.w.WelkinTern.class));
    }
}
