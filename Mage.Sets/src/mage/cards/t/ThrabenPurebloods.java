
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class ThrabenPurebloods extends CardImpl {

    public ThrabenPurebloods(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{W}");
        this.subtype.add(SubType.DOG);

        this.power = new MageInt(3);
        this.toughness = new MageInt(5);
    }

    private ThrabenPurebloods(final ThrabenPurebloods card) {
        super(card);
    }

    @Override
    public ThrabenPurebloods copy() {
        return new ThrabenPurebloods(this);
    }
}
