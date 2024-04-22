

package mage.cards.m;

import java.util.UUID;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.MyrToken;

/**
 *
 * @author Loki
 */
public final class MastersCall extends CardImpl {

    public MastersCall (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{W}");

        this.getSpellAbility().addEffect(new CreateTokenEffect(new MyrToken(), 2));
    }

    private MastersCall(final MastersCall card) {
        super(card);
    }

    @Override
    public MastersCall copy() {
        return new MastersCall(this);
    }

}
