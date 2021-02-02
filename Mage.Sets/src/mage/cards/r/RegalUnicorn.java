
package mage.cards.r;

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
public final class RegalUnicorn extends CardImpl {

    public RegalUnicorn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}");
        this.subtype.add(SubType.UNICORN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);
    }

    private RegalUnicorn(final RegalUnicorn card) {
        super(card);
    }

    @Override
    public RegalUnicorn copy() {
        return new RegalUnicorn(this);
    }
}
