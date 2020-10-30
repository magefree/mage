package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/j19
 */
public class JudgeGiftCards2019 extends ExpansionSet {

    private static final JudgeGiftCards2019 instance = new JudgeGiftCards2019();

    public static JudgeGiftCards2019 getInstance() {
        return instance;
    }

    private JudgeGiftCards2019() {
        super("Judge Gift Cards 2019", "J19", ExpansionSet.buildDate(2019, 4, 10), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Chalice of the Void", 7, Rarity.RARE, mage.cards.c.ChaliceOfTheVoid.class));
        cards.add(new SetCardInfo("Isolated Watchtower", 4, Rarity.MYTHIC, mage.cards.i.IsolatedWatchtower.class));
        cards.add(new SetCardInfo("Mirri's Guile", 1, Rarity.MYTHIC, mage.cards.m.MirrisGuile.class));
        cards.add(new SetCardInfo("Monastery Mentor", 5, Rarity.MYTHIC, mage.cards.m.MonasteryMentor.class));
        cards.add(new SetCardInfo("Mox Opal", 3, Rarity.MYTHIC, mage.cards.m.MoxOpal.class));
        cards.add(new SetCardInfo("Reflecting Pool", 8, Rarity.RARE, mage.cards.r.ReflectingPool.class));
        cards.add(new SetCardInfo("Sliver Legion", 2, Rarity.MYTHIC, mage.cards.s.SliverLegion.class));
        cards.add(new SetCardInfo("Yuriko, the Tiger's Shadow", 6, Rarity.RARE, mage.cards.y.YurikoTheTigersShadow.class));
     }
}
