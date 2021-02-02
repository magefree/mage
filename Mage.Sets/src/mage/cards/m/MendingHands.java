
package mage.cards.m;

import java.util.UUID;
import mage.abilities.effects.common.PreventDamageToTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author Loki
 */
public final class MendingHands extends CardImpl {

    public MendingHands(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{W}");

        // Prevent the next 4 damage that would be dealt to any target this turn.
        this.getSpellAbility().addEffect(new PreventDamageToTargetEffect(Duration.EndOfTurn, 4));
        this.getSpellAbility().addTarget(new TargetAnyTarget());
    }

    private MendingHands(final MendingHands card) {
        super(card);
    }

    @Override
    public MendingHands copy() {
        return new MendingHands(this);
    }
}
