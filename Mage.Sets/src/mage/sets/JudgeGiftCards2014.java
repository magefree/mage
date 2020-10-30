package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/j14
 */
public class JudgeGiftCards2014 extends ExpansionSet {

    private static final JudgeGiftCards2014 instance = new JudgeGiftCards2014();

    public static JudgeGiftCards2014 getInstance() {
        return instance;
    }

    private JudgeGiftCards2014() {
        super("Judge Gift Cards 2014", "J14", ExpansionSet.buildDate(2014, 1, 1), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = true;

        // Phyrexian-only printing. Non-English, so not adding it https://github.com/magefree/mage/pull/6190#issuecomment-582354790
        //cards.add(new SetCardInfo("Elesh Norn, Grand Cenobite", 8, Rarity.RARE, mage.cards.e.EleshNornGrandCenobite.class));
        cards.add(new SetCardInfo("Force of Will", 4, Rarity.RARE, mage.cards.f.ForceOfWill.class));
        cards.add(new SetCardInfo("Forest", "5*", Rarity.LAND, mage.cards.basiclands.Forest.class, FULL_ART_ZEN_VARIOUS));
        cards.add(new SetCardInfo("Greater Good", 2, Rarity.RARE, mage.cards.g.GreaterGood.class));
        cards.add(new SetCardInfo("Hanna, Ship's Navigator", 5, Rarity.RARE, mage.cards.h.HannaShipsNavigator.class));
        cards.add(new SetCardInfo("Island", "2*", Rarity.LAND, mage.cards.basiclands.Island.class, FULL_ART_ZEN_VARIOUS));
        cards.add(new SetCardInfo("Karador, Ghost Chieftain", 1, Rarity.RARE, mage.cards.k.KaradorGhostChieftain.class));
        cards.add(new SetCardInfo("Mountain", "4*", Rarity.LAND, mage.cards.basiclands.Mountain.class, FULL_ART_ZEN_VARIOUS));
        cards.add(new SetCardInfo("Nekusar, the Mindrazer", 7, Rarity.RARE, mage.cards.n.NekusarTheMindrazer.class));
        cards.add(new SetCardInfo("Oloro, Ageless Ascetic", 9, Rarity.RARE, mage.cards.o.OloroAgelessAscetic.class));
        cards.add(new SetCardInfo("Plains", "1*", Rarity.LAND, mage.cards.basiclands.Plains.class, FULL_ART_ZEN_VARIOUS));
        cards.add(new SetCardInfo("Riku of Two Reflections", 3, Rarity.MYTHIC, mage.cards.r.RikuOfTwoReflections.class));
        cards.add(new SetCardInfo("Swamp", "3*", Rarity.LAND, mage.cards.basiclands.Swamp.class, FULL_ART_ZEN_VARIOUS));
        cards.add(new SetCardInfo("Sword of Feast and Famine", 6, Rarity.RARE, mage.cards.s.SwordOfFeastAndFamine.class));
     }
}
