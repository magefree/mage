
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
public final class RagingBull extends CardImpl {

    public RagingBull(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.OX);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
    }

    private RagingBull(final RagingBull card) {
        super(card);
    }

    @Override
    public RagingBull copy() {
        return new RagingBull(this);
    }
}
