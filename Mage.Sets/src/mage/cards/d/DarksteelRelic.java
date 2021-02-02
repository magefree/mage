
package mage.cards.d;

import java.util.UUID;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author North
 */
public final class DarksteelRelic extends CardImpl {

    public DarksteelRelic(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{0}");

        this.addAbility(IndestructibleAbility.getInstance());
    }

    private DarksteelRelic(final DarksteelRelic card) {
        super(card);
    }

    @Override
    public DarksteelRelic copy() {
        return new DarksteelRelic(this);
    }
}
