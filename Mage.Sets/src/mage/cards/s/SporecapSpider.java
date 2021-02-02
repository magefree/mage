
package mage.cards.s;

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
public final class SporecapSpider extends CardImpl {

    public SporecapSpider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.SPIDER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(5);

        this.addAbility(ReachAbility.getInstance());
    }

    private SporecapSpider(final SporecapSpider card) {
        super(card);
    }

    @Override
    public SporecapSpider copy() {
        return new SporecapSpider(this);
    }
}
