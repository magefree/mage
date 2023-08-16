package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class WildsOfEldraine extends ExpansionSet {

    private static final WildsOfEldraine instance = new WildsOfEldraine();

    public static WildsOfEldraine getInstance() {
        return instance;
    }

    private WildsOfEldraine() {
        super("Wilds of Eldraine", "WOE", ExpansionSet.buildDate(2023, 9, 8), SetType.EXPANSION);
        this.blockName = "Wilds of Eldraine";
        this.hasBoosters = false; // temporary

        cards.add(new SetCardInfo("Ash, Party Crasher", 201, Rarity.UNCOMMON, mage.cards.a.AshPartyCrasher.class));
        cards.add(new SetCardInfo("Cruel Somnophage", 222, Rarity.RARE, mage.cards.c.CruelSomnophage.class));
        cards.add(new SetCardInfo("Evolving Wilds", 256, Rarity.COMMON, mage.cards.e.EvolvingWilds.class));
        cards.add(new SetCardInfo("Faerie Dreamthief", 89, Rarity.UNCOMMON, mage.cards.f.FaerieDreamthief.class));
        cards.add(new SetCardInfo("Forest", 266, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Glass Casket", 16, Rarity.UNCOMMON, mage.cards.g.GlassCasket.class));
        cards.add(new SetCardInfo("Greta, Sweettooth Scourge", 205, Rarity.UNCOMMON, mage.cards.g.GretaSweettoothScourge.class));
        cards.add(new SetCardInfo("Island", 263, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Moonshaker Cavalry", 21, Rarity.MYTHIC, mage.cards.m.MoonshakerCavalry.class));
        cards.add(new SetCardInfo("Mountain", 265, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 262, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Rat Out", 103, Rarity.COMMON, mage.cards.r.RatOut.class));
        cards.add(new SetCardInfo("Restless Fortress", 259, Rarity.RARE, mage.cards.r.RestlessFortress.class));
        cards.add(new SetCardInfo("Sleight of Hand", 67, Rarity.COMMON, mage.cards.s.SleightOfHand.class));
        cards.add(new SetCardInfo("Swamp", 264, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Syr Ginger, the Meal Ender", 252, Rarity.RARE, mage.cards.s.SyrGingerTheMealEnder.class));
        cards.add(new SetCardInfo("Talion, the Kindly Lord", 215, Rarity.MYTHIC, mage.cards.t.TalionTheKindlyLord.class));
        cards.add(new SetCardInfo("Tanglespan Lookout", 188, Rarity.UNCOMMON, mage.cards.t.TanglespanLookout.class));
        cards.add(new SetCardInfo("Tough Cookie", 193, Rarity.UNCOMMON, mage.cards.t.ToughCookie.class));
    }

//    @Override
//    public BoosterCollator createCollator() {
//        return new WildsOfEldraineCollator();
//    }
}
