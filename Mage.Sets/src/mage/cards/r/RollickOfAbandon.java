
package mage.cards.r;

import java.util.UUID;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author LevelX2
 */
public final class RollickOfAbandon extends CardImpl {

    public RollickOfAbandon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{R}{R}");


        // All creatures get +2/-2 until end of turn.
        this.getSpellAbility().addEffect(new BoostAllEffect(2,-2, Duration.EndOfTurn));
    }

    private RollickOfAbandon(final RollickOfAbandon card) {
        super(card);
    }

    @Override
    public RollickOfAbandon copy() {
        return new RollickOfAbandon(this);
    }
}
