
package mage.cards.e;

import java.util.UUID;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.ErrandOfDutyKnightToken;

/**
 *
 * @author L_J
 */
public final class ErrandOfDuty extends CardImpl {

    public ErrandOfDuty(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{W}");

        // Create a 1/1 white Knight creature token with banding.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new ErrandOfDutyKnightToken()));
    }

    private ErrandOfDuty(final ErrandOfDuty card) {
        super(card);
    }

    @Override
    public ErrandOfDuty copy() {
        return new ErrandOfDuty(this);
    }
}
