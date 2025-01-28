
package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author Saga
 */
public final class HasCon2017 extends ExpansionSet {

    private static final HasCon2017 instance = new HasCon2017();

    public static HasCon2017 getInstance() {
        return instance;
    }

    private HasCon2017() {
        super("HasCon 2017", "H17", ExpansionSet.buildDate(2017, 9, 8), SetType.JOKE_SET);
        this.hasBasicLands = false;

        cards.add(new ExpansionSet.SetCardInfo("Grimlock, Dinobot Leader", 1, Rarity.MYTHIC, mage.cards.g.GrimlockDinobotLeader.class));
        cards.add(new ExpansionSet.SetCardInfo("Grimlock, Ferocious King", 1, Rarity.MYTHIC, mage.cards.g.GrimlockFerociousKing.class));
        cards.add(new ExpansionSet.SetCardInfo("Sword of Dungeons & Dragons", 3, Rarity.MYTHIC, mage.cards.s.SwordOfDungeonsAndDragons.class));
    }
}
