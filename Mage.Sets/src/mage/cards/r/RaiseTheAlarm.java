

package mage.cards.r;

import java.util.UUID;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.SoldierToken;

/**
 *
 * @author Loki
 */
public final class RaiseTheAlarm extends CardImpl {

    public RaiseTheAlarm (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{W}");

        this.getSpellAbility().addEffect(new CreateTokenEffect(new SoldierToken(), 2));
    }

    private RaiseTheAlarm(final RaiseTheAlarm card) {
        super(card);
    }

    @Override
    public RaiseTheAlarm copy() {
        return new RaiseTheAlarm(this);
    }

}
