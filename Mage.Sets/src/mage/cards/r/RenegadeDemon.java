
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
public final class RenegadeDemon extends CardImpl {

    public RenegadeDemon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}{B}");
        this.subtype.add(SubType.DEMON);

        this.power = new MageInt(5);
        this.toughness = new MageInt(3);
    }

    private RenegadeDemon(final RenegadeDemon card) {
        super(card);
    }

    @Override
    public RenegadeDemon copy() {
        return new RenegadeDemon(this);
    }
}
