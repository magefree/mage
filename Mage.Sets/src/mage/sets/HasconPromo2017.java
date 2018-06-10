
package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 *
 * @author Saga
 */
public final class HasconPromo2017 extends ExpansionSet {

    private static final HasconPromo2017 instance = new HasconPromo2017();

    public static HasconPromo2017 getInstance() {
        return instance;
    }

    private HasconPromo2017() {
        super("HASCON Promo 2017", "H17", ExpansionSet.buildDate(2017, 9, 8), SetType.JOKESET);
        cards.add(new ExpansionSet.SetCardInfo("Grimlock, Dinobot Leader", "1a", Rarity.MYTHIC, mage.cards.g.GrimlockDinobotLeader.class));
        cards.add(new ExpansionSet.SetCardInfo("Grimlock, Ferocious King", "1b", Rarity.MYTHIC, mage.cards.g.GrimlockFerociousKing.class));
        cards.add(new ExpansionSet.SetCardInfo("Sword of Dungeons & Dragons", 3, Rarity.MYTHIC, mage.cards.s.SwordOfDungeonsAndDragons.class));
    }
}
