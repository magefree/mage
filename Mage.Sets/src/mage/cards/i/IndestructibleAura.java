
package mage.cards.i;

import java.util.UUID;
import mage.abilities.effects.common.PreventDamageToTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class IndestructibleAura extends CardImpl {

    public IndestructibleAura(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}");

        // Prevent all damage that would be dealt to target creature this turn.
        this.getSpellAbility().addEffect(new PreventDamageToTargetEffect(Duration.EndOfTurn, Integer.MAX_VALUE));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private IndestructibleAura(final IndestructibleAura card) {
        super(card);
    }

    @Override
    public IndestructibleAura copy() {
        return new IndestructibleAura(this);
    }
}
