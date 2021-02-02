
package mage.cards.n;

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
public final class NeedlepeakSpider extends CardImpl {

    public NeedlepeakSpider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}");
        this.subtype.add(SubType.SPIDER);

        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        this.addAbility(ReachAbility.getInstance());
    }

    private NeedlepeakSpider(final NeedlepeakSpider card) {
        super(card);
    }

    @Override
    public NeedlepeakSpider copy() {
        return new NeedlepeakSpider(this);
    }
}
