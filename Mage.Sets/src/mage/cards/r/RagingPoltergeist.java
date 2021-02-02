
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
public final class RagingPoltergeist extends CardImpl {

    public RagingPoltergeist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{R}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(6);
        this.toughness = new MageInt(1);
    }

    private RagingPoltergeist(final RagingPoltergeist card) {
        super(card);
    }

    @Override
    public RagingPoltergeist copy() {
        return new RagingPoltergeist(this);
    }
}
