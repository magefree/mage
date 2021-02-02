

package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author Loki
 */
public final class TangleSpider extends CardImpl {

    public TangleSpider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}{G}");
        this.subtype.add(SubType.SPIDER);

        this.power = new MageInt(3);
        this.toughness = new MageInt(4);
        this.addAbility(FlashAbility.getInstance());
        this.addAbility(ReachAbility.getInstance());
    }

    private TangleSpider(final TangleSpider card) {
        super(card);
    }

    @Override
    public TangleSpider copy() {
        return new TangleSpider(this);
    }

}
