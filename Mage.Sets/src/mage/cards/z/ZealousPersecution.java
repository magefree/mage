
package mage.cards.z;

import java.util.UUID;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.BoostOpponentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author North
 */
public final class ZealousPersecution extends CardImpl {

    public ZealousPersecution(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{W}{B}");

        this.getSpellAbility().addEffect(new BoostControlledEffect(1, 1, Duration.EndOfTurn));
        this.getSpellAbility().addEffect(new BoostOpponentsEffect(-1, -1, Duration.EndOfTurn));
    }

    public ZealousPersecution(final ZealousPersecution card) {
        super(card);
    }

    @Override
    public ZealousPersecution copy() {
        return new ZealousPersecution(this);
    }
}
