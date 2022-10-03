package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class TheBrothersWar extends ExpansionSet {

    private static final TheBrothersWar instance = new TheBrothersWar();

    public static TheBrothersWar getInstance() {
        return instance;
    }

    private TheBrothersWar() {
        super("The Brothers' War", "BRO", ExpansionSet.buildDate(2022, 11, 18), SetType.EXPANSION);
        this.blockName = "The Brothers' War";
        this.hasBoosters = false; // temporary

        cards.add(new SetCardInfo("Forest", 286, Rarity.LAND, mage.cards.basiclands.Forest.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Island", 280, Rarity.LAND, mage.cards.basiclands.Island.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Mishra's Foundry", 265, Rarity.RARE, mage.cards.m.MishrasFoundry.class));
        cards.add(new SetCardInfo("Mountain", 284, Rarity.LAND, mage.cards.basiclands.Mountain.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Plains", 278, Rarity.LAND, mage.cards.basiclands.Plains.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Queen Kayla bin-Kroog", 218, Rarity.RARE, mage.cards.q.QueenKaylaBinKroog.class));
        cards.add(new SetCardInfo("Recruitment Officer", 23, Rarity.UNCOMMON, mage.cards.r.RecruitmentOfficer.class));
        cards.add(new SetCardInfo("Surge Engine", 81, Rarity.MYTHIC, mage.cards.s.SurgeEngine.class));
        cards.add(new SetCardInfo("Swamp", 282, Rarity.LAND, mage.cards.basiclands.Swamp.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("The Mightstone and Weakstone", "238a", Rarity.RARE, mage.cards.t.TheMightstoneAndWeakstone.class));
        cards.add(new SetCardInfo("Urza, Lord Protector", 225, Rarity.MYTHIC, mage.cards.u.UrzaLordProtector.class));
        cards.add(new SetCardInfo("Urza, Planeswalker", "238b", Rarity.MYTHIC, mage.cards.u.UrzaPlaneswalker.class));
    }

//    @Override
//    public BoosterCollator createCollator() {
//        return new TheBrothersWarCollator();
//    }
}
