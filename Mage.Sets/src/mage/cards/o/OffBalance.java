
package mage.cards.o;

import java.util.UUID;
import mage.abilities.effects.common.combat.CantAttackBlockTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class OffBalance extends CardImpl {

    public OffBalance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{W}");

        // Target creature can't attack or block this turn.
        this.getSpellAbility().addEffect(new CantAttackBlockTargetEffect(Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private OffBalance(final OffBalance card) {
        super(card);
    }

    @Override
    public OffBalance copy() {
        return new OffBalance(this);
    }
}
