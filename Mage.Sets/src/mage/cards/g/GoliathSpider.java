
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class GoliathSpider extends CardImpl {

    public GoliathSpider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{6}{G}{G}");
        this.subtype.add(SubType.SPIDER);

        this.power = new MageInt(7);
        this.toughness = new MageInt(6);

        this.addAbility(ReachAbility.getInstance());
    }

    private GoliathSpider(final GoliathSpider card) {
        super(card);
    }

    @Override
    public GoliathSpider copy() {
        return new GoliathSpider(this);
    }
}
